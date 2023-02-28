package com.mashibing.mergesort;

import com.mashibing.common.SortCommonUtils;

/**
 * 归并排序的衍生问题：小和问题
 * 在一个数组中，将每一个比当前数小的左边的数累加起来，叫做这个数组的小和。求一个数组的小和。
 * <p>
 * 解题思路：实际上是记录右边比当前数大的数的个数，当前数乘以个数的积进行累加
 * 1、使用归并排序将数组进行划分，划分为左边数组和右边数组
 * 2、当右边数组中的数值比左边数组中的数值大时，记录右边数组中大于左边
 * 数组中的数值个数，并且乘以左边数组中的数值得到的积，累加到小和中
 * 3、当右边数组中的数值比左边数组中的数值小时，不记录
 * 4、最终小和 = 左边数组的小和 + 右边数组的小和 + 左边数组和右边数组合并的小和
 *
 * @author xcy
 * @date 2022/4/16 - 17:23
 */
public class SmallSunDemo {
	public static void main(String[] args) {
		int[] arr = {6, 3, 2, 1, 6, 7};
		int sum1 = SortCommonUtils.returnSmallSum(arr);
		int sum2 = smallSum(arr);
		System.out.println(sum1 == sum2);
	}

	/**
	 * 小和问题
	 *
	 * @param arr   原始无序数组
	 * @return 返回所有右边比当前数大的数的个数 * 当前数 = sum累加，得到小和
	 */
	public static int smallSum(int[] arr) {
		if (arr == null || arr.length < 2) {
			return 0;
		}
		int sum = mergeSort(arr, 0, arr.length - 1);
		System.out.println(sum);
		return sum;

	}

	/**
	 * 归并排序将数组分为两组：左边数组和右边数组
	 * 以递归的方式让两个数组有序，并取出左边数组和右边数组各自的小和进行累加
	 *
	 * @param arr   原始无序数组
	 * @param left  原始无序数组的左边界
	 * @param right 原始无序数组的右边界
	 * @return 左边数组和右边数组各自的小和的累加和
	 */
	public static int mergeSort(int[] arr, int left, int right) {
		if (left >= right) {
			return 0;
		}
		int mid = left + ((right - left) >> 1);
		return mergeSort(arr, left , mid) + mergeSort(arr, mid + 1, right) + merge(arr, left, mid, right) ;

	}

	/**
	 * 合并
	 *
	 * @param arr   原始无序数组
	 * @param left  原始无序数组的左边界
	 * @param right 原始无序数组的右边界
	 * @return
	 */
	public static int merge(int[] arr, int left, int mid, int right) {
		//拷贝数组
		int[] temp = new int[right - left + 1];
		// int[] temp = new int[arr.length];
		//拷贝数组的索引
		int index = 0;
		//当前合并的左边数组的左边界
		int cur1 = left;
		//当前合并的右边数组的左边界
		int cur2 = mid + 1;

		//小和
		int smallSum = 0;
		while (cur1 <= mid && cur2 <= right) {
			//1)判断右边数组中的数值是否大于左边数组中的数值 --> arr[cur1] < arr[cur2]
			//1.1)如果大于的话，取出右边数组中有多少个大于当前左边数组中数的数值的个数
			//小和累加个数乘以当前左边数组中数值的积
			//smallSum += arr[cur1++] * (right - cur2 + 1)
			//a. arr[cur1++]：表示当前左边数组的数值
			//b.(right - cur2 + 1)：表示右边数组的右边界 - 左边界 + 1，就是右边数组数值的个数
			//1.2)如果小于的话，小和不进行累加 --> smallSum += 0
			smallSum += arr[cur1] < arr[cur2] ? arr[cur1] * (right - cur2 + 1) : 0;
			//2)判断当前左边数组的数值和右边数组的数值
			//2.1)当前左边数组的数值和右边数组的数值相等时，为什么要先拷贝右边数组的数值
			//因为一定要能够准确的计算出 右边数组中有多少个大于当前左边数组中的数 的数值，求出小和
			//所以arr[cur1] <= arr[cur2]，会优先添加左边数组中的数值
			temp[index++] = arr[cur1] < arr[cur2] ? arr[cur1++] : arr[cur2++];
			// temp[index++] = Math.min(arr[cur1++], arr[cur2++]);
		}

		while (cur1 <= mid) {
			temp[index++] =  arr[cur1++];
		}

		while (cur2 <= right) {
			temp[index++] = arr[cur2++];
		}

		for (index = 0; index < temp.length; index++) {
			arr[left + index] = temp[index];
		}
		return smallSum;
	}
}
