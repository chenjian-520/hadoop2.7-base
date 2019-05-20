package com.ghgj01.student01;

import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class StuReducer extends Reducer<Text, Text, Text, DoubleWritable>{
	Text mk =new Text();
	DoubleWritable mv =new DoubleWritable();
	@Override
	protected void reduce(Text key, Iterable<Text> values,Context context)
			throws IOException, InterruptedException {
		int max =0;
		int min =1000;
		int sum =0;
		int count=0;
		double avg = 0.0;
		String str = "kk";
		for(Text v:values){
			String line = v.toString();
			if(str.equals(line)){
				continue;
			}else{
				count++;
				str=line;
				
				String[] split = line.split(",");
				
				int data=Integer.parseInt(split[1]);
				count++;
				sum+=data;
				//最大值
				if(max<data){
					max=data;
				}
				if(min>data){
					min=data;
				}
				
			}
			
		}
		avg=(double)sum/count;
		
		mk.set(key+"的最大值：");
		mv.set(max);
		context.write(mk, mv);
		
		mk.set(key+"的最小值：");
		mv.set(min);
		context.write(mk, mv);
		
		mk.set(key+"的平均值：");
		mv.set(avg);
		context.write(mk, mv);
	}
	
}
