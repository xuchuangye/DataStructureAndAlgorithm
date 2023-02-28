package com.mashibing.logarithmic_comparator;

/**
 * 题目三：
 * 定义一种数：可以表示成若干（数量>1）连续正数和的数
 * 比如:
 * 5 = 2 + 3，5就是这样的数
 * 12 = 3 + 4 + 5，12就是这样的数
 * 10 = 1 + 2 + 3 + 4,10就是这样的数
 * 1不是这样的数，因为题目要求数要大于1、连续正数和
 * 2 = 1 + 1，2也不是，因为等号右边不是连续正数
 * 给定一个参数N，返回是不是可以表示成若干连续正数和的数
 * 如果一个数可以表示成若干个连续正数的和，返回true，如果不能，返回false
 *
 * @author xcy
 * @date 2022/6/4 - 9:15
 */
public class SomeContinuityPositiveNumberSumOfNumber {
	public static void main(String[] args) {
		for (int num = 1; num <= 50; num++) {
			System.out.println(num + ":" + someContinuityPositiveNumberSumOfNumberOptimal(num));
		}
	}

	/**
	 * 暴力解
	 * @return 如果一个数可以表示成若干个连续正数的和，返回true，如果不能，返回false
	 */
	public static boolean someContinuityPositiveNumberSumOfNumber(int num) {
		//题目要求若干个大于1的正数，所以从1开始
		for (int start = 1; start <= num; start++) {
			int sum = start;
			for (int end = start + 1; end <= num; end++) {
				if (sum + end > num) {
					break;
				} else if (sum + end == num) {
					return true;
				} else {
					sum += end;
				}
			}
		}
		return false;
	}

	/**
	 * 最优解
	 *
	 * 1:false
	 * 2:false
	 * 3:true
	 * 4:false
	 * 5:true
	 * 6:true
	 * 7:true
	 * 8:false
	 * 9:true
	 * 10:true
	 * 11:true
	 * 12:true
	 * 13:true
	 * 14:true
	 * 15:true
	 * 16:false
	 * 17:true
	 * 18:true
	 * 19:true
	 * 20:true
	 * 21:true
	 * 22:true
	 * 23:true
	 * 24:true
	 * 25:true
	 * 26:true
	 * 27:true
	 * 28:true
	 * 29:true
	 * 30:true
	 * 31:true
	 * 32:false
	 *
	 * @return 如果一个数可以表示成若干个连续正数的和，返回true，如果不能，返回false
	 */
	public static boolean someContinuityPositiveNumberSumOfNumberOptimal(int num) {
		/*
		//方式一
		//取出num最右侧的1
		int mostRight_1 = num & (~num + 1);
		//如果num只有最右侧的1
		if (num == mostRight_1) {
			return false;
		}else {
			return true;
		}*/

		//方式二
		//return num != (num & (~num + 1));

		//方式三
		//return num != (num & (-num));

		//方式四
		//假设num = 10100000
		//&
		//num - 1 = 10011111
		//如果不等于0，表示num的二进制不止有一个1，num就不是2的某次方
		return (num & (num - 1)) != 0;
	}
}
