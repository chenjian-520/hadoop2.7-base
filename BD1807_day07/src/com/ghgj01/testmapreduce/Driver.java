package com.ghgj01.testmapreduce;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class Driver {
	public static void main(String[] args) {
		//��mapper  reducer�����һ����װ  ��װΪһ������----job����ҵ��
		//���������ļ�
		Configuration conf=new Configuration();
		//����һ��Job  ����һ��job����
		try {
			Job job=Job.getInstance(conf);
			//�������job
			//��������job�����������
			job.setJarByClass(Driver.class);
			
			//����job��mappper����
			job.setMapperClass(MyMapper.class);
			
			//����job��reducer����
			job.setReducerClass(MyReducer.class);
			
			
			//����map���key   value������
			//ָ���˷���  ����Ϊʲô��Ҫ����һ��   ���͵����÷�Χ  �����ʱ����Ч   ���е�ʱ���ͻ��Զ�����
			job.setMapOutputKeyClass(Text.class);
			job.setMapOutputValueClass(IntWritable.class);
			
			//����reduce�������k   v����  ���·������õ���mr���������
			job.setOutputKeyClass(Text.class);
			job.setOutputValueClass(DoubleWritable.class);
			
			
			//ָ����Ҫͳ�Ƶ��ļ�������·��  FileInputFormat  �ļ�������
			Path inpath=new Path(args[0]);
			FileInputFormat.addInputPath(job, inpath);
			
			//ָ�����Ŀ¼  ���·�����ܴ��ڵ�  ����ᱨ��  Ĭ������Ǹ���ʽ�����  ������Ŀ¼����  �п������ԭʼ���ݵĶ�ʧ
			Path outpath=new Path(args[1]);
			FileOutputFormat.setOutputPath(job, outpath);
			
			//�ύjob  ִ����һ���ʱ�� job�Ż��ύ  ��������һϵ�еĹ���  ��������job
			//job.submit();
			job.waitForCompletion(true);
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}

}
