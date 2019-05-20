package com.aura.hbase.hbase.homework;

import java.util.List;

import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;

public interface HBaseDemoInterface {
	
	// 查询所有表
	public abstract void getAllTables() throws Exception;

	// 创建表，传参，表名和列簇的名字
	public abstract void createTable(String tableName, String[] family) throws Exception;
	
	// 创建表，传参:封装好的多个列簇
	public abstract void createTable(HTableDescriptor htds) throws Exception;

	// 创建表，传参，表名和封装好的多个列簇
	public abstract void createTable(String tableName, HTableDescriptor htds) throws Exception;
	
	// 查看表的列簇属性
	public abstract void descTable(String tableName) throws Exception;
	
	// 判断表存在不存在
	public abstract boolean existTable(String tableName) throws Exception;
		
	// disable表
	public abstract void disableTable(String tableName) throws Exception;
		
	// drop表
	public abstract void dropTable(String tableName) throws Exception; 
	
	// 修改表(增加和删除)
	public abstract void modifyTable(String tableName)  throws Exception;
	public abstract void modifyTable(String tableName, String[] addColumn, String[] removeColumn)  throws Exception;
	public abstract void modifyTable(String tableName, HColumnDescriptor hcds)  throws Exception;
	
	// 添加或者修改数据
	public abstract void addData(String tableName, String rowKey, String[] column, String[] value) throws Exception;
	public abstract void putData(String tableName, String rowKey, String familyName, String columnName, String value) throws Exception;
	public abstract void putData(String tableName, String rowKey, String familyName, String columnName, String value, long timestamp) throws Exception;
	public abstract void putData(Put put) throws Exception;
	public abstract void putData(List<Put> putList) throws Exception;

	// 根据rowkey查询数据
	public abstract Result getResult(String tableName, String rowKey) throws Exception;
	public abstract Result getResult(String tableName, String rowKey, String familyName) throws Exception;
	public abstract Result getResult(String tableName, String rowKey, String familyName, String columnName) throws Exception;
	// 查询指定version
	public abstract Result getResultByVersion(String tableName, String rowKey, String familyName, String columnName, int versions) throws Exception;

	// scan全表数据
	public abstract ResultScanner getResultScann(String tableName) throws Exception;
	public abstract ResultScanner getResultScann(String tableName, Scan scan) throws Exception;

	// 删除数据（指定的列）
	public abstract void deleteColumn(String tableName, String rowKey) throws Exception;
	public abstract void deleteColumn(String tableName, String rowKey, String falilyName) throws Exception;
	public abstract void deleteColumn(String tableName, String rowKey,String falilyName, String columnName) throws Exception;

}
