package com.ghgj.workReduceTask;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Partitioner;

public class Mypartitioner extends Partitioner<Student3, NullWritable>{

	@Override
	public int getPartition(Student3 key, NullWritable value, int numPartitions) {
		// TODO Auto-generated method stub
		String name = key.getName();
		if(name.equals("math")){
			return 0;
		}else if(name.equals("computer")){
			return 1;
		}else if(name.equals("english")){
			return 2;
		}else{
			return 3;
		}
	}
	
}
