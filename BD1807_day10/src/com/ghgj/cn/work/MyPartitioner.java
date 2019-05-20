package com.ghgj.cn.work;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

public class MyPartitioner extends Partitioner<Text, Text>{
	@Override
	public int getPartition(Text key, Text value, int numPartitions) {
		// TODO Auto-generated method stub
		String cla = key.toString();
		if(cla.equals("1303")){
			return 0;
		}else if(cla.equals("1304")){
			return 1;
		}else if(cla.equals("1305")){
			return 2;
		}else if(cla.equals("1306")){
			return 3;
		}else {
			return 4;
		}
		
	}
}
