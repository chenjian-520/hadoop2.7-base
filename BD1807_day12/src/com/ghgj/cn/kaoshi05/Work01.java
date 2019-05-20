package com.ghgj.cn.kaoshi05;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;



public class Work01 {
	
	static class MyMapper extends Mapper<LongWritable, Text, Text, Text>{
		
		String filename="";
		@Override
		protected void setup(Mapper<LongWritable, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			//获取切片信息 InputSplit 切片信息
			FileSplit inputSplit = (FileSplit) context.getInputSplit();
			filename = inputSplit.getPath().toString();
		}
		
		Text mk = new Text();
		Text mv = new Text();
		@Override
		protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			
			if(filename.endsWith("mapreduce-4-1.txt")){
				String[] datas = value.toString().split("\t");
				for(int i=0 ; i<datas.length;i++){
					mk.set(datas[i]);
					String str="mapreduce-4-1.txt"+","+1;
					mv.set(str);
					context.write(mk, mv);
				}
			}else{
				String[] datas = value.toString().split("\t");
				for(int i=0 ; i<datas.length;i++){
					mk.set(datas[i]);
					String str="mapreduce-4-2.txt"+","+1;
					mv.set(str);
					context.write(mk, mv);
				}
			}
			
		}
	}
	
	static class MyReducer extends Reducer<Text, Text, Text, Text>{
		Text mk = new Text();
		Text mv = new Text();
		static int a=0;
		static int b=0;
		@Override
		protected void reduce(Text key, Iterable<Text> values,
				Reducer<Text, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			String str="";
			
			Map<Integer,Integer> map1 = new HashMap<>();
			Map<Integer,Integer> map2 = new HashMap<>();
			for(Text v:values){
				String[] data = v.toString().split(",");
				if(data[0].equals("mapreduce-4-1.txt")){
					a++;
				}else{
					b++;
				}
				
			}
			String str1="mapreduce-4-1.txt"+","+a;
			String str2="mapreduce-4-2.txt"+","+b;
			
			mv.set(str1+"\t"+str2);
			context.write(key, mv);
	
		}
	}
	
	public static void main(String[] args) {
		System.setProperty("HADOOP_USER_NAME", "chen");
		Configuration conf = new Configuration();
		try {
			Job job = Job.getInstance(conf);
			
			job.setJarByClass(Work01.class);
			job.setMapperClass(MyMapper.class);
			job.setReducerClass(MyReducer.class);
			
			
			job.setMapOutputKeyClass(Text.class);
			job.setMapOutputValueClass(Text.class);
			job.setOutputKeyClass(Text.class);
			job.setOutputValueClass(Text.class);
			
			
			Path inpath = new Path("hdfs://192.168.66.131:9000/kaoshi/mapreduce02");
			FileInputFormat.addInputPath(job, inpath);
			Path outpath = new Path("hdfs://192.168.66.131:9000/kaoshi/mapreduce04_out");
			FileOutputFormat.setOutputPath(job, outpath);
			
			job.waitForCompletion(true);
		} catch (IOException | ClassNotFoundException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
