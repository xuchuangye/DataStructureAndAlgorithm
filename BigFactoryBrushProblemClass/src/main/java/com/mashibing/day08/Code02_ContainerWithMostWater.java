package com.mashibing.day08;

/**
 * 题目二：
 * 给定n个非负整数a1，a2，...an，每个数代表坐标中的一个点 (i, ai)。在坐标内画n条垂直线
 * 垂直线i的两个端点分别为(i, ai)和(i, 0)，找出其中的两条线，使得它们与x轴共同构成的容器可以容纳最多的水
 * <p>
 * 解题思路：
 * 只关注推高答案的可能性
 * <p>
 * 举例：
 * height[] = {1, 100, 100, 2}
 * index =     0   1    2   3
 * L = 0,R = 3
 * min = arr[L] == 1
 * 容纳水量 = 最低高度1 * 间距(R - L == 3)   --> 计算出0位置的最大容纳水量，L++
 * L = 1,R = 3
 * min = arr[R] == 2
 * 容纳水量 = 最低高度2 * 间距(R - L == 2)   --> 计算出3位置的最大容纳水量，R--
 * L = 1,R = 2
 * min = arr[R] == 100
 * 容纳水量 = 最低高度100 * 间距(R - L == 1) --> 计算出2位置的最大容纳水量，R--
 *
 * <p>
 * Leetcode测试链接：
 * https://leetcode.com/problems/container-with-most-water/
 *
 * @author xcy
 * @date 2022/7/24 - 9:30
 */
public class Code02_ContainerWithMostWater {
	public static void main(String[] args) {
		int[] height = {1, 8, 6, 2, 5, 4, 8, 3, 7};
		int area = maxArea(height);
		System.out.println(area);
	}

	/**
	 * 时间复杂度：O(N)
	 *
	 * @param height
	 * @return
	 */
	public static int maxArea(int[] height) {
		if (height == null || height.length == 0) {
			return 0;
		}
		int L = 0;
		int R = height.length - 1;
		int max = Integer.MIN_VALUE;
		while (L < R) {
			//每次计算容纳水量时，取最低高度以及间距
			//Math.min(height[L], height[R])表示最低高度
			//R - L表示间距
			//计算容纳水量时，取最大值
			max = Math.max(max, Math.min(height[L], height[R]) * (R - L));
			//如果左边高，右边低，右边往左移动
			if (height[L] > height[R]) {
				R--;
			}
			//如果左边低，右边高，左边往右移动
			else {
				L++;
			}
		}
		return max;
	}
}
