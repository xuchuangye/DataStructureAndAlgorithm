package com.mashibing.guessingmethod_externalinformationsimplification;

/**
 * 题目一：
 * 给定一个数组arr，代表一排有分数的气球。每打爆一个气球都能获得分数，假设打爆气球的分数为X，获得分数的规则如下:
 * 1)如果被打爆气球的左边有没被打爆的气球，找到离被打爆气球最近的气球，假设分数为L;
 * 如果被打爆气球的右边有没被打爆的气球，找到离被打爆气球最近的气球，假设分数为 R。
 * 获得分数为 L * X * R。
 * 2)如果被打爆气球的左边有没被打爆的气球，找到离被打爆气球最近的气球，假设分数为L;
 * 如果被打爆气球的右边所有气球都已经被打爆。获得分数为 L * X。
 * 3)如果被打爆气球的左边所有的气球都已经被打爆;
 * 如果被打爆气球的右边有没被打爆的气球，找到离被打爆气球最近的气球，假设分数为R;
 * 如果被打爆气球的右边所有气球都已经被打爆。获得分数为 X * R。
 * 4)如果被打爆气球的左边和右边所有的气球都已经被打爆。获得分数为 X。
 * 目标是打爆所有气球，获得每次打爆的分数。通过选择打爆气球的顺序，可以得到不同的总分，请返回能获得的最大分数。
 * 【举例】
 * arr = {3,2,5}
 * 1)如果先打爆3，获得3*2;再打爆2，获得2*5;最后打爆5，获得5;
 * 最后总分21
 * 2)如果先打爆3，获得3*2;再打爆5，获得2*5;最后打爆2，获得2;
 * 最后总分18
 * 3)如果先打爆2，获得3*2*5;再打爆3，获得3*5;最后打爆5，获得5;
 * 最后总分50
 * 4)如果先打爆2，获得3*2*5;再打爆5，获得3*5;最后打爆3，获得3;
 * 最后总分48
 * 5)如果先打爆5，获得2*5;再打爆3，获得3*2;最后打爆2，获得2;
 * 最后总分18
 * 6)如果先打爆5，获得2*5;再打爆2，获得3*2;最后打爆3，获得3;
 * 最后总分19
 * 返回能获得的最大分数为50
 * <p>
 * 思路分析：
 * 1.创建方法返回值int类型的f(L, R)，表示在arr[L ... R]范围上打爆气球
 * (1)必须满足L - 1位置上的气球没被打爆，R + 1位置上的气球没被打爆的情况
 * 2.f(L, R)如何进行调用，根据最后打爆某个位置的气球进行调用
 * 假设
 * arr[] = {2, 3, 5, 2, 1}
 * index =  0  1  2  3  4
 * (1).假设0位置的气球最后被打爆
 * 先调用f(1, 4)得到分数，再调用[左边位置(-1)的气球被打爆 = 1 * f(0, 0) * 右边的气球都被打爆 = 1]
 * 也就是f(1, 4) + (1 * f(0, 0) * 1)
 * (2).假设1位置的气球最后被打爆
 * 先调用f(0, 0)得到分数，再调用f(2, 4)得到分数，最后调用[左边气球都被打爆 = 1 * f(1, 1) * 右边的气球都被打爆 = 1]
 * 也就是f(0, 0) + f(2, 4) + (1 * f(1, 1) * 1)
 * 3.将原始的arr[]的最左侧和最右侧都添加1，表示永远都不会被打爆
 * 举例：
 * 原始数组arr[] = {2, 3, 1, 5, 4}
 * index =         0  1  2  3  4
 * 新的数组array[] = {1, 2, 3, 1, 5, 4, 1}
 * index =           0  1  2  3  4  5  6
 * 求出新的数组array[]对应的方法f(1, 5)，就是原始数组arr[]对应的方法f(0, 4)，可以避免很多边界问题
 * (3).可以省去左侧的具体情况和省去右侧的具体情况
 * 4.所以这道题有两个技巧：
 * (1).补潜台词 -> 外部信息简化的试法，比如：L - 1位置的气球最后被打爆和R + 1位置的气球最后被打爆
 * 如果最后被打爆的试法不行，那么就换一个最先被打爆的试法
 * (2).可能性的展开方面有什么分类，通过尝试入手，如果是最先被打爆的分类，这道题就废了
 * 如果是最后被打爆的分类，这道题就OK
 * (3).潜台词和可能性的联动关系
 * <p>
 * LeetCode测试链接：https://leetcode.cn/problems/burst-balloons/
 *
 * @author xcy
 * @date 2022/6/17 - 8:46
 */
public class Code01_BurstBalloons {
	public static void main(String[] args) {
		int[] arr = new int[]{4, 2, 5, 6, 8};
		int total1 = maxCoins(arr);
		int total2 = maxCoinsWithDynamicProgramming(arr);
		System.out.println(total1 == total2);
	}

