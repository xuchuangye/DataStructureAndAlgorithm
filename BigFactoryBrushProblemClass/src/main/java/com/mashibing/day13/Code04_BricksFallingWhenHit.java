package com.mashibing.day13;

/**
 * 有一个 m * n 的二元网格，其中 1表示砖块，0表示空白。砖块稳定（不会掉落）的前提是：
 * 一块砖直接连接到网格的顶部，或者至少有一块相邻（4个方向之一）砖块稳定不会掉落时
 * 给你一个数组 hits ，这是需要依次消除砖块的位置。
 * 每当消除 hits[i] = (rowi, coli) 位置上的砖块时，对应位置的砖块（若存在）会消失
 * 然后其他的砖块可能因为这一消除操作而掉落。一旦砖块掉落，它会立即从网格中消失（即，它不会落在其他稳定的砖块上）
 * 返回一个数组result ，其中 result[i] 表示第 i 次消除操作对应掉落的砖块数目
 * 注意，消除可能指向是没有砖块的空白位置，如果发生这种情况，则没有砖块掉落。
 * <p>
 * 解题思路：
 * 1.可以通过每次发射炮弹之后，根据挂载在天花板上砖块的数量变化来收集答案
 * 2.使用并查集UnionSet
 * 具体思路：
 * int[][] grid 表示砖块
 * -   0  1  2  3  4  5   <- 天花板
 * -0  1  0  0  1  0  0
 * -1  1  0  0  1  0  0
 * -2  1  1  0  1  1  1
 * -3  0  0  1  0  0  1
 * int[][] hits 表示炮弹
 * -  0  1
 * -0[1, 0]
 * -1[2, 0]
 * -2[2, 4]
 * -3[1, 3]
 * 1.首先将炮弹的数组进行过滤，如果炮弹本身就没有命中砖块，掉落砖块的数量一定为0，之后的炮弹必定命中砖块
 * 2.然后根据所有的炮弹的位置，将网格上对应的位置1全部修改为2
 * -   0  1  2  3  4  5   <- 天花板
 * -0  1  0  0  1  0  0
 * -1  2  0  0  2  0  0
 * -2  2  1  0  1  2  1
 * -3  0  0  1  0  0  1
 * 3.此时挂载在天花板上的砖块总共有2块
 * 4.逆序遍历炮弹数组，将炮弹的位置的2修改为1,
 * -   0  1  2  3  4  5   <- 天花板
 * -0  1  0  0  1  0  0
 * -1  2  0  0  1  0  0
 * -2  1  1  0  1  1  1
 * -3  0  0  1  0  0  1
 * (1).将grid[1][3]的2修改为1，此时挂载在天花板上的砖块总共有4块
 * 那么就表示如果炮弹打碎grid[1][3]位置的砖块，那么肯定掉落2块砖，又因为grid[1][3]位置的砖块本身就被炮弹打碎了
 * 所以只掉落了1块砖
 * -   0  1  2  3  4  5   <- 天花板
 * -0  1  0  0  1  0  0
 * -1  2  0  0  1  0  0
 * -2  2  1  0  1  1  1
 * -3  0  0  1  0  0  1
 * (2).将grid[2][4]的2修改为1，此时挂载在天花板上的砖块总共有7块
 * 那么就表示如果炮弹打碎grid[2][4]位置的砖块，那么肯定掉落3块砖，又因为grid[2][4]位置的砖块本身就被炮弹打碎了
 * 所以只掉落了2块砖
 * -   0  1  2  3  4  5   <- 天花板
 * -0  1  0  0  1  0  0
 * -1  2  0  0  1  0  0
 * -2  1  1  0  1  1  1
 * -3  0  0  1  0  0  1
 * (3).将grid[2][0]的2修改为1，此时挂载在天花板上的砖块总共有7块
 * 那么就表示炮弹没有命中grid[2][0]位置的砖块，那么肯定掉落0块砖
 * -   0  1  2  3  4  5   <- 天花板
 * -0  1  0  0  1  0  0
 * -1  1  0  0  1  0  0
 * -2  1  1  0  1  1  1
 * -3  0  0  1  0  0  1
 * (4).将grid[1][0]的2修改为1，此时挂载在天花板上的砖块总共有10块
 * 那么就表示如果炮弹打碎grid[1][0]位置的砖块，那么肯定掉落3块砖，又因为grid[1][0]位置的砖块本身就被炮弹打碎了
 * 所以只掉落了2块砖
 *
 * <p>
 * Leetcode题目：https://leetcode.cn/problems/bricks-falling-when-hit/
 *
 * @author xcy
 * @date 2022/8/1 - 15:02
 */
