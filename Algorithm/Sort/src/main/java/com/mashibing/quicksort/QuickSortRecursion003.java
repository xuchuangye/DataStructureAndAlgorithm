package com.mashibing.quicksort;

import com.mashibing.common.SortCommonUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 快速排序3.0版本
 * 时间复杂度：O(N * logN)
 *
 * 额外空间复杂度：最差情况O(N)
 * 举例：{1,2,3,4,5,6,7,...,N}
 * 即使是不使用递归，也要使用栈
 * 栈空间记录中间等于区的位置信息，所以最差情况下依然需要记录N,N-1,N-2,...,0，所以额外空间复杂度O(N)
 *
 * 额外空间复杂度：最好情况O(logN)
 * 最差和最好都是N分之一，所以最终额外空间复杂度O(logN)
 * @author xcy
 * @date 2022/4/19 - 9:49
 */
public class QuickSortRecursion003 {
	public static void main(String[] args) {
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
			quickSort(array);
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

		//测试时间
		int[] arr = SortCommonUtils.getArray();

		Date dateStart = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String start = simpleDateFormat.format(dateStart);
		System.out.printf("快速排序前的时间：\n%s\n", start);

		quickSort(arr);

		Date dateEnd = new Date();
		String end = simpleDateFormat.format(dateEnd);
		System.out.printf("快速排序后的时间：\n%s\n", end);
	}

	/**
	 * 快速排序3.0 --> 递归的方式
	 * @param arr 原始无序数组
	 */
	public static void quickSort(int[] arr) {
		//没有排序的必要
		if (arr == null || arr.length < 2) {
			return;
		}
		process(arr, 0, arr.length - 1);
	}

	/**
	 * 快速排序 --> 递归的方式
	 * @param arr 原始无序数组
	 * @param left 原始无序数组的左边界
	 * @param right 原始无序数组的右边界
	 */
	public static void process(int[] arr, int left, int right) {
		if (left >= right) {
			return;
		}
		SortCommonUtils.swap(arr, left + (int) (Math.random() * (right - left + 1)), right);
		//等于区的边界
		int[] equalArea = dutchFlag(arr, left, right);
		//因为等于区已经有序
		//所以对小于区进行递归排序
		process(arr, left, equalArea[0] - 1);
		//对大于区递归排序
		process(arr, equalArea[1] + 1, right);
	}

	/**
	 * 荷兰问题的划分
	 *
	 * @param arr   原始无序数组
	 * @param left  原始无序数组的左边界
	 * @param right 原始无序数组的右边界，arr[right]的值作为划分值
	 * @return 返回等于区的左边界和右边界
	 */
	public static int[] dutchFlag(int[] arr, int left, int right) {
		if (left > right) {
			return new int[]{-1, -1};
		}
		if (left == right) {
			return new int[]{left, right};
		}
		//小于区的右边界
		int less = left - 1;
		//大于区的左边界，最后一个数arr[right]不参与
		int more = right;
		//索引的起始位置
		int index = left;

		//index不能和大于区的左边界相遇
		while (index < more) {
			if (arr[index] < arr[right]) {
				//当前index索引上的值和小于区的下一个索引上的值进行交换
				//SortCommonUtils.swap(arr, index++, ++less);
				SortCommonUtils.swap(arr, index, less + 1);
				less++;
				index++;
			} else if (arr[index] == arr[right]) {
				//直接跳过
				index++;
			} else {
				//当前index索引上的值和大于区的上一个索引上的值进行交换
				//SortCommonUtils.swap(arr, index, --more);
				SortCommonUtils.swap(arr, index, more - 1);
				more--;
				//index不移动
			}
		}

		//因为more肯定会移动到小于区的右边界的下一个位置，也就是等于区的第一个位置
		//所以more和right进行交换
		SortCommonUtils.swap(arr, more, right);

		return new int[]{less + 1, more};
	}
}
