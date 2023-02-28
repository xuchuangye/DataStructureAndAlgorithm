package com.mashibing.day14;

import java.util.TreeSet;

/**
 * arr中求子数组的累加和是<=K的并且是最大的，返回这个最大的累加和
 * <p>
 * 解题思路：
 * 求所有子数组累加和 <= K的最大累加和，已知数组的累加和sum是固定不变的，其实就是求前缀和 >= sum - K的最大的
 * 0     ->|<-     arr.length - 1
 * 前缀和与累加和两边往都往中间靠
 *
 * @author xcy
 * @date 2022/8/2 - 17:00
 */
public class Code02_MaxSubArraySumLessOrEqualK {
	public static void main(String[] args) {

	}

	public static int getMaxLessOrEqualK(int[] arr, int K) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		int max = Integer.MIN_VALUE;
		int sum = 0;
		//创建有序表，记录每一个位置的前缀和
		TreeSet<Integer> set = new TreeSet<>();
		//一个元素都没有的时候，前缀和就已经是0了
		set.add(0);
		for (int value : arr) {
			//前缀和累加当前元素值
			sum += value;
			//其实就是求前缀和 >= sum - K并且是最大的
			if (set.ceiling(sum - K) != null) {
				max = Math.max(max, sum - set.ceiling(sum - K));
			}
			//当前的前缀和添加到set中
			//为了让set更新
			set.add(sum);
		}
		return max;
	}
}
