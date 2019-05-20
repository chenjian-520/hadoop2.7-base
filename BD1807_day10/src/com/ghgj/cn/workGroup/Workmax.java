package com.ghgj.cn.workGroup;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;




public class Workmax {
	
	static class MyMapper extends Mapper<LongWritable, Text, Student, NullWritable>{
		
		Text mk = new Text();
		Student stu = new Student();
		@Override
		protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Student, NullWritable>.Context context)
				throws IOException, InterruptedException {
			String[] datas = value.toString().split("\t");
			int sum=0;
			for(int i=3;i<datas.length;i++){
				sum+=Integer.parseInt(datas[i].trim());
			}
			stu.setBan(datas[0].trim());
			stu.setCount(datas[1].trim());
			stu.setName(datas[2].trim());
			stu.setSum(sum);
			System.out.println(stu);
			context.write(stu, NullWritable.get());
			
		}
	}
	
	static class MyReducer extends Reducer<Student, NullWritable, Student, NullWritable>{
	
		@Override
		protected void reduce(Student key, Iterable<NullWritable> values,
				Reducer<Student, NullWritable, Student, NullWritable>.Context context)
				throws IOException, InterruptedException {
			int count=0;
			System.out.println("11111111111111111111111111111111111");
			for(NullWritable v:values){
				System.out.println(key);
				count++;
				context.write(key, NullWritable.get());
				if(count==5){
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
			
			job.setJarByClass(Workmax.class);
			job.setMapperClass(MyMapper.class);
			job.setReducerClass(MyReducer.class);
			
			
			job.setMapOutputKeyClass(Student.class);
			job.setMapOutputValueClass(NullWritable.class);
			job.setOutputKeyClass(Student.class);
			job.setOutputValueClass(NullWritable.class);
			
			//设定分组
			job.setGroupingComparatorClass(MyGroup.class);
			
			
			Path inpath = new Path("hdfs://192.168.66.131:9000/score_in");
			FileInputFormat.addInputPath(job, inpath);
			Path outpath = new Path("hdfs://192.168.66.131:9000/score_out03");
			FileOutputFormat.setOutputPath(job, outpath);
			
			job.waitForCompletion(true);
		} catch (IOException | ClassNotFoundException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
