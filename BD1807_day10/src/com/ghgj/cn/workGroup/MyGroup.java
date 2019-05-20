package com.ghgj.cn.workGroup;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

public class MyGroup extends WritableComparator {
	
	


	public MyGroup() {
		//创建需要的比较对象
		super(Student.class,true);
		// TODO Auto-generated constructor stub
	}



	@Override
	public int compare(WritableComparable a, WritableComparable b) {
		// TODO Auto-generated method stub
		Student aa=(Student)a;
		Student bb=(Student)b;
		return aa.getBan().compareTo(bb.getBan());
	}
}
