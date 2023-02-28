package com.mashibing.dynamic;

/**
 * 给定5个参数：N, M, row, col, k
 * 表示在N * M的区域上，醉汉Bob初始位置在(row, col)上
 * Bob必须要迈出k步，且每步都会等概率向上下左右四个方向走一个单位
 * 任何时候，只要Bob离开N * M的区域，就直接死亡，返回迈出k步之后，Bob还在N * M的区域的概率
 *
 * @author xcy
 * @date 2022/5/11 - 15:35
 */
public class BobAliveProbability {
	public static void main(String[] args) {
		int N = 6;
		int M = 5;
		double probability1 = aliveProbability(N, M, 2, 3, 10);
		double probability2 = aliveProbabilityWithTable(N, M, 2, 3, 10);
		System.out.println(probability1);
		System.out.println(probability2);
	}

	/**
	 * 在初始(row, col)位置上，随机上下左右走，必须要走出k步之后，不超出N * M的区域，返回活着的概率 --> 使用暴力递归的方式
	 *
	 * @param N   区域的纵坐标
	 * @param M   区域的横坐标
	 * @param row 初始位置的行
	 * @param col 初始位置的列
	 * @param k   必须要走的步数
	 * @return 返回在初始(row, col)位置上，随机上下左右走，必须要走出k步之后，不超出N * M的区域，仍然活着的概率
	 */
	public static double aliveProbability(int N, int M, int row, int col, int k) {
		return coreLogic(N, M, row, col, k) / Math.pow(4, k);
	}

	/**
	 * 初始在(row, col)位置上，随机上下左右走，必须要走出k步之后，不超出N * M区域的方法有多少种
	 *
	 * @param N    区域的纵坐标
	 * @param M    区域的横坐标
	 * @param row  初始位置的行
	 * @param col  初始位置的列
	 * @param rest 剩余需要走的步数
	 * @return 返回初始在(row, col)位置上，随机上下左右走，必须要走出k步之后，不超出N * M区域的方法有多少种
	 */
	public static int coreLogic(int N, int M, int row, int col, int rest) {
		//如果越界，直接返回0，没有任何后续
		if (row < 0 || row == N || col < 0 || col == M) {
			return 0;
		}
		//如果没有超出N * M的区域，并且剩余步数为0，说明走完了并且没有越界，算作一种方法，返回1
		if (rest == 0) {
			return 1;
		}
		//如果没有超出N * M的区域，并且剩余步数没有走完
		int up = coreLogic(N, M, row + 1, col, rest - 1);
		int down = coreLogic(N, M, row - 1, col, rest - 1);
		int left = coreLogic(N, M, row, col - 1, rest - 1);
		int right = coreLogic(N, M, row, col + 1, rest - 1);
		//四种情况的活着的方法
		return up + down + left + right;
	}

	/**
	 * 在初始(row, col)位置上，随机上下左右走，必须要走出k步之后，不超出N * M的区域，返回活着的概率 --> 使用动态规划的方式
	 *
	 * @param N   区域的纵坐标
	 * @param M   区域的横坐标
	 * @param row 初始位置的行
	 * @param col 初始位置的列
	 * @param K   必须要走的步数
	 * @return 返回在初始(row, col)位置上，随机上下左右走，必须要走出k步之后，不超出N * M的区域，仍然活着的概率
	 */
	public static double aliveProbabilityWithTable(int N, int M, int row, int col, int K) {
		long[][][] table = new long[N][M][K + 1];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				table[i][j][0] = 1;
			}
		}

		for (int rest = 1; rest <= K; rest++) {
			for (int r = 0; r < N; r++) {
				for (int c = 0; c < M; c++) {
					//四种情况的活着的方法
					table[r][c][rest] = pick(table, N, M, r - 1, c, rest - 1);
					table[r][c][rest] += pick(table, N, M, r + 1, c, rest - 1);
					table[r][c][rest] += pick(table, N, M, r, c - 1, rest - 1);
					table[r][c][rest] += pick(table, N, M, r, c + 1, rest - 1);
				}
			}
		}
		//coreLogic(N, M, row, col, k) / Math.pow(4, k);
		return (double) table[row][col][K] / Math.pow(4, K);
	}

	/**
	 * 判断越界问题
	 * @param table
	 * @param N
	 * @param M
	 * @param row
	 * @param col
	 * @param rest
	 * @return
	 */
	public static long pick(long[][][] table, int N, int M, int row, int col, int rest) {
		if (row < 0 || row == N || col < 0 || col == M) {
			return 0;
		}
		return table[row][col][rest];
	}
}
