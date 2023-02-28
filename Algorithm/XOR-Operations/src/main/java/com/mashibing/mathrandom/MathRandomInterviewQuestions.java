package com.mashibing.mathrandom;

/**
 * Math.random()经典面试题
 *
 * 题目：从1-5之间随机获取一个数调整到从1-7之间随机获取一个数
 * 解题思路：
 * 1、先解出等概率返回0-1
 * 2、再将1-7中随机获取一个数需要多少个二进制为能够满足，最少需要3个二进制位，解析为0-6中随机获取一个数再加1
 * 3、然后解出0-6随机获取一个数最后再加1
 * @author xcy
 * @date 2022/4/7 - 16:20
 */
public class MathRandomInterviewQuestions {
	private static final int testTime = 10000000;

	public static void main(String[] args) {
		/*int count = 0;
		for (int i = 0; i < testTime; i++) {
			if (methodReturn0Or1() < 1) {
				count++;
			}
		}
		System.out.println((double) count / (double) testTime);*/
		int[] counts = new int[8];
		for (int i = 0; i < testTime; i++) {
			//出现[0, 9 - 1]中的任意一个数
			int ans = methodReturn0_7();
			//将该数出现的次数加1
			counts[ans]++;
		}

		for (int i = 0; i < counts.length; i++) {
			System.out.println(i + "这个数出现了" + counts[i] + "次");
		}
		System.out.println("-------------------");

		counts = new int[8];
		for (int i = 0; i < testTime; i++) {
			//出现[0, 9 - 1]中的任意一个数
			int ans = methodReturn0_6();
			//将该数出现的次数加1
			counts[ans]++;
		}

		for (int i = 0; i < counts.length; i++) {
			System.out.println(i + "这个数出现了" + counts[i] + "次");
		}

		System.out.println("-------------------");

		counts = new int[8];
		for (int i = 0; i < testTime; i++) {
			//出现[0, 9 - 1]中的任意一个数
			int ans = (methodReturn0_6()) + 1;
			//将该数出现的次数加1
			counts[ans]++;
		}

		for (int i = 0; i < counts.length; i++) {
			System.out.println(i + "这个数出现了" + counts[i] + "次");
		}
	}

	/**
	 * 1、等概率返回[1, 5]左闭右闭区间
	 *
	 * @return 等概率返回1-5中的任意一个数
	 */
	public static int methodReturn1_5() {
		return (int) (Math.random() * 5) + 1;
	}

	/**
	 * 2、等概率返回[0, 1]左闭右闭区间
	 *
	 * @return 等概率返回0或者1
	 */
	public static int methodReturn0Or1() {
		int is1_5 = methodReturn1_5();
		while (is1_5 == 3) {
			is1_5 = methodReturn1_5();
			if (is1_5 != 3) {
				break;
			}
		}
		if (is1_5 < 3) {
			is1_5 = 0;
		} else {
			is1_5 = 1;
		}
		return is1_5;
	}

	/**
	 * 3、等概率返回[0, 7]左闭右闭区间
	 * @return 等概率返回0-7的任意一个数
	 */
	public static int methodReturn0_7() {
		/*StringBuilder stringBuilder = new StringBuilder("");
		for (int i = 0; i < 3; i++) {
			int random = methodReturn0Or1();
			stringBuilder.append(random);
		}
		String s = new String(stringBuilder);
		//System.out.println(s);
		int i = Integer.parseInt(s, 2);
		return i;*/
		return (methodReturn0Or1() << 2) + (methodReturn0Or1() << 1) + methodReturn0Or1();
	}

	/**
	 * 4、等概率返回[0, 6]左闭右闭区间
	 *
	 * @return 等概率返回0-6中的任意一个数
	 */
	public static int methodReturn0_6() {
		int is0_6 = methodReturn0_7();
		while (is0_6 == 7) {
			is0_6 = methodReturn0_7();
			if (is0_6 != 7) {
				break;
			}
		}
		return is0_6;
	}
}
