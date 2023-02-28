package com.mashibing.iterviews;

/**
 * 题目一：
 * 给定一个正整数组成的无序数组arr，给定一个正整数值K
 * 找到arr的所有子数组里，哪个子数组的累加和等于K，并且是长度最大的
 * 返回其长度
 * <p>
 * 思路分析：
 * arr[] = { 3, 2, 1, 3, 3, 1, 1, 1, 1, 1, 1, 2, 2, 2} K=6
 * 滑动窗口：   [        ]
 * -          L        R
 * 情况1：滑动窗口的累加和小于K，R++
 * 情况2：滑动窗口的累加和等于K，记录累加和等于K的子数组的最大长度，R++
 * 情况3：滑动窗口的累加和大于K，L++
 *
 * @author xcy
 * @date 2022/6/9 - 8:17
 */
public class SubArrayCumulativeSumUpToStandardMaximumLength {
	public static void main(String[] args) {
		int[] arr = {3, 2, 1, 3, 3, 1, 1, 1, 1, 1, 1, 2, 2, 2};
		int K = 6;
		int testTimes = 100000;
		System.out.println("测试开始！");
		for (int i = 0; i < testTimes; i++) {
			int maxLength = getMaxLength(arr, K);
			int maxLength2 = getMaxLength2(arr, K);
			if (maxLength != maxLength2) {
				System.out.println(maxLength);
				System.out.println(maxLength2);
				System.out.println("测试错误！");
				break;
			}
		}
		System.out.println("测试结束！");
	}

	public static int getMaxLength(int[] arr, int K) {
		if (arr == null || arr.length == 0 || K <= 0) {
			return 0;
		}
		int left = 0;
		int right = 0;

		int sum = 0;
		int maxLength = 0;
		while (right < arr.length) {
			if (sum < K) {
				sum += arr[right];
				right++;
				if (right == arr.length) {
					break;
				}
			} else if (sum == K) {
				maxLength = Math.max(maxLength, right - left);
				sum += arr[right];
				right++;
			} else {
				sum -= arr[left];
				left++;
			}
		}
		return maxLength;
	}

	public static int getMaxLength2(int[] arr, int K) {
		if (arr == null || arr.length == 0 || K <= 0) {
			return 0;
		}
		int left = 0;
		int right = 0;

		int sum = arr[0];
		int len = 0;
		while (right < arr.length) {
			if (sum == K) {
				len = Math.max(len, right - left + 1);
				sum -= arr[left++];
			} else if (sum < K) {
				right++;
				if (right == arr.length) {
					break;
				}
				sum += arr[right];
			} else {
				sum -= arr[left++];
			}
		}
		return len;
	}
}
