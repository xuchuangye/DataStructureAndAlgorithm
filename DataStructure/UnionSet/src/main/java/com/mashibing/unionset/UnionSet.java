package com.mashibing.unionset;

import java.util.HashMap;
import java.util.List;
import java.util.Stack;

/**
 * 并查集 --> 使用HashMap的方式实现
 *
 * <p>
 * 核心逻辑：
 * 1、代表节点
 * <p>
 * 优化：
 * 1、数量少的节点集合挂载到数量多的节点集合上，减少集合链的长度
 * 2、如果节点查找该节点的代表节点，整个链过长，
 * 将代表节点优化为当前节点的直接父节点，那么每次操作的时间复杂度都是O(1)，减少集合链的长度
 * 并查集的应用：
 * 1、解决连通性的问题，尤其是图的连通性问题，时间复杂度O(1)
 * @author xcy
 * @date 2022/5/3 - 9:17
 */
public class UnionSet<V> {
	/**
	 * 并查集的变量的包装类
	 * @param <V>
	 */
	public static class Node<V> {
		V value;

		public Node(V v) {
			value = v;
		}
	}
	/**
	 * 每一个变量对应自己的包装类Node
	 */
	public HashMap<V, Node<V>> nodes;

	/**
	 * 每一个节点和自己的唯一父节点的对应关系表
	 */
	public HashMap<Node<V>, Node<V>> parents;

	/**
	 * 每一个代表节点对应该节点所在集合的节点个数
	 */
	public HashMap<Node<V>, Integer> sizeMap;

	/**
	 * 初始化
	 *
	 * @param list 自定义类型的变量集合
	 */
	public UnionSet(List<V> list) {
		nodes = new HashMap<>();
		parents = new HashMap<>();
		sizeMap = new HashMap<>();
		for (V value : list) {
			//创建变量对应的包装类Node
			Node<V> node = new Node<>(value);
			//key：键对应变量value，value：值对应变量的包装类node
			nodes.put(value, node);
			//node节点自己的父节点是自己
			parents.put(node, node);
			//node节点作为自己集合的代表节点，集合的节点个数为1
			sizeMap.put(node, 1);
		}
	}

	/**
	 * 返回当前节点的代表节点
	 * 时间复杂度：O(1)
	 * 样本量为N，findHead()的调用次数非常频繁，达到O(N)或者超过O(N)的规模
	 * 单次查询时间复杂度：O(1)
	 *
	 * @param cur 当前节点
	 * @return 返回当前节点的代表节点
	 */
	public Node<V> findHead(Node<V> cur) {
		Stack<Node<V>> stack = new Stack<>();
		//当前节点是否是自己集合中的代表节点
		while (cur != parents.get(cur)) {
			//如果当前节点不是代表节点，说明没有找到代表节点
			stack.push(cur);
			//cur继续来到自己的父节点的位置
			cur = parents.get(cur);
		}
		//当退出上面的while循环时，cur一定是代表节点
		//如果stack不为空
		while (!stack.isEmpty()) {
			//那么就取出栈中每一个节点
			Node<V> vNode = stack.pop();
			//并且设置每一个节点的直接父节点为代表节点cur
			parents.put(vNode, cur);
		}
		//返回代表节点cur
		return cur;
	}

	/**
	 * 判断变量和a和变量b是否在同一个集合中
	 *
	 * @param a 变量a
	 * @param b 变量b
	 * @return 如果在同一个集合，返回true，否则返回false
	 */
	public boolean isSameSet(V a, V b) {
		//变量a所对应的包装类和变量b所对应的包装类
		Node<V> aNode = nodes.get(a);
		Node<V> bNode = nodes.get(b);
		//查找并判断各自对应的代表节点是否是同一个，如果是同一个，那么变量a和b就在同一个集合中
		//否则变量a和b不在同一个集合中
		return findHead(aNode) == findHead(bNode);
	}

	/**
	 * 将变量a和变量b放在同一个集合中
	 *
	 * @param a 变量a
	 * @param b 变量b
	 */
	public void union(V a, V b) {
		//查找变量a对应包装类的代表节点
		Node<V> aHead = findHead(nodes.get(a));
		//查找变量b对应包装类的代表节点
		Node<V> bHead = findHead(nodes.get(b));
		//如果两个代表节点一样，说明变量a和变量b就在同一个集合中
		if (aHead == bHead) {
			return;
		}
		//变量a对应包装类的代表节点的集合节点个数
		Integer aSize = sizeMap.get(aHead);
		//变量b对应包装类的代表节点的集合节点个数
		Integer bSize = sizeMap.get(bHead);

		//确定长的集合的代表节点
		Node<V> longHead = aSize >= bSize ? aHead : bHead;
		//确定短的集合的代表节点
		Node<V> shortHead = longHead == aHead ? bHead : aHead;

		//短的集合的代表节点挂载到长的集合上，所以短集合代表节点的父节点就是长集合的代表节点
		parents.put(shortHead, longHead);
		sizeMap.put(longHead, aSize + bSize);
		//短的集合已经挂载到长的集合当中，短的集合的代表节点已经不再是代表节点了
		//所以在代表节点与集合节点个数的对应关系表sizeMap中，移除短的集合的代表节点
		sizeMap.remove(shortHead);
	}

	/**
	 * 并查集的集合
	 * @return 返回并查集的集合个数
	 */
	public int sets() {
		return sizeMap.size();
	}
}
