package com.mashibing;

import com.mashibing.common.BFPRTUtils;

import java.util.Arrays;

/**
 * BFPRT算法
 * <p>
 * BFPRT算法的过程：
 * 1.数组的元素个数为N，将数组按照M个进行划分，划分为N / M个小组
 * 2.在N / M个小组中，每一个小组进行排序，每一个小组排序的时间复杂度为O(1)，一共有N / M个小组，
 * 总共时间复杂度O(N / M) -> O(N)
 * 3.取出每一个小组的中位数，组成新的数组array，如果小组元素个数为偶数，取出左中位数
 * 举例：小组的数组arr = {1, 4, 7, 10}，左中位数就是4
 * 4.取出所有小组中位数组成新数组array的中位数P --> BFPRT(array, N / (M * 2))
 * 5.估计在整个数组中，小于P的最多有多少个，大于P的最多有多少个
 * 1)在整个数组中，没有办法直接估计最多有多少个数小于中位数P，但是可以估计至少有多少个数大于等于中位数P的
 * 举例：
 * [a, b, c, d, e] 中位数-> c
 * [f, g, h, i, j] 中位数-> h
 * [k, l, m, n, o] 中位数-> m
 * [p, q, r, s, t] 中位数-> r
 * [u, v, w, x, y] 中位数-> w
 * 数组按照M = 5进行划分，一共有N / 5 个数组，选取出每一个小组的中位数组成新的数组arr = {c, h, m, r, w}
 * 假设数组arr的中位数是m，那么在小组中，大于等于m的数一共有N / (5 * 2) -> N / 10个  m就是中位数P
 * 那么再整个数组中，其它小组中的r和w肯定大于m，多出来 2 * N / 10个
 * 所以在整个数组中，大于等于中位数P的至少有3*N / 10，那么最多小于P的右7*N / 10
 * 2)在整个数组中，没有办法直接估计最多有多少个数大于中位数P，但是可以估计至少有多少个数小于等于中位数P的
 * 举例：
 * [a, b, c, d, e] 中位数-> c
 * [f, g, h, i, j] 中位数-> h
 * [k, l, m, n, o] 中位数-> m
 * [p, q, r, s, t] 中位数-> r
 * [u, v, w, x, y] 中位数-> w
 * 数组按照M = 5进行划分，一共有N / 5 个数组，选取出每一个小组的中位数组成新的数组arr = {c, h, m, r, w}
 * 假设数组arr的中位数是m，那么在小组中，小于等于m的数一共有N / (5 * 2) -> N / 10个  m就是中位数P
 * 那么再整个数组中，其它小组中的c和h肯定小于m，多出来 2*N / 10个
 * 所以在整个数组中，小于等于中位数P的至少有3*N / 10，那么最多大于P的右7*N / 10
 * <p>
 * BFPRT算法的精髓：
 * 1.为什么精挑细选的选择中位数P，保证了每一次递归至少淘汰掉3*N / 10
 * <p>
 * BFPRT算法的时间复杂度：T(N) = T(N / 5) + T(7*N / 10) + O(N)
 * 1.数组按照指定长度划分小组，因为只是逻辑概念，所以时间复杂度：O(1)
 * 2.每一个小组内排序，所有小组都进行排序，时间复杂度：O(N)
 * 3.将每一个小组的中位数取出，组成新的数组M，时间复杂度：O(N)
 * 4.BFPRT(M)，数据规模：T(N / 5)，
 * 5.计算出最终的中位数P，小于P的左侧，等于P的中间，大于P的右侧，时间复杂度：O(N)
 * 6.如果命中就停止，如果没有命中，即使是最差情况，数据规模:T(7*N / 10)
 * <p>
 * BFPRT算法的问题
 * 1.为什么选择5个数作为数组分小组的长度？因为是5个人发明的，所以他们喜欢
 * 2.
 * <p>
 * BFPRT算法的思想：
 * 1.BFPRT算法在整个算法中的地位是很高的
 * 2.
 * - 尽量选择一种平庸或者平凡的分界 去优化整个行为
 * - 虽然随机选择P，数学上证明了最后能够收敛到时间复杂度：O(N)
 * - 如果不想随机选择，那么需要选能够确定的淘汰掉一定比例特殊的划分值，
 * - 进而能够规避掉最差的情况，使得算法拥有严格的优秀时间复杂度，这是BFPRT算法带给整个算法发展最重要的思想
 *
 * @author xcy
 * @date 2022/5/22 - 10:14
 */
public class BFPRTAlgorithm {
	public static void main(String[] args) {
		/*int arrayMaxSize = 100000000;
		int maxValue = 10000000;
		int[] arr = BFPRTUtils.generateRandomArray(arrayMaxSize, maxValue);
		System.out.println(minKth(arr, 100));*/

		int[] array = {100, -100, 20, 0, 280, 20, 30, -10, -20, 50};
		for (int i = 1; i < array.length; i++) {
			for (int j = i - 1; j >= 0 && array[j] > array[j + 1]; j--) {
				swap(array, j, j + 1);
			}
		}
		System.out.println(Arrays.toString(array));
	}

