package com.mashibing.day04;

import com.mashibing.common.TestUtils;

/**
 * 题目三：
 * 返回一个二维数组中子矩阵最大累加和
 *
 * @author xcy
 * @date 2022/7/15 - 11:38
 */
public class Code03_SubMatrixMaxSum {
	public static void main(String[] args) {
		int n = 10;
		int valueMax = 100;
		int testTimes = 5;
		System.out.println("测试开始！");
		for (int i = 0; i < testTimes; i++) {
			int[][] matrix = TestUtils.randomMatrix(n, valueMax);
			int sum1 = maxSum1(matrix);
			int sum2 = maxSum2(matrix);
			if (sum1 != sum2) {
				System.out.println("测试出错！");
				System.out.println(sum1);
				System.out.println(sum2);
				break;
			}
		}
		System.out.println("测试结束！");
	}

	/**
	 * 时间复杂度：O(N ^ 2 * M)
	 *
	 * @param matrix 二维数组
	 * @return 返回子矩阵的最大累加和
	 */
	public static int maxSum1(int[][] matrix) {
		if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
			return 0;
		}

		int N = matrix.length;
		int M = matrix[0].length;

		int max = Integer.MIN_VALUE;
		int cur = 0;

		//从i行到j行
		for (int i = 0; i < N; i++) {
			//辅助数组，计算每一行中子数组的最大累加和
			int[] sum = new int[M];
			for (int j = i; j < N; j++) {
				//下一行重新计算
				cur = 0;
				//遍历每一列
				for (int k = 0; k < M; k++) {
					//辅助数组用于求出每一行子数组的最大累加和
					sum[k] += matrix[j][k];
					//cur用于求出每一行的最大累加和
					cur += sum[k];
					//求出总的最大累加和
					max = Math.max(max, cur);
					cur = Math.max(cur, 0);
				}
			}
		}
		return max;
	}

	public static int maxSum2(int[][] m) {
		if (m == null || m.length == 0 || m[0].length == 0) {
			return 0;
		}
		// O(N^2 * M)
		int N = m.length;
		int M = m[0].length;

		int max = Integer.MIN_VALUE;

		for (int i = 0; i < N; i++) {
			// i~j
			int[] s = new int[M];
			for (int j = i; j < N; j++) {
				for (int k = 0; k < M; k++) {
					s[k] += m[j][k];
				}
				max = Math.max(max, maxSubArray(s));
			}
		}
		return max;
	}

	/**
	 * @param arr
	 * @return 返回子数组的最大累加和
	 */
	public static int maxSubArray(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		int max = Integer.MIN_VALUE;
		int cur = 0;
		for (int value : arr) {
			cur += value;
			max = Math.max(max, cur);
			cur = Math.max(cur, 0);
		}
		return max;
	}

	/**
	 * LeetCode测试链接：
	 * https://leetcode-cn.com/problems/max-submatrix-lcci/
	 *
	 * @param matrix
	 * @return
	 */
	public static int[] getMatrix(int[][] matrix) {
		if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
			return null;
		}
		int N = matrix.length;
		int M = matrix[0].length;

		int max = Integer.MIN_VALUE;
		int cur = 0;

		int a = 0;
		int b = 0;
		int c = 0;
		int d = 0;

		for (int i = 0; i < N; i++) {
			int[] sum = new int[M];
			for (int j = i; j < N; j++) {
				cur = 0;
				int begin = 0;
				for (int k = 0; k < M; k++) {
					sum[k] += matrix[j][k];
					cur += sum[k];
					if (max < cur) {
						max = cur;
						a = i;
						b = begin;
						c = j;
						d = k;
					}
					if (cur < 0) {
						cur = 0;
						begin = k + 1;
					}
				}
			}
		}
		return new int[]{a, b, c, d};
	}


}
