package com.mashibing;

/**
 * 异或运算
 * 题目三：怎么把一个int类型的二进制的数,提取出最右侧的1
 *
 * 思路分析：
 * 	1、将该二进制数进行取反~
 * 	2、然后该取反之后的二进制数末尾+1
 * 	3、最后得到的结果与原来的数本身进行&运算，也就是(~a + 1) & a 或者 (-a) & a
 * @author xcy
 * @date 2022/3/11 - 16:39
 */
public class InterviewQuestions003 {
	public static void main(String[] args) {
		int a = 10;
		a = a & (~a + 1);
		int b = 10;
		b = b & (-b);
		System.out.println(a);
		System.out.println(b);
	}

}
