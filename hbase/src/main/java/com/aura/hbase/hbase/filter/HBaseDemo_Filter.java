package com.aura.hbase.hbase.filter;

import org.apache.commons.io.FileCleaningTracker;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.SubstringComparator;
import org.apache.hadoop.hbase.filter.ValueFilter;

import com.aura.hbase.hbase.util.HBasePrintUtil;



public class HBaseDemo_Filter {
	
	private static Connection connection;
	private static Admin admin;
	private static Table table;
	
	private static final String ZK_CONNECT_KEY = "hbase.zookeeper.quorum";
	private static final String ZK_CONNECT_VALUE = "hadoop02:2181,hadoop03:2181,hadoop04:2181";
	
	private static final String TABLE_NAME = "user_info";
	private static final String FAMILY_BASE = "base_info";
	private static final String FAMILY_EXTRA = "extra_info";
	
	/**
	 * o  0
	 * l  1
	 */
	
	static {
		
		Configuration config = HBaseConfiguration.create();
		config.set(ZK_CONNECT_KEY, ZK_CONNECT_VALUE);
		
		try {
			connection = ConnectionFactory.createConnection(config);
			admin = connection.getAdmin();
			table = connection.getTable(TableName.valueOf(TABLE_NAME));
		} catch (Exception e) {
			System.out.println("获取HBase数据库的连接失败");
		}
		
	}

	public static void main(String[] args) throws Exception {
		
		Scan scan = new Scan();
		
		Filter filter1 = new ValueFilter(CompareOp.EQUAL, new SubstringComparator("2"));
		Filter filter2 = new ValueFilter(CompareOp.EQUAL, new SubstringComparator("zhangsan"));
		
		// 过滤链
		// 区块链  BlockChain
		Filter filter = new FilterList(filter1, filter2);
		
		scan.setFilter(filter);
		
		
		ResultScanner scanner = table.getScanner(scan);
		for(Result result: scanner){
			
			HBasePrintUtil.printResult(result);
		}
	}
}
