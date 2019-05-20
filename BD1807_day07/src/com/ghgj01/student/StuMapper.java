package com.ghgj01.student;

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
		mk.set(line);
		mv.set("1");
		context.write(mk, mv);
	}
}	
