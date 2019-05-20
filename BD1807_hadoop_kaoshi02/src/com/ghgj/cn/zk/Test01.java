package com.ghgj.cn.zk;

import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

public class Test01 {

	public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
		// TODO Auto-generated method stub
		ZooKeeper zk = new ZooKeeper("192.168.66.131:2181", 5000, null);
		//创建节点 create

		createtrue(zk,"/ge/cc/ss");
		zk.close();
		}
	
	private static void createtrue(ZooKeeper zk,String file) throws KeeperException, InterruptedException {
		String[] split = file.split("/");
		StringBuffer str = new StringBuffer("/");	
		for(int i=1;i<split.length;i++){
			 str.append(split[i]).append("/");
			 str.substring(0, str.length()-1);
			 String a=str.toString().substring(0,str.length()-1);
			if(zk.exists(a, null) == null){
				zk.create(a, "1".getBytes(),  Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
			}		
		}	
	}
}
