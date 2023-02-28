package com.mashibing.sort;

import java.util.Arrays;

/**
 * @author xcy
 * @date 2022/3/26 - 16:56
 */
public class HeapSorting {
	public static void main(String[] args) {
		int[] arr = {4, 6, 8, 5, 9};
		heapSorting(arr);
	}

	public static void heapSorting(int[] arr) {
		int temp = 0;
		//System.out.println("堆排序");
		/*adjustHeap(arr, 1, arr.length);
		System.out.println("第一次调整，" + Arrays.toString(arr));

		adjustHeap(arr, 0, arr.length);
		System.out.println("第一次调整，" + Arrays.toString(arr));*/

		//1.将无序序列构建成一个堆，根据升序选择大顶堆，降序选择小顶堆
		for (int i = arr.length / 2 - 1; i >= 0; i--) {
			adjustHeap(arr, i, arr.length);
		}

		//2.将堆顶元素与末尾元素进行交换，并且将最大元素沉入数组尾端
		//3.重新调整结构，使其满足堆定义，然后继续交换堆顶元素与末尾元素，反复执行调整+交换步骤，直到整个序列有序为止
		//需要交换的次数 == 数组长度 - 1
		for (int j = arr.length - 1; j > 0; j--) {
			//交换
			temp = arr[j];
			//大顶堆的最大值就是根节点，也就是数组中的第一个元素
			arr[j] = arr[0];
			arr[0] = temp;

			//上述代码只是完成了第一个数的交换，仅仅只是找到了第一个最大值，还有第二个
			//从根节点0开始的，
			adjustHeap(arr, 0, j);
		}
		System.out.println("堆排序之后的数组=" + Arrays.toString(arr));
	}

	/**
	 * 从以i为父节点的子树中取出最大值并调整到顶端
	 *
	 * @param arr    需要被调整的数组
	 * @param i      数组中对应的非叶子节点的索引
	 * @param length 需要调整多少个节点
	 */
	public static void adjustHeap(int[] arr, int i, int length) {
		//保存非叶子节点索引的值
		int temp = arr[i];

		//k指向当前节点的左子节点
		for (int k = i * 2 + 1; k < length; k = k * 2 + 1) {
			//k + 1 < length
			//arr[k] < arr[k + 1]表示当前节点的左子节点的值小于当前节点的右子节点
			if ((k + 1) < length && arr[k] < arr[k + 1]) {
				k++;//k移动并且指向当前节点的右子节点
			}

			//如果右子节点的值大于非叶子节点的值
			if (arr[k] > temp) {
				//将当前节点的右子节点的值（较大值）赋值给当前非叶子节点
				arr[i] = arr[k];
				//非叶子节点继续指向当前的右子节点
				i = k;
			} else {
				break;
			}
		}
		//当for循环结束后，已经将以i为父节点的子树的最大值调整到了最顶端（局部）
		//将原来保存非叶子节点的值放置在调整后的位置
		arr[i] = temp;
	}
}
