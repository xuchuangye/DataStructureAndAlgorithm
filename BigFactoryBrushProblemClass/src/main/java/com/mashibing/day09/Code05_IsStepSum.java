package com.mashibing.day09;

/**
 * 题目五：
 * 定义何为step sum？
 * 比如680，680 + 68 + 6 = 754，680的step sum叫754。
 * 给定一个正数num，判断它是不是某个数的step sum
 * <p>
 * 解题思路：
 * 使用二分搜索
 *
 * @author xcy
 * @date 2022/7/25 - 15:40
 */
public class Code05_IsStepSum {
	public static void main(String[] args) {
		int stepSum = isStepSum(754);
		System.out.println(stepSum);
	}

	/**
	 * 二分查找的时间复杂度：O(log以2为底的num为真数)
	 *
	 * @param num
	 * @return
	 */
	public static int isStepSum(int num) {
		int L = 0;
		int R = num;
		int mid = 0;
		int cur = 0;
		while (L <= R) {
			mid = L + ((R - L) >> 1);
			cur = stepSum(mid);

			if (cur == num) {
				return mid;
			} else if (cur < num) {
				L = mid + 1;
			} else {
				R = mid - 1;
			}
		}
		return -1;
	}

	/**
	 * 求出step sum的时间复杂度：O(log以10为底的num为真数)
	 *
	 * @param num
	 * @return
	 */
	public static int stepSum(int num) {
		int sum = 0;
		while (num != 0) {
			sum += num;
			num /= 10;
		}
		return sum;
	}
}
