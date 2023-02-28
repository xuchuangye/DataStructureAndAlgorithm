package com.mashibing.interviewquestions;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * Stack的面试题002：
 * 1)如何使用栈结构实现队列结构
 * 2)如何使用队列结构实现栈结构
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
 * @date 2022/4/15 - 9:33
 */
public class StackInterviewQuestions002 {
	public static void main(String[] args) {
		/*MyQueue<Integer> queue = new MyQueue<>();
		queue.push(1);
		queue.push(2);
		queue.push(3);
		queue.push(4);
		queue.push(5);

		queue.poll();
		queue.poll();
		//queue.poll();
		//queue.poll();
		//queue.poll();

		queue.list();*/

		MyStack<Integer> stack = new MyStack<>();
		stack.push(1);
		stack.push(2);
		stack.push(3);
		stack.push(4);
		stack.push(5);

		System.out.println("栈中元素的个数是：" + stack.size());
		Integer poll1 = stack.poll();
		Integer poll2 = stack.poll();
		Integer poll3 = stack.poll();
		Integer poll4 = stack.poll();
		Integer poll6 = stack.peek();
		Integer poll5 = stack.poll();
		System.out.println(poll1 + "," + poll2 + "," + poll3 + "," + poll4 + "," + poll5);
		System.out.println(poll6);
	}

	public static class MyQueue<T> {
		private final Stack<T> pushStack = new Stack<>();
		private final Stack<T> popStack = new Stack<>();

		/**
		 * 判断队列是否为空
		 *
		 * @return
		 */
		public boolean isEmpty() {
			//判断pushStack和popStack是否都为空
			return pushStack.isEmpty() && popStack.isEmpty();
		}


		/**
		 * pushStack向popStack导入数据
		 */
		private void importData() {
			//如果popStack不为空，则不能导入数据，因为可能会导致数据混乱
			if (!popStack.isEmpty()) {
				//System.out.println("popStack不为空，无法导入数据");
				return;
			}
			//如果pushStack不为空，则需要一次性导入完数据到popStack中，直到pushStack为空为止
			while (!pushStack.isEmpty()) {
				//依次取出pushStack栈顶元素
				T pop = pushStack.pop();
				//将pushStack的栈顶元素导入到popStack
				popStack.push(pop);
			}
		}

		/**
		 * 队列中添加数据，从pushStack中添加
		 */
		public void push(T value) {
			pushStack.push(value);

			/*if (!popStack.isEmpty()) {
				popStack.clear();
			}*/

			importData();
		}


		/**
		 * 队列中取出数据，从popStack中取出
		 *
		 * @return
		 */
		public T poll() {
			if (pushStack.isEmpty() && popStack.isEmpty()) {
				throw new RuntimeException("队列为空，无法取出数据");
			}

			importData();

			return popStack.pop();
		}

		/**
		 * 队列中查看但不取出数据，从popStack中查看
		 *
		 * @return
		 */
		public T peek() {
			if (pushStack.isEmpty() && popStack.isEmpty()) {
				throw new RuntimeException("队列为空，无法取出数据");
			}

			importData();
			return popStack.peek();
		}

		/**
		 * 队列元素的遍历
		 */
		public void list() {
			if (pushStack.isEmpty() && popStack.isEmpty()) {
				throw new RuntimeException("队列为空，无法取出数据");
			}
			while (!isEmpty()) {
				System.out.println(poll());
			}
		}
	}

	public static class MyStack<T> {
		private final Queue<T> queue;
		private Integer size;

		public MyStack() {
			queue = new LinkedList<>();
			size = 0;
		}

		/**
		 * 判断栈是否为空
		 *
		 * @return
		 */
		public boolean isEmpty() {
			return queue.isEmpty();
		}

		/**
		 * 返回 栈中元素个数
		 *
		 * @return
		 */
		public Integer size() {
			return this.size;
		}

		/**
		 * 栈中添加元素
		 *
		 * @param value
		 */
		public void push(T value) {
			size++;
			//将元素插入到队列中
			queue.offer(value);
			int size = queue.size();
			//因为队列的特性，栈中最后一个元素肯定是最新添加的元素
			//所以除了最新的元素之外，将其他元素出队列，在重新入队，就实现了栈的特性：先进后出
			for (int i = 0; i < size - 1; i++) {
				//除去最后添加的元素，其他元素都出队，再重新入队
				queue.offer(queue.poll());
			}
		}

		/**
		 * 栈中添加元素
		 */
		public T poll() {
			T value = null;
			if (!queue.isEmpty()) {
				value = queue.poll();
				size--;
			}
			return value;
		}

		/**
		 * 栈中弹出元素
		 */
		public T peek() {
			T value = null;
			if (!queue.isEmpty()) {
				value = queue.peek();
			}
			return value;
		}
	}
}
