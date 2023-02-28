package com.mashibing.sort;

import java.util.Arrays;

/**
 * @author xcy
 * @date 2021/9/7 - 21:28
 */

/**
 * 选择排序
 */
public class SelectionSort {
	public static void selectionSort(int[] arr) {
		if (arr == null || arr.length < 2) {
			return;
		}
		//0 ~ N - 1
		//1 ~ N - 1
		//2 ~ N - 1
		//外层for循环控制在该范围判断最小值
		for (int i = 0; i < arr.length - 1; i++) {// i ~ N - 1
			//默认最小值在i位置上
			int minIndex = i;
			//从i位置之后，继续循环遍历i+1 ~ N-1
			for (int j = i + 1; j < arr.length; j++) {//i ~ N - 1 上找最小值的索引
				//如果j位置上的数值比当前最小值还小，那么j位置上就是最小值，否则当前最小值仍然是最小值
				minIndex = arr[j] < arr[minIndex] ? j : minIndex;
			}
			//找到最小值，将最小值与当前的值进行交换
			swap(arr, i, minIndex);
		}
	}

	public static void swap(int[] arr, int i, int j) {
		int temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}

	//for test
	public static void comparator(int[] arr) {
		Arrays.sort(arr);
	}

	/**
	 * 产生长度随机并且数组元素也都随机的一个数组
	 *
	 * @param maxSize
	 * @param maxValue
	 * @return
	 */
	public static int[] generateRandomArray(int maxSize, int maxValue) {
		//Math.random() [0, 1)等概率返回0 ~ 1之间的一个小数
		//Math.random() * N [0, N)等概率返回0 ~ N之间的一个小数
		//(int)(Math.random() * N) [0, N - 1]等概率返回0 ~N - 1之间的一个整数

		int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
		for (int i = 0; i < arr.length; i++) {
			//数组元素既可以出现负值，也可以出现正值
			arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
		}
		return arr;
	}

	/**
	 * 拷贝数组
	 *
	 * @param arr
	 * @return
	 */
	public static int[] copyArray(int[] arr) {
		if (arr == null) {
			return null;
		}
		int[] res = new int[arr.length];
		for (int i = 0; i < arr.length; i++) {
			res[i] = arr[i];
		}
		return res;
	}

	/**
	 * 必须保证两个数组都为空，或者都不为空，并且两个数组的长度相同，其余皆返回false
	 * 并且比较两个数组的每一个元素，只要有一个元素不相同，返回false
	 * 如果两个数组的所有元素都相同，返回true
	 * @param arr1
	 * @param arr2
	 * @return
	 */
	public static boolean isEqual(int[] arr1, int[] arr2) {
		if ((arr1 == null && arr2 != null) || (arr1 != null && arr2 == null)) {
			return false;
		}
		if (arr1 == null && arr2 == null) {
			return true;
		}
		if (arr1.length != arr2.length) {
			return false;
		}
		for (int i = 0; i < arr1.length; i++) {
			if (arr1[i] != arr2[i]) {
				return false;
			}
		}
		return true;

	}

	public static void printArray(int[] arr) {
		if (arr == null) {
			return;
		}
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + "  ");
		}
		System.out.println();
	}

	public static void main(String[] args) {
		//测试次数
		int testTime = 500000;
		//最大长度
		int maxSize = 100;
		//最大值
		int maxValue = 100;
		//默认情况下是测试成功的
		boolean succeed = true;
		for (int i = 0; i < testTime; i++) {
			//产生长度随机并且数组元素也都随机的一个数组arr1
			int[] arr1 = generateRandomArray(maxSize, maxValue);
			//将数组arr1的所有元素拷贝到数组arr2中
			int[] arr2 = copyArray(arr1);
			//使用选择排序对数组arr1进行排序
			selectionSort(arr1);
			//使用系统提供的排序对数组arr2进行排序
			comparator(arr2);
			if (!isEqual(arr1, arr2)) {
				//只要isEqual()返回返回false，就表示数组的元素不相同，测试失败
				succeed = false;
				printArray(arr1);
				printArray(arr2);
				break;
			}
		}
		System.out.println(succeed ? "Nice!" : "Fucking fucked ! ");
		int[] arr = generateRandomArray(maxSize, maxValue);
		printArray(arr);
		selectionSort(arr);
		printArray(arr);
	}

}
