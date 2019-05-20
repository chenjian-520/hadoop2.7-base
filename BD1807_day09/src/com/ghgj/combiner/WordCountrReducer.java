package com.ghgj.combiner;
import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
/**
 * 单词统计
 */
import org.apache.hadoop.mapreduce.Reducer;
/**
 * reduce 的数据来源于map
 * @author ASUS
 *KEYIN, 输入的key的类型 这里指的是map输出key类型  Text
 *VALUEIN, 输入的value的类型  这里指的是map输出的value的类型  IntWretable
 *输出
 *KEYOUT, 输出的key 	这里指的单词的类型  Text
 *VALUEOUT 输出的value的类型 	这里指的是单词的总次数 IntWritable
 */
public class WordCountrReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
	/**
	 * 到reduce端的数据 是已经分好组的数据
	 * 默认情况下 按照map输出的key进行分组 将map输出的key相同的分为一组
	 * Text key		每一组中相同的key
	 * Iterable<IntWritable> value  每一组中的所有的value值 封装到一个迭代器中
	 * Context context 上下文对象 用于传输的 写出到hdfs中
	 * 
	 * reduce调用频率：
	 *   一组调用一次  每次只统计一个单词最终结果
	 */
	@Override
	protected void reduce(Text key, Iterable<IntWritable> values,Context context) 
		throws IOException, InterruptedException {
		//循环遍历values 求和
		int sum=0;
		for(IntWritable v : values){
			//IntWritable --- int  数值类型 get 将hadoop中的类型转换为java中的类型
			sum+=v.get();
		}
		//写出结果
		IntWritable rv = new IntWritable(sum);
		context.write(key, rv);
	}
	
}
