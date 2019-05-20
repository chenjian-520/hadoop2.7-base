package com.ghgj.cn.define_in;

import java.io.IOException;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;

/**
 * 自定义文件加载类
 * @author ASUS
 * 泛型：文件读取完成的key  value 的泛型 读取完成---Mapper
 * 一次读取一个文件
 * 文件内容
 */
public class MyFileInputFormat extends FileInputFormat<NullWritable, Text>{
	@Override
	public RecordReader<NullWritable, Text> createRecordReader(InputSplit split, TaskAttemptContext context)
			throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		MyRecordReader mrr = new MyRecordReader();
		mrr.initialize(split, context);
		return mrr;
	}
}
