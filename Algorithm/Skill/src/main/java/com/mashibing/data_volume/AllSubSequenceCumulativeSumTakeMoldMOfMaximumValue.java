package com.mashibing.data_volume;

import com.mashibing.common.SkillUtils;

import java.util.HashSet;
import java.util.TreeSet;

/**
 * 题目一：
 * 给定一个非负数组arr，和一个正数m。
 * 返回arr的所有子序列中累加和%m之后的最大值。
 *
 * @author xcy
 * @date 2022/6/5 - 8:28
 */
public class AllSubSequenceCumulativeSumTakeMoldMOfMaximumValue {
	public static void main(String[] args) {
		int len = 10;
		int value = 100;
		int m = 76;
		int testTime = 500000;
		System.out.println("测试开始！");
		for (int i = 0; i < testTime; i++) {
			int[] arr = SkillUtils.generateRandomArray(len, value);
			int ans1 = allSubSequenceCumulativeSumTakeMoldMOfMaximumValue_1(arr, m);
			int ans2 = allSubSequenceCumulativeSumTakeMoldMOfMaximumValue_2(arr, m);
			int ans3 = allSubSequenceCumulativeSumTakeMoldMOfMaximumValue_3(arr, m);
			int ans4 = allSubSequenceCumulativeSumTakeMoldMOfMaximumValue_4(arr, m);
			if (ans1 != ans2 || ans1 != ans3 || ans1 != ans4) {
				System.out.println("测试失败！");
				break;
			}
		}
		System.out.println("测试结束！");
	}

