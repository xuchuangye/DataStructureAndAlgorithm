package com.mashibing.day20;

import com.sun.source.util.TaskEvent;

import java.util.Arrays;

/**
 * 完美洗牌问题
 * 给定一个长度为偶数的数组arr，假设长度为N*2
 * 左部分：arr[L1...Ln] 右部分： arr[R1...Rn]
 * 请把arr调整成arr[L1,R1,L2,R2,L3,R3,...,Ln,Rn]
 * 要求时间复杂度O(N)，额外空间复杂度O(1)
 * <p>
 * 该题目的算法原型：
 * 给定一个长度为N的数组arr，已知左部分为L ~ M，右部分为M ~ R
 * 不使用辅助数组，将原始数组调整为右部分在左，左部分在右
 * 举例：
 * arr = {abcdef}
 * left = {abc}
 * right = {def}
 * 要求返回newArr = {defabc}
 * 解题思路：
 * 需要使用三反转的思路，将left部分逆序 = {cba}，right部分逆序 = {fed}，最后整体逆序 = {defabc}
 * <p>
 * 该题目的解题思路：
 * 下标循环怼，下标连续推
 * 两个公式：
 * 1.当i % 2 == 0时，i来到i * 2的位置
 * 2.当i % 2 != 0时，i来到(i - arr.length / 2) * 2 - 1
 * 举例：
 * arr = {1, 2, 3, 4, 5, 6}
 * new = {4, 1, 5, 2, 6, 3}
 * 第1步：1位置来到2位置，2位置来到4位置，4位置回到1位置
 * 第2步：3位置来到6位置，6位置来到5位置，5位置回到3位置
 * 所以需要方法知道1位置和3位置这两个环的出发点
 * 数组长度N = 3的K次方 - 1，比如N = 2， N = 8， N =26
 * 那么当数组长度为3的K次方 - 1时，环的出发点3的K - 1次方，也就是1位置
 * 那么当数组长度为3的K次方 - 2时，环的出发点3的K - 2次方，也就是1，3位置
 * 那么当数组长度为3的K次方 - 3时，环的出发点3的K - 3次方，也就是1，3，9位置
 * <p>
 * 解题过程：
 * 1.N = 12，查看N距离哪个3的K次方 - 1最近，距离3的2次方 - 1 = 8最近
 * arr = {L1L2L3L4L5L6 | R1R2R3R4R5R6}
 * 按照8 / 2的长度取出左部分L1L2L3L4 | L5L6R1R2R3R4这一部分进行三反转 -> R1R2R3R4L5L6 | R5R6
 * 按照8 / 2的长度取出右部分R1R2R3R4
 * 所以左边部分形成3的2次方 - 1 == 8的长度数组：L1L2L3L4R1R2R3R4，剩余部分：L5L6R5R6，继续查看距离哪个3的K次方 - 1最近
 * <p>
 * 时间复杂度：
 * 1.以log以3为底N为真数的时间复杂度去查找数组长度N距离哪个3的K次方 - 1最接近
 * 2.3的K次方 - 1有2， 8， 26， 80
 * 假设N = 152
 * 第一步：
 * 那么log以3为底N为真数的时间复杂度去查找数组长度N距离哪个3的K次方 - 1最接近，log以3为底152为真数的时间复杂度
 * 找到组长度N距离哪个3的K次方 - 1最接近，80，O(80)的时间复杂度
 * 并且80长度的数组进行了逆序，O(80)的时间复杂度
 * 第二步：
 * 还剩下72的长度，那么log以3为底N为真数的时间复杂度去查找数组长度N距离哪个3的K次方 - 1最接近，log以3为底72为真数的时间复杂度
 * 找到组长度N距离哪个3的K次方 - 1最接近，26，O(26)的时间复杂度
 * 并且26长度的数组进行了逆序，O(26)的时间复杂度
 * 第三步：
 * 还剩下46的长度，那么log以3为底N为真数的时间复杂度去查找数组长度N距离哪个3的K次方 - 1最接近，log以3为底46为真数的时间复杂度
 * 找到组长度N距离哪个3的K次方 - 1最接近，26，O(26)的时间复杂度
 * 并且26长度的数组进行了逆序，O(26)的时间复杂度
 * 第四步：
 * 还剩下20的长度，那么log以3为底N为真数的时间复杂度去查找数组长度N距离哪个3的K次方 - 1最接近，log以3为底20为真数的时间复杂度
 * 找到组长度N距离哪个3的K次方 - 1最接近，8，O(8)的时间复杂度
 * 并且8长度的数组进行了逆序，O(8)的时间复杂度
 * 可以看出前面都是小阶，后面都是大阶，都是以3为底的等比数列
 * 最终的时间复杂度：O(N)
 * <p>
 * Leetcode与完美洗牌问题相关的题目：
 * https://leetcode.cn/problems/wiggle-sort-ii/
 *
 * @author xcy
 * @date 2022/8/13 - 8:33
 */
