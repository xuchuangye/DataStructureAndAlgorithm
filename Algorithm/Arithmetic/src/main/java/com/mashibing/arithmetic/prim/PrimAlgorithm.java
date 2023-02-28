package com.mashibing.arithmetic.prim;

/**
 * 普里姆算法 -> 解决修路的问题
 *
 * 普里姆算法的思路分析：
 * 1.从<A>顶点开始处理  ======> <A,G> 2
 * A-C [7] A-G[2] A-B[5] => A-G的路径最小，路径为2
 *
 * 2. <A,G> 开始 , 将A 和 G 顶点和他们相邻的还没有访问的顶点进行处理 =》<A,G,B>
 * A-C[7] A-B[5]  G-B[3] G-E[4] G-F[6] -> G-B的路径最小，路径为3
 *
 * 3. <A,G,B> 开始，将A,G,B 顶点 和他们相邻的还没有访问的顶点进行处理=><A,G,B,E>
 * A-C[7] G-E[4] G-F[6] B-D[9] -> G-E[4]的路径最小，路径为4
 * .....
 * 4.{A,G,B,E}->F//第4次大循环 ，  对应 边<E,F> 权值：5
 * 5.{A,G,B,E,F}->D//第5次大循环 ， 对应 边<F,D> 权值：4
 * 6. {A,G,B,E,F,D}->C//第6次大循环 ， 对应 边<A,C> 权值：7 ===> <A,G,B,E,F,D,C>
 * @author xcy
 * @date 2022/4/2 - 10:30
 */
public class PrimAlgorithm {
	private static final int INF = Integer.MAX_VALUE;
	public static void main(String[] args) {
		//图的各个顶点
		String[] data = {"A", "B", "C", "D", "E", "F", "G"};
		//图的顶点个数
		int vertexCount = data.length;
		//weight表示图中顶点之间的连通关系图，INF表示不连通
		int[][] weight = new int[][]{
				//A     B     C    D    E    F    G
		/*A*/{INF, 5, 7, INF, INF, INF, 2},
		/*B*/{5, INF, INF, 9, INF, INF, 3},
		/*C*/{7, INF, INF, INF, 8, INF, INF},
		/*D*/{INF, 9, INF, INF, INF, 4, INF},
		/*E*/{INF, INF, 8, INF, INF, 5, 4},
		/*F*/{INF, INF, INF, 4, 5, INF, 6},
		/*G*/{2, 3, INF, INF, 4, 6, INF},
		};
		Graph graph = new Graph(vertexCount);

		//最小生成树
		MinimumSpanningTree minimumSpanningTree = new MinimumSpanningTree();
		//创建最小生成树
		minimumSpanningTree.createMinimumSpanningTree(graph, vertexCount, data, weight);
		//展示图中顶点之间的连通关系图
		minimumSpanningTree.showWeight(graph);

		//测试普里姆算法，计算出最小生成树，也就是修路的最短路径
		minimumSpanningTree.prim(graph, 0);//0表示从A顶点开始计算
	}
}
