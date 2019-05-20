package com.ghgj01.testmapreduce;

import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
/**
 * reduce输出
 * 	key：最大值/最小值/平均值
 * value：值
 * @author Administrator
 *
 */
public class MyReducer extends Reducer<Text, IntWritable, Text, DoubleWritable>{
	Text rk=new Text();
	DoubleWritable rv=new DoubleWritable();
	@Override
	protected void reduce(Text key, Iterable<IntWritable> values,
			Context context) throws IOException, InterruptedException {
		//循环遍历values
		int max=0;
		int min=100000;
		int sum=0;
		int count=0;
		for(IntWritable v:values){
			int data=v.get();
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
		double avg=(double)sum/count;
		//输出
		rk.set("最大值：");
		rv.set(max);
		//context.write(rk, rv);   可以调用多次  调用一次 写出一条数据
		context.write(rk, rv);
		
		rk.set("最小值：");
		rv.set(min);
		context.write(rk, rv);
		
		rk.set("平均值：");
		rv.set(avg);
		context.write(rk, rv);
	}
}
