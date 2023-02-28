package com.mashibing.day11;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * 题目二：
 * 题目：
 * 问题一：一个字符串至少要切几刀能让切出来的子串都是回文串
 * 解题思路：
 * 动态规划的从左往右的尝试模型
 *
 *
 * <p>
 * 问题二：返回问题一的其中一种划分结果
 * 问题三：返回问题一的所有划分结果
 *
 * @author xcy
 * @date 2022/7/29 - 8:40
 */
public class Code02_PalindromePartitioningII {
	public static void main(String[] args) {
		String s = null;
		List<String> ans2 = null;
		List<List<String>> ans3 = null;

		System.out.println("本题第二问，返回其中一种结果测试开始");
		s = "abacbc";
		ans2 = minCutOneWay(s);
		for (String str : ans2) {
			System.out.print(str + " ");
		}
		System.out.println();
		System.out.println("-----");

		s = "aabccbac";
		ans2 = minCutOneWay(s);
		for (String str : ans2) {
			System.out.print(str + " ");
		}
		System.out.println();
		System.out.println("-----");

		s = "aabaa";
		ans2 = minCutOneWay(s);
		for (String str : ans2) {
			System.out.print(str + " ");
		}
		System.out.println();
		System.out.println("-----");
		System.out.println("本题第二问，返回其中一种结果测试结束");
		System.out.println("-----");
		System.out.println("本题第三问，返回所有可能结果测试开始");
		s = "bb";
		ans3 = minCutAllWays(s);
		for (List<String> way : ans3) {
			for (String str : way) {
				System.out.print(str + " ");
			}
			System.out.println();
		}
		System.out.println("-----");

		s = "aaaaaa";
		ans3 = minCutAllWays(s);
		for (List<String> way : ans3) {
			for (String str : way) {
				System.out.print(str + " ");
			}
			System.out.println();
		}
		System.out.println("-----");

		s = "fcfffcffcc";
		ans3 = minCutAllWays(s);
		for (List<String> way : ans3) {
			for (String str : way) {
				System.out.print(str + " ");
			}
			System.out.println();
		}
		System.out.println("-----");
		System.out.println("本题第三问，返回所有可能结果测试结束");
		List<List<String>> lists = minCutAllWays("bbc");
	}

	public static int minCutWithRecursion(String s) {
		if (s == null || s.length() < 2) {
			return 0;
		}

		return process(s.toCharArray(), 0);
	}

	/**
	 * @param str   原始字符串的字符数组
	 * @param index 当前字符所在的位置
	 * @return
	 */
	public static int process(char[] str, int index) {
		int next = Integer.MIN_VALUE;
		int N = str.length;
		boolean[][] checkMap = createCheckMap(str, N);
		//已经没有后续字符了，能够切分的部分为0
		if (index == str.length) {
			return 0;
		} else {
			//为什么不是end < str.length - 1，因为BaseCase已经将i == str.length这个条件给写上了
			for (int end = index; end < str.length; end++) {
				if (checkMap[index][end]) {
					next = Math.max(next, process(str, end + 1));
				}
			}
		}
		return next;
	}

	/**
	 * 使用动态规划的方式
	 *
	 * @param s 原始字符串
	 * @return 返回切分整个字符串之后，让每一个部分都变成回文串，至少需要切多少刀
	 */
	public static int minCutWithDynamicProgramming(String s) {
		if (s == null || s.length() < 2) {
			return 0;
		}
		char[] str = s.toCharArray();
		int N = str.length;
		boolean[][] checkMap = createCheckMap(str, N);

		int[] dp = new int[N + 1];
		dp[N] = 0;
		//dp[N - 1]
		//dp[N - 2]
		//...
		//dp[0]
		for (int i = N - 1; i >= 0; i--) {
			//i ... N - 1整体是回文串，返回一个部分
			if (checkMap[i][N - 1]) {
				dp[i] = 1;
			} else {
				int next = Integer.MIN_VALUE;
				//为什么不是end < str.length - 1，因为BaseCase已经将i == str.length这个条件给写上了
				for (int j = i; j < N; j++) {
					if (checkMap[i][j]) {
						next = Math.max(next, dp[j + 1]);
					}
				}
				dp[i] = next + 1;
			}
		}
		return dp[0] - 1;
	}

