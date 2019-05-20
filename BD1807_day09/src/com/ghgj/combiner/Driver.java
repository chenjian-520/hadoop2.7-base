package com.ghgj.combiner;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


/**
 * ������
 * @author ASUS
 *
 */
public class Driver {
	public static void main(String[] args) {
		//��mapper  reducer �����һ����װ ��װΪһ������ ---job
		System.setProperty("HADOOP_USER_NAME", "chen");
		//���������ļ�
		Configuration conf = new Configuration();
		//����һ��job ����һ��job����
		try {
			Job job = Job.getInstance(conf);
			//�������job
			//��ֵ����job�����������
			job.setJarByClass(Driver.class);
			
			//����job��mapper����
			job.setMapperClass(WordCountMapper.class);
			
			//������Ƭ��С
			//FileInputFormat.setMaxInputSplitSize(job, size);
			//FileInputFormat.setMinInputSplitSize(job, size);
			
			//����job��reducer����
			job.setReducerClass(WordCountrReducer.class);
			
			//����map���key  value������
			//ָ���˷��� ����Ϊʲô��Ҫ����һ��  ���͵���ҵ��Χ  �����ʱ����Ч ���е�ʱ���ͻ��Զ�����
			job.setMapOutputKeyClass(Text.class);
			job.setMapOutputValueClass(IntWritable.class);
			
			//ָ�����combiner���
			job.setCombinerClass(MyCombiner.class);
			
			//����reduce�����k   v ���� һ�·������õ���Ĭ�ϵ��������
			job.setOutputKeyClass(Text.class);
			job.setOutputValueClass(IntWritable.class);
			
			//ָ����Ҫͳ�Ƶ��ļ�����·�� FileInputFormat �ļ�������
			Path inpath = new Path("hdfs://192.168.66.131:9000/wcin");
			FileInputFormat.addInputPath(job, inpath);
			
			//ָ�����Ŀ¼  ���·�����ܴ���  ����ᱨ��  Ĭ������Ǹ���ʽ����� ���Ŀ¼���� �п������ԭʼ���ݵĶ�ʧ
			Path outpath = new Path("hdfs://192.168.66.131:9000/wcout_combiner");
			FileOutputFormat.setOutputPath(job, outpath);
			
			//�ύjob ִ����һ���ʱ��
			//job.submit(); ���ܴ�ӡ��־
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
