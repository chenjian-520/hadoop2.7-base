package com.ghgj.cn.defineClass;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;


public class FlowBean implements Writable{
	//��������
	private int upFlow;
	//��������
	private int downFlow;
	//������
	private int sumFlow;
	
	//���л�����
	@Override
	public void write(DataOutput out) throws IOException {
		// ͨ������������� д��   �ַ��������л�  writeUTF
		out.writeInt(upFlow);
		out.writeInt(downFlow);
		out.writeInt(sumFlow);
	}
	//�����л�����
	//�����л���˳��  һ��Ҫ�� ���л���˳��һ��
	@Override
	public void readFields(DataInput in) throws IOException {
		// �����ж�ȡ  ת������  String---readUtf
		this.upFlow=in.readInt();
		this.downFlow=in.readInt();
		this.sumFlow=in.readInt();
	}
	
	@Override
	public String toString() {
		return upFlow + "\t" + downFlow + "\t" + sumFlow;
	}
	
	public FlowBean(int upFlow, int downFlow) {
		super();
		this.upFlow = upFlow;
		this.downFlow = downFlow;
		this.sumFlow = upFlow+downFlow;
	}
	
	public FlowBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public int getUpFlow() {
		return upFlow;
	}
	public void setUpFlow(int upFlow) {
		this.upFlow = upFlow;
	}
	public int getDownFlow() {
		return downFlow;
	}
	public void setDownFlow(int downFlow) {
		this.downFlow = downFlow;
	}
	public int getSumFlow() {
		return sumFlow;
	}
	public void setSumFlow(int sumFlow) {
		this.sumFlow = sumFlow;
	}


}
