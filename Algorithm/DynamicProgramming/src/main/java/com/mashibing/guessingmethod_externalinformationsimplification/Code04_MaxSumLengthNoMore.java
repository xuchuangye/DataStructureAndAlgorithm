package com.mashibing.guessingmethod_externalinformationsimplification;

import com.mashibing.common.DynamicProgrammingUtils;

import java.util.LinkedList;

/**
 * 题目四：
 * 给定一个数组arr，和一个正数M，
 * 返回在arr的子数组在长度不超过M的情况下，最大的累加和
 * <p>
 * 思路分析：
 * 1.使用滑动窗口预处理结构，求出窗口内的最大值
 * 举例：
 * 原始数组：
 * arr[] = {3,  2, -1,  5, -6,  4}
 * index =  0   1   2   3   4   5
 * 累加和数组：
 * sum[] = {3,  5,  4,  9,  3,  7}
 * index =  0,  1,  2,  3,  4,  5
 * 假设M = 3
 * LR都在sum[]的0位置，L不动，R移动到索引2，此时满足M个数，求此时L==0，R==2的累加和的最大值，是5
 * L往右移一位，R往右移一位，求此时L==1，R==3的累加和的最大值，是9，需要减去L的左边一位的值3，所以结果是6
 *
 * @author xcy
 * @date 2022/6/17 - 17:56
 */
public class Code04_MaxSumLengthNoMore {
	public static void main(String[] args) {
		int maxN = 50;
		int maxValue = 100;
		int testTime = 1000000;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int N = (int) (Math.random() * maxN);
			int M = (int) (Math.random() * maxN);
			int[] arr = DynamicProgrammingUtils.randomArray(N, maxValue);
			int ans1 = maxSum_violentSolution(arr, M);
			int ans2 = maxSum_OptimalSolution(arr, M);
			if (ans1 != ans2) {
				System.out.println(ans1);
				System.out.println(ans2);
				System.err.println("测试错误!");
				break;
			}
		}
		System.out.println("测试结束");
	}

	/**
	 * 暴力解
	 * 时间复杂度：O(N²)
	 *
	 * @param arr
	 * @param M
	 * @return
	 */
	public static int maxSum_violentSolution(int[] arr, int M) {
		if (arr == null || arr.length == 0 || M < 1) {
			return 0;
		}
		int max = Integer.MIN_VALUE;
		for (int L = 0; L < arr.length; L++) {
			int sum = 0;
			for (int R = L; R < arr.length; R++) {
				if (R - L + 1 > M) {
					break;
				}
				sum += arr[R];
				max = Math.max(max, sum);
			}
		}
		return max;
	}


	/**
	 * 最优解
	 * 时间复杂度：O(N)
	 *
	 * @param arr
	 * @param M
	 * @return
	 */
	public static int maxSum_OptimalSolution(int[] arr, int M) {
		if (arr == null || arr.length == 0 || M < 1) {
			return 0;
		}
		int N = arr.length;
		//前缀和数组
		int[] sum = new int[N];
		sum[0] = arr[0];
		for (int i = 1; i < N; i++) {
			sum[i] = sum[i - 1] + arr[i];
		}

		LinkedList<Integer> qmax = new LinkedList<>();
		int i = 0;
		int end = Math.min(N, M);
		for (; i < end; i++) {
			while (!qmax.isEmpty() && sum[qmax.peekLast()] <= sum[i]) {
				qmax.pollLast();
			}
			qmax.add(i);
		}
		int max = sum[qmax.peekFirst()];
		int L = 0;
		for (; i < N; L++, i++) {
			if (qmax.peekFirst() == L) {
				qmax.pollFirst();
			}
			while (!qmax.isEmpty() && sum[qmax.peekLast()] <= sum[i]) {
				qmax.pollLast();
			}
			qmax.add(i);
			max = Math.max(max, sum[qmax.peekFirst()] - sum[L]);
		}
		for (; L < N - 1; L++) {
			if (qmax.peekFirst() == L) {
				qmax.pollFirst();
			}
			max = Math.max(max, sum[qmax.peekFirst()] - sum[L]);
		}
		return max;
	}
}
