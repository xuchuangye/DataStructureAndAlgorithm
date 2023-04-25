package com.mashibing;

import com.mashibing.common.SortCommonUtils;

/**
 * @author xcy
 * @date 2023/3/23 - 19:04
 */
public class MergeSortTest2 {
	public static void main(String[] args) {
		int len = 100;
		int maxValue = 200;

		int time = 1000000000;
		for (int i = 0; i < time; i++) {

			int[] array = SortCommonUtils.generateRandomArray(len, maxValue);
			int[] arr = SortCommonUtils.copyArray(array);
			mergeSort1(array);
			SortCommonUtils.sort(arr);

			if (!SortCommonUtils.isEqual(arr, array)) {
				System.out.println("测试失败");
			}
		}
		//System.out.println(Arrays.toString(array));
	}


	public static void mergeSort(int[] arr) {
		if (arr == null || arr.length < 2) {
			return;
		}
		int l = 0;
		int r = arr.length - 1;
		process(arr, l, r);
	}

	public static void process(int[] arr, int l, int r) {
		if (l >= r) {
			return;
		}
		int m = l + ((r - l) >> 1);
		process(arr, l, m);
		process(arr, m + 1, r);
		merge(arr, l, m, r);
	}

	public static void merge(int[] arr, int l, int m, int r) {
		int[] help = new int[r - l + 1];

		int p1 = l;
		int p2 = m + 1;
		int index = 0;

		while (p1 <= m && p2 <= r) {
			help[index++] = arr[p1] <= arr[p2] ? arr[p1++] : arr[p2++];
		}

		while (p1 <= m) {
			help[index++] = arr[p1++];
		}

		while (p2 <= r) {
			help[index++] = arr[p2++];
		}

		for (int i = 0; i < help.length; i++) {
			arr[l + i] = help[i];
		}
	}


	public static void mergeSort1(int[] arr) {
		if (arr == null || arr.length < 2) {
			return;
		}
		int mergeSize = 1;
		int N = arr.length;

		while (mergeSize < N) {
			//左数组的左边界
			int L = 0;
			while (L < N) {
				//左数组的右边界
				int M = L + mergeSize - 1;

				//左数组的右边界已经越界
				if (M >= N) {
					break;
				}
				//有数组的右边界
				int R = Math.min(M + mergeSize, N - 1);

				merge(arr, L, M, R);

				L = R + 1;
			}
			if (mergeSize > N / 2) {
				break;
			}

			mergeSize <<= 1;
		}
	}
}