public class Code03_ShuffleProblem {
	public static void main(String[] args) {
		for (int i = 0; i < 5000000; i++) {
			int[] arr = generateArray();
			wiggleSort(arr);
			if (!isValidWiggle(arr)) {
				System.out.println("ooops!");
				printArray(arr);
				break;
			}
		}
	}

	/**
	 * 主函数
	 *
	 * @param arr 数组不能为空，并且数组的长度为偶数
	 */
	public static void shuffle(int[] arr) {
		if (arr == null || arr.length == 0 || (arr.length & 1) != 0) {
			return;
		}
		shuffle(arr, 0, arr.length - 1);
	}

	/**
	 * 在arr[]中的L ~ R范围上进行完美洗牌的调整，并且L ~ R范围必须是偶数个元素
	 *
	 * @param arr 原始数组
	 * @param L   L
	 * @param R   R
	 */
	public static void shuffle(int[] arr, int L, int R) {
		//arr[]中L ~ R范围内的元素个数
		while (R - L + 1 > 0) {
			int len = R - L + 1;
			//表示3的K次方，一开始是3的1次方
			int base = 3;
			int K = 1;

			//根据len找到距离最近的3的K次方 - 1的数
			//计算小于等于len并且是离len最近的，满足(3 ^ k) - 1的数
			//也就是找到最大的k，满足3 ^ k <= len + 1
			while (len >= 3 * base - 1) {
				base *= 3;
				K++;
			}
			//当前能够解决的3的K次方 - 1，除以2，就是左部分先拼接的部分
			int half = (base - 1) / 2;
			int mid = L + ((R - L) >> 1);

			//要进行旋转的左部分：L + half ~ mid
			//要进行旋转的右部分：mid + 1 ~ mid + half
			//arr下标是从0开始的
			rotate(arr, L + half, mid, mid + half);
			//旋转完成之后，从L开始计算，长度为base - 1的部分进行下标连续推
			cycles(arr, L, base - 1, K);
			//解决了base - 1的部分，剩余的部分继续下标连续推
			L = L + base - 1;
		}
	}

	/**
	 * 从start位置出发，往右len的长度这一段，进行下标连续推
	 *
	 * @param arr   原始数组
	 * @param start 从 start位置出发
	 * @param len   len的长度
	 * @param K     K
	 */
	public static void cycles(int[] arr, int start, int len, int K) {
		//trigger表示每一个环的出发点，一共K个
		//每一个trigger都进行下标连续推
		//出发点是从1开始的，而数组下标是从0开始的
		for (int i = 0, trigger = 1; i < K; i++, trigger *= 3) {
			//index原来的位置的值
			int preValue = arr[trigger + start - 1];
			//index需要去的位置
			int curIndex = modifyIndex2(trigger, len);
			while (curIndex != trigger) {
				int temp = arr[curIndex + start - 1];
				arr[curIndex + start - 1] = preValue;
				preValue = temp;
				curIndex = modifyIndex2(curIndex, len);
			}
			arr[curIndex + start - 1] = preValue;
		}
	}

