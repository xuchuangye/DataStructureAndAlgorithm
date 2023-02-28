package com.mashibing.dijkstra.extend;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * 最小生成树之Dijkstra算法
 * <p>
 * 迪杰斯特拉算法：
 * 图中已经给定所有节点之间的距离，返回任意一个节点到达其他节点的最短距离的集合.
 * 如果有到达不了的点，集合中就没有该点以及该点的距离
 * <p>
 * 限制：
 * 没有权值为负数的边
 *
 * @author xcy
 * @date 2022/5/5 - 10:50
 */
public class DijkstraImpMinSpanningTree {

	public static void main(String[] args) {
	}

	/**
	 * 最小生成树之迪杰斯特拉算法
	 *
	 * @param from 从from节点出发
	 * @return 返回从from出发，能够到达的其他节点与最短路径的对应关系表，到达不了的权值为正无穷
	 */
	public static HashMap<Node, Integer> dijkstraMinimumSpanningTree(Node from) {
		//创建从from节点出发，能够到达的其他节点与最短路径的对应关系表
		HashMap<Node, Integer> distanceMap = new HashMap<>();
		//节点到达节点自己本身，最短距离为0
		distanceMap.put(from, 0);
		//已经确定好的节点的集合
		HashSet<Node> selectedNodes = new HashSet<>();
		//获取没有确定好并且与当前节点连接的最短路径的节点
		Node minNode = getDistanceAndUnSelectedNode(distanceMap, selectedNodes);
		while (minNode != null) {
			//从原始点 -> minNode(跳转点)的最短距离distance
			int distance = distanceMap.get(minNode);
			//对minNode节点指向的其他节点的每一条边进行遍历
			for (Edge edge : minNode.edges) {
				//边能够到达的节点
				Node toNode = edge.to;
				//
				if (!distanceMap.containsKey(toNode)) {
					distanceMap.put(toNode, distance + edge.weight);
				} else {
					distanceMap.put(edge.to, Math.min(distanceMap.get(toNode), distance + edge.weight));
				}
			}
			selectedNodes.add(minNode);
			minNode = getDistanceAndUnSelectedNode(distanceMap, selectedNodes);
		}
		return distanceMap;
	}

	/**
	 * 返回不在已经在selectedNodes集合中存在，并且距离最短的节点
	 *
	 * @param distanceMap   从原始节点出发，能够到达的其他节点与最短路径的对应关系表
	 * @param selectedNodes 已经确定好的节点的集合
	 * @return 返回不在已经在selectedNodes集合中存在，并且距离最短的节点
	 */
	private static Node getDistanceAndUnSelectedNode(HashMap<Node, Integer> distanceMap, HashSet<Node> selectedNodes) {
		Node minNode = null;
		int minDistance = Integer.MAX_VALUE;
		for (Map.Entry<Node, Integer> entry : distanceMap.entrySet()) {
			Node node = entry.getKey();
			int distance = entry.getValue();
			if (!selectedNodes.contains(node) && distance < minDistance) {
				minNode = node;
				minDistance = distance;
			}
		}
		return minNode;
	}

	/**
	 * @param from
	 * @param size
	 * @return
	 */
	public static HashMap<Node, Integer> dijkstraMinimumSpanningTree(Node from, int size) {
		//加强堆
		HeapGreator<Node> heapGreator = new HeapGreator<>(size);
		//出发节点到自己的距离为0
		heapGreator.addOrUpdateOrIgnore(from, 0);
		//创建返回值
		HashMap<Node, Integer> result = new HashMap<>();
		//判断加强堆是否为空
		while (!heapGreator.isEmpty()) {
			//如果不为空，取出加强堆的堆顶元素
			NodeRecord<Node> record = heapGreator.pop();
			//堆顶元素包括当前节点与出发节点到当前节点的距离两个信息
			Node cur = record.node;
			int distance = record.distance;
			//遍历当前节点指向其他节点的所有边的集合
			for (Edge edge : cur.edges) {
				//每一条边到达的结束节点，每一条边的权值 + 当前节点与每条边到达的节点的距离
				heapGreator.addOrUpdateOrIgnore(edge.to, edge.weight + distance);
			}
			//将当前节点和出发节点到当前节点的距离两个信息添加到返回的结果集中
			result.put(cur, distance);
		}
		return result;
	}
}
