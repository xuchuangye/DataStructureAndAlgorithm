package com.mashibing.day18;

/**
 * 在给定的二维二进制数组A中，存在两座岛。（岛是由四面相连的1形成的一个最大组。）
 * 现在，我们可以将0变为1，以使两座岛连接起来，变成一座岛。
 * 返回必须翻转的 0 的最小数目。（可以保证答案至少是1）
 * <p>
 * Leetcode题目：https://leetcode.com/problems/shortest-bridge/
 *
 * @author xcy
 * @date 2022/8/8 - 14:47
 */
public class Code02_ShortestBridge {
	public static void main(String[] args) {

	}

	public static int shortestBridge(int[][] matrix) {
		int N = matrix.length;
		int M = matrix[0].length;
		int all = N * M;

		//curs的元素值表示所有出现1的位置
		int[] curs = new int[all];
		int[] nexts = new int[all];
		//题目要求的就是两个岛，所以record[][] = new int[2][all]
		//record的索引表示二维数组坐标转换成一维数组的下标
		int[][] record = new int[2][all];

		//当前第几个岛，一开始为0
		int island = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				if (matrix[i][j] == 1) {
					//将当前的岛，也就是连成一片的1都修改为2
					//并且将matrix二维数组的坐标(i, j)转换成一维数组的索引(i * M + j)收集到curs(int[]的队列)中
					//然后将matrix二维数组转换为一维数组record[]，record[]中i * M + j的位置都是1
					//记录收集到不同的1的个数，也就是当前岛连成一片1的个数
					int queueSize = infect(matrix, i, j, N, M, curs, 0, record[island]);
					//当前层数，一开始在第一层
					int V = 1;
					while (queueSize != 0) {
						V++;
						//根据curs[]中的标记1的位置和record[]中的标记的距离向外部广播
						//当前岛到达自己的距离为1，nexts表示下一层没有走过的位置修改为当前层数
						queueSize = bfs(N, M, all, V, curs, queueSize, nexts, record[island]);
						//空间进行复用，所以使用temp辅助数组交换curs[]和nexts[]
						int[] temp = curs;
						curs = nexts;
						nexts = temp;
					}
					island++;
				}
			}
		}
		int min = Integer.MAX_VALUE;
		for (int i = 0; i < all; i++) {
			min = Math.min(min, record[0][i] + record[1][i]);
		}
		return min - 3;
	}

	/**
	 * matrix[i][j]
	 * -  0  1  2  3  4
	 * -0 1  1  0  0  0
	 * -1 0  0  0  0  0
	 * -2 0  0  0  0  0
	 * -3 0  0  0  0  0
	 * -4 0  0  0  1  1
	 * <p>
	 * 创建两个辅助数组，例如：
	 * arr1[]：
	 * 左侧位置的两个1组成的岛，上下左右依次广播
	 * 1  1  2  3  4
	 * 2  2  3  4  5
	 * arr2[]：
	 * 右侧位置的两个1组成的岛，上下左右依次广播
	 * 5  4  3  2  2
	 * 4  3  2  1  1
	 * 对应的位置依次相加，
	 * 6  5  5  5  6
	 * 6  5  5  5  6
	 * 数值最小的，并且减去3就是两个岛连通至少需要的数目，例如：5 - 3
	 * <p>
	 * -  0  1  2
	 * -0 1  1  0
	 * -1 1  0  0
	 * -2 0  0  0
	 * matrix[0][0] = 1, curs[0] = 0 * 3 + 0, record[0 * 3 + 0] = 1
	 * matrix[0][1] = 1, curs[1] = 0 * 3 + 1, record[0 * 3 + 1] = 1
	 * matrix[1][0] = 1, curs[2] = 1 * 3 + 0, record[1 * 3 + 0] = 1
	 *
	 * @param matrix 原始二维数组
	 * @param i      横坐标
	 * @param j      纵坐标
	 * @param N      N行
	 * @param M      M列
	 * @param curs   所有收集到的不同的1，int[]队列，将位置记录到队列中，可以方便第二层的广播，依此类推
	 * @param index  收集到不同的1的个数
	 * @param record 将原始的二维坐标matrix[i][j]的1转换成一维record[i * M + j]的1
	 * @return 返回收集到不同的1的个数，也就是队列的长度，队列成长到什么位置
	 */
	public static int infect(int[][] matrix, int i, int j, int N, int M, int[] curs, int index, int[] record) {
		//如果出现越界或者matrix[i][j] != 1，返回index
		if (i < 0 || i == N || j < 0 || j == M || matrix[i][j] != 1) {
			return index;
		}
		//i和j都没有越界
		//matrix[i][j] == 1

		//必须将matrix[i][j]修改为2，否则会一直递归下去
		//而且将matrix[i][j]修改为2之后不会回退
		matrix[i][j] = 2;
		//二维数组的坐标转换为一维数组的索引
		int cur = i * M + j;
		//将二维数组中的1记录一维数组的1
		record[cur] = 1;
		//收集不同的1，并且记录位置
		curs[index++] = cur;
		//上下左右收集所有连成一片的1
		index = infect(matrix, i + 1, j, N, M, curs, index, record);
		index = infect(matrix, i - 1, j, N, M, curs, index, record);
		index = infect(matrix, i, j + 1, N, M, curs, index, record);
		index = infect(matrix, i, j - 1, N, M, curs, index, record);
		return index;
	}

	/**
	 * 宽度优先遍历
	 *
	 * @param N      N行
	 * @param M      M列
	 * @param all    matrix[][]所有的位置
	 * @param V      当前层数
	 * @param curs   元素值表示二维数组坐标转换成的一维数组索引
	 * @param size   当前这片岛的1的个数
	 * @param nexts  下一层的辅助数组
	 * @param record 当前的岛到自己的距离
	 * @return 返回下一层的辅助数组的长度
	 */
	public static int bfs(int N, int M, int all, int V, int[] curs, int size, int[] nexts, int[] record) {
		int nextsIndex = 0;
		for (int i = 0; i < size; i++) {
			//往上走，并且不是第一行
			int up = curs[i] < M ? -1 : curs[i] - M;
			//往下走，并且不是最后一行
			int down = curs[i] + M >= all ? -1 : curs[i] + M;
			//往左走，并且不是第一列
			int left = curs[i] % M == 0 ? -1 : curs[i] - 1;
			//往右走，并且不是最后一列
			int right = curs[i] % M == M - 1 ? -1 : curs[i] + 1;

			if (up != -1 && record[up] == 0) {
				record[up] = V;
				nexts[nextsIndex++] = up;
			}

			if (down != -1 && record[down] == 0) {
				record[down] = V;
				nexts[nextsIndex++] = down;
			}

			if (left != -1 && record[left] == 0) {
				record[left] = V;
				nexts[nextsIndex++] = left;
			}

			if (right != -1 && record[right] == 0) {
				record[right] = V;
				nexts[nextsIndex++] = right;
			}
		}
		return nextsIndex;
	}
}
