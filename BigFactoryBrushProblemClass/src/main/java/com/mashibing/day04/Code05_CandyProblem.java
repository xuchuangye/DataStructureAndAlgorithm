package com.mashibing.day04;

import com.mashibing.common.TestUtils;

import java.util.Arrays;

/**
 * 题目五：
 * 老师想给孩子们分发糖果，有N个孩子站成了一条直线，老师会根据每个孩子的表现，预先给他们评分
 * 你需要按照以下要求，帮助老师给这些孩子分发糖果：
 * 每个孩子至少分配到 1 个糖果。
 * 评分更高的孩子必须比他两侧的邻位孩子获得更多的糖果。
 * 那么这样下来，返回老师至少需要准备多少颗糖果
 * <p>
 * 测试链接：
 * https://leetcode.com/problems/candy/
 * <p>
 * 进阶(阿里面试题)：
 * 在原来要求的基础上，增加一个要求，相邻的孩子间如果分数一样，分的糖果数必须一样，返回至少需要准备多少颗糖果
 *
 * @author xcy
 * @date 2022/7/15 - 11:38
 */
public class Code05_CandyProblem {
	public static void main(String[] args) {
		int testTimes = 1000;
		int n = (int) (Math.random() * 100) + 1;
		int valueMax = (int) (Math.random() * 100) + 1;
		System.out.println("测试开始！");
		for (int i = 0; i < testTimes; i++) {
			int[] arr = TestUtils.randomArray(n, valueMax);
			int candy1 = candy1(arr);
			int candy2 = candy2(arr);
			if (candy1 != candy2) {
				System.out.println(candy1);
				System.out.println(candy2);
				break;
			}

			int candy3 = candy3(arr);
			int candy4 = candy4(arr);
			if (candy3 != candy4) {
				System.out.println(candy3);
				System.out.println(candy4);
				break;
			}
		}
		System.out.println("测试结束！");
	}

	/**
	 * 时间复杂度O(N)，额外空间复杂度O(N)
	 *
	 * @param arr
	 * @return
	 */
	public static int candy1(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		//一开始都是0，可以省去很多代码Coding问题
		int[] left = new int[arr.length];
		for (int i = 1; i < arr.length; i++) {
			if (arr[i] > arr[i - 1]) {
				left[i] = left[i - 1] + 1;
			}
		}

		int[] right = new int[arr.length];
		for (int i = arr.length - 2; i >= 0; i--) {
			if (arr[i] > arr[i + 1]) {
				right[i] = right[i + 1] + 1;
			}
		}

		int sum = 0;
		for (int i = 0; i < arr.length; i++) {
			sum += Math.max(left[i], right[i]);
		}

		return sum + arr.length;
	}

	/**
	 * 时间复杂度O(N)，额外空间复杂度O(N)
	 *
	 * @param arr
	 * @return
	 */
	public static int candy2(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		int[] left = new int[arr.length];
		//不需要管第一个孩子的分数，第一个孩子分的糖果数 == 1
		left[0] = 1;
		//从第二个孩子的分数开始
		for (int i = 1; i < arr.length; i++) {
			//表示当前孩子的分数比前一个孩子的分数大
			if (arr[i] > arr[i - 1]) {
				//当前孩子分的糖果数 = 前一个孩子分的糖果数 + 1
				left[i] = left[i - 1] + 1;
			}
			//否则，表示当前孩子的分数 <= 前一个孩子的分数
			else {
				//当前孩子分的糖果数 == 1
				left[i] = 1;
			}
		}

		int[] right = new int[arr.length];
		//不需要管最后一个孩子的分数，最后一个孩子分的糖果数 == 1
		right[arr.length - 1] = 1;
		//从倒数第二个孩子的分数开始
		for (int i = arr.length - 2; i >= 0; i--) {
			//表示当前孩子的分数比后一个孩子的分数大
			if (arr[i] > arr[i + 1]) {
				//当前孩子分的糖果数 == 后一个孩子分的糖果数 + 1
				right[i] = right[i + 1] + 1;
			}
			//否则，表示当前孩子的分数 <= 后一个孩子的分数
			else {
				//当前孩子分的糖果数 == 1
				right[i] = 1;
			}
		}

		int ans = 0;
		for (int i = 0; i < arr.length; i++) {
			ans += Math.max(left[i], right[i]);
		}
		return ans;
	}

