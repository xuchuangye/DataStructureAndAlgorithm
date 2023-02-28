package com.mashibing.quadrilateral_inequality;

import com.mashibing.common.SkillUtils;

/**
 * 题目一：
 * 给定一个非负数组arr，长度为N，
 * 那么有N-1种方案可以把arr切成左右两部分
 * 每一种方案都有，min{左部分累加和，右部分累加和}
 * 求这么多方案中，min{左部分累加和，右部分累加和}的最大值是多少？
 * 整个过程要求时间复杂度O(N)
 *
 * @author xcy
 * @date 2022/6/11 - 8:43
 */
public class Code01_BestSplitForAll {
	public static void main(String[] args) {
		int N = 20;
		int max = 30;
		int testTime = 1000000;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int len = (int) (Math.random() * N);
			int[] arr = SkillUtils.randomArray(len, max);
			int ans1 = logarithmicComparator(arr);
			int ans2 = bestSplitForAll(arr);
			if (ans1 != ans2) {
				System.out.println(ans1);
				System.out.println(ans2);
				System.out.println("Oops!");
			}
		}
		System.out.println("测试结束");
	}

	/**
	 * 对数器
	 *
	 * 时间复杂度：O(N²)
	 * @param arr
	 * @return
	 */
	public static int logarithmicComparator(int[] arr) {
		if (arr == null || arr.length < 2) {
			return 0;
		}
		int N = arr.length;
		int ans = 0;
		for (int s = 0; s < N - 1; s++) {
			int sumL = 0;
			for (int L = 0; L <= s; L++) {
				sumL += arr[L];
			}
			int sumR = 0;
			for (int R = s + 1; R < N; R++) {
				sumR += arr[R];
			}
			ans = Math.max(ans, Math.min(sumL, sumR));
		}
		return ans;
	}

	/**
	 * 最优解
	 *
	 * 时间复杂度：O(N)
	 * @param arr
	 * @return
	 */
	public static int bestSplitForAll(int[] arr) {
		//数组为空或者数组的长度小于2，无法分成左右两部分
		if (arr == null || arr.length < 2) {
			return 0;
		}
		//数组总的累加和
		int sumAll = 0;
		for (int value : arr) {
			sumAll += value;
		}
		//将数组分成两部分
		//sumLeft表示左部分的累加和
		int sumLeft = 0;
		//sumRight表示右部分的累加和
		int sumRight = 0;
		int ans = 0;
		//s表示左部分的累加和与右部分的累加和的分界线
		//举例：
		//arr =     {3, 2, 5, 4, 1, 6, 8}
		//sumAll = 29
		//s = 0
		//sumLeft =  3
		//sumRight = 26
		//s = 1
		//sumLeft =  5
		//sumRight = 24
		// s = 2
		//sumLeft =  10
		//sumRight = 19
		for (int s = 0; s < arr.length - 1; s++) {
			sumLeft += arr[s];
			sumRight = sumAll - sumLeft;
			//左右部分的累加和取最小值
			//所有的最小值中取出最大值
			ans = Math.max(ans, Math.min(sumLeft, sumRight));
		}
		return ans;
	}
}
