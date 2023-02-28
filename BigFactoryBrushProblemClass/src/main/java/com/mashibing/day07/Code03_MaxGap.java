package com.mashibing.day07;

/**
 * 题目三：
 * 给定一个数组arr，返回如果排序之后（注意是如果排序），相邻两数的最大差值。
 * 如果数组元素个数小于 2，则返回 0
 * 要求时间复杂度O(N)，不能使用非基于比较的排序
 * <p>
 * 解题思路：
 * 1.借助桶排序的思想
 * 假设：
 * arr = {17, 13, 15,  0, 49, 47, 43, 99, 84}
 * arr.length = 9
 * 根据桶排序的思想，创建9 + 1 == 10个桶
 * 桶的范围：0 ~ 9  10 ~ 19  20 ~ 29 30 ~ 39 40 ~ 49 50 ~ 59 60 ~ 69 70 ~ 79 80 ~ 89 90 ~ 99
 * min  :    0       13                       43                              84      99
 * max  :    0       17                       49                              84      99
 * 0 ~ 9的max : 0 与 10 ~ 19的min : 13的差值 -> 13
 * 10 ~ 19的max : 17 与 40 ~ 49的min : 43的差值 -> 26
 * 40 ~ 49的max : 49 与 80 ~ 89的min : 84的差值 -> 35
 * 80 ~ 89的max : 84 与 90 ~ 99的min : 99的差值 -> 15
 * 所以arr[]排序之后，相邻两数的最大差值是35
 * 2.为什么是所有的桶都需要找差值，而不是仅仅在空桶的左右两边去找
 * (1)因为空桶左边非空桶的最大值和右边非空桶的最小值的差值不一定是最大的
 * 假设：
 * 桶的范围：10 ~ 19 20 ~ 29 30 ~ 39 40 ~ 49
 * min  ：    10      29              40
 * max  ：    10      29              40
 * 空桶30 ~ 39的左边的非空桶的最大值29 与 右边的非空桶的最小值40的差值 -> 11
 * 而非空桶10 ~ 19的最大值10 与 右边的非空桶的最小值29的差值 -> 19
 * 反而是非空桶之间的最大值比较大
 * (2)因此在空桶的左右两边去找，找寻这个空桶，找寻这个空桶的目的：
 * 不是说，答案一定在空桶的左边非空桶的最大值以及右边非空桶的最小值
 * 而是，找到一个平凡解，平凡解可能不是 答案，意义是：为了去除绝对不是必要考虑的条件和答案：同一个桶内的最大值和最小值的差值
 * 去除同一个桶内的最大值和最小值的差值这个可能性，优化代码
 * 答案可能来自平凡解的情况，也可能来自跨桶的情况，肯定不是同一个桶内的情况
 * 3.利用鸽笼原理的思想
 * 假设arr.length == N，那么创建N + 1个桶，必有一个桶为空
 * <p>
 * LeetCode测试链接：
 * https://leetcode.com/problems/maximum-gap/
 *
 * @author xcy
 * @date 2022/7/23 - 7:51
 */
public class Code03_MaxGap {
	public static void main(String[] args) {

	}

	/**
	 * 时间复杂度：O(N)
	 *
	 * @param arr
	 * @return
	 */
	public static int maximumGap(int[] arr) {
		if (arr == null || arr.length < 2) {
			return 0;
		}
		int N = arr.length;
		int min = Integer.MAX_VALUE;
		int max = Integer.MIN_VALUE;

		for (int element : arr) {
			min = Math.min(min, element);
			max = Math.max(max, element);
		}
		//如果最大值==最小值，表示数组中只包含一种数值
		if (max == min) {
			return 0;
		}
		//数组长度为N，那么创建N + 1个桶
		//桶包含的三个信息
		//桶中是否存在数据
		boolean[] hasNum = new boolean[N + 1];
		//桶中的最大值
		int[] maxs = new int[N + 1];
		//桶中的最小值
		int[] mins = new int[N + 1];
		for (int num : arr) {
			//获取num进入第几个桶的桶号
			int bucket = getBucketNumber(num, N, min, max);
			//判断该桶中是否包含数据，如果包含，当前num和原来桶中的最大值进行比较，如果不包含，当前num直接作为桶中的最大值
			maxs[bucket] = hasNum[bucket] ? Math.max(num, maxs[bucket]) : num;
			//判断该桶中是否包含数据，如果包含，当前num和原来桶中的最小值进行比较，如果不包含，当前num直接作为桶中的最小值
			mins[bucket] = hasNum[bucket] ? Math.min(num, mins[bucket]) : num;
			//标记桶中已经包含数据
			hasNum[bucket] = true;
		}
		int ans = 0;
		//上一个桶的最大值
		//第0号桶作为遍历时，第一个桶的最大值
		int lastMax = maxs[0];

		for (int index = 1; index <= N; index++) {
			//如果桶中包含数据
			if (hasNum[index]) {
				//求出当前非空桶的最小值 - 上一个非空桶的最大值的最大差值
				ans = Math.max(ans, mins[index] - lastMax);
				//当前非空桶的最大值作为下一个桶遍历时，上一个桶的最大值
				lastMax = maxs[index];
			}
		}
		return ans;
	}

	/**
	 * 当前num进入第几个桶
	 *
	 * @param num
	 * @param len
	 * @param min
	 * @param max
	 * @return 返回num进入第几个桶的桶号
	 */
	public static int getBucketNumber(long num, long len, long min, long max) {
		return (int) ((num - min) * len / (max - min));
	}
}
