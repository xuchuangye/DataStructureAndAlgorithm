package com.mashibing.day15;

/**
 * 给定一个数组，它的第i个元素是一支给定的股票在第i天的价格。
 * 设计一个算法来计算你所能获取的最大利润。你最多可以完成两笔交易。
 * 注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
 * <p>
 * LeetCode测试链接：
 * https://leetcode.cn/problems/best-time-to-buy-and-sell-stock-iii/
 *
 * @author xcy
 * @date 2022/8/4 - 9:31
 */
public class Code03_BestTimeToBuyAndSellStockIII {
	public static void main(String[] args) {

	}

	public static int maxProfit(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		return maxProfit(2, arr);
	}

	public static int maxProfit(int K, int[] arr) {
		if (K <= 0) {
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
}
