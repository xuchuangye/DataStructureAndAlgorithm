package com.mashibing.interviewquestions;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * 堆的题目：
 * 已知一个几乎有序的数组，数组中每一个元素只需要移动不超过K次即可做到整个数组都有序，并且K对于整个数组的长度来说比较小
 * 请选择一个合适的排序策略，对这个数组进行排序
 *
 * @author xcy
 * @date 2022/4/20 - 8:49
 */
public class HeapInterviewQuestions001 {
	public static void main(String[] args) {
		//测试时间
		/*int[] arr = SortCommonUtils.getArray();

		Date dateStart = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String start = simpleDateFormat.format(dateStart);
		System.out.printf("堆排序前的时间：\n%s\n", start);

		System.out.println();
		heapSort(arr);

		Date dateEnd = new Date();
		String end = simpleDateFormat.format(dateEnd);
		System.out.printf("堆排序后的时间：\n%s\n", end);*/

		//测试错误
		/*//测试次数
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
			heapSort(array, 2);
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
		System.out.println(success ? "测试成功" : "测试失败");*/

		int[] arr = {3, 1, 4, 2, 5};
		heapSort(arr, 2);
		System.out.println(Arrays.toString(arr));
	}

	/**
	 * 堆排序
	 * @param arr
	 * @param k
	 */
	public static void heapSort(int[] arr, int k) {
		//没有排序的必要
		if (arr == null || arr.length < 2) {
			return;
		}
		//int heapSize = arr.length;
		//默认小根堆
		PriorityQueue<Integer> heap = new PriorityQueue<>();
		//如果不需要移动，那么直接就是有序的，直接返回
		if (k < 2) {
			return;
		}
		int index = 0;
		int[] temp = new int[arr.length];
		//先加入到小根堆
		for (int i = 0; i < k; i++) {
			heap.add(arr[i]);
		}
		for (int i = k; i < arr.length; i++) {
			//小根堆每加入一个元素
			heap.add(arr[i]);
			//就弹出一个元素到i位置上，i从0开始
			temp[index] = heap.poll();
			index++;
		}
		while (!heap.isEmpty()) {
			temp[index] = heap.poll();
			index++;
		}

		for (index = 0; index < temp.length; index++) {
			arr[index] = temp[index];
		}
	}

	/**
	 * 判断当前子节点和父节点的权值谁比较小的关系，也就是小根堆的规则
	 *
	 * @param arr   原始无序数组
	 * @param index 当前节点的索引
	 */
	public static void heapInsert(int[] arr, int index) {
		while (arr[index] < arr[(index - 1) / 2]) {
			swap(arr, index, (index - 1) / 2);
			index = (index - 1) / 2;
		}
	}

	/**
	 * 先判断当前左节点和右节点的权值谁比较小，然后再判断当前子节点和父节点的权值谁比较小
	 *
	 * @param arr      原始无序数组
	 * @param index    当前节点的索引
	 * @param heapSize 堆的大小
	 */
	public static void heapify(int[] arr, int index, int heapSize) {
		int left = 2 * index + 1;
		while (left < heapSize) {
			//判断当前左节点和右节点的权值谁比较小
			int minIndex = left + 1 < heapSize && arr[left + 1] < arr[left] ? arr[left + 1] : arr[left];
			//判断当前节点和父节点谁的权值比较小
			minIndex = arr[minIndex] < arr[index] ? minIndex : index;

			//如果当前节点和父节点的权值一样，直接退出循环
			if (minIndex == index) {
				break;
			}
			//否则就进行交换
			swap(arr, index, minIndex);
			//当前的节点指向minIndex
			index = minIndex;
			//继续下一层节点
			left = 2 * index + 1;
		}
	}

	/**
	 * 数组中两个元素的交换
	 * @param arr
	 * @param i
	 * @param j
	 */
	public static void swap(int[] arr, int i, int j) {
		int temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}
}
