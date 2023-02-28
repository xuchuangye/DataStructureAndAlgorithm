package com.mashibing.arithmetic.search;

/**
 * 二分查找算法 -> 以非递归的方式
 *
 * @author xcy
 * @date 2022/3/31 - 16:40
 */
public class BinarySearch {
	public static void main(String[] args) {
		int[] arr = {1, 3, 8, 10, 11, 67, 100};

		int index = binarySearch(arr, 10);
		if (index == -1) {
			System.out.println("在数组中没有找到要查找的值");
		} else {
			System.out.println("在数组中找到了要查找的值，值所在的索引是：" + index);
		}
	}

	/**
	 * 二分查找算法 -> 非递归的方式
	 *
	 * @param arr    准备查找的数组
	 * @param target 要查找的值
	 * @return 在数组中查找要要查找的值，如果找到返回值所在的索引，如果没有找到则返回-1
	 */
	public static int binarySearch(int[] arr, int target) {
		//从索引0开始
		int left = 0;
		//到数组的最后一个元素结束
		int right = arr.length - 1;

		//方式一
		for (int i = left; i <= right;) {
			//中间索引
			int mid = (left + right) / 2;
			if (target == arr[mid]) {
				return mid;
			}
			//如果要查找的值比中间索引位置上的值小
			else if (target < arr[mid]) {
				//从左边查找，从left == 0到mid - 1结束
				right = mid - 1;
			}
			//如果要查找的值比中间索引位置上的值大
			else {
				//从右边查找，从mid + 1到 right = arr.length - 1结束
				left = mid + 1;
			}
		}

		//方式二
		/*while (left <= right) {
			//中间索引
			int mid = (left + right) / 2;
			if (target == arr[mid]) {
				return mid;
			} else if (target < arr[mid]) {
				right = mid - 1;
			} else {
				left = mid + 1;
			}
		}*/
		return -1;
	}
}
