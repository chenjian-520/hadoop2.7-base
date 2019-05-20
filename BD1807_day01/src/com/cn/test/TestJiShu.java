package com.cn.test;

import java.util.Arrays;
//计数排序  
//原理：要排序的数组存到另一个数组的下标里  用另一个数组的值记录值出现的个数  然后顺序打印另一个数组
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
