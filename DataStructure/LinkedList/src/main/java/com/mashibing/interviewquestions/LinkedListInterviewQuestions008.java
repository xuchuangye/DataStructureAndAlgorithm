package com.mashibing.interviewquestions;


import com.mashibing.linkedlist.LinkedListCode001;
import com.mashibing.node.Node;

import java.util.HashMap;

/**
 * 单向链表的应用实例：
 * 一种特殊的单链表节点类描述如下：
 * class Node {
 * int value;
 * Node next;
 * Node rand;
 * public Node(int value) {this.value = value}
 * }
 * rand指针是单链表节点结构中新增的指针，rand可能指向链表中的任意一个节点，也可能指向null
 * 给定一个由Node节点类型组成的无环单链表的头节点head，请实现一个函数完成这个链表的复制，并返回复制的新链表的头节点
 * 要求：
 * 时间复杂度：O(N)，额外空间复杂度：O(1)
 *
 * @author xcy
 * @date 2022/4/26 - 08:59
 */
public class LinkedListInterviewQuestions008 {

	public static void main(String[] args) {
		LinkedListCode001.MyLinkedList myLinkedList = new LinkedListCode001.MyLinkedList();
		myLinkedList.add(new Node(4));
		myLinkedList.add(new Node(5));
		myLinkedList.add(new Node(7));
		myLinkedList.add(new Node(19));
		myLinkedList.add(new Node(9));
		Node head = myLinkedList.getHead();
		Node copyHead = copyLinkedList(head);
		while (copyHead != null) {
			System.out.println(copyHead);
			copyHead = copyHead.next;
		}
	}

	/**
	 * 使用HashMap拷贝原来的单链表
	 *
	 * @param head 原来的头节点
	 * @return 返回拷贝之后的新链表的头节点
	 */
	public static Node copyLinkedList(Node head) {
		if (head == null) {
			return null;
		}
		HashMap<Node, Node> hashMap = new HashMap<>();

		Node cur = head;
		while (cur != null) {
			//cur作为原来的节点，使用原来节点的值作为拷贝的新节点
			hashMap.put(cur, new Node(cur.value));
			cur = cur.next;
		}
		//cur重新回到头节点的位置
		cur = head;

		while (cur != null) {
			//拷贝的新节点cur的next指向cur.next对应的新节点
			hashMap.get(cur).next = hashMap.get(cur.next);
			//拷贝的新节点cur的rand指向cur.rand对应的新节点
			hashMap.get(cur).rand = hashMap.get(cur.rand);
			cur = cur.next;
		}
		return hashMap.get(head);
	}

	/**
	 * @param head
	 * @return
	 */
	public static Node copyLinkedListWithRand(Node head) {
		if (head == null) {
			return null;
		}

		Node cur = head;
		Node next = null;

		while (cur != null) {
			//next记录cur.next
			next = cur.next;
			//使用cur.value作为拷贝cur节点的新节点
			cur.next = new Node(cur.value);
			//cur.next的next指向next
			cur.next.next = next;
			//cur来到next的位置
			cur = next;
		}

		Node curCopy = null;
		//cur重新回到head的位置
		cur = head;
		while (cur != null) {
			//next记录cur.next.next
			next = cur.next.next;
			//curCopy记录cur.next，也就是拷贝的新节点
			curCopy = cur.next;
			//新节点的rand指针根据原来的节点的rand来进行连接
			//如果原来节点rand指针不为空，则新节点rand指针指向原来节点的rand指针指向的节点的next节点
			//如果原来节点rand指针为空，那么新节点的rand也为空
			curCopy.rand = cur.rand != null ? cur.rand.next : null;
			//cur指针来到next的位置
			cur = next;
		}
		//res记录head的next节点
		Node res = head.next;
		//cur重新回到head节点
		cur = head;

		//将原来节点和新节点组成的链表进行拆分
		//拆分为原来节点组成的链表和新节点组成的链表
		while (cur != null) {
			//next记录cur.next.next，也就是next记录下一个原来的节点
			next = cur.next.next;
			//curCopy记录cur.next，也就是拷贝的新节点
			curCopy = cur.next;
			//原来的节点的next指向下一个原来的节点
			cur.next = next;
			//新节点的next指向下一个新的节点
			//如果next为空，说明已经没有原来的节点，那么自然也就不会有新的节点
			curCopy.next = next != null ? next.next : null;
			//cur来到next的位置
			cur = next;
		}
		return res;
	}
}
