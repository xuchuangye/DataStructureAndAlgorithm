package com.mashibing.sort;

import com.mashibing.common.SortCommonUtils;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * 基数排序
 * 基数排序（radix sort）属于“分配式排序”（distribution sort），又称“桶子法”（bucket sort）或bin sort，
 * 顾名思义，它是通过键值的各个位的值，将要排序的元素分配至某些“桶”中，达到排序的作用
 * <p>
 * 基数排序基本思想：
 * 将所有待比较数值统一为同样的数位长度，数位较短的数前面补零。然后，从最低位开始，依次进行一次排序。
 * 这样从最低位排序一直到最高位排序完成以后, 数列就变成一个有序序列
 * <p>
 * 基数排序的说明：
 * 基数排序是对传统桶排序的扩展，速度很快.
 * 基数排序是经典的空间换时间的方式，占用内存很大, 当对海量数据排序时，容易造成 OutOfMemoryError 。
 * 基数排序是稳定的。
 * 基数排序是效率高的稳定性排序法
 * <p>
 * 注意事项：
 * 1.基数排序有限制：必须是十进制的正整数，如果是负数，需要进行修改，比较麻烦
 * 2.基数排序的数据源不能过大，否则会抛出错误
 * Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
 * <p>
 * 时间复杂度：O(N * log 以10为底max为真数)，max表示数组中的最大值
 *
 * @author xcy
 * @date 2022/3/22 - 9:20
 */
