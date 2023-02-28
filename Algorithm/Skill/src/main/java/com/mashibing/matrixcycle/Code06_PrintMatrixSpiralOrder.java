package com.mashibing.matrixcycle;

/**
 * 题目六：
 * 给定一个长方形矩阵matrix，实现转圈打印
 * a  b  c  d
 * e  f  g  h
 * i  j  k  l
 * 打印顺序：a b c d h l k j i e f g
 * <p>
 * 思路分析：
 * 1.矩阵分圈结构的思想
 * 2.其实打印就相当于一圈接一圈的打印
 * 假设matrix[][] = new int[5][5];
 * -  0    1    2    3    4
 * 0  o —— o —— o —— o —— o
 * -                      |
 * 1  o —— o —— o —— o    o
 * -  |              |    |
 * 2  o    o —— o    o    o
 * -  |    |         |    |
 * 3  o    o —— o —— o    o
 * -  |                   |
 * 4  o —— o —— o —— o —— o
 *
 * @author xcy
 * @date 2022/6/9 - 17:36
 */
public class Code06_PrintMatrixSpiralOrder {
	public static void main(String[] args) {
		String[][] matrix = new String[][] {
				{"a",  "b",  "c",  "d"},
				{"e",  "f",  "g",  "h"},
				{"i",  "j",  "k",  "l"},
		};
		spiralOrderPrint(matrix);
	}

	public static void spiralOrderPrint(String[][] matrix) {
		if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
			return;
		}

		int topRow = 0;
		int topCol = 0;
		int downRow = matrix.length - 1;
		int downCol = matrix[0].length - 1;

		while (topRow <= downRow && topCol <= downCol) {
			printEdge(matrix, topRow++, topCol++, downRow--, downCol--);
		}
	}

	public static void printEdge(String[][] matrix, int topRow, int topCol, int downRow, int downCol) {
		/*
		左上角开始往右：matrix[a][b + i]
		左下角开始往上：matrix[c - i][b]
		右下角开始往左：matrix[c][d - i]
		右上角开始往下：matrix[a + i][d]
		*/
		if (topRow == downRow) {
			for (int i = topCol; i <= downCol; i++) {
				System.out.print(matrix[topRow][i] + " ");
			}
		}else if (topCol == downCol) {
			for (int i = topRow; i <= downRow; i++) {
				System.out.print(matrix[i][topCol] + " ");
			}
		}else {
			int curCol = topCol;
			int curRow = topRow;
			while (curCol != downCol) {
				System.out.print(matrix[topRow][curCol] + " ");
				curCol++;
			}
			while (curRow != downRow) {
				System.out.print(matrix[curRow][downCol] + " ");
				curRow++;
			}
			while (curCol != topCol) {
				System.out.print(matrix[downRow][curCol] + " ");
				curCol--;
			}
			while (curRow != topRow) {
				System.out.print(matrix[curRow][topCol] + " ");
				curRow--;
			}
		}
	}
}
