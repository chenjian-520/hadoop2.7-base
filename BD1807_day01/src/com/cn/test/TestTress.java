package com.cn.test;

import java.util.TreeSet;

public class TestTress {
	public static void main(String[] args) {
		System.out.println(digui(6));
		
	}

	public static void test() {
		TreeSet<Integer> test = new TreeSet<>();
		test.add(6);
		test.add(3);
		test.add(5);
		test.add(2);
		test.add(8);
		test.add(11);
		test.add(13);
		System.out.println(test);
		
		TreeSet<Stu> test1 = new TreeSet<>();
		test1.add(new Stu("zs",18));
		test1.add(new Stu("ls",27));
		test1.add(new Stu("ww",28));
		test1.add(new Stu("zl",38));
		test1.add(new Stu("lq",22));
		System.out.println(test1);
	}
	
	public static int digui(int month){
		if(month==1||month==2){
			return 1;
		}else{
			return digui(month-1)+digui(month-2);
		}
	
		
	}
	
}
