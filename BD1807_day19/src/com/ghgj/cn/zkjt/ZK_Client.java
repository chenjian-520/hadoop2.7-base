package com.ghgj.cn.zkjt;

import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooDefs.Ids;

public class ZK_Client {
	public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
		ZooKeeper zk= new ZooKeeper("192.168.66.131:2181", 5000,null);
		
		String path = "/config/hdfs_site.xml";
		//´¥·¢¼àÌý nodedatachanged
//		zk.setData(path, "dfs.replication=3".getBytes(), -1);
		
//		zk.delete(path, -1);
		
		
		zk.create("/config/hdfs_site5.xml", "dfs.replication=4".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		
		
	}
}
