package com.mashibing.day07;

import java.util.HashSet;

/**
 * 题目四：
 * 给定一个有序数组arr，其中值可能为正、负、0。返回arr中每个数都平方之后不同的结果有多少种？
 * <p>
 * 给定一个数组arr，先递减然后递增，返回arr中有多少个不同的数字？
 * <p>
 * 解题思路：
 *
 * @author xcy
 * @date 2022/7/23 - 7:51
 */
public class Code04_Power2Diffs {
	public static void main(String[] args) {
		//int[] arr = {-8, -6, -5, -3,  0,  4,  5,  6,  7,  9};
		//int[] arr = {-8, -8, -7, -7, -7,  0,  6,  7,  7,  7,  9,  9,  9};

		int[] arr = {-8, -8, -8, -7, -6, 0, 6, 8, 8, 8, 9};
		int count1 = comparator(arr);
		int count2 = returnHowManyCountMethod(arr);
		System.out.println(count1);
		System.out.println(count2);
	}

	/**
	 * 时间复杂度：O(N)，额外空间复杂度：O(N)
	 *
	 * @param arr
	 * @return
	 */
	public static int comparator(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		HashSet<Integer> set = new HashSet<>();
		for (int value : arr) {
			set.add(value * value);
		}
		return set.size();
	}

	/**
	 * 时间复杂度：O(N)，额外空间复杂度：O(1)
	 *
	 * @param arr
	 * @return
	 */
	public static int returnHowManyCountMethod(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		for (int i = 0; i < arr.length; i++) {
			arr[i] = Math.abs(arr[i]);
		}
		//System.out.println(Arrays.toString(arr));
		int L = 0;
		int R = arr.length - 1;
		int count = 0;

		int left = 0;
		int right = 0;

		while (L <= R) {
			left = arr[L];
			right = arr[R];
			if (left > right) {
				while (L < arr.length && arr[L] == left) {
					L++;
				}
				count++;
			} else if (left < right) {
				while (R >= 0 && arr[R] == right) {
					R--;
				}
				count++;
			} else {
				while (L < arr.length && arr[L] == left) {
					L++;
				}
				while (R >= 0 && arr[R] == right) {
					R--;
				}
				count++;
			}
		}
		return count;
	}
}
