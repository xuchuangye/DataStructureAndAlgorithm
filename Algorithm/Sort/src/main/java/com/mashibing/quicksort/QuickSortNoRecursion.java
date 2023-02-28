package com.mashibing.quicksort;

import com.mashibing.common.SortCommonUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Stack;

/**
 * 快速排序4.0版本 --> 非递归的方式
 * 时间复杂度：O(N * logN)
 *
 * @author xcy
 * @date 2022/4/19 - 9:49
 */
public class QuickSortNoRecursion {
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
	 * 快速排序4.0 --> 非递归的方式
	 *
	 * @param arr 原始无序数组
	 */
	public static void quickSort(int[] arr) {
		//没有排序的必要
		if (arr == null || arr.length < 2) {
			return;
		}
		//随机生成一个索引和数组的最后一个元素位置进行交换
		SortCommonUtils.swap(arr, (int)(Math.random() * (arr.length)), arr.length - 1);
		//等于区，并且等于区已经有序
		int[] equalArea = dutchFlag(arr, 0, arr.length - 1);
		//等于区的左边界
		int equalLeft = equalArea[0];
		//等于区的右边界
		int equalRight = equalArea[1];
		//创建一个栈，存放处理数组中left到right范围的排序任务
		Stack<Task> tasks = new Stack<>();
		//添加两个任务到栈中
		tasks.push(new Task(0, equalLeft - 1));
		tasks.push(new Task(equalRight + 1, arr.length - 1));
		//判断栈是否为空，如果不为空
		while (!tasks.isEmpty()) {
			//取出处理数组中left到right范围的排序任务类
			Task task = tasks.pop();
			if (task.left < task.right) {
				//随机生成一个当前处理的任务的索引和当前处理任务的最后一个索引进行交换
				SortCommonUtils.swap(arr, task.left + (int)(Math.random() * (task.right - task.left + 1)), task.right);
				//处理数组中left到right范围的排序的子任务的等于区
				equalArea = dutchFlag(arr, task.left, task.right);
				//子任务等于区的左边界
				equalLeft = equalArea[0];
				//子任务等于区的右边界
				equalRight = equalArea[1];
				//将子任务添加到Stack中
				tasks.push(new Task(task.left, equalLeft - 1));
				tasks.push(new Task(equalRight + 1, task.right));
			}
		}
	}

	/**
	 * 处理数组中left到right范围的排序任务
	 */
	public static class Task {
		public int left;
		public int right;

		public Task(int l, int r) {
			this.left = l;
			this.right = r;
		}
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
