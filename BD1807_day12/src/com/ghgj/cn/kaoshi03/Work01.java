package com.ghgj.cn.kaoshi03;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;



public class Work01 {
	
	static class MyMapper extends Mapper<LongWritable, Text, Shop, NullWritable>{
		
		@Override
		protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Shop, NullWritable>.Context context)
				throws IOException, InterruptedException {
			Shop shop = new Shop();
			String[] datas = value.toString().split("\t");
			shop.setWeek(datas[0]);
			shop.setName(datas[1]);
			shop.setPrice(Double.parseDouble(datas[2]));
			shop.setCount(Integer.parseInt(datas[3]));
			
			double a = Double.parseDouble(datas[2]);
			double b = Double.parseDouble(datas[2]);
			double sum = a*b;
			shop.setAvg(sum);
			context.write(shop, NullWritable.get());
			
		}
	}
	
	static class MyReducer extends Reducer<Shop, NullWritable, Shop, NullWritable>{
		Text mk = new Text();
		DoubleWritable mv = new DoubleWritable();
		@Override
		protected void reduce(Shop key, Iterable<NullWritable> values,
				Reducer<Shop, NullWritable, Shop, NullWritable>.Context context)
				throws IOException, InterruptedException {
			int count =0;
			String name =key.getName();
			for(NullWritable v:values){
				count++;
				
				context.write(key, NullWritable.get());
				
				if(count==3){
					break;
				}
			}
		
		
		}
	}
	
	public static void main(String[] args) {
		System.setProperty("HADOOP_USER_NAME", "chen");
		Configuration conf = new Configuration();
		try {
			Job job = Job.getInstance(conf);
			
			job.setJarByClass(Work01.class);
			job.setMapperClass(MyMapper.class);
			job.setReducerClass(MyReducer.class);
			
			
			job.setMapOutputKeyClass(Shop.class);
			job.setMapOutputValueClass(NullWritable.class);
			job.setOutputKeyClass(Shop.class);
			job.setOutputValueClass(NullWritable.class);
			
			//设定分组
			job.setGroupingComparatorClass(MyGroup.class);
			
			Path inpath = new Path("hdfs://192.168.66.131:9000/kaoshi/mapreduce01");
			FileInputFormat.addInputPath(job, inpath);
			Path outpath = new Path("hdfs://192.168.66.131:9000/kaoshi/mapreduce02_out");
			FileOutputFormat.setOutputPath(job, outpath);
			
			job.waitForCompletion(true);
		} catch (IOException | ClassNotFoundException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
