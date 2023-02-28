package com.mashibing.day01;

import com.mashibing.common.TestUtils;

/**
 * 题目四：
 * 一个数组中只有两种字符'G'和'B'，
 * 可以让所有的G都放在左侧，所有的B都放在右侧
 * 或者可以让所有的G都放在右侧，所有的B都放在左侧，
 * 但是只能在相邻字符之间进行交换操作，返回至少需要交换几次
 * <p>
 * 解题思路：
 * 假设字符串："BBGGBBGGBB"
 * index =    0123456789
 * 假设G放在左边，循环遍历字符串的字符数组
 * 当遍历到index = 2时，是'G'字符，将'G'字符放在0位置上，交换次数2次
 * 当遍历到index = 3时，是'G'字符，将'G'字符放在1位置上，交换次数2次
 * 当遍历到index = 6时，是'G'字符，将'G'字符放在2位置上，交换次数4次
 * 当遍历到index = 7时，是'G'字符，将'G'字符放在3位置上，交换次数4次
 * 得到总交换次数
 * <p>
 * 时间复杂度：
 * 在遍历中，index永远不回退，时间复杂度：O(N)
 *
 * @author xcy
 * @date 2022/7/6 - 8:32
 */
public class Code04_MinSwapStep {
	public static void main(String[] args) {
		int maxLen = 100;
		int testTime = 1000000;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			String str = TestUtils.randomString(maxLen);
			int ans1 = minExchangeCount(str);
			int ans2 = minSwapCount(str);
			if (ans1 != ans2) {
				System.err.println("测试出错");
			}
		}
		System.out.println("测试结束");
	}

	/**
	 * @param s
	 * @return 返回G在左边B在右边或者G在右边B在左边的交换的最小次数
	 */
	public static int minExchangeCount(String s) {
		if (s == null || "".equals(s)) {
			return 0;
		}
		char[] str = s.toCharArray();
		int situation1 = 0;
		int bIndex = 0;
		for (int i = 0; i < str.length; i++) {
			if (str[i] == 'B') {
				situation1 += (i - (bIndex));
				bIndex++;
			}
		}

		int situation2 = 0;
		int gIndex = 0;
		for (int i = 0; i < str.length; i++) {
			if (str[i] == 'G') {
				situation2 += (i - (gIndex));
				gIndex++;
			}
		}
		return Math.min(situation1, situation2);
	}

	/**
	 * @param s
	 * @return 返回G在左边B在右边或者G在右边B在左边的交换的最小次数
	 */
	public static int minSwapCount(String s) {
		if (s == null || "".equals(s)) {
			return 0;
		}
		char[] str = s.toCharArray();
		int situation1 = 0;
		int situation2 = 0;
		int bIndex = 0;
		int gIndex = 0;
		for (int i = 0; i < str.length; i++) {
			//方案1，当前的'G'去左边
			if (str[i] == 'G') {
				situation1 += (i - (gIndex++));
			}
			//方案2，当前的'B'去左边
			else {
				situation2 += (i - (bIndex++));
			}
		}
		return Math.min(situation1, situation2);
	}

}
