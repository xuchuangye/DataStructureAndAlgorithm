package com.mashibing.interviewquestions;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 单链表的面试题
 *
 * 题目三：
 * 合并k个升序链表
 * 给定一个链表的数组，每一个链表都已经按升序排列
 * 返回合并所有有序链表之后的大链表，并且依然有序
 *
 * 举例：
 * 单链表1：1 -> 3 -> 3 -> 5 -> 7
 * 单链表2：2 -> 2 -> 3 -> 3 -> 7
 * 合并之后的大链表： 1 -> 2 -> 2 -> 3 -> 3 -> 3 -> 3 -> 5 -> 7 -> 7
 * @author xcy
 * @date 2022/4/9 - 16:10
 */
public class LinkedListInterviewQuestions004 {
	public static class ListNode {
		public int value;
		public ListNode next;

		public ListNode(int data) {
			this.value = data;
		}
	}
	public static void main(String[] args) {
		ListNode head1 = new ListNode(1);
		head1.next = new ListNode(3);
		head1.next.next = new ListNode(5);
		head1.next.next.next = new ListNode(7);
		head1.next.next.next.next = new ListNode(9);

		ListNode head2 = new ListNode(2);
		head2.next = new ListNode(4);
		head2.next.next = new ListNode(6);
		head2.next.next.next = new ListNode(8);
		head2.next.next.next.next = new ListNode(10);

		ListNode[] listNodes = new ListNode[]{head1, head2};
		ListNode head = mergeKLinkedList(listNodes);
		while (head != null) {
			System.out.print(head.value + " ");
			head = head.next;
		}

	}

	/**
	 * 合并K个升序链表
	 * @param lists K个升序链表的头节点组成的数组
	 * @return 返回合并K个升序链表的大链表的头节点
	 */
	private static ListNode mergeKLinkedList(ListNode[] lists) {
		//使用堆，并且使用自定义比较器进行排序
 		PriorityQueue<ListNode> heap = new PriorityQueue<>(new MyComparator());


		//将链表数组的每个头节点依次添加到堆中
		for (ListNode listNode : lists) {
			//判断是否为空，不为空再进行添加
			if (listNode != null) {
				heap.add(listNode);
			}
		}
		//如果堆为空，直接返回null
		if (heap.isEmpty()) {
			return null;
		}

		//取出头节点head
		ListNode head = heap.poll();
		//因为head头节点不能移动，所以需要辅助节点pre节点来完成
		ListNode pre = head;

		//判断pre的下一个节点是否为空，不为空就添加到堆中
		if (pre.next != null) {
			heap.add(pre.next);
		}

		//判断堆是否为空
		while (!heap.isEmpty()) {
			//依次取出
			ListNode cur = heap.poll();
			pre.next = cur;
			pre = cur;
			if (cur.next != null) {
				heap.add(cur.next);
			}
		}

		return head;
	}

	public static class MyComparator implements Comparator<ListNode> {
		@Override
		public int compare(ListNode o1, ListNode o2) {
			return o1.value - o2.value;
		}
	}
}
