package com.cn.paixu;

public class ErFenFa {
	 public static void main(String[] args) { 
         int srcArray[] = {3,5,11,17,21,23,28,30,32,50,64,78,81,95,101};   
         System.out.println(binSearch(srcArray, 0, srcArray.length - 1, 81));  
     } 

     // ���ֲ��ҵݹ�ʵ��   
     public static int binSearch(int srcArray[], int start, int end, int key) {   
         int mid = (end - start) / 2 + start;   
         if (srcArray[mid] == key) {   
             return mid;   
         }   
         if (start >= end) {   
             return -1;   
         } else if (key > srcArray[mid]) {   
             return binSearch(srcArray, mid + 1, end, key);   
         } else if (key < srcArray[mid]) {   
             return binSearch(srcArray, start, mid - 1, key);   
         }   
         return -1;   
     } 

     // ���ֲ�����ͨѭ��ʵ��   
     public static int binSearch(int srcArray[], int key) {   
    	 int mid = srcArray.length/2;
    	 if(srcArray[mid]==key){
    		 return mid;
    	 }
    	 int start = 0;
    	 int end = srcArray.length-1;
    	 while(start<=end){
    		 mid = (end - start) / 2 + start;
    		 if(srcArray[mid]<key){
    			 start=mid+1;
    		 }else if(srcArray[mid]>key){
    			 end = mid-1;
    		 }else if(srcArray[mid]==key){
    			 return mid;
    		 }
    	 }	 
    	 return -1;
     } 
}
