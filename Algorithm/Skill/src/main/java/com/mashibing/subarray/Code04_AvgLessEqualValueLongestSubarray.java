package com.mashibing.subarray;

import com.mashibing.common.SkillUtils;

import java.util.TreeMap;

/**
 * 题目四：
 * 给定一个数组arr，给定一个值v
 * 求子数组平均值小于等于v的最长子数组长度
 *
 * 思路分析：
 * 1.将原数组的所有元素都减去v，得到处理之后的数组，求出累加和<=0的最长子数组的长度，可以是正数、负数、0
 * 2.将原数组的所有元素都减去v，得到处理之后的数组的子数组累加和<=0，子数组的元素都加10还原之后，平均值一定<=10
 * 3.这道题就是求解原数组的所有元素都减去v，得到处理之后的数组，求出累加和<=0的最长子数组是多长
 * 对应回原数组，平均值一定<=10
 * @author xcy
 * @date 2022/6/9 - 15:35
 */
public class Code04_AvgLessEqualValueLongestSubarray {
	public static void main(String[] args) {
		System.out.println("测试开始");
		int maxLen = 20;
		int maxValue = 100;
		int testTime = 500000;
		for (int i = 0; i < testTime; i++) {
			int[] arr = SkillUtils.randomArray(maxLen, maxValue);
			int value = (int) (Math.random() * maxValue);
			int[] arr1 = SkillUtils.copyArray(arr);
			int[] arr2 = SkillUtils.copyArray(arr);
			int[] arr3 = SkillUtils.copyArray(arr);
			int ans1 = maxLength_OptimalSolution(arr1, value);
			int ans2 = logarithmicComparator(arr2, value);
			int ans3 = maxLength(arr3, value);
			if (ans1 != ans2 || ans1 != ans3) {
				System.out.println("测试出错！");
				System.out.print("测试数组：");
				SkillUtils.printArray(arr);
				System.out.println("子数组平均值不小于 ：" + value);
				System.out.println(" maxLength_OptimalSolution方法得到的最大长度：" + ans1);
				System.out.println("logarithmicComparator方法得到的最大长度：" + ans2);
				System.out.println("maxLength方法得到的最大长度：" + ans3);
				System.out.println("=========================");
			}
		}
		System.out.println("测试结束");
	}

	/**
	 * 最优解
	 * 时间复杂度：O(N)
	 * @param arr
	 * @param v
	 * @return
	 */
	public static int maxLength_OptimalSolution(int[] arr, int v) {
		if (arr == null || arr.length == 0) {
			return 0;
		}

		for (int i = 0; i < arr.length; i++) {
			arr[i] -= v;
		}
		return coreLogic(arr, 0);
	}

	/**
	 * 核心逻辑
	 * @param arr
	 * @param K
	 * @return
	 */
	public static int coreLogic(int[] arr, int K) {
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
		int maxLength = 0;
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
			maxLength = Math.max(maxLength, end - i);
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
		return maxLength;
	}


	/**
	 * 暴力解，用于做对数器
	 * 时间复杂度：O(N³)
	 * @param arr
	 * @param v
	 * @return
	 */
	public static int logarithmicComparator(int[] arr, int v) {
		int ans = 0;
		for (int L = 0; L < arr.length; L++) {
			for (int R = L; R < arr.length; R++) {
				int sum = 0;
				int k = R - L + 1;
				for (int i = L; i <= R; i++) {
					sum += arr[i];
				}
				double avg = (double) sum / (double) k;
				if (avg <= v) {
					ans = Math.max(ans, k);
				}
			}
		}
		return ans;
	}

	/**
	 * 时间复杂度：O(N*logN)
	 * @param arr
	 * @param v
	 * @return
	 */
	public static int maxLength(int[] arr, int v) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		TreeMap<Integer, Integer> origins = new TreeMap<>();
		int ans = 0;
		int modify = 0;
		for (int i = 0; i < arr.length; i++) {
			int p1 = arr[i] <= v ? 1 : 0;
			int p2 = 0;
			int querry = -arr[i] - modify;
			if (origins.floorKey(querry) != null) {
				p2 = i - origins.get(origins.floorKey(querry)) + 1;
			}
			ans = Math.max(ans, Math.max(p1, p2));
			int curOrigin = -modify - v;
			if (origins.floorKey(curOrigin) == null) {
				origins.put(curOrigin, i);
			}
			modify += arr[i] - v;
		}
		return ans;
	}
}
