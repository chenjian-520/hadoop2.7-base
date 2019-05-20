package com.cn.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestSort {
	public static void main(String[] args) {
		/*ArrayList<String> list = new ArrayList<>();
		list.add("a");
		list.add("w");
		list.add("e");
		list.add("g");
		list.add("j");
		System.out.println(list);
		Collections.sort(list);
		System.out.println(list);*/
		/*ArrayList<Stu> list = new ArrayList<>();
		list.add(new Stu("zs",18));
		list.add(new Stu("ls",27));
		list.add(new Stu("ww",28));
		list.add(new Stu("zl",38));
		list.add(new Stu("lq",22));
		System.out.println(list);
		Collections.sort(list);
		System.out.println(list);*/
		
		ArrayList<Stu2> list = new ArrayList<>();
		list.add(new Stu2("zs",18));
		list.add(new Stu2("ls",27));
		list.add(new Stu2("ww",28));
		list.add(new Stu2("zl",38));
		list.add(new Stu2("lq",22));
		System.out.println(list);
		Collections.sort(list,(a,b)->a.getAge()-b.getAge());
		System.out.println(list);
		
	}
}
