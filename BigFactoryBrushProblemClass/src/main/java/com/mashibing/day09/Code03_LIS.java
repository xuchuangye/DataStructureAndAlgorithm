package com.mashibing.day09;

/**
 * 题目三：
 * 给定一个数组arr，求最长递增子序列长度
 * <p>
 * Leetcode测试链接：
 * https://leetcode.cn/problems/longest-increasing-subsequence
 *
 * @author xcy
 * @date 2022/7/25 - 15:39
 */
public class Code03_LIS {
	public static void main(String[] args) {
		int[] arr = {4, 10, 4, 3, 8, 9};
		int length1 = lengthOfLIS(arr);
		System.out.println(length1);
	}

	/**
	 * 使用动态规划的方式
	 * <p>
	 * 时间复杂度：O(N的2次方)
	 *
	 * @param arr
	 * @return
	 */
	public static int lengthOfLIS(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		int[] dp = new int[arr.length];
		dp[0] = 1;
		for (int i = 0; i < arr.length; i++) {
			int j = i;
			while (j <= i && j + 1 < arr.length) {
				if (arr[j + 1] <= arr[j]) {
					dp[j + 1] = dp[j];
				} else {
					dp[j + 1] = dp[j] + 1;
				}
				j++;
			}
		}
		int ans = 0;
		for (int element : dp) {
			ans = Math.max(ans, element);
		}
		return ans;
	}

	/**
	 * dp[i]表示以当前位置结尾的最长递增子序列的长度
	 * end[i]表示目前，在所有长度为i + 1的子序列中，最小结尾是end[i]
	 * <p>
	 * arr[] = {3, 2, 1, 2, 3, 0, 4, 6, 2, 7}
	 * index =  0  1  2  3  4  5  6  7  8  9
	 * <p>
	 * dp[i] = {1, 1, 1, 2, 3, 1, 4, 5, 2, 6}
	 * index =  0  1  2  3  4  5  6  7  8  9
	 * 0位置的长度为1子序列的最小结尾是3
	 * 当前位置的值比左边的小，1位置的长度为1子序列的最小结尾是2，同样都是长度为1的子序列，并且1位置的值比0位置的值小
	 * 所以将end[0] == 3修改为end[0] == 2
	 * 当前位置的值比左边的小，2位置的长度为1子序列的最小结尾是1，同样都是长度为1的子序列，并且2位置的值比1位置的值小
	 * 所以将end[0] == 2修改为end[0] == 1
	 * 当前位置的值比左边的大，3位置的长度为2子序列的最小结尾是2，那么可以进行扩充，所以end[1] == 2
	 * 当前位置的值比左边的大，4位置的长度为3子序列的最小结尾是3，那么可以进行扩充，所以end[2] == 3
	 * 当前位置的值比左边的小，5位置的长度为1子序列的最小结尾是0，同样都是长度为1的子序列，并且5位置的值比2位置的值小
	 * 所以将end[0] == 1修改 为end[0] == 0
	 * 当前位置的值比左边的大，6位置的长度为4子序列的最小结尾是4，那么可以进行扩充，所以end[3] == 4
	 * 当前位置的值比左边的大，7位置的长度为5子序列的最小结尾是6，那么可以进行扩充，所以end[4] == 6
	 * 当前位置的值比左边的小，8位置的长度为2子序列的最小结尾是2，并且8位置的值比3位置的值小或者相等
	 * 所以将end[1] == 2修改 为end[1] == 2
	 * 当前位置的值比左边的大，9位置的长度为6子序列的最小结尾是7，那么可以进行扩充，所以end[5] == 7
	 * end[i]= {0, 2, 3, 4, 6, 7}
	 * index =  0  1  2  3  4  5
	 *
	 * @param arr 原始数组
	 * @return 返回最长递增子序列的长度
	 */
	public static int longestIncreasingSubsequence(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		int[] ends = new int[arr.length];
		ends[0] = arr[0];

		int l = 0;
		int r = 0;
		int right = 0;

		int max = 1;
		int mid = 0;
		for (int i = 1; i < arr.length; i++) {
			l = 0;
			r = right;
			while (l <= r) {
				mid = l + ((r - l) >> 1);
				if (arr[i] > ends[mid]) {
					l = mid + 1;
				} else {
					r = mid - 1;
				}
			}
			right = Math.max(l, right);
			ends[l] = arr[i];
			max = Math.max(max, l + 1);
		}
		return max;
	}
}
