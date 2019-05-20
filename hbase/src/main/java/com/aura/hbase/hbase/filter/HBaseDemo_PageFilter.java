package com.aura.hbase.hbase.filter;

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
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.PageFilter;
import org.apache.hadoop.hbase.util.Bytes;

import com.aura.hbase.hbase.util.HBasePrintUtil;



public class HBaseDemo_PageFilter {
	
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
		
		// 这是我们已经实现了的方法。
//		String startRow = "baiyc_20150716_0001";
		long pageSize = 4;
//		ResultScanner resultScaner = getPageData(pageSize, startRow);
//		HBasePrintUtil.printResultScanner(resultScaner);
		
		
		// 这是产品经理要求的方法
		// 每页4条数据，查询第二页
		long pageIndex = 3;
		ResultScanner resultScaner1 = getPageData(pageSize, pageIndex);
		HBasePrintUtil.printResultScanner(resultScaner1);
	}
	
	
	// 这就是转换的方法
	public static ResultScanner getPageData(long pageSize, long pageIndex) throws Exception{
		
		/** 
		 * 传入进来的参数； long pageSize, long pageIndex
		 * 要调用的方法的参数； long pageSize, String startRow
		 */
		//  如何把 pageIndex转换成 startRowKey
		String startRow = getPageStartRow(pageIndex, pageSize);
		
		return getPageData(pageSize, startRow);
		
	}
	
	public static String getPageStartRow(long pageIndex, long pageSize) throws Exception{
		String startRow = "";
		
//		if(pageIndex == 1){
//			startRow = "baiyc_20150716_0001";
//		}else if(pageIndex == 2){
//			startRow = "baiyc_20150716_0004";
//		}
//		else if(pageIndex == 3){
//			startRow = "baiyc_20150716_0006";
//		}
		// 以下编写的所有的代码都是为了吧  pageIndex 转换成 合适的  startRow
		
		
		if(pageIndex == 1){
			
			startRow = null;
			
		}else if(pageIndex > 1){
			
			long totalNumber = (pageIndex - 1) * pageSize + 1;
			
			// 查询第一页： 第一页：totalNumber
			ResultScanner resultScanner = getPageData(totalNumber, null);
			
			Result result = null;
			for(Result r : resultScanner){
				result = r;
			}
			
			startRow = Bytes.toString(result.getRow());
		}else {
			
			return getPageStartRow(1, pageSize);
		}
		
		return startRow;
	}
	
	
	// 只要指定    这一页的startRowkey，  每页多少条   就可以查询到这一页的几条数据
	public static ResultScanner getPageData(long pageSize, String startRow)  throws Exception{
		
		Scan scan = new Scan();
		scan.addColumn("base_info".getBytes(), "name".getBytes());
		
		// 这个参数：每页多少条， 但是没有让我指定查询第几页
		Filter filter = new PageFilter(pageSize);
		scan.setFilter(filter);
		
		
		/**
		 * 难点：
		 * 
		 * 	  传入的参数是页码，但是需要的真正的参数值是  startRowKey,  
		 *  	
		 *   如何把 pageIndex转换成 startRowKey
		 */
		if(startRow != null){
			scan.setStartRow(startRow.getBytes());
		}
		
		ResultScanner scanner = table.getScanner(scan);
		
		return scanner;
		
	}
}
