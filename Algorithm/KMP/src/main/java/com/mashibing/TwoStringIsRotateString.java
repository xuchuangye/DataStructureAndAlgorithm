package com.mashibing;

/**
 * 旋转字符串：
 * 字符串截取不超过字符串长度的任意长度字符串进行旋转，就是旋转字符串
 * 举例：
 * 123456 截取123456 旋转之后还是123456
 * 123456 截取1 旋转之后是 234561
 * 123456 截取12 旋转之后是 345612
 * 123456 截取123 旋转之后是 456123
 * 123456 截取1234 旋转之后是 561234
 * 123456 截取12345 旋转之后是 612345
 * <p>
 * 给定两个字符串，判断str1字符串和str2字符串是否是旋转字符串
 *
 * 分析：
 * 1.判断两个字符串的长度是否相等，如果不相等，两个字符串就不是互为旋转字符串
 * 2.str1 + str1之后的字符串str'，判断str'字符串中是否包含str2，如果包含，str1和str2互为旋转字符串
 * @author xcy
 * @date 2022/5/21 - 13:15
 */
public class TwoStringIsRotateString {
	public static void main(String[] args) {
		String string1 = "abcabcabcd";
		String string2 = "abcdabcabc";
		boolean b = twoStringIsRotateString(string1, string2);
		System.out.println(b);
	}

	/**
	 * 1.判断两个字符串的长度是否相等，如果不相等，两个字符串就不是互为旋转字符串
	 * 2.string1 + string1之后的字符串str'，判断str'字符串中是否包含str2，如果包含，str1和str2互为旋转字符串
	 * @param string1
	 * @param string2
	 * @return
	 */
	public static boolean twoStringIsRotateString(String string1, String string2) {
		if (string1 == null || string1.length() == 0 || string2 == null || string1.length() != string2.length()) {
			return false;
		}
		String str = string1 + string1;
		return KMPAlgorithm.KMP(str, string2) != 0;
	}
}
