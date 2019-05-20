package com.ghgj.cn.jobs;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;



public class CommonFriend01 {
	
	static class MyMapper extends Mapper<LongWritable, Text, Text, Text>{
		Text mk = new Text();
		Text mv = new Text();
		@Override
		protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			String[] user_friends = value.toString().split(":");
			mv.set(user_friends[0]);
			//循环遍历每一个好友
			String[] friends = user_friends[1].split(",");
			for(String f:friends){
				mk.set(f);
				context.write(mk, mv);
			}
		}
	}
	
	static class MyReducer extends Reducer<Text, Text, Text, Text>{
		Text mv = new Text();
		//具有同一个好友的同一个用户
		
		@Override
		protected void reduce(Text key, Iterable<Text> values, Reducer<Text, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			StringBuffer sb =new StringBuffer();
			for(Text v:values){
				sb.append(v).append(",");
			}
			mv.set(sb.substring(0,sb.length()-1));
			context.write(key, mv);
		}
	}
	
	public static void main(String[] args) {
		
		System.setProperty("HADOOP_USER_NAME", "chen");
		Configuration conf = new Configuration();
		try {
			Job job = Job.getInstance(conf);
			
			job.setJarByClass(CommonFriend01.class);
			job.setMapperClass(MyMapper.class);
			
			job.setNumReduceTasks(0);
			
			job.setOutputKeyClass(Text.class);
			job.setOutputValueClass(Text.class);
			
			Path inpath = new Path("hdfs://192.168.66.131:9000/work_hufen");
			FileInputFormat.addInputPath(job, inpath);
			Path outpath = new Path("hdfs://192.168.66.131:9000/work_text_out1");
			FileOutputFormat.setOutputPath(job, outpath);
			
			job.waitForCompletion(true);
		} catch (IOException | ClassNotFoundException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
