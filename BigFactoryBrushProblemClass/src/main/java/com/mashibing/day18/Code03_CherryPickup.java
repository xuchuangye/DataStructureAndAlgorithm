package com.mashibing.day18;

import com.mashibing.common.TestUtils;

import java.io.*;
import java.util.Scanner;

/**
 * 给定一个矩阵matrix，先从左上角开始，每一步只能往右或者往下走，走到右下角。
 * 然后从右下角出发，每一步只能往上或者往左走，再回到左上角。任何一个位置的数字，只能获得一遍。返回最大路径和。
 * 输入描述:
 * 第一行输入两个整数M和N，M,N<=200
 * 接下来M行，每行N个整数，表示矩阵中元素
 * 输出描述:
 * 输出一个整数，表示最大路径和
 * <p>
 * 牛客网题目：https://www.nowcoder.com/questionTerminal/8ecfe02124674e908b2aae65aad4efdf
 *
 * @author xcy
 * @date 2022/8/8 - 14:47
 */
public class Code03_CherryPickup {
	public static void main(String[] args) throws IOException {
		/*int len = 100;
		int testTimes = 100;
		System.out.println("测试开始！");
		for (int i = 0; i < testTimes; i++) {
		int[][] matrix = TestUtils.randomOneOrZeroMatrix(len);
			int result1 = maxPathSum(matrix);
			int result2 = maxPathSumWithCache(matrix);
			if (result1 != result2) {
				System.out.println("测试失败！");
				System.out.println(result1);
				System.out.println(result2);
				break;
			}
		}
		System.out.println("测试结束！");*/

		//acm模式：自己输入，输出
		//ICPC模式
		/*Scanner scanner = new Scanner(System.in);
		int N = scanner.nextInt();
		int M = scanner.nextInt();
		int[][] matrix = new int[N][M];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				matrix[i][j] = scanner.nextInt();
			}
		}
		int ans = maxPathSum(matrix);
		System.out.println(ans);
		scanner.close();*/

		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer streamTokenizer = new StreamTokenizer(bufferedReader);
		PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(System.out));

