package com.aura.hbase.day03.mr;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Mutation;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.mapreduce.TableReducer;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.ID;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;

/**
 * 读取HDFS上的数据，插入到对应的hbase表中
 * 
 * 原始数据：student.txt 二维文本数据
 * 
 * hbase的表：四维表：rowkey, column family, qualifier, value, timestamp
 * 
 * 难点： 如何把二维的数据插入到hbase中呢？
 * 
 * id info name,sex,age,department rowkey, column family, qualifier, value, ts
 */
public class ReadHDFSDataToHBaseMR {
	
	private static final String ZK_CONNECT_KEY = "hbase.zookeeper.quorum";
	private static final String ZK_CONNECT_VALUE = "hadoop01:2181,hadoop02:2181,hadoop03:2181";
	private static final String TABLE = "student";

	public static void main(String[] args) throws Exception {

		System.setProperty("HADOOP_USER_NAME", "chen");
		Configuration conf = HBaseConfiguration.create();
		conf.addResource("config/core-site.xml");
		conf.addResource("config/hdfs-site.xml");
		conf.set(ZK_CONNECT_KEY, ZK_CONNECT_VALUE);
		Job job = Job.getInstance(conf);
		
		
		job.setJarByClass(ReadHDFSDataToHBaseMR.class);
		
		
		job.setMapperClass(ReadHDFSDataToHBaseMR_Mapper.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(NullWritable.class);
		
		
		TableMapReduceUtil.initTableReducerJob(TABLE, 
				ReadHDFSDataToHBaseMR_Reducer.class, 
				job, 
				null, 
				null, 
				null, 
				null, 
				false);
//		TableMapReduceUtil.initTableReducerJob
		
		
		FileInputFormat.setInputPaths(job, new Path("/student/student.txt"));
		
		
		boolean isDone = job.waitForCompletion(true);
		System.out.println(isDone);
	}
}

class ReadHDFSDataToHBaseMR_Mapper extends Mapper<LongWritable, Text, Text, NullWritable> {
	
	/**
	 * value :   95019,邢小丽,女,19,IS
	 */
	@Override
	protected void map(LongWritable key, Text value, 
			Mapper<LongWritable, Text, Text, NullWritable>.Context context)
			throws IOException, InterruptedException {
		
		context.write(value,  NullWritable.get());
	}
}

// 往hbase表输出数据。
// 必须要更改对应的数据输出组件：  因为默认的数据输出组件：
	//   TextOutputFormat
    //   LineRecordWriter
class ReadHDFSDataToHBaseMR_Reducer extends TableReducer<Text, NullWritable, NullWritable>{
	
	String[] columns = new String[]{"name","sex","age","department"};
	
	/**
	 * key :   95019,邢小丽,女,19,IS
	 */
	@Override
	protected void reduce(Text key, Iterable<NullWritable> nvls,
			Reducer<Text, NullWritable, NullWritable, Mutation>.Context context) throws IOException, InterruptedException {
		
		// 这一句代码，就是解析一个行数据，得出了五个字段
		String[] fields = key.toString().trim().split(",");
		
		// 第一个字段，就是当做rowkey
		Put put = new Put(fields[0].getBytes());
		
		// 后面的四个字段，分别构造四个key-value
		// 插入四个key-value， 就是四个put
		for(int i=1; i<fields.length; i++){
			String field = fields[i];
			String column = columns[i-1];
			put.addColumn("info".getBytes(), column.getBytes(), field.getBytes());
			context.write(NullWritable.get(), put);
		}
	}
}
