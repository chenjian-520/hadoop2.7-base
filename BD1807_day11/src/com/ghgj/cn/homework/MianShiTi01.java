package com.ghgj.cn.homework;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


public class MianShiTi01 {
	static class MyMapper extends Mapper<LongWritable, Text, Text, Text>{
		Text mk=new Text();
		Text mv=new Text();
		@Override
		protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			String[] datas = value.toString().split("\t");
			//判断第二个字段是否为0
			if("0".equals(datas[1])){//父目录  找儿子
				//1        0        家电
				mk.set(datas[0]);//自己的id
				mv.set(datas[1]+"\t"+datas[2]);
				context.write(mk, mv);
			}else{//不是父目录  找爸爸
				//4        1        洗衣机
				mk.set(datas[1]);//发送父id
				mv.set(datas[0]+"\t"+datas[2]);
				context.write(mk, mv);
			}
		}
	}
	static class MyReducer extends Reducer<Text, Text, Text, Text>{
		Text rk=new Text();
		Text rv=new Text();
		/**
		 * 父目录
		 * key=1       value： 0        家电
		 * 子目录：
		 * 4        1        洗衣机
5        1        冰箱
		 * key=1   value:4   洗衣机           5   冰箱
		 */
		@Override
		protected void reduce(Text key, Iterable<Text> values, Context context)
				throws IOException, InterruptedException {
			String parent="";
			List<String> childDir=new ArrayList<String>();
			for(Text v:values){
				String tmp = v.toString();
				if(tmp.startsWith("0")){//父目录
					parent=tmp;
				}else{//子目录
					childDir.add(tmp);
				}
			}
			//拼接
			//没有获取父目录  parent=""  继续输出  等待自己的父目录拼接出来
			if("".equals(parent)&&childDir.size()!=0){//原样输出
				//key=14    value:15             特仑苏
				// 15        14        特仑苏
				for(int i=0;i<childDir.size();i++){
				String[] temp01 = childDir.get(i).split("\t");
				rk.set(temp01[0]);
				rv.set(key.toString()+"\t"+temp01[1]);
				context.write(rk, rv);
				}
			}else if(childDir.size()==0){////没有子目录
				//10			0		家电-洗衣机-美的
				//key=10   value:0		家电-洗衣机-美的
				rv.set(parent);
				context.write(key, rv);
			}else{
				/* 父目录
				 * key=1       value： 0        家电
				 * 子目录：
				 * 4        1        洗衣机
		5        1        冰箱
				 * key=1   value:4   洗衣机           5   冰箱
				 *
				 */
				//循环遍历子目录  和父目录拼接
				for(String c:childDir){
					//c=4   洗衣机     //p= 0        家电  //res=4   0   家电-洗衣机
					String[] temp02 = c.split("\t");
					String[] temp03 = parent.split("\t");
					rk.set(temp02[0]);
					rv.set(temp03[0]+"\t"+temp03[1]+"-"+temp02[1]);
					context.write(rk, rv);
				}
				
				 
			}
			
			
			
			
			
			
			
			//都有
		}
	}
	
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		int count=0;
		while(true){
			count++;
			System.setProperty("HADOOP_USER_NAME", "hadoop");
			Configuration conf=new Configuration();
			conf.set("fs.defaultFS", "hdfs://hadoop01:9000");
			Job job=Job.getInstance(conf);
			job.setJarByClass(MianShiTi01.class);
			
			
			job.setMapperClass(MyMapper.class);
			job.setReducerClass(MyReducer.class);
			
			
			job.setOutputKeyClass(Text.class);
			job.setOutputValueClass(Text.class);

			if(count==1){
				FileInputFormat.addInputPath(job, new Path("hdfs://hadoop01:9000/mianshiin"));
			}else{
				FileInputFormat.addInputPath(job, new Path("hdfs://hadoop01:9000/mianshi_out0"+(count-1)));
			}
			
			//需要设置          存成功标志文件
			FileOutputFormat.setOutputPath(job, new Path("hdfs://hadoop01:9000/mianshi_out0"+count));
			//提交代码
			job.waitForCompletion(true);
			if(count==3){
				break;
			}
		}
		
	}

}
