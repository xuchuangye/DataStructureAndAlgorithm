package com.mashibing.day12;

import com.mashibing.common.TestUtils;

import java.util.Arrays;

/**
 * 题目一：
 * 给定长度为m的字符串aim，以及一个长度为n的字符串str，问能否在str中找到一个长度为m的连续子串，
 * 使得这个子串刚好由aim的m个字符组成，顺序无所谓，返回任意满足条件的一个子串的起始位置，未找到返回-1
 * <p>
 * LeetCode测试链接：
 * https://leetcode.cn/problems/permutation-in-string/
 *
 * @author xcy
 * @date 2022/7/30 - 15:19
 */
public class Code01_ContainAllCharExactly {
	public static void main(String[] args) {
		int possibilities = 5;
		int strMaxSize = 5;
		int aimMaxSize = 3;
		int testTimes = 5;
		System.out.println("test begin, test time : " + testTimes);
		for (int i = 0; i < testTimes; i++) {
			String str = TestUtils.getRandomString(possibilities, strMaxSize);
			String aim = TestUtils.getRandomString(possibilities, aimMaxSize);
			int ans1 = containExactly1(str, aim);
			int ans2 = containExactly2(str, aim);
			int ans3 = containExactly3(str, aim);
			if (ans1 != ans2 || ans2 != ans3) {
				System.out.println("Oops!");
				System.out.println(str);
				System.out.println(aim);
				System.out.println(ans1);
				System.out.println(ans2);
				System.out.println(ans3);
				break;
			}
			boolean checkInclusion = checkInclusion1(str, aim);
			System.out.println("str : " + str);
			System.out.println("aim : " + aim);
			System.out.println(checkInclusion);
		}
		System.out.println("test finish");
	}

	/**
	 * 暴力解法
	 *
	 * @param s
	 * @param a
	 * @return
	 */
	public static int containExactly1(String s, String a) {
		if (s == null || a == null || s.length() < a.length()) {
			return -1;
		}
		char[] aim = a.toCharArray();
		Arrays.sort(aim);
		String aimSort = String.valueOf(aim);
		for (int L = 0; L < s.length(); L++) {
			for (int R = L; R < s.length(); R++) {
				char[] cur = s.substring(L, R + 1).toCharArray();
				Arrays.sort(cur);
				String curSort = String.valueOf(cur);
				if (curSort.equals(aimSort)) {
					return L;
				}
			}
		}
		return -1;
	}

	public static int containExactly2(String s, String a) {
		if (s == null || a == null || s.length() < a.length()) {
			return -1;
		}
		char[] str = s.toCharArray();
		char[] aim = a.toCharArray();
		for (int L = 0; L <= str.length - aim.length; L++) {
			if (isCountEqual(str, L, aim)) {
				return L;
			}
		}
		return -1;
	}

	public static boolean isCountEqual(char[] str, int L, char[] aim) {
		int[] count = new int[256];
		for (int i = 0; i < aim.length; i++) {
			count[aim[i]]++;
		}
		for (int i = 0; i < aim.length; i++) {
			if (count[str[L + i]]-- == 0) {
				return false;
			}
		}
		return true;
	}

