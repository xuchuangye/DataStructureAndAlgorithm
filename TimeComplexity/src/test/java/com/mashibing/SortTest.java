package com.mashibing;

import org.junit.jupiter.api.Test;

/**
 * @author xcy
 * @date 2022/2/19 - 21:18
 */
public class SortTest {
	@Test
	public void testInsertSort() {
		//插入排序
		/*int[] arr = {345, 587, 328, 459, 60, 2394, 39, 3020};
		if (arr != null || arr.length >= 2) {
			//0 ~ 0已经有序，不需要比较
			//从索引1位置开始，到N - 1结束
			for (int i = 1; i <= arr.length - 1; i++) {
				for (int j = i - 1; j >= 0 && arr[j] > arr[j + 1]; j--) {
					swap(arr, j, j + 1);
				}
			}
			for (int i : arr) {
				System.out.println(i);
			}
		}*/

		//冒泡排序
		int[] array = {345, 5, 87, 328, 4, 59, 60, 23, 94, 39, 30, 20};
		if (array != null || array.length >= 2) {
			for (int i = 0; i <= array.length - 1; i++) {
				for (int j = array.length - 1; j > i; j--) {
					if (array[j - 1] > array[j]) {
						//swap(array, j - 1 , j); == swap(array, j, j - 1);
						swap(array, j, j - 1);
					}
				}
			}
		}
		for (int i : array) {
			System.out.println("i = " + i);
		}
	}

	public void swap(int[] arr, int i, int j) {
		arr[i] = arr[i] ^ arr[j];
		arr[j] = arr[i] ^ arr[j];
		arr[i] = arr[i] ^ arr[j];
	}
}