	public static int allSubSequenceCumulativeSumTakeMoldMOfMaximumValue_1(int[] arr, int m) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		HashSet<Integer> set = new HashSet<>();
		//从arr数组第0位置开始，此时的累加和 sum == 0
		coreLogic(arr, 0, 0, set);
		int max = 0;
		for (Integer sum : set) {
			max = Math.max(max, sum % m);
		}
		return max;
	}

	public static void coreLogic(int[] arr, int index, int sum, HashSet<Integer> set) {
		//表示arr数组0 ~ arr.length - 1所有位置上的数值已经组成累加和sum
		if (index == arr.length) {
			set.add(sum);
		} else {
			//情况1：不使用arr数组i位置上的数值
			coreLogic(arr, index + 1, sum, set);
			//情况2：使用arr数组i位置上的数值
			coreLogic(arr, index + 1, sum + arr[index], set);
		}
	}


	/**
	 * 适用于arr[i]的值范围比较小，m比较大的情况
	 * 举例：
	 * 假设m = 10的12次方，arr[]长度不超过10的3次方，arr[i]值不超过10的2次方，那么累加和就不超过10的5次方
	 * <p>
	 * 时间复杂度：
	 * dp[i][j]
	 * 数组索引i的范围不超过10的3次方，累加和j范围不超过10的5次方，10的8次方刚刚好
	 * <p>
	 * boolean dp[i][j]的含义：
	 * i表示arr数组的索引，j表示累加和
	 * dp[i][j]表示arr数组上0 ~ i的值的累加和是j
	 * 情况1：不使用i位置上的数值
	 * 如果dp[i - 1][j]能够组成累加和j，那么dp[i][j]也能够组成累加和j
	 * dp[i][j] = dp[i - 1][j]
	 * 情况2：使用i位置上的数值
	 * 如果dp[i - 1][j]能够组成累加和j - arr[i]，那么dp[]i[j]就能够组成累加和j
	 *
	 * @param arr
	 * @param m
	 * @return
	 */
	public static int allSubSequenceCumulativeSumTakeMoldMOfMaximumValue_2(int[] arr, int m) {
		if (arr == null || arr.length == 0) {
			return 0;
		}

		int sum = 0;
		for (int value : arr) {
			sum += value;
		}

		//dp[i][j]
		//i表示数组索引，范围：0 ~ arr.length - 1
		//j表示累加和，范围：0 ~ sum
		boolean[][] dp = new boolean[arr.length][sum + 1];
		//所有行的第0列都是true，因为sum == 0时，肯定是true
		for (int i = 0; i < arr.length; i++) {
			dp[i][0] = true;
		}

		dp[0][arr[0]] = true;
		//第0行已经填写过了，i从第1行 开始
		for (int i = 1; i < arr.length; i++) {
			for (int j = 0; j <= sum; j++) {
				dp[i][j] = dp[i - 1][j];
				//防止越界问题
				if (j - arr[i] >= 0) {
					//dp[i - 1][j - arr[i]]表示arr数组0 ~ i - 1位置上的数值能不能组成累加和j - arr[i]
					//如果能组成累加和j - arr[i]，返回true，否则返回false
					//false | true == true
					//true | true == true
					//false |= true --> ture
					dp[i][j] |= dp[i - 1][j - arr[i]];
				}
			}
		}
		int ans = 0;
		for (int j = 0; j <= sum; j++) {
			//dp[arr.length - 1][j]
			//arr.length - 1表示arr数组0 ~ arr.length - 1位置上的数值能不能组成累加和j
			//如果能够组成累加和j，返回true，否则返回false
			if (dp[arr.length - 1][j]) {
				ans = Math.max(ans, j % m);
			}
		}
		return ans;
	}

	/**
	 * 适用于arr[i]的值范围比较大，m比较小的情况
	 * <p>
	 * dp[i][j]的含义：
	 * 表示arr数组0 ~ i位置上的数值自由选择，组成所有的累加和中有没有 % m之后，余数是j的
	 * 这里有个隐含条件：余数j一定是小于m的
	 * <p>
	 * 情况1：不使用i位置上的数值
	 * dp[i - 1][j]表示0 ~ i - 1位置上的数值自由选择，组成所有的累加和中有没有 % m之后，余数是j的
	 * 如果dp[i - 1][j]能够计算出余数j，那么dp[i][j]也能够计算出余数j
	 * <p>
	 * 情况2：使用i位置上的数值
	 * 1.
	 * dp[i][j]表示i位置上的数值 % m，得到余数a，如果余数a 小于等于 余数j，那么就看dp[i - 1][j - a]
	 * dp[i - 1][j - a]表示0 ~ i - 1位置上的数值自由选择，组成所有的累加和中有没有 % m之后，余数是j - a
	 * 如果dp[i - 1][j - a]能够计算出余数j，那么dp[i][j]也能够计算出余数j
	 * 2.
	 * dp[i][j]表示i位置上的数值 % m，得到余数a，如果余数a 大于 余数j，那么就看dp[i - 1][m + j - a]
	 * dp[i - 1][m + j - a]表示0 ~ i - 1位置上的数值自由选择，组成所有的累加和中有没有 % m之后，余数是m + j - a
	 * 如果dp[i - 1][m + j - a]能够计算出余数j，那么dp[i][j]也能够计算出余数j
	 *
	 * @param arr
	 * @param m
	 * @return
	 */
	public static int allSubSequenceCumulativeSumTakeMoldMOfMaximumValue_3(int[] arr, int m) {
		if (arr == null || arr.length == 0) {
			return 0;
		}

		//任何数%m之后，不可能超出0 ~ m - 1的范围
		boolean[][] dp = new boolean[arr.length][m];
		for (int i = 0; i < arr.length; i++) {
			//所有行的余数都是j，并且j == 0，那么arr数组0 ~ i位置上的数值自由选择，组成的累加和中一定有%m之后，余数是j的
			dp[i][0] = true;
		}
		dp[0][arr[0] % m] = true;

		for (int i = 1; i < arr.length; i++) {
			for (int j = 0; j < m; j++) {
				//情况1：不使用i位置上的数值
				//dp[i - 1][j]表示0 ~ i - 1位置上的数值自由选择，组成所有的累加和中有没有 % m之后，余数是j的
				dp[i][j] = dp[i - 1][j];

				//情况2：使用i位置上的数值
				//cur表示当前i位置上单独的余数
				int cur = arr[i] % m;
				//j - cur >= 0
				//j >= cur
				//dp[i][j]表示i位置上的数值 % m，得到余数cur，如果余数a 小于等于 余数j，那么就看dp[i - 1][j - cur]
				if (cur <= j) {
					//dp[i - 1][j - a]表示0 ~ i - 1位置上的数值自由选择，组成所有的累加和中有没有 % m之后，余数是j - cur
					dp[i][j] |= dp[i - 1][j - cur];
				}
				//dp[i][j]表示i位置上的数值 % m，得到余数cur，如果余数a 大于 余数j，那么就看dp[i - 1][m + j - cur]
				else {
					//dp[i - 1][m + j - a]表示0 ~ i - 1位置上的数值自由选择，组成所有的累加和中有没有 % m之后，余数是m + j - cur
					dp[i][j] |= dp[i - 1][m + j - cur];
				}
			}
		}
		int ans = 0;
		for (int j = 0; j < m; j++) {
			if (dp[arr.length - 1][j]) {
				ans = j;
			}
		}
		return ans;
	}

	/**
	 * 适用于arr[i]的值范围比较大，m比较大，但是arr数组长度比较小的情况
	 * 举例：
	 * 假设arr数组长度为30，暴力展开的，2的30次方1,073,741,824 -> 10的9次方
	 * 超出了10的8次方，拿不下的
	 * 1.此时就需要使用分治，将原数组暴力的分成左右两半，左边在10的8次方以内，右边在10的8次方以内，而且整合的逻辑不太复杂
	 * 举例：
	 * 左边15个，右边15个，
	 * 求出左边15个数值自由选择，组成的所有累加和中%m得到的所有余数
	 * 求出右边15个数值自由选择，组成的所有累加和中%m得到的所有余数
	 * 2的15次方 + 2的15次方 = 32768 + 32768 = 65532
	 * 2.三种情况：
	 * 1)只从左边取
	 * 2)只从右边取
	 * 3)左右两边取，假设左边取出的余数i，右边取出的余数小于等于m - i
	 * 假设获取出余数的时间复杂度：O(logN)
	 *
	 * @param arr
	 * @param m
	 * @return
	 */
	public static int allSubSequenceCumulativeSumTakeMoldMOfMaximumValue_4(int[] arr, int m) {
		if (arr == null || arr.length == 0) {
			return 0;
		}

		if (arr.length == 1) {
			return arr[0] % m;
		}

		int mid = (arr.length - 1) / 2;
		//左边
		TreeSet<Integer> leftSet = new TreeSet<>();
		coreLogic(arr, 0, 0, mid, m, leftSet);

		//右边
		TreeSet<Integer> rightSet = new TreeSet<>();
		coreLogic(arr, 0, mid + 1, arr.length - 1, m, rightSet);

		int ans = 0;
		for (Integer leftMod : leftSet) {
			ans = Math.max(ans, leftMod + rightSet.floor(m - 1 - leftMod));
		}
		return ans;
	}

	/**
	 *
	 * @param arr 数组
	 * @param sum 累加和
	 * @param index 从index开始
	 * @param end 到end截止
	 * @param m 被模数
	 * @param set TreeSet
	 */
	public static void coreLogic(int[] arr, int sum, int index, int end, int m, TreeSet<Integer> set) {
		if (index == end + 1) {
			set.add(sum % m);
		} else {
			//不使用当前index位置上的数值
			coreLogic(arr, sum, index + 1, end, m, set);
			//使用当前index位置上的数值
			coreLogic(arr, sum + arr[index], index + 1, end, m, set);
		}
	}
}

