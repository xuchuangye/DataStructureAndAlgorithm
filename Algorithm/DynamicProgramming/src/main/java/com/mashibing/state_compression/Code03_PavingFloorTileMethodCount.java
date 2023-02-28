package com.mashibing.state_compression;

/**
 * 题目三：
 * 你有无限的1*2的砖块，要铺满M*N的区域，
 * 不同的铺法有多少种?
 * <p>
 * 思路分析：
 * 1.假设摆砖块的方式限制为向右和向上，并不影响最终的结果
 *
 * @author xcy
 * @date 2022/6/14 - 15:02
 */
public class Code03_PavingFloorTileMethodCount {
	public static void main(String[] args) {
		int testTimes = 100;
		System.out.println("测试开始！");
		for (int i = 0; i < testTimes; i++) {
			int N = (int) (Math.random() * 10) + 1;
			int M = (int) (Math.random() * 6) + 1;
			int count1 = returnPavingFullMethodCount(N, M);
			int count2 = returnPavingFullMethodCountWithBitOperations(N, M);
			int count3 = returnPavingFullMethodCountWithBitOperationsAndCache(N, M);
			if (count1 != count2 || count1 != count3) {
				System.out.println(count1);
				System.out.println(count2);
				System.out.println(count3);
				System.out.println("测试错误！");
				break;
			}
		}
		System.out.println("测试结束！");
	}

	/**
	 * 使用暴力递归的方式
	 *
	 * @param N 一共N
	 * @param M 一共M
	 * @return 做出的每一种选择，能够让从1行到N行全部铺满的方法数总和
	 */
	public static int returnPavingFullMethodCount(int N, int M) {
		if (N < 1 || M < 1 || ((N * M) & 1) != 0) {
			return 0;
		}
		//只有一列，或者只有一行，那么肯定只有一种摆放，返回1
		if (N == 1 || M == 1) {
			return 1;
		}
		//pre[]表示每一行的铺砖块的信息
		//pre[i] == 0表示没有铺上砖块
		//pre[i] == 1表示已经铺上砖块
		int[] pre = new int[M];
		for (int i = 0; i < pre.length; i++) {
			pre[i] = 1;
		}
		return coreLogic(pre, 0, N);
	}

	/**
	 * 潜在信息：level - 2层以及之上的层，都已经铺满砖块
	 *
	 * @param pre   pre[]表示level - 1层的铺砖块信息，0表示没有铺上砖块，1表示已经铺上砖块
	 * @param level 表示在level层铺砖块
	 * @param N     一共N层
	 * @return 在level铺砖块，让0 ~ N层都铺满砖块的方法数总和，并返回
	 */
	public static int coreLogic(int[] pre, int level, int N) {
		//举例：
		//level - 2 这一层肯定是铺满的
		//pre 这一层有0或者1
		//如果level已经来到N层，说明已经没有办法继续摆砖块了
		if (level == N) {
			//查看pre这一层，只要铺满了，全部都是1，说明有1种摆砖块的方法法
			for (int i = 0; i < pre.length; i++) {
				//如果只要有一个0，说明没有铺满砖块，也就是有0种摆砖块的方法数
				if (pre[i] == 0) {
					return 0;
				}
			}
			return 1;
		}
		//根据pre这一层决定level这一层的操作
		int[] op = accordingToPreLevelDecisionCurLevel(pre);
		//获取到op[]之后，从col == 0 这一列开始
		return dfs(op, 0, level, N);
	}

	/**
	 * 该方法表示在当前每一层的每一列进行深度优先搜索算法操作
	 *
	 * @param op    根据上一行决定当前这一行的操作
	 *              举例：假设pre[] = 0110011
	 *              那么op[] = 1001100,0表示没有铺上砖块，可以选择向右摆放；1表示已经铺上砖块，只能选择向上摆放
	 * @param col   当前这一列
	 * @param level 当前这一层
	 * @param N     一个N层
	 * @return 返回当前每一层的砖块摆放的方法数
	 */
	private static int dfs(int[] op, int col, int level, int N) {
		//col == op.length表示当前这一行已经做好决定了，继续去下一行根据op做决定
		if (col == op.length) {
			return coreLogic(op, level + 1, N);
		}
		//当前这一行砖块摆放的方法数
		int ans = 0;
		//col这一列不摆放砖块，继续col + 1列
		ans += dfs(op, col + 1, level, N);
		//col这一列摆放砖块，说明要向右横着摆放，因为1 * 2的砖块，所以占两列
		//因此要判断是否有col + 1列，如果有并且col列为0，col + 1列也为0，表示col和col + 1这两列可以摆放一块砖
		if (col + 1 < op.length && op[col] == 0 && op[col + 1] == 0) {
			//将col列标记为1
			op[col] = 1;
			//将col + 1标记为1
			op[col + 1] = 1;
			ans += dfs(op, col + 2, level, N);
			op[col] = 0;
			op[col + 1] = 0;
		}
		return ans;
	}

