package com.mashibing.day17;

/**
 * 给定一个每一行有序、每一列也有序，整体可能无序的二维数组，再给定一个数num，返回二维数组中有没有num这个数
 *
 * @author xcy
 * @date 2022/8/7 - 10:04
 */
public class Code01_FindNumInSortedMatrix {
	public static void main(String[] args) {
		int[][] matrix = {
				{0, 1, 2, 3, 4, 5, 6}, // 0
				{10, 12, 13, 15, 16, 17, 18}, // 1
				{23, 24, 25, 26, 27, 28, 29}, // 2
				{44, 45, 46, 47, 48, 49, 50}, // 3
				{65, 66, 67, 68, 69, 70, 71}, // 4
				{96, 97, 98, 99, 100, 111, 122}, // 5
				{166, 176, 186, 187, 190, 195, 200}, // 6
				{233, 243, 321, 341, 356, 370, 380} // 7
		};
		int num = 233;
		boolean containsNum = isContainsNum(matrix, num);
		boolean contains = isContains(matrix, num);
		System.out.println(containsNum);
		System.out.println(contains);
	}

	/**
	 * 时间复杂度：O(N + M)
	 *
	 * @param matrix
	 * @param num
	 * @return
	 */
	public static boolean isContainsNum(int[][] matrix, int num) {
		if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) {
			return false;
		}
		int N = matrix.length;
		int M = matrix[0].length;
		for (int i = N - 1; i >= 0; i--) {
			for (int j = M - 1; j >= 0; j--) {
				if (matrix[i][j] > num && j - 1 >= 0) {
					matrix[i][j] = matrix[i][j - 1];
				} else if (matrix[i][j] < num && i - 1 >= 0) {
					matrix[i][j] = matrix[i - 1][j];
				} else {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean isContains(int[][] matrix, int num) {
		if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) {
			return false;
		}
		int row = 0;
		int col = matrix[0].length - 1;
		while (row < matrix.length && col >= 0) {
			if (matrix[row][col] == num) {
				return true;
			} else if (matrix[row][col] > num) {
				col--;
			} else {
				row++;
			}
		}
		return false;
	}
}
