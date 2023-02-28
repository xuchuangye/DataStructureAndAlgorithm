package com.mashibing;

import com.common.SlidingWindowUtils;

import java.util.LinkedList;

/**
 * 假设一个固定窗口，大小为W，依次滑动经过arr，返回每一次滑出状况的最大值
 * <p>
 * 举例：
 * arr = {4, 3, 5, 4, 3, 3, 6, 7}, W = 3
 * [4, 3, 5] 最大值5
 * [3, 5, 4] 最大值5
 * [5, 4, 3] 最大值5
 * [4, 3, 3] 最大值4
 * [3, 3, 6] 最大值6
 * [3, 6, 7] 最大值7
 * 返回：[5, 5, 5, 4, 6, 7]
 *
 * @author xcy
 * @date 2022/5/14 - 10:39
 */
public class SlidingWindowMaxValueInArray {
	public static void main(String[] args) {
		int testTime = 1000000;
		int maxSize = 100;
		int maxValue = 100;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int[] arr = SlidingWindowUtils.generateRandomArray(maxSize, maxValue);
			int w = (int) (Math.random() * (arr.length + 1));
			int[] ans1 = returnCurWindowMaxValueList(arr, w);
			int[] ans2 = right(arr, w);
			if (!SlidingWindowUtils.isEqual(ans1, ans2)) {
				System.out.println("Oops!");
			}
		}
		System.out.println("测试结束");
	}

	public static int[] right(int[] arr, int w) {
		if (arr == null || w < 1 || arr.length < w) {
			return null;
		}
		int N = arr.length;
		int[] res = new int[N - w + 1];
		int index = 0;
		int L = 0;
		int R = w - 1;
		while (R < N) {
			int max = arr[L];
			for (int i = L + 1; i <= R; i++) {
				max = Math.max(max, arr[i]);

			}
			res[index++] = max;
			L++;
			R++;
		}
		return res;
	}

	/**
	 *
	 * @param arr 原始数组
	 * @param M 窗口的大小
	 * @return
	 */
	public static int[] returnCurWindowMaxValueList(int[] arr, int M) {
		if (arr == null || arr.length < M || M < 1) {
			return null;
		}
		//存放索引的双端队列
		LinkedList<Integer> queue = new LinkedList<>();
		//返回所有arr数组中，从0到arr.length - 1，窗口为W的窗口最大值的数组集合
		int[] res = new int[arr.length - M + 1];
		int index = 0;
		//R表示索引，范围0 ~ arr.length - 1
		for (int R = 0; R < arr.length; R++) {
			//判断队列是否为空，并且队列的尾部元素在arr数组的值 <= 当前R索引所在的arr数组中的值
			while (!queue.isEmpty() && arr[queue.peekLast()] <= arr[R]) {
				//如果满足上述条件，则在队列中移除尾部元素，直到队列为空或者arr[R] < 当前队列的尾部元素所在arr数组的值结束
				queue.pollLast();
			}
			//队列为空时，直接从队列尾部添加索引R
			queue.addLast(R);
			//判断什么时候移除过期的元素，从队列头部移除
			if (queue.peekFirst() == R - M) {
				queue.pollFirst();
			}
			//判断如果R >= W - 1时，满足窗口，取最大值
			if (R >= M - 1) {
				res[index++] = arr[queue.peekFirst()];
			}
		}
		return res;
	}
}
