package com.mashibing.day03;

import com.mashibing.common.TestUtils;

import java.util.Arrays;
import java.util.TreeSet;

/**
 * 题目六：
 * 给定整数数组nums和目标值goal，需要从nums中选出一个子序列，使子序列元素总和最接近goal
 * 也就是说如果子序列元素和为sum ，需要最小化绝对差abs(sum - goal)，返回 abs(sum - goal)可能的最小值
 * 注意数组的子序列是通过移除原始数组中的某些元素（可能全部或无）而形成的数组。
 * <p>
 * 数据规模：
 * 1 <= nums.length <= 40
 * -10的7次方 <= nums[i] <= 10的7次方
 * -10的9次方 <= goal <= 10的9次方
 * <p>
 * 解题思路：
 * 1.使用分治，将nums数组分成左右两边，进行暴力递归，左边的时间复杂度：O(2的20次方)
 * 右边的时间复杂度：O(2的20次方)，总体数据规模2的20次方，那么总体的时间复杂度：O(2的20次方 * log以2为底的2的20次方)
 * 也就是O(2的20次方 * 20)
 * 2.最终时间复杂度：O(2的20次方) + O(2的20次方) + O(2的20次方 * 20)
 * <p>
 * LeetCode测试链接：
 * https://leetcode.com/problems/closest-subsequence-sum/
 *
 * @author xcy
 * @date 2022/7/13 - 8:33
 */
public class Code06_ClosestSubsequenceSum {
	public static void main(String[] args) {
		int testTimes = 10;
		int n = 10;
		int valueMax = 100;
		System.out.println("测试开始！");
		for (int i = 0; i < testTimes; i++) {
			int[] nums = TestUtils.randomArray(n, valueMax);
			int goal = (int) (Math.random() * valueMax) + 1;
			int count1 = minAbsDifference(nums, goal);
			int count2 = minAbsDifference2(nums, goal);
			if (count1 != count2) {
				System.out.println(count1);
				System.out.println(count2);
				System.out.println("测试出错！");
				break;
			}
		}
		System.out.println("测试结束！");
	}

	/**
	 * @param nums
	 * @param goal
	 * @return
	 */
	public static int minAbsDifference(int[] nums, int goal) {
		if (nums == null || nums.length == 0) {
			return goal;
		}
		int mid = nums.length / 2;

		TreeSet<Integer> leftSet = new TreeSet<>();
		int le = coreLogic(nums, 0, 0, mid,0, leftSet);

		TreeSet<Integer> rightSet = new TreeSet<>();
		int re = coreLogic(nums, 0, mid + 1, nums.length - 1,0, rightSet);

		int ans = Math.abs(goal);
		for (Integer leftSum : leftSet) {
			int rest = goal - leftSum;
			while (!rightSet.isEmpty() && Math.abs(rest - rightSet.floor(re - 1)) <= Math.abs(rest - rightSet.floor(re))) {
				rightSet.pollFirst();
			}
			ans = Math.min(ans, Math.abs(rest - rightSet.floor(re)));
		}
		return ans;
	}

	public static int coreLogic(int[] arr, int sum, int index, int end,int fill, TreeSet<Integer> treeSet) {
		if (index == end + 1) {
			treeSet.add(sum);
			fill++;
		} else {
			//选择当前index位置的元素
			coreLogic(arr, sum + arr[index], index + 1, end,fill, treeSet);
			//不选择当前index位置的元素
			coreLogic(arr, sum, index + 1, end,fill, treeSet);
		}
		return fill;
	}

	//l.length = 2的20次方
	public static int[] l = new int[1 << 20];
	//r.length = 2的20次方
	public static int[] r = new int[1 << 20];

	public static int minAbsDifference2(int[] nums, int goal) {
		if (nums == null || nums.length == 0) {
			return goal;
		}
		int mid = nums.length >> 1;
		int le = process(nums, 0, mid, 0, 0, l);
		int re = process(nums, mid, nums.length, 0, 0, r);
		Arrays.sort(l, 0, le);
		Arrays.sort(r, 0, re--);
		int ans = Math.abs(goal);
		for (int i = 0; i < le; i++) {
			int rest = goal - l[i];
			while (re > 0 && Math.abs(rest - r[re - 1]) <= Math.abs(rest - r[re])) {
				re--;
			}
			ans = Math.min(ans, Math.abs(rest - r[re]));
		}
		return ans;
	}

	public static int process(int[] nums, int index, int end, int sum, int fill, int[] arr) {
		if (index == end) {
			arr[fill++] = sum;
		} else {
			fill = process(nums, index + 1, end, sum, fill, arr);
			fill = process(nums, index + 1, end, sum + nums[index], fill, arr);
		}
		return fill;
	}
}
