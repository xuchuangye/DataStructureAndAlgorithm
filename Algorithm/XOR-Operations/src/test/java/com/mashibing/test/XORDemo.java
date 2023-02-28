package com.mashibing.test;

import com.mashibing.common.SortCommonUtils;

/**
 * @author xcy
 * @date 2022/4/14 - 9:29
 */
public class XORDemo {
	public static void main(String[] args) {
		//数组中出现的几种数
		int kinds = 3;
		//数组中元素值的范围
		int range = 200;
		//测试的次数
		int times = 100000;
		//确定K和M值的随机性，并且保证M > 1和K < M这两个条件
		int max = 9;
		System.out.println("测试开始，开始时间-" + System.currentTimeMillis());
		for (int i = 1; i <= times; i++) {
			int a = SortCommonUtils.randomNumber(max) + 1;//1~9
			int b = SortCommonUtils.randomNumber(max) + 1;//1~9
			int k = Math.min(a, b);
			int m = Math.max(a, b);
			//可能会出现K == M的情况，需要让K < M
			if (k == m) {
				m++;
			}
			int[] arr = SortCommonUtils.randomArray(kinds, range, k, m);
			int ans1 = SortCommonUtils.test(arr, k, m);
			int ans2 = returnKTimes(arr, k, m);

			if (ans1 != ans2) {
				System.err.println("出错了！");
				break;
			}
		}

		System.out.println("测试结束，结束时间-" + System.currentTimeMillis());
	}

	public static int returnKTimes(int[] arr, int K, int M) {
		//记录int类型数的每一位上出现1的次数
		int[] temp = new int[32];

		//for循环对数组中的每一个元素进行遍历
		for (int element : arr) {
			//对该元素的每一位进行遍历
			for (int i = 0; i < temp.length; i++) {
				//元素左移i位，并且&上1的结果 != 0，说明该位上存在1,
				if (((element >> i) & 1) != 0) {
					//那么就记录到temp数组中，并且在i位置上累加
					temp[i]++;
				}
			}
		}

		K = 0;

		//for循环对数组中的每一个元素进行遍历
		for (int element : arr) {
			//对该元素的每一位进行遍历
			for (int i = 0; i < temp.length; i++) {
				//如果temp数组记录的每一位上出现1的次数，i对M取余，结果不等于0，
				//说明temp数组中i位置上记录1的次数不是M的整数倍，
				//存在出现K次的数在该位上也进行了累加，那么将i位置上的1标记到K这个数对应的位上即可
				if (temp[i] % M != 0) {
					//如何将i位置上的1记录到K这个数对应的位上，
					// 1左移到出现1的i位上，并且|=K，因为K本身就是0，那么所有的位上都是0,
					//0 |= 1就可以将1记录到K这个数对应的位上
					K |= (1 << i);
				}
			}
		}
		//返回的结果就是出现K次的这个数
		return K;
	}
}
