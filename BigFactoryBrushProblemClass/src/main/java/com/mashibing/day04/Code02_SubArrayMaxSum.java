package com.mashibing.day04;

import com.mashibing.common.TestUtils;

/**
 * 题目二：
 * 返回一个数组中子数组最大累加和
 * <p>
 * 解题思路：
 * 1.求出以每一个索引尽量往左扩的最大值
 * 举例：
 * arr[] = {-2,  3, -2,  4, -2, -1,  2}
 * index =   0   1   2   3   4   5   6
 * 0位置往左扩和不往左扩，最大累加和都是-2
 * 1位置往左扩，依赖0位置的最大累加和 -2，加上-2 == 1，不往左扩累加和3，最大累加和3
 * 2位置往左扩，依赖1位置的最大累加和  3，加上3 == 1，不往左扩累加和-2，最大累加和1
 * 3位置往左扩，依赖2位置的最大累加和  1，加上1 == 5，不往左扩累加和 4，最大累加和5
 * 4位置往左扩，依赖3位置的最大累加和  5，加上5 == 3，不往左扩累加和-2，最大累加和3
 * 5位置往左扩，依赖4位置的最大累加和  3，加上3 == 2，不往左扩累加和-1，最大累加和2
 * 6位置往左扩，依赖5位置的最大累加和  2，加上2 == 4，不往左扩累加和 2，最大累加和4
 * dp[] = {-2,  3,  1,  5,  3,  2,  4}
 * 所以dp[i + 1]依赖dp[i]的值，所以使用一个变量即可，不需要申请数组
 * <p>
 * LeetCode测试链接：
 * https://leetcode.com/problems/maximum-subarray/
 *
 * @author xcy
 * @date 2022/7/15 - 11:38
 */
public class Code02_SubArrayMaxSum {
	public static void main(String[] args) {
		int n = 10;
		int valueMax = 100;
		int testTimes = 5;
		System.out.println("测试开始！");
		for (int i = 0; i < testTimes; i++) {
			int[] arr = TestUtils.randomArray(n, valueMax);
			int sum1 = maxSubArrayWithViolentSolution(arr);
			int sum2 = maxSubArrayWithDpArray(arr);
			if (sum1 != sum2) {
				System.out.println("测试出错！");
				System.out.println(sum1);
				System.out.println(sum2);
				break;
			}
		}
		System.out.println("测试结束！");
	}

	/**
	 * 暴力解，对数器
	 * @param arr
	 * @return
	 */
	public static int maxSubArrayWithViolentSolution(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		int max = Integer.MIN_VALUE;
		int cur = 0;
		for (int value : arr) {
			cur += value;
			max = Math.max(max, cur);
			cur = Math.max(cur, 0);
		}
		return max;
	}

	/**
	 * @param arr
	 * @return
	 */
	public static int maxSubArrayWithDpArray(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		//本来是需要创建dp[]，但是因为当前位置的最大累加和依赖前一个位置的最大累加和
		//所以只需要一个变量即可，不需要再创建dp[]

		//前一个位置往左扩或者不往左扩的最大累加和
		//0位置的最大累加和就是arr[0]本身
		//dp[0] == arr[0]
		int pre = arr[0];
		int max = arr[0];
		for (int i = 1; i < arr.length; i++) {
			/*//当前位置不往左扩
			int situation1 = arr[i];
			//当前位置往左扩，需要加上前一个位置的最大累加和
			int situation2 = arr[i] + pre;
			//当前位置的最大累加和
			int cur = Math.max(situation1, situation2);
			//选择出总的最大累加和
			max = Math.max(max, cur);
			//当前位置的最大累加和作为下一个位置的前一个位置的最大累加和
			pre = cur;*/

			//当前位置不往左扩
			//当前位置往左扩，需要加上前一个位置的最大累加和
			//当前位置的最大累加和
			pre = Math.max(arr[i], arr[i] + pre);
			//选择出总的最大累加和
			max = Math.max(max, pre);
			//当前位置的最大累加和作为下一个位置的前一个位置的最大累加和
		}
		return max;
	}
}
