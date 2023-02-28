package com.mashibing.TimeComplexity;

/**
 * @author xcy
 * @date 2021/9/10 - 20:25
 */

/**
 * 二分法
 * 在一个有序数组中，找到>=某个数的最左侧位置
 */
public class BSExists {
	public static boolean exist(int[] sortedArr, int num) {
		if (sortedArr == null || sortedArr.length == 0) {
			return false;
		}

		int L = 0;//最左边
		int R = sortedArr.length - 1;//最右边
		int mid = 0;//表示临时中间位置的索引
		// L..R
		while (L < R) {
			//mid = (L + R) / 2
			//如果L和R的数字特别 大，如果相加可能超出int类型的最大值范围，容易出现了不期望的结果

			//R - L 表示中间的距离
			//L + (R - L) / 2 表示R和L之间的距离的一半，再加上 + L 就得到中间的位置
			//并且非常安全，不会出现数据溢出的结果
			//因为位运算要比算数运算效率高
			//L + (R - L) / 2  ==> L + (R - L) >> 1
			mid = L + ((R - L) >> 1);
			//如果中间位置的元素刚好等于期望的值，说明正好处于
			if (sortedArr[mid] == num) {
				return true;
			}
			//中间位置的元素大于num，说明在右边并且不会有num这个值，那么就需要去左侧二分，R - 1
			else if (sortedArr[mid] > num) {
				R = mid - 1;
			}
			//中间位置的元素小于num，说明在左边并且不会有num这个值，那么就需要去右侧二分，L + 1
			else {
				L = mid + 1;
			}
		}
		return sortedArr[L] == num;
	}
}
