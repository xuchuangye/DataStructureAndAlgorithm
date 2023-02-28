package com.mashibing.day20;

import static com.mashibing.common.TestUtils.randomString;

/**
 * 给定一个字符串str，当然可以生成很多子序列，返回有多少个子序列是回文子序列，空序列不算回文
 * 比如，str = “aba”，回文子序列：{a}、{a}、 {a,a}、 {b}、{a,b,a}，返回5
 * <p>
 * 解题思路：
 * 动态规划的范围尝试模型
 * 讨论开头和结尾
 *
 * @author xcy
 * @date 2022/8/13 - 8:33
 */
public class Code04_PalindromeWays {
	public static void main(String[] args) {
		int N = 10;
		int types = 5;
		int testTimes = 100000;
		System.out.println("测试开始！");
		for (int i = 0; i < testTimes; i++) {
			int len = (int) (Math.random() * N);
			String str = randomString(len, types);
			int ans1 = ways1(str);
			int ans2 = returnPalindromeStringSubSequence(str);
			if (ans1 != ans2) {
				System.out.println(str);
				System.out.println(ans1);
				System.out.println(ans2);
				System.out.println("测试失败！");
				break;
			}
		}
		System.out.println("测试结束！");
	}

	public static int ways1(String str) {
		if (str == null || str.length() == 0) {
			return 0;
		}
		char[] s = str.toCharArray();
		char[] path = new char[s.length];
		return process(str.toCharArray(), 0, path, 0);
	}

	public static int process(char[] s, int si, char[] path, int pi) {
		if (si == s.length) {
			return isP(path, pi) ? 1 : 0;
		}
		int ans = process(s, si + 1, path, pi);
		path[pi] = s[si];
		ans += process(s, si + 1, path, pi + 1);
		return ans;
	}

	public static boolean isP(char[] path, int pi) {
		if (pi == 0) {
			return false;
		}
		int L = 0;
		int R = pi - 1;
		while (L < R) {
			if (path[L++] != path[R--]) {
				return false;
			}
		}
		return true;
	}

