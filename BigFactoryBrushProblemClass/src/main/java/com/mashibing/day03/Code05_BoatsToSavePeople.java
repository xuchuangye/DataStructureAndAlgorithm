package com.mashibing.day03;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 题目五：
 * 给定一个正数数组arr，代表若干人的体重，再给定一个正数limit，表示所有船共同拥有的载重量，
 * 每艘船最多坐两人，且不能超过载重
 * 想让所有的人同时过河，并且用最好的分配方法让船尽量少，返回最少的船数
 * <p>
 * 解题思路：
 * 1.首先确定 <= limit / 2的位置mid
 * 2.然后有L和R两个变量，L指向mid位置，R指向mid + 1位置
 * 3.接着按照mid划分左右两边，有两种情况，左边消耗完和右边消耗完
 * 举例：
 * limit = 10
 * mid = 10 / 2 = 5
 * -      X  X  √  √  √  √  √  X  X  X  X              √  √  √  √  √
 * arr = {1, 1, 1, 1, 1, 2, 3, 5, 5, 5, 5,             6, 6, 8, 8, 9}
 * -            L                                                  R
 * (1)右边先消耗完的情况：
 * L位置的值5和R位置的值6的和超出limit，标记此时的L位置为X，L往左移动
 * 一直到L位置的值3和R位置的值6的和没有超出limit，标记此时的L和R位置为√，L往左移动，R往右移动
 * 此时L位置的值2和R位置的值6的和没有超出limit,标记此时的L和R位置为√，L往左移动，R往右移动
 * 此时L位置的值1和R位置的值8的和没有超出limit,标记此时的L和R位置为√，L往左移动，R往右移动
 * 此时L位置的值1和R位置的值8的和没有超出limit,标记此时的L和R位置为√，L往左移动，R往右移动
 * 此时L位置的值1和R位置的值9的和没有超出limit,标记此时的L和R位置为√，此时R不能往右移动，右边已经消耗完
 * 剩余的左边全部标记为X，
 * 记录√的个数：10，然后除以2，得到5，因为要两个人凑一艘船
 * 记录X的个数：6，然后除以2，得到3，因为左边都是 <= limit / 2的，所以两个人必定能凑一艘船
 * 最终至少需要的船数：5 + 3 = 8艘
 * limit = 10
 * mid = 10 / 2 = 5
 * -      X  X  √  √  √  √  √  X  X  X  X              √  √  √  √  √
 * arr = {1, 1, 1, 1, 1, 2, 3, 5, 5, 5, 5,             6, 6, 8, 8, 9, 10}
 * -            L                                                  R
 * (2)左边先消耗完的情况：
 * L位置的值5和R位置的值6的和超出limit，标记此时的L位置为X，L往左移动
 * L一直往左移动到L位置的值3和R位置的值6的和没有超出limit，标记此时的L和R位置为√，L往左移动，R往右移动
 * 此时L位置的值2和R位置的值6的和没有超出limit,标记此时的L和R位置为√，L往左移动，R往右移动
 * 此时L位置的值1和R位置的值8的和没有超出limit,标记此时的L和R位置为√，L往左移动，R往右移动
 * 此时L位置的值1和R位置的值8的和没有超出limit,标记此时的L和R位置为√，L往左移动，R往右移动
 * 此时L位置的值1和R位置的值9的和没有超出limit,标记此时的L和R位置为√，此时R不能往右移动，因为R位置的10本身就==limit，加上左边剩余的任何一个数都会超出limit
 * 所以左边先消耗完，剩余没有标记的，剩余的左边全部标记为X，
 * 记录√的个数：10，然后除以2，得到5，因为要两个人凑一艘船，并且一定是成双成对的
 * 记录X的个数：6，然后除以2，(一定要记得向上取整)得到3，因为左边都是 <= limit / 2的，所以两个人必定能凑一艘船
 * 记录右边剩余没有被消耗的个数：1
 * 最终至少需要的船数：5 + 3 + 1= 9艘
 * Leetcode链接:
 * https://leetcode.com/problems/boats-to-save-people/
 *
 * @author xcy
 * @date 2022/7/13 - 8:33
 */
public class Code05_BoatsToSavePeople {
	public static void main(String[] args) {
		int[] arr = {1, 1, 1, 1, 1, 2, 3, 5, 5, 5, 5, 6, 6, 8, 8, 9, 10};
		int limit = 10;
		int count1 = numRescueBoats1(arr, limit);
		int count2 = leastNeedNumberOfShips(arr, limit);
		System.out.println(count1);
		System.out.println(count2);
	}

	public static int numRescueBoats1(int[] arr, int limit) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		int N = arr.length;
		Arrays.sort(arr);
		if (arr[N - 1] > limit) {
			return -1;
		}
		int lessR = -1;
		for (int i = N - 1; i >= 0; i--) {
			if (arr[i] <= (limit / 2)) {
				lessR = i;
				break;
			}
		}
		if (lessR == -1) {
			return N;
		}
		int L = lessR;
		int R = lessR + 1;
		int noUsed = 0;
		while (L >= 0) {
			int solved = 0;
			while (R < N && arr[L] + arr[R] <= limit) {
				R++;
				solved++;
			}
			if (solved == 0) {
				noUsed++;
				L--;
			} else {
				L = Math.max(-1, L - solved);
			}
		}
		int all = lessR + 1;
		int used = all - noUsed;
		int moreUnsolved = (N - all) - used;
		return used + ((noUsed + 1) >> 1) + moreUnsolved;
	}

	public static int leastNeedNumberOfShips(int[] arr, int limit) {
		//人的体重不可能为负数和0，船的载重也不可能为负数和0
		if (arr == null || arr.length == 0 || limit < 1) {
			return 0;
		}
		int N = arr.length;
		//排序
		Arrays.sort(arr);
		//排完序之后，数组最后一个元素大于limit，表示人根本就没办法上船，一上船船就沉了
		if (arr[N - 1] > limit) {
			return -1;
		}
		//右侧 <= (limit / 2)
		int rightLeast = -1;
		for (int i = N - 1; i >= 0; i--) {
			if (arr[i] <= (limit / 2)) {
				rightLeast = i;
				break;
			}
		}
		//没有右侧 <= (limit / 2)，表示只能一个人上一只船，返回N
		if (rightLeast == -1) {
			return N;
		}

		int L = rightLeast;
		int R = rightLeast + 1;
		//标记正确的
		List<Integer> correct = new ArrayList<>();
		//标记错误的
		List<Integer> error = new ArrayList<>();

		int ans = 0;
		while (L >= 0 && R <= N - 1) {
			if (arr[L] + arr[R] > limit) {
				error.add(arr[L]);
				L--;
			} else if (arr[L] + arr[R] <= limit) {
				//L和R位置都标记为√
				correct.add(arr[L]);
				correct.add(arr[R]);
				L--;
				R++;
			} else {
				//右边先消耗完
				if (R == N - 1) {
					ans += (N - R);
				} else {
					break;
				}
			}
		}
		//向上取整
		//System.out.println((2101 + (700 - 1)) / 700);
		ans += correct.size() / 2 + (int) (Math.ceil(error.size() / 2));
		return ans;
	}
}
