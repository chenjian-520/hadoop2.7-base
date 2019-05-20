package com.cn.test;

import java.util.Comparator;

public class MyComparator implements Comparator<Stu2> {

	@Override
	public int compare(Stu2 o1, Stu2 o2) {
		// TODO Auto-generated method stub
		return o1.getAge()-o2.getAge();
	}
	
}	
