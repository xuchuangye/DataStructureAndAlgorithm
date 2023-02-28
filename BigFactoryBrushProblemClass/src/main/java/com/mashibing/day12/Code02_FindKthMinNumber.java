package com.mashibing.day12;

/**
 * 题目二：
 * 给定两个大小分别为 m 和 n 的正序（从小到大）数组 nums1 和 nums2。请你找出并返回这两个正序数组的中位数
 * 进阶，在两个都有序的数组中找整体第K小的数，可以做到O(log(Min(M,N)))
 * <p>
 * 两种情况：
 * (一)
 * 情况一：如果是两个等长的数组，那么需要返回第(两个数组的长度之和 / 2)小就是两个有序数组合并之后的中位数
 * 又分为两种情况：
 * (1)
 * 小情况1：这两个等长的数组的长度都是偶数
 * arr1[] = {1, 2, 3, 4}，这些数字表示数组的第几个元素
 * arr2[] = {1',2',3',4'}
 * 两个数组的长度之和 / 2 = 8 / 2 = 4就是两个有序数组合并之后的中位数，也就是求整体的第4小
 * 那么有可能是第4小的有哪些？
 * <1>.
 * 2 == 2'，那么直接返回就是整体的第4小
 * <2>.
 * 2 > 2'，那么有可能是整体第4小的有：
 * arr1[]中的1 -> 大于arr2[]中的1' 2' 3'，本身有序的，肯定小于2
 * arr1[]中的2 -> 大于arr2[]中的1' 2' 不大于3'，本身有序的，肯定大于arr1[]中的1
 * arr2[]中的3' -> 大于arr1[]中的1 2 不大于3，本身有序的，肯定大于arr2[]中的1' 2'
 * arr2[]中的4' -> 小于arr1[]中所有的数，本身有序的，肯定大于arr2[]中的1' 2' 3'
 * 继续递归下去，f(arr1[]中的1和2, arr2[]中的3'和4')，找到其中的第2小
 * <3>.
 * 2 < 2'，那么有可能是整体第4小的有：
 * arr1[]中的3 -> 大于arr2[]中的1'，本身有序的，肯定大于arr[]1中的1 2
 * arr1[]中的4 -> 小于arr2[]中所有的数，本身有序的，肯定大于arr1[]中的1 2 3
 * arr2[]中的1' -> 大于arr1[]中的1 2 3，本身有序的，肯定小于arr2[]中的2'
 * arr2[]中的2' -> 大于arr1[]中的1 2，本身有序的，肯定大于arr2[]中的1'
 * 继续递归下去，f(arr1[]中的3和4, arr2[]中的1'和2')，找到其中的第2小
 * (2)
 * 小情况2：这两个等长的数组的长度都是奇数
 * arr1[] = {1, 2, 3, 4, 5}，这些数字表示数组的第几个元素
 * arr2[] = {1',2',3',4',5'}
 * 那么有可能是第5小的有哪些？
 * <1>.
 * 3 == 3，那么直接返回就是整体的第5小
 * <2>.
 * 3 > 3'，那么有可能是整体第5小的有：
 * arr1[]中的1 -> 大于arr2[]中的1' 2' 3' 4'，小于5'，
 * arr1[]中的2 -> 大于arr2[]中的1' 2' 3'，小于4'，本身有序的，肯定大于arr1[]中的1
 * arr2[]中的3' -> 大于arr1[]中的1 2，小于3，本身有序的，肯定大于arr2[]中的1' 2'
 * arr2[]中的4' -> 大于arr1[]中的1，小于2，本身有序的，肯定大于arr2[]中的1' 2' 3'
 * arr2[]中的5' -> 小于arr1[]中所有的数，本身有序的，肯定大于arr2[]中的1' 2' 3' 4'
 * 判断arr1[]中的2是否 <= arr2[]中的3'
 * a.如果arr1[]中的2 <= arr2[]中的3'，那么arr1[]中的2肯定是整体的第5小
 * b.如果arr1[]中的2 > arr2[]中的3'，那么arr2[]中的3'肯定不是整体的第5小，因为只大于arr1[]中的1和arr2[]中的1' 2'
 * 所以排除arr2[]中的3'，继续递归下去，f(arr1[]中的1和2，arr2[]中的4' 5')，找到其中的第2小
 * <3>.
 * 3 < 3'
 * (二)
 * 情况二：如果两个不等长的数组，那么需要返回第(两个数组的长度之和 / 2)小就是两个有序数组合并之后的中位数
 * 两个数组不相等，arr1[].length == N，arr2[].length == M，求第K小
 * (1)1 <= K <= Math.min(N, M)
 * arr1[]和arr2[]同时取出前Math.min(N, M)个值，求出第Math.min(N, M) / 2小就是整体的第Math.min(N, M) / 2小
 * (2)Math.max(N, M) < K <= N + M
 * 假设求出整体的第23小
 * arr1[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17}
 * arr2[] = {1',2',3',4',5',6',7',8',9',10'}
 * 那么不可能是第23小的有哪些？
 * arr1[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12}都不可能，
 * 因为arr1[]中最大的12即使是大于arr2[]中的10个数，再加上前面的11个数，最多也只是第22小
 * arr2[] = {1', 2', 3', 4', 5'}都不可能，
 * 因为arr2[]中最大的5即使是大于arr1[]中的17个数，再加上前面的4个数，最多也只是第22小
 * arr1[]剩余{13, 14, 15, 16, 17}
 * arr2[]剩余{6', 7', 8', 9', 10'}
 * arr1[]中去除12个最小的数，arr2[]中去除5个最小的数，求出此时arr1[]和arr2[]的第5小，也凑不够第23小
 * 所以应该判断，
 * 如果arr1[]中的第13个数大于等于arr2[]中的第10个数那么直接返回arr1[13]这个第23小，否则多淘汰掉这个arr1[13]
 * 如果arr2[]中的第6个数大于等于arr1[]中的第17个数，那么直接返回arr2[6']这个第23小，否则多淘汰掉这个arr2[6']
 * arr1[]剩余{14, 15, 16, 17}
 * arr2[]剩余{7', 8', 9', 10'}
 * arr1[]中去除13个最小的数，arr2[]中去除6个最小的数，求出此时arr1[]和arr2[]的第4小，刚好凑够第23小
 * <p>
 * (3)Math.min(N, M) < K <= Math.max(N, M)
 *
 * <p>
 * LeetCode测试链接：
 * https://leetcode.cn/problems/median-of-two-sorted-arrays/
 *
 * @author xcy
 * @date 2022/7/30 - 15:20
 */
