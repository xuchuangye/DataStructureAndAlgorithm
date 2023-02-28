package com.mashibing.day16;

/**
 * 约瑟夫环问题
 * 给定一个链表头节点head，和一个正数m，从头开始，每次数到m就杀死当前节点，然后被杀节点的下一个节点从1开始重新数，
 * 周而复始直到只剩一个节点，返回最后的节点
 * <p>
 * 解题思路：
 * f(当前节点被杀死之后的编号)，返回当前节点被杀死之前的编号   前 = (后 - 1 + S) % n + 1
 * S = 长度为i的链表中数到M就杀死节点的编号 -> S = (m - 1) % n + 1
 * g(数字)，返回编号  号 = (数 - 1) % n + 1
 * 假设：n = 4
 * 数字:1 2 3 4 5 6 7 8 9 10
 * 编号:1 2 3 4 1 2 3 4 1 2
 * <p>
 * 最终得到公式：前 = (后 - 1 + (m - 1) % n + 1) % n + 1 -> 前 = (后 + (m - 1) % n) % n + 1
 * 假设m - 1 = k * n + 余数r
 * 前 = (后 + (m - 1) % n) % n + 1 推导出 -> 前 = (后 + 余数r) % n + 1
 * 前 = (后 + m - 1) % n + 1 推导出 -> (后 + 余数r) % n + 1
 * 所以优化之后：前 = (后 + m - 1) % n + 1
 * <p>
 * 解题过程：
 * 1.一开始的想法是调用f(n, m)返回最后剩余的节点
 * 2.于是退而求其次，调用f(当前节点被杀死之后的编号)，返回当前节点被杀死之前的编号
 * 因为最后剩余的节点编号肯定为1，所以求出还剩下两个节点时，当前节点被杀死之前的编号，最后求出还剩下n个节点时，
 * 当前节点被杀死之前的节点
 * 3.进而调用S = (m - 1) % n + 1，也就是数字和编号的对应关系
 * <p>
 * Leetcode测试链接：
 * https://leetcode.cn/problems/yuan-quan-zhong-zui-hou-sheng-xia-de-shu-zi-lcof/
 *
 * @author xcy
 * @date 2022/8/5 - 14:32
 */
public class Code05_JosephusProblem {
	public static void main(String[] args) {
		Node head1 = new Node(1);
		head1.next = new Node(2);
		head1.next.next = new Node(3);
		head1.next.next.next = new Node(4);
		head1.next.next.next.next = new Node(5);
		head1.next.next.next.next.next = head1;
		printCircularList(head1);
		head1 = josephusKill1(head1, 3);
		printCircularList(head1);

		Node head2 = new Node(1);
		head2.next = new Node(2);
		head2.next.next = new Node(3);
		head2.next.next.next = new Node(4);
		head2.next.next.next.next = new Node(5);
		head2.next.next.next.next.next = head2;
		printCircularList(head2);
		head2 = josephusKill2(head2, 3);
		printCircularList(head2);
	}

	public static int lastRemaining(int n, int m) {
		if (n < 1 || m < 1) {
			return -1;
		}
		//LeetCode题目要求是从0开始，到n - 1结束
		//而process()是从1开始，到n结束，所以需要 - 1
		return getLive(n, m) - 1;
	}

	/**
	 * @param n 从1开始，到n结束
	 * @param m m
	 * @return 返回最后剩余的节点
	 */
	public static int getLive(int n, int m) {
		if (n == 1) {
			return 1;
		}
		//getLive(n - 1, m表示当前节点被杀死之后的编号
		//(getLive(n - 1, m) + m - 1) % n + 1表示当前节点被杀死之前的编号
		//前 = (后 + m - 1) % n + 1
		return (getLive(n - 1, m) + m - 1) % n + 1;
	}

	public static int lastRemaining2(int n, int m) {
		int ans = 1;
		int r = 1;
		while (r <= n) {
			ans = (ans + m - 1) % (r++) + 1;
		}
		return ans - 1;
	}


	// 以下的code针对单链表，不要提交
	public static class Node {
		public int value;
		public Node next;

		public Node(int data) {
			this.value = data;
		}
	}

	public static Node josephusKill1(Node head, int m) {
		if (head == null || head.next == head || m < 1) {
			return head;
		}
		Node last = head;
		while (last.next != head) {
			last = last.next;
		}
		int count = 0;
		while (head != last) {
			if (++count == m) {
				last.next = head.next;
				count = 0;
			} else {
				last = last.next;
			}
			head = last.next;
		}
		return head;
	}

	public static Node josephusKill2(Node head, int m) {
		if (head == null || head.next == head || m < 1) {
			return head;
		}
		Node cur = head.next;
		int size = 1; // tmp -> list size
		while (cur != head) {
			size++;
			cur = cur.next;
		}
		int live = getLive(size, m); // tmp -> service node position
		while (--live != 0) {
			head = head.next;
		}
		head.next = head;
		return head;
	}

	public static void printCircularList(Node head) {
		if (head == null) {
			return;
		}
		System.out.print("Circular List: " + head.value + " ");
		Node cur = head.next;
		while (cur != head) {
			System.out.print(cur.value + " ");
			cur = cur.next;
		}
		System.out.println("-> " + head.value);
	}
}
