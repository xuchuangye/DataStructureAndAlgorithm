package com.mashibing.test;

/**
 * @author xcy
 * @date 2022/5/24 - 16:09
 */
public class Right {
	public int[] arr;

	public Right(int[] origin) {
		arr = new int[origin.length + 1];
		for (int i = 0; i < origin.length; i++) {
			arr[i + 1] = origin[i];
		}
	}

	public void update(int L, int R, int C) {
		for (int i = L; i <= R; i++) {
			arr[i] = C;
		}
	}

	public void add(int L, int R, int C) {
		for (int i = L; i <= R; i++) {
			arr[i] += C;
		}
	}

	public long query(int L, int R) {
		long ans = 0;
		for (int i = L; i <= R; i++) {
			ans += arr[i];
		}
		return ans;
	}
}