	/**
	 * 进阶问题：孩子的评分一样，那么分发的糖果数必须一样
	 * 生成辅助数组left[]分为三种情况：
	 * 1.当前位置的分数比前一个位置的分数小，left[i] == 1
	 * 2.当前位置的分数比前一个位置的分数大，left[i] == left[i - 1] + 1
	 * 3.当前位置的分数和前一个位置的分数相等，left[i] == left[i - 1]
	 * 生成辅助数组right[]分为三种情况：
	 * 1.当前位置的分数比后一个位置的分数小，right[i] == 1
	 * 2.当前位置的分数比后一个位置的分数大，right[i] == right[i + 1] + 1
	 * 3.当前位置的分数和后一个位置的分数相等，right[i] == right[i + 1]
	 * <p>
	 * 时间复杂度O(N)，额外空间复杂度O(N)
	 *
	 * @return
	 */
	public static int candy3(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}

		int[] left = new int[arr.length];
		left[0] = 1;

		for (int i = 1; i < arr.length; i++) {
			if (arr[i] > arr[i - 1]) {
				left[i] = left[i - 1] + 1;
			} else if (arr[i] == arr[i - 1]) {
				left[i] = left[i - 1];
			} else {
				left[i] = 1;
			}
		}

		int[] right = new int[arr.length];
		right[arr.length - 1] = 1;

		for (int i = arr.length - 2; i >= 0; i--) {
			if (arr[i] > arr[i + 1]) {
				right[i] = right[i + 1] + 1;
			} else if (arr[i] == arr[i + 1]) {
				right[i] = right[i + 1];
			} else {
				right[i] = 1;
			}
		}

		int ans = 0;
		for (int i = 0; i < arr.length; i++) {
			ans += Math.max(left[i], right[i]);
		}
		return ans;
	}

	/**
	 * 进阶问题：孩子的评分一样，那么分发的糖果数必须一样
	 * 时间复杂度O(N)，额外空间复杂度O(1)
	 *
	 * @param arr
	 * @return
	 */
	public static int candy4(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		int index = nextMinIndex3(arr, 0);
		int[] data = rightCandsAndBase(arr, 0, index++);
		int res = data[0];
		int lbase = 1;
		int same = 1;
		int next = 0;
		while (index != arr.length) {
			if (arr[index] > arr[index - 1]) {
				res += ++lbase;
				same = 1;
				index++;
			} else if (arr[index] < arr[index - 1]) {
				next = nextMinIndex3(arr, index - 1);
				data = rightCandsAndBase(arr, index - 1, next++);
				if (data[1] <= lbase) {
					res += data[0] - data[1];
				} else {
					res += -lbase * same + data[0] - data[1] + data[1] * same;
				}
				index = next;
				lbase = 1;
				same = 1;
			} else {
				res += lbase;
				same++;
				index++;
			}
		}
		return res;
	}

	public static int nextMinIndex3(int[] arr, int start) {
		for (int i = start; i != arr.length - 1; i++) {
			if (arr[i] < arr[i + 1]) {
				return i;
			}
		}
		return arr.length - 1;
	}

	public static int[] rightCandsAndBase(int[] arr, int left, int right) {
		int base = 1;
		int cands = 1;
		for (int i = right - 1; i >= left; i--) {
			if (arr[i] == arr[i + 1]) {
				cands += base;
			} else {
				cands += ++base;
			}
		}
		return new int[]{cands, base};
	}
}
