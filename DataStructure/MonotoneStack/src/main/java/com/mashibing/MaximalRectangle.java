package com.mashibing;

import java.util.Arrays;
import java.util.Stack;

/**
 * maximal-rectangle
 * 给定一个二维数组matrix，其中的值不是0就是1
 * 返回全部由1组成的最大子矩形，该矩形内部有多少个1
 * <p>
 * 使用技巧：
 * 1.压缩数组
 * 2.单调栈
 * <p>
 * 基本思路：
 * 1.根据二维数组matrix的列数，创建辅助数组height，表示1累加的高度之和
 * 2.按照二维数组matrix的行数，依次对每一列进行遍历
 * 1)如果是1，那么就进行累加
 * 2)如果是0，那么就重新计算
 * 举例：
 * char[][] matrix = {          int[] height = {
 * {1, 1, 1, 1, 1},              {1, 1, 1, 1, 1},
 * {1, 0, 1, 1, 1},              {2, 0, 2, 2, 2},
 * {1, 1, 1, 0, 1},              {3, 1, 3, 0, 3},
 * {1, 1, 1, 1, 1},              {4, 2, 4, 1, 4},
 * {1, 1, 1, 1, 1},              {5, 3, 5, 2, 5},
 * }                            }
 * 3.int[] height 计算出累加和之后得到直方图 -> {5, 3, 5, 2, 5} 返回直方图中的最大矩形面积即可
 * 直方图中的最大矩形面积就是1最多的子矩形
 * <p>
 * LeetCode测试链接：
 * https://leetcode.cn/problems/maximal-rectangle/submissions/
 *
 * @author xcy
 * @date 2022/5/17 - 16:00
 */
public class MaximalRectangle {
	public static void main(String[] args) {
		char[][] matrix = new char[][]{
				{'1', '1', '1', '1', '1'},
				{'1', '0', '1', '1', '1'},
				{'1', '1', '1', '0', '1'},
				{'1', '1', '1', '1', '1'},
				{'1', '1', '1', '1', '1'},
		};
		int area = maximalRectangle(matrix);
		System.out.println(area);
		/*int[] height = {5, 3, 5, 2, 5};
		int area1 = largestRectangleAreaWithArray(height);
		int area2 = largestRectangleAreaWithStack(height);
		System.out.println(area1);
		System.out.println(area2);*/
	}

	/**
	 * @param matrix 二维数组
	 * @return 必须全部由1组成的矩形并且1数量是最大的，返回1的数量
	 */
	public static int maximalRectangle(char[][] matrix) {
		if (matrix == null || matrix.length == 0) {
			return 0;
		}
		//直方图
		int[] height = new int[matrix[0].length];
		int maxArea = 0;
		//遍历二维数组matrix的每一行
		for (int i = 0; i < matrix.length; i++) {
			//内层for循环遍历二维数组matrix的每一列
			for (int j = 0; j < matrix[0].length; j++) {
				height[j] = matrix[i][j] == '0' ? 0 : height[j] + 1;
			}
			System.out.println(Arrays.toString(height));
			//每一次遍历都得到一个直方图int[] height，返回当前直方图中的最大矩形面积
			int curArea = largestRectangleAreaWithArray(height);
			//最终最大的就是由1组成并且1最多的子矩形
			maxArea = Math.max(curArea, maxArea);
		}
		return maxArea;
	}

	/**
	 * 返回直方图的最大矩形面积 --> 使用Stack表示单调栈
	 *
	 * @param arr 直方图
	 * @return 返回直方图的最大矩形面积
	 */
	public static int largestRectangleAreaWithStack(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		Stack<Integer> stack = new Stack<>();
		int maxArea = 0;
		for (int i = 0; i < arr.length; i++) {
			while (!stack.isEmpty() && arr[stack.peek()] >= arr[i]) {
				int width = stack.pop();
				int leftLessIndex = stack.isEmpty() ? -1 : stack.peek();
				int length = i - leftLessIndex - 1;
				int curArea = length * arr[width];
				maxArea = Math.max(curArea, maxArea);
			}
			stack.push(i);
		}
		while (!stack.isEmpty()) {
			int width = stack.pop();
			int leftLessIndex = stack.isEmpty() ? -1 : stack.peek();
			int length = arr.length - leftLessIndex - 1;
			int curArea = length * arr[width];
			maxArea = Math.max(curArea, maxArea);
		}
		return maxArea;
	}

	/**
	 * 返回直方图的最大矩形面积 --> 使用Array表示单调栈
	 *
	 * @param arr 直方图
	 * @return 返回直方图的最大矩形面积
	 */
	public static int largestRectangleAreaWithArray(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		int[] stack = new int[arr.length];
		int stackSize = 0;
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
			int length = arr.length - leftLessIndex - 1;
			int curArea = length * arr[width];
			maxArea = Math.max(curArea, maxArea);
		}
		return maxArea;
	}
}
