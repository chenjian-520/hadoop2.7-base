package com.ghgj.defineGroup;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

public class MyGroup extends WritableComparator{
	/**
	 * �����������������Ƚ϶����key
	 */
	
	@Override
	public int compare(WritableComparable a, WritableComparable b) {
		// TODO Auto-generated method stub
		Student3 asb = (Student3)a;
		Student3 bsb = (Student3)b;
		return asb.getName().compareTo(bsb.getName());
	}

	public MyGroup() {
		//������Ҫ�ıȽ϶���
		super(Student3.class,true);
		// TODO Auto-generated constructor stub
	}
	
}
