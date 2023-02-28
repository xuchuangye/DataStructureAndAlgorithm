package com.mashibing.interviewquestions;

/**
 * 二叉树的面试题
 * 二叉树的结构如下定义：
 * public static class Node {
 * 	public int value;
 * 	public Node left;
 * 	public Node right;
 * 	public Node parent;
 * 	public Node(int value) {
 * 		this.value = value;
 *  }
 * }
 * 给定一个二叉树中的某一个节点，返回该节点的后继节点
 * @author xcy
 * @date 2022/4/29 - 9:06
 */
public class SuccessorNode {
	public static void main(String[] args) {
		Node head = new Node(6);
		head.parent = null;
		head.left = new Node(3);
		head.left.parent = head;
		head.left.left = new Node(1);
		head.left.left.parent = head.left;
		head.left.left.right = new Node(2);
		head.left.left.right.parent = head.left.left;
		head.left.right = new Node(4);
		head.left.right.parent = head.left;
		head.left.right.right = new Node(5);
		head.left.right.right.parent = head.left.right;
		head.right = new Node(9);
		head.right.parent = head;
		head.right.left = new Node(8);
		head.right.left.parent = head.right;
		head.right.left.left = new Node(7);
		head.right.left.left.parent = head.right.left;
		head.right.right = new Node(10);
		head.right.right.parent = head.right;

		Node test = head.left.left;
		System.out.println(test.value + " next: " + getSuccessorNode(test).value);
		test = head.left.left.right;
		System.out.println(test.value + " next: " + getSuccessorNode(test).value);
		test = head.left;
		System.out.println(test.value + " next: " + getSuccessorNode(test).value);
		test = head.left.right;
		System.out.println(test.value + " next: " + getSuccessorNode(test).value);
		test = head.left.right.right;
		System.out.println(test.value + " next: " + getSuccessorNode(test).value);
		test = head;
		System.out.println(test.value + " next: " + getSuccessorNode(test).value);
		test = head.right.left.left;
		System.out.println(test.value + " next: " + getSuccessorNode(test).value);
		test = head.right.left;
		System.out.println(test.value + " next: " + getSuccessorNode(test).value);
		test = head.right;
		System.out.println(test.value + " next: " + getSuccessorNode(test).value);
		test = head.right.right; // 10's next is null
		System.out.println(test.value + " next: " + getSuccessorNode(test));
	}
	public static class Node {
		public int value;
		public Node left;
		public Node right;
		public Node parent;
		public Node(int value) {
			this.value = value;
		}
	}

	/**
	 * 获取当前节点的后继节点
	 * @param node 当前节点
	 * @return 返回当前节点的后继节点
	 */
	public static Node getSuccessorNode(Node node) {
		if (node == null) {
			return null;
		}
		//如果当前节点的右子树不为空
		if (node.right != null) {
			//返回当前节点右子树的最左节点
			return getLeftNode(node.right);
		}else {
			Node parent = node.parent;
			//parent == null表示当前node节点已经是整棵树的最右节点，没有后继节点
			//parent.right != node表示当前node节点是parent节点的左子节点，那么后继节点就是parent节点，直接返回
			while (parent != null && parent.right == node) {
				//当前node节点来到parent节点的位置
				node = parent;
				//parent节点来到自己节点的parent节点的位置
				parent = node.parent;
			}
			return parent;
		}
	}

	/**
	 * 获取最左节点
	 * @param node 当前节点
	 * @return 返回当前节点的最左节点
	 */
	public static Node getLeftNode(Node node) {
		if (node == null) {
			return null;
		}
		while (node.left != null) {
			node = node.left;
		}
		return node;
	}
}
