package com.mashibing;

/**
 * 给定一块区域，区域的宽度总是2，区域的长度为N，有一块瓷砖：宽度为1，长度为2
 * 返回瓷砖能够铺满整个区域的方法数
 *
 * 分析：
 * 1.当N = 1时，区域宽度为2，长度为1，能够铺满的方法数有1种
 * 2.当N = 2时，区域宽度为2，长度为2，能够铺满的方法数有2种
 * 3.当N = 3时，区域宽度为2，长度为3，能够铺满的方法数有3种
 * 4.当N = 4时，区域宽度为2，长度为4，能够铺满的方法数有5种
 * 5.斐波那契数列公式：F(N) = F(N - 1) + F(N - 2)
 * @author xcy
 * @date 2022/5/20 - 11:07
 */
public class TileOverspreadNumberOfMethods {
	public static void main(String[] args) {
		int count1 = tileOverspreadNumberOfMethods(20);
		int count2 = tileOverspreadNumberOfMethodsWithVariable(20);
		int count3 = tileOverspreadNumberOfMethodsWithMatrix(20);
		System.out.println(count1);
		System.out.println(count2);
		System.out.println(count3);
	}

	/**
	 * 使用暴力递归的方式
	 * @param N N为区域的长度，并且区域的宽度永远为2，瓷砖的宽度为1，长度为2
	 * @return 返回瓷砖能够铺满整个区域的方法数
	 */
	public static int tileOverspreadNumberOfMethods(int N) {
		if (N < 1) {
			return 0;
		}
		if (N == 1 || N == 2) {
			return N;
		}
		return tileOverspreadNumberOfMethods(N - 1) + tileOverspreadNumberOfMethods(N - 2);
	}

	/**
	 * 使用变量的方式
	 * @param N N为区域的长度，并且区域的宽度永远为2，瓷砖的宽度为1，长度为2
	 * @return 返回瓷砖能够铺满整个区域的方法数
	 */
	public static int tileOverspreadNumberOfMethodsWithVariable(int N) {
		if (N < 1) {
			return 0;
		}
		if (N == 1 || N == 2) {
			return N;
		}
		int cur = 1;
		int pre = 1;
		int temp = 0;
		for (int i = 2; i <= N; i++) {
			temp = cur;
			cur += pre;
			pre = temp;
		}
		return cur;
	}

	/**
	 * 使用矩阵乘法的方式
	 * @param N N为区域的长度，并且区域的宽度永远为2，瓷砖的宽度为1，长度为2
	 * @return 返回瓷砖能够铺满整个区域的方法数
	 */
	public static int tileOverspreadNumberOfMethodsWithMatrix(int N) {
		if (N < 1) {
			return 0;
		}
		else if (N == 1 || N == 2) {
			return N;
		}else {
			int[][] matrix = new int[][] {
					{1, 1},
					{1, 0},
			};
			int[][] newMatrix = matrixPow(matrix, N - 2);
			//|F(N), F(N - 1)| = |F2, F1| * |2 * 2的矩阵|的N- 2次方
			//|F(N), F(N - 1)| = |2, 1| * |2 * 2的矩阵|的N- 2次方
			//|F(N), F(N - 1)| = |2, 1| * |a, b|的N- 2次方
			//--------------------------|c, d|
			//|a, b|                   |x, y|
			//|c, d|的N- 2次方的计算结果为|z, t|
			//F(N) = 2 * x + 1 * z
			return 2 * newMatrix[0][0] + newMatrix[1][0];
		}
	}

	private static int[][] matrixPow(int[][] matrix, int pow) {
		int[][] newMatrix = new int[matrix.length][matrix[0].length];
		for (int i = 0; i < newMatrix.length; i++) {
			newMatrix[i][i] = 1;
		}
		int[][] t = matrix;
		while (pow != 0) {
			if ((pow & 1) != 0) {
				newMatrix = multiMatrix(newMatrix, t);
			}
			t = multiMatrix(t, t);
			pow >>= 1;
		}
		return newMatrix;
	}

	private static int[][] multiMatrix(int[][] matrix1, int[][] matrix2) {
		int[][] matrix = new int[matrix1.length][matrix2[0].length];
		for (int i = 0; i < matrix1.length; i++) {
			for (int j = 0; j < matrix2[0].length; j++) {
				for (int k = 0; k < matrix1[0].length; k++) {
					matrix[i][j] += matrix1[i][k] * matrix2[k][j];
				}
			}
		}
		return matrix;
	}
}
