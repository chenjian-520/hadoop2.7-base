package com.ghgj01.student01;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


public class Driver {
	public static void main(String[] args) {
	//��mapper  reducer �����һ����װ ��װΪһ������ ---job
			//���������ļ�
			Configuration conf = new Configuration();
			//����һ��job ����һ��job����
			try {
				Job job = Job.getInstance(conf);
				//�������job
				//��ֵ����job�����������
				job.setJarByClass(Driver.class);
				
				//����job��mapper����
				job.setMapperClass(StuMapper.class);
				
				//����job��reducer����
				job.setReducerClass(StuReducer.class);
				
				//����map���key  value������
				//ָ���˷��� ����Ϊʲô��Ҫ����һ��  ���͵���ҵ��Χ  �����ʱ����Ч ���е�ʱ���ͻ��Զ�����
				job.setMapOutputKeyClass(Text.class);
				job.setMapOutputValueClass(Text.class);
				
				//����reduce�����k   v ���� һ�·������õ���Ĭ�ϵ��������
				job.setOutputKeyClass(Text.class);
				job.setOutputValueClass(DoubleWritable.class);
				
				//ָ����Ҫͳ�Ƶ��ļ�����·�� FileInputFormat �ļ�������
				Path inpath = new Path(args[0]);
				FileInputFormat.addInputPath(job, inpath);
				
				//ָ�����Ŀ¼  ���·�����ܴ���  ����ᱨ��  Ĭ������Ǹ���ʽ����� ���Ŀ¼���� �п������ԭʼ���ݵĶ�ʧ
				Path outpath = new Path(args[1]);
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
