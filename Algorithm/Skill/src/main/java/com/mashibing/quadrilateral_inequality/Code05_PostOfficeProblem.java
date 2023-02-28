package com.mashibing.quadrilateral_inequality;

import com.mashibing.common.SkillUtils;

/**
 * 题目五：
 * 一条直线上有居民点，邮局只能建在居民点上。
 * 给定一个有序正数数组arr，每个值表示居民点的一维坐标，再给定一个正数num，表示邮局数量。
 * 选择num个居民点建立num个邮局，使所有的居民点到最近邮局的总距离最短，返回最短的总距离
 * 【举例】
 * arr=[1,2,3,4,5,1000]，num=2。
 * 第一个邮局建立在3位置，第二个邮局建立在1000位置。那么1位置到邮局的距离为2，2位置到邮局距离为1，3位置到邮局的距离为0，
 * 4位置到邮局的距离为1，5位置到邮局的距离为2，1000位置到邮局的距离为0。这种方案下的总距离为6，
 * 其他任何方案的总距离都不会比该方案的总距离更短，所以返回6
 * <p>
 * 思路分析：
 * 1.当有偶数个居民点，邮局自动搬迁到下一个位置
 * 2.当有奇数个居民点，邮局不能搬迁就不搬迁，反正最优解的位置还是不变
 * 举例：
 * arr = {3, 9, 20, 27, 30}
 * 当arr中只有一个居民点，邮局就建立在该居民点，最短距离0
 * 当arr中有两个居民点，偶数个居民点，邮局自动搬迁到下一个位置1，最短距离6
 * 当arr中有三个居民点，奇数个居民点，邮局不搬迁，最短距离 = 11(2位置的坐标 - 1位置的坐标) + 前一个的最短距离6 = 17
 * 当arr中有四个居民点，偶数个居民点，邮局自动搬迁到下一个位置2，最短距离 = 18(3位置的坐标 - 1位置的坐标) + 前一个的最短距离17 = 35
 * 当arr中有五个居民点，奇数个居民点，邮局不搬迁，最短距离 = 10(4位置的坐标 - 2位置的坐标) + 前一个的最短距离35 = 45
 *
 * @author xcy
 * @date 2022/6/13 - 9:11
 */
