package com.mashibing.day22;

/**
 * 给定 n 个非负整数表示每个宽度为 1 的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水
 * <p>
 * Leetcode题目：https://leetcode.cn/problems/trapping-rain-water/
 *
 * @author xcy
 * @date 2022/8/15 - 8:19
 */
public class Code02_TrappingRainWater {
	public static void main(String[] args) {

	}

	/**
	 * 求出i位置能够容纳的雨水量
	 * 如何求出？
	 * 左边：0 ~ i - 1范围内求出最大值max
	 * 右边：i + 1 ~ N - 1范围内求出最大值max
	 * 两边的最小值min - i位置的自己的值就是i位置能够容纳的雨水量
	 * 举例：
	 * -  max = 17    | 9 |     max = 23
	 * -  0 ~ i - 1     i     i + 1 ~ N - 1
	 * i位置能够容纳的雨水量 = Math.min(17, 23) - arr[i] = 17 - 9 = 8
	 * <p>
	 * 边界问题：
	 * 如果两边的最小值min - i位置的自己的值是负数，那么表示柱子过高，不能容纳雨水
	 * 所以Math.min(左边max，右边max) - arr[i] 和 0 进行比较max
	 * 举例：
	 * -  max = 17    | 18 |     max = 23
	 * -  0 ~ i - 1     i     i + 1 ~ N - 1
	 * i位置能够容纳的雨水量 = Math.min(17, 23) - arr[i] = 17 - 18 = -1
	 * Math.max(-1, 0) == 0
	 * i位置能够容纳的雨水量为0
	 *
	 * @param height
	 * @return
	 */
	public static int trap(int[] height) {
		if (height == null || height.length < 2) {
			return 0;
		}
		int N = height.length;
		int L = 1;
		int leftMax = height[0];
		int R = N - 2;
		int rightMax = height[N - 1];
		int water = 0;
		while (L <= R) {
			if (leftMax <= rightMax) {
				water += Math.max(0, leftMax - height[L]);
				leftMax = Math.max(leftMax, height[L++]);
			} else {
				water += Math.max(0, rightMax - height[R]);
				rightMax = Math.max(rightMax, height[R--]);
			}
		}
		return water;
	}

	/**
	 * 最优解
	 * arr[] = {17, 20, 8, 10, 18, 10, 27, 13}
	 * index =       L                  R
	 * leftMax = 17
	 * rightMax = 13
	 *
	 * @param height 柱子的高度数组
	 * @return 返回所有柱子存储的雨水量
	 */
	public static int trapOptimalSolution(int[] height) {
		if (height == null || height.length < 2) {
			return 0;
		}
		int N = height.length;
		//默认height[0]作为左边的最大值
		int leftMax = height[0];
		//默认height[N - 1]作为右边的最大值
		int rightMax = height[N - 1];
		//所以
		//L从1开始
		int L = 1;
		//R从N - 2开始
		int R = N - 2;
		//存储的雨水量
		int ans = 0;
		//arr[] = {17, 20, 8, 10, 18, 10, 27, 13}
		//index =       L                  R
		//leftMax = 17
		//rightMax = 13
		while (L <= R) {
			//比较当前左右两边的的最大值
			//哪边的值小，先进行计算
			//如果右边的最大值小
			if (rightMax <= leftMax) {
				//如果R位置的值大于等于右边的最大值，那么没有必要进行计算
				if (height[R] >= rightMax) {
					//更新右边的最大值
					rightMax = height[R];
				} else {
					//否则R位置的值小于右边的最大值，表示右边最高的柱子可以拦截R位置存储的雨水
					//然后将这个存储的雨水量累加
					ans += rightMax - height[R];
				}
				//位移
				R--;
			}
			//否则如果左边的最大值小
			else {
				//如果L位置的值大于等于左边的最大值，那么没有必要进行计算
				if (height[L] >= leftMax) {
					//更新左边的最大值
					leftMax = height[L];
				} else {
					//否则L位置的值小于左边的最大值，表示左边最高的柱子可以拦截L位置存储的雨水
					//然后将这个存储的雨水量累加
					ans += leftMax - height[L];
				}
				//位移
				L++;
			}
		}
		return ans;
	}
}
