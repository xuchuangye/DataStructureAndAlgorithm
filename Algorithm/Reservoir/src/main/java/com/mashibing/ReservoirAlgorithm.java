package com.mashibing;

/**
 * 蓄水池算法
 * 解决的问题：
 * 假设有一个源源不断吐出不同球的机器，
 * 只有一个装下10个球的袋子，每一个从机器中吐出的球，要么放入袋子，要么永远的扔掉
 * 如何做到机器吐出每一个球之后，所有吐出的球都等概率被放进袋子里
 * <p>
 * 基本思路：
 * 1.假设机器吐出i号球，有10 / i的概率要不要放入袋子，实现函数f(i)，如果i == 1 ~ 10，那么放入袋子，i > 10就永远扔掉
 * 2.袋子中的每一个球，不管是多少号球，等概率随机扔掉一个，让i号球进来
 *
 * @author xcy
 * @date 2022/5/22 - 15:38
 */
public class ReservoirAlgorithm {
	public static final int NUMBER = 1729;
	public static void main(String[] args) {
		reservoirAlgorithm(NUMBER);
	}

	/**
	 *
	 * @param i
	 * @return 随机返回1 - i的任意一个数
	 */
	public static int random(int i) {
		return (int) (Math.random() * i + 1);
	}

	/**
	 * 蓄水池算法
	 */
	public static void reservoirAlgorithm(int number) {
		//测试次数
		int testTimes = 1000000;
		//词频统计
		int[] count = new int[number + 1];
		for (int i = 0; i < testTimes; i++) {
			//袋子数组，容量为10
			int[] bag = new int[10];
			int bagIndex = 0;
			//一共准备多少个球
			for (int num = 1; num <= number; num++) {
				//num <= 10
				if (num <= 10) {
					bag[bagIndex++] = num;
				}
				//num > 10
				else {
					//满足10 / num的概率，说明被选中了，放入袋子
					if (random(num) <= 10) {
						//随机扔掉袋子中的一个球
						bagIndex = (int) (Math.random() * 10);
						//随机扔掉一个球之后，在该位置上放入新的球
						bag[bagIndex] = num;
					}
					//random(num) > 10，表示10 / num就没有选中，没有选中就直接扔掉
				}
			}
			for (int num : bag) {
				count[num]++;
			}
		}
		for (int num = 1; num <= number; num++) {
			System.out.println(count[num]);
		}
	}
}
