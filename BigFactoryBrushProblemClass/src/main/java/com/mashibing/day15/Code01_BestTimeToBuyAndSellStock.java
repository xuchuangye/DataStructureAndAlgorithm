package com.mashibing.day15;

/**
 * 给定一个数组prices，它的第i个元素prices[i]表示一支给定股票第i天的价格。
 * 你只能选择某一天买入这只股票，并选择在未来的某一个不同的日子卖出该股票。
 * 设计一个算法来计算你所能获取的最大利润。
 * 返回你可以从这笔交易中获取的最大利润。如果你不能获取任何利润，返回0。
 *
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/best-time-to-buy-and-sell-stock
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * LeetCode测试链接：
 * https://leetcode.com/problems/best-time-to-buy-and-sell-stock/
 *
 * 解题思路：
 *
 *
 * @author xcy
 * @date 2022/8/4 - 9:30
 */
public class Code01_BestTimeToBuyAndSellStock {
	public static void main(String[] args) {

	}

	public static int maxProfit(int[] prices) {
		if (prices == null || prices.length == 0) {
			return 0;
		}
		//表示至少第i天买入，第i天卖出这一种选择
		int ans = 0;
		//默认最小值
		int min = prices[0];
		//从1位置开始，
		for (int i = 1; i < prices.length; i++) {
			//找到当前i位置之前的最小值min
			min = Math.min(min, prices[i]);
			//那么当前i位置的最大利润就是当前i位置的值减去之前的最小值min
			//如果当前i位置的最大利润为负数，那么至少还有一种选择，就是当天买，当天卖，也就是最大利润为0
			ans = Math.max(ans, prices[i] - min);
		}
		return ans;
	}
}
