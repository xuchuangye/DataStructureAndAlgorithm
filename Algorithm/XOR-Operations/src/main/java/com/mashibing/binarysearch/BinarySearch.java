package com.mashibing.binarysearch;

import com.mashibing.common.SortCommonUtils;

import java.util.Arrays;

/**
 * @author xcy
 * @date 2022/4/8 - 9:11
 */
public class BinarySearch {
	public static void main(String[] args) {
		/*int testTime = 1000000;
		int length = 10;
		int value = 100;
		boolean isSuccess = true;
		for (int i = 0; i < testTime; i++) {
			//生成随机长度并且值也随机的数组
			int[] arr = SortCommonUtils.generateRandomArray(length, value);
			//排序
			Arrays.sort(arr);
			int number = (int) (Math.random() * (value + 1)) - (int) (Math.random() * (value + 1));

			if (SortCommonUtils.greaterValueLeftIndex(arr, number) != binarySearchGreaterValueLeft(arr, number)) {
				SortCommonUtils.printArray(arr);
				System.out.println(value);
				System.out.println(SortCommonUtils.greaterValueLeftIndex(arr, number));
				System.out.println(binarySearchGreaterValueLeft(arr, number));
				isSuccess = false;
				break;
			}
		}
		System.out.println(isSuccess ? "Nice!" : "测试失败");*/

		System.out.println("测试开始");
		int testTime = 1000000;
		int length = 100;
		int value = 100;
		for (int i = 0; i < testTime; i++) {
			int[] arr = SortCommonUtils.randomArray(length, value);
			int ans = binarySearchLocalMin(arr);

			if (!SortCommonUtils.check(arr, ans)) {
				SortCommonUtils.printArray(arr);
				System.out.println(ans);
				break;
			}

		}
		System.out.println("测试结束");
	}

	/**
	 * 二分查找算法在数组中查找指定值的索引
	 *
	 * @param arr
	 * @param target
	 * @return
	 */
	public static int binarySearch(int[] arr, int target) {
		if (arr == null || arr.length == 0) {
			return -1;
		}
		int left = 0;
		int right = arr.length - 1;

		while (left <= right) {
			//int mid = left + ((right - left) / 2);
			int mid = left + ((right - left) >> 1);
			if (target < arr[mid]) {
				right = mid - 1;
			} else if (target > arr[mid]) {
				left = mid + 1;
			} else {
				return mid;
			}
		}
		return -1;
	}

	/**
	 * 二分查找算法在数组中查找大于指定值的最左侧位置
	 *
	 * @return
	 */
	public static int binarySearchGreaterValueLeft(int[] arr, int target) {
		if (arr == null || arr.length == 0) {
			return -1;
		}
		int left = 0;
		int right = arr.length - 1;
		int index = -1;
		while (left <= right) {
			int mid = left + ((right - left) >> 1);
			if (target <= arr[mid]) {
				index = mid;
				right = mid - 1;
			} else {
				left = mid + 1;
			}
		}
		return index;
	}

	/**
	 * 二分查找算法在数组中查找局部最小值，并返回局部最小值的索引
	 * 1、整体无序
	 * 2、相邻的两个元素之间不相等
	 *
	 * @param arr
	 * @return
	 */
	public static int binarySearchLocalMin(int[] arr) {
		if (arr == null || arr.length == 0) {
			return -1;
		}
		if (arr.length == 1) {
			return 0;
		}
		if (arr.length == 2 && arr[0] < arr[1]) {
			return 0;
		}
		if (arr[arr.length - 1] < arr[arr.length - 2]) {
			return arr.length - 1;
		}
		int left = 0;
		int right = arr.length - 1;
		while (left <= right) {
			int mid = left + ((right - left) >> 1);
			//防止越界
			if (mid - 1 < 0) {
				break;
			}
			if (arr[mid] < arr[mid + 1] && arr[mid] < arr[mid - 1]) {
				return mid;
			} else {
				if (arr[mid] > arr[mid - 1]) {
					right = mid - 1;
				} else {
					left = mid + 1;
				}
			}
		}
		return arr[left] < arr[right] ? left : right;
	}
}
