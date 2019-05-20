package com.ghgj.cn.zkjt;


import java.io.IOException;
import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

public class ZkService {
	static String parent="/config";
	static ZooKeeper zk=null;
	static List<String> oldchildren=null;
	public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
		
		//创建zk对象
		zk=new ZooKeeper("hadoop01:2181", 5000, new Watcher(){

			//监听触发的时候  回调这个方法
			@Override
			public void process(WatchedEvent event) {
				//获取事件的类型
				EventType type = event.getType();
				
				//nodecreated  nodechildrenchanged(nodedeleted  nodecreated)
				if(EventType.NodeChildrenChanged.equals(type)){
					try {
						List<String> newchildren = zk.getChildren(parent, true);
						//判断两个集合的size大小  new>old
						if(newchildren.size()>oldchildren.size()){
							//判断两个结合中的你不同的元素
							String diff=getDiff(oldchildren, newchildren);
							String context=new String(zk.getData(parent+"/"+diff, true, null));
							
							System.out.println("添加了一个节点，节点名字叫"+diff+"节点的内容是"+context);
							//给oldchildren重新赋值
							oldchildren=newchildren;
						}
					} catch (KeeperException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				
				//nodedatachanged
				if(EventType.NodeDataChanged.equals(type)){
					//触发事件的节点  /config/core-site.xml
					String path = event.getPath();
					String name=path.substring(path.lastIndexOf("/")+1);
					String text=null;
					try {
						text = new String(zk.getData(path, true, null));
						System.out.println("修改了一个配置文件，节点为"+name+"内容为"+text);
					} catch (KeeperException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				
				//nodedeleted
				if(EventType.NodeDeleted.equals(type)){
					String path = event.getPath();
					String name=path.substring(path.lastIndexOf("/")+1);
					System.out.println("删除了一个配置文件，配置文件的名字为"+name);
					try {
						oldchildren=zk.getChildren(parent, true);
					} catch (KeeperException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				
			}
			
		});
		
		//准备数据

		if(zk.exists(parent, null)==null){
			zk.create(parent, "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		}
		if(zk.exists(parent+"/hdfs-site.xml", null)==null){
			zk.create(parent+"/hdfs-site.xml", "aa".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		}
		if(zk.exists(parent+"/core-site.xml", null)==null){
			zk.create(parent+"/core-site.xml", "bb".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		}
		
		//添加监听
		//先添加父节点
		oldchildren = zk.getChildren(parent, true);
		//循环遍历所有的子节点  添加监听
		for(String c:oldchildren){
			String child_path=parent+"/"+c;
			zk.getData(child_path, true, null);
		}
		
		Thread.sleep(Long.MAX_VALUE);
	}
	
	static String getDiff(List<String> oldlist,List<String> newlist){
		//遍历新的集合  在老的集合中查找  没有证明新的元素
		String diff="";
		for(String l:newlist){
			if(!oldlist.contains(l)){
				diff=l;
				break;
			}
			
		}
		return diff;
		

	}

}
