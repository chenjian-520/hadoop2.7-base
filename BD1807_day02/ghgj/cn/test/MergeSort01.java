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
		//�������ս��������
		int[] newarr=new int[arr1.length+arr2.length];
		//���������±�   
		int m=0;
		int n=0;
		int index=0;
		while(m<arr1.length&&n<arr2.length){
			//�����ж�  ������������
			//arr1 �е�Ԫ��  С��  arr2�е�Ԫ��
			if(arr1[m]<=arr2[n]){
				newarr[index++]=arr1[m++];
				/*index++;
				m++;*/
			}else{//arr1   >  arr2
				newarr[index++]=arr2[n++];
			}
		}
		//�ֿ���ʣ�µ���arr1
		/*for(int i=m;i<arr1.length;i++){
			newarr[index++]=arr1[i];
		}*/
		while(m<arr1.length){
			newarr[index++]=arr1[m++];
		}
		
		//ʣ�µ���arr2
		while(n<arr2.length){
			newarr[index++]=arr2[n++];
		}
		
		return newarr;
	}
}