public class Code04_BricksFallingWhenHit {
	public static void main(String[] args) {

	}

	/**
	 * 并查集
	 */
	public static class UnionFind {
		/**
		 * N行
		 */
		private int N;
		/**
		 * M列
		 */
		private int M;
		/**
		 * 有多少块砖，挂载在天花板上
		 */
		private int cellingAll;
		/**
		 * 原始矩阵
		 */
		private int[][] grid;
		/**
		 * 表示当前以i为头节点的集合是否是天花板集合
		 */
		private boolean[] cellingSet;
		/**
		 * 当前节点的父节点
		 */
		private int[] parents;
		/**
		 * 当前节点所在的集合大小
		 */
		private int[] setSize;
		/**
		 * 栈，辅助数组
		 */
		private int[] stack;


		public UnionFind(int[][] matrix) {
			initSpace(matrix);
			initConnect();
		}

		/**
		 * 初始化空间
		 *
		 * @param matrix 地图
		 */
		public void initSpace(int[][] matrix) {
			//拷贝地图
			grid = matrix;
			//地图的N行
			N = grid.length;
			//地图的M列
			M = grid[0].length;
			//地图所有的位置数
			int all = N * M;
			//当前天花板上连接的砖块数量
			cellingAll = 0;
			//所有的天花板集合
			cellingSet = new boolean[all];
			//所有节点的父节点
			parents = new int[all];
			//所有节点的集合大小
			setSize = new int[all];
			//辅助数组
			stack = new int[all];
			//遍历地图
			for (int row = 0; row < N; row++) {
				for (int col = 0; col < M; col++) {
					//获取N行M列的位置所在编号
					int index = row * M + col;
					//设置当前节点的父节点
					parents[index] = index;
					//设置当前节点集合的大小
					setSize[index] = 1;
					//如果所在的行数是第0行
					if (row == 0) {
						//当前节点所在的集合是天花板集合
						cellingSet[index] = true;
						//此时挂载在天花板上的砖块数量++
						cellingAll++;
					}
				}
			}
		}

		/**
		 * 初始化连接
		 */
		public void initConnect() {
			//遍历地图，并且上下左右都进行砖块的连接
			for (int row = 0; row < N; row++) {
				for (int col = 0; col < M; col++) {
					union(row, col, row - 1, col);
					union(row, col, row + 1, col);
					union(row, col, row, col - 1);
					union(row, col, row, col + 1);
				}
			}
		}

		/**
		 * 建立连接
		 *
		 * @param row1 横坐标
		 * @param col1 纵坐标
		 * @param row2 横坐标
		 * @param col2 纵坐标
		 */
		public void union(int row1, int col1, int row2, int col2) {
			//检查两个坐标的有效性
			if (valid(row1, col1) && valid(row2, col2)) {
				//找到两个坐标的节点所在集合的头节点
				int father1 = find(row1, col1);
				int father2 = find(row2, col2);
				//判断两个集合的头节点是否一样
				//如果不一样
				if (father1 != father2) {
					//集合1和集合2的大小
					int size1 = setSize[father1];
					int size2 = setSize[father2];
					//集合1是否是天花板集合，集合2是否是天花板集合
					boolean isCellingSet1 = cellingSet[father1];
					boolean isCellingSet2 = cellingSet[father2];
					//如果集合1的大小小于等于集合2的大小
					//集合1挂载到集合2底下
					if (size1 >= size2) {
						parents[father1] = father2;
						setSize[father2] = size1 + size2;
						//如果两个集合都是天花板集合或者都不是天花板集合，关于天花板的任何属性都不需要进行修改
						//只有两个集合的其中一个是天花板集合另一个不是天花板集合，关于天花板的任何属性才会修改
						if (isCellingSet1 ^ isCellingSet2) {
							cellingSet[father2] = true;
							cellingAll += isCellingSet1 ? size2 : size1;
						}
					}
					//否则
					else {
						parents[father2] = father1;
						setSize[father1] = size1 + size2;
						//如果两个集合都是天花板集合或者都不是天花板集合，关于天花板的任何属性都不需要进行修改
						//只有两个集合的其中一个是天花板集合另一个不是天花板集合，关于天花板的任何属性才会修改
						if (isCellingSet1 ^ isCellingSet2) {
							cellingSet[father1] = true;
							cellingAll += isCellingSet2 ? size1 : size2;
						}
					}
				}
			}
		}

		/**
		 * 检验当前i行j列的位置是否有效
		 *
		 * @param row 横坐标
		 * @param col 纵坐标
		 * @return 如果有效返回true，否则返回false
		 */
		public boolean valid(int row, int col) {
			return row >= 0 && row < N && col >= 0 && col < M && grid[row][col] == 1;
		}

