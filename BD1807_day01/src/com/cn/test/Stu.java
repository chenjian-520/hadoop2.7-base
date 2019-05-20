package com.cn.test;

public class Stu implements Comparable<Stu>{
  private String name;
  private int age ;
public Stu(String name, int age) {
	super();
	this.name = name;
	this.age = age;
}
public Stu() {
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
@Override
public int compareTo(Stu o) {
	// TODO Auto-generated method stub
	return this.age-o.age;
}
  
}
