package com.mashibing.day03;

/**
 * 题目三：
 * 给定一个只有0和1组成的二维数组，返回边框全是1（内部无所谓）的最大正方形面积
 * <p>
 * 举例：
 * {1, 1, 1, 1, 1}
 * {1, 0, 1, 0, 1}
 * {1, 1, 1, 1, 1}
 * {1, 0, 0, 1, 1}
 * {1, 1, 1, 1, 1}
 * {1, 1, 1, 1, 0}
 * 最大正方形面积 = 5 * 5 = 25
 * <p>
 * 时间复杂度：
 * N * N的数据规模，长方形的时间复杂度：O(N的4次方)
 * 在N * N的数据规模中，随便选一个点a的时间复杂度：O(N的2次方)，随便选一个点b的时间复杂度O(N的2次方)
 * a点和b点形成长方形，总的时间复杂度：O(N的4次方)
 * 正方形的时间复杂度：O(N的3次方)
 * 在N * N的数据规模中，随便选一个点a的时间复杂度：O(N的2次方)，因为是正方形，边长的范围是0 ~ N，
 * 所以总的时间复杂度：O(N的3次方)
 * <p>
 * LeetCode测试链接：
 * https://leetcode.com/problems/largest-1-bordered-square/
 *
 * @author xcy
 * @date 2022/7/13 - 8:31
 */
public class Code03_Largest1BorderedSquare {
	public static void main(String[] args) {
		int[][] matrix = new int[][] {
				/*{1, 1, 1, 1, 1},
				{1, 0, 1, 0, 1},
				{1, 1, 1, 1, 1},
				{1, 0, 0, 1, 1},
				{1, 1, 1, 1, 1},
				{1, 1, 1, 1, 0}*/
				{0}
		};
		int area1 = maximumArea(matrix);
		int area2 = largest1BorderedSquare(matrix);
		System.out.println(area1);
		System.out.println(area2);
	}

	/**
	 * 对数器
	 * @param m
	 * @return
	 */
	public static int largest1BorderedSquare(int[][] m) {
		int[][] right = new int[m.length][m[0].length];
		int[][] down = new int[m.length][m[0].length];
		setBorderMap(m, right, down);
		for (int size = Math.min(m.length, m[0].length); size != 0; size--) {
			if (hasSizeOfBorder(size, right, down)) {
				return size * size;
			}
		}
		return 0;
	}

	/**
	 * TODO 代码待定
	 * @param matrix
	 * @return
	 */
	public static int maximumArea(int[][] matrix) {
		if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
			return 0;
		}
		int N = matrix.length;
		int M = matrix[0].length;
		//生成右信息
		//举例：
		//{1, 1, 1, 1, 1}
		//{1, 0, 1, 0, 1}
		//{1, 1, 1, 1, 1}
		//{1, 0, 0, 1, 1}
		//{1, 1, 1, 1, 1}
		//{1, 1, 1, 1, 0}
		//右信息：
		//{5, 4, 3, 2, 1}
		//{1, 0, 1, 0, 1}
		//{5, 4, 3, 2, 1}
		//{1, 0, 0, 2, 1}
		//{5, 4, 3, 2, 1}
		//{4, 3, 2, 1, 0}

		//生成下信息
		//举例：
		//{1, 1, 1, 1, 1}
		//{1, 0, 1, 0, 1}
		//{1, 1, 1, 1, 1}
		//{1, 0, 0, 1, 1}
		//{1, 1, 1, 1, 1}
		//{1, 1, 1, 1, 0}
		//下信息：
		//{6, 1, 3, 1, 5}
		//{5, 0, 2, 0, 4}
		//{4, 1, 1, 4, 3}
		//{3, 0, 0, 3, 2}
		//{2, 2, 2, 2, 1}
		//{1, 1, 1, 1, 0}
		int[][] right = new int[N][M];
		int[][] down = new int[N][M];
		setBorderMap(matrix, right, down);

		//时间复杂度：O(N的3次方)
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				for (int border = Math.min(N - i, M - j); border >= 0; border--) {
					//该正方形的左上顶点(i, j)，边长为border，从左上往右下拉
					//验证这个正方形，是不是边框都是1，时间复杂度：O(1)
					if (hasSizeOfBorder(border, right, down)) {
						return border * border;
					}
				}
			}
		}
		return 0;
	}

	private static void setBorderMap(int[][] matrix, int[][] right, int[][] down) {
		int r = matrix.length;
		int c = matrix[0].length;
		if (matrix[r - 1][c - 1] == 1) {
			right[r - 1][c - 1] = 1;
			down[r - 1][c - 1] = 1;
		}
		for (int i = r - 2; i != -1; i--) {
			if (matrix[i][c - 1] == 1) {
				right[i][c - 1] = 1;
				down[i][c - 1] = down[i + 1][c - 1] + 1;
			}
		}
		for (int i = c - 2; i != -1; i--) {
			if (matrix[r - 1][i] == 1) {
				right[r - 1][i] = right[r - 1][i + 1] + 1;
				down[r - 1][i] = 1;
			}
		}
		for (int i = r - 2; i != -1; i--) {
			for (int j = c - 2; j != -1; j--) {
				if (matrix[i][j] == 1) {
					right[i][j] = right[i][j + 1] + 1;
					down[i][j] = down[i + 1][j] + 1;
				}
			}
		}
	}

	public static boolean hasSizeOfBorder(int border, int[][] right, int[][] down) {
		for (int i = 0; i != right.length - border + 1; i++) {
			for (int j = 0; j != right[0].length - border + 1; j++) {
				if (right[i][j] >= border && down[i][j] >= border && right[i + border - 1][j] >= border
						&& down[i][j + border - 1] >= border) {
					return true;
				}
			}
		}
		return false;
	}
}
