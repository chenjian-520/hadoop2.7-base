package com.ghgj01.student02;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class StuReducer extends Reducer<Text, Text, Text, Text>{
	Text mk = new Text();
	Text mv = new Text();
	@Override
	protected void reduce(Text key, Iterable<Text> values,Context context)
			throws IOException, InterruptedException {
		int count =0;
		StringBuffer sb =new StringBuffer();
		for(Text na:values){
			String line = na.toString();
			if(line.equals(null)){
				continue;
			}else{
				sb.append(line).append(",");
				count++;
			}	
		}
		mk.set(key);
		mv.set(count+"   "+sb.substring(0, sb.length()-1));
		context.write(mk, mv);
		
	}
	
}