	/**
	 * 创建dp[L][R]L ... R范围内的字符串是否是回文串的二维数组，如果是，值是true，如果不是，返回false
	 *
	 * @param str 原始字符串的字符数组
	 * @param N   字符数组的长度
	 * @return 返回dp[][]
	 */
	public static boolean[][] createCheckMap(char[] str, int N) {
		boolean[][] ans = new boolean[N][N];
		//String str = "abaca"
		//index =       01234
		//  0  1  2  3  4   i
		//0 T  F
		//1    T  F
		//2       T  F
		//3          T  F
		//4
		//
		//j
		for (int i = 0; i < N - 1; i++) {
			ans[i][i] = true;
			ans[i][i + 1] = str[i] == str[i + 1];
		}
		//              4   i
		//4             T
		//
		//j
		ans[N - 1][N - 1] = true;
		for (int i = N - 3; i >= 0; i--) {
			for (int j = i + 2; j < N; j++) {
				ans[i][j] = str[i] == str[j] && ans[i + 1][j - 1];
			}
		}
		return ans;
	}

	/**
	 * 问题二：返回问题一的其中一种划分结果
	 *
	 * @param s
	 * @return
	 */
	public static List<String> minCutOneWay(String s) {
		List<String> ans = new LinkedList<>();
		if (s == null || s.length() < 2) {
			ans.add(s);
		} else {
			char[] str = s.toCharArray();
			int N = str.length;
			//dp[]表示以当前位置开始到结尾位置，至少要切多少刀，并且切出的每一个部分都是回文串
			//String s = "a a e e f f c f f x"
			//index =     0 1 2 3 4 5 6 7 8 9
			//dp[] = {    3 3 2 2 1 2 2 1 1 0}
			//index =     0 1 2 3 4 5 6 7 8 9
			//8位置到9位置，切1刀，"f"是回文串
			//7位置到9位置，切1刀，"ff"是回文串
			//6位置到9位置，切2刀，"c"和"ff"是回文串
			//5位置到9位置，切2刀，"fcf"和"f"是回文串
			//4位置到9位置，切1刀，"ffcff是回文串
			//3位置到9位置，切2刀，"e"和"ffcff是回文串
			//2位置到9位置，切2刀，"ee"和"ffcff是回文串
			//1位置到9位置，切3刀，"a"和"ee"以及"ffcff是回文串
			//0位置到9位置，切3刀，"aa"和"ee"以及"ffcff是回文串
			//N + 1表示在结尾多加一个位置9，为了前面能够至少切出2部分，也就是1刀
			int[] dp = new int[N + 1];
			dp[N] = 0;
			//检查当前i到j的范围内是否是回文串
			boolean[][] checkMap = createCheckMap(str, N);
			for (int i = N - 1; i >= 0; i--) {
				//如果i到N - 1范围内整体是回文串，至少切出2部分，也就是1刀
				if (checkMap[i][N - 1]) {
					dp[i] = 1;
				}
				//否则如果i到N - 1范围内整体不是回文串
				else {
					int next = Integer.MAX_VALUE;
					//判断i到j范围内整体是回文串
					for (int j = i; j < N; j++) {
						//继续从j + 1位置开始
						next = Math.min(next, dp[j + 1]);
					}
					//至少切出next + 1个部分
					dp[i] = next + 1;
				}
			}

			//dp[]在最后多加了一个位置，所以j从1开始到N结束
			//N = 7
			//String s = "a b a c f c k x"
			//index =     0 1 2 3 4 5 6 7
			//dp[] =     {3 4 3 2 3 2 1 0}
			//index =     0 1 2 3 4 5 6 7
			//i = 0, j = 1
			//第一步，0 ~ 0范围上是回文串，但是dp[i] != dp[j] + 1，j++，j = 2
			//第二步，0 ~ 1范围上不是回文串，j++，j = 3
			//第三步，0 ~ 2范围上是回文串，dp[i] = 3，dp[j] = 2，满足dp[i] = dp[j] + 1
			//将0 ~ 2范围上的回文串截取出来，添加到list中，i = 3，j++，j = 4
			//第四步，3 ~ 3范围上不是回文串，j++，j = 5
			//第五步，3 ~ 4范围上不是回文串，j++，j = 6
			//第六步，3 ~ 5范围上是回文串，dp[i] = 2，dp[j] = 1，满足dp[i] = dp[j] + 1
			//将3 ~ 5范围上的回文串截取出来，添加到list中，i = 6，j++，j = 7
			//第七步，6 ~ 6范围上是回文串，dp[i] = 1，dp[j] = 1，满足dp[i] = dp[j] + 1
			//将6 ~ 6范围上的回文串截取出来，添加到list中，i = 7，j++，j = 8，j不满足 <= N，退出循环
			for (int i = 0, j = 1; j <= N; j++) {
				//如果i到j - 1范围内是回文串并且dp[i] == dp[j] + 1
				if (checkMap[i][j - 1] && dp[i] == dp[j] + 1) {
					//截取出i到j - 1的字符串
					ans.add(s.substring(i, j));
					i = j;
				}
			}
		}
		return ans;
	}

