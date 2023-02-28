package com.mashibing;

import com.common.SlidingWindowUtils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * arr是货币数组，其中的值都是正数，再给定一个正数aim
 * 每个值都认为是一张货币，返回组成aim的最少货币数
 * <p>
 * 注意：
 * 因为是求最少货币数，所以每一张货币认为是相同或者不同就不重要了
 *
 * @author xcy
 * @date 2022/5/15 - 8:58
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
			int ans1 = returnLeastCurrencyNumRecursion(arr, aim);
			int ans2 = returnLeastCurrencyNumRecursion_II(arr, aim);
			int ans3 = returnLeastCurrencyNumWithDynamicProgramming(arr, aim);
			int ans4 = returnLeastCurrencyNumWithDynamicProgramming_II(arr, aim);
			int ans5 = returnLeastCurrencyNumWithTable_III(arr, aim);
			//int ans4 = dp3(arr, aim);
			if (ans1 != ans2 || ans1 != ans3 || ans1 != ans4 || ans1 != ans5) {
				System.out.println("Oops!");
				SlidingWindowUtils.printArray(arr);
				System.out.println(aim);
				System.out.println("ans1 = " + ans1);
				System.out.println("ans2 = " + ans2);
				System.out.println("ans3 = " + ans3);
				System.out.println("ans4 = " + ans4);
				System.out.println("ans5 = " + ans5);
				break;
			}
		}
		System.out.println("功能测试结束");

		System.out.println("==========");

		int aim = 0;
		int[] arr = null;
		long start;
		long end;
		int ans2;
		int ans3;

		System.out.println("性能测试开始");
		maxLen = 30000;
		maxValue = 20;
		aim = 60000;
		arr = SlidingWindowUtils.randomArray(maxLen, maxValue);

		start = System.currentTimeMillis();
		ans2 = returnLeastCurrencyNumWithDynamicProgramming_II(arr, aim);
		end = System.currentTimeMillis();
		System.out.println("returnLeastCurrencyNumWithTable_II() 答案 : " + ans2 + ", 运行时间 : " + (end - start) + " ms");

		start = System.currentTimeMillis();
		ans3 = returnLeastCurrencyNumWithTable_III(arr, aim);
		end = System.currentTimeMillis();
		System.out.println("returnLeastCurrencyNumWithTable_III() 答案 : " + ans3 + ", 运行时间 : " + (end - start) + " ms");
		System.out.println("性能测试结束");

		/*System.out.println("===========");

		System.out.println("货币大量重复出现情况下，");
		System.out.println("大数据量测试returnLeastCurrencyNumWithTable_II() 开始");
		maxLen = 20000000;
		aim = 10000;
		maxValue = 10000;
		arr = SlidingWindowUtils.randomArray(maxLen, maxValue);
		start = System.currentTimeMillis();
		returnLeastCurrencyNumWithTable_II(arr, aim);
		end = System.currentTimeMillis();
		System.out.println("returnLeastCurrencyNumWithTable_II() 运行时间 : " + (end - start) + " ms");
		System.out.println("大数据量测试returnLeastCurrencyNumWithTable_II()结束");
		System.out.println("..........");

		System.out.println("大数据量测试returnLeastCurrencyNumWithTable_III() 开始");
		maxLen = 20000000;
		aim = 10000;
		maxValue = 10000;
		arr = SlidingWindowUtils.randomArray(maxLen, maxValue);
		start = System.currentTimeMillis();
		returnLeastCurrencyNumWithTable_III(arr, aim);
		end = System.currentTimeMillis();
		System.out.println("returnLeastCurrencyNumWithTable_III() 运行时间 : " + (end - start) + " ms");
		System.out.println("大数据量测试returnLeastCurrencyNumWithTable_III()结束");

		System.out.println("==========");

		System.out.println("当货币很少出现重复，returnLeastCurrencyNumWithTable_II() 比returnLeastCurrencyNumWithTable_III() 有常数时间优势");
		System.out.println("当货币大量出现重复，returnLeastCurrencyNumWithTable_III() 时间复杂度明显优于returnLeastCurrencyNumWithTable_II()");
		System.out.println("returnLeastCurrencyNumWithTable_III() 的优化用到了窗口内最小值的更新结构");
		*/
	}


	/**
	 * 使用暴力递归的方式 --> 版本I
	 *
	 * @param arr 原始数组
	 * @param aim 需要组成的正数
	 * @return 返回使用arr数组的元素组成aim的最少货币数
	 */
	public static int returnLeastCurrencyNumRecursion(int[] arr, int aim) {
		if (arr == null || arr.length == 0 || aim < 0) {
			return 0;
		}
		return coreLogic(arr, 0, aim);
	}

	/**
	 * 核心逻辑
	 *
	 * @param arr   原始数组
	 * @param index 原始数组的索引
	 * @param rest  剩余需要组成的正数
	 * @return 返回使用arr数组的元素组成rest的最少货币数
	 */
	public static int coreLogic(int[] arr, int index, int rest) {
		//剩余需要组成的正数竟然小于0，说明使用多少张arr数组中货币都无法组成，返回Integer.MAX_VALUE
		if (rest < 0) {
			return Integer.MAX_VALUE;
		}
		//如果已经没有货币了
		if (index == arr.length) {
			//如果剩余需要组成的正数rest等于0，说明还需要0张货币来组成
			//否则表示使用多少张arr数组中货币都无法组成，返回Integer.MAX_VALUE
			return rest == 0 ? 0 : Integer.MAX_VALUE;
		} else {
			//情况1：不选择当前货币
			int situation1 = coreLogic(arr, index + 1, rest);
			//情况2：选择当前货币
			int situation2 = coreLogic(arr, index + 1, rest - arr[index]);
			//判断情况2的返回值是否有效
			if (situation2 != Integer.MAX_VALUE) {
				//如果有效，累加
				situation2++;
			}
			return Math.min(situation1, situation2);
		}
	}

	/**
	 * 使用暴力递归的方式 --> 版本II
	 *
	 * @param arr 原始数组
	 * @param aim 需要组成的正数
	 * @return 返回使用arr数组的元素组成aim的最少货币数
	 */
	public static int returnLeastCurrencyNumRecursion_II(int[] arr, int aim) {
		if (arr == null || arr.length == 0 || aim < 0) {
			return 0;
		}
		return coreLogic_II(arr, 0, aim);
	}

	/**
	 * 核心逻辑
	 *
	 * @param arr   原始数组
	 * @param index 原始数组的索引
	 * @param rest  剩余需要组成的正数
	 * @return 返回使用aim数组中的元素能够组成aim的最少货币数
	 */
	public static int coreLogic_II(int[] arr, int index, int rest) {
		//已经没有钱了
		if (index == arr.length) {
			//判断rest还有剩余没有组成完毕
			//如果没有，则还需要0张，如果有，则表示数组中的面值无法组成aim，返回Integer.MAX_VALUE
			return rest == 0 ? 0 : Integer.MAX_VALUE;
		} else {
			int ans = Integer.MAX_VALUE;
			//张数从0开始，直到张数 * 当前货币 > 剩余组成的正数为止
			for (int paper = 1; paper * arr[index] <= rest; paper++) {
				//选择当前货币之后，下一张货币的情况
				int next = coreLogic_II(arr, index + 1, rest - paper * arr[index]);
				//判断返回值是否有效
				if (next != Integer.MAX_VALUE) {
					//如果有效当前货币
					ans = Math.min(ans, next + paper);
				}
			}
			return ans;
		}
	}

	/**
	 * 使用动态规划的方式 --> 版本I(使用暴力递归I的方式转换 --> 不去除重复的货币面值)
	 *
	 * @param arr 原始的数组
	 * @param aim 需要组成的正数
	 * @return 返回使用aim数组中的元素能够组成aim的最少货币数
	 */
	public static int returnLeastCurrencyNumWithDynamicProgramming(int[] arr, int aim) {
		if (arr == null || arr.length == 0 || aim < 0) {
			return 0;
		}
		int[][] table = new int[arr.length + 1][aim + 1];
		//if (index == arr.length) {
		//    return rest == 0 ? 0 : Integer.MAX_VALUE;
		//}
		//因为table[index == arr.length][rest == 0] = 0，所以rest从1开始
		for (int rest = 1; rest <= aim; rest++) {
			//table[index == arr.length][rest == 0] = 0;
			table[arr.length][rest] = Integer.MAX_VALUE;
		}
		//因为index位置依赖index + 1的位置，所以从下往上填写
		//table数组第arr.length]行已经填写完毕，所以index从arr.length - 1开始
		for (int index = arr.length - 1; index >= 0; index--) {
			for (int rest = 0; rest <= aim; rest++) {
				//情况1：不选择当前货币
				int situation1 = table[index + 1][rest];
				//情况2：选择当前货币
				int situation2 = rest - arr[index] >= 0 ? table[index + 1][rest - arr[index]] : Integer.MAX_VALUE;
				if (situation2 != Integer.MAX_VALUE) {
					situation2++;
				}
				table[index][rest] = Math.min(situation1, situation2);
			}
		}

		//return coreLogic(arr, 0, aim);
		return table[0][aim];
	}

	/**
	 * 货币类
	 */
	public static class Currency {
		/**
		 * 货币的面值
		 */
		public int[] banknotes;
		/**
		 * 货币的张数
		 */
		public int[] papers;

		public Currency() {

		}

		public Currency(int[] banknotes, int[] papers) {
			this.banknotes = banknotes;
			this.papers = papers;
		}
	}

	/**
	 * 获取Currency货币类的对象
	 *
	 * @param arr 原始数组
	 * @return 返回Currency货币类的对象
	 */
	public static Currency getCurrency(int[] arr) {
		if (arr == null || arr.length == 0) {
			return new Currency();
		}
		//词频统计
		HashMap<Integer, Integer> map = new HashMap<>();
		for (int value : arr) {
			if (!map.containsKey(value)) {
				map.put(value, 1);
			} else {
				map.put(value, map.get(value) + 1);
			}
		}
		int size = map.size();
		//创建货币的面值数组
		int[] banknotes = new int[size];
		//创建货币的张数数组
		int[] papers = new int[size];
		int index = 0;
		for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
			banknotes[index] = entry.getKey();
			papers[index] = entry.getValue();
			index++;
		}
		return new Currency(banknotes, papers);
	}

	/**
	 * 使用动态规划的方式 --> 版本II(使用暴力递归方式II转换 --> 去除重复的货币面值)
	 * 时间复杂度：O(N * aim) N表示arr数组的长度
	 *
	 * @param arr 原始的数组
	 * @param aim 需要组成的正数
	 * @return 返回使用aim数组中的元素能够组成aim的最少货币数
	 */
	public static int returnLeastCurrencyNumWithDynamicProgramming_II(int[] arr, int aim) {
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

	/**
	 * 使用动态规划的方式 --> 版本III(不仅去除了重复的货币面值，还使用了滑动窗口)
	 * 时间复杂度：O(M * aim) M表示arr数组中货币面值的种数
	 *
	 * @param arr 原始的数组
	 * @param aim 需要组成的正数
	 * @return 返回使用aim数组中的元素能够组成aim的最少货币数
	 */
	public static int returnLeastCurrencyNumWithTable_III(int[] arr, int aim) {
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
			//rest表示分组的组数，从0开始，rest小于Math.min(aim + 1, 当前面值)
			//举例：即使货币的面值是100，但是aim是30，组数最多只能到达30
			//假设：aim = 30, 当前面值100
			//分组本来是：0 ~ 99，但是目标值aim = 30，所以分组是：0 ~ 30，也就是小于30 + 1
			for (int group = 0; group < Math.min(aim + 1, banknotes[index]); group++) {
				//当前面值 X
				//rest  rest + x   rest + 2 * x   rest + 3 * x
				//创建最小值的双端队列
				LinkedList<Integer> minWindow = new LinkedList<>();
				//将组数添加到队列中
				minWindow.add(group);
				//默认的最少张数是下一行当前列的位置的值
				table[index][group] = table[index + 1][group];
				//rest从组数 + 当前面值开始，不超过aim，下一次循环继续加上当前面值
				for (int rest = group + banknotes[index]; rest <= aim; rest += banknotes[index]) {
					//判断双端队列不为空
					//table[index + 1][minWindow.peekLast()]位置的值无效 或者
					//table[index + 1][minWindow.peekLast()]位置的值有效，并且加上补偿之后比table[index + 1][rest]大，那么就退出窗口
					while (!minWindow.isEmpty() &&
							(table[index + 1][minWindow.peekLast()] == Integer.MAX_VALUE
									//当在窗口中(双端队列的尾部元素)出来的数需要进行补偿
									|| table[index + 1][minWindow.peekLast()] + compensate(minWindow.peekLast(), rest, banknotes[index])
									>= table[index + 1][rest])) {
						//双端队列的尾部元素退出窗口
						minWindow.pollLast();
					}
					//添加到最小值的双端队列
					minWindow.add(rest);
					//逾期
					int overdue = rest - banknotes[index] * (papers[index] + 1);
					if (minWindow.peekFirst() == overdue) {
						minWindow.pollFirst();
					}
					//在窗口内使用最小值时，根据面值还需要补偿
					table[index][rest] = table[index + 1][minWindow.peekFirst()] + compensate(minWindow.peekFirst(), rest, banknotes[index]);
				}
			}
		}

		return table[0][aim];
	}

	/**
	 * 补偿方法
	 * x面值 -> a张 和 y面值 -> b张
	 * a和b比较需要进行补偿，a + (y - x)/面值 和 b进行比较
	 * 1.所以在窗口中出来的数需要进行补偿，根据面值补给合适的张数才决定要填谁，(y - x) / 面值
	 * 2.在窗口内使用最小值时，根据面值还需要补偿，才是正确的值
	 * 举例：
	 * 假设面值是3元
	 * 19元     22元     25元     28元
	 * ☆       ?
	 * d        c        b        a
	 * a 和 b比较最少张数值，那么b的张数是： b + 补偿的张数(28 - 25) / 面值3 = b + 1，然后b + 1和a + 0进行比较
	 * a 和 c比较最少张数值，那么c的张数是： c + 补偿的张数(28 - 22) / 面值3 = c + 2，然后c + 2和a + 0进行比较
	 * a 和 d比较最少张数值，那么d的张数是： d + 补偿的张数(28 - 19) / 面值3 = d + 3，然后d + 3和a + 0进行比较
	 *
	 * @param x        x货币
	 * @param y        y货币
	 * @param banknote 面值
	 * @return 返回补偿的张数
	 */
	public static int compensate(int x, int y, int banknote) {
		return (y - x) / banknote;
	}
}
