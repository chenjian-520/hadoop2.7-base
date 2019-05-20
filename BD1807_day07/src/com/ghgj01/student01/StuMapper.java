package com.ghgj01.student01;

import java.io.IOException;

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
		mk.set(split[0]);

		String st =split[split.length-2]+","+split[split.length-1];
		mv.set(st);
		context.write(mk, mv);
	}
}	
