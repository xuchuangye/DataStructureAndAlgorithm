package com.mashibing.day08;

import com.mashibing.common.TestUtils;

/**
 * 题目四：
 * 给定一个矩阵matrix，值有正、负、0。
 * 蛇可以空降到最左列的任何一个位置，初始增长值是0。蛇每一步可以选择右上、右、右下三个方向的任何一个前进
 * 沿途的数字累加起来，作为增长值；但是蛇一旦增长值为负数，就会死去。蛇有一种能力，可以使用一次：把某个格子里的数变成相反数
 * 蛇可以走到任何格子的时候停止，返回蛇能获得的最大增长值
 * <p>
 * 解题思路：
 * 1.蛇可以使用或者不使用能力
 * 2.蛇可以右上、右、右下进行移动
 * 3.蛇的初始增长值为0
 * 4.蛇的增长值为负数，就表示死去
 * 5.蛇可以在任意位置上停止
 *
 * @author xcy
 * @date 2022/7/24 - 9:30
 */
public class Code04_SnakeGame {
	public static void main(String[] args) {
		int len = 100;
		int value = 100;
		int testTimes = 10000;
		System.out.println("测试开始！");
		for (int i = 0; i < testTimes; i++) {
			int[][] matrix = TestUtils.randomMatrix(len, value);
			int value1 = snakesCanGetMaximumGrowth(matrix);
			int value2 = snakesCanGetMaximumGrowth2(matrix);
			int value3 = snakesCanGetMaximumGrowth3(matrix);
			int value4 = walk1(matrix);
			int value5 = walk2(matrix);
			if (value1 != value2 || value1 != value3 || value1 != value4 || value1 != value5) {
				System.out.println("测试出错！");
				System.out.println(value1);
				System.out.println(value2);
				System.out.println(value3);
				System.out.println(value4);
				break;
			}
		}
		System.out.println("测试结束！");
	}

	public static class Info {
		/**
		 * 不使用能力
		 */
		public int no;
		/**
		 * 使用能力
		 */
		public int yes;

		public Info(int no, int yes) {
			this.no = no;
			this.yes = yes;
		}
	}

