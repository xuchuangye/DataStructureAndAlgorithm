package com.mashibing;

/**
 * @author xcy
 * @date 2022/8/1 - 8:00
 */
public class InterviewQuestions4 {
	public static void main(String[] args) {

	}

	public static int maxProfit(int[] arr, int K) {
		if (arr == null || arr.length == 0 || K <= 0) {
			return 0;
		}
		int N = arr.length;
		if (K >= N / 2) {
			int ans = 0;
			for (int i = 1; i < arr.length; i++) {
				ans += Math.max(arr[i] - arr[i - 1], 0);
			}
			return ans;
		}else {
			int[][] dp = new int[N][K + 1];
			for (int i = 0; i < N; i++) {
				dp[i][0] = 0;
			}
			for (int j = 0; j <= K; j++) {
				dp[0][j] = 0;
			}
			//dp[5][3] dp[4][3]
			for (int j = 1; j <= K; j++) {
				//dp[1][j]
				int p1 = dp[0][j];
				//将arr[1]抽取出来
				//int best = Math.max(dp[1][j - 1] + arr[1] - arr[1], dp[0][j - 1] + arr[1] - arr[0]);
				int best = Math.max(dp[1][j - 1] - arr[1], dp[0][j - 1] - arr[0]);
				dp[1][j] = Math.max(p1, best + arr[1]);
				//dp[i][j]
				//dp[2][j]
				//2位置不参与股票交易，dp[1][j]
				//2位置参与股票交易，dp[2][j - 1] - arr[2] + arr[2]
				//dp[1][j - 1] - arr[1] + arr[2]
				//dp[0][j - 1] - arr[0] + arr[2]
				for (int i = 2; i < N; i++) {
					int cur1 = dp[i - 1][j];
					int cur2 = dp[i][j - 1] - arr[i];
					best = Math.max(cur2, best);
					dp[i][j] = Math.max(cur1, best + arr[i]);
				}
			}
			return dp[N - 1][K];
		}
	}
}
