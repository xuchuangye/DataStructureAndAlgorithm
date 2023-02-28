package com.mashibing;

/**
 * 第一年农场有1只成熟的母牛A，往后的每年：
 * 1）每一只成熟的母牛都会生一只母牛
 * 2）每一只新出生的母牛都在出生的第三年成熟
 * 3）每一只母牛永远不会死
 * 返回N年后牛的数量
 *
 * @author xcy
 * @date 2022/5/19 - 11:14
 */
public class ReturnNYearCattleCount {
	public static void main(String[] args) {
		int count = returnNYearCattleCountWithRecursion(20);
		System.out.println("count = " + count);
		int number = returnNYearCattleCountWithRecurrence(20);
		System.out.println("count = " + number);
		int num = returnNYearCattleCountWithMatrix(20);
		System.out.println("num = " + num);
	}

	/**
	 * 使用暴力递归的方式
	 *
	 * @param N
	 * @return
	 */
	public static int returnNYearCattleCountWithRecursion(int N) {
		if (N < 1) {
			return 0;
		}
		if (N == 1) {
			return 1;
		} else if (N == 2) {
			return 2;
		} else if (N == 3) {
			return 3;
		} else {
			return returnNYearCattleCountWithRecursion(N - 1) + returnNYearCattleCountWithRecursion(N - 3);
		}
	}

	/**
	 * 使用递推的方式
	 * @param N
	 * @return
	 */
	public static int returnNYearCattleCountWithRecurrence(int N) {
		if (N < 1) {
			return 0;
		}
		if (N == 1 || N == 2 || N == 3) {
			return N;
		}
		int res = 3;
		int pre = 2;
		int prepre = 1;
		int temp1 = 0;
		int temp2 = 0;
		for (int i = 4; i <= N; i++) {
			temp1 = res;
			temp2 = pre;
			res = res + prepre;
			pre = temp1;
			prepre = temp2;
		}
		return res;
	}
	/**
	 * 使用矩阵乘法的方式
	 * @param N
	 * @return
	 */
	public static int returnNYearCattleCountWithMatrix(int N) {
		if (N < 1) {
			return 0;
		}
		if (N == 1) {
			return 1;
		}
		if (N == 2) {
			return 2;
		}
		if (N == 3) {
			return 3;
		}

		//计算得出
		//x = 1, i = 0, a = 1
		//y = 0, j = 0, b = 1
		//z = 0, k = 1, c = 0
		int[][] matrix = {
				{1, 1, 0},
				{0, 0, 1},
				{1, 0, 0},
		};

		int[][] newMatrix = returnMatrix(matrix, N - 3);
		//|F(N),F(N - 1),F(N - 2)| = |F3,F2,F1| * |3 * 3的矩阵matrix|的N - 3次方
		//|F(N),F(N - 1),F(N - 2)| = |3, 2, 1| * |3 * 3的矩阵matrix|的N - 3次方
		//---------------------------------------|a, b, c|
		//|F(N),F(N - 1),F(N - 2)| = |3, 2, 1| * |i, j, k|的N - 3次方
		//---------------------------------------|x, y, z|
		//|a, b, c|                         |a, b, c|
		//|i, j, k|的N - 3次方计算出新的matrix |i, j, k|
		//|x, y, z|                         |x, y, z|
		//                                        |a, b, c|
		//|F(N),F(N - 1),F(N - 2)| = |3, 2, 1| *  |i, j, k|
		//                                        |x, y, z|
		//F(N) = 3 * a + 2 * i + 1 * x
		return 3 * newMatrix[0][0] + 2 * newMatrix[1][0] + newMatrix[2][0];
	}

	/**
	 * @param matrix 原始矩阵
	 * @param pow    次方
	 * @return 返回计算matrix的pow次方的新的矩阵
	 */
	private static int[][] returnMatrix(int[][] matrix, int pow) {
		int[][] newMatrix = new int[matrix.length][matrix[0].length];
		for (int j = 0; j < newMatrix.length; j++) {
			newMatrix[j][j] = 1;
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

	/**
	 * @param matrix1 矩阵1
	 * @param matrix2 矩阵2
	 * @return 返回两个矩阵相乘之后新的矩阵
	 */
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
