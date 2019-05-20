package com.ghgj.cn.kaoshi03;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;



public class MyGroup  extends WritableComparator {
	public MyGroup() {
		//创建需要的比较对象
		super(Shop.class,true);
		// TODO Auto-generated constructor stub
	}



	@Override
	public int compare(WritableComparable a, WritableComparable b) {
		// TODO Auto-generated method stub
		Shop aa=(Shop)a;
		Shop bb=(Shop)b;
		return aa.getName().compareTo(bb.getName());
	}
}
