package com.ghgj01.student02;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;



public class Driver {
	public static void main(String[] args) {
	//��mapper  reducer �����һ����װ ��װΪһ������ ---job
		System.setProperty("HADOOP_USER_NAME","chen");
	//	System.setProperty("hadoop.home.dir","192.168.66.131");
			//���������ļ�
			Configuration conf = new Configuration();
			//����һ��job ����һ��job����
			try {
				Job job = Job.getInstance(conf);
				
				job.setJarByClass(Driver.class);
				
				job.setMapperClass(StuMapper.class);
				
				job.setReducerClass(StuReducer.class);
				
				job.setMapOutputKeyClass(Text.class);
				job.setMapOutputValueClass(Text.class);
				
				job.setOutputKeyClass(Text.class);
				job.setOutputValueClass(Text.class);
				
				Path inpath = new Path("hdfs://192.168.66.131:9000/wcin01");
				FileInputFormat.addInputPath(job, inpath);
				
				Path outpath = new Path("hdfs://192.168.66.131:9000/stu.01");
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
