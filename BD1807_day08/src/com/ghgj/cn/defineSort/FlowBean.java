package com.ghgj.cn.defineSort;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;

public class FlowBean implements WritableComparable<FlowBean>{
	//�ֻ���
	private String phoneno;
	private int upFlow;
	private int downFlow;
	private int sumFlow;
	//���л�
	@Override
	public void write(DataOutput out) throws IOException {
		// TODO Auto-generated method stub
		out.writeUTF(phoneno);
		out.writeInt(upFlow);
		out.writeInt(downFlow);
		out.writeInt(sumFlow);
	}
	//�����л�
	@Override
	public void readFields(DataInput in) throws IOException {
		// TODO Auto-generated method stub
		this.phoneno=in.readUTF();
		this.upFlow=in.readInt();
		this.downFlow=in.readInt();
		this.sumFlow=in.readInt();
	}
	//�ȽϷ���
	@Override
	public int compareTo(FlowBean o) {
		// ������������
		int tmp=o.getSumFlow()-this.getSumFlow();
		if(tmp==0){
			//�ֻ���������
			tmp=this.getPhoneno().compareTo(o.getPhoneno());
		}
		return tmp;
	}
	
	@Override
	public String toString() {
		return phoneno + "\t" + upFlow + "\t" + downFlow + "\t" + sumFlow
				;
	}
	public FlowBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public FlowBean(String phoneno, int upFlow, int downFlow, int sumFlow) {
		super();
		this.phoneno = phoneno;
		this.upFlow = upFlow;
		this.downFlow = downFlow;
		this.sumFlow = sumFlow;
	}
	public String getPhoneno() {
		return phoneno;
	}
	public void setPhoneno(String phoneno) {
		this.phoneno = phoneno;
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
