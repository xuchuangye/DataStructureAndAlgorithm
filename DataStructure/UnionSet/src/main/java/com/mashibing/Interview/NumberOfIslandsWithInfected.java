package com.mashibing.Interview;

/**
 * 并查集的面试题 --> 使用感染的方式实现
 * 给你一个由'1'（陆地）和 '0'（水）组成的的二维网格，请你计算网格中岛屿的数量。
 * 岛屿总是被水包围，并且每座岛屿只能由水平方向和/或竖直方向上相邻的陆地连接形成。
 * 此外，你可以假设该网格的四条边均被水包围。
 * <p>
 *
 * 时间复杂度：O(M * N)
 * M表示行，N表示列
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/number-of-islands
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author xcy
 * @date 2022/5/3 - 10:24
 */
public class NumberOfIslandsWithInfected {
	public static void main(String[] args) {
		char[][] board = {
				{'1', '1', '1'},
				{'0', '1', '0'},
				{'1', '1', '1'}
		};
		int count = numIslands(board);
		System.out.println(count);
	}

	/**
	 * 获取岛屿数量
	 *
	 * @param board 由'1'（陆地）和 '0'（水）组成的的二维网格
	 * @return
	 */
	public static int numIslands(char[][] board) {
		if (board == null || board.length == 0) {
			return 0;
		}
		//岛屿数量
		int islands = 0;
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				if (board[i][j] == '1') {
					islands++;
					infect(board, i, j);
				}
			}
		}
		return islands;
	}

	/**
	 * 将所有连接的'1'都修改为0的ASCII码
	 * @param board 由'1'（陆地）和 '0'（水）组成的的二维网格
	 * @param i i索引
	 * @param j j索引
	 */
	private static void infect(char[][] board, int i, int j) {
		if (i < 0 || i == board.length || j < 0 || j == board[0].length || board[i][j] != '1') {
			return;
		}
		board[i][j] = 0;
		infect(board, i - 1, j);
		infect(board, i + 1, j);
		infect(board, i, j - 1);
		infect(board, i, j + 1);
	}
}
