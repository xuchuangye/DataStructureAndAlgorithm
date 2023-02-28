package com.mashibing.linkedlist;

import com.mashibing.node.Node;

/**
 * 单链表的练习题：
 * 情况一、
 * 1、单链表中如果节点个数为奇数，返回唯一的中间节点
 * 2、单链表中如果节点个数为偶数，返回前一个中间节点
 * 情况二、
 * 1、单链表中如果节点个数为奇数，返回唯一的中间节点
 * 2、单链表中如果节点个数为偶数，返回后一个中间节点
 * 情况三、
 * 1、单链表中如果节点个数为奇数，返回唯一的中间节点的前一个节点
 * 2、单链表中如果节点个数为偶数，返回前一个中间节点的前一个节点
 * 情况四、
 * 1、单链表中如果节点个数为奇数，返回唯一的中间节点的前一个节点
 * 2、单链表中如果节点个数为偶数，返回后一个中间节点的前一个节点
 *
 * @author xcy
 * @date 2022/4/24 - 15:21
 */
public class LinkedListCode001 {
	public static void main(String[] args) {
		MyLinkedList myLinkedList = new MyLinkedList();
		myLinkedList.add(new Node(1));
		myLinkedList.add(new Node(2));
		myLinkedList.add(new Node(3));
		myLinkedList.add(new Node(4));
		myLinkedList.add(new Node(5));
		myLinkedList.add(new Node(6));
		myLinkedList.add(new Node(7));
		myLinkedList.add(new Node(8));
		//myLinkedList.add(new Node(9));
		//myLinkedList.add(new Node(10));
		System.out.println("单链表的节点个数：" + myLinkedList.getNodeCount());
		/*Node head1 = myLinkedList.getHead();
		System.out.println("单链表的头节点：" + head1);
		System.out.println("单链表的中间节点：" + myLinkedList.midOrPreMidNode(head1));*/
		/*Node head2 = myLinkedList.getHead();
		System.out.println("单链表的中间节点：" + myLinkedList.midOrPostMidNode(head2));*/
		/*Node head3 = myLinkedList.getHead();
		System.out.println("单链表中间节点的前一个节点：" + myLinkedList.midOrMidNodePreNode(head3));*/
		Node head4 = myLinkedList.getHead();
		System.out.println("单链表中间节点的前一个节点：" + myLinkedList.midOrMidNodePostNode(head4));
		System.out.println("单链表节点的遍历：");
		myLinkedList.list();
	}

	public static class MyLinkedList {
		private Node head;

		private int nodeCount;

		public MyLinkedList() {
			head = null;
			nodeCount = 0;
		}

		public Node getHead() {
			return this.head;
		}

		public void add(Node node) {
			if (node == null) {
				return;
			}
			nodeCount++;
			node.next = head;
			head = node;
		}

		public void list() {
			if (head == null) {
				System.out.println("单链表的头节点为空，无法进行遍历");
				return;
			}
			while (head != null) {
				System.out.println(head);
				head = head.next;
			}
		}

		public int getNodeCount() {
			return this.nodeCount;
		}

		/**
		 * 情况一
		 * 如果单链表的节点个数为奇数，返回唯一的中间节点
		 * 如果单链表的节点个数为偶数，返回前一个中间节点
		 *
		 * @param head 单链表的头节点
		 * @return 节点个数为奇数，返回唯一的中间节点，节点个数为偶数，返回前一个中间节点
		 */
		public Node midOrPreMidNode(Node head) {
			if (nodeCount == 0) {
				return null;
			}
			if (nodeCount == 1) {
				return head;
			}
			int mid = (int) Math.ceil(((double)(nodeCount + 1) / 2));

			Node cur = head;

			while (cur != null) {
				if (nodeCount == mid) {
					break;
				}
				nodeCount--;
				cur = cur.next;
			}
			return cur;
		}

		/**
		 * 情况二
		 * 如果单链表的节点个数为奇数，返回唯一的中间节点
		 * 如果单链表的节点个数为偶数，返回后一个中间节点
		 *
		 * @param head 单链表的头节点
		 * @return 节点个数为奇数，返回唯一的中间节点，节点个数为偶数，返回后一个中间节点
		 */
		public Node midOrPostMidNode(Node head) {
			if (nodeCount == 0) {
				return null;
			}
			if (nodeCount == 1) {
				return head;
			}
			int mid = (int) Math.ceil(((double)(nodeCount) / 2));

			Node cur = head;
			while (cur != null) {
				if (nodeCount == mid) {
					break;
				}
				nodeCount--;
				cur = cur.next;
			}
			return cur;
		}

		/**
		 * 情况三、
		 * 1、单链表中如果节点个数为奇数，返回唯一的中间节点的前一个节点
		 * 2、单链表中如果节点个数为偶数，返回前一个中间节点的前一个节点
		 * @param head 单链表的头节点
		 * @return 节点个数为奇数，返回唯一的中间节点的前一个节点，节点个数为偶数，返回前一个中间节点的的前一个节点
		 */
		public Node midOrMidNodePreNode(Node head) {
			if (nodeCount == 0) {
				return null;
			}
			if (nodeCount == 1) {
				return head;
			}
			int mid = (int) Math.ceil(((double)(nodeCount + 1) / 2));

			Node cur = head;
			while (cur != null) {
				nodeCount--;
				if (nodeCount == mid) {
					break;
				}
				cur = cur.next;
			}
			return cur;
		}

		/**
		 * 情况四、
		 * 1、单链表中如果节点个数为奇数，返回唯一的中间节点的前一个节点
		 * 2、单链表中如果节点个数为偶数，返回后一个中间节点的前一个节点
		 * @param head 单链表的头节点
		 * @return 节点个数为奇数，返回唯一的中间节点的前一个节点，节点个数为偶数，返回后一个中间节点的的前一个节点
		 */
		public Node midOrMidNodePostNode(Node head) {
			if (nodeCount == 0) {
				return null;
			}
			if (nodeCount == 1) {
				return head;
			}
			int mid = (int) Math.ceil(((double)(nodeCount) / 2));

			Node cur = head;
			while (cur != null) {
				nodeCount--;
				if (nodeCount == mid) {
					break;
				}
				cur = cur.next;
			}
			return cur;
		}
	}
}
