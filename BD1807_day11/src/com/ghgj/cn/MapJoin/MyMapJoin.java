package com.ghgj.cn.MapJoin;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


public class MyMapJoin {
	//�����key---������    value������������
	static class MyMapper extends Mapper<LongWritable, Text, Text, Text>{
		Map<String,String> map = new HashMap<String,String>();
		//setup
		@Override
		protected void setup(Context context)
				throws IOException, InterruptedException {
			//��ȡt_product ������ ��ȡ����һ��������map������    �������ֶ�  velue��������
			//��ȡ�ļ��Ļ���·��
			Path[] lpath = context.getLocalCacheFiles();
			//����һ�������ж�ȡ
			BufferedReader br=new BufferedReader(new FileReader(lpath[0].toString()));
			//��ȡ
			String line=null;
			//P0001   С��5   C01     2000   
			//P0001   С��6   C02     2000 
			while((line=br.readLine())!=null){
				String[] datas = line.split("\t");
				map.put(datas[0], datas[1]+"\t"+datas[2]+"\t"+datas[3]);
			}
		}
		Text mk=new Text();
		Text mv=new Text();
		@Override
		protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			//��ȡ  t_order�ļ�����Ϣ  1001    20150710        P0001   2
			String[] datas = value.toString().split("\t");
			String mkey=datas[2];
			mk.set(mkey);
			//���й���
			if(map.containsKey(mkey)){
				//����ƴ��
				String res=map.get(mkey)+"\t"+datas[0]+"\t"+datas[1]+"\t"+datas[3];
				mv.set(res);
				context.write(mk, mv);
			}
			
		}
		
	}
	
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException, URISyntaxException {
		System.setProperty("HADOOP_USER_NAME", "hadoop");
		
		Configuration conf=new Configuration();
		conf.set("fs.defaultFS", "hdfs://hadoop01:9000");
		Job job=Job.getInstance(conf);
		job.setJarByClass(MyMapJoin.class);
		
		
		job.setMapperClass(MyMapper.class);
		
		//
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);

		//��reducetask�ĸ�������Ϊ0   
		job.setNumReduceTasks(0);
		//���ƶ���·���ļ����ص�ÿһ��maptask�Ļ�����   ������������Ҫ���صĻ����ļ�hdfs·��
		job.addCacheFile(new URI("/joinin/t_product"));

		FileInputFormat.setInputPaths(job, "/joinin/t_order");
		FileOutputFormat.setOutputPath(job, new Path("/map_join_out02"));
		//�ύ����
		job.waitForCompletion(true);
	}
	
}
