package com.mashibing.day09;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 * 题目四：
 * 给你一个二维整数数组envelopes，其中 envelopes[i] = [wi, hi] ，表示第 i 个信封的宽度和高度
 * 当另一个信封的宽度和高度都比这个信封大的时候，这个信封就可以放进另一个信封里，如同俄罗斯套娃一样
 * 请计算 最多能有多少个 信封能组成一组“俄罗斯套娃”信封（即可以把一个信封放到另一个信封里面）
 * 注意：不允许旋转信封
 * <p>
 * 解题思路：
 * 1.进行排序，首先按照宽度进行从小到大的排序，如果宽度相等，然后再按照高度进行从大到小的排序
 * <p>
 * LeetCode测试链接：
 * https://leetcode.cn/problems/russian-doll-envelopes/
 *
 * @author xcy
 * @date 2022/7/25 - 15:40
 */
public class Code04_EnvelopesProblem {
	public static void main(String[] args) {
		int[][] envelopes =  {
				{7, 2},
				{5, 6},
				{1, 3},
				{1, 5},
				{1, 2},
				{5, 4},
				{7, 6},
		};
		int length1 = maxEnvelopes(envelopes);
		System.out.println(length1);
	}

	public static class Info {
		public int width;
		public int height;

		public Info(int width, int height) {
			this.width = width;
			this.height = height;
		}
	}

	/**
	 * 因为能够保证宽度一样，但是高度比我小的，在我后面
	 * 所以高度较小的是套不进去的，因为长度一样我套不进去。而高度比我小的在我后面，它不会干扰我
	 * 我形成的最长子序列根本没有高度比我小的份
	 * 那么只要左侧高度小于我，长度必小于我，我所形成的递增子序列必能套进去
	 */
	public static class MyComparator implements Comparator<Info> {
		@Override
		public int compare(Info o1, Info o2) {
			return o1.width - o2.width != 0 ? o1.width - o2.width : o2.height - o1.height;
		}
	}

	public static int maxEnvelopes(int[][] envelopes) {
		if (envelopes == null || envelopes.length == 0 || envelopes[0] == null || envelopes[0].length == 0) {
			return 0;
		}
		ArrayList<Info> infos = new ArrayList<>();
		for (int[] envelope : envelopes) {
			infos.add(new Info(envelope[0], envelope[1]));
		}
		infos.sort(new MyComparator());
		int[] arr = new int[envelopes.length];
		for (int i = 0; i < infos.size(); i++) {
			arr[i] = infos.get(i).height;
		}
		return longestIncrementSubSequence(arr);
	}

	/**
	 * 最长递增子序列的长度
	 * @param arr 原始数组
	 * @return 返回最长递增子序列的长度
	 */
	public static int longestIncrementSubSequence(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		int[] ends = new int[arr.length];
		ends[0] = arr[0];

		int l = 0;
		int r = 0;
		int right = 0;

		int mid = 0;
		//最长递增子序列的最大长度至少是1
		int max = 1;

		for (int i = 1; i < arr.length; i++) {
			l = 0;
			r = right;
			while (l <= r) {
				mid = l + ((r - l) >> 1);
				if (arr[i] > ends[mid]) {
					l = mid + 1;
				}else {
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
