package com.mashibing;

/**
 * 异或运算
 * 题目：计算一个int类型的数值的32位上总共出现多少个1
 *
 * @author xcy
 * @date 2022/4/6 - 16:46
 */
public class IntNumber_1_Count {
	public static void main(String[] args) {
		int number = 19929;
		int count = intNumber_1_Count(number);
		System.out.printf("int类型的number：%d的32位上总共出现了%d次1\n", number, count);
	}

	public static int intNumber_1_Count(int number) {
		int count = 0;
		while (number != 0) {
			//取出最右侧的1
			int right_1 = number & ((~number) + 1);
			count++;
			number ^= right_1;
		}
		return count;
	}
}
