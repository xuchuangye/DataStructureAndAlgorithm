package com.mashibing.search;

/**
 * 插值查找
 *
 * 插值查找的注意事项
 * 在数据量比较大并且查找的关键字分布比较均匀的情况下，使用插值查找的效率是比较高的
 * 在数据量比较大并且查找的关键字分布不均匀，也就是数据值跳跃性很大的情况下，插值查找并不一定比二分查找效率高
 * @author xcy
 * @date 2022/3/23 - 8:58
 */
public class InterpolationSearch {
	public static final int ARRAY_LENGTH = 100;
	public static final int[] arr = new int[ARRAY_LENGTH];
	static {
		for (int i = 0; i < arr.length; i++) {
			arr[i] = i + 1;
		}
	}
	public static void main(String[] args) {
		int index = interpolationSearch(arr, 0, arr.length - 1, 1);
		if (index != -1) {
			System.out.println("插值查找找到值了，该值的索引是=" + index);
		}else {
			System.out.println("插值查找没有找到值，返回" + index);
		}
	}

	/**
	 * 插值查找（自适应） -> 从小到大
 	 * @param arr 插值查找的数组
	 * @param left 左边索引
	 * @param right 右边索引
	 * @param findValue 插值查找的值
	 */
	public static int interpolationSearch(int[] arr, int left, int right, int findValue) {
		if (left > right || findValue < arr[0] || findValue > arr[arr.length - 1]) {
			return -1;
		}

		//自适应插值的索引
		int mid = left + (right - left) * (findValue - arr[left]) / (arr[right] - arr[left]);
		int midValue = arr[mid];

		if (findValue > midValue) {
			return interpolationSearch(arr, mid + 1, right, findValue);
		}else if (findValue < midValue) {
			return interpolationSearch(arr, left, mid - 1, findValue);
		}else {
			return mid;
		}
	}
}
