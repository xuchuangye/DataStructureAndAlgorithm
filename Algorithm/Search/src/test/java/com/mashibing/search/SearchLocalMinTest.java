package com.mashibing.search;

/**
 * @author xcy
 * @date 2022/4/13 - 15:51
 */
public class SearchLocalMinTest {
	public static void main(String[] args) {
		int[] arr = {100, 10, 10, 10, 15, 20, 20, 30, 45, 50, 78, 100};

		int index = searchLocalMin(arr);
		System.out.println("局部最小值的索引是：" + index);
	}

	public static int searchLocalMin(int[] arr) {
		if (arr == null || arr.length == 0) {
			return -1;
		}
		if (arr.length == 1 || arr[0] < arr[1]) {
			return 0;
		}

		if (arr[arr.length - 1] < arr[arr.length - 2]) {
			return arr.length - 1;
		}

		int left = 1;
		int right = arr.length - 2;
		int mid = 0;
		while (left < right) {
			mid = left + (right - left) / 2;
			if (arr[mid] > arr[mid - 1]) {
				right = mid - 1;
			} else if (arr[mid] > arr[mid + 1]) {
				left = mid + 1;
			} else {
				return mid;
			}
		}
		return left;
	}
}
