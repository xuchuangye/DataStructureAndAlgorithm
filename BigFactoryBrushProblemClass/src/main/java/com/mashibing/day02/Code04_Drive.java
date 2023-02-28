package com.mashibing.day02;

import com.mashibing.common.TestUtils;

import java.util.Arrays;

/**
 * 题目四：
 * 现有司机N*2人，调度中心会将所有司机平分给A、B两区域
 * i号司机去A可得收入为income[i][0]，
 * 去B可得收入为income[i][1]
 * 返回能使所有司机总收入最高的方案是多少钱?
 *
 * @author xcy
 * @date 2022/7/11 - 9:10
 */
public class Code04_Drive {
	public static void main(String[] args) {
		int N = 10;
		int value = 100;
		int testTime = 500;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int len = (int) (Math.random() * N) + 1;
			int[][] matrix = TestUtils.randomMatrix(len, value);
			int ans1 = driverMaxIncomeToRecursion(matrix);
			int ans2 = driverMaxIncomeToCache(matrix);
			int ans3 = driverMaxIncomeToDynamicProgramming(matrix);
			int ans4 = driverMaxIncomeToGreedyAlgorithm(matrix);
			if (ans1 != ans2 || ans1 != ans3 || ans1 != ans4) {
				System.out.println(ans1);
				System.out.println(ans2);
				System.out.println(ans3);
				System.out.println(ans4);
				System.out.println("Oops!");
			}
		}
		System.out.println("测试结束");
	}

	/**
	 * 使用暴力递归的方式
	 * <p>
	 * income -> N行 * 2列的矩阵，N是偶数
	 * 0 [9, 13]
	 * 1 [45,60]
	 *
	 * @param income
	 * @return
	 */
	public static int driverMaxIncomeToRecursion(int[][] income) {
		if (income == null || income.length < 1 || (income.length & 1) != 0) {
			return 0;
		}

		int N = income.length;
		int M = N >> 1;
		return processToRecursion(income, 0, M);
	}

	/**
	 * 将index之后的所有司机平均分配到A和B区域
	 * 为什么不再使用restB这个变量，因为一旦确定restA，那么restB的值也就确定了，所以不需要使用restB这个变量，没有必要
	 *
	 * @param income 司机收入的数组
	 * @param index  当前司机的索引
	 * @param restA   当前A区域剩余可服务的司机名额个数
	 * @return 返回将index之后的所有司机平均分配到A和B区域，index之后所有的司机的整体的收入是多少
	 */
	public static int processToRecursion(int[][] income, int index, int restA/*, int restB*/) {
		//没有剩余的司机了
		if (index == income.length) {
			//没有司机，肯定也就没有收入
			return 0;
		}

		//有剩余的司机，但是B区域已经满了，进入A区域
		if (income.length - index == restA) {
			return income[index][0] + processToRecursion(income, index + 1, restA - 1);
		}
		//有剩余的司机，但是A区域已经满了，进入B区域
		if (restA == 0) {
			return income[index][1] + processToRecursion(income, index + 1, restA);
		}

		//有剩余的司机，A和B区域都没有满，都可以进入
		//情况1，司机去A区域
		int situation1 = income[index][0] + processToRecursion(income, index + 1, restA - 1);
		//情况2：司机去B区域
		int situation2 = income[index][1] + processToRecursion(income, index + 1, restA);
		//取最大的司机收入
		return Math.max(situation1, situation2);
	}

	/**
	 * 使用傻缓存的方式
	 * <p>
	 * income -> N行 * 2列的矩阵，N是偶数
	 * 0 [9, 13]
	 * 1 [45,60]
	 *
	 * @param income
	 * @return
	 */
	public static int driverMaxIncomeToCache(int[][] income) {
		if (income == null || income.length < 1 || (income.length & 1) != 0) {
			return 0;
		}

		int N = income.length;
		int M = N >> 1;
		int[][] dp = new int[N][M + 1];
		return processToCache(income, 0, M, dp);
	}

	/**
	 * 将index之后的所有司机平均分配到A和B区域
	 *
	 * @param income 司机收入的数组
	 * @param index  当前司机的索引
	 * @param restA   当前A区域剩余可服务的司机名额个数
	 * @return 返回将index之后的所有司机平均分配到A和B区域，index之后所有的司机的整体的收入是多少
	 */
	public static int processToCache(int[][] income, int index, int restA, int[][] dp) {
		//没有剩余的司机了
		if (index == income.length) {
			return 0;
		}
		//有缓存就使用缓存
		if (dp[index][restA] != 0) {
			return dp[index][restA];
		}
		int ans = 0;
		//有剩余的司机，但是B区域已经满了，进入A区域
		if (income.length - index == restA) {
			ans = income[index][0] + processToCache(income, index + 1, restA - 1, dp);
		}
		//有剩余的司机，但是A区域已经满了，进入B区域
		else if (restA == 0) {
			ans = income[index][1] + processToCache(income, index + 1, restA, dp);
		}
		//有剩余的司机，A和B区域都没有满，都可以进入
		else {
			//情况1，司机去A区域
			int situation1 = income[index][0] + processToCache(income, index + 1, restA - 1, dp);
			//情况2：司机去B区域
			int situation2 = income[index][1] + processToCache(income, index + 1, restA, dp);
			//取最大的司机收入
			ans = Math.max(situation1, situation2);
		}

		dp[index][restA] = ans;
		return ans;
	}

	/**
	 * 使用动态规划的方式
	 * <p>
	 * income -> N行 * 2列的矩阵，N是偶数
	 * 0 [9, 13]
	 * 1 [45,60]
	 *
	 * @param income
	 * @return
	 */
	public static int driverMaxIncomeToDynamicProgramming(int[][] income) {
		if (income == null || income.length < 1 || (income.length & 1) != 0) {
			return 0;
		}

		int N = income.length;
		int M = N >> 1;
		int[][] dp = new int[N + 1][M + 1];

		for (int index = N - 1; index >= 0; index--) {
			for (int restA = 0; restA <= M; restA++) {
				//有剩余的司机，但是B区域已经满了，进入A区域
				/*if (income.length - index == rest) {
					return income[index][0] + processToCache(income, index + 1, rest - 1, dp);
				}*/
				if (N - index == restA) {
					dp[index][restA] = income[index][0] + dp[index + 1][restA - 1];
				}
				//有剩余的司机，但是A区域已经满了，进入B区域
				/*else if (rest == 0) {
					return income[index][1] + processToCache(income, index + 1, rest, dp);
				}*/
				else if (restA == 0) {
					dp[index][restA] = income[index][1] + dp[index + 1][restA];
				}
				//有剩余的司机，A和B区域都没有满，都可以进入
				/*else {
					//情况1，司机去A区域
					int situation1 = income[index][0] + processToCache(income, index + 1, rest - 1, dp);
					//情况2：司机去B区域
					int situation2 = income[index][1] + processToCache(income, index + 1, rest, dp);
					//取最大的司机收入
					return Math.max(situation1, situation2);
				}*/
				else {
					//情况1，司机去A区域
					int situation1 = income[index][0] + dp[index + 1][restA - 1];
					//情况2：司机去B区域
					int situation2 = income[index][1] + dp[index + 1][restA];
					//取最大的司机收入
					dp[index][restA] = Math.max(situation1, situation2);
				}
			}
		}
		//return processToCache(income, 0, M, dp);
		return dp[0][M];
	}

	/**
	 * 这题有贪心策略 :
	 * 假设一共有10个司机，思路是先让所有司机去A，得到一个总收益
	 * 然后看看哪5个司机改换门庭(去B)，可以获得最大的额外收益
	 * 这道题有贪心策略，打了我的脸
	 * 但是我课上提到的技巧请大家重视
	 * 根据数据量猜解法可以省去大量的多余分析，节省时间
	 * 这里感谢卢圣文同学
	 * @param income
	 * @return
	 */
	public static int driverMaxIncomeToGreedyAlgorithm(int[][] income) {
		int N = income.length;
		int[] arr = new int[N];
		int sum = 0;
		for (int index = 0; index < N; index++) {
			arr[index] = income[index][1] - income[index][0];
			sum += income[index][0];
		}
		Arrays.sort(arr);
		int M = N >> 1;
		for (int index = N - 1; index >= M; index--) {
			sum += arr[index];
		}
		return sum;
	}
}
