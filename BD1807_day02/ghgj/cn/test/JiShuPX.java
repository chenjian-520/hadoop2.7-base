package com.ghgj.cn.test;

import java.util.Arrays;

public class JiShuPX {
	public static void main(String[] args) {
		int[] arr={1,4,7,3,2,4};
		int[] res=jspx(arr);
		System.out.println(Arrays.toString(res));
	}
	
	
	public static int[] jspx(int[] arr){
		//��  ���ֵ  ����Сֵ
		int max=arr[0];
		int min=arr[0];
		for(int a:arr){
			if(max<a){
				max=a;
			}
			if(min>a){
				min=a;
			}
		}
		int[] pxarr=new int[max-min+1];
		//ѭ������  ��������м�������
		for(int a:arr){
			pxarr[a-min]+=1;
		}
		//�������
		//������������
		int[] result=new int[arr.length];
		//��¼����������±�
		int index=0;
		//��ѭ�� ÿһ��Ԫ��  �������������±���
		for(int i=0;i<pxarr.length;i++){
			//ѭ�����ֵĴ���
			for(int j=0;j<pxarr[i];j++){
				result[index++]=i+min;
			}
		}
		return result;
	}

}
