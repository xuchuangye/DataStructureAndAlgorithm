package com.mashibing.subarray;

import com.mashibing.common.SkillUtils;

import java.util.HashMap;

/**
 * 题目二：
 * 给定一个整数组成的无序数组arr，值可能正、可能负、可能0
 * 给定一个整数值K
 * 找到arr的所有子数组里，哪个子数组的累加和等于K，并且是长度最大的
 * 返回其长度
 * <p>
 * 思路分析：
 * 有正数，有负数的数组arr，不符合单调性，不能再使用滑动窗口
 *
 * @author xcy
 * @date 2022/6/9 - 8:17
 */
public class Code02_LongestSumSubArrayLength {
	public static void main(String[] args) {
		int len = 50;
		int value = 100;
		int testTime = 500000;

		System.out.println("测试开始！");
		for (int i = 0; i < testTime; i++) {
			int[] arr = SkillUtils.generateRandomArray(len, value);
			int K = (int) (Math.random() * value) - (int) (Math.random() * value);
			int ans1 = getMaxLength(arr, K);
			int ans2 = SkillUtils.right(arr, K);
			if (ans1 != ans2) {
				System.out.println("测试错误！");
				SkillUtils.printArray(arr);
				System.out.println("K : " + K);
				System.out.println(ans1);
				System.out.println(ans2);
				break;
			}
		}
		System.out.println("测试结束！");
	}

	public static int getMaxLength(int[] arr, int K) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		int sum = 0;
		int maxLength = 0;
		//前缀和表，key：前缀和，value：0 ~ value这个前缀和首次出现的key这个前缀和的索引位置
		HashMap<Integer, Integer> preSum = new HashMap<>();
		//必须写上这句代码，否则会错过以0开始的累加和
		preSum.put(0, -1);
		for (int i = 0; i < arr.length; i++) {
			sum += arr[i];
			//判断是否出现过 当前位置的累加和 - K的前缀和
			if (preSum.containsKey(sum - K)) {
				//当前位置 - 获取sum - K的累加和首次出现的位置
				maxLength = Math.max(maxLength, i - preSum.get(sum - K));
			}

			//只有首次出现的累加和才会被记录，之后出现的都不更新
			if (!preSum.containsKey(sum)) {
				preSum.put(sum, i);
			}
		}
		return maxLength;
	}
}
