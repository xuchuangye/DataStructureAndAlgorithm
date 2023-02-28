package com.mashibing.matrixcycle;

/**
 * 题目七：
 * 给定一个正方形或者长方形矩阵matrix，实现zigzag打印
 * 0 1 2
 * 3 4 5
 * 6 7 8
 * 打印: 0 1 3 6 4 2 5 7 8
 * <p>
 * 思路分析：
 * 1.A和B
 * A能往右就往右，不能往右就往下，直到超过边界结束
 * B能往下就往下，不能往下就往右，直到超过边界结束
 * 2.
 * -      A
 * -      |
 * - B -> O O O O
 * -      O O O O
 * -      O O O O
 * -      O O O O
 *
 * @author xcy
 * @date 2022/6/9 - 18:08
 */
public class Code07_ZigZagPrintMatrix {
	public static void main(String[] args) {
		int[][] matrix = new int[][]{
				{1, 2, 6, 7},
				{3, 5, 8, 11},
				{4, 9, 10, 12}
		};
		printMatrixZigZag(matrix);

	}

	public static void printMatrixZigZag(int[][] matrix) {
		int topRow = 0;
		int topCol = 0;
		int downRow = 0;
		int downCol = 0;
		//行
		int endRow = matrix.length - 1;
		//列
		int endCol = matrix[0].length - 1;
		//默认
		boolean fromUp = false;
		while (topRow != endRow + 1) {
			printLevel(matrix, topRow, topCol, downRow, downCol, fromUp);
			topRow = topCol == endCol ? topRow + 1 : topRow;
			topCol = topCol == endCol ? topCol : topCol + 1;
			downCol = downRow == endRow ? downCol + 1 : downCol;
			downRow = downRow == endRow ? downRow : downRow + 1;
			fromUp = !fromUp;
		}
		System.out.println();
	}

	/**
	 * 根据fromUp的值决定是从右上往左下打印，还是从左下往右上
	 * @param m
	 * @param topRow
	 * @param topCol
	 * @param downRow
	 * @param downCol
	 * @param fromUp 值为false，从右上往左下打印，值为true，从左下往右上
	 */
	public static void printLevel(int[][] m, int topRow, int topCol, int downRow, int downCol, boolean fromUp) {
		//之后的第二次fromUp的值为true
		if (fromUp) {
			//值为true，从左下往右上
			//\
			// \
			//  \
			while (topRow != downRow + 1) {
				System.out.print(m[topRow++][topCol--] + " ");
			}
		}
		//第一次fromUp的值为false
		else {
			//值为false，从右上往左下打印
			//  /
			// /
			///
			while (downRow != topRow - 1) {
				System.out.print(m[downRow--][downCol++] + " ");
			}
		}
	}
}
