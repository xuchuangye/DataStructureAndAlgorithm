package com.mashibing.day11;

import java.util.ArrayList;
import java.util.List;

/**
 * 题目一：
 * 题目：
 * 问题一：一个字符串至少需要添加多少个字符能整体变成回文串
 * 数据规模：
 * 1 <= s.length <= 500
 * s中所有字符都是小写字母
 * <p>
 * 解题思路：
 * 动态规划的范围尝试模型
 * <p>
 * LeetCode测试链接：
 * https://leetcode.cn/problems/minimum-insertion-steps-to-make-a-string-palindrome/
 * 问题二：返回问题一的其中一种添加结果
 * 问题三：返回问题一的所有添加结果
 *
 * @author xcy
 * @date 2022/7/29 - 8:40
 */
public class Code01_MinimumInsertionStepsToMakeAStringPalindrome {
	public static void main(String[] args) {
		String s = null;
		String ans2 = null;
		List<String> ans3 = null;

		System.out.println("本题第二问，返回其中一种结果测试开始");
		s = "mbadm";

		ans2 = minInsertionsOneWay(s);
		System.out.println(ans2);

		s = "leetcode";
		ans2 = minInsertionsOneWay(s);
		System.out.println(ans2);

		s = "aabaa";
		ans2 = minInsertionsOneWay(s);
		System.out.println(ans2);
		System.out.println("本题第二问，返回其中一种结果测试结束");

		System.out.println();

		System.out.println("本题第三问，返回所有可能的结果测试开始");
		s = "mbadm";
		ans3 = minInsertionsAllWays(s);
		for (String way : ans3) {
			System.out.println(way);
		}
		System.out.println();

		s = "leetcode";
		ans3 = minInsertionsAllWays(s);
		for (String way : ans3) {
			System.out.println(way);
		}
		System.out.println();

		s = "aabaa";
		ans3 = minInsertionsAllWays(s);
		for (String way : ans3) {
			System.out.println(way);
		}
		System.out.println();
		System.out.println("本题第三问，返回所有可能的结果测试结束");
	}

	/**
	 * 问题一：一个字符串至少需要添加多少个字符能整体变成回文串
	 * <p>
	 * 使用动态规划的范围尝试模型的方式
	 *
	 * @param s 原始字符串
	 * @return 返回原始字符串变成回文串，至少需要添加多少个字符
	 */
	public static int minInsertions(String s) {
		if (s == null || s.length() < 2) {
			return 0;
		}
		char[] str = s.toCharArray();
		int N = str.length;
		int[][] dp = new int[N][N];


		//代码优化之前
		/*for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (i == j) {
					dp[i][j] = 0;
				}
				//i == j - 1表示i == j对角线的上一条对角线
				if (i == j - 1) {
					//如果两个字符相等，不需要添加额外的字符，否则需要添加额外的字符
					dp[i][j] = str[i] == str[j] ? 0 : 1;
				}
			}
		}*/
		//代码优化之后
		for (int i = 0; i < N - 1; i++) {
			dp[i][i + 1] = str[i] == str[i + 1] ? 0 : 1;
		}

		//对角线i == j不需要处理
		//对角线i == j - 1也已经填写好了
		//所以i从N - 3开始
		for (int i = N - 3; i >= 0; i--) {
			//对角线i == j不需要处理
			//对角线i + 1 = j也已经填写好了
			//所以j从i + 2开始
			for (int j = i + 2; j < N; j++) {
				//代码优化之前
				/*
				//前面i - 1个字符组成j个回文串，最后一个i字符，在最前面添加一个
				dp[i][j] = dp[i + 1][j] + 1;
				//三种情况：
				//前面i个字符组成j - 1个回文串，最后一个j字符，在最前面添加一个
				if (j - 1 > 0) {
					dp[i][j] = Math.min(dp[i][j], dp[i][j - 1] + 1);
				}
				//如果i字符 == j字符，前面i - 1个字符组成j - 1个回文串
				if (str[i] == str[j] && j - 1 > 0) {
					dp[i][j] = Math.min(dp[i][j], dp[i + 1][j - 1]);
				}*/

				//代码优化之后
				dp[i][j] = (Math.min(dp[i][j - 1], dp[i + 1][j])) + 1;
				if (str[i] == str[j]) {
					dp[i][j] = Math.min(dp[i][j], dp[i + 1][j - 1]);
				}
			}
		}
		return dp[0][N - 1];
	}

