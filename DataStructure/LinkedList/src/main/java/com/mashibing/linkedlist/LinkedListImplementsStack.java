package com.mashibing.linkedlist;

/**
 * 单链表实现栈
 * @author xcy
 * @date 2022/4/9 - 9:01
 */
public class LinkedListImplementsStack {

	public static class Node<T> {
		public T value;
		public Node<T> next;

		public Node(T data) {
			this.value = data;
		}
	}

	public static class MyStack<T> {
		public Node<T> head;
		public int size;

		public MyStack() {
			head = null;
			size = 0;
		}

		public boolean isEmpty() {
			return size == 0;
		}

		public int size() {
			return size;
		}

		public void push(T value) {
			Node<T> cur = new Node<T>(value);
			//如果head为空
			if (head == null) {
				//head指向cur，让cur作为新的head
				head = cur;
			}
			//如果head不为空
			else {
				//cur.next指向head
				cur.next = head;
				//head指向cur，让cur作为新的head
				head = cur;
			}
			//长度加1
			size++;
		}

		public T pop() {
			T ans = null;
			if (head != null) {
				ans = head.value;
				head = head.next;
				size--;
			}
			return ans;
		}

		public T peek() {
			T ans = null;
			if (head != null) {
				ans = head.value;
			}
			return ans;
		}
	}
}
