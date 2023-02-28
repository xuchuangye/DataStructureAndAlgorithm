package com.mashibing.interviewquestions;

import java.util.Stack;

/**
 * Stack的面试题001：
 * 实现一个特殊的栈，在基本功能的基础上，再实现返回栈中最小值元素的功能
 * 要求：
 * 1）pop、push、getMin操作的时间复杂度为O(1)
 * 2）设计的栈类型可以使用现有的栈结构
 * <p>
 * 基本实现思路：
 * 1、准备两个栈，一个数据栈，一个最小元素的栈
 * 2、两个栈同步增长，最小元素的栈的元素个数跟随数据栈的元素个数增加而增加
 * 3、当数据栈为空，最小元素的栈也为空时，新添加的元素同时入两个栈
 * 4、当数据栈压入数据时，判断该数据是否大于等于最小元素栈中的数据，
 * 如果大于等于，最小元素栈就重复压入当前栈顶元素
 * 如果该数据小于最小元素栈中的数据，最小元素栈就压入该数据
 *
 * @author xcy
 * @date 2022/4/15 - 9:33
 */
public class StackInterviewQuestions001 {
	public static final Stack<Integer> numberStack = new Stack<>();
	public static final Stack<Integer> minStack = new Stack<>();

	public static void main(String[] args) {
		push(1);
		pop();
		getMin();
	}

	/**
	 * @param value
	 */
	public static void push(int value) {
		//当数据栈为空，最小元素的栈也为空时，新添加的元素同时入两个栈
		if (numberStack.isEmpty() && minStack.isEmpty()) {
			numberStack.push(value);
			minStack.push(value);
		}

		if (numberStack.isEmpty() && !minStack.isEmpty()) {
			throw new RuntimeException("数据栈和最小元素占异常，数据不同步");
		}

		if (!numberStack.isEmpty() && minStack.isEmpty()) {
			throw new RuntimeException("数据栈和最小元素占异常，数据不同步");
		}

		//数据栈和最小元素栈都不为空时
		//当数据栈压入数据时，判断该数据是否大于等于最小元素栈中的数据，
		//如果大于等于，最小元素栈就重复压入当前栈顶元素
		//取出最小元素栈的栈顶元素
		Integer pop = minStack.pop();
		if (value >= pop) {
			//最小元素栈就重复压入当前栈顶元素
			minStack.push(pop);
		}
		//如果该数据小于最小元素栈中的数据，最小元素栈就压入该数据
		else {
			minStack.push(value);
		}

		//将该元素压入数据栈
		numberStack.push(value);
	}

	/**
	 *
	 * @return
	 */
	public static int pop() {
		int ans = 0;
		//当数据栈为空，最小元素的栈也为空时，新添加的元素同时入两个栈
		if (!numberStack.isEmpty() && !minStack.isEmpty()) {
			ans = numberStack.pop();
			minStack.pop();
		}

		if (numberStack.isEmpty() && minStack.isEmpty()) {
			System.out.println("数据栈和最小元素栈 都为空");
			return -1;
		}

		if (numberStack.isEmpty() || minStack.isEmpty()) {
			throw new RuntimeException("数据栈和最小元素占异常，数据不同步");
		}
		return ans;
	}

	/**
	 * 返回整个栈中的最小元素
	 * @return
	 */
	public static int getMin() {
		int min = 0;
		//当数据栈为空，最小元素的栈也为空时，新添加的元素同时入两个栈
		if (!numberStack.isEmpty() && !minStack.isEmpty()) {
			numberStack.pop();
			min = minStack.pop();
		}

		if (numberStack.isEmpty() && minStack.isEmpty()) {
			System.out.println("数据栈和最小元素栈 都为空");
			return -1;
		}

		if (numberStack.isEmpty() || minStack.isEmpty()) {
			throw new RuntimeException("数据栈和最小元素占异常，数据不同步");
		}
		return min;
	}
}