	/**
	 * 问题三：返回问题一的所有划分结果
	 *
	 * @param s
	 * @return
	 */
	public static List<List<String>> minCutAllWays(String s) {
		List<List<String>> ans = new ArrayList<>();
		if (s == null || s.length() < 2) {
			List<String> cur = new ArrayList<>();
			cur.add(s);
			ans.add(cur);
		} else {
			char[] str = s.toCharArray();
			int N = str.length;
			boolean[][] checkMap = createCheckMap(str, N);
			int[] dp = new int[N + 1];
			dp[N] = 0;
			for (int i = N - 1; i >= 0; i--) {
				if (checkMap[i][N - 1]) {
					dp[i] = 1;
				} else {
					int next = Integer.MAX_VALUE;
					for (int j = i; j < N; j++) {
						if (checkMap[i][j]) {
							next = Math.min(next, dp[j + 1]);
						}
					}
					dp[i] = next + 1;
				}
			}
			process(s, 0, 1, checkMap, dp, new ArrayList<>(), ans);
		}
		return ans;
	}

	public static void process(String s, int i, int j, boolean[][] checkMap, int[] dp, List<String> path, List<List<String>> ans) {
		if (j == s.length()) {
			if (checkMap[i][j - 1] && dp[i] == dp[j] + 1) {
				path.add(s.substring(i, j));
				//因为后续的path这个list会被修改，而ans只是添加了 一个地址，地址没有变，但是地址对应的list变了，所以需要拷贝path这个原始的list
				ans.add(copyForStringList(path));
				//删除并且还原
				path.remove(path.size() - 1);
			}
		} else {
			if (checkMap[i][j - 1] && (dp[i] == dp[j] + 1)) {
				path.add(s.substring(i, j));
				//i == j, j == j + 1
				process(s, j, j + 1, checkMap, dp, path, ans);
				//删除并且还原
				path.remove(path.size() - 1);
			}
			process(s, i, j + 1, checkMap, dp, path, ans);
		}
	}

	/**
	 * 拷贝list
	 *
	 * @param list 原始list
	 * @return 拷贝之后的list
	 */
	public static List<String> copyForStringList(List<String> list) {
		return new ArrayList<>(list);
	}
}
