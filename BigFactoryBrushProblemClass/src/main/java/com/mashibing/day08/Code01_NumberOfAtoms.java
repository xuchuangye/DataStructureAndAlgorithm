package com.mashibing.day08;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * 题目一类似的题：原子的数量
 * 给你一个字符串化学式formula，返回每种原子的数量。
 * 原子总是以一个大写字母开始，接着跟随0个或任意个小写字母，表示原子的名字。
 * <p>
 * 如果数量大于1，原子后会跟着数字表示原子的数量。如果数量等于1则不会跟数字。
 * 例如："H2O" 和 "H2O2" 是可行的，但 "H1O2" 这个表达是不可行的。
 * 两个化学式连在一起可以构成新的化学式。
 * 例如："H2O2He3Mg4" 也是化学式。
 * 由括号括起的化学式并佐以数字（可选择性添加）也是化学式。
 * 例如："(H2O2)" 和 "(H2O2)3" 是化学式。
 * 返回所有原子的数量，格式为：
 * 第一个（按字典序）原子的名字，跟着它的数量（如果数量大于 1），
 * 然后是第二个原子的名字（按字典序），跟着它的数量（如果数量大于 1），以此类推。
 * <p>
 * LeetCode测试链接：
 * https://leetcode.cn/problems/number-of-atoms
 *
 * @author xcy
 * @date 2022/7/24 - 10:24
 */
public class Code01_NumberOfAtoms {
	public static void main(String[] args) {

	}

	/**
	 * //TODO 待定
	 * @param formula
	 * @return
	 */
	public static String countOfAtoms(String formula) {
		if (formula == null) {
			return null;
		}
		if ("".equals(formula)) {
			return "";
		}
		char[] str = formula.toCharArray();

		return null;
	}

	public static HashMap<String, Integer> process(char[] str, int i) {
		LinkedList<String> queue = new LinkedList<>();
		int cur = 0;
		while (i < str.length || str[i] != ')') {
			if (str[i] >= '2' && str[i] <= '9') {
				cur = cur * 10 + (str[i] - '0');
				i++;
			} else if ((str[i] >= 'A' && str[i] <= 'Z') || (str[i] >= 'a' && str[i] <= 'z')) {
				addChar(queue, str[i]);
				i++;
			} else if (str[i] == '(') {
				HashMap<String, Integer> map = process(str, i + 1);
				if (!map.isEmpty()) {
					i = map.get(str);
				}
			}
		}
		return null;
	}

	public static void addChar(LinkedList<String> queue, char aChar) {
		if (queue.isEmpty()) {
			queue.addLast(String.valueOf(aChar));
		} else {
			String pop = queue.pollLast();
			char letter = pop.charAt(0);
			//如果是小写字母，需要再取出大写字母
			if (letter >= 'a' && letter <= 'z') {
				pop = queue.pollLast();
				char cur = pop.charAt(0);
				queue.add(String.valueOf(cur) + letter);
			} else {
				queue.add(pop);
			}
		}
	}
}
