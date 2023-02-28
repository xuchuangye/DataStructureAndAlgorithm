package com.mashibing.day15;

/**
 * 给定一个整数数组prices，其中 prices[i]表示第i天的股票价格；整数fee代表了交易股票的手续费用。
 * 你可以无限次地完成交易，但是你每笔交易都需要付手续费。如果你已经购买了一个股票，在卖出它之前你就不能再继续购买股票了。
 * 返回获得利润的最大值。
 * 注意：这里的一笔交易指买入持有并卖出股票的整个过程，每笔交易你只需要为支付一次手续费。
 * <p>
 * LeetCode测试链接：
 * https://leetcode.cn/problems/best-time-to-buy-and-sell-stock-with-transaction-fee/
 *
 * @author xcy
 * @date 2022/8/4 - 9:31
 */
public class Code06_BestTimeToBuyAndSellStockWithTransactionFee {
	public static void main(String[] args) {

	}

	public static int maxProfit(int[] prices, int fee) {
		if (prices == null || prices.length == 0) {
			return 0;
		}
		//在0位置上进行买入的操作，所以 - prices[0]，每笔交易需要扣手续费，所以 - fee
		int bestBuy = -prices[0] - fee;
		//在0位置上进行卖出的操作，收益为0
		int bestSell = 0;
		for (int i = 1; i < prices.length; i++) {
			//如果在i位置上进行买入的操作，那么i - 1位置上一定进行了卖出的操作，所以是bestSell
			//并且在位置上进行买入的操作时，除了股票价格，还有手续费，所以 - prices[i] - fee
			int curBuy = bestSell - prices[i] - fee;
			//如果在i位置上进行卖出的操作，那么i - 1位置上一定进行了买入的操作，因为只要买入股票就不能再买入股票
			//只能先卖出之后，再进行买入
			int curSell = bestBuy + prices[i];
			//之前的选择和现在的选择求出最大收益
			bestBuy = Math.max(curBuy, bestBuy);
			bestSell = Math.max(curSell, bestSell);
		}
		return bestSell;
	}
}
