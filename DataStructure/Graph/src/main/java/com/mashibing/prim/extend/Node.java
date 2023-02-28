package com.mashibing.prim.extend;

import java.util.ArrayList;

/**
 * 图中的节点信息类
 * @author xcy
 * @date 2022/5/4 - 14:56
 */
public class Node {
	/**
	 * 节点的值
	 */
	public int value;
	/**
	 * 入度：入度指的是指向当前顶点的边数
	 */
	public int in;
	/**
	 * 出度：出度指的是从当前顶点出发的边数
	 */
	public int out;
	/**
	 * 该节点指向其他节点的集合
	 */
	public ArrayList<Node> nexts;
	/**
	 * 该节点指向其他节点的所有边的集合
	 */
	public ArrayList<Edge> edges;

	public Node(int value)  {
		this.value = value;
		in = 0;
		out = 0;
		nexts = new ArrayList<>();
		edges = new ArrayList<>();
	}
}
