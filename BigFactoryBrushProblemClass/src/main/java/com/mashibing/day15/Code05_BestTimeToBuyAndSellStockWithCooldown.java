package com.mashibing.day15;

/**
 * 给定一个整数数组prices，其中第prices[i]表示第i天的股票价格。
 * 设计一个算法计算出最大利润。在满足以下约束条件下，你可以尽可能地完成更多的交易（多次买卖一支股票）:
 * 卖出股票后，你无法在第二天买入股票 (即冷冻期为 1 天)。
 * 注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
 * <p>
 * 解题思路：
 * 1.buy[i]表示在0 ~ i范围上，最后一次交易一定是买入的操作，扣除掉最好的买入价格，加上前面无限次交易的最优收入的整体最优
 * (1)如果i位置不参与交易，那么i位置可以忽略，这个时候buy[i] = buy[i - 1]
 * 表示0 ~ i - 1无限次交易并且最后一次操作是买入的情况下，扣除掉最好的买入时机，加上前面无限次交易的最优收入的整体最优
 * (2)如果i位置参与交易，并且是最后一次买入的操作，需要扣除i位置的值prices[i]，并且只能在0 ~ i - 2范围上进行无限次交易
 * 因为冷冻期的原因，需要隔一天才能再次进行交易，所以是sell[i - 2] - prices[i]，
 * 2.sell[i]表示在0 ~ i范围上，最后一次交易一定是卖出的操作
 * (1)如果i位置不参与交易，那么i位置可以忽略，这个时候sell[i] = sell[i - 1]
 * (2)如果i位置参与交易，并且是最后一次卖出的操作，需要加上i位置的值prices[i]，并且在0 ~ i - 1范围上进行无限次交易以及
 * 最优的买入价格，所以是buy[i - 1] + prices[i]
 * <p>
 * LeetCode测试链接：
 * https://leetcode.cn/problems/best-time-to-buy-and-sell-stock-with-cooldown/
 *
 * @author xcy
 * @date 2022/8/4 - 9:31
 */
public class Code05_BestTimeToBuyAndSellStockWithCooldown {
	public static void main(String[] args) {

	}

	public static int maxProfit(int[] prices) {
		if (prices == null || prices.length < 2) {
			return 0;
		}
		int N = prices.length;
		int[] buy = new int[N];
		int[] sell = new int[N];
		buy[0] = -prices[0];
		sell[0] = 0;
		//要么在0位置上买入，要么在1位置上买入
		//买入股票肯定减收益的，所以都是负数
		buy[1] = Math.max(-prices[0], -prices[1]);
		//要么在0位置上买入，在0位置上卖出
		//要么在0位置上买入，在1位置上卖出
		sell[1] = Math.max(0, -prices[0] + prices[1]);
		for (int i = 2; i < N; i++) {
			//情况1：i位置不参与股票交易
			//那么可以忽略i位置，在0 ~ i - 1范围上进行无限次交易，并且扣除最好的买入价格之后获得整体的最优收益
			//情况2：i位置参与股票交易
			//sell[i - 2]表示因为冷冻期的原因，两次交易中间要隔一天
			//所以在0 ~ i - 2范围上进行无限次交易，并且最后一次是卖出的操作的整体最优收益
			//最后在i位置买入股票，所以 - prices[i]
			buy[i] = Math.max(buy[i - 1], sell[i - 2] - prices[i]);
			//情况1：i位置不参与股票交易
			//sell[i - 1]表示在i - 1位置上进行卖出操作
			//情况2：i位置参与股票交易
			//buy[i - 1]表示在0 ~ i - 1范围上进行无限次交易，并且已经扣除最好的买入时机的整体收益
			//最后在i位置卖出股票，所以 + prices[i]
			sell[i] = Math.max(sell[i - 1], buy[i - 1] + prices[i]);
		}
		//返回0 ~ N - 1范围上，在N - 1位置上进行卖出操作的最优收益
		return sell[N - 1];
	}

	public static int maxProfit2(int[] prices) {
		if (prices == null || prices.length < 2) {
			return 0;
		}
		int N = prices.length;
		int buy = Math.max(-prices[0], -prices[1]);
		int sell1 = Math.max(0, -prices[0] + prices[1]);
		int sell2 = 0;
		for (int i = 2; i < N; i++) {
			int temp = sell1;
			sell1 = Math.max(sell1, buy + prices[i]);
			buy = Math.max(buy, sell2 - prices[i]);
			sell2 = temp;
		}
		return sell1;
	}
}
