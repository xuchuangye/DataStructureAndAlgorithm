package com.mashibing;

import com.mashibing.common.SortCommonUtils;

/**
 * @author xcy
 * @date 2022/4/13 - 11:42
 */
public class InsertSortTest {
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
			insertSort(array);
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
	}

	public static void insertSort(int[] arr) {
		//数组第一个元素本身就是有序的，所以从数组第二个元素开始，直到数组的最后一个元素arr.length - 1
		for (int i = 1; i <= arr.length - 1; i++) {
			for (int j = i - 1; j >= 0 && arr[j] > arr[j + 1]; j--) {
				SortCommonUtils.swap(arr, j, j + 1);
			}
		}
	}
}
