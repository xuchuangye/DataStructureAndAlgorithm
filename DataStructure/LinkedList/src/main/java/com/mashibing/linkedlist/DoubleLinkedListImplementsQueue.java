package com.mashibing.linkedlist;

/**
 * 双链表实现队列
 *
 * @author xcy
 * @date 2022/4/9 - 9:20
 */
public class DoubleLinkedListImplementsQueue {
	public static class DoubleNode<T> {
		public T value;
		public DoubleNode<T> next;
		public DoubleNode<T> last;

		public DoubleNode(T data) {
			this.value = data;
		}
	}

	public static class MyQueue<T> {
		public DoubleNode<T> head;
		public DoubleNode<T> tail;
		public int size;

		public MyQueue() {
			head = null;
			tail = null;
			size = 0;
		}

		public boolean isEmpty() {
			return size == 0;
		}

		public int size() {
			return size;
		}

		/**
		 * 头部添加节点
		 * @param value
		 */
		public void pushHead(T value) {
			DoubleNode<T> cur = new DoubleNode<>(value);
			//如果head为空
			if (head == null) {
				//head指向cur
				head = cur;
				//tail指向cur
				tail = cur;
			} else {
				//cur.next指向head
				cur.next = head;
				//head.last指向cur
				head.last = cur;
				//head指向cur
				head = cur;
			}
			size++;
		}

		/**
		 * 尾部添加节点
		 * @param value
		 */
		public void pushTail(T value) {
			DoubleNode<T> cur = new DoubleNode<T>(value);

			if (head == null) {
				tail = cur;
				head = cur;
			} else {
				//tail.next指向cur
				tail.next = cur;
				//cur.last指向tail
				cur.last = tail;
				//tail指向cur
				tail = cur;
			}
			size++;
		}

		/**
		 * 头部抛出节点
		 * @return
		 */
		public T popHead() {
			T ans = null;
			//证明队列中没有节点，直接返回
			if (head == null) {
				return ans;
			}
			//证明队列中有节点，那么长度先减1
			size--;

			//获取头部节点的值
			ans = head.value;
			//如果队列中只有一个节点，并且head和tail都指向该节点
			if (head == tail) {
				//那么直接将head和tail置空
				head = null;
				tail = null;
			}
			//如果队列中有多个节点，head记录head.next
			else {
				head = head.next;
				//并且将head.last指向该节点也置空，否则JVM不会回收该节点
				head.last = null;
			}
			return ans;
		}

		/**
		 * 尾部抛出节点
		 * @return
		 */
		public T popTail() {
			T ans = null;
			//证明队列中没有节点，直接返回
			if (head == null) {
				return ans;
			}
			//证明队列中有节点，那么长度先减1
			size--;
			//获取尾部节点的值
			ans = tail.value;
			//如果队列中只有一个节点，并且head和tail都指向该节点
			if (head == tail) {
				//那么head和tail节点直接置空
				head = null;
				tail = null;
			}
			//如果队列中有多个节点
			else {
				//tail记录tail.last
				tail = tail.last;
				//tail.next指向该节点也置为空
				tail.next = null;
			}
			return ans;
		}

		public T peekHead() {
			T ans = null;
			if (head != null) {
				ans = head.value;
			}
			return ans;
		}

		public T peekTail() {
			T ans = null;
			if (tail != null) {
				ans = tail.value;
			}
			return ans;
		}
	}
}
