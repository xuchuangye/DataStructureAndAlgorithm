package com.mashibing.subarray;

import com.mashibing.common.SkillUtils;

/**
 * 题目三：
 * 给定一个整数组成的无序数组arr，值可能正、可能负、可能0
 * 给定一个整数值K
 * 找到arr的所有子数组里，哪个子数组的累加和<=K，并且是长度最大的
 * 返回其长度
 * <p>
 * 思路分析：
 * 1.创建两个数组minSum[]和minSumEnd[]
 * <p>
 * 2.minSum[i]表示从右往左，以i为起始位置的最小累加和
 * 1)如果当前i位置能够继续往右扩，并且往右扩之后的累加和比当前i位置的累加和还小，
 * 修改minSumEnd[i]的往右扩之后的有边界
 * 2)如果当前i位置不能够继续往右扩，或者往右扩之后的累加和比当前i位置的累加和大
 * 那么minSumEnd[i]的右边界就是i位置本身
 * 3).minSumEnd[i]表示从右往左，以i为起始位置的最小累加和的右边界
 * 举例：
 * arr[] =       { 1,   2,  -4,   3,   7,  -2,   4,  -3}
 * index =         0    1    2    3    4    5    6    7
 * minSum[]    = {-1   -2   -4    3    5   -2    1   -3}
 * minSumEnd[] = { 2    2    2    3    5    5    7    7}
 * minSum[i] = 任何以i开始的子数组之后的所有情况中，最优的最小累加和存放在minSun[]
 * minSumEnd[i] = 任何以i开始的子数组之后的所有情况中，最优的最小累加和的右边界存放在minSunEnd[]
 * <p>
 * 3.
 * a, b, c, d | e
 * 假设a区域的累加和小于等于K，继续扩b区域，a + b区域的累加和小于等于K，继续扩c区域，a + b + c区域的
 * 累加和小于等于K，继续扩d区域，a + b + c + d区域的累加和小于等于K，此时如果继续扩e区域，a + b + c
 * + d + e的区域累加和大于K，截止到e区域之前停止，所以以0位置开始，累加和小于等于K的的最长子数组是
 * a + b + c + d
 * 举例：
 * 假设K = 6
 * --------[ a区域   ] [  b区域  ] [  c区域  ]  d
 * arr[] = {6, -2, -3, 7, -5, -1, 8, -2, -3, 10}
 * index =  0   1   2  3   4   5  6   7   8   9
 * -----a区域的累加和1, b区域的累加和1，c区域的累加和3，a + b + c区域的累加和小于等于K
 * 所以以0开始的小于等于K的累加和的子数组的最大长度为a + b + c区域的长度
 * <p>
 * 4.知道了以0位置开始的小于等于K的子数组的累加和Sum，并且知道以0位置开始的小于等于K的子数组的累加和的右边界
 * 减去0位置上的数值就得到以1位置开始的累加和Sum'，此时Sum'继续加上(右边界++)位置上的数值，如果仍然
 * 小于等于K，那么继续加(右边界++)位置上的数值，直到大于K时停止，就能得到以1位置开始累加和小于等于K的子数组
 * 的最大长度，依此类推
 * 举例：
 * index = 0 ... 13 | 14
 * 以0开始到13位置小于等于K的子数组的累加和Sum，Sum减去0位置上的数值得到1位置到13位置的累加和Sum'，如果
 * 此时加上14位置上的数值大于K，那么以1位置开始的小于等于K的子数组的累加和就是Sum'
 * <p>
 * 5.题目是求子数组累加和小于等于7，并且是长度最大的
 * 已经求出0位置开始的小于等于K的累加和的子数组长度len,如果之后以1位置开始的小于等于K的累加和的子数组长度
 * 都没有比len长，那么就直接舍弃掉，所以步骤4是尝试之后的小于等于K的累加和的子数组的长度能否超过len
 * 举例：
 * index = 0   ...   13 | 14
 * index =   1...7
 * 以0位置开始的小于等于K的累加和的子数组的长度为14，如果1位置开始的小于等于K的累加和的子数组的长度比14
 * 还要小，那么就直接舍弃掉，依此类推，最终会舍弃掉一部分呢可能性
 * <p>
 * 6.
 * minSum知道某个区域的累加和是多少
 * minSumEnd知道某个区域的右边界在哪里
 * <p>
 * 7.题目的最优解会舍弃掉一部分可能性，时间复杂度为O(N)
 * 举例：
 * L  ----------  R
 * L + 1
 * 因为只关心全局中，子数组累加和小于等于K的最大长度，所以已经知道L - R范围的长度了，以L+1位置开始的子数组
 * 累加和小于等于K的最大长度比L - R范围还要短，那么就可以直接忽略
 *
 * @author xcy
 * @date 2022/6/9 - 10:12
 */
public class Code03_LongestLessThanOrEqualToSumSubArrayLength {
	public static void main(String[] args) {
		System.out.println("测试开始！");
		for (int i = 0; i < 10000000; i++) {
			final int value = 100;
			final int len = 10;
			int[] arr = SkillUtils.generateRandomArray(len, value);
			int k = (int) (Math.random() * 20) - 5;
			if (maxLength_OptimalSolution(arr, k) != maxLength(arr, k)) {
				System.out.println("测试错误！");
			}
		}
		System.out.println("测试结束！");
	}

