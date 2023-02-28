package com.mashibing.day15;

import com.mashibing.common.TestUtils;

import java.util.Arrays;

/**
 * 给定一个整数数组prices，它的第i个元素prices[i]是一支给定的股票在第i天的价格。
 * 设计一个算法来计算你所能获取的最大利润。你最多可以完成k笔交易。
 * 注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
 * 数据规模：
 * 0 <= k <= 100
 * 0 <= prices.length <= 1000
 * 0 <= prices[i] <= 1000
 * <p>
 * 解题思路：
 * 1.k分为两种情况，一种是：k >= N / 2，另一种是：k < N / 2
 * (1).当k >= N / 2时，就是Code02的解
 * (2).当k < N / 2时，需要创建dp[][]，dp[i][j]表示在arr[0 ... i]范围上，不超过j次交易得到的最大收益
 * -  0  1  2  3  j -> 不超过的交易次数
 * -0 0  0  0  0
 * -1 0
 * -2 0
 * -3 0
 * -4 0
 * i
 * arr[]的索引表示0 ... i范围上，进行不超过j次的交易之后，得到的最大收益
 * dp[i][0]表示在arr[]0 ... i范围上，进行不超过0次交易，得到的最大收益都为0
 * dp[0][j]表示在arr[]0 ... 0范围上，进行不超过j次交易，得到的最大收益都为0
 * 2.dp[i][j]的普遍位置存在几种可能性：
 * 可能性1：在arr[]0...i范围上，i位置不参与交易，那么dp[i][j] = dp[i - 1][j]
 * 可能性2：在arr[]0...i范围上，i位置参与交易，那么最后一次交易的卖出就在i位置，那么最后一次交易的买入有哪些可能性？
 * (1)可能性1：i位置就是最后一次交易的买入，所以i位置本身占了1次交易次数，还剩下j - 1次交易次数，
 * 也就是dp[i][j - 1] + dp[i] - dp[i]
 * (2)可能性2：i - 1位置就是最后一次交易的买入，所以i和i - 1位置本身占了1次交易次数，还剩下j - 1次交易次数，
 * 也就是dp[i - 1][j - 1] + dp[i] - dp[i - 1]
 * (3)可能性3：i - 2位置就是最后一次交易的买入，所以i和i - 2位置本身占了1次交易次数，还剩下j - 1次交易次数，
 * 也就是dp[i - 2][j - 1] + dp[i] - dp[i - 2]
 * 依此类推
 * <p>
 * LeetCode测试链接：
 * https://leetcode.cn/problems/best-time-to-buy-and-sell-stock-iv/
 *
 * @author xcy
 * @date 2022/8/4 - 9:31
 */
public class Code04_BestTimeToBuyAndSellStockIV {
	public static void main(String[] args) {
		int testTimes = 10000;
		int length = 100;
		int maxValue = 100;
		System.out.println("测试开始！");
		for (int i = 0; i < testTimes; i++) {
			int[] arr = TestUtils.randomArray(length, maxValue);
			int k = (int) (Math.random() * 100) + 1;
			int result1 = maxProfit(k, arr);
			int result2 = maxProfit1(k, arr);
			if (result1 != result2) {
				System.out.println("测试失败！");
				System.out.println(Arrays.toString(arr));
				System.out.println(k);
				System.out.println(result1);
				System.out.println(result2);
				break;
			}
		}
		System.out.println("测试结束！");
	}

	public static int maxProfit(int K, int[] arr) {
		if (arr == null || arr.length == 0 || K <= 0) {
			return 0;
		}
		int N = arr.length;
		if (K >= N / 2) {
			int ans = 0;
			for (int i = 1; i < N; i++) {
				ans += Math.max(0, arr[i] - arr[i - 1]);
			}
			return ans;
		} else {
			int[][] dp = new int[N][K + 1];

			/*for (int i = 0; i < N; i++) {
				dp[i][0] = 0;
			}*/
			/*for (int j = 0; j <= K; j++) {
				dp[0][j] = 0;
			}*/

			//dp[3][2] 与 dp[4][2]有依赖关系，并且是行与行之间的关系，所以先从第1列到最后一列
			//然后再从上往下进行遍历
			for (int j = 1; j <= K; j++) {
				//求出dp[1][j]的值
				//1位置的股票不参与交易
				int p1 = dp[0][j];
				//1位置的股票参与交易
				//dp[1][j - 1] - arr[1]          + arr[1]
				//dp[0][j - 1] - arr[0]          + arr[1]
				//dp[1][j]的best = {
				//    dp[1][j - 1] - arr[1]
				//    dp[0][j - 1] - arr[0]
				//}
				//求出best中的最大值，然后best + arr[1]与dp[0][j]比较大小
				int best = Math.max(dp[1][j - 1] - arr[1], dp[0][j - 1] - arr[0]);
				dp[1][j] = Math.max(p1, best + arr[1]);

				//求出dp[i][j]
				//第0行第1行都已经填写过了，从第2行开始
				//举例：
				//dp[2][j]
				//2位置的股票不参与交易
				//int cur1 = dp[1][j]
				//2位置的股票参与交易
				//dp[2][j - 1] - arr[2]          + arr[2]
				//dp[1][j - 1] - arr[1]          + arr[2]
				//dp[0][j - 1] - arr[0]          + arr[2]
				//dp[1][j]的best = {
				//    dp[1][j - 1] - arr[1]
				//    dp[0][j - 1] - arr[0]
				//}
				//比较dp[2][j - 1] - arr[2]与dp[1][j]的best得到最大收益 + 最后一次交易的卖出arr[i]
				//与dp[2][j]进行比较，得到最终的最大收益
				for (int i = 2; i < N; i++) {
					//i位置的股票不参与交易
					int cur1 = dp[i - 1][j];
					//j位置的股票参与交易
					//dp[2][j - 1] - arr[2]
					int cur2 = dp[i][j - 1] - arr[i];
					best = Math.max(cur2, best);
					dp[i][j] = Math.max(cur1, best + arr[i]);
				}
			}
			return dp[N - 1][K];
		}
	}


	public static int maxProfit1(int K, int[] prices) {
		if (prices == null || prices.length == 0) {
			return 0;
		}
		int N = prices.length;
		if (K >= N / 2) {
			return allTrans(prices);
		}
		int[][] dp = new int[K + 1][N];
		int ans = 0;
		for (int tran = 1; tran <= K; tran++) {
			int pre = dp[tran][0];
			int best = pre - prices[0];
			for (int index = 1; index < N; index++) {
				pre = dp[tran - 1][index];
				dp[tran][index] = Math.max(dp[tran][index - 1], prices[index] + best);
				best = Math.max(best, pre - prices[index]);
				ans = Math.max(dp[tran][index], ans);
			}
		}
		return ans;
	}

	private static int allTrans(int[] prices) {
		int ans = 0;
		for (int i = 1; i < prices.length; i++) {
			ans += Math.max(prices[i] - prices[i - 1], 0);
		}
		return ans;
	}
}
