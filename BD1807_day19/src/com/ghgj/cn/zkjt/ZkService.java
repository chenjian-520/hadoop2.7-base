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
		
		//����zk����
		zk=new ZooKeeper("hadoop01:2181", 5000, new Watcher(){

			//����������ʱ��  �ص��������
			@Override
			public void process(WatchedEvent event) {
				//��ȡ�¼�������
				EventType type = event.getType();
				
				//nodecreated  nodechildrenchanged(nodedeleted  nodecreated)
				if(EventType.NodeChildrenChanged.equals(type)){
					try {
						List<String> newchildren = zk.getChildren(parent, true);
						//�ж��������ϵ�size��С  new>old
						if(newchildren.size()>oldchildren.size()){
							//�ж���������е��㲻ͬ��Ԫ��
							String diff=getDiff(oldchildren, newchildren);
							String context=new String(zk.getData(parent+"/"+diff, true, null));
							
							System.out.println("�����һ���ڵ㣬�ڵ����ֽ�"+diff+"�ڵ��������"+context);
							//��oldchildren���¸�ֵ
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
					//�����¼��Ľڵ�  /config/core-site.xml
					String path = event.getPath();
					String name=path.substring(path.lastIndexOf("/")+1);
					String text=null;
					try {
						text = new String(zk.getData(path, true, null));
						System.out.println("�޸���һ�������ļ����ڵ�Ϊ"+name+"����Ϊ"+text);
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
					System.out.println("ɾ����һ�������ļ��������ļ�������Ϊ"+name);
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
		
		//׼������

		if(zk.exists(parent, null)==null){
			zk.create(parent, "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		}
		if(zk.exists(parent+"/hdfs-site.xml", null)==null){
			zk.create(parent+"/hdfs-site.xml", "aa".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		}
		if(zk.exists(parent+"/core-site.xml", null)==null){
			zk.create(parent+"/core-site.xml", "bb".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		}
		
		//��Ӽ���
		//����Ӹ��ڵ�
		oldchildren = zk.getChildren(parent, true);
		//ѭ���������е��ӽڵ�  ��Ӽ���
		for(String c:oldchildren){
			String child_path=parent+"/"+c;
			zk.getData(child_path, true, null);
		}
		
		Thread.sleep(Long.MAX_VALUE);
	}
	
	static String getDiff(List<String> oldlist,List<String> newlist){
		//�����µļ���  ���ϵļ����в���  û��֤���µ�Ԫ��
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
