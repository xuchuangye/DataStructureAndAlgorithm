package com.mashibing.arithmetic.kruskal;

/**
 * 图中的边
 *
 * @author xcy
 * @date 2022/4/2 - 17:19
 */
public class Edge {
	String start;
	String end;
	Integer weight;

	public Edge(String start, String end, Integer weight) {
		this.start = start;
		this.end = end;
		this.weight = weight;
	}

	/**
	 * 打印边的信息，包括起始顶点和结束顶点以及权值
	 * @return 返回边的信息
	 */
	@Override
	public String toString() {
		return "Edge{" +
				"<" + start +
				", " + end +
				"> = " + weight +
				'}';
	}
}