		/**
		 * @param row 横坐标
		 * @param col 纵坐标
		 * @return 查找当前节点所在集合的头节点并返回
		 */
		public int find(int row, int col) {
			int stackSize = 0;
			int index = row * M + col;
			while (index != parents[index]) {
				stack[stackSize++] = index;
				index = parents[index];
			}
			while (stackSize != 0) {
				parents[stack[--stackSize]] = index;
			}
			return index;
		}

		/**
		 * 手指，将grid[][]中出现2的位置修改为1
		 *
		 * @param row 横坐标
		 * @param col 纵坐标
		 * @return 返回炮弹打碎并掉落的砖块数量
		 */
		public int finger(int row, int col) {
			//将地图中的该位置修改为1
			grid[row][col] = 1;
			//获取编号
			int cur = row * M + col;
			//如果在第0行
			if (row == 0) {
				//表示是天花板集合
				cellingSet[cur] = true;
				//挂载在天花板上的砖块数量++
				cellingAll++;
			}
			//当前节点的父节点
			parents[cur] = cur;
			//当前节点所在的集合大小
			setSize[cur] = 1;
			//记录之前挂载在天花板上的砖块数量
			int pre = cellingAll;
			//上下左右进行连接
			union(row, col, row - 1, col);
			union(row, col, row + 1, col);
			union(row, col, row, col - 1);
			union(row, col, row, col + 1);
			//记录现在挂载在天花板上的砖块数量
			int now = cellingAll;
			//返回掉落的砖块
			//如果是第0行
			//假设grid[][]，炮弹恢复之前的cellingAll = 0，炮弹恢复的时候pre = cellingAll++ -> 1
			//union之后的砖块数量now = 6，所以推测出掉落的砖块数量 = now - pre -> 6 - 1 == 5
			//-  0  1  2  3
			//-0 2
			//-1 1
			//-2 1  1  1  1
			//-3
			if (row == 0) {
				return now - pre;
			}
			//否则查看之前的与现在的，没有变化，证明没有打碎并掉落砖块，否则现在的 - 之前的 - 1
			//如果不是第0行
			//假设grid[][]，炮弹恢复之前的cellingAll = 2，不是第0行，所以cellingAll不变
			//union之后的砖块数量now = 6，所以推测出掉落的砖块数量 = now - pre - 1 -> 6 - 2  - 1 == 3
			//-  0  1  2  3
			//-0 1
			//-1 1
			//-2 2  1  1  1
			//-3
			else {
				return now - pre == 0 ? 0 : now - pre - 1;
			}
		}
	}

	/**
	 * int[][] grid 表示砖块
	 * -   0  1  2  3  4  5   <- 天花板
	 * -1  1  0  0  1  0  0
	 * -2  1  0  0  1  0  0
	 * -3  1  1  0  1  1  1
	 * -4  0  0  1  0  0  1
	 * int[][] hits 表示炮弹
	 * [1, 0]表示在grid[1][0]的位置上发射一发炮弹，如果该位置上有砖块，将其打碎
	 * -   0  1  2  3  4  5   <- 天花板
	 * -1  1  0  0  1  0  0
	 * -2  0  0  0  1  0  0
	 * -3  0  0  0  1  1  1
	 * -4  0  0  1  0  0  1
	 * 此时grid[3][0]和grid[3][1]的砖块因为grid[1][0]的砖块被打碎，没有了粘性，所以会掉下来，2块砖
	 *
	 * @param grid 二维网格，0行是天花板，从上往下，砖块按照上下左右的粘性依次挂载到天花板上
	 * @param hits 炮弹发射在grid[][]上的位置数组，按照索引的顺序依次发射炮弹
	 * @return 返回每次在grid[i][j]位置上打碎砖块之后落下来的砖块数量形成的数组
	 */
	public int[] hitBricks(int[][] grid, int[][] hits) {
		/*for (int i = 0; i < hits.length; i++) {
			if (grid[hits[i][0]][hits[i][1]] == 1) {
				grid[hits[i][0]][hits[i][1]] = 2;
			}
		}*/
		for (int[] hit : hits) {
			if (grid[hit[0]][hit[1]] == 1) {
				grid[hit[0]][hit[1]] = 2;
			}
		}
		//创建并查集
		UnionFind unionFind = new UnionFind(grid);
		int[] ans = new int[hits.length];
		//逆序遍历
		for (int i = hits.length - 1; i >= 0; i--) {
			if (grid[hits[i][0]][hits[i][1]] == 2) {
				ans[i] = unionFind.finger(hits[i][0], hits[i][1]);
			}
		}
		return ans;
	}
}
