package com.ghgj.defineGroup;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

public class MyGroup extends WritableComparator{
	/**
	 * 两个参数代表两个比较对象的key
	 */
	
	@Override
	public int compare(WritableComparable a, WritableComparable b) {
		// TODO Auto-generated method stub
		Student3 asb = (Student3)a;
		Student3 bsb = (Student3)b;
		return asb.getName().compareTo(bsb.getName());
	}

	public MyGroup() {
		//创建需要的比较对象
		super(Student3.class,true);
		// TODO Auto-generated constructor stub
	}
	
}
