package com.mashibing.arithmetic.prim;

/**
 * 最小生成树
 *
 * @author xcy
 * @date 2022/4/2 - 10:33
 */
public class MinimumSpanningTree {
	/**
	 * 创建最小生成树
	 *
	 * @param graph       图的对象
	 * @param vertexCount 顶点个数
	 * @param data        顶点名称的数组
	 * @param weight      描述顶点之间的关系图
	 */
	public void createMinimumSpanningTree(Graph graph, int vertexCount, String[] data, int[][] weight) {
		for (int i = 0; i < vertexCount; i++) {
			graph.data[i] = data[i];
			for (int j = 0; j < vertexCount; j++) {
				graph.weight[i][j] = weight[i][j];
			}
		}
	}

	/**
	 * 展示图中顶点之间的关系图
	 *
	 * @param graph 图的对象
	 */
	public void showWeight(Graph graph) {
		/*for (int[] link : graph.weight) {
			System.out.println(Arrays.toString(link));
		}*/
		for (int i = 0; i < graph.vertexCount; i++) {
			for (int j = 0; j < graph.vertexCount; j++) {
				System.out.printf("%12d",graph.weight[i][j]);
			}
			System.out.println();
		}
	}

	/**
	 * 普里姆算法计算最小生成树
	 *
	 * @param graph 图的对象
	 * @param v     表示指定的顶点开始计算
	 */
	public void prim(Graph graph, int v) {
		//最小生成树的路径总数
		int totalPath = 0;
		//记录顶点是否被访问过
		boolean[] isVisited = new boolean[graph.vertexCount];
		//从指定的顶点开始计算，那么该顶点就已经被访问过
		isVisited[v] = true;
		//记录两个顶点的索引
		int h1 = -1;
		int h2 = -1;
		//表示默认的最小路径是不连通的
		int minPath = 10000;
		//k表示边的遍历，共有graph.vertexCount个顶点，那么至少要遍历graph.vertexCount - 1次，所以k从0开始，到graph.vertexCount - 1结束
		for (int k = 0; k < graph.vertexCount - 1; k++) {
			//i表示已经被访问过的顶点
			for (int i = 0; i < graph.vertexCount; i++) {
				//j表示没有被访问过的顶点
				for (int j = 0; j < graph.vertexCount; j++) {
					//graph.weight[i][j] < minPath表示已访问的顶点和未访问的顶点之间是连通的
					if (isVisited[i] == true && isVisited[j] == false && graph.weight[i][j] < minPath) {

						//将 当前的已访问顶点和未访问顶点的最小路径替换掉minPath
						minPath = graph.weight[i][j];
						//h1记录已访问顶点
						h1 = i;
						//h2记录未访问顶点
						h2 = j;
					}
				}
			}
			//累加最小路径
			totalPath += minPath;
			System.out.println("边<" + graph.data[h1] + "," + graph.data[h2] + ">的路径是：" + minPath);
			//h1记录i的值，i表示已访问的顶点，那么h1也表示已访问的顶点
			//而h2记录j的值，j表示未访问的顶点，那么h2也表示未访问的，所以需要设置为true，表示已访问的
			isVisited[h2] = true;
			//重置minPath，默认表示顶点之间是不连通的
			minPath = 10000;
		}
		System.out.println("最小生成树的最小路径是：" + totalPath);
	}
}
