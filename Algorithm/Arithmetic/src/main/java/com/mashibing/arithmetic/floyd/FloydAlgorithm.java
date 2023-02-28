package com.mashibing.arithmetic.floyd;

/**
 * 弗洛伊德算法
 *
 * 弗洛伊德算法的核心思想：
 * 判断起始顶点i到中间顶点k的距离 + 中间顶点k到目标顶点j的距离之和 与 起始顶点i直达目标顶点j的距离 谁的距离最短，获取最短距离
 *
 * 弗洛伊德算法与迪杰斯特拉算法的区别：
 * 1）迪杰斯特拉算法是指定一个初始访问顶点，计算该顶点与其他顶点的最短距离/路径
 * 2）弗洛伊德算法是每一个顶点都是初始访问顶点，计算每个顶点与其他顶点的最短距离/路径
 *
 * @author xcy
 * @date 2022/4/4 - 9:52
 */
public class FloydAlgorithm {
	/**
	 * 表示图中的顶点之间不是直接连通的关系
	 */
	public static final int N = 65535;

	public static void main(String[] args) {
		//顶点数组
		String[] matrix = {"A", "B", "C", "D", "E", "F", "G"};
		//图中 顶点之间的连通关系图，也就是邻接矩阵
		int[][] dis = new int[][]{
				  /*A*//*B*//*C*//*D*//*E*//*F*//*G*/
			/*A*/{  0,   5,   7,   N,   N,   N,   2},
			/*B*/{  5,   0,   N,   9,   N,   N,   3},
			/*C*/{  7,   N,   0,   N,   8,   N,   N},
			/*D*/{  N,   9,   N,   0,   N,   4,   N},
			/*E*/{  N,   N,   8,   N,   0,   5,   4},
			/*F*/{  N,   N,   N,   4,   5,   0,   6},
			/*G*/{  2,   3,   N,   N,   4,   6,   0},
		};
		//创建图对象
		Graph graph = new Graph(matrix.length, matrix, dis);

		//测试弗洛伊德算法
		graph.floydAlgorithm();

		//展示最小生成树
		graph.showGraph();
	}
}
