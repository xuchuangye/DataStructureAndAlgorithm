package com.mashibing.interviewquestions;


import com.mashibing.linkedlist.LinkedListCode001;
import com.mashibing.node.Node;

import java.util.HashMap;

/**
 * 单向链表的应用实例：
 * 给定两个可能有环也可能无环的单链表，头节点head1和head2
 * 请实现一个函数，如果两个链表相交，请返回相交的第一个 节点
 * 如果不相交，返回null
 * 要求：
 * 如果两个链表的长度之和为N，时间复杂度达到O(N)，额外空间复杂度达到O(1)
 * <p>
 * 解题思路：
 * 一、判断单链表是否有环，如果有环，返回第一个入环节点，如果没有，返回null --> getLinkedListFirstLoopNode(Node head)
 * 二、两个链表的几种情况：
 *     1.第一个链表和第二个链表都是无环链表 --> TwoLinkedListNoLoopNode(Node head1, Node head2)
 *     2.第一个链表是无环链表，第二个链表是有环链表 --> 因为是单链表，节点只会有一个指针，所以两个链表不可能相交
 *     3.第一个链表是有环链表，第二个链表是无环链表 --> 因为是单链表，节点只会有一个指针，所以两个链表不可能相交
 *     4.第一个链表和第二个链表都是有环链表 -->
 *         1)、第一个链表和第二个链表不相交，各自独立
 *         |            |
 *         |            |
 *         |————|       |————|
 *         |————|       |————|
 *         2)、两个链表相交，但是环处于相交的节点位置以外，也就是入环节点只有一个
 *              \    /
 *               \  /
 *                |
 *               / \
 *              /   \
 *             ——  ——
 *         3)、两个链表相交，入环节点不止一个
 *         \          /
 *          \        /
 *          /—— —— ——\
 *          \—— —— ——/
 *
 *         4)、如何确定以上几种情况：
 *             a、判断head1和head2的引用地址，相同就是情况2)，不相同就是情况1)或者情况3)
 *             b、如果head1 == head2，那么就和两个链表都是无环链表的情况一样，情况2)
 *             c、如果head1 != head2，对整个head1的环进行循环遍历，如果从始至终都没遇到head2，情况1)
 *             d、如果head1 != head2，对整个head1的环进行循环遍历，如果遇到了head2，情况3)
 *
 * @author xcy
 * @date 2022/4/26 - 08:59
 */
public class LinkedListInterviewQuestions009 {

	public static void main(String[] args) {
		LinkedListCode001.MyLinkedList myLinkedList1 = new LinkedListCode001.MyLinkedList();
		myLinkedList1.add(new Node(4));
		myLinkedList1.add(new Node(5));
		myLinkedList1.add(new Node(7));
		myLinkedList1.add(new Node(19));
		myLinkedList1.add(new Node(9));
		Node head1 = myLinkedList1.getHead();

		LinkedListCode001.MyLinkedList myLinkedList2 = new LinkedListCode001.MyLinkedList();
		myLinkedList2.add(new Node(4));
		myLinkedList2.add(new Node(5));
		myLinkedList2.add(new Node(7));
		myLinkedList2.add(new Node(19));
		myLinkedList2.add(new Node(9));
		myLinkedList2.add(head1);
		Node head2 = myLinkedList2.getHead();

		System.out.println(getIntersectNode(head1, head2));
	}

	/**
	 *
	 * @param head1
	 * @param head2
	 * @return
	 */
	public static Node getIntersectNode (Node head1, Node head2) {
		//获取第一个链表和第二个链表各自的第一个入环节点
		Node loop1 = getLinkedListFirstLoopNode(head1);
		Node loop2 = getLinkedListFirstLoopNode(head2);
		//情况一：第一个链表和第二个链表都是无环链表
		if (loop1 == null && loop2 == null) {
			return twoLinkedListNoLoopNode(head1, head2);
		}
		//情况三：第一个链表和第二个链表都是有环链表
		if (loop1 != null && loop2 != null) {
			return twoLinkedListLoopNode(head1, loop1, head2, loop2);
		}
		//情况二：第一个链表是有环链表第二个链表是无环链表或者第一个链表是无环链表第二个链表是有环链表
		return null;
	}
	/**
	 * 一、获取链表第一个入环节点，如果没有找到，返回null
	 *
	 * @param head
	 * @return
	 */
	public static Node getLinkedListFirstLoopNode(Node head) {
		//只要出现null就证明该链表中没有形成回环，也就不会有入环节点，直接返回null
		//防止出现空指针异常
		if (head == null || head.next == null || head.next.next == null) {
			return null;
		}
		//慢指针
		//slow慢指针从head.next出发
		Node slow = head.next;
		//fast快指针从head.next.next出发
		Node fast = head.next.next;
		//判断slow慢指针是否和fast快指针相遇
		while (slow != fast) {
			if (fast.next == null || fast.next.next == null) {
				return null;
			}
			//1.fast快指针每次走两步，slow慢指针每次走一步
			slow = slow.next;
			fast = fast.next.next;
		}

		//2.当slow慢指针和fast快指针相遇时，快指针重新回到head节点
		fast = head;
		//3.fast快指针每次走一步，slow慢指针每次走一步
		while (slow != fast) {
			slow = slow.next;
			fast = fast.next;
		}
		//4.当slow慢指针和fast快指针相遇时，返回slow慢指针
		return slow;
	}

