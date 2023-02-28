package com.mashibing.test;

/**
 * @author xcy
 * @date 2022/3/19 - 9:35
 */
public class RecursionDemo_001 {
	public static void main(String[] args) {

		//int result = test(4);
		//System.out.printf("方法最终打印结果%d\n", result);
		print(4);
		int result = factorial(4);
		System.out.println("最终结果" + result);
	}

	/**
	 * 打印问题
	 * @param n
	 */
	public static void print(int n) {
		if (n > 2) {
			print(n - 1);
		}
		System.out.println("n = " + n);
	}

	/**
	 * 阶乘问题
	 * @param n
	 * @return
	 */
	public static int factorial(int n) {
		if (n == 1) {
			return 1;
		}else {
			return factorial(n - 1) * n;
		}
	}
}
