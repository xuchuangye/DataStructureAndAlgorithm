package com.mashibing;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * @author xcy
 * @date 2022/2/20 - 21:49
 */
/*
二分法
 */
public class DichotomyTest {

	public static boolean testNumIsExists(int[] arr, int num) {
		if (arr == null || arr.length == 0) {
			return false;
		}
		sortedArray(arr);

		int L = 0;
		int R = arr.length - 1;
		int mid = 0;

		while (L < R) {
			mid = L + (R - L) >> 1;
			if (arr[mid] == num) {
				return true;
			}
			if (arr[mid] > num) {
				R = mid - 1;
			} else {
				L = mid + 1;
			}
		}
		return arr[L] == num;
	}

	private static void sortedArray(int[] arr) {
		Arrays.sort(arr);
	}


}
