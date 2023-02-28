package com.mashibing.search;

/**
 * 在数组中查找大于指定值的最左侧位置
 * @author xcy
 * @date 2022/4/6 - 10:55
 */
public class SearchMaxValueLeft {
	public static void main(String[] args) {
		int[] arr = {1, 10, 10, 10, 15, 20, 20, 30, 45, 50, 78, 100};
		//指定值
		int value = 23;
		int index = searchMaxValueLeft(arr, value);
		if (index != -1) {
			System.out.println("大于" + value + "的值的最左边的索引是：" + index);
		} else {
			System.out.println("没有找到大于" + value + "的值最左边的索引");
		}

	}

	/**
	 * 在数组中找到大于指定值的最左侧位置
	 *
	 * @param arr   数组
	 * @param value 指定值
	 * @return 返回在数组中找到大于指定值的最左位置
	 */
	public static int searchMaxValueLeft(int[] arr, int value) {
		int left = 0;
		int right = arr.length - 1;
		int index = -1;
		while (left <= right) {
			int mid = left + ((right - left) / 2);
			if (arr[mid] >= value) {
				index = mid;
				right = mid - 1;
			} else {
				left = mid + 1;
			}
		}
		return index;
	}
}
