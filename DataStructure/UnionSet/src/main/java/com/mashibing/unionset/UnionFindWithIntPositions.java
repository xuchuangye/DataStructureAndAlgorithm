package com.mashibing.unionset;

/**
 * 并查集 --> 使用数组的方式实现，针对int m, int n,int[][] positions进行了改进
 *
 * @author xcy
 * @date 2022/5/3 - 15:32
 */
public class UnionFindWithIntPositions {
	/**
	 * parents数组的索引表示当前变量(节点)，索引对应的值表示当前节点的父节点
	 */
	private final int[] parents;
	/**
	 * size数组的索引表示当前变量(代表节点)，索引对应的值表示当前代表节点所在的集合的节点个数
	 */
	private final int[] size;
	/**
	 * 辅助数组
	 */
	private final int[] temp;
	/**
	 * 行
	 */
	private final int ROW;
	/**
	 * 列
	 */
	private final int COL;
	/**
	 * 集合的总个数
	 */
	private int sets;

	/**
	 * 通过参数m和n初始化类中的属性
	 *
	 * @param m 行数
	 * @param n 列数
	 */
	public UnionFindWithIntPositions(int m, int n) {
		ROW = m;
		COL = n;
		int length = ROW * COL;
		parents = new int[length];
		size = new int[length];
		temp = new int[length];
		sets = 0;
	}

	/**
	 * 将原始位置转换为索引，
	 * 例如将原始位置(10, 5) -> 转换为一维数组parents、size、temp中的索引：10 * COL + 5
	 *
	 * @param row 行位置
	 * @param col 列位置
	 * @return 返回索引
	 */
	public int index(int row, int col) {
		return row * COL + col;
	}

	/**
	 * 返回索引i(当前节点)的代表节点，并且剪短代表节点所在的集合长度，提升效率
	 *
	 * @param i 当前索引(当前节点)
	 * @return 返回索引i的代表节点
	 */
	public int findHead(int i) {
		//辅助数组的索引
		int index = 0;
		//如果当前节点不是自己的代表节点
		while (i != parents[i]) {
			//先添加到辅助数组中，类似于Stack的功能
			temp[index++] = i;
			//当前节点来到父节点的位置
			i = parents[i];
		}
		///依次遍历辅助数组temp中的每一个节点
		for (index --; index >= 0; index--) {
			int element = temp[index];
			//将每一个元素的父节点都设置为i
			parents[element] = i;
		}
		//返回代表节点
		return i;
	}

	/**
	 * 岛屿之间的连接
	 * @param row1 第一个原始位置的行
	 * @param col1 第一个原始位置的列
	 * @param row2 第二个原始位置的行
	 * @param col2 第二个原始位置的列
	 */
	public void union(int row1, int col1, int row2, int col2) {
		//边界问题
		if (row1 < 0 || row1 == ROW || col1 < 0 || col1 == COL || row2 < 0 || row2 == ROW || col2 < 0 || col2 == COL) {
			return;
		}
		//将原始位置转换为索引
		int aIndex = index(row1, col1);
		int bIndex = index(row2, col2);
		//size数组中默认所有的元素初始化为0，表示没有进行过连接
		//所以只要有其中的一个代表节点的集合个数为0，说明没有初始化，也就没有必要进行连接，直接返回
		if (size[aIndex] == 0 || size[bIndex] == 0) {
			return;
		}
		//查找到各自的代表节点
		int aHead = findHead(aIndex);
		int bHead = findHead(bIndex);
		//如果代表节点不相同，说明两个节点不在同一个集合
		if (aHead != bHead) {
			//短的集合挂载到长的集合上
			if (size[aHead] >= size[bHead]) {
				size[aHead] += size[bHead];
				//设置短的集合的代表节点的父节点为长的集合的代表节点
				parents[bHead] = aHead;
			} else {
				//反之也是如此
				size[bHead] += size[aHead];
				parents[aHead] = bHead;
			}
			//两个集合合并为1个，sets--
			sets--;
		}
	}

	/**
	 * 岛屿连接之后需要进行合并
	 * @param row 原始位置的行
	 * @param col 原始位置的列
	 * @return 返回当前连接的集合总个数，也就是岛屿的数量
	 */
	public int connect(int row, int col) {
		//将原始位置转换为索引
		int index = index(row, col);
		//查看size数组该索引的值是否为0，如果为0，表示该位置还没有初始化
		if (size[index] == 0) {
			//该节点的父节点指向自己
			parents[index] = index;
			//size数组该索引的值设置为1，表示初始化为1
			size[index] = 1;
			//集合总个数++
			sets++;
			//向左合并
			union(row - 1, col, row, col);
			//向右合并
			union(row + 1, col, row, col);
			//向下合并
			union(row, col - 1, row, col);
			//向上合并
			union(row, col + 1, row, col);
		}
		//返回当前连接的集合总个数
		return sets;
	}
}
