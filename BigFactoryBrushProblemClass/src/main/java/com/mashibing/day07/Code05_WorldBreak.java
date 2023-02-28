package com.mashibing.day07;

import java.util.Arrays;
import java.util.HashSet;

/**
 * 题目五：
 * 假设所有字符都是小写字母，大字符串是str，arr是去重的单词表, 每个单词都不是空字符串且可以使用任意次。
 * 使用arr中的单词有多少种拼接str的方式，返回方法数。
 *
 * @author xcy
 * @date 2022/7/23 - 7:52
 */
public class Code05_WorldBreak {
	public static void main(String[] args) {

	}

	/**
	 * 暴力解
	 *
	 * @param str 大字符串
	 * @param arr 去重的单词表
	 * @return 使用arr中的单词有多少种拼接str的方式，返回方法数
	 */
	public static int ways(String str, String[] arr) {
		if (str == null || "".equals(str) || arr == null || arr.length == 0) {
			return 0;
		}
		HashSet<String> set = new HashSet<>(Arrays.asList(arr));
		return process(str, 0, set);
	}

	/**
	 * 时间复杂度：O(N的3次方)
	 * <p>
	 * i的范围0 ~ str.length() -> O(N)
	 * for循环 -> O(N)
	 * 前缀字符串pre的检查 -> O(N)
	 *
	 * @param str 大字符串
	 * @param i   前缀字符串的起始位置
	 * @param set 所有可以分解的字符串，都放在了set集合中
	 * @return 返回str[]i ~ str.length() - 1能够被set中的贴纸分解的情况下，返回分解的方法数
	 */
	private static int process(String str, int i, HashSet<String> set) {
		//表示已经没有字符串可以被分解了
		if (i == str.length()) {
			//set中贴纸都不用，
			//也可以理解为收集到一种有效的方法，i之前做过的分解决定
			return 1;
		}
		//表示还有字符串可以被分解
		int ways = 0;
		//i ... end
		for (int end = i; end < str.length(); end++) {
			//i ... i
			//i ... i + 1
			//i ... str.length() - 1
			//前缀字符串：[)
			String pre = str.substring(i, end + 1);
			if (set.contains(pre)) {
				//继续下一个字符串前缀
				//i + 1 ... i + 1
				//i + 1 ... i + 2
				//i + 1 ... str.length() - 1
				ways += process(str, i + 1, set);
			}
		}
		return ways;
	}

	/**
	 * 前缀树节点
	 */
	public static class Node {
		public boolean end;
		/**
		 * 英文字母26个，申请长度26
		 */
		public Node[] nexts;

		public Node() {
			this.end = false;
			this.nexts = new Node[26];
		}
	}

	/**
	 * 使用前缀树的方式
	 * <p>
	 * 时间复杂度：O(N的2次方)
	 *
	 * @param str 大字符串
	 * @param arr 单词表
	 * @return 使用arr中的单词有多少种拼接str的方式，返回方法数
	 */
	public static int ways2(String str, String[] arr) {
		if (str == null || arr == null || str.length() == 0 || arr.length == 0) {
			return 0;
		}
		Node root = createPrefixTree(arr);
		return g(str.toCharArray(), root, 0);
	}

	/**
	 * 创建前缀树
	 *
	 * @param arr 根据单词表创建前缀树
	 * @return 返回前缀树的根节点
	 */
	private static Node createPrefixTree(String[] arr) {
		//前缀树根节点
		Node root = new Node();
		//arr[]中有多少个字符串，都挂在前缀树上
		for (String s : arr) {
			char[] chars = s.toCharArray();
			Node node = root;
			for (char aChar : chars) {
				int index = aChar - 'a';
				if (node.nexts[index] == null) {
					node.nexts[index] = new Node();
				}
				node = node.nexts[index];
			}
			root.end = true;
		}
		return root;
	}

	/**
	 * 时间复杂度：O(N的2次方)
	 *
	 * @param str
	 * @param root
	 * @param i
	 * @return
	 */
	private static int g(char[] str, Node root, int i) {
		if (i == str.length) {
			return 1;
		}
		int ways = 0;
		Node cur = root;
		for (int end = i; end < str.length; end++) {
			int path = str[end] - 'a';
			if (cur.nexts[path] == null) {
				break;
			}
			cur = cur.nexts[path];
			if (cur.end) {
				ways += g(str, root, i + 1);
			}
		}
		return ways;
	}
}
