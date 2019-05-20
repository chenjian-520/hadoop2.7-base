package com.cn.paixu;

import java.util.Arrays;

public class MaoPao {
	//冒泡  时间复杂度为 2的n次方
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] arr = new int[]{1,5,3,8,6,7,3,46,84,22,5,2};
		for(int i=0;i<arr.length-1;i++){
			for(int j=0;j<arr.length-i-1;j++){
				if(arr[j]>arr[j+1]){
					arr[j]=arr[j]^arr[j+1];
					arr[j+1]=arr[j]^arr[j+1];
					arr[j]=arr[j]^arr[j+1];
				}
			}
		}
		System.out.println(Arrays.toString(arr));
	}

}
