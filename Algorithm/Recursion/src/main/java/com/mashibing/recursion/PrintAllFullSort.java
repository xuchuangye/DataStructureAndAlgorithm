package com.mashibing.recursion;

import java.util.ArrayList;
import java.util.List;

/**
 * 打印字符串的全排序
 *
 * @author xcy
 * @date 2022/5/6 - 10:26
 */
public class PrintAllFullSort {
	public static void main(String[] args) {
		String string = "acc";
		List<String> list = permutation(string);
		for (String s : list) {
			System.out.println(s);
		}
		System.out.println("-----------");
		List<String> strings = fullSort(string);
		for (String s : strings) {
			System.out.println(s);
		}
		System.out.println("-----------");
		List<String> noRepeat = fullSortNoRepeat(string);
		for (String s : noRepeat) {
			System.out.println(s);
		}
	}

	/**
	 * @param string 原始字符串
	 * @return 返回字符串的所有全排序
	 */
	public static List<String> permutation(String string) {
		List<String> ans = new ArrayList<>();
		if (string == null || string.length() == 0) {
			return ans;
		}
		ArrayList<Character> characters = new ArrayList<Character>();
		for (char c : string.toCharArray()) {
			characters.add(c);
		}
		String path = "";
		process(characters, ans, path);
		return ans;
	}

	/**
	 * @param rest 字符串的字符集合
	 * @param ans  字符串的所有全排序的集合
	 * @param path 确定各种排列的组合的字符串
	 */
	public static void process(ArrayList<Character> rest, List<String> ans, String path) {
		if (rest.isEmpty()) {
			ans.add(path);
		} else {
			//对字符的List集合进行遍历
			for (int i = 0; i < rest.size(); i++) {
				//获取当前索引上的字符
				char cur = rest.get(i);
				//确定当前索引上的字符之后，删除字符集合中当前索引上的字符
				rest.remove(i);
				//继续确定下一个索引上的字符，组成新的path
				process(rest, ans, path + cur);
				//恢复原始的字符集合(特别重要)，因为从第一个索引开始之后，还需要确定之后的索引，期间的原始字符集合不能变
				rest.add(i, cur);
			}
		}
	}

	/**
	 * 返回字符串的所有全排序
	 * @param string 原始字符串
	 * @return 返回字符串的所有全排序
	 */
	public static List<String> fullSort(String string) {
		List<String> ans = new ArrayList<>();
		if (string == null || string.length() == 0) {
			return ans;
		}
		char[] chars = string.toCharArray();
		//从字符数组的第0位置开始
		coreLogic(chars, 0, ans);
		return ans;
	}

	/**
	 * 核心逻辑
	 * @param str 字符串的字符数组
	 * @param index 字符数组的索引
	 * @param ans 字符串的全排序的集合
	 */
	public static void coreLogic(char[] str, int index, List<String> ans) {
		//index已经越界了
		if (index == str.length) {
			//将str字符数组直接添加到ans中
			ans.add(String.valueOf(str));
			return;
		}
		//从index开始，已知到字符数组的最后一个字符
		for (int i = index; i < str.length; i++) {
			//0和0交换，递归继续下一个字符的交换
			swap(str, i, index);
			coreLogic(str, index + 1, ans);
			//当交换之后，返回第0个字符重新全排序，需要进行复原
			swap(str, i, index);
		}
	}

	/**
	 * 返回字符串的全排序，去除重复的字符串
	 * @param string 原始字符串
	 * @return 返回字符串的全排序，去除重复的字符串的集合
	 */
	public static List<String> fullSortNoRepeat(String string) {
		List<String> ans = new ArrayList<>();
		if (string == null ||string.length() == 0) {
			return ans;
		}
		char[] chars = string.toCharArray();
		coreLogicNoRepeat(chars, 0, ans);
		return ans;
	}

	/**
	 * 核心逻辑
	 * @param str
	 * @param index
	 * @param ans
	 */
	public static void coreLogicNoRepeat(char[] str, int index, List<String> ans) {
		if (index == str.length) {
			ans.add(String.valueOf(str));
		}else {
			boolean[] isVisited = new boolean[256];
			for (int i = index; i < str.length; i++) {
				if (!isVisited[str[i]]) {
					isVisited[str[i]] = true;
					swap(str, i, index);
					coreLogicNoRepeat(str, index + 1, ans);
					swap(str, i, index);
				}
			}
		}
	}

	public static void swap(char[] chars, int i, int j) {
		char temp = chars[i];
		chars[i] = chars[j];
		chars[j] = temp;
	}
}
