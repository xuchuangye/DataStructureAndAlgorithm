package com.mashibing.day02;

/**
 * 题目七：
 * 求出100万阶乘，有多少个0？
 *
 * 解题思路：
 * 1.2 * 5才能得到0
 * 2.5的因子比2的因子少
 * 3.求出1 ~ 100万中有多少个5的因子，就有多少个0
 * @author xcy
 * @date 2022/7/12 - 14:37
 */
public class Code07_OnemillionFactorial {
	public static void main(String[] args) {

	}

	public static int onemillionFactorial() {
		int ans = 0;
		for (int i = 1; i < 10000000; i++) {
			if (i % 5 == 0) {
				ans++;
			}
		}
		return ans;
	}
}
