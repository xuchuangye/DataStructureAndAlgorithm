package com.mashibing;

import com.common.SlidingWindowUtils;

import java.util.LinkedList;

/**
 * 给定一个整型数组arr，和一个整数num，arr中的子数组sub如果想要达标，
 * 必须满足：sub中最大值 - sub中最小值 <= num，返回arr中所有达标的子数组数量
 * @author xcy
 * @date 2022/5/14 - 14:45
 */
public class ReturnAllUpToStandardSubArrayCount {
	public static void main(String[] args) {
		int maxLen = 10;
		int maxValue = 200;
		int testTime = 100000;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int[] arr = SlidingWindowUtils.generateRandomArray(maxLen, maxValue);
			int num = (int) (Math.random() * (maxValue + 1));
			int ans1 = returnAllUpToStandardSubArrayCount(arr, num);
			int ans2 = returnAllUpToStandardSubArrayCountWithQueue(arr, num);
			if (ans1 != ans2) {
				System.out.println("Oops!");
				SlidingWindowUtils.printArray(arr);
				System.out.println(num);
				System.out.println(ans1);
				System.out.println(ans2);
				break;
			}
		}
		System.out.println("测试结束");
	}

	/**
	 * 时间复杂度：O(N³)
	 * @param arr
	 * @param num
	 * @return
	 */
	public static int returnAllUpToStandardSubArrayCount(int[] arr, int num) {
		if (arr == null || arr.length == 0 || num < 0) {
			return 0;
		}
		int count = 0;
		for (int L = 0; L < arr.length; L++) {
			for (int R = L; R < arr.length; R++) {
				int max = arr[L];
				int min = arr[L];
				for (int i = L + 1; i <= R; i++) {
					max = Math.max(max,  arr[i]);
					min = Math.min(min,  arr[i]);
				}
				if (max - min <= num) {
					count++;
				}
			}
		}
		return count;
	}

	/**
	 * 时间复杂度：O(N)
	 *
	 * 两个结论：
	 * 1、如果L - R范围上的max - min <= num，那么在L到R内的L' - R'范围上的max - min <= num
	 *
	 * 2、如果L - R范围上不达标，L往左扩一定不达标，R往右扩一定不达标
	 * @param arr
	 * @param num
	 * @return
	 */
	public static int returnAllUpToStandardSubArrayCountWithQueue(int[] arr, int num) {
		if (arr == null || arr.length == 0 || num < 0) {
			return 0;
		}
		//创建最大值双端队列
		LinkedList<Integer> maxWindow = new LinkedList<>();
		//创建最小值双端队列
		LinkedList<Integer> minWindow = new LinkedList<>();
		//统计满足子数组中最大值 - 最小值 <= num的子数组个数
		int count = 0;
		//滑动窗口的右边界，范围0 ~ arr.length - 1
		int R = 0;
		//滑动窗口的左边界范围0 ~ arr.length - 1
		for (int L = 0; L < arr.length; L++) {
			//如果L...R范围达标，就继续循环下去
			while (R < arr.length) {
				//判断最大值双端队列是否不为空，并且最大值双端队列尾部元素在数组中的值 <= R索引在数组中的值
				while (!maxWindow.isEmpty() && arr[maxWindow.peekLast()] <= arr[R]) {
					//移除最大值双端队列尾部元素
					maxWindow.pollLast();
				}
				//将R添加到最大值双端队列的尾部
				maxWindow.addLast(R);
				//判断最小值双端队列是否不为空，并且最小值双端队列尾部元素在数组中的值 >= R索引在数组中的值
				while (!minWindow.isEmpty() && arr[minWindow.peekLast()] >= arr[R]) {
					////移除最小值双端队列尾部元素
					minWindow.pollLast();
				}
				//将R添加到最小值双端队列的尾部
				minWindow.addLast(R);

				if (arr[maxWindow.peekFirst()] - arr[minWindow.peekFirst()] > num) {
					break;
				}else {
					R++;
				}
			}
			//如果退出while循环，说明此时L...R范围已经不达标
			//那么达标的子数组一共R - L个，累加起来
			count += R - L;
			//判断L是否退出滑动窗口，如果退出了，就从最大值双端队列中移除
			if (maxWindow.peekFirst() == L) {
				maxWindow.pollFirst();
			}
			//判断L是否退出滑动窗口，如果退出了，就从最小值双端队列中移除
			if (minWindow.peekFirst() == L) {
				minWindow.pollFirst();
			}
		}

		return count;
	}
}
