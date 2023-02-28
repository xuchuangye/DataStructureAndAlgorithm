package com.mashibing.sort;

import java.util.Arrays;

/**
 * 计数排序
 *
 * 注意事项：
 * 1.计数排序有限制：必须是整数，并且范围比较窄
 * @author xcy
 * @date 2022/4/23 - 10:39
 */
public class CountSorting {
	public static void main(String[] args) {
		int[] arr = new int[]{2,5,9,8,7};
		countSort(arr);
		System.out.println(Arrays.toString(arr));
	}

	public static void countSort(int[] arr) {
		if (arr == null || arr.length < 2) {
			return;
		}

		int max = Integer.MIN_VALUE;
		for (int j : arr) {
			max = Math.max(max, j);
		}

		int[] bucket = new int[max + 1];
		for (int j : arr) {
			bucket[j]++;
		}

		int index = 0;
		for (int i = 0; i < bucket.length; i++) {
			while (bucket[i]-- > 0) {
				arr[index++] = i;
			}
		}
	}
}
