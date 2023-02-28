package com.mashibing.test;

import java.util.ArrayList;

/**
 * @author xcy
 * @date 2022/4/14 - 11:25
 */
public class LinkedListTest {
	public static void main(String[] args) {
		/*Node head = new Node(1);
		head.next = new Node(2);
		head.next.next = new Node(3);
		Node newHead = reverseLinkedList(head);
		while (newHead != null) {
			System.out.print(newHead.value + " ");
			newHead = newHead.next;
		}
		Node node = testReverseLinkedList(head);
		while (node != null) {
			System.out.print(node.value + " ");
			node = node.next;
		}*/
		/*DoubleNode head = new DoubleNode("A");
		head.next = new DoubleNode("B");
		head.next.next = new DoubleNode("C");
		DoubleNode newDoubleHead = reverseDoubleLinkedList(head);
		while (newDoubleHead != null) {
			System.out.print(newDoubleHead.value + " ");
			newDoubleHead = newDoubleHead.next;
		}*/

		Node head = new Node(3);
		head.next = new Node(1);
		head.next.next = new Node(2);
		head.next.next.next = new Node(1);
		head.next.next.next.next = new Node(3);
		head.next.next.next.next.next = new Node(2);
		/*Node newHead = removeValue(head, 3);
		while (newHead != null) {
			System.out.print(newHead.value + " ");
			newHead = newHead.next;
		}*/

		Node node = testRemoveValue(head, 3);
		while (node != null) {
			System.out.print(node.value + " ");
			node = node.next;
		}
	}

	public static class Node {
		public Integer value;
		public Node next;

		public Node(Integer data) {
			this.value = data;
		}
	}

	/**
	 * 单链表的反转
	 *
	 * @param head
	 * @return
	 */
	public static Node reverseLinkedList(Node head) {
		Node pre = null;
		Node next = null;
		while (head != null) {
			next = head.next;
			head.next = pre;
			pre = head;
			head = next;
		}
		return pre;
	}

	/**
	 * 单链表的反转的对数器
	 *
	 * @param head
	 * @return
	 */
	public static Node testReverseLinkedList(Node head) {
		if (head == null) {
			return null;
		}
		ArrayList<Node> nodes = new ArrayList<>();
		while (head != null) {
			nodes.add(head);
			head = head.next;
		}
		nodes.get(0).next = null;

		int size = nodes.size();
		for (int i = 1; i < size; i++) {
			nodes.get(i).next = nodes.get(i - 1);
		}

		return nodes.get(size - 1);
	}

	/**
	 * 删除单链表中所有指定值的节点，并返回删除所有节点之后的新的单链表的头节点
	 *
	 * @param head  初始的单链表的头节点
	 * @param value 删除节点的指定值
	 * @return 返回删除所有节点之后的新的单链表的头节点
	 */
	public static Node removeValue(Node head, int value) {
		//指定返回的head节点
		while (head != null) {
			if (head.value != value) {
				break;
			}
			head = head.next;
		}

		Node pre = head;
		Node cur = head;

		//如果cur不为空，那么pre肯定也不为空，所以不需要判断pre是否为空
		while (cur != null) {
			//判断cur节点的value是否等于要删除节点的值
			if (cur.value == value) {
				//如果等于，说明cur节点是要删除的节点，那么pre.next指向要删除节点cur的下一个节点
				pre.next = cur.next;
			} else {
				//如果不等于，说明cur 节点不是要删除的节点
				//pre就指向当前的cur节点
				pre = cur;
			}
			//每遍历一次，cur节点就继续指向下一个节点
			cur = cur.next;
		}

		return head;
	}

	/**
	 * 删除单链表中所有指定值的节点 --> 对数器
	 * 并返回删除所有节点之后的新的单链表的头节点
	 *
	 * @param head  初始的单链表的头节点
	 * @param value 删除节点的指定值
	 * @return 返回删除所有节点之后的新的单链表的头节点
	 */
	public static Node testRemoveValue(Node head, int value) {
		if (head == null) {
			return null;
		}
		ArrayList<Node> nodes = new ArrayList<>();
		while (head != null) {
			if (head.value != value) {
				nodes.add(head);
			}
			head = head.next;
		}

		nodes.get(0).next = null;

		for (int i = 1; i < nodes.size(); i++) {
			nodes.get(i - 1).next = nodes.get(i);
		}
		return nodes.get(0);
	}

	public static class DoubleNode {
		public String value;
		public DoubleNode last;
		public DoubleNode next;

		public DoubleNode(String data) {
			this.value = data;
		}
	}

	/**
	 * 双链表的反转
	 *
	 * @param head
	 * @return
	 */
	public static DoubleNode reverseDoubleLinkedList(DoubleNode head) {
		DoubleNode pre = null;
		DoubleNode next = null;
		while (head != null) {
			next = head.next;
			head.next = pre;
			head.last = head;
			pre = head;
			head = next;
		}
		return pre;
	}
}
