package com.aura.hbase.hbase.homework;

import java.io.IOException;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.protobuf.generated.CellProtos.Cell;


public class MyHomeWork implements HBaseDemoInterface {
	private static Connection connection ;
	private static Admin admin ;
	private static String QUORUM="hbase.zookeeper.quorum";
	private static String ZK_VALUE="hadoop01:2181,hadoop02:2181,hadoop03:2181";
	static{
		Configuration config = HBaseConfiguration.create();
		config.set(QUORUM, ZK_VALUE );
		try {
			connection = ConnectionFactory.createConnection(config);
			 admin = connection.getAdmin();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws Exception {
		MyHomeWork home = new MyHomeWork();
		String[] family={"info_1","info_2"};
//		home.createTable("test03",s);
//		home.descTable("text_03");
	/*	
	   TableName name = TableName.valueOf("text_03");
		HTableDescriptor table = new HTableDescriptor(name);
		for(String s:family){
			HColumnDescriptor hc = new HColumnDescriptor(s);
			table.addFamily(hc);
		}
		home.createTable(table);
	*/
		boolean existTable = home.existTable("text_03");
		System.out.println(existTable);
	}
	
	// 查询所有表
	@Override
	public void getAllTables() throws Exception {
		HTableDescriptor[] listTables = admin.listTables();
		for(HTableDescriptor table:listTables){
			System.out.println(table.getNameAsString());
		}
	}
	
	// 创建表，传参，表名和列簇的名字
	@Override
	public void createTable(String tableName, String[] family) throws Exception {
		TableName name = TableName.valueOf(tableName);
		HTableDescriptor table = new HTableDescriptor(name);
		for(String s:family){
			HColumnDescriptor hc = new HColumnDescriptor(s);
			table.addFamily(hc);
		}
		admin.createTable(table);
	}
	
	// 创建表，传参:封装好的多个列簇
	@Override
	public void createTable(HTableDescriptor htds) throws Exception {
		admin.createTable(htds);
	}
	
	// 创建表，传参，表名和封装好的多个列簇
	@Override
	public void createTable(String tableName, HTableDescriptor htds) throws Exception {
		htds.setName(tableName.getBytes());
		admin.createTable(htds);
	}
	
	// 查看表的列簇属性
	@Override
	public void descTable(String tableName) throws Exception {
		HTableDescriptor table = admin.getTableDescriptor(TableName.valueOf(tableName.getBytes()));
		HColumnDescriptor[] columnFamilies = table.getColumnFamilies();
		   for(HColumnDescriptor columnFamilie : columnFamilies) {
               //获取列簇的名字
               String columnFamilyName = columnFamilie.getNameAsString();
               System.out.print("\t"+"columnFamilyName:"+columnFamilyName);
           }
	}
	
	// 判断表存在不存在
	@Override
	public boolean existTable(String tableName) throws Exception {
		boolean tableExists = admin.tableExists(TableName.valueOf(tableName));
		return tableExists;
	}
	// disable表
	@Override
	public void disableTable(String tableName) throws Exception {
		admin.disableTable(TableName.valueOf(tableName));
	}
	// drop表
	@Override
	public void dropTable(String tableName) throws Exception {
		admin.deleteTable(TableName.valueOf(tableName));
	}
	// 修改表(增加和删除)
	@Override
	public void modifyTable(String tableName) throws Exception {
		HTableDescriptor table = admin.getTableDescriptor(TableName.valueOf(tableName));
		table.addFamily(new HColumnDescriptor("info_88"));
	}
	// 修改表(增加和删除)
	@Override
	public void modifyTable(String tableName, String[] addColumn, String[] removeColumn) throws Exception {
		HTableDescriptor table = admin.getTableDescriptor(TableName.valueOf(tableName));
		HColumnDescriptor hcd = new HColumnDescriptor("info_88");
		table.removeFamily(hcd.getName());
	}
	// 修改表(增加和删除) 替换该表所有列簇
	@Override
	public void modifyTable(String tableName, HColumnDescriptor hcds) throws Exception {
		HTableDescriptor table = admin.getTableDescriptor(TableName.valueOf(tableName));
		table.modifyFamily(hcds);
	}
	// 添加或者修改数据
	@Override
	public void addData(String tableName, String rowKey, String[] column, String[] value) throws Exception {
		Table table = connection.getTable(TableName.valueOf(tableName));
		Put put = new Put(rowKey.getBytes());
		HTableDescriptor tableDescriptor = admin.getTableDescriptor(TableName.valueOf(tableName));
		HColumnDescriptor[] columnFamilies = tableDescriptor.getColumnFamilies();
		for(int i=0;i<=column.length;i++){
		put.addColumn(columnFamilies[0].getNameAsString().getBytes(), column[i].getBytes(), value[i].getBytes());
		table.put(put);
		}
	}
	// 添加或者修改数据
	@Override
	public void putData(String tableName, String rowKey, String familyName, String columnName, String value)
			throws Exception {
		Table table = connection.getTable(TableName.valueOf(tableName));
		Put put = new Put(rowKey.getBytes());
		put.addImmutable(familyName.getBytes(), columnName.getBytes(), value.getBytes());
		table.put(put);
	}
	// 添加或者修改数据
	@Override
	public void putData(String tableName, String rowKey, String familyName, String columnName, String value,
			long timestamp) throws Exception {
		Table table = connection.getTable(TableName.valueOf(tableName));
		Put put = new Put(rowKey.getBytes());
		put.addImmutable(familyName.getBytes(), columnName.getBytes(),timestamp, value.getBytes());
		table.put(put);
		
	}
	// 添加或者修改数据
	@Override
	public void putData(Put put) throws Exception {
		// TODO Auto-generated method stub
		
	}
	// 添加或者修改数据
	@Override
	public void putData(List<Put> putList) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Result getResult(String tableName, String rowKey) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Result getResult(String tableName, String rowKey, String familyName) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Result getResult(String tableName, String rowKey, String familyName, String columnName) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Result getResultByVersion(String tableName, String rowKey, String familyName, String columnName,
			int versions) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultScanner getResultScann(String tableName) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultScanner getResultScann(String tableName, Scan scan) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteColumn(String tableName, String rowKey) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteColumn(String tableName, String rowKey, String falilyName) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteColumn(String tableName, String rowKey, String falilyName, String columnName) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
