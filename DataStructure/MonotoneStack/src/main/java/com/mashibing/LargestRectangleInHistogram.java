package com.mashibing;

import java.util.Stack;

/**
 * 给定一个正数数组arr，代表直方图
 * 返回直方图的最大长方形面积
 *
 * LeetCode测试链接：
 * https://leetcode.cn/problems/largest-rectangle-in-histogram/submissions/
 * @author xcy
 * @date 2022/5/17 - 10:30
 */
public class LargestRectangleInHistogram {
	public static void main(String[] args) {
		int[] arr = {5, 3, 5, 2, 5};
		int maxArea = largestRectangleArea(arr);
		int maxArea2 = largestRectangleArea2(arr);
		System.out.println(maxArea);
		System.out.println(maxArea2);
	}

	/**
	 * 使用栈表示单调栈
	 *
	 * @param arr 原始数组
	 * @return 返回直方图中最大的矩形面积
	 */
	public static int largestRectangleArea(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		//最大矩形面积
		int maxArea = 0;
		//创建单调栈
		Stack<Integer> stack = new Stack<>();
		for (int i = 0; i < arr.length; i++) {
			while (!stack.isEmpty() && arr[i] <= arr[stack.peek()]) {
				//当前栈顶元素的索引在arr数组中的值就是矩形的宽度
				int width = stack.pop();
				int leftLessIndex = stack.isEmpty() ? -1 : stack.peek();
				//长度
				int length = i - leftLessIndex - 1;
				//当前矩形面积
				int curArea = length * arr[width];
				maxArea = Math.max(curArea, maxArea);
			}
			stack.push(i);
		}
		//遍历完数组，栈不为空
		while (!stack.isEmpty()) {
			//当前栈顶元素的索引在arr数组中的值就是矩形的宽度
			int width = stack.pop();
			int leftLessIndex = stack.isEmpty() ? -1 : stack.peek();
			//长度
			int length = arr.length - leftLessIndex - 1;
			//当前矩形
			int curArea = length * arr[width];
			maxArea = Math.max(curArea, maxArea);
		}
		return maxArea;
	}

	/**
	 * 使用数组表示单调栈
	 *
	 * @param arr 原始数组
	 * @return 返回直方图中最大的矩形面积
	 */
	public static int largestRectangleArea2(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		//创建栈
		int[] stack = new int[arr.length];
		int stackSize = 0;
		//最大矩形面积
		int maxArea = 0;
		for (int i = 0; i < arr.length; i++) {
			while (stackSize != 0 && arr[stack[stackSize - 1]] >= arr[i]) {
				int width = stack[--stackSize];
				int leftLessIndex = stackSize == 0 ? -1 : stack[stackSize - 1];
				int length = i - leftLessIndex - 1;
				int curArea = length * arr[width];
				maxArea = Math.max(curArea, maxArea);
			}
			stack[stackSize++] = i;
		}
		while (stackSize != 0) {
			int width = stack[--stackSize];
			int leftLessIndex = stackSize == 0 ? -1 : stack[stackSize - 1];
			//长度
			int length = arr.length - leftLessIndex - 1;
			int curArea = length * arr[width];
			maxArea = Math.max(curArea, maxArea);
		}
		return maxArea;
	}
}
