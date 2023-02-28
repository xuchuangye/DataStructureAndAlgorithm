package com.mashibing.arithmetic.greedy;

import com.mashibing.common.ArithmeticUtils;

import java.util.Arrays;
import java.util.Comparator;
import java.util.TreeSet;

/**
 * 贪心算法的面试题
 * 给定一个由字符串组成的数组strs，必须把所有的字符串拼接起来
 * 返回所有可能拼接的结果中，字典序最小的结果
 * <p>
 * 字典序：
 * 在Java中，字符串默认的排序方式就是字典序
 * 将字符串理解为进制的话，长度相等的字符串就是数字，所以两个字符串长度相等的时候，直接比数字大小
 * <p>
 * 基本规则：
 * 1.不能修改字符串数组中的任意字符串
 * 2、字符串数组所有字符串不管是按照任意顺序拼接，最终拼接的字符串长度不会变
 *
 * @author xcy
 * @date 2022/5/1 - 10:48
 */
public class LowestLexicography {
	public static void main(String[] args) {
		int arrLen = 6;
		int strLen = 5;
		int testTimes = 10000;
		System.out.println("测试开始！");
		for (int i = 0; i < testTimes; i++) {
			String[] arr1 = ArithmeticUtils.generateRandomStringArray(arrLen, strLen);
			String[] arr2 = ArithmeticUtils.copyStringArray(arr1);
			if (!lowestString(arr1).equals(lowestString2(arr2))) {
				for (String str : arr1) {
					System.out.print(str + ",");
				}
				System.out.println();
				System.err.println("测试失败");
			}
		}
		System.out.println("测试结束！");
	}

	public static String lowestString(String[] strs) {
		if (strs == null || strs.length == 0) {
			return "";
		}
		TreeSet<String> ans = process(strs);
		return ans.size() != 0 ? ans.first() : "";
	}

	/**
	 *
	 * @param strs
	 * @return
	 */
	public static TreeSet<String> process(String[] strs) {
		TreeSet<String> ans = new TreeSet<>();
		if (strs.length == 0) {
			//所以strs长度为0时，ans需要加上空字符串
			ans.add("");
			return ans;
		}
		for (int i = 0; i < strs.length; i++) {
			String first = strs[i];
			String[] nexts = removeIndexString(strs, i);
			TreeSet<String> next = process(nexts);
			//如果ans为null，ans无法进行拼接
			for (String s : next) {
				ans.add(first + s);
			}
		}
		return ans;
	}

	/**
	 * // {"abc", "cks", "bct"}
	 * //    0      1      2
	 * // removeIndexString(arr , 1) -> {"abc", "bct"}
	 *
	 * @param strings 原始的字符串数组
	 * @param index 删除数组中index位置上字符串
	 * @return 返回删除指定index位置上的字符串之后的字符串数组
	 */
	public static String[] removeIndexString(String[] strings, int index) {
		String[] ans = new String[strings.length - 1];
		int ansIndex = 0;
		for (int i = 0; i < strings.length; i++) {
			if (i != index) {
				ans[ansIndex++] = strings[i];
			}
		}
		return ans;
	}

	/**
	 * 字典序的实现类
	 */
	public static class StringComparator implements Comparator<String> {
		@Override
		public int compare(String a, String b) {
			//经典的字典序，但不是最优的字符串排序方式
			//return a.compareTo(b);
			return (a + b).compareTo(b + a);
		}
	}

	/**
	 * 字符串数组使用字典序进行排序之后，返回最小的字典序
	 * @param strs 原始字符串数组
	 * @return 最小字典序
	 */
	public static String lowestString2(String[] strs) {
		if (strs == null || strs.length == 0) {
			return "";
		}
		Arrays.sort(strs, new StringComparator());
		/*String string = "";
		for (String str : strs) {
			string += str;
		}
		return string;*/
		StringBuilder string = new StringBuilder("");
		for (String str : strs) {
			string.append(str);
		}
		return string.toString();
	}
}
