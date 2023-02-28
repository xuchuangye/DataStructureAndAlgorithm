package com.mashibing.common;

/**
 * BFPRT算法的工具类
 * @author xcy
 * @date 2022/5/22 - 9:43
 */
public class BFPRTUtils {
	/**
	 * 生成随机长度的随机值的数组
	 *
	 * @param arrayMaxSize 随机长度
	 * @param maxValue  随机值
	 * @return 返回随机长度的元素都是随机值的数组
	 */
	public static int[] generateRandomArray(int arrayMaxSize, int maxValue) {
		int[] arr = new int[(int) (Math.random() * arrayMaxSize) + 1];

		for (int i = 0; i < arr.length; i++) {
			arr[i] = (int) (Math.random() * maxValue) - (int) (maxValue * Math.random());
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

	// for test
	public static boolean isEqual(int[] arr1, int[] arr2) {
		if ((arr1 == null && arr2 != null) || (arr1 != null && arr2 == null)) {
			return false;
		}
		if (arr1 == null && arr2 == null) {
			return true;
		}
		if (arr1.length != arr2.length) {
			return false;
		}
		for (int i = 0; i < arr1.length; i++) {
			if (arr1[i] != arr2[i]) {
				return false;
			}
		}
		return true;
	}

	// for test
	public static void printArray(int[] arr) {
		if (arr == null) {
			return;
		}
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}
}
