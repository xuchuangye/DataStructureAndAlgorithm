package com.mashibing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * 图
 * @author xcy
 * @date 2022/3/31 - 9:01
 */
public class Graph {
	/**
	 * 存储图的集合
	 */
	private final List<String> vertexList;

	/**
	 * 存储图对应的邻接矩阵
	 */
	private final int[][] edges;

	/**
	 * 存储边的数目
	 */
	private int edgeCounts;

	/**
	 * 记录顶点是否 被访问过
	 */
	private boolean[] isVisited;


	public Graph(int n) {
		//初始化邻接矩阵
		edges = new int[n][n];
		//初始化图的集合
		vertexList = new ArrayList<>();
		edgeCounts = 0;
		isVisited = new boolean[5];
	}

	/**
	 * 查找并返回当前顶点的第一个邻接顶点的索引
	 * @param index 当前顶点的索引
	 * @return 如果找到就返回，如果找不到就返回-1
	 */
	public int getFirstAdjacencyVertex(int index) {
		for (int j = 0; j < getGraphVertexNumber(); j++) {
			if (edges[index][j] > 0) {
				return j;
			}
		}
		return -1;
	}

	/**
	 * 根据前一个顶点的索引，获取下一个顶点的索引
	 * @return
	 */
	public int getNextVertex(int v1, int v2) {
		for (int j = v2 + 1;j < getGraphVertexNumber(); j++) {
			if (edges[v1][j] > 0) {
				return j;
			}
		}
		return -1;
	}

	/**
	 * 深度优化遍历算法
	 *
	 * 1、访问初始顶点v，并标记顶点v为已访问。
	 * 2、查找顶点v的第一个邻接顶点w。
	 * 3、若w存在，则继续执行4，如果w不存在，则回到第1步，将从v的下一个顶点继续。
	 * 4、若w未被访问，对w进行深度优先遍历递归（即把w当做另一个v，然后进行步骤123）。
	 * 5、查找顶点v的w邻接顶点的下一个邻接顶点，转到步骤3。
	 * @param isVisited 记录当前顶点是否被访问过
	 * @param v 当前顶点，默认是从初始顶点开始
	 */
	private void depthFirstSearch(boolean[] isVisited, int v) {
		//1.1 首先访问初始顶点v，并且进行输出
		System.out.print(getVertexWithNumber(v) + "->");
		//1.2 将该顶点设置为已经被访问过
		isVisited[v] = true;
		//2.查找顶点i的第一个邻接顶点w
		int w = getFirstAdjacencyVertex(v);
		//3.判断w顶点是否存在，w != -1表示存在，w == - 1表示不存在
		while (w != -1) {
			//4.1 判断索引为w的顶点是否被访问过
			if (!isVisited[w]) {
				//4.2 如果没有被访问过，就继续深度递归
				depthFirstSearch(isVisited, w);
			}
			//4.3 w的顶点存在并且被访问过，就继续判断w顶点的下一个顶点
			w = getNextVertex(v, w);
		}
	}

	/**
	 * 5. 如果w的顶点不存在，回到第一步，从v的w邻接顶点的下一个邻接顶点开始
	 */
	public void depthFirstSearch() {
		//重新初始化
		isVisited = new boolean[getGraphVertexNumber()];
		for (int v = 0; v < getGraphVertexNumber(); v++) {
			//判断当前顶点v是否 被访问 过
			if (!isVisited[v]) {
				depthFirstSearch(isVisited, v);
			}
		}
	}

	/**
	 * 广度(宽度)优先遍历算法
	 *
	 * 1、访问初始结点v并标记结点v为已访问。
	 * 2、结点v入队列
	 * 3、当队列非空时，继续执行，否则算法结束。
	 * 4、出队列，取得队头结点u。
	 * 5、查找结点u的第一个邻接结点w。
	 * 6、若结点u的邻接结点w不存在，则转到步骤3；否则循环执行以下三个步骤
	 * @param isVisited 记录当前顶点是否被访问过
	 * @param v 初始顶点v
	 */
	private void broadFirstSearch(boolean[] isVisited, int v) {
		//记录节点的索引
		LinkedList<Integer> queue = new LinkedList<Integer>();
		int u;
		int w;
		//输出初始节点v
		System.out.print(getVertexWithNumber(v) + "->");
		//将初始节点标记为已访问
		isVisited[v] = true;
		//将初始节点添加到队列中
		queue.addLast(v);

		//判断队列是否为空
		while (!queue.isEmpty()) {
			//取出队列的第一个节点的索引
			u = queue.removeFirst();
			//获取u节点的第一个邻接节点
			w = getFirstAdjacencyVertex(u);

			//判断w节点是否存在，w != -1表示存在
			while (w != -1) {
				//判断w是否被访问过，取反表示没有访问过
				if (!isVisited[w]) {
					//输出节点
					System.out.print(getVertexWithNumber(w) + "->");
					//标记节点已经被 访问过
					isVisited[w] = true;
					//将节点添加到队列中
					queue.addLast(w);
				}
				//否则就表示w已经被访问过
				w = getNextVertex(u, w);
			}
		}

	}

	/**
	 * 重载broadFirstSearch(boolean[] isVisited, int v)
	 * 6.1 若结点w尚未被访问，则访问结点w并标记为已访问。
	 * 6.2 结点w入队列
	 * 6.3 查找结点u的继w邻接结点后的下一个邻接结点w，转到步骤6
	 */
	public void broadFirstSearch() {
		//重新初始化
		isVisited = new boolean[getGraphVertexNumber()];
		for (int v = 0; v < getGraphVertexNumber(); v++) {
			//判断是否已经被访问过
			if (!isVisited[v]) {
				broadFirstSearch(isVisited, v);
			}
		}
	}

	/**
	 * 返回图中顶点的个数
	 * @return 图中顶点的个数
	 */
	public int getGraphVertexNumber() {
		return vertexList.size();
	}

	/**
	 * 返回图中边的个数
	 * @return 图中边的个数
	 */
	public int getGraphEdgeNumber() {
		return edgeCounts;
	}

	/**
	 * 根据顶点索引返回指定顶点
	 * @param i 顶点的索引
	 * @return 返回指定顶点
	 */
	public String getVertexWithNumber(int i) {
		return vertexList.get(i);
	}

	/**
	 * 返回两个顶点之间的权值
	 * @param v1 第一个顶点
	 * @param v2 第二个顶点
	 * @return 两个顶点之间的权值
	 */
	public int getVertexWeight(int v1, int v2) {
		return edges[v1][v2];
	}

	/**
	 * 图的遍历
	 */
	public void showGraph() {
		for (int[] link : edges) {
			System.err.println(Arrays.toString(link));
		}
	}

	/**
	 * 添加图
	 * @param vertex
	 */
	public void insertVertex(String vertex) {
		vertexList.add(vertex);
	}

	/**
	 * 添加边
	 * @param v1 第一个顶点的索引 0 -> A 1 -> B 2 -> C 3 -> D 4 -> E
	 * @param v2 第二个顶点的索引
	 * @param weight 权值，1表示直接关联，0表示不直接关联
	 */
	public void insertEdge(int v1, int v2, int weight) {
		//A - B,A -> 0,B -> 1
		edges[v1][v2] = weight;
		edges[v2][v1] = weight;
		edgeCounts += 1;
	}
}
