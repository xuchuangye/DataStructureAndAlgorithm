package com.mashibing.mathrandom;

/**
 * 将不等概率 返回0或者1调整为等概率返回0或者1
 * @author xcy
 * @date 2022/4/7 - 15:26
 */
public class MathRandomTest {
	/**
	 * 测试次数
	 */
	private static final int testTime = 10000000;

	public static void main(String[] args) {
		int count = 0;
		for (int i = 0; i < testTime; i++) {
			if (noEqualReturn0Or1() == 0) {
				count++;
			}
		}

		System.out.println((double) count / (double) testTime);

		System.out.println("------------");

		count = 0;
		for (int i = 0; i < testTime; i++) {
			if (equalReturn0Or1() == 0) {
				count++;
			}
		}

		System.out.println((double) count / (double) testTime);
	}

	/**
	 * 不等待率返回0或者1
	 * @return
	 */
	public static int noEqualReturn0Or1() {
		return Math.random() < 0.8 ? 0 : 1;
	}

	/**
	 * 等概率返回0或者1
	 * @return
	 */
	public static int equalReturn0Or1() {
		int ans = 0;
		while (true) {
			ans = noEqualReturn0Or1();
			if (ans != noEqualReturn0Or1()) {
				break;
			}
		}
		/*int ans = 0;
		do {
			ans = noEqualReturn0Or1();
		}while (ans == noEqualReturn0Or1());*/
		return ans;
	}
}