		//streamTokenizer.nextToken()申请了一块内存，将数据加载到内存中
		//接着读取数据是从内存中获取的，而不再是从IO层面获取
		//StreamTokenizer.TT_EOF表示指示已读取流的结尾
		while (streamTokenizer.nextToken() != StreamTokenizer.TT_EOF) {
			int M = (int) streamTokenizer.nval;
			streamTokenizer.nextToken();
			int N = (int) streamTokenizer.nval;

			int[][] matrix = new int[M][N];
			for (int i = 0; i < M; i++) {
				for (int j = 0; j < N; j++) {
					streamTokenizer.nextToken();
					matrix[i][j] = (int) streamTokenizer.nval;
				}
			}

			printWriter.println(maxPathSum(matrix));
			printWriter.flush();
		}

	}

	/**
	 * 使用暴力递归的方式
	 * <p>
	 * A和B同时从左上角出发，到达右下角结束。相当于A是去往的方向，B是A回来的方向
	 *
	 * @param matrix 矩阵
	 * @return 返回A从左上角出发然后到达右下角之后，再回到左上角的收集的最大路径和
	 */
	public static int maxPathSum(int[][] matrix) {
		if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) {
			return 0;
		}
		return process(matrix, 0, 0, 0, 0);
	}

	/**
	 * A来到matrix[i][j]的位置上
	 * B来到matrix[x][y]的位置上
	 * A和B同时从左上角的位置出发，同时到达右下角的位置结束
	 * A和B同时来到的位置上只获取一遍
	 *
	 * @param matrix 矩阵
	 * @param i      A的横坐标
	 * @param j      A的纵坐标
	 * @param x      B的横坐标
	 * @param y      B的纵坐标
	 * @return 返回A和B同时从左上角出发，到达右下角的最大路径和
	 * 相当于A从左上角出发然后到达右下角之后，再回到左上角的收集的最大路径和
	 */
	public static int process(int[][] matrix, int i, int j, int x, int y) {
		int N = matrix.length;
		int M = matrix[0].length;

		if (i == N - 1 && j == M - 1) {
			return matrix[i][j];
		}
		//获取当前位置的数
		int cur = 0;
		//两种情况：
		//情况1：A和B同时来到同一个位置
		if (i == x && j == y) {
			cur = matrix[i][j];
		} else {
			cur = matrix[i][j] + matrix[x][y];
		}

		//已经获取到当前位置的数，继续获取后续的数
		int best = 0;
		//四种情况：
		//情况1：A往下走，B往下走
		if (i < N - 1) {
			if (x < N - 1) {
				best = Math.max(best, process(matrix, i + 1, j, x + 1, y));
			}
			//情况2：A往下走，B往右走
			if (y < M - 1) {
				best = Math.max(best, process(matrix, i + 1, j, x, y + 1));
			}
		}
		//情况3：A往右走，B往右走
		if (j < M - 1) {
			if (y < M - 1) {
				best = Math.max(best, process(matrix, i, j + 1, x, y + 1));
			}
			//情况4：A往右走，B往下走
			if (x < N - 1) {
				best = Math.max(best, process(matrix, i, j + 1, x + 1, y));
			}
		}
		return cur + best;
	}

	/**
	 * 使用暴力递归 + 傻缓存的方式
	 * <p>
	 * A和B同时从左上角出发，到达右下角结束。相当于A是去往的方向，B是A回来的方向
	 *
	 * @param matrix 矩阵
	 * @return 返回A从左上角出发然后到达右下角之后，再回到左上角的收集的最大路径和
	 */
	public static int maxPathSumWithCache(int[][] matrix) {
		if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) {
			return 0;
		}
		int N = matrix.length;
		int M = matrix[0].length;
		int[][][] dp = new int[N][M][N];
		return processWithCache(matrix, 0, 0, 0, dp);
	}

	/**
	 * A来到matrix[i][j]的位置上
	 * B来到matrix[x][y]的位置上
	 * A和B同时从左上角的位置出发，同时到达右下角的位置结束
	 * A和B同时来到的位置上只获取一遍
	 * <p>
	 * 不使用[][][][]四维数组，可以省去一个变量y，因为i + j == x + y，所以推导出y = i + j - x
	 * 所以创建缓存为[][][]三维数组
	 *
	 * @param matrix 矩阵
	 * @param i      A的横坐标
	 * @param j      A的纵坐标
	 * @param x      B的横坐标
	 * @param dp     傻缓存
	 * @return 返回A和B同时从左上角出发，到达右下角的最大路径和
	 * 相当于A从左上角出发然后到达右下角之后，再回到左上角的收集的最大路径和
	 */
	public static int processWithCache(int[][] matrix, int i, int j, int x, int[][][] dp) {
		if (dp[i][j][x] != 0) {
			return dp[i][j][x];
		}
		int ans = 0;
		int N = matrix.length;
		int M = matrix[0].length;
		//B的纵坐标，i + j == x + y
		int y = i + j - x;

		if (i == N - 1 && j == M - 1) {
			return matrix[i][j];
		}
		//获取当前位置的数
		int cur = 0;
		//两种情况：
		//情况1：A和B同时来到同一个位置
		if (i == x && j == y) {
			cur = matrix[i][j];
		} else {
			cur = matrix[i][j] + matrix[x][y];
		}

		//已经获取到当前位置的数，继续获取后续的数
		int best = 0;
		//四种情况：
		//情况1：A往下走，B往下走
		if (i < N - 1) {
			if (x < N - 1) {
				best = Math.max(best, processWithCache(matrix, i + 1, j, x + 1, dp));
			}
			//情况2：A往下走，B往右走
			if (y < M - 1) {
				best = Math.max(best, processWithCache(matrix, i + 1, j, x, dp));
			}
		}
		//情况3：A往右走，B往右走
		if (j < M - 1) {
			if (y < M - 1) {
				best = Math.max(best, processWithCache(matrix, i, j + 1, x, dp));
			}
			//情况4：A往右走，B往下走
			if (x < N - 1) {
				best = Math.max(best, processWithCache(matrix, i, j + 1, x + 1, dp));
			}
		}
		ans = cur + best;
		dp[i][j][x] = ans;
		return ans;
	}
}
