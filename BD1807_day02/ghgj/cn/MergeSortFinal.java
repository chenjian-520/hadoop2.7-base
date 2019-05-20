package com.ghgj.cn;

import java.util.Arrays;

public class MergeSortFinal {
	public static void main(String[] args) {
		int[] arr={3,1,2,5,7,5,3,8,7,6,4,8,9,34,56,78,23,12,11};
		int[] newarr=new int[arr.length];
		chai(arr,0,arr.length-1,newarr);
		System.out.println(Arrays.toString(arr));
	}
	//拆分   递归
	public static void chai(int[] arr,int left,int right,int[] newarr){
		//出口
		if(left>=right){
			return;
		}else{
			//计算中间值
			int mid=(left+right)/2;
			//递归调用
			//左半部分
			chai(arr, left, mid,newarr);//边界
			chai(arr,mid+1,right,newarr);//这句代码执行完    整个拆的过程完成
			//并
			mergeResult(arr, left, right, mid, newarr);
		}
	}
	
	
	//并  merge文件归并
	public static void mergeResult(int[] arr,int left,int right,int mid,int[] newarr){
		//定义；两个变量      记录数据集的左侧的边界
		int m=left;
		int n=mid+1;
		//定义两个变量   记录每个数据集的右侧的边界
		int x=mid;
		int y=right;
		//定义一个变量  记录新数组的下标的
		int index=0;
		while(m<=x&&n<=y){
			if(arr[m]<arr[n]){
				newarr[index++]=arr[m++];
			}else{
				//arr[left]>=arr[mid+1]
				newarr[index++]=arr[n++];;
			}
		}
		while(m<=x){
			newarr[index++]=arr[m++];
		}
		while(n<=y){
			newarr[index++]=arr[n++];
		}
		for(int i=0;i<index;i++){
			arr[i+left]=newarr[i];
			
		}
		
	}

}
