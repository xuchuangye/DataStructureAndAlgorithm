package com.mashibing.recursion;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * 打印字符串中的所有子序列，但是没有重复的子序列
 *
 * @author xcy
 * @date 2022/5/6 - 9:49
 */
public class PrintAllSubSequencesNoRepeat {
	public static void main(String[] args) {
		String string = "acc";
		List<String> list = subSequencesNoRepeat(string);
		for (String s : list) {
			System.out.println(s);
		}
	}

	/**
	 * 关键点：使用HashSet集合可以去除重复的子序列
	 * @param string 原始字符串
	 * @return 返回所有字符串的子序列的集合
	 */
	public static List<String> subSequencesNoRepeat(String string) {
		char[] chars = string.toCharArray();
		String path = "";
		HashSet<String> set = new HashSet<>();
		process(chars, 0, set, path);
		/*for (String str : set) {
			ans.add(str);
		}*/
		//ans.addAll(set);
		return new ArrayList<String>(set);
	}

	/**
	 * 核心逻辑
	 * @param str 字符串的字符数组
	 * @param index 当前字符数组的索引
	 * @param set 将每一种选择之后的字符串结果添加到该集合中，可以去除重复的子序列
	 * @param path 根据是否选择当前字符数组上index位置上的字符，来确定每一种结果的字符串
	 */
	public static void process(char[] str, int index, HashSet<String> set, String path) {
		//index已经超出字符数组的索引了
		if (str.length == index) {
			//将最终的path添加到ans数组中即可
			set.add(path);
			return;
		}
		//在str字符数组中，从当前index索引到index+1的位置之后，path没有任何变化，说明没有要当前index索引上的字符
		process(str, index + 1, set, path);
		//在str字符 数组中，从当前index索引到index+1的位置之后，path加上了当前索引上的字符，说明要当前index索引上的字符
		process(str, index + 1, set, path + str[index]);
	}
}
