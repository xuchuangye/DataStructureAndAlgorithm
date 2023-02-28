package com.mashibing.day11;

/**
 * 题目三：
 * 题目：
 * 问题一：两个字符串的最长公共子序列的长度
 * 问题二：返回两个字符串的最长公共子序列
 *
 * @author xcy
 * @date 2022/7/30 - 11:05
 */
public class Code03_LongestCommonSubsequence {
	public static void main(String[] args) {
		String str1 = "a1fafagr2b";
		String str2 = "cdgagrafr12b";
		int len = longestCommonSubsequence(str1, str2);
		System.out.println(len);
		String s = returnLongestCommonSubsequence(str1, str2);
		System.out.println(s);
	}

	/**
	 * @param s1 s1字符串
	 * @param s2 s2字符串
	 * @return 返回s1字符串和s2字符串的最长公共子序列的长度
	 */
	public static int longestCommonSubsequence(String s1, String s2) {
		if (s1 == null || s2 == null) {
			return 0;
		}
		if ("".equals(s1) && "".equals(s2)) {
			return 1;
		}
		char[] str1 = s1.toCharArray();
		char[] str2 = s2.toCharArray();
		int N = str1.length;
		int M = str2.length;
		//String str1 = "a12b"
		//String str2 = "cd12"
		//  0  1  2  3
		//0 0  0  0  0
		//1 0
		//2 0
		//3 0
		int[][] dp = new int[N][M];
		for (int i = 0; i < N; i++) {
			//str1[0 ... i]包含str2[0]，如果包含，最长公共子序列的长度为1，否则为0
			dp[i][0] = s1.substring(0, i).contains(String.valueOf(str2[0])) ? 1 : 0;
		}

		for (int j = 0; j < M; j++) {
			//str2[0 ... j]包含str1[0]，如果包含，最长公共子序列的长度为1，否则为0
			dp[0][j] = s2.substring(0, j).contains(String.valueOf(str1[0])) ? 1 : 0;
		}

		for (int i = 1; i < N; i++) {
			for (int j = 1; j < M; j++) {
				//情况一：
				//dp[i - 1][j]
				//str1 = "abc 3"
				//str2 = "abc"
				//情况二：
				//dp[i][j - 1]
				//str1 = "abc"
				//str2 = "abc d"
				dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
				//情况三：
				//str1[i] == str2[j] && dp[i - 1][j - 1]
				//str1 = "abc d"
				//str2 = "abc d"
				if (str1[i] == str2[j]) {
					dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - 1] + 1);
				}
			}
		}
		return dp[N - 1][M - 1];
	}

	/**
	 *
	 * @param s1
	 * @param s2
	 * @return 返回s1字符串和s2字符串的最长公共子序列
	 */
	public static String returnLongestCommonSubsequence(String s1, String s2) {
		if (s1 == null || s2 == null) {
			return null;
		}
		if ("".equals(s1) && "".equals(s2)) {
			return "";
		}

		char[] str1 = s1.toCharArray();
		char[] str2 = s2.toCharArray();
		int N = str1.length;
		int M = str2.length;
		int[][] dp = new int[N][M];
		for (int i = 0; i < N; i++) {
			dp[i][0] = s1.substring(0, i).contains(String.valueOf(str2[0])) ? 1 : 0;
		}

		for (int j = 0; j < M; j++) {
			dp[0][j] = s2.substring(0, j).contains(String.valueOf(str1[0])) ? 1 : 0;
		}

		for (int i = 1; i < N; i++) {
			for (int j = 1; j < M; j++) {
				dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
				if (str1[i] == str2[j]) {
					dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - 1] + 1);
				}
			}
		}
		int ansR = dp[N - 1][M - 1];
		char[] ans = new char[dp[N - 1][M - 1]];
		for (int i = 1; i < N; i++) {
			for (int j = 1; j < M; j++) {
				if (str1[i] == str2[j] && dp[i][j] == dp[i - 1][j - 1]) {
					ans[ansR--] = str1[i];
					i--;
					j--;
				}
			}
		}
		return String.valueOf(ans);
	}
}
