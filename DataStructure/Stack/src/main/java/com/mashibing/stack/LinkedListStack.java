package com.mashibing.stack;

import com.mashibing.node.Node;

/**
 * @author xcy
 * @date 2022/3/16 - 16:38
 */
/*
使用链表表示栈
 */
public class LinkedListStack {
	private Node head = new Node(0);//head表示头节点，指向栈的栈顶

	public LinkedListStack() {

	}

	/**
	 * 返回栈的长度
	 *
	 * @return
	 */
	public int size() {
		if (isStackNull()) {
			throw new RuntimeException("栈已经为空，没有数据可以打印输出");
		}
		Node temp = head.next;
		int length = 0;
		while (true) {
			length++;
			if (temp.next == null) {
				break;
			}
		}
		return length;
	}

	/**
	 * 判断栈是否已满
	 * @return
	 */
	/*public boolean isStackFull() {
		return head.next.equals(new Node(length));
	}*/

	/**
	 * 判断栈是否为空
	 *
	 * @return
	 */
	public boolean isStackNull() {
		return head.next == null;
	}

	/**
	 * 入栈
	 *
	 * @param newNode
	 */
	public void push(Node newNode) {
		Node temp = head;

		while (true) {
			if (temp.next == null) {
				break;
			}
			temp = temp.next;
		}
		temp.next = newNode;
	}

	/**
	 * 出栈
	 *
	 * @return
	 */
	public void pop() {
		if (isStackNull()) {
			throw new RuntimeException("栈已经为空，没有数据可以取出");
		}

		Node temp = head;

		while (true) {
			if (temp.next == null) {
				break;
			}
			temp = temp.next;
		}
		System.out.println("出栈的节点是：" + temp);
	}

	/**
	 * 遍历栈
	 */
	public void list() {
		if (isStackNull()) {
			throw new RuntimeException("栈已经为空，没有数据打印输出");
		}
		Node temp = head.next;

		while (true) {
			if (temp.next == null) {
				System.out.println("最后出栈的元素为：" + temp);
				break;
			}
			System.out.println("出栈的元素为：" + temp);
			temp = temp.next;
		}

	}
}

