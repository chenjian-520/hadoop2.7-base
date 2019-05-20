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
 * 对wc的结果进行排序的
 * @author Administrator
 *
 */
public class WCSort {
	//key----次数   value---单词
	static class MyMapper extends Mapper<LongWritable, Text, IntWritable, Text>{
		IntWritable mk=new IntWritable();
		Text mv=new Text();
		@Override
		protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, IntWritable, Text>.Context context)
				throws IOException, InterruptedException {
			//单词，次数
			String[] word_count = value.toString().split("\t");
			//封装  发送
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
		 * 一组调用一次  相同的key为一组
		 * 相同的次数分到一组中
		 * @see org.apache.hadoop.mapreduce.Reducer#reduce(KEYIN, java.lang.Iterable, org.apache.hadoop.mapreduce.Reducer.Context)
		 * 
		 * key=5
		 * values=<hello,world,a>
		 */
		@Override
		protected void reduce(IntWritable key, Iterable<Text> values,
				Reducer<IntWritable, Text, Text, IntWritable>.Context context) throws IOException, InterruptedException {
			//循环遍历values   对调输出
			for(Text v:values){
				context.write(v, key);
			}
		}
		
	}
	
	public static void main(String[] args) {
		System.setProperty("HADOOP_USER_NAME", "hadoop");
		//将mapper  reducer类进行一个封装  封装为一个任务----job（作业）
		//加载配置文件
		Configuration conf=new Configuration();
		//启动一个Job  创建一个job对象
		try {
			Job job=Job.getInstance(conf);
			//设置这个job
			//设置整个job的主函数入口
			job.setJarByClass(WCSort.class);
			
			//设置job的mappper的类
			job.setMapperClass(MyMapper.class);
			
			//设置job的reducer的类
			job.setReducerClass(MyReducer.class);
			
			
			//设置map输出key   value的类型
			//指定了泛型  这里为什么还要设置一次   泛型的作用范围  编译的时候生效   运行的时候泛型会自动擦除
			job.setMapOutputKeyClass(IntWritable.class);
			job.setMapOutputValueClass(Text.class);
			
			
			//设置reduce的输出的k   v类型  以下方法设置的是mr的最终输出
			job.setOutputKeyClass(Text.class);
			job.setOutputValueClass(IntWritable.class);
			
			
			//指定需要统计的文件的输入路径  FileInputFormat  文件输入类
			Path inpath=new Path("hdfs://hadoop01:9000/wc_out01");
			FileInputFormat.addInputPath(job, inpath);
			
			
			//指定输出目录  输出路径不能存在的  否则会报错  默认输出是覆盖式的输出  如果输出目录存在  有可能造成原始数据的丢失
			Path outpath=new Path("hdfs://hadoop01:9000/wc_sort_01");
			FileOutputFormat.setOutputPath(job, outpath);
			
			//提交job  执行这一句的时候 job才会提交  上面做的一系列的工作  都是设置job
			//job.submit();
			job.waitForCompletion(true);
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}

}