public class Code02_FindKthMinNumber {
	public static void main(String[] args) {
		int[] arr1 = {4, 9, 10, 20, 15};
		int[] arr2 = {40, 9, 100, 20, 25};
		int kthNum1 = (int) findKthNum1(arr1, arr2);
		int kthNum2 = findKthNum2(arr1, arr2, 2);
		System.out.println(kthNum1);
		System.out.println(kthNum2);
	}

	/**
	 * 数据规模：
	 * nums1.length == m
	 * nums2.length == n
	 * 0 <= m <= 1000
	 * 0 <= n <= 1000
	 * 1 <= m + n <= 2000
	 * -10的6次方 <= nums1[i], nums2[i] <= 10的6次方
	 *
	 * @param arr1
	 * @param arr2
	 * @return
	 */
	public static double findKthNum1(int[] arr1, int[] arr2) {
		if (arr1 == null || arr2 == null || arr1.length != arr2.length) {
			return -1;
		}
		int N = arr1.length;
		return getUpMedian1(arr1, 0, N - 1, arr2, 0, N - 1);
	}

	/**
	 * LeetCode测试链接：
	 * https://leetcode.cn/problems/median-of-two-sorted-arrays/
	 * 给定两个大小分别为 N 和 M 的正序（从小到大）数组nums1 和nums2。请你找出并返回这两个正序数组的 中位数 。
	 * <p>
	 * 算法的时间复杂度应该为 O(log (N + M)) 。
	 *
	 * @param nums1 长度为N的数组
	 * @param nums2 长度为M的数组
	 * @return 返回两个数组合并之后整体的中位数
	 */
	public double findMedianSortedArrays(int[] nums1, int[] nums2) {
		if (nums1 == null || nums2 == null) {
			return 0.0;
		}
		int N = nums1.length;
		int M = nums2.length;
		int size = N + M;
		boolean even = (size & 1) == 0;
		if (nums1.length != 0 && nums2.length != 0) {
			if (even) {
				return (double) (findKthNum2(nums1, nums2, size / 2) + findKthNum2(nums1, nums2, size / 2 + 1)) / 2D;
			} else {
				return findKthNum2(nums1, nums2, size / 2 + 1);
			}
		} else if (nums1.length != 0) {
			if (even) {
				return (double) (nums1[(size - 1) / 2] + nums1[size / 2]) / 2D;
			} else {
				return nums1[size / 2];
			}
		} else {
			if (even) {
				return (double) (nums2[(size - 1) / 2] + nums2[size / 2]) / 2D;
			} else {
				return nums2[size / 2];
			}
		}
	}

