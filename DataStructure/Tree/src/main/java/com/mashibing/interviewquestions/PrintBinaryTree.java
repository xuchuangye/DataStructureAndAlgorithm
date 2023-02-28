package com.mashibing.interviewquestions;

import com.mashibing.node.Node;

/**
 * @author xcy
 * @date 2022/4/28 - 11:30
 */
public class PrintBinaryTree {
	public static void main(String[] args) {
		Node head = new Node(1);
		head.setLeft(new Node(-222222222));
		head.setRight(new Node(3));
		head.getLeft().setLeft(new Node(Integer.MIN_VALUE));
		head.getRight().setLeft(new Node(55555555));
		head.getRight().setRight(new Node(66));
		head.getLeft().getLeft().setRight(new Node(777));
		printTree(head);

		head = new Node(1);
		head.setLeft(new Node(2));
		head.setRight(new Node(3));
		head.getLeft().setLeft(new Node(4));
		head.getRight().setLeft(new Node(5));
		head.getRight().setRight(new Node(6));
		head.getLeft().getLeft().setRight(new Node(7));
		printTree(head);

		head = new Node(1);
		head.setLeft(new Node(1));
		head.setRight(new Node(1));
		head.getLeft().setLeft(new Node(1));
		head.getRight().setLeft(new Node(1));
		head.getRight().setRight(new Node(1));
		head.getLeft().getLeft().setRight(new Node(1));
		printTree(head);
	}

	public static void printTree(Node head) {
		System.out.println("Binary Tree:");
		printInOrder(head, 0, "H", 17);
		System.out.println();
	}

	public static void printInOrder(Node head, int height, String to, int len) {
		if (head == null) {
			return;
		}
		printInOrder(head.getRight(), height + 1, "v", len);
		String val = to + head.getValue() + to;
		int lenM = val.length();
		int lenL = (len - lenM) / 2;
		int lenR = len - lenM - lenL;
		val = getSpace(lenL) + val + getSpace(lenR);
		System.out.println(getSpace(height * len) + val);
		printInOrder(head.getLeft(), height + 1, "^", len);
	}

	public static String getSpace(int num) {
		String space = " ";
		StringBuffer buf = new StringBuffer("");
		for (int i = 0; i < num; i++) {
			buf.append(space);
		}
		return buf.toString();
	}
}
