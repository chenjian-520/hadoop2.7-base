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
 * 驱动类
 * @author ASUS
 *
 */
public class Driver {
	public static void main(String[] args) {
		//将mapper  reducer 类进行一个封装 封装为一个任务 ---job
		System.setProperty("HADOOP_USER_NAME", "chen");
		//加载配置文件
		Configuration conf = new Configuration();
		//启动一个job 创建一个job对象
		try {
			Job job = Job.getInstance(conf);
			//设置这个job
			//数值整个job的主函数入口
			job.setJarByClass(Driver.class);
			
			//设置job的mapper的类
			job.setMapperClass(WordCountMapper.class);
			
			//设置切片大小
			//FileInputFormat.setMaxInputSplitSize(job, size);
			//FileInputFormat.setMinInputSplitSize(job, size);
			
			//设置job的reducer的类
			job.setReducerClass(WordCountrReducer.class);
			
			//设置map输出key  value的类型
			//指定了泛型 这里为什么还要设置一次  泛型的作业范围  编译的时候生效 运行的时候泛型会自动擦除
			job.setMapOutputKeyClass(Text.class);
			job.setMapOutputValueClass(IntWritable.class);
			
			//指定添加combiner组件
			job.setCombinerClass(MyCombiner.class);
			
			//设置reduce的输出k   v 类型 一下方法设置的是默认的最终输出
			job.setOutputKeyClass(Text.class);
			job.setOutputValueClass(IntWritable.class);
			
			//指定需要统计的文件输入路径 FileInputFormat 文件输入类
			Path inpath = new Path("hdfs://192.168.66.131:9000/wcin");
			FileInputFormat.addInputPath(job, inpath);
			
			//指定输出目录  输出路径不能存在  否则会报错  默认输出是覆盖式的输出 如果目录存在 有可能造成原始数据的丢失
			Path outpath = new Path("hdfs://192.168.66.131:9000/wcout_combiner");
			FileOutputFormat.setOutputPath(job, outpath);
			
			//提交job 执行这一句的时候
			//job.submit(); 不能打印日志
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