	/**
	 * index不从0开始，index从1开始
	 * 如果index <= length / 2，那么index调整之后的位置是index * 2的位置
	 * 如果Index > length / 2，那么index调整之后的位置是(index - (length / 2)) * 2 - 1的位置
	 * 举例：
	 * arr[] = {1, 2, 3, 4, 5, 6, 7, 8}
	 * index =  5, 1, 6, 2, 7, 3, 8, 4
	 * 当index <= 8 / 2时, 1来到2,2来到4,3来到6,4来到8
	 * 当index >  8 / 2时，5来到1,6来到3,7来到5,8来到7
	 *
	 * @param index  index不从0开始，index从1开始
	 * @param length 数组的长度
	 * @return 返回index调整之后的位置
	 */
	public static int modifyIndex(int index, int length) {
		if (index <= length / 2) {
			return 2 * index;
		} else {
			return 2 * (index - (length / 2)) - 1;
		}
	}

	/**
	 * @param i   index不从0开始，index从1开始
	 * @param len 数组的长度
	 * @return 返回index调整之后的位置
	 */
	public static int modifyIndex2(int i, int len) {
		return (2 * i) % (len + 1);
	}

	/**
	 * 该题目的原型：
	 * 给定一个长度为N的数组arr，已知左部分为L ~ M，右部分为M ~ R
	 * 不使用辅助数组，将原始数组调整为右部分在左，左部分在右
	 * 举例：
	 * arr = {abcdef}
	 * left = {abc}
	 * right = {def}
	 * 要求返回newArr = {defabc}
	 *
	 * @param arr 原始数组
	 * @param L   左部分的左边界
	 * @param M   左部分的右边界
	 * @param R   右部分的右边界
	 */
	public static void rotate(int[] arr, int L, int M, int R) {
		//左部分逆序
		reverse(arr, L, M);
		//右部分逆序
		reverse(arr, M + 1, R);
		//整体逆序
		reverse(arr, L, R);
	}

	/**
	 * 将arr[]L ~ R范围上进行逆序操作
	 *
	 * @param arr 原始数组
	 * @param L   左边界
	 * @param R   右边界
	 */
	public static void reverse(int[] arr, int L, int R) {
		while (L < R) {
			int temp = arr[L];
			arr[L++] = arr[R];
			arr[R--] = temp;
		}
	}

	// for test
	public static int[] generateArray() {
		int len = (int) (Math.random() * 10) * 2;
		int[] arr = new int[len];
		for (int i = 0; i < len; i++) {
			arr[i] = (int) (Math.random() * 100);
		}
		return arr;
	}

	public static void wiggleSort(int[] arr) {
		if (arr == null || arr.length == 0) {
			return;
		}
		// 假设这个排序是额外空间复杂度O(1)的，当然系统提供的排序并不是，你可以自己实现一个堆排序
		Arrays.sort(arr);
		if ((arr.length & 1) == 1) {
			shuffle(arr, 1, arr.length - 1);
		} else {
			shuffle(arr, 0, arr.length - 1);
			for (int i = 0; i < arr.length; i += 2) {
				int tmp = arr[i];
				arr[i] = arr[i + 1];
				arr[i + 1] = tmp;
			}
		}
	}

	// for test
	public static boolean isValidWiggle(int[] arr) {
		for (int i = 1; i < arr.length; i++) {
			if ((i & 1) == 1 && arr[i] < arr[i - 1]) {
				return false;
			}
			if ((i & 1) == 0 && arr[i] > arr[i - 1]) {
				return false;
			}
		}
		return true;
	}

	// for test
	public static void printArray(int[] arr) {
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}
}
