package com.mashibing.subject;

/**
 * @author xcy
 * @date 2021/9/13 - 19:50
 */

/**
 * 怎么把一个int类型的数,提取出最右侧的1
 */
public class Question003 {
	public static void main(String[] args) {
		//N & (~N + 1)
		int num = 14354333;
		//原码：1101 1011 0000 0111 1001 1101
		//反码：0010 0100 1111 1000 0110 0010
		//加一：0010 0100 1111 1000 0110 0011
		//1101 1011 0000 0111 1001 1101
		//&
		//0010 0100 1111 1000 0110 0011
		//0000 0000 0000 0000 0000 0001
		//得到最右侧的1
		System.out.println(num &(~num + 1));
	}
}
