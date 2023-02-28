package com.mashibing.arithmetic.kmp;

import java.util.Arrays;

/**
 * KMP算法
 * KMP算法是一个解决模式串在文本串中出现过，如果出现过，最早出现的位置的经典算法
 * <p>
 * KnuthMorrisPratt：字符串查找算法，常用于在一个文本串中S内查找一个模式串P的出现的位置
 * <p>
 * KMP算法的基本思想
 * 利用已知信息，不要把搜索位置移回到已经比较过的位置，继续将它向后移，这样就提高了效率
 * <p>
 * 1、部分搜索词
 * 部分搜索词就是ABCDABD
 * 2、部分匹配值
 * 部分匹配值就是前缀和后缀共有元素长度最长的
 * 3、部分匹配表
 * "A" 前缀和后缀都为空集，所以共有元素的长度为0，部分匹配值为0
 * "AB" 前缀为A，后缀为B，所以共有元素的长度为0，部分匹配值为0
 * "ABC" 前缀为A，AB，后缀为BC，B，所以共有元素的长度为0，部分匹配值为0
 * "ABCD" 前缀为A，AB，ABC，后缀为BCD，CD，D，所以共有元素的长度为0，部分匹配值为0
 * "ABCDA" 前缀为A，AB，ABC，ABCD，后缀为BCDA，CDA，DA，A，所以共有元素的长度为1，部分匹配值为1
 * "ABCDAB" 前缀为A，AB，ABC，ABCD，ABCDA，后缀为BCDAB，CDAB，DAB，AB，B，所以共有元素的长度为2，部分匹配值为2
 * "ABCDABD" 前缀为A，AB，ABC，ABCD，ABCDA，ABCDAB，后缀为BCDABD，CDABD，DABD，ABD，BD，D所以共有元素的长度为0，部分匹配值为0
 * <p>
 * 部分匹配表
 * 部分搜索词：A B C D A B D
 * 部分匹配值：0 0 0 0 1 2 0
 * A -> 0
 * AB -> 0
 * ABC -> 0
 * ABCD -> 0
 * ABCDA -> 1
 * ABCDAB -> 2
 * ABCDABD -> 0
 *
 * @author xcy
 * @date 2022/4/1 - 14:51
 */
public class KnuthMorrisPratt {
	public static void main(String[] args) {
		String str1 = "BBC ABCDAB ABCDABCDABDE";
		String str2 = "ABCDABD";

		int[] next = getStringNext(str2);
		System.out.println("部分匹配值：" + Arrays.toString(next));

		int index = getStringIndexSearch(str1, str2, next);
		if (index != -1) {
			System.out.println("字符串str1中包含字符串str2，并且包含字符串的起始位置是：" + index);
		}else {
			System.out.println("字符串str1中不包含字符串str2");
		}
	}

	/**
	 * 根据KMP算法计算子串在主串中第一次匹配到的位置
	 * @param str1 主串
	 * @param str2 子串
	 * @param next str2子串对应的部分匹配值
	 * @return 如果匹配到返回第一次匹配到的位置，如果没有匹配到返回-1
	 */
	public static int getStringIndexSearch(String str1, String str2, int[] next) {
		for (int i = 0, j = 0; i < str1.length(); i++) {
			//KMP算法的核心
			while (j > 0 && str1.charAt(i) != str2.charAt(j)) {
				//j从部分匹配值的前一个去找
				j = next[j - 1];
			}
			if (str1.charAt(i) == str2.charAt(j)) {
				j++;
			}
			if (j == str2.length()) {
				//+ 1是因为，当j满足条件时，j已经进行++，而i还没有进行++就已经退出循环了，所以需要加1
				return i - j + 1;
			}
		}
		return -1;
	}

	/**
	 * 获取字符串的部分匹配值
	 *
	 * @param str 字符串
	 * @return 返回字符串的部分匹配值
	 */
	public static int[] getStringNext(String str) {
		//数组的索引表示字符串的长度，索引对应的值表示部分匹配值
		int[] next = new int[str.length()];
		//next[0] = 0表示字符串长度为1，前缀和后缀都是空集，部分匹配值为0，例如字符串"A"，索引为0，该字符串的部分匹配值为0
		next[0] = 0;

		for (int i = 1, j = 0; i < str.length(); i++) {
			//当str.charAt(i) != str.charAt(j)时，需要从next[j - 1]中获取新的j
			while (j > 0 && str.charAt(i) != str.charAt(j)) {
				j = next[j - 1];
			}
			//当str.charAt(i) == str.charAt(j)满足时，部分匹配值加1
			if (str.charAt(i) == str.charAt(j)) {
				j++;
			}
			next[i] = j;
		}
		return next;
	}
}
