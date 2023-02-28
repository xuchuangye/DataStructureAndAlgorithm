package com.mashibing.day01;

import java.util.HashMap;

/**
 * 题目七：
 * 给定一个数组arr，你可以在每个数字之前决定+或者-
 * 但是必须所有数字都参与，再给定一个数target
 * 请问最后算出target的方法数
 * <p>
 * 解题思路：
 * 假设：
 * arr[] = {1, 2, 3, 4, 5}   target = 3
 * index =  0  1  2  3  4
 * arr[0]前面+，arr[1]前面-，arr[2]前面+，arr[3]前面-，arr[4]前面+，得出结论
 * +1, -2, +3, -4, +5  得到结果：3  == target
 * 1.暴力递归
 * 遍历当前索引index，两种情况：
 * (1).所有的元素都递归完了，那么查看是否已经组成target目标值，如果组成了，方法数为1，如果没有组成，方法数为0
 * (2).所有的元素都没有递归完，两种方案：
 * a.当前元素前面加上+，那么新的需要组成的目标值就是target - arr[index]
 * b.当前元素前面加上-，那么新的需要组成的目标值就是target + arr[index]
 * 两种方案的结果相加就是总的方法数
 * 2.傻缓存
 * 有结果，直接返回结果
 * 没有结果，查看是否建立缓存，没有建立缓存，那么就建立缓存，有缓存，将结果添加到缓存中，方便下次查看
 * 3.动态规划
 * <p>
 * 优化点：
 * 1.如果arr[]中含有负数，将arr[]改为非负数组array，最终的方法数不会变
 * 2.非负数组array[]，计算所有元素的累加和sum，如果target > sum，那么不可能有答案
 * 3.如果所有的累加和sum和target的奇偶性不一样，那么一定有0种方法数
 * 4.所有奇数的累加和P，所有偶数的累加和N
 * P - N = target
 * P - N + P + N = target + P + N
 * 2 * P = target + P + N
 * 2 * P = target + sum
 * P = (target + sum) / 2
 * 表示如果arr[]中，所有的元素不需要都使用，只要能组成累加和P并且知道组成累加和P的方法数，就能知道组成target的方法数有多少种
 * 原来的数据规模，即使arr[]所有的元素都是正数，仍然能够在元素前面加上+或者-，那么数据规模为：-sum到+sum
 * 现在的数据规模，只需要求出(target + sum) / 2即可，根据优化点2，target一定 <= sum，那么再最差的情况下，数据规模为：0 ~ +sum
 * 5.二维动态规划的空间压缩技巧
 *
 * LeetCode测试链接：
 * https://leetcode.cn/problems/target-sum/
 *
 * @author xcy
 * @date 2022/7/6 - 9:16
 */
public class Code07_TargetSum {
	public static void main(String[] args) {
		int[] arr = {1, 2, 3, 4, 5};
		int target = 3;
		int ways1 = findTargetSumWays1(arr, target);
		int ways2 = findTargetSumWays2(arr, target);
		int ways3 = findTargetSumWays3(arr, target);
		System.out.println(ways1);
		System.out.println(ways2);
		System.out.println(ways3);
	}

