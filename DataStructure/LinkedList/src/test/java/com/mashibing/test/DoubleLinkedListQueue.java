package com.mashibing.test;

/**
 * @author xcy
 * @date 2022/4/14 - 17:08
 */
public class DoubleLinkedListQueue<T> {
	public static void main(String[] args) {
		DoubleLinkedListQueue<Integer> queue = new DoubleLinkedListQueue<>();
		DoubleNode<Integer> node1 = new DoubleNode<>(1);
		DoubleNode<Integer> node2 = new DoubleNode<>(2);
		DoubleNode<Integer> node3 = new DoubleNode<>(3);
		DoubleNode<Integer> node4 = new DoubleNode<>(4);
		DoubleNode<Integer> node5 = new DoubleNode<>(5);
		//队列的特性是：先进先出
		//首先添加的节点是node1，最后是node5，所以此时node1应该先出，node5最后出
		queue.pushHead(node1);
		queue.pushHead(node2);
		queue.pushHead(node3);
		queue.pushHead(node4);
		queue.pushHead(node5);
		//queue.list();
		//从尾部删除节点，应该删除的是node5
		DoubleNode<Integer> popTail = queue.popTail();
		System.out.println("从尾部删除的节点是：" + popTail);
		queue.list();
		System.out.println(queue.size());
	}
	public static class DoubleNode<T>{
		public T value;
		public DoubleNode<T> last;
		public DoubleNode<T> next;

		public DoubleNode(T data) {
			this.value = data;
		}

		@Override
		public String toString() {
			return "DoubleNode:{ " + this.value + " }";
		}
	}

	public DoubleNode<T> head;
	public DoubleNode<T> tail;
	public Integer size;

	public DoubleLinkedListQueue() {
		head = null;
		tail = null;
		size = 0;
	}

	/**
	 * 判断队列是否为空
	 * @return
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * 判断队列中元素的个数
	 * @return
	 */
	public int size() {
		return this.size;
	}

	/**
	 * 从头部添加节点
	 * @param node
	 */
	public void pushHead(DoubleNode<T> node) {
		if (head == null) {
			head = node;
			tail = node;
		}else {
			node.next = head;
			head.last = node;
			head = node;
		}
		size++;
	}

	/**
	 * 从尾部添加节点
	 * @param node
	 */
	public void pushTail(DoubleNode<T> node) {
		if (head == null) {
			head = node;
			tail = node;
		}else {
			tail.next = node;
			node.last = tail;
			tail = node;
		}
		size++;
	}

	/**
	 * 从头街删除节点
	 */
	public DoubleNode<T> popHead() {
		DoubleNode<T> result = null;
		if (head == null) {
			System.out.println("队列为空，无法删除节点");
			return result;
		}
		result = tail;
		if (head == tail) {
			head = null;
			tail = null;
		}else {
			tail = tail.last;
			tail.next = null;
			result.last = null;
		}

		size--;
		return result;
	}

	/**
	 * 从尾部删除节点
	 * @return
	 */
	public DoubleNode<T> popTail() {

		DoubleNode<T> result = null;
		if (head == null) {
			System.out.println("队列为空，无法删除节点");
			return result;
		}

		result = head;
		//如果队列中只有一个节点
		if (head == tail) {
			head = null;
			tail = null;
		}
		//如果队列中有多个节点
		else {
			head = head.next;
			result.next = null;
			head.last = null;
		}
		size--;

		return result;
	}

	public void list() {
		if (head == null) {
			System.out.println("队列为空，无法遍历节点");
			return;
		}
		while (tail != null) {
			System.out.print(tail + " ");
			tail = tail.last;
		}
		System.out.println();
	}

	/**
	 * 弹出队列的头节点的值
	 * @return
	 */
	public T peekHead() {
		T result = null;
		if (head != null) {
			result = head.value;
		}
		return result;
	}

	/**
	 * 弹出队列的尾节点的值
	 * @return
	 */
	public T peekTail() {
		T result = null;
		if (tail != null) {
			result = tail.value;
		}
		return result;
	}
}
