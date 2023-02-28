package com.mashibing;

import com.mashibing.common.SortCommonUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

/**
 * 异或运算
 * 题目五：一个数组中有一种数出现了K次，其他数都出现了M次，M > 1，K < M，找到出现了K次的数.
 * 要求额外空间复杂度O(1)，时间复杂度O(N)
 * <p>
 * 思路分析：
 * 1、声明一个长度为32的数组
 * 2、将所有的数都转换为二进制，判断与1进行&运算，运算的结果是否不等于0，如果不等于0，则证明该位置上出现了1，
 * 将每一位上出现1的结果依次累加到数组t中
 * 3、循环遍历数组t中所有的数，判断t的每一位对m进行取余，如果哪一个位置上不等于0，则将1左移到该位置上
 * 并且参数与0这个干净的数进行|=运算，记录的最终二进制结果转换为十进制即为出现K次的数
 * 4、使用对数器进行测试
 *
 * @author xcy
 * @date 2022/3/11 - 16:39
 */
public class InterviewQuestions005 {

	/**
	 * 请求保证arr数组中，只有一种数出现了K次，其他数出现了M次
	 * M > 1，K < M
	 *
	 * @param arr 数组
	 * @param K   数组中只有一种数出现了K次
	 * @param M   数组中其他数出现了M次
	 * @return 返回数组中出现了K次的数
	 */
	public static int onlyKTimes(int[] arr, int K, int M) {
		/*
		额外空间复杂度O(1)
		 */
		//temp数组记录int类型的数每一位上出现1的次数，进行累加
		int[] temp = new int[32];

		/*
		最外层for循环时间复杂度O(N)

		num表示遍历数组中的每个元素
		 */
		for (int num : arr) {
			/*
			最内层for循环时间复杂度O(1)
			i是整型数值，一共就32位，最少移动0位，最多移动31位，因此内层for循环的时间复杂度为O(1)
		    */
			for (int i = 0; i < temp.length; i++) {
				//依次提取每一位上的1
				//t[i] += (num >> i) & 1;
				//移动i位，如果i位置和1进行&运算，不等于0，则证明i位置的数是1，否则等于0，则证明i位置的数是0
				if (((num >> i) & 1) != 0) {
					//i位置的数是1，就在i位置上加1
					temp[i]++;
				}
			}
		}
		/*
		时间复杂度为O(1)

		i表示循环遍历数组t的每一位
		 */
		K = 0;

		//for循环对数组中的每一个元素进行遍历
		//对该元素的每一位进行遍历
		for (int i = 0; i < temp.length; i++) {
			//如果temp数组记录的每一位上出现1的次数：i对M取余，结果不等于0，
			//说明temp数组中i位置上记录1的次数不是M的整数倍，
			//存在出现K次的数在该位上也进行了累加，那么将i位置上的1标记到K这个数对应的位上即可
			if (temp[i] % M != 0) {
				//如何将i位置上的1记录到K这个数对应的位上，
				// 1左移到出现1的i位上，并且|=K，因为K本身就是0，那么所有的位上都是0,
				//0 |= 1就可以将1记录到K这个数对应的位上
				K |= (1 << i);
			}
		}
		//返回的结果就是出现K次的这个数
		return K;
	}

	/**
	 * 对数器
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		//数组中出现的几种数
		int kinds = 3;
		//数组中元素值的范围
		int range = 200;
		//测试的次数
		int times = 100000;
		//确定K和M值的随机性，并且保证M > 1和K < M这两个条件
		int max = 9;
		Date date1 = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String start = simpleDateFormat.format(date1);
		System.out.println("测试开始，开始时间 - " + start);
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
			int ans2 = onlyKTimes(arr, k, m);

			if (ans1 != ans2) {
				System.err.println("出错了！");
				break;
			}
		}
		Date date2 = new Date();
		String end = simpleDateFormat.format(date2);
		System.out.println("测试结束，结束时间 - " + end);
	}
}
