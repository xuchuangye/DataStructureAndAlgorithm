package com.mashibing.dynamic;

/**
 * 最长公共子序列
 * <p>
 * 基本思路：
 * 1、两个字符串str1和str2，以及各个字符串的字符数组对应的索引i和j
 * 1)完全不考虑i，可能考虑j
 * 2)可能考虑i，完全不考虑j
 * 3)考虑i，考虑j，判断i == j
 * <p>
 * a.1)和2)虽然有交集，但是求最大公共子序列长度的结果不会变
 * b.可能考虑i或者j，就不需要对j或者i进行遍历查看是否存在，省去遍历的过程
 * <p>
 * leetcode: https://leetcode.com/problems/longest-common-subsequence/
 *
 * @author xcy
 * @date 2022/5/9 - 9:06
 */
public class LongestCommonSubsequence {
	public static void main(String[] args) {

	}

	/**
	 * 返回第一个字符串和第二个字符串的最大公共子序列 --> 使用暴力递归的方式
	 *
	 * @param s1 第一个字符串
	 * @param s2 第二个字符串
	 * @return 返回第一个字符串和第二个字符串的最大公共子序列
	 */
	public static int longestCommonSubsequence(String s1, String s2) {
		if (s1 == null || s2 == null || s1.length() == 0 || s2.length() == 0) {
			return 0;
		}
		char[] str1 = s1.toCharArray();
		char[] str2 = s2.toCharArray();

		return coreLogic(str1, str2, str1.length - 1, str2.length - 1);
	}

	/**
	 * @param str1 第一个字符串的字符数组
	 * @param str2 第二个字符串的字符数组
	 * @param i    第一个字符数组的索引
	 * @param j    第二个字符数组的索引
	 * @return 返回第一个字符串和第二个字符串的最大公共子序列
	 */
	public static int coreLogic(char[] str1, char[] str2, int i, int j) {
		//如果str1只有一个字符并且str2也只有一个字符
		if (i == 0 && j == 0) {
			//判断str[i] == str[j]，等于结果返回1，否则返回0
			return str1[i] == str2[j] ? 1 : 0;
		}
		//如果str1只有一个字符并且str2有多个字符
		else if (i == 0) {
			//如果str1[i] == str2[j]，返回1
			if (str1[i] == str2[j]) {
				return 1;
			} else {
				//否则继续递归查看str2[j - 1]位置上的字符是否 == str1[i]
				return coreLogic(str1, str2, i, j - 1);
			}
		}
		//如果str1有多个字符并且str2只有一个字符
		else if (j == 0) {
			//如果str1[i] == str2[j]，返回1
			if (str1[i] == str2[j]) {
				return 1;
			} else {
				//否则继续递归查看str1[i - 1]位置上的字符是否 == str2[j]
				return coreLogic(str1, str2, i - 1, j);
			}
		}
		//如果str1和str2都有多个字符
		else {
			//情况1：完全不考虑j，可能考虑i，i不变，j - 1
			int situation1 = coreLogic(str1, str2, i, j - 1);
			//情况2：完全不考虑i，可能考虑j，j不变，i - 1
			int situation2 = coreLogic(str1, str2, i - 1, j);
			//情况3：必须str1[i] == str2[j]相等时，至少有一个字符是公共的 + 之前的情况，i - 1，j - 1
			//否则str1[i] != str2[j]时，没有，最大公共子序列为0个
			int situation3 = str1[i] == str2[j] ? (1 + coreLogic(str1, str2, i - 1, j - 1)) : 0;
			//返回三种情况的最大值，也就是最大公共子序列
			return Math.max(Math.max(situation1, situation2), situation3);
		}

	}

	/**
	 * 返回第一个字符串和第二个字符串的最大公共子序列 --> 使用动态规划的方式
	 *
	 * @param s1 第一个字符串
	 * @param s2 第二个字符串
	 * @return 返回第一个字符串和第二个字符串的最大公共子序列
	 */
	public static int longestCommonSubsequenceWithTable(String s1, String s2) {
		if (s1 == null || s2 == null || s1.length() == 0 || s2.length() == 0) {
			return 0;
		}
		char[] str1 = s1.toCharArray();
		char[] str2 = s2.toCharArray();

		int[][] table = new int[str1.length][str2.length];
		table[0][0] = str1[0] == str2[0] ? 1 : 0;

		//填写第0行
		for (int j = 1; j < str2.length; j++) {
			table[0][j] = str1[0] == str2[j] ? 1 : table[0][j - 1];
		}

		//填写第0列
		for (int i = 1; i < str1.length; i++) {
			table[i][0] = str1[i] == str2[0] ? 1 : table[i - 1][0];
		}

		//行从第1行开始，列从第1列开始
		for (int i = 1; i < str1.length; i++) {
			for (int j = 1; j < str2.length; j++) {
				//情况1：完全不考虑j，可能考虑i，i不变，j - 1
				int situation1 = table[i][j - 1];
				//情况2：完全不考虑i，可能考虑j，j不变，i - 1
				int situation2 = table[i - 1][j];
				//情况3：必须str1[i] == str2[j]相等时，至少有一个字符是公共的 + 之前的情况，i - 1，j - 1
				//否则str1[i] != str2[j]时，没有，最大公共子序列为0个
				int situation3 = str1[i] == str2[j] ? (1 + table[i - 1][j - 1]) : 0;
				//返回三种情况的最大值，也就是最大公共子序列
				table[i][j] =  Math.max(Math.max(situation1, situation2), situation3);
			}
		}

		//coreLogic(str1, str2, str1.length - 1, str2.length - 1);
		return table[str1.length - 1][str2.length - 1];
	}
}
