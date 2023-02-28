package com.mashibing.prim;

/**
 * 图
 *
 * @author xcy
 * @date 2022/4/2 - 10:31
 */
public class Graph {
	/**
	 * 顶点个数
	 */
	int vertexCount;
	/**
	 * 顶点的名称数组，{"A","B","C","D","E","F","G"}
	 */
	String[] data;

	/**
	 * 二维数组，表示顶点之间的连通关系图
	 */
	int[][] weight;

	public Graph(int vertexCount) {
		this.vertexCount = vertexCount;
		this.data = new String[vertexCount];
		this.weight = new int[vertexCount][vertexCount];
	}
}
