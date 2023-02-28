package com.mashibing.search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 二分查找算法 -> 以递归的方式
 *
 * @author xcy
 * @date 2022/3/22 - 15:29
 */
public class BinarySearch {
	public static void main(String[] args) {
		int[] arr = {1, 10, 10, 10, 15, 20, 20, 30, 45, 50, 78, 100};
		//返回以递归的方式查找到的结果
		int index = binarySearch(arr, 0, arr.length - 1, 10);
		if (index == -1) {
			System.out.println("没有找到");
		} else {
			System.out.println("找到元素所在的索引：" + index);
		}


		//返回以递归的方式查找到的结果的集合
		List<Integer> list = binarySearchToList(arr, 0, arr.length - 1, 10);
		if (list == null) {
			System.out.println("没有找到");
		}else {
			System.out.println("找到元素所在的索引：" + list.toString());
		}

		//返回以非递归的方式查找到的结果
		int binarySearch = binarySearch(arr, 100);
		if (binarySearch == -1) {
			System.out.println("没有找到");
		} else {
			System.out.println("找到元素所在的索引：" + binarySearch);
		}

		//返回以非递归的方式查找到的结果的集合
		List<Integer> binarySearchList = binarySearchList(arr, 10);
		if (binarySearchList == null) {
			System.out.println("没有找到");
		}else {
			System.out.println("找到元素所在的索引：" + binarySearchList.toString());
		}
	}

	/**
	 * 二分查找（查找元素的单个索引） -> 以递归的方式进行查找
	 *
	 * @param arr       查找元素所在的数组
	 * @param left      左边的索引
	 * @param right     右边的索引
	 * @param findValue 需要查找的元素
	 * @return 如果查找到就返回元素的索引，如果没有查找到就返回-1
	 * <p>
	 * 注意：必须要加上能够退出递归的条件，否则会出现栈溢出StackOverFlowError
	 */
	public static int binarySearch(int[] arr, int left, int right, int findValue) {
		if (left > right) {
			return -1;
		}
		//中间的索引
		//left + right因为是int类型，它们的和可能会超出int的最大 范围，所以为了保险起见
		//int mid = (left + right) / 2;
		//所以为了保险起见，使用left + (right - left) / 2
		//int mid = left + (right - left) / 2;
		int mid = left + ((right - left) >> 1);
		//中间索引对应的值
		int midValue = arr[mid];
		//判断如果查找的值大于中间的值，那么就需要去右边查找
		if (findValue > midValue) {
			return binarySearch(arr, mid + 1, right, findValue);
		}
		//如果查找的值小于中间的值，那么就需要去左边查找
		else if (findValue < midValue) {
			return binarySearch(arr, left, mid - 1, findValue);
		}
		//如果查找的值等于中间的值，直接返回中间值的索引
		else {
			return mid;
		}
	}

	/**
	 * 二分查找（查找元素的多个索引的集合） -> 从小到大
	 *
	 * @param arr       查找元素所在的数组
	 * @param left      左边的索引
	 * @param right     右边的索引
	 * @param findValue 需要查找的元素
	 * @return 如果查找到就返回元素的索引，如果没有查找到就返回-1
	 * <p>
	 * 注意：必须要加上能够退出递归的条件，否则会出现栈溢出StackOverFlowError
	 */
	public static List<Integer> binarySearchToList(int[] arr, int left, int right, int findValue) {
		if (left > right || findValue < arr[0] || findValue > arr[arr.length - 1]) {
			return new ArrayList<Integer>();
		}
		//中间的索引可能会存在越界
		//int mid = (left + right) / 2;
		//int mid = left + (right - left) / 2;
		int mid = left + ((right - left) >> 1);
		//中间索引对应的值
		int midValue = arr[mid];
		//判断如果查找的值大于中间的值，那么就需要去右边查找
		if (findValue > midValue) {
			return binarySearchToList(arr, mid + 1, right, findValue);
		}
		//如果查找的值小于中间的值，那么就需要去左边查找
		else if (findValue < midValue) {
			return binarySearchToList(arr, left, mid - 1, findValue);
		}
		//如果查找的值等于中间的值，直接返回中间值的索引
		else {
			List<Integer> list = new ArrayList<>();
			//return mid;
			//遍历mid左边的元素，如果查找到符合条件的元素，那么就添加到集合中
			/*int temp = mid - 1;
			while (true) {
				//如果越界或者没有查找到，直接退出循环
				if (temp < 0 || arr[temp] != findValue) {
					break;
				}
				list.add(temp);
				temp--;
			}*/
			for (int temp = mid - 1; temp > 0; temp--) {
				if (arr[temp] == findValue) {
					list.add(temp);
				}
			}
			list.add(mid);
			//遍历mid右边的元素，如果查找到符合条件的元素，那么就添加到集合中
			/*temp = mid + 1;
			while (true) {
				//如果越界或者没有查找到，直接退出循环
				if (temp > arr.length - 1 || arr[temp] != findValue) {
					break;
				}
				list.add(temp);
				temp++;
			}*/
			for (int temp = mid + 1; temp < arr.length - 1; temp++) {
				if (arr[temp] == findValue) {
					list.add(temp);
				}
			}
			return list;
		}
	}

	/**
	 * 二分查找算法 -> 非递归的方式进行查找
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
			//left + right因为是int类型，它们的和可能会超出int的最大 范围，所以为了保险起见
			//int mid = (left + right) / 2;
			//所以为了保险起见，使用left + (right - left) / 2
			//int mid = left + (right - left) / 2;
			int mid = left + ((right - left) >> 1);
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

	/**
	 * 二分查找算法 -> 非递归的方式进行查找
	 *
	 * @param arr    准备查找的数组
	 * @param target 要查找的值
	 * @return 在数组中查找要要查找的值，如果找到返回值所在的索引，如果没有找到则返回-1
	 */
	public static List<Integer> binarySearchList(int[] arr, int target) {
		//从索引0开始
		int left = 0;
		//到数组的最后一个元素结束
		int right = arr.length - 1;

		//方式一
		for (int i = left; i <= right;) {
			//中间索引
			//left + right因为是int类型，它们的和可能会超出int的最大 范围，所以为了保险起见
			//int mid = (left + right) / 2;
			//所以为了保险起见，使用left + (right - left) / 2
			//int mid = left + (right - left) / 2;
			int mid = left + ((right - left) >> 1);

			//如果要查找的值比中间索引位置上的值小
			if (target < arr[mid]) {
				//从左边查找，从left == 0到mid - 1结束
				right = mid - 1;
			}
			//如果要查找的值比中间索引位置上的值大
			else if (target > arr[mid]){
				//从右边查找，从mid + 1到 right = arr.length - 1结束
				left = mid + 1;
			}else {
				List<Integer> list = new ArrayList<>();

				for (int j = mid - 1; j > 0; j--) {
					if (target == arr[j]) {
						list.add(j);
					}
				}

				list.add(mid);

				for (int j = mid + 1; j < arr.length - 1; j++) {
					if (target == arr[j]) {
						list.add(j);
					}
				}
				return list;
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
		return null;
	}
}
