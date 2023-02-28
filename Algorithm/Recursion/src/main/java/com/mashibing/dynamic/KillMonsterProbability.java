package com.mashibing.dynamic;

/**
 * 给定三个参数：N, M, K
 * 怪物有N滴血，等着英雄来砍自己
 * 英雄每打击一次，都会让怪物减少[0, M]滴血
 * 到底流失多少滴血？每次在[0, M]上等概率的获得一个值
 * 求K次攻击之后，返回英雄把怪物杀死的几率
 * @author xcy
 * @date 2022/5/12 - 8:26
 */
public class KillMonsterProbability {
	public static void main(String[] args) {
		int NMax = 10;
		int MMax = 10;
		int KMax = 10;
		int testTime = 200;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int N = (int) (Math.random() * NMax);
			int M = (int) (Math.random() * MMax);
			int K = (int) (Math.random() * KMax);
			double ans1 = killMonsterProbability(N, M, K);
			double ans2 = killMonsterProbabilityWithTable(N, M, K);
			double ans3 = killMonsterProbabilityWithTableOptimization(N, M, K);
			if (ans1 != ans2 || ans1 != ans3) {
				System.out.println("Oops!");
				break;
			}
		}
		System.out.println("测试结束");
	}

	/**
	 * 怪物有N滴血，攻击的范围0-M，攻击K次之后，返回杀死怪物的概率 --> 使用暴力递归的方式
	 *
	 * @param N 怪物的血量
	 * @param M 攻击的范围0 - M
	 * @param K 可以攻击的次数
	 * @return 怪物有N滴血，攻击的范围0-M，攻击K次之后，返回杀死怪物的概率
	 */
	public static double killMonsterProbability(int N, int M, int K) {
		if (N <= 0 || M <= 0 || K <= 0) {
			return .0;
		}
		return (double) (coreLogic(N, M, K)) / Math.pow(M + 1, K);
	}

	/**
	 * 怪物有N滴血，攻击的范围0-M，攻击K次之后，返回杀死怪物的次数
	 *
	 * @param N 怪物的血量
	 * @param M 攻击的范围0 - M
	 * @param K 可以攻击的次数
	 * @return 怪物有N滴血，攻击的范围0-M，攻击K次之后，返回杀死怪物的次数
	 */
	public static int coreLogic(int N, int M, int K) {
		//可以攻击的次数为0
		if (K == 0) {
			//如果怪物的血量<=0说明被杀死了，返回1，否则没有被杀死，返回0
			return N <= 0 ? 1 : 0;
		}
		//怪物的血量为0
		if (N <= 0) {
			//直接返回(M + 1)的剩余次数K次方
			return (int) Math.pow(M + 1, K);
		}

		int ways = 0;

		//假设第0次攻击0滴血，第1次1滴血，第M次M滴血
		for (int i = 0; i <= M; i++) {
			//下一次的攻击时，怪物还有N-i滴血，M不变，攻击次数K-1
			ways += coreLogic(N - i, M, K - 1);
		}
		return ways;
	}

	/**
	 * 怪物有N滴血，攻击的范围0-M，攻击K次之后，返回杀死怪物的概率 --> 使用动态规划的方式
	 *
	 * @param N 怪物的血量
	 * @param M 攻击的范围0 - M
	 * @param K 可以攻击的次数
	 * @return 怪物有N滴血，攻击的范围0-M，攻击K次之后，返回杀死怪物的概率
	 */
	public static double killMonsterProbabilityWithTable(int N, int M, int K) {
		if (N <= 0 || M <= 0 || K <= 0) {
			return .0;
		}
		int[][] table = new int[N + 1][K + 1];
		//if (K == 0) {
		//	return N <= 0 ? 1 : 0;
		//}
		//table[N == 0][K == 0] = 1;
		table[0][0] = 1;

		for (int hp = 0; hp <= N; hp++) {
			for (int times = 1; times <= K; times++) {
				//当hp == 0时，除了第times == 0列是1，其余所有的都是(int) Math.pow(M + 1, times)
				table[0][times] = (int) Math.pow(M + 1, times);
				int ways = 0;
				for (int index = 0; index <= M; index++) {
					//如果不越界，获得真实格子的值
					if (hp - index >= 0) {
						ways += table[hp - index][times - 1];
					} else {
						//如果越界，拿不到table[][]的值，只要怪物的血量 < 0，并且还有剩余的攻击次数
						//获得这么多
						ways += (int) Math.pow(M + 1, times - 1);
					}
				}
				table[hp][times] = ways;
			}
		}

		double all = Math.pow(M + 1, K);
		double kill = table[N][K];
		return kill / all;
	}

	/**
	 * 怪物有N滴血，攻击的范围0-M，攻击K次之后，返回杀死怪物的概率 --> 使用动态规划优化之后的方式
	 *
	 * @param N 怪物的血量
	 * @param M 攻击的范围0 - M
	 * @param K 可以攻击的次数
	 * @return 怪物有N滴血，攻击的范围0-M，攻击K次之后，返回杀死怪物的概率
	 */
	public static double killMonsterProbabilityWithTableOptimization(int N, int M, int K) {
		if (N <= 0 || M <= 0 || K <= 0) {
			return .0;
		}
		int[][] table = new int[N + 1][K + 1];
		table[0][0] = 1;


		for (int hp = 0; hp <= N; hp++) {
			for (int times = 1; times <= K; times++) {
				//当hp == 0时，除了第times == 0列是1，其余所有的都是(int) Math.pow(M + 1, times)
				table[0][times] = (int) Math.pow(M + 1, times);

				if (hp >= 1) {
					//table[i][j - 1] = table[i - 1][j - 1 ... j - 1 - M]
					//table[times][hp - 1] = table[times - 1][hp - 1 ... hp - 1 - M]
					//table[i][j] = table[i - 1][j ... j - M]
					//table[times][hp] = table[times - 1][hp ... hp - M]
					//table[i][j] = table[i - 1][j] + table[i][j - 1] - table[i - 1][j - 1 - M]
					//table[times][hp] = table[times - 1][hp] + table[times][hp - 1]
					// - table[times - 1][hp - 1 - M]
					table[hp][times] += table[hp][times - 1] + table[hp - 1][times];
				}
				//怪物还有血量
				if (hp - 1 - M >= 0) {
					table[hp][times] -= table[hp - 1 - M][times - 1];
				}
				//怪物没有血量，表示越界了
				else {
					//越界之后，数组已经无法表示，那就使用公式：(M + 1)的K次方
					//依然要减去怪物血量为0的各种杀死怪物的方法数
					table[hp][times] -= (int) Math.pow(M + 1, times - 1);
				}
			}
		}

		//总次数
		double all = Math.pow(M + 1, K);
		//杀死怪物的次数
		double kill = table[N][K];
		//
		return kill / all;
	}
}
