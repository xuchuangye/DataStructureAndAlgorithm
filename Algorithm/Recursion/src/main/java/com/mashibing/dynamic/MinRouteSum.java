package com.mashibing.dynamic;

import com.mashibing.common.DynamicUtils;

/**
 * 最短路径和
 *
 * 空间压缩 --> 小技巧
 * @author xcy
 * @date 2022/5/10 - 17:17
 */
public class MinRouteSum {
	public static void main(String[] args) {
		int rowSize = 10;
		int colSize = 10;
		int[][] m = DynamicUtils.generateRandomMatrix(rowSize, colSize);
		System.out.println(minPathSum1(m));
		System.out.println(minPathSum2(m));
		System.out.println(minPathSum3(m));
	}

	/**
	 * 最短路径和 --> 使用二维数组的方式
	 * <p>
	 * 基本思路：
	 * 1、创建对应m数组的二维数组table
	 * 2、求出table数组第0行的元素值
	 * 3、求出table数组第0列的元素值
	 * 4、根据第0行和第0列的最小值 + 对应m数组当前位置的值，求出table数组的所有元素值
	 * 5、返回table[m.length - 1][m[0].length - 1]
	 *
	 * @param m 二维数组
	 * @return 返回最短路径和
	 */
	public static int minPathSum1(int[][] m) {
		if (m == null || m.length == 0 || m[0] == null || m[0].length == 0) {
			return 0;
		}
		int row = m.length;
		int col = m[0].length;
		int[][] table = new int[row][col];
		table[0][0] = m[0][0];

		for (int j = 1; j < col; j++) {
			table[0][j] = table[0][j - 1] + m[0][j];
		}

		for (int i = 1; i < row; i++) {
			table[i][0] = table[i - 1][0] + m[i][0];
		}

		for (int i = 1; i < row; i++) {
			for (int j = 1; j < col; j++) {
				table[i][j] = Math.min(table[i - 1][j], table[i][j - 1]) + m[i][j];
			}
		}
		return table[row - 1][col - 1];
	}

	/**
	 * 最短路径和 --> 使用一维数组的方式(行多列少时使用)
	 * <p>
	 * 基本思路：
	 * 1、创建对应m数组的一维数组arr
	 * 2、根据m[0][0]，求出arr[0]的元素值
	 * 3、根据arr[0]的值以及m第0行的每一列的值，求出arr的所有元素值，此时的arr表示m第0行的所有路径累加和
	 * 4、遍历m数组的每一行之前，先求出arr[0](也就是arr[0] + m每一行第0列的值)
	 * 5、然后在每一行中遍历每一列：
	 * 1)重新赋值之前的arr[j]表示当前上一行当前列的值
	 * 2)arr[j - 1]表示当前这一行前一列的值
	 * 3)重新赋值之后的arr[j]表示当前这一行当前列的值：从1)和2)中选择最短路径和之后 + 对应m数组当前位置的值
	 * 6、返回arr[col - 1]
	 *
	 * @param m 二维数组
	 * @return 返回最短路径和
	 */
	public static int minPathSum2(int[][] m) {
		if (m == null || m.length == 0 || m[0] == null || m[0].length == 0) {
			return 0;
		}
		int row = m.length;
		int col = m[0].length;
		int[] arr = new int[col];
		//arr[0]表示m[0][0]的值
		arr[0] = m[0][0];

		//此时的arr[j]表示m[][]第0行所有的元素值
		//j表示m[][]第0行的所有列
		for (int j = 1; j < col; j++) {
			//当前列元素的值 = 前一列元素的值 + m[0][当前列]的值
			arr[j] = arr[j - 1] + m[0][j];
		}

		//此时的arr重复使用，遍历m[][]的每一行
		//i表示m[][]的行，因为m[][]第0行已经填充完毕，所以从第1行开始
		for (int i = 1; i < row; i++) {
			//开始之前，先确定每一行的第一列的值，因为该值只依赖上一行的值
			//所以arr[0]  = arr[0] + m[i][0](m[][]第1行第0列的值)，依次类推
			arr[0] += m[i][0];
			//j从第1列开始，因为第0列已经填充完毕
			for (int j = 1; j < col; j++) {
				//arr[j - 1]表示当前这一行前一列的值
				//arr[j]表示当前上一行当前列的值
				//当前这一行当前列的值 = 当前这一行前一列的值和上一行当前列的值中取出最小值 + m[当前行][当前列]的值
				arr[j] = Math.min(arr[j - 1], arr[j]) + m[i][j];
				//确定arr[j]结果之后，表示当前这一行当前列的值
			}
		}
		return arr[col - 1];
	}

	/**
	 * 最短路径和 --> 使用一维数组的方式(行少列多时使用)
	 * @param m
	 * @return
	 */
	public static int minPathSum3(int[][] m) {
		if (m == null || m.length == 0 || m[0] == null || m[0].length == 0) {
			return 0;
		}
		int row = m.length;
		int col = m[0].length;
		int[] arr = new int[col];
		arr[0] = m[0][0];

		for (int i = 1; i < row; i++) {
			arr[i] = arr[i - 1] + m[i][0];
		}

		for (int j = 1; j < col; j++) {
			arr[0] += m[0][j];
			for (int i = 1; i < row; i++) {
				arr[i] = Math.min(arr[i - 1], arr[i]) + m[i][j];
			}
		}
		return arr[row - 1];
	}
}