	/**
	 * 使用暴力递归的方式
	 *
	 * @param arr
	 * @return
	 */
	public static int maxCoins(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}

		int[] newArr = new int[arr.length + 2];
		newArr[0] = 1;
		for (int i = 0; i < arr.length; i++) {
			newArr[i + 1] = arr[i];
		}
		newArr[newArr.length - 1] = 1;
		//新数组newArr[]的1 ~ newArr.length - 2范围就相当于原始数组arr[]的0 ~ arr.length - 1的范围
		//return coreLogic(newArr, 1, arr.length);
		return coreLogic(newArr, 1, newArr.length - 2);
	}

	/**
	 * 前提：在arr[]中，L - 1和R + 1位置的气球永远不会被打爆
	 *
	 * @param arr
	 * @param L
	 * @param R
	 * @return
	 */
	public static int coreLogic(int[] arr, int L, int R) {
		if (L == R) {
			return arr[L - 1] * arr[R] * arr[R + 1];
		}
		//情况一：
		//最后打爆L位置上的气球
		int situation1 = coreLogic(arr, L + 1, R) + arr[L - 1] * arr[L] * arr[R + 1];
		//情况二：
		//最后打爆R位置上的气球
		int situation2 = coreLogic(arr, L, R - 1) + arr[L - 1] * arr[R] * arr[R + 1];
		int max = Math.max(situation1, situation2);
		//枚举：L + 1位置到R - 1位置的所有位置
		for (int i = L + 1; i < R; i++) {
			//打爆i位置左边所有的气球
			int left = coreLogic(arr, L, i - 1);
			//打爆i位置右边所有的气球
			int right = coreLogic(arr, i + 1, R);
			//最后打爆i位置的气球
			int last = arr[L - 1] * arr[i] * arr[R + 1];
			int total = left + right + last;
			max = Math.max(max, total);
		}
		return max;
	}

	/**
	 * 使用暴力递归修改之后的动态规划的方式
	 *
	 * @param arr
	 * @return
	 */
	public static int maxCoinsWithDynamicProgramming(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		if (arr.length == 1) {
			//相当于1 * arr[0] * 1
			return arr[0];
		}

		//假设arr =    {3, 2, 4, 5, 3}
		//那么newArr = {1, 3, 4, 5, 3, 1}
		int[] newArr = new int[arr.length + 2];
		newArr[0] = 1;
		for (int i = 1; i <= newArr.length - 2; i++) {
			newArr[i] = arr[i - 1];
		}
		newArr[newArr.length - 1] = 1;

		//dp[][]，L最大范围newArr.length，R的最大范围newArr.length
		int[][] dp = new int[newArr.length][newArr.length];

		//L的范围是从newArr[]的1位置开始，到newArr[].length - 2结束
		//L == R
		for (int L = 1; L <= newArr.length - 2; L++) {
			//对角线L == R
			//if (L == R) {
			//	return arr[L - 1] * arr[R] * arr[R + 1];
			//}
			dp[L][L] = newArr[L - 1] * newArr[L] * newArr[L + 1];
		}
		for (int L = newArr.length - 2; L >= 1; L--) {
			for (int R = L + 1; R <= newArr.length - 2; R++) {
				//情况一：
				//最后打爆L位置上的气球
				//int situation1 = coreLogic(arr, L + 1, R) + arr[L - 1] * arr[L] * arr[R + 1];
				int situation1 = dp[L + 1][R] + newArr[L - 1] * newArr[L] * newArr[R + 1];
				//情况二：
				//最后打爆R位置上的气球
				//int situation2 = coreLogic(arr, L, R - 1) + arr[L - 1] * arr[R] * arr[R + 1];
				int situation2 = dp[L][R - 1] + newArr[L - 1] * newArr[R] * newArr[R + 1];
				int max = Math.max(situation1, situation2);
				//枚举：L + 1位置到R - 1位置的所有位置
				for (int i = L + 1; i < R; i++) {
					//打爆i位置左边所有的气球
					//int left = coreLogic(arr, L, i - 1);
					int left = dp[L][i - 1];
					//打爆i位置右边所有的气球
					//int right = coreLogic(arr, i + 1, R);
					int right = dp[i + 1][R];
					//最后打爆i位置的气球
					//int last = arr[L - 1] * arr[i] * arr[R + 1];
					int last = newArr[L - 1] * newArr[i] * newArr[R + 1];
					//当前最后打爆i位置的总的得分
					int total = left + right + last;
					max = Math.max(max, total);
				}
				dp[L][R] = max;
			}
		}
		//return coreLogic(newArr, 1, newArr.length - 2);
		return dp[1][newArr.length - 2];
	}
}
