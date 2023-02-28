package com.mashibing.monotonestack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * 单调栈
 * @author xcy
 * @date 2022/5/16 - 10:25
 */
public class MonotoneStack {
	public static void main(String[] args) {
		int[] arr = {3, 2, 1, 4, 5};
		int sum = returnIToJSum(arr, 0, 3);
		System.out.println(sum);
	}

	/**
	 * 数组中没有重复值
	 * <p>
	 * 时间复杂度：O(N)
	 *
	 * @param arr 原始数组
	 * @return 返回数组每个元素的左边最近并且比该元素小的值的索引，右边最近并且比该元素小的值的索引的数组
	 * 举例：
	 * arr = {3, 4, 1, 5}
	 * res = {
	 * ----res[0] = {-1,  2}
	 * ----res[1] = { 0,  2}
	 * ----res[2] = {-1, -1}
	 * ----res[3] = { 2, -1}
	 * }
	 */
	public static int[][] getNearLessNoRepeat(int[] arr) {
		if (arr == null || arr.length == 0) {
			return null;
		}
		int[][] res = new int[arr.length][2];
		//创建栈，并且栈中存储索引
		Stack<Integer> stack = new Stack<>();
		for (int i = 0; i < arr.length; i++) {
			//判断栈不为空并且栈顶元素的值大于准备入栈的元素值
			while (!stack.isEmpty() && arr[stack.peek()] > arr[i]) {
				//取出栈顶元素，也就是索引
				int popIndex = stack.pop();
				//栈顶元素的左边最近并且比该元素小的值的索引
				//如果栈为空，左边没有值，-1，不为空就是新的栈顶元素(索引)
				int leftLessIndex = stack.isEmpty() ? -1 : stack.peek();
				res[popIndex][0] = leftLessIndex;
				//栈顶元素的右边是离该元素最近并且比该元素小的值的索引
				res[popIndex][1] = i;
			}
			//如果栈为空或者栈顶元素的值小于准备入栈的元素值，准备入栈的元素直接入栈
			stack.push(i);
		}
		//数组已经遍历完，栈不为空
		while (!stack.isEmpty()) {
			//取出栈顶元素
			int popIndex = stack.pop();
			//栈顶元素的左边离该元素最近并且比该元素小的值的索引
			//如果栈为空，左边没有值，-1，不为空就是新的栈顶元素(索引)
			int leftLessIndex = stack.isEmpty() ? -1 : stack.peek();
			res[popIndex][0] = leftLessIndex;
			//栈顶元素的右边没有该元素最近并且比该元素小的值的索引，所以都是-1
			res[popIndex][1] = -1;
		}
		return res;
	}

	/**
	 * 数组中有重复值
	 * <p>
	 * 时间复杂度：O(N)
	 *
	 * @param arr 原始数组
	 * @return 返回数组每个元素的左边最近并且比该元素小的值的索引，右边最近并且比该元素小的值的索引的数组
	 * 举例：
	 * arr = {3, 4, 1, 5, 1, 2, 2}
	 * -------0, 1, 2, 3, 4, 5, 6
	 * res = {
	 * ----res[0] = {-1,  2}
	 * ----res[1] = { 0,  2}
	 * ----res[2] = {-1, -1}
	 * ----res[3] = { 2,  4}
	 * ----res[4] = {-1, -1}
	 * ----res[5] = { 4, -1}
	 * ----res[6] = { 4, -1}
	 * }
	 */
	public static int[][] getNearLess(int[] arr) {
		if (arr == null || arr.length == 0) {
			return null;
		}
		int[][] res = new int[arr.length][2];
		Stack<List<Integer>> stack = new Stack<>();
		for (int i = 0; i < arr.length; i++) {
			//判断栈不为空，并且栈顶元素，也就是链表的第一个元素所在arr数组中的值大于准备入栈的值
			while (!stack.isEmpty() && arr[stack.peek().get(0)] > arr[i]) {
				//取出栈顶元素 --> 链表
				List<Integer> pops = stack.pop();
				//链表中每个元素的左边都一样，都是新的栈顶元素 --> 链表的最后一个元素(索引)
				int leftLessIndex = stack.isEmpty() ? -1 : stack.peek().get(stack.peek().size() - 1);
				//对栈顶元素 --> 链表的中的元素都设置左边和右边离该元素最近并且比该元素小的值的索引
				for (Integer popIndex : pops) {
					res[popIndex][0] = leftLessIndex;
					res[popIndex][1] = i;
				}
			}
			//如果链表对应的值和准备入栈的值相等
			if (!stack.isEmpty() && arr[stack.peek().get(0)] == arr[i]) {
				//准备入栈的值的索引合并到值一样的链表中
				stack.peek().add(i);
			} else {
				//如果栈为空，或者链表的第一个元素所在arr数组中的值小于准备入栈的值，创建新的链表
				List<Integer> list = new ArrayList<>();
				//将索引添加到该链表中
				list.add(i);
				//将该链表添加 到栈中
				stack.push(list);
			}
		}
		//如果数组已经遍历完，并且栈中还有元素(链表)
		while (!stack.isEmpty()) {
			//取出栈顶元素 --> 链表
			List<Integer> pops = stack.pop();
			//链表中所有的元素的左边和右边离该元素最近并且比该元素小的值都一样
			//如果栈为空，左边没有，-1，如果栈不为空，左边就是新的栈顶元素(链表)中最后一个元素(索引)
			int leftLessIndex = stack.isEmpty() ? -1 : stack.peek().get(stack.peek().size() - 1);
			for (Integer popIndex : pops) {
				res[popIndex][0] = leftLessIndex;
				res[popIndex][1] = -1;
			}
		}
		return res;
	}

	/**
	 * 获取数组中索引i-j的累加和
	 *
	 * 时间复杂度：O(N)
	 * @param arr 原始数组
	 * @param i 索引i
	 * @param j 索引j
	 * @return 返回数组中索引i-j的累加和
	 */
	public static int returnIToJSum(int[] arr, int i, int j) {
		if (arr == null || arr.length == 0 || i > j) {
			return 0;
		}
		int[] temp = new int[arr.length];
		temp[0] = arr[0];
		for (int k = 1; k < arr.length; k++) {
			temp[k] = temp[k - 1] + arr[k];
		}
		System.out.println(Arrays.toString(temp));
		//0 ... j 的累加和 - 0 ... i的累加和就是i ... j的累加和
		if (i == 0) {
			return temp[j];
		}
		return temp[j] - temp[i - 1];
	}
}
