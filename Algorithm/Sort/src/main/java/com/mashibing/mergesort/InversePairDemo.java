package com.mashibing.mergesort;

/**
 * 归并排序的衍生问题：逆序对问题
 *
 * 基本思路：
 * 1、归并排序将数组分为两组：左边数组和右边数组
 * 2、求出在右边数组中所有比当前左边数组元素小的元素的个数，最后合并
 * @author xcy
 * @date 2022/4/17 - 8:50
 */
public class InversePairDemo {
	public static void main(String[] args) {
		int[] arr = {2, 2, 3, 3, 4, 2, 3, 1};
		int count = inversePair(arr);
		System.out.println(count);
	}

	/**
	 * 逆序对问题
	 *
	 * @param arr 原始无序数组
	 * @return 返回数组中逆序对的个数
	 */
	public static int inversePair(int[] arr) {
		if (arr == null || arr.length < 2) {
			//有0个逆序对
			return 0;
		}

		return mergeSort(arr, 0, arr.length - 1);
	}

	/**
	 * 归并排序
	 *
	 * @param arr   原始无序数组
	 * @param left  原始无序数组的左边界
	 * @param right 原始无序数组的右边界
	 * @return
	 */
	public static int mergeSort(int[] arr, int left, int right) {
		if (left >= right) {
			return 0;
		}
		int mid = left + ((right - left) >> 1);
		return mergeSort(arr, left, mid)
				+
				mergeSort(arr, mid + 1, right)
				+
				merge(arr, left, mid, right);
	}

	/**
	 * @param arr 原始有序数组
	 * @param left 原始左边有序数组的左边界
	 * @param mid 原始左边有序数组的右边界
	 * @param right 原始右边有序数组的右边界
	 * @return
	 */
	public static int merge(int[] arr, int left, int mid, int right) {
		//记录逆序对的个数
		int count = 0;
		//拷贝数组
		int[] temp = new int[right - left + 1];
		//拷贝数组的索引，从temp.length - 1开始
		int index = temp.length - 1;
		//当前左边数组的右边界
		int cur1 = mid;
		//当前右边数组的右边界
		int cur2 = right;
		//逆序遍历
		while (cur1 >= left && cur2 >= (mid + 1)) {
			//count记录逆序对
			//如果左边数组的元素大于右边数组的元素，那么就查看右边数组中大于当前左边数组元素的元素的个数
			//如果左边数组的元素小于等于右边数组的元素，那么逆序对的个数 为0
			count += arr[cur1] > arr[cur2] ? (cur2 - (mid + 1) + 1) : 0;

			temp[index--] = arr[cur1] > arr[cur2] ? arr[cur1--] : arr[cur2--];
		}

		while (cur1 >= left) {
			temp[index--] = arr[cur1--];
		}

		while (cur2 >= (mid + 1)) {
			temp[index--] = arr[cur2--];
		}

		for (index = 0; index < temp.length; index++) {
			arr[left + index] = temp[index];
		}
		return count;
	}
}
