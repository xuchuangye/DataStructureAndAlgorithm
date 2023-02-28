package com.mashibing.day07;

import java.util.HashMap;

/**
 * 题目六：
 * String str, int K, String[] parts, int[] record
 * parts和records的长度一样长，str一定要分割成k个部分，分割出来的每部分在parts里必须得有，
 * 那一部分的得分在record里，请问str切成k个部分，返回最大得分
 *
 * @author xcy
 * @date 2022/7/23 - 7:52
 */
public class Code06_SplitStringMaxValue {
	public static void main(String[] args) {
		String str = "aaaab";
		int K = 5;
		String[] parts = {"a", "aa", "aaa", "ab", "b"};
		int[] record = {1, 2, 3, 2, 1};
		int score1 = maxScore(str, K, parts, record);
		int score2 = maxScore2(str, K, parts, record);
		int score3 = maxScore3(str, K, parts, record);
		System.out.println(score1);
		System.out.println(score2);
		System.out.println(score3);
	}

	/**
	 * 暴力解
	 * <p>
	 * 时间复杂度：O(N的3次方)
	 *
	 * @param str    大字符串
	 * @param K      分割成K个部分
	 * @param parts  单词表
	 * @param record 每个单词对应的得分
	 * @return 返回str分割成K个部分，每个部分在parts[]中必须都包含，并且每个部分有对应的得分，返回最大得分
	 */
	public static int maxScore(String str, int K, String[] parts, int[] record) {
		if (str == null || str.length() == 0 || parts == null || parts.length == 0 || record == null || parts.length != record.length) {
			return 0;
		}
		HashMap<String, Integer> map = new HashMap<>();
		for (int i = 0; i < parts.length; i++) {
			map.put(parts[i], record[i]);
		}
		return process(str, 0, K, map);
	}

	/**
	 * @param str   大字符串
	 * @param index 前缀字符串的起始索引
	 * @param rest  剩余还需要分割成几个部分
	 * @param map   记录贴纸以及贴纸对应得分的统计表
	 * @return 返回str分割成rest个部分，每个部分在map中必须都包含，并且每个部分有对应的得分，返回最大得分
	 */
	private static int process(String str, int index, int rest, HashMap<String, Integer> map) {
		//如果没有剩余需要分割的部分，直接返回-1
		if (rest < 0) {
			return -1;
		}
		//表示没有字符串可以分割了
		if (index == str.length()) {
			//如果没有剩余需要分割的部分，返回0，如果还有，返回-1
			return rest == 0 ? 0 : -1;
		}
		int ans = -1;
		for (int end = index; end < str.length(); end++) {
			//前一个分割的部分
			String pre = str.substring(index, end + 1);
			//下一个分割的部分
			int next = -1;
			if (map.containsKey(pre)) {
				next = process(str, index + 1, rest - 1, map);
			}
			if (next != -1) {
				ans = Math.max(ans, map.get(pre) + next);
			}
		}
		return ans;
	}

	/**
	 * 动态规划
	 * <p>
	 * 时间复杂度：O(N的3次方)
	 *
	 * @param str    大字符串
	 * @param K      分割成K个部分
	 * @param parts  单词表
	 * @param record 每个单词对应的得分
	 * @return 返回str分割成K个部分，每个部分在parts[]中必须都包含，并且每个部分有对应的得分，返回最大得分
	 */
	public static int maxScore2(String str, int K, String[] parts, int[] record) {
		if (str == null || str.length() == 0 || parts == null || parts.length == 0 || record == null || parts.length != record.length) {
			return 0;
		}
		HashMap<String, Integer> map = new HashMap<>();
		for (int i = 0; i < parts.length; i++) {
			map.put(parts[i], record[i]);
		}
		int N = str.length();
		int[][] dp = new int[N + 1][K + 1];
		for (int rest = 0; rest <= K; rest++) {
			dp[N][rest] = -1;
		}
		dp[N][0] = 0;
		for (int index = N - 1; index >= 0; index--) {
			for (int rest = 0; rest <= K; rest++) {
				int ans = -1;
				for (int end = index; end < str.length(); end++) {
					//前一个分割的部分
					String pre = str.substring(index, end + 1);
					//下一个分割的部分
					int next = -1;
					if (map.containsKey(pre) && rest > 0) {
						next = dp[index + 1][rest - 1];
					}
					if (next != -1) {
						ans = Math.max(ans, map.get(pre) + next);
					}
				}
				dp[index][rest] = ans;
			}
		}

		return dp[0][K];
	}

	/**
	 * 前缀树的节点
	 */
	public static class TrieNode {
		public int value;
		public TrieNode[] nexts;

		public TrieNode() {
			this.value = -1;
			nexts = new TrieNode[26];
		}
	}

	/**
	 * 动态规划 + 前缀树
	 * <p>
	 * 时间复杂度：O(N的3次方)
	 *
	 * @param str    大字符串
	 * @param K      分割成K个部分
	 * @param parts  单词表
	 * @param record 每个单词对应的得分
	 * @return 返回str分割成K个部分，每个部分在parts[]中必须都包含，并且每个部分有对应的得分，返回最大得分
	 */
	public static int maxScore3(String str, int K, String[] parts, int[] record) {
		if (str == null || str.length() == 0 || parts == null || parts.length == 0 || record == null || parts.length != record.length) {
			return 0;
		}

		TrieNode root = createPrefixTree(parts, record);
		char[] chars = str.toCharArray();

		int N = str.length();
		int[][] dp = new int[N + 1][K + 1];
		for (int rest = 0; rest <= K; rest++) {
			dp[N][rest] = -1;
		}
		dp[N][0] = 0;

		for (int index = N - 1; index >= 0; index--) {
			for (int rest = 0; rest <= K; rest++) {
				int ans = -1;
				TrieNode cur = root;
				for (int end = index; end < str.length(); end++) {
					int path = chars[end] - 'a';
					if (cur.nexts[path] == null) {
						break;
					}
					cur = cur.nexts[path];

					int next = -1;
					if (cur.value != -1 && rest > 0) {
						next = dp[index + 1][rest - 1];
					}
					if (next != -1) {
						ans = Math.max(ans, cur.value + next);
					}
				}
				dp[index][rest] = ans;
			}
		}

		return dp[0][K];
	}

	/**
	 * 创建前缀树
	 * 根据单词表以及每个单词对应的得分创建前缀树
	 *
	 * @param parts  单词表
	 * @param record 每个单词对应的得分
	 * @return 返回前缀树的根节点
	 */
	private static TrieNode createPrefixTree(String[] parts, int[] record) {
		TrieNode root = new TrieNode();
		for (int i = 0; i < parts.length; i++) {
			TrieNode cur = root;
			char[] chars = parts[i].toCharArray();
			for (char aChar : chars) {
				int path = aChar - 'a';
				if (cur.nexts[path] == null) {
					cur.nexts[path] = new TrieNode();
				}
				cur = cur.nexts[path];
			}
			cur.value = record[i];
		}
		return root;
	}
}
