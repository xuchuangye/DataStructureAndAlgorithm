package com.mashibing.state_compression;

import java.util.ArrayList;
import java.util.List;

/**
 * 题目二：
 * TSP问题有N个城市，任何两个城市之间的都有距离，任何一座城市到自己的距离都为0。
 * 所有点到点的距离都存在一个N * N的二维数组matrix里，也就是整张图由邻接矩阵表示。
 * 现要求一旅行商从k城市出发必须经过每一个城市且只在一个城市逗留一次，最后回到出发的k城，返回总距离最短的路的距离。
 * 参数给定一个matrix，给定k。
 *
 * @author xcy
 * @date 2022/6/14 - 8:32
 */
public class Code02_TSPProblem {
	public static void main(String[] args) {

	}

	/**
	 * 使用暴力递归的方式
	 *
	 * @param matrix
	 * @return
	 */
	public static int t1(int[][] matrix) {
		List<Integer> set = new ArrayList<>();
		// set.get(i) != null i这座城市在集合里
		// set.get(i) == null i这座城市不在集合里
		for (int i = 0; i < matrix.length; i++) {
			set.add(i);
		}
		return coreLogic(matrix, set, 0);
	}

	/**
	 * 核心逻辑
	 *
	 * @param matrix 任何两座城市之间的距离，可以在matrix里面拿到
	 * @param set    表示着哪些城市的集合
	 * @param start  这座城一定在set里,
	 * @return 从start出发，要把set中所有的城市过一遍，最终回到0这座城市，最小距离是多少
	 */
	public static int coreLogic(int[][] matrix, List<Integer> set, int start) {
		//当前城市的计数
		int cityCount = 0;
		for (Integer city : set) {
			if (city != null) {
				cityCount++;
			}
		}
		//如果只有一座城市
		if (cityCount == 1) {
			//返回start城市到全局初始点的距离
			return matrix[start][0];
		}
		//cityCount > 1，表示不只有start这一座城市
		//将start设置为null
		set.set(start, null);
		int min = Integer.MAX_VALUE;
		for (int i = 0; i < set.size(); i++) {
			if (set.get(i) != null /*&& i != start*/) {
				//start到i的距离 + i回到全局初始点0的距离
				int cur = matrix[start][i] + coreLogic(matrix, set, i);
				min = Math.min(min, cur);
			}
		}
		set.set(start, 1);
		return min;
	}

	/**
	 * 使用暴力递归 + 位运算的方式
	 *
	 * @param matrix
	 * @return
	 */
	public static int t2(int[][] matrix) {
		//如果matrix.length == 8，那么1左移8位 -> 100000000，减去1 -> 011111111
		int status = (1 << matrix.length) - 1;
		return coreLogicWithBitOperations(matrix, status, 0);
	}

	/**
	 * 核心逻辑
	 *
	 * @param matrix     任何两座城市之间的距离，可以在matrix里面拿到
	 * @param cityStatus 标记城市是否存在，如果是0表示不存在，如果是1表示存在
	 * @param start      start这座城一定在cityStatus标记了
	 * @return 从start出发，要把cityStatus中标记所有的城市都过一遍，最终回到0这座城市，最小距离是多少
	 */
	public static int coreLogicWithBitOperations(int[][] matrix, int cityStatus, int start) {
		//如果只有一座城市start
		//cityStatus & (~cityStatus + 1)表示最右侧的1
		//如果只有最右侧的1表示只有一座城市
		if (cityStatus == (cityStatus & (~cityStatus + 1))) {
			return matrix[start][0];
		}
		//不只有start这一座城市
		cityStatus &= (~(1 << start));
		int min = Integer.MAX_VALUE;
		for (int i = 0; i < matrix.length; i++) {
			if ((cityStatus & (1 << i)) != 0) {
				int cur = matrix[start][i] + coreLogicWithBitOperations(matrix, cityStatus, i);
				min = Math.min(min, cur);
			}
			cityStatus |= (1 << start);
		}
		return min;
	}

	/**
	 * 使用暴力递归 + 位运算 + 傻缓存的方式
	 *
	 * @param matrix
	 * @return
	 */
	public static int t3(int[][] matrix) {
		//如果matrix.length == 8，那么1左移8位 -> 100000000，减去1 -> 011111111
		int allCity = (1 << matrix.length) - 1;
		int[][] dp = new int[1 << matrix.length][matrix.length];
		for (int i = 0; i < (1 << matrix.length); i++) {
			for (int j = 0; j < matrix.length; j++) {
				dp[i][j] = -1;
			}
		}
		return coreLogicWithBitOperationsAndCache(matrix, allCity, 0, dp);
	}

	/**
	 * 核心逻辑
	 *
	 * @param matrix     任何两座城市之间的距离，可以在matrix里面拿到
	 * @param cityStatus 标记城市是否存在，如果是0表示不存在，如果是1表示存在
	 * @param start      start这座城一定在cityStatus标记了
	 * @param dp         傻缓存
	 * @return start出发，要把cityStatus中标记所有的城市都过一遍，最终回到0这座城市，最小距离是多少
	 */
	public static int coreLogicWithBitOperationsAndCache(int[][] matrix, int cityStatus, int start, int[][] dp) {
		if (dp[cityStatus][start] != -1) {
			return dp[cityStatus][start];
		}
		//如果只有一座城市start
		//cityStatus & (~cityStatus + 1)表示最右侧的1
		//如果只有最右侧的1表示只有一座城市
		if (cityStatus == (cityStatus & (~cityStatus + 1))) {
			dp[cityStatus][start] = matrix[start][0];
		}
		//不只有start这一座城市
		cityStatus &= (~(1 << start));
		int min = Integer.MAX_VALUE;
		for (int i = 0; i < matrix.length; i++) {
			if ((cityStatus & (1 << i)) != 0) {
				int cur = matrix[start][i] + coreLogicWithBitOperationsAndCache(matrix, cityStatus, i, dp);
				min = Math.min(min, cur);
			}
			cityStatus |= (1 << start);
			dp[cityStatus][start] = min;
		}
		return dp[cityStatus][start];
	}
}
