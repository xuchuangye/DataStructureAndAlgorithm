package com.mashibing;

/**
 * IndexTree的对数器
 * @author xcy
 * @date 2022/5/29 - 9:03
 */
public class Right {
	private final int[] nums;
	private final int N;

	public Right(int size) {
		N = size + 1;
		nums = new int[N + 1];
	}

	public int sum(int index) {
		int ret = 0;
		for (int i = 1; i <= index; i++) {
			ret += nums[i];
		}
		return ret;
	}

	public void add(int index, int d) {
		nums[index] += d;
	}

}
