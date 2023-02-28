package com.mashibing.day03;

import java.util.HashSet;

/**
 * 题目二：
 * 只由小写字母（a~z）组成的一批字符串，都放在字符类型的数组String[] arr中，
 * 如果其中某两个字符串所含有的字符种类完全一样就将两个字符串算作一类，
 * 比如baacbba和bac就算作一类，返回arr中有多少类
 *
 * 解题思路：
 * 1.使用一个int类型的32位表示字符串的字符
 * 0表示'a'，1表示'b',2表示'c'...25表示'z'
 * 2.一个字符串得到一个二进制类型，自动转换为int类型，存放在HashSet中，去重
 *
 * @author xcy
 * @date 2022/7/13 - 8:30
 */
public class Code02_HowManyTypes {
	public static void main(String[] args) {

	}

	/**
	 *
	 * @param arr String[]
	 * @return 返回String[] arr中的类型数
	 */
	public static int types(String[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		//HashSet可以去重
		HashSet<Integer> types = new HashSet<>();
		//对字符串数组的每一个字符串进行遍历
		for (String s : arr) {
			char[] str = s.toCharArray();
			//使用int类型的32位表示字符
			int key = 0;
			for (char aChar : str) {
				key |= (1 << (aChar - 'a'));
			}
			types.add(key);
		}
		return types.size();
	}
}
