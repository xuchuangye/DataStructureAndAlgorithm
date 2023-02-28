package com.mashibing.sort;

import com.mashibing.common.SortCommonUtils;

/**
 * 选择排序
 * <p>
 * 选择排序思想:
 * <p>
 * 选择排序（select sorting）也是一种简单的排序方法。它的基本思想是：
 * 第一次从arr[0]~arr[n-1]中选取最小值，与arr[0]交换，
 * 第二次从arr[1]~arr[n-1]中选取最小值，与arr[1]交换，
 * 第三次从arr[2]~arr[n-1]中选取最小值，与arr[2]交换，…，
 * 第i次从arr[i-1]~arr[n-1]中选取最小值，与arr[i-1]交换，…,
 * 第n-1次从arr[n-2]~arr[n-1]中选取最小值，与arr[n-2]交换，总共通过n-1次，得到一个按 排序码从小到大 排列的有序序列
 * <p>
 * 选择排序的优化：
 * 如果当次循环已经找到的最小值的索引i是默认的最小值minIndex的索引，那就不需要进行交换，也就是minIndex == i
 * 否则就需要交换
 * <p>
 * 选择排序的稳定性：
 * 不稳定
 *
 * @author xcy
 * @date 2022/3/20 - 15:38
 */
public class SelectionSorting {

	public static void main(String[] args) {
		//测试时间
		/*int[] arr = SortCommonUtils.getArray();
		//int[] arr = {29, 30, 40, 10, 60, 28};

		Date dateStart = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String start = simpleDateFormat.format(dateStart);
		System.out.printf("选择排序前的时间：\n%s\n", start);

		System.out.println();
		selectionSort(arr);

		Date dateEnd = new Date();
		String end = simpleDateFormat.format(dateEnd);
		System.out.printf("选择排序后的时间：\n%s\n", end);*/

		//测试错误
		//测试次数
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
			selectSort(array);
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

		//System.out.println(Arrays.toString(arr));
		/*
		//第1轮
		//当前循环的最小值索引
		int minIndex = 0;
		//当前循环的最小值
		int min = arr[0];

		//j从当前最小值的下一个位数开始比较的，因为默认的最小值是从0开始的
		//j < arr.length 表示需要选择交换的位置截止到数组的最后一个元素
		for (int j = 0 + 1; j < arr.length; j++) {
			if (min > arr[j]) {
				//注意：只是确定当次循环的最小值和最小值的索引，并没有进行交换
				min = arr[j];
				minIndex = j;
			}
		}
		//如果最小值的索引不是数组的起始位置，那么就进行交换
		if (minIndex != 0) {
			//将当次循环默认的最小值和已经找到的最小值之间的索引进行交换
			arr[minIndex] = arr[0];
			//最后将当次循环已经找到的最小值设置到数组的起始位置
			arr[0] = min;
		}


		System.out.println("第1轮选择排序之后的数组");
		System.out.println(Arrays.toString(arr));


		//第2轮
		//当前循环的最小值索引
		minIndex = 1;
		//当前循环的最小值
		min = arr[1];

		//j从当前最小值的下一个位数开始比较的，因为默认的最小值是从0开始的
		//j < arr.length 表示需要选择交换的位置截止到数组的最后一个元素
		for (int j = 1 + 1; j < arr.length; j++) {
			if (min > arr[j]) {
				min = arr[j];
				minIndex = j;
			}
		}
		//将当次循环默认的最小值和已经找到的最小值之间的索引进行交换
		arr[minIndex] = arr[1];
		//最后将当次循环已经找到的最小值设置到数组的起始位置
		arr[1] = min;

		System.out.println("第2轮选择排序之后的数组");
		System.out.println(Arrays.toString(arr));


		//第3轮
		//当前循环的最小值索引
		minIndex = 2;
		//当前循环的最小值
		min = arr[2];

		//j从当前最小值的下一个位数开始比较的，因为默认的最小值是从0开始的
		//j < arr.length 表示需要选择交换的位置截止到数组的最后一个元素
		for (int j = 2 + 1; j < arr.length; j++) {
			if (min > arr[j]) {
				min = arr[j];
				minIndex = j;
			}
		}
		//将当次循环默认的最小值和已经找到的最小值之间的索引进行交换
		arr[minIndex] = arr[2];
		//最后将当次循环已经找到的最小值设置到数组的起始位置
		arr[2] = min;

		System.out.println("第3轮选择排序之后的数组");
		System.out.println(Arrays.toString(arr));


		//第4轮
		//当前循环的最小值索引
		minIndex = 3;
		//当前循环的最小值
		min = arr[3];

		//j从当前最小值的下一个位数开始比较的，因为默认的最小值是从0开始的
		//j < arr.length 表示需要选择交换的位置截止到数组的最后一个元素
		for (int j = 3 + 1; j < arr.length; j++) {
			if (min > arr[j]) {
				min = arr[j];
				minIndex = j;
			}
		}
		//将当次循环默认的最小值和已经找到的最小值之间的索引进行交换
		arr[minIndex] = arr[3];
		//最后将当次循环已经找到的最小值设置到数组的起始位置
		arr[3] = min;

		System.out.println("第3轮选择排序之后的数组");
		System.out.println(Arrays.toString(arr));

		//第5轮
		//当前循环的最小值索引
		minIndex = 4;
		//当前循环的最小值
		min = arr[4];

		//j从当前最小值的下一个位数开始比较的，因为默认的最小值是从0开始的
		//j < arr.length 表示需要选择交换的位置截止到数组的最后一个元素
		for (int j = 4 + 1; j < arr.length; j++) {
			if (min > arr[j]) {
				min = arr[j];
				minIndex = j;
			}
		}
		//将当次循环默认的最小值和已经找到的最小值之间的索引进行交换
		arr[minIndex] = arr[4];
		//最后将当次循环已经找到的最小值设置到数组的起始位置
		arr[4] = min;

		System.out.println("第3轮选择排序之后的数组");
		System.out.println(Arrays.toString(arr));
		*/
	}


