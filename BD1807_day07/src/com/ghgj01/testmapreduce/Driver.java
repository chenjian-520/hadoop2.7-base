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
		//将mapper  reducer类进行一个封装  封装为一个任务----job（作业）
		//加载配置文件
		Configuration conf=new Configuration();
		//启动一个Job  创建一个job对象
		try {
			Job job=Job.getInstance(conf);
			//设置这个job
			//设置整个job的主函数入口
			job.setJarByClass(Driver.class);
			
			//设置job的mappper的类
			job.setMapperClass(MyMapper.class);
			
			//设置job的reducer的类
			job.setReducerClass(MyReducer.class);
			
			
			//设置map输出key   value的类型
			//指定了泛型  这里为什么还要设置一次   泛型的作用范围  编译的时候生效   运行的时候泛型会自动擦除
			job.setMapOutputKeyClass(Text.class);
			job.setMapOutputValueClass(IntWritable.class);
			
			//设置reduce的输出的k   v类型  以下方法设置的是mr的最终输出
			job.setOutputKeyClass(Text.class);
			job.setOutputValueClass(DoubleWritable.class);
			
			
			//指定需要统计的文件的输入路径  FileInputFormat  文件输入类
			Path inpath=new Path(args[0]);
			FileInputFormat.addInputPath(job, inpath);
			
			//指定输出目录  输出路径不能存在的  否则会报错  默认输出是覆盖式的输出  如果输出目录存在  有可能造成原始数据的丢失
			Path outpath=new Path(args[1]);
			FileOutputFormat.setOutputPath(job, outpath);
			
			//提交job  执行这一句的时候 job才会提交  上面做的一系列的工作  都是设置job
			//job.submit();
			job.waitForCompletion(true);
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}

}
