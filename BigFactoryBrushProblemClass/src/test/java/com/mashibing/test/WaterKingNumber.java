package com.mashibing.test;

import com.mashibing.common.TestUtils;

/**
 * @author xcy
 * @date 2022/10/5 - 8:59
 */
public class WaterKingNumber {
	public static void main(String[] args) {
		int testTime = 1000000;
		int len = 1000;
		int maxValue = 1000;
		System.out.println("测试开始！");
		for (int i = 0; i < testTime; i++) {
			int[] arr = TestUtils.randomArray(len, maxValue);
			int number1 = waterKingNumber(arr);
			int number2 = waterKing(arr);
			if (number1 != number2) {
				System.out.println("测试出错！");
				break;
			}
		}
		System.out.println("测试结束！");
	}

	public static int waterKingNumber(int[] arr) {
		if (arr == null || arr.length == 0) {
			return -1;
		}
		//当前候选数
		int cur = arr[0];
		//当前血量值
		int bloodVolume = 1;
		for (int i = 1; i < arr.length; i++) {
			if (cur == arr[i]) {
				bloodVolume++;
			}else {
				bloodVolume--;
			}
		}
		if (bloodVolume == 1) {
			return cur;
		}else {
			return -1;
		}
	}

	public static int waterKing(int[] arr) {
		if (arr == null || arr.length == 0) {
			return -1;
		}
		int cand = 0;
		int hp = 0;
		for (int i = 0; i < arr.length; i++) {
			if (hp == 0) {
				cand = arr[i];
				hp = 1;
			}else if (arr[i] == cand) {
				hp++;
			}else {
				hp--;
			}
		}
		if (hp == 0) {
			return -1;
		}
		int count = 0;

		for (int i = 0; i < arr.length; i++) {
			if (arr[i] == cand) {
				count++;
			}
		}
		return count > arr.length / 2 ? cand : -1;
	}
}