public class RadixSorting {
	public static void main(String[] args) {
		int[] arr = {53, 3, 542, 748, 14, 214};
		radixSorting(arr);
		System.out.println(Arrays.toString(arr));
		int digit = getDigit(arr[3], 2);
		System.out.println(digit);
		/*int[] arr = SortCommonUtils.getArray();

		Date dateStart = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String start = simpleDateFormat.format(dateStart);
		System.out.printf("基数排序前的时间：\n%s\n", start);
		radixSort(arr);//5s
		//radixSorting(arr);//24s

		Date dateEnd = new Date();
		String end = simpleDateFormat.format(dateEnd);
		System.out.printf("基数排序后的时间：\n%s\n", end);*/


		//测试错误
		/*//测试次数
		int testTime = 1;
		//数组长度
		int length = 100;
		//数组元素的值的范围
		int value = 100;
		//是否测试成功，默认测试是成功的
		boolean success = true;
		for (int i = 1; i <= testTime; i++) {
			//生成随机长度值也随机的数组
			int[] array = SortCommonUtils.getArray();
			//拷贝数组
			int[] arr = SortCommonUtils.copyArray(array);

			//自己实现的排序方式
			radixSorting(array);
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

		//System.out.println(Arrays.toString(arr));
		/*
		//1、基数排序就是利用空间换时间
		//2、所以需要额外创建一个二维数组，表示10个桶，数组的每一个元素都表示一个一维数组，也就是桶
		//3、因为在极端情况下，可能所有数的位数都一样或者所有数的某一位的值都一样，所以每一个桶的最大元素为数组的最大长度arr.length
		int[][] bucket = new int[10][arr.length];

		//1、创建一个一维数组，数组的索引表示哪一个桶，索引对应的元素值记录每个桶中有多少个元素
		//bucketElementCounts[0]表示bucket[0]桶中存放元素的个数
		int[] bucketElementCounts = new int[10];


		//针对每个元素的每一位进行排序处理
		for (int j = 0; j < arr.length; j++) {
			//既可以表示每一个桶，也可以表示桶中的元素个数
			int digitOfElement = arr[j] / 1 % 10;
			//第一个digitOfElement表示每一个桶，第二个digitOfElement表示桶中的元素个数
			bucket[digitOfElement][bucketElementCounts[digitOfElement]]  = arr[j];
			//因为可能会存在有一部分的数的位数的值是相同的，所以会存放在同一个桶中，计数加1
			bucketElementCounts[digitOfElement]++;
		}


		//按照桶的顺序（一维数组的索引依次取出数据，放入到原来的数组中）
		int index = 0;
		//遍历每一个桶，并且将桶中的数据放入到原数组
		for (int k = 0; k < bucketElementCounts.length; k++) {
			//判断记录每一个桶中的元素是否不为0，表示有数据，才将桶中的元素放入原数组
			if (bucketElementCounts[k] != 0) {
				//循环遍历第k个桶，也就是第k个一维数组，每个桶中记录了多少个元素，就循环遍历多少次将值取出来
				for (int l = 0; l < bucketElementCounts[k]; l++) {
					//取出元素之后放入到原数组中，也就是第k个桶的第l个元素
					//原数组的索引加1
					arr[index++] = bucket[k][l];
				}
			}

			//一定要将记录桶中的元素个数的数组bucketElementCounts置空，因为下一次该数组还需要使用
			//并且bucketElementCounts[k]当前循环中的数据已经都记录到原数组中，该数组中的元素就不需要了
			bucketElementCounts[k] = 0;
		}


		System.out.println("第1轮排序的情况");
		System.out.println(Arrays.toString(arr));
		//针对每个元素的每一位进行排序处理
		for (int j = 0; j < arr.length; j++) {
			//既可以表示每一个桶，也可以表示桶中的元素个数
			int digitOfElement = arr[j] / 10 % 10;
			//第一个digitOfElement表示每一个桶，第二个digitOfElement表示桶中的元素个数
			bucket[digitOfElement][bucketElementCounts[digitOfElement]]  = arr[j];
			//因为可能会存在有一部分的数的位数的值是相同的，所以会存放在同一个桶中，计数加1
			bucketElementCounts[digitOfElement]++;
		}


		//按照桶的顺序（一维数组的索引依次取出数据，放入到原来的数组中）
		index = 0;
		//遍历每一个桶，并且将桶中的数据放入到原数组
		for (int k = 0; k < bucketElementCounts.length; k++) {
			//判断记录每一个桶中的元素是否不为0，表示有数据，才将桶中的元素放入原数组
			if (bucketElementCounts[k] != 0) {
				//循环遍历第k个桶，也就是第k个一维数组
				for (int l = 0; l < bucketElementCounts[k]; l++) {
					//取出元素放入到原数组中
					arr[index++] = bucket[k][l];
				}
			}

			//一定要将记录桶中的元素个数的数组bucketElementCounts置空，因为下一次该数组还需要使用
			//并且bucketElementCounts[k]当前循环中的数据已经都记录到原数组中，该数组中的元素就不需要了
			bucketElementCounts[k] = 0;
		}


		System.out.println("第2轮排序的情况");
		System.out.println(Arrays.toString(arr));

		//针对每个元素的每一位进行排序处理
		for (int j = 0; j < arr.length; j++) {
			//既可以表示每一个桶，也可以表示桶中的元素个数
			int digitOfElement = arr[j] / 100 % 10;
			//第一个digitOfElement表示每一个桶，第二个digitOfElement表示桶中的元素个数
			bucket[digitOfElement][bucketElementCounts[digitOfElement]]  = arr[j];
			//因为可能会存在有一部分的数的位数的值是相同的，所以会存放在同一个桶中，计数加1
			bucketElementCounts[digitOfElement]++;
		}


		//按照桶的顺序（一维数组的索引依次取出数据，放入到原来的数组中）
		index = 0;
		//遍历每一个桶，并且将桶中的数据放入到原数组
		for (int k = 0; k < bucketElementCounts.length; k++) {
			//判断记录每一个桶中的元素是否不为0，表示有数据，才将桶中的元素放入原数组
			if (bucketElementCounts[k] != 0) {
				//循环遍历第k个桶，也就是第k个一维数组
				for (int l = 0; l < bucketElementCounts[k]; l++) {
					//取出元素放入到原数组中
					arr[index++] = bucket[k][l];
				}
			}

			//一定要将记录桶中的元素个数的数组bucketElementCounts置空，因为下一次该数组还需要使用
			//并且bucketElementCounts[k]当前循环中的数据已经都记录到原数组中，该数组中的元素就不需要了
			bucketElementCounts[k] = 0;
		}


		System.out.println("第3轮排序的情况");
		System.out.println(Arrays.toString(arr));
		 */
	}

