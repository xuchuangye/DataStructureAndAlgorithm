package com.mashibing.guessingmethod_externalinformationsimplification;

/**
 * 题目二：
 * 给出一些不同颜色的盒子boxes，盒子的颜色由不同的正数表示。
 * 你将经过若干轮操作去去掉盒子，直到所有的盒子都去掉为止。
 * 每一轮你可以移除具有相同颜色的连续 k 个盒子（k>= 1），这样一轮之后你将得到 k * k 个积分。
 * 返回 你能获得的最大积分和。
 * <p>
 * 示例1：
 * 输入：boxes = [1,3,2,2,2,3,4,3,1]
 * 输出：23
 * 解释：
 * [1, 3, 2, 2, 2, 3, 4, 3, 1]
 * ----> [1, 3, 3, 4, 3, 1] (3个*3个=9 分)
 * ----> [1, 3, 3, 3, 1] (1个*1个=1 分)
 * ----> [1, 1] (3个*3个=9 分)
 * ----> [] (2个*2个=4 分)
 * <p>
 * 示例2：
 * 输入：boxes = [1,1,1]
 * ----> [1, 1, 1] (3个*3个 = 9 分)
 * 输出：9
 * <p>
 * 示例3：
 * 输入：boxes = [1]
 * ----> [1] (1个*1个 = 1 分)
 * 输出：1
 * <p>
 * 提示：
 * 1 <= boxes.length <= 100
 * 1 <= boxes[i] <= 100
 * <p>
 * 思路分析：
 * 1.创建f(L, R, K)表示在arr[] L ~ R 范围上进行消除，前面有K个和L位置上的数一样的数紧跟着arr[L ~ R]
 * 消除掉L ~ R范围上以及前面K个数之后，最大得分是多少
 * 假设：
 * arr = { 1,  1,  1,  3,  2,  1,  1,  1,  6,  4,  1}
 * index = 21  22  23  24  25  26  27  28  29  30  31
 * k = 5
 * 如果5个1跟着22位置的1一起消除掉，那么左边调用f(21,21,0) + 右边调用f(22,31,5)
 * 如果5个1跟着23位置的1一起消除掉，那么左边调用f(21,22,0) + 右边调用f(23,31,5)
 * 依次类推
 * 这5个1可以跟着24,25,26...31位置的1一起消除掉
 * LeetCode测试链接：https://leetcode.cn/problems/remove-boxes/
 *
 * @author xcy
 * @date 2022/6/17 - 10:49
 */
public class Code02_RemoveBoxes {
	public static void main(String[] args) {
		int[] arr = { 1,  1,  1,  3,  2,  1,  1,  1,  6,  4,  1};
		int count1 = removeBoxes(arr);
		int count2 = removeBoxesWithCache(arr);
		int count3 = removeBoxesWithCacheOptimization(arr);
		if (count1 != count2 || count1 != count3) {
			System.out.println("测试失败！");
		}
	}

	/**
	 * 使用暴力递归的方式
	 *
	 * @param boxes
	 * @return
	 */
	public static int removeBoxes(int[] boxes) {
		if (boxes == null || boxes.length == 0) {
			return 0;
		}
		return coreLogic(boxes, 0, boxes.length - 1, 0);
	}

	/**
	 * 核心逻辑：表示arr[]中L到R范围上，前面存在着K个连续的arr[L]的数值
	 *
	 * @param arr
	 * @param L
	 * @param R
	 * @param K
	 * @return
	 */
	public static int coreLogic(int[] arr, int L, int R, int K) {
		if (L > R) {
			return 0;
		}
		//情况：先消除L和前面K个与L位置相同的数
		//(K + 1) * (K + 1)表示K个连续的数和arr[L]一起消除掉，所以是K + (L自己，也就是1)
		//coreLogic(arr, L + 1, R, 0)表示从arr[]中L + 1开始到R范围上继续往下递归，前面因为已经消除过了，所以K == 0
		int ans = coreLogic(arr, L + 1, R, 0) + (K + 1) * (K + 1);
		for (int i = L + 1; i <= R; i++) {
			if (arr[i] == arr[L]) {
				//举例：
				//arr[]的范围：L .   .   i   . R
				//前面有K个1，L位置上是1，如果i位置上也是1，那么L + 1到i - 1范围上肯定有0个1
				//在i到R范围上，前面有至少K + 1(L位置上的1)个1
				ans = Math.max(ans, coreLogic(arr, L + 1, i - 1, 0) + coreLogic(arr, i, R, K + 1));
			}
		}
		return ans;
	}

	/**
	 * 使用暴力递归 + 傻缓存的方式
	 *
	 * @param boxes
	 * @return
	 */
	public static int removeBoxesWithCache(int[] boxes) {
		if (boxes == null || boxes.length == 0) {
			return 0;
		}
		int N = boxes.length;
		int[][][] dp = new int[N][N][N];
		return coreLogicWithCache(boxes, 0, N - 1, 0, dp);
	}

