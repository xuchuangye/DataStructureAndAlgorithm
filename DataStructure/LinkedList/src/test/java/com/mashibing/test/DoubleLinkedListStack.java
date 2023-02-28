package com.mashibing.test;

/**
 * @author xcy
 * @date 2022/4/14 - 17:41
 */
public class DoubleLinkedListStack<T> {
	public static void main(String[] args) {
		DoubleLinkedListStack<Integer> stack = new DoubleLinkedListStack<>();
		DoubleNode<Integer> node1 = new DoubleNode<>(1);
		DoubleNode<Integer> node2 = new DoubleNode<>(2);
		DoubleNode<Integer> node3 = new DoubleNode<>(3);
		DoubleNode<Integer> node4 = new DoubleNode<>(4);
		DoubleNode<Integer> node5 = new DoubleNode<>(5);
		//栈的特性是：先进后出
		//首先添加的node1，最后的是node5，所以node5先出，node1最后出
		stack.push(node1);
		stack.push(node2);
		stack.push(node3);
		stack.push(node4);
		stack.push(node5);
		DoubleNode<Integer> pop = stack.pop();
		System.out.println("从栈顶删除的节点是：" + pop);
		stack.list();
		System.out.println("栈中元素的个数是：" + stack.size());
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
	public Integer size;

	public DoubleLinkedListStack() {
		head = null;
		size = 0;
	}

	/**
	 * 判断栈是否为空
	 * @return
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * 返回栈中元素的个数
	 * @return
	 */
	public int size() {
		return this.size;
	}

	/**
	 * 在栈中添加元素
	 * @param node
	 */
	public void push(DoubleNode<T> node) {
		if (head == null) {
			head = node;
		}
		if (head != null) {
			head.next = node;
			node.last = head;
			head = node;
			node.next = null;
		}
		size++;
	}

	/**
	 * 在栈中取出元素
	 * @return
	 */
	public DoubleNode<T> pop() {
		DoubleNode<T> cur = null;
		if (head == null) {
			System.out.println("栈中元素为空，无法删除节点");
			return cur;
		}
		cur = head;

		if (head != null) {
			head = head.last;
			head.next = null;
		}
		size--;
		return cur;
	}

	/**
	 * 弹出栈顶元素的值
	 * @return
	 */
	public T peek() {
		T result = null;
		if (head != null) {
			result = head.value;
		}
		return result;
	}

	/**
	 * 栈中元素的遍历
	 */
	public void list() {
		if (head == null) {
			System.out.println("栈中元素为空，无法遍历");
			return;
		}
		while (head != null) {
			System.out.println(head);
			head.next = null;
			head = head.last;
			size--;
			if (size == 0) {
				break;
			}
		}
		System.out.println();
	}
}
