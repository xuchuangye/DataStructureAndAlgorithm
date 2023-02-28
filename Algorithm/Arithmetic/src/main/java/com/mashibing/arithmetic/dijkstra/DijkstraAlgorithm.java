package com.mashibing.arithmetic.dijkstra;

/**
 * 迪杰斯特拉算法
 * <p>
 * 1、解决最短路径问题
 * 2、底层基于图的广度优先算法
 * 3、VisitedVertex已访问顶点的类
 * already_arr[]记录被访问过的顶点，1表示访问过，0表示未访问，会动态更新
 * dis[]记录初始顶点到图中各个顶点的距离的数组
 * 比如：以G为出发点，记录G到其他顶点的距离，{A(2),B(3),C(N),D(N),E(4),F(6),G(N)}，表示G->A的边(权值)距离是2，N表示G与该顶点不连通
 * 会动态更新
 * pre_visited记录各个顶点的前驱节点的数组，每一个索引对应的值为前一个顶点的索引，
 * 比如：{6,6,0,0,6,6,0}表示以G为出发点，能够和ABEF连通，那么索引为0(顶点A)，1(顶点B)，4(顶点E)，5(顶点F)的前一个顶点是6，也就是顶点G
 * 会动态更新
 *
 * @author xcy
 * @date 2022/4/3 - 9:20
 */
public class DijkstraAlgorithm {
	/**
	 * 表示图中顶点之间的不连通
	 */
	public static final int N = 65535;
	//顶点数组
	private static final String[] vertex = {"A", "B", "C", "D", "E", "F", "G"};

	//创建邻接矩阵
	private static final int[][] matrix = new int[][]{
					/*A*//*B*//*C*//*D*//*E*//*F*//*G*/
			/*A*/	{ N,   5,   7,   N,   N,   N,   2},
			/*B*/	{ 5,   N,   N,   9,   N,   N,   3},
			/*C*/	{ 7,   N,   N,   N,   8,   N,   N},
			/*D*/	{ N,   9,   N,   N,   N,   4,   N},
			/*E*/	{ N,   N,   8,   N,   N,   5,   4},
			/*F*/	{ N,   N,   N,   4,   5,   N,   6},
			/*G*/	{ 2,   3,   N,   N,   4,   6,   N},
	};
	public static void main(String[] args) {
		//创建图对象
		Graph graph = new Graph(vertex, matrix);
		//展示图
		graph.showGraph();
		//迪杰斯特拉算法
		graph.dijkstraAlgorithm(2);

		graph.showDijkstraResult();
	}
}
