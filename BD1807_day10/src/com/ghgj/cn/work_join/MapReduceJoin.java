package com.ghgj.cn.work_join;

import java.io.IOException;
import java.util.ArrayList;

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



public class MapReduceJoin {
	
	static class MyMapper extends Mapper<LongWritable, Text, Text, Text>{
		Text mk = new Text();
		Text mv = new Text();
		String filename="";
		@Override
		protected void setup(Mapper<LongWritable, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			//获取切片信息 InputSplit 切片信息
			FileSplit inputSplit = (FileSplit) context.getInputSplit();
			filename = inputSplit.getPath().toString();
		}
		@Override
		protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			String[] datas = value.toString().split("::");
			if(filename.endsWith("users.dat")){
				mk.set(datas[0]);
				mv.set("u"+datas[1]+"\t"+datas[2]+"\t"+datas[3]+"\t"+datas[4]);
				context.write(mk, mv);
			}else{
				mk.set(datas[0]);
				mv.set("r"+datas[1]+"\t"+datas[2]+"\t"+datas[3]+"\t"+datas[4]);
				context.write(mk, mv);
			}
		}
	}
	
	static class MyReducer extends Reducer<Text, Text, Text, Text>{
		
		Text mv = new Text();
		@Override
		protected void reduce(Text key, Iterable<Text> value, Reducer<Text, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			ArrayList<String> user =new ArrayList<>();
			ArrayList<String> rating =new ArrayList<>();
			for(Text v : value){
				String data = v.toString();
				if(data.startsWith("u")){
					user.add(data.substring(1));
				}else{
					rating.add(data.substring(1));
				}
			}
			for(String u:user){
				for(String r:rating){	
					mv.set(u+"\t"+r);
					context.write(key, mv);
				}
			}
			
		}
	}
	
	public static void main(String[] args) {
		Configuration conf = new Configuration();
		System.setProperty("HADOOP_USER_NAME", "NAME");
		try {
			Job job = Job.getInstance(conf);
			
			job.setJarByClass(MapReduceJoin.class);
			job.setMapperClass(MyMapper.class);
			job.setReducerClass(MyReducer.class);
			
			
			job.setMapOutputKeyClass(Text.class);
			job.setMapOutputValueClass(Text.class);
			job.setOutputKeyClass(Text.class);
			job.setOutputValueClass(Text.class);
			
			
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
