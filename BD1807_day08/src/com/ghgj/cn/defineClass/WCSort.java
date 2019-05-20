package com.ghgj.cn.defineClass;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * ��wc�Ľ�����������
 * @author Administrator
 *
 */
public class WCSort {
	//key----����   value---����
	static class MyMapper extends Mapper<LongWritable, Text, IntWritable, Text>{
		IntWritable mk=new IntWritable();
		Text mv=new Text();
		@Override
		protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, IntWritable, Text>.Context context)
				throws IOException, InterruptedException {
			//���ʣ�����
			String[] word_count = value.toString().split("\t");
			//��װ  ����
			/*
			 * hello,5    world,5   a,5
			 */
			mk.set(Integer.parseInt(word_count[1]));
			mv.set(word_count[0]);
			//5,hello   5,world   5,a
			context.write(mk, mv);
		}
	}
	//key--text  value---int
	static class MyReducer extends Reducer<IntWritable, Text, Text, IntWritable>{
		
		/*
		 * һ�����һ��  ��ͬ��keyΪһ��
		 * ��ͬ�Ĵ����ֵ�һ����
		 * @see org.apache.hadoop.mapreduce.Reducer#reduce(KEYIN, java.lang.Iterable, org.apache.hadoop.mapreduce.Reducer.Context)
		 * 
		 * key=5
		 * values=<hello,world,a>
		 */
		@Override
		protected void reduce(IntWritable key, Iterable<Text> values,
				Reducer<IntWritable, Text, Text, IntWritable>.Context context) throws IOException, InterruptedException {
			//ѭ������values   �Ե����
			for(Text v:values){
				context.write(v, key);
			}
		}
		
	}
	
	public static void main(String[] args) {
		System.setProperty("HADOOP_USER_NAME", "hadoop");
		//��mapper  reducer�����һ����װ  ��װΪһ������----job����ҵ��
		//���������ļ�
		Configuration conf=new Configuration();
		//����һ��Job  ����һ��job����
		try {
			Job job=Job.getInstance(conf);
			//�������job
			//��������job�����������
			job.setJarByClass(WCSort.class);
			
			//����job��mappper����
			job.setMapperClass(MyMapper.class);
			
			//����job��reducer����
			job.setReducerClass(MyReducer.class);
			
			
			//����map���key   value������
			//ָ���˷���  ����Ϊʲô��Ҫ����һ��   ���͵����÷�Χ  �����ʱ����Ч   ���е�ʱ���ͻ��Զ�����
			job.setMapOutputKeyClass(IntWritable.class);
			job.setMapOutputValueClass(Text.class);
			
			
			//����reduce�������k   v����  ���·������õ���mr���������
			job.setOutputKeyClass(Text.class);
			job.setOutputValueClass(IntWritable.class);
			
			
			//ָ����Ҫͳ�Ƶ��ļ�������·��  FileInputFormat  �ļ�������
			Path inpath=new Path("hdfs://hadoop01:9000/wc_out01");
			FileInputFormat.addInputPath(job, inpath);
			
			
			//ָ�����Ŀ¼  ���·�����ܴ��ڵ�  ����ᱨ��  Ĭ������Ǹ���ʽ�����  ������Ŀ¼����  �п������ԭʼ���ݵĶ�ʧ
			Path outpath=new Path("hdfs://hadoop01:9000/wc_sort_01");
			FileOutputFormat.setOutputPath(job, outpath);
			
			//�ύjob  ִ����һ���ʱ�� job�Ż��ύ  ��������һϵ�еĹ���  ��������job
			//job.submit();
			job.waitForCompletion(true);
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}

}
