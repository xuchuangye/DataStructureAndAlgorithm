package com.mashibing.arithmetic.dynamic;

import java.util.Arrays;

/**
 * 动态规划 -> 背包问题
 *
 *
 * 背包问题的思路分析：
 * i表示物品的数量，j表示背包的容量
 * v[i][0] = 0;//表示不管物品数量有多少个，背包的容量为0，物品都放不进去，那么背包中的价值就为0
 * v[0][j] = 0;//表示不管背包的容量有多大，物品的数量为0，那么背包中的价值为0
 * v[i][0]=v[0][j]=0;
 *
 * 因为动态规划的核心在于：下一个子问题的求解是建立在上一个子问题的解的基础之上
 * 所以，当物品的重量过大，背包容量过小，导致物品无法放入到背包当中，那么就继承上一个子问题的解
 * 也就是当前物品的价值就等于上一个物品的价值的策略方式
 * 当w[i]> j时：v[i][j]=v[i-1][j]
 * j >= w[i]表示装了当前物品i之后，j这个背包仍然有剩余的容量
 * v[i][j]=max{v[i-1][j], v[i]+v[i-1][j-w[i]]}
 * 表示从上一个阶段的最大价值v[i-1][j] 和 当前物品的价值 + [装入当前物品之后，减去当前物品的数量][背包的剩余空间]取出最大值
 * 当j >= w[i]时： v[i][j]=max{v[i-1][j], v[i]+v[i-1][j-w[i]]}
 *
 * @author xcy
 * @date 2022/4/1 - 9:53
 */
public class KnapsackDemo {
	public static void main(String[] args) {
		//物品的重量
		int[] weight = {1, 4, 3};
		//物品的价值
		int[] price = {1500, 3000, 2000};
		//物品的数量
		int num = weight.length;
		//背包的容量
		int capacity = 4;

		//创建一个二维数组，num + 1表示物品的数量 + 1，capacity + 1表示背包的容量 + 1
		int[][] v = new int[num + 1][capacity + 1];
		//创建一个二维数组记录在背包中放入的物品
		int[][] path = new int[num + 1][capacity + 1];

		//第一行的元素全部置为0
		for (int i = 0; i < v.length; i++) {
			//物品数量为0，背包容量再大，没有物品可以装，也没有价值
			v[0][i] = 0;
		}

		//第一列的元素全部置为0
		for (int i = 0; i < v.length; i++) {
			//物品数量再多，背包容量为0，物品装不进去，也没有价值
			v[i][0] = 0;
		}

		//从第二行开始，跳过第一行
		for (int i = 1; i < v.length; i++) {
			//从第二列开始，跳过第一列
			for (int j = 1; j < v[0].length; j++) {
				//当背包的容量放不下物品的重量时，i从1开始的，所以需要减1
				if (weight[i - 1] > j) {
					//当前物品的价值继承上一个物品的价值
					v[i][j] = v[i - 1][j];
				}
				//当背包的容量能够放得下物品的重量时
				else {
					//当前物品的价值从上一个物品的价值和当前物品的价值加上剩余容量的最大价值中选择最大值
					//当j >= w[i]时： v[i][j]=Math.max(v[i-1][j], v[i]+v[i-1][j-w[i]])
					//v[i][j] = Math.max(v[i - 1][j], price[i - 1] + v[i - 1][j - weight[i - 1]]);
					if (v[i - 1][j] < price[i - 1] + v[i - 1][j - weight[i - 1]]) {
						v[i][j] = price[i - 1] + v[i - 1][j - weight[i - 1]];
						//记录放入第i个物品和物品的重量是多少
						path[i][j] = 1;
					}else {
						v[i][j] = v[i - 1][j];
					}
				}
			}
		}

		//遍历二维数组
		for (int[] arr : v) {
			System.out.println(Arrays.toString(arr));
		}

		int i = path.length - 1;
		int j = path[0].length - 1;

		while (i > 0 && j > 0) {
			if (path[i][j] == 1) {
				System.out.printf("第%d个物品放入到背包中，价格是%d\n", i, price[i - 1]);
				//每次放入一个物品，背包就减去该物品的重量，以及物品的数量减1
				j -= weight[i - 1];
			}
			//物品的数量减1
			i--;
		}
	}
}
