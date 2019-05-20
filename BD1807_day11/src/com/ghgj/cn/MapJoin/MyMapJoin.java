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
	//输出：key---关联件    value：输出关联结果
	static class MyMapper extends Mapper<LongWritable, Text, Text, Text>{
		Map<String,String> map = new HashMap<String,String>();
		//setup
		@Override
		protected void setup(Context context)
				throws IOException, InterruptedException {
			//读取t_product 缓存中 读取放在一个容器：map集合中    《关联字段  velue：其他》
			//获取文件的缓存路径
			Path[] lpath = context.getLocalCacheFiles();
			//创建一个流进行读取
			BufferedReader br=new BufferedReader(new FileReader(lpath[0].toString()));
			//读取
			String line=null;
			//P0001   小米5   C01     2000   
			//P0001   小米6   C02     2000 
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
			//获取  t_order文件的信息  1001    20150710        P0001   2
			String[] datas = value.toString().split("\t");
			String mkey=datas[2];
			mk.set(mkey);
			//进行关联
			if(map.containsKey(mkey)){
				//进行拼接
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

		//将reducetask的个数设置为0   
		job.setNumReduceTasks(0);
		//将制定的路径文件加载到每一个maptask的缓存中   参数：就是需要加载的缓存文件hdfs路劲
		job.addCacheFile(new URI("/joinin/t_product"));

		FileInputFormat.setInputPaths(job, "/joinin/t_order");
		FileOutputFormat.setOutputPath(job, new Path("/map_join_out02"));
		//提交代码
		job.waitForCompletion(true);
	}
	
}
