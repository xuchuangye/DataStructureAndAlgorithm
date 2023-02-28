package com.mashibing.day02;

import com.mashibing.common.TestUtils;

/**
 * 题目六：
 * 给定一个数组arr，只能对arr中的一个子数组排序，但是想让arr整体都有序，返回满足这一设定的子数组中最短的是多长
 *
 * 解题思路：
 * 1.从前往后遍历，获取到修改最短长度的右边界
 * 2.从后往前遍历，获取到修改最短长度的左边界
 * 3、左边界 - 右边界 + 1 = 整个数组修改之后有序的最短长度
 *
 * @author xcy
 * @date 2022/7/11 - 9:11
 */
public class Code06_MinLengthForSort {
	public static void main(String[] args) {

		int valueMax = 1000;
		int length = 100;
		int testTime = 1000;
		System.out.println("测试开始！");
		for (int i = 0; i < testTime; i++) {
			int[] arr = TestUtils.randomArray(length, valueMax);
			int length1 = getMinLength1(arr);
			int length2 = getMinLength2(arr);
			if (length1 != length2) {
				System.err.println("测试出错！");
				break;
			}
		}
		System.out.println("测试结束！");
	}

	/**
	 * 老师写的代码
	 * @param arr
	 * @return
	 */
	public static int getMinLength1(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}

		int rightMin = arr[arr.length - 1];
		int mostMinIndex = -1;
		for (int i = arr.length - 2; i != -1; i--) {
			if (arr[i] > rightMin) {
				mostMinIndex = i;
			} else {
				rightMin = Math.min(rightMin, arr[i]);
			}
		}

		if (mostMinIndex == -1) {
			return -1;
		}

		int leftMax = arr[0];
		int mostLeftIndex = -1;
		for (int i = 1; i != arr.length; i++) {
			if (arr[i] < leftMax) {
				mostLeftIndex = i;
			} else {
				leftMax = Math.max(leftMax, arr[i]);
			}
		}
		return mostLeftIndex - mostMinIndex + 1;
	}

	/**
	 * 自己写的代码
	 * @param arr
	 * @return
	 */
	public static int getMinLength2(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		//默认右边的最小值是数组中最后一个元素
		int rightMin = arr[arr.length - 1];
		//最右侧的边界
		int mostRightIndex = -1;
		//arr[] = {1, 2, 7, 6, 5, 4, 3, 8, 9}
		//index =  0  1  2  3  4  5  6  7  8
		//逆序遍历
		//                                 rightMin
		//rightMin = 9, arr[i] = 8, arr[i] < rightMin
		//rightMin = 8
		//rightMin = 8, arr[i] = 3, arr[i] < rightMin
		//rightMin = 3
		//rightMin = 3, arr[i] = 4, arr[i] > rightMin
		//rightMin不变,mostMinIndex = 5的位置
		for (int i = arr.length - 2; i >= 0; i--) {
			if (arr[i] > rightMin) {
				mostRightIndex = i;
			} else {
				rightMin = arr[i];
			}
		}

		//如果mostMinIndex从始至终都没有改变过，表示这个数组本身就是有序的，那么需要修改之后变为有序数组的长度为0
		if (mostRightIndex == -1) {
			return 0;
		}
		//默认左边的最大值是数组中第一个元素
		int leftMax = arr[0];
		//最左侧的边界
		int mostLeftIndex = -1;
		//arr[] = {1, 2, 7, 6, 5, 4, 3, 8, 9}
		//index =  0  1  2  3  4  5  6  7  8
		//顺序遍历
		//leftMax
		//leftMax = 1, arr[i] = 2, arr[i] > leftMax
		//leftMax = 2
		//leftMax = 2, arr[i] = 7, arr[i] > leftMax
		//leftMax = 7
		//leftMax = 7, arr[i] = 6, arr[i] < leftMax
		//leftMax不变,mostLeftIndex = 3的位置
		for (int i = 1; i < arr.length; i++) {
			if (arr[i] < leftMax) {
				mostLeftIndex = i;
			} else {
				leftMax = arr[i];
			}
		}
		//最左的边界 - 最右的边界 + 1
		return mostLeftIndex - mostRightIndex + 1;
	}
}
