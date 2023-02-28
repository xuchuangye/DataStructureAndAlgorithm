package com.mashibing;

/**
 * 打印int类型的数的二进制形式
 *
 * + - * / 底层都是二进制位运算
 * @author xcy
 * @date 2022/4/6 - 18:28
 */
public class PrintNumber_Binary {
	public static void main(String[] args) {
		/*int number = Integer.MAX_VALUE;
		printNumber_Binary(number);
		//负数需要补码取反再加1，因为系统底层不管什么运算都走一套逻辑
		int num = -Integer.MAX_VALUE;
		printNumber_Binary(num);

		int a = Integer.MIN_VALUE;
		int b = ~a + 1;
		int c = -a;
		System.out.println(a);
		System.out.println(b);
		System.out.println(c);
		printNumber_Binary(a);
		printNumber_Binary(b);
		printNumber_Binary(c);*/

		int count = print(3);
		System.out.println(count);
	}

	public static void printNumber_Binary(int number) {
		for (int i = 31; i >= 0; i--) {
			System.out.print((number & 1 << i) == 0 ? "0" : "1" );
		}
		System.out.println();
	}

	/**
	 * 返回N的阶乘之和
	 * @param N
	 * @return 返回N的阶乘
	 */
	public static int print(int N) {
		int cur = 1;
		int result = 0;
		for (int i = 1; i <= N; i++) {
			cur = cur * i;
			result += cur;
		}
		return result;
	}
}
