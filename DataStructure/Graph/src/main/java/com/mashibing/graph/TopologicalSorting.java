package com.mashibing.graph;

import java.util.*;

/**
 * 图的拓扑排序
 * <p>
 * 1）在图中找到所有入度为0的点输出
 * 2）把所有入度为0的点从图中删除，继续找入度为0的点输出，周而复始
 * 3）图的所有点都被删除后，依次输出的顺序就是拓扑排序
 * <p>
 * 要求：有向图且其中没有环
 * 应用：事件安排，编译顺序
 * <p>
 * 编译顺序：拓扑排序，指的是根据先后顺序能够依次把工作顺利做完而且不缺依赖的一个顺序
 * 所以为什么不能循环依赖，因为对于拓扑排序来说一定是有向图，而且没有环
 * <p>
 * 拓扑排序最简单的解法：使用入度就够了
 * @author xcy
 * @date 2022/5/4 - 16:37
 */
public class TopologicalSorting {
	/**
	 * 图的拓扑排序
	 * @param graph 图
	 * @return 返回拓扑排序之后的节点集合
	 */
	public static List<Node> topologicalSorting(Graph graph) {
		//创建图中所有节点与入度的关系表HashMap
		//key表示图中的节点，value表示入度的值
		HashMap<Node,Integer> inMap = new HashMap<>();
		//创建入度为0的节点队列
		Queue<Node> zeroIn_Queue = new LinkedList<>();
		//对图中所有的节点进行遍历，此地图中包含的值的视图
		for (Node node : graph.nodes.values()) {
			//将当前节点作为key，当前节点的入度作为value添加到inMap
			inMap.put(node, node.in);
			//如果入度为0，则添加到入度为0的节点队列中
			if (node.in == 0) {
				zeroIn_Queue.add(node);
			}
		}
		//创建拓扑排序之后的节点集合
		List<Node> ans = new ArrayList<Node>();
		//如果入度为0的节点不为空
		while (!zeroIn_Queue.isEmpty()) {
			//取出队列中的顶部节点
			Node cur = zeroIn_Queue.poll();
			//将当前入度为0的节点首先添加到拓扑排序之后的节点集合中
			ans.add(cur);
			//将当前节点指向的其他节点的集合进行遍历
			for (Node next : cur.nexts) {
				//消除当前节点对指向其他的节点的入度的影响
				inMap.put(next, inMap.get(next) - 1);
				//如果当前节点指向的其他节点的入度为0，也添加到入度为0的节点队列中
				if (inMap.get(next) == 0) {
					zeroIn_Queue.add(next);
				}
			}
		}
		return ans;
	}
}
