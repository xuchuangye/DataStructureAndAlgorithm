package com.mashibing.day06;

/**
 * 题目五：
 * Nim博弈，给定一个正数数组arr，先手和后手每次可以选择在一个位置拿走若干值，这个值要大于0，但是要小于该处的剩余，
 * 谁最先拿空arr谁赢，根据arr返回谁赢
 *
 * @author xcy
 * @date 2022/7/21 - 8:58
 */
public class Code05_Nim {
	public static void main(String[] args) {

	}

	public static void printWinner(int[] arr) {
		if (arr == null || arr.length == 0) {
			return;
		}

		int xor = 0;
		for (int num : arr) {
			xor ^= num;
		}
		if (xor != 0) {
			System.out.println("先手赢！");
		}else {
			System.out.println("后手赢！");
		}
	}
}
