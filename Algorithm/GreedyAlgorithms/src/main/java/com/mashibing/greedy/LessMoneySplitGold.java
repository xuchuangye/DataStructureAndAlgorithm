package com.mashibing.greedy;

import com.mashibing.common.ArithmeticUtils;

import java.util.PriorityQueue;

/**
 * 贪心算法的面试题
 * <p>
 * 一块金条切成两半，是需要花费和长度数值一样的铜板的
 * 比如长度为20的金条，不管怎么切，都需要花费20个铜板。一群人想整分整块金条，怎么分最省铜板
 * 假如给定数组:{10, 20, 30}，代表一共3个人，整块金条长度为60，金条要分成10,20,30三个部分
 * (1)如果先把长度为60的金条分成10和50，花费60；再把长度50的金条分为20和30，花费50；一共花费110铜板
 * (2)如果先把长度为60的金条分成30和30，花费60，再把长度30的金条分成20和30，花费30,；一共花费90铜板
 * 输入一个数组，返回分割的最小代价
 * <p>
 * 基本思路：
 * 1、小根堆
 * 假设:
 * 原始数组：
 * arr[] = {10, 20, 30}
 * 累加和数组：
 * sum[] = {10, 30, 50}
 *
 * @author xcy
 * @date 2022/5/2 - 8:08
 */
public class LessMoneySplitGold {
	public static void main(String[] args) {
		int testTime = 100000;
		int maxSize = 6;
		int maxValue = 1000;
		System.out.println("测试开始！");
		for (int i = 0; i < testTime; i++) {
			int[] arr = ArithmeticUtils.generateRandomArray(maxSize, maxValue);
			if (lessMoneySplitGold(arr) != lessMoneySplitGoldScheme(arr)) {
				System.err.println("测试错误!");
			}
		}
		System.out.println("测试结束！");
	}

	/**
	 * 暴力方法
	 *
	 * @param arr
	 * @return
	 */
	public static int lessMoneySplitGoldScheme(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		return process(arr, 0);
	}

	/**
	 * @param arr 数组
	 * @param pre 之前已经付出的代价
	 * @return
	 */
	public static int process(int[] arr, int pre) {
		if (arr.length == 1) {
			return pre;
		}
		int ans = Integer.MAX_VALUE;
		for (int i = 0; i < arr.length; i++) {
			for (int j = i + 1; j < arr.length; j++) {
				ans = Math.min(ans, process(copyAndMergeTwo(arr, i, j),
						pre + arr[i] + arr[j]));
			}
		}
		return ans;
	}

	public static int[] copyAndMergeTwo(int[] arr, int a, int b) {
		int[] ans = new int[arr.length - 1];
		int index = 0;
		for (int i = 0; i < arr.length; i++) {
			if (i != a && i != b) {
				ans[index++] = arr[i];
			}
		}
		ans[index] = arr[a] + arr[b];
		return ans;
	}


	/**
	 * @param arr 数组的长度代表切分的人数，数组的元素代表切分的长度
	 * @return 返回切分黄金的最小代价
	 */
	public static int lessMoneySplitGold(int[] arr) {
		//小根堆
		PriorityQueue<Integer> heap = new PriorityQueue<>();
		//遍历数组的每一个元素
		for (int length : arr) {
			heap.add(length);
		}
		//最小代价
		int sum = 0;
		int cur = 0;
		//只要小根堆中元素的长度不小于1，也就是大于等于2，就一直循环
		while (heap.size() >= 2) {
			//取出小根堆中第一个和第二个堆顶元素
			cur = heap.poll() + heap.poll();
			//将小根堆中第一个和第二个堆顶元素相加的和cur累加到最小代价sum中
			sum += cur;
			//将小根堆中第一个和第二个堆顶元素相加的和cur添加到小根堆中
			heap.add(cur);
		}
		return sum;
	}
}