	/**
	 * 核心逻辑：表示arr[]中L到R范围上，前面存在着K个连续的arr[L]的数值
	 *
	 * @param arr
	 * @param L
	 * @param R
	 * @param K
	 * @return
	 */
	public static int coreLogicWithCache(int[] arr, int L, int R, int K, int[][][] dp) {
		if (L > R) {
			return 0;
		}
		if (dp[L][R][K] > 0) {
			return dp[L][R][K];
		}
		//情况：先消除L和前面K个与L位置相同的数
		//(K + 1) * (K + 1)表示K个连续的数和arr[L]一起消除掉，所以是K + (L自己，也就是1)
		//coreLogic(arr, L + 1, R, 0)表示从arr[]中L + 1开始到R范围上继续往下递归，前面因为已经消除过了，所以K == 0
		int ans = coreLogicWithCache(arr, L + 1, R, 0, dp) + (K + 1) * (K + 1);
		for (int i = L + 1; i <= R; i++) {
			if (arr[i] == arr[L]) {
				//举例：
				//arr[]的范围：L .   .   i   . R
				//前面有K个1，L位置上是1，如果i位置上也是1，那么L + 1到i - 1范围上肯定有0个1
				//在i到R范围上，前面有至少K + 1(L位置上的1)个1
				ans = Math.max(ans, coreLogicWithCache(arr, L + 1, i - 1, 0, dp)
						+
						coreLogicWithCache(arr, i, R, K + 1, dp));
			}
		}
		dp[L][R][K] = ans;
		return dp[L][R][K];
	}

	/**
	 * 使用暴力递归 + 傻缓存(优化的版本)的方式
	 *
	 * @param boxes
	 * @return
	 */
	public static int removeBoxesWithCacheOptimization(int[] boxes) {
		if (boxes == null || boxes.length == 0) {
			return 0;
		}
		int N = boxes.length;
		int[][][] dp = new int[N][N][N];
		return coreLogicWithCacheOptimization(boxes, 0, N - 1, 0, dp);
	}

	/**
	 * 核心逻辑：表示arr[]中L到R范围上，前面存在着K个连续的arr[L]的数值
	 * 优化之后减少大量调用递归的次数，时间复杂度的常数时间将大大减少
	 * 优化一：
	 * (1)优化之前：
	 * 前面有5个1
	 * arr[] = {1,1,1,1,2,2,3,1,1,1,4}
	 * index =  L                   R
	 * (2)直接将L位置出现的连续的1只保留1个，优化之后：
	 * 前面有8个1
	 * arr[] = {1,2,2,3,1,1,1,4}
	 * index =  L             R
	 * 优化二：
	 * 优化之前：
	 * 需要对连续相同的数值一个一个的试验，例如：
	 * arr[] = {1,2,2,3,1,1,1,4}
	 * index =  L       x y z R
	 * f()需要对x,y,z连续相同数值的位置依次一个一个的进行试验
	 * 优化之后：
	 * 只需要对连续相同的数值的第一个进行试验，例如：
	 * arr[] = {1,2,2,3,1,1,1,4}
	 * index =  L       x y z R
	 * f()只需要对x,y,z连续相同数值的第一个位置x进行试验即可
	 *
	 * @param arr
	 * @param L
	 * @param R
	 * @param K
	 * @return
	 */
	public static int coreLogicWithCacheOptimization(int[] arr, int L, int R, int K, int[][][] dp) {
		if (L > R) {
			return 0;
		}
		if (dp[L][R][K] > 0) {
			return dp[L][R][K];
		}
		//last表示从L位置开始，一直往右找，直到找到连续相同数值的最后一位
		int last = L;
		while (last + 1 <= R && arr[last + 1] == arr[L]) {
			last++;
		}

		//确定last之前有多少个K
		int pre = K + last - L;
		//先消除last和前面pre个与L位置数值相同的数
		//(pre + 1) * (pre + 1)表示pre个连续的数和arr[L]以及到last之前一起消除掉
		//coreLogic(arr, last + 1, R, 0)表示从arr[]中last + 1开始到R范围上继续往下递归，前面因为已经消除过了，所以K == 0
		int ans = coreLogicWithCacheOptimization(arr, last + 1, R, 0, dp) + (pre + 1) * (pre + 1);
		for (int i = last + 2; i <= R; i++) {
			//确定连续相同数值的第一个位置
			//arr[i] == arr[L]表示i位置的数值和L位置的数值相等
			//arr[i - 1] != arr[L]表示i - 1位置的数值和L位置的数值不相等
			//就可以确定i位置就是连续相同数值的第一个位置
			if (arr[i] == arr[L] && arr[i - 1] != arr[L]) {
				//举例：
				//            1         1          1      1
				//arr[]的范围: L ...   last   ...   i    i + 1   ... R
				//前面有pre个1，last位置上是1，如果i位置上也是1，那么last + 1到i - 1范围上肯定有0个1
				//在i到R范围上，前面有至少pre + 1(last位置上的1)个1
				ans = Math.max(ans, coreLogicWithCacheOptimization(arr, last + 1, i - 1, 0, dp)
						+
						coreLogicWithCacheOptimization(arr, i, R, pre + 1, dp));
			}
		}
		dp[L][R][K] = ans;
		return dp[L][R][K];
	}
}
