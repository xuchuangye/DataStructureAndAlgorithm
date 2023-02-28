package com.mashibing.interviewquestions;

/**
 * 单链表的面试题
 *
 * 题目二：
 * 给定两个单链表的头节点head1和head2节点
 * 从左到右依次让两个链表的节点相加组成新的链表
 *
 * 举例：
 * 单链表1：4 -> 3 -> 6 -> 2
 * 单链表2：2 -> 5 -> 3
 * 从左到右两个单链表的节点权值依次相加并返回： 6 -> 8 -> 9 -> 2
 * @author xcy
 * @date 2022/4/9 - 15:01
 */
public class LinkedListInterviewQuestions002 {
	public static void main(String[] args) {
		Node head1 = new Node(4);
		head1.next = new Node(3);
		head1.next.next = new Node(6);
		head1.next.next.next = new Node(2);

		Node head2 = new Node(2);
		head2.next = new Node(5);
		head2.next.next = new Node(3);

		Node head = twoLinkedListAddNumbers(head1, head2);
		while (head != null) {
			System.out.print(head.value + " ->");
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

	/**
	 * 获取单链表的节点个数
	 * @param head 单链表的头节点
	 * @return 返回单链表的节点个数
	 */
	public static int getLinkedListSize(Node head) {
		int size = 0;
		while (head != null) {
			size++;
			head = head.next;
		}
		return size;
	}

	public static Node twoLinkedListAddNumbers(Node head1, Node head2) {
		//获取单链表1的节点个数
		int linkedListSize1 = getLinkedListSize(head1);
		//获取单链表2的节点个数
		int linkedListSize2 = getLinkedListSize(head2);
		//获取链表长度较大的链表头节点
		Node longNode = linkedListSize1 >= linkedListSize2 ? head1 : head2;
		//获取链表长度较小的链表头节点
		Node shortNode = longNode == head1 ? head2 : head1;

		//因为头节点不能移动，所以需要辅助节点进行记录
		//记录链表长度较大的头节点
		Node curLong = longNode;
		//记录链表长度较小的头节点
		Node curShort = shortNode;

		//记录两个链表上节点的值相加的和
		int curNum = 0;
		//两个节点的value值相加是否需要进位，0表示默认情况下不需要进位
		int carry = 0;
		Node last = null;
		//情况一：节点个数多的链表和节点个数少的链表都有节点时
		while (curShort != null) {
			//节点个数多的链表上的节点的值 + 节点个数少的链表上的节点的值 + 进位
			curNum = curLong.value + curShort.value + carry;
			//将相加的值对10取余，得到的结果覆盖节点个数多的链表的节点上的值，然后作为新的链表返回
			curLong.value = curNum % 10;
			//判断相加的和是否超出了10，是否 需要进位
			carry = curNum / 10;

			//last时刻记录节点个数多的链表的位置
			last = curLong;

			curLong = curLong.next;
			curShort = curShort.next;
		}
		//情况二：节点个数多的链表还有节点，而节点个数少的链表没有节点时
		while (curLong != null) {
			//节点个数多的链表上的节点的值 + 进位
			curNum = curLong.value + carry;
			//将相加的值对10取余，得到的结果覆盖节点个数多的链表的节点上的值，然后作为新的链表返回
			curLong.value = curNum % 10;
			//判断相加的和是否超出了10，是否 需要进位
			carry = curNum / 10;
			//last时刻记录节点个数多的链表的位置
			last = curLong;
			curLong = curLong.next;
		}
		//情况三：节点个数多的链表和节点个数少的链表都没有节点时
		//判断是否需要进位，进位就是创建新的节点，在last节点记录的位置上添加下一个节点
		if (carry != 0) {
			last.next = new Node(1);
		}
		return longNode;
	}
}