	/**
	 * op[i] == 0可以选择向右摆砖
	 * op[i] == 1只能选择向上摆砖
	 * 根据pre这一层来决定cur这一层的操作
	 *
	 * @param pre 上一层
	 * @return 返回cur这一层的操作的数组
	 */
	public static int[] accordingToPreLevelDecisionCurLevel(int[] pre) {
		int[] op = new int[pre.length];
		for (int i = 0; i < pre.length; i++) {
			//如果pre[i] == 1，op[i] == 0
			//如果pre[i] == 0，op[i] == 1
			op[i] = pre[i] ^ 1;
		}
		return op;
	}


	/**
	 * 使用暴力递归 + 位运算的方式
	 *
	 * @param N 一共N
	 * @param M 一共M
	 * @return 做出的每一种选择，能够让从1行到N行全部铺满的方法数总和
	 */
	public static int returnPavingFullMethodCountWithBitOperations(int N, int M) {
		if (N < 1 || M < 1 || ((N * M) & 1) != 0) {
			return 0;
		}
		//只有一列，或者只有一行，那么肯定只有一种摆放，返回1
		if (N == 1 || M == 1) {
			return 1;
		}
		int max = Math.max(N, M);
		int min = Math.min(N, M);
		int pre = (1 << min) - 1;
		return coreLogicWithBitOperations(pre, 0, max, min);
	}

	/**
	 * 潜在信息：level - 2层以及之上的层，都已经铺满砖块
	 *
	 * @param pre   pre[]表示level - 1层的铺砖块信息，0表示没有铺上砖块，1表示已经铺上砖块
	 * @param level 表示在level层铺砖块
	 * @param N     一共N层
	 * @param M     一共M列
	 * @return 在level铺砖块，让0 ~ N层都铺满砖块的方法数总和，并返回
	 */
	public static int coreLogicWithBitOperations(int pre, int level, int N, int M) {
		//举例：
		//level - 2 这一层肯定是铺满的
		//pre 这一层有0或者1
		//如果level已经来到N层，说明已经没有办法继续摆砖块了
		if (level == N) {
			//查看pre这一层，只要铺满了，全部都是1，说明有1种摆砖块的方法法
			//如果只要有一个0，说明没有铺满砖块，也就是有0种摆砖块的方法数
			return pre == ((1 << M) - 1) ? 1 : 0;
		}
		//根据pre这一层决定level这一层的操作
		int op = (~pre) & ((1 << M) - 1);
		//获取到op[]之后，从col == M - 1 这一列开始
		return dfsWithBitOperations(op, M - 1, level, N, M);
	}

	/**
	 * 该方法表示在当前每一层的每一列进行深度优先搜索算法操作
	 *
	 * @param op    根据上一行决定当前这一行的操作
	 *              举例：假设pre = 0110011
	 *              那么op = 1001100,0表示没有铺上砖块，可以选择向右摆放；1表示已经铺上砖块，只能选择向上摆放
	 * @param col   当前这一列
	 * @param level 当前这一层
	 * @param N     一共N层
	 * @param M     一共M列
	 * @return 返回当前每一层的砖块摆放的方法数
	 */
	private static int dfsWithBitOperations(int op, int col, int level, int N, int M) {
		//col == -1表示当前这一行已经做好决定了，继续去下一行根据op做决定
		if (col == -1) {
			return coreLogicWithBitOperations(op, level + 1, N, M);
		}
		//当前这一行砖块摆放的方法数
		int ans = 0;
		//col这一列不摆放砖块，继续col - 1列
		ans += dfsWithBitOperations(op, col - 1, level, N, M);
		//col这一列摆放砖块，说明要向右横着摆放，因为1 * 2的砖块，所以占两列
		//因此要判断是否有col - 1列，如果有并且col列为0，col - 1列也为0，表示col和col - 1这两列可以摆放一块砖
		if (col - 1 >= 0 && (op & (1 << col)) == 0 && (op & (1 << (col - 1))) == 0) {
			ans += dfsWithBitOperations(op | (3 << (col - 1)), col - 2, level, N, M);
		}
		return ans;
	}

