package com.mashibing;

import com.mashibing.common.BFPRTUtils;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 在无序数组中求第K小的数
 *
 * @author xcy
 * @date 2022/5/22 - 8:25
 */
public class FindArrayMinKth {
	public static void main(String[] args) {
		int testTime = 100000;
		int maxSize = 100;
		int maxValue = 100;
		System.out.println("测试开始！");
		for (int i = 0; i < testTime; i++) {
			int[] arr = BFPRTUtils.generateRandomArray(maxSize, maxValue);
			int k = (int) (Math.random() * arr.length) + 1;
			int ans1 = minKthWithGreatorHeap(arr, k);
			int ans2 = minKthWithRewriteQuickSort(arr, k);
			int ans3 = BFPRTAlgorithm.minKth(arr, k);
			if (ans1 != ans2 || ans2 != ans3) {
				System.err.println("测试出错！");
			}
		}
		System.out.println("测试结束！");
	}

	public static class MaxHeapComparator implements Comparator<Integer> {
		@Override
		public int compare(Integer o1, Integer o2) {
			return o2 - o1;
		}
	}

	// 利用大根堆，时间复杂度O(N*logK)
	public static int minKthWithGreatorHeap(int[] arr, int k) {
		PriorityQueue<Integer> maxHeap = new PriorityQueue<>(new MaxHeapComparator());
		for (int i = 0; i < k; i++) {
			maxHeap.add(arr[i]);
		}
		for (int i = k; i < arr.length; i++) {
			if (arr[i] < maxHeap.peek()) {
				maxHeap.poll();
				maxHeap.add(arr[i]);
			}
		}
		return maxHeap.peek();
	}

	/**
	 * 使用改写快速排序的方式
	 * <p>
	 * 时间复杂度：O(N)
	 * <p>
	 * 最好时间复杂度：O(N²)
	 * 最差时间复杂度：O(N)
	 * 等概率出现，所以时间复杂度：O(N)
	 *
	 * @param array 无序数组
	 * @param K     是从1开始的，而索引是从0开始的，所以K - 1
	 * @return
	 */
	public static int minKthWithRewriteQuickSort(int[] array, int K) {
		int[] arr = BFPRTUtils.copyArray(array);
		return process(arr, 0, arr.length - 1, K - 1);
	}

	/**
	 * 核心逻辑
	 *
	 * @param arr   无序数组
	 * @param left
	 * @param right
	 * @param index
	 * @return
	 */
	private static int process(int[] arr, int left, int right, int index) {
		if (left == right) {
			return arr[left];
		}
		//随机在left - right范围上选择一个索引作为快速排序的划分值
		int pivot = arr[left + (int) (Math.random() * (right - left + 1))];
		//返回等于区的数组[] = {等于区的左边界, 等于区的右边界}
		int[] equalRegion = partition(arr, left, right, pivot);
		//如果要查找的值就在等于区内，直接返回
		if (index >= equalRegion[0] && index <= equalRegion[1]) {
			return arr[index];
		}
		//如果要查找的值小于等于区的左边界，递归小于区
		else if (index < equalRegion[0]) {
			return process(arr, left, equalRegion[0] - 1, index);
		}
		//否则，递归大于区
		else {
			return process(arr, equalRegion[1] + 1, right, index);
		}
	}

	/**
	 * 划分小于区、等于区、大于区
	 *
	 * @param arr   无序数组
	 * @param left  左边界
	 * @param right 右边界
	 * @param pivot 中轴的值
	 * @return 返回数组，数组中的元素时小于区右边界的下一个索引，以及大于区左边界
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
	 * 在数组中，交换两个索引上的值
	 *
	 * @param arr 数组
	 * @param i   索引1
	 * @param j   索引2
	 */
	public static void swap(int[] arr, int i, int j) {
		int temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}
}
