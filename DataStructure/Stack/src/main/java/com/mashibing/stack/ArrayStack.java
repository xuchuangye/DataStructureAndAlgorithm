package com.mashibing.stack;

/**
 * 使用数组表示栈
 *
 * @author xcy
 * @date 2022/3/16 - 16:02
 */
public class ArrayStack {
	private int maxSize;//找的最大空间
	private int[] stack;//使用数组表示栈
	private int top = -1;//top表示栈顶

	public ArrayStack() {

	}

	public ArrayStack(int maxSize) {
		this.maxSize = maxSize;
		stack = new int[maxSize];
	}

	/**
	 * 返回栈顶元素的值
	 *
	 * @return
	 */
	public int peek() {
		return this.stack[this.top];
	}

	/**
	 * 判断是否栈满
	 *
	 * @return
	 */
	public boolean isStackFull() {
		return top == maxSize - 1;
	}

	/**
	 * 判断是否栈空
	 *
	 * @return
	 */
	public boolean isStackNull() {
		return top == -1;
	}

	/**
	 * 数据入栈
	 *
	 * @param value
	 */
	public void push(int value) {
		//判断栈是否已满
		if (isStackFull()) {
			System.out.println("栈已经满了，数据无法入栈");
			return;
		}
		//如果栈没有满，那么数据入栈
		this.top++;
		this.stack[this.top] = value;
	}

	/**
	 * 数据出栈
	 *
	 * @return
	 */
	public int pop() {
		//判断栈是否为空
		if (isStackNull()) {
			throw new RuntimeException("栈已经为空，没有数据可以出栈");
		}
		//如果栈不为空
		int value = this.stack[this.top];
		this.top--;
		return value;
	}

	/**
	 * 遍历栈中所有的元素
	 */
	public void list() {
		if (isStackNull()) {
			System.out.println("栈空间为空，没有数据可以打印输出");
			return;
		}

		for (int i = top; i >= 0; i--) {
			System.out.printf("栈中元素stack[%d]=%d\n", i, stack[i]);
		}
	}

	/**
	 * 返回栈底元素
	 *
	 * @param stack
	 * @return
	 */
	public int returnLastElement(ArrayStack stack) {
		int topElement = stack.pop();
		if (stack.isStackNull()) {
			return topElement;
		} else {
			//通过递归返回栈底元素的值
			int last = returnLastElement(stack);
			//存储当前的值
			stack.push(last);
			return last;
		}
	}

	/**
	 * 栈中元素的逆转
	 */
	public void reverse(ArrayStack stack) {
		//判断栈空
		if (isStackNull()) {
			return;
		}
		//取出栈顶元素
		int topElement = returnLastElement(stack);
			reverse(stack);
		//将元素入栈
		stack.push(topElement);
	}

	/**
	 * 运算符的优先级
	 *
	 * @param operator
	 * @return
	 */
	public int operatorPriority(int operator) {
		if (operator == '(' || operator == ')') {
			return 2;
		} else if (operator == '*' || operator == '/') {
			return 1;
		} else if (operator == '+' || operator == '-') {
			return 0;
		} else {
			return -1;
		}
	}

	/**
	 * 判断是否是运算符
	 *
	 * @param operator
	 * @return
	 */
	public boolean isOperator(int operator) {
		return operator == '*' || operator == '/' || operator == '+' || operator == '-';
	}

	/**
	 * 判断运算符的类型
	 *
	 * @param num1
	 * @param num2
	 * @param operator
	 * @return
	 */
	public int OperatorResult(int num1, int num2, int operator) {
		int result = 0;
		if (isOperator(operator)) {
			switch (operator) {
				case '*':
					result = num2 * num1;
					break;
				case '/':
					result = num2 / num1;
					break;
				case '+':
					result = num2 + num1;
					break;
				case '-':
					result = num2 - num1;
					break;
				default:
					break;
			}
		}

		return result;
	}
}
