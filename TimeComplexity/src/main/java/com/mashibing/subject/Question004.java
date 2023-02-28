package com.mashibing.subject;

/**
 * @author xcy
 * @date 2021/9/13 - 19:59
 */

/**
 * —个数组中有两种数出现了奇数次，其他数都出现了偶数次，怎么找到并打印这两种数
 */
public class Question004 {
	public static void main(String[] args) {
		int[] arr = {1, 2, 4, 6, 3, 5, 7, 2, 4, 7, 7, 5, 4, 1, 3, 3, 3, 2, 2, 7, 6, 1};
		printOddTimesNum(arr);
	}

	public static void printOddTimesNum(int[] arr) {
		int eor = 0;
		for (int k : arr) {
			eor ^= k;
		}

		//eor = a ^ b;
		//eor != 0
		//eor必然有一个位置上是1
		int maxRight = eor & (~eor + 1);//取出最右侧的1

		int onlyOne = 0;//eor`

		for (int j : arr) {
			if ((j & maxRight) != 0) {
				onlyOne ^= j;
			}
		}
		System.out.println(onlyOne + "" + (eor ^ onlyOne));
	}
}
