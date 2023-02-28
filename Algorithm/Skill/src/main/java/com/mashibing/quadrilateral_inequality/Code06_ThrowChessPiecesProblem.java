package com.mashibing.quadrilateral_inequality;

/**
 * 题目六：
 * 一座大楼有 0~N 层，地面算作第 0 层，最高的一层为第 N 层。已知棋子从第 0 层掉落肯定 不会摔碎，从第 i 层掉落有可能会摔碎，
 * 也有可能不会摔碎(1 ≤ i ≤ N)。
 * 给定整数N作为楼层数，再给定整数K作为棋子数，返回如果想找到棋子不会摔碎的最高层数，即使在最差的情况下扔的最少次数。
 * 一次只能扔一个棋子。
 * 【举例】
 * N=10，K=1。
 * 返回10。因为只有1棵棋子，所以不得不从第1层开始一直试到第10层，在最差的情况下，即第10层是不会摔坏的最高层，最少也要扔10次。
 * N=3，K=2。
 * 返回2。先在2层扔1棵棋子，如果碎了，试第1层，如果没碎，试第3层。
 * N=105，K=2
 * 返回14。第一个棋子先在14层扔，碎了则用仅存的一个棋子试1~13。若没碎，第一个棋子继续在27层扔，碎了则用仅存的一个棋子试15~26。
 * 若没碎，第一个棋子继续在39层扔，碎了则用仅存的一个棋子试28~38。若没碎，第一个棋子继续在50层扔，碎了则用仅存的一个棋子试40~49。
 * 若没碎，第一个棋子继续在60层扔，碎了则用仅存的一个棋子试51~59。若没碎，第一个棋子继续在69层扔，碎了则用仅存的一个棋子试61~68。
 * 若没碎，第一个棋子继续在77层扔，碎了则用仅存的一个棋子试70~76。若没碎，第一个棋子继续在84层扔，碎了则用仅存的一个棋子试78~83。
 * 若没碎，第一个棋子继续在90层扔，碎了则用仅存的一个棋子试85~89。若没碎，第一个棋子继续在95层扔，碎了则用仅存的一个棋子试91~94。
 * 若没碎，第一个棋子继续在99层扔，碎了则用仅存的一个棋子试96~98。若没碎，第一个棋子继续在102层扔，碎了则用仅存的一个棋子试100、101。
 * 若没碎，第一个棋子继续在104层扔，碎了则用仅存的一个棋子试103。若没碎，第一个棋子继续在105层扔，若到这一步还没碎，那么105便是结果。
 * <p>
 * LeetCode测试链接：https://leetcode.com/problems/super-egg-drop
 *
 * @author xcy
 * @date 2022/6/13 - 11:10
 */
