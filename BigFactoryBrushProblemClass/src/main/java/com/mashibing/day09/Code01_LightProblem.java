package com.mashibing.day09;

import com.mashibing.common.TestUtils;

import java.util.Arrays;

/**
 * 题目一：
 * 给定一个数组arr，长度为N，arr中的值不是0就是1。
 * arr[i]表示第i栈灯的状态，0代表灭灯，1代表亮灯
 * 每一栈灯都有开关，但是按下i号灯的开关，会同时改变i - 1、i、i + 1栈灯的状态
 * <p>
 * 问题一：如果N栈灯排成一条直线,请问最少按下多少次开关？
 * i为中间位置时，i号灯的开关能影响i - 1、i和i + 1
 * 0号灯的开关只能影响0和1位置的灯
 * N-1号灯的开关只能影响N-2和N-1位置的灯
 * <p>
 * 问题二：如果N栈灯排成一个圈,请问最少按下多少次开关,能让灯都亮起来
 * i为中间位置时，i号灯的开关能影响i - 1、i和i + 1
 * 0号灯的开关能影响N-1、0和1位置的灯
 * N-1号灯的开关能影响N-2、N-1和0位置的灯
 * <p>
 * 解题思路：
 * 动态规划的从左往右的尝试模型
 *
 * @author xcy
 * @date 2022/7/25 - 10:59
 */
public class Code01_LightProblem {
	public static void main(String[] args) {
		int length = 100;
		int testTimes = 10000;
		System.out.println("测试开始！");
		for (int i = 0; i < testTimes; i++) {
			int[] arr = TestUtils.randomOneOrZeroArray(length);
			//System.out.println(Arrays.toString(arr));
			int step1 = noLoopMinStep1(arr);
			int step2 = noLoopMinStep2(arr);
			int step3 = loopMinStep1(arr);
			int step4 = loopMinStep2(arr);
			if (step1 != step2 || step3 != step4) {
				System.out.println("测试出错！");
				System.out.println(step1);
				System.out.println(step2);
				System.out.println(step3);
				System.out.println(step4);
				break;
			}
		}
		System.out.println("测试结束！");
	}
	/*
	i表示下一个位置，那么i - 1表示当前位置
	i - 2位置的状态，那么i - 2表示当前位置的左边位置
	当前i - 1位置的状态

	假设：
	arr = {0, 1, 1, 0, 1, 1, 0}
	index =0, 1, 2, 3, 4, 5, 6
	情况1：按下0位置灯的开关，调用f(i, i - 2位置灯的状态,当前位置i - 1灯的状态)
	也就表示按下0位置灯的开关，应该继续1位置，所以当前位置是1，下一个位置是2，
	当前位置左边0位置灯的状态，本来是0，但是因为按下了开关，所以当前位置左边0位置灯的状态是1，
	当前位置灯的状态本来是1，但是因为受到左边0位置的灯按下了开关的影响，所以当前位置灯的状态是0
	因此调用f(2, 1, 0)
	情况2：不按0位置灯的开关，调用f(i, i - 2位置灯的状态,当前位置i - 1灯的状态)
	也就表示不按0位置的灯的开关，应该继续1位置，所以当前位置是1，下一个位置是2，
	当前位置左边0位置灯的状态，本来是0，也没有按下开关，所以当前位置左边0位置灯的状态还是0，
	当前位置灯的状态本来是1，也没有受到左边0位置灯按下开关的影响，所以当前位置灯的状态还是1
	调用f(2, 0, 1)
	public static int f(, i - 2位置的状态, 当前位置的状态) {

	}
    */

