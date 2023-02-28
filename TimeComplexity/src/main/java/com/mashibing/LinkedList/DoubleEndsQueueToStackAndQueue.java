package com.mashibing.LinkedList;

/**
 * @author xcy
 * @date 2021/9/19 - 20:19
 */
public class DoubleEndsQueueToStackAndQueue {
	public static class Node<T> {
		public T value;
		public Node<T> last;
		public Node<T> next;

		private Node(T data) {
			this.value = data;
		}
	}

	public static class DoubleEndsQueue<T> {
		public Node<T> head;
		public Node<T> tail;

		//从头部添加value值封装的节点
		public void pushFromHead(T value) {
			//将value值封装为节点类型
			Node<T> cur = new Node<T>(value);
			//如果头部为空，此时添加的value是第一个进来的值
			if (head == null) {
				head = cur;
				tail = cur;
			} else {
				//因为是头部节点
				//所以每次新进来的节点的next指针都指向之前的head节点
				cur.next = head;
				//每次进来的节点之前的节点的last指针指向新的头部节点
				head.last = cur;
				//新进来的节点都作为head头部结点
				head = cur;
			}
		}
		//从尾部添加value值封装的节点
		public void pushFromTail(T value) {
			//将value值封装为节点类型
			Node<T> cur = new Node<T>(value);

			//如果尾部为空，此时添加的value是第一个进来的值封装的节点
			if (tail == null) {
				tail = cur;
				head = cur;
			}else {
				//因为是尾部节点
				//所以每次新进来的节点的last指针都指向之前的tail节点
				cur.last = tail;
				//每次进来的节点之前的节点的next指针指向新的头部节点
				tail.next = cur;
				//新进来的节点都作为tail尾部结点
				tail = cur;
			}
		}
		//从头部删除value值封装的节点
		public T popFromHead() {
			if (head == null) {
				return null;
			}

			Node<T> cur = head;

			if (head == tail) {
				head = null;
				tail = null;
			}else {
				head = head.next;
				head.last = null;
				cur.last = null;
			}
			return cur.value;
		}

		//从尾部删除value值封装的节点
		public T popFromTail() {
			if (tail == null) {
				return null;
			}

			Node<T> cur = tail;

			if (tail == head) {
				tail = null;
				head = null;
			}else {
				tail = tail.next;
				tail.next = null;
				cur.last = null;
			}
			return cur.value;
		}

		public boolean isEmpty() {
			return head == null;
		}
	}

	public static class MyStack<T> {
		private DoubleEndsQueue<T> queue;

		public  MyStack() {
queue = new DoubleEndsQueue<T>();
		}
		public void push(T value) {
			queue.pushFromHead(value);
		}

		public T pop() {
			return queue.popFromHead();
		}

		public boolean isEmpty() {
			return queue.isEmpty();
		}
	}

	public static class MyQueue<T> {
		private DoubleEndsQueue<T> queue;

		public MyQueue() {
			queue = new DoubleEndsQueue<T>();
		}

		public void push(T value) {
			queue.pushFromHead(value);
		}

		public T pop() {
			return queue.popFromTail();
		}

		public boolean isEmpty() {
			return queue.isEmpty();
		}
	}
}
