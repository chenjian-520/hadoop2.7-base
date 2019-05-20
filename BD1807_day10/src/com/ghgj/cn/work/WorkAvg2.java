package com.ghgj.cn.work;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;



public class WorkAvg2 {
	
	static class MyMapper extends Mapper<LongWritable, Text, Text, Text>{
		
		Text mk = new Text();
		Text mv = new Text();
		@Override
		protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			String[] datas = value.toString().split("\t");
			
			mk.set(datas[0].trim());
			StringBuffer sb = new StringBuffer();
			for(int i=1;i<datas.length;i++){
				sb.append(datas[i]).append(",");
			}
			String substring = sb.substring(0,sb.length()-1);
			mv.set(substring);
			context.write(mk, mv);
		}
	}
	
	static class MyReducer extends Reducer<Text, Text, Text, Text>{
		Text mk = new Text();
		Text mv = new Text();
		@Override
		protected void reduce(Text key, Iterable<Text> values,
				Reducer<Text, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			int count=0;
			int sum1=0;
			int sum2=0;
			int sum3=0;
			double avg1=0;
			double avg2=0;
			double avg3=0;
	
			for(Text v:values){
		
				String[] datas = v.toString().split(",");
				sum1+=Integer.parseInt(datas[2].trim());
				sum2+=Integer.parseInt(datas[3].trim());
				sum3+=Integer.parseInt(datas[4].trim());
				count++;
			
			}
			avg1=sum1/count;
			avg2=sum2/count;
			avg3=sum3/count;
			String aa = String.valueOf(avg1);
			String bb = String.valueOf(avg2);
			String cc = String.valueOf(avg3);
		
			mv.set(aa+"\t"+bb+"\t"+cc);
			mk.set(key);
			context.write(mk, mv);
		}
	}
	
	public static void main(String[] args) {
		System.setProperty("HADOOP_USER_NAME", "chen");
		Configuration conf = new Configuration();
		try {
			Job job = Job.getInstance(conf);
			
			job.setJarByClass(WorkAvg2.class);
			job.setMapperClass(MyMapper.class);
			job.setReducerClass(MyReducer.class);
			
			
			job.setMapOutputKeyClass(Text.class);
			job.setMapOutputValueClass(Text.class);
			job.setOutputKeyClass(Text.class);
			job.setOutputValueClass(Text.class);
			
			job.setPartitionerClass(MyPartitioner.class);
			job.setNumReduceTasks(5);
			
			Path inpath = new Path("hdfs://192.168.66.131:9000/score_in");
			FileInputFormat.addInputPath(job, inpath);
			Path outpath = new Path("hdfs://192.168.66.131:9000/score_out02");
			FileOutputFormat.setOutputPath(job, outpath);
			
			job.waitForCompletion(true);
		} catch (IOException | ClassNotFoundException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