	/**
	 * 问题以：如果N栈灯排成一条直线,请问最少按下多少次开关？ -> 使用暴力递归的方式
	 *
	 * @param arr 灯的数组
	 * @return 返回要点亮所有的灯，至少需要按下的开关次数
	 */
	public static int noLoopMinStep1(int[] arr) {
		//如果没有一盏灯，按下灯的开关次数为0
		if (arr == null || arr.length == 0) {
			return 0;
		}
		//如果只有一盏灯，看看灯是亮的还是灭的
		if (arr.length == 1) {
			//return arr[0] == 0 ? 1 : 0;
			return arr[0] ^ 1;
		}
		//如果只有两盏灯，看看登是否都是亮的或者灭的，还是一盏灯亮一盏灯灭
		if (arr.length == 2) {
			//如果是一盏灯亮一盏灯灭，那么没有办法让整个数组的灯都亮，返回Integer.MAX_VALUE无效值
			//如果都是亮的，不需要按开关
			//如果都是灭的，需要按下1次开关
			//return arr[0] != arr[1] ? Integer.MAX_VALUE : (arr[0] == 1 ? 0 : 1);
			return arr[0] != arr[1] ? Integer.MAX_VALUE : (arr[0] ^ 1);
		}

		//不按下当前位置灯的开关
		//因为arr.length == 0和arr.length == 1以及arr.length == 2时，已经判断过了
		//所以当前位置的下一个位置从2开始再合适不过了
		//当前位置1
		//当前位置的下一个位置2
		//当前位置的前一个位置0
		int situation1 = process1(arr, 2, arr[0], arr[1]);
		//按下当前位置灯的开关
		//当前位置1
		//当前位置的下一个位置2
		//当前位置的前一个位置0
		int situation2 = process1(arr, 2, arr[0] ^ 1, arr[1] ^ 1);
		//如果按下之后返回值有效，那么按下当前位置这盏灯的开关次数也是要算1次的
		if (situation2 != Integer.MAX_VALUE) {
			situation2++;
		}
		return Math.min(situation1, situation2);
	}

	/**
	 * 隐含条件：0 ~ i - 2的灯都是亮的！
	 *
	 * @param arr       灯的数组
	 * @param nextIndex 当前位置的下一个位置
	 * @param preStatus 当前位置的前一个位置灯的状态
	 * @param curStatus 当前位置灯的状态
	 * @return 返回要点亮所有的灯，至少需要按下的开关次数
	 */
	public static int process1(int[] arr, int nextIndex, int preStatus, int curStatus) {
		//表示当前位置 == arr.length - 1
		//当前位置的灯已经是最后一盏灯了
		if (nextIndex == arr.length) {
			//如果当前位置的前一个位置灯的状态和当前位置灯的状态一样
			//如果都是亮的，那么不需要按下开关，如果都是灭的，那么按下当前位置灯的开关即可，算作1次
			//如果当前位置的前一个位置灯的状态和当前位置灯的状态不一样，那么就表示没有办法点亮整个数组的灯，返回
			//Integer.MAX_VALUE无效值
			//return preStatus != curStatus ? Integer.MAX_VALUE : curStatus == 1 ? 0 : 1;
			return preStatus != curStatus ? (Integer.MAX_VALUE) : (curStatus ^ 1);
		}
		//表示当前位置的前一个位置灯的状态是灭的，那么当前位置灯的开关必须按下，
		//否则当前位置的前一个位置灯就再也没有机会亮了，因为没有其余的灯可以影响到了
		if (preStatus == 0) {
			//所以必须按下当前位置的灯的开关
			curStatus ^= 1;
			//arr[nextIndex]表示当前位置下一个位置灯的状态，^ 1表示按下灯的开关
			int cur = arr[nextIndex] ^ 1;
			//下一步递归，当前位置灯的状态curStatus表示作为下一步递归时，当前位置的前一个位置灯的状态
			//然后cur作为下一步递归时，当前位置灯的状态
			int next = process1(arr, nextIndex + 1, curStatus, cur);
			//如果下一步的返回值无效，直接返回next的值
			//如果下一步的返回值有效，那么返回next + 1，因为按下了当前位置灯的开关这一次也是要算的
			return next == Integer.MAX_VALUE ? next : (next + 1);
		}
		//表示当前位置的前一个位置灯的状态是亮的，那么当前位置灯的开关必须不能按，否则按下就再也没有机会亮了
		else {
			//当前位置灯的状态curStatus表示作为下一步递归时，当前位置的前一个位置灯的状态
			//当前位置下一个位置的状态灯的状态arr[nextIndex]表示当前作为下一步递归时，当前位置灯的状态
			return process1(arr, nextIndex + 1, curStatus, arr[nextIndex]);
		}
	}

	/**
	 * 问题一：如果N栈灯排成一条直线,请问最少按下多少次开关？ -> 使用循环迭代的方式
	 *
	 * @param arr
	 * @return
	 */
	public static int noLoopMinStep2(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		if (arr.length == 1) {
			return arr[0] == 0 ? 1 : 0;
		}
		if (arr.length == 2) {
			return arr[0] != arr[1] ? Integer.MAX_VALUE : (arr[1] ^ 1);
		}

		int situation1 = traceNoLoop(arr, arr[0], arr[1]);
		int situation2 = traceNoLoop(arr, arr[0] ^ 1, arr[1] ^ 1);
		if (situation2 != Integer.MAX_VALUE) {
			situation2 += 1;
		}
		return Math.min(situation1, situation2);
	}

