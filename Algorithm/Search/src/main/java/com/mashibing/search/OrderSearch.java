package com.mashibing.search;

/**
 * @author xcy
 * @date 2022/3/24 - 15:09
 */
public class OrderSearch {
	public static void main(String[] args) {
		int[] arr = {1,8, 10, 89, 1000, 1234};
		int index = orderSearch(arr, 100);
		if (index == -1) {
			System.out.println("没有找到");
		}else {
			System.out.println("查找的值找到了，索引是" + index);
		}
	}

	/**
	 * 线性查找算法
	 * @param arr 数组
	 * @param key 查找的值
	 * @return 查找值的索引
	 */
	public static int orderSearch(int[] arr, int key) {
		for (int i = 0; i < arr.length; i++) {
			if (key == arr[i]) {
				return i;
			}
		}
		return -1;
	}
}
