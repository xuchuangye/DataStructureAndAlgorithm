package com.mashibing;

import com.mashibing.common.BFPRTUtils;
import com.mashibing.heap.HeapGreator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * 给定一个无序数组arr中，长度为N，给定一个正数K，返回top K个最大的数
 * 不同时间复杂度三个方法：
 * 1）O(N * logN)
 * 2）O(N + K * logN) 加强堆 + 堆中弹出K个最大值
 * 3）O(N + K * logK)
 *
 * @author xcy
 * @date 2022/5/22 - 15:36
 */
public class MaxTopK {
	public static void main(String[] args) {
		int arrayMaxSize = 10000000;
		int maxValue = 1000000;
		int[] arr = BFPRTUtils.generateRandomArray(arrayMaxSize, maxValue);
		int[] array1 = returnArrayMaxTopK1(arr, 10);
		int[] array2 = returnArrayMaxTopK2(arr, 10);
		int[] array3 = returnArrayMaxTopK3(arr, 10);
		System.out.println(Arrays.toString(array1));
		System.out.println(Arrays.toString(array2));
		System.out.println(Arrays.toString(array3));
	}

	/**
	 * 数组进行排序，然后使用额外的数组(长度为K)收集
	 * <p>
	 * 时间复杂度：O(N * logN)
	 *
	 * @param arr
	 * @param K
	 * @return
	 */
	public static int[] returnArrayMaxTopK1(int[] arr, int K) {
		if (arr == null || arr.length == 0 || K < 1) {
			return null;
		}
		K = Math.min(arr.length, K);
		Arrays.sort(arr);
		int[] ans = new int[K];
		for (int i = arr.length - 1, j = 0; j < K; i--, j++) {
			ans[j] = arr[i];
		}
		return ans;
	}

	/**
	 * 将数组所有的元素都放入加强堆，时间复杂度：O(N)，每次取出堆顶元素的时间复杂度：O(logN)，一共取出K次，时间复杂度：O(K * logN)
	 * <p>
	 * 时间复杂度：O(N + K * logN)
	 *
	 * @param arr
	 * @param K
	 * @return
	 */
	public static int[] returnArrayMaxTopK2(int[] arr, int K) {
		if (arr == null || arr.length == 0 || K < 1) {
			return null;
		}
		return coreLogic(arr, K);
	}

	/**
	 * 升序
	 */
	public static class DescendingOrder implements Comparator<Integer> {
		@Override
		public int compare(Integer o1, Integer o2) {
			return o2 - o1;
		}
	}

	/**
	 * 核心逻辑
	 *
	 * @param arr
	 * @param K
	 * @return
	 */
	public static int[] coreLogic(int[] arr, int K) {
		int[] ans = new int[K];
		int index = 0;
		//创建加强堆
		HeapGreator<Integer> heapGreator = new HeapGreator<>(new DescendingOrder());
		for (int value : arr) {
			heapGreator.push(value);
		}
		for (int i = 0; i < K; i++) {
			ans[index++] = heapGreator.pop();
		}
		return ans;
	}

	/**
	 * 求第N - K小的数，就可以得到第K大的数，时间复杂度：O(N)
	 * 遍历数组arr，准备长度为K的数组ans，只要比K大的数就添加到arr中，，时间复杂度：O(K)
	 * 长度为K的数组ans需要进行从大到小的排序，所以时间复杂度：O(logK)
	 * <p>
	 * 时间复杂度：O(N + K * logK)
	 *
	 * @param array
	 * @param K
	 * @return
	 */
	public static int[] returnArrayMaxTopK3(int[] array, int K) {
		int[] arr = BFPRTUtils.copyArray(array);
		K = Math.min(arr.length, K);
		int[] ans = new int[K];
		int index = 0;
		//第arr.length - K小的数就是第K大的数
		int num = minKth(arr, arr.length - K);
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] > num) {
				ans[index++] = arr[i];
			}
		}
		while (index < K) {
			ans[index] = num;
			index++;
		}
		//O(k * logK)
		Arrays.sort(ans);
		for (int left = 0, right = K - 1; left < right; left++, right--) {
			swap(arr, left, right);
		}
		return ans;
	}

	/**
	 *
	 * @param arr
	 * @param index
	 * @return 返回第N - K小的数，也就是第K大的数
	 */
	public static int minKth(int[] arr, int index) {
		int left = 0;
		int right = arr.length - 1;
		while (left < right) {
			int pivot = arr[left + (int) (Math.random() * (right - left + 1))];
			int[] equalRegion = partition(arr, left, right, pivot);
			if (index < equalRegion[0]) {
				right = equalRegion[0] - 1;
			} else if (index > equalRegion[1]) {
				left = equalRegion[1] + 1;
			} else {
				return pivot;
			}
		}
		return arr[left];
	}

	/**
	 * @param arr
	 * @param left
	 * @param right
	 * @param pivot
	 * @return
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
