package com.mashibing.dynamic;

import com.mashibing.common.DynamicUtils;

/**
 * 给定一个正数数组arr，请将arr数组中的所有元素分成两个集合
 * 如果arr的长度是偶数，那么两个集合的元素个数必须一样多
 * 如果arr的长度是奇数，那么两个集合的元素个数必须只相差一个
 * 在两个集合的累加和最接近的情况下，返回较小集合的累加和
 *
 * @author xcy
 * @date 2022/5/13 - 9:39
 */
public class DivisionArrTwoListLessListSum_II {
	public static void main(String[] args) {
		int maxLen = 10;
		int maxValue = 50;
		int testTime = 10000;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int len = (int) (Math.random() * maxLen);
			int[] arr = DynamicUtils.randomArray(len, maxValue);
			int ans1 = returnDivisionArrTwoListLessSum(arr);
			int ans2 = returnDivisionArrTwoListLessSumWithTable(arr);
			int ans3 = dp(arr);
			int ans4 = dp2(arr);

			if (ans1 != ans2 || ans1 != ans3 || ans1 != ans4) {
				DynamicUtils.printArray(arr);
				System.out.println(ans1);
				System.out.println(ans2);
				System.out.println(ans3);
				System.out.println(ans4);
				System.out.println("Oops!");
				break;
			}
		}
		System.out.println("测试结束");
	}

	/**
	 * 使用暴力递归的方式
	 *
	 * @param arr 原始数组
	 * @return 在arr数组分成的两个集合的累加和最接近的情况下，返回较小集合的累加和
	 */
	public static int returnDivisionArrTwoListLessSum(int[] arr) {
		//数组为空或者数组的长度小于2，无法分成两个集合，直接返回0
		if (arr == null || arr.length < 2) {
			return 0;
		}
		int sum = 0;
		for (int num : arr) {
			sum += num;
		}
		//如果数组元素个数为偶数
		if (arr.length % 2 == 0) {
			return coreLogic(arr, 0, arr.length / 2, sum / 2);
		}
		//如果数组元素个数为奇数
		else {
			return Math.max(coreLogic(arr, 0, arr.length / 2, sum / 2)
					, coreLogic(arr, 0, arr.length / 2 + 1, sum / 2));
		}
	}

	/**
	 * 核心逻辑
	 *
	 * @param arr    原始数组
	 * @param index  数组对应的索引
	 * @param select 可以选择的数组元素的个数
	 * @param rest   最接近的值
	 * @return 在arr数组分成的两个集合的累加和最接近的情况下，返回较小集合的累加和
	 */
	public static int coreLogic(int[] arr, int index, int select, int rest) {
		//已经没有元素了
		if (index == arr.length) {
			//判断是否依然有可以选择的元素个数
			//如果已经超出数组的索引，没有可以选择的元素个数，返回0
			//如果已经超出数组的索引，还有可以选择的元素个数，无效，返回-1
			return select == 0 ? 0 : -1;
		} else {
			//情况1：没有选择当前arr[index]的元素
			int situation1 = coreLogic(arr, index + 1, select, rest);
			//情况2：选择当前arr[index]的元素
			int situation2 = -1;
			int next = -1;
			if (rest - arr[index] >= 0) {
				//下一个递归的返回值
				next = coreLogic(arr, index + 1, select - 1, rest - arr[index]);
			}
			//说明下一个递归的返回值有效
			if (next != -1) {
				situation2 = arr[index] + next;
			}
			//返回在两个集合的累加和最接近的情况下，较小集合的累加和(最接近rest的累加和)
			return Math.max(situation1, situation2);
		}
	}

	/**
	 * 使用动态规划的方式
	 *
	 * @param arr 原始数组
	 * @return 在arr数组分成的两个集合的累加和最接近的情况下，返回较小集合的累加和
	 */
	public static int returnDivisionArrTwoListLessSumWithTable(int[] arr) {
		//数组为空或者数组的长度小于2，无法分成两个集合，直接返回0
		if (arr == null || arr.length < 2) {
			return 0;
		}
		int sum = 0;
		for (int num : arr) {
			sum += num;
		}
		//index范围：0 ~ arr.length
		//select范围：0 ~ arr.length / 2 + 1，依据：返回值中的table[0][arr.length / 2 + 1][sum / 2]
		//rest范围：0 ~ sum / 2
		int[][][] table = new int[arr.length + 1][arr.length / 2 + 1 + 1][sum / 2 + 1];

		//index不需要循环，因为在index == arr.length的情况下，所有select == 0的位置都是0，select != 0的位置都是-1
		//select从1开始，因为select == 0时默认已经初始化
		for (int select = 1; select <= arr.length / 2 + 1; select++) {
			for (int rest = 0; rest <= sum / 2; rest++) {
				//if (index == arr.length) {
				//判断是否依然有可以选择的元素个数
				//如果已经超出数组的索引，没有可以选择的元素个数，返回0
				//如果已经超出数组的索引，还有可以选择的元素个数，无效，返回-1
				//return select == 0 ? 0 : -1;

				//table[index == arr.length][select == 0][rest] = 0;
				table[arr.length][select][rest] = -1;
			}
		}

		//上述for循环中，所有table[index == arr.length][select == 0 || select != 0][rest]位置的值都已经填写过了
		//并且因为index位置的值依赖index + 1位置的值，所以从下往上填写
		//所以index从arr.length - 1开始
		for (int index = arr.length - 1; index >= 0; index--) {
			//select从1开始，因为select == 0时默认已经初始化
			for (int select = 1; select <= arr.length / 2 + 1; select++) {
				for (int rest = 0; rest <= sum / 2; rest++) {
					//情况1：没有选择当前arr[index]的元素
					int situation1 = table[index + 1][select][rest];
					//情况2：选择当前arr[index]的元素
					int situation2 = -1;
					int next = -1;
					if (rest - arr[index] >= 0) {
						//下一个递归的返回值
						next = table[index + 1][select - 1][rest - arr[index]];
					}
					//说明下一个递归的返回值有效
					if (next != -1) {
						situation2 = arr[index] + next;
					}
					//返回在两个集合的累加和最接近的情况下，较小集合的累加和(最接近rest的累加和)
					table[index][select][rest] = Math.max(situation1, situation2);
				}
			}
		}
		//if (arr.length % 2 == 0) {
		if (arr.length % 2 == 0) {
			//	return coreLogic(arr, 0, arr.length / 2, sum / 2);
			return table[0][arr.length / 2][sum / 2];
			//} else {
		} else {
			//	return Math.max(coreLogic(arr, 0, arr.length / 2, sum / 2)
			//			, coreLogic(arr, 0, arr.length / 2 + 1, sum / 2));
			return Math.max(table[0][arr.length / 2][sum / 2], table[0][arr.length / 2 + 1][sum / 2]);
		//}
		}
	}

	//for test
	public static int dp(int[] arr) {
		if (arr == null || arr.length < 2) {
			return 0;
		}
		int sum = 0;
		for (int num : arr) {
			sum += num;
		}
		sum /= 2;
		int N = arr.length;
		int M = (N + 1) / 2;
		int[][][] dp = new int[N + 1][M + 1][sum + 1];
		for (int i = 0; i <= N; i++) {
			for (int j = 0; j <= M; j++) {
				for (int k = 0; k <= sum; k++) {
					dp[i][j][k] = -1;
				}
			}
		}
		for (int rest = 0; rest <= sum; rest++) {
			dp[N][0][rest] = 0;
		}
		for (int i = N - 1; i >= 0; i--) {
			for (int picks = 0; picks <= M; picks++) {
				for (int rest = 0; rest <= sum; rest++) {
					int p1 = dp[i + 1][picks][rest];
					// 就是要使用arr[i]这个数
					int p2 = -1;
					int next = -1;
					if (picks - 1 >= 0 && arr[i] <= rest) {
						next = dp[i + 1][picks - 1][rest - arr[i]];
					}
					if (next != -1) {
						p2 = arr[i] + next;
					}
					dp[i][picks][rest] = Math.max(p1, p2);
				}
			}
		}
		if ((arr.length & 1) == 0) {
			return dp[0][arr.length / 2][sum];
		} else {
			return Math.max(dp[0][arr.length / 2][sum], dp[0][(arr.length / 2) + 1][sum]);
		}
	}

	//for test
	public static int dp2(int[] arr) {
		if (arr == null || arr.length < 2) {
			return 0;
		}
		int sum = 0;
		for (int num : arr) {
			sum += num;
		}
		sum >>= 1;
		int N = arr.length;
		int M = (arr.length + 1) >> 1;
		int[][][] dp = new int[N][M + 1][sum + 1];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j <= M; j++) {
				for (int k = 0; k <= sum; k++) {
					dp[i][j][k] = Integer.MIN_VALUE;
				}
			}
		}
		for (int i = 0; i < N; i++) {
			for (int k = 0; k <= sum; k++) {
				dp[i][0][k] = 0;
			}
		}
		for (int k = 0; k <= sum; k++) {
			dp[0][1][k] = arr[0] <= k ? arr[0] : Integer.MIN_VALUE;
		}
		for (int i = 1; i < N; i++) {
			for (int j = 1; j <= Math.min(i + 1, M); j++) {
				for (int k = 0; k <= sum; k++) {
					dp[i][j][k] = dp[i - 1][j][k];
					if (k - arr[i] >= 0) {
						dp[i][j][k] = Math.max(dp[i][j][k], dp[i - 1][j - 1][k - arr[i]] + arr[i]);
					}
				}
			}
		}
		return Math.max(dp[N - 1][M][sum], dp[N - 1][N - M][sum]);
	}
}
