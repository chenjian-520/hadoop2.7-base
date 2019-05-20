package com.ghgj.workHuFen02;

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


public class HuFen {

	static class MyMapper extends Mapper<LongWritable, Text, Text, Text> {
		
		   Text mk = new Text();
	       Text mv = new Text();
		@Override
		protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			String[] split = value.toString().split(":");
			mv.set(split[0]);
			String[] ren = split[1].split(",");
			for(String k:ren){
				mk.set(combineStr(split[0], k));
				context.write(mk, mv);
			}
		}
		
		private String combineStr(String a, String b) {
			if (a.compareTo(b) > 0) {
				return b +"-"+ a;
			} else {
				return a +"-"+ b;
			}

	}
}
	static class MyReducer extends Reducer<Text, Text, Text, NullWritable> {
	     Text mv = new Text();
		@Override
		protected void reduce(Text key, Iterable<Text> values, Reducer<Text, Text, Text, NullWritable>.Context context)
				throws IOException, InterruptedException {
			int sum = 0;
			for (Text t : values) {
				sum++;
			}
			if (sum == 2) {
				context.write(key, NullWritable.get());
			}
 
		}
	}
	
	public static void main(String[] args) {
		
		System.setProperty("HADOOP_USER_NAME", "chen");
		Configuration conf = new Configuration();
		try {
			Job job = Job.getInstance(conf);
			
			job.setJarByClass(HuFen.class);
			job.setMapperClass(MyMapper.class);
			job.setReducerClass(MyReducer.class);
			
			job.setMapOutputKeyClass(Text.class);
			job.setMapOutputValueClass(Text.class);
			job.setOutputKeyClass(Text.class);
			job.setOutputValueClass(NullWritable.class);
			
			Path inpath = new Path("hdfs://192.168.66.131:9000/work_hufen");
			FileInputFormat.addInputPath(job, inpath);
			Path outpath = new Path("hdfs://192.168.66.131:9000/work_hufen_out3");
			FileOutputFormat.setOutputPath(job, outpath);
			
			job.waitForCompletion(true);
		} catch (IOException | ClassNotFoundException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
