package com.mashibing;

import com.mashibing.common.SortCommonUtils;

import java.util.Arrays;
import java.util.Stack;

/**
 * @author xcy
 * @date 2023/3/23 - 11:13
 */
public class QuickSortTest {
	public static void main(String[] args) {
		int len = 100;
		int variable = 100000;
		int testTime = 1000000000;
		for (int i = 0; i < testTime; i++) {
			int[] arr = SortCommonUtils.generateArray(len, variable);
			int[] array = SortCommonUtils.copyArray(arr);
			quickWithIterator(arr);
			SortCommonUtils.sort(array);
			if (!SortCommonUtils.isEqual(arr, array)) {
				System.out.println("测试失败");
			}
		}
		//System.out.println(Arrays.toString(arr));
	}

	/**
	 * 递归版本
	 *
	 * @param arr
	 */
	public static void QuickWithRecursion(int[] arr) {
		if (arr == null || arr.length < 2) {
			return;
		}
		process(arr, 0, arr.length - 1);
	}

	public static void process(int[] arr, int l, int r) {
		if (l > r) {
			return;
		}

		swap(arr, (int) (Math.random() * (r - l + 1)), arr.length - 1);
		int[] equal = partition(arr, l, r);
		process(arr, l, equal[0] - 1);
		process(arr, equal[1] + 1, r);
	}

	public static class Option {
		public int l;
		public int r;

		public Option(int l, int r) {
			this.l = l;
			this.r = r;
		}
	}

	public static void quickWithIterator(int[] arr) {
		if (arr == null || arr.length < 2) {
			return;
		}

		int N = arr.length;
		swap(arr, (int) (Math.random() * (N)), N - 1);

		Stack<Option> stack = new Stack<>();
		int[] equalArea = partition(arr, 0, N - 1);
		int equalL = equalArea[0];
		int equalR = equalArea[1];

		stack.push(new Option(0, equalL - 1));
		stack.push(new Option(equalR + 1, N - 1));

		while (!stack.isEmpty()) {
			Option option = stack.pop();
			if (option.l < option.r) {
				swap(arr, option.l + (int) (Math.random() * (option.r - option.l + 1)), option.r);

				equalArea = partition(arr, option.l, option.r);

				equalL = equalArea[0];
				equalR = equalArea[1];

				stack.push(new Option(option.l, equalL - 1));
				stack.push(new Option(equalR + 1, option.r));
			}
		}

	}


	public static int[] partition(int[] arr, int L, int R) {
		if (L > R) {
			return new int[]{-1, -1};
		}

		if (L == R) {
			return new int[]{L, R};
		}

		int lessR = L - 1;
		int moreL = R;
		int index = L;
		while (index < moreL) {
			if (arr[index] == arr[R]) {
				index++;
			} else if (arr[index] > arr[R]) {
				swap(arr, --moreL, index);
			} else {
				swap(arr, ++lessR, index++);
			}
		}

		swap(arr, moreL, R);
		return new int[]{lessR + 1, moreL};
	}

	public static void swap(int[] arr, int i, int j) {
		int temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}
}
