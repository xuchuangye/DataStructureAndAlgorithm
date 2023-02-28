package com.mashibing.day22;

/**
 * 给定数组 nums 由正整数组成，找到三个互不重叠的子数组的最大和。每个子数组的长度为k，我们要使这3*k个项的和最大化。
 * 返回每个区间起始索引的列表（索引从 0 开始）。如果有多个结果，返回字典序最小的一个。
 * <p>
 * Leetcode题目：https://leetcode.com/problems/maximum-sum-of-3-non-overlapping-subarrays/
 * <p>
 * 解题思路：
 * 3个子数组必须连续，并且不能为空，并且不能重合
 * 1个数组中最大累加和问题
 *
 * @author xcy
 * @date 2022/8/15 - 8:19
 */
public class Code01_MaximumSumof3NonOverlappingSubarrays {
	public static void main(String[] args) {

	}

	/**
	 * arr[] = {3,-4,-5, 6,-3, 7,-8,-2};
	 * index =  0  1  2  3  4  5  6  7
	 * help[i]表示必须以i位置结尾，能够往左扩，并且范围内累加和最大
	 * 1)只选择i位置的数arr[i]
	 * 2)i位置往左扩，并且累加和最大help[i - 1] + arr[i]
	 * help[] ={3,-1,-5, 6, 3, 7,-1,-2}
	 * dp[i]表示在0 ~ 1范围内，选择一个子数组，并且子数组的累加和最大
	 * 1)选择i位置的值，在0 ~ 1范围内，选择一个子数组dp[i]
	 * 2)不选择i位置的值，在0 ~ i - 1范围内，选择一个子数组dp[i - 1]
	 * dp[] =  {3, 3, 3, 6, 6, 7, 7, 7}
	 * index =  0  1  2  3  4  5  6  7
	 *
	 * @param nums
	 * @param k
	 * @return
	 */
	public static int[] maxSumOfThreeSubarrays(int[] nums, int k) {
		if (nums == null || nums.length < 3 || k < 0) {
			return null;
		}
		int N = nums.length;
		int[] range = new int[N];
		int[] left = new int[N];
		int sum = 0;
		//计算0 ~ k - 1的累加和
		for (int i = 0; i < k; i++) {
			sum += nums[i];
		}
		range[0] = sum;
		left[k - 1] = 0;
		int max = sum;
		//计算0 ~ i范围
		for (int i = k; i < N; i++) {
			sum = sum - nums[i - k] + nums[i];
			range[i - k + 1] = sum;
			left[i] = left[i - 1];
			if (sum > max) {
				max = sum;
				left[i] = i - k + 1;
			}
		}
		sum = 0;
		for (int i = N - 1; i >= N - k; i--) {
			sum += nums[i];
		}
		max = sum;
		int[] right = new int[N];
		right[N - k] = N - k;
		//计算i - N - 1范围
		for (int i = N - k - 1; i >= 0; i--) {
			sum = sum - nums[i + k] + nums[i];
			right[i] = right[i + 1];
			if (sum >= max) {
				max = sum;
				right[i] = i;
			}
		}
		int a = 0;
		int b = 0;
		int c = 0;
		max = 0;
		//枚举中间位置
		//中间一块的起始点 (0...k-1)选不了 i == N-1
		for (int i = k; i < N - 2 * k + 1; i++) {
			int part1 = range[left[i - 1]];
			int part2 = range[i];
			int part3 = range[right[i + k]];
			if (part1 + part2 + part3 > max) {
				max = part1 + part2 + part3;
				a = left[i - 1];
				b = i;
				c = right[i + k];
			}
		}
		return new int[]{a, b, c};
	}
}
