package com.mashibing;

import com.mashibing.common.SortCommonUtils;

import java.util.HashMap;
import java.util.HashSet;

/**
 * 题目：一个数组中有一种数出现了K次，其他数都出现了M次，M > 1，K < M，
 * 如果找到了出现K次的数，则打印K次的数
 * 如果找不到出现了K次的数，则打印-1
 * 要求额外空间复杂度O(1)，时间复杂度O(N)
 * <p>
 * 思路分析：
 * 1、声明一个长度为32的数组
 * 2、将所有的数都转换为二进制，判断与1进行&运算，运算的结果是否不等于0，如果不等于0，则证明该位置上出现了1，
 * 将每一位上出现1的结果依次累加到数组t中
 * 3、循环遍历数组t中所有的数，判断t的每一位对m进行取余，如果哪一个位置上不等于0，则将1左移到该位置上
 * 并且参数与0这个干净的数进行|=运算，记录的最终二进制结果转换为十进制即为出现K次的数
 * 4、可能存在只有一种数0，并且0出现的次数不满足K次的要求，可能会报错
 * 5、使用对数器进行测试
 *
 * @author xcy
 * @date 2022/3/11 - 16:39
 */
public class InterviewQuestions006 {

	/**
	 * 请求保证arr数组中，只有一种数出现了K次，其他数出现了M次
	 * M > 1，K < M
	 *
	 * @param arr
	 * @param K   数组中只有一种数出现了K次
	 * @param M   数组中其他数出现了M次
	 * @return 返回数组中出现了K次的数
	 */
	public static int onlyKTimes(int[] arr, int K, int M) {
		/*
		额外空间复杂度O(1)
		 */
		int[] temp = new int[32];

		/*
		最外层for循环时间复杂度O(N)

		num表示遍历数组中的每个元素
		 */
		for (int num : arr) {
			/*
			i是整型数值，一共就32位，最少移动0位，最多移动31位，因此内层for循环的时间复杂度为O(1)
		    */
			for (int i = 0; i < temp.length; i++) {
				//t[i] += (num >> i) & 1;
				//移动i位，如果i位置和1进行&运算，不等于0，则证明i位置的数是1，否则等于0，则证明i位置的数是0
				if (((num >> i) & 1) != 0) {
					//i位置的数是1，就在i位置上加1
					temp[i]++;
				}
			}
		}
		//ans
		int ans = 0;
		/*
		时间复杂度为O(1)

		i表示循环遍历数组t的每一位
		 */
		//对temp数组中记录的每一位进行遍历
		for (int i = 0; i < temp.length; i++) {
			//如果temp数组记录的每一位上出现1的次数：i对M取余，结果等于0
			//说明temp数组中i位置上记录1的次数是M的整数倍
			//出现K次的数没有在该位上进行累加，直接跳过进行下一次循环
			if (temp[i] % M == 0) {
				continue;
			}
			//如果temp数组记录的每一位上出现1的次数：i对M取余，结果不等于0，
			//说明temp数组中i位置上记录1的次数不是M的整数倍，并且取余之后得到的是K
			//说明存在出现K次的数在该位上也进行了累加，那么将i位置上的1标记到ans这个数对应的位上即可
			if (temp[i] % M == K) {
				//如何将i位置上的1记录到ans这个数对应的位上，
				//1左移到出现1的i位上，并且|=K，因为ans本身就是0，那么所有的位上都是0,
				//0 |= 1就可以将1记录到K这个数对应的位上
				ans |= (1 << i);
			} else {
				return -1;
			}
		}

		//因为0可能表示没有出现K次的数，也有可能是0是出现K次的数
		//所以如果ans == 0，首先记录0出现的次数，
		//然后判断出现K次的数是否是0，使用记录0出现的次数count和K进行比较，如果count == K说明出现K次的数就是0
		if (ans == 0) {
			//那么就记录0出现的次数
			int count = 0;
			//遍历数组中的每一个元素
			for (int num : arr) {
				//判断元素中是否有出现0
				if (num == 0) {
					//如果元素中出现了0，那么就记录0出现的次数，对次数进行累加
					count++;
				}
			}
			//判断0出现的次数是否等于k，如果不等于K，说明0表示没有出现K的次数，返回-1
			//如果等于K，说明出现K次的数就是0，ans就是出现K次的数，直接返回ans
			if (count != K) {
				return -1;
			}
		}
		return ans;
	}


	public static void main(String[] args) {
		//数组中出现数的种类
		int kinds = 3;
		//数组中出现数的随机范围
		int range = 200;
		//测试次数
		int times = 1000000;
		//生成K和M的最大值
		int max = 9;
		System.out.println("测试开始，开始时间-" + System.currentTimeMillis());
		for (int i = 1; i <= times; i++) {
			int a = SortCommonUtils.randomNumber(max) + 1;//1~9
			int b = SortCommonUtils.randomNumber(max) + 1;//1~9
			//取出a和b较小的值作为K
			int k = Math.min(a, b);
			//取出a和b较大的值作为M
			int m = Math.max(a, b);

			//如果K 和 M 相等，让M+1，保证M > K
			if (k == m) {
				m++;
			}
			int[] arr = SortCommonUtils.randomArray(kinds, range, k, m);
			int ans1 = SortCommonUtils.test(arr, k, m);
			int ans2 = onlyKTimes(arr, k, m);

			if (ans1 != ans2) {
				System.out.println(ans1);
				System.out.println(ans2);
				System.err.println("出错了！");
				break;
			}
		}

		System.out.println("测试结束，结束时间-" + System.currentTimeMillis());
	}
}
