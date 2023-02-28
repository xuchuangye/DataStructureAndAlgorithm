package com.mashibing.sort;

import com.mashibing.common.SortCommonUtils;

import java.util.LinkedList;
import java.util.Stack;

/**
 * 快速排序
 * 快速排序是对冒泡排序的一种改进
 * <p>
 * 快速排序的基本思想是：
 * 通过一趟排序将要排序的数据分割成独立的两部分，其中一部分的所有数据都比另外一部分的所有数据都要小，
 * 然后再按此方法对这两部分数据分别进行快速排序，整个排序过程可以递归进行，以此达到整个数据变成有序序列
 * <p>
 * 快速排序的稳定性：
 * 快速排序不能实现稳定性
 *
 * @author xcy
 * @date 2022/3/21 - 15:30
 */
public class QuickSorting {
	public static void main(String[] args) throws InterruptedException {
		//测试时间
		int arrayMaxSize = 100000000;
		int maxValue = 10000;
		int testTime = 1;
		int[] array = SortCommonUtils.generateRandomArray(arrayMaxSize, maxValue);

		System.out.println("测试开始！");
		long start = System.currentTimeMillis();
		for (int i = 0; i < testTime; i++) {
			quickSortingNoRecursionWithStack(array);
		}
		long end = System.currentTimeMillis();
		System.out.println("测试结束！");
		System.out.println("quickSortingNoRecursionWithStack()所花费的时间：" + (end - start));

		Thread.sleep(1000);

		System.out.println("测试开始！");
		start = System.currentTimeMillis();
		for (int i = 0; i < testTime; i++) {
			quickSortingNoRecursionWithList(array);
		}
		end = System.currentTimeMillis();
		System.out.println("测试结束！");
		System.out.println("quickSortingNoRecursionWithList()所花费的时间：" + (end - start));

		Thread.sleep(1000);

		System.out.println("测试开始！");
		start = System.currentTimeMillis();
		for (int i = 0; i < testTime; i++) {
			quickSortingRecursion(array);
		}
		end = System.currentTimeMillis();
		System.out.println("测试结束！");
		System.out.println("quickSortingRecursion()所花费的时间：" + (end - start));

		Thread.sleep(1000);

		System.out.println("测试开始！");
		start = System.currentTimeMillis();
		for (int i = 0; i < testTime; i++) {
			quickSort(array, 0, array.length - 1);
		}
		end = System.currentTimeMillis();
		System.out.println("测试结束！");
		System.out.println("quickSort()所花费的时间：" + (end - start));

		//splitNumber(arr);
		//splitNumber_(arr);

		/*//测试错误
		//测试次数
		int testTime = 10000000;
		//数组长度
		int arrayMaxSize = 100;
		//数组元素的值的范围
		int maxValue = 100;
		//是否测试成功，默认测试是成功的
		boolean success = true;
		for (int i = 1; i <= testTime; i++) {
			//生成随机长度值也随机的数组
			int[] array = SortCommonUtils.generateRandomArray(arrayMaxSize, maxValue);
			//拷贝数组
			int[] arr = SortCommonUtils.copyArray(array);

			//自己实现的排序方式
			quickSortingNoRecursionWithList(array);
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
	}

	/**
	 * TODO 代码有问题  --> 已经成功解决
	 * 快速排序 -> 从小到大
	 *
	 * @param arr   数组
	 * @param left  最左边
	 * @param right 最右边
	 */
	public static void quickSort(int[] arr, int left, int right) {
		//递归退出的重要条件
		if (left >= right) {
			return;
		}
		//记录最左边
		int l = left;
		//记录最右边
		int r = right;
		//中轴的值
		int pivot = arr[left + ((right - left) >> 1)];
		//int temp = 0;

		while (l < r) {
			//如果左边的值都小于pivot，继续后移
			while (arr[l] < pivot) {
				l++;
			}
			//如果右边的值都大于pivot，继续前移
			while (arr[r] > pivot) {
				r--;
			}
			//当退出循环时，最右边的索引达到最左边的临界点时，
			//证明pivot左边的值已经全部小于等于pivot，pivot右边的值已经全部大于等于pivot
			if (l == r) {
				break;
			}
			//如果仍然不满足上述的条件，则左边和右边的值进行交换
			swap(arr, l, r);

			//如果交换完之后，arr[l] == pivot，那么r -= 1，也就是向前移一步
			if (arr[l] == pivot) {
				r -= 1;
			}
			//如果交换完之后，arr[r] == pivot，那么l += 1，也就是向后移一步
			if (arr[r] == pivot) {
				l += 1;
			}
		}

		//上述代码可能会出现 l == r的情况
		/*if (l >= r) {
			break;
		}*/
		//如果l == r，必须l += 1，r -= 1，否则会出现栈溢出问题，因为没有可以退出递归的条件，所以会进入死递归
		if (l == r) {
			l ++;
			r --;
		}

		//向左递归
		if (left < r) {
			quickSort(arr, left, r);
		}

		//向右递归
		if (right > l) {
			quickSort(arr, l, right);
		}
	}


	/**
	 * 划分小于等于区和大于区
	 *
	 * @param arr
	 */
	public static void splitNumber(int[] arr) {
		//小于区
		int lessEqualRight = -1;
		int index = 0;
		int mostRight = arr.length - 1;
		while (index < arr.length) {
			if (arr[index] <= arr[mostRight]) {
				/*swap(arr, lessEqualRight + 1, index);
				lessEqualRight++;
				index++;*/
				swap(arr, ++lessEqualRight, index++);
			} else {
				index++;
			}
		}
	}

	/**
	 * 划分小于区，等于区，大于区
	 *
	 * @param arr
	 */
	public static void splitNumber_(int[] arr) {
		//小于区
		int lessRight = -1;
		int index = 0;
		//大于区
		int mostLeft = arr.length - 1;
		//当前索引 == 大于区的最左边时退出循环
		while (index < mostLeft) {
			//以数组的最后一个元素为大于区和小于区的划分点
			//如果当前索引的值小于数组的最后一个元素的值
			if (arr[index] < arr[arr.length - 1]) {
				//当前索引的值和小于区的下一个索引上的值做交换
				swap(arr, lessRight + 1, index);
				lessRight++;
				index++;
			}
			//如果当前索引的值大于数组的最后一个元素的值
			else if (arr[index] > arr[arr.length - 1]) {
				//当前索引的值和大于区的前一个索引上的 值做交换
				swap(arr, mostLeft - 1, index);
				mostLeft--;
			}
			//如果当前索引的值等于数组的最后一个元素的值，直接跳过，进行下一个
			else {
				index++;
			}
		}
		swap(arr, mostLeft, arr.length - 1);
	}


	/**
	 * 快速排序 -> 递归的方式
	 *
	 * @param arr 原始数组
	 */
	public static void quickSortingRecursion(int[] arr) {
		if (arr == null || arr.length < 2) {
			return;
		}
		process(arr, 0, arr.length - 1);
	}

	/**
	 * 核心逻辑
	 *
	 * @param arr
	 * @param left
	 * @param right
	 */
	public static void process(int[] arr, int left, int right) {
		if (left >= right) {
			return;
		}

		//等于区域
		int[] equals = partition(arr, left, right);
		//小于区域递归
		process(arr, left, equals[0] - 1);
		//大于区域递归
		process(arr, equals[1] + 1, right);
	}

	/**
	 * 等于区
	 */
	public static class EqualRegion {
		/**
		 * 等于区的左边界
		 */
		public int left;
		/**
		 * 等于区的右边界
		 */
		public int right;

		public EqualRegion(int l, int r) {
			this.left = l;
			this.right = r;
		}
	}

	/**
	 * 快速排序 -> 非递归的方式
	 *
	 * @param arr 原始数组
	 */
	public static void quickSortingNoRecursionWithStack(int[] arr) {
		if (arr == null || arr.length < 2) {
			return;
		}
		Stack<EqualRegion> equalRegions = new Stack<>();
		equalRegions.push(new EqualRegion(0, arr.length - 1));
		while (!equalRegions.isEmpty()) {
			EqualRegion curEqualRegion = equalRegions.pop();
			//等于区的左侧边界和右侧边界
			int[] equals = partition(arr, curEqualRegion.left, curEqualRegion.right);
			//等于区的左侧边界大于当前任务的最左边，说明有小于区
			if (equals[0] > curEqualRegion.left) {
				equalRegions.push(new EqualRegion(curEqualRegion.left, equals[0] - 1));
			}
			//等于区的右侧边界小于当前任务的最右边，说明有大于区
			if (equals[1] < curEqualRegion.right) {
				equalRegions.push(new EqualRegion(equals[1] + 1, curEqualRegion.right));
			}
		}
	}

	/**
	 * 快速排序 -> 非递归的方式
	 *
	 * @param arr 原始数组
	 */
	public static void quickSortingNoRecursionWithList(int[] arr) {
		if (arr == null || arr.length < 2) {
			return;
		}
		LinkedList<EqualRegion> stack = new LinkedList<>();
		stack.addLast(new EqualRegion(0, arr.length - 1));
		while (stack.size() != 0) {
			EqualRegion curEqualRegion = stack.pollLast();
			//等于区的左侧边界和右侧边界
			int[] equals = partition(arr, curEqualRegion.left, curEqualRegion.right);
			//等于区的左侧边界大于当前任务的最左边，说明有小于区
			if (equals[0] > curEqualRegion.left) {
				stack.addLast(new EqualRegion(curEqualRegion.left, equals[0] - 1));
			}
			//等于区的右侧边界小于当前任务的最右边，说明有大于区
			if (equals[1] < curEqualRegion.right) {
				stack.addLast(new EqualRegion(equals[1] + 1, curEqualRegion.right));
			}
		}
	}

	/**
	 * 从左侧边界left到右侧边界right上，划分小于区、等于区、大于区
	 *
	 * @param arr   原始数组
	 * @param left  左侧边界
	 * @param right 右侧边界
	 * @return 返回等于区的左侧边界和等于区的右侧边界的数组
	 */
	public static int[] partition(int[] arr, int left, int right) {
		if (left > right) {
			return new int[]{-1, -1};
		}
		if (left == right) {
			return new int[]{left, right};
		}
		//小于区的起始索引，也就是右边界
		int lessRight = left - 1;
		//大于区的起始索引，也就是左边界
		int greaterLeft = right;
		//当前索引，从left开始
		int index = left;
		//当前索引 == 大于区的最左边时退出循环
		while (index < greaterLeft) {
			//以数组的最后一个元素为大于区和小于区的划分点
			//如果当前索引的值小于right索引上的值
			if (arr[index] < arr[right]) {
				//当前索引的值和小于区的下一个索引上的值做交换
				swap(arr, lessRight + 1, index);
				//小于区的右边界继续往右扩
				lessRight++;
				//继续当前索引的下一个索引
				index++;
			}
			//如果当前索引的值大于right索引上的值
			else if (arr[index] > arr[right]) {
				//当前索引的值和大于区的前一个索引上的值做交换
				swap(arr, greaterLeft - 1, index);
				//大于区的左边界继续往左扩
				greaterLeft--;
			}
			//如果当前索引的值等于数组的最后一个元素的值，直接跳过，进行下一个
			else {
				index++;
			}
		}
		swap(arr, greaterLeft, right);
		return new int[]{lessRight + 1, greaterLeft};
	}

	/**
	 * 交换数组中的两个变量
	 *
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
