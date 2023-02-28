package com.mashibing.state_compression;

/**
 * 题目一：
 * 在 "100 game" 这个游戏中，两名玩家轮流选择从1到10的任意整数，累计整数和，先使得累计整数和达到或超过100的玩家，即为胜者。
 * 如果我们将游戏规则改为 “玩家不能重复使用整数” 呢？
 * 例如，两个玩家可以轮流从公共整数池中抽取从1到15的整数（不放回），直到累计整数和 >= 100。
 * 给定两个整数maxChoosableInteger（整数池中可选择的最大数）和desiredTotal（累计和），
 * 若先出手的玩家是否能稳赢则返回true，否则返回false。假设两位玩家游戏时都表现最佳。
 * <p>
 * 示例1：
 * 输入：maxChoosableInteger = 10, desiredTotal = 11
 * 输出：false
 * 解释：
 * 无论第一个玩家选择哪个整数，他都会失败。
 * 第一个玩家可以选择从 1 到 10 的整数。
 * 如果第一个玩家选择 1，那么第二个玩家只能选择从 2 到 10 的整数。
 * 第二个玩家可以通过选择整数 10（那么累积和为 11 >= desiredTotal），从而取得胜利.
 * 同样地，第一个玩家选择任意其他整数，第二个玩家都会赢。
 * <p>
 * 示例2：
 * 输入：maxChoosableInteger = 10, desiredTotal = 0
 * 输出：true
 * 解释：第一个玩家面临0，直接赢
 * <p>
 * 示例3：
 * 输入：maxChoosableInteger = 10, desiredTotal = 1
 * 输出：true
 * 解释：第一个玩家直接选择1，直接赢
 * <p>
 * 提示：
 * 1 <= maxChoosableInteger <= 20
 * 0 <= desiredTotal <= 300
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/can-i-win
 *
 * @author xcy
 * @date 2022/6/14 - 8:27
 */
public class Code01_CanIWin {
	public static void main(String[] args) {

	}

	/**
	 * 使用暴力递归的方式
	 * 时间复杂度：O(N!)
	 *
	 * @param choose 表示1 ~ choose可以选择的数，而且选择之后不能回退
	 * @param total  总的累计和
	 * @return 返回先手是否能赢
	 */
	public static boolean canIWin(int choose, int total) {
		//如果total等于0，设定先手赢
		if (total == 0) {
			return true;
		}
		//
		if ((choose * (choose + 1) >> 1) < total) {
			return false;
		}
		//根据choose的值创建数组
		int[] arr = new int[choose];
		//arr[]所有的元素范围： {1 ~ choose}
		for (int i = 0; i < choose; i++) {
			arr[i] = i + 1;
		}
		//arr[i] != 1 表示没有选择过，可以选择
		//arr[i] == -1表示已经选择过，不能再次选择
		return coreLogic(arr, total);
	}

