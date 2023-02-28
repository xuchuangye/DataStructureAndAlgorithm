package com.mashibing.arithmetic.dijkstra;

import java.util.Arrays;

/**
 * 已访问的顶点的类
 * VisitedVertex已访问顶点的类
 * already_arr[]记录被访问过的顶点，1表示访问过，0表示未访问，会动态更新
 *
 * dis[]记录初始顶点到图中各个顶点的距离的数组
 * 比如：以G为出发点，记录G到其他顶点的距离，{A(2),B(3),C(N),D(N),E(4),F(6),G(N)}，表示G->A的边(权值)距离是2，N表示G与该顶点不连通
 * 会动态更新
 * pre_visited记录各个顶点的前驱节点的数组，每一个索引对应的值为前一个顶点的索引，
 * 比如：{6,6,0,0,6,6,0}表示以G为出发点，能够和ABEF连通，那么索引为0(顶点A)，1(顶点B)，4(顶点E)，5(顶点F)的前一个顶点是6，也就是顶点G
 * 会动态更新
 * @author xcy
 * @date 2022/4/3 - 10:52
 */
public class VisitedVertex {
	/**
	 * 顶点个数
	 */
	public int vertexCount;
	/**
	 * 记录顶点之间的连通关系，65535表示不连通
	 */
	public static final int N = 65535;

	/**
	 * 记录已访问过的顶点，1表示已访问过，0表示未访问过
	 */
	public int[] already_arr;

	/**
	 * 记录初始顶点到图中其他顶点的距离
	 */
	public int[] dis;

	/**
	 * 记录各个顶点的前一个顶点，索引表示各个顶点，索引对应的值表示各个顶点的前一个顶点
	 */
	public int[] pre_visited;

	/**
	 * 初始化
	 * @param vertexCount 表示顶点的个数
	 * @param index 从指定的顶点开始出发，也就是初始顶点，比如：以G顶点，index就是6
	 */
	public VisitedVertex(int vertexCount, int index) {
		this.vertexCount = vertexCount;
		this.already_arr = new int[vertexCount];
		this.dis = new int[vertexCount];
		this.pre_visited = new int[vertexCount];

		//填充dis数组的所有元素都为N
		Arrays.fill(dis, N);
		//设置初始顶点的访问情况为已访问
		this.already_arr[index] = 1;
		//设置初始顶点的访问距离为0
		this.dis[index] = 0;
	}

	/**
	 * 判断当前顶点是否已经访问过
	 * @param index 当前顶点的索引
	 * @return 返回当前顶点是否已经被访问过，如果访问过就是true，如果未访问过就是false
	 */
	public boolean isVisited(int index) {
		//1表示已访问，否则就是未访问
		return already_arr[index] == 1;
	}

	/**
	 * 更新初始顶点到index当前顶点的距离，也就是边的长度(权值weight)
	 * @param index 当前顶点的索引
	 * @param weight 顶点之间的边的长度(权值weight)
	 * @return
	 */
	public void updateDis(int index, int weight) {
		//更新初始顶点到当前顶点的距离，也就是边的长度(权值)
		dis[index] = weight;
	}

	/**
	 * 更新pre顶点的前驱顶点为index顶点
	 * @param index 前驱顶点
	 * @param pre
	 */
	public void updatePre(int index, int pre) {
		pre_visited[pre] = index;
	}

	/**
	 * 返回初始顶点到当前index顶点的距离
	 * @param index 当前顶点的索引
	 * @return
	 */
	public int getDis(int index) {
		return dis[index];
	}

	/**
	 * 继续选择并返回新的访问顶点， 比如这里的G完后，就是A点作为新的访问顶点(注意不是出发顶点)
	 * 更新并返回新的访问顶点
	 * @return 新的访问顶点
	 */
	public int updateArray() {
		int min = N;
		int index = 0;
		for (int i = 0; i < vertexCount; i++) {
			//already_arr[i] == 0表示判断当前顶点是否被访问过，0表示未访问过
			//dis[i] < min表示顶点之间是连通的
			if (already_arr[i] == 0 && dis[i] < min) {
				//将最小值赋给min
				min = dis[i];
				//index指向i
				index = i;
			}
		}
		//标记index顶点已访问
		already_arr[index] = 1;
		return index;
	}

	/**
	 * 展示already_arr[]，dis[]，pre_visited[]
	 */
	public void show() {
		for (int i : already_arr) {
			System.out.print(i + "\t");
		}
		System.out.println();
		for (int i : pre_visited) {
			System.out.print(i + "\t");
		}
		System.out.println();
		for (int di : dis) {
			System.out.print(di + "\t");
		}
		System.out.println();
		String[] vertex = {"A","B","C","D","E","F","G"};
		int index = 0;
		for (int i : dis) {
			if (i != N) {
				System.out.print(vertex[index++] + "(" + i + ")");
			}else {
				System.out.print("N");
			}
		}
	}
}
