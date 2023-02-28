package com.mashibing.day16;

import com.mashibing.common.TestUtils;

import java.util.HashSet;

/**
 * 给定一个有正、有负、有0的数组arr，
 * 给定一个整数k，
 * 返回arr的子集是否能累加出k
 * 1）正常怎么做？
 * 解题思路：
 * 最简单的背包问题
 * 2）如果arr中的数值很大，但是arr的长度不大，怎么做？
 *
 * @author xcy
 * @date 2022/8/5 - 14:31
 */
public class Code01_IsSum {
	public static void main(String[] args) {
		int len = 40;
		int maxValue = 100;
		int testTimes = 100;
		System.out.println("测试开始！");
		for (int i = 0; i < testTimes; i++) {
			int[] arr = TestUtils.randomArray(len, maxValue);
			boolean isSum1 = subArrayCanGetSumK(arr, 1000);
			boolean isSum2 = subArrayCanGetSum(arr, 1000);
			if (isSum1 != isSum2) {
				System.out.println("测试失败！");
				break;
			}
		}
		System.out.println("测试结束！");
		//System.out.println(Math.pow(2, 20));
		//System.out.println(1 << 20);
	}

	/**
	 * -   -4  -3  -2  -1  0   j -> 累加和，范围min <= j <= max
	 * -0
	 * -1
	 * -2
	 * -3
	 * i -> 索引
	 * dp[i][j]
	 *
	 * @param arr arr[]的长度比较长，但是arr[]的元素值比较小
	 * @param sum 累加和
	 * @return 返回arr的子集是否能组成累加和sum
	 */
	public static boolean subArrayCanGetSumK(int[] arr, int sum) {
		if (sum == 0) {
			return true;
		}
		if (arr == null || arr.length == 0) {
			return false;
		}
		int N = arr.length;
		//数组中所有的负数累加和
		int min = 0;
		//数组中所有的正数累加和
		int max = 0;
		for (int num : arr) {
			min += Math.min(num, 0);
			max += Math.max(num, 0);
		}
		//超出最小值或者超出最大值，没有办法组成累加和sum
		if (sum < min || sum > max) {
			return false;
		}
		//dp[i][j]
		//-  0  1  2  3  4  5  6  7(实际上)
		//- -7 -6 -5 -4 -3 -2 -1  0(想象中)
		boolean[][] dp = new boolean[N][max - min + 1];
		//dp[0][0] = true -> dp[0][-min] -> dp[0][-(-7)] == true
		dp[0][-min] = true;
		//dp[0][arr[0]] -> dp[0][arr[0] - min] == true
		dp[0][arr[0] - min] = true;
		for (int i = 1; i < N; i++) {
			for (int j = min; j <= max; j++) {
				//dp[i][j] = dp[i - 1][j]
				dp[i][j - min] = dp[i - 1][j - min];
				//dp[i][j] |= dp[i][j - arr[i]]
				if (j - min - arr[i] >= 0 && i - min - arr[0] <= max - min) {
					dp[i][j - min] |= dp[i][j - min - arr[i]];
				}
			}
		}
		return dp[N - 1][sum - min];
	}

	/**
	 * 使用分治思想
	 *
	 * @param arr arr[]的长度比较小，arr[]的元素值比较大，此时再创建dp[][]就不合适了
	 * @param sum 累加和
	 * @return 返回arr的子集是否能组成累加和sum
	 */
	public static boolean subArrayCanGetSum(int[] arr, int sum) {
		if (sum == 0) {
			return true;
		}

		if (arr == null || arr.length == 0) {
			return false;
		}

		int N = arr.length;
		int mid = N >> 1;
		HashSet<Integer> leftSum = new HashSet<>();
		HashSet<Integer> rightSum = new HashSet<>();
		//0 ~ mid - 1,mid是终止位置
		process(arr, 0, mid, 0, leftSum);
		//mid ~ N - 1,N是终止位置
		process(arr, mid, N, 0, rightSum);
		//单独查看，左边的集合能否组成累加和sum
		/*if (left.contains(sum)) {
			return true;
		}*/
		//单独查看，右边的集合能否组成累加和sum
		/*if (right.contains(sum)) {
			return true;
		}*/
		//两个集合组合能否组成sum
		for (Integer l : leftSum) {
			//如果左边累加和能够组成sum，那么sum - l必定 == 0，而右边累加和也一定包含累加和为0的情况
			//如果右边累加和能够组成sum，那么sum - l必定 == 0，而左边累加和也一定包含累加和为0的情况
			if (rightSum.contains(sum - l)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param arr 原始数组
	 * @param i   起始位置
	 * @param end 终止位置
	 * @param pre arr[]0 ~ i - 1范围上的累加和
	 * @param set set集合
	 */
	public static void process(int[] arr, int i, int end, int pre, HashSet<Integer> set) {
		//如果i来到终止位置
		if (i == end) {
			//set集合将之前的累加和添加进去
			set.add(pre);
		} else {
			//i位置不参与累加
			process(arr, i + 1, end, pre, set);
			//i位置参与累加
			process(arr, i + 1, end, pre + arr[i], set);
		}
	}
}
