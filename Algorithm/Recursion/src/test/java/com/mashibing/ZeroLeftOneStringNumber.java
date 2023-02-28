package com.mashibing;

/**
 * 给定一个数N，想象只由0和1两种字符，组成的所有长度为N的字符串
 * 如果某个字符串,任何0字符的左边都必须有1紧挨着,认为这个字符串达标
 * 返回有多少达标的字符串
 *
 * 分析：
 * 1.N = 1时，字符串0或者1，达标的有1种情况
 * 2.N = 2时，字符串00, 01, 10, 11达标的有2种情况
 * 3.N = 3时，字符串000, 001, 010, 011, 100, 110, 111, 101达标的有3种情况
 *
 * 所以F(N) = F(N - 1) + F(N - 2)
 * @author xcy
 * @date 2022/5/20 - 10:31
 */
public class ZeroLeftOneStringNumber {
	public static void main(String[] args) {
		int count1 = zeroLeftOneStringNumber1(10);
		int count2 = zeroLeftOneStringNumber2(10);
		int count3 = zeroLeftOneStringNumberWithMatrix(10);
		System.out.println(count1);
		System.out.println(count2);
		System.out.println(count3);
	}

	/**
	 * 使用暴力递归的方式1
	 * @param N
	 * @return
	 */
	public static int zeroLeftOneStringNumber1(int N) {
		if (N < 1) {
			return 0;
		}
		if (N == 1) {
			return 1;
		}
		if (N == 2) {
			return 2;
		}
		return zeroLeftOneStringNumber1(N - 1) + zeroLeftOneStringNumber1(N - 2);
	}

	/**
	 * 使用暴力递归的方式2
	 * @param N
	 * @return
	 */
	public static int zeroLeftOneStringNumber2(int N) {
		if (N < 1) {
			return 0;
		}

		return process(1, N);
	}
	public static int process(int i, int N) {
		if (N - 1 == i) {
			return 2;
		}
		if (N == i) {
			return 1;
		}
		return process(i + 1, N) + process(i + 2, N);
	}

	/**
	 * 使用矩阵乘法的方式
	 * @param N
	 * @return
	 */
	public static int zeroLeftOneStringNumberWithMatrix(int N) {
		if (N < 1) {
			return 0;
		}
		else if (N == 1) {
			return 1;
		}
		else if (N == 2) {
			return 2;
		}else {
			int[][] matrix = {
					{1, 1},
					{1, 0},
			};
			int[][] newMatrix = matrixPow(matrix, N - 2);
			//|F(N),F(N - 1)| = |F2, F1| * |2 * 2的矩阵|的N - 2次方
			//---------------------------|a, b|
			//|F(N),F(N - 1)| = |2, 1| * |c, d|的N - 2次方
			//|a, b|                  |x, y|
			//|c, d|的N - 2次方计算出结果|z, t|
			//F(N) = 2 * x + 1 * z
			return 2 * newMatrix[0][0] + newMatrix[1][0];
		}
	}

	public static int[][] matrixPow(int[][] matrix, int pow) {
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

	public static int[][] multiMatrix(int[][] matrix1, int[][] matrix2) {
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