	/**
	 * @param arr1 长度为N的数组
	 * @param arr2 长度为M的数组
	 * @param kth  第K小的数
	 * @return 返回在两个数组合并之后整体第K小的数
	 */
	public static int findKthNum2(int[] arr1, int[] arr2, int kth) {
		if (arr1 == null || arr2 == null || arr1.length == 0 || arr2.length == 0 || kth <= 0) {
			return -1;
		}
		int[] longs = arr1.length >= arr2.length ? arr1 : arr2;
		int[] shorts = arr1.length < arr2.length ? arr1 : arr2;
		//arr1[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17}
		int l = longs.length;//17
		//arr2[] = {1',2',3',4',5',6',7',8',9',10'}
		int s = shorts.length;//10
		//kth >= short
		//kth <= 10
		if (kth <= s) {
			//因为是索引的原因，所以kth需要 - 1
			return getUpMedian1(longs, 0, kth - 1, shorts, 0, kth - 1);
		}
		//long < kth <= (short + long)
		//17 < kth <= 27
		if (kth > l && kth <= (l + s)) {
			//arr1[] = 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17
			//arr2[] = 1',2',3',4',5',6',7',8',9',10'
			//arr1[13] >= arr2[10']
			if (longs[kth - s - 1] >= shorts[s - 1]) {
				return longs[kth - s - 1];
			}
			//arr2[6'] >= arr1[17]
			if (shorts[kth - l - 1] > longs[l - 1]) {
				return shorts[kth - l - 1];
			}
			return getUpMedian1(longs, kth - s, l - 1, shorts, kth - l, s - 1);
		}
		//short < kth <= long
		//10 < kth <= 17
		//假设kth == 15
		//arr1[] = {5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17}
		//arr2[] = 1',2',3',4',5',6',7',8',9',10'
		//arr1[5] >= arr2[10']
		if (longs[kth - s - 1] >= shorts[s - 1]) {
			return longs[kth - s - 1];
		}
		//arr1[] = {6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17}
		//arr2[] = 1',2',3',4',5',6',7',8',9',10'
		return getUpMedian1(longs, kth - s, kth - 1, shorts, 0, s - 1);
	}

	/**
	 * A[] = [s1 ... e1]
	 * B[] = [s2 ... e2]
	 * 两个数组的长度是相等的
	 * <p>
	 * 时间复杂度：o(logN)
	 *
	 * @param A  A[]
	 * @param s1 A[]左边界
	 * @param e1 A[]右边界
	 * @param B  B[]
	 * @param s2 B[]左边界
	 * @param e2 B[]右边界
	 * @return 返回两个数组合并之后整体
	 */
	public static int getUpMedian1(int[] A, int s1, int e1, int[] B, int s2, int e2) {
		int mid1 = 0;
		int mid2 = 0;
		while (s1 < e1) {
			mid1 = s1 + ((e1 - s1) >> 1);
			mid2 = s2 + ((e2 - s2) >> 1);
			//如果两个数组的中位数相等，那么必定是两个数组合并之后的整体的中位数
			if (A[mid1] == B[mid2]) {
				return A[mid1];
			}
			//判断数组的长度是否是偶数
			//如果数组的长度是奇数
			if (((e1 - s1 + 1) & 1) == 1) {
				//A[] = {1, 2, 3, 4, 5}
				//B[] = {1',2',3',4',5'}
				//A[mid1] != B[mid2]
				if (A[mid1] > B[mid2]) {
					if (B[mid2] >= A[mid1 - 1]) {
						return B[mid2];
					}
					//A[]的s1不动，e1移动到mid1 - 1的位置
					e1 = mid1 - 1;
					//B[]的e2不动，s2移动到mid2 + 1的位置
					s2 = mid2 + 1;
				}
				//A[mid1] < B[mid2]
				else {
					if (A[mid1] >= B[mid2 - 1]) {
						return A[mid1];
					}
					//A[]的e1不动，s1移动到mid1 + 1的位置
					s1 = mid1 + 1;
					//B[]的s2不动，e2移动到mid2 - 1的位置
					e2 = mid2 - 1;
				}
			}
			//如果数组的长度是偶数
			else {
				//A[] = {1, 2, 3, 4}
				//B[] = {1',2',3',4'}
				//A[mid1] != B[mid2]
				if (A[mid1] > B[mid2]) {
					//A[]的s1不动，e1移动到mid1的位置
					e1 = mid1;
					//B[]的e2不动，s2移动到mid2 + 1的位置
					s2 = mid2 + 1;
				} else {
					//A[]的e1不动，s1移动到mid1 + 1的位置
					s1 = mid1 + 1;
					//B[]的s2不动，e2移动到mid2的位置
					e2 = mid2;
				}
			}
		}
		return Math.min(A[s1], B[s2]);
	}
}
