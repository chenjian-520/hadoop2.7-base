package com.aura.hbase.hbase;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;

/**
 *  这是hbase的java api的第一个代码
 *  
 *  
 *  
 */
public class HBaseDemo_01 {

	public static void main(String[] args) throws Exception {
		
		/**
		 * 获取一个配置对象
		 * 
		 * 要点：获取到了配置信息之后，就一定要告诉这个配置对象，说接下来要创建的连接是哪个hbase集群的连接
		 */
		Configuration config = HBaseConfiguration.create();
		config.set("hbase.zookeeper.quorum", "hadoop01:2181,hadoop02:2181,hadoop03:2181");
		
		/**
		 * 通过配置对象创建连接
		 */
		Connection connection = ConnectionFactory.createConnection(config);
		
		/**
		 * 通过连接获取管理员对象
		 */
		Admin admin = connection.getAdmin();
		
		/**
		 * 通过管理员对象就可以对表进行各种操作
		 */
		HTableDescriptor[] tables = admin.listTables();
		System.out.println(tables);
		
		/**
		 * 针对结果进行处理
		 */
		for(HTableDescriptor table : tables){
			System.out.println(table.getNameAsString());
			System.out.println("ssdfs4");
		}
		
		
		/**
		 * 关闭连接
		 */
		connection.close();
		
	}
}
