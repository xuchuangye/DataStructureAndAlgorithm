package com.mashibing.subject;

/**
 * @author xcy
 * @date 2021/9/13 - 19:35
 */
/**
 * 题目一：如何不用额外的变量交换两个数的值
 */
public class Question001 {
	public static void main(String[] args) {
		int a = 10;
		int b = 20;
		a  = a ^ b;
		b =  b ^ a;
		a =  a ^ b;
		System.out.println(a);
		System.out.println(b);



		int[] arr = {101,6,100};
		System.out.println(arr[0]);
		System.out.println(arr[2]);
		arr[0] = arr[0] ^ arr[2];
		arr[2] = arr[2] ^ arr[0];
		arr[0] = arr[0] ^ arr[2];
		System.out.println(arr[0]);
		System.out.println(arr[2]);
	}
}
