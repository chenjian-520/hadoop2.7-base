package com.ghgj.cn.zkwork;

import java.io.IOException;
import java.util.List;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class HomeWork02 {

	public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
		// TODO Auto-generated method stub
		ZooKeeper zk = new ZooKeeper("192.168.66.131:2181", 5000, null);

		String st = "/text02";
		Stat exists2 = zk.exists(st, null);
		if(exists2!=null){
			digui(zk,st);
		}else{
			zk.delete(st, -1);
		}
		zk.delete(st, -1);
	}
	
	
	public static void digui(ZooKeeper zk,String url) throws IOException, KeeperException, InterruptedException{
		
			List<String> children = zk.getChildren(url, null);	
			
			if(children.size()!=0){
				
				for(String c:children){
					Stat exists = zk.exists(url+"/"+c, null);
					int numChildren = exists.getNumChildren();
					if(numChildren!=0){
						digui(zk,url+"/"+c);
					}
					zk.delete(url+"/"+c, -1);
				}
			}
		
	}

	
}
