package com.ghgj.cn.zk;

import java.io.IOException;

import org.apache.zookeeper.ZooKeeper;

public class TestZkclick {
	public static void main(String[] args) throws IOException {
		//����һ��zookeeper����
		/**
		 * ����1��zk���ӵ�url
		 * ����2����ʱʱ��
		 * ����3������ ����Ҫ���� ��null
		 */
		ZooKeeper zk = new ZooKeeper("192.168.66.131:2181", 5000, null);
		System.out.println(zk);
		
		
	}
}
