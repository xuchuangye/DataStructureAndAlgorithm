package com.mashibing;

/**
 * 异或运算
 * 题目四：—个数组中有两种数出现了奇数次，其他数都出现了偶数次，找到并打印这两种数
 *
 * 思路分析：
 * 	1、假设这两种数是a和b，并且a和b肯定不相等，那么使用0循环遍历数组中的所有数，得到的结果必然是a ^ b
 * 	2、也就是最终异或的结果肯定不等于0，
 * 	3、获取最右侧的1，分为两种情况，最右侧的1和最右侧的不是1
 * 	4、循环遍历数组，判断最右侧的1为一组，结果是a，a ^ b ^ a得到的就是最右侧不是1的另一组，结果是b
 * @author xcy
 * @date 2022/3/11 - 16:39
 */
public class InterviewQuestions004 {
	public static void main(String[] args) {
		int[] arr = {1, 3, 3, 3, 5, 5, 6, 6, 7, 7};
		//eor异或数组中所有数之后只剩下两种数a ^ b的结果
		int eor = 0;
		for (int element : arr) {
			eor ^= element;
		}
		/*
		1、此时eor = a ^ b，a ^ b != 0，那么eor一定有一个位置上是1
		2、取出eor最右侧的1
		a和b是两种数
		eor != 0
		eor : 001011001110101000
		最右侧的1 right_1 : 000000000000001000
		 */
		//int right_1 = eor & (~eor + 1);
		int right_1 = eor & (-eor);

		//假设异或取出最右侧的1为a
		int eor_ = 0;
		for (int element : arr) {
			/*
			element & right_1 != 0表示element元素必然有一个位置上也是1
			并且将最右侧的1都归为一类
			 */
			if ((element & right_1) == 0) {
				eor_ ^= element;
			}
		}
		//eor_ ^ eor即为最右侧的不是1的为一类，也就是b
		System.out.println(eor_ + ", " + (eor_ ^ eor));
	}

}
