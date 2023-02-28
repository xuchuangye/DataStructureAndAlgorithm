package com.mashibing.day17;

import java.util.HashMap;

/**
 * 给定一个字符串str，返回str的所有子序列中有多少不同的字面值
 * <p>
 * 解题思路：
 * 1.如果str字符串的字符都不相同的情况下
 * str = "123"
 * all = {}表示所有的集合，当str字符串的字符都没有遍历时，all = {}
 * 当遍历到str的0位置时，产生新的集合：{} + 1 -> {1}，目前的总集合all为：{},{1}
 * 当遍历到str的1位置时，产生新的集合：{} + 2 -> {2}，{1} + 2 -> {1, 2}，目前的总集合all为：{},{1},{2},{1,2}
 * 当遍历到str的2位置时，产生新的集合：{} + 3 -> {3}，{1} + 3 -> {1. 3}，{2} + 3 -> {2, 3}
 * {1,2} + 3 -> {1,2,3}，目前的总集合all为：{},{1},{2},{1,2},{3},{1,3},{2,3},{1,2,3}
 * 2.如果str字符串的字符出现相同的情况下
 *
 * <p>
 * Leetcode题目：https://leetcode.cn/problems/distinct-subsequences-ii/
 *
 * @author xcy
 * @date 2022/8/7 - 10:04
 */
public class Code05_DistinctSubsequenceValue {
	public static void main(String[] args) {

	}

	/**
	 * 原始代码，计算空集{}
	 *
	 * @param s
	 * @return
	 */
	public static int distinctSubseqII1(String s) {
		if (s == null || s.length() < 1) {
			return 0;
		}
		char[] str = s.toCharArray();
		//一开始all = {}
		int all = 1;
		int m = 1000000007;
		HashMap<Character, Integer> map = new HashMap<>();
		for (char aChar : str) {
			//新增的继承之前的all
			int newAdd = all;
			int curAll = all + newAdd - (map.containsKey(aChar) ? map.get(aChar) : 0);
			all = curAll;
			map.put(aChar, newAdd);
		}
		return all;
	}

	/**
	 * 满足Leetcode测试的方法，不计算空集{}，并且取模
	 *
	 * @param s
	 * @return
	 */
	public static int distinctSubseqII2(String s) {
		if (s == null || s.length() < 1) {
			return 0;
		}
		char[] str = s.toCharArray();
		//一开始all = {}
		int all = 1;
		int m = 1000000007;
		HashMap<Character, Integer> map = new HashMap<>();
		for (char aChar : str) {
			//新增的继承之前的all
			int newAdd = all;
			int curAll = all;
			curAll = (curAll + newAdd) % m;
			curAll = (curAll - (map.containsKey(aChar) ? map.get(aChar) : 0) + m) % m;
			all = curAll;
			map.put(aChar, newAdd);
		}
		//Leetcode测试不计算空集{}
		return all - 1;
	}
}
