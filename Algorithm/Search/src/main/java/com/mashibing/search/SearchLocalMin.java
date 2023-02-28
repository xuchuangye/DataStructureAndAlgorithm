package com.mashibing.search;

/**
 * 在数组中查找局部最小值
 *
 * @author xcy
 * @date 2022/4/6 - 15:48
 */
public class SearchLocalMin {
	public static void main(String[] args) {
		int[] arr = {100, 10, 10, 10, 15, 20, 20, 30, 45, 50, 78, 100};

		int index = searchLocalMin(arr);
		System.out.println("局部最小值的索引是：" + index);
	}

	/**
	 * 在数组中查找到局部最小值
	 *
	 * @param arr 数组
	 * @return
	 */
	public static int searchLocalMin(int[] arr) {
		//数组为空或者长度为0，直接 返回-1
		if (arr == null || arr.length == 0) {
			return -1;
		}
		//数组长度为1或者长度大于1并且数组索引0上的值小于索引1上的数，返回0
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
			mid = left + ((right - left) >> 1);
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
