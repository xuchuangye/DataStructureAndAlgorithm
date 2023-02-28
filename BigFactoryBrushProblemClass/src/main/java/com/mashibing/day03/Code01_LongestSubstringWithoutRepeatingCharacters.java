package com.mashibing.day03;

/**
 * 题目一：
 * 求一个字符串中，最长无重复字符子串长度
 * <p>
 * 解题思路：
 * 1.当前字符上一次出现的位置
 * 2.当前字符的前一个位置能够往左推的最大距离
 * 3.上述两个位置，谁距离当前字符的位置最近就选谁
 * 举例：
 * 当前字符上一次出现的位置距离当前字符的位置最近
 * d   k   c   b   a       d   a
 * 11  12  13  14  15      18  19
 * 当前字符的前一个位置能够往左推的最大距离距离当前字符的位置最近
 * a   k   d   b   c       d   a
 * 11  12  13  14  15      18  19
 * <p>
 * LeetCode测试链接：
 * https://leetcode.com/problems/longest-substring-without-repeating-characters/
 *
 * @author xcy
 * @date 2022/7/13 - 8:29
 */
public class Code01_LongestSubstringWithoutRepeatingCharacters {
	public static void main(String[] args) {

	}

	/**
	 * @param s
	 * @return
	 */
	public static int lengthOfLongestSubString1(String s) {
		if (s == null || "".equals(s)) {
			return 0;
		}
		char[] str = s.toCharArray();
		//ASCII码的范围是0 ~ 255，所以申请长度为256
		int[] map = new int[256];
		for (int i = 1; i < 256; i++) {
			map[i] = -1;
		}
		map[str[0]] = 0;

		//当前字符上一次出现的位置，从1开始
		int pre = 1;
		//最长无重复子串至少长度为1
		int ans = 1;
		//当前字符所在的位置，从1开始
		for (int i = 1; i < str.length; i++) {
			//情况1：当前字符的位置 - 当前字符上一次出现的位置
			int situation1 = i - map[str[i]];
			//情况2：当前字符的前一个位置能够往左推的最大距离 + 1 = 当前字符的位置距离前一个位置能够往左推的最大距离
			int situation2 = pre + 1;
			//谁距离最近就取谁
			int cur = Math.min(situation1, situation2);
			//
			ans = Math.max(ans, cur);
			pre = cur;
			map[str[i]] = i;
		}
		return ans;
	}

	/**
	 * 代码进行优化
	 *
	 * @param s
	 * @return
	 */
	public static int lengthOfLongestSubString2(String s) {
		if (s == null || "".equals(s)) {
			return 0;
		}
		char[] str = s.toCharArray();
		//ASCII码的范围是0 ~ 255，所以申请长度为256
		int[] map = new int[256];
		for (int i = 1; i < 256; i++) {
			map[i] = -1;
		}
		map[str[0]] = 0;

		//当前字符上一次出现的位置，从1开始
		int pre = 1;
		//最长无重复子串至少长度为1
		int ans = 1;
		//当前字符所在的位置，从1开始
		for (int i = 1; i < str.length; i++) {
			//情况1：当前字符的位置 - 当前字符上一次出现的位置
			//情况2：当前字符的前一个位置能够往左推的最大距离 + 1 = 当前字符的位置距离前一个位置能够往左推的最大距离
			//谁距离最近就取谁
			pre = Math.min(i - map[str[i]], pre + 1);
			ans = Math.max(ans, pre);
			map[str[i]] = i;
		}
		return ans;
	}
}
