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



public class CommonFriend02 {
	
	static class MyMapper01 extends Mapper<LongWritable, Text, Text, Text>{
		Text mk = new Text();
		Text mv = new Text();
		@Override
		protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			String[] friend_users = value.toString().split("\t");
			mv.set(friend_users[0]);
			String[] users = friend_users[1].split(",");
			for(int i=0;i<users.length-1;i++){
				for(int j=i+1;j<users.length;j++){
					String pingjie = pingjie(users[i],users[j]);
					mk.set(pingjie);
					context.write(mk, mv);
				}
			}
			
		}
		
		private String pingjie(String a , String b){
			if(a.compareTo(b)>0){
				return b+"-"+a;
			}else{
				return a+"-"+b;
			}
		}
		
	}
	
	static class MyReducer01 extends Reducer<Text, Text, Text, Text>{
		Text mv = new Text();
		//具有同一个好友的同一个用户
		@Override
		protected void reduce(Text key, Iterable<Text> values, Reducer<Text, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			 //好友对组合起来
            StringBuilder sb = new StringBuilder();
            for(Text text : values){
                sb.append(text.toString()).append(",");
            }
            String vv = sb.substring(0, sb.length()-1);
            mv.set(vv);
            context.write(key, mv);
		}
	}
	
	public static void main(String[] args) {
		
		System.setProperty("HADOOP_USER_NAME", "chen");
		Configuration conf = new Configuration();
		try {
			Job job = Job.getInstance(conf);
			
			job.setJarByClass(CommonFriend02.class);
			job.setMapperClass(MyMapper01.class);
			job.setReducerClass(MyReducer01.class);
			
			job.setOutputKeyClass(Text.class);
			job.setOutputValueClass(Text.class);
			
			Path inpath = new Path("hdfs://192.168.66.131:9000/work_hufen_out1");
			FileInputFormat.addInputPath(job, inpath);
			Path outpath = new Path("hdfs://192.168.66.131:9000/work_common_out01");
			FileOutputFormat.setOutputPath(job, outpath);
			
			job.waitForCompletion(true);
		} catch (IOException | ClassNotFoundException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
