package com.ghgj.cn.zk;

import java.io.IOException;

import org.apache.zookeeper.ZooKeeper;

public class TestZkclick {
	public static void main(String[] args) throws IOException {
		//创建一个zookeeper对象
		/**
		 * 参数1：zk连接的url
		 * 参数2：超时时间
		 * 参数3：监听 不需要监听 传null
		 */
		ZooKeeper zk = new ZooKeeper("192.168.66.131:2181", 5000, null);
		System.out.println(zk);
		
		
	}
}
