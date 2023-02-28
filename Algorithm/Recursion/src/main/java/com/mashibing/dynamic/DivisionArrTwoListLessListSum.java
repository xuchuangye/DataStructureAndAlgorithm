package com.mashibing.dynamic;

import com.mashibing.common.DynamicUtils;

/**
 * 分裂数组中所有的元素为两个集合，返回最接近的情况下，较小集合的累加和
 * <p>
 * 给定一个数组arr，请把arr数组中的所有元素分成两个集合，尽量让两个集合的累加和接近
 * 返回最接近的情况下，较小集合的累加和
 *
 * @author xcy
 * @date 2022/5/13 - 8:46
 */
public class DivisionArrTwoListLessListSum {
	public static void main(String[] args) {
		int maxLen = 20;
		int maxValue = 50;
		int testTime = 10000;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int len = (int) (Math.random() * maxLen);
			int[] arr = DynamicUtils.randomArray(len, maxValue);
			int ans1 = returnDivisionArrTwoListLessListSum(arr);
			int ans2 = returnDivisionArrTwoListLessListSumWithTable(arr);
			int ans3 = returnDivisionArrTwoListLessListSumWithTableOptimization(arr);
			if (ans1 != ans2 || ans1 != ans3) {
				DynamicUtils.printArray(arr);
				System.out.println(ans1);
				System.out.println(ans2);
				System.out.println(ans3);
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
	 * @return 将数组分成两个集合，返回两个集合的累加和最接近的情况下，较小集合的累加和
	 */
	public static int returnDivisionArrTwoListLessListSum(int[] arr) {
		//数组为空或者元素个数小于2个，无法分成两个集合，直接返回0
		if (arr == null || arr.length < 2) {
			return 0;
		}
		int sum = 0;
		for (int num : arr) {
			sum += num;
		}
		return coreLogic(arr, 0, sum / 2);
	}

	/**
	 * 核心逻辑
	 *
	 * @param arr   原始数组
	 * @param index 数组对应的索引
	 * @param rest  假设较小集合累加和最接近的值
	 * @return 返回arr数组分成两个集合，在两个集合累加和最接近的情况下，较小集合的累加和
	 */
	public static int coreLogic(int[] arr, int index, int rest) {
		//arr数组中已经没有元素，
		if (index == arr.length) {
			return 0;
		} else {
			//情况1：不选择当前arr[index]的值
			int situation1 = coreLogic(arr, index + 1, rest);
			//情况2：选择当前arr[index]的值
			int situation2 = 0;
			if (arr[index] <= rest) {
				situation2 = arr[index] + coreLogic(arr, index + 1, rest - arr[index]);
			}
			//返回两个集合累加和最接近的情况下，最接近rest的集合的累加和
			return Math.max(situation1, situation2);
		}
	}

	/**
	 * 使用动态规划的方式
	 *
	 * @param arr 原始数组
	 * @return 将数组分成两个集合，返回两个集合的累加和最接近的情况下，较小集合的累加和
	 */
	public static int returnDivisionArrTwoListLessListSumWithTable(int[] arr) {
		//数组为空或者元素个数小于2个，无法分成两个集合，直接返回0
		if (arr == null || arr.length < 2) {
			return 0;
		}
		int sum = 0;
		for (int num : arr) {
			sum += num;
		}

		int[][] table = new int[arr.length + 1][(sum / 2) + 1];

		for (int index = arr.length - 1; index >= 0; index--) {
			for (int rest = 0; rest <= sum / 2; rest++) {
				//情况1：不选择当前arr[index]的值
				int situation1 = table[index + 1][rest];
				//情况2：选择当前arr[index]的值
				int situation2 = 0;
				if (rest - arr[index] >= 0) {
					situation2 = arr[index] + table[index + 1][rest - arr[index]];
				}
				//返回两个集合累加和最接近的情况下，最接近rest的集合的累加和
				table[index][rest] = Math.max(situation1, situation2);
			}
		}

		//return coreLogic(arr, 0, sum / 2);
		return table[0][sum / 2];
	}

	/**
	 * 使用动态规划的方式
	 *
	 * @param arr 原始数组
	 * @return 将数组分成两个集合，返回两个集合的累加和最接近的情况下，较小集合的累加和
	 */
	public static int returnDivisionArrTwoListLessListSumWithTableOptimization(int[] arr) {
		//数组为空或者元素个数小于2个，无法分成两个集合，直接返回0
		if (arr == null || arr.length < 2) {
			return 0;
		}
		int sum = 0;
		for (int num : arr) {
			sum += num;
		}

		int[][] table = new int[arr.length + 1][(sum / 2) + 1];

		for (int index = arr.length - 1; index >= 0; index--) {
			for (int rest = 0; rest <= sum / 2; rest++) {
				//情况1：不选择当前arr[index]的值
				int situation1 = table[index + 1][rest];
				//情况2：选择当前arr[index]的值
				int situation2 = 0;
				//越界问题
				if (rest - arr[index] >= 0) {
					situation2 = arr[index] + table[index + 1][rest - arr[index]];
				}
				//返回两个集合累加和最接近的情况下，最接近rest的集合的累加和
				table[index][rest] = Math.max(situation1, situation2);
			}
		}

		//return coreLogic(arr, 0, sum / 2);
		return table[0][sum / 2];
	}
}
