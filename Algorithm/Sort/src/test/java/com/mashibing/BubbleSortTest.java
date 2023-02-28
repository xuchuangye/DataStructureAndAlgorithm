package com.mashibing;

import com.mashibing.common.SortCommonUtils;

/**
 * @author xcy
 * @date 2022/4/13 - 11:17
 */
public class BubbleSortTest {
	public static void main(String[] args) {
		int testTime = 1000000;
		//数组长度
		int length = 100;
		//数组元素的值的范围
		int value = 100;
		//是否测试成功，默认测试是成功的
		boolean success = true;
		for (int i = 1; i <= testTime; i++) {
			//生成随机长度值也随机的数组
			int[] array = SortCommonUtils.generateRandomArray(length, value);
			//拷贝数组
			int[] arr = SortCommonUtils.copyArray(array);

			//自己实现的排序方式
			bubbleSort(array);
			//系统提供的排序方式
			SortCommonUtils.sort(arr);

			//判断array和arr数组中的所有元素是否都相等
			if (!SortCommonUtils.isEqual(array, arr)) {
				//如果不相等，则标记success为false
				success = false;
				SortCommonUtils.printArray(array);
				SortCommonUtils.printArray(arr);
				break;
			}

		}
		System.out.println(success ? "测试成功" : "测试失败");
/*
		Date dateStart = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String start = simpleDateFormat.format(dateStart);
		System.out.printf("冒泡排序前的时间：\n%s\n", start);

		System.out.println();
		int[] arr = SortCommonUtils.getArray();
		bubbleSort(arr);

		Date dateEnd = new Date();
		String end = simpleDateFormat.format(dateEnd);
		System.out.printf("冒泡排序后的时间：\n%s\n", end);*/
	}

	public static void bubbleSort(int[] arr) {
		boolean isSwap = false;
		//i表示交换的轮数，元素一共是arr.length，至少要交换的轮数是arr.length - 1
		for (int i = 1; i <= arr.length - 1; i++) {
			//每一轮交换时交换的次数
			for (int j = 1; j <= arr.length - 1 - i; j++) {
				isSwap = true;
				if (arr[j] < arr[j + 1]) {
					SortCommonUtils.swap(arr, j, j + 1);
				}
			}

			if (isSwap) {
				isSwap = false;
			}else {
				break;
			}
		}
	}
}
