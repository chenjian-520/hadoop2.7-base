package com.cn.paixu;

import java.util.Arrays;

public class MaoPao {
	//ð��  ʱ�临�Ӷ�Ϊ 2��n�η�
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
