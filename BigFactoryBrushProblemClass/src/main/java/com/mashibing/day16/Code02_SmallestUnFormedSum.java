package com.mashibing.day16;

import java.util.Arrays;
import java.util.HashSet;

/**
 * 给定一个正数数组arr，
 * 返回arr的子集不能累加出的最小正数
 * 1）正常怎么做？
 * 2）如果arr中肯定有1这个值，怎么做？
 *
 * @author xcy
 * @date 2022/8/5 - 14:31
 */
public class Code02_SmallestUnFormedSum {
	public static void main(String[] args) {

	}

	public static int unformedSum(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}

		int N = arr.length;
		int mid = N >> 1;

		HashSet<Integer> leftSum = new HashSet<>();
		process(arr, 0, mid, 0, leftSum);
		HashSet<Integer> rightSum = new HashSet<>();
		process(arr, mid, N, 0, rightSum);

		return 0;
	}

	public static void process(int[] arr, int i, int end, int pre, HashSet<Integer> set) {
		if (i == end) {
			set.add(pre);
		} else {
			process(arr, i + 1, end, pre, set);
			process(arr, i + 1, end, pre + arr[i], set);
		}
	}

	/**
	 * @param arr arr数组中必定有1这个元素值
	 * @return 返回arr[]中所有元素不能累加出的最小正数
	 */
	public static int unformedSum1(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		//对数组进行排序，时间复杂度：O(N * logN)
		Arrays.sort(arr);
		//表示能够组成的累加和范围，一开始是1 ~ 1，因为arr[0] == 1
		int range = 1;
		//arr[0] = 1
		for (int i = 1; i < arr.length; i++) {
			//假设range = 100，表示能够组成的累加和范围是1 ~ 100
			//如果此时的arr[i] > range + 1，那么range不能够组成，直接返回
			if (arr[i] > range + 1) {
				return range + 1;
			}
			//如果此时的arr[i] <= range + 1，那么range都能够组成，继续进行扩充
			else {
				range += arr[i];
			}
		}
		//如果遍历整个数组都没有range能够组成的累加和，那么返回range能够组成的累加和 + 1就是arr[]不能组成的最小正数
		return range + 1;
	}
}
