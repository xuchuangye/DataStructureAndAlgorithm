package com.mashibing.graph;

/**
 * 图的转换
 * 将任意一个二维数组转换为图
 * 二维数组由每一个一维数组组成，一维数组就是每一条边，边的信息有：[weight, fromNode, toNode]
 * @author xcy
 * @date 2022/5/4 - 15:10
 */
public class GraphGenerator {
	public static Graph createGraph(Integer[][] matrix) {
		Graph graph = new Graph();
		for (Integer[] edge: matrix) {
			//获取边的信息
			//边的权重
			Integer weight = edge[0];
			//边的起始节点的值
			Integer from = edge[1];
			//边的结束节点的值
			Integer to = edge[2];
			if (!graph.nodes.containsKey(from)) {
				graph.nodes.put(from, new Node(from));
			}
			if (!graph.nodes.containsKey(to)) {
				graph.nodes.put(to, new Node(to));
			}
			Node fromNode = graph.nodes.get(from);
			Node toNode = graph.nodes.get(to);
			//创建边的对象
			Edge newEdge = new Edge(weight, fromNode, toNode);
			//将边的对象添加到图的边集中
			graph.edges.add(newEdge);
			//将该节点指向其他节点的集合中添加toNode节点
			fromNode.nexts.add(toNode);
			//fromNode指向其他节点的个数++
			fromNode.out++;
			//其他节点指向toNode的个数++
			toNode.in++;
			//将该节点指向其他节点的边的集合中添加newEdge
			fromNode.edges.add(newEdge);
		}
		return graph;
	}
}
