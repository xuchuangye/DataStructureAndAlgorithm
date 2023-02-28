package com.mashibing;

/**
 * @author xcy
 * @date 2022/3/31 - 9:16
 */
public class GraphDemo {
	public static void main(String[] args) {
		//顶点个数
		int vertexCount = 8;
		//String[] vertexs = {"A","B","C","D","E"};
		String[] vertexs = {"1", "2", "3", "4", "5", "6", "7", "8"};
		//创建图
		Graph graph = new Graph(vertexCount);

		//添加图
		for (String vertex : vertexs) {
			graph.insertVertex(vertex);
		}
		//添加边和权值
		//A-B A-C B-C B-D B-E
		/*graph.insertEdge(0,1,1);
		graph.insertEdge(0,2,1);
		graph.insertEdge(1,2,1);
		graph.insertEdge(1,3,1);
		graph.insertEdge(1,4,1);*/

		graph.insertEdge(0, 1, 1);
		graph.insertEdge(0, 2, 1);
		graph.insertEdge(1, 3, 1);
		graph.insertEdge(1, 4, 1);
		graph.insertEdge(3, 7, 1);
		graph.insertEdge(2, 5, 1);
		graph.insertEdge(2, 6, 1);
		graph.insertEdge(5, 6, 1);
		//遍历图
		graph.showGraph();

		//深度优先遍历图
		System.out.println("深度优先遍历");
		graph.depthFirstSearch();

		System.out.println();

		//广度优先遍历图
		System.out.println("广度优先遍历");
		graph.broadFirstSearch();
	}
}
