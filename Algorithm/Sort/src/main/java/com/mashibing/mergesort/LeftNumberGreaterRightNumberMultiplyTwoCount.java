package com.mashibing.mergesort;

/**
 * 归并排序的衍生问题：数组中当前左边的数大于右边的数 * 2的个数
 *
 * @author xcy
 * @date 2022/4/17 - 9:46
 */
public class LeftNumberGreaterRightNumberMultiplyTwoCount {
	public static void main(String[] args) {
		int[] arr = {7,3,5,6,3,2,6,5};
		int count = leftNumberGreaterRightNumberMultiplyTwoCount(arr);
		System.out.println(count);
	}

	/**
	 * 记录当前数组中当前左边的元素大于右边元素*2的个数
	 *
	 * @param arr
	 * @return
	 */
	public static int leftNumberGreaterRightNumberMultiplyTwoCount(int[] arr) {
		if (arr == null || arr.length < 2) {
			return 0;
		}
		return mergeSort(arr, 0, arr.length - 1);
	}

	/**
	 * 归并排序
	 *
	 * @param arr 原始数组
	 * @param left 原始左边数组
	 * @param right 原始右边数组
	 * @return
	 */
	private static int mergeSort(int[] arr, int left, int right) {
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
	 * 合并
	 *
	 * @param arr 原始无序数组
	 * @param left 左边有序数组的左边界
	 * @param mid 左边有序数组的右边界
	 * @param right 右边有序数组的右边界
	 * @return
	 */
	public static int merge(int[] arr, int left, int mid, int right) {
		//不回退，因为左边数组和右边数组都是有序的，时间复杂度：O(N)
		//记录当前左边数组中大于右边数组中数值 * 2的数的个数
		int count = 0;
		//当前右边数组的左边界，也就是起始位置索引，左闭右开[mid+1, R)
		int R = mid + 1;
		//当前左边数组从left开始，到mid结束
		for (int i = left; i <= mid; i++) {
			//R <= right表示当前右边数组从R开始到right结束
			//arr[left] > arr[R] * 2
			while (R <= right && arr[i] > arr[R] * 2) {
				R++;
			}
		}
		count += R - (mid + 1);

		//拷贝数组
		int[] temp = new int[right - left + 1];
		//拷贝数组的索引
		int index = 0;

		int cur1 = left;
		int cur2 = mid + 1;
		while (cur1 <= mid && cur2 <= right) {
			temp[index++] = arr[cur1] < arr[cur2] ? arr[cur1++] : arr[cur2++];
		}

		while (cur1 <= mid) {
			temp[index++] = arr[cur1++];
		}

		while (cur2 <= right) {
			temp[index++] = arr[cur2++];
		}

		for (index = 0; index < temp.length; index++) {
			arr[left + index] = temp[index];
		}

		return count;
	}
}
