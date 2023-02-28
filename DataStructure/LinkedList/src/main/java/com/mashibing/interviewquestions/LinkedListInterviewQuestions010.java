package com.mashibing.interviewquestions;


import com.mashibing.linkedlist.LinkedListCode001;
import com.mashibing.node.Node;

/**
 * 单向链表的应用实例：
 *
 * @author xcy
 * @date 2022/4/26 - 08:59
 */
public class LinkedListInterviewQuestions010 {

	public static void main(String[] args) {
		LinkedListCode001.MyLinkedList myLinkedList1 = new LinkedListCode001.MyLinkedList();
		myLinkedList1.add(new Node(4));
		myLinkedList1.add(new Node(5));
		myLinkedList1.add(new Node(7));
		myLinkedList1.add(new Node(19));
		myLinkedList1.add(new Node(9));
		Node head1 = myLinkedList1.getHead();

		/*LinkedListCode001.MyLinkedList myLinkedList2 = new LinkedListCode001.MyLinkedList();
		myLinkedList2.add(new Node(4));
		myLinkedList2.add(new Node(5));
		myLinkedList2.add(new Node(7));
		myLinkedList2.add(new Node(19));
		myLinkedList2.add(new Node(9));
		myLinkedList2.add(head1);
		Node head2 = myLinkedList2.getHead();*/
		Node head = deleteNode(head1, new Node(7));
		while (head != null) {
			System.out.println(head);
			head = head.next;
		}

	}

	/**
	 *
	 * @param head 单链表的头节点
	 * @param node 删除指定的节点
	 * @return 如果删除的是头节点，返回新链表的头节点，如果删除的不是头节点，返回删除指定节点之后的头节点
	 */
	public static Node deleteNode(Node head, Node node) {
		//头节点为空无法删除指定节点
		//节点为空，没有必要删除
		if (head == null || node == null) {
			return null;
		}

		//情况一：删除的节点不是头节点
		Node cur = head.next;
		if (head.value == node.value) {
			return head.next;
		}else {
			while (cur != null) {
				//如果要删除的节点不是头节点
				if (cur.next.value == node.value) {
					cur.next = cur.next.next;
				}
				if (cur.value == node.value) {
					return cur;
				}
				cur = cur.next;
			}
		}

		return head;
	}
}
