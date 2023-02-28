package com.mashibing.arithmetic.floyd;

import java.util.Arrays;

/**
 * 图
 * @author xcy
 * @date 2022/4/4 - 9:44
 */
public class Graph {
	/**
	 * 表示图中顶点之间不是直接连通的关系
	 */
	public static final int N = 65535;
	/**
	 * 图中顶点个数
	 */
	private final int vertexCount;

	/**
	 * 存放顶点的数组
	 */
	private final String[] vertex;

	/**
	 * 存储各个顶点到其他顶点的距离
	 * 比如：
	 *   A B C D E F G
	 * A 0,5,7,N,N,N,2
	 * B 5,0,N,9,N,N,3
	 * C 7,N,0,N,8,N,N
	 * D N,9,N,0,N,4,N
	 * E N,N,8,N,0,5,4
	 * F N,N,N,4,5,0,6
	 * G 2,3,N,N,4,6,0
	 */
	public int[][] dis;

	/**
	 * 存储到达目标顶点的前驱顶点
	 */
	public int[][] pre;

	public Graph(int vertexCount, String[] vertex, int[][] dis) {
		this.vertexCount = vertexCount;
		this.vertex = vertex;
		this.dis = dis;
		this.pre = new int[vertexCount][vertexCount];

		for (int i = 0; i < pre.length; i++) {
			Arrays.fill(pre[i], i);
		}
	}

	/**
	 * 展示图的pre数组和dis数组
	 */
	public void showGraph() {
		for (int i = 0; i < dis.length; i++) {
			System.out.printf("起始顶点%s->到达",vertex[i]);
			for (int j = 0; j < dis[i].length; j++) {
				System.out.printf("目标顶点%s", vertex[j]);
				System.out.print("(");
				if (dis[i][j] != N) {
					System.out.printf("距离%2d",dis[i][j]);
				}else {
					System.out.printf("%2s", "N");
				}
				System.out.print(")    ");
			}
			System.out.println();
			System.out.print("      ");
			for (int j = 0; j < pre[i].length; j++) {
				System.out.printf("      经过中间顶点->%s",vertex[pre[i][j]]);
			}
			System.out.println();
		}
	}

	/**
	 * 弗洛伊德算法
	 */
	public void floydAlgorithm() {
		//记录最短距离
		int length = 0;
		//k表示中间顶点，用于辅助完成起始顶点i到达目标顶点j的距离是否是最短距离
		for (int k = 0; k < vertexCount; k++) {
			//i表示起始顶点
			for (int i = 0; i < vertexCount; i++) {
				//j表示目标顶点
				for (int j = 0; j < vertexCount; j++) {
					//length记录从起始顶点i到中间顶点k的距离 + 中间顶点到目标顶点j的距离之和
					length = dis[i][k] + dis[k][j];
					//判断如果起始顶点经过中间顶点到达目标顶点的距离之和比起始顶点直达目标顶点的距离还要小
					if (length < dis[i][j]) {
						//那么就需要更新起始顶点直达目标顶点的距离
						dis[i][j] = length;
						//更新到达目标顶点的前驱顶点
						//比如：C->A->G，那么C是起始顶点i，G是目标顶点j，A是中间顶点k
						//A就是G的前驱顶点，所以k就作为前驱顶点
						pre[i][j] = pre[k][j];
					}
				}
			}
		}
	}
}
