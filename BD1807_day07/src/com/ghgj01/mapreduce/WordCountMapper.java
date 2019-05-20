package com.ghgj01.mapreduce;
import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
/**
 * 单词统计
 */
import org.apache.hadoop.mapreduce.Mapper;
/**
 * 输入输出  map类型 
 * KEYIN, 输入的键的类型  这里指的是每一行的起始偏移量 long
 * VALUEIN, 输入的value的类型 这里指的是一行的内容   和偏移量一一对应的 String
 * 输出的两个类型   取决于自己的业务
 * KEYOUT, 输出的键的类型  这里指的每一个单词
 * VALUEOUT 输出的值的类型  这里指的每一个单词出现的次数
 * @author ASUS
 * 
 * 这里的数据类型 不能使用java的原生类型
 * 	序列化：数据持久化 或 网络传输的时候 数据需要序列化和反序列化的
 * 	java---实现---Serializable
 * 	mapreduce编程中的用于传输的数据类型必须是有序列化和反序列化能力
 * 		hadoop弃用了java中原生的Serializable  实现的之间的一套序列化和反序列化的接口 Writable 只会对数据的值进行序列化和反序列化
 *  	原因：java中的序列化和反序列化太重   繁琐
 *  	对于一些常用的数据类型 hadoop帮我们实现好了
 *  	int---IntWritable
 *  	long---LongWritable
 *  	String---Text
 *  	btye---ByteWritable
 *  	double---DoubleWritable
 *  	float---FloatWritable
 *  	boolean--BooleanWritable
 *  	null---NullWritable
 *  自己定义的需要序列化和反序列化 实现 Writable接口
 */
public class WordCountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

	/**
	 * 重写map函数
	 * LongWritable key：输入的key 这里指的是每一行的偏移量  没有实际作用 一行的表示而已
	 * Text value, 输入的value 这里指的是一行的内容 
	 * Context context 上下文对象 用于传输  传输reduce中
	 * 函数的调用频率：
	 * 一行调用一次
	 * 一个文件10行  map函数调用10次
	 */
	
	@Override
	protected void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		//创建一个流 进行读取 （MapReduce框架帮你做了）  每一行内容进行切分
		//每一行的内容进行切分
		//text---String
		String line = value.toString();
		//进行切分 
		String[] words = line.split("\t");
		//循环遍历每一个单词进行统计 直接发送到reduce端  发送的时候 k-v
		for(String w:words){
			//将String---Text
			Text mk = new Text(w);
			//int ---- IntWritable
			IntWritable mv = new IntWritable(1);
			//这里的write直接写出 调用一次 就会写出一个
			context.write(mk, mv);
		}
		
	}

	
}






