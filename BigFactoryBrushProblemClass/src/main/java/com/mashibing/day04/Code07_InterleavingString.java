package com.mashibing.day04;

/**
 * 题目七：
 * 给定三个字符串str1、str2、str3，请你帮忙验证str3是否是由str1和str2交错组成的
 * <p>
 * 举例：
 * str1 = "ab123dc"
 * str2 = "fts67k"
 * str3 = "ftab12s367kde"
 * str3是str1和str2的交错组成
 * <p>
 * 解题思路：
 * 1.动态规划的样本对应尝试模型
 * <p>
 * Leetcode测试链接：
 * https://leetcode.com/problems/interleaving-string/
 *
 * @author xcy
 * @date 2022/7/15 - 11:39
 */
public class Code07_InterleavingString {
	public static void main(String[] args) {

	}

	public static boolean isInterleave(String str1, String str2, String str3) {
		if (str1 == null || str2 == null || str3 == null) {
			return false;
		}
		if (str1.length() == 0 && str2.length() == 0 && str3.length() == 0) {
			return true;
		}
		if (str1.length() + str2.length() != str3.length()) {
			return false;
		}

		return coreLogic(str1.toCharArray(), str2.toCharArray(), str3.toCharArray());
	}

	/**
	 * str1 = "aaabsk" -> length = 6
	 * str2 = "aacfk"  -> length = 5
	 * str3 = "aaacaabfskk"
	 * dp[i][j]表示取出str1的i个字符和取出str2的j个字符，能否组成str3的i + j个字符
	 * 0   1   2   3   4   5   str2
	 * -0  T   T   T   F   F   F
	 * -1  T
	 * -2  T
	 * -3  T
	 * -4  F
	 * -5  F
	 * -6  F
	 * str1
	 *
	 * @param str1 str1字符串
	 * @param str2 str2字符串
	 * @param str3 str3字符串
	 * @return 判断str3字符串是否是str1字符串和str2字符串交错组成的
	 */
	public static boolean coreLogic(char[] str1, char[] str2, char[] str3) {
		boolean[][] dp = new boolean[str1.length + 1][str2.length + 1];
		//dp[0][0]表示从str1字符串中取出0个字符串，从str2字符串中取出0个字符串，能够组成str3的(0 + 0)个字符串
		dp[0][0] = true;
		for (int i = 1; i <= str1.length; i++) {
			if (str1[i - 1] != str3[i - 1]) {
				break;
			}
			dp[i][0] = true;
		}
		for (int j = 1; j <= str2.length; j++) {
			if (str2[j - 1] != str3[j - 1]) {
				break;
			}
			dp[0][j] = true;
		}
		//dp[i][j]
		//str1取前i个字符，范围0 ~ i - 1
		//str2取前j个字符，范围0 ~ j - 1
		//str3取i + j 个字符，范围0 ~ i + j - 1

		//情况1：
		//总的字符串的最后一个字符 == str1字符串的最后一个字符
		//str3[i + j - 1] == str1[i - 1]
		//总的字符串去除掉总的字符串的最后一个字符，还需要交错组成的字符串长度i + j - 1
		//str1字符串去除掉最后一个字符，还能够使用的字符串长度i - 1
		//str2字符串还能够使用的字符串长度不变，还是j
		//那么只依赖从str1取前i - 1个字符，从str2取前j个字符的结果，也就是dp[i - 1][j]必须也要成立
		//情况2：
		//总的字符串的最后一个字符 == str2字符串的最后一个字符
		//str3[i + j - 1] == str2[j - 1]
		//总的字符串去除掉总的字符串的最后一个字符，还需要交错组成的字符串长度i + j - 1
		//str1字符串还能够使用的字符串长度不变，还是i
		//str2字符串去除掉最后一个字符，还能够使用的字符串长度j - 1
		//那么只依赖从str1取前i个字符，从str2取前j - 1个字符的结果，也就是dp[i][j - 1]必须也要成立
		//两种情况只要满足其中一种，都算成立
		for (int i = 1; i <= str1.length; i++) {
			for (int j = 1; j <= str2.length; j++) {
				if ((str3[i + j - 1] == str1[i - 1] && dp[i - 1][j])
						||
						(str3[i + j - 1] == str2[j - 1] && dp[i][j - 1])) {
					dp[i][j] = true;
				}
			}
		}
		//返回从str1字符串取前i个字符，从str2字符串取前j个字符，能够组成str3字符串i + j个字符
		return dp[str1.length][str2.length];
	}
}
