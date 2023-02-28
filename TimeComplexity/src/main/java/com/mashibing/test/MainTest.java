package com.mashibing.test;

/**
 * @author xcy
 * @date 2021/9/10 - 21:22
 */

/**
 * 题目一：如何不用额外的变量交换两个数的值
 */
public class MainTest {
	public static void main(String[] args) {
		int count = bit1Counts(15423);
		System.out.println("该数字的二进制数的位数上出现1的次数为 " + count);
	}

	/**
	 * 打印出一个数的二级制数上出现所有的1的次数
	 * @return
	 */
	public static int bit1Counts(int num) {
		//默认为0次
		int count = 0;



		while (num != 0) {
			//11 1100 0011 1111
			//00 0000 0000 0001取出最右侧的1
			int maxRight = num &((~num) + 1);
			count++;
			//次数加1，再与最右侧的1进行异或，一次取出每一个最右侧的1
			//11 1100 0011 1110
			num ^= maxRight;
		}

		return count;
	}
}
