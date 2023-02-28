package com.mashibing.day22;

import java.util.PriorityQueue;

/**
 * 给你一个 m x n 的矩阵，其中的值均为非负整数，代表二维高度图每个单元的高度，请计算图中形状最多能接多少体积的雨水。
 * <p>
 * Leetcode题目：https://leetcode.cn/problems/trapping-rain-water-ii/
 *
 * @author xcy
 * @date 2022/8/15 - 8:19
 */
public class Code03_TrappingRainWaterII {
	public static void main(String[] args) {

	}

	/**
	 * 二维数组的点
	 */
	public static class Point {
		/**
		 * 当前点的值
		 */
		public int value;
		/**
		 * 当前点所在的行
		 */
		public int row;
		/**
		 * 当前点所在的列
		 */
		public int col;

		public Point(int value, int row, int col) {
			this.value = value;
			this.row = row;
			this.col = col;
		}
	}

	/**
	 * @param heightMap
	 * @return
	 */
	public static int trapRainWater(int[][] heightMap) {
		if (heightMap == null || heightMap.length == 0 || heightMap[0] == null || heightMap[0].length == 0) {
			return 0;
		}

		int N = heightMap.length;
		int M = heightMap[0].length;

		//标记二维数组的点是否被小根堆访问过，false表示未被访问过，true表示被访问过
		boolean[][] isEnter = new boolean[N][M];
		//创建小根堆
		PriorityQueue<Point> queue = new PriorityQueue<>((a, b) -> a.value - b.value);

		//-  0  1  2  3  4
		//-0 √
		//-1 √
		//-2 √
		//-3 √
		//-4 √
		for (int row = 0; row < N; row++) {
			isEnter[row][0] = true;
			queue.add(new Point(heightMap[row][0], row, 0));
		}
		//-  0  1  2  3  4
		//-0             √
		//-1             √
		//-2             √
		//-3             √
		//-4             √
		for (int row = 0; row < N; row++) {
			isEnter[row][M - 1] = true;
			queue.add(new Point(heightMap[row][M - 1], row, M - 1));
		}
		//-  0  1  2  3  4
		//-0 √  √  √  √  √
		//-1
		//-2
		//-3
		//-4
		for (int col = 0; col < M; col++) {
			isEnter[0][col] = true;
			queue.add(new Point(heightMap[0][col], 0, col));
		}
		//-  0  1  2  3  4
		//-0
		//-1
		//-2
		//-3
		//-4 √  √  √  √  √
		for (int col = 0; col < M; col++) {
			isEnter[N - 1][col] = true;
			queue.add(new Point(heightMap[N - 1][col], N - 1, col));
		}
		int ans = 0;
		//当前最低的柱子口，也就是木桶的最短短板
		int max = 0;
		//-  0  1  2  3  4
		//-0 8  8  8  8  8
		//-1 8  3  5  9  8
		//-2 8  7  2  6  4
		//-3 8  4  1  7  8
		//-4 8  8  8  8  8
		//heightMap[2][4] = 4, max = 4
		//将row == 1 || row == N - 1 || col == 0 || col = M - 1的所有元素全部添加到小根堆中
		//| 4 |
		//| 8 |
		//| . |
		//| . |
		//| . |
		//| 8 |
		//————
		//小根堆最顶部元素就是当前最低的水出口
		while (!queue.isEmpty()) {
			Point point = queue.poll();
			//更新最大值
			max = Math.max(max, point.value);
			int row = point.row;
			int col = point.col;
			//忽略row == 0行，并且能够往上继续
			if (row > 0 && !isEnter[row - 1][col]) {
				ans += Math.max(0, max - heightMap[row - 1][col]);
				isEnter[row - 1][col] = true;
				queue.add(new Point(heightMap[row - 1][col], row - 1, col));
			}
			//忽略col == 0列，并且能够往左继续
			if (col > 0 && !isEnter[row][col - 1]) {
				ans += Math.max(0, max - heightMap[row][col - 1]);
				isEnter[row][col - 1] = true;
				queue.add(new Point(heightMap[row][col - 1], row, col - 1));
			}
			//忽略row == N - 1行，并且能够往下继续
			if (row < N - 1 && !isEnter[row + 1][col]) {
				ans += Math.max(0, max - heightMap[row + 1][col]);
				isEnter[row + 1][col] = true;
				queue.add(new Point(heightMap[row + 1][col], row + 1, col));
			}
			//忽略col == M - 1列，并且能够往右继续
			if (col < M - 1 && !isEnter[row][col + 1]) {
				ans += Math.max(0, max - heightMap[row][col + 1]);
				isEnter[row][col + 1] = true;
				queue.add(new Point(heightMap[row][col + 1], row, col + 1));
			}
		}
		return ans;
	}
}
