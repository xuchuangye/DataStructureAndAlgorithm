package com.mashibing.learn;

/**
 * @author xcy
 * @date 2021/8/19 - 21:37
 */

/**
 * 打印二进制
 */
public class PrintBinary {
	public static void print(int num) {
		for (int i = 31; i >= 0; i--) {
			//&与运算，只有1和1才为1，其余都是0
			System.out.print((num & (1 << i)) == 0 ? 0 : 1);
		}
		System.out.println();
	}

	public static void main(String[] args) {
		int num = 10;
		print(num);
	}
}
