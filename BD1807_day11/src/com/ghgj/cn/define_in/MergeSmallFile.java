package com.ghgj.cn.define_in;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;



public class MergeSmallFile {
	/**
	 * 小文件合并  map将每个小文件内容发送给reduce 在reduce端进行合并
	 * @author ASUS
	 *
	 */
	static class MyMapper extends Mapper<NullWritable, Text, NullWritable, Text>{
		/**
		 * 一个文件切片调用一次
		 */
		@Override
		protected void map(NullWritable key, Text value, Mapper<NullWritable, Text, NullWritable, Text>.Context context)
				throws IOException, InterruptedException {
			// TODO Auto-generated method stub
			context.write(NullWritable.get(), value);
		}
	}
	
	static class MyReducer extends Reducer<NullWritable, Text, NullWritable, Text>{
		/**
		 * 一组调用一次
		 */
		@Override
		protected void reduce(NullWritable key, Iterable<Text> values,
				Reducer<NullWritable, Text, NullWritable, Text>.Context context) throws IOException, InterruptedException {
			// TODO Auto-generated method stub
			for(Text v : values){
				context.write(NullWritable.get(), v);
			}
		}
	}
	
	public static void main(String[] args) {
		System.setProperty("HADOOP_USER_NAME", "chen");
		Configuration conf = new Configuration();
		conf.set("fs.defaultFS", "hdfs://192.168.66.131:9000");
		try {
			Job job = Job.getInstance(conf);
			
			job.setJarByClass(MergeSmallFile.class);
			job.setMapperClass(MyMapper.class);
			job.setReducerClass(MyReducer.class);
			
			
			job.setMapOutputKeyClass(NullWritable.class);
			job.setMapOutputValueClass(Text.class);
			job.setOutputKeyClass(NullWritable.class);
			job.setOutputValueClass(Text.class);
			
			//设定job的输入为自定义
			job.setInputFormatClass(MyFileInputFormat.class);
			
			
			Path inpath = new Path("/in");
			MyFileInputFormat.addInputPath(job, inpath);
			Path outpath = new Path("/in_merge01");
			FileOutputFormat.setOutputPath(job, outpath);
			
			job.waitForCompletion(true);
		} catch (IOException | ClassNotFoundException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