	/**
	 * 基数排序
	 *
	 * @param arr 原始无序数组
	 */
	public static void radixSort(int[] arr) {
		int max = arr[0];
		//计算数组中的最大值
		for (int value : arr) {
			if (max < value) {
				max = value;
			}
		}

		//计算数组中最大值的位数
		int maxLength = (max + "").length();

		//1、基数排序就是利用空间换时间
		//2、所以需要额外创建一个二维数组，表示10个桶，数组的每一个元素都表示一个一维数组，也就是桶
		//3、因为在极端情况下，可能所有数的位数都一样或者所有数的某一位的值都一样，所以每一个桶的最大元素为数组的最大长度arr.length
		int[][] bucket = new int[10][arr.length];

		//1、创建一个一维数组，bucketElementCounts[0]表示bucket[0]，桶中记录元素存放的个数
		int[] bucketElementCounts = new int[10];
		//基数排序根据数组中最大值的位数进行遍历
		for (int i = 0, n = 1; i < maxLength; i++, n *= 10) {

			//针对每个元素对应的位数进行排序处理，第一次是从个位，第二次从十位，第三次从百位
			for (int j = 0; j < arr.length; j++) {
				//digitOfElement既可以表示每一个桶，也可以表示桶中的元素个数
				int digitOfElement = arr[j] / n % 10;
				//将当前的元素放入对应的桶中
				//第一个digitOfElement表示每一个桶，第二个digitOfElement表示桶中的元素个数
				bucket[digitOfElement][bucketElementCounts[digitOfElement]] = arr[j];
				//因为可能会存在有一部分的数的位数的值是相同的，所以元素会存放在同一个桶中，桶中记录元素存放的个数加1
				bucketElementCounts[digitOfElement]++;
			}


			//按照桶的顺序（一维数组的索引依次取出数据，放入到原来的数组中）
			int index = 0;
			//遍历每一个桶，并且将桶中的数据放入到原数组
			for (int k = 0; k < bucketElementCounts.length; k++) {
				//判断记录每一个桶中的元素是否不为0，表示有数据，才将桶中的元素放入原数组
				if (bucketElementCounts[k] != 0) {
					//循环遍历第k个桶，也就是第k个一维数组，每个桶中记录了多少个元素，就循环遍历多少次将值取出来
					for (int l = 0; l < bucketElementCounts[k]; l++) {
						//取出元素之后放入到原数组中，也就是第k个桶的第l个元素
						//原数组的索引加1
						arr[index++] = bucket[k][l];
					}
				}

				//一定要将记录桶中的元素个数的数组bucketElementCounts置空，因为下一次该数组还需要使用
				//并且bucketElementCounts[k]当前循环中的数据已经都记录到原数组中，该数组中的元素就不需要了
				bucketElementCounts[k] = 0;
			}
			//System.out.println("第" + (i + 1) + "次排序后的情况");
			//System.out.println(Arrays.toString(arr));
		}
	}

	/**
	 * 基数排序
	 *
	 * @param arr 原始无序数组
	 */
	public static void radixSorting(int[] arr) {
		if (arr == null || arr.length < 2) {
			return;
		}
		radixSorting(arr, 0, arr.length - 1, maxBit(arr));
	}

	/**
	 * 获取数组中最大值的十进制有多少位
	 *
	 * @param arr 原始无序数组
	 * @return 返回数组中最大值的十进制有多少位
	 */
	private static int maxBit(int[] arr) {
		int max = Integer.MIN_VALUE;
		for (int element : arr) {
			max = Math.max(max, element);
		}
		int ans = 0;
		while (max != 0) {
			ans++;
			max /= 10;
		}
		return ans;
	}

	/**
	 * @param arr   原始无序数组
	 * @param left  排序的左边界
	 * @param right 排序的右边界
	 * @param digit 数组中最大值的十进制总共多少位
	 */
	public static void radixSorting(int[] arr, int left, int right, int digit) {
		final int radix = 10;

		//int j = 0;
		//left到right之间有多少个数就创建相等的辅助空间
		int[] temp = new int[right - left + 1];
		//有多少位就循环多少次
		for (int k = 1; k <= digit; k++) {
			//10个空间，表示每一个数的十进制的每一位出现0-9中的任意一个数的次数，并进行累加
			//count[0] 当前十进制位(k位)是0的数字有多少个
			//count[1] 当前十进制位(k位)是1的数字有多少个
			//count[2] 当前十进制位(k位)是2的数字有多少个
			//count[3] 当前十进制位(k位)是3的数字有多少个
			//...
			int[] count = new int[radix];


			for (int i = left; i <= right; i++) {
				//103 1 3
				//209 1 9
				//获取当前数组元素的当前十进制位上的数是多少
				int num = getDigit(arr[i], k);
				//累加到count数组中
				count[num]++;
			}

			//
			for (int i = 1; i < radix; i++) {
				//count[0] 当前十进制位(k位)是(0)的数字共有多少个
				//count[1] 当前十进制位(k位)是(0、1)的数字共有多少个
				//count[2] 当前十进制位(k位)是(0、1、2)的数字共有多少个
				//count[3] 当前十进制位(k位)是(0、1、2、3)的数字共有多少个
				//...
				count[i] = count[i] + count[i - 1];
			}
			//从右往左遍历
			for (int i = right; i >= left; i--) {
				//获取当前数组元素的当前位上的 数是多少
				int num = getDigit(arr[i], k);
				//如果是5，那么范围是0~4，并且是从右往左遍历，所以是count[j] - 1，也就是在temp[count[j] - 1]
				temp[count[num] - 1] = arr[i];
				//需要减1
				count[num]--;
			}

			for (int i = left, j = 0; i <= right; i++, j++) {
				arr[i] = temp[j];
			}
		}
	}

	/**
	 * 获取当前元素的当前十进制位上的数是多少
	 * @param element 表示数组中的元素
	 * @param d 表示位数，1表示个位，2表示十位，3表示百位，...
	 * @return 返回当前元素的当前十进制位上的数是多少
	 */
	public static int getDigit(int element, int d) {
		return ((element / ((int)Math.pow(10, d - 1))) % 10);
	}
}
