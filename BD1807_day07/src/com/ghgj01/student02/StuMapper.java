package com.ghgj01.student02;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class StuMapper extends Mapper<LongWritable, Text, Text, Text> {
	Text mk =new Text();
	Text mv =new Text();
	@Override
	protected void map(LongWritable key, Text value,Context context)
			throws IOException, InterruptedException {
		String line = value.toString();
		String[] split = line.split(",");
		mk.set(split[0]+","+split[split.length-1]);

		String st =split[split.length-2];
		if(split.length==2){
			mv.set("null");
		}else{
			mv.set(st);
		}
		context.write(mk, mv);
	}
}	
