package com.mashibing;

import com.mashibing.stack.ArrayStack;

import java.util.Scanner;

/**
 * 测试使用数组表示的栈的操作
 * @author xcy
 * @date 2022/3/16 - 16:10
 */
public class StackDemo_001 {
	public static void main(String[] args) {
		ArrayStack arrayStack = new ArrayStack(3);

		String key = "";
		//是否退出程序，默认没有退出
		boolean isExit = true;
		Scanner scanner = new Scanner(System.in);

		while (isExit) {
			System.out.println("欢迎进行栈的测试");
			System.out.println("exit：退出循环");
			System.out.println("show：显示当前栈的所有元素");
			System.out.println("push：数据入栈");
			System.out.println("pop：数据出栈");
			System.out.println("请输入您的选择：");
			key = scanner.next();

			switch (key) {
				case "exit":
					scanner.close();
					isExit = false;
					break;
				case "show":
					arrayStack.list();
					break;
				case "push":
					System.out.println("请输入您要入栈的数据");
					int value = scanner.nextInt();

					arrayStack.push(value);
					break;
				case "pop":
					if (arrayStack.isStackNull()) {
						System.out.println("栈已经为空");
					}
					arrayStack.pop();
					break;
				default:
					System.out.println("您的选择不正确");
					break;
			}
		}
		System.out.println("再见！欢迎再次使用！");
	}
}
