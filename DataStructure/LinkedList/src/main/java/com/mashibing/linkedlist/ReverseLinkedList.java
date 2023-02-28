package com.mashibing.linkedlist;

/**
 * 单链表和双向链表的反转
 * @author xcy
 * @date 2022/4/8 - 16:52
 */
public class ReverseLinkedList {
	public static void main(String[] args) {
		/*ListNode head = new ListNode(1);
		head.next = new ListNode(2);
		head.next.next = new ListNode(3);
		ListNode newHead = reverseLinkedList(head);
		while (newHead != null) {
			System.out.println(newHead.value);
			newHead = newHead.next;
		}*/

		DoubleNode head = new DoubleNode(1);
		head.next = new DoubleNode(2);
		head.next.next = new DoubleNode(3);
		/*while (head != null) {
			System.out.print(head.value + " ");
			head = head.next;
		}*/
		DoubleNode newHead = reverseDoubleLinkedList(head);
		while (newHead != null) {
			System.out.print(newHead.value + " ");
			newHead = newHead.next;
		}
	}

	public static class Node {
		public int value;
		public Node next;

		public Node(int data) {
			this.value = data;
		}
	}

	public static class DoubleNode {
		public int value;
		public DoubleNode next;
		public DoubleNode last;

		public DoubleNode(int data) {
			this.value = data;
		}
	}

	/**
	 * 单链表的逆序输出
	 * @param head
	 * @return
	 */
	public static Node reverseLinkedList(Node head) {
		Node pre = null;
		Node next = null;
		while (head != null) {
			//next记录head.next
			next = head.next;
			//head.next指向pre
			head.next = pre;
			//pre指向head
			pre = head;
			//head指向next
			head = next;
		}
		return pre;
	}


	public static DoubleNode reverseDoubleLinkedList(DoubleNode head) {
		DoubleNode pre = null;
		DoubleNode next = null;
		while (head != null) {
			//next记录head.next
			next = head.next;
			//head.next指向pre
			head.next = pre;
			//head.last指向next
			head.last = next;
			//pre指向head
			pre = head;
			//head指向next
			head = next;
		}
		return pre;
	}
}
