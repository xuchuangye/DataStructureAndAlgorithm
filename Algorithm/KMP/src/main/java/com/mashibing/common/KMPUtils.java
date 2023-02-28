package com.mashibing.common;

/**
 * KMP算法的工具类
 * @author xcy
 * @date 2022/5/21 - 8:20
 */
public class KMPUtils {
	// for test
	public static String getRandomString(int possibilities, int size) {
		char[] ans = new char[(int) (Math.random() * size) + 1];
		for (int i = 0; i < ans.length; i++) {
			ans[i] = (char) ((int) (Math.random() * possibilities) + 'a');
		}
		return String.valueOf(ans);
	}
}
