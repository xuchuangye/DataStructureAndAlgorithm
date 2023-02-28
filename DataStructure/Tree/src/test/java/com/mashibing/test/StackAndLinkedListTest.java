package com.mashibing.test;

import java.util.LinkedList;
import java.util.Stack;

/**
 * @author xcy
 * @date 2022/4/11 - 11:46
 */
public class StackAndLinkedListTest {
	public static final int COUNT = 10000000;
	public static final Stack<Integer> stack = new Stack<>();
	public static final LinkedList<Integer> list = new LinkedList<>();
	public static final int[] arrayStack = new int[COUNT];

	static {
		for (int i = 1; i <= COUNT; i++) {
			stack.add(i);
		}

		for (int i = 1; i <= COUNT; i++) {
			list.addLast(i);
		}

		for (int i = 0; i < COUNT; i++) {
			arrayStack[i++] = i;
		}
	}

	public static void main(String[] args) {
		long start = 0L;
		long end = 0L;

		start = System.currentTimeMillis();

		while (!stack.isEmpty()) {
			stack.pop();
		}
		end = System.currentTimeMillis();
		System.out.println("Stack pop()的时间：" + (end - start));

		start = System.currentTimeMillis();


		while (!list.isEmpty()) {
			list.pollLast();
		}

		end = System.currentTimeMillis();
		System.out.println("LinkedList pollLast()的时间：" + (end - start));

		start = System.currentTimeMillis();

		int index = 0;
		while (index != 0) {
			int element = arrayStack[--index];
		}

		end = System.currentTimeMillis();
		System.out.println("Array 的时间：" + (end - start));
	}
}
