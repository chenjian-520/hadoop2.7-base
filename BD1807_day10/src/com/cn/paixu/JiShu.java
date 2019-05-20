package com.cn.paixu;

import java.util.Arrays;

public class JiShu {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] arr={1,4,7,3,2,4};
		int[] res=jishu(arr);
		System.out.println(Arrays.toString(res));

	}
	public static int[] jishu(int[] arr){
		int max=0;
		int min=0;
		for(int a:arr ){
			if(a>max){
				max=a;
			}if(min>a){
				min=a;
			}
		}
		int[] newarr = new int[max-min+1];
		for(int a :arr){
			newarr[a-min]++;
		}
		
		int index = 0;
		for(int i=0;i<newarr.length;i++){
			for(int j=0;j<newarr[i];j++){
				arr[index++]=i+min;
			}
		}
		return arr;
	}
	
}
