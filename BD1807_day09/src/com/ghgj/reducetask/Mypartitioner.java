package com.ghgj.reducetask;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

public class Mypartitioner extends Partitioner<Text, Text>{
	/**
	 * 参数1-3  map的输出key  map的输出value  分区个数
	 */
	@Override
	public int getPartition(Text key, Text value, int numPartitions) {
		// TODO Auto-generated method stub
		String mk = key.toString();
		if(mk.startsWith("134")||mk.startsWith("135")||mk.startsWith("136")){
			return 0;
		}else if(mk.startsWith("137")||mk.startsWith("138")||mk.startsWith("139")){
			return 1;
		}else{
			return 2;
		}
	}
	
}
