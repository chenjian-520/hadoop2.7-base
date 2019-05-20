package com.ghgj.cn.define_out;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;



public class Score_DiffDic {
	static class MyMapper extends Mapper<LongWritable, Text, Text, IntWritable>{
		Text mk=new Text();
		IntWritable mv=new IntWritable();
		@Override
		protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, IntWritable>.Context context)
				throws IOException, InterruptedException {
			String[] datas = value.toString().split(",");
			if(datas.length==3){
				mk.set(datas[1]);
				mv.set(Integer.parseInt(datas[2]));
				context.write(mk, mv);
			}
		}
	}
	
	static class MyReducer extends Reducer<Text, IntWritable, Text, DoubleWritable>{
		DoubleWritable rv=new DoubleWritable();
		@Override
		protected void reduce(Text key, Iterable<IntWritable> values,
				Reducer<Text, IntWritable, Text, DoubleWritable>.Context context)
				throws IOException, InterruptedException {
			int sum=0;
			int count=0;
			for(IntWritable  v: values){
				count++;
				sum+=v.get();
			}
			double avg=1.0*sum/count;
			rv.set(avg);
			context.write(key, rv);
		}
	}
	public static void main(String[] args) throws IllegalArgumentException, IOException, ClassNotFoundException, InterruptedException {
		System.setProperty("HADOOP_USER_NAME", "hadoop");
		Configuration conf=new Configuration();
		conf.set("fs.defaultFS", "hdfs://hadoop01:9000");
		Job job=Job.getInstance(conf);
		job.setJarByClass(Score_DiffDic.class);
		
		
		job.setMapperClass(MyMapper.class);
		job.setReducerClass(MyReducer.class);
		
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(IntWritable.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(DoubleWritable.class);

		//����job������Ϊ�Զ�������
		job.setOutputFormatClass(MyFileOutputFormat.class);

		FileInputFormat.addInputPath(job, new Path("hdfs://hadoop01:9000/stuin"));
		//����������ļ�  ������гɹ���־�ļ���
		MyFileOutputFormat.setOutputPath(job, new Path("hdfs://hadoop01:9000/diff_out_01"));
		//�ύ����
		job.waitForCompletion(true);
	}

}
