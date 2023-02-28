package com.mashibing.kruskal.extend;

import java.util.*;

/**
 * 最小生成树之Kruskal算法 --> 使用并查集的方式实现
 * <p>
 * 1、总是从权值最小的边开始考虑，依次考察权值依次变大的边
 * 2、当前的边要么进入最小生成树的集合，要么丢弃
 * 3、如果当前的边进入最小生成树的集合之后不会形成环，就要当前边
 * 4、如果当前的边进入最小生成树的集合之后会形成环，就不要当前边
 * 5、考察完所有的边之后，最小生成树的集合也就得到了
 * <p>
 * 基本思路：
 * 该实现可以利用贪心算法，从权值最小的边开始，只要不形成环就要
 * @author xcy
 * @date 2022/5/5 - 8:25
 */
public class KruskalWithUnionSetImpMinSpanningTree {
	public static class UnionSet {
		public HashMap<Node, Node> parents;
		public HashMap<Node, Integer> size;

		public UnionSet() {
			parents = new HashMap<>();
			size = new HashMap<>();
		}

		public void makeSet(Collection<Node> collection) {
			parents.clear();
			size.clear();
			for (Node node : collection) {
				parents.put(node, node);
				size.put(node, 1);
			}
		}

		public Node findHead(Node node) {
			Stack<Node> stack = new Stack<>();
			while (!node.equals(parents.get(node))) {
				stack.push(node);
				node = parents.get(node);
			}

			while (!stack.isEmpty()) {
				Node cur = stack.pop();
				parents.put(cur, node);
			}
			return node;
		}

		public boolean isSameSet(Node from, Node to) {
			return findHead(from) == findHead(to);
		}

		public void union(Node from, Node to) {
			if (from == null || to == null) {
				return;
			}
			Node aHead = findHead(from);
			Node bHead = findHead(to);
			if (aHead != bHead) {
				if (size.get(aHead) >= size.get(bHead)) {
					parents.put(bHead, aHead);
					size.put(aHead, size.get(aHead) + size.get(bHead));
					size.remove(bHead);
				}else {
					parents.put(aHead, bHead);
					size.put(bHead, size.get(bHead) + size.get(aHead));
					size.remove(aHead);
				}
			}
		}
	}

	public static class WeightComparator implements Comparator<Edge> {
		@Override
		public int compare(Edge o1, Edge o2) {
			return o1.weight - o2.weight;
		}
	}

	/**
	 * 最小生成树之Kruskal算法
	 * @param graph 图
	 * @return
	 */
	public static Set<Edge>  kruskalMinimumSpanningTree(Graph graph) {
		UnionSet unionSet = new UnionSet();
		//将图中所有的节点集合拷贝给unionSet中
		unionSet.makeSet(graph.nodes.values());
		//创建按照Edge的权值大小升序排列的小根堆
		PriorityQueue<Edge> edges = new PriorityQueue<>(new WeightComparator());
		/*for (Edge edge : graph.edges) {
			edges.add(edge);
		}*/
		//将所有的边集合添加到小根堆中
		edges.addAll(graph.edges);
		//创建
		Set<Edge> edgeSet = new HashSet<>();
		//如果小根堆不为空
		while (!edges.isEmpty()) {
			//取出堆顶的边对象
			Edge edge = edges.poll();
			//判断边的起始节点和结束节点是否在同一个集合中
			if (!unionSet.isSameSet(edge.from, edge.to)) {
				//如果在同一个集合中，就添加到Set集合中
				edgeSet.add(edge);
				//将边的起始节点和结束节点整合到一个集合中
				unionSet.union(edge.from, edge.to);
			}
		}
		return edgeSet;
	}
}
