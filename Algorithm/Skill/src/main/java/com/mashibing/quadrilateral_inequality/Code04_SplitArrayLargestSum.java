package com.mashibing.quadrilateral_inequality;

import com.mashibing.common.SkillUtils;

/**
 * 题目四：
 * 给定一个整型数组arr，数组中的每个值都为正数，表示完成一幅画作需要的时间，再给定 一个整数K，表示画匠的数量，
 * 每个画匠只能画连在一起的画作。所有的画家并行工作，请返回完成所有的画作需要的最少时间。
 * 【举例】
 * 1)
 * arr=[3,1,4]，K=2。
 * 最好的分配方式为第一个画匠画 3 和 1，所需时间为 4。第二个画匠画 4，所需时间 为 4。
 * 因为并行工作，所以最少时间为 4。如果分配方式为第一个画匠画 3，所需时 间为 3。
 * 第二个画匠画 1 和 4，所需的时间为 5。那么最少时间为 5，显然没有第一种分配方式好。所以返回4。
 * 2)
 * arr=[1,1,1,4,3]，K=3。
 * 最好的分配方式为第一个画匠画前三个 1，所需时间为 3。第二个画匠画 4，所需时间为 4。 第三个画匠画 3，所需时间为3。返回4。
 * <p>
 * leetcode原题
 * 测试链接：https://leetcode.com/problems/split-array-largest-sum/
 *
 * @author xcy
 * @date 2022/6/12 - 15:16
 */
