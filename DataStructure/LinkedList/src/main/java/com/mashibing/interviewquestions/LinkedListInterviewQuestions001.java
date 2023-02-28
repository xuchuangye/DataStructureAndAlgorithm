package com.mashibing.interviewquestions;

/**
 * 单链表的面试题
 *
 * 题目一：
 * 给定一个单链表的head节点，和一个正整数k
 * 实现单链表的节点按照k个进行分组并且逆序，如果最后一组的节点个数小于k，无法进行分组和逆序那么该组节点就不进行调整
 *
 * 举例：
 * 单链表：A -> B -> C -> D -> E -> F -> G -> H，正整数k为3
 * 单链表调整前： A -> B -> C -> D -> E -> F -> G -> H
 * 单链表调整后： C -> B -> A -> F -> E -> D -> G -> H
 *
 * @author xcy
 * @date 2022/4/9 - 10:51
 */
public class LinkedListInterviewQuestions001 {
	public static void main(String[] args) {
		Node head = new Node("A");
		head.next = new Node("B");
		head.next.next = new Node("C");
		head.next.next.next = new Node("D");
		head.next.next.next.next = new Node("E");
		head.next.next.next.next.next = new Node("F");
		head.next.next.next.next.next.next = new Node("G");
		head.next.next.next.next.next.next.next = new Node("H");

		Node newNode = reverseKGroup(head, 3);
		while (newNode != null) {
			System.out.print(newNode.value + " ");
			newNode = newNode.next;
		}
	}

	public static class Node {
		public String value;
		public Node next;

		public Node(String data) {
			this.value = data;
		}
	}

	public static Node reverseKGroup(Node head, int k) {
		//start记录head
		Node start = head;
		//
		Node end = getKGroupEnd(start, k);
		//end == null表示链表的节点个数不能按照K个进行分组，因此无法分组，也就不需要逆序，所以直接返回head即可
		if (end == null) {
			return head;
		}
		//如果end != null，证明第一组已经分组成功，head指向end
		head = end;
		//对于分组之后的节点进行逆序
		reverse(start, end);
		//上一组的结尾节点
		Node lastEnd = start;
		//判断分完一组之后，是否还有剩余的节点能够进行分组，如果有剩余的节点就继续
		while (lastEnd.next != null) {
			//start指向lastEnd.next节点
			start = lastEnd.next;
			//end指向
			end = getKGroupEnd(start, k);

			//end == null表示链表的节点个数不能按照K个进行分组，因此无法分组，也就不需要逆序，所以直接返回head即可
			if (end == null) {
				return head;
			}
			//head节点不需要移动

			//如果end != null证明可以进行分组，也就可以对分组的节点进行逆序操作
			reverse(start, end);
			//让上一组的尾部节点的next指向end
			lastEnd.next = end;
			//上一组的尾部节点指向start
			lastEnd = start;
		}
		//如果没有剩余的节点，直接返回head

		return head;
	}
	/**
	 * 单链表的节点个数按照k个进行分组，并且返回该组的尾部节点
	 * @param start 起始节点，也就是当前分组的该组的头节点
	 * @param k 单链表的节点按照k个数目进行分组
	 * @return 返回分组之后该组的尾部节点
	 */
	public static Node getKGroupEnd(Node start, int k) {
		while (--k != 0 && start != null) {
			start = start.next;
		}
		return start;
	}

	/**
	 * 按照当前分组的头节点start到尾部节点end进行逆序操作
	 * @param start 当前分组的头节点start
	 * @param end 当前分组的尾节点end
	 */
	public static void reverse(Node start, Node end) {
		//当分组之后，该组的尾部节点指向该尾部节点的下一个节点
		end = end.next;

		Node pre = null;
		//start节点不能移动，使用辅助节点cur作为临时的head头节点
		Node cur = start;
		Node next = null;
		while (cur != end) {
			next = cur.next;
			cur.next = pre;
			pre = cur;
			cur = next;
		}
		//start.next指向end
		//表示分组之后start已经作为该组的尾部节点，该组的尾部节点指向end，也就是原来尾部节点的下一个节点
		start.next = end;
	}
}
