package com.mashibing.sort;

import com.mashibing.common.SortCommonUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 插入排序
 * <p>
 * 插入排序法思想:
 * 插入排序（Insertion Sorting）的基本思想是：
 * 把n个待排序的元素看成为一个有序表和一个无序表，开始时有序表中只包含一个元素，无序表中包含有n-1个元素，
 * 排序过程中每次从无序表中取出第一个元素，把它的排序码依次与有序表元素的排序码进行比较，
 * 将它插入到有序表中的适当位置，使之成为新的有序表。
 * <p>
 * 插入排序的优化：
 * 如果当次循环i的位置就是插入时该插入的位置insertIndex，那么就不需要进行插入，也就是insertIndex == i
 * 否则就需要进行插入，也就是insertIndex != i
 * <p>
 * 插入排序的稳定性：
 * 插入排序可以实现稳定性，==时，两两不交换就可以实现稳定性
 * @author xcy
 * @date 2022/3/20 - 16:50
 */
public class InsertionSorting {
	public static void main(String[] args) {
		int arrayMaxSize = 100000000;
		int maxValue = 1000000;
		int[] arr = SortCommonUtils.generateRandomArray(arrayMaxSize, maxValue);
		// int[] arr = {29, 30, 40, 10, 60, 28};

		Date dateStart = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String start = simpleDateFormat.format(dateStart);
		System.out.printf("插入排序前的时间：\n%s\n", start);

		//insertSort_2(arr);//9分17秒
		//insertSort_1(arr);//9分35秒
		insertionSort(arr);//2分21秒

		Date dateEnd = new Date();
		String end = simpleDateFormat.format(dateEnd);
		System.out.printf("插入排序后的时间：\n%s\n", end);

		//System.out.println(Arrays.toString(arr));
		/*
		//第1轮
		//定义待插入的数
		//因为插入排序是将数组的第一个元素作为一个有序的数组，剩余其它元素作为一个无序的数组，所以需要从第二个元素开始
		int insertValue = arr[1];
		//第一个元素作为一个有序数组，剩余其它元素都需要和该元素进行比较，如果满足条件就需要将第一个元素的位置空出来，方便交换
		int insertIndex = 1 - 1;

		//1.insertIndex >= 0 防止insertValue在插入时，出现越界的问题
		//2.insertValue < arr[insertIndex] 表示待插入的数没有找到插入的位置
		//	也就是说当前的insertIndex后移
		//3.
		while (insertIndex >= 0 && insertValue < arr[insertIndex]) {
			//将当前的insertIndex后移
			arr[insertIndex + 1] = arr[insertIndex];
			insertIndex--;
		}

		arr[insertIndex + 1] = insertValue;

		System.out.println("第1轮插入排序的数组");
		System.out.println(Arrays.toString(arr));

		//第2轮
		insertValue = arr[2];
		insertIndex = 2 - 1;
		while (insertIndex >= 0 && insertValue < arr[insertIndex]) {
			arr[insertIndex + 1] = arr[insertIndex];
			insertIndex--;
		}

		arr[insertIndex + 1] = insertValue;

		System.out.println("第2轮插入排序的数组");
		System.out.println(Arrays.toString(arr));

		//第3轮
		insertValue = arr[3];
		insertIndex = 3 - 1;
		while (insertIndex >= 0 && insertValue < arr[insertIndex]) {
			arr[insertIndex + 1] = arr[insertIndex];
			insertIndex--;
		}

		arr[insertIndex + 1] = insertValue;

		System.out.println("第3轮插入排序的数组");
		System.out.println(Arrays.toString(arr));
		*/
	}

	/**
	 * 插入排序（从小到大） --> 效率高版本
	 *
	 * @param arr 数组
	 */
	private static void insertionSort(int[] arr) {
		//如果数组为空或者数组的元素个数小于2，那就没有必要进行交换，直接退出循环
		if (arr == null || arr.length < 2) {
			return;
		}
		//待插入的值
		int insertValue = 0;
		//待插入的值的索引
		int insertIndex = 0;
		//因为插入排序是将数组的第一个元素作为一个有序的数组，剩余其它元素作为一个无序的数组
		//所以需要从第二个元素开始，并且截止到最后一个元素：i == arr.length - 1
		for (int i = 1; i < arr.length; i++) {
			//定义待插入的数
			insertValue = arr[i];
			//插入排序是将第一个元素作为一个有序数组，剩余其它元素作为一个无序数组，都需要和第一个元素进行比较
			//如果满足条件就需要将第一个元素的位置空出来，方便交换，交换之后继续查找下一个待插入的位置
			insertIndex = i - 1;
			//1.insertIndex >= 0 防止insertValue在插入时，出现越界的问题
			//2.insertValue < arr[insertIndex] 待插入的数比当前被比较的数要小
			// 表示待插入的数没有找到插入的位置，待插入的数比前被比较的数还要小
			// 也就是说当前的insertIndex需要后移，空出待插入的数的位置
			//3.insertIndex--表示依次往前遍历，查看是否都满足条件（比如：从小到大），直到退出循环
			while (insertIndex >= 0 && insertValue < arr[insertIndex]) {
				//将当前的insertIndex后移
				arr[insertIndex + 1] = arr[insertIndex];
				insertIndex--;
			}

			//插入排序算法的优化
			//如果i的位置就是插入时该插入的位置insertIndex，那么就不需要进行插入

			//否则就需要进行插入
			if (insertIndex + 1 != i) {
				//当退出while循环时，证明插入的位置已经找到
				//因为退出while循环时的insertIndex多往前移动了一次，所以插入时insertIndex需要加1往后移
				arr[insertIndex + 1] = insertValue;
			}

			//System.out.println("第" + i + "轮插入排序的数组");
			//System.out.println(Arrays.toString(arr));
		}
	}

	/**
	 * 插入排序（从小到大） --> 效率低版本
	 *
	 * @param arr 数组
	 */
	private static void insertSort_1(int[] arr) {
		if (arr == null || arr.length < 2) {
			return;
		}
		//因为插入排序是将数组的第一个元素作为一个有序的数组，剩余其它元素作为一个无序的数组
		//所以需要从第二个元素开始，并且截止到最后一个元素：i == arr.length - 1
		for (int i = 1; i < arr.length; i++) {
			for (int j = i - 1; j >= 0 && arr[j] > arr[j + 1]; j--) {
				SortCommonUtils.swap(arr, j, j + 1);
			}
		}
	}

	private static void insertSort_2(int[] arr) {
		if (arr == null || arr.length < 2) {
			return;
		}
		//因为插入排序是将数组的第一个元素作为一个有序的数组，剩余其它元素作为一个无序的数组
		//所以需要从第二个元素开始，并且截止到最后一个元素：i == arr.length - 1
		for (int i = 1; i < arr.length; i++) {
			int newNumberIndex = i;
			while (newNumberIndex - 1 >= 0 && arr[newNumberIndex - 1] > arr[newNumberIndex]) {
				SortCommonUtils.swap(arr, newNumberIndex - 1, newNumberIndex);
				newNumberIndex--;
			}
		}
	}
}
