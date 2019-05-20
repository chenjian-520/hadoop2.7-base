package com.cn.test;

public class Stu2 {
  private String name;
  private int age ;
public Stu2(String name, int age) {
	super();
	this.name = name;
	this.age = age;
}
public Stu2() {
	super();
	// TODO Auto-generated constructor stub
}
@Override
public String toString() {
	return "Stu [name=" + name + ", age=" + age + "]";
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public int getAge() {
	return age;
}
public void setAge(int age) {
	this.age = age;
}

  
}