public class Code06_ThrowChessPiecesProblem {
	public static void main(String[] args) {
		int maxN = 500;
		int maxK = 30;
		int testTime = 1000;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int N = (int) (Math.random() * maxN) + 1;
			int K = (int) (Math.random() * maxK) + 1;
			int ans1 = returnMinimumNumberOfTimesWithRecursion(K, N);
			int ans2 = returnMinimumNumberOfTimesWithNoEnumerationOptimization(K, N);
			int ans3 = returnMinimumNumberOfTimesWithEnumerationOptimization(K, N);
			int ans4 = returnMinimumNumberOfTimesWithOptimalSolution(K, N);
			int ans5 = superEggDrop5(K, N);
			if (ans1 != ans2 || ans1 != ans3 || ans1 != ans4 || ans1 != ans5) {
				System.out.println("出错了!");
			}
		}
		System.out.println("测试结束");
	}

	public static int returnMinimumNumberOfTimesWithRecursion(int kChess, int nLevel) {
		if (nLevel < 1 || kChess < 1) {
			return 0;
		}
		return coreLogic(nLevel, kChess);
	}

	/**
	 * 一定要验证出最高的不会碎的楼层！但是每次都是坏运气，也就是最差情况，所以是最大的试验次数。
	 *
	 * @param rest rest还剩多少层楼需要去验证
	 * @param k    k还有多少颗棋子能够使用
	 * @return 返回至少需要扔的次数
	 */
	public static int coreLogic(int rest, int k) {
		if (rest == 0) {
			return 0;
		}
		if (k == 1) {
			return rest;
		}
		int min = Integer.MAX_VALUE;
		for (int i = 1; i != rest + 1; i++) { // 第一次扔的时候，仍在了i层
			min = Math.min(min, Math.max(coreLogic(i - 1, k - 1), coreLogic(rest - i, k)));
		}
		return min + 1;
	}

	/**
	 * 不使用枚举优化的动态规划
	 *
	 * @param kChess 棋子的个数
	 * @param nLevel 楼层的层数
	 * @return 返回棋子在不摔碎的最高层数下，试验摔下楼层的最少次数
	 */
	public static int returnMinimumNumberOfTimesWithNoEnumerationOptimization(int kChess, int nLevel) {
		if (kChess < 1 || nLevel < 1) {
			return 0;
		}
		//如果棋子只有一个，只能一层层试，所以返回nLevel
		if (kChess == 1) {
			return nLevel;
		}

		int[][] dp = new int[nLevel + 1][kChess + 1];
		for (int i = 1; i != dp.length; i++) {
			//i层楼只有一个棋子，一定要扔i次
			dp[i][1] = i;
		}
		//i既表示层数也表示行数，第0层没有意义，所以从第1层开始
		for (int i = 1; i != dp.length; i++) {
			//j既表示棋子个数也表示列数，第0列没有意义，第1列已经填过了，所以从第2列开始
			for (int j = 2; j != dp[0].length; j++) {
				int min = Integer.MAX_VALUE;
				for (int k = 1; k != i + 1; k++) {
					//在两种方案中，求dp[k - 1][j - 1] 和 dp[i - k][j]最差的情况，也就是max
					//dp[k - 1][j - 1]表示棋子摔碎了，层数还剩k - 1，棋子还剩j - 1
					//dp[i - k][j]表示棋子没有碎，已经试过k层了，层数还剩i - k层，棋子还剩j个
					//最差情况下，也就是试验次数最多的 最大的，所以Math.max()
					min = Math.min(min, Math.max(dp[k - 1][j - 1], dp[i - k][j]));
				}
				//棋子试过的次数一定要 + 1
				dp[i][j] = min + 1;
			}
		}
		return dp[nLevel][kChess];
	}

	/**
	 * 使用枚举的动态规划
	 *
	 * @param kChess 棋子的个数
	 * @param nLevel 楼层的层数
	 * @return 返回棋子在不摔碎的最高层数下，试验摔下楼层的最少次数
	 */
	public static int returnMinimumNumberOfTimesWithEnumerationOptimization(int kChess, int nLevel) {
		if (kChess < 1 || nLevel < 1) {
			return 0;
		}
		if (kChess == 1) {
			return nLevel;
		}
		int[][] dp = new int[nLevel + 1][kChess + 1];
		//best[i][j]存放dp[i][j]i个楼层数，j个棋子的最优解
		int[][] best = new int[nLevel + 1][kChess + 1];

		//i表示楼层数以及行数，层数为0时没有意义，所以从1开始
		for (int i = 1; i < dp.length; i++) {
			//不管楼层数有多少层，只有一个棋子，那么必须要试验i次
			dp[i][1] = i;
		}
		//j表示棋子数以及列数，列数为0时没有意义，所以从1开始
		for (int j = 1; j < dp[0].length; j++) {
			//不管棋子数有多个个，只有一层楼层，那么只需要试验1次
			dp[1][j] = 1;
			//最优解为1次
			best[1][j] = 1;
		}
		//i即表示层数也表示行数，i从2开始
		for (int i = 2; i < nLevel + 1; i++) {
			//j即表示棋子数也表示列数，j从kChess开始
			for (int j = kChess; j >= 2; j--) {
				//下限，上边的格子
				int down = best[i - 1][j];
				//上限，如果j来到最后一列，那么代价就是i，如果不是最后一列，那么上限就是右边的格子
				int up = j == kChess ? i : best[i][j + 1];
				int min = Integer.MAX_VALUE;
				int choose = -1;
				for (int k = down; k <= up; k++) {
					int cur = Math.max(dp[k - 1][j - 1], dp[i - k][j]);
					if (cur <= min) {
						min = cur;
						choose = k;
					}
				}
				dp[i][j] = min + 1;
				best[i][j] = choose;
			}
		}
		return dp[nLevel][kChess];
	}

	/**
	 * 最优解
	 *
	 * @param kChess 棋子的个数
	 * @param nLevel 楼层的层数
	 * @return
	 */
	public static int returnMinimumNumberOfTimesWithOptimalSolution(int kChess, int nLevel) {
		if (kChess < 1 || nLevel < 1) {
			return 0;
		}

		if (kChess == 1) {
			return nLevel;
		}
		//dp[i][j]表示在最差的情况下，有i个棋子，试验j次之后，能够试验出的最高楼层层数，j不知道，而是以i个棋子为一个数组，从左往右移动过去
		int[] dp = new int[kChess];
		//    0  1  2  3  4  -> 试验的次数
		//0   X  X  X  X  X
		//1   X  1  2  3  4
		//2   X  1 左上
		//3   X  1 左  *
		//4   X  1
		//棋子数
		//不管有多少个棋子，试验次数是1，也就是只能扔1次，那么试验出的最高楼层层数是1
		//不管扔多少次棋子，棋子只有1个，试验次数是1，能够试验出的最高楼层层数是1，试验次数是2，能够试验出的最高楼层层数是2，试验次数是3，能够试验出的最高楼层层数是3
		//*标记的位置的值依赖左上和左边的位置的值

		//当前到达哪一列，从第0列开始
		int col = 0;
		while (true) {
			col++;
			int previous = 0;
			for (int i = 0; i < dp.length; i++) {
				int temp = dp[i];
				dp[i] = dp[i] + previous + 1;
				previous = temp;
				if (dp[i] >= nLevel) {
					return col;
				}
			}
		}
	}

	public static int superEggDrop5(int kChess, int nLevel) {
		if (nLevel < 1 || kChess < 1) {
			return 0;
		}
		int bsTimes = log2N(nLevel) + 1;
		if (kChess >= bsTimes) {
			return bsTimes;
		}
		int[] dp = new int[kChess];
		int res = 0;
		while (true) {
			res++;
			int previous = 0;
			for (int i = 0; i < dp.length; i++) {
				int tmp = dp[i];
				dp[i] = dp[i] + previous + 1;
				previous = tmp;
				if (dp[i] >= nLevel) {
					return res;
				}
			}
		}
	}

	public static int log2N(int n) {
		int res = -1;
		while (n != 0) {
			res++;
			n >>>= 1;
		}
		return res;
	}
}
