package com.mashibing.day01;

/**
 * 题目三：
 * 给定一个非负整数num，
 * 如何不用循环语句，
 * 返回 >= num，并且离num最近的，2的某次方
 * @author xcy
 * @date 2022/7/4 - 10:59
 */
public class Code03_Near2Power {
	public static void main(String[] args) {
		int n = 120;
		int number1 = tableSizeFor1(n);
		int number2 = tableSizeFor2(n);
		System.out.println(number1);
		System.out.println(number2);
	}

	public static int tableSizeFor1(int n) {
		n--;
		n |= n >>> 1;
		n |= n >>> 2;
		n |= n >>> 4;
		n |= n >>> 8;
		n |= n >>> 16;
		//如果n本身就是负数，通过上述的操作，可能最终结果都是 < 0，直接返回1
		return n < 0 ? 1 : n + 1;
	}

	public static int tableSizeFor2(int n) {
		/*
		//最右侧的1
		int mostRight_1 = n & (~n + 1);
		//如果n == 最右侧的1，直接返回n
		if (n == mostRight_1) {
			return n;
		}*/
		if (n == (n & (~n + 1))) {
			return n;
		}
		n |= n >>> 1;
		n |= n >>> 2;
		n |= n >>> 4;
		n |= n >>> 8;
		n |= n >>> 16;
		return n < 0 ? 1 : n + 1;
	}
}
