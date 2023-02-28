package com.mashibing.prim.extend;

import com.mashibing.prim.extend.Graph;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * 最小生成树之Prim算法
 *
 * 1、可以从任意节点出发来寻找最小生成树
 * 2、某个点加入到被选取的点之后，解锁这个点出发的所有新的边
 * 3、在所有解锁的边中选择最小的边，然后查看是否会形成环
 * 4、如果会，就不要当前边，继续考察剩下解锁的边中最小的边，重复3步骤
 * 5、如果不会，就要当前边，将该边的指向点加入到被选取的点中，重复2步骤
 * 6、当所有的点都被选取，最小生成树就形成了
 *
 * Prim算法的过程：
 * 选择点 -> 解锁新的边 -> 选择点 -> 解锁新的边 -> ... -> 直到最小生成树的形成
 * @author xcy
 * @date 2022/5/5 - 9:18
 */
public class PrimImpMinSpanningTree {
	public static Set<Edge> primMinimumSpanningTree(Graph graph) {
		//创建解锁的边的小根堆
		PriorityQueue<Edge> edgesQueue = new PriorityQueue<>();
		//创建解锁的节点的注册中心
		HashSet<Node> nodeSet = new HashSet<>();
		//创建被选择的边的Set集合
		Set<Edge> edgeSet = new HashSet<>();

		//对图中所有的节点进行遍历
		for (Node node : graph.nodes.values()) {
			//查看当前节点是否在注册中心注册过
			if (!nodeSet.contains(node)) {
				//如果没有注册过，就进行注册
				nodeSet.add(node);
				//对当前节点指向其他节点的所有边的集合进行遍历，一个点解锁所有相连的边
				for (Edge edge : node.edges) {
					//将前节点指向其他节点的所有的边都添加到解锁的边的小根堆中
					edgesQueue.add(edge);
				}
				//如果小根堆不为空
				while (!edgesQueue.isEmpty()) {
					//取出小根堆的堆顶的元素，也就是权值最小的边
					Edge edge = edgesQueue.poll();
					//该边的结束节点，也就是选择的点，可能是新的节点
					Node toNode = edge.to;
					//如果该选择的点没有在注册中心注册过，没有注册的节点就是新的节点
					if (!nodeSet.contains(toNode)) {
						//那就进行注册
						nodeSet.add(toNode);
						//该边符合要求，将该边添加到被选择的边的Set集合中
						edgeSet.add(edge);
						for (Edge nextEdge : toNode.edges) {
							edgesQueue.add(nextEdge);
						}
					}
				}
			}
			//如果整个图就只有一个最小生成树
			break;
			//如果整个图是一个森林，也就是不止一个最小生成树，那么就将 break 注释
			//break;
		}
		return edgeSet;
	}
}
