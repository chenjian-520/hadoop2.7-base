package com.ghgj.cn.zk;

import java.io.IOException;
import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class ZkApi {

	public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
		//获取zk连接
		ZooKeeper zk = new ZooKeeper("192.168.66.131:2181", 5000, null);
		//操作文件系统
		
		//创建节点 create
		/**
		 * 参数1：节点路径  string
		 * 参数2：节点内容  byte[]
		 * 参数3：权限  默认
		 * 参数3：节点的类型
		 */
		/**
		 * 永久的 PERSISTENT
		 * 临时的 PERSISTENT_SEQUENTIAL
		 * 永久有编号 EPHEMERAL
		 * 临时有编号 EPHEMERAL_SEQUENTIAL
		 */
	//	zk.create("/test_api","nihenc".getBytes(),
	//			Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
		//修改节点内容
		/**
		 * 参数1：节点路径
		 * 参数2：修改内容
		 * 参数3：数据 版本  如果不知道 -1 代表获取最新版本
		 */
	//	zk.setData("/test_api", "sss".getBytes(), -1);
		
		//删除节点  只能删除空节点
		//非空节点需要递归删除
	//	zk.delete("/test_api", -1);
		
		//查看节点内容 get
		/**
		 * 参数1：节点路径
		 * 参数2：监听   不见听传null
		 * 参数3：节点状态对象  不知道  传  null
		 */
	/*	byte[] data = zk.getData("/test_api", null, null);
		String con = new String(data);
		System.out.println(con);*/
		
		//查看子节点 ls
		/**
		 * 参数1：节点路径
		 * 参数2：监听
		 */
		List<String> children = zk.getChildren("/", null);
		for(String c: children){
			System.out.println(c);
		}
		System.out.println(children);
		
	/*	//判断节点是否存在
		Stat exists = zk.exists("/text", null);
		//返回值是一个节点状态对象   封装节点状态信息的   当前节点不存在  返回null
		System.out.println(exists);
		System.out.println(exists.getDataLength());*/
		
		
		
	}

}
