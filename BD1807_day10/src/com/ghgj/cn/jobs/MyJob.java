package com.ghgj.cn.jobs;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.jobcontrol.JobControl;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.jobcontrol.ControlledJob;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import com.ghgj.cn.jobs.CommonFriend01.MyMapper;
import com.ghgj.cn.jobs.CommonFriend01.MyReducer;
import com.ghgj.cn.jobs.CommonFriend02.MyMapper01;
import com.ghgj.cn.jobs.CommonFriend02.MyReducer01;

public class MyJob {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.setProperty("HADOOP_USER_NAME", "chen");
		Configuration conf = new Configuration();
		try {
			Job job = Job.getInstance(conf);
			
			job.setJarByClass(CommonFriend01.class);
			job.setMapperClass(MyMapper.class);
			job.setReducerClass(MyReducer.class);
			
			job.setOutputKeyClass(Text.class);
			job.setOutputValueClass(Text.class);
			
			Path inpath = new Path("hdfs://192.168.66.131:9000/work_hufen");
			FileInputFormat.addInputPath(job, inpath);
			Path outpath = new Path("hdfs://192.168.66.131:9000/work_common_out02");
			FileOutputFormat.setOutputPath(job, outpath);
			
			
			
			
			Job job1 = Job.getInstance(conf);
				
			job1.setJarByClass(CommonFriend02.class);
			job1.setMapperClass(MyMapper01.class);
			job1.setReducerClass(MyReducer01.class);
				
			job1.setOutputKeyClass(Text.class);
			job1.setOutputValueClass(Text.class);
				
			Path inpath1 = new Path("hdfs://192.168.66.131:9000/work_common_out02");
			FileInputFormat.addInputPath(job1, inpath1);
			Path outpath1 = new Path("hdfs://192.168.66.131:9000/work_common_out03");
			FileOutputFormat.setOutputPath(job1, outpath1);
				
			
			//创建JobControl对象
			//参数：组名   会将添加到这个jc对象的所有job放在一个组中  提交一个组的job
			JobControl jc=new JobControl("bd1807");
			//添加job之间的依赖  ControlledJob  将job转换为ControlledJob
			//job1  参数：jobConf  job1.getConfiguration()  xml
			ControlledJob cjob1=new ControlledJob(job.getConfiguration());
			//job2
			ControlledJob cjob2=new ControlledJob(job1.getConfiguration());
			//添加依赖关系
			cjob2.addDependingJob(cjob1);
			
			jc.addJob(cjob1);
			jc.addJob(cjob2);
			
			
			//提交  jc   class JobControl implements Runnable
			Thread t=new Thread(jc);
			//启动
			t.start();
			
			//jc  判断jc组中所有的job是否执行完成
			//jc.allFinished()
			while(!jc.allFinished()){
				Thread.sleep(500);
			}
			jc.stop();
			
			
			
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}