	/**
	 * dp[L][R] str[L ~ R]有多少个回文串，分为四种情况：
	 * 情况1：
	 * 回文子序列不选择L位置的字符，也不选择R位置的字符  -> 不选择[L]位置的字符，不选择[R]位置的字符，回文子序列个数为a
	 * 情况2：
	 * 回文子序列不选择L位置的字符，选择R位置的字符     -> 不选择[L]位置的字符，选择[R]位置的字符，回文子序列个数为b
	 * 情况3：
	 * 回文子序列选择L位置的字符，不选择R位置的字符     -> 选择[L]位置的字符，不选择[R]位置的字符，回文子序列个数为c
	 * 情况4：
	 * 回文子序列既选择L位置的字符，也选择R位置的字符   -> 选择[L]位置的字符，选择[R]位置的字符，回文子序列个数为d，并且L位置字符 == R位置字符
	 * <p>
	 * 1.
	 * dp[L + 1][R]表示str[L + 1 ~ R]有多少个回文串，一定没有选择L位置的字符：情况1 + 情况2 = a + b
	 * 举例：
	 * String s = "x   a   a"
	 * index =     L  L+1  R
	 * 情况1：不选择[L]位置的字符，不选择[R]位置的字符，选择[L + 1]位置的字符，回文子序列 = "a"
	 * 情况2：不选择[L]位置的字符，选择[R]位置的字符，回文子序列 = "a"
	 * <p>
	 * 2.
	 * dp[L][R - 1]表示str[L ~ R - 1]有多少个回文串，一定没有选择R位置的字符：情况1 + 情况3 = a + c
	 * 举例：
	 * String s = "a   a   x"
	 * index =     L  L+1  R
	 * 情况1：不选择[L]位置的字符，不选择[R]位置的字符，选择[L + 1]位置的字符，回文子序列 = "a"
	 * 情况2：选择[L]位置的字符，不选择[R]位置的字符，回文子序列 = "a"
	 * <p>
	 * 3.
	 * dp[L + 1][R - 1]str[L + 1 ~ R - 1]有多少个回文串，一定没有选择L位置和R位置的字符：情况1 + 情况2 + 情况3 = a + b + c
	 * 所以当L位置的字符 != R位置的字符时，dp[L + 1][R] + dp[L][R - 1] - dp[L + 1][R - 1]
	 * 举例：
	 * String s = "a  c  a  c"
	 * index =     0  1  2  3
	 * 1)不选择0位置字符，不选择3位置字符，回文子序列 {1, "c"}{2, "a"}
	 * 2)一定选择0位置字符，不选择3位置字符，回文子序列 {0, "a"}{012, "aca"}{02, "aa"}
	 * 3)不选择0位置字符，一定选择3位置字符，回文子序列 {3, "c"}{123, "cac"}{13, "cc"}
	 * 0 ~ 2的回文子序列{1, "c"}{2, "a"}{0, "a"}{012, "aca"}{02, "aa"}
	 * 1 ~ 3的回文子序列{1, "c"}{2, "a"}{3, "c"}{123, "cac"}{13, "cc"}
	 * 重复的是{1, "c"}{2, "a"}，也就是0 ~ 3的回文子序列
	 * 总结：
	 * 1.
	 * 当L位置字符 != R位置字符，包含情况1，情况2，情况3，不包含情况4 = a + b + c
	 * 不选择L一定选择R的情况 + 一定选择L不选择R的情况 - 不选择L不选择R的情况
	 * dp[L][R} = dp[L + 1][R] + dp[L][R - 1] - dp[L + 1][R - 1]
	 * 2.
	 * 当L位置字符 == R位置字符时，L + 1 ~ R - 1范围上必定能够组成情况4：dp[L + 1][R - 1]，
	 * 所以L + 1 ~ R - 1范围上有多少个回文子序列，L ~ R范围上就有多少个回文子序列
	 * 还有一种情况：即使L + 1 ~ R - 1范围上是空串，但是因为L位置字符 == R位置字符，所以L ~ R范围上至少有1个回文子序列，所以最后依然需要加1
	 * dp[L][R] = dp[L + 1][R] + dp[L][R - 1] - dp[L + 1][R - 1] + dp[L + 1][R - 1] + 1
	 *
	 * @param s
	 * @return
	 */
	public static int returnPalindromeStringSubSequence(String s) {
		if (s == null || s.length() == 0) {
			return 0;
		}
		char[] str = s.toCharArray();
		int N = str.length;
		int[][] dp = new int[N][N];
		//L和R处于同一个位置，都是同一个字符，回文子序列的个数为1
		for (int L = 0; L < N; L++) {
			dp[L][L] = 1;
		}
		//a   a
		//L  L+1
		//L = L + 1
		//L位置的回文子序列"a"，L + 1位置的回文子序列"a"，L和L + 1位置的回文子序列"aa"，回文子序列的个数为3
		//a   c
		//L  L+1
		//L != L + 1
		//L位置的回文子序列"a"，L + 1位置的回文子序列"c"，回文子序列的个数为2

		//{"a"}, {"a"}, {"aa"}
		for (int L = 0; L < N - 1; L++) {
			dp[L][L + 1] = str[L] == str[L + 1] ? 3 : 2;
		}

		//-  0  1  2  3  -> R
		//-0 1  √     ☆
		//-1    1  √
		//-2       1  √
		//-3          1
		//L
		//√表示填写过了，☆表示需要返回的结果
		for (int L = N - 3; L >= 0; L--) {
			for (int R = L + 2; R < N; R++) {
				//情况1：str[L] != str[R]
				//包含：
				//1.不选择L位置字符，不选择R位置字符
				//2.不选择L位置字符，一定选择R位置字符
				//3.一定选择L位置字符，不选择R位置字符
				//不包含：
				//1.一定选择L位置字符，一定选择R位置字符
 				dp[L][R] = dp[L + 1][R] + dp[L][R - 1] - dp[L + 1][R - 1];
 				//情况2：str[L] == str[R]
				//包含：
				//1.L + 1 ~ R - 1范围内的回文子序列个数就是L ~ R范围内的回文子序列个数
				//2.即使是L + 1 ~ R - 1范围内是空串，但是因为str[L] == str[R}，所以仍然还有1种回文子序列，需要加1
				if (str[L] == str[R]) {
					dp[L][R] += dp[L + 1][R - 1] + 1;
				}
			}
		}
		return dp[0][N - 1];
	}
}
