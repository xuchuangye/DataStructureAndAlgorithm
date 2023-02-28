package com.mashibing;

import com.mashibing.common.SortCommonUtils;

import java.util.Arrays;

/**
 * 异或运算
 * 题目二：数组中的其中有一种数出现了奇数次，其余的数出现了偶数次，找到并打印该数
 *
 * 思路分析：
 * 1、异或运算满足交换律和结合律
 * 2、取出数组中的任意一个元素，循环遍历让该元素和数组中的其他元素进行异或运算：
 *   2.1、出现偶数次的异或结果都为0
 *   2.2、出现奇数次的异或结果为该元素本身
 *   2.3、所以使用0和数组中所有的元素进行异或运算，得到的就是出现奇数次的数
 * @author xcy
 * @date 2022/3/11 - 16:39
 */
/*

 */
public class InterviewQuestions002 {
	public static void main(String[] args) {
		//int[] arr = {1, 1, 4, 3, 4, 5, 2, 2, 3, 5, 1};

		int testTime = 1000000;
		boolean success = true;
		for (int i = 0; i < testTime; i++) {
			int[] array = new int[]{1,4,5,6,7,5,7,6,4};
			int[] arr = SortCommonUtils.copyArray(array);
			int searchCountValue = searchCountValue(arr);
			int search = SortCommonUtils.search(arr);

			if (search != searchCountValue) {
				success = false;
				System.out.println(searchCountValue);
				System.out.println(search);
				System.out.println(Arrays.toString(array));
				System.out.println(Arrays.toString(arr));
				break;
			}
		}
		System.out.println(success ? "测试成功" : "测试失败");
	}

	private static int searchCountValue(int[] arr) {
		int eor = 0;
		for (int element : arr) {
			eor ^= element;
		}
		return eor;
	}

}
