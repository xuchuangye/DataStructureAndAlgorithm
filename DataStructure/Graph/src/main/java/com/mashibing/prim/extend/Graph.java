package com.mashibing.prim.extend;

import java.util.*;

/**
 * 图的信息类
 * @author xcy
 * @date 2022/5/4 - 15:03
 */
public class Graph {
	/**
	 * 点集，key：节点的值，value：节点值的封装类Node
	 */
	public HashMap<Integer, Node> nodes;
	/**
	 * 边集
	 */
	public HashSet<Edge> edges;

	public Graph() {
		nodes = new HashMap<>();
		edges = new HashSet<>();
	}

	/**
	 * 从from节点出发，进行宽度优先遍历
	 * @param from
	 */
	public void widthFirstTraversal(Node from) {
		if (from == null) {
			return;
		}
		Queue<Node> queue = new LinkedList<>();
		HashSet<Node> set = new HashSet<>();
		//将出发的节点添加到队列中
		queue.add(from);
		//将出发的节点在Set集合中进行注册
		set.add(from);
		//队列不为空
		while (!queue.isEmpty()) {
			//取出队列的顶部元素
			Node cur = queue.poll();
			//打印当前节点的值
			System.out.println(cur.value);
			//对当前节点指向其他节点的集合进行遍历
			for (Node node : cur.nexts) {
				//判断在Set集合中是否已经注册过了，如果没有注册过
				if (!set.contains(node)) {
					//将当前节点指向的其他节点添加到队列中，并且在Set集合中进行注册
					queue.add(node);
					set.add(node);
				}
			}
		}
	}

	/**
	 * 从from节点出发，进行深度优先遍历
	 * @param from
	 */
	public void depthFirstTraversal(Node from) {
		if (from == null) {
			return;
		}
		Stack<Node> stack = new Stack<>();
		HashSet<Node> set = new HashSet<>();
		stack.add(from);
		System.out.println(from.value);
		//将from节点进行注册
		set.add(from);
		//判断栈是否为空
		while (!stack.isEmpty()) {
			//如果栈不为空，弹出栈顶节点
			Node cur = stack.pop();
			//遍历该节点指向的其他节点的集合
			for (Node node : cur.nexts) {
				//判断该节点指向的其他节点是否在Set集合中注册过，如果没有注册
				if (!set.contains(node)) {
					//将当前节点入栈
					stack.push(cur);
					//将当前节点指向的其他节点入栈
					stack.push(node);
					//没有在Set集合中注册过并且入栈的节点，将其打印
					System.out.println(node.value);
					//将当前节点指向的其他节点在Set集合中进行注册
					set.add(node);
					break;
				}
			}
		}
	}
}
