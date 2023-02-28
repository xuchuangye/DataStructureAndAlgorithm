package com.mashibing.day09;

import java.util.LinkedList;
import java.util.List;

/**
 * 题目二：
 * 给你一个由若干括号和字母组成的字符串s，删除最小数量的无效括号，使得输入的字符串有效。
 * 返回所有可能的结果。答案可以按任意顺序返回
 * <p>
 * 解题思路：
 * 有两种方法：
 * 方法1.收集所有的答案，然后再进行过滤
 * 方法2.先剪枝，然后再收集答案
 * 方法2的常数时间更短
 * <p>
 * Leetcode测试链接：
 * https://leetcode.com/problems/remove-invalid-parentheses/
 *
 * @author xcy
 * @date 2022/7/25 - 15:39
 */
public class Code02_RemoveInvalidParentheses {
	public static void main(String[] args) {

	}

	/**
	 * 索引         i
	 * String s = "( ) ( ) ) ( ( ) ) )  (  (  )  (  )  )  )"
	 * index =     0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16
	 * 索引         j
	 * (1)remove(0 -> 检查的索引, 0 -> 删除的索引)
	 * i来到4位置，j来到1位置
	 * (2)remove(4, 1)，删除1位置的')'，继续剪枝
	 * 索引                 i
	 * String s = "( ( ) ) ( ( ) ) ) (  (  )  (  )  )  )"
	 * index =     0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15
	 * 索引          j
	 * i来到4位置，j来到3位置
	 * (3)remove(4, 3)，删除3位置的')'，继续剪枝
	 * 索引                 i
	 * String s = "( ) ( ) ( ( ) ) ) (  (  )  (  )  )  )"
	 * index =     0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15
	 * 索引              j
	 * @param s 原始字符串
	 * @return
	 */
	public static List<String> removeInvalidParentheses(String s) {
		List<String> list = new LinkedList<>();
		if (s == null || s.length() == 0) {
			return list;
		}
		//检查的索引以及删除的索引都是从0位置开始
		//par[] = {'(', ')'}
		remove(s, list, 0, 0, new char[]{'(', ')'});
		return list;
	}

	/**
	 * 剪枝的过程
	 *
	 * @param s           原始字符串
	 * @param list        所有可能的结果
	 * @param checkIndex  检查的索引
	 * @param deleteIndex 删除的索引
	 * @param par         长度为2的字符数组，par[0] == '('，par[1] == ')'
	 */
	public static void remove(String s, List<String> list, int checkIndex, int deleteIndex, char[] par) {
		for (int count = 0, i = checkIndex; i < s.length(); i++) {
			//如果是左括号，count++
			if (s.charAt(i) == par[0]) {
				count++;
			}
			//如果是右括号，count--
			if (s.charAt(i) == par[1]) {
				count--;
			}
			//如果count < 0，表示右括号比较多，删除多余的右括号
			if (count < 0) {
				//删除的索引，从deleteIndex出发，不能超出checkIndex
				for (int j = deleteIndex; j <= i; j++) {
					//如果j位置上就是右括号，j位置 == deleteIndex或者j - 1位置上的字符 != 右括号，删除j位置上的右括号
					if (s.charAt(j) == par[1] && (j == deleteIndex || s.charAt(j - 1) != par[1])) {
						//截取s字符串的[0, j)到s字符串的[j + 1, s.length)
						remove(s.substring(0, j) + s.substring(j + 1, s.length()), list, i, j, par);
					}
				}
				return;
			}
		}
		//上述for循环结束，如果s字符串中左括号比右括号多，反转字符串
		String reversed = new StringBuilder(s).reverse().toString();
		if (par[0] == '(') {
			remove(reversed, list, 0, 0, new char[]{')', '('});
		}
		//如果par[0] == ')'表示已经将s字符串逆序了并且收集过答案了，直接将答案添加即可
		else {
			list.add(reversed);
		}
	}
}
