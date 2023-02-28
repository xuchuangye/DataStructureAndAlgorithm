package com.mashibing.sort;

/**
 * @author xcy
 * @date 2021/9/8 - 19:13
 */

/**
 * 冒泡排序
 * 冒泡排序不受数据影响即使是最好的情况下，时间复杂度仍然是O(N²)
 */
public class BubbleSort {
	public static void bubbleSort(int[] arr) {
		//如果数组为空或者数组长度小于2，那么直接返回
		if (arr == null || arr.length < 2) {
			return;
		}
		//0 ~ N - 1
		//0 ~ N - 2
		//0 ~ N - 3
		for (int i = arr.length - 1; i > 0; i--) {// 0 ~ i

			//0 - 1
			//1 - 2
			//2 - 3
			//j - 2  j - 1
			for (int j = 0; j < i; j++) {
				if (arr[j] > arr[j + 1]) {
					swap(arr, j, j + 1);
				}
			}
		}
	}

	public static void swap(int[] arr, int i, int j) {
		arr[i] = arr[i] ^ arr[j];
		arr[j] = arr[i] ^ arr[j];
		arr[i] = arr[i] ^ arr[j];
	}

	public static void main(String[] args) {
		int[] arr = {1, 29, 49, 480, 288, 100, 30, 85, 478, 90};
		bubbleSort(arr);
		for (int i = 0; i < arr.length - 1; i++) {
			System.out.print(arr[i] + ",");
		}
	}
}
