package com.cn.test;

import java.util.Arrays;
//��������  
//ԭ��Ҫ���������浽��һ��������±���  ����һ�������ֵ��¼ֵ���ֵĸ���  Ȼ��˳���ӡ��һ������
public class TestJiShu {
	public static void main(String[] args) {
		int[] arr = {1,5,3,5,7,29,6,9,15,2,1};
		int max=0;
		for(int i=0;i<arr.length;i++){
			if(max<arr[i]){
				max=arr[i];
			}
		}
		System.out.println(max);
		int[] arr1 = new int[max+1];
		System.out.println(Arrays.toString(arr1));
		for(int i=0;i<arr.length;i++){
			arr1[arr[i]]++;
		}
		System.out.println(Arrays.toString(arr1));
		for(int i=0;i<arr1.length;i++){
			for(int j=1;j<=arr1[i];j++){
				System.out.print(i+",");
				
			}
		}
	}
}
