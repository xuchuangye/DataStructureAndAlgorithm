package com.mashibing.node;

import java.util.ArrayList;

/**
 * @author xcy
 * @date 2022/3/12 - 15:31
 */
public class DataStructure {
	/**
	 * 单链表
	 */
	public static class Node {
		public int value;
		public Node next;

		public Node(int data) {
			this.value = data;
		}
	}

	/**
	 * 双链表
	 */
	public static class DoubleNode {
		public int value;
		public DoubleNode last;
		public DoubleNode next;

		public DoubleNode(int data) {
			this.value = data;
		}
	}

	/**
	 * 使用两个变量完成单链表的反转
	 * @param head
	 * @return
	 */
	public static Node reverseLinkedList(Node head) {
		if (head == null) {
			return null;
		}
		Node present = null;
		Node next = null;
		while (head != null) {
			next = head.next;
			head.next = present;
			present = head;
			head = next;
		}
		return present;
	}

	/**
	 * 使用容器完成单链表的反转
	 * @param head
	 * @return
	 */
	public static Node testReverseLLinkedList(Node head) {
		if (head == null) {
			return null;
		}
		ArrayList<Node> list = new ArrayList<>();
		while (head != null) {
			list.add(head);
			head = head.next;
		}
		list.get(0).next = null;
		int len = list.size();
		for (int i = 0; i < len; i++) {
			list.get(i).next = list.get(i - 1);
		}
		return list.get(len - 1);
	}


	/*public static DoubleNode reverseDoubleList(DoubleNode head) {
		if (head == null) {
			return null;
		}
		DoubleNode next = null;
		DoubleNode last = null;
		while (head != null) {
			head.next = head;
			head.last = next;
			next = head.last;

		}
		return present;
	}*/
}
