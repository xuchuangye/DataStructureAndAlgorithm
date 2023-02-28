package com.mashibing;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * 加油站良好的出发点问题
 *
 * 解题思路：
 * 1、累加和最薄弱的点 --> 窗口内最小值
 * @author xcy
 * @date 2022/5/14 - 17:16
 */
public class GasStationStartingPoint {
	public static void main(String[] args) {

	}

	public static boolean[] gasStationStartingPoint(int[] gas, int[] cost) {
		if (gas == null || cost == null || cost.length == 0 || gas.length != cost.length) {
			return null;
		}

		int[] arr = new int[gas.length];
		for (int i = 0; i < gas.length; i++) {
			arr[i] = gas[i] + cost[i];
		}

		boolean[] ans = new boolean[arr.length];
		int index = 0;
		for (int first = 0; first < arr.length; first++) {
			int sum = 0;
			for (int i = first; i < arr.length; i++) {
					sum += arr[i];
					if (sum < 0) {
						break;
					}
			}
			ans[index++] = true;
		}
		return ans;
	}
}
