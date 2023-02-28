package com.mashibing.dynamic;

import com.mashibing.common.DynamicUtils;

/**
 * arr是面值数组，其中的值都是正数并且不重复，再给定一个正数aim
 * 每个值都认为是一种面值，且任务张数是无限的，返回组成aim的方法数
 *
 * 举例：arr = {1, 2} aim = 4
 * 方法如下： 1 + 1 + 1 + 1， 1 + 1 + 2, 2 + 2
 * 一共3种方法，所以返回3
 * @author xcy
 * @date 2022/5/11 - 9:17
 */
public class CoinsWayNoLimit {
	public static void main(String[] args) {
		int maxLen = 10;
		int maxValue = 30;
		int testTime = 1000000;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int[] arr = DynamicUtils.randomArray(maxLen, maxValue);
			int aim = (int) (Math.random() * maxValue);
			int ans1 = coinsWays(arr, aim);
			int ans2 = coinsWaysWithTable(arr, aim);
			int ans3 = coinsWaysWithTableOptimization(arr, aim);
			if (ans1 != ans2 || ans1 != ans3) {
				System.out.println("Oops!");
				DynamicUtils.printArray(arr);
				System.out.println(aim);
				System.out.println(ans1);
				System.out.println(ans2);
				System.out.println(ans3);
				break;
			}
		}
		System.out.println("测试结束");
	}

	/**
	 * 返回arr数组中能够组成aim的方法数，没有限制 --> 使用暴力递归的方式
	 * @param arr 表示面值的数组
	 * @param aim 需要组成的数值
	 * @return 返回arr数组中能够组成aim的方法数
	 */
	public static int coinsWays(int[] arr, int aim) {
		if (arr == null || arr.length == 0 || aim < 0) {
			return 0;
		}
		return coreLogic(arr, 0, aim);
	}

	/**
	 * 核心逻辑
	 * @param arr 表示面值的数组
	 * @param index 对应面值数组的索引
	 * @param rest 剩余需要组成的数值
	 * @return  返回arr数组中能够组成rest的方法数
	 */
	public static int coreLogic(int[] arr, int index, int rest) {
		if (index == arr.length) {
			return rest == 0 ? 1 : 0;
		}

		int ways = 0;
		//从当前arr数组中index位置上的第0张开始推导，以及index之后位置和rest - (张数 * 当前index位置上的面值)
		//接着从从当前arr数组中index位置上的第1张、第2张、...开始推导
		//直到张数 * arr[index]的面值 <= rest结束
		for (int paperNum = 0; paperNum * arr[index] <= rest; paperNum++) {
			ways += coreLogic(arr, index + 1, rest - (arr[index] * paperNum));
		}
		return ways;
	}

	/**
	 * 返回arr数组中能够组成aim的方法数，没有限制 --> 使用动态规划的方式
	 * @param arr 表示面值的数组
	 * @param aim 需要组成的数值
	 * @return 返回arr数组中能够组成aim的方法数，没有限制
	 */
	public static int coinsWaysWithTable(int[] arr, int aim) {
		if (arr == null || arr.length == 0 || aim < 0) {
			return 0;
		}
		//if (index == arr.length) {
		//	return rest == 0 ? 1 : 0;
		//}
		int[][] table = new int[arr.length + 1][aim + 1];
		table[arr.length][0] = 1;

		for (int index = arr.length - 1; index >= 0; index--) {
			for (int rest = 0; rest <= aim; rest++) {
				int ways = 0;
				//从当前arr数组中index位置上的第0张开始推导，以及index之后位置和rest - (张数 * 当前index位置上的面值)
				//接着从从当前arr数组中index位置上的第1张、第2张、...开始推导
				//直到张数 * arr[index]的面值 <= rest结束
				for (int paperNum = 0; paperNum * arr[index] <= rest; paperNum++) {
					ways += table[index + 1] [rest - (arr[index] * paperNum)];
				}
				table[index][rest] =  ways;
			}
		}
		//return coreLogic(arr, 0, aim);
		return table[0][aim];
	}

	/**
	 * 返回arr数组中能够组成aim的方法数，没有限制 --> 使用动态规划优化之后的方式
	 * @param arr 表示面值的数组
	 * @param aim 需要组成的数值
	 * @return 返回arr数组中能够组成aim的方法数，没有限制
	 */
	public static int coinsWaysWithTableOptimization(int[] arr, int aim) {
		if (arr == null || arr.length == 0 || aim < 0 ) {
			return 0;
		}
		int[][] table = new int[arr.length + 1][aim + 1];
		//if (index == arr.length) {
		//    return = rest == 0 ? 1 : 0;
		//}
		//table[index == arr.length][rest == 0]
		table[arr.length][0] = 1;

		for (int index = arr.length - 1; index >= 0; index--) {
			for (int rest = 0; rest <= aim; rest++) {
				//首先获取下一行当前列的值
				table[index][rest] = table[index + 1][rest];
				//判断如果rest - arr[index] >= 0
				if (rest - arr[index] >= 0) {
					//然后再获取当前这一行前一列的值
					table[index][rest] += table[index][rest - arr[index]];
				}
			}
		}
		return table[0][aim];
	}
}
