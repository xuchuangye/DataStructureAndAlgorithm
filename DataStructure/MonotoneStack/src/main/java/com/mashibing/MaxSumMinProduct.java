package com.mashibing;

/**
 * 本题可以在leetcode上找到原题
 * 测试链接 : https://leetcode.com/problems/maximum-subarray-min-product/
 * 注意测试题目数量大，要取模，但是思路和课上讲的是完全一样的
 * 注意溢出的处理即可，也就是用long类型来表示累加和
 * 还有优化就是，你可以用自己手写的数组栈，来替代系统实现的栈，也会快很多
 *
 * 一个数组的 最小乘积定义为这个数组中 最小值乘以数组的和。
 * 比方说，数组[3,2,5]（最小值是2）的最小乘积为2 * (3+2+5) = 2 * 10 = 20。
 * 给你一个正整数数组nums，请你返回nums任意非空子数组的最小乘积的最大值。由于答案可能很大，请你返回答案对109 + 7取余的结果。
 *
 * 注意：
 * 最小乘积的最大值考虑的是取余操作之前的结果。题目保证最小乘积的最大值在不取余的情况下可以用64位有符号整数保存。
 * 子数组定义为一个数组的 连续部分。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/maximum-subarray-min-product
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author xcy
 * @date 2022/5/17 - 9:12
 */
public class MaxSumMinProduct {
	public static void main(String[] args) {
		int[] arr = {3, 2, 4, 2, 5};
		int maxSum1 = maxSumMinProduct(arr);
		int maxSum2 = maxSumMinProduct2(arr);
		System.out.println(maxSum1);
		System.out.println(maxSum2);
	}

	/**
	 *
	 * @param arr 原始数组
	 * @return 返回arr数组中任意非空子数组的最小乘积的最大值
	 */
	public static int maxSumMinProduct(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}

		long[] sums = new long[arr.length];
		sums[0] = arr[0];
		for (int i = 1; i < arr.length; i++) {
			sums[i] = sums[i - 1] + arr[i];
		}
		long max = Long.MIN_VALUE;
		//创建栈
		int[] stack = new int[arr.length];
		//栈的大小
		int stackSize = 0;
		for (int i = 0; i < arr.length; i++) {
			//判断栈不为空，并且栈顶元素大于等于arr[i]
			while (stackSize != 0 && arr[stack[stackSize - 1]] >= arr[i]) {
				//取出栈顶元素
				int popIndex = stack[--stackSize];
				//判断栈为空，如果为空
				max = Math.max(max,
						(stackSize == 0 ? sums[i - 1] : sums[i - 1] - sums[stack[stackSize - 1]]) * arr[popIndex]);
			}
			stack[stackSize++] = i;
		}

		while (stackSize != 0) {
			int popIndex = stack[--stackSize];
			//判断栈为空，如果为空
			max = Math.max(max,
					(stackSize == 0 ? sums[arr.length - 1] : sums[arr.length - 1] - sums[stack[stackSize - 1]]) * arr[popIndex]);
		}

		return (int) (max % 1000000007);
	}

	//for test
	public static int maxSumMinProduct2(int[] arr) {
		long[] sums = new long[arr.length];
		sums[0] = arr[0];
		for (int i = 1; i < arr.length; i++) {
			sums[i] = sums[i - 1] + arr[i];
		}
		long max = Long.MIN_VALUE;
		int[] stack = new int[arr.length];
		int stackSize = 0;
		for (int i = 0; i < arr.length; i++) {
			while (stackSize != 0 && arr[stack[stackSize - 1]] >= arr[i]) {
				int j = stack[--stackSize];
				max = Math.max(max,
						(stackSize == 0 ? sums[i - 1] : (sums[i - 1] - sums[stack[stackSize - 1]])) * arr[j]);
			}
			stack[stackSize++] = i;
		}
		while (stackSize != 0) {
			int j = stack[--stackSize];
			max = Math.max(max,
					(stackSize == 0 ? sums[arr.length - 1] : (sums[arr.length - 1] - sums[stack[stackSize - 1]])) * arr[j]);
		}
		return (int) (max % 1000000007);
	}
}
