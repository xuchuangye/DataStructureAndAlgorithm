package com.mashibing.arithmetic.kmp;

/**
 * 暴力匹配算法
 *
 * @author xcy
 * @date 2022/4/1 - 11:46
 */
public class ViolenceMatchDemo {

	public static void main(String[] args) {
		String str1 = "硅硅谷 尚硅谷你尚硅 尚硅谷你尚硅谷你尚硅你好";
		String str2 = "尚硅谷你尚硅你";
		int index = violenceMatch(str1, str2);
		if (index != -1) {
			System.out.println("字符串str1中包含字符串str2，并且包含字符串的起始位置是：" + index);
		}else {
			System.out.println("字符串str1中不包含字符串str2");
		}
	}

	/**
	 * 暴力匹配算法
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static int violenceMatch(String str1, String str2) {
		char[] s1 = str1.toCharArray();
		char[] s2 = str2.toCharArray();

		int s1Length = s1.length;
		int s2Length = s2.length;

		int i = 0;//指向s1
		int j = 0;//指向s2

		//防止越界
		while (i < s1Length && j < s2Length) {
			//每匹配到一个字符就继续往后遍历
			if (s1[i] == s2[j]) {
				i++;
				j++;
			} else {
				//没有匹配到，i就回退到原来的位置
				i = i - (j - 1);
				//j回退到起始位置
				j = 0;
			}
		}

		//说明两个字符串完全遍历完毕
		if (j == s2Length) {
			return i - j;
		}else {
			return -1;
		}
	}
}