	/**
	 * 使用循环迭代的方式
	 *
	 * @param arr
	 * @param preStatus
	 * @param curStatus
	 * @return
	 */
	public static int traceNoLoop(int[] arr, int preStatus, int curStatus) {
		int nextIndex = 2;
		int op = 0;
		while (nextIndex != arr.length) {
			if (preStatus == 0) {
				op++;
				preStatus = curStatus ^ 1;
				curStatus = arr[nextIndex] ^ 1;
			} else {
				preStatus = curStatus;
				curStatus = arr[nextIndex];
			}
			nextIndex++;
		}
		return preStatus != curStatus ? Integer.MAX_VALUE : (op + (curStatus ^ 1));
	}

	/**
	 * 问题二：如果N栈灯排成一个圈,请问最少按下多少次开关,能让灯都亮起来 -> 使用暴力递归的方式
	 *
	 * @param arr 灯的数组
	 * @return 返回要点亮所有的灯，至少需要按下的开关次数
	 */
	public static int loopMinStep1(int[] arr) {
		//如果一盏灯都没有，需要按下按钮的次数为0
		if (arr == null || arr.length == 0) {
			return 0;
		}
		//如果只有一盏灯，查看灯是否是亮着的
		//如果本来就是亮着的，不需要按下按钮返回0
		//如果本来就是灭着的，需要按下按钮返回1
		if (arr.length == 1) {
			return arr[0] ^ 1;
		}
		//如果只有两盏灯，查看灯是否是同时亮还是同时灭，还是一盏灯亮，一盏灯灭
		//如果是一盏灯亮，一盏灯灭，直接返回Integer.MAX_VALUE，表示无法点亮所有的灯
		//如果同时亮，不需要按下按钮，返回0
		//如果同时灭，需要按下按钮，返回1
		if (arr.length == 2) {
			return arr[0] != arr[1] ? Integer.MAX_VALUE : (arr[0] ^ 1);
		}
		//如果只有三盏灯，查看是否都是亮的还是都是灭的
		//只要三盏灯的状态不一样，直接返回Integer.MAX_VALUE，表示无法点亮所有的灯
		//如果同时亮，不需要按下按钮，返回0
		//如果同时灭，需要按下按钮，返回1
		if (arr.length == 3) {
			return (arr[0] != arr[1]) || (arr[0] != arr[2]) ? Integer.MAX_VALUE : (arr[0] ^ 1);
		}

		int N = arr.length;
		//四种情况
		//情况一：不按下0位置的按钮，按下1位置的按钮
		int situation1 = process2(arr, 3, arr[1] ^ 1, arr[2] ^ 1, arr[N - 1], arr[0] ^ 1);
		if (situation1 != Integer.MAX_VALUE) {
			situation1 += 1;
		}
		//情况二：按下0的按钮，也按下1位置的按钮
		int situation2 = process2(arr, 3, arr[1], arr[2] ^ 1, arr[N - 1] ^ 1, arr[0]);
		if (situation2 != Integer.MAX_VALUE) {
			situation2 += 2;
		}
		//情况三：不按下0的按钮，也不按下1的按钮
		int situation3 = process2(arr, 3, arr[1], arr[2], arr[N - 1], arr[0]);
		//情况四：按下0的按钮，不按下1位置的按钮
		int situation4 = process2(arr, 3, arr[1] ^ 1, arr[2], arr[N - 1] ^ 1, arr[0] ^ 1);
		if (situation4 != Integer.MAX_VALUE) {
			situation4 += 1;
		}
		return Math.min(Math.min(situation1, situation2), Math.min(situation3, situation4));
	}