	/**
	 * 选择排序（从小到大） --> 效率高版本
	 *
	 * @param arr 数组
	 */
	private static void selectionSort(int[] arr) {
		if (arr == null || arr.length < 2) {
			return;
		}
		//i表示在当前默认的最小值与之后的元素中选择的最小值进行交换，交换的次数
		for (int i = 0; i < arr.length - 1; i++) {
			//当前循环的最小值索引
			int minIndex = i;
			//当前循环的最小值
			int min = arr[i];

			//j从当前最小值的下一个位数开始比较的，因为默认的最小值是从0开始的
			//j < arr.length 表示需要选择交换的位置截止到数组的最后一个元素
			for (int j = i + 1; j < arr.length; j++) {
				if (arr[j] < min) {
					//注意：只是确定当次循环的最小值和最小值的索引，并没有进行交换
					min = arr[j];
					minIndex = j;
				}
			}
			//如果当次循环已经找到的最小值并不是默认的最小值，那么就进行交换
			if (minIndex != i) {
				//将当次循环默认的最小值和已经找到的最小值之间的索引进行交换
				arr[minIndex] = arr[i];
				//最后将当次循环已经找到的最小值设置到数组的起始位置
				arr[i] = min;
			}
			//System.out.println("第1轮选择排序之后的数组");
			//System.out.println(Arrays.toString(arr));
		}
	}

	/**
	 * 选择排序（从小到大） --> 效率低版本
	 *
	 * @param arr 数组
	 */
	public static void selectSort(int[] arr) {
		if (arr == null || arr.length < 2) {
			return;
		}

		for (int i = 0; i < arr.length; i++) {
			int minValueIndex = 0;
			for (int j = i + 1; j < arr.length; j++) {
				if (arr[j] < arr[minValueIndex]) {
					minValueIndex = j;
				}
			}
			SortCommonUtils.swap(arr, i, minValueIndex);
		}
	}
}
