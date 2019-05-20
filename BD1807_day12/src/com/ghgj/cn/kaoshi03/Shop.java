package com.ghgj.cn.kaoshi03;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;

public class Shop implements WritableComparable<Shop> {
	private String week;
	private String name;
	private double price;
	private int count;
	private double avg;
	@Override
	public void write(DataOutput out) throws IOException {
		// TODO Auto-generated method stub
		out.writeUTF(week);
		out.writeUTF(name);
		out.writeDouble(price);
		out.writeInt(count);
		out.writeDouble(avg);
	}
	@Override
	public void readFields(DataInput in) throws IOException {
		// TODO Auto-generated method stub
		this.week=in.readUTF();
		this.name=in.readUTF();
		this.price=in.readDouble();
		this.count=in.readInt();
		this.avg=in.readDouble();
	}
	@Override
	public int compareTo(Shop o) {
		// TODO Auto-generated method stub
		int temp=o.name.compareTo(this.name);
		if(temp==0){
			double temp1=o.avg-this.avg;
			if(temp1>0){
				temp=1;
			}else if(temp1<0){
				temp=-1;
			}else{
				temp=0;
			}
		}
		return temp;
	}
	public Shop() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Shop(String week, String name, double price, int count, double avg) {
		super();
		this.week = week;
		this.name = name;
		this.price = price;
		this.count = count;
		this.avg = avg;
	}
	public String getWeek() {
		return week;
	}
	public void setWeek(String week) {
		this.week = week;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public double getAvg() {
		return avg;
	}
	public void setAvg(double avg) {
		this.avg = avg;
	}
	@Override
	public String toString() {
		return week + "\t" + name + "\t" + price + "\t" + count + "\t" + avg;
	}
	
	
	
}