public class Code04_SplitArrayLargestSum {
	public static void main(String[] args) {
		int N = 100;
		int maxValue = 100;
		int testTime = 10000;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int len = (int) (Math.random() * N) + 1;
			int M = (int) (Math.random() * N) + 1;
			int[] arr = SkillUtils.randomArray(len, maxValue);
			int ans1 = splitArrayWithOptimalSolution(arr, M);
			int ans2 = splitArrayWithNoEnumDynamicProgramming(arr, M);
			int ans3 = splitArrayWithEnumDynamicProgramming(arr, M);
			if (ans1 != ans2 || ans1 != ans3) {
				System.out.print("arr : ");
				SkillUtils.printArray(arr);
				System.out.println("M : " + M);
				System.out.println("ans1 : " + ans1);
				System.out.println("ans2 : " + ans2);
				System.out.println("ans3 : " + ans3);
				System.out.println("Oops!");
				break;
			}
		}
		System.out.println("测试结束");
	}

	/**
	 * 最优解
	 * @param arr
	 * @param K
	 * @return
	 */
	public static int splitArrayWithOptimalSolution(int[] arr, int K) {
		long sum = 0;
		for (int num : arr) {
			sum += num;
		}
		//答案一定在l == 0到累加和r == sum中间
		long l = 0;
		long r = sum;

		long ans = 0;
		while (l <= r) {
			//二分查找
			long mid = l + ((r - l) / 2);
			long cur = getNeedParts(arr, mid);
			//举例：
			//arr = {0 ...  100}
			//假设目标5个，答案一定在0 ~ 100之间
			//先进行二分查找，如果0 ~ 50范围的画家是4，说明还有更好的最优解
			//再进行二叉查找，如果0 ~ 25范围的画家是7，说明0 ~ 25范围上没有最优解，来到26 ~ 50的范围上进行二叉查找
			//直到找到最优解
			if (cur <= K) {
				//当前找到的最优解
				ans = mid;
				r = mid - 1;
			} else {
				l = mid + 1;
			}
		}
		//最晚画√号的ans返回
		return (int) ans;
	}

	/**
	 * @param arr arr数组
	 * @param aim 不超过这个目标
	 * @return 返回至少需要多少个画家
	 */
	public static int getNeedParts(int[] arr, long aim) {
		for (int num : arr) {
			if (num > aim) {
				return Integer.MAX_VALUE;
			}
		}
		//组数，也就是画家的个数
		int parts = 1;
		//默认的累加和是arr[0]
		int sum = arr[0];
		for (int i = 1; i < arr.length; i++) {
			//如果i - 1位置的累加和 + arr[i]的值 大于 目标，那么画家的个数++，累加和重新计算，arr[i]作为新的累加和
			if (sum + arr[i] > aim) {
				parts++;
				sum = arr[i];
			}
			//如果i - 1位置的累加和 + i位置的值 小于等于 目标，那么画家的个数不变，累加和加上arr[i]的值
			else {
				sum += arr[i];
			}
		}
		//返回画家的个数
		return parts;
	}

	/**
	 * 不优化枚举的动态规划方法，O(N^2 * K)
	 *
	 * @param arr
	 * @param K
	 * @return 返回完成arr[]中所有的画作需要的最少时间
	 */
	public static int splitArrayWithNoEnumDynamicProgramming(int[] arr, int K) {

		int[] sum = sum(arr);
		int[][] dp = new int[arr.length][K + 1];
		for (int j = 1; j <= K; j++) {
			dp[0][j] = arr[0];
		}
		for (int i = 1; i < arr.length; i++) {
			dp[i][1] = sum(sum, 0, i);
		}
		// 每一行从上往下
		// 每一列从左往右
		// 根本不去凑优化位置对儿！
		for (int i = 1; i < arr.length; i++) {
			for (int j = 2; j <= K; j++) {
				int ans = Integer.MAX_VALUE;
				// 枚举是完全不优化的！
				for (int leftEnd = 0; leftEnd <= i; leftEnd++) {
					int leftCost = leftEnd == -1 ? 0 : dp[leftEnd][j - 1];
					int rightCost = leftEnd == i ? 0 : sum(sum, leftEnd + 1, i);
					int cur = Math.max(leftCost, rightCost);
					if (cur < ans) {
						ans = cur;
					}
				}
				dp[i][j] = ans;
			}
		}
		return dp[arr.length - 1][K];
	}

	/**
	 * 优化枚举的动态规划方法，O(N * K)
	 *
	 * @param arr
	 * @param K
	 * @return 返回完成arr[]中所有的画作需要的最少时间
	 */
	public static int splitArrayWithEnumDynamicProgramming(int[] arr, int K) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		int[] sum = sum(arr);
		int[][] dp = new int[arr.length][K + 1];
		int[][] best = new int[arr.length][K + 1];
		//dp[0][0]表示既没有画作可以画，也没有画家去画画作
		//所以j表示画家，j从1开始，到K截止
		for (int j = 1; j <= K; j++) {
			//不管有多少个画家，都只有一幅画作，花费的时间代价为arr[0]
			dp[0][j] = arr[0];
			//只有最后一个画家j负责0 ~ 0画作，前面j - 1个画家都不作画，所以是-1
			best[0][j] = -1;
		}
		//dp[0][0]表示既没有画作可以画，也没有画家去画画作
		//所以i表示画作的数量，i从1开始
		for (int i = 1; i < arr.length; i++) {
			//dp[i][0]表示即使有再多的画作，但是没有画家可以作画，所以没有意义，从dp[i][1]开始
			dp[i][1] = sum(sum, 0, i);
			//只有最后一个画家j负责0 ~ i画作，前面j - 1个画家都不作画，所以是-1
			best[i][1] = -1;
		}

		//   0  1  2  3  4  5  ...  j
		//0  X  O  O  O  O  O
		//1  X  O / \/ \/ \/ \
		//2  X  O  |  |  |  |
		//3  X  O  |  |  |  |
		//4  X  O  |  |  |  |
		//5  X  O  |  |  |  |
		//.  X  O  |  |  |  |
		//.  X  O  |  |  |  |
		//.  X  O  |  |  |  |
		//i  X  O  |  |  |  |
		//j表示画家的数量，j == 1这一列已经填充好了，所以从2开始
		//从第2列开始，从左往右
		//每一列，从下往上
		//为什么这样的顺序？因为要去凑（左，下）优化位置对儿
		for (int j = 2; j <= K; j++) {
			//i从arr.length - 1开始，因为这些格子都同时依赖左边的格子和下边的格子
			//i >= 1表示1 == 1的这一行已经填充好了
			for (int i = arr.length - 1; i >= 1; i--) {
				//down表示格子需要遍历范围的下限
				//因为best[i][j]依赖best[i][j - 1]的格子
				int down = best[i][j - 1];
				//up表示格子需要遍历范围的上限
				//因为只有best[arr.length - 1][j]不依赖下限的格子，其余的best[i][j]都依赖best[i + 1][j]的格子
				int up = i == arr.length - 1 ? arr.length - 1 : best[i + 1][j];
				int next = Integer.MAX_VALUE;
				int choose = -1;
				for (int leftEnd = down; leftEnd <= up; leftEnd++) {
					//如果leftEnd == -1，表示前面j - 1个画家什么都没有负责，左侧的代价就是0
					//否则左侧的代价就是dp[leftEnd][j - 1]表示0 ~ leftEnd 由前面j - 1个画家负责
					int leftCost = leftEnd == -1 ? 0 : dp[leftEnd][j - 1];
					//如果leftEnd == i，表示最后一个画家j什么都没有负责，右侧的代价就是0
					//否则右侧的代价就是leftEnd + 1 ~ i的累加和，表示leftEnd + 1 ~ i - 1 由最后一个画家j负责
					int rightCost = leftEnd == i ? 0 : sum(sum, leftEnd + 1, i);

					int cur = Math.max(leftCost, rightCost);
					if (cur < next) {
						next = cur;
						choose = leftEnd;
					}
				}
				dp[i][j] = next;
				best[i][j] = choose;
			}
		}
		return dp[arr.length - 1][K];
	}

	/**
	 * 根据arr数组返回sum累加和数组
	 *
	 * @param arr 原始数组
	 * @return 返回累加和数组
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
	 * 返回arr[]中L - R范围上的累加和
	 *
	 * @param sum 累加和数组
	 * @param L   左边界
	 * @param R   右边界
	 * @return 返回arr[]中L - R范围上的 累加和
	 */
	public static int sum(int[] sum, int L, int R) {
		return sum[R + 1] - sum[L];
	}
}
