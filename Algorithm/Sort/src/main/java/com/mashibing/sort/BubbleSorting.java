package com.mashibing.sort;

import com.mashibing.common.SortCommonUtils;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * 冒泡排序
 * <p>
 * 冒泡排序（Bubble Sorting）的基本思想是：
 * 通过对待排序序列从前向后（从下标较小的元素开始）,依次比较相邻元素的值，
 * 若发现逆序则交换，使值较大的元素逐渐从前移向后部，就象水底下的气泡一样逐渐向上冒。
 * <p>
 * 冒泡排序的优化：
 * 因为排序的过程中，各元素不断接近自己的位置，
 * 如果一趟比较下来没有进行过交换，就说明序列有序，
 * 因此要在排序过程中设置一个标志isSwap判断元素是否进行过交换。从而减少不必要的比较。
 *
 * 冒泡排序的稳定性：
 * 冒泡排序可以实现稳定性，==时，两两不交换就可以实现
 *
 * @author xcy
 * @date 2022/3/20 - 10:48
 */
public class BubbleSorting {
	public static void main(String[] args) {
		int[] arr = SortCommonUtils.getArray();
		// int[] arr = {29, 30, 40, 10, 60, 28};

		Date dateStart = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String start = simpleDateFormat.format(dateStart);
		System.out.printf("冒泡排序前的时间：\n%s\n", start);

		System.out.println();
		bubbleSort(arr);//25s
		//bubbleSorting(arr);//24s

		Date dateEnd = new Date();
		String end = simpleDateFormat.format(dateEnd);
		System.out.printf("冒泡排序后的时间：\n%s\n", end);

		//System.out.println(Arrays.toString(arr));
		/*
		for (int j = 0; j < arr.length - 1 - 1; j++) {
			//每次交换和遍历的次数
			if (arr[j] > arr[j + 1]) {
				temp = arr[j];
				arr[j] = arr[j + 1];
				arr[j + 1] = temp;
			}
		}
		System.out.println("第二趟冒泡排序的结果");
		System.out.println(Arrays.toString(arr));

		for (int j = 0; j < arr.length - 1 - 2; j++) {
			//每次交换和遍历的次数
			if (arr[j] > arr[j + 1]) {
				temp = arr[j];
				arr[j] = arr[j + 1];
				arr[j + 1] = temp;
			}
		}
		System.out.println("第三趟冒泡排序的结果");
		System.out.println(Arrays.toString(arr));

		for (int j = 0; j < arr.length - 1 - 3; j++) {
			//每次交换和遍历的次数
			if (arr[j] > arr[j + 1]) {
				temp = arr[j];
				arr[j] = arr[j + 1];
				arr[j + 1] = temp;
			}
		}
		System.out.println("第四趟冒泡排序的结果");
		System.out.println(Arrays.toString(arr));

		for (int j = 0; j < arr.length - 1 - 4; j++) {
			//每次交换和遍历的次数
			if (arr[j] > arr[j + 1]) {
				temp = arr[j];
				arr[j] = arr[j + 1];
				arr[j + 1] = temp;
			}
		}
		System.out.println("第五趟冒泡排序的结果");
		System.out.println(Arrays.toString(arr));
		*/
	}

	/**
	 * 冒泡排序（从小到大） -->
	 *
	 * @param arr 准备冒泡排序的数组
	 */
	private static void bubbleSort(int[] arr) {
		if (arr == null || arr.length < 2) {
			return;
		}
		//数组元素之间交换的临时变量
		int temp = 0;

		boolean isSwap = false;//数据是否进行过交换，默认为false
		//1.总共需要遍历arr.length - 1次循环，也就是数组元素之间两两交换的轮数
		for (int i = 0; i < arr.length - 1; i++) {
			//2.每次循环中排序的次数都在减少
			for (int j = 0; j < arr.length - 1 - i; j++) {
				//判断当前索引位置上的值是否 大于 下一个索引位置上的值
				if (arr[j] > arr[j + 1]) {
					//如果大于就进行交换，将isSwap的值设置为true，证明已经进行过交换
					isSwap = true;
					temp = arr[j];
					arr[j] = arr[j + 1];
					arr[j + 1] = temp;
				}
			}
			//System.out.println("第" + (i + 1) +"趟冒泡排序的结果");
			//System.out.println(Arrays.toString(arr));

			//冒泡排序的优化
			//如果isSwap的值为false，证明不需要进行交换，那么直接退出循环即可
			if (!isSwap) {
				break;
			} else {
				//如果isSwap的值为false，证明没有进行过交换，将isSwap的值设置为false，继续下一个循环
				isSwap = false;
			}
		}
	}

	/**
	 * 冒泡排序（从小到大）
	 *
	 * @param arr 准备冒泡排序的数组
	 */
	private static void bubbleSorting(int[] arr) {
		if (arr == null || arr.length < 2) {
			return;
		}

		boolean isSwap = false;
		for (int j = arr.length - 1; j > 0; j--) {
			for (int i = 0; i < j; i++) {
				if (arr[i] > arr[i + 1]) {
					isSwap = true;
					SortCommonUtils.swap(arr, i, i + 1);
				}
			}

			if (!isSwap) {
				break;
			} else {
				isSwap = false;
			}
		}
	}
}