	/**
	 * 使用暴力递归 + 位运算 + 傻缓存的方式
	 *
	 * @param N 一共N
	 * @param M 一共M
	 * @return 做出的每一种选择，能够让从1行到N行全部铺满的方法数总和
	 */
	public static int returnPavingFullMethodCountWithBitOperationsAndCache(int N, int M) {
		if (N < 1 || M < 1 || ((N * M) & 1) != 0) {
			return 0;
		}
		//只有一列，或者只有一行，那么肯定只有一种摆放，返回1
		if (N == 1 || M == 1) {
			return 1;
		}
		int max = Math.max(N, M);
		int min = Math.min(N, M);
		int pre = (1 << min) - 1;
		int[] dp = new int[(1 << max)];
		for (int i = 0; i < dp.length; i++) {
			dp[i] = -1;
		}
		return coreLogicWithBitOperationsAndCache(pre, 0, max, min, dp);
	}

	/**
	 * 潜在信息：level - 2层以及之上的层，都已经铺满砖块
	 *
	 * @param pre   pre[]表示level - 1层的铺砖块信息，0表示没有铺上砖块，1表示已经铺上砖块
	 * @param level 表示在level层铺砖块
	 * @param N     一共N层
	 * @param M     一共M列
	 * @param dp    傻缓存
	 * @return 在level铺砖块，让0 ~ N层都铺满砖块的方法数总和，并返回
	 */
	public static int coreLogicWithBitOperationsAndCache(int pre, int level, int N, int M, int[] dp) {
		//举例：
		//level - 2 这一层肯定是铺满的
		//pre 这一层有0或者1
		//如果level已经来到N层，说明已经没有办法继续摆砖块了
		if (level == N) {
			//查看pre这一层，只要铺满了，全部都是1，说明有1种摆砖块的方法法
			//如果只要有一个0，说明没有铺满砖块，也就是有0种摆砖块的方法数
			return pre == ((1 << M) - 1) ? 1 : 0;
		}
		//根据pre这一层决定level这一层的操作
		int op = (~pre) & ((1 << M) - 1);
		//获取到op[]之后，从col == M - 1 这一列开始
		return dfsWithBitOperationsAndCache(op, M - 1, level, N, M, dp);
	}

	/**
	 * 该方法表示在当前每一层的每一列进行深度优先搜索算法操作
	 *
	 * @param op    根据上一行决定当前这一行的操作
	 *              举例：假设pre = 0110011
	 *              那么op = 1001100,0表示没有铺上砖块，可以选择向右摆放；1表示已经铺上砖块，只能选择向上摆放
	 * @param col   当前这一列
	 * @param level 当前这一层
	 * @param N     一共N层
	 * @param M     一共M列
	 * @return 返回当前每一层的砖块摆放的方法数
	 */
	private static int dfsWithBitOperationsAndCache(int op, int col, int level, int N, int M, int[] dp) {
		if (dp[level] != -1) {
			return dp[level];
		}
		//col == -1表示当前这一行已经做好决定了，继续去下一行根据op做决定
		if (col == -1) {
			return coreLogicWithBitOperations(op, level + 1, N, M);
		}
		//当前这一行砖块摆放的方法数
		int ans = 0;
		//col这一列不摆放砖块，继续col - 1列
		ans += dfsWithBitOperationsAndCache(op, col - 1, level, N, M, dp);
		//col这一列摆放砖块，说明要向右横着摆放，因为1 * 2的砖块，所以占两列
		//因此要判断是否有col - 1列，如果有并且col列为0，col - 1列也为0，表示col和col - 1这两列可以摆放一块砖
		if (col - 1 >= 0 && (op & (1 << col)) == 0 && (op & (1 << (col - 1))) == 0) {
			ans += dfsWithBitOperationsAndCache(op | (3 << (col - 1)), col - 2, level, N, M, dp);
		}
		dp[level] = ans;
		return dp[level];
	}
}
