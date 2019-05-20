package com.aura.hbase.day03.mr;


import java.io.IOException;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


public class ReadHBaseDataMR {
	
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		
		System.setProperty("HADOOP_USER_NAME", "chen");
		Configuration conf = HBaseConfiguration.create();
		conf.set("hbase.zookeeper.quorum", "hadoop01:2181,hadoop02:2181,hadoop03:2181");
		conf.addResource("resources/log4j.properties");
		conf.addResource("resources/core.site.xml");
		conf.addResource("resources/hdfs.site.xml");
		
		Job job = Job.getInstance(conf);
		
		job.setJarByClass(ReadHBaseDataMR.class);
		
		Scan scan = new Scan();
		TableMapReduceUtil.initTableMapperJob(
				"user_info", 
				scan, 
				ReadHBaseDataMRP_Mapper.class, 
				Text.class, 
				NullWritable.class, 
				job,
				false);
		
		job.setNumReduceTasks(0);
		
		FileOutputFormat.setOutputPath(job, new Path("/hbase/mapreduce/tx_1"));
		
		boolean waitForCompletion = job.waitForCompletion(true);
		System.out.println(waitForCompletion);
		
	}
}


class ReadHBaseDataMRP_Mapper extends TableMapper<Text, NullWritable>{
	@Override
	protected void map(ImmutableBytesWritable key, Result result,
			Mapper<ImmutableBytesWritable, Result, Text, NullWritable>.Context context)
			throws IOException, InterruptedException {
		List<Cell> cells = result.listCells();
		Text text = new Text();
		 // 最后输出格式是: rowkye, base_info:name-huangbo, base-info:age-34
		 for (Cell cell : cells) {
		 String rowkey = Bytes.toString(CellUtil.cloneRow(cell));
		 String family = new String(CellUtil.cloneFamily(cell));
		 String qualifier = new String(CellUtil.cloneQualifier(cell));
		 String v = new String(CellUtil.cloneValue(cell));
		 long ts = cell.getTimestamp();
		 text.set(rowkey + "\t" + family + "\t" + qualifier + "\t" + v + "\t" + ts);
		 context.write(text, NullWritable.get());
		 }

	}
}