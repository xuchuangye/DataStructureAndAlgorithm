package com.mashibing.quicksort;

import com.mashibing.common.SortCommonUtils;

/**
 * 快速排序的衍生问题：荷兰国旗问题
 *
 * @author xcy
 * @date 2022/4/19 - 8:32
 */
public class DutchFlagDemo {
	public static void main(String[] args) {

	}

	/**
	 * 时间复杂度：O(N²)
	 * @param arr 原始无序数组
	 * @param left 原始无序数组的左边界
	 * @param right 原始无序数组的右边界
	 * @return
	 */
	public static int partition(int[] arr, int left, int right) {
		if (left > right) {
			return -1;
		}
		if (left == right) {
			return left;
		}
		//小于等于区的右边界
		int lessEqual = left - 1;

		//索引的起始位置
		int index = left;

		while (index < right) {
			if (arr[index] <= arr[right]) {
				SortCommonUtils.swap(arr, index, lessEqual);
				index++;
				lessEqual++;
			} else {
				index++;
			}
		}
		SortCommonUtils.swap(arr, ++lessEqual, right);
		return lessEqual;
	}

	/**
	 * 荷兰问题的划分
	 *
	 * @param arr   原始无序数组
	 * @param left  原始无序数组的左边界
	 * @param right 原始无序数组的右边界，arr[right]的值作为划分值
	 * @return 返回等于区的左边界和右边界
	 */
	public static int[] dutchFlag(int[] arr, int left, int right) {
		if (left > right) {
			return new int[]{-1, -1};
		}
		if (left == right) {
			return new int[]{left, right};
		}
		//小于区的右边界
		int less = left - 1;
		//大于区的左边界，最后一个数arr[right]不参与
		int more = right;
		//索引的起始位置
		int index = left;

		//index不能和大于区的左边界相遇
		while (index < more) {
			if (arr[index] < arr[right]) {
				//当前index索引上的值和小于区的下一个索引上的值进行交换
				//SortCommonUtils.swap(arr, index++, ++less);
				SortCommonUtils.swap(arr, index, less + 1);
				less++;
				index++;
			} else if (arr[index] == arr[right]) {
				//直接跳过
				index++;
			} else {
				//当前index索引上的值和大于区的上一个索引上的值进行交换
				//SortCommonUtils.swap(arr, index, --more);
				SortCommonUtils.swap(arr, index, more - 1);
				more--;
				//index不移动
			}
		}

		//因为more肯定会移动到小于区的右边界的下一个位置，也就是等于区的第一个位置
		//所以more和right进行交换
		SortCommonUtils.swap(arr, more, right);

		return new int[]{less + 1, more};
	}
}
