package com.mashibing.slidingwindow;

import com.common.SlidingWindowUtils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * @author xcy
 * @date 2022/5/16 - 8:19
 */
public class LeastCoinsOnePaper {
	public static void main(String[] args) {
		int maxLen = 20;
		int maxValue = 30;
		int testTime = 300000;
		System.out.println("功能测试开始");
		for (int i = 0; i < testTime; i++) {
			int N = (int) (Math.random() * maxLen);
			int[] arr = SlidingWindowUtils.randomArray(N, maxValue);
			int aim = (int) (Math.random() * maxValue);
			int ans4 = returnLeastCurrencyNumWithTable_II(arr, aim);
			int ans5 = returnLeastCurrencyNumWithTable_III(arr, aim);
			//int ans4 = dp3(arr, aim);
			if (ans4 != ans5) {
				System.out.println("Oops!");
				SlidingWindowUtils.printArray(arr);
				System.out.println(aim);
				System.out.println("ans4 = " + ans4);
				System.out.println("ans5 = " + ans5);
				break;
			}
		}
		System.out.println("功能测试结束");
	}

	/**
	 * 使用动态规划的方式 --> 版本II(使用暴力递归方式II转换 --> 去除重复的货币面值)
	 * 时间复杂度：O(N * aim) N表示arr数组的长度
	 *
	 * @param arr 原始的数组
	 * @param aim 需要组成的正数
	 * @return 返回使用aim数组中的元素能够组成aim的最少货币数
	 */
	public static int returnLeastCurrencyNumWithTable_II(int[] arr, int aim) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		//获取Currency对象，时间复杂度：O(arr.length)
		Currency currency = getCurrency(arr);
		//获取货币的面值数组
		int[] banknotes = currency.banknotes;
		//获取货币的张数数组
		int[] papers = currency.papers;

		//动态规划
		int[][] table = new int[banknotes.length + 1][aim + 1];
		//当index == banknotes.length时，
		//rest != 0时，所有的位置都是Integer.MAX_VALUE
		//rest == 0时，当前的位置是0
		for (int rest = 1; rest <= aim; rest++) {
			//table[banknotes.length][rest == 0] = 0;
			table[banknotes.length][rest] = Integer.MAX_VALUE;
		}

		//index位置的值依赖index + 1位置上的值，所以是从下往上遍历
		//并且index ==  banknotes.length时的所有位置都已经遍历过，所以index从banknotes.length - 1开始到0结束
		for (int index = banknotes.length - 1; index >= 0; index--) {
			//rest == 0已经遍历过，所以rest从1开始
			for (int rest = 1; rest <= aim; rest++) {
				//默认当前位置的最少张数从table[index + 1][rest]取
				table[index][rest] = table[index + 1][rest];
				//张数paper从1开始，有两个限制条件
				//1.当前张数 * 当前货币面值不能超过aim
				//2.当前张数必须小于等于当前货币的面值统计的张数
				for (int paper = 1; paper * banknotes[index] <= aim && paper <= papers[index]; paper++) {
					//防止越界问题
					//rest - paper * banknotes[index] >= 0表示当前位置的左边rest - 张数 * 当前货币面值 的位置
					if (rest - paper * banknotes[index] >= 0
							//判断table[index + 1][rest - paper * banknotes[index]]的位置的值有效
							&& table[index + 1][rest - paper * banknotes[index]] != Integer.MAX_VALUE) {
						//
						table[index][rest] = Math.min(table[index][rest], paper + table[index + 1][rest - paper * banknotes[index]]);
					}
				}
			}
		}

		return table[0][aim];
	}

	public static class Currency {
		public int[] banknotes;
		public int[] papers;

		public Currency() {

		}

		public Currency(int[] b, int[] p) {
			banknotes = b;
			papers = p;
		}
	}

	public static Currency getCurrency(int[] arr) {
		if (arr == null || arr.length == 0) {
			return new Currency();
		}
		HashMap<Integer, Integer> map = new HashMap<>();
		for (int value : arr) {
			if (!map.containsKey(value)) {
				map.put(value, 1);
			} else {
				map.put(value, map.get(value) + 1);
			}
		}
		int size = map.size();

		int[] banknotes = new int[size];
		int[] papers = new int[size];
		int index = 0;
		for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
			banknotes[index] = entry.getKey();
			papers[index] = entry.getValue();
			index++;
		}
		return new Currency(banknotes, papers);
	}

	public static int returnLeastCurrencyNumWithTable_III(int[] arr, int aim) {
		if (arr == null || arr.length == 0 || aim < 0) {
			return 0;
		}
		Currency currency = getCurrency(arr);
		int[] banknotes = currency.banknotes;
		int[] papers = currency.papers;

		int[][] table = new int[banknotes.length + 1][aim + 1];

		for (int rest = 1; rest <= aim; rest++) {
			table[banknotes.length][rest] = Integer.MAX_VALUE;
		}

		for (int index = banknotes.length - 1; index >= 0; index--) {
			for (int group = 0; group < Math.min(aim + 1, banknotes[index]); group++) {
				LinkedList<Integer> minWindow = new LinkedList<>();
				minWindow.add(group);
				//默认最小值是当前下一行当前列的位置的值
				table[index][group] = table[index + 1][group];
				for (int rest = group + banknotes[index]; rest <= aim; rest += banknotes[index]) {
					while (!minWindow.isEmpty() && (table[index + 1][minWindow.peekLast()] == Integer.MAX_VALUE
							|| table[index + 1][minWindow.peekLast()] + compensate(minWindow.peekLast(), rest, banknotes[index])
							>= table[index + 1][rest])) {
						minWindow.pollLast();
					}
					minWindow.add(rest);
					int overdue = rest - banknotes[index] * (papers[index] + 1);
					if (minWindow.peekFirst() == overdue) {
						minWindow.pollFirst();
					}
					table[index][rest] = table[index + 1][minWindow.peekFirst()] + compensate(minWindow.peekFirst(), rest, banknotes[index]);
				}
			}
		}

		return table[0][aim];
	}

	public static int compensate(int x, int y, int banknote) {
		return (y - x) / banknote;
	}
}
