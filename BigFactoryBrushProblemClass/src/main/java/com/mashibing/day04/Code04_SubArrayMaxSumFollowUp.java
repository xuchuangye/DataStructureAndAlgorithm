package com.mashibing.day04;

import com.mashibing.common.TestUtils;

/**
 * 题目四：
 * 返回一个数组中，在所选数字不能相邻的情况下，返回最大子序列累加和
 * <p>
 * 解题思路：
 * 1.动态规划 -> 从左往右的尝试模型
 * 2.假设求0 ~ 17范围上的子序列最大累加和
 * (1).只选择17位置的数
 * (2).不选择17位置上的数，那么就需要求出0 ~ 16范围上的子序列的最大累加和
 * (3).选择17位置上的数，那么就需要求出0 ~ 15范围上的子序列的最大累加和
 * dp[i] = Math.max(dp[i - 1], dp[i - 2] + arr[i]);
 * (1).只选择i位置的数，dp[i] = arr[i]
 * (2).不选择i位置的数，dp[i] = dp[i - 1]
 * (3).选择i位置的数，又因为不能选择相邻的数，dp[i] = dp[i - 2] + arr[i]
 *
 * 定义dp[i] : 表示arr[0...i]范围上，在不能选择相邻数的情况下，返回所有组合中的最大累加和
 * 在arr[0...i]范围上，在不能选择相邻数的情况下，得到的最大累加和，可能性分为：
 * 可能性(1) 选出的组合，只包含arr[i]。那么dp[i] = arr[i]
 * 比如，arr[0...i] = {-3,-4,4}，最大累加和是只包含i位置数的时候
 * 可能性(2) 选出的组合，不包含arr[i]。那么dp[i] = dp[i - 1]
 * 比如，arr[0...i] = {3,4,-4}，最大累加和是不包含i位置数的时候
 * 可能性 3) 选出的组合，包含arr[i], 且包含arr[0...i - 2]范围上的累加和。那么dp[i] = arr[i] + dp[i - 2]
 * 比如，arr[0...i] = {3,1,4}，最大累加和是3和4组成的7，因为相邻不能选，所以i-1位置的数要跳过
 *
 * @author xcy
 * @date 2022/7/15 - 11:38
 */
public class Code04_SubArrayMaxSumFollowUp {
	public static void main(String[] args) {
		int n = 10;
		int valueMax = 100;
		int testTimes = 10;
		System.out.println("测试开始！");
		for (int i = 0; i < testTimes; i++) {
			int[] arr = TestUtils.randomArray(n, valueMax);
			int sum1 = maxSubArray(arr);
			int sum2 = maxSubArraySum(arr);
			if (sum1 != sum2) {
				System.out.println(sum1);
				System.out.println(sum2);
				break;
			}
		}
		System.out.println("测试结束！");
	}

	public static int maxSubArraySum(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}

		if (arr.length == 1) {
			return arr[0];
		}

		if (arr.length == 2) {
			return Math.max(arr[0], arr[1]);
		}

		int[] dp = new int[arr.length];
		dp[0] = arr[0];
		dp[1] = Math.max(arr[0], arr[1]);

		for (int i = 2; i < arr.length; i++) {
			dp[i] = Math.max(Math.max(dp[i - 1], arr[i]), Math.max(dp[i - 2], 0) + arr[i]);
		}
		return dp[arr.length - 1];
	}

	/**
	 *
	 * @param arr
	 * @return
	 */
	public static int maxSubArray(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		//数组长度为1时，直接返回arr[0]
		if (arr.length == 1) {
			return arr[0];
		}

		//数组长度为2时，返回arr[0]和arr[1]中的最大值，因为要求是不能选择相邻的数
		if (arr.length == 2) {
			return Math.max(arr[0], arr[1]);
		}

		int[] dp = new int[arr.length];
		dp[0] = arr[0];
		dp[1] = Math.max(arr[0], arr[1]);


		for (int i = 2; i < arr.length; i++) {
			//情况1：只选择i位置的数
			int situation1 = arr[i];
			//情况2：不选择i位置的数，依赖dp[i - 1]
			int situation2 = dp[i - 1];
			//情况3：选择i位置的数，依赖dp[i - 2]
			//既然选择了i位置的数，根据不能选择相邻的数，那么就依赖dp[i - 2]的结果
			int situation3 = Math.max(dp[i - 2], 0) + arr[i];
			dp[i] = Math.max(Math.max(situation1, situation2), situation3);
		}
		return dp[arr.length - 1];
	}
}
