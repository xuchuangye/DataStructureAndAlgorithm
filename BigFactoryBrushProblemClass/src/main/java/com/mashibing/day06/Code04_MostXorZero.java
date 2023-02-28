package com.mashibing.day06;

import com.mashibing.common.TestUtils;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 题目四：
 * 数组中所有数都异或起来的结果，叫做异或和。
 * 给定一个数组arr，可以任意切分成若干个不相交的子数组。
 * 其中一定存在一种最优方案，使得切出异或和为0的子数组最多，返回这个最多数量
 *
 * 解题思路：
 * 使用假设答案法
 * 首先假设一个客观上最优的答案，把best最好的状况剖析出来，分析这个状况所拥有的性质/特征，设计coding流程，而且是简单的coding流程，
 * 把best这种状况在coding里，不要让这个状况错误即可
 *
 * 这道题的假设答案法思路：
 * 1.假设最后一部分子数组的异或和不为0，那么0 ~ i范围上如何切分，那么肯定是0 ~ i - 1范围上如何进行切分
 * 2.假设最后一部分子数组的异或和为0，那么找到总的异或和上一次出现的位置进行切分，左边是0 ~ pre范围，也就是dp[pre]，右边是pre + 1 ~ i
 * 范围，而pre + 1 ~ i范围上的异或和为0的个数也就是1
 *
 * @author xcy
 * @date 2022/7/21 - 8:57
 */
public class Code04_MostXorZero {
	public static void main(String[] args) {
		int testTime = 150000;
		int maxSize = 12;
		int maxValue = 5;
		boolean succeed = true;
		for (int i = 0; i < testTime; i++) {
			int[] arr = TestUtils.generateRandomArray(maxSize, maxValue);
			int res = mostXOR(arr);
			int comp = comparator(arr);
			if (res != comp) {
				succeed = false;
				TestUtils.printArray(arr);
				System.out.println(res);
				System.out.println(comp);
				break;
			}
		}
		System.out.println(succeed ? "Nice!" : "Fucking fucked!");
	}

	/**
	 * 暴力解法
	 * <p>
	 * 时间复杂度：O(2的N次方)，N为arr.length
	 *
	 * @param arr
	 * @return
	 */
	public static int comparator(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}

