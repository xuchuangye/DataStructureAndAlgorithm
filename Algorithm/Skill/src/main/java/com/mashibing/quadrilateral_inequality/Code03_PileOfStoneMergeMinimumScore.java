package com.mashibing.quadrilateral_inequality;

import com.mashibing.common.SkillUtils;

/**
 * 题目三：
 * 摆放着n堆石子。现要将石子有次序地合并成一堆
 * 规定每次只能选相邻的2堆石子合并成新的一堆，
 * 并将新的一堆石子数记为该次合并的得分
 * 求出将n堆石子合并成一堆的最小得分（或最大得分）合并方案
 *
 * @author xcy
 * @date 2022/6/12 - 10:30
 */
public class Code03_PileOfStoneMergeMinimumScore {
	public static void main(String[] args) {
		int N = 15;
		int maxValue = 100;
		int testTime = 1000;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int len = (int) (Math.random() * N);
			int[] arr = SkillUtils.randomArray(len, maxValue);
			int ans1 = pileOfStoneMergeMinimumScoreWithRecursion(arr);
			int ans2 = pileOfStoneMergeMinimumScoreWithDynamicProgramming(arr);
			int ans3 = pileOfStoneMergeMinimumScoreWithDynamicProgrammingOptimalSolution(arr);
			if (ans1 != ans2 || ans1 != ans3) {
				System.out.println("Oops!");
			}
		}
		System.out.println("测试结束");
	}

	/**
	 * 暴力递归
	 *
	 * @param arr
	 * @return
	 */
	public static int pileOfStoneMergeMinimumScoreWithRecursion(int[] arr) {
		if (arr == null || arr.length < 2) {
			return 0;
		}
		int[] sum = sum(arr);
		return coreLogic(0, arr.length - 1, sum);
	}

	public static int coreLogic(int L, int R, int[] sum) {
		//对角线，L == R时，L - R范围的合并代价是0，所以返回0
		if (L == R) {
			return 0;
		}
		int next = Integer.MAX_VALUE;
		//以leftEnd作为分界线，L - leftEnd范围以及leftEnd + 1 - R范围的最小累加和，也就是最小的合并代价
		for (int leftEnd = L; leftEnd < R; leftEnd++) {
			//取出L - leftEnd范围以及leftEnd + 1 - R范围的累加和的最小值，也就是最小的合并代价
			next = Math.min(next, coreLogic(L, leftEnd, sum) + coreLogic(leftEnd + 1, R, sum));
		}
		return next + sum(sum, L, R);
	}

	/**
	 * 动态规划
	 *
	 * @param arr
	 * @return
	 */
	public static int pileOfStoneMergeMinimumScoreWithDynamicProgramming(int[] arr) {
		//数组为空或者数组的长度小于2，合并的代价是0
		if (arr == null || arr.length < 2) {
			return 0;
		}
		//创建累加和数组
		int[] sum = sum(arr);
		int[][] dp = new int[arr.length][arr.length];
		//所有的对角线都是0，即dp[i][i] == 0
		//- 0  1  2  3  R
		//0 0
		//1 X  0
		//2 X  X  0
		//3 X  X  X  0
		//L
		//因为L = arr.length - 1这一行已经填过了，所以从L = arr.length - 2这一行开始
		for (int L = arr.length - 2; L >= 0; L--) {
			//R = L表示对角线，而对角线都是0，所以填过了，从R = L + 1这一列开始
			for (int R = L + 1; R < arr.length; R++) {
				int next = Integer.MAX_VALUE;
				for (int leftEnd = L; leftEnd < R; leftEnd++) {
					next = Math.min(next, dp[L][leftEnd] + dp[leftEnd + 1][R]);
				}
				//arr[]中L ~ R范围上合并的最小代价记录到dp[L][R]中
				dp[L][R] = next + sum(sum, L, R);
			}
		}
		return dp[0][arr.length - 1];
	}

	/**
	 * 动态规划的最优解
	 * <p>
	 * 四边形不等式技巧具有以下特征：
	 * 1，两个可变参数的区间划分问题 L - R
	 * 2，每个格子有枚举行为
	 * 3，当两个可变参数固定一个，另一个参数和答案之间存在单调性关系
	 * 3 - 17的范围，如果17位置固定，3位置往上，最优解也就是合并的最小代价降低，3位置往下，最优解也就是合并的最小代价升高
	 * 3位置固定，17位置往上，最优解也就是合并的最小代价升高，17位置往下，最优解也就是合并的最小代价降低
	 * 4，而且两组单调关系是反向的：(升 升，降 降)  (升 降，降 升)
	 * 5，能否获得指导枚举优化的位置对：上+右，或者，左+下
	 * <p>
	 * 1，不要证明！用对数器验证！
	 * 2，枚举的时候面对最优答案相等的时候怎么处理？用对数器都试试！
	 * 3，可以把时间复杂度降低一阶
	 * O(N^3) -> O(N^2)
	 * O(N^2 * M) -> O(N * M)
	 * O(N * M^2) -> O(N * M)
	 * 4，四边形不等式有些时候是最优解，有些时候不是
	 * 不是的原因：尝试思路，在根儿上不够好
	 *
	 * @param arr
	 * @return
	 */
	public static int pileOfStoneMergeMinimumScoreWithDynamicProgrammingOptimalSolution(int[] arr) {
		if (arr == null || arr.length < 2) {
			return 0;
		}
		//创建累加和数组
		int[] sum = sum(arr);
		//动态规划的数组
		//dp[L][R]表示arr[]中L - R范围内合并的最小代价
		int[][] dp = new int[arr.length][arr.length];
		//best[L][R]表示dp[]中L - R范围内合并的最小代价的分界线
		//best[L][R]中best[i][i]这条对角线的值没有意义，因为只有i自己，不需要分界线进行划分

		//dp[]填充示意图
		//- 0  1  2  3  4 -> R
		//0 *  √
		//1 X  *  √
		//2 X  X  *  √
		//3 X  X  X  *  √
		//4 X  X  X  X  *
		//L
		//X表示不需要去处理，*表示值没有意义，所以求L = R + 1
		int[][] best = new int[arr.length][arr.length];

		//该for循环就是填充上述dp[]的标记√号的对角线
		for (int i = 0; i < arr.length - 1; i++) {
			//best[i][i + 1]表示arr[]中i ~ i + 1范围的累加和的最小代价的分界线
			//best[2][2 + 1 == 3]，2 -> 2和2 -> 3，分界线就是2
			best[i][i + 1] = i;
			//dp[i][i + 1]表示arr[]中i ~ i + 1的累加和的最小代价
			dp[i][i + 1] = sum(sum, i, i + 1);
		}

		//从上面的dp[]填充示意图中可以看出arr.length - 1不需要填充
		//arr.length - 2都已经填充过了，所以从arr.length - 3开始
		for (int L = arr.length - 3; L >= 0; L--) {
			for (int R = L + 2; R < arr.length; R++) {
				int next = Integer.MAX_VALUE;
				int choose = -1;
				//leftEnd = best[L][R - 1]表示下限，也就是best[L][R]左边的格子
				//leftEnd <= best[L + 1][R]表示上限，也就是best[L][R]下边的格子
				for (int leftEnd = best[L][R - 1]; leftEnd <= best[L + 1][R]; leftEnd++) {
					//cur表示L - R的合并的代价
					int cur = dp[L][leftEnd] + dp[leftEnd + 1][R];
					//如果出现比next更小的，那么next就记录更小的
					if (cur < next) {
						//next记录当前累加和的最小代价
						next = cur;
						//将leftEnd作为当前累加和的最小代价的分界线
						choose = leftEnd;
					}
				}
				//记录分界线
				best[L][R] = choose;
				//记录arr[]中L ~ R范围上合并的最小代价记录到dp[L][R]中
				dp[L][R] = next + sum(sum, L, R);
			}
		}
		return dp[0][arr.length - 1];
	}

	/**
	 * @param arr 原始数组
	 * @return 返回arr[]的累加和数组
	 */
	public static int[] sum(int[] arr) {
		if (arr == null || arr.length == 0) {
			return new int[0];
		}
		int[] sum = new int[arr.length + 1];
		for (int i = 0; i < arr.length; i++) {
			sum[i + 1] = sum[i] + arr[i];
		}
		return sum;
	}

	/**
	 * @param sum 累加和数组
	 * @param L   左边界
	 * @param R   右边界
	 * @return 返回arr[]中L ~ R范围的累加和
	 */
	public static int sum(int[] sum, int L, int R) {
		return sum[R + 1] - sum[L];
	}
}
