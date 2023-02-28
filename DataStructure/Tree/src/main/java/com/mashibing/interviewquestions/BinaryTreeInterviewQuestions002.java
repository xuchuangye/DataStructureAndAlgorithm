package com.mashibing.interviewquestions;

import com.mashibing.node.Node;

/**
 * 二叉树面试题
 * 题目二：
 * 给定个颗树的根节点
 * 判断该树的结构是否是镜面树
 * @author xcy
 * @date 2022/4/10 - 16:59
 */
public class BinaryTreeInterviewQuestions002 {
	public static void main(String[] args) {
		Node root = new Node(1);
		root.setLeft(new Node(2));
		root.setRight(new Node(2));
		root.getLeft().setLeft(new Node(3));
		root.getLeft().setRight(new Node(4));
		root.getRight().setLeft(new Node(4));
		root.getRight().setRight(new Node(3));

		boolean symmetric = isSymmetric(root);
		System.out.println(symmetric);
	}
	public static boolean isSymmetric(Node root) {
		return isMirror(root, root);
	}

	public static boolean isMirror(Node root1, Node root2) {
		//判断root1根节点为空，root2根节点不为空或者root1根节点不为空，root2根节点为空，返回false
		if (root1 == null ^ root2 == null) {
			return false;
		}
		if (root1 == null && root2 == null) {
			return true;
		}

		return root1.getValue() == root2.getValue() && isMirror(root1.getLeft(), root2.getRight())  && isMirror(root1.getRight(), root2.getLeft());
	}
}
