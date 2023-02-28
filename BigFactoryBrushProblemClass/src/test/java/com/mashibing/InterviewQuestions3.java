package com.mashibing;

/**
 * @author xcy
 * @date 2022/8/4 - 8:03
 */
public class InterviewQuestions3 {
	public static void main(String[] args) {

	}

	public static int f(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}

		int L = 0;
		int R = arr.length;
		while (L != R) {
			if (arr[L] == L + 1) {
				L++;
			}

			else if (arr[L] <= L || arr[L] > R || arr[arr[L] - 1] == arr[L]) {
				swap(arr, L, R - 1);
				R--;
			}

			else {
				swap(arr, L, arr[L] - 1);
			}
		}
		return L + 1;
	}

	public static void swap(int[] arr, int i, int j) {
		int temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}
}
