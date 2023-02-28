package com.mashibing.dynamic;

import com.mashibing.common.DynamicUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * arr是货币数组，其中的值都是正数，给定一个正数aim
 * 每个值都认为是一张货币，认为值相同的货币没有任何不同，返回组成aim的方法数
 * <p>
 * 举例：arr = {1, 2, 1, 1, 2, 1, 2} aim = 4
 * 方法： 1 + 1 + 1 + 1, 1 + 1 + 2, 2 + 2，一共有3种方法，所以返回3
 *
 * @author xcy
 * @date 2022/5/11 - 11:05
 */
public class CoinsWaySameValueSamePaper {
	public static void main(String[] args) {
		int maxLen = 10;
		int maxValue = 20;
		int testTime = 1000000;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int[] arr = DynamicUtils.randomArray(maxLen, maxValue);
			int aim = (int) (Math.random() * maxValue);
			int ans1 = coinsWaysSameValueSamePaper(arr, aim);
			int ans2 = coinsWaysSameValueSamePaperWithTable(arr, aim);
			int ans3 = coinsWaysSameValueSamePaperWithTableOptimization(arr, aim);
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

	public static class Paper {
		/**
		 * 纸币的面值
		 */
		public int[] banknotes;
		/**
		 * 纸币的张数
		 */
		public int[] papers;

		public Paper(int[] b, int[] p) {
			banknotes = b;
			papers = p;
		}
	}

	/**
	 * 获取纸张对象
	 *
	 * @param arr 原始货币的数组
	 * @return 返回纸张对象
	 */
	public static Paper getPaper(int[] arr) {
		if (arr == null || arr.length == 0) {
			return new Paper(null, null);
		}
		//建立词频统计
		//key：使用arr数组的元素值作为货币的值
		//value：使用arr数组的元素值出现的次数作为货币的张数
		HashMap<Integer, Integer> counts = new HashMap<>();
		for (int value : arr) {
			if (!counts.containsKey(value)) {
				counts.put(value, 1);
			} else {
				counts.put(value, counts.get(value) + 1);
			}
		}
		int size = counts.size();
		//根据去除货币重复的面值，获取货币的种类
		//创建纸张的面值的数组
		int[] banknotes = new int[size];
		//创建纸张的张数的数组
		int[] papers = new int[size];

		int index = 0;
		for (Map.Entry<Integer, Integer> entry : counts.entrySet()) {
			banknotes[index] = entry.getKey();
			papers[index] = entry.getValue();
			index++;
		}
		return new Paper(banknotes, papers);
	}

	/**
	 * 获取arr数组中能够组成aim的方法数 --> 使用暴力递归的方式
	 *
	 * @param arr arr表示货币的数组
	 * @param aim 组成的值
	 * @return 返回arr数组中能够组成aim的方法数
	 */
	public static int coinsWaysSameValueSamePaper(int[] arr, int aim) {
		if (arr == null || arr.length == 0 || aim < 0) {
			return 0;
		}
		Paper paper = getPaper(arr);
		return coreLogic(paper.banknotes, paper.papers, 0, aim);
	}

	/**
	 * 核心逻辑
	 *
	 * @param banknotes 表示货币的面值数组
	 * @param papers    表示货币的张数数组
	 * @param index     表示货币的面值数组的索引
	 * @param rest      表示剩余需要组成的正数值
	 * @return 返回arr数组中能够组成aim的方法数
	 */
	public static int coreLogic(int[] banknotes, int[] papers, int index, int rest) {
		//没有纸币了
		if (index == banknotes.length) {
			return rest == 0 ? 1 : 0;
		}
		int ways = 0;
		//当前张数 * 当前index所在的货币面值 <= 余需要组成的正数值 && 当前张数 <= 该货币面值记录的张数
		for (int paper = 0; paper * banknotes[index] <= rest && paper <= papers[index]; paper++) {
			//累加下一个index以及rest - 张数 * 当前index的货币面值
			ways += coreLogic(banknotes, papers, index + 1, rest - (paper * banknotes[index]));
		}
		return ways;
	}

	/**
	 * 返回arr数组中能够组成aim的方法数 --> 使用动态规划的方式
	 *
	 * @param arr 表示货币的数组
	 * @param aim 表示需要组成的正数值
	 * @return 返回arr数组中能够组成aim的方法数
	 */
	public static int coinsWaysSameValueSamePaperWithTable(int[] arr, int aim) {
		if (arr == null || arr.length == 0 || aim < 0) {
			return 0;
		}
		Paper info = getPaper(arr);
		int[] banknotes = info.banknotes;
		int[] papers = info.papers;
		//return coreLogic(paper.banknotes, paper.papers, 0, aim);
		int[][] table = new int[banknotes.length + 1][aim + 1];
		//if (index == banknotes.length) {
		//	return rest == 0 ? 1 : 0;
		//}
		//table[index == banknotes][rest == 0] = 1;
		table[banknotes.length][0] = 1;

		//最后一行已经填充完毕，所以从banknotes.length - 1开始
		for (int index = banknotes.length - 1; index >= 0; index--) {
			//
			for (int rest = 0; rest <= aim; rest++) {
				int ways = 0;
				for (int paper = 0; paper * banknotes[index] <= rest && paper <= papers[index]; paper++) {
					ways += table[index + 1][rest - (paper * banknotes[index])];
				}
				table[index][rest] = ways;
			}
		}
		//return coreLogic(paper.banknotes, paper.papers, 0, aim);
		return table[0][aim];
	}

	/**
	 * 返回arr数组中能够组成aim的方法数 --> 使用动态规划的方式(优化的版本)
	 * @param arr 表示货币的数组
	 * @param aim 表示需要组成的正数值
	 * @return 返回arr数组中能够组成aim的方法数
	 */
	public static int coinsWaysSameValueSamePaperWithTableOptimization(int[] arr, int aim) {
		if (arr == null || arr.length == 0 || aim < 0) {
			return 0;
		}
		Paper info = getPaper(arr);
		//货币的面值数组
		int[] banknotes = info.banknotes;
		//货币的张数数组
		int[] papers = info.papers;
		//二维数组
		int[][] table = new int[banknotes.length + 1][aim + 1];
		//if (index == banknotes.length) {
		//	return rest == 0 ? 1 : 0;
		//}
		//table[index == banknotes.length][rest == 0] = 1;
		table[banknotes.length][0] = 1;
		for (int index = banknotes.length - 1; index >= 0; index--) {
			for (int rest = 0; rest <= aim; rest++) {
				/*int ways = 0;
				for (int paper = 0; paper * banknotes[index] <= rest && paper < papers[index]; paper++) {
					ways += table[index + 1][rest - (paper * banknotes[index])];
				}
				table[index][rest] = ways;*/
				//首先加上下一行当前列的值
				table[index][rest] = table[index + 1][rest];
				//判断如果满足条件，加上左边☆的值
				if (rest - banknotes[index] >= 0) {
					table[index][rest] += table[index][rest - banknotes[index]];
				}
				//判断如果满足条件，减去☆最后一个元素的值，因为☆和当前√的值多算了一个
				if (rest - banknotes[index] * (papers[index] + 1) >= 0) {
					table[index][rest] -= table[index + 1][rest - banknotes[index] * (papers[index] + 1)];
				}
			}
		}
		//return coreLogic(paper.banknotes, paper.papers, 0, aim);
		return table[0][aim];
	}
}