public class Code05_PostOfficeProblem {
	public static void main(String[] args) {
		int N = 30;
		int maxValue = 100;
		int testTime = 10000;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int len = (int) (Math.random() * N) + 1;
			int[] arr = SkillUtils.randomSortedArray(len, maxValue);
			int num = (int) (Math.random() * N) + 1;
			int ans1 = returnShortestTotalDistance_EnumerationOptimization(arr, num);
			int ans2 = returnShortestTotalDistance_NoEnumerationOptimization(arr, num);
			if (ans1 != ans2) {
				SkillUtils.printArray(arr);
				System.out.println(num);
				System.out.println(ans1);
				System.out.println(ans2);
				System.out.println("Oops!");
			}
		}
		System.out.println("测试结束");
	}

	/**
	 * 不使用枚举优化的版本
	 *
	 * @param arr
	 * @param num
	 * @return
	 */
	public static int returnShortestTotalDistance_NoEnumerationOptimization(int[] arr, int num) {
		//没有居民点，值无效
		//或者
		//邮局数量小于1，值无效
		//或者
		//居民点数量小于邮局数量，邮局过剩，相当于居民点都是邮局，最短距离都是0
		//返回0
		if (arr == null || num < 1 || arr.length < num) {
			return 0;
		}
		//arr.length + 1表示即使是出现无效的位置，w[][]也能够装得下，不需要再进行越界的判断
		//w[][] 表示L - R范围上只建立一个邮局，所有居民点到邮局的最短距离是多少
		int[][] w = new int[arr.length + 1][arr.length + 1];
		for (int L = 0; L < arr.length; L++) {
			//L == R表示只有一个居民点，即使设置了邮局，最短距离也是0，所以R从L + 1开始
			for (int R = L + 1; R < arr.length; R++) {
				//(L + (R - L) >> 1)表示所有居民点到邮局的最短距离的最优位置
				//举例：
				//案例1.
				//arr = {3, 6}
				//   0   1   ->   R
				//0  0   ☆
				//1
				//L
				//w[L][R] = 0 + arr[1] - arr[0] = 6 - 3 = 3
				//案例2.
				//arr = {3, 6, 13}
				//   0   1   2   ->   R
				//0  0   3   ☆
				//1
				//2
				//L
				//w[L][R] = 3 + arr[2] - arr[1] = 3 + 13 - 6 = 10
				w[L][R] = w[L][R - 1] + arr[R] - arr[L + ((R - L) >> 1)];
			}
		}
		int[][] dp = new int[arr.length][num + 1];
		for (int i = 0; i < arr.length; i++) {
			//dp[][]第0列跳过
			//dp[][]第i行的第1列 = w[][]0 ~ i范围上所有居民点到邮局的最短距离
			dp[i][1] = w[0][i];
		}

		//i既表示行数也表示居民点数量，第0行已经填过了，所以从第1行开始
		for (int i = 1; i < arr.length; i++) {
			//j既表示列数也表示邮局数量，邮局数量如果大于居民点，那么邮局过剩，每个居民点都是邮局，最短距离肯定是0
			//num表示邮局数量的限制，所以j < Math.min(i, num)，邮局数量既不能超出居民点数量也不能超出邮局数量的限制
			for (int j = 2; j <= Math.min(i, num); j++) {
				int next = Integer.MAX_VALUE;
				for (int k = 0; k <= i; k++) {
					//dp[k][j - 1]表示前面j - 1个邮局负责0 ~ K个居民点
					//w[k + 1][i]表示最后一个邮局负责k + 1 ~ I个居民点
					next = Math.min(next, dp[k][j - 1] + w[k + 1][i]);
				}
				dp[i][j] = next;
			}
		}
		return dp[arr.length - 1][num];
	}

	/**
	 * 使用枚举优化的版本
	 *
	 * @param arr
	 * @param num
	 * @return
	 */
	public static int returnShortestTotalDistance_EnumerationOptimization(int[] arr, int num) {
		//没有居民点，值无效
		//或者
		//邮局数量小于1，值无效
		//或者
		//居民点数量小于邮局数量，邮局过剩，相当于居民点都是邮局，最短距离都是0
		//返回0
		if (arr == null || num < 1 || arr.length < num) {
			return 0;
		}
		//arr.length + 1表示即使是出现无效的位置，w[][]也能够装得下，不需要再进行越界的判断
		//w[][] 表示L - R范围上只建立一个邮局，所有居民点到邮局的最短距离是多少
		int[][] w = new int[arr.length + 1][arr.length + 1];
		for (int L = 0; L < arr.length; L++) {
			//L == R表示只有一个居民点，即使设置了邮局，最短距离也是0，所以R从L + 1开始
			for (int R = L + 1; R < arr.length; R++) {
				//(L + (R - L) >> 1)表示所有居民点到邮局的最短距离的最优位置
				//举例：
				//案例1.
				//arr = {3, 6}
				//   0   1   ->   R
				//0  0   ☆
				//1
				//L
				//w[L][R] = 0 + arr[1] - arr[0] = 6 - 3 = 3
				//案例2.
				//arr = {3, 6, 13}
				//   0   1   2   ->   R
				//0  0   3   ☆
				//1
				//2
				//L
				//w[L][R] = 3 + arr[2] - arr[1] = 3 + 13 - 6 = 10
				w[L][R] = w[L][R - 1] + arr[R] - arr[L + ((R - L) >> 1)];
			}
		}
		int[][] dp = new int[arr.length][num + 1];
		int[][] best = new int[arr.length][num + 1];
		for (int i = 0; i < arr.length; i++) {
			//dp[][]第0列跳过
			//dp[][]第i行的第1列 = w[][]0 ~ i范围上所有居民点到邮局的最短距离
			dp[i][1] = w[0][i];
			best[i][1] = -1;
		}

		//j既表示列数也表示邮局数量，第0列表示不管居民点数量再多，邮局数量为0没有意义
		//第1列已经填过了，所以从第2列开始
		for (int j = 2; j <= num; j++) {
			//i既表示行数也表示居民点数量
			for (int i = arr.length - 1; i >= j; i--) {
				//num表示邮局数量的限制，所以j < Math.min(i, num)，邮局数量既不能超出居民点数量也不能超出邮局数量的限制
				int down = best[i][j - 1];
				int up = i == arr.length - 1 ? arr.length - 1 : best[i + 1][j];
				int next = Integer.MAX_VALUE;
				int choose = -1;
				for (int leftEnd = down; leftEnd <= up; leftEnd++) {
					int leftCost = leftEnd == -1 ? 0 : dp[leftEnd][j - 1];
					int rightCost = leftEnd == i ? 0 : w[leftEnd + 1][i];
					//dp[k][j - 1]表示前面j - 1个邮局负责0 ~ K个居民点
					//w[k + 1][i]表示最后一个邮局负责k + 1 ~ I个居民点
					int cur = leftCost + rightCost;
					if (cur <= next) {
						next = cur;
						choose = leftEnd;
					}
				}
				dp[i][j] = next;
				best[i][j] = choose;
			}
		}
		return dp[arr.length - 1][num];
	}
}
