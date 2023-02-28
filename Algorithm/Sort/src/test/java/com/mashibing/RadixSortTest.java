package com.mashibing;

import java.util.Arrays;

/**
 * @author xcy
 * @date 2022/3/22 - 10:49
 */
public class RadixSortTest {
	public static void main(String[] args) {

		/*int[] arr = {53, 3, 542, 748, 14, 214};

		int[][] bucket = new int[10][arr.length];
		int[] bucketNumberCounts = new int[10];

		int max = arr[0];
		for (int i = 0; i < arr.length; i++) {
			if (max < arr[i]) {
				max = arr[i];
			}
		}
		int maxLength = (max + "").length();

		for (int i = 0, n = 1; i < maxLength; i++, n *= 10) {
			for (int j = 0; j < arr.length; j++) {
				int digitOfNumber = arr[j] / n % 10;
				bucket[digitOfNumber][bucketNumberCounts[digitOfNumber]] = arr[j];
				bucketNumberCounts[digitOfNumber]++;
			}

			int index = 0;
			for (int k = 0; k < bucketNumberCounts.length; k++) {

				if (bucketNumberCounts[k] != 0) {
					for (int l = 0; l < bucketNumberCounts[k]; l++) {
						arr[index++] = bucket[k][l];
					}
				}

				bucketNumberCounts[k] = 0;
			}

			System.out.println("第" + (i + 1) + "次排序之后的数组：" + Arrays.toString(arr));
		}*/

		int a= 9;
		System.out.println(~a + 1);
	}
}