	/**
	 * 时间复杂度：O(N)
	 * 因为end不回退
	 *
	 * @param arr
	 * @param K
	 * @return
	 */
	public static int maxLength_OptimalSolution(int[] arr, int K) {
		if (arr == null || arr.length == 0) {
			return 0;
		}

		int[] minSum = new int[arr.length];
		int[] minSumEnd = new int[arr.length];
		minSum[arr.length - 1] = arr[arr.length - 1];
		minSumEnd[arr.length - 1] = arr.length - 1;
		//从右往左
		for (int i = arr.length - 2; i >= 0; i--) {
			//因为是求以i位置开始的累加和能够往右扩并且最小
			//所以只要是负数，minSum就直接累加，minSumEnd就是下一个负数的索引
			//举例：
			//arr[] =       { 1,   2,  -4,   3,   7,  -2,   4,  -3}
			//index =         0    1    2    3    4    5    6    7
			//minSum[]    = {-1   -2   -4    3    5   -2    1   -3}
			//minSumEnd[] = { 2    2    2    3    5    5    7    7}
			if (minSum[i + 1] < 0) {
				minSum[i] = arr[i] + minSum[i + 1];
				minSumEnd[i] = minSumEnd[i + 1];
			}
			//如果i位置的数值本身就是正数，那么minSum[i]就是arr[i]自己本身
			//minSumEnd[i]就是i位置自己
			else {
				minSum[i] = arr[i];
				minSumEnd[i] = i;
			}
		}
		//能够扩到不能再扩的边界的下一个位置，也就是迟迟扩不进来那一块儿的起始位置
		int end = 0;
		int sum = 0;
		int ans = 0;
		for (int i = 0; i < arr.length; i++) {
			//end < arr.length表示不能越界
			//sum + minSum[end] <= K表示能够继续往右扩，那么就继续往右扩
			//while循环结束之后：
			//1)如果以i开头的情况下，累加和<=k的最长子数组是arr[i..end-1]，看看这个子数组长度能不能更新ans
			//2)如果以i开头的情况下，累加和<=k的最长子数组比arr[i..end-1]短，更新还是不更新ans都不会影响最终结果
			while (end < arr.length && sum + minSum[end] <= K) {
				//累加和加上以i位置开始的子数组小于等于K的累加和
				sum += minSum[end];
				//end来到以i位置开始小于等于K的累加和的子数组的右边界 + 1的位置
				end = minSumEnd[end] + 1;
			}
			//以i位置开始小于等于K的累加和的子数组的最大长度：end - i 等同于 minSumEnd[end] - i + 1
			ans = Math.max(ans, end - i);
			//退出while循环时，说明sum + minSum[end] > K
			//需要减去前面的数值，看看是否还大于K，准备继续往右扩
			//表示还有窗口，哪怕窗口没有数字 [i~end) [4,4)
			if (end > i) {
				sum -= arr[i];
			}
			//i == end,  即将 i++, i > end, 此时窗口概念维持不住了，所以end必须要跟着i一起走
			//i++, end = i + 1
			else {
				//否则就表示既有sum + minSum[end] > K，而且不管减去多少，往右都扩不动了
				//end直接i + 1的位置
				//举例：
				//arr = {-10, 6, 3, 1, 5}  K = 0
				//index=   0  1  2  3  4
				//-10从窗口出去，sum = 10
				//位置1的6从窗口出去，sum = 4
				//位置2的3从窗口出去，sum = 1
				//位置3的1从窗口出去，sum = 0
				//窗口在3和4位置中间
				//4位置的5不能进入窗口，5自己已经超过K了，此时的i == end
				//接下来继续从5位置开始算，end跟着i继续往下走，因为i即将++，所以end = i + 1
				end = i + 1;
			}
		}
		return ans;
	}


	/**
	 * 对数器的解法
	 *
	 * @param arr
	 * @param k
	 * @return
	 */
	public static int maxLength(int[] arr, int k) {
		int[] h = new int[arr.length + 1];
		int sum = 0;
		h[0] = sum;
		for (int i = 0; i != arr.length; i++) {
			sum += arr[i];
			h[i + 1] = Math.max(sum, h[i]);
		}
		sum = 0;
		int res = 0;
		int pre = 0;
		int len = 0;
		for (int i = 0; i != arr.length; i++) {
			sum += arr[i];
			pre = getLessIndex(h, sum - k);
			len = pre == -1 ? 0 : i - pre + 1;
			res = Math.max(res, len);
		}
		return res;
	}

	public static int getLessIndex(int[] arr, int num) {
		int low = 0;
		int high = arr.length - 1;
		int mid = 0;
		int res = -1;
		while (low <= high) {
			mid = (low + high) / 2;
			if (arr[mid] >= num) {
				res = mid;
				high = mid - 1;
			} else {
				low = mid + 1;
			}
		}
		return res;
	}
}
