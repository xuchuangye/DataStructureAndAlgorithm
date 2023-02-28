package com.mashibing.day01;

import com.mashibing.common.TestUtils;

import java.util.Arrays;

/**
 * 题目一：
 * 给定一个有序数组arr，代表坐落在X轴上的点，给定一个正数K，代表绳子的长度，返回绳子最多压中几个点？
 * 注意：即使绳子边缘处盖住点也算盖住
 * <p>
 * 解题思路：
 * 1.贪心算法
 * 绳子的末尾没有必要压在arr[]中不存在的位置(元素)上
 * arr[] = {1,  3,  4,  7, 13, 16, 17}  length = 4
 * index =  0,  1,  2,  3,  4,  5,  6
 * 绳子以0位置为末尾，0到0位置的距离是0，最多能盖住1个点
 * 绳子以1位置为末尾，0到1位置的距离是3 - 1 = 2，最多能盖住2个点
 * 绳子以2位置为末尾，0到2位置的距离是4 - 1 = 3，最多能盖住3个点
 * 绳子以3位置为末尾，1到3位置的距离是7 - 3 = 4，最多能盖住3个点
 * 时间复杂度：O(N * logN)
 * 2.
 * arr[] = {1,  4,  5,  6,  9, 10, 12, 17}  length = 4
 * index =  0,  1,  2,  3,  4,  5,  6,  7
 * 第一次：
 * L和R同时来到0的位置，0到0位置的距离是0，没有超出length，R继续往右移动
 * R来到1的位置，0到1位置上的距离是4 - 1 = 3，没有超出length，R继续往右移动
 * R来到2的位置，0到2位置上的距离是5 - 1 = 4，没有超出length，R继续往右移动
 * 当R来到3的位置时，0到3位置上的距离是6 - 1 = 5，超出length，记录以0位置开始往右绳子能够覆盖的点数：0位置，1位置，2位置，一共3个点
 * 第二次：
 * L来到1的位置，R在原来2的位置不变，1到2位置的距离是5 - 4 = 1，没有超出length，R继续往右移动
 * R来到3的位置，1到3位置上的距离是6 - 4 = 2，没有超出length，R继续往右移动
 * 当R来到4的位置时，1到4位置上的距离是9 - 4 = 5，超出length，记录以1位置开始往右绳子能够覆盖的点数：1位置，2位置，3位置，一共3个点
 * 依此类推，可以看出L和R永远不回退的解法，一个窗口搞定，所以时间复杂度：O(N)
 *
 * @author xcy
 * @date 2022/7/2 - 8:26
 */
public class Code01_CordCoverMaxPoint {
	public static void main(String[] args) {
		/*int[] arr = {100, 10, 90, 40, 60, 70};
		int length = 50;
		Arrays.sort(arr);
		int count1 = maxPoint1(arr, length);
		int count2 = maxPoint2(arr, length);
		System.out.println(count1 == count2);*/

		int len = 100;
		int max = 1000;
		int testTime = 100000;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int L = (int) (Math.random() * max);
			int[] arr = TestUtils.generateArray(len, max);
			int ans1 = maxPoint1(arr, L);
			int ans2 = maxPoint2(arr, L);
			int ans3 = TestUtils.test(arr, L);
			if (ans1 != ans2 || ans2 != ans3) {
				System.out.println("oops!");
				break;
			}
		}
		System.out.println("测试结束");
	}

	/**
	 * 使用贪心算法：以数组每个元素结尾往左能够覆盖多少个点
	 * 时间复杂度：O(N * logN)
	 * @param arr
	 * @param length
	 * @return
	 */
	public static int maxPoint1(int[] arr, int length) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		int res = 1;
		for (int i = 0; i < arr.length; i++) {
			int nearest = nearestIndex(arr, i, arr[i] - length);
			res = Math.max(res, i - nearest + 1);
		}
		return res;
	}

	/**
	 * 二分查找算法
	 * @param arr
	 * @param right
	 * @param value
	 * @return
	 */
	public static int nearestIndex(int[] arr, int right, int value) {
		int left = 0;
		int index = right;
		while (left <= right) {
			int mid = left + ((right - left) >> 1);
			if (arr[mid] >= value) {
				index = mid;
				right = mid - 1;
			} else {
				left = mid + 1;
			}
		}
		return index;
	}

	/**
	 * 使用滑动窗口：L和R永远不回退
	 * 时间复杂度：O(N)
	 * @param arr
	 * @param length
	 * @return
	 */
	public static int maxPoint2(int[] arr, int length) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		int left = 0;
		int right = 0;
		int max = 0;
		while (left < arr.length) {
			//right < arr.length表示right不能越界
			// arr[right] - arr[left] < length表示绳子的长度不能超过length
			while (right < arr.length && arr[right] - arr[left] <= length) {
				right++;
			}
			//此时的arr[right] - arr[left] > length,right已经++过了
			//所以本来是right - left需要 + 1，现在不需要了
			max = Math.max(max, right - left);
			left++;
		}
		return max;
	}
}
