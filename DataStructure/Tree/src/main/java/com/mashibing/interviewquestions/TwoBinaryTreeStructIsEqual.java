package com.mashibing.interviewquestions;

import com.mashibing.node.Node;

/**
 * 二叉树面试题
 * 题目一：
 * 给定两颗树的根节点
 * 判断两个树的结构是否一样
 * @author xcy
 * @date 2022/4/10 - 16:59
 */
public class TwoBinaryTreeStructIsEqual {
	public static void main(String[] args) {
		Node head1 = new Node(1);
		head1.setLeft(new Node(2));
		head1.setRight(new Node(3));

		Node head2 = new Node(1);
		head2.setLeft(new Node(2));
		head2.setRight(new Node(3));


		boolean equalTwoBinaryTree = isEqualTwoBinaryTree(head1, head2);
		System.out.println(equalTwoBinaryTree);
	}

	public static boolean isEqualTwoBinaryTree(Node root1, Node root2) {
		//判断root1根节点为空，root2根节点不为空或者root1根节点不为空，root2根节点为空，返回false
		if (root1 == null ^ root2 == null) {
			return false;
		}
		if (root1 == null && root2 == null) {
			return true;
		}

		return root1.getValue() == root2.getValue() && isEqualTwoBinaryTree(root1.getLeft(), root2.getLeft())  && isEqualTwoBinaryTree(root1.getRight(), root2.getRight());
	}
}
