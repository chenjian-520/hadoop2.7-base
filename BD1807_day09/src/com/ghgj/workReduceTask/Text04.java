package com.ghgj.workReduceTask;

import java.io.IOException;
import java.math.BigDecimal;

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



public class Text04 {
	
	static class MyMapper extends Mapper<LongWritable, Text, Student3, NullWritable>{
		
	@Override
		protected void map(LongWritable key, Text value,
				Mapper<LongWritable, Text, Student3, NullWritable>.Context context)
				throws IOException, InterruptedException {
		String[] datas = value.toString().split(",");		
		int sum=0;
		double avg =0;
		for(int i=2;i<datas.length;i++){
			sum += Integer.parseInt(datas[i].trim());
		}
		avg = (double)sum/(datas.length-2);
		//保留一位小数
	double ss = new BigDecimal(avg).setScale(1,BigDecimal.ROUND_HALF_UP).doubleValue();

		
		Student3 stu = new Student3(datas[0],datas[1],ss);
		
		context.write(stu, NullWritable.get());
		}
	}
	
	static class MyReducer extends Reducer<Student3, NullWritable, Student3, NullWritable>{
		@Override
		protected void reduce(Student3 key, Iterable<NullWritable> values, Reducer<Student3, NullWritable, Student3, NullWritable>.Context context)
				throws IOException, InterruptedException {
			int count =0;
			for(NullWritable v: values){
				count++;
				context.write(key, NullWritable.get());
				if(count>=3){
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
			
			job.setJarByClass(Text04.class);
			
			job.setMapperClass(MyMapper.class);
			
			job.setReducerClass(MyReducer.class);
			
			//设定分组
		//	job.setGroupingComparatorClass(MyGroup.class);
			
			job.setPartitionerClass(Mypartitioner.class);
			job.setNumReduceTasks(4);
			
			job.setMapOutputKeyClass(Student3.class);
			job.setMapOutputValueClass(NullWritable.class);
			
			job.setOutputKeyClass(Student3.class);
			job.setOutputValueClass(NullWritable.class);
			
			Path inpath =new Path("hdfs://192.168.66.131:9000/sort");
			FileInputFormat.addInputPath(job, inpath);
			
			Path outpath = new Path("hdfs://192.168.66.131:9000/sort_Group2");
			FileOutputFormat.setOutputPath(job, outpath);
			
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
