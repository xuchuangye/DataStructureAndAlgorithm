package com.mashibing.heapsort;

/**
 * 大根堆
 * @author xcy
 * @date 2022/4/19 - 16:14
 */
public class BiggerRootHeap {
	public static void main(String[] args) {

	}
	public static void swap(int[] arr, int i , int j) {
		int temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}

	/**
	 * 用户在堆结构中插入节点
	 * 该方法判断当前子节点和父节点的关系根据大根堆的规则能否进行交换
	 *
	 * 时间复杂度：O(logN)
	 * 两个限制条件：
	 * 1、如果当前节点已经是根节点，退出while循环
	 * 2、如果当前节点不是根节点，但是当前节点的权值比父节点的权值小，退出while循环
	 * @param arr 数组
	 * @param index 当前节点的索引
	 */
	public static void heapInsert(int[] arr, int index) {
		//1、判断当前节点是否是根节点
		//2、判断当前节点的值是否大于父节点的值
		while (arr[index] > arr[(index - 1) / 2]) {
			swap(arr, index, (index - 1) / 2);
			index = (index - 1) / 2;
		}
	}

	/**
	 * 用户能pop出大根堆中的最大值，并且依然能够维持大根堆的结构和规则
	 * 该方法判断当前父节点和子节点的关系根据大根堆的规则能否进行交换
	 *
	 * 时间复杂度：O(logN)
	 * @param arr 数组
	 * @param index 当前节点的索引
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
			swap(arr, maxIndex, index);
			//当前节点来到maxIndex
			index = maxIndex;
			//继续进行下一层的判断，从左子节点开始
			left = 2 * index + 1;
		}
	}
}
