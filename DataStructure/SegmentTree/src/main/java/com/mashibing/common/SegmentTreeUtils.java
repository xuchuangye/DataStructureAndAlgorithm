package com.mashibing.common;

/**
 * 线段树的工具类
 * @author xcy
 * @date 2022/5/24 - 16:10
 */
public class SegmentTreeUtils {
	public static int[] generateRandomArray(int len, int max) {
		int size = (int) (Math.random() * len) + 1;
		int[] origin = new int[size];
		for (int i = 0; i < size; i++) {
			origin[i] = (int) (Math.random() * max) - (int) (Math.random() * max);
		}
		return origin;
	}
}
