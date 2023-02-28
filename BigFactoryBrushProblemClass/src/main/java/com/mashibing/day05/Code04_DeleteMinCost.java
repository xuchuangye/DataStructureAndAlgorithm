package com.mashibing.day05;

import com.mashibing.common.TestUtils;

import java.util.*;

/**
 * 题目四：
 * 给定两个字符串s1和s2，问s2最少删除多少字符可以成为s1的子串？
 * 比如 s1 = "abcde"，s2 = "axbc"，s2删掉'x'即可，返回1
 * <p>
 * 解题思路：
 * 使用动态规划
 *
 * @author xcy
 * @date 2022/7/18 - 16:23
 */
public class Code04_DeleteMinCost {
	public static void main(String[] args) {
		/*String word1 = "abcde", word2 = "acd";
		int cost1 = minCost(word1, word2);
		int cost2 = minCost(word1, word2);
		int cost3 = minCost2(word1, word2);
		int cost4 = minCost3(word1, word2);
		int cost5 = minCost3OptimalSolution(word1, word2);
		System.out.println(cost1);
		System.out.println(cost2);
		System.out.println(cost3);
		System.out.println(cost4);
		System.out.println(cost5);*/

		char[] x = {'a', 'b', 'c', 'd'};
		char[] y = {'a', 'd'};

		System.out.println(onlyDelete(x, y));

		int str1Len = 20;
		int str2Len = 10;
		int v = 5;
		int testTime = 10000;
		boolean pass = true;
		System.out.println("test begin");
		for (int i = 0; i < testTime; i++) {
			String str1 = TestUtils.generateRandomString(str1Len, v);
			String str2 = TestUtils.generateRandomString(str2Len, v);
			int ans1 = minCost1(str1, str2);
			int ans2 = minCost2(str1, str2);
			int ans3 = minCost3(str1, str2);
			int ans4 = minCost4(str1, str2);
			if (ans1 != ans2 || ans1 != ans3 || ans1 != ans4) {
				pass = false;
				System.out.println("str1 : " + str1);
				System.out.println("str2 : " + str2);
				System.out.println("minCost1 : " + ans1);
				System.out.println("minCost3 : " + ans2);
				System.out.println("minCost3OptimalSolution : " + ans3);
				System.out.println("minCost4 : " + ans4);
				break;
			}
		}
		System.out.println("test pass : " + pass);
	}

	/**
	 * 字符串长度的比较器，字符串长度越大的排前面
	 */
	public static class LengthComparator implements Comparator<String> {
		@Override
		public int compare(String o1, String o2) {
			return o2.length() - o1.length();
		}
	}

	/**
	 * 如果s2的字符串长度非常短，也就是M的值非常小，此方法是最优解
	 * <p>
	 * 时间复杂度：O(2的M次方 * M * N) -> M的值比较小，O(2的M次方 * N)
	 * s2字符串生成所有子串的时间复杂度：O(2的M次方)，M是s2字符串的长度
	 * 生成的每个子串的长度范围是0 ~ M
	 * 每个子串和s1使用KMP算法进行比较，时间复杂度：O(N)
	 * <p>
	 * 求出s2所有的子序列，然后按照长度排序，长度大的排在前面。
	 * 然后考察哪个子序列字符串和s1的某个子串相等(KMP)，答案就出来了。
	 * 分析：
	 * 因为题目原本的样本数据中，有特别说明s2的长度很小。所以这么做也没有太大问题，也几乎不会超时。
	 * 但是如果某一次考试给定的s2长度远大于s1，这么做就不合适了。
	 *
	 * @param s1
	 * @param s2
	 * @return
	 */
	public static int minCost1(String s1, String s2) {
		//生成s2字符串的所有子序列
		List<String> s2SubSequence = new ArrayList<>();
		process(s2.toCharArray(), 0, "", s2SubSequence);
		//按照字符串长度从大到小进行排序
		s2SubSequence.sort(new LengthComparator());
		for (String str : s2SubSequence) {
			if (s1.contains(str)) {
				return s2.length() - str.length();
			}
		}
		return s2.length();
	}

	public static void process(char[] str2, int index, String path, List<String> list) {
		if (index == str2.length) {
			list.add(path);
			return;
		}
		process(str2, index + 1, path, list);
		process(str2, index + 1, path + str2[index], list);
	}

