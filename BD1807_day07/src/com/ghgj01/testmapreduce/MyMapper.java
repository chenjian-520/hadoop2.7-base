package com.ghgj01.testmapreduce;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class MyMapper extends Mapper<LongWritable, Text, Text, IntWritable>{
	//一行调用一次
	Text mk=new Text();
	IntWritable mv=new IntWritable();
	
	@Override
	protected void map(LongWritable key, Text value,
			Context context)
			throws IOException, InterruptedException {
		//获取每一行内容
		String line = value.toString();
		//切分
		String[] datas = line.split("\t");
		//循环遍历  发送
		for(String dt:datas){
			
			//设置值
			mk.set("a");
			mv.set(Integer.parseInt(dt));
			context.write(mk, mv);
		}
	}

}
