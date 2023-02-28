package com.mashibing;

import com.mashibing.common.MonotoneStackUtils;

/**
 * 给定一个数组arr，返回所有子数组最小值的累加和
 * <p>
 * LeetCode测试链接：
 * https://leetcode.cn/problems/sum-of-subarray-minimums/description/
 *
 * @author xcy
 * @date 2022/5/19 - 8:12
 */
public class SumOfSubarrayMinimums {
	public static void main(String[] args) {
		int maxLen = 100;
		int maxValue = 50;
		int testTime = 1000000;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int len = (int) (Math.random() * maxLen);
			int[] arr = MonotoneStackUtils.randomArray(len, maxValue);
			int ans1 = sumSubarrayMinsWithViolenceSolution(arr);
			int ans2 = sumSubarrayMinsWithOptimalSolution(arr);
			int ans3 = sumSubarrayMinsWithOptimalSolutionOptimization(arr);
			if (ans1 != ans2 || ans1 != ans3) {
				MonotoneStackUtils.printArray(arr);
				System.out.println(ans1);
				System.out.println(ans2);
				System.out.println(ans3);
				System.out.println("出错了！");
				break;
			}
		}
		System.out.println("测试结束");
	}

	/**
	 * 使用暴力解
	 *
	 * @param arr
	 * @return 返回arr数组中所有子数组最小值的累加和
	 */
	public static int sumSubarrayMinsWithViolenceSolution(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}

		int ans = 0;
		for (int i = 0; i < arr.length; i++) {
			for (int j = i; j < arr.length; j++) {
				int min = arr[i];
				for (int k = i + 1; k <= j; k++) {
					min = Math.min(min, arr[k]);
				}
				ans += min;
			}
		}
		return ans;
	}

	/**
	 * 使用最优解
	 *
	 * @param arr
	 * @return 返回arr数组中所有子数组最小值的累加和
	 */
	public static int sumSubarrayMinsWithOptimalSolution(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		//left[i] = x，arr[i]左边，离arr[i]最近，并且 <= arr[i]
		int[] left = leftNearLessEquals(arr);
		//right[i] = y， arr[i]右边，离arr[i]最近，并且 < arr[i]
		int[] right = rightNearLess(arr);
		int ans = 0;
		for (int i = 0; i < arr.length; i++) {
			int start = i - left[i];
			int end = right[i] - i;
			ans += start * end * arr[i];
		}
		return ans;
	}

	/**
	 * @param arr
	 * @return
	 */
	public static int[] leftNearLessEquals(int[] arr) {
		if (arr == null || arr.length == 0) {
			return null;
		}
		int[] left = new int[arr.length];
		for (int i = 0; i < arr.length; i++) {
			int ans = -1;
			for (int j = i - 1; j >= 0; j--) {
				if (arr[j] <= arr[i]) {
					ans = j;
					break;
				}
			}
			left[i] = ans;
		}
		return left;
	}

	/**
	 * @param arr
	 * @return
	 */
	public static int[] rightNearLess(int[] arr) {
		if (arr == null || arr.length == 0) {
			return null;
		}
		int[] right = new int[arr.length];
		for (int i = 0; i < arr.length; i++) {
			int ans = arr.length;
			for (int j = i + 1; j < arr.length; j++) {
				if (arr[j] < arr[i]) {
					ans = j;
					break;
				}
			}
			right[i] = ans;
		}
		return right;
	}


	/**
	 * 使用最优解的优化
	 *
	 * @param arr
	 * @return
	 */
	public static int sumSubarrayMinsWithOptimalSolutionOptimization(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		int[] stack = new int[arr.length];
		//left[i] = x，arr[i]左边，离arr[i]最近，并且 <= arr[i]
		int[] left = leftNearLessEqualsWithArray(arr, stack);
		//right[i] = y， arr[i]右边，离arr[i]最近，并且 < arr[i]
		int[] right = rightNearLessWithArray(arr, stack);
		long ans = 0;
		for (int i = 0; i < arr.length; i++) {
			long start = i - left[i];
			long end = right[i] - i;
			ans += start * end * (long) arr[i];
			ans %= 1000000007;
		}
		return (int) ans;
	}

	/**
	 * @param arr   原始数组
	 * @param stack 使用Array表示单调栈
	 * @return
	 */
	public static int[] leftNearLessEqualsWithArray(int[] arr, int[] stack) {
		int[] left = new int[arr.length];
		int size = 0;
		for (int i = arr.length - 1; i >= 0; i--) {
			while (size != 0 && arr[stack[size - 1]] >= arr[i]) {
				left[stack[--size]] = i;
			}
			stack[size++] = i;
		}
		while (size != 0) {
			left[stack[--size]] = -1;
		}
		return left;
	}

	/**
	 * @param arr   原始数组
	 * @param stack 使用Array表示单调栈
	 * @return
	 */
	public static int[] rightNearLessWithArray(int[] arr, int[] stack) {
		int[] right = new int[arr.length];
		int size = 0;
		for (int i = 0; i < arr.length; i++) {
			while (size != 0 && arr[stack[size - 1]] > arr[i]) {
				right[stack[--size]] = i;
			}
			stack[size++] = i;
		}
		while (size != 0) {
			right[stack[--size]] = arr.length;
		}
		return right;
	}
}
