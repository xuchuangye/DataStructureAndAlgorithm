package com.mashibing.subject;

/**
 * @author xcy
 * @date 2021/9/13 - 19:36
 */

/**
 * —个数组中只有一种数出现了奇数次,其他数都出现了偶数次，怎么找到并打印这种数
 */
public class Question002 {
	public static void main(String[] args) {
		int[] arr = {1,2,4,6,3,5,7,2,4,7,7,5,4,1,3,3,3,2,2,7,6};
		printOddTimesNum(arr);
	}
	//arr中只有一种数，出现奇数次
	public static void printOddTimesNum(int[] arr){
		int eor = 0;
		for (int i = 0; i < arr.length; i++) {
			eor ^= arr[i];
		}
		System.out.println(eor);
	}

}
