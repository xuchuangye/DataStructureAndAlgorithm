package com.mashibing;

/**
 * IndexTree --> 二维
 *
 * @author xcy
 * @date 2022/5/25 - 15:53
 */
public class IndexTree_TwoDimension {
	public static void main(String[] args) {

	}

	/**
	 * 二维数组的行
	 */
	private int MAXN;
	/**
	 * 二维数组的列
	 */
	private int MAXM;
	/**
	 * 前缀和/累加和的二维数组
	 */
	private int[][] sums;
	/**
	 * 原始的二维数组
	 */
	private int[][] array;

	public IndexTree_TwoDimension(int[][] matrix) {
		if (matrix.length == 0 || matrix[0].length == 0) {
			return;
		}
		MAXN = matrix.length;
		MAXM = matrix[0].length;
		sums = new int[MAXN + 1][MAXM + 1];
		array = new int[MAXN][MAXM];
		for (int i = 0; i < MAXN; i++) {
			for (int j = 0; j < MAXM; j++) {
				update(i, j, matrix[i][j]);
			}
		}
	}

	/**
	 * add()
	 *
	 * @param row 行，从1开始，所以row + 1
	 * @param col 列，从1开始，所以col + 1
	 * @param num 要加的值
	 */
	public void add(int row, int col, int num) {
		for (int i = row + 1; i <= MAXN; i += i & (~i + 1)) {
			for (int j = col + 1; j <= MAXN; j += j & (~j + 1)) {
				sums[i][j] += num;
			}
		}
	}

	/**
	 * update()
	 *
	 * @param row   行，从1开始，所以row + 1
	 * @param col   列，从1开始，所以col + 1
	 * @param value 要更新的值
	 */
	public void update(int row, int col, int value) {
		if (MAXN == 0 || MAXM == 0) {
			return;
		}
		int add = value - array[row][col];
		array[row][col] = value;
		for (int i = row + 1; i <= MAXN; i += i & (~i + 1)) {
			for (int j = col + 1; j <= MAXN; j += j & (~j + 1)) {
				sums[i][j] += add;
			}
		}
	}

	/**
	 * sum()
	 * @param row 指定行数，从1开始，所以row + 1
	 * @param col 指定列数，从1开始，所以col + 1
	 * @return 返回指定行和列的累加和/前缀和
	 */
	public int sum(int row, int col) {
		int ans = 0;
		for (int i = row + 1; i > 0; i -= i & (~i + 1)) {
			for (int j = col + 1; j > 0; j -= j & (~j + 1)) {
				ans += sums[i][j];
			}
		}
		return ans;
	}

	/**
	 * 时间复杂度：O(logN * logN)
	 * @param leftUpX 左上角的x行
	 * @param leftUpY 左上角的y列
	 * @param rightDownX 右下角的x行
	 * @param rightDownY 右下角的y列
	 * @return 返回指定的左上角 到 右下角区域的累加和/前缀和
	 */
	public int sumOfRegion(int leftUpX,int leftUpY, int rightDownX, int rightDownY) {
		//注意：边界的问题
		return sums[rightDownX][rightDownY]
				- sums[leftUpX - 1][rightDownY]
				- sums[rightDownX][leftUpY - 1]
				+ sums[leftUpX - 1][leftUpY - 1];
	}
}
