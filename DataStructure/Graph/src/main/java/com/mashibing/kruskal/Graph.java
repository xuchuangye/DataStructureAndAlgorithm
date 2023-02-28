package com.mashibing.kruskal;

import lombok.Data;

/**
 * 图
 *
 * @author xcy
 * @date 2022/4/2 - 16:43
 */
@Data
public class Graph {
	public static final int INF = Integer.MAX_VALUE;
	/**
	 * 图中边的数量
	 */
	private int edgeNum;

	/**
	 * 图中顶点个数
	 */
	private int vertexCount;

	/**
	 * 顶点名称数组
	 */
	private String[] data;

	/**
	 * 图中顶点之间的连通关系图
	 */
	private int[][] weight;

	public Graph(String[] data, int[][] weight) {
		//初始化顶点个数
		this.vertexCount = data.length;

		//初始化顶点数组
		this.data = new String[vertexCount];
		for (int i = 0; i < data.length; i++) {
			this.data[i] = data[i];
		}

		//初始化顶点连通关系图
		this.weight = new int[vertexCount][vertexCount];
		for (int i = 0; i < vertexCount; i++) {
			for (int j = 0; j < vertexCount; j++) {
				this.weight[i][j] = weight[i][j];
			}
		}

		//初始化图中的边数
		for (int i = 0; i < vertexCount; i++) {
			for (int j = i + 1; j < vertexCount; j++) {
				if (this.weight[i][j] != INF) {
					edgeNum++;
				}
			}
		}
	}

	/**
	 * 展示图中的顶点连通关系图
	 */
	public void showKruskalGraph() {
		for (int i = 0; i < vertexCount; i++) {
			for (int j = 0; j < vertexCount; j++) {
				System.out.printf("%12d", this.weight[i][j]);
			}
			System.out.println();
		}
	}

	/**
	 * 使用冒泡排序对图中的边进行从小到大的排序
	 *
	 * @param edges 图中边的数组
	 */
	public void sortEdge(Edge[] edges) {
		for (int i = 0; i < edges.length - 1; i++) {
			for (int j = 0; j < edges.length - 1 - i; j++) {
				if (edges[j].weight > edges[j + 1].weight) {
					Edge temp = edges[j];
					edges[j] = edges[j + 1];
					edges[j + 1] = temp;
				}
			}
		}
	}

	/**
	 * 返回指定顶点名称的索引
	 *
	 * @param vertex 指定顶点
	 * @return 返回指定顶点所在的索引
	 */
	public int getVertexIndex(String vertex) {
		for (int i = 0; i < data.length; i++) {
			if (vertex.equals(data[i])) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * 根据邻接矩阵获取图中所有边的信息数组集合
	 * @return 返回图中所有的边的信息
	 */
	public Edge[] getEdges() {
		int index = 0;
		Edge[] edges = new Edge[edgeNum];
		for (int i = 0; i < data.length; i++) {
			for (int j = i + 1; j < data.length; j++) {
				if (weight[i][j] != INF) {
					edges[index++] = new Edge(data[i], data[j], weight[i][j]);
				}
			}
		}
		return edges;
	}

	/**
	 * 返回指定顶点i的最后一个顶点，如果没有最后一个顶点，那么就返回i本身
	 * @param ends 每个顶点的终点的数组集合
	 * @param i 指定顶点
	 * @return 返回指定顶点的终点
	 */
	public int getEnd(int[] ends, int i) {
		while (ends[i] != 0) {
			i = ends[i];
		}

		return i;
	}

	/**
	 *
	 */
	public void kruskalAlgorithm() {
		//辅助最终结果的数组的指针
		int index = 0;
		//保存已知最小生成树
		int[] ends = new int[edgeNum];
		//最终的最小生成树
		Edge[] results = new Edge[edgeNum];
		//获取所有边的数组结合
		Edge[] edges = getEdges();
		//System.out.println(Arrays.toString(edges));
		//对已知所有的边进行从小到大的排序
		sortEdge(edges);
		//System.out.println(Arrays.toString(edges));

		for (int i = 0; i < edgeNum; i++) {
			//获取第i条边的第一个顶点
			int p1 = getVertexIndex(edges[i].start);
			//获取第i条边的第二个顶点
			int p2 = getVertexIndex(edges[i].end);

			//获取第p1这个顶点在已知最小生成树中的终点
			int m = getEnd(ends, p1);
			//获取第p2这个顶点在已知最小生成树中的终点
			int n = getEnd(ends, p2);
			//表示如果没有产生回路，那么就添加到results中，如果产生回路
			if (m != n) {
				//ends[] = {0,0,0,0,0,0,0,0,0,0,0,0}
				//<E,F> E的索引是4，F的索引是5，那么在ends数组中就表示为在ends索引为4的位置上设置的值为5
				//也就是ends[] = {0,0,0,0,5,0,0,0,0,0,0,0}
				//记录m在已知最小生成树中的终点n
				ends[m] = n;
				//满足没有产生回路的条件，可以将当前第i这条边edges[i]添加到results最终最小生成树中
				results[index] = edges[i];
				//每添加一条边，index加1
				index++;
			}
		}

		//总共12条边，已知7个顶点，至少6条边，剩余6条边为null，影响展示效果，所以需要进行修改
		//之打印输出记录权值的边
		for (int i = 0; i < index; i++) {
			System.out.println(results[i]);
		}
	}
}
