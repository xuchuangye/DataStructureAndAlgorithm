package com.mashibing.sort;

import com.mashibing.common.SortCommonUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 希尔排序
 * 希尔排序也是一种插入排序，它是简单插入排序经过改进之后的一个更高效的版本，也称为缩小增量排序。
 *
 * 希尔排序法基本思想
 *
 * 希尔排序是把记录按下标的一定增量分组，对每组使用直接插入排序算法排序；
 * 随着增量逐渐减少，每组包含的关键词越来越多，当增量减至1时，整个文件恰被分成一组，算法便终止
 *
 * 使用交换法 --> shellSortSwap(int[] arr)
 * 使用位移法 --> shellSortMove(int[] arr)
 * @author xcy
 * @date 2022/3/21 - 8:57
 */
public class ShellSorting {
	public static void main(String[] args) {
		int[] arr = SortCommonUtils.getArray();
		//int[] arr = {8, 9, 1, 7, 2, 3, 5, 4, 6, 0};


		Date dateStart = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String start = simpleDateFormat.format(dateStart);
		System.out.printf("希尔排序前的时间：\n%s\n", start);

		System.out.println();

		//采用交换法的希尔排序
		//shellSortSwap(arr);
		//采用移位法的希尔排序
		shellSortMove(arr);

		Date dateEnd = new Date();
		String end = simpleDateFormat.format(dateEnd);
		System.out.printf("希尔排序后的时间：\n%s\n", end);

		//System.out.println(Arrays.toString(arr));

		/*
		int temp = 0;
		//第1轮
		//数组总共10个元素，一共分为5组
		for (int i = 5; i < arr.length; i++) {
			//遍历所有数组中的每个元素
			for (int j = i - 5; j >= 0; j -= 5) {
				if (arr[j] > arr[j + 5]) {
					temp = arr[j];
					arr[j] = arr[j + 5];
					arr[j + 5] = temp;
				}
			}
		}

		System.out.println("第1轮希尔排序之后的数组");
		System.out.println(Arrays.toString(arr));

		//第2轮
		//数组总共10个元素，一共分为5组
		for (int i = 2; i < arr.length; i++) {
			//遍历所有数组中的每个元素
			for (int j = i - 2; j >= 0; j -= 2) {
				if (arr[j] > arr[j + 2]) {
					temp = arr[j];
					arr[j] = arr[j + 2];
					arr[j + 2] = temp;
				}
			}
		}

		System.out.println("第2轮希尔排序之后的数组");
		System.out.println(Arrays.toString(arr));

		//第3轮
		//数组总共10个元素，一共分为5组
		for (int i = 1; i < arr.length; i++) {
			//遍历所有数组中的每个元素
			for (int j = i - 1; j >= 0; j -= 1) {
				if (arr[j] > arr[j + 1]) {
					temp = arr[j];
					arr[j] = arr[j + 1];
					arr[j + 1] = temp;
				}
			}
		}

		System.out.println("第3轮希尔排序之后的数组");
		System.out.println(Arrays.toString(arr));
		*/
	}

	/**
	 * 希尔排序(采用交换法) -> 从小到大
	 *
	 * @param arr 数组
	 */
	public static void shellSortSwap(int[] arr) {
		int temp = 0;
		int count = 0;
		//gap表示步长
		for (int gap = arr.length / 2; gap > 0; gap /= 2) {

			//数组总共10个元素，一共分为5组
			for (int i = gap; i < arr.length; i++) {
				//遍历各组中的所有元素（共gap组，每组有 arr.length / gap 个元素，步长gap）
				for (int j = i - gap; j >= 0; j -= gap) {
					//如果当前元素大于加上步长之后的那个元素，那么就进行交换
					if (arr[j] > arr[j + gap]) {
						temp = arr[j];
						arr[j] = arr[j + gap];
						arr[j + gap] = temp;
					}
				}
			}
			//System.out.println("第" + (++count) + "轮希尔排序之后的数组");
			//System.out.println(Arrays.toString(arr));
		}
	}

	/**
	 * 希尔排序(采用移位法) -> 从小到大
	 * @param arr 数组
	 */
	public static void shellSortMove(int[] arr) {
		//增量步长gap，循环遍历逐步缩小增量
		for (int gap = arr.length / 2; gap > 0; gap /= 2) {
			//每一组遍历都从索引为步长值的元素开始
			//比如：数组长度为10，那么步长就为5，数组索引是从0开始
			//所以第一组元素的索引是0和5，第二组1和6，第三组2和7，第四组3和8，第五组4和9，并且增量步长都是gap
			for (int i = gap; i < arr.length; i++) {
				//记录待插入的数的索引
				int j = i;
				//记录待插入的数
				int temp = arr[j];
				//当前待插入的元素比当前待插入的数的索引 - 增量步长的值还要小，那么进行就进行移位
				//比如：第一组元素中索引5的数比索引0的数还要小时，就进行移位
				if (arr[j] < arr[j - gap]) {
					//j - gap >= 0
					//  表示防止插入待插入的元素时，出现越界的问题
					//temp < arr[j - gap]
					//  表示当前待插入的数比当前索引为(待插入的数的 - 增量步长)的数的值还要小
					while ((j - gap >= 0) && temp < arr[j - gap]) {
						//将较小的值移动到较大的值的位置上
						arr[j] = arr[j - gap];
						//往后移动增量步长gap
						j -= gap;
					}
					//当退出循环时，那么就证明已经找到了插入的位置
					//将原来较大的值的位置上插入较小的值，完成排序
					arr[j] = temp;
				}
			}
		}
	}
}