	/**
	 * 利用最短编辑距离问题的解题思路，让x字符串只通过删除的方式，变到y字符串
	 * <p>
	 * 如果s2的字符串长度非常长，M(s2的字符串长度)的值非常大，此方法是较优解
	 * 并且使用此方法推导出时间复杂度：O(N的2次方 * M)的最优解方法
	 *
	 * @param x s1字符串
	 * @param y s2字符串
	 * @return 返回x字符串至少要删几个字符才能变成y字符串，如果变不成，返回Integer.MAX_VALUE
	 */
	private static int onlyDelete(char[] x, char[] y) {
		//如果s2字符串的长度大于s1的字符串，没有s1没有办法删除字符变成s2
		if (x.length < y.length) {
			return Integer.MAX_VALUE;
		}
		//s1的字符串长度
		int N = x.length;
		//s2的字符串长度
		int M = y.length;
		int[][] dp = new int[N + 1][M + 1];

		for (int xLen = 0; xLen <= N; xLen++) {
			for (int yLen = 0; yLen <= M; yLen++) {
				//如果s1和s2字符串长度相等，但字符串不一样，s1删除0个字符变成s2
				//如果s1的字符串长度大于s2的字符串长度，之后的for循环会进行处理
				//如果s1的字符串长度小于s2的字符串长度，s1字符串无法删除操作变成s2，所以值无效
				dp[xLen][yLen] = Integer.MAX_VALUE;
			}
		}
		dp[0][0] = 0;

		for (int xLen = 1; xLen <= N; xLen++) {
			dp[xLen][0] = xLen;
		}
		//x[]表示s1字符串，y[]表示s2字符串
		for (int xLen = 1; xLen <= N; xLen++) {
			for (int yLen = 1; yLen <= Math.min(M, xLen); yLen++) {
				//x[]取前xLen - 1个字符，变成y[]取前yLen个字符，并且x[]最后一个字符删除
				if (dp[xLen - 1][yLen] != Integer.MAX_VALUE) {
					dp[xLen][yLen] = dp[xLen - 1][yLen] + 1;
				}
				//x[]的最后一个字符 == y[]的最后一个字符
				//并且
				//x[]取前xLen - 1个字符，变成y[]取前yLen - 1个字符
				if (x[xLen - 1] == y[yLen - 1] && dp[xLen - 1][yLen - 1] != Integer.MAX_VALUE) {
					dp[xLen][yLen] = Math.min(dp[xLen][yLen], dp[xLen - 1][yLen - 1]);
				}
			}
		}
		//返回当x[]取前x.length个字符，y[]取前y.length个字符时，至少x[]删除多少个字符才能变成y[]的子串
		return dp[N][M];
	}

	/**
	 * 时间复杂度：O(N的2次方 * M)
	 *
	 * @param s1
	 * @param s2
	 * @return
	 */
	public static int minCost2(String s1, String s2) {
		if (s1.length() == 0 || s2.length() == 0) {
			return s2.length();
		}
		int ans = Integer.MAX_VALUE;
		char[] str2 = s2.toCharArray();
		for (int start = 0; start <= s1.length() - 1; start++) {
			for (int end = start + 1; end <= s1.length(); end++) {
				// str1[start....end]
				// substring -> [ 0,1 )
				ans = Math.min(ans, distance(str2, s1.substring(start, end).toCharArray()));
			}
		}
		return ans != Integer.MAX_VALUE ? ans : s2.length();
	}

	/**
	 * 求str2到s1sub的编辑距离
	 * 假设编辑距离只有删除动作且删除一个字符的代价为1
	 *
	 * @param str2
	 * @param s1sub
	 * @return
	 */
	private static int distance(char[] str2, char[] s1sub) {
		int row = str2.length;
		int col = s1sub.length;
		int[][] dp = new int[row][col];
		// dp[i][j]的含义：
		// str2[0..i]仅通过删除行为变成s1sub[0..j]的最小代价
		// 可能性一：
		// str2[0..i]变的过程中，不保留最后一个字符(str2[i])，
		// 那么就是通过str2[0..i-1]变成s1sub[0..j]之后，再最后删掉str2[i]即可 -> dp[i][j] = dp[i-1][j] + 1
		// 可能性二：
		// str2[0..i]变的过程中，想保留最后一个字符(str2[i])，然后变成s1sub[0..j]，
		// 这要求str2[i] == s1sub[j]才有这种可能, 然后str2[0..i-1]变成s1sub[0..j-1]即可
		// 也就是str2[i] == s1sub[j] 的条件下，dp[i][j] = dp[i-1][j-1]
		dp[0][0] = str2[0] == s1sub[0] ? 0 : Integer.MAX_VALUE;

		for (int i = 1; i < row; i++) {
			dp[i][0] = (dp[i - 1][0] != Integer.MAX_VALUE || str2[i] == s1sub[0]) ? i : Integer.MAX_VALUE;
		}

		for (int j = 1; j < col; j++) {
			dp[0][j] = Integer.MAX_VALUE;
		}

		for (int i = 1; i < row; i++) {
			for (int j = 1; j < col; j++) {
				//初始化
				dp[i][j] = Integer.MAX_VALUE;
				//因为str2最后一个字符 != s1sub最后一个字符
				//所以str2字符串需要删除最后一个字符，剩余长度为i - 1的str2字符串变成长度为j的s1sub字符串
				//删除的代价是1
				//如果长度为i - 1的str2字符串能够变成长度为j的s1sub字符串
				//那么当前长度为i的str2字符串能够变成长度为j的s1sub字符串的最低代价就是dp[i - 1][j] + 1
				if (dp[i - 1][j] != Integer.MAX_VALUE) {
					dp[i][j] = dp[i - 1][j] + 1;
				}
				//因为str2最后一个字符 == s1sub最后一个字符
				//所以保留即可，保留的代价是0
				//并且长度为i - 1的str2字符串能够变成长度为j - 1的s1sub字符串
				//那么当前长度为i的str2字符串能够变成长度为j的s1sub字符串的最低代价就是dp[i - 1][j - 1]
				if (str2[i] == s1sub[j] && dp[i - 1][j - 1] != Integer.MAX_VALUE) {
					dp[i][j] = Math.min(dp[i][j], dp[i - 1][j - 1]);
				}

			}
		}
		return dp[row - 1][col - 1];
	}

