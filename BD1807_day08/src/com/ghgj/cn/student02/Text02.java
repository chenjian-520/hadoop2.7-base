package com.ghgj.cn.student02;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class Text02 {
	
	static class MyMapper extends Mapper<LongWritable, Text, Student02, NullWritable>{
		Text mk = new Text();
		Text mv = new Text();
		@Override
		protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Student02, NullWritable>.Context context)
				throws IOException, InterruptedException {
			String[] datas = value.toString().split("\t");
			Student02 stu = new Student02(datas[0],Integer.parseInt(datas[1]),Double.valueOf(datas[2]));
			context.write(stu, NullWritable.get());
		}
	}
	
	static class MyReducer extends Reducer<Student02, NullWritable, Student02, NullWritable>{
		Text mk = new Text();
		Text mv = new Text();
		@Override
		protected void reduce(Student02 key, Iterable<NullWritable> values,
				Reducer<Student02, NullWritable, Student02, NullWritable>.Context context)
				throws IOException, InterruptedException {
		
				context.write(key, NullWritable.get());
		}
	}
	
	public static void main(String[] args) {
		
		System.setProperty("HADOOP_USER_NAME", "chen");
		
		Configuration conf = new Configuration();
		try {
			Job job = Job.getInstance(conf);
			
			job.setJarByClass(Text02.class);
			
			job.setMapperClass(MyMapper.class);
			
			job.setReducerClass(MyReducer.class);
			
			job.setMapOutputKeyClass(Student02.class);
			job.setMapOutputValueClass(NullWritable.class);
			
			job.setOutputKeyClass(Student02.class);
			job.setOutputValueClass(NullWritable.class);
			
			Path inpath =new Path("hdfs://192.168.66.131:9000/sort_out1");
			FileInputFormat.addInputPath(job, inpath);
			
			Path outpath = new Path("hdfs://192.168.66.131:9000/sort_out2");
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