	/**
	 * @param s1 长度为N的字符串str
	 * @param s2 长度为M的字符串aim
	 * @return
	 */
	public static int containExactly3(String s1, String s2) {
		if (s1 == null || s2 == null || s1.length() < s2.length()) {
			return -1;
		}
		//长度为N的字符串str
		char[] str1 = s1.toCharArray();
		int N = str1.length;
		//长度为M的字符串aim
		char[] str2 = s2.toCharArray();
		int M = str2.length;
		//ASCII码是256个字符，所以count[]申请的长度为256
		//统计str2字符串的字符种类以及字符个数
		int[] count = new int[256];
		//举例：
		//假设str2 = "ccaba"
		//c : 2
		//b : 1
		//a : 2
		for (char aChar : str2) {
			count[aChar]++;
		}
		int all = M;
		int R = 0;
		//首次生成窗口：0 ~ M - 1
		//最早的M个字符，让其窗口初步形成
		for (; R < M; R++) {
			//判断str1是否出现过str2统计的字符种类以及字符个数之前，先判断是否大于0，如果大于0，证明是有效的
			//否则就是无效的
			//而且这个--比较秒，后--是先判断后运算，表示count[str1[R]]在--之前是大于0的
			if (count[str1[R]]-- > 0) {
				all--;
			}
		}
		//窗口继续往右移动
		for (; R < N; R++) {
			//表示str2统计的字符种类以及字符个数都解决完了，返回此时R的位置 - M的长度，就是满足条件的一个子串的起始位置
			if (all == 0) {
				return R - M;
			}
			//右边进一个字符
			//如果all != 0，表示没有解决完
			if (count[str1[R]]-- > 0) {
				all--;
			}

			//左边出一个字符

			//窗口左边的字符需要移出去，移出去之后，当前移出去的字符统计次数++
			//表示当前就已经超出str2统计字符的个数了
			//举例：
			//str1 = "c    ccbabacbca"
			//index = 0    123456789
			//      R - M      R

			//str2 = "ccaba"  count[] = {c : 2  b : 1  a : 2}
			//第一次：R == 0，窗口将0位置的c括进来，count[] = {c : 1  b : 1  a : 2}，all--
			//第二次：R == 1，窗口将1位置的c括进来，count[] = {c : 0  b : 1  a : 2}，all--
			//第三次：R == 2，窗口将2位置的c括进来，count[] = {c : -1  b : 1  a : 2}，all不变
			//第四次：R == 3，窗口将3位置的b括进来，count[] = {c : -1  b : 0  a : 2}，all--
			//第五次：R == 4，窗口将4位置的a括进来，count[] = {c : -1  b : 0  a : 1}，all--
			//窗口的长度为str2的长度 -> 5，此时R来到5的位置时，左边的c要移出窗口，如果之前已经是负数，那么all不变
			//如果之前还不是负数，那么all++
			//所以此时的R - M位置(0位置)的字符统计在++之前还是非负数，那么all++
			if (count[str1[R - M]]++ >= 0) {
				all++;
			}
		}
		//N == str1.length
		//退出上述的for循环之后，R == N，那么还有最后一次的窗口(N - 1 ~ N - 1 - M)还没有判断
		return all == 0 ? R - M : -1;
	}

	public static boolean checkInclusion1(String s1, String s2) {
		return containExactly3(s2, s1) != -1;
	}