	/**
	 * 问题二：返回问题一的其中一种添加结果
	 *
	 * @param s 原始字符串
	 * @return 返回原始字符串变成回文串，至少需要添加多少个字符，其中一种添加结果
	 */
	public static String minInsertionsOneWay(String s) {
		if (s == null || s.length() < 2) {
			return s;
		}

		char[] str = s.toCharArray();
		int N = str.length;
		int[][] dp = new int[N][N];
		for (int i = 0; i < N - 1; i++) {
			dp[i][i + 1] = str[i] == str[i + 1] ? 0 : 1;
		}
		for (int i = N - 3; i >= 0; i--) {
			for (int j = i + 2; j < N; j++) {
				dp[i][j] = (Math.min(dp[i][j - 1], dp[i + 1][j])) + 1;
				if (str[i] == str[j]) {
					dp[i][j] = Math.min(dp[i][j], dp[i + 1][j - 1]);
				}
			}
		}
		int L = 0;
		int R = N - 1;
		//回文串的长度 = 原始字符串的长度 + 变成回文串至少需要添加的字符个数
		char[] ans = new char[N + dp[L][R]];
		int ansL = 0;
		int ansR = N - 1;
		//String s = "abcbb"
		//index    =  01234
		//  0  1  2  3  4
		//0 0  1  2  1  2
		//1    0  1  0  1
		//2       0  1  1
		//3          0  0
		//4             0
		//ans[] = {''}
		while (L < R) {
			//左面一个格子
			//前面L个字符组成R - 1个回文串，最后一个R字符，在最前面添加一个
			if (dp[L][R] == dp[L][R - 1] + 1) {
				//R位置的字符填在最左，R位置的字符填在最右，R--
				ans[ansL++] = str[R];
				ans[ansR--] = str[R--];
			}
			//下面一个格子
			//前面L + 1个字符组成R个回文串，最后一个L字符，在最前面添加一个
			else if (dp[L][R] == dp[L + 1][R] + 1) {
				ans[ansL++] = str[L];
				ans[ansR--] = str[L++];
			}
			//如果L字符 == R字符，前面L + 1个字符组成R - 1个回文串
			else {
				ans[ansL++] = str[L++];
				ans[ansR--] = str[R--];
			}
		}
		//退出上述while循环时，L > R和L == R
		//L > R表示已经填完了
		//举例：
		//"aa"
		// 78
		//假设L == 7，R == 8，此时只剩下7和8这两个位置没有填写，当填完之后，L++，R--，此时如果L > R表示原本该填写的字符已经填写完了
		//L == R表示还有最后一个位置没有填完
		//举例：
		//"aba"
		// 789
		//假设L == 7，R == 9，此时只剩下7和8这两个位置没有填写，当填完之后，L++，R--，此时如果L == R表示只剩下一个字符还没有填写
		if (L == R) {
			ans[ansL] = str[L];
		}
		return new String(ans);
	}

	/**
	 * 问题三：返回问题一的所有添加结果
	 *
	 * @param s
	 * @return
	 */
	public static List<String> minInsertionsAllWays(String s) {
		List<String> ans = new ArrayList<>();
		if (s == null || s.length() < 2) {
			ans.add(s);
		} else {
			//创建并填写dp[][]
			char[] str = s.toCharArray();
			int N = str.length;
			int[][] dp = new int[N][N];
			for (int i = 0; i < N - 1; i++) {
				dp[i][i + 1] = str[i] == str[i + 1] ? 0 : 1;
			}
			for (int i = N - 3; i >= 0; i--) {
				for (int j = i + 2; j < N; j++) {
					dp[i][j] = Math.min(dp[i][j - 1], dp[i + 1][j]) + 1;
					if (str[i] == str[j]) {
						dp[i][j] = Math.min(dp[i][j], dp[i + 1][j - 1]);
					}
				}
			}
			//回文串的长度
			int M = N + dp[0][N - 1];
			char[] path = new char[M];
			process(str, dp, 0, N - 1, path, 0, M - 1, ans);
		}
		return ans;
	}

	/**
	 * 当前来到的动态规划中的格子，(L,R)
	 * path ....  [pl....pr] ....
	 *
	 * @param str  原始字符串的字符数组
	 * @param dp   dp[][]
	 * @param L    字符数组str的L
	 * @param R    字符数组str的R
	 * @param path path
	 * @param pl   path的pl
	 * @param pr   path的pr
	 * @param ans  所有可能的结果
	 */
	public static void process(char[] str, int[][] dp, int L, int R, char[] path, int pl, int pr, List<String> ans) {
		//L > R的情况：
		//举例：
		//"aba"
		//L++，R--时，L和R同时来到相同的位置
		//L == R的情况：
		//举例：
		//"b"
		if (L >= R) {
			if (L == R) {
				path[pl] = str[L];
			}
			ans.add(new String(path));
		}
		//L < R的情况：
		else {
			//前面L个字符组成R - 1个回文串，最后一个R字符，在最前面添加一个
			if (dp[L][R - 1] + 1 == dp[L][R]) {
				//填在path[]最左
				path[pl] = str[R];
				//填在path[]最右
				path[pr] = str[R];
				//填在path[]中间，深度优先遍历
				process(str, dp, L, R - 1, path, pl + 1, pr - 1, ans);
			}
			//前面L + 1个字符组成R个回文串，最后一个L字符，在最前面添加一个
			if (dp[L + 1][R] == dp[L][R] - 1) {
				path[pl] = str[L];
				path[pr] = str[L];
				process(str, dp, L + 1, R, path, pl + 1, pr - 1, ans);
			}
			////如果L字符 == R字符，前面L + 1个字符组成R - 1个回文串
			//L == R - 1表示只有两个字符，举例：3位置和4位置一样的字符"a"，"aa"
			//dp[L + 1][R - 1] == dp[L][R]表示左下角的格子 == 当前的格子
			if (str[L] == str[R] && (L == R - 1 || dp[L + 1][R - 1] == dp[L][R])) {
				path[pl] = str[L];
				path[pr] = str[R];
				process(str, dp, L + 1, R - 1, path, pl + 1, pr - 1, ans);
			}

			//最后不需要去擦除原来位置上的字符，因为会覆盖
			//举例：
			//String s = "ab"
			//index    =  01
			//情况1：先填左边'a'字符
			//path[] = {'a', 'b', 'a'}
			//index =    0    1    2
			//如果遇到情况二：先填右边'b'字符
			//path[] = {'b', 'a', 'b'}
			//index =    0    1    2
		}
	}
}
