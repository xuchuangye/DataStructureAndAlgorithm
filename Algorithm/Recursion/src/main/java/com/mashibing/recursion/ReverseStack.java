package com.mashibing.recursion;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * 将栈中所有元素进行逆序 --> 通过递归的方式实现
 * 不能申请额外的数据结构，只能使用递归函数
 * @author xcy
 * @date 2022/5/6 - 15:19
 */
public class ReverseStack {
	public static void main(String[] args) {
		Stack<Integer> stack = new Stack<Integer>();
		stack.push(1);
		stack.push(2);
		stack.push(3);
		stack.push(4);

		for (Integer integer : stack) {
			System.out.println(integer);
		}

		reverseStack(stack);
		//reverse(stack);

		for (Integer integer : stack) {
			System.out.println(integer);
		}

	}

	/**
	 * 栈中所有元素的逆序
	 * @param stack 栈
	 */
	public static void reverse(Stack<Integer> stack) {
		//如果栈为空，直接返回
		if (stack.isEmpty()) {
			return;
		}
		//取出栈底元素，如果下一次递归直接返回，那么bottom
		int bottom = returnStackBottom(stack);
		reverse(stack);
		stack.push(bottom);
	}

	/**
	 * 返回栈底元素
	 *
	 * @param stack 栈
	 * @return 返回栈底元素
	 */
	public static Integer returnStackBottom(Stack<Integer> stack) {
		if (stack.isEmpty()) {
			return -1;
		}
		int result = stack.pop();
		//取出栈顶元素之后，栈为空，说明result就是栈底元素，直接返回
		if (stack.isEmpty()) {
			return result;
		} else {
			//如果栈不为空，说明result不是栈底元素，保存起来
			//继续递归，last记录下一次递归的栈底元素，如果栈为空，说明last是栈底元素，否则继续递归
			int last = returnStackBottom(stack);
			//result不是栈底元素，那么就进行入栈
			stack.push(result);
			//直到last接收的是栈底元素，返回
			return last;
		}
	}

	/**
	 * 栈中所有的元素逆序 --> 使用队列的方式实现
	 * @param stack 栈
	 */
	public static void reverseStack(Stack<Integer> stack) {
		Queue<Integer> queue = new LinkedList<>();
		while (!stack.isEmpty()) {
			queue.add(stack.pop());
		}
		for (int i = 0; i < queue.size(); i++) {
			queue.add(queue.poll());
		}
		for (Integer integer : queue) {
			stack.push(integer);
		}
	}
}
