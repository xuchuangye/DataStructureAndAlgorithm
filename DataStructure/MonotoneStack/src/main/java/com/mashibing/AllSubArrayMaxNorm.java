package com.mashibing;

import com.mashibing.common.MonotoneStackUtils;

import java.util.Stack;

/**
 * 给定一个只包含正数的数组arr，arr中任何一个子数组sub，
 * 一定可以算出(sub的累加和) * (sub的最小值)的指标是什么，返回所有子数组中最大的指标
 *
 * @author xcy
 * @date 2022/5/16 - 17:01
 */
public class AllSubArrayMaxNorm {
	public static void main(String[] args) {
		int testTimes = 2000000;
		System.out.println("测试开始！");
		for (int i = 0; i < testTimes; i++) {
			int[] arr = MonotoneStackUtils.generateRandomArray();
			if (returnAllSubArrayMaxNorm(arr) != returnAllSubArrayMaxNormWithMonotoneStack(arr)) {
				System.err.println("测试错误！");
				break;
			}
		}
		System.out.println("测试结束！");
	}

	/**
	 * 时间复杂度：O(N³)
	 * @param arr 原始数组
	 * @return 返回在arr数组所有子数组中的累加和 * 最小值的指标中最大的指标
	 */
	public static int returnAllSubArrayMaxNorm(int[] arr) {
		int max = Integer.MIN_VALUE;
		for (int i = 0; i < arr.length; i++) {
			for (int j = i; j < arr.length; j++) {
				int minNum = Integer.MAX_VALUE;
				int sum = 0;
				for (int k = i; k <= j; k++) {
					sum += arr[k];
					minNum = Math.min(minNum, arr[k]);
				}
				max = Math.max(max, minNum * sum);
			}
		}
		return max;
	}

	/**
	 * 使用单调栈结构
	 *
	 * @param arr 原始数组
	 * @return 返回在arr数组所有子数组中的累加和 * 最小值的指标中最大的指标
	 */
	public static int returnAllSubArrayMaxNormWithMonotoneStack(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		//创建累加和数组
		int[] sums = new int[arr.length];
		sums[0] = arr[0];
		for (int i = 1; i < arr.length; i++) {
			sums[i] = sums[i - 1] + arr[i];
		}
		//最大指标
		int max = Integer.MIN_VALUE;
		Stack<Integer> stack = new Stack<>();
		for (int i = 0; i < arr.length; i++) {
			//判断栈不为空并且arr[栈顶元素] 大于等于 arr[i]
			while (!stack.isEmpty() && arr[stack.peek()] >= arr[i]) {
				//举例：
				//arr = {3, 2, 4, 3, 2, 5}
				//       0  1  2  3  4  5
				//栈中元素
				//索引   值             准备入栈的元素
				//[2 -> 4]               [3 -> 3]
				//[1 -> 2]
				//此时弹出栈顶元素 -> [2 -> 4]，所以 popIndex == 2
				Integer popIndex = stack.pop();
				//判断栈为空，如果为空 sums[i - 1] * arr[popIndex] -->
				//sums = {3, 5, 9, 12, 14, 19}
				//        0  1  2  3   4   5
				//当前i == 3
				//如果不为空，sums[i - 1] - sums[stack.peek()] * arr[popIndex] --> (9 - 5) * 4 == 16
				max = Math.max(max, (stack.isEmpty() ? sums[i - 1] : sums[i - 1] - sums[stack.peek()]) * arr[popIndex]);
			}
			stack.push(i);
		}
		while (!stack.isEmpty()) {
			//取出栈顶元素
			Integer popIndex = stack.pop();
			max = Math.max(max, (stack.isEmpty() ? sums[arr.length - 1] : sums[arr.length - 1] - sums[stack.peek()]) * arr[popIndex]);
		}

		return max;
	}
}
