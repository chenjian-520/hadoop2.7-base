package com.ghgj.reducetask;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

public class Mypartitioner extends Partitioner<Text, Text>{
	/**
	 * ����1-3  map�����key  map�����value  ��������
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