	/**
	 * LeetCode测试链接：
	 * https://leetcode.cn/problems/permutation-in-string/
	 *
	 * @param s1
	 * @param s2
	 * @return
	 */
	public static boolean checkInclusion2(String s1, String s2) {
		if (s1 == null || s2 == null || s1.length() == 0 || s2.length() == 0) {
			return false;
		}
		//长度为N的字符串str
		char[] str1 = s1.toCharArray();
		int N = str1.length;
		//长度为M的字符串aim
		char[] str2 = s2.toCharArray();
		int M = str2.length;
		//ASCII码是256个字符，所以count[]申请的长度为256
		//统计str2字符串的字符种类以及字符个数
		int[] count = new int[256];
		//举例：
		//假设str2 = "ccaba"
		//c : 2
		//b : 1
		//a : 2
		if (N >= M) {
			for (char aChar : str2) {
				count[aChar]++;
			}
		}else {
			for (char aChar : str1) {
				count[aChar]++;
			}
		}

		int all = N;

		int R = 0;
		//首次生成窗口：0 ~ M - 1
		//最早的M个字符，让其窗口初步形成
		if (N >= M) {
			for (; R < M; R++) {
				//判断str1是否出现过str2统计的字符种类以及字符个数之前，先判断是否大于0，如果大于0，证明是有效的
				//否则就是无效的
				//而且这个--比较秒，后--是先判断后运算，表示count[str1[R]]在--之前是大于0的
				if (count[str1[R]]-- > 0) {
					all--;
				}
			}
			//窗口继续往右移动
			for (; R < N; R++) {
				//表示str2统计的字符种类以及字符个数都解决完了，返回此时R的位置 - M的长度，就是满足条件的一个子串的起始位置
				if (all == 0) {
					return true;
				}
				//右边进一个字符
				//如果all != 0，表示没有解决完
				if (count[str1[R]]-- > 0) {
					all--;
				}

				//左边出一个字符

				//窗口左边的字符需要移出去，移出去之后，当前移出去的字符统计次数++
				//表示当前就已经超出str2统计字符的个数了
				//举例：
				//str1 = "c    ccbabacbca"
				//index = 0    123456789
				//      R - M      R

				//str2 = "ccaba"  count[] = {c : 2  b : 1  a : 2}
				//第一次：R == 0，窗口将0位置的c括进来，count[] = {c : 1  b : 1  a : 2}，all--
				//第二次：R == 1，窗口将1位置的c括进来，count[] = {c : 0  b : 1  a : 2}，all--
				//第三次：R == 2，窗口将2位置的c括进来，count[] = {c : -1  b : 1  a : 2}，all不变
				//第四次：R == 3，窗口将3位置的b括进来，count[] = {c : -1  b : 0  a : 2}，all--
				//第五次：R == 4，窗口将4位置的a括进来，count[] = {c : -1  b : 0  a : 1}，all--
				//窗口的长度为str2的长度 -> 5，此时R来到5的位置时，左边的c要移出窗口，如果之前已经是负数，那么all不变
				//如果之前还不是负数，那么all++
				//所以此时的R - M位置(0位置)的字符统计在++之前还是非负数，那么all++
				if (count[str1[R - M]]++ >= 0) {
					all++;
				}
			}
		}
		//首次生成窗口：0 ~ N - 1
		//最早的N个字符，让其窗口初步形成
		else {
			for (; R < N; R++) {
				//判断str2是否出现过str1统计的字符种类以及字符个数之前，先判断是否大于0，如果大于0，证明是有效的
				//否则就是无效的
				//而且这个--比较秒，后--是先判断后运算，表示count[str2[R]]在--之前是大于0的
				if (count[str2[R]]-- > 0) {
					all--;
				}
			}
			//窗口继续往右移动
			for (; R < M; R++) {
				//表示str2统计的字符种类以及字符个数都解决完了，返回此时R的位置 - M的长度，就是满足条件的一个子串的起始位置
				if (all == 0) {
					return true;
				}
				//右边进一个字符
				//如果all != 0，表示没有解决完
				if (count[str2[R]]-- > 0) {
					all--;
				}

				//左边出一个字符

				//窗口左边的字符需要移出去，移出去之后，当前移出去的字符统计次数++
				//表示当前就已经超出str2统计字符的个数了
				//举例：
				//str1 = "c    ccbabacbca"
				//index = 0    123456789
				//      R - M      R

				//str2 = "ccaba"  count[] = {c : 2  b : 1  a : 2}
				//第一次：R == 0，窗口将0位置的c括进来，count[] = {c : 1  b : 1  a : 2}，all--
				//第二次：R == 1，窗口将1位置的c括进来，count[] = {c : 0  b : 1  a : 2}，all--
				//第三次：R == 2，窗口将2位置的c括进来，count[] = {c : -1  b : 1  a : 2}，all不变
				//第四次：R == 3，窗口将3位置的b括进来，count[] = {c : -1  b : 0  a : 2}，all--
				//第五次：R == 4，窗口将4位置的a括进来，count[] = {c : -1  b : 0  a : 1}，all--
				//窗口的长度为str2的长度 -> 5，此时R来到5的位置时，左边的c要移出窗口，如果之前已经是负数，那么all不变
				//如果之前还不是负数，那么all++
				//所以此时的R - N位置(0位置)的字符统计在++之前还是非负数，那么all++
				if (count[str2[R - N]]++ >= 0) {
					all++;
				}
			}
		}
		//N == str1.length
		//退出上述的for循环之后，R == N，那么还有最后一次的窗口(N - 1 ~ N - 1 - M)还没有判断
		return all == 0;
	}
}
