package com.cn.paixu;

import java.util.Arrays;

public class XuanZe {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] arr = new int[]{1,5,3,8,6,7,3,46,84,22,5,2};
		for(int i=0;i<arr.length-1;i++){
			for(int j=i+1;j<arr.length;j++){
				if(arr[i]>arr[j]){
					arr[i]=arr[i]^arr[j];
					arr[j]=arr[i]^arr[j];
					arr[i]=arr[i]^arr[j];
				}
			}
		}
		System.out.println(Arrays.toString(arr));
	}

}