	/**
	 * 使用暴力递归的方式
	 *
	 * @param arr         灯的数组
	 * @param nextIndex   当前位置的下一个位置
	 * @param preStatus   当前位置的前一个位置灯的状态
	 * @param curStatus   当前位置的灯的状态
	 * @param endStatus   最后一个位置(arr.length - 1)灯的状态
	 * @param firstStatus 第一个位置(0)灯的状态
	 * @return 返回点亮所有的灯至少需要按下按钮多少次
	 */
	public static int process2(int[] arr, int nextIndex, int preStatus, int curStatus,
	                           int endStatus, int firstStatus) {
		//如果当前位置的下一个位置来到arr.length，那么当前位置 == arr.length - 1
		if (nextIndex == arr.length) {
			//当前位置 == arr.length - 1，表示已经来到最后一盏灯了
			//只要最后一盏灯的状态和前一盏灯的状态不一样或者最后一盏灯的状态和第一盏灯的状态不一样
			//那么就无法点亮整个数组的灯，返回Integer.MAX_VALUE
			//如果最后一盏灯的状态和前一盏灯的状态以及第一盏灯的状态都一样
			//如果都是亮的，不需要按下当前位置灯的开关，如果都是灭的，按下当前位置灯的开关
			return ((endStatus != firstStatus) || (endStatus != preStatus)) ? Integer.MAX_VALUE : (endStatus ^ 1);
		}
		//不按下当前位置灯的开关的前一个位置灯的状态
		int noNextPreStatus = 0;
		//按下当前位置灯的开关的前一个位置灯的状态
		int yesNextPreStatus = 0;
		//不按下当前位置灯的开关的状态
		int noNextCurStatus = 0;
		//按下当前位置灯的开关的状态
		int yesNextCurStatus = 0;

		//按下最后一盏灯时最后一盏灯的状态
		int noEndStatus = 0;
		//不按下最后一盏灯时最后一盏灯的状态
		int yesEndStatus = 0;

		//当前位置没有来到arr.length - 2的位置
		if (nextIndex < arr.length - 1) {
			//不按下当前位置灯的开关的前一个位置灯的状态
			noNextPreStatus = curStatus;
			//按下当前位置灯的开关的前一个位置灯的状态
			yesNextPreStatus = curStatus ^ 1;
			//不按下当前位置灯的开关的状态
			noNextCurStatus = arr[nextIndex];
			//按下当前位置灯的开关的状态
			yesNextCurStatus = arr[nextIndex] ^ 1;
		}
		//当前位置已经来到arr.length - 2的位置
		else if (nextIndex == arr.length - 1) {
			//不按下当前位置灯的开关的前一个位置灯的状态
			noNextPreStatus = curStatus;
			//按下当前位置灯的开关的前一个位置灯的状态
			yesNextPreStatus = curStatus ^ 1;
			//不按下当前位置灯的开关的状态
			noNextCurStatus = endStatus;
			//按下当前位置灯的开关的状态
			yesNextCurStatus = endStatus ^ 1;
			//按下最后一盏灯时最后一盏灯的状态
			noEndStatus = endStatus;
			//不按下最后一盏灯时最后一盏灯的状态
			yesEndStatus = endStatus ^ 1;
		}
		//如果当前位置的前一个位置的灯是灭的，那么当前位置灯的按钮必须要按
		if (preStatus == 0) {
			int next = process2(arr, nextIndex + 1, yesNextPreStatus, yesNextCurStatus,
					//nextIndex == arr.length - 1 ? yesEndStatus : endStatus表示如果是最后一盏灯，返回yesEndStatus
					//如果不是最后一盏灯，返回endStatus
					nextIndex == arr.length - 1 ? yesEndStatus : endStatus, firstStatus);
			return next == Integer.MAX_VALUE ? next : (next + 1);
		}
		//如果当前位置的前一个位置的灯是亮的，那么当前位置灯的按钮必须不按
		else {
			return process2(arr, nextIndex + 1, noNextPreStatus, noNextCurStatus,
					//nextIndex == arr.length - 1 ? noEndStatus : endStatus表示如果是最后一盏灯，返回noEndStatus
					//如果不是最后一盏灯，返回endStatus
					nextIndex == arr.length - 1 ? noEndStatus : endStatus, firstStatus);
		}
	}

