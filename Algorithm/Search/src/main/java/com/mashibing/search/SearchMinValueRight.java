package com.mashibing.search;

/**
 * 在数组中查找小于指定值的最右侧位置
 * @author xcy
 * @date 2022/4/6 - 11:04
 */
public class SearchMinValueRight {
	public static void main(String[] args) {
		int[] arr = {1, 10, 10, 10, 15, 20, 20, 30, 45, 50, 78, 100};

		//指定值
		int value = 23;
		int index = searchMinValueRight(arr, value);
		if (index != -1) {
			System.out.println("小于" + value + "的值的最右边的索引是：" + index);
		} else {
			System.out.println("没有找到小于" + value + "的值最有边的索引");
		}
	}

	/**
	 * 在数组中找到小于指定值的最右侧的索引
	 * @param arr 数组
	 * @param value 指定值
	 * @return 返回数组中小于指定值的最右边的索引
	 */
	public static int searchMinValueRight(int[] arr, int value) {
		int left =0;
		int right = arr.length - 1;
		int index = -1;
		while (left <= right) {
			int mid = left + ((right - left) >> 1);
			if (arr[mid] <= value) {
				index = mid;
				left = mid + 1;
			}else {
				right = mid - 1;
			}
		}
		return index;
	}
}
