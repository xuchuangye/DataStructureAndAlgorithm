package com.mashibing.day03;

import com.mashibing.common.TestUtils;

import java.util.Arrays;

/**
 * 题目四：
 * 给定一个数组arr，代表每个人的能力值。再给定一个非负数k，如果两个人能力差值正好为k，那么可以凑在一起比赛
 * 一局比赛只有两个人，返回最多可以同时有多少场比赛
 * <p>
 * 解题思路：
 * 1.使用滑动窗口
 * L和R
 * 分为几种情况：
 * 第一种情况：L和R处于同一个位置，此时这种情况R往右移动
 * 第二种情况：L和R不在同一个位置，但是L位置的值和R位置的值的差值小于K，此时这种情况R往右移动
 * 第三种情况：L和R不在同一个位置，L位置的值和R位置的值差值等于K，此时这种情况首先标记R位置，
 * 然后记录一场比赛，接着L和R同时往右移动
 * 第四种情况：L和R不在同一个位置，L位置已经被访问过了，此时这种情况L往右移动
 *
 * @author xcy
 * @date 2022/7/13 - 8:32
 */
public class Code04_MaxPairNumber {
	public static void main(String[] args) {
		int maxLen = 10;
		int maxValue = 20;
		int maxK = 5;
		int testTime = 1000;
		System.out.println("功能测试开始");
		for (int i = 0; i < testTime; i++) {
			int N = (int) (Math.random() * (maxLen + 1));
			int[] arr = TestUtils.randomArray(N, maxValue);
			int[] arr1 = TestUtils.copyArray(arr);
			int[] arr2 = TestUtils.copyArray(arr);
			int k = (int) (Math.random() * (maxK + 1));
			int ans1 = howManyGamesAtMostWithRecursion(arr1, k);
			int ans2 = howManyGamesAtMostWithGreedyAlgorithm(arr2, k);
			if (ans1 != ans2) {
				System.out.println("Oops!");
				TestUtils.printArray(arr);
				System.out.println(k);
				System.out.println(ans1);
				System.out.println(ans2);
				break;
			}
		}
		System.out.println("功能测试结束");
	}

	/**
	 * 暴力解，对数器
	 * @param arr
	 * @param k
	 * @return
	 */
	public static int howManyGamesAtMostWithRecursion(int[] arr, int k) {
		if (k < 0) {
			return -1;
		}
		return process1(arr, 0, k);
	}

	public static int process1(int[] arr, int index, int k) {
		int ans = 0;
		if (index == arr.length) {
			for (int i = 1; i < arr.length; i += 2) {
				if (arr[i] - arr[i - 1] == k) {
					ans++;
				}
			}
		} else {
			for (int r = index; r < arr.length; r++) {
				swap(arr, index, r);
				ans = Math.max(ans, process1(arr, index + 1, k));
				swap(arr, index, r);
			}
		}
		return ans;
	}

	public static void swap(int[] arr, int i, int j) {
		int tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}

	/**
	 * 使用滑动窗口 + 贪心算法
	 * 尽量先使用小值进行计算，防止错过比赛最多的场次
	 *
	 * @param arr
	 * @param K
	 * @return
	 */
	public static int howManyGamesAtMostWithGreedyAlgorithm(int[] arr, int K) {
		if (arr == null || arr.length < 2 || K < 0) {
			return 0;
		}
		//排序，根据贪心算法
		Arrays.sort(arr);

		int L = 0;
		int R = 0;
		//是否被访问过
		boolean[] visited = new boolean[arr.length];
		//记录比赛的场次
		int ans = 0;
		while (L < arr.length && R < arr.length) {
			//第四种情况：如果L位置已经被访问过了，此时这种情况L往右移动
			if (visited[L]) {
				L++;
			}
			//第一种情况：如果L和R处于同一个位置，此时这种情况R往右移动
			else if (L == R) {
				R++;
			} else {
				//第三种情况：如果L位置的值和R位置的值差值等于K，
				//先标记R位置，然后记录一场比赛，L和R同时往右移动
				if (arr[R] - arr[L] == K) {
					visited[R] = true;
					ans++;
					L++;
					R++;
				}
				//第二种情况：如果L位置的值和R位置的值的差值小于K，此时这种情况R往右移动
				else if (arr[R] - arr[L] < K) {
					R++;
				}
				//如果都没有满足的，表示当前L位置的值和R位置的值永远凑不出K这个值来
				//那么L位置往右移动
				else {
					L++;
				}
			}
		}
		return ans;
	}
}
