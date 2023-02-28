package com.mashibing;

import java.util.Stack;

/**
 * 给定一个二维数组matrix，其中的值不是0就是1
 * 返回全部由1组成的子矩形的数量
 *
 * LeetCode测试链接：
 * https://leetcode.cn/problems/count-submatrices-with-all-ones/submissions/
 * @author xcy
 * @date 2022/5/18 - 16:11
 */
public class CountSubMatricesWithAllOnes {
	public static void main(String[] args) {
		int[][] mat = new int[][]{
				{0, 1},
				{1, 1},
				{1, 0},
		};
		int count = numSubmat(mat);
		System.out.println(count);
	}

	/**
	 * @param mat 原始数组
	 * @return 返回全部由1组成的子矩形的数量
	 */
	public static int numSubmat(int[][] mat) {
		if (mat == null || mat.length == 0 || mat[0].length == 0) {
			return 0;
		}
		int[] height = new int[mat[0].length];
		int count = 0;
		for (int i = 0; i < mat.length; i++) {
			for (int j = 0; j < mat[0].length; j++) {
				height[j] = mat[i][j] == 0 ? 0 : height[j] + 1;
			}
			//count += largestRectangleAreaWithStack(height);
			count += largestRectangleAreaWithArray(height);
		}
		return count;
	}


	/**
	 * @param arr 直方图
	 * @return 返回直方图中所有由1组成的子矩形的数量
	 */
	public static int largestRectangleAreaWithArray(int[] arr) {
		//判断如果数组为空或者数组元素个数为0，直接返回0
		if (arr == null || arr.length == 0) {
			return 0;
		}
		//使用Array创建单调栈
		int[] stack = new int[arr.length];
		//数组对应的索引
		int stackSize = 0;
		//所有由1组成的子矩形数量
		int count = 0;
		//遍历arr数组
		for (int i = 0; i < arr.length; i++) {
			//当栈不为空并且arr[栈顶元素]大于等于arr[i]时
			while (stackSize != 0 && arr[stack[stackSize - 1]] >= arr[i]) {
				//弹出栈顶元素(索引)，避免死循环的问题
				int curIndex = stack[--stackSize];
				//如果索引curIndex上的值 大于 索引上i的值
				if (arr[curIndex] > arr[i]) {
					//左边离curIndex最近并且比arr[curIndex]小的值的索引
					int leftLessIndex = stackSize == 0 ? -1 : stack[stackSize - 1];
					//距离
					int L = i - leftLessIndex - 1;
					//左右达到不了，并且左右两边较大的值，也就是高度
					//如果左边有值，则是arr[leftLessIndex]，如果左边没有值，高度就为0
					int max = Math.max(leftLessIndex == -1 ? 0 : arr[leftLessIndex], arr[i]);
					//当前索引curIndex所在的值 - 高度 * 距离
					//就表示在长度为L的距离上，高度为arr[curIndex] - max，一共有多少个由1组成的子矩形，累加起来
					count += (arr[curIndex] - max) * num(L);
				}
			}
			//如果栈为空，直接入栈
			stack[stackSize++] = i;
		}
		//遍历完arr数组，栈不为空
		while (stackSize != 0) {
			//弹出栈顶元素(索引)
			int curIndex = stack[--stackSize];
			//左边离curIndex最近并且比arr[curIndex]小的值的索引
			int leftLessIndex = stackSize == 0 ? -1 : stack[stackSize - 1];
			//距离
			int L = arr.length - leftLessIndex - 1;
			//左右达到不了，并且左右两边较大的值，也就是高度
			//因为没有右边，右边的索引使用-1表示，也就没有高度，所以高度取决于左边索引的高度
			//如果左边有值，则是arr[leftLessIndex]，如果左边没有值，高度就为0
			int max = leftLessIndex == -1 ? 0 : arr[leftLessIndex];
			//当前索引curIndex所在的值 - 高度 * 距离
			//就表示在长度为L的距离上，高度为arr[curIndex] - max，一共有多少个由1组成的子矩形，累加起来
			count += (arr[curIndex] - max) * num(L);
		}
		return count;
	}

	/**
	 * @param arr 直方图
	 * @return 返回直方图中所有由1组成的子矩形的数量
	 */
	public static int largestRectangleAreaWithStack(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		Stack<Integer> stack = new Stack<>();
		int count = 0;
		for (int i = 0; i < arr.length; i++) {
			while (!stack.isEmpty() && arr[stack.peek()] >= arr[i]) {
				//避免死循环的问题
				int curIndex = stack.pop();
				if (arr[curIndex] > arr[i]) {
					int leftLessIndex = stack.isEmpty() ? -1 : stack.peek();
					int L = i - leftLessIndex - 1;
					int max = Math.max(leftLessIndex == -1 ? 0 : arr[leftLessIndex], arr[i]);
					count += (arr[curIndex] - max) * num(L);
				}
			}
			stack.push(i);
		}
		while (!stack.isEmpty()) {
			int curIndex = stack.pop();
			int leftLessIndex = stack.isEmpty() ? -1 : stack.peek();
			int L = arr.length - leftLessIndex - 1;
			int max = leftLessIndex == -1 ? 0 : arr[leftLessIndex];
			count += (arr[curIndex] - max) * num(L);
		}
		return count;
	}

	/**
	 * @param L 距离
	 * @return 返回距离L上的所有由1组成的子矩形数量
	 */
	private static int num(int L) {
		return (L * (L + 1)) >> 1;
	}
}