	/**
	 * 如果N栈灯排成一个圈,请问最少按下多少次开关,能让灯都亮起来 -> 使用循环迭代的方式
	 *
	 * @param arr 灯的数组
	 * @return 返回点亮所有的灯至少需要按下按钮多少次
	 */
	public static int loopMinStep2(int[] arr) {
		//如果一盏灯都没有，需要按下按钮的次数为0
		if (arr == null || arr.length == 0) {
			return 0;
		}
		//如果只有一盏灯，查看灯是否是亮着的
		//如果本来就是亮着的，不需要按下按钮返回0
		//如果本来就是灭着的，需要按下按钮返回1
		if (arr.length == 1) {
			return arr[0] ^ 1;
		}
		//如果只有两盏灯，查看灯是否是同时亮还是同时灭，还是一盏灯亮，一盏灯灭
		//如果是一盏灯亮，一盏灯灭，直接返回Integer.MAX_VALUE，表示无法点亮所有的灯
		//如果同时亮，不需要按下按钮，返回0
		//如果同时灭，需要按下按钮，返回1
		if (arr.length == 2) {
			return arr[0] != arr[1] ? Integer.MAX_VALUE : (arr[0] ^ 1);
		}
		//如果只有三盏灯，查看是否都是亮的还是都是灭的
		//只要三盏灯的状态不一样，直接返回Integer.MAX_VALUE，表示无法点亮所有的灯
		//如果同时亮，不需要按下按钮，返回0
		//如果同时灭，需要按下按钮，返回1
		if (arr.length == 3) {
			return (arr[0] != arr[1] || arr[0] != arr[2]) ? Integer.MAX_VALUE : (arr[0] ^ 1);
		}

		int N = arr.length;
		//四种情况
		//情况一：不按下0位置的按钮，按下1位置的按钮
		int situation1 = traceLoop(arr, arr[1] ^ 1, arr[2] ^ 1, arr[N - 1], arr[0] ^ 1);
		if (situation1 != Integer.MAX_VALUE) {
			situation1 += 1;
		}
		//情况二：按下0的按钮，也按下1位置的按钮
		int situation2 = traceLoop(arr, arr[1], arr[2] ^ 1, arr[N - 1] ^ 1, arr[0]);
		if (situation2 != Integer.MAX_VALUE) {
			situation2 += 2;
		}
		//情况三：不按下0的按钮，也不按下1的按钮
		int situation3 = traceLoop(arr, arr[1], arr[2], arr[N - 1], arr[0]);
		//情况四：按下0的按钮，不按下1位置的按钮
		int situation4 = traceLoop(arr, arr[1] ^ 1, arr[2], arr[N - 1] ^ 1, arr[0] ^ 1);
		if (situation4 != Integer.MAX_VALUE) {
			situation4 += 1;
		}
		return Math.min(Math.min(situation1, situation2), Math.min(situation3, situation4));
	}

	/**
	 * 使用循环迭代的方式
	 *
	 * @param arr         灯的数组
	 * @param preStatus   当前位置的前一个位置灯的状态
	 * @param curStatus   当前位置的灯的状态
	 * @param endStatus   最后一个位置(arr.length - 1)灯的状态
	 * @param firstStatus 第一个位置(0)灯的状态
	 * @return 返回点亮所有的灯至少需要按下按钮多少次
	 */
	public static int traceLoop(int[] arr, int preStatus, int curStatus,
	                            int endStatus, int firstStatus) {
		//当前位置的下一个位置
		int nextIndex = 3;
		//点亮所有的灯，至少要按下多少次按钮
		int op = 0;
		while (nextIndex < arr.length - 1) {
			if (preStatus == 0) {
				op++;
				preStatus = curStatus ^ 1;
				curStatus = (arr[nextIndex++] ^ 1);
			} else {
				preStatus = curStatus;
				curStatus = arr[nextIndex++];
			}
		}
		//nextIndex == arr.length - 1
		if (preStatus == 0) {
			op++;
			preStatus = curStatus ^ 1;
			endStatus ^= 1;
			//curStatus = endStatus;
		} else {
			preStatus = curStatus;
			//curStatus = endStatus;
		}
		//当前位置 == arr.length - 1，表示已经来到最后一盏灯了
		//只要最后一盏灯的状态和前一盏灯的状态不一样或者最后一盏灯的状态和第一盏灯的状态不一样
		//那么就无法点亮整个数组的灯，返回Integer.MAX_VALUE
		//如果最后一盏灯的状态和前一盏灯的状态以及第一盏灯的状态都一样
		//如果都是亮的，不需要按下当前位置灯的开关，如果都是灭的，按下当前位置灯的开关
		return ((endStatus != firstStatus) || (endStatus != preStatus)) ? Integer.MAX_VALUE :
				(op + (endStatus ^ 1));
	}
}
