package com.ghgj.cn.student;

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

public class Text01 {
	
	static class MyMapper extends Mapper<LongWritable, Text, Text, Text>{
		Text mk = new Text();
		Text mv = new Text();
		String count=new String();
		@Override
		protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			String[] datas = value.toString().split(",");
			mk.set(datas[0]);
			for(int i=2;i<datas.length;i++){
				count += datas[i]+",";
			}
			
			mv.set(count);
			context.write(mk, mv);
		}
	}
	
	static class MyReducer extends Reducer<Text, Text, Text, Text>{
		Text mk = new Text();
		Text mv = new Text();
		@Override
		protected void reduce(Text key, Iterable<Text> values, Reducer<Text, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			int count =0;
			int sum = 0;
			int su = 0;
			mk.set(key);
			for(Text value:values){
				su++;
				String[] datas = value.toString().split(",");
				
				for( int i=0;i<datas.length;i++){
					count++;
					sum +=Integer.parseInt(datas[i].trim());
				}
			}
			double avg =(double) sum/(double)count;
			String mv1 = su +"\t"+avg;
			mv.set(mv1);
			context.write(mk, mv);
		}
	}
	
	public static void main(String[] args) {
		
		System.setProperty("HADOOP_USER_NAME", "chen");
		
		Configuration conf = new Configuration();
		try {
			Job job = Job.getInstance(conf);
			
			job.setJarByClass(Text01.class);
			
			job.setMapperClass(MyMapper.class);
			
			job.setReducerClass(MyReducer.class);
			
			job.setMapOutputKeyClass(Text.class);
			job.setMapOutputValueClass(Text.class);
			
			job.setOutputKeyClass(Text.class);
			job.setOutputValueClass(Text.class);
			
			Path inpath =new Path("hdfs://192.168.66.131:9000/sort");
			FileInputFormat.addInputPath(job, inpath);
			
			Path outpath = new Path("hdfs://192.168.66.131:9000/sort_out1");
			FileOutputFormat.setOutputPath(job, outpath);
			
			job.waitForCompletion(true);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
