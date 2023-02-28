package com.mashibing.matrixcycle;

/**
 * 题目八：
 * 给定一个正数N，按照规则打印"*"
 * 当N = 3时：
 * -* * *
 * -    *
 * -  * *
 * 当N = 4时：
 * _* * * *
 * _      *
 * _  *   *
 * _  * * *
 * 当N = 10时：
 * _* * * * * * * * * *
 * _                  *
 * _  * * * * * * *   *
 * _  *           *   *
 * _  *   * * *   *   *
 * _  *   *   *   *   *
 * _  *   *       *   *
 * _  *   * * * * *   *
 * _  *               *
 * _  * * * * * * * * *
 * <p>
 * 思路分析：
 * 1.将两行作为一行
 * 2.将两列作为一列
 *
 * @author xcy
 * @date 2022/6/9 - 18:08
 */
public class Code08_PrintStar {
	public static void main(String[] args) {
		printStar(8);
	}

	/**
	 * 根据N的值，创建N * N的矩阵，并且在矩阵上按照规则打印星星
	 *
	 * @param N
	 */
	public static void printStar(int N) {
		if (N <= 2) {
			System.out.println("无法打印出*");
			return;
		}
		//根据N的值，创建N * N的矩阵
		String[][] matrix = new String[N][N];

		//初始化矩阵
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				matrix[i][j] = " ";
			}
		}
		int leftUp = 0;
		int rightDown = N - 1;

		//将两行作为一行
		//将两列作为一列
		//设置matrix矩阵的"*"
		while (leftUp <= rightDown) {
			set(matrix, leftUp, rightDown);
			leftUp += 2;
			rightDown -= 2;
		}

		//循环遍历打印"*"
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				System.out.print(matrix[i][j] + " ");
			}
			System.out.println();
		}
	}

	/**
	 * 设置matrix的"*"
	 * matrix[][] = new String[N][N], N = 8
	 * -* * * * * * * *
	 * -              *
	 * -  * * * * *   *
	 * -  *       *   *
	 * -  *   *   *   *
	 * -  *   * * *   *
	 * -  *           *
	 * -  * * * * * * *
	 *
	 * @param matrix    矩阵
	 * @param leftUp    既可以表示列，也可以表示行
	 * @param rightDown 既可以表示列，也可以表示行
	 */
	private static void set(String[][] matrix, int leftUp, int rightDown) {
		//设置第col列的元素为"*"，从leftUp == 0这一列开始，到rightDown == N - 1结束
		for (int col = leftUp; col <= rightDown; col++) {
			matrix[leftUp][col] = "*";
		}
		//设置第row行的元素为"*"，从leftUp == 0这一行开始，到rightDown == N - 1结束
		for (int row = leftUp; row <= rightDown; row++) {
			matrix[row][rightDown] = "*";
		}
		//设置第col列的元素为"*"，从rightDown - 1 == N - 1 - 1开始，到leftUp + 1结束
		for (int col = rightDown - 1; col > leftUp; col--) {
			matrix[rightDown][col] = "*";
		}
		//设置第row行的元素为"*"，从rightDown - 1 == N - 1 - 1开始，到leftUp + 1结束
		for (int row = rightDown - 1; row > leftUp + 1; row--) {
			matrix[row][leftUp + 1] = "*";
		}
	}
}
