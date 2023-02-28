package com.mashibing.TimeComplexity;

/**
 * @author xcy
 * @date 2021/9/10 - 21:02
 */

/**
 * 数组arr中所有的元素都无序，并且相邻的元素不相等，并返回局部最小的元素
 */
public class BSAwesome {
	public static int getLessIndex(int[] arr) {
		/*if (arr == null || arr.length == 0) {
			return -1; // 表示不存在
		}
		//返回最左侧局部最小元素
		if (arr.length == 1 || arr[0] < arr[1]) {
			return 0;
		}
		//返回最右侧局部最小元素
		if (arr[arr.length - 1] < arr[arr.length - 2]) {
			return arr.length - 1;
		}

		int left = 1;//索引0位置的元素已经排除
		int right = arr.length - 2;//索引arr.length - 1位置的元素已经 排除
		int mid = 0;
		while (left < right) {
			mid = left + (right - left >> 1);
			if (arr[mid] > arr[mid - 1]) {
				right = mid - 1;
			} else if (arr[mid] > arr[mid + 1]) {
				left = mid + 1;
			} else {
				return mid;
			}
		}
		return left;*/

		if (arr == null || arr.length == 0) {
			return -1;
		}

		if (arr.length == 1 || arr[0] < arr[1]) {
			return arr[0];
		}

		if (arr[arr.length - 1] < arr[arr.length - 2]) {
			return arr[arr.length - 1];
		}

		//索引0位置的数已经验证过了
		int left = 1;
		//索引arr.length - 1位置的数已经验证过了
		int right = arr.length - 2;
		int mid = 0;

		while (left < right) {
			mid = left + (right - left) >> 1;

			if (arr[mid] > arr[mid - 1]) {
				right = mid - 1;
			} else if (arr[mid] > arr[mid + 1]) {
				left = mid + 1;
			} else {
				return mid;
			}
		}

		//返回left和right一样，因为该方法结果求一个数，而循环最起码需要两个数
		return left;
	}

	public static void main(String[] args) {
		int[] arr = {31, 13, 31, 45, 8, 573, 238, 58, 29, 29, 39, 10, 100};
		int lessIndex = getLessIndex(arr);
		System.out.println("数组局部最小的元素是：" + lessIndex);
	}
}
