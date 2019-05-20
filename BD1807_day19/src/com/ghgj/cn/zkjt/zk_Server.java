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

public class zk_Server {
	static String path = "/config";
	static ZooKeeper zk = null;
	static List<String> oldChildren = null;
	public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
		
		zk = new ZooKeeper("192.168.66.131:2181", 5000, new Watcher(){
			
			//����������ʱ��    �ص��������
			@Override
			public void process(WatchedEvent event) {
				EventType type = event.getType();

				//nodecreated	nodechildrenchanged(nodeleted  nodecreated)
				if(EventType.NodeChildrenChanged.equals(type)){
					try {
						List<String> newChildren = zk.getChildren(path, true);
						//�ж��������ϵ�size��С   new>old
						if(newChildren.size() > oldChildren.size()){
							//�ж����������еĲ�ͬ��Ԫ��
							String diff = getDiff(oldChildren, newChildren);
							String context = new String(zk.getData(path+"/"+diff, true, null));
							System.out.println("�����һ���ڵ�,�ڵ����ֽ�"+diff+"�ڵ�����"+context);
							//��oldChildren���¸�ֵ
							oldChildren = newChildren;
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
				
				//nodedeleted
				
			}
			
		});
		
		//׼������
		if(zk.exists(path, null) == null){
			zk.create(path, "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		}
		if(zk.exists(path+"/hdfs-site.xml", null) == null){
			zk.create(path+"/hdfs-site.xml", "aa".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		}
		if(zk.exists(path+"/core-site.xml", null) == null){
			zk.create(path+"/core-site.xml", "bb".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		}
		//��Ӽ���
		//����Ӹ��ڵ�
		oldChildren = zk.getChildren(path, true);
		//ѭ���������е��ӽڵ������Ӽ���
		for(String c:oldChildren){
			String child_path = path+"/"+c;
			zk.getData(child_path, true, null);
		}
		Thread.sleep(Long.MAX_VALUE);	
	}
	
	
	public static String getDiff(List<String> oldlist,List<String> newlist){
		//�����¼���     ���ϵļ����в���      û��֤���µ�Ԫ��
		String diff = "";
		for(String l:newlist){
			if(!oldlist.contains(l)){
				diff = l;
			}
		}
		return diff;
	}
	
	
	
}
