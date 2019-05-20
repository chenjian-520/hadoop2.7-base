package com.ghgj.cn.defineClass;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


public class FlowCount {
	//mapper
	static class MyMapper extends Mapper<LongWritable, Text, Text, FlowBean>{
		Text mk = new Text();
		@Override
		protected void map(LongWritable key, Text value, Context context) 
					throws IOException ,InterruptedException {
			String[] datas = value.toString().split("\t");
			//获取需要的数据  进行封装发送
			mk.set(datas[1]);
			//封装fb
			int upFlow=Integer.parseInt(datas[datas.length-3].trim());
			int downFlow=Integer.parseInt(datas[datas.length-2].trim());
			FlowBean fb = new FlowBean(upFlow,downFlow);
			//发送
			context.write(mk, fb);
		};
	}
	
	//reducer
	//key---手机号  value---fb
	static class MyReducer extends Reducer<Text, FlowBean, Text, FlowBean>{
		@Override
		protected void reduce(Text key, Iterable<FlowBean> values,Context context)
				throws IOException, InterruptedException {
			//相同的手机号  一次性全部过来了   循环遍历  values就可以了
			int sum_upFlow=0;
			int sum_downFlow=0;
			for(FlowBean fb : values){
				sum_upFlow+=fb.getUpFlow();
				sum_downFlow+=fb.getDownFlow();
			}
			//封装发送
			FlowBean res_fb = new FlowBean(sum_upFlow,sum_downFlow);
			context.write(key, res_fb);
		}
	}
	
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
					job.setJarByClass(FlowCount.class);
					
					//设置job的mapper的类
					job.setMapperClass(MyMapper.class);
					
					//设置切片大小
					//FileInputFormat.setMaxInputSplitSize(job, size);
					//FileInputFormat.setMinInputSplitSize(job, size);
					
					//设置job的reducer的类
					job.setReducerClass(MyReducer.class);
					
					//设置map输出key  value的类型
					//指定了泛型 这里为什么还要设置一次  泛型的作业范围  编译的时候生效 运行的时候泛型会自动擦除
					job.setMapOutputKeyClass(Text.class);
					job.setMapOutputValueClass(FlowBean.class);
					
					//设置reduce的输出k   v 类型 一下方法设置的是默认的最终输出
					job.setOutputKeyClass(Text.class);
					job.setOutputValueClass(FlowBean.class);
					
					//指定需要统计的文件输入路径 FileInputFormat 文件输入类
					Path inpath = new Path("hdfs://192.168.66.131:9000/in_phone01");
					FileInputFormat.addInputPath(job, inpath);
					
					//指定输出目录  输出路径不能存在  否则会报错  默认输出是覆盖式的输出 如果目录存在 有可能造成原始数据的丢失
					Path outpath = new Path("hdfs://192.168.66.131:9000/phone01");
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

