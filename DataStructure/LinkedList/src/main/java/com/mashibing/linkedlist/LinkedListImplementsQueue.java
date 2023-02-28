package com.mashibing.linkedlist;

/**
 * 单链表实现队列
 *
 * @author xcy
 * @date 2022/4/9 - 8:44
 */
public class LinkedListImplementsQueue {
	public static class Node<T> {
		public T value;
		public Node<T> next;

		public Node(T data) {
			this.value = data;
		}
	}

	public static class MyQueue<T> {
		/**
		 * 头节点
		 */
		public Node<T> head;
		/**
		 * 尾节点
		 */
		public Node<T> tail;

		/**
		 * 队列的长度
		 */
		public int size;

		public MyQueue() {
			this.head = null;
			this.tail = null;
			this.size = 0;
		}

		/**
		 * 判断是否栈空
		 *
		 * @return
		 */
		public boolean isEmpty() {
			return size == 0;
		}

		public int size() {
			return size;
		}

		/**
		 * 队列中推入节点
		 * @param value
		 */
		public void push(T value) {
			Node<T> cur = new Node<>(value);
			if (tail == null) {
				head = cur;
				tail = cur;
			} else {
				tail.next = cur;
				tail = cur;
			}
			//长度累加
			size++;
		}

		/**
		 * 队列中抛出节点
		 * @return
		 */
		public T pop() {
			T ans = null;
			if (head != null) {
				ans = head.value;
				head = head.next;

				//取出一个节点，长度累减
				size--;
			}
			//说明队列中所有的节点都已经删除了
			if (head == null) {
				//防止脏数据
				tail = null;
			}
			return ans;
		}

		/**
		 * 队列中返回
		 * @return
		 */
		public T peek() {
			T ans = null;
			if (head != null) {
				ans = head.value;
			}
			return ans;
		}
	}
}
