package com.mashibing.day17;

import java.util.Arrays;

/**
 * 给定两个字符串S和T，返回S的所有子序列中有多少个子序列的字面值等于T
 * <p>
 * Leetcode测试链接：
 * https://leetcode.cn/problems/21dk04/
 *
 * @author xcy
 * @date 2022/8/7 - 10:04
 */
public class Code04_DistinctSubsequence {
	public static void main(String[] args) {
		String s = "1231123";
		String t = "1";
		int count = numDistinct(s, t);
		System.out.println(count);
	}

	/**
	 * String s = "12311";
	 * index =     01234
	 * String t = "13112";
	 * -  0  1  2  3  4  j
	 * -0 1  0  0  0  0
	 * -1 1
	 * -2 1
	 * -3 2
	 * -4 3
	 * i
	 * dp[i][j]表示s字符串0 ~ i位置上有多少个子序列等于t字符串0 ~ j位置上的字面值
	 *
	 * @param s s字符串
	 * @param t t字符串
	 * @return 返回s的所有子序列中有多少个子序列的字面值等于t
	 */
	public static int numDistinct(String s, String t) {
		if (s == null || t == null) {
			return 0;
		}
		if ("".equals(s) && "".equals(t)) {
			return 1;
		}
		char[] str1 = s.toCharArray();
		char[] str2 = t.toCharArray();
		int N = str1.length;
		int M = str2.length;
		int[][] dp = new int[N][M];
		//s字符串0位置上的字符是否等于t字符串0位置上的字符，如果等于是1种子序列，否则0种子序列
		dp[0][0] = str1[0] == str2[0] ? 1 : 0;
		//s只有一个字符，t有多个字符，搞不定
		//所以除去j == 0的情况下，其余的dp[0][j]都 == 0
		for (int i = 1; i < N; i++) {
			//String s = "12311";
			//index =     01234
			//String t = "13112";
			//-  0  1  2  3  4  j
			//-0 1  0  0  0  0
			//-1 1
			//-2 1
			//-3 2
			//-4 3
			//i
			dp[i][0] = str1[i] == str2[0] ? dp[i - 1][0] + 1 : dp[i - 1][0];
		}

		for (int i = 1; i < N; i++) {
			//有可能N远远大于M，也就是行数大于列数，可能会出现越界的情况
			//所以j <= Math.min(i, M - 1)
			for (int j = 1; j <= Math.min(i, M - 1); j++) {
				int situation1 = dp[i - 1][j];
				int situation2 = 0;
				if (str1[i] == str2[j]) {
					situation2 = dp[i - 1][j - 1];
				}
				dp[i][j] += situation1 + situation2;
			}
		}

		return dp[N - 1][M - 1];
	}
}
