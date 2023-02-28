package com.mashibing.unionset;

/**
 * 并查集 --> 使用数组的方式实现，针对char[][] board进行了改进
 *
 * @author xcy
 * @date 2022/5/3 - 15:32
 */
public class UnionFindWithCharBoard {
	/**
	 *
	 */
	private final int[] parents;
	/**
	 * 集合的大小
	 */
	private final int[] size;
	private final int[] stack;
	private int sets;
	private final int COL;

	public UnionFindWithCharBoard(char[][] board) {
		int ROW = board.length;
		COL = board[0].length;
		int length = ROW * COL;
		parents = new int[length];
		size = new int[length];
		stack = new int[length];
		sets = 0;

		for (int row = 0; row < ROW; row++) {
			for (int col = 0; col < COL; col++) {
				if (board[row][col] == '1') {
					int i = index(row, col);
					parents[i] = i;
					size[i] = 1;
					sets++;
				}
			}
		}
	}

	/**
	 * 原始位置 --> 索引
	 * 将处于行位置和列位置上的值转换为一维数组parents、size、temp的绝对索引位置
	 * int[][] = {
	 * -    0  1  2  3  4
	 * - 0{ 0  1  2  3  4  },
	 * - 1{ 5  6  7  8  9  },
	 * - 2{ 10 11 12 13 14 },
	 * - 3{ 15 16 17 18 19 },
	 * }
	 * int[] = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19}
	 * @param row 行位置上的索引
	 * @param col 列位置上的索引
	 * @return 返回将二维数组转换成一维数组的绝对位置
	 */
	public int index(int row, int col) {
		return row * COL + col;
	}

	/**
	 * 查找并返回当前索引上的代表节点
	 *
	 * @param index 当前已经转化过的索引，也就是调用index()之后的索引
	 * @return 返回当前索引上的代表节点
	 */
	public int findHead(int index) {
		int size = 0;
		//i索引上的值不是自己，证明没有找到代表节点
		while (index != parents[index]) {
			//
			stack[size++] = index;
			//i来到自己的父节点的位置
			index = parents[index];
		}
		//当退出上述while循环时，i一定是代表节点
		//遍历temp数组中的每一个元素，将所有的元素的代表节点都设置为i
		for (size--; size >= 0; size--) {
			parents[stack[size]] = index;
		}
		//返回代表节点
		return index;
	}

	/**
	 * 将(i, j)位置上的'1'和(a, b)位置上的'1'进行合并
	 *
	 * @param i i横坐标
	 * @param j j纵坐标
	 * @param a a横坐标
	 * @param b b纵坐标
	 */
	public void union(int i, int j, int a, int b) {
		//根据原始位置获取绝对索引
		int aindex = index(i, j);
		int bindex = index(a, b);
		//根据绝对索引获取各自的代表节点
		int aHead = findHead(aindex);
		int bHead = findHead(bindex);
		//判断是否是同一个代表节点
		if (aHead != bHead) {
			//判断各自代表节点所在的集合的长度
			if (size[aHead] >= size[bHead]) {
				//长的集合加上短的集合
				size[aHead] += size[bHead];
				//设置短的集合的代表节点的父节点是长的集合的代表节点
				parents[bHead] = aHead;
			} else {
				size[bHead] += size[aHead];
				parents[aHead] = bHead;
			}
			//集合总个数++
			sets--;
		}
	}

	/**
	 * 返回集合的总个数
	 *
	 * @return 返回集合的总个数
	 */
	public int sets() {
		//返回集合的总个数
		return this.sets;
	}
}
