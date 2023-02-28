package com.mashibing.recursion;

import java.util.ArrayList;
import java.util.List;

/**
 * 打印字符串中的所有子序列
 *
 * @author xcy
 * @date 2022/5/6 - 9:49
 */
public class PrintAllSubSequences {
	public static void main(String[] args) {
		String string = "acc";
		List<String> list = subSequences(string);
		for (String s : list) {
			System.out.println(s);
		}
	}

	/**
	 *
	 * @param string 原始字符串
	 * @return 返回所有字符串的子序列的集合
	 */
	public static List<String> subSequences(String string) {
		char[] chars = string.toCharArray();
		String path = "";
		List<String> ans = new ArrayList<String>();
		process(chars, 0, ans, path);
		return ans;
	}

	/**
	 * 核心逻辑
	 * @param str 字符串的字符数组
	 * @param index 当前字符数组的索引
	 * @param ans 将每一种选择之后的字符串结果添加到该集合中
	 * @param path 根据是否选择当前字符数组上index位置上的字符，来确定每一种结果的字符串
	 */
	public static void process(char[] str, int index, List<String> ans, String path) {
		//index已经超出字符数组的索引了
		if (str.length == index) {
			//将最终的path添加到ans数组中即可
			ans.add(path);
			return;
		}
		//在str字符数组中，从当前index索引到index+1的位置之后，path没有任何变化，说明没有要当前index索引上的字符
		process(str, index + 1, ans, path);
		//在str字符 数组中，从当前index索引到index+1的位置之后，path加上了当前索引上的字符，说明要当前index索引上的字符
		process(str, index + 1, ans, path + str[index]);
	}
}
