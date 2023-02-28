package com.mashibing.day08;

import java.util.LinkedList;
import java.util.Objects;

/**
 * 题目一：
 * 给定一个字符串str，str表示一个公式，公式里可能有整数、加减乘除符号和左右括号。返回公式的计算结果
 * 难点在于括号可能嵌套很多层，str="48*((70-65)-43)+8*1"，返回-1816。str="3+1*4"，返回7。str="3+(1*4)"，返回7。
 * 题目说明：
 * 1，可以认为给定的字符串一定是正确的公式，即不需要对str做公式有效性检查
 * 2，如果是负数，就需要用括号括起来，比如"4*(-3)"但如果负数作为公式的开头或括号部分的开头，则可以没有括号，
 * 比如"-3*4"和"(-3*4)"都是合法的
 * 3，不用考虑计算过程中会发生溢出的情况。
 * <p>
 * LeetCode测试链接：
 * https://leetcode.com/problems/basic-calculator-iii/
 *
 * @author xcy
 * @date 2022/7/24 - 9:30
 */
public class Code01_ExpressionCompute {
	public static void main(String[] args) {
		String str="48*((70-65)-43)+8*1";
		String str2="4-8*((70-6+5)-4*3)+8*1";
		String str3="4+8*((700-6*50)-42/3)+80*(-100)";
		int result1 = calculate(str3);
		System.out.println(result1);
	}

	public static int calculate(String expression) {
		if (expression == null || expression.length() == 0) {
			return 0;
		}
		int[] value = value(expression.toCharArray(), 0);
		return value[0];
	}

	/**
	 * @param str expression的字符数组
	 * @param i   从expression的i位置出发，一直往右，直到遇到终止为止或者右括号)停，返回expression的结果
	 * @return int[]包含两个值：
	 * 第一个值：返回从i位置出发到字符串结束或者遇到右括号结束时，结果是什么
	 * 第二个值，函数是计算到哪里结束的，结束的位置需要知道
	 */
	public static int[] value(char[] str, int i) {
		//1.创建队列或者栈结构
		LinkedList<String> queue = new LinkedList<>();
		int cur = 0;

		int[] bra = null;
		//2.设置循环条件
		//i没有越界以及str[i]不等于右括号
		while (i < str.length && str[i] != ')') {
			//此时的str[i]表示数值
			if (str[i] >= '0' && str[i] <= '9') {
				cur = cur * 10 + (str[i] - '0');
				//往右移动
				i++;
			}
			//此时的str[i]表示运算符
			else if (str[i] != '(') {
				//先将cur的值添加进去
				addNum(queue, cur);
				//再将运算符添加进队列
				queue.addLast(String.valueOf(str[i]));
				//往右移动
				i++;
				//将cur重置
				cur = 0;
			}
			//此时的str[i]表示左括号
			//需要进行递归操作
			else {
				//返回两个信息
				bra = value(str, i + 1);
				//每一次进入左括号时，到距离最近的一次的右括号的计算结果
				cur = bra[0];
				//每一次进入左括号时，到距离最近的一次的右括号的计算完成时的结束位置 + 1
				//就是下一个计算的开始位置
				i = bra[1] + 1;
			}
		}

		//3.退出循环时的操作
		//退出循环时，最后一次的cur没有添加，现在添加进去
		addNum(queue, cur);
		return new int[]{getNum(queue), i};
	}

	/**
	 * 数值计算的方法
	 *
	 * @param queue 队列
	 * @param num   数值
	 */
	private static void addNum(LinkedList<String> queue, int num) {
		if (!queue.isEmpty()) {
			int cur = 0;
			//第一次最后轮询的是运算符
			String operator = queue.pollLast();
			if ("+".equals(operator) || "-".equals(operator)) {
				queue.addLast(operator);
			} else {
				//第二次最后轮询的就是数值
				cur = Integer.parseInt(Objects.requireNonNull(queue.pollLast()));
				num = "*".equals(operator) ? (cur * num) : (cur / num);
			}
		}
		//最后计算的结果重新入队列
		queue.addLast(String.valueOf(num));
	}

	/**
	 * 数值获取的方法
	 *
	 * @param queue 队列
	 * @return 根据匹配"+"或者"-"进行计算并返回结果
	 */
	private static int getNum(LinkedList<String> queue) {
		int ans = 0;
		boolean add = true;
		String cur = null;
		int num = 0;

		while (!queue.isEmpty()) {
			cur = queue.pollFirst();
			//+运算符
			if ("+".equals(cur)) {
				add = true;
			}
			//-运算符
			else if ("-".equals(cur)) {
				add = false;
			}
			//数值
			else {
				num = Integer.parseInt(cur);
				ans += add ? num : (-num);
			}
		}
		return ans;
	}
}
