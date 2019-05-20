package com.ghgj.cn.HbaseMR;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.mapreduce.TableReducer;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;

public class T4 {

	private static final String ZK_CONNECT_KEY="hbase.zookeeper.quorum";
	private static final String ZK_CONNECT_VALUE="hadoop01:2181,hadoop02:2181,hadoop03:2181";
	private static final String Table = "mingxing";
	
	public static void main(String[] args) throws Exception {
		System.setProperty("HADOOP_USER_NAME", "yujie");
		Configuration conf = HBaseConfiguration.create();
		conf.set(ZK_CONNECT_KEY, ZK_CONNECT_VALUE);
		conf.addResource("core-site.xml");
		conf.addResource("hdfs-site.xml");
		Job job = Job.getInstance(conf);
		
		job.setJarByClass(T4.class);
		
		job.setMapperClass(T4_Mapper.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(NullWritable.class);
		
		TableMapReduceUtil.initTableReducerJob(Table, T4_Reducer.class, job,
				null,null,null,null,false);
		
		FileInputFormat.setInputPaths(job, new Path("/mingxing.txt"));
		
		boolean isDone = job.waitForCompletion(true);
		System.out.println(isDone);
		
	}
}
class T4_Mapper extends Mapper<LongWritable, Text, Text, NullWritable>{
	
	@Override
	protected void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		context.write(value, NullWritable.get());
	}
}
//寰�hbase琛ㄨ緭鍑烘暟鎹�
//蹇呴』瑕佹洿鏀瑰搴旂殑鏁版嵁杈撳嚭缁勪欢锛氬洜涓洪粯璁ょ殑鏁版嵁杈撳嚭缁勪欢锛�
	//	TextOutputFormat
	//	LineRecordWriter
class T4_Reducer extends TableReducer<Text, NullWritable, NullWritable>{
	String[] columns = new String[]{"name","sex","age","phone","email","qq"};
	@Override
	protected void reduce(Text key, Iterable<NullWritable> nvls,
			Context context) throws IOException, InterruptedException {
		//瑙ｆ瀽涓�琛屾暟鎹紝寰楀埌6涓瓧娈�
		String[] fields = key.toString().trim().split(",");
		//寰楀埌绗竴涓瓧娈�,褰撳仛rowkey
		Put put = new Put(fields[0].getBytes());

		for(int i=1;i<3;i++){
			String field = fields[i];
			String column = columns[i-1];
			put.addColumn("basicinfo".getBytes(), column.getBytes(), field.getBytes());
			context.write(NullWritable.get(), put);
		}
		for(int i=3;i<fields.length;i++){
			String field = fields[i];
			String column = columns[i-1];
			put.addColumn("extrainfo".getBytes(), column.getBytes(), field.getBytes());
			context.write(NullWritable.get(), put);
		}
		
	}

}
