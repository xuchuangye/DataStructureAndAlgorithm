package com.mashibing;

/**
 * 异或运算
 * 题目一：不使用任何额外的变量交换两个数
 * @author xcy
 * @date 2022/3/11 - 16:39
 */
public class InterviewQuestions001 {
	public static void main(String[] args) {
		/*int a = 7;
		int b = -1000;
		a = a ^ b;
		b = a ^ b;
		a = a ^ b;
		System.out.println(a);
		System.out.println(b);*/

		int[] arr = {3, 1, 100};
		System.out.println(arr[0]);
		System.out.println(arr[2]);

		swap(arr, 0, 2);
		System.out.println(arr[0]);
		System.out.println(arr[2]);
	}

	public static void swap(int[] arr, int i, int j) {
		arr[i] = arr[i] ^ arr[j];
		arr[j] = arr[i] ^ arr[j];
		arr[i] = arr[i] ^ arr[j];
	}
}
