package com.mashibing;

import java.util.HashMap;

/**
 * 求斐波那契数列矩阵乘法的方法 --> int[][] returnMatrix(int[][] matrix, int pow)
 * 1）斐波那契数列的线性求解（O(N)）的方式非常好理解
 * 2）同时利用线性代数，也可以改写出另一种表示
 * | F(N) , F(N-1) | = | F(2), F(1) |  *  某个二阶矩阵的N-2次方
 * 3）求出这个二阶矩阵，进而最快求出这个二阶矩阵的N-2次方
 *
 * 类似斐波那契数列的递归优化 -->
 * 1）如果某个递归，除了初始项之外，具有如下的形式
 * F(N) = C1 * F(N) + C2 * F(N-1) + … + Ck * F(N-k) ( C1…Ck 和k都是常数)
 * 2）并且这个递归的表达式是严格的、不随条件转移的
 * 3）那么都存在类似斐波那契数列的优化，时间复杂度都能优化成O(logN)
 *
 * @author xcy
 * @date 2022/5/6 - 16:12
 */
public class FibonacciSequence {
	public static void main(String[] args) {
		int sum = fibonacciSequence(10);
		System.out.println(sum);
		int count = fibonacciSequenceWithCache(10);
		System.out.println(count);
		int i = fibonacciSequenceWithMatrix(10);
		System.out.println(i);
	}

	/**
	 * 使用暴力递归
	 *
	 * @param N
	 * @return 返回斐波那契数列的第N项
	 */
	public static int fibonacciSequence(int N) {
		if (N < 0) {
			return 0;
		}
		if (N == 2 || N == 1) {
			return 1;
		}
		return fibonacciSequence(N - 1) + fibonacciSequence(N - 2);
	}

	/**
	 * 使用傻缓存
	 *
	 * @param N
	 * @return 返回斐波那契数列的第N项
	 */
	public static int fibonacciSequenceWithCache(int N) {
		int[] cache = new int[N + 1];
		for (int i = 0; i < N + 1; i++) {
			cache[i] = -1;
		}
		return fibonacciSequenceWithCache(N, cache);
	}

	/**
	 * 傻缓存的核心逻辑
	 *
	 * @param N
	 * @param cache 傻缓存
	 * @return 返回斐波那契数列的第N项
	 */
	private static int fibonacciSequenceWithCache(int N, int[] cache) {
		if (cache[N] != -1) {
			return cache[N];
		}
		int ans = 0;
		if (N == 1) {
			ans = 1;
		} else if (N == 2) {
			ans = 1;
		} else {
			ans = fibonacciSequenceWithCache(N - 1, cache) + fibonacciSequenceWithCache(N - 2, cache);
		}
		cache[N] = ans;
		return ans;
	}

	/**
	 * 使用矩阵乘法
	 * <p>
	 * 基本思路：
	 * |F(N), F(N - 1)行列式 = |F2, F1| * (2 * 2的矩阵) 的 n - 2次方
	 * 所以求出2 * 2的矩阵就可以求出F(N)
	 * <p>
	 * 举例：
	 * 1)求某个数的多少次方，比如：10的75次方
	 * --------------------- 64 32 16  8  4  2  1
	 * 1.将十进制的75换算成二进制的1  0  0  1  0  1  1
	 * 2.创建t = 10的1次方，每遍历一个二进制的位数，t都乘以自己，比如：
	 * 位数 t = 10
	 * 1 * 10的1次方
	 * 1 * 10的2次方
	 * 0 * 10的4次方
	 * 1 * 10的8次方
	 * 0 * 10的16次方
	 * 0 * 10的32次方
	 * 1 * 10的64次方
	 * ans = 1 * 10的1次方 * 10的2次方 * 10的8次方 * 10的64次方 == 10的75次方
	 * <p>
	 * 2)求矩阵的多少次方，比如：|a, b|的75次方
	 * ----------------------|c, d|
	 * 1.|1, 0|矩阵就相当于求某个数的1
	 * --|0, 1|
	 * 2.创建t = |矩阵|的1次方，每遍历一个二进制的位数，t都乘以自己，比如：
	 * 位数  t = |矩阵|
	 * 1 * |矩阵|的1次方
	 * 1 * |矩阵|的2次方
	 * 0 * |矩阵|的4次方
	 * 1 * |矩阵|的8次方
	 * 0 * |矩阵|的16次方
	 * 0 * |矩阵|的32次方
	 * 1 * |矩阵|的64次方
	 * ans = |1, 0| * |矩阵|的1次方 * |矩阵|的2次方 * |矩阵|的8次方 * |矩阵|的64次方 == |矩阵|的75次方
	 * ------|0, 1|
	 * <p>
	 * 3)求斐波那契数列的第N项
	 * |F(N), F(N - 1)| = |F2, F1| * |1, 1|的 n - 2次方
	 * ------------------------------|1, 0|
	 * <p>
	 * |F(N), F(N - 1)| = |1, 1| * |x, y|的 n - 2次方 --> 1 * x + 1 * z
	 * ----------------------------|z, t|
	 * <p>
	 * 时间复杂度：O(logN)
	 *
	 * @param N
	 * @return
	 */
	public static int fibonacciSequenceWithMatrix(int N) {
		if (N < 0) {
			return 0;
		}
		if (N == 1 || N == 2) {
			return 1;
		}
		//创建矩阵
		/*
		行列式一：|F3, F2| = |F2, F1| * (2 * 2 的矩阵) -> |a, b|
                                            |c, d|
		|2, 1| = |1, 1| * |a, b|
                          |c, d|

        行列式二：|F4, F3|行列式 = |F3, F2| * (2 * 2 的矩阵) -> |a, b|
                                            |c, d|
        |3, 2| = |2, 1| * |a, b|
                          |c, d|
		行列式一推导公式1：a + c = 2 和 b + d = 1
		行列式二推导公式2：2a + c = 3 和 2b + d = 2
		计算出 a = 1, c = 1, b = 1, d = 0
		最终得到2 * 2的矩阵
		|1, 1|
		|1, 0|
		 */
		int[][] matrix = {
				{1, 1},
				{1, 0},
		};
		//斐波那契数列的第N项的行列式 |F(N), F(N - 1)| = |1, 1| * |矩阵|的n - 2次方
		int[][] newMatrix = returnMatrix(matrix, N - 2);
		//斐波那契数列的第N项
		return newMatrix[0][0] + newMatrix[1][0];
	}

