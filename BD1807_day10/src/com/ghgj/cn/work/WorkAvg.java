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



public class WorkAvg {
	
	static class MyMapper extends Mapper<LongWritable, Text, DoubleWritable, Text>{
		
		DoubleWritable mk = new DoubleWritable();
		Text mv = new Text();
		@Override
		protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, DoubleWritable, Text>.Context context)
				throws IOException, InterruptedException {
			double sum = 0;
			int count =3;
			double avg = 0;
			String[] datas = value.toString().split("\t");
			for(int i=3;i<datas.length;i++){
				sum+=Integer.parseInt(datas[i].trim());
			}
			avg=sum/count;
			mk.set(-avg);
			mv.set(datas[0]+"\t"+datas[1]+"\t"+datas[2]);
			context.write(mk, mv);
		}
	}
	
	static class MyReducer extends Reducer<DoubleWritable, Text, Text, DoubleWritable>{
		Text mk = new Text();
		DoubleWritable mv = new DoubleWritable();
		@Override
		protected void reduce(DoubleWritable key, Iterable<Text> values,
				Reducer<DoubleWritable, Text, Text, DoubleWritable>.Context context)
				throws IOException, InterruptedException {
			
			mv.set(-key.get());
			for(Text v:values){
				
				context.write(v, mv);
			}
			
		}
	}
	
	public static void main(String[] args) {
		System.setProperty("HADOOP_USER_NAME", "chen");
		Configuration conf = new Configuration();
		try {
			Job job = Job.getInstance(conf);
			
			job.setJarByClass(WorkAvg.class);
			job.setMapperClass(MyMapper.class);
			job.setReducerClass(MyReducer.class);
			
			
			job.setMapOutputKeyClass(DoubleWritable.class);
			job.setMapOutputValueClass(Text.class);
			job.setOutputKeyClass(Text.class);
			job.setOutputValueClass(DoubleWritable.class);
			
			Path inpath = new Path("hdfs://192.168.66.131:9000/score_in");
			FileInputFormat.addInputPath(job, inpath);
			Path outpath = new Path("hdfs://192.168.66.131:9000/score_out01");
			FileOutputFormat.setOutputPath(job, outpath);
			
			job.waitForCompletion(true);
		} catch (IOException | ClassNotFoundException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