	/**
	 * 使用暴力递归的方式
	 *
	 * @param arr
	 * @param target
	 * @return
	 */
	public static int findTargetSumWays1(int[] arr, int target) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		//从arr数组的index == 0位置开始，组成目标值target
		return coreLogicWithRecursion(arr, 0, target);
	}

	/**
	 * @param arr   数组，必须使用所有的元素
	 * @param index 索引
	 * @param rest  目标值
	 * @return 返回在arr数组中的每个元素前面加上+或者-，组成rest目标这个数的方法数有多少种？
	 */
	public static int coreLogicWithRecursion(int[] arr, int index, int rest) {
		//表示已经递归完整个arr数组中的元素
		if (index == arr.length) {
			//查看目标是否还有剩余，如果目标为0，则是一种方法数，否则，则是0种方法数
			return rest == 0 ? 1 : 0;
		}
		//方案1：将当前arr元素前面加上-，那么就需要搞定rest + arr[index]之后的结果
		int situation1 = coreLogicWithRecursion(arr, index + 1, rest + arr[index]);
		//方案2，将当前arr元素前面加上+，那么就需要搞定rest - arr[index]之后的结果
		int situation2 = coreLogicWithRecursion(arr, index + 1, rest - arr[index]);
		return situation1 + situation2;
	}

	/**
	 * 使用傻缓存的方式
	 *
	 * @param arr
	 * @param target
	 * @return
	 */
	public static int findTargetSumWays2(int[] arr, int target) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		//从arr数组的index == 0位置开始，组成目标值target
		return coreLogicWithCache(arr, 0, target, new HashMap<>());
	}

	/**
	 * HashMap<Integer, HashMap<Integer, Integer>> cache
	 * index == 7, rest == 13  方法数256种
	 * index == 7, rest == 35  方法数17种
	 * cache = {
	 * -   7: {
	 * -      {13, 256},
	 * -      {35, 17}
	 * -   }
	 * }
	 *
	 * @param arr   数组，必须使用所有的元素
	 * @param index 索引
	 * @param rest  目标值
	 * @return 返回在arr数组中的每个元素前面加上+或者-，组成rest目标这个数的方法数有多少种？
	 */
	public static int coreLogicWithCache(int[] arr, int index, int rest, HashMap<Integer, HashMap<Integer, Integer>> cache) {
		//命中缓存
		if (cache.containsKey(index) && cache.get(index).containsKey(rest)) {
			return cache.get(index).get(rest);
		}
		//没有命中缓存
		int ans = 0;
		//表示已经递归完整个arr数组中的元素
		if (index == arr.length) {
			//查看目标是否还有剩余，如果目标为0，则是一种方法数，否则，则是0种方法数
			ans = rest == 0 ? 1 : 0;
		} else {
			//方案1：将当前arr元素前面加上-，那么就需要搞定rest + arr[index]之后的结果
			int situation1 = coreLogicWithCache(arr, index + 1, rest + arr[index], cache);
			//方案2，将当前arr元素前面加上+，那么就需要搞定rest - arr[index]之后的结果
			int situation2 = coreLogicWithCache(arr, index + 1, rest - arr[index], cache);
			ans = situation1 + situation2;
		}
		//如果没有缓存，则建立缓存
		if (!cache.containsKey(index)) {
			cache.put(index, new HashMap<>());
		}
		//如果有缓存，则往缓存中添加
		cache.get(index).put(rest, ans);
		return ans;
	}


	/**
	 * 使用动态规划的方式
	 * <p>
	 * 优化点：
	 * 1.如果arr[]中含有负数，将arr[]改为非负数组array，最终的方法数不会变
	 * 2.非负数组array[]，计算所有元素的累加和sum，如果target > sum，那么不可能有答案
	 * 3.如果所有的累加和sum和target的奇偶性不一样，那么一定有0种方法数
	 * 4.所有奇数的累加和P，所有偶数的累加和N
	 * P - N = target
	 * P - N + P + N = target + P + N
	 * 2 * P = target + P + N
	 * 2 * P = target + sum
	 * P = (target + sum) / 2
	 * 表示如果arr[]中，所有的元素不需要都使用，只要能组成累加和P并且知道组成累加和P的方法数，就能知道组成target的方法数有多少种
	 * 原来的数据规模，即使arr[]所有的元素都是正数，仍然能够在元素前面加上+或者-，那么数据规模为：
	 * -sum到+sum
	 * 现在的数据规模，只需要求出(target + sum) / 2即可，根据优化点2，target一定 <= sum，那么再最差的情况下，数据规模为：
	 * (target + sum) / 2   -->   0 ~ +sum
	 * 5.填写整张二维表的时候，利用空间压缩技巧，二维动态规划的空间压缩技巧
	 * <p>
	 * 启示：
	 * 1.不卡常数时间，能保证过
	 * 2.但是如果有了优化点，能够成为高手
	 *
	 * @param arr    数组，必须使用所有的元素
	 * @param target 目标值
	 * @return 返回在arr数组中的每个元素前面加上+或者-，组成rest目标这个数的方法数有多少种？
	 */
	public static int findTargetSumWays3(int[] arr, int target) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		int sum = 0;
		for (int value : arr) {
			sum += value;
		}
		if (target > sum || ((target & 1) ^ (sum & 1)) != 0) {
			return 0;
		}
		//P = (target + sum) / 2
		//在arr[]中，有多少个数自由选择之后正好能够组合出P的集合有多少个，就代表target有多少种方法
		return coreLogicWithDynamicProgrammingNoSpaceCompression(arr, (target + sum) >> 1);
	}

	/**
	 * 求非负数组arr有多少个子集，累加和是target
	 * 二维动态规划
	 * 不用空间压缩
	 * 经典背包动态规划
	 * <p>
	 * 优化点一 :
	 * 可以认为arr中都是非负数
	 * 因为即便是arr中有负数，比如[3,-4,2]
	 * 因为你能在每个数前面用+或者-号
	 * 所以[3,-4,2]其实和[3,4,2]达成一样的效果
	 * 那么我们就全把arr变成非负数，不会影响结果的
	 * 优化点二 :
	 * 如果arr都是非负数，并且所有数的累加和是sum
	 * 那么如果target > sum，很明显没有任何方法可以达到target，可以直接返回0
	 * 优化点三 :
	 * arr内部的数组，不管怎么+和-，最终的结果都一定不会改变奇偶性
	 * 所以，如果所有数的累加和是sum，
	 * 并且与target的奇偶性不一样，没有任何方法可以达到target，可以直接返回0
	 * 优化点四 :
	 * 比如说给定一个数组, arr = [1, 2, 3, 4, 5] 并且 target = 3
	 * 其中一个方案是 : +1 -2 +3 -4 +5 = 3
	 * 该方案中取了正的集合为P = {1，3，5}
	 * 该方案中取了负的集合为N = {2，4}
	 * 所以任何一种方案，都一定有 sum(P) - sum(N) = target
	 * 现在我们来处理一下这个等式，把左右两边都加上sum(P) + sum(N)，那么就会变成如下：
	 * sum(P) - sum(N) + sum(P) + sum(N) = target + sum(P) + sum(N)
	 * 2 * sum(P) = target + 数组所有数的累加和
	 * sum(P) = (target + 数组所有数的累加和sum) / 2
	 * 也就是说，任何一个集合，只要累加和是(target + 数组所有数的累加和) / 2
	 * 那么就一定对应一种target的方式
	 * 也就是说，比如非负数组arr，target = 7, 而所有数累加和是11
	 * 求有多少方法组成7，其实就是求有多少种达到累加和(7+11)/2=9的方法
	 * 优化点五 :
	 * 二维动态规划的空间压缩技巧
	 *
	 * @return
	 */
	public static int coreLogicWithDynamicProgrammingNoSpaceCompression(int[] arr, int target) {
		if (target < 0) {
			return 0;
		}
		int N = arr.length;
		//dp[i][j]表示在arr[]中，0到i位置的范围上能够组成累加和为j的方法数
		//两种情况：
		//情况1：arr[]中i位置的数值不使用，那么arr[]中0 ~ i - 1位置的数值使用，并且组成累加和j，也就是dp[0 ~ i - 1][j]
		//情况2：arr[]中i位置的数值使用，那么arr[]中0 ~ i - 1位置的数值使用，并且组成累加和j - arr[i]，也就是dp[0 ~ i - 1][j - arr[i]]
		//又因为，i是从1开始的，所以需要减1，也就是dp[0 ~ i - 1][j - arr[i - 1]]
		//arr[] = {3, 2, 4, 1, 0}   target = 6
		//index =  0  1  2  3  4
		int[][] dp = new int[N + 1][target + 1];
		dp[0][0] = 1;
		for (int i = 1; i <= N; i++) {
			for (int j = 0; j <= target; j++) {
				dp[i][j] = dp[i - 1][j];
				if (j - arr[i - 1] >= 0) {
					dp[i][j] += dp[i - 1][j - arr[i - 1]];
				}
			}
		}
		return dp[N][target];
	}

	/**
	 * 求非负数组arr有多少个子集，累加和是target
	 * 二维动态规划
	 * 使用空间压缩:
	 * 核心就是for循环里面的：for (int i = target; i >= n; i--) {
	 * 为啥不枚举所有可能的累加和？只枚举 n...target 这些累加和？
	 * 因为如果 i - n < 0，dp[i]怎么更新？和上一步的dp[i]一样！所以不用更新
	 * 如果 i - n >= 0，dp[i]怎么更新？上一步的dp[i] + 上一步dp[i - n]的值，这才需要更新
	 *
	 * @param arr
	 * @param target
	 * @return
	 */
	public static int coreLogicWithDynamicProgrammingSpaceCompression(int[] arr, int target) {
		if (target < 0) {
			return 0;
		}
		int[] dp = new int[target + 1];
		dp[0] = 1;
		for (int n : arr) {
			for (int i = target; i >= n; i--) {
				dp[i] += dp[i - n];
			}
		}
		return dp[target];
	}
}
