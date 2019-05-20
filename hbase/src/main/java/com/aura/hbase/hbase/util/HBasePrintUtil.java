package com.aura.hbase.hbase.util;

import java.util.List;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * 作者： 马中华   https://blog.csdn.net/zhongqi2513
 * 时间： 2017/10/30 16:04
 * 描述： 辅助方法工具类
 */
public class HBasePrintUtil {

	public static void printResultScanner(ResultScanner resultScann) {
		for (Result result : resultScann) {
			printResult(result);
		}
	}

	public static void printResult(Result result) {
		List<Cell> cells = result.listCells();
		for (int i = 0; i < cells.size(); i++) {
			Cell cell = cells.get(i);
			printCellNew(cell);
		}
	}
	
	public static void printCellNew(Cell cell){
		String row = Bytes.toString(CellUtil.cloneRow(cell));
		String cf = Bytes.toString(CellUtil.cloneFamily(cell));
		String column = Bytes.toString(CellUtil.cloneQualifier(cell));
		String value = Bytes.toString(CellUtil.cloneValue(cell));
		long timestamp = cell.getTimestamp();
		System.out.println(row + "\t" + cf + "\t" + column + "\t" + timestamp + "\t" + value);
	}
	

	public static void printCell(Cell cell) {
		System.out.println(Bytes.toString(cell.getRow()) + "\t" + Bytes.toString(cell.getFamily()) + "\t" + Bytes.toString(cell.getQualifier())
				+ "\t" + Bytes.toString(cell.getValue()) + "\t" + cell.getTimestamp());
	}

	public static void printKeyValye(KeyValue kv) {
		System.out.println(Bytes.toString(kv.getRow()) + "\t" + Bytes.toString(kv.getFamily()) + "\t" + Bytes.toString(kv.getQualifier()) + "\t"
				+ Bytes.toString(kv.getValue()) + "\t" + kv.getTimestamp());
	}
}
