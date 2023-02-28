package com.mashibing.heapsort;

import com.mashibing.common.SortCommonUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 堆排序
 *
 * @author xcy
 * @date 2022/4/19 - 17:08
 */
public class HeapSort {
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
			heapSort(array);
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

	/**
	 * 堆排序
	 *
	 * @param arr 原始无序数组
	 */
	public static void heapSort(int[] arr) {
		if (arr == null || arr.length < 2) {
			return;
		}
		//循环遍历依次插入数组的每一个元素，得到大根堆
		/*for (int i = 0; i < arr.length; i++) {
			heapInsert(arr, i);
		}*/
		//大根堆的节点个数
		int heapSize = arr.length;

		for (int i = arr.length - 1; i >= 0; i--) {
			heapify(arr, i, heapSize);
		}
		//此时数组第一个元素一定是最大的，跟数组的最后一个元素进行交换，所以heapSize需要先--
		SortCommonUtils.swap(arr, 0, --heapSize);
		while (heapSize > 0) {
			//此时的heapSize = arr.length - 1
			heapify(arr, 0, heapSize);
			SortCommonUtils.swap(arr, 0, --heapSize);
		}
	}

	/**
	 * 用户在堆结构中插入节点
	 * 该方法判断当前子节点和父节点的关系根据大根堆的规则能否进行交换
	 * <p>
	 * 时间复杂度：O(logN)
	 * 两个限制条件：
	 * 1、如果当前节点已经是根节点，退出while循环
	 * 2、如果当前节点不是根节点，但是当前节点的权值比父节点的权值小，退出while循环
	 *
	 * @param arr   数组
	 * @param index 当前节点的索引
	 */
	public static void heapInsert(int[] arr, int index) {
		//1、判断当前节点是否是根节点
		//2、判断当前节点的值是否大于父节点的值
		while (arr[index] > arr[(index - 1) / 2]) {
			SortCommonUtils.swap(arr, index, (index - 1) / 2);
			index = (index - 1) / 2;
		}
	}

	/**
	 * 用户能pop出大根堆中的最大值，并且依然能够维持大根堆的结构和规则
	 * 该方法判断当前父节点和子节点的关系根据大根堆的规则能否进行交换
	 * <p>
	 * 时间复杂度：O(logN)
	 *
	 * @param arr      数组
	 * @param index    当前节点的索引
	 * @param heapSize 堆结构中节点的个数
	 */
	public static void heapify(int[] arr, int index, int heapSize) {
		//当前节点的左子节点
		int left = 2 * index + 1;
		while (left < heapSize) {
			//判断当前节点的左子节点和右子节点的权值大小，返回权值较大的索引
			int maxIndex = left + 1 < heapSize && arr[left + 1] > arr[left] ? left + 1 : left;
			//取出权值较大的子节点，再和父节点的权值进行比较，返回权值较大的索引
			maxIndex = arr[maxIndex] > arr[index] ? maxIndex : index;
			//判断最终获取的较大值是否是当前节点的索引，如果是，直接返回
			if (maxIndex == index) {
				break;
			}
			//如果不是，证明当前节点不是整棵子树中的最大的节点，当前节点索引需要和整棵子树的最大值索引进行交换
			SortCommonUtils.swap(arr, maxIndex, index);
			//当前节点来到maxIndex
			index = maxIndex;
			//继续进行下一层的判断，从左子节点开始
			left = 2 * index + 1;
		}
	}
}
