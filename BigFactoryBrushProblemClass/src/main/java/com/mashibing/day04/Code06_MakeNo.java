package com.mashibing.day04;

/**
 * 题目六：
 * 生成长度为size的达标数组，什么叫达标？
 * 对于任意的i < k < j，满足[i] + [j] != [k] * 2。
 * 给定一个正数size，返回长度为size的达标数组
 * <p>
 * 解题思路：
 * 1.i，k，j表示数组的左中右位置，任意左边的数 + 右边的数 != 中间的数 * 2
 * 2.假设arr[] = {a, b, c}，那么 a + c != b * 2
 * 可以推出：2a + 2c != 2b * 2，继续推出：(2a + 1) + (2c + 1) != (2b + 1) * 2
 * 由此可以组成新的数组array[] = {2a, 2b, 2c, 2a + 1, 2b + 1, 2c + 1}
 * 分为三种情况：
 * (1)i和j都从左边的2a,2b,2c中取出，满足2a + 2c != 2b * 2，达标
 * (2)i和j都从右边的2a + 1, 2b + 1, 2c + 1中取出，满足(2a + 1) + (2c + 1) != (2b + 1) * 2，达标
 * (3)i和j随机取出，2a,2b,2c都是偶数，2a + 1,2b + 1,2c + 1都是奇数，偶数 + 奇数 != 某个数 * 2，达标
 * 3.那么如何组成新的数组都达标
 * 假设需要组成的新的数组array[].length = 7，arr[左] + arr[右] != arr[中]
 * 那么需要数组因子arr1，并且arr1.length = 4,7 / 2向上取整，
 * 那么需要数组因子arr2，并且arr2.length = 2,4 / 2，
 * 那么需要数组因子arr3，并且arr3.length = 1,2 / 2，那么arr3[]只有一个数，假设arr3[0] = 1
 * 那么可以组成arr2[] = {2 * 1, 2 * 1 + 1} = {2, 3}
 * 那么可以组成arr1[] = {2 * 2, 2 * 3, 2 * 2 + 1, 2 * 3 + 1} = {4, 6, 5, 7}
 * 那么可以组成array[] = {2 * 4, 2 * 6, 2 * 5, 2 * 7, 2 * 4 + 1, 2 * 6 + 1, 2 * 5 + 1, 2 * 7 + 1}
 * = {8, 12, 10, 14, 9, 13, 11, 15}，去掉最后一个元素，array[] = {8, 12, 10, 14, 9, 13, 11}
 * 4.这道题使用了分治的思想
 *
 * @author xcy
 * @date 2022/7/15 - 11:39
 */
public class Code06_MakeNo {
	public static void main(String[] args) {
		System.out.println("测试开始！");
		for (int N = 1; N < 1000; N++) {
			int[] arr = returnTargetArray(N);
			if (!isValid(arr)) {
				System.out.println("测试出错！");
			}
		}
		System.out.println("测试结束！");
		System.out.println(isValid(returnTargetArray(1042)));
		System.out.println(isValid(returnTargetArray(2981)));
	}

	/**
	 * 满足i < k < j，
	 *
	 * @param size
	 * @return 返回达标数组
	 */
	public static int[] returnTargetArray(int size) {
		if (size == 1) {
			return new int[]{1};
		}
		//向上取整
		int halfSize = (size + 1) / 2;
		//解决一半问题
		int[] base = returnTargetArray(halfSize);
		//达标数组
		int[] ans = new int[size];
		int index = 0;
		//左侧
		for (; index < halfSize; index++) {
			ans[index] = base[index] * 2;
		}
		//右侧
		for (int i = 0; index < size; index++, i++) {
			ans[index] = base[i] * 2 + 1;
		}
		return ans;
	}

	/**
	 * 校验函数
	 *
	 * @param arr
	 * @return
	 */
	public static boolean isValid(int[] arr) {
		int N = arr.length;
		for (int i = 0; i < N; i++) {
			for (int k = i + 1; k < N; k++) {
				for (int j = k + 1; j < N; j++) {
					if (arr[i] + arr[j] == 2 * arr[k]) {
						return false;
					}
				}
			}
		}
		return true;
	}
}
