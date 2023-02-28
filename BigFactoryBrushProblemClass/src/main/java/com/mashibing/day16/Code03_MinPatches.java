package com.mashibing.day16;

import com.mashibing.common.TestUtils;

import java.util.Arrays;

/**
 * 给定一个已排序的正整数数组 nums，和一个正整数 n 。从 [1, n] 区间内选取任意个数字补充到 nums 中，
 * 使得 [1, n] 区间内的任何数字都可以用 nums 中某几个数字的和来表示，请输出满足上述要求的最少需要补充的数字个数
 * <p>
 * 数据规模：
 * 1 <= nums.length <= 1000
 * 1 <= nums[i] <= 10的4次方
 * nums 按 升序排列
 * 1 <= n <= 2的31次方 - 1
 * <p>
 * 解题思路：
 * 举例：
 * nums[] = {4, 17, 39, 5}  n = 83
 * 1.首先按照从小到大进行排序
 * nums[] = {4, 5, 17, 39}
 * 2.使用4，必须有1 ~ 3的范围，所以1 ~ 3需要补充两个数1,2   ->  2
 * 3.现在可以组成的范围是1 ~ 12,
 * 4.使用17，必须有1 ~ 16的范围，1 ~ 12没办法满足1 ~ 16，所以需要补充一个数13  ->  1
 * 5.现在可以组成的范围是1 ~ 25 + 17  -> 1 ~ 42
 * 6.使用39，必须有1 ~ 38的范围，1 ~ 42满足1 ~ 38，所以不需要补充
 * 7.现在可以组成的范围是1 ~ 42 + 39  -> 1 ~ 81
 * 8.n == 83，必须要1 ~ 82的范围，1 ~ 81没办法满足1 ~ 82，所以需要补充一个数82 -> 1
 * 9.最终最少需要补充的数字个数为4个
 * <p>
 * LeetCode测试链接：
 * https://leetcode.cn/problems/patching-array/
 *
 * @author xcy
 * @date 2022/8/5 - 14:31
 */
public class Code03_MinPatches {
	public static void main(String[] args) {
		System.out.println(Integer.MAX_VALUE);
		int len = 1000;
		int maxValue = 1;
		int testTimes = 100000;
		System.out.println("测试开始！");
		for (int i = 0; i < testTimes; i++) {
			int[] arr = randomArray(len, maxValue);
			int aim = (int) (Math.random() * maxValue) + maxValue * 2099248999;
			int patch1 = minPatches(arr, aim);
			int patch2 = minPatches2(arr, aim);
			if (patch1 != patch2) {
				System.out.println("测试失败！");
				break;
			}
		}
		System.out.println("测试结束！");
	}
	// 为了测试
	public static int[] randomArray(int len, int maxValue) {
		int[] ans = new int[len];
		for (int i = 0; i < len; i++) {
			ans[i] = (int) (Math.random() * maxValue) + 1;
		}
		return ans;
	}

	/**
	 * range变量使用long类型，不需要扣边界
	 *
	 * @param arr 原始数组
	 * @param aim 目标值
	 * @return 返回满足题目要求最少需要补充的数字个数
	 */
	public static int minPatches(int[] arr, int aim) {
		if (arr == null || arr.length == 0 || aim < 1) {
			return 0;
		}
		//需要补充的数的个数
		int patch = 0;
		//range的范围，一开始是0 ~ 0
		long range = 0;
		Arrays.sort(arr);
		for (int i = 0; i != arr.length; i++) {
			//第一步，判断当前i位置的值是否大于range + 1
			//arr[] = {4, 17, 39, 48}
			//arr[0] > range + 1
			//range += range + 1 -> 1 ~ 1，补充一个数1
			//arr[0] > range + 1
			//range += range + 1 -> 1 ~ 3，补充一个数2

			//第三步，判断当前i位置的值是否大于range + 1
			//arr[1] > range + 1
			//17 > 7 + 1
			//range += range + 1 -> 1 ~ 15，补充一个数8
			//arr[1] > range + 1
			//17 > 16
			//range += range + 1 -> 1 ~ 31，补充一个数16
			while (arr[i] > range + 1) {
				range += range + 1;
				patch++;
				if (range >= aim) {
					return patch;
				}
			}
			//第二步，如果当前i位置的值小于range + 1，表示当前i位置的值已经可以使用range范围内的数组成了，扩大range范围
			//继续下一个位置的数
			//range += arr[0] -> 1 ~ 7

			//第四步，如果当前i位置的值小于range + 1，表示当前i位置的值已经可以使用range范围内的数组成了，扩大range范围
			//继续下一个位置的数
			//range += arr[1] -> 48
			range += arr[i];
			if (range >= aim) {
				return patch;
			}
		}
		//数组最大值也满足不了目标值
		while (range + 1 <= aim) {
			patch++;
			range += range + 1;
		}
		return patch;
	}

	/**
	 * range变量使用int类型，需要扣边界
	 *
	 * @param arr arr 原始数组
	 * @param aim aim 目标值
	 * @return 返回满足题目要求最少需要补充的数字个数
	 */
	public static int minPatches2(int[] arr, int aim) {
		if (arr == null || arr.length == 0 || aim < 1) {
			return 0;
		}
		//需要补充的数的个数
		int patch = 0;
		//range的范围，一开始是0 ~ 0
		int range = 0;
		Arrays.sort(arr);
		for (int i = 0; i != arr.length; i++) {
			//第一步，判断当前i位置的值是否大于range + 1
			//arr[] = {4, 17, 39, 48}
			//arr[0] > range + 1
			//range += range + 1 -> 1 ~ 1，补充一个数1
			//arr[0] > range + 1
			//range += range + 1 -> 1 ~ 3，补充一个数2

			//第三步，判断当前i位置的值是否大于range + 1
			//arr[1] > range + 1
			//17 > 7 + 1
			//range += range + 1 -> 1 ~ 15，补充一个数8
			//arr[1] > range + 1
			//17 > 16
			//range += range + 1 -> 1 ~ 31，补充一个数16
			while (arr[i] > range + 1) {
				if (range > Integer.MAX_VALUE - (range + 1)) {
					return patch + 1;
				}
				range += range + 1;
				patch++;
				if (range >= aim) {
					return patch;
				}
			}
			//第二步，如果当前i位置的值小于range + 1，表示当前i位置的值已经可以使用range范围内的数组成了，扩大range范围
			//继续下一个位置的数
			//range += arr[0] -> 1 ~ 7

			//第四步，如果当前i位置的值小于range + 1，表示当前i位置的值已经可以使用range范围内的数组成了，扩大range范围
			//继续下一个位置的数
			//range += arr[1] -> 48
			if (range > Integer.MAX_VALUE - arr[i]) {
				return patch;
			}
			range += arr[i];
			if (range >= aim) {
				return patch;
			}
		}
		//数组最大值也满足不了目标值
		while (range + 1 <= aim) {
			if (aim == range && aim == Integer.MAX_VALUE) {
				return patch;
			}
			if (range > Integer.MAX_VALUE - (range + 1)) {
				return patch;
			}
			range += range + 1;
			patch++;
		}
		return patch;
	}
}
