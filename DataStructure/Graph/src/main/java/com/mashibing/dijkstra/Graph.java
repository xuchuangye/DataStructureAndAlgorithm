package com.mashibing.dijkstra;

import lombok.Data;

/**
 * 图
 *
 * @author xcy
 * @date 2022/4/3 - 10:35
 */
@Data
public class Graph {
	public static final int N = 65535;
	/**
	 * 已访问的顶点的集合类
	 */
	private VisitedVertex visitedVertex;
	/**
	 * 图中顶点个数
	 */
	private int vertexCount;

	/**
	 * 图中的边数
	 */
	private int edgeCount;

	/**
	 * 顶点名称的数组，比如{"A","B","C","D","E","F","G"}
	 */
	private String[] vertex;

	/**
	 * 图中的顶点之间的连通关系图，也就是邻接矩阵
	 */
	private int[][] matrix;

	public Graph(String[] vertex, int[][] matrix) {
		this.vertexCount = vertex.length;
		this.vertex = vertex;
		this.matrix = matrix;
	}

	/**
	 * 展示图
	 */
	public void showGraph() {
		for (int i = 0; i < vertexCount; i++) {
			for (int j = 0; j < vertexCount; j++) {
				System.out.printf("%6d", this.matrix[i][j]);
			}
			System.out.println();
		}
	}

	/**
	 * 迪杰斯特拉算法
	 *
	 * @param index 从指定的顶点开始
	 */
	public void dijkstraAlgorithm(int index) {
		visitedVertex = new VisitedVertex(vertex.length, index);
		//更新当前index的顶点到能够连通的顶点的距离和顶点的前驱顶点
		update(index);

		for (int i = 1; i < vertexCount; i++) {
			//更新并返回新的访问顶点
			index = visitedVertex.updateArray();
			//再次更新当前index的顶点到能够连通的顶点的距离和顶点的前驱顶点
			update(index);
		}
	}

	/**
	 * 更新当前index顶点的能够连通的顶点的距离和顶点的前驱顶点
	 *
	 * @param index
	 */
	public void update(int index) {
		int weight = 0;
		for (int i = 0; i < matrix[index].length; i++) {
			//记录初始顶点到当前index顶点的距离 + 当前index顶点到i顶点的距离 的和
			weight = visitedVertex.getDis(index) + matrix[index][i];
			//判断当前i顶点是否被访问过，并且初始顶点到当前i顶点的距离大于weight之和，就表示连通并且需要更新最小距离
			if (!visitedVertex.isVisited(i) && visitedVertex.getDis(i) > weight) {
				//更新当前顶点的距离和当前顶点的前驱顶点
				visitedVertex.updateDis(i, weight);
				visitedVertex.updatePre(i, index);
			}
		}
	}

	/**
	 * 展示结果
	 */
	public void showDijkstraResult() {
		visitedVertex.show();
	}
}
