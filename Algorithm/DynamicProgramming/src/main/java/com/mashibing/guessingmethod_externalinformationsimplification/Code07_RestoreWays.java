package com.mashibing.guessingmethod_externalinformationsimplification;

/**
 * 题目七：
 * 整型数组arr长度为n(3 <= n <= 10^4)，最初每个数字是<=200的正数且满足如下条件：
 * 1.arr[0] <= arr[1]
 * 2.arr[n-1] <= arr[n-2]
 * 3.arr[i] <= max(arr[i-1], arr[i+1])
 * 但是在arr有些数字丢失了，比如k位置的数字之前是正数，
 * 丢失之后k位置的数字为0。
 * 请你根据上述条件， 计算可能有多少种不同的arr可以满足以上条件。
 * 比如 [6,0,9] 只有还原成 [6,9,9]满足全部三个条件，所以返回1种。
 *
 * @author xcy
 * @date 2022/6/18 - 11:03
 */
public class Code07_RestoreWays {
	public static void main(String[] args) {

	}


}