	/**
	 * 二、
	 * 已经确定两个链表为无环链表，如果相交，返回第一个相交的节点，如果不相交，返回null
	 * @param head1 第一个链表
	 * @param head2 第二个链表
	 * @return 如果相交，返回第一个相交的节点，如果不相交，返回null
	 */
	public static Node twoLinkedListNoLoopNode(Node head1, Node head2) {
		if (head1 == null || head2 == null) {
			return null;
		}

		Node cur1 = head1;
		Node cur2 = head2;
		//记录第一个链表和第二个链表节点个数的差值
		int count = 0;
		while (cur1 != null) {
			//记录第一个链表的节点个数
			count++;
			cur1 = cur1.next;
		}

		while (cur2 != null) {
			//记录第一个链表的节点个数减去第二个链表的节点个数就得到两个链表的节点个数差值
			count--;
			cur2 = cur2.next;
		}

		if (cur1 != cur2) {
			return null;
		}

		//根据count的值是正数还是负数，如果是正数，证明第一个链表的节点个数大于第二个链表的节点个数
		//如果是负数，证明第二个链表的节点个数大于第一个链表的节点个数
		//那么确认节点个数多的长链表和节点个数少的短链表
		//1.节点个数差值为正数时，head1作为长链表的头节点，否则head2作为长链表的头节点
		//2.如果head1作为长链表的头节点，那么head2作为短链表的头节点
		//否则head1作为短链表的头节点，那么head2作为长链表的头节点
		cur1 = count > 0 ? head1 : head2;
		cur2 = cur1 == head1 ? head2 : head1;

		//获取长链表和短链表的节点个数的差值
		count = Math.abs(count);

		//长链表走到长链表和短链表的节点个数的差值个节点
		while (count != 0) {
			count--;
			cur1 = cur1.next;
		}
		//此时cur1和cur2所指向的各自链表的长度相同
		while (cur1 != cur2) {
			cur1 = cur1.next;
			cur2 = cur2.next;
		}

		return cur1;
	}

	/**
	 * 二、
	 * 已经确定两个链表为有环链表，如果相交，返回第一个相交的节点，如果不相交，返回null
	 * @param head1 第一个链表
	 * @param loop1 第一个链表的第一个入环节点
	 * @param head2 第二个链表
	 * @param loop2 第二个链表的第一个入环节点
	 * @return 如果相交，返回第一个相交的节点，如果不相交，返回null
	 */
	public static Node twoLinkedListLoopNode(Node head1,Node loop1, Node head2,Node loop2) {
		Node cur1 = null;
		Node cur2 = null;
		//情况2：两个链表都为有环链表，并且入环节点只有一个
		if (head1 == head2) {
			//cur1指向head1
			cur1 = head1;
			//cur2指向head2
			cur2 = head2;

			//记录第一个链表的节点个数，减去第二个链表的节点个数，根据差值确定哪一条链表长，哪一条链表短
			int count = 0;
			//记录第一个链表的节点个数
			while (cur1 != loop1) {
				count++;
				cur1 = cur1.next;
			}
			//减去第二个链表的节点个数
			while (cur2 != loop2) {
				count--;
				cur2 = cur2.next;
			}
			//说明cur1所在的链表长度和cur2所在的链表长度不一致
			if (cur1 != cur2) {
				return null;
			}

			//如果count大于0，说明cur1所在的链表长度比较长，那么head1作为长链表的头节点，否则head2作为长链表的头节点
			cur1 = count > 0 ? head1 : head2;
			//如果head1作为长链表的头节点，那么head2作为短链表的头节点，反之也是如此
			cur2 = cur1 == head1 ? head2 : head1;
			//取出cur1所在的链表长度和cur2所在的链表长度的差值的绝对值
			count = Math.abs(count);
			//循环让两个链表的长度保持一致
			while (count != 0) {
				count--;
				cur1 = cur1.next;
			}
			//判断cur1是否等于cur2
			while (cur1 != cur2) {
				cur1 = cur1.next;
				cur2 = cur2.next;
			}
			//循环遍历结束cur1和cur2保持一致，返回cur1或者cur2都可以
			return cur1;
		}
		//情况1：两个链表都为有环链表，但是各自独立
		//或者
		//情况3：两个链表都为有环链表，并且入环节点不止一个
		else {
			//cur1从第一个链表的第一个入环节点的下一个节点开始
			cur1 = loop1.next;
			//只要cur1没有重新回到第一个链表的第一个入环节点，那就继续循环
			//直到cur1重新回到第一个链表的第一个入环节点，循环结束
			while (cur1 != loop1) {
				//如果循环的过程中，遇到了第二个链表的第一个入环节点loop2，那就是情况3
				if (cur1 == loop2) {
					return loop1;
				}
				cur1 = cur1.next;
			}
			//如果循环的过程中，没有遇到第二个链表的第一个入环节点loop2，那就是情况1，直接返回null
			return null;
		}
	}
}
