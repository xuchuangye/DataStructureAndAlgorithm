package com.mashibing.dynamic;

import java.util.Locale;

/**
 * 最长回文子序列
 * <p>
 * 给定一个字符串str，返回这个字符串的最长回文子序列的长度
 * 举例：str = "a12b3c43def2ghi1kpm"
 * 最长回文子序列是 "1234321" 或者 "123c321" 返回长度
 * <p>
 * 基本思路：
 * 1、一个字符串和该字符串的逆序字符串，两个字符串的最大公共子序列就是该字符串的最长回文子序列
 * 2、使用范围模型的动态规划的尝试模型
 *
 * @author xcy
 * @date 2022/5/9 - 11:21
 */
public class LongestPalindromeSubsequence {
	public static void main(String[] args) {
		String string = "bbbaafa3aga323gaq432323b";
		int count1 = longestPalindromeSubsequence(string);
		int count2 = longestPalindromeSubsequenceWithTable1(string);
		int count3 = longestPalindromeSubsequenceWithTable2(string);
		int count4 = longestPalindromeSubsequenceWithTable3(string);
		System.out.println(count1);
		System.out.println(count2);
		System.out.println(count3);
		System.out.println(count4);
	}

	/**
	 * 返回字符串的最长回文子序列的长度 --> 使用暴力递归的方式
	 *
	 * @param string 原始的字符串
	 * @return 返回字符串的最长回文子序列的长度
	 */
	public static int longestPalindromeSubsequence(String string) {
		if (string == null || string.length() == 0) {
			return 0;
		}
		return coreLogic(string.toCharArray(), 0, string.length() - 1);
	}

	/**
	 * 核心逻辑
	 *
	 * @param str   字符串的字符数组
	 * @param left  左边界
	 * @param right 右边界
	 * @return 返回字符串的最长回文子序列的长度
	 */
	public static int coreLogic(char[] str, int left, int right) {
		//说明str字符数组只有一个字符，最大回文子序列的长度为1
		if (left == right) {
			return 1;
		}
		//说明str字符数组有两个字符，最大回文子序列的长度为2或者1
		if (left == right - 1) {
			return str[left] == str[right] ? 2 : 1;
		}
		//说明str字符数组有两个字符以上
		//情况1：不以left为开始，也不以right为结尾
		int situation1 = coreLogic(str, left + 1, right - 1);
		//情况2：不以left为开始，以right为结尾
		int situation2 = coreLogic(str, left + 1, right);
		//情况3：以left为开始，不以right为结尾
		int situation3 = coreLogic(str, left, right - 1);
		//情况4：以left为开始，以right为结尾，满足str[left] == str[right]
		//所以str[left] == str[right]最大回文子序列的长度至少为2，加上left + 1到right - 1的最大回文子序列的长度
		//否则str[left] != str[right]最大回文子序列的长度就是0
		int situation4 = str[left] == str[right] ? (2 + coreLogic(str, left + 1, right - 1)) : 0;
		int max1 = Math.max(situation1, situation2);
		int max2 = Math.max(situation3, situation4);
		return Math.max(max1, max2);
	}

	/**
	 * 返回字符串的最长回文子序列的长度 --> 使用动态规划的方式(优化版本1)
	 *
	 * @param string 原始的字符串
	 * @return 返回字符串的最长回文子序列的长度
	 */
	public static int longestPalindromeSubsequenceWithTable1(String string) {
		if (string == null || string.length() == 0) {
			return 0;
		}
		char[] str = string.toCharArray();
		int[][] table = new int[str.length][str.length];

		//正常的情况下，不可能出现left > right
		//table[1][0] = str[1] == str[0] ? 2 : 1;
		//所有对角线的数值都是1
		for (int i = 0; i < str.length; i++) {
			for (int j = 0; j < str.length; j++) {
				if (i == j) {
					table[i][j] = 1;
				}
			}
		}
		//居中对角线的下一条对角线
		for (int i = 0; i < str.length; i++) {
			for (int j = i + 1; j < str.length; j++) {
				table[i][j] = str[0] == str[1] ? 2 : 1;
			}
		}
		for (int i = 1; i < table.length; i++) {
			int left = 0;
			for (int right = i; right < table[0].length; right++) {
				//情况1：不以left为开始，也不以right为结尾
				int situation1 = table[left + 1][right - 1];
				//情况2：不以left为开始，以right为结尾
				int situation2 = table[left + 1][right];
				//情况3：以left为开始，不以right为结尾
				int situation3 = table[left][right - 1];
				//情况4：以left为开始，以right为结尾，满足str[left] == str[right]
				//所以str[left] == str[right]最大回文子序列的长度至少为2，加上left + 1到right - 1的最大回文子序列的长度
				//否则str[left] != str[right]最大回文子序列的长度就是0
				int situation4 = str[left] == str[right] ? (2 + table[left + 1][right - 1]) : 0;
				int max1 = Math.max(situation1, situation2);
				int max2 = Math.max(situation3, situation4);
				table[left][right] = Math.max(max1, max2);
				left++;
			}
		}
		//coreLogic(string.toCharArray(), 0, string.length() - 1);
		return table[0][str.length - 1];
	}

