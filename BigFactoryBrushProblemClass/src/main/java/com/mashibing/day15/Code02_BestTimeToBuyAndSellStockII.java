package com.mashibing.day15;

import java.util.Arrays;

/**
 * 给你一个整数数组prices，其中prices[i]表示某支股票第i天的价格。
 * 在每一天，你可以决定是否购买和/或出售股票。你在任何时候最多只能持有 一股股票。你也可以先购买，然后在同一天出售。
 * 返回你能获得的最大利润
 * 数据规模：
 * 1 <= prices.length <= 3 * 10的4次方
 * 0 <= prices[i] <= 10的4次方
 * <p>
 * 解题思路：
 * 1.计算每一天的最大收益，也就是今天的股票价格 - 前一天的股票价格，如果为负数，就当天卖出股票
 * 2.第一天肯定不会产生收益
 * <p>
 * LeetCode测试链接：
 * https://leetcode.com/problems/best-time-to-buy-and-sell-stock-ii/
 *
 * @author xcy
 * @date 2022/8/4 - 9:30
 */
public class Code02_BestTimeToBuyAndSellStockII {
	public static void main(String[] args) {
		int[] arr = {1, 2, 3, 4, 5};
		int maxProfit = maxProfit(arr);
		int maxProfit2 = maxProfitOptimization(arr);
		System.out.println(maxProfit);
		System.out.println(maxProfit2);
	}

	/**
	 * @param prices 表示每一天的股票价格数组
	 * @return 返回最大收益
	 */
	public static int maxProfit(int[] prices) {
		if (prices == null || prices.length <= 1) {
			return 0;
		}
		//prices[] = 7, 6, 1, 3, 5, 4, 7, 2, 7, 6, 7, 8
		//temp[] =   0  0  0  2  2  0  3  0  5  0  1  1
		//每一次涨幅最大的即为最大收益，也就是波峰减去波谷的差值
		//比如1 -> 3 -> 5，涨幅为4，相当于(5 - 3) + (3 - 1)
		//比如4 -> 7，涨幅为3，相当于7 - 4
		//比如2 -> 7，涨幅为5，相当于7 - 2
		//temp[]表示产生收益的数组
		int[] temp = new int[prices.length];
		//第一天的收益为0，因为当天买入然后再卖出也不会产生收益
		temp[0] = 0;
		//i位置从1出发
		for (int i = 1; i < prices.length; i++) {
			//判断今天的股票价格 - 昨天的股票价格能否产生收益，如果不能产生收益，那么就直接当天卖出，至少不会亏钱
			//因此和0进行比较
			temp[i] = Math.max(0, prices[i] - prices[i - 1]);
		}
		//System.out.println(Arrays.toString(temp));
		//所有的收益进行累加
		int ans = 0;
		for (int profit : temp) {
			ans += profit;
		}
		return ans;
	}

	/**
	 * @param prices 表示每一天的股票价格数组
	 * @return 返回最大收益
	 */
	public static int maxProfitOptimization(int[] prices) {
		if (prices == null || prices.length <= 1) {
			return 0;
		}
		int ans = 0;
		for (int i = 1; i < prices.length; i++) {
			ans += Math.max(0, prices[i] - prices[i - 1]);
		}
		return ans;
	}
}
