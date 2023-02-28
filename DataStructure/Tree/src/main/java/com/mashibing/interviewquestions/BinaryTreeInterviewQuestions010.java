package com.mashibing.interviewquestions;

import com.mashibing.node.Node;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;


/**
 * 二叉树面试题
 * <p>
 * 实现二叉树的层序遍历
 * 1、其实就是宽度的优先遍历，使用队列
 * 2、可以通过设置flag变量的方式，来发现某一层的结束
 * <p>
 * 基本思路：
 * 1、队列弹出一个节点cur，并且打印
 * 2、判断cur节点是否有子节点，如果有左子节点，左子节点入队，如果有右子节点，右子节点入队
 *
 * @author xcy
 * @date 2022/4/10 - 16:59
 */
public class BinaryTreeInterviewQuestions010 {
	public static void main(String[] args) {
		Node root = new Node(1);
		root.setLeft(new Node(2));
		root.setRight(new Node(3));
		root.getLeft().setLeft(new Node(4));
		root.getLeft().setRight(new Node(5));
		root.getRight().setLeft(new Node(6));
		root.getRight().setRight(new Node(7));
		levelOrder(root);
	}

	/**
	 * 层序遍历
	 *
	 * @param root 二叉树的根节点
	 */
	public static void levelOrder(Node root) {
		if (root == null) {
			System.out.println("二叉树的节点为空，无法层序遍历");
			return;
		}
		//LinkedList底层是双端队列
		Queue<Node> queue = new LinkedList<>();
		queue.add(root);
		while (!queue.isEmpty()) {
			Node cur = queue.poll();
			System.out.println(cur);
			//有左子节点，压入左子节点
			if (cur.getLeft() != null) {
				queue.add(cur.getLeft());
			}
			//有右子节点，压入右子节点
			if (cur.getRight() != null) {
				queue.add(cur.getRight());
			}
		}
	}
}
