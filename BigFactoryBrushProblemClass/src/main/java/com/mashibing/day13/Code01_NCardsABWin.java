package com.mashibing.day13;

/**
 * 谷歌面试题扩展版，面值为1~N的牌组成一组，每次你从组里等概率的抽出1~N中的一张，下次抽会换一个新的组，有无限组
 * 当累加和< a时，你将一直抽牌；当累加和>= a且< b时，你将获胜；当累加和>= b时，你将失败
 * 返回获胜的概率，给定的参数为N，a，b
 *
 * 题目启示：
 * 1.有枚举行为，也就是for或者while循环，通过查看是否能够将循环改为位置依赖
 * 2.推的时候多观察，有限的几个位置，BaseCase需要重点关注Coding边界
 * 3.别慌，看数据量，量力而行
 *
 * @author xcy
 * @date 2022/8/1 - 15:01
 */
public class Code01_NCardsABWin {
	public static void main(String[] args) {

	}

	/**
	 * 谷歌面试题原题：
	 * 面值为1~10的牌组成一组
	 * 每次你从组里等概率的抽出1~10中的一张
	 * 下次抽会换一个新的组，有无限组，也就表示等概率
	 * 当累加和< 17时，你将一直抽牌；
	 * 当累加和>= 17且< 21时，你将获胜；
	 * 当累加和>= 21时，你将失败
	 * 返回获胜的概率
	 *
	 * @return 返回获胜的概率
	 */
	public static double p1() {
		return process(0);
	}

	/**
	 * @param cur 当前的累加和
	 * @return 返回玩家赢的概率
	 */
	public static double process(int cur) {
		if (cur >= 17 && cur < 21) {
			return 1.0;
		}
		if (cur >= 21) {
			return 0.0;
		}
		double win = 0.0;
		for (int i = 1; i <= 10; i++) {
			win += process(cur + i);
		}
		return win / 10;
	}

	/**
	 * 谷歌面试题扩展版：
	 * 面值为1~N的牌组成一组，
	 * 每次你从组里等概率的抽出1~N中的一张，
	 * 下次抽会换一个新的组，有无限组
	 * 当累加和< a时，你将一直抽牌；
	 * 当累加和>= a且< b时，你将获胜；
	 * 当累加和>= b时，你将失败
	 * 返回获胜的概率，给定的参数为N，a，b
	 *
	 * @param N 牌的面值：1 ~ N
	 * @param a 累加和a
	 * @param b 累加和b
	 * @return 返回赢的概率
	 */
	public static double p2(int N, int a, int b) {
		if (N < 1 || a >= b || a < 0) {
			return 0.0;
		}

		if (b - a >= N) {
			return 1.0;
		}
		return process2(0, N, a, b);
	}

	/**
	 * @param cur 当前累加和
	 * @param N   牌的面值：1 ~ N
	 * @param a   累加和a
	 * @param b   累加和b
	 * @return 返回赢的概率
	 */
	public static double process2(int cur, int N, int a, int b) {
		if (cur >= a && cur < b) {
			return 1.0;
		}
		if (cur > b) {
			return 0.0;
		}
		double win = 0.0;
		for (int i = 1; i < N; i++) {
			win += process2(cur + N, N, a, b);
		}
		return win / N;
	}

	/**
	 * @param N 牌的面值：1 ~ N
	 * @param a 累加和a
	 * @param b 累加和b
	 * @return 返回赢的概率
	 */
	public static double p3(int N, int a, int b) {
		if (N < 1 || a >= b || a < 0) {
			return 0.0;
		}

		if (b - a >= N) {
			return 1.0;
		}

		return process3(0, N, a, b);
	}

	public static double process3(int cur, int N, int a, int b) {
		if (cur >= a && cur < b) {
			return 1.0;
		}
		if (cur > b) {
			return 0.0;
		}

		if (cur == a - 1) {
			return (1.0) * (b - a) / N;
		}
		//Coding边界的两种情况：
		//情况1：i + N + 1 >= b
		//f(i) = (f(i + 1) + f(i + 1) * N
		//假设：
		//N = 6
		//13 14 15 16 17 | 18 19 20 | 21
		//                  a          b
		//f(17) = f(18) + f(18) * 6
		double win = process3(cur + 1, N, a, b) + process3(cur + 1, N, a, b) * N;
		//情况2：i + N + 1 < b
		//f(i) = (f(i + 1) + f(i + 1) * N      - f(i + N + 1)) / N
		//f(4) = (f(5) + f(5) * 6      - f(11)) / 6
		//f(13) = (f(14) + f(14) * 6      - f(20)) / 6
		if (cur + N + 1 < b) {
			win -= process3(cur + N + 1, N, a, b);
		}
		return win / N;
	}

	/**
	 * @param N 牌的面值：1 ~ N
	 * @param a 累加和a
	 * @param b 累加和b
	 * @return 返回赢的概率
	 */
	public static double p4(int N, int a, int b) {
		if (N < 1 || a >= b || a < 0) {
			return 0.0;
		}

		if (b - a >= N) {
			return 1.0;
		}

		double[] dp = new double[b];
		for (int i = a; i < b; i++) {
			dp[i] = 1;
		}
		if (a - 1 >= 0) {
			dp[a - 1] = (1.0) * (b - a) / N;
		}
		for (int cur = a - 2; cur >= 0; cur--) {
			double win = dp[cur + 1] + dp[cur + 1] * N;
			if (cur + N + 1 < b) {
				win -= dp[cur + N + 1];
			}
			dp[cur] = win / N;
		}
		return dp[0];
	}
}
