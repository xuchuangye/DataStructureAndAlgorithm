package com.mashibing.day10;

/**
 * 题目一：
 * 给你一个非负整数数组nums ，你最初位于数组的第一个位置。数组中的每个元素代表你在该位置可以跳跃的最大长度。
 * 你的目标是使用最少的跳跃次数到达数组的最后一个位置。假设你总是可以到达数组的最后一个位置
 * <p>
 * LeetCode测试链接：
 * https://leetcode.cn/problems/jump-game-ii/
 *
 * @author xcy
 * @date 2022/7/27 - 15:11
 */
public class Code01_JumpGame {
	public static void main(String[] args) {
		int[] nums = new int[]{2, 3, 6, 10, 7, 3, 5, 2, 1, 1, 2, 5};
		int step = jump(nums);
		System.out.println(step);
	}

	/**
	 * 时间复杂度：O(N)
	 *
	 * @param arr
	 * @return
	 */
	public static int jump(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		//step 以最精简的方式能够跳跃的步数
		int step = 0;
		//cur 当前在跳跃的步数之内，能够跳跃的最远位置
		int cur = 0;
		//next 如果能够多跳一步，能够跳跃的最远位置
		int next = 0;
		for (int i = 0; i < arr.length; i++) {
			if (cur < i) {
				//增加一步
				step++;
				//将如果多跳一步的最远位置拷贝给cur
				cur = next;
			}
			//如果能够多跳一步，那么下一步能够跳跃的最远位置
			next = Math.max(i + arr[i], next);
		}
		return step;
	}


}
