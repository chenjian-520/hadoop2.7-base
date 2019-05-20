package com.ghgj.cn.test;

import java.util.Arrays;

public class MergeSort01 {
	public static void main(String[] args) {
		int[] arr1={2,3,4,5,6,7};
		int[] arr2={3,4,6,7,8,9,10,11};
		int[] res=bingSort(arr1, arr2);
		System.out.println(Arrays.toString(res));
	}
	

	public static int[] bingSort(int[] arr1,int[] arr2){
		//创建最终结果的数组
		int[] newarr=new int[arr1.length+arr2.length];
		//定义三个下标   
		int m=0;
		int n=0;
		int index=0;
		while(m<arr1.length&&n<arr2.length){
			//进行判断  放在新数组中
			//arr1 中的元素  小于  arr2中的元素
			if(arr1[m]<=arr2[n]){
				newarr[index++]=arr1[m++];
				/*index++;
				m++;*/
			}else{//arr1   >  arr2
				newarr[index++]=arr2[n++];
			}
		}
		//又可能剩下的是arr1
		/*for(int i=m;i<arr1.length;i++){
			newarr[index++]=arr1[i];
		}*/
		while(m<arr1.length){
			newarr[index++]=arr1[m++];
		}
		
		//剩下的是arr2
		while(n<arr2.length){
			newarr[index++]=arr2[n++];
		}
		
		return newarr;
	}
}
