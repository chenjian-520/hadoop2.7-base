package com.ghgj01.student;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class StuReducer extends Reducer<Text, Text, Text, Text>{
	Text mv = new Text();
	@Override
	protected void reduce(Text key, Iterable<Text> values,Context context)
			throws IOException, InterruptedException {
		mv.set("..");
		context.write(key, mv);
	}
	
}
