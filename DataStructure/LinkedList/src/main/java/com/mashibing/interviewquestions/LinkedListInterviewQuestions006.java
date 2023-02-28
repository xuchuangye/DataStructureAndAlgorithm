package com.mashibing.interviewquestions;


import com.mashibing.linkedlist.LinkedListCode001;
import com.mashibing.node.Node;

import java.util.Stack;

/**
 * 单向链表的应用实例：
 * 给定一个单链表的头节点，查看该链表是否满足回文结构
 *
 * 1、栈的方式（笔试用）
 * 2、改原链表的方式就需要注意边界的问题（面试用）
 * 举例：
 * 节点个数为奇数的单链表： 1 -> 2 -> 3 -> 2 -> 1
 * 节点个数为偶数的单链表： 1 -> 2 -> 3 -> 3 -> 2 -> 1
 *
 * @author xcy
 * @date 2022/3/13 - 17:26
 */
public class LinkedListInterviewQuestions006 {

	public static void main(String[] args) {
		LinkedListCode001.MyLinkedList myLinkedList = new LinkedListCode001.MyLinkedList();
		myLinkedList.add(new Node(1));
		myLinkedList.add(new Node(2));
		myLinkedList.add(new Node(3));
		myLinkedList.add(new Node(3));
		myLinkedList.add(new Node(2));
		myLinkedList.add(new Node(1));
		Node head = myLinkedList.getHead();
		System.out.println(isPalindromeOptimalSolution(head));
		System.out.println(isPalindrome(head));
	}

	/**
	 * 判断单链表是否满足回文结构
	 * <p>
	 * 时间复杂度：O(N)
	 *
	 * @param head 单链表的头节点
	 * @return true表示满足回文结构
	 */
	public static boolean isPalindrome(Node head) {
		Stack<Node> stack = new Stack<>();
		Node cur = head;
		while (cur != null) {
			stack.push(cur);
			cur = cur.next;
		}
		while (head != null) {
			if (head.value != stack.pop().value) {
				return false;
			}
			head = head.next;
		}
		return true;
	}

	/**
	 * 判断单链表是否满足回文结构 --> 最优解
	 * <p>
	 * 时间复杂度：O(logN)
	 *
	 * @param head 单链表的头节点
	 * @return true表示满足回文结构
	 */
	public static boolean isPalindromeOptimalSolution(Node head) {
		if (head == null || head.next == null) {
			return true;
		}

		//1.
		Node cur1 = head;
		Node cur2 = head;
		//当cur2走完之后，cur1此时在中间
		while (cur1.next != null && cur2.next.next != null) {
			cur1 = cur1.next;
			cur2 = cur2.next.next;
		}

		//2.
		cur2 = cur1.next;
		cur1.next = null;
		Node cur3 = null;
		while (cur2 != null) {
			cur3 = cur2.next;
			cur2.next = cur1;
			cur1 = cur2;
			cur2 = cur3;
		}

		//3.
		cur3 = cur1;
		cur2 = head;
		boolean isEqual = true;
		while (cur1 != null && cur2 != null) {
			if (cur1.value != cur2.value) {
				isEqual = false;
				break;
			}
			cur1 = cur1.next;
			cur2 = cur2.next;
		}

		//4.
		cur1 = cur3.next;
		cur3.next = null;
		while (cur1 != null) {
			cur2 = cur1.next;
			cur1.next = cur3;
			cur3 = cur1;
			cur1 = cur2;
		}

		return isEqual;
	}
}
