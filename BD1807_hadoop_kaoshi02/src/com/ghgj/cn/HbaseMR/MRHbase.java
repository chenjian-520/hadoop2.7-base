package com.ghgj.cn.HbaseMR;

import java.io.IOException;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;



public class MRHbase {
		private static final String TABLE_NAME = "mingxing1";
		private static final String MAN = "M";
		private static final String WOMAN = "F";
		private static final String ZK_CONNECT = "hadoop01:2181,hadoop02:2181,hadoop03:2181";

	
		static class CountMingxingSexMRMapper extends TableMapper<Text, IntWritable> {
			@Override
			protected void map(ImmutableBytesWritable key, 
					Result value, 
					Context context) throws IOException, InterruptedException {
			Text mk=new Text();
			IntWritable mv=new IntWritable();
			List<Cell> cells = value.listCells();
			for(Cell c:cells){
				String sex = new String(CellUtil.cloneValue(c));
					if(sex.equals(MAN)){
						mk.set(MAN);
						mv.set(1);
						context.write(mk, mv);
					}else if(sex.equals(WOMAN)){
						mk.set(WOMAN);
						mv.set(1);
						context.write(mk, mv);
					}
				}
			}
		}
		static class CountMingxingSexMRReducer extends Reducer<Text, IntWritable, Text, LongWritable> {
			@Override
			protected void reduce(Text key, 
					Iterable<IntWritable> values, Context context)
					throws IOException, InterruptedException {
				long count = 0;
				for(IntWritable lw : values){
						count += lw.get();	
				}
				context.write(key, new LongWritable(count));
			}
		}

	public static void main(String[] args) throws ClassNotFoundException, IOException, InterruptedException {
		
		Configuration conf = HBaseConfiguration.create();		
		conf.set("hbase.zookeeper.quorum", ZK_CONNECT);		 	
		System.setProperty("HADOOP_USER_NAME", "chen");		

		Job job = Job.getInstance(conf);
		job.setJarByClass(MRHbase.class);

		Scan scan = new Scan();
		TableMapReduceUtil.initTableMapperJob(TABLE_NAME,
				scan, 
				CountMingxingSexMRMapper.class,
				Text.class, 
				IntWritable.class,
				job);
		job.setReducerClass(CountMingxingSexMRReducer.class);	

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(LongWritable.class);

		Path outputPath = new Path("hdfs://bd1807/mingxing_out01");
		FileSystem fs = FileSystem.get(conf);
		if (fs.exists(outputPath)) {
			fs.delete(outputPath, true);
		}
		FileOutputFormat.setOutputPath(job, outputPath);

		boolean waitForCompletion = job.waitForCompletion(true);
		System.exit(waitForCompletion ? 0 : 1);		
	}

	

}
