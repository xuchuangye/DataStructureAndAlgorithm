package com.mashibing.common;

import java.util.Arrays;

/**
 * 排序算法的工具类
 * <p>
 * 对数器
 * 1、需要测试的方法A，也就是自己实现的方法
 * 2、实现复杂度不好但是容易实现的方法B --> comparator(int[] arr)，系统提供的排序算法
 * 3、实现随机样本产生器
 * 3.1、返回随机长度并且随机值的数组的方法 --> generateRandomArray(int length, int value)
 * 3.2、拷贝数组的方法 --> copyArray(int[] arr)
 * 4、将方法A和方法B测试相同的随机样本，查看结果是否一致的方法 --> isEqual(int[] arr1, int[] arr2)
 * 5、如果两个方法测试相同的随机样本，但是测试的结果不一致，打印样本进行人工干预 --> printArray(int[] arr)并且修改对方法A和方法B
 * 6、当随机样本量非常大时两个方法测试的结果依然一直，证明自己实现的方法A是正确的
 *
 * @author xcy
 * @date 2022/3/20 - 16:08
 */
public class SortCommonUtils {
	private static final int ARRAY_LENGTH = 1000000;

	/**
	 * 生成随机值的数组
	 *
	 * @return 数组
	 */
	public static int[] getArray() {
		int[] arr = new int[ARRAY_LENGTH];

		for (int i = 0; i < ARRAY_LENGTH - 1; i++) {
			arr[i] = (int) (Math.random() * 80000);
		}
		return arr;
	}

	/**
	 * 数组元素之间的交换
	 *
	 * @param arr 数组
	 * @param i   arr[i]的索引
	 * @param j   arr[j]的索引
	 */
	public static void swap(int[] arr, int i, int j) {
		arr[i] = arr[i] ^ arr[j];
		arr[j] = arr[i] ^ arr[j];
		arr[i] = arr[i] ^ arr[j];
	}

	/**
	 * 生成随机长度的随机值的数组
	 *
	 * @param length 随机长度
	 * @param value  随机值
	 * @return 返回随机长度的元素都是随机值的数组
	 */
	public static int[] generateRandomArray(int length, int value) {
		int[] arr = new int[(int) ((Math.random()) * length + 1)];

		for (int i = 0; i < arr.length; i++) {
			arr[i] = (int) ((Math.random() + 1) * value);
		}
		return arr;
	}

	/**
	 * 拷贝数组
	 *
	 * @param arr 准备拷贝的数组
	 * @return 拷贝之后的新数组
	 */
	public static int[] copyArray(int[] arr) {
		int[] result = new int[arr.length];

		/*for (int i = 0; i < arr.length; i++) {
			result[i] = arr[i];
		}*/
		System.arraycopy(arr, 0, result, 0, arr.length);
		return result;
	}

	/**
	 * 系统比较器 --> 系统提供的排序方法
	 *
	 * @param arr 准备
	 */
	public static void sort(int[] arr) {
		Arrays.sort(arr);
	}

	/**
	 * 判断数组arr1和数组arr2中的所有元素是否都相等
	 *
	 * @param arr1 数组arr1
	 * @param arr2 数组arr2
	 * @return 如果数组arr1和arr2的所有元素都相等则返回true，否则返回false
	 */
	public static boolean isEqual(int[] arr1, int[] arr2) {
		if ((arr1 == null && arr2 != null) || (arr1 != null && arr2 == null)) {
			return false;
		}
		if (arr1 == null && arr2 == null) {
			return true;
		}
		//数组arr1和arr2数组的长度必须一致
		if (arr1.length != arr2.length) {
			return false;
		}
		//数组arr1和arr2数组中的元素只要有一个不相等就返回false
		for (int i = 0; i < arr1.length; i++) {
			if (arr1[i] != arr2[i]) {
				return false;
			}
		}

		return true;
	}

	/**
	 * 打印输出数组
	 *
	 * @param arr 数组
	 */
	public static void printArray(int[] arr) {
		System.out.println(Arrays.toString(arr));
	}

	/**
	 * 查找并返回最左侧大于指定值的索引
	 *
	 * @param arr
	 * @param value
	 * @return
	 */
	public static int greaterValueLeftIndex(int[] arr, int value) {
		int index = -1;
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] >= value) {
				index = i;
				break;
			}
		}
		return index;
	}

	/**
	 * 生成随机长度随机值并且相邻两个元素的值不相等的数组
	 *
	 * @param length
	 * @param value
	 * @return
	 */
	public static int[] randomArray(int length, int value) {
		int len = (int) ((Math.random() + 1) * length);
		int[] array = new int[len];
		if (len > 0) {
			array[0] = (int) (Math.random() * value);
			for (int i = 1; i < array.length; i++) {
				do {
					array[i] = (int) (Math.random() * value);
				} while (array[i] == array[i - 1]);
			}
		}
		return array;
	}

	public static boolean check(int[] arr, int minIndex) {
		if (arr == null || arr.length == 0) {
			return minIndex == -1;
		}
		int left = minIndex - 1;
		int right = minIndex + 1;
		boolean leftBig = left >= 0 ? arr[left] > arr[minIndex] : true;
		boolean rightBig = right < arr.length ? arr[right] > arr[minIndex] : true;
		return leftBig && rightBig;
	}
}
