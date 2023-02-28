package com.mashibing.day05;

/**
 * 题目三：
 * 编辑距离问题
 * 举例：
 * 假设String string = "abcd"，想要的结果result = "abce"
 * 一共有几种方式：
 * 1.保留，代价为0
 * 2.删除，代价为x
 * 3.添加，代价为y
 * 4.替换，代价为z
 * 如果在修改原始字符串之后返回想要的结果的情况下，使用的代价最低？
 * <p>
 * 实际应用：
 * 百度搜索"今晚吃什么？"，反馈的词条信息比较少，假设有100万条
 * 而搜索库中有"今夜吃什么？"，词条信息比较多，假设有100亿条，那么搜索结果会将"今晚吃什么？"的相似搜索词"今夜吃什么？"
 * 的结果也显示出来。
 * 在工程上，可以描述str1和str2两个字符串的相似程度
 * <p>
 * 解题思路：
 * 1.使用动态规划中的样本对应模型(行列模型)
 *
 * @author xcy
 * @date 2022/7/18 - 16:23
 */
public class Code03_EditCost {
	public static void main(String[] args) {
		String word1 = "intention", word2 = "execution";
		int cost1 = minDistance(word1, word2, 1, 1, 1);
		System.out.println(cost1);
	}

	public static int minDistance(String s1, String s2) {
		if (s1 == null || s2 == null) {
			return 0;
		}
		return minDistance(s1, s2, 1, 1, 1);
	}

	/**
	 * 假设：删除的代价d，添加的代价a，替换的代价r
	 * dp[i][j]表示str1取前i个字符，str2取前j个字符，将str1修改成str2的最低代价
	 * str1 = "aab151"
	 * index = 012345
	 * str2 = "a1b51"
	 * index = 01234
	 * -     0   1   2   3   4   5      str2
	 * -0    0   a   2a  3a  4a  5a
	 * -1    d
	 * -2   2d
	 * -3   3d
	 * -4   4d
	 * -5   5d
	 * -6   6d
	 * str1
	 * str1取前0个字符，也就是""，str2取前0个字符，还是""，""修改成""的最低代价只能是保留，也就是0
	 * dp[0][0] = 0
	 * str1取前0个字符，也就是""，str2取前j个字符，j的范围是1 ~ 5，从无到有，最低代价只能是添加，也就是a * j
	 * dp[0][j] = a * j
	 * str1取前i个字符，i的范围是1 ~ 6，str2取前0个字符，从有到无，最低代价只能是删除，也就是d * i
	 * dp[i][0] = d * i
	 * <p>
	 * 一共有三种可能性：
	 * 第1种可能性：dp[i - 1][j] + d，也就是str1取前i - 1个字符变成str2取前j个字符，str1最后一个字符删除，d的代价
	 * str1 = "abcde"
	 * str2 = "abcd"
	 * str1取前i - 1字符的长度 = "abcd" -> str2取前j个字符 = "abcd"，最后一个字符"e"删除，所以是dp[i - 1][j] + d
	 * 第2种可能性：dp[i][j - 1] + a，也就是str1取前i个字符变成str2取前j - 1个字符，str1最后一个字符添加，a的代价
	 * str1 = "abtc"
	 * str2 = "abkcd"
	 * str1取前i字符的长度 = "abtc" -> str2取前j - 1个字符 = "abkc"，最后一个字符"d"添加，所以是dp[i][j - 1] + a
	 * 第3种可能性：str1[i - 1] == str2[j - 1]的情况下，dp[i - 1][j - 1]，也就是str1取前i - 1个字符变成str2取前j - 1个字符，最后一个字符保留，0的代价
	 * str1 = "abckda"
	 * str2 = "abkbca"
	 * str1[i - 1] == str2[j - 1]，最后一个字符"a"添加，所以是dp[i - 1][j - 1] + 0
	 * 第4种可能性：str1[i - 1] != str2[j - 1]的情况下，dp[i - 1][j - 1] + r，也就是str1取前i - 1个字符变成str2取前j - 1个字符，最后一个字符替换，r的代价
	 * str1 = "avdfca"
	 * str2 = "abahdm"
	 * str1[i - 1] != str2[j - 1]，最后一个字符替换，所以是dp[i - 1][j - 1] + r
	 *
	 * @param s1
	 * @param s2
	 * @param ac 插入的代价
	 * @param dc 删除的代价
	 * @param rc 替换的代价
	 * @return
	 */
	public static int minDistance(String s1, String s2, int ac, int dc, int rc) {
		char[] str1 = s1.toCharArray();
		char[] str2 = s2.toCharArray();
		int N = str1.length;
		int M = str2.length;
		int[][] dp = new int[N + 1][M + 1];
		dp[0][0] = 0;

		//从str1取前i个字符，str2取前0个字符，也就是""，从str1右到str2无，最低代价只能是删除，dc的代价
		for (int i = 0; i <= N; i++) {
			//i个字符，代价就为i * dc
			dp[i][0] = i * dc;
		}
		//从str1取前0个字符，也就是""，str2取前j个字符，从str1无到str2有，最低代价只能是插入，ac的代价
		for (int j = 0; j <= M; j++) {
			dp[0][j] = j * ac;
		}

		//第0行和第0列都已经填写过了，从第1行和第1列开始
		for (int i = 1; i <= N; i++) {
			for (int j = 1; j <= M; j++) {
				//dp[i][j] = str1[i - 1] == str2[j - 1] ?  dp[i - 1][j - 1] : dp[i - 1][j - 1] + rc;

				//dp[i][j] = dp[i - 1][j - 1] + (str1[i - 1] == str2[j - 1] ? 0 : rc);

				//第4种可能性
				if (str1[i - 1] == str2[j - 1]) {
					dp[i][j] = dp[i - 1][j - 1];
				}
				//第3种可能性
				else {
					dp[i][j] = dp[i - 1][j - 1] + rc;
				}

				//第1种可能性
				dp[i][j] = Math.min(dp[i][j], dp[i - 1][j] + dc);
				//第2种可能性
				dp[i][j] = Math.min(dp[i][j], dp[i][j - 1] + ac);
			}
		}
		//返回从str1取前str1.length，变成str2取前str2.length的最低代价
		return dp[N][M];
	}
}
