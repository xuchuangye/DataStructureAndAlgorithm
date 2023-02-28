package com.mashibing.sort;

/**
 * @author xcy
 * @date 2021/9/8 - 19:33
 */

import java.util.Arrays;

/**
 * 插入排序
 * 插入排序和冒泡排序是不一样的，最好的情况下时间复杂度是O(N)
 */
public class InsertionSort {
	public static void insertionSort(int[] arr) {
		if (arr == null || arr.length < 2) {
			return;
		}
		//0 ~ 0有序，所以直接从1开始
		//0 ~ 1有序
		//0 ~ 2有序
		//0 ~ 3有序
		//0 ~ i有序
		//0 ~ N - 1i有序
		//0 ~ 0 已经做到有序了，所以索引从1开始
		for (int i = 1; i < arr.length; i++) {//0 ~ i 做到有序
			//arr[j] > arr[j + 1]表示前面的值要比后面的值大的时候才进行交换
			//j先来到i-1位置，左边是否有，左边的数是否比右边的大，如果是交换
			//j--，一直到左边的数不在比右边的数大时，停止，或者左边没数了，停止
			for (int j = i - 1; j >= 0 && arr[j] > arr[j + 1]; j--) {
				swap(arr, j, j + 1);
			}
		}
	}

	public static void swap(int[] arr, int i, int j) {
		arr[i] = arr[i] ^ arr[j];
		arr[j] = arr[i] ^ arr[j];
		arr[i] = arr[i] ^ arr[j];
	}

	public static void comparator(int[] arr) {
		Arrays.sort(arr);
	}

	/**
	 * 判断两个数组中所有的元素是否都一致
	 * @param arr1 比较数组
	 * @param arr2 比较数组
	 * @return 如果一致返回true，不一致返回false
	 */
	private static boolean isEquals(int[] arr1, int[] arr2) {
		if (arr1 == null || arr2 == null) {
			return false;
		}
		for (int i = 0; i < arr1.length; i++) {
			if (arr1[i] != arr2[i]) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 拷贝数组
	 * @param arr 被拷贝的数组
	 * @return 拷贝之后的新数组
	 */
	private static int[] copyArray(int[] arr) {
		if (arr == null) {
			return null;
		}
		int[] res = new int[arr.length];
		System.arraycopy(arr, 0, res, 0, arr.length);
		return res;
	}

	/**
	 * 产生一个长度随机，元素的值随机的数组
	 * @param maxSize 数组长度随机
	 * @param maxValue 数组元素值随机
	 * @return
	 */
	private static int[] generateRandomArray(int maxSize, int maxValue) {
		/*
		Math.random()随机数发生器，在数学上是不可能做到的，在计算机上是可能的
		因为0~1上所有的小数在计算机里面是有穷尽的，有一个精度，超过精度以外的小数是不要的
		数学上做不到的事情，计算机上是因为离散的，0~1范围上的小数有穷个的，有限个的，所以能够做到
		 */
		//Math.random() --> [0,1)所有的小数，等概率返回一个
		//Math.random() * N--> [0,N)所有的小数，等概率返回一个
		//(int)(Math.random() * N)--> [0,N - 1]所有的整数，等概率返回一个
		int[] arr = new int[(int)((maxSize + 1) * Math.random())];//长度随机
		for (int i = 0; i < arr.length; i++) {
			arr[i] = (int)((maxValue + 1) *Math.random()) - (int)(maxValue * Math.random());
		}
		return arr;
	}

	public static void main(String[] args) {
		//测试次数
		int testCount = 1000000;
		//随机数组的长度0~100
		int maxSize = 100;
		//值-100~100
		int maxValue = 100;
		//判断数组arr1与arr2所有的元素是否都一致，默认是true，不一致false
		boolean success = true;
		for (int i = 1; i <= testCount; i++) {
			int[] arr = generateRandomArray(maxSize, maxValue);
			int[] arr1 = copyArray(arr);
			int[] arr2 = copyArray(arr);

			//对数组arr1使用插入排序方法进行排序，arr2使用Arrays工具类.sort()进行排序
			insertionSort(arr1);
			comparator(arr2);
			//比较arr1中所有的元素是否与arr2中的所有元素一致
			if (!isEquals(arr1, arr2)) {
				success = false;
				for (int j : arr1) {
					System.out.print(j + " : " + " --> arr1  ]  ");
				}
				for (int j : arr2) {
					System.err.print(j + " , " + " --> arr2  ]  ");
				}
				break;
			}
		}
		System.out.println(success ? "Nice!" : "Fucking fucked");
	}
}