	/**
	 * 核心逻辑
	 * 当前轮到先手去选择
	 * 先手只能选择在arr[]中没有被选择过的数值
	 *
	 * @param arr  数组
	 * @param rest 剩余的累计和
	 * @return 返回先手是否能赢
	 */
	public static boolean coreLogic(int[] arr, int rest) {
		//先手一上来，rest就是0，先手就输了
		if (rest <= 0) {
			return false;
		}

		for (int i = 0; i < arr.length; i++) {
			//arr[i] != -1表示可以选择
			if (arr[i] != -1) {
				//选择arr[i]的值
				int cur = arr[i];
				//选择之后，标记不能再次选择
				arr[i] = -1;
				//子过程的后手
				boolean next = coreLogic(arr, rest - cur);

				arr[i] = cur;
				//子过程的后手就是当前的先手，如果子过程的后手赢了，那么当前的先手就赢了
				if (!next) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 使用暴力递归 + 位运算的方式
	 * 时间复杂度：O(N!)
	 *
	 * @param choose 表示1 ~ choose可以选择的数，而且选择之后不能回退
	 * @param total  总的累计和
	 * @return 返回先手是否能赢
	 */
	public static boolean canIWinWithBitOperations(int choose, int total) {
		//如果total等于0，设定先手赢
		if (total == 0) {
			return true;
		}
		//
		if ((choose * (choose + 1) >> 1) < total) {
			return false;
		}
		//status一开始的状态是等于0的
		return coreLogicWithBitOperations(choose, 0, total);
	}

	/**
	 * 核心逻辑
	 *
	 * @param choose 表示先手可以从1 ~ choose中选择任意一个数值
	 * @param status status的位数标记是否可以选择，如果位数上是0，表示可以选择，如果位数上是1，表示不能选择
	 * @param rest   剩余的累计和
	 * @return 返回先手是否能赢
	 */
	public static boolean coreLogicWithBitOperations(int choose, int status, int rest) {
		//先手一上来，rest就是0，先手就输了
		if (rest <= 0) {
			return false;
		}
		//
		for (int i = 1; i <= choose; i++) {
			//表示该位上没有记录对应的数值i，可以选择
			if (((1 << i) & status) == 0) {
				//子过程的后手就是当前的先手，如果子过程的后手赢了，那么就表示当前的先手赢了
				//status | (1 << i)表示将i标记到status的位数上
				//rest - 表示剩余的累计和减去i的值
				if (!coreLogicWithBitOperations(choose, status | (1 << i), rest - i)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 使用暴力递归 + 位运算 + 傻缓存的方式
	 * 时间复杂度：O(2的N次方 * N)
	 *
	 * @param choose 表示1 ~ choose可以选择的数，而且选择之后不能回退
	 * @param total  总的累计和
	 * @return 返回先手是否能赢
	 */
	public static boolean canIWinWithSillyCache(int choose, int total) {
		//如果total等于0，设定先手赢
		if (total == 0) {
			return true;
		}
		//
		if ((choose * (choose + 1) >> 1) < total) {
			return false;
		}
		int[] dp = new int[1 << (choose + 1)];
		//status一开始的状态是等于0的
		//dp[status] == 1表示已经算过了
		//dp[status] == -1表示没有算过
		//dp[status] == 0表示coreLogicWithSillyCache(status)没有算过，去算
		return coreLogicWithSillyCache(choose, 0, total, dp);
	}

	/**
	 * 核心逻辑
	 * 为什么明明status和rest是两个可变参数，却只用status来代表状态(也就是dp)
	 * 因为选了一批数字之后，得到的和一定是一样的，所以rest是由status决定的，所以rest不需要参与记忆化搜索
	 * 举例：
	 * arr[] = {3, 5, 7, 9, 11} rest = 20
	 * 先手选择5，后手选择11或者先手选择11，后手选择5，得到的累计和都是一样的，所以rest是由status决定的
	 *
	 * @param choose 表示先手可以从1 ~ choose中选择任意一个数值
	 * @param status status的位数标记是否可以选择，如果位数上是0，表示可以选择，如果位数上是1，表示不能选择
	 * @param rest   剩余的累计和
	 * @param dp     傻缓存
	 * @return 返回先手是否能赢
	 */
	public static boolean coreLogicWithSillyCache(int choose, int status, int rest, int[] dp) {
		//dp[status] == 0表示coreLogicWithSillyCache(status)没有算过，去算
		if (dp[status] != 0) {
			//dp[status] == 1表示已经算过了
			//dp[status] == -1表示没有算过
			return dp[status] == 1 ? true : false;
		}
		//默认先手是没有赢的
		boolean ans = false;
		if (rest > 0) {
			for (int i = 1; i <= choose; i++) {
				if (((1 << i) & status) == 0) {
					//子过程的后手就是当前的先手，如果子过程的后手赢了，那么就是当前的先手赢了
					if (!coreLogicWithSillyCache(choose, status | (1 << i), rest - i, dp)) {
						//此时的先手赢了，记录下来
						ans = true;
						break;
					}
				}

			}
		}
		//如果先手赢了，dp[status] == 1，如果先手没赢，dp[status] == -1
		dp[status] = ans ? 1 : -1;
		return ans;
	}
}
