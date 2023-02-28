package com.mashibing.test;

/**
 * 数组实现队列
 * @author xcy
 * @date 2022/4/15 - 8:51
 */
public class ArrayQueue {
	public static void main(String[] args) {
		ArrayQueue queue = new ArrayQueue(10);
		queue.push(1);
		queue.push(2);
		queue.push(3);
		queue.push(4);
		queue.push(5);

		queue.list();
	}
	public int[] array;
	public int push_i;//从哪里添加 -> end
	public int pop_i;//从哪里取出 -> begin
	public int size;//使用size控制是否队列为空，或者队列已满
	public final int limit;//数组的长度

	public ArrayQueue(int limit) {
		array = new int[limit];
		push_i = 0;
		pop_i = 0;
		size = 0;
		this.limit = limit;
	}

	public int newIndex(int i) {
		//如果在尾部，重新回到0，如果不在尾部，那么就加1
		return i < limit - 1 ? i + 1 : 0;
	}

	/**
	 * 判断队列是否为空
	 * @return
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * 返回队列的元素个数
	 * @return
	 */
	public int size() {
		return this.size;
	}

	/**
	 * 在队列中添加元素
	 * @param value
	 */
	public void push(int value) {
		if (size == limit) {
			throw new RuntimeException("队列已满，不能添加元素");
		}

		size++;
		array[push_i] = value;
		//判断push_i是否在数组尾部，如果在尾部，重新回到0，如果不在尾部，那么就加1
		push_i = newIndex(push_i);
	}

	/**
	 * 在队列中删除元素
	 * @return
	 */
	public int pop() {
		if (size == 0) {
			throw new RuntimeException("队列为空，无法删除元素");
		}
		size--;
		int ans = array[pop_i];
		//判断push_i是否在数组尾部，如果在尾部，重新回到0，如果不在尾部，那么就加1
		pop_i = newIndex(pop_i);
		return ans;
	}

	/**
	 * 队列的遍历
	 */
	public void list() {
		if (size == 0) {
			throw new RuntimeException("队列为空，无法遍历元素");
		}
		while (size != 0) {
			size--;
			int ans = array[pop_i];
			//判断push_i是否在数组尾部，如果在尾部，重新回到0，如果不在尾部，那么就加1
			pop_i = newIndex(pop_i);
			System.out.println(ans);
		}
	}
}