	/**
	 * 使用暴力递归的方式
	 *
	 * @param matrix 地图
	 * @return 返回蛇使用能力或者不使用一次能力的最大增长值
	 */
	public static int snakesCanGetMaximumGrowth(int[][] matrix) {
		if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
			return 0;
		}
		int N = matrix.length;
		int M = matrix[0].length;
		Info cur = null;
		int ans = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				cur = coreLogic(matrix, i, j);
				ans = Math.max(ans, Math.max(cur.no, cur.yes));
			}
		}
		return ans;
	}

	/**
	 * 从最左侧的最优解，进行降落，来到(i, j)的位置，分为两种情况
	 * 情况1.最左列
	 * 情况2.不是最左列，有三种情况
	 * (1)左边有值
	 * (2)左上有值
	 * (3)左下有值
	 * <p>
	 * 使用能力，并且到达(i, j)位置的最大增长值，如果到达不了，no == -1
	 * 不使用能力，并且到达(i, j)位置的最大增长值，如果到达不了，yes == -1，因为能力只能使用一次，所以分为两种情况：
	 * a.之前使用过能力
	 * b.当前正使用能力
	 *
	 * @param matrix 地图
	 * @param i      横坐标
	 * @param j      纵坐标
	 * @return 所以每个位置返回两个值，一个是从始至终都不使用能力的最大增长值，一个是使用一次能力之后的最大增长值
	 */
	public static Info coreLogic(int[][] matrix, int i, int j) {
		//表示在最左列
		if (j == 0) {
			//不使用能力
			int no = Math.max(matrix[i][0], -1);
			//使用 能力
			int yes = Math.max(-matrix[i][0], -1);
			return new Info(no, yes);
		}
		//j > 0，表示不在最左列
		//之前不使用能力的最大增长值
		int preNo = -1;
		//之前使用能力的最大增长值
		int preYes = -1;
		//必有左边的值
		Info pre = coreLogic(matrix, i, j - 1);
		preNo = Math.max(preNo, pre.no);
		preYes = Math.max(preYes, pre.yes);

		//i > 0表示必有左上的值
		if (i > 0) {
			pre = coreLogic(matrix, i - 1, j - 1);
			preNo = Math.max(preNo, pre.no);
			preYes = Math.max(preYes, pre.yes);
		}

		//i < matrix.length - 1表示必有左下的值
		if (i < matrix.length - 1) {
			pre = coreLogic(matrix, i + 1, j - 1);
			preNo = Math.max(preNo, pre.no);
			preYes = Math.max(preYes, pre.yes);
		}
		//到达当前matrix[i][j]的位置，并且不使用能力的最大增长值
		//如果之前不使用能力的最大增长值 == -1，那么肯定到达不了当前matrix[i][j]的位置，那么no == -1
		//如果之前不使用能力的最大增长值 != -1，那么直接加上当前matrix[i][j]的值即可，并且和-1进行比较，防止出现死亡的情况下
		//no也 == -1
		int no = preNo == -1 ? -1 : Math.max(-1, preNo + matrix[i][j]);

		//情况1
		//到达当前matrix[i][j]的位置之前，已经使用过能力了，那么直接加上matrix[i][j]的值即可
		int situation1 = preYes == -1 ? -1 : Math.max(-1, preYes + matrix[i][j]);
		//情况2
		//到达当前matrix[i][j]的位置之前，还没有使用过能力，将matrix[i][j]的值取反并相加
		int situation2 = preNo == -1 ? -1 : Math.max(-1, preNo - matrix[i][j]);
		//到达当前matrix[i][j]的位置，并且使用能力的最大增长值
		int yes = Math.max(-1, Math.max(situation1, situation2));
		return new Info(no, yes);
	}

	/**
	 * 使用暴力递归 + 傻缓存的方式
	 *
	 * @param matrix
	 * @return
	 */
	public static int snakesCanGetMaximumGrowth2(int[][] matrix) {
		if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
			return 0;
		}
		int N = matrix.length;
		int M = matrix[0].length;
		Info[][] dp = new Info[N][M];
		Info cur = null;
		int ans = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				cur = coreLogicWithCache(matrix, i, j, dp);
				ans = Math.max(ans, Math.max(cur.no, cur.yes));
			}
		}
		return ans;
	}

	public static Info coreLogicWithCache(int[][] matrix, int i, int j, Info[][] dp) {
		if (dp[i][j] != null) {
			return dp[i][j];
		}
		Info ans = null;
		//表示在最左列
		if (j == 0) {
			int no = Math.max(matrix[i][0], -1);
			int yes = Math.max(-matrix[i][0], -1);
			return new Info(no, yes);
		}
		//j > 0，表示不在最左列
		//之前不使用能力的最大增长值
		int preNo = -1;
		//之前使用能力的最大增长值
		int preYes = -1;
		//必有左边的值
		Info pre = coreLogicWithCache(matrix, i, j - 1, dp);
		preNo = Math.max(preNo, pre.no);
		preYes = Math.max(preYes, pre.yes);

		//i > 0表示必有左上的值
		if (i > 0) {
			pre = coreLogicWithCache(matrix, i - 1, j - 1, dp);
			preNo = Math.max(preNo, pre.no);
			preYes = Math.max(preYes, pre.yes);
		}

		//i < matrix.length - 1表示必有左下的值
		if (i < matrix.length - 1) {
			pre = coreLogicWithCache(matrix, i + 1, j - 1, dp);
			preNo = Math.max(preNo, pre.no);
			preYes = Math.max(preYes, pre.yes);
		}
		//到达当前matrix[i][j]的位置，并且不使用能力的最大增长值
		//如果之前不使用能力的最大增长值 == -1，那么肯定到达不了当前matrix[i][j]的位置，那么no == -1
		//如果之前不使用能力的最大增长值 != -1，那么直接加上当前matrix[i][j]的值即可，并且和-1进行比较，防止出现死亡的情况下
		//no也 == -1
		int no = preNo == -1 ? -1 : Math.max(-1, preNo + matrix[i][j]);

		//情况1
		//到达当前matrix[i][j]的位置之前，已经使用过能力了，那么直接加上matrix[i][j]的值即可
		int situation1 = preYes == -1 ? -1 : Math.max(-1, preYes + matrix[i][j]);
		//情况2
		//到达当前matrix[i][j]的位置之前，还没有使用过能力，将matrix[i][j]的值取反并相加
		int situation2 = preNo == -1 ? -1 : Math.max(-1, preNo - matrix[i][j]);
		//到达当前matrix[i][j]的位置，并且使用能力的最大增长值
		int yes = Math.max(-1, Math.max(situation1, situation2));
		ans = new Info(no, yes);
		dp[i][j] = ans;
		return ans;
	}


	/**
	 * 使用暴力递归 -> 动态规划的方式
	 *
	 * @param matrix 地图
	 * @return 返回蛇使用能力或者不使用一次能力的最大增长值
	 */
	public static int snakesCanGetMaximumGrowth3(int[][] matrix) {
		if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
			return 0;
		}
		int N = matrix.length;
		int M = matrix[0].length;
		Info[][] dp = new Info[N][M];

		for (int i = 0; i < N; i++) {
			//不使用能力
			int no = Math.max(matrix[i][0], -1);
			//使用 能力
			int yes = Math.max(-matrix[i][0], -1);
			dp[i][0] = new Info(no, yes);
		}

		for (int i = 0; i < N; i++) {
			for (int j = 1; j < M; j++) {
				//j > 0，表示不在最左列
				//之前不使用能力的最大增长值
				int preNo = -1;
				//之前使用能力的最大增长值
				int preYes = -1;
				//必有左边的值
				Info pre = dp[i][j - 1];
				preNo = Math.max(preNo, pre.no);
				preYes = Math.max(preYes, pre.yes);

				//i > 0表示必有左上的值
				if (i > 0) {
					pre = dp[i - 1][j - 1];
					preNo = Math.max(preNo, pre.no);
					preYes = Math.max(preYes, pre.yes);
				}

				//i < matrix.length - 1表示必有左下的值
				if (i < matrix.length - 1) {
					pre = dp[i + 1][j - 1];
					preNo = Math.max(preNo, pre.no);
					preYes = Math.max(preYes, pre.yes);
				}
				//到达当前matrix[i][j]的位置，并且不使用能力的最大增长值
				//如果之前不使用能力的最大增长值 == -1，那么肯定到达不了当前matrix[i][j]的位置，那么no == -1
				//如果之前不使用能力的最大增长值 != -1，那么直接加上当前matrix[i][j]的值即可，并且和-1进行比较，防止出现死亡的情况下
				//no也 == -1
				int no = preNo == -1 ? -1 : Math.max(-1, preNo + matrix[i][j]);

				//情况1
				//到达当前matrix[i][j]的位置之前，已经使用过能力了，那么直接加上matrix[i][j]的值即可
				int situation1 = preYes == -1 ? -1 : Math.max(-1, preYes + matrix[i][j]);
				//情况2
				//到达当前matrix[i][j]的位置之前，还没有使用过能力，将matrix[i][j]的值取反并相加
				int situation2 = preNo == -1 ? -1 : Math.max(-1, preNo - matrix[i][j]);
				//到达当前matrix[i][j]的位置，并且使用能力的最大增长值
				int yes = Math.max(-1, Math.max(situation1, situation2));
				dp[i][j] = new Info(no, yes);
			}
		}
		Info cur = null;
		int ans = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				cur = dp[i][j];
				ans = Math.max(ans, Math.max(cur.no, cur.yes));
			}
		}
		return ans;
	}

	public static int walk1(int[][] matrix) {
		if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
			return 0;
		}
		int N = matrix.length;
		int M = matrix[0].length;

		int ans = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				Info cur = walk1(matrix, i, j);
				ans = Math.max(ans, Math.max(cur.no, cur.yes));
			}
		}
		return ans;
	}
	/**
	 * 蛇从某一个最左列，且最优的空降点降落
	 * 沿途走到(i,j)必须停！
	 * 返回，一次能力也不用，获得的最大成长值
	 * 返回，用了一次能力，获得的最大成长值
	 * 如果蛇从某一个最左列，且最优的空降点降落，不用能力，怎么都到不了(i,j)，那么no = -1
	 * 如果蛇从某一个最左列，且最优的空降点降落，用了一次能力，怎么都到不了(i,j)，那么yes = -1
	 * @param matrix
	 * @param i
	 * @param j
	 * @return
	 */
	public static Info walk1(int[][] matrix, int i, int j) {
		if (j == 0) { // 最左列
			int no = Math.max(matrix[i][0], -1);
			int yes = Math.max(-matrix[i][0], -1);
			return new Info(no, yes);
		}
		// j > 0 不在最左列
		int preNo = -1;
		int preYes = -1;
		Info pre = walk1(matrix, i, j - 1);
		preNo = Math.max(pre.no, preNo);
		preYes = Math.max(pre.yes, preYes);
		if (i > 0) {
			pre = walk1(matrix, i - 1, j - 1);
			preNo = Math.max(pre.no, preNo);
			preYes = Math.max(pre.yes, preYes);
		}
		if (i < matrix.length - 1) {
			pre = walk1(matrix, i + 1, j - 1);
			preNo = Math.max(pre.no, preNo);
			preYes = Math.max(pre.yes, preYes);
		}
		int no = preNo == -1 ? -1 : (Math.max(-1, preNo + matrix[i][j]));
		// 能力只有一次，是之前用的！
		int p1 = preYes == -1 ? -1 : (Math.max(-1, preYes + matrix[i][j]));
		// 能力只有一次，就当前用！
		int p2 = preNo == -1 ? -1 : (Math.max(-1, preNo - matrix[i][j]));
		int yes = Math.max(Math.max(p1, p2), -1);
		return new Info(no, yes);
	}

	// 从假想的最优左侧到达(i,j)的旅程中
	// 0) 在没有使用过能力的情况下，返回路径最大和，没有可能到达的话，返回负
	// 1) 在使用过能力的情况下，返回路径最大和，没有可能到达的话，返回负
	public static int[] process(int[][] m, int i, int j) {
		if (j == 0) { // (i,j)就是最左侧的位置
			return new int[]{m[i][j], -m[i][j]};
		}
		int[] preAns = process(m, i, j - 1);
		// 所有的路中，完全不使用能力的情况下，能够到达的最好长度是多大
		int preUnuse = preAns[0];
		// 所有的路中，使用过一次能力的情况下，能够到达的最好长度是多大
		int preUse = preAns[1];
		if (i - 1 >= 0) {
			preAns = process(m, i - 1, j - 1);
			preUnuse = Math.max(preUnuse, preAns[0]);
			preUse = Math.max(preUse, preAns[1]);
		}
		if (i + 1 < m.length) {
			preAns = process(m, i + 1, j - 1);
			preUnuse = Math.max(preUnuse, preAns[0]);
			preUse = Math.max(preUse, preAns[1]);
		}
		// preUnuse 之前旅程，没用过能力
		// preUse 之前旅程，已经使用过能力了
		int no = -1; // 之前没使用过能力，当前位置也不使用能力，的最优解
		int yes = -1; // 不管是之前使用能力，还是当前使用了能力，请保证能力只使用一次，最优解
		if (preUnuse >= 0) {
			no = m[i][j] + preUnuse;
			yes = -m[i][j] + preUnuse;
		}
		if (preUse >= 0) {
			yes = Math.max(yes, m[i][j] + preUse);
		}
		return new int[]{no, yes};
	}

	/**
	 * 老师的方法
	 *
	 * @param matrix
	 * @return
	 */
	public static int walk2(int[][] matrix) {
		if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
			return 0;
		}
		int max = Integer.MIN_VALUE;
		int[][][] dp = new int[matrix.length][matrix[0].length][2];
		for (int i = 0; i < dp.length; i++) {
			dp[i][0][0] = matrix[i][0];
			dp[i][0][1] = -matrix[i][0];
			max = Math.max(max, Math.max(dp[i][0][0], dp[i][0][1]));
		}
		for (int j = 1; j < matrix[0].length; j++) {
			for (int i = 0; i < matrix.length; i++) {
				int preUnuse = dp[i][j - 1][0];
				int preUse = dp[i][j - 1][1];
				if (i - 1 >= 0) {
					preUnuse = Math.max(preUnuse, dp[i - 1][j - 1][0]);
					preUse = Math.max(preUse, dp[i - 1][j - 1][1]);
				}
				if (i + 1 < matrix.length) {
					preUnuse = Math.max(preUnuse, dp[i + 1][j - 1][0]);
					preUse = Math.max(preUse, dp[i + 1][j - 1][1]);
				}
				dp[i][j][0] = -1;
				dp[i][j][1] = -1;
				if (preUnuse >= 0) {
					dp[i][j][0] = matrix[i][j] + preUnuse;
					dp[i][j][1] = -matrix[i][j] + preUnuse;
				}
				if (preUse >= 0) {
					dp[i][j][1] = Math.max(dp[i][j][1], matrix[i][j] + preUse);
				}
				max = Math.max(max, Math.max(dp[i][j][0], dp[i][j][1]));
			}
		}
		return max;
	}
}
