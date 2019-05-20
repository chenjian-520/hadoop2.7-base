package com.ghgj.cn.HbaseMR;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Mutation;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.mapreduce.TableReducer;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;



public class HbaseMR {

	
	private static final String ZK_CONNECT_KEY = "hbase.zookeeper.quorum";
	private static final String ZK_CONNECT_VALUE = "hadoop01:2181,hadoop02:2181,hadoop03:2181";
	private static final String TABLE = "mingxing1";

	public static void main(String[] args) throws Exception {

		System.setProperty("HADOOP_USER_NAME", "chen");
		Configuration conf = HBaseConfiguration.create();
		conf.addResource("core-site.xml");
		conf.addResource("hdfs-site.xml");
		conf.set(ZK_CONNECT_KEY, ZK_CONNECT_VALUE);
		Job job = Job.getInstance(conf);
		
		
		job.setJarByClass(HbaseMR.class);
		
		
		job.setMapperClass(MyMapper.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(NullWritable.class);
		
	
		
		
		TableMapReduceUtil.initTableReducerJob(TABLE, 
				MyReducer.class, 
				job, 
				null, 
				null, 
				null, 
				null, 
				false);
//		TableMapReduceUtil.initTableReducerJob
		
		
		FileInputFormat.setInputPaths(job, new Path("/mingxing.txt"));
		
		
		boolean isDone = job.waitForCompletion(true);
		System.out.println(isDone);
	}

	
}


 class MyMapper extends Mapper<LongWritable, Text, Text, NullWritable>{
	@Override
	protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, NullWritable>.Context context)
			throws IOException, InterruptedException {
		context.write(value,  NullWritable.get());
	}
}

 class MyReducer extends TableReducer<Text, NullWritable, NullWritable>{
	
	String[] columns = new String[]{"sex","age","phone","email","qq"};
	@Override
	protected void reduce(Text key, Iterable<NullWritable> nvls,
			Reducer<Text, NullWritable, NullWritable, Mutation>.Context context)
			throws IOException, InterruptedException {
		String[] fields = key.toString().trim().split(",");
		
		Put put = new Put(fields[0].getBytes());
		
		// 后面的四个字段，分别构造四个key-value
		// 插入四个key-value， 就是四个put
		for(int i=1; i<fields.length-2; i++){
			String field = fields[i];
			String column = columns[i-1];
			put.addColumn("basicinfo".getBytes(), column.getBytes(), field.getBytes());
			context.write(NullWritable.get(), put);
		}
		for(int i=4; i<fields.length; i++){
			String field = fields[i];
			String column = columns[i-1];
			put.addColumn("extrainfo".getBytes(), column.getBytes(), field.getBytes());
			context.write(NullWritable.get(), put);
		}
	}
}
