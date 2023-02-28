package com.mashibing.Interview.extend;

import com.mashibing.unionset.UnionFind;
import com.mashibing.unionset.UnionSet;

import java.util.ArrayList;
import java.util.List;

/**
 * 并查集的面试题
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
public class NumberOfIslandsWithUnionSet {
	public static class Node {
		public int row;
		public int col;

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
		int ROW = board.length;
		int COL = board[0].length;
		Node[][] nodes = new Node[ROW][COL];
		List<Node> nodeList = new ArrayList<>();
		for (int i = 0; i < ROW; i++) {
			for (int j = 0; j < COL; j++) {
				if (board[i][j] == '1') {
					//为了区别二维数组中不同索引上相同的'1'，使用对象的地址
					Node node = new Node();
					nodes[i][j] = node;
					nodeList.add(nodes[i][j]);
				}
			}
		}
		UnionSet<Node> unionSet = new UnionSet<>(nodeList);
		//第0行，第0列就没有左边，也没有上边，所以从第1个索引开始
		for (int j = 1; j < COL; j++) {
			if (board[0][j - 1] == '1' && board[0][j] == '1') {
				unionSet.union(nodes[0][j - 1], nodes[0][j]);
			}
		}
		for (int i = 1; i < ROW; i++) {
			if (board[i - 1][0] == '1' && board[i][0] == '1') {
				unionSet.union(nodes[i - 1][0], nodes[i][0]);
			}
		}
		//第0行，第0列就没有左边，也没有上边，所以从第1个索引开始
		for (int i = 1; i < ROW; i++) {
			for (int j = 1; j < COL; j++) {
				//当前位置为'1'
				if (board[i][j] == '1') {
					//查看上边
					if (board[i][j - 1] == '1') {
						unionSet.union(nodes[i][j - 1], nodes[i][j]);
					}
					//查看左边
					if (board[i - 1][j] == '1') {
						unionSet.union(nodes[i - 1][j], nodes[i][j]);
					}
				}
			}
		}

		return unionSet.sets();
	}

}
