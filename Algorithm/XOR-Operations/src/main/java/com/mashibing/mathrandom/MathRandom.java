package com.mashibing.mathrandom;

/**
 * @author xcy
 * @date 2022/4/7 - 15:26
 */
public class MathRandom {
	/**
	 * 测试次数
	 */
	private static final int testTime = 10000000;

	public static void main(String[] args) {
		//mathRandomXToXPowerThree();
		mathRandomXToXPowerTwo();
		//mathRandomFrequency(10);
	}

	/**
	 * 测试Math.random()将[0, x)范围的任意数出现的次数由x调整到x³
	 */
	private static void mathRandomXToXPowerThree() {
		double x = 0.7;
		int count = 0;
		for (int i = 0; i < testTime; i++) {
			if (xToXPowerThree() < x) {
				count++;
			}
		}
		System.out.println((double) count / (double) testTime);
		System.out.println(Math.pow(x, 3));
	}

	/**
	 * 测试Math.random()将[0, x)范围的任意数出现的次数由x调整到x²
	 */
	private static void mathRandomXToXPowerTwo() {
		double x = 0.7;
		int count = 0;
		for (int i = 0; i < testTime; i++) {
			if (xToXPowerTwo() < x) {
				count++;
			}
		}
		System.out.println((double) count / (double) testTime);
		System.out.println(Math.pow(x, 2));
		//System.out.println((double) 1 - Math.pow((double) 1 - x, 2));
	}

	/**
	 * 使用Math.random()测试[0, length)中任意数出现的次数为等概率
	 * @param length [0, length)左闭右开
	 */
	private static void mathRandomFrequency(final int length) {
		int[] counts = new int[length];
		for (int i = 0; i < testTime; i++) {
			//出现[0, 9 - 1]中的任意一个数
			int ans = (int) (Math.random() * length);
			//将该数出现的次数加1
			counts[ans]++;
		}

		for (int i = 0; i < counts.length; i++) {
			System.out.println(i + "这个数出现了" + counts[i] + "次");
		}
	}

	/**
	 * @return [0, x]范围上的任意数出现的概率由x调整到x²
	 */
	private static double xToXPowerTwo() {
		return Math.max(Math.random(), Math.random());
	}

	/**
	 * @return [0, x]范围上的任意数出现的概率由x调整到x²
	 */
	private static double xToXPowerThree() {
		return Math.max(Math.random(), Math.max(Math.random(), Math.random()));
	}
}
