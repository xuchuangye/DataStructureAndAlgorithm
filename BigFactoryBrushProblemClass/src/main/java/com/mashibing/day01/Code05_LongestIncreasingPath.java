package com.mashibing.day01;

/**
 * 题目五：
 * 给定一个二维数组matrix，你可以从任何位置出发，走向上、下、左、右四个方向，返回能走出来的最长的递增链长度
 * <p>
 * 解题思路：
 * 记忆化搜索
 * <p>
 * LeetCode测试链接：
 * https://leetcode.cn/problems/fpTFWP/
 *
 * @author xcy
 * @date 2022/7/6 - 8:58
 */
public class Code05_LongestIncreasingPath {
	public static void main(String[] args) {

	}

	/**
	 * 使用暴力递归的方式
	 *
	 * @param matrix
	 * @return
	 */
	public static int longestIncreasingPath1(int[][] matrix) {
		if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
			return 0;
		}
		int N = matrix.length;
		int M = matrix[0].length;

		int ans = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				ans = Math.max(ans, processWithRecursion(matrix, i, j));
			}
		}
		return ans;
	}

	/**
	 * @param matrix
	 * @param i
	 * @param j
	 * @return
	 */
	public static int processWithRecursion(int[][] matrix, int i, int j) {
		//i或者j越界
		if (i < 0 || i == matrix.length || j < 0 || j == matrix[0].length) {
			return 0;
		}
		//i或者j不越界
		//往上的方向走，i > 0表示能够往上走，并且上面i - 1行j列的值大于当前i行j列的值
		int up = i > 0 && matrix[i][j] < matrix[i - 1][j] ? processWithRecursion(matrix, i - 1, j) : 0;
		//往下的方向走，i < matrix.length - 1表示能够往下走，并且下面i + 1行j列的值大于当前i行j列的值
		int down = i < matrix.length - 1 && matrix[i][j] < matrix[i + 1][j] ? processWithRecursion(matrix, i + 1, j) : 0;
		//往左的方向走，j > 0表示能够往左走，并且左面i行j - 1列的值大于当前i行j列的值
		int left = j > 0 && matrix[i][j] < matrix[i][j - 1] ? processWithRecursion(matrix, i, j - 1) : 0;
		//往右的方向走，j < matrix[0].length - 1表示能够往右走，并且右面i行j + 1列的值大于当前i行j列的值
		int right = j < matrix[0].length - 1 && matrix[i][j] < matrix[i][j + 1] ? processWithRecursion(matrix, i, j + 1) : 0;
		return Math.max(Math.max(up, down), Math.max(left, right)) + 1;
	}

	/**
	 * 使用傻缓存的方式
	 *
	 * @param matrix
	 * @return
	 */
	public static int longestIncreasingPath2(int[][] matrix) {
		if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
			return 0;
		}
		int N = matrix.length;
		int M = matrix[0].length;

		//dp[i][j] == 0表示还这个位置还没有走过
		int[][] dp = new int[N][M];

		int ans = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				ans = Math.max(ans, processWithCache(matrix, i, j, dp));
			}
		}
		return ans;
	}

	/**
	 * @param matrix
	 * @param i
	 * @param j
	 * @return
	 */
	public static int processWithCache(int[][] matrix, int i, int j, int[][] dp) {
		//i或者j越界
		/*if (i < 0 || i == matrix.length || j < 0 || j == matrix[0].length) {
			return 0;
		}*/
		if (dp[i][j] != 0) {
			return dp[i][j];
		}
		int ans = 0;
		//i或者j不越界
		//往上的方向走，i > 0表示能够往上走，并且上面i - 1行j列的值大于当前i行j列的值
		int up = i > 0 && matrix[i][j] < matrix[i - 1][j] ? processWithCache(matrix, i - 1, j, dp) : 0;
		//往下的方向走，i < matrix.length - 1表示能够往下走，并且下面i + 1行j列的值大于当前i行j列的值
		int down = i < matrix.length - 1 && matrix[i][j] < matrix[i + 1][j] ? processWithCache(matrix, i + 1, j, dp) : 0;
		//往左的方向走，j > 0表示能够往左走，并且左面i行j - 1列的值大于当前i行j列的值
		int left = j > 0 && matrix[i][j] < matrix[i][j - 1] ? processWithCache(matrix, i, j - 1, dp) : 0;
		//往右的方向走，j < matrix[0].length - 1表示能够往右走，并且右面i行j + 1列的值大于当前i行j列的值
		int right = j < matrix[0].length - 1 && matrix[i][j] < matrix[i][j + 1] ? processWithCache(matrix, i, j + 1, dp) : 0;
		ans = Math.max(Math.max(up, down), Math.max(left, right)) + 1;
		dp[i][j] = ans;
		return ans;
	}
}
