package com.mashibing.day12;

/**
 * 题目四：
 * 给你一个字符串 s 和一个字符规律 p，请你来实现一个支持 '.' 和 '*' 的正则表达式匹配。
 * '.' 匹配任意单个字符
 * '*' 匹配零个或多个前面的那一个元素
 * 所谓匹配，是要涵盖整个字符串s的，而不是部分字符串。
 * 返回p能否匹配s
 * 数据规模：
 * 1 <= s.length<= 20
 * 1 <= p.length<= 30
 * s只包含从a-z的小写字母。
 * p只包含从a-z的小写字母，以及字符.和*。
 * 保证每次出现字符* 时，前面都匹配到有效的字符
 * <p>
 * 解题思路：
 * 动态规划的样本对应尝试模型
 * <p>
 * 字符串str和字符规律exp
 * dp[i][j]表示str[i及其往后]匹配exp[j及其往后]
 * str[]的si对应exp[]的ei
 * (1)如果ei之后也就是ei + 1不是'*'。那么只有两种情况：
 * 1.str[si] == exp[ei]
 * 2.exp[ei] == '.'
 * 调用f(si + 1, ei + 1)
 * (2)如果ei之后也就是ei + 1是'*'
 * 1.str = "aaaab" exp = "a*aaaab"，此时a*必须等于0个a，所以调用f(si, ei + 2)
 * 2.str = "aaaab" exp = "a*aaab"，此时a*必须等于1个a，所以调用f(si + 1, ei + 2)
 * 3.str = "aaaab" exp = "a*aab"，此时a*必须等于2个a，所以调用f(si + 2, ei + 2)
 * 4.str = "aaaab" exp = "a*ab"，此时a*必须等于3个a，所以调用f(si + 3, ei + 2)
 * 5.str = "aaaab" exp = "a*b"，此时a*必须等于4个a，所以调用f(si + 4, ei + 2)
 * <p>
 * LeetCode测试链接：
 * https://leetcode.cn/problems/regular-expression-matching/
 *
 * @author xcy
 * @date 2022/7/30 - 15:20
 */
public class Code04_RegularExpressionMatch {
	public static void main(String[] args) {

	}

	/**
	 * 使用暴力递归的方式
	 *
	 * @param s 字符串
	 * @param p 表达式
	 * @return 返回p是否能够完全匹配s
	 */
	public static boolean isMatch(String s, String p) {
		if (s == null || p == null) {
			return false;
		}

		char[] str = s.toCharArray();
		char[] exp = p.toCharArray();
		return isValid(str, exp) && process(str, exp, 0, 0);
	}