		int index = 0;
		//前缀异或和
		int[] eor = new int[arr.length];
		eor[0] = arr[0];
		for (int i = 1; i < arr.length; i++) {
			eor[i] = eor[i - 1] ^ arr[i];
		}
		return process(eor, 1, new ArrayList<Integer>());
	}

	/**
	 * 假设
	 * arr = {}
	 * index = 0 1 2 3 4 5
	 * process(1, list:{})
	 * (1)情况一：表示[0, 1)左闭右开，0 ~ 0切分为一部分，1处于待定状态，2位置决定了1位置是否进行切分，list表示切分的位置，到不了的位置
	 * 如果2位置决定了1位置进行切分，也就是调用process(2, list:{1})
	 * a.如果2位置决定对1位置进行切分，调用process(3, list:{1, 2})，表示[0, 1), [1, 2)，此时3位置决定了2位置是否进行切分
	 * b.如果2位置决定对1位置不进行切分，调用process(3, list:{1})，表示[0, 1)，此时3位置决定了1,2位置是否进行切分
	 * (2)情况二：表示0,1作为一部分处于待定状态，2位置决定了0和1位置是否进行切分，如果2位置决定了1位置不进行切分，也就是调用process(2, list{})
	 *
	 * @param eor
	 * @param index 决定切分是否结束，如果切分结束，就将index放入parts中，如果不结束，就不放
	 * @param parts 切分的位置，也就是到不了的位置
	 * @return
	 */
	private static int process(int[] eor, int index, ArrayList<Integer> parts) {
		int ans = 0;
		if (index == eor.length) {
			//深度优先遍历清理现场
			//举例：
			//arr.length - 1 == 9 越界位置arr.length == 10
			//假设切分(隔断)的界限：{3, 6, 8}
			//来到最后一部分，将10放进去，否则最后一部分不全
			//第1个部分：[0, 3)，第2个部分：[3, 6)，第3个部分：[6, 8)，最后一个部分：[8, 10)
			//最后将10删除，否则数据会脏

			//将越界位置放入parts，作为最后一部分的结束
			parts.add(eor.length);
			//然后统计异或和为0有多少个切分区域
			ans = eorZeroParts(eor, parts);
			//最后将parts中人为添加进去的位置删掉
			parts.remove(parts.size() - 1);
		} else {
			//前一个部分不结束，而且index还要进去
			int situation1 = process(eor, index + 1, parts);
			//前一个部分结束，将当前的index加入进parts中进行切分和隔断
			parts.add(index);
			int situation2 = process(eor, index + 1, parts);
			//将当前index从parts中删除，清理现场
			parts.remove(parts.size() - 1);
			ans = Math.max(situation1, situation2);
		}
		return ans;
	}

	/**
	 * 统计每一个切分的区域中异或和为0的个数，并返回
	 *
	 * @param eor
	 * @param parts
	 * @return
	 */
	public static int eorZeroParts(int[] eor, ArrayList<Integer> parts) {
		int ans = 0;
		int L = 0;
		//每一个切分的界限
		for (Integer end : parts) {
			if ((eor[end - 1] ^ (L == 0 ? 0 : eor[L - 1])) == 0) {
				ans++;
			}
			L = end;
		}
		return ans;
	}

	/**
	 * 使用动态规划的从左往右的尝试模型
	 * 时间复杂度：o(N)
	 * <p>
	 * dp[]有两种可能性：
	 * 可能性一：最后一部分的异或和不为0，最后一部分可能包含i位置的数，也可能不包含，那么包含i位置的数和不包含i位置的数都一样，所以dp[i] == dp[i - 1]的值
	 * 反正最后一个部分的异或和不为0，有没有i位置的数都一样
	 * 可能性二：最后一部分的异或和为0，那么假设arr[]0 ~ arr.length - 1范围上总的异或和为xor，那么数组中可能出现过并且是最后一次出现异或和为xor，而且位置是pre，
	 * 以及pre + 1 ~ arr.length - 1的范围异或和为0的个数为1，因为根据最优的情况下，arr[]pre + 1 ~ arr.length - 1的范围上不可能再分出
	 * 两个异或和都为0的子数组，所以可能性二：dp[i] = pre == -1 ? 1: dp[pre] + 1
	 *
	 * @param arr
	 * @return
	 */
	public static int mostXOR(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		//dp[i]表示在arr[]0 ~ i范围上切分成若干个子数组，并且在所有子数组中，数组元素能够异或和成为0并且数量最多的子数组的元素个数
		int[] dp = new int[arr.length];
		//key : 表示前缀和
		//value : 表示前缀和上一次出现的位置
		//统计表：统计异或和以及异或和上一次出现的位置
		HashMap<Integer, Integer> hashMap = new HashMap<>();
		//一开始，前缀和为0，上一次出现的位置是-1
		hashMap.put(0, -1);
		//表示arr[]0 ~ i范围的异或和
		int xor = 0;
		for (int i = 0; i < arr.length; i++) {
			xor ^= arr[i];
			//如果统计表中记录了该异或和
			if (hashMap.containsKey(xor)) {
				//得到该异或和上一次出现的位置
				int pre = hashMap.get(xor);
				//可能性二：
				//如果pre == -1表示之前没有出现过相同的异或和，此时pre + 1 == 0，那么0 ~ i自己就是一个整体，所以dp[i] == 1
				//如果pre != -1表示之前已经出现过相同的异或和，那么
				//之前arr[] 0 ~ pre范围上的异或和为0的个数：dp[pre]
				// +
				//现在arr[] pre + 1 ~ i范围上的异或和为0的个数：1
				//所以dp[i] = 也就是dp[pre] + 1
				dp[i] = pre == -1 ? 1 : (dp[pre] + 1);
			}
			//可能性一：
			//如果i > 0
			if (i > 0) {
				dp[i] = Math.max(dp[i - 1], dp[i]);
			}
			//即使更新下一次循环的异或和
			//xor作为下一次循环的异或和的上一次出现的位置
			hashMap.put(xor, i);
		}
		//返回在arr[]0 ~ arr.length - 1范围上切分成若干个子数组，并且在所有子数组中，数组元素能够异或和成为0并且数量最多的子数组的元素个数
		return dp[arr.length - 1];
	}
}
