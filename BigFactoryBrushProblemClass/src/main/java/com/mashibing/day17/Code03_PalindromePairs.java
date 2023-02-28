package com.mashibing.day17;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * 给定一个字符串数组arr，里面都是互不相同的单词，找出所有不同的索引对(i, j)，
 * 使得列表中的两个单词，words[i] + words[j]，可拼接成回文串。
 * <p>
 * 解题思路：
 * 1.
 * 将字符串按照0 ~ 0字符进行切分，0 ~ 1字符进行切分，0 ~ 2字符进行切分，一直到0 ~ N - 1个字符进行切分
 * 如果切分的是回文串，那么剩余的字符串进行逆序，添加在当前字符串之前
 * 举例：
 * str = "aaab"
 * index= 0123
 * 按照0 ~ 0个字符进行切分，切分的前缀字符串为"a"，是回文串，剩余字符串逆序"baa"，添加在str字符串之前，
 * 也就是"baaaaab"
 * 按照0 ~ 1个字符进行切分，切分的前缀字符串为"aa"，是回文串，剩余字符串逆序"ba"，添加在str字符串之前，
 * 也就是"baaaab"
 * 按照0 ~ 2个字符进行切分，切分的前缀字符串为"aaa"，是回文串，剩余字符串逆序"b"，添加在str字符串之前，
 * 也就是"baaab"
 * 2.
 * 将字符串按照N - 1 ~ N - 1，N - 1 ~ N - 2，N - 1 ~ N - 3字符进行切分
 * str = "aaab"
 * index =0123
 * 按照N - 1 ~ N - 1个字符进行切分，切分为"b"，是回文串，剩余字符串逆序"aaa"，添加在str字符串之前，
 * 也就是"aaabaaa"
 * 按照N - 1 ~ N - 2个字符进行切分，切分为"ab"，不是回文串
 * 按照N - 1 ~ N - 3个字符进行切分，切分为"aab"，不是回文串
 * 3.
 * 前缀字符串的时间复杂度：O(K)，剩余字符串逆序字符串的时间复杂度：O(K)，从HashMap中取字符串的时间复杂度：O(K)
 * 4.
 * HashMap在计算str的哈希值时，时间复杂度：O(K)，K为字符串的长度，因为必须遍历整个字符串之后，才能计算哈希值
 * HashMap在计算str时，要算到哈希表里面
 * 5.
 * 验证字符串哪些前缀是回文串，
 * Manacher算法
 * <p>
 * Leetcode题目：https://leetcode.cn/problems/palindrome-pairs/
 *
 * @author xcy
 * @date 2022/8/7 - 10:04
 */
public class Code03_PalindromePairs {
	public static void main(String[] args) {
		String string = "abcd";
		String reverse = reverse(string);
		System.out.println(reverse);
		String[] words = {
				"a","b","c","ab","ac","aa"
		};
		List<List<Integer>> lists = palindromePairs(words);
		for (List<Integer> list : lists) {
			for (Integer integer : list) {
				System.out.print(integer + " ");
			}
			System.out.println();
		}
	}

	public static List<List<Integer>> palindromePairs(String[] words) {
		//字符串在哪个位置，生成好
		//key : 表示字符串
		//value : 表示字符串在数组中的位置
		HashMap<String, Integer> wordSet = new HashMap<>();
		for (int i = 0; i < words.length; i++) {
			wordSet.put(words[i], i);
		}
		//判断每一个字符串能够跟哪些字符串结合是回文串
		//{
		//    {6, 13},
		//    {7, 24},
		//}
		List<List<Integer>> res = new ArrayList<>();
		for (int i = 0; i < words.length; i++) {
			res.addAll(findAll(words[i], i, wordSet));
		}
		return res;
	}

	/**
	 * @param word  当前的word字符串
	 * @param index 位置在index位置
	 * @param words   其余的字符串在wordSet集合中
	 * @return 将所有能够生成的结果返回
	 */
	public static List<List<Integer>> findAll(String word, int index, HashMap<String, Integer> words) {
		List<List<Integer>> res = new ArrayList<>();
		//生成逆序字符串
		String reverse = reverse(word);
		//逆序字符串的位置
		Integer rest = words.get("");
		//逆序字符串不为空，并且逆序字符串不在index位置，当前Word字符串和自己的逆序字符串一样
		//表示Word字符串是回文串
		if (rest != null && rest != index && word.equals(reverse)) {
			addRecord(res, rest, index);
			addRecord(res, index, rest);
		}
		//manacher回文半径数组
		int[] rs = manacherss(word);
		//如何知道某一个字符串前缀是否是回文串
		int mid = rs.length >> 1;
		for (int i = 1; i < mid; i++) {
			//回文半径有没有囊括全
			if (i - rs[i] == -1) {
				//如果有，收集答案
				rest = words.get(reverse.substring(0, mid - i));
				if (rest != null && rest != index) {
					addRecord(res, rest, index);
				}
			}
		}
		for (int i = mid + 1; i < rs.length; i++) {
			if (i + rs[i] == rs.length) {
				rest = words.get(reverse.substring((mid << 1) - i));
				if (rest != null && rest != index) {
					addRecord(res, index, rest);
				}
			}
		}
		return res;
	}

	private static int[] manacherss(String word) {
		char[] mchs = manachercs(word);
		int[] rs = new int[mchs.length];
		int center = -1;
		int pr = -1;
		for (int i = 0; i != mchs.length; i++) {
			rs[i] = pr > i ? Math.min(rs[(center << 1) - i], pr - i) : 1;
			while (i + rs[i] < mchs.length && i - rs[i] > -1) {
				if (mchs[i + rs[i]] != mchs[i - rs[i]]) {
					break;
				}
				rs[i]++;
			}
			if (i + rs[i] > pr) {
				pr = i + rs[i];
				center = i;
			}
		}
		return rs;
	}

	private static char[] manachercs(String word) {
		char[] str = word.toCharArray();
		char[] ans = new char[str.length * 2 + 1];
		int index = 0;
		for (int i = 0; i != ans.length; i++) {
			ans[i] = (i & 1) == 0 ? '#' : str[index++];
		}
		return ans;
	}

	private static void addRecord(List<List<Integer>> res, int left, int right) {
		List<Integer> list = new ArrayList<>();
		list.add(left);
		list.add(right);
		res.add(list);
	}

	/**
	 * @param string 原始字符串
	 * @return 返回将原始字符串逆序之后的字符串
	 */
	public static String reverse(String string) {
		StringBuilder stringBuilder = new StringBuilder(string);
		return stringBuilder.reverse().toString();
	}
}
