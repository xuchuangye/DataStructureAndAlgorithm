package com.mashibing.kruskal.extend;

/**
 * 图中的边信息类
 *
 * @author xcy
 * @date 2022/5/4 - 14:59
 */
public class Edge {
	/**
	 * 边的权重
	 */
	public int weight;
	/**
	 * 边的起始节点
	 */
	public Node from;
	/**
	 * 边的结束节点
	 */
	public Node to;

	public Edge(int weight, Node from, Node to) {
		this.weight = weight;
		this.from = from;
		this.to = to;
	}
}