	/**
	 * @param matrix |F2, F1|的矩阵
	 * @param pow    次方
	 * @return 计算matrix矩阵的n - 2次方之后，返回新的矩阵
	 */
	private static int[][] returnMatrix(int[][] matrix, int pow) {
		//创建新的矩阵
		int[][] newMatrix = new int[matrix.length][matrix[0].length];
		//对角线都为1
		for (int i = 0; i < newMatrix.length; i++) {
			newMatrix[i][i] = 1;
		}
		//t == matrix的1次方
		int[][] t = matrix;
		//判断pow不为0，直到pow为0结束循环
		//举例： 十进制的75换算成二进制的1001011，每次循环之后往右移1位，即可遍历二进制的每一位数
		for (; pow != 0; pow >>= 1) {
			//如果不为0，判断pow & 1 不等于 0，说明该位置上存在1
			//举例：
			//第一次循环
			//01001011
			//00000001
			//右移1位
			//第二次循环
			//00100101
			//00000001
			//右移1位
			//...
			//最后一次循环
			//00000001
			//00000001
			//右移1位
			//此时pow为0，退出循环
			if ((pow & 1) != 0) {
				//矩阵 乘以 t
				newMatrix = multiMatrix(newMatrix, t);
			}
			//t 乘以 t自己
			t = multiMatrix(t, t);
		}
		return newMatrix;
	}

	/**
	 * @param matrix1 矩阵1
	 * @param matrix2 矩阵2
	 * @return 返回两个矩阵相乘的新矩阵
	 */
	private static int[][] multiMatrix(int[][] matrix1, int[][] matrix2) {
		//创建新的矩阵
		int[][] newMatrix = new int[matrix1.length][matrix2[0].length];
		for (int i = 0; i < matrix1.length; i++) {
			for (int j = 0; j < matrix2[0].length; j++) {
				for (int k = 0; k < matrix1[0].length; k++) {
					newMatrix[i][j] += matrix1[i][k] * matrix2[k][j];
				}
			}
		}
		return newMatrix;
	}

	// O(logN)
	public static int f3(int n) {
		if (n < 1) {
			return 0;
		}
		if (n == 1 || n == 2) {
			return 1;
		}
		// [ 1 ,1 ]
		// [ 1, 0 ]
		int[][] base = {
				{1, 1},
				{1, 0}
		};
		int[][] res = matrixPower(base, n - 2);
		return res[0][0] + res[1][0];
	}

	private static int[][] matrixPower(int[][] m, int p) {
		int[][] res = new int[m.length][m[0].length];
		for (int i = 0; i < res.length; i++) {
			res[i][i] = 1;
		}
		// res = 矩阵中的1
		int[][] t = m;// 矩阵1次方
		for (; p != 0; p >>= 1) {
			if ((p & 1) != 0) {
				res = product(res, t);
			}
			t = product(t, t);
		}
		return res;
	}

	// 两个矩阵乘完之后的结果返回
	private static int[][] product(int[][] matrix1, int[][] matrix2) {
		// a的列数同时也是b的行数
		int[][] ans = new int[matrix1.length][matrix2[0].length];
		for (int i = 0; i < matrix1.length; i++) {
			for (int j = 0; j < matrix2[0].length; j++) {
				for (int c = 0; c < matrix1[0].length; c++) {
					ans[i][j] += matrix1[i][c] * matrix2[c][j];
				}
			}
		}
		return ans;
	}
}
