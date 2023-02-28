package com.mashibing.dynamic;

import com.mashibing.common.DynamicUtils;

/**
 * arr是面值数组，其中的值都是正数并且没有重复，每种面值没有张数限制。
 * 再给定一个正数aim，返回组成aim的货币最少的数
 *
 * @author xcy
 * @date 2022/5/12 - 15:17
 */
public class CoinsMinNoLimit {
	public static void main(String[] args) {
		int maxLen = 20;
		int maxValue = 30;
		int testTime = 300000;
		System.out.println("功能测试开始");
		for (int i = 0; i < testTime; i++) {
			int N = (int) (Math.random() * maxLen);
			int[] arr = DynamicUtils.randomArray(N, maxValue);
			int aim = (int) (Math.random() * maxValue);
			int ans1 = banknotesLeastNumber(arr, aim);
			int ans2 = banknotesLeastNumberWithTable(arr, aim);
			int ans3 = banknotesLeastNumberWithTableOptimization(arr, aim);
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
		System.out.println("功能测试结束");
	}

	/**
	 * 使用暴力递归的方式
	 *
	 * @param arr 表示面值的数组，值都是正数并且没有重复，张数没有限制
	 * @param aim 表示需要使用arr[]中的面值组成的正整数
	 * @return 返回组成aim的货币最少的张数
	 */
	public static int banknotesLeastNumber(int[] arr, int aim) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		return coreLogic(arr, 0, aim);
	}

	/**
	 * 核心逻辑
	 *
	 * @param arr   表示面值的数组，值都是正数并且没有重复，张数没有限制
	 * @param index 表示面值的数组的索引
	 * @param rest  表示需要使用arr[]中的面值组成的正整数
	 * @return 返回组成aim的货币最少的张数
	 */
	public static int coreLogic(int[] arr, int index, int rest) {
		//已经没有钱了
		if (index == arr.length) {
			//判断rest还有剩余没有组成完毕
			//如果没有，则还需要0张，如果有，则表示数组中的面值无法组成aim，返回Integer.MAX_VALUE
			return rest == 0 ? 0 : Integer.MAX_VALUE;
		} else {
			int ans = Integer.MAX_VALUE;
			//开始遍历arr中index位置上的面值，张数从第0张开始，直到张数 * 当前index位置的面值超过rest时结束
			//遍历arr中index + 1位置上的面值
			//依次类推
			for (int paper = 0; paper * arr[index] <= rest; paper++) {
				//遍历arr中下一个index + 1位置上的面值
				int next = coreLogic(arr, index + 1, rest - paper * arr[index]);
				//判断next的值是否有效
				if (next != Integer.MAX_VALUE) {
					//从ans和当前张数以及下一个张数中选择最小张数
					ans = Math.min(ans, next + paper);
				}
			}
			return ans;
		}
	}

	/**
	 * 使用动态规划的方式
	 *
	 * @param arr 表示面值的数组，值都是正数并且没有重复，张数没有限制
	 * @param aim 表示需要使用arr[]中的面值组成的正整数
	 * @return 返回组成aim的货币最少的张数
	 */
	public static int banknotesLeastNumberWithTable(int[] arr, int aim) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		int[][] table = new int[arr.length + 1][aim + 1];
		//if (index == arr.length) {
		//	return rest == 0 ? 0 : Integer.MAX_VALUE;
		//}
		table[arr.length][0] = 0;
		for (int rest = 1; rest <= aim; rest++) {
			table[arr.length][rest] = Integer.MAX_VALUE;
		}
		//因为是index位置依赖index + 1位置的值，所以从下往上填写
		//又因为table数组第arr.length行已经填写完毕，所以从第arr.length - 1开始往上填写
		for (int index = arr.length - 1; index >= 0; index--) {
			//每一列，rest从第0列开始，到aim结束
			for (int rest = 0; rest <= aim; rest++) {
				int ans = Integer.MAX_VALUE;
				//开始遍历arr中index位置上的面值，张数从第0张开始，直到张数 * 当前index位置的面值超过rest时结束
				//遍历arr中index + 1位置上的面值
				//依次类推
				for (int paper = 0; paper * arr[index] <= rest; paper++) {
					//遍历arr中下一个index + 1位置上的面值
					//int next = coreLogic(arr, index + 1, rest - paper * arr[index]);
					int next = table[index + 1][rest - paper * arr[index]];
					//判断next的值是否有效
					if (next != Integer.MAX_VALUE) {
						//从ans和当前张数以及下一个张数中选择最小张数
						ans = Math.min(ans, next + paper);
					}
				}
				//return ans;
				table[index][rest] = ans;
			}
		}

		//return coreLogic(arr, 0, aim);
		return table[0][aim];
	}

	/**
	 * 使用动态规划优化之后的方式
	 *
	 * @param arr 表示面值的数组，值都是正数并且没有重复，张数没有限制
	 * @param aim 表示需要使用arr[]中的面值组成的正整数
	 * @return 返回组成aim的货币最少的张数
	 */
	public static int banknotesLeastNumberWithTableOptimization(int[] arr, int aim) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		int[][] table = new int[arr.length + 1][aim + 1];
		//if (index == arr.length) {
		//	return rest == 0 ? 0 : Integer.MAX_VALUE;
		//}
		table[arr.length][0] = 0;
		for (int rest = 1; rest <= aim; rest++) {
			table[arr.length][rest] = Integer.MAX_VALUE;
		}
		//因为是index位置依赖index + 1位置的值，所以从下往上填写
		//又因为table数组第arr.length行已经填写完毕，所以从第arr.length - 1开始往上填写
		for (int index = arr.length - 1; index >= 0; index--) {
			//每一列，rest从第0列开始，到aim结束
			for (int rest = 0; rest <= aim; rest++) {
				//首先让当前下一行当前列的值作为最小张数值 --> a
				table[index][rest] = table[index + 1][rest];
				//rest - arr[index] >= 0表示当前这一行的前一列
				//table[index][rest - arr[index]] != Integer.MAX_VALUE判断当前这一行前一列的值是否有效
				if (rest - arr[index] >= 0 && table[index][rest - arr[index]] != Integer.MAX_VALUE) {
					//当前这一行当前列的最小张数table[index][rest] 从a + 0,b + 1,c + 2,d + 3 ... 中选择最小张数值
					//当前这一行前一列的最小张数table[index][rest - arr[index]] 从 b + 0,c + 1,d + 2 ... 中选择最小张数值
					//那么table[index][rest] 和 table[index][rest - arr[index]] + 1中选择最小张数值即可
					table[index][rest] = Math.min(table[index][rest], table[index][rest - arr[index]] + 1);
				}
			}
		}

		//return coreLogic(arr, 0, aim);
		return table[0][aim];
	}
}
