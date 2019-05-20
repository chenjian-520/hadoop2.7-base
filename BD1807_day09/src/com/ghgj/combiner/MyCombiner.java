package com.ghgj.combiner;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
/**
 * maptask --- combiner---reducetask
 *前两个对应的maptask的输出
 *后两个对应reducetask的输入
 *前两个泛型的类型===后两个泛型
 */
public class MyCombiner extends Reducer<Text, IntWritable, Text, IntWritable>{
	/**
	 * 调用频率 ： 一组调用一次
	 * 一组：相同的key为一组
	 * 这里的combiner-----对应的仅仅是一个maptask的数据--一部分数据
	 * reducer对应的是所有的maptask数据
	 */
	IntWritable rv = new IntWritable();
	@Override
	protected void reduce(Text key, Iterable<IntWritable> values,
			Reducer<Text, IntWritable, Text, IntWritable>.Context context) throws IOException, InterruptedException {
		//循环遍历values 求和
		int sum=0;
		for(IntWritable v : values){
			//IntWritable --- int  数值类型 get 将hadoop中的类型转换为java中的类型
			sum+=v.get();
		}
		//写出结果
		rv.set(sum);
		context.write(key, rv);
		
	}
}
