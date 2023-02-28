package com.mashibing.LinkedList;

/**
 * @author xcy
 * @date 2021/9/18 - 20:20
 */
public class LinkedTest {
	public static class Node {
		//节点中的值
		public int value;
		//指向下一个同类型节点的指针
		public Node next;

		public Node(int data) {
			value = data;
		}
	}

	public static class DoubleNode {
		public int value;
		//指向前一个同类型节点的指针
		public DoubleNode last;
		//指向后一个同类型节点的指针
		public DoubleNode next;

		public DoubleNode(int data) {
			value = data;
		}
	}

	public static Node reverseLinkedList(Node head) {
		//前一个节点
		Node pre = null;
		//下一个节点
		Node next = null;
		//如果head为空，反转之后依然为空，直接返回pre
		while (head != null) {
			//先将head下一个的值给next
			next = head.next;
			head.next = pre;
			pre = head;
			head = next;
		}
		return pre;
	}

	public static DoubleNode reverseDoubleList(DoubleNode head) {
		//前一个节点
		DoubleNode pre = null;
		//下一个节点
		DoubleNode next = null;
		while (head != null) {
			next = head.next;

			//head的next指针指向前一个节点
			head.next = pre;
			//head的last指针指向后一个节点
			head.last = next;
			pre = head;

			head = next;
		}
		return pre;
	}

	/**
	 * 把给定值全部都删除
	 * @param head 头部节点
	 * @param num 给定值
	 * @return 新的节点
	 */
	public static Node removeValue(Node head, int num) {
		// 3 -> 4 -> 4 -> 3 -> 2
		//num = 3,那么4就是新的头部节点head
		//先遍历链表，如果不为空，则往下遍历
		while (head != null) {
			//找到不是num第一个的头部节点
			if (head.value != num) {
				break;
			}
			//返回该头部节点
			head = head.next;
		}
		//head来到第一个不需要删除的位置
		Node pre = head;

		Node cur = head;
		//删除完所有需要删除的头部节点，并按照新的头部节点往下进行遍历
		while (cur != null) {
			//从第二次开始，cur可能是需要被删除的节点，如果cur是需要被删除的节点
			if (cur.value == num) {
				pre.next = cur.next;
			} else {
				//第一次遍历，pre和cur指向同一个节点
				pre = cur;
			}
			//cur往下跳
			cur = cur.next;
		}
		return head;
	}
}