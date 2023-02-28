package com.mashibing.interviewquestions;

/**
 * 单链表的面试题
 *
 * 题目三：
 * 两个有序链表的合并
 * 给定两个有序单链表的头节点head1和head2节点
 * 返回合并之后的大链表，并且依然有序
 *
 * 举例：
 * 单链表1：1 -> 3 -> 3 -> 5 -> 7
 * 单链表2：2 -> 2 -> 3 -> 3 -> 7
 * 合并之后的大链表： 1 -> 2 -> 2 -> 3 -> 3 -> 3 -> 3 -> 5 -> 7 -> 7
 * @author xcy
 * @date 2022/4/9 - 16:10
 */
public class LinkedListInterviewQuestions003 {
	public static void main(String[] args) {
		Node head1 = new Node(1);
		head1.next = new Node(3);
		head1.next.next = new Node(5);
		head1.next.next.next = new Node(7);
		head1.next.next.next.next = new Node(9);

		Node head2 = new Node(2);
		head2.next = new Node(4);
		head2.next.next = new Node(6);
		head2.next.next.next = new Node(8);
		head2.next.next.next.next = new Node(10);

		Node head = mergeTwoLinkedList(head1, head2);
		while (head != null) {
			System.out.print(head.value + " ");
			head = head.next;
		}
	}

	public static class Node {
		public int value;
		public Node next;

		public Node(int data) {
			this.value = data;
		}
	}

	public static Node mergeTwoLinkedList(Node head1, Node head2) {
		if (head1 == null) {
			return head2;
		}
		if (head2 == null) {
			return head1;
		}

		//判断head1头节点的值和head2头节点的值谁更小，谁小谁就作为头节点
		Node head = head1.value <= head2.value ? head1 : head2;
		Node cur1 = head1.next;
		//判断head1是否作为新的头节点，如果head1作为新的头节点，那么cur2指向head2
		Node cur2 = head == head1 ? head2 : head1;
		//head节点不能移动，需要辅助节点进行
		Node pre = head;
		//判断cur1和cur2都不为空
		while (cur1 != null && cur2 != null) {
			//判断cur1节点的值是否小于等于cur2节点的值
			if (cur1.value <= cur2.value) {
				//pre.next指向cur1
				pre.next = cur1;
				//cur1继续指向下一个节点
				cur1 = cur1.next;
			}
			//否则，cur1节点的值大于cur2节点的值
			else {
				//pre.next指向cur2
				pre.next = cur2;
				//cur2继续指向下一个节点
				cur2 = cur2.next;
			}
			//pre继续指向下一个节点
			pre = pre.next;
		}
		//退出循环时，当cur1节点为空时，pre.next指向cur2，否则指向cur1
		pre.next = cur1 == null ? cur2 : cur1;
		return head;
	}
}