	/**
	 * @param str str字符串
	 * @param exp exp表达式
	 * @param si  str字符数组的索引
	 * @param ei  exp字符数组的索引
	 * @return 返回exp是否能够完全匹配str
	 */
	public static boolean process(char[] str, char[] exp, int si, int ei) {
		//已经没有表达式可以进行匹配了
		if (ei == exp.length) {
			//那么如果str已经匹配完毕，返回true，否则，返回false
			return si == str.length;
		}
		//ei != exp.length
		//exp[ei + 1] != '*'，有两种情况
		//情况1：ei + 1 == exp.length，表示已经越界了
		//情况2：exp[ei + 1]本身就!= '*
		if (ei + 1 == exp.length || exp[ei + 1] != '*') {
			//si != N表示匹配还没有结束
			//exp[ei] == str[si] || exp[ei] == '.'表示当前str[si]的字符能够和exp[ei]匹配上
			//继续从str[]的si + 1位置开始，从exp[]的ei + 2位置开始
			return si != str.length && (exp[ei] == str[si] || exp[ei] == '.') && process(str, exp, si + 1, ei + 1);
		}
		//ei != exp.length
		//exp[ei + 1] == '*'
		//si != N表示str还没有结束匹配
		//exp[ei] == str[si] || exp[ei] == '.'表示当前str[si]的字符能够和exp[ei]匹配上
		while (si != str.length && (exp[ei] == str[si] || exp[ei] == '.')) {
			//1.str = "aaaab" exp = "a*aaaab"，此时a*必须等于0个a，否则si++，继续调用f(si, ei + 2)
			//2.str = "aaaab" exp = "a*aaab"，此时a*必须等于1个a，否则si++，继续调用f(si, ei + 2)
			//3.str = "aaaab" exp = "a*aab"，此时a*必须等于2个a，否则si++，继续调用f(si, ei + 2)
			//4.str = "aaaab" exp = "a*ab"，此时a*必须等于3个a，否则si++，继续调用f(si, ei + 2)
			//因为sxp[ei + 1] == '*'，所以ei + 2
			if (process(str, exp, si, ei + 2)) {
				return true;
			}
			si++;
		}
		//退出上述while循环之后，有两种情况
		//情况1：si来到最后一个字符，从exp[]ei + 2的位置继续进行匹配
		//5.str = "aaaab" exp = "a*b"，此时a*必须等于4个a，否则si++，继续调用f(si, ei + 2)
		//情况2：str[si] != exp[ei]，那么exp[ei]和exp[ei + 1]的字符直接忽略，从exp[]ei + 2的位置继续进行匹配
		//str = "aaaab"
		//exp = "c*aaaab"
		//那么"c*"直接忽略，直接往后面的ei + 2继续进行匹配
		return process(str, exp, si, ei + 2);
	}


	/**
	 * 使用暴力递归 + 傻缓存的方式
	 *
	 * @param s 字符串
	 * @param p 表达式
	 * @return 返回p是否能够完全匹配s
	 */
	public static boolean isMatchWithCache(String s, String p) {
		if (s == null || p == null) {
			return false;
		}
		char[] str = s.toCharArray();
		char[] exp = p.toCharArray();
		if (!isValid(str, exp)) {
			return false;
		}
		int N = str.length;
		int M = exp.length;
		//dp[i][j] == 0表示没有计算过
		//dp[i][j] == 1表示计算过，返回值是true
		//dp[i][j] == -1表示计算过，返回值是false
		int[][] dp = new int[N + 1][M + 1];
		return processWithCache(str, exp, 0, 0, dp);
	}

	/**
	 * @param str str字符串
	 * @param exp exp表达式
	 * @param si  str字符数组的索引
	 * @param ei  exp字符数组的索引
	 * @return 返回exp是否能够完全匹配str
	 */
	public static boolean processWithCache(char[] str, char[] exp, int si, int ei, int[][] dp) {
		//计算过
		if (dp[si][ei] != 0) {
			//如果dp[si][ei] == 1，返回值true
			//如果dp[si][ei] == -1，返回值false
			return dp[si][ei] == 1;
		}
		boolean ans = false;
		int N = str.length;
		int M = exp.length;
		if (ei == M) {
			ans = si == N;
		} else {
			if (ei + 1 == M || exp[ei + 1] != '*') {
				ans = si != N && (exp[ei] == str[si] || exp[ei] == '.') && processWithCache(str, exp, si + 1, ei + 1, dp);
			} else {
				while (si != N && (exp[ei] == str[si] || exp[ei] == '.')) {
					if (processWithCache(str, exp, si, ei + 2, dp)) {
						ans = true;
					}
					si++;
				}
				ans = ans | processWithCache(str, exp, si, ei + 2, dp);
			}
		}
		dp[si][ei] = ans ? 1 : -1;
		return ans;
	}

