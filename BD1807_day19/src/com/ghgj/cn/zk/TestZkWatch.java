package com.ghgj.cn.zk;

import java.io.IOException;



import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.ZooKeeper;

public class TestZkWatch {

	public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
		// ��ȡzk������
		ZooKeeper zk=new ZooKeeper("192.168.66.131:2181", 5000, null);
		
		//��Ӽ���
		/**
		 * getData
		 * getChildren
		 * exists
		 */
		
		/**
		 * ����2������ 1. watcher����   
		 * 2.boolean
		 */
		
		zk.exists("/test1",new Watcher(){
			/**
			 * zk �������ǵ�ʱ����õķ���
			 * zk�Ĵ��� �����ص�����
			 * 
			 * ������ watchedEvent event �����¼�����
			 * 	final private EventType eventType
			 *  private String path��  �����¼��Ľڵ�·��
			 */
			 @Override
			public void process(WatchedEvent event) {
				// TODO Auto-generated method stub
				EventType type = event.getType();
				String path = event.getPath();
				System.out.println(type+"--------"+path);
				
			}
		});
		
		
		// �� true   ��ζ��ǰ��ָ���˼�������  �ͻص�����
		zk.exists("test1", true);
		
		Thread.sleep(10000);
	}

}
