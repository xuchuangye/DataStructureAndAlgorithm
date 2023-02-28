package com.mashibing.common;

import java.util.Arrays;

/**
 * @author xcy
 * @date 2022/7/10 - 14:37
 */
public class TestUtils {
	// for test
	public static int test(int[] arr, int L) {
		int max = 0;
		for (int i = 0; i < arr.length; i++) {
			int pre = i - 1;
			while (pre >= 0 && arr[i] - arr[pre] <= L) {
				pre--;
			}
			max = Math.max(max, i - pre);
		}
		return max;
	}

	// for test
	public static int[] generateArray(int len, int max) {
		int[] ans = new int[(int) (Math.random() * len) + 1];
		for (int i = 0; i < ans.length; i++) {
			ans[i] = (int) (Math.random() * max);
		}
		Arrays.sort(ans);
		return ans;
	}


	// 为了测试
	public static String randomString(int maxLen) {
		char[] str = new char[(int) (Math.random() * maxLen)];
		for (int i = 0; i < str.length; i++) {
			str[i] = Math.random() < 0.5 ? 'G' : 'B';
		}
		return String.valueOf(str);
	}

	// 为了测试
	public static int[] randomArray(int len, int maxValue) {
		int[] ans = new int[len];
		for (int i = 0; i < len; i++) {
			ans[i] = (int) (Math.random() * maxValue) + 1;
		}
		return ans;
	}

	// 返回随机len*2大小的正数矩阵
	// 值在0~value-1之间
	public static int[][] randomMatrix(int len, int value) {
		int[][] ans = new int[len << 1][2];
		for (int i = 0; i < ans.length; i++) {
			ans[i][0] = (int) (Math.random() * value);
			ans[i][1] = (int) (Math.random() * value);
		}
		return ans;
	}

	/**
	 * 随机生成元素值随机，长度随机的字符串
	 *
	 * @param l
	 * @param v
	 * @return
	 */
	public static String generateRandomString(int l, int v) {
		int len = (int) (Math.random() * l);
		char[] str = new char[len];
		for (int i = 0; i < len; i++) {
			str[i] = (char) ('a' + (int) (Math.random() * v));
		}
		return String.valueOf(str);
	}

	/**
	 * 随机生成元素值范围[-maxValue,+maxValue]，长度随机的数组
	 *
	 * @param maxSize
	 * @param maxValue
	 * @return
	 */
	public static int[] generateRandomArray(int maxSize, int maxValue) {
		int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
		}
		return arr;
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

	/**
	 * 随机生成元素值范围[0,1]，长度随机的数组
	 *
	 * @param len
	 * @return
	 */
	public static int[] randomOneOrZeroArray(int len) {
		int[] ans = new int[len];
		for (int i = 0; i < len; i++) {
			ans[i] = (int) (Math.random() * 2);
		}
		return ans;
	}

	/**
	 * 随机生成元素值随机范围[0,1]，长度随机的矩阵
	 *
	 * @param len
	 * @return
	 */
	public static int[][] randomOneOrZeroMatrix(int len) {
		int[][] ans = new int[len << 1][2];
		for (int i = 0; i < ans.length; i++) {
			ans[i][0] = (int) (Math.random() * 2);
			ans[i][1] = (int) (Math.random() * 2);
		}
		return ans;
	}

	public static String randomStrings(int len) {
		StringBuilder stringBuilder = new StringBuilder("");
		for (int i = 0; i < len; i += (int) (Math.random() * 2)) {
			stringBuilder.append((char) (i + 32));
		}
		return stringBuilder.toString();
	}

	// for test
	public static String getRandomString(int possibilities, int maxSize) {
		char[] ans = new char[(int) (Math.random() * maxSize) + 1];
		for (int i = 0; i < ans.length; i++) {
			ans[i] = (char) ((int) (Math.random() * possibilities) + 'a');
		}
		return String.valueOf(ans);
	}

	// for test
	public static int[] generateRandomOriginArray(int power, int value) {
		int[] ans = new int[1 << power];
		for (int i = 0; i < ans.length; i++) {
			ans[i] = (int) (Math.random() * value);
		}
		return ans;
	}

	// for test
	public static int[] generateRandomReverseArray(int len, int power) {
		int[] ans = new int[len];
		for (int i = 0; i < ans.length; i++) {
			ans[i] = (int) (Math.random() * (power + 1));
		}
		return ans;
	}

	// for test
	public static void printArray_(int[] arr) {
		System.out.println("arr size : " + arr.length);
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}

	// for test
	public static int[] copyArray(int[] arr) {
		if (arr == null) {
			return null;
		}
		int[] res = new int[arr.length];
		for (int i = 0; i < arr.length; i++) {
			res[i] = arr[i];
		}
		return res;
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

	/**
	 * 生成随机字符串
	 *
	 * @param len
	 * @param types
	 * @return
	 */
	public static String randomString(int len, int types) {
		char[] str = new char[len];
		for (int i = 0; i < str.length; i++) {
			str[i] = (char) ('a' + (int) (Math.random() * types));
		}
		return String.valueOf(str);
	}
}
