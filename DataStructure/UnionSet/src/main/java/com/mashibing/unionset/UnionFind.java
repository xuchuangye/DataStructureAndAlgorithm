package com.mashibing.unionset;

/**
 * 并查集 --> 使用数组的方式实现
 *
 * 并查集：支持合并和查询的结构
 * @author xcy
 * @date 2022/5/3 - 9:19
 */
public class UnionFind {

	/**
	 * 数组的索引：表示当前节点与数组的元素值：表示当前节点的父节点之间的对应关系数组
	 */
	private final int[] parents;
	/**
	 * 数组的索引：表示代表节点与数组的元素值：表示代表节点所在的集合的节点个数之间的对应关系数组
	 */
	private final int[] size;
	/**
	 * 辅助数组
	 */
	private final int[] temp;

	/**
	 * 集合的个数
	 */
	private int sets;

	public UnionFind(int N) {
		parents = new int[N];
		size = new int[N];
		temp = new int[N];

		sets = N;
		for (int i = 0; i < N; i++) {
			//变量的索引与元素的值
			parents[i] = i;
			size[i] = 1;
		}
	}

	/**
	 * 查找并返回该变量的代表节点
	 * @param i 该变量
	 * @return 返回该变量的代表节点
	 */
	public int findHead(int i) {
		int index = 0;
		//如果i的代表节点不是自己，那么就记录到temp辅助数组中，i来到自己的父节点的位置
		//直到i是自己的代表节点时，退出循环
		while (i != parents[i]) {
			temp[index++] = i;
			i = parents[i];
		}
		//当退出上述while循环时，i已经是代表节点
		//该循环是为了剪短链的路径
		for (index--; index >= 0; index--) {
			//取出辅助数组中的每一个元素
			int element = temp[index];
			//将辅助数组的每一个元素的代表节点都设置为i
			parents[element] = i;
		}
		return i;
	}

	/**
	 * 将变量a和变量b添加到一个集合中
	 * @param a 变量a
	 * @param b 变量b
	 */
	public void union(int a, int b) {
		//变量a的代表节点
		int aHead = findHead(a);
		//变量b的代表节点
		int bHead = findHead(b);
		//变量a和变量b不在同一个集合中
		if (aHead != bHead) {
			//判断哪一个集合的长度长，哪一个集合的长度短，短的集合挂载到长的集合上
			if (size[aHead] >= size[bHead]) {
				size[aHead] += size[bHead];
				parents[bHead] = aHead;
			}else {
				size[bHead] += size[aHead];
				parents[aHead] = bHead;
			}
			//两个集合合并到一起，集合总数肯定要减1
			sets--;
		}
	}

	/**
	 * 返回集合总数
	 * @return 返回集合总数
	 */
	public int sets() {
		return this.sets;
	}
}
