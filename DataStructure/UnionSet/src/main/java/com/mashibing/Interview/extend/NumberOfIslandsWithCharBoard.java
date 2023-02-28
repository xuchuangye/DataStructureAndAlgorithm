package com.mashibing.Interview.extend;

import com.mashibing.unionset.UnionFindWithCharBoard;

/**
 * 并查集的面试题
 * 给你一个由'1'（陆地）和 '0'（水）组成的的二维网格，请你计算网格中岛屿的数量。
 * 岛屿总是被水包围，并且每座岛屿只能由水平方向和/或竖直方向上相邻的陆地连接形成。
 * 此外，你可以假设该网格的四条边均被水包围。
 * <p>
 *
 * 时间复杂度：O(M * N)
 * M表示 行，N表示列
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/number-of-islands
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author xcy
 * @date 2022/5/3 - 10:24
 */
public class NumberOfIslandsWithCharBoard {
	public static class Node {

	}
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
		int row = board.length;
		int col = board[0].length;
		//创建数组实现的并查集
		UnionFindWithCharBoard unionFindWithCharBoard = new UnionFindWithCharBoard(board);
		//第0行，第0列，既没有左边，也没有上边，所以i从1开始
		for (int i = 1; i < row; i++) {
			//第0行的所有'1'进行合并
			if (board[i - 1][0] == '1' && board[i][0] == '1') {
				unionFindWithCharBoard.union(i - 1, 0, i, 0);
			}
		}
		//第0行，第0列，既没有左边，也没有上边，所以j从1开始
		for (int j = 1; j < col; j++) {
			//第0列的所有'1'进行合并
			if (board[0][j - 1] == '1' && board[0][j] == '1') {
				unionFindWithCharBoard.union(0, j - 1, 0, j);
			}
		}

		//所有的'1'进行合并
		for (int i = 1; i < row; i++) {
			for (int j = 1; j < col; j++) {
				if (board[i][j] == '1') {
					if (board[i - 1][j] == '1') {
						unionFindWithCharBoard.union(i, j, i - 1, j);
					}
					if (board[i][j - 1] == '1') {
						unionFindWithCharBoard.union(i, j, i, j - 1);
					}
				}
			}
		}
		//返回集合的总个数
		return unionFindWithCharBoard.sets();
	}

}