	/**
	 * 返回字符串的最长回文子序列的长度 --> 使用动态规划的方式(优化版本2)
	 *
	 * @param string 原始的字符串
	 * @return 返回字符串的最长回文子序列的长度
	 */
	public static int longestPalindromeSubsequenceWithTable2(String string) {
		if (string == null || string.length() == 0) {
			return 0;
		}
		char[] str = string.toCharArray();
		int[][] table = new int[str.length][str.length];
		table[str.length - 1][str.length - 1] = 1;
		//在二维数组table中，所有left == right的对角线元素值都为1
		//所有left == right - 1的对角线元素值都为str[i] == str[i + 1] ? 2 : 1
		for (int i = 0; i < str.length - 1; i++) {
			table[i][i] = 1;
			//相邻的字符相同时最大回文子序列的长度为2，不同则为1
			table[i][i + 1] = str[i] == str[i + 1] ? 2 : 1;
		}
		//所有left = str.length - 1以及left = str.length - 2的元素值都已经填充好了
		//所以left从str.length = 3开始
		for (int left = str.length - 3; left >= 0; left--) {
			//所有right = left以及right = left + 1的元素值都已经填充好了
			//所以right从left + 2开始
			for (int right = left + 2; right < str.length; right++) {
				//情况1：不以left为开始，也不以right为结尾
				int situation1 = table[left + 1][right - 1];
				//情况2：不以left为开始，以right为结尾
				int situation2 = table[left + 1][right];
				//情况3：以left为开始，不以right为结尾
				int situation3 = table[left][right - 1];
				//情况4：以left为开始，以right为结尾
				//如果满足str[left] == str[right]，最大回文子序列的长度至少为2，加上left + 1到right - 1的最大回文子序列的长度
				//否则str[left] != str[right]最大回文子序列的长度就是0
				int situation4 = str[left] == str[right] ? (2 + table[left + 1][right - 1]) : 0;
				int max1 = Math.max(situation1, situation2);
				int max2 = Math.max(situation3, situation4);
				table[left][right] = Math.max(max1, max2);
			}
		}
		//coreLogic(string.toCharArray(), 0, string.length() - 1);
		return table[0][str.length - 1];
	}

	/**
	 * 返回字符串的最长回文子序列的长度 --> 使用动态规划的方式(优化版本3)
	 *
	 * @param string 原始的字符串
	 * @return 返回字符串的最长回文子序列的长度
	 */
	public static int longestPalindromeSubsequenceWithTable3(String string) {
		if (string == null || string.length() == 0) {
			return 0;
		}
		char[] str = string.toCharArray();
		int[][] table = new int[str.length][str.length];
		table[str.length - 1][str.length - 1] = 1;
		//在二维数组table中，所有left == right的对角线元素值都为1
		//所有left == right - 1的对角线元素值都为str[i] == str[i + 1] ? 2 : 1
		for (int i = 0; i < str.length - 1; i++) {
			table[i][i] = 1;
			//相邻的字符相同时最大回文子序列的长度为2，不同则为1
			table[i][i + 1] = str[i] == str[i + 1] ? 2 : 1;
		}
		//所有left = str.length - 1以及left = str.length - 2的元素值都已经填充好了
		//所以left从str.length = 3开始
		for (int left = str.length - 3; left >= 0; left--) {
			//所有right = left以及right = left + 1的元素值都已经填充好了
			//所以right从left + 2开始
			for (int right = left + 2; right < str.length; right++) {
				/*
				情况1已经不需要了
				因为table[left][right]从
				情况1：table[left + 1][right - 1]、
				情况2：table[left][right - 1]、
				情况3：table[left + 1][right]、
				情况4：2 + table[left + 1][right - 1]
				中取出最大值，所以table[left][right]比以上所有位置上的值都大
				以此类推，
				table[left][right - 1]的值肯定比table[left + 1][right - 1]的值大
				table[left + 1][right]的值肯定比table[left + 1][right - 1]的值大
				所以，可以忽略table[left + 1][right - 1]的值，因为该位置不是table[left][right]取出最大值的重要依据
				 */
				table[left][right] = Math.max(table[left + 1][right], table[left][right - 1]);
				if (str[left] == str[right]) {
					table[left][right] = Math.max(table[left][right], 2 + table[left + 1][right - 1]);
				}
			}
		}
		//coreLogic(string.toCharArray(), 0, string.length() - 1);
		return table[0][str.length - 1];
	}
}
