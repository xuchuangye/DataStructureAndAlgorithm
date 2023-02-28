package com.mashibing.dynamic;

/**
 * arr是货币数组，其中的值都是正数，在给定一个正数aim，每个值都认为是一张货币。
 * 即使是值相同的货币也认为每一张不相同的，返回组成aim的方法数
 * <p>
 * 举例：arr = {1, 1, 1} aim = 2
 * 第0个和第1个能组成2，第0个和第2个能组成2，第1个和第2个能组成2，一共3种方法，所有返回3
 *
 * @author xcy
 * @date 2022/5/11 - 8:16
 */
public class CoinsWayEveryPaperDifferent {
	public static void main(String[] args) {
		int[] arr = {1, 1, 1};
		int aim = 2;
		int count1 = coinsWays(arr, aim);
		int count2 = coinsWaysWithTable(arr, aim);
		System.out.println(count1);
		System.out.println(count2);
	}

	/**
	 * 返回arr数组中可以组成aim的方法数 --> 使用暴力递归的方式
	 *
	 * @param arr 表示钱的数组
	 * @param aim 需要使用钱组成的正数值
	 * @return 返回arr数组中可以组成aim的方法数
	 */
	public static int coinsWays(int[] arr, int aim) {
		if (arr == null || arr.length == 0 || aim < 0) {
			return 0;
		}
		return coreLogic(arr, 0, aim);
	}

	/**
	 * 核心逻辑
	 *
	 * @param arr   表示钱的数组
	 * @param index 对应钱的数组的索引 --> 可变参数
	 * @param rest  剩余需要钱组成的正数 --> 可变参数
	 * @return 返回arr数组中可以组成aim的方法数
	 */
	public static int coreLogic(int[] arr, int index, int rest) {
		//所有的钱都是正数，aim也是正数，rest < 0既表示可能钱比较大，无法组成aim，也表示aim本身就是正数，存在负数直接返回0
		if (rest < 0) {
			return 0;
		}
		//判断index遍历arr数组结束时
		if (index == arr.length) {
			//如果rest == 0，表示可以组成aim，返回1，否则不能组成aim，返回0
			return rest == 0 ? 1 : 0;
		}
		//两种情况
		//情况1：需要当前arr数组中index位置上的钱进行组合
		int situation1 = coreLogic(arr, index + 1, rest - arr[index]);
		//情况2：不需要当前arr数组中index位置上的钱进行组合
		int situation2 = coreLogic(arr, index + 1, rest);
		return situation1 + situation2;
	}

	/**
	 * 返回arr数组中可以组成aim的方法数 --> 使用动态规划的方式
	 *
	 * @param arr 表示钱的数组
	 * @param aim 需要使用钱组成的正数值
	 * @return 返回arr数组中可以组成aim的方法数
	 */
	public static int coinsWaysWithTable(int[] arr, int aim) {
		if (arr == null || arr.length == 0 || aim < 0) {
			return 0;
		}

		if (aim == 0) {
			return 1;
		}
		int[][] table = new int[arr.length + 1][aim + 1];
		//if (index == arr.length) {
		//	return rest == 0 ? 1 : 0;
		//}
		//index == arr.length && aim == 0
		table[arr.length][0] = 1;

		for (int index = arr.length - 1; index >= 0; index--) {
			for (int rest = 0; rest <= aim; rest++) {
				//两种情况
				//情况1：需要当前arr数组中index位置上的钱进行组合
				int situation1 = 0;
				if (rest - arr[index] >= 0) {
					situation1 = table[index + 1][rest - arr[index]];
				}
				//情况2：不需要当前arr数组中index位置上的钱进行组合
				int situation2 = table[index + 1][rest];
				table[index][rest] = situation1 + situation2;
			}
		}
		//coreLogic(arr, 0, aim);
		return table[0][aim];
	}
}
