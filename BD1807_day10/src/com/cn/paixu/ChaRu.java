package com.cn.paixu;

import java.util.Arrays;

public class ChaRu {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] arr = new int[]{1,5,3,8,6,7,3,46,84,22,5,651};
		for(int i=1;i<arr.length;i++){
			int temp=arr[i];
			int j=i-1;
			while(j>=0&&arr[j]>temp){
				arr[j+1]=arr[j];
				j--;
			}
			arr[j+1]=temp;
			
		}
		System.out.println(Arrays.toString(arr));
	}

}