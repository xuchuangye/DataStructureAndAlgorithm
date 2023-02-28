package com.mashibing.interviewquestions;

import com.mashibing.node.Node;

/**
 * 二叉树面试题
 * 题目二：
 * 给定一颗树的根节点root
 * 判断该树的深度
 * @author xcy
 * @date 2022/4/10 - 16:59
 */
public class BinaryTreeHeight {
	public static void main(String[] args) {
		Node root = new Node(1);
		root.setLeft(new Node(2));
		root.setRight(new Node(2));
		root.getLeft().setLeft(new Node(3));
		root.getLeft().setRight(new Node(4));
		root.getRight().setLeft(new Node(4));
		root.getRight().setRight(new Node(3));

		int path = maxPath(root);
		System.out.println("树的深度为：" + path);
	}

	/**
	 * 返回树的深度
	 * @param root
	 * @return
	 */
	public static int maxPath(Node root) {
		if (root == null) {
			return 0;
		}

		return Math.max(maxPath(root.getLeft()), maxPath(root.getRight())) + 1;
	}
}
