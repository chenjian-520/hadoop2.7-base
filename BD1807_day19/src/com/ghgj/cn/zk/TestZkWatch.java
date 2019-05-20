package com.ghgj.cn.zk;

import java.io.IOException;



import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.ZooKeeper;

public class TestZkWatch {

	public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
		// 获取zk的连接
		ZooKeeper zk=new ZooKeeper("192.168.66.131:2181", 5000, null);
		
		//添加监听
		/**
		 * getData
		 * getChildren
		 * exists
		 */
		
		/**
		 * 参数2：传入 1. watcher对象   
		 * 2.boolean
		 */
		
		zk.exists("/test1",new Watcher(){
			/**
			 * zk 监听除非的时候调用的方法
			 * zk的触发 监听回调方法
			 * 
			 * 参数： watchedEvent event 监听事件对象
			 * 	final private EventType eventType
			 *  private String path；  触发事件的节点路径
			 */
			 @Override
			public void process(WatchedEvent event) {
				// TODO Auto-generated method stub
				EventType type = event.getType();
				String path = event.getPath();
				System.out.println(type+"--------"+path);
				
			}
		});
		
		
		// 传 true   意味着前面指定了监听对象  和回调函数
		zk.exists("test1", true);
		
		Thread.sleep(10000);
	}

}
