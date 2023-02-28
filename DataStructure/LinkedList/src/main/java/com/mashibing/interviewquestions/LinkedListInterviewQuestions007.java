package com.mashibing.interviewquestions;


import com.mashibing.linkedlist.LinkedListCode001;
import com.mashibing.node.Node;

import java.util.Stack;

/**
 * 单向链表的应用实例：
 * 将单链表按照指定值进行划分：小于该值放左边，与该值相等的放中间，大于该值的放右边的形式
 * <p>
 * 1、将链表放入数组中，在数组中做partition（笔试用）
 * 2、分成小、中、大三部分，再将各个部分之间串联起来（面试用）
 * 举例：
 * 指定值 5
 * 原始的单链表： 5 -> 1 -> 3 -> 7 -> 8 -> 5
 * 调整过后的单链表： 1 -> 3 -> 5 -> 5 -> 7 -> 8
 *
 * @author xcy
 * @date 2022/3/13 - 17:26
 */
public class LinkedListInterviewQuestions007 {

	public static void main(String[] args) {
		LinkedListCode001.MyLinkedList myLinkedList = new LinkedListCode001.MyLinkedList();
		myLinkedList.add(new Node(4));
		myLinkedList.add(new Node(5));
		myLinkedList.add(new Node(7));
		myLinkedList.add(new Node(19));
		myLinkedList.add(new Node(9));
		Node head = myLinkedList.getHead();
		Node newNode = LinkedListPartition001(head, 3);
		while (newNode != null) {
			System.out.println(newNode);
			newNode = newNode.next;
		}
	}

	/**
	 * 判断单链表是否满足回文结构
	 * <p>
	 * 时间复杂度：O(N)
	 *
	 * @param nodeArr 单链表的头节点
	 * @return true表示满足回文结构
	 */
	public static void arrPartition(Node[] nodeArr, int pivot) {
		int small = -1;
		int big = nodeArr.length;
		int index = 0;

		while (index != big) {
			if (nodeArr[index].value > pivot) {
				swap(nodeArr, ++small, index++);
			} else if (nodeArr[index].value == pivot) {
				index++;
			} else {
				swap(nodeArr, --big, index);
			}
		}
	}

	public static void swap(Node[] nodeArr, int i, int j) {
		Node temp = nodeArr[i];
		nodeArr[i] = nodeArr[j];
		nodeArr[j] = temp;
	}

	/**
	 * 判断单链表是否满足回文结构 --> 最优解
	 * <p>
	 * 时间复杂度：O(logN)
	 *
	 * @param head 单链表的头节点
	 * @return true表示满足回文结构
	 */
	public static Node LinkedListPartition001(Node head, int value) {
		if (head == null) {
			return null;
		}
		//小于区的头部
		Node ltL = null;
		//小于区的尾部
		Node ltR = null;

		//等于区的头部
		Node etL = null;
		//等于区的尾部
		Node etR = null;

		//大于区的头部
		Node gtL = null;
		//大于区的尾部
		Node gtR = null;

		Node cur = head;
		while (cur != null) {
			if (cur.value < value) {
				if (ltL == null) {
					ltL = cur;
				} else {
					ltR.next = cur;
				}
				ltR = cur;
			} else if (cur.value == value) {
				if (etL == null) {
					etL = cur;
				} else {
					etR.next = cur;
				}
				etR = cur;
			} else {
				if (gtL == null) {
					gtL = cur;
				} else {
					gtR.next = cur;
				}
				gtR = cur;
			}
			cur = cur.next;
		}
		//如果小于区的尾部不为空，证明存在小于区的链表
		if (ltR != null) {
			//让小于区的尾部的next指向等于区的头部
			ltR.next = etL;
			//判断等于区的尾部是否为空，如果为空，证明不存在等于区，直接小于区的尾部连接大于区的头部
			etR = etR == null ? ltR : etR;
		}
		//如果等于区的尾部不为空，证明存在等于区的链表
		if (etR != null) {
			//让等于区的尾部的next指向大于区的头部
			etR.next = gtL;
		}

		if (gtR != null) {
			gtR.next = null;
		}
		//1.判断小于区的头部是否为空，如果不为空，返回小于区的头部
		//2.如果小于区的头部为空，证明小于区不存在，判断等于区的头部是否为空
		//3.如果等于区的头部不为空，证明等于区存在，返回等于区的头部
		//4.如果等于区的头部为空，证明等于区不存在，直接返回大于区的头部
		//5.因为如果只剩下大于区，那么就不需要在进行任何的连接了
		//6.如果大于区的头部为空，返回null，如果不为空，返回 整个大于区链表
		return ltL != null ? ltL : (etL != null ? etL : gtL);
	}

	/**
	 * @param head
	 * @param value
	 * @return
	 */
	public static Node LinkedListPartition002(Node head, int value) {
		if (head == null) {
			return null;
		}
		//小于区的头部
		Node ltL = null;
		//小于区的尾部
		Node ltR = null;

		//等于区的头部
		Node etL = null;
		//等于区的尾部
		Node etR = null;

		//大于区的头部
		Node gtL = null;
		//大于区的尾部
		Node gtR = null;

		Node next = null;

		while (head != null) {
			//next记录head.next
			next = head.next;
			//head.next指向null
			head.next = null;

			if (head.value < value) {
				if (ltL == null) {
					ltL = head;
					ltR = head;
				}else {
					ltR.next = head;
					ltR = head;
				}
			}else if (head.value == value) {
				if (etL == null) {
					etL = head;
					etR = head;
				}else {
					etR.next = head;
					etR = head;
				}
			}else {
				if (gtL == null) {
					gtL = head;
					gtR = head;
				}else {
					gtR.next = head;
					gtR = head;
				}
			}

			//head来到next
			head = next;
		}
		//判断小于区的尾部是否为空，如果不为空，证明小于区存在，是可以连接等于区
		if (ltR != null) {
			//小于区的尾部的next连接等于区的头部
			ltR.next = etL;
			//判断等于区的尾部是否为空，
			//如果为空，证明等于区不存在，让小于区的尾部作为等于区的尾部
			//如果不为空，证明等于区存在，返回等于区的尾部
			etR = etR == null ? ltR : etR;
		}
		//判断等于区的尾部是否为空
		//如果不为空，证明等于区存在，是可以连接大于区
		if (etR != null) {
			//等于区的尾部的next连接大于区的头部
			etR.next = gtL;
		}
		//1.判断小于区的头部是否为空，如果不为空，返回小于区的头部
		//2.如果小于区的头部为空，证明小于区不存在，判断等于区的头部是否为空
		//3.如果等于区的头部不为空，证明等于区存在，返回等于区的头部
		//4.如果等于区的头部为空，证明等于区不存在，直接返回大于区的头部
		//5.因为如果只剩下大于区，那么就不需要在进行任何的连接了
		//6.如果大于区的头部为空，返回null，如果不为空，返回 整个大于区链表
		return ltL != null ? ltL : (etL != null ? etL : gtL);
	}
}
