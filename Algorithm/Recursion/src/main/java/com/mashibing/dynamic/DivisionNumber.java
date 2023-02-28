package com.mashibing.dynamic;

/**
 * 分裂一个数的问题
 * <p>
 * 要求：给定一个正数，分裂一个数可以只有该数自己，也可以分裂多个数
 * 当分裂一个数为多个数时，后一个数不能比前一个数小
 * 返回分裂一个数有多少种方式
 * <p>
 * 举例：
 * number = 1, 分裂的结果：1 一共1种方式
 * number = 2, 分裂的结果：1 + 1, 2 一共2种方式
 * number = 3, 分裂的结果：1 + 1 + 1, 1 + 2, 3 一共3种方式
 *
 * @author xcy
 * @date 2022/5/12 - 16:39
 */
public class DivisionNumber {
	public static void main(String[] args) {
		int times = 100;
		System.out.println("测试开始");
		for (int i = 0; i < times; i++) {
			int number = (int) (Math.random() * 100) + 1;
			int count1 = divisionNumber(number);
			int count2 = divisionNumberWithTable(number);
			int count3 = divisionNumberWithTableOptimization(number);
			if (count1 != count2 || count1 != count3) {
				System.out.println("测试失败");
			}
		}
		System.out.println("测试结束");
	}

	/**
	 * 使用暴力递归的方式
	 *
	 * @param number 一个正数
	 * @return 返回分裂该数的方法数
	 */
	public static int divisionNumber(int number) {
		if (number < 0) {
			return 0;
		}
		if (number == 1) {
			return 1;
		}
		return coreLogic(1, number);
	}

	/**
	 * 核心逻辑
	 *
	 * @param pre  拆分的前一个数
	 * @param rest 剩余可拆分的数
	 * @return 返回剩余可拆分的数的方法数
	 */
	public static int coreLogic(int pre, int rest) {
		if (rest == 0) {
			return 1;
		}
		//后一个数不能比前一个数小
		//当拆分当前数之后，rest - pre作为下一轮的pre
		if (pre > rest) {
			return 0;
		}

		int ways = 0;
		//将pre的赋值给first，让first作为本次循环的递归中的pre参数，让rest- first作为本次循环的递归中的rest参数
		for (int first = pre; first <= rest; first++) {
			//将所有的可能性累加
			ways += coreLogic(first, rest - first);
		}
		return ways;
	}

	/**
	 * 使用动态规划的方式
	 *
	 * @param number 一个正数
	 * @return 返回分裂该数的方法数
	 */
	public static int divisionNumberWithTable(int number) {
		if (number < 0) {
			return 0;
		}
		if (number == 1) {
			return 1;
		}
		int[][] table = new int[number + 1][number + 1];

		for (int pre = 0; pre <= number; pre++) {
			table[pre][0] = 1;
			table[pre][pre] = 1;
		}

		for (int pre = number - 1; pre >= 0; pre--) {
			//table[]数组中rest < pre的位置都为0，rest == pre的位置都为1
			for (int rest = pre + 1; rest <= number; rest++) {
				int ways = 0;
				//将pre的赋值给first，让first作为本次循环的递归中的pre参数，让rest- first作为本次循环的递归中的rest参数
				for (int first = pre; first <= rest; first++) {
					//将所有的可能性累加
					ways += table[first][rest - first];
				}
				table[pre][rest] = ways;
			}
		}
		//return coreLogic(1, number);
		return table[1][number];
	}

	/**
	 * 使用动态规划优化之后的方式
	 *
	 * @param number 一个正数
	 * @return 返回分裂该数的方法数
	 */
	public static int divisionNumberWithTableOptimization(int number) {
		if (number < 0) {
			return 0;
		}
		if (number == 1) {
			return 1;
		}
		int[][] table = new int[number + 1][number + 1];

		for (int pre = 0; pre <= number; pre++) {
			//table第0列填写完毕
			table[pre][0] = 1;
			//table对角线填写完毕
			table[pre][pre] = 1;
		}

		for (int pre = number - 1; pre >= 0; pre--) {
			//table[]数组中rest < pre的位置都为0，rest == pre的位置都为1
			for (int rest = pre + 1; rest <= number; rest++) {
				/*int ways = 0;
				//将pre的赋值给first，让first作为本次循环的递归中的pre参数，让rest- first作为本次循环的递归中的rest参数
				for (int first = pre; first <= rest; first++) {
					//将所有的可能性累加
					ways += table[first][rest - first];
				}
				table[pre][rest] = ways;*/

				//首先当前这一行当前列的位置table[pre][rest]依赖当前下一行当前列的位置table[pre + 1][rest]
				//因为当前这一行当前列依赖如下的位置：
				//[pre][rest - pre]
				//[pre + 1]rest - pre - 1]
				//[pre + 2]rest - pre - 2]
				//[pre + 3]rest - pre - 3]
				//...
				//[pre == rest][rest == 0]

				//又因为当前下一行当前列依赖如下的位置：
				//除了这个位置：[pre][rest - pre]
				//其余都包含
				//[pre + 1]rest - pre - 1]
				//[pre + 2]rest - pre - 2]
				//[pre + 3]rest - pre - 3]
				//...
				//[pre == rest][rest == 0]
				//所以当前这一行当前列的位置记录当前下一行当前列的位置即可：table[pre][rest] == table[pre + 1][rest]
				table[pre][rest] = table[pre + 1][rest];

				//如果rest - pre没有越界
				//当前这一行当前列的位置累加当前这一行前一列的位置
				if (rest - pre >= 0) {
					table[pre][rest] += table[pre][rest - pre];
				}
			}
		}
		//return coreLogic(1, number);
		return table[1][number];
	}
}
