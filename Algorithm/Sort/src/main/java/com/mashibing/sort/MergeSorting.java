package com.mashibing.sort;

import com.mashibing.common.SortCommonUtils;

/**
 * 归并排序
 * 归并排序（MERGE-SORT）是利用归并的思想实现的排序方法
 * 该算法采用经典的分治（divide-and-conquer）策略（分治法将问题分(divide)成一些小的问题然后递归求解，
 * 而治(conquer)的阶段则将分的阶段得到的各答案"修补"在一起，即分而治之)。
 * <p>
 * 归并排序的稳定性：
 * 归并排序可以实现稳定性，==时，先将左边数组赋值到拷贝数组中，然后在将右边数组赋值到拷贝数组中
 *
 * @author xcy
 * @date 2022/3/21 - 17:25
 */
public class MergeSorting {
	public static void main(String[] args) {
		/*int[] arr = {80, 43, 5, 27, 1, 32, 6, 2, 18, 24, 15, 70, 11, 30, 66, 20};
		// int[] arr = SortCommonUtils.getArray();

		*//*int[] temp = new int[arr.length];
		int left = 0;
		int right = arr.length - 1;
		mergeSort(arr, left, right, temp);

		System.out.println("归并排序的情况");
		System.out.println(Arrays.toString(arr));*//*

		Date dateStart = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String start = simpleDateFormat.format(dateStart);
		System.out.printf("归并排序前的时间：\n%s\n", start);

		System.out.println();
		*//*int[] temp = new int[arr.length];
		int left = 0;
		int right = arr.length - 1;
		mergeSort(arr, left, right, temp);*//*
		 *//*int left = 0;
		int right = arr.length - 1;
		mergeSortingRecursion(arr, left, right);*//*
		mergeSortNoRecursion(arr);

		Date dateEnd = new Date();
		String end = simpleDateFormat.format(dateEnd);
		System.out.printf("归并排序后的时间：\n%s\n", end);

		System.out.println(Arrays.toString(arr));*/

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

			//int[] temp = new int[arr.length];
			//自己实现的排序方式
			//mergeSortRecursion(array, 0, array.length - 1, temp);
			//mergeSortingRecursion(array, 0, array.length - 1);
			mergeSortNoRecursion(array);
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
	 * 效率高一点
	 * 数组元素的分和合并 -> 递归的方式
	 *
	 * @param arr   准备排序的原始数组
	 * @param left  左边有序序列的起始位置
	 * @param right 右边有序序列的起始位置
	 * @param temp  额外空间，用于拷贝数据
	 */
	public static void mergeSortRecursion(int[] arr, int left, int right, int[] temp) {
		if (left < right) {
			//中间索引
			//int mid = (left + right) / 2;
			//int mid = left + (right - left) / 2;
			int mid = left + ((right - left) >> 1);
			//向左递归进行分
			mergeSortRecursion(arr, left, mid, temp);
			//向右递归进行分
			mergeSortRecursion(arr, mid + 1, right, temp);
			//分之后，进行合并
			merge(arr, left, mid, right, temp);
		}
	}


	/**
	 * 数组元素治的阶段以及数据拷贝
	 *
	 * @param arr   准备排序的原始数组
	 * @param left  左边有序序列的起始位置
	 * @param mid   中间
	 * @param right 右边有序序列的起始位置
	 * @param temp  额外空间，用于拷贝数据
	 */
	public static void merge(int[] arr, int left, int mid, int right, int[] temp) {
		//记录左边有序序列的起始位置
		int i = left;
		//记录右边有序序列的起始位置
		int j = mid + 1;
		//记录temp数组的当前索引
		int t = 0;

		//左边有序序列从i == left到mid，右边有序序列从j == mid + 1到right
		while (i <= mid && j <= right) {
			//如果左边有序序列的当前元素小于等于右边有序序列的当前元素
			if (arr[i] <= arr[j]) {
				//那么就将左边有序序列的当前元素填充到temp数组中
				temp[t] = arr[i];
				//当前索引t已经有元素了，加1继续指向下一个索引
				t++;
				//左边有序序列的当前索引i元素已经填充到temp数组中了，加1继续指向下一个索引
				i++;
			} else {
				//反之，将右边有序序列的当前元素填充到temp数组中
				temp[t] = arr[j];
				//当前索引t已经有元素了，加1继续指向下一个索引
				t++;
				//右边有序序列的当前索引j元素已经填充到temp数组中了，加1继续指向下一个索引
				j++;
			}
		}

		//如果左边有序序列有剩余的元素，就将剩余的元素全部填充到temp数组中
		while (i <= mid) {
			//那么就将左边有序序列的当前元素填充到temp数组中
			temp[t] = arr[i];
			//当前索引t已经有元素了，加1继续指向下一个索引
			t++;
			//左边有序序列的当前索引i元素已经填充到temp数组中了，加1继续指向下一个索引
			i++;
		}

		//反之，如果右边的有序序列还有剩余的元素，就将剩余的元素全部填充到temp数组中
		while (j <= right) {
			temp[t] = arr[j];
			t++;
			j++;
		}

		//填充完毕之后，就将temp数组中元素拷贝到原始数组arr中
		t = 0;
		//原始数组的索引和temp数组的索引对应
		int tempLeft = left;
		//System.out.println("tempLeft=" +tempLeft + ",right=" +right);
		//从temp数组的左边开始，直到数组的右边结束
		while (tempLeft <= right) {
			//将temp数组的元素依次拷贝到原始数组中
			arr[tempLeft] = temp[t];
			//temp数组的当前元素的索引++
			t++;
			//原始数组的当前元素的索引++
			tempLeft++;
		}
	}

	/**
	 * 归并排序 -> 递归的方式
	 * <p>
	 * 时间复杂度：O(NlogN) -> 线性对数阶
	 * 使用Master公式计算时间复杂度
	 * T(N) = 2 * T(N / 2) + O(N) log以b为底数a为真数 == d，所以时间复杂度为：N ^ d * logN
	 *
	 * @param arr   原始无序数组
	 * @param left  原始无序数组的左边界
	 * @param right 原始无序数组的右边界
	 */
	public static void mergeSortingRecursion(int[] arr, int left, int right) {
		//防止出现栈溢出的异常
		if (left >= right) {
			return;
		}
		//数组分为左边数组和右边数组两组，左边数组和右边数组都已经有序
		process(arr, left, right);
		//让两个数组合并
		merge(arr, left, right);
	}

	/**
	 * 数组分为左边数组和右边数组，递归使其两个数组有序
	 *
	 * @param arr   原始无序数组
	 * @param left  原始无序数组的左边界
	 * @param right 原始无序数组的右边界
	 */
	public static void process(int[] arr, int left, int right) {
		//防止出现栈溢出的异常
		if (left >= right) {
			return;
		}
		//取出中间索引
		int mid = left + (right - left) / 2;
		//数组分为左边数组和右边数组两组
		//1、递归让左边数组有序
		process(arr, left, mid);
		//2、递归让右边数组有序
		process(arr, mid + 1, right);
	}

	/**
	 * 数组分为左边数组和右边数组，将两个数组进行合并
	 *
	 * @param arr   原始无序数组
	 * @param left  原始无序数组的左边界
	 * @param right 原始无序数组的右边界
	 */
	public static void merge(int[] arr, int left, int right) {
		//防止出现栈溢出的异常
		if (left >= right) {
			return;
		}
		//取出中间索引
		int mid = left + (right - left) / 2;
		//让cur1指向左边有序数组
		int cur1 = left;
		//让cur2指向右边有序数组
		int cur2 = mid + 1;
		//拷贝数组temp
		//int[] temp = new int[right - left + 1];
		int[] temp = new int[arr.length];
		//循环指向temp数组的索引
		int index = 0;
		//cur1不能超出mid，cur2不能超出right，边界问题
		while (cur1 <= mid && cur2 <= right) {
			temp[index++] = arr[cur1] <= arr[cur2] ? arr[cur1++] : arr[cur2++];
		}

		//上边的循环结束之后，左边有序数组有剩余或者右边有序数组有剩余
		while (cur1 <= mid) {
			temp[index++] = arr[cur1++];
		}

		while (cur2 <= right) {
			temp[index++] = arr[cur2++];
		}

		//最终将temp数组赋值给arr
		System.arraycopy(temp, 0, arr, 0, temp.length);
	}

	/**
	 * 效率还行
	 * 合并并排序左侧数组和右侧数组，最终合并排序数组 -> 非递归的方式
	 * <p>
	 * 时间复杂度：O(NlogN)
	 *
	 * @param arr 原始数组
	 */
	public static void mergeSortNoRecursion(int[] arr) {
		if (arr == null || arr.length < 2) {
			return;
		}

		//步长，从1开始，每次乘2
		int step = 1;
		//数组的长度
		int length = arr.length;

		while (step < length) {
			//第一个步长为step的左边的数组起始索引
			int left = 0;
			//第一个步长为step的左边的数组结束索引
			int mid = 0;
			//第一个步长为step的右边的数组起始索引
			int right = 0;
			while (left < length) {
				//从left开始，到length - 1结束，能否满足step个元素进行合并排序
				//length - 1 - left + 1   --> length - left
				if (length - left >= step) {
					//如果满足上述 条件，那么第一个步长为step的左边的数组结束索引就已经找到
					mid = left + step - 1;
				} else {
					mid = length - 1;
				}
				//mid == length - 1，表示后续已经没有元素可以进行合并排序了，直接退出循环
				if (mid == length - 1) {
					break;
				}

				//length - 1 - mid + 1 - 1 --> length - 1 - mid
				//从mid + 1开始，到length - 1结束，能否满足step个元素进行合并排序
				if (length - 1 - mid >= step) {
					//如果满足上述 条件，那么第一个步长为step的右边的数组起始索引就已经找到
					right = mid + 1 + step - 1;
				} else {
					right = length - 1;
				}
				//合并排序
				merge(arr, left, right);
				//如果right已经是最后一个元素，那么就表示后续已经没有元素可以合并排序，直接退出循环
				if (right == length - 1) {
					break;
				} else {
					//否则right + 1就作为第二个左边数组的起始索引
					left = right + 1;
				}
			}

			//防止step * 2 超出Integer.MAX_VALUE，所以先判断step能否继续乘以2
			//注意length >> 1可能存在向下取整的问题，所以step == (length / 2)会导致最后一组没有进行合并交换
			//if (step > (length / 2)) {
			if (step > (length >> 1)) {
				break;
			} else {
				//step *= 2;
				step <<= 1;
			}
		}
	}

}