	/**
	 * 使用暴力递归 +  傻缓存 + 观察位置优化的方式
	 *
	 * @param s 字符串
	 * @param p 表达式
	 * @return 返回p是否能够完全匹配s
	 */
	public static boolean isMatchWithDynamicProgramming(String s, String p) {
		if (s == null || p == null) {
			return false;
		}
		char[] str = s.toCharArray();
		char[] exp = p.toCharArray();
		if (!isValid(str, exp)) {
			return false;
		}
		int N = str.length;
		int M = exp.length;
		//dp[i][j] == 0表示没有计算过
		//dp[i][j] == 1表示计算过，返回值是true
		//dp[i][j] == -1表示计算过，返回值是false
		int[][] dp = new int[N + 1][M + 1];
		return processWithDynamicProgramming(str, exp, 0, 0, dp);
	}

	/**
	 * @param str str字符串
	 * @param exp exp表达式
	 * @param si  str字符数组的索引
	 * @param ei  exp字符数组的索引
	 * @return 返回exp是否能够完全匹配str
	 */
	public static boolean processWithDynamicProgramming(char[] str, char[] exp, int si, int ei, int[][] dp) {
		//计算过
		if (dp[si][ei] != 0) {
			//如果dp[si][ei] == 1，返回值true
			//如果dp[si][ei] == -1，返回值false
			return dp[si][ei] == 1;
		}
		boolean ans = false;
		int N = str.length;
		int M = exp.length;
		if (ei == M) {
			ans = si == N;
		} else {
			if (ei + 1 == M || exp[ei + 1] != '*') {
				ans = si != N && (exp[ei] == str[si] || exp[ei] == '.') && processWithDynamicProgramming(str, exp, si + 1, ei + 1, dp);
			} else {
				/*while (si != N && (exp[ei] == str[si] || exp[ei] == '.')) {
					if (processWithDynamicProgramming(str, exp, si, ei + 2, dp)) {
						ans = true;
					}
					si++;
				}
				ans = ans | processWithDynamicProgramming(str, exp, si, ei + 2, dp);*/
				//情况1：str还剩下最后一个字符没有进行匹配，从exp[]的ei + 2位置开始继续递归
				if (si == str.length) {
					ans = processWithDynamicProgramming(str, exp, si, ei + 2, dp);
				}
				//情况2：str还剩下很多字符没有进行匹配
				else {
					//如果当前str[si]和exp[ei]的字符没有匹配成功，也就是不相等
					if (str[si] != exp[ei] || exp[ei] != '.') {
						ans = processWithDynamicProgramming(str, exp, si, ei + 2, dp);
					}
					//如果当前str[si]和exp[ei]的字符匹配成功，也就是相等
					else {
						//str =  "a   a   a   a   b"
						//si =   12  13  14  15  16
						//exp =  "a   *   ..."
						//ei =   23  24  25
						//str[si] == exp[ei]
						//            a*表示0个a   a*表示1个a   a*表示2个a   a*表示3个a   a*表示4个a
						//f(12, 23) = f(12, 25) | f(13, 25) | f(14, 25) | f(15, 25) | f(16, 25)
						//f(13, 23) = f(13, 25) | f(13, 25) | f(15, 25) | f(16, 25)
						//f(12, 23) = f(12, 25) | f(13, 23)
						ans = processWithDynamicProgramming(str, exp, si, ei + 2, dp)
								|
								processWithDynamicProgramming(str, exp, si + 1, ei, dp);
					}
				}
			}
		}
		dp[si][ei] = ans ? 1 : -1;
		return ans;
	}



	/**
	 * 对str字符串和exp字符串进行校验
	 *
	 * @param str str字符串不能出现'.'字符和'*'字符
	 * @param exp exp字符串不能在0位置出现'*'字符，以及两个'*'字符不能相邻
	 * @return 如果满足要求，返回true，否则，返回false
	 */
	public static boolean isValid(char[] str, char[] exp) {
		for (char c : str) {
			if (c == '.' || c == '*') {
				return false;
			}
		}
		for (int i = 0; i < exp.length; i++) {
			if (exp[i] == '*' && (i == 0 || exp[i - 1] == '*')) {
				return false;
			}
		}
		return true;
	}
}
