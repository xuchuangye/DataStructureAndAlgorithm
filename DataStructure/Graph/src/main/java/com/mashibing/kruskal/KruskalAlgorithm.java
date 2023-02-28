package com.mashibing.kruskal;

/**
 * 克鲁斯卡尔算法
 *
 * @author xcy
 * @date 2022/4/2 - 16:50
 */
public class KruskalAlgorithm {
	//表示图中的两个顶点之间不连通
	private static final int INF = Integer.MAX_VALUE;

	public static void main(String[] args) {
		String[] data = {"A", "B", "C", "D", "E", "F", "G"};
		int[][] weight = new int[][]{
				/*A*//*B*//*C*//*D*//*E*//*F*//*G*/
		/*A*/	{  0,  12, INF, INF, INF,  16,  14},
		/*B*/	{ 12,   0,  10, INF, INF,   7, INF},
		/*C*/	{INF,  10,   0,   3,   5,   6, INF},
		/*D*/	{INF, INF,   3,   0,   4, INF, INF},
		/*E*/	{INF, INF,   5,   4,   0,   2,   8},
		/*F*/	{ 16,   7,   6, INF,   2,   0,   9},
		/*G*/	{ 14, INF, INF, INF,   8,   9,   0},
		};
		Graph graph = new Graph(data, weight);

		//展示图中顶点的连通关系图
		/*graph.showKruskalGraph();
		System.out.println("图中边的数目是：" + graph.getEdgeNum());

		Edge[] edges = graph.getEdges();
		System.out.println("排序之前");
		System.out.println(Arrays.toString(edges));

		graph.sortEdge(edges);
		System.out.println("排序之后");
		System.out.println(Arrays.toString(edges));*/

		System.out.println("最终最小生成树是");
		graph.kruskalAlgorithm();
	}
}
