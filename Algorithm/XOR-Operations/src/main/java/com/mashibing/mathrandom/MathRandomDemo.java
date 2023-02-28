package com.mashibing.mathrandom;

/**
 * Math.random()经典面试题
 * <p>
 * 题目：从3-15之间随机获取一个数调整到从17-59之间随机获取一个数
 *
 * @author xcy
 * @date 2022/4/7 - 17:14
 */
public class MathRandomDemo {
	public static void main(String[] args) {
		int testTime = 10000000;

		int[] counts = new int[16];

		System.out.println("-----------------------");
		/*for (int i = 0; i < testTime; i++) {
			int ans = methodReturn3_15();
			counts[ans]++;
		}

		for (int i = 0; i < counts.length; i++) {
			System.out.println(i + "这个数出现了" + counts[i] + "次");
		}*/

		System.out.println("-----------------------");
		/*counts = new int[2];

		for (int i = 0; i < testTime; i++) {
			int ans = methodReturn0Or1();
			counts[ans]++;
		}

		for (int i = 0; i < counts.length; i++) {
			System.out.println(i + "这个数出现了" + counts[i] + "次");
		}*/

		System.out.println("-----------------------");
		/*counts = new int[64];

		for (int i = 0; i < testTime; i++) {
			int ans = methodReturn0_63();
			counts[ans]++;
		}

		for (int i = 0; i < counts.length; i++) {
			System.out.println(i + "这个数出现了" + counts[i] + "次");
		}*/

		System.out.println("-----------------------");
		/*counts = new int[60];

		for (int i = 0; i < testTime; i++) {
			int ans = methodReturn0_42();
			counts[ans]++;
		}

		for (int i = 0; i < counts.length; i++) {
			System.out.println(i + "这个数出现了" + counts[i] + "次");
		}*/

		System.out.println("-----------------------");
		counts = new int[60];

		for (int i = 0; i < testTime; i++) {
			int ans = methodReturn17_59();
			counts[ans]++;
		}

		for (int i = 0; i < counts.length; i++) {
			if (counts[i] != 0) {
				System.out.println(i + "这个数出现了" + counts[i] + "次");
			}
		}
	}

	/**
	 * 从3-15中随机获取一个数
	 *
	 * @return
	 */
	public static int methodReturn3_15() {
		return (int) (Math.random() * 13) + 3;
	}

	/**
	 * 从0-1中随机获取一个数
	 *
	 * @return
	 */
	public static int methodReturn0Or1() {
		int answer = methodReturn3_15();
		int mid = (15 + 3) / 2;
		while (answer == mid) {
			answer = methodReturn3_15();
			if (answer != mid) {
				break;
			}
		}
		if (answer < mid) {
			answer = 0;
		} else {
			answer = 1;
		}
		return answer;
	}

	/**
	 * 从0-63中随机获取一个数
	 *
	 * @return
	 */
	public static int methodReturn0_63() {
		/*return (methodReturn0_1() << 5) +
				(methodReturn0_1() << 4) +
				(methodReturn0_1() << 3) +
				(methodReturn0_1() << 2) +
				(methodReturn0_1() << 1) +
				(methodReturn0_1());*/
		StringBuilder stringBuilder = new StringBuilder("");
		for (int i = 0; i < 6; i++) {
			int random = methodReturn0Or1();
			stringBuilder.append(random);
		}
		String s = new String(stringBuilder);
		return Integer.parseInt(s, 2);
	}

	/**
	 * 从0-42中随机获取一个数
	 *
	 * @return
	 */
	public static int methodReturn0_42() {
		int answer = methodReturn0_63();
		while (answer >= 43) {
			answer = methodReturn0_63();
			if (answer <= 42) {
				break;
			}
		}
		return answer;
	}

	/**
	 * 从0-42中随机获取一个数
	 *
	 * @return
	 */
	public static int methodReturn17_59() {
		return methodReturn0_42() + 17;
	}
}