	/**
	 * 在minCost2()的基础上，使用空间压缩技巧
	 * <p>
	 * 时间复杂度：O(N的2次方 * M)
	 *
	 * @param s1
	 * @param s2
	 * @return
	 */
	public static int minCost3(String s1, String s2) {
		if (s1.length() == 0 || s2.length() == 0) {
			return s2.length();
		}
		char[] str2 = s2.toCharArray();
		char[] str1 = s1.toCharArray();

		int M = str2.length;
		int N = str1.length;

		int[][] dp = new int[M][N];
		int ans = M;
		// 开始的列数
		for (int start = 0; start < N; start++) {
			//str2取第0个字符，str1取第start个字符，如果相等，代价为0，否则代价为str2.length == M
			dp[0][start] = str2[0] == str1[start] ? 0 : M;
			for (int row = 1; row < M; row++) {
				dp[row][start] = (str2[row] == str1[start] || dp[row - 1][start] != M) ? row : M;
			}
			ans = Math.min(ans, dp[M - 1][start]);
			// 以上已经把start列，填好
			// 以下要把dp[...][start + 1....N - 1]的信息填好
			// start ... end end - start + 2
			for (int end = start + 1; end <= N - 1 && end - start < M; end++) {
				// 0... first-1 行 不用管
				int first = end - start;
				dp[first][end] = (str2[first] == str1[end] && dp[first - 1][end - 1] == 0) ? 0 : M;
				for (int row = first + 1; row < M; row++) {
					dp[row][end] = M;
					if (dp[row - 1][end] != M) {
						dp[row][end] = dp[row - 1][end] + 1;
					}
					if (dp[row - 1][end - 1] != M && str2[row] == str1[end]) {
						dp[row][end] = Math.min(dp[row][end], dp[row - 1][end - 1]);
					}
				}
				ans = Math.min(ans, dp[M - 1][end]);
			}
		}
		return ans;
	}

	/**
	 * 来自学生的做法，时间复杂度O(N * M的2次方)
	 * 复杂度和方法三一样，但是思路截然不同
	 *
	 * @param s1
	 * @param s2
	 * @return
	 */
	public static int minCost4(String s1, String s2) {
		char[] str1 = s1.toCharArray();
		char[] str2 = s2.toCharArray();

		HashMap<Character, ArrayList<Integer>> map = new HashMap<>();
		for (int i = 0; i < str1.length; i++) {
			ArrayList<Integer> list = map.getOrDefault(str1[i], new ArrayList<Integer>());
			list.add(i);
			map.put(str1[i], list);
		}
		int ans = 0;
		// 假设删除后的str2必以i位置开头
		// 那么查找i位置在str1上一共有几个，并对str1上的每个位置开始遍历
		// 再次遍历str2一次，看存在对应str1中i后续连续子串可容纳的最长长度
		for (int i = 0; i < str2.length; i++) {
			if (map.containsKey(str2[i])) {
				ArrayList<Integer> keyList = map.get(str2[i]);
				for (int j = 0; j < keyList.size(); j++) {
					int cur1 = keyList.get(j) + 1;
					int cur2 = i + 1;
					int count = 1;
					for (int k = cur2; k < str2.length && cur1 < str1.length; k++) {
						if (str2[k] == str1[cur1]) {
							cur1++;
							count++;
						}
					}
					ans = Math.max(ans, count);
				}
			}
		}
		return s2.length() - ans;
	}
}
