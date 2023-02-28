package com.mashibing.interviewquestions;

import java.util.Stack;

/**
 * Graph的面试题001：
 * 1)如何使用栈结构求出图的宽度
 * 2)如何使用队列结构求出图的深度
 * <p>
 * 基本思路：
 * 1、栈结构本身是求出图的深度，栈结构是不能求出图的宽度的，所以需要先将栈结构转换为队列结构，才能求出图的宽度
 * 2、队列结构本身是求出图的宽度，队列结构是不能求出图的深度的，所以需要先将队列结构转换为栈结构，才能求出图的深度
 * <p>
 * 栈结构实现队列的结构的具体实现：
 * 1、准备两个栈，一个pushStack，一个popStack，如果想要利用栈结构实现队列结构
 * 需要先将数据添加到pushStack中，再依次导入到popStack中，从popStack取出数据时就达到了队列的特性：先进先出
 * <p>
 * 注意事项：
 * 1、当数据从pushStack取出依次导入到popStack时，需要一次性导入完
 * 2、当popStack不为空时，pushStack不能将数据导入到popStack中，否则会导致数据混乱
 * <p>
 * 队列结构实现栈的结构的具体实现：
 * 1、准备一个队列即可
 * push操作时，除去最新添加的元素之外，其余的元素先出队列，然后再入队列，保持最新添加的元素总是队列的最前
 * pop操作时，先出队列的就是最新添加的元素，也就实现了栈的先进后出的特性
 * <p>
 * 注意事项：
 * 1、除去最新添加的元素之外，其余的元素先出队列，然后再入队列，保持最新添加的元素总是队列的最前
 * 2、当popStack不为空时，pushStack不能将数据导入到popStack中，否则会导致数据混乱
 *
 * @author xcy
 * @date 2022/4/15 - 10:24
 */
public class GraphInterviewQuestions001 {

	public static void main(String[] args) {
		MyQueue myQueue = new MyQueue();

	}

	public static class MyQueue {
		public static final Stack<Integer> pushStack = new Stack<>();
		public static final Stack<Integer> popStack = new Stack<>();

		/**
		 * 添加数据，从pushStack中添加
		 */
		public static void add(Integer value) {
			pushStack.push(value);

			/*if (!popStack.isEmpty()) {
				popStack.clear();
			}*/

			importData();
		}

		/**
		 * pushStack向popStack导入数据
		 */
		private static void importData() {
			//如果popStack不为空，则不能导入数据，因为可能会导致数据混乱
			if (!popStack.isEmpty()) {
				System.out.println("popStack不为空，无法导入数据");
				return;
			}
			//如果pushStack不为空，则需要一次性导入完数据到popStack中，直到pushStack为空为止
			while (!pushStack.isEmpty()) {
				//依次取出pushStack栈顶元素
				Integer pop = pushStack.pop();
				//将pushStack的栈顶元素导入到popStack
				popStack.push(pop);
			}
		}

		/**
		 * 取出数据，从popStack中取出
		 *
		 * @return
		 */
		public static Integer poll() {
			if (pushStack.isEmpty() && popStack.isEmpty()) {
				throw new RuntimeException("队列为空，无法取出数据");
			}

			importData();
			return popStack.pop();
		}

		/**
		 * 查看但不取出数据，从popStack中查看
		 *
		 * @return
		 */
		public static Integer peek() {
			if (pushStack.isEmpty() && popStack.isEmpty()) {
				throw new RuntimeException("队列为空，无法取出数据");
			}

			importData();
			return popStack.peek();
		}
	}
}