	/**
	 * @param arr 原始数组
	 * @param K   数组中第K个元素
	 * @return 返回第K小的数
	 */
	public static int minKth(int[] arr, int K) {
		if (arr == null || arr.length == 0 || K < 1) {
			return -1;
		}
		return BFPRT(arr, 0, arr.length - 1, K - 1);
	}

	/**
	 * BFPRT算法
	 *
	 * @param arr   原始数组
	 * @param left  左边界
	 * @param right 右边界
	 * @param index 原始数组的索引
	 * @return 返回第K小的数
	 */
	public static int BFPRT(int[] arr, int left, int right, int index) {
		if (left == right) {
			return arr[left];
		}
		//中位数的中位数
		//1.将数组按照5的长度分成N/5个小组
		//2.每个小组都进行排序
		//3.取出每个小组的中位数，组成数组mArr
		//4.取出mArr数组的中位数并返回
		int pivot = medianOfMedians(arr, left, right);
		//等于区
		int[] equalArea = partition(arr, left, right, pivot);
		if (index >= equalArea[0] && index <= equalArea[1]) {
			return arr[index];
		} else if (index < equalArea[0]) {
			return BFPRT(arr, left, equalArea[0] - 1, index);
		} else {
			return BFPRT(arr, equalArea[1] + 1, right, index);
		}
	}

	/**
	 * @param arr   所有小组的中位数组成的中位数数组
	 * @param left  左边界
	 * @param right 右边界
	 * @return 返回中位数数组的中位数
	 */
	private static int medianOfMedians(int[] arr, int left, int right) {
		//中位数数组的长度
		int size = right - left + 1;
		//中位数数组的长度偏移量
		int offset = size % 5 == 0 ? 0 : 1;
		//每5个元素分为一组，取出所有小组的中位数组成的中位数数组
		int[] mArr = new int[size / 5 + offset];
		//遍历数组的每一个元素
		for (int team = 0; team < mArr.length; team++) {
			//每一个元素的起始位置
			int teamFirst = left + team * 5;
			//如果teamLast + 4越界，也就是大于right，那么取right和teamFirst + 4的最小值
			//left ... left + 4
			//left + 5 ... left + 9
			//left + 10 ... left + 14
			mArr[team] = getMedian(arr, teamFirst, Math.min(right, teamFirst + 4));
		}
		//返回中位数数组mArr的中位数，从0开始到mArr.length - 1结束，中位数的索引为mArr.length / 2
		return BFPRT(mArr, 0, mArr.length - 1, mArr.length / 2);
	}

	/**
	 * @param arr   所有小组的中位数组成的中位数数组
	 * @param left  左边界
	 * @param right 右边界
	 * @return 返回由所有小组的中位数组成的中位数
	 */
	private static int getMedian(int[] arr, int left, int right) {
		insertSort(arr, left, right);
		return arr[left + ((right - left) / 2)];
	}

	/**
	 * 划分小于区、等于区、大于区
	 *
	 * @param arr   原始数组
	 * @param left  左边界
	 * @param right 右边界
	 * @param pivot 划分值的索引
	 * @return 返回等于区的数组，包含等于区的左边界和右边界两个元素
	 */
	private static int[] partition(int[] arr, int left, int right, int pivot) {
		if (left > right) {
			return new int[]{-1, -1};
		}
		if (left == right) {
			return new int[]{left, right};
		}
		//小于区的右边界，起始位置left - 1
		int lessAreaRight = left - 1;
		//大于区的左边界，起始位置right + 1
		int greatorAreaLeft = right + 1;
		//当前索引，起始位置left
		int index = left;
		//遍历当前索引 == 大于区的左边界为止
		while (index < greatorAreaLeft) {
			//如果当前索引的值 大于 划分值
			if (arr[index] > pivot) {
				//当前索引上的值和大于区的左边界的前一个索引上的值进行交换
				swap(arr, index, --greatorAreaLeft);

				//swap(arr, index, greatorAreaLeft - 1);
				//大于区的左边界往左扩
				//greatorAreaLeft--;
			}
			//如果当前索引上的值 小于 划分值
			else if (arr[index] < pivot) {
				//当前索引上的值和小于区的右边界的下一个索引上的值进行交换
				swap(arr, index++, ++lessAreaRight);

				//swap(arr, index, lessAreaRight + 1);
				//小于区的右边界往右扩
				//lessAreaRight++;
				//当前索引来到下一个索引
				//index++;
			}
			//如果当前索引上的值 等于 划分值
			else {
				//说明index在等于区内，继续往下一个索引遍历
				index++;
			}
		}
		//等于区的左边界：小于区的右边界的下一个索引
		//等于区的右边界：大于区的左边界的前一个索引
		return new int[]{lessAreaRight + 1, greatorAreaLeft - 1};
	}

	/**
	 * 插入排序，常数时间最快
	 *
	 * @param arr   所有小组的中位数组成的中位数数组
	 * @param left  左边界
	 * @param right 右边界
	 */
	private static void insertSort(int[] arr, int left, int right) {
		for (int i = left + 1; i <= right; i++) {
			for (int j = i - 1; j >= left && arr[j] > arr[j + 1]; j--) {
				swap(arr, j, j + 1);
			}
		}
	}


	/**
	 * 在数组中交换两个索引上的值
	 *
	 * @param arr
	 * @param i
	 * @param j
	 */
	public static void swap(int[] arr, int i, int j) {
		int temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}
}
