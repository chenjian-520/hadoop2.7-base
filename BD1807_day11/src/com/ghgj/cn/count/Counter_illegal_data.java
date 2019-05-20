package com.ghgj.cn.count;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Counter;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


public class Counter_illegal_data {
	static class MyMapper extends Mapper<LongWritable, Text, NullWritable, NullWritable>{
		@Override
		protected void map(LongWritable key, Text value,
				Context context)
				throws IOException, InterruptedException {
			String[] datas = value.toString().split(",");
			//int score=Integer.parseInt(datas[2]);
			//字段缺失
			Counter counter=context.getCounter(MyCounter.ILLEGAL_DATA_RECORDS);
			if(datas.length!=3){
				//获取计数器  进行++
				//对这个计数器对象  ++
				counter.increment(1L);
			}else if(Integer.parseInt(datas[2])<0 || Integer.parseInt(datas[2])>100){
				counter.increment(1L);
			}
		}
	}
	
	public static void main(String[] args) throws IllegalArgumentException, IOException, ClassNotFoundException, InterruptedException {
		System.setProperty("HADOOP_USER_NAME", "hadoop");
		Configuration conf=new Configuration();
		conf.set("fs.defaultFS", "hdfs://hadoop01:9000");
		Job job=Job.getInstance(conf);
		job.setJarByClass(Counter_illegal_data.class);
		
		
		job.setMapperClass(MyMapper.class);
		job.setNumReduceTasks(0);
		
		job.setOutputKeyClass(NullWritable.class);
		job.setOutputValueClass(NullWritable.class);


		FileInputFormat.addInputPath(job, new Path("hdfs://hadoop01:9000/stuin"));
		//需要设置          存成功标志文件
		FileOutputFormat.setOutputPath(job, new Path("hdfs://hadoop01:9000/counter_out01"));
		//提交代码
		job.waitForCompletion(true);
	}
	

}
