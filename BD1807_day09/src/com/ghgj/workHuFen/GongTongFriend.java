package com.ghgj.workHuFen;

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

public class GongTongFriend {
	
	static class MyMapper extends Mapper<LongWritable, Text, Text, Text> {
		
		   Text mk = new Text();
	       Text mv = new Text();
		@Override
		protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
            //A:B,C,D,F,E,O
            String [] reads = value.toString().trim().split(":");
            //以用户为value值，好友为key值，求取以某用户为共同好友的人
            String vv = reads[0];
            mv.set(vv);
            String [] friends = reads[1].split(",");
            for(int i = 0; i < friends.length; i++){
                String kk = friends[i];
                mk.set(kk);
                context.write(mk, mv);
		}
	}
}
	static class MyReducer extends Reducer<Text, Text, Text, Text> {
	     Text mv = new Text();
		@Override
		protected void reduce(Text key, Iterable<Text> values, Reducer<Text, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
            //将用户共同好友的人组合起来
            StringBuilder sb = new StringBuilder();
            for(Text text : values){
                sb.append(text.toString()).append(",");
            }
            String vv = sb.substring(0, sb.length()-1);
            mv.set(vv);
            context.write(key, mv);
            
		}
	}
	
	public static void main(String[] args) {
		
		System.setProperty("HADOOP_USER_NAME", "chen");
		Configuration conf = new Configuration();
		try {
			Job job = Job.getInstance(conf);
			
			job.setJarByClass(GongTongFriend.class);
			job.setMapperClass(MyMapper.class);
			job.setReducerClass(MyReducer.class);
			
			job.setOutputKeyClass(Text.class);
			job.setOutputValueClass(Text.class);
			
			Path inpath = new Path("hdfs://192.168.66.131:9000/work_hufen");
			FileInputFormat.addInputPath(job, inpath);
			Path outpath = new Path("hdfs://192.168.66.131:9000/work_hufen_out1");
			FileOutputFormat.setOutputPath(job, outpath);
			
			job.waitForCompletion(true);
		} catch (IOException | ClassNotFoundException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
	
