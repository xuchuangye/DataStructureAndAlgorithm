package com.mashibing.test;

/**
 * @author xcy
 * @date 2022/4/14 - 8:49
 */
public class XOPTest {
	public static void main(String[] args) {
		int[] arr = {1, 2, 7, 7, 7, 8, 2, 9, 9, 8};
		searchTwoCountValue(arr);
	}

	public static void searchTwoCountValue(int[] arr) {
		//eor = a ^ b
		int eor = 0;
		for (int element : arr) {
			eor ^= element;
		}

		//取出最右侧的1
		int right_1 = eor & (~eor + 1);
		int eor_ = 0;

		for (int element : arr) {
			if ((right_1 & element) == 0) {
				eor_ ^= element;
			}
		}

		System.out.println(eor_ + ", " + (eor_ ^ eor));
	}
}
