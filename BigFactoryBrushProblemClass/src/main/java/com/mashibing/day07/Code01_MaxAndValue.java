package com.mashibing.day07;

/**
 * 题目一：
 * 给定一个非负数组成的数组，长度一定大于1，想知道数组中哪两个数&的结果最大，返回这个最大结果。
 * 要求时间复杂度O(N)，额外空间复杂度O(1)
 * <p>
 * 解题思路：
 * 1.数组元素都为非负数，int类型32位，那么第31位一定是0，所以不需要管
 * 2.从第31位到第0位，在数组中进行&运算，会出现三种情况：
 * 如果在某位上出现1的个数 < 2，表示只有一个数是最大，而题目要求是返回两个数&结果最大的，所以所有的数仍然有用
 * 如果在某位上出现1的个数 == 2，表示只有这两个数&的结果是最大的，直接返回
 * 如果在某位上出现1的个数 > 2，表示有多个数在该位上是1，那么最大的结果中该位上肯定为1，继续下一位
 *
 * @author xcy
 * @date 2022/7/23 - 7:50
 */
public class Code01_MaxAndValue {
	public static void main(String[] args) {

	}

	/**
	 * 时间复杂度：O(N)，额外空间复杂度：O(1)
	 *
	 * @param arr
	 * @return
	 */
	public static int maxAndValue(int[] arr) {
		if (arr == null || arr.length < 2) {
			return 0;
		}
		//表示垃圾区的起始位置
		//有用区:arr[0 ~ arr.length - 1]，垃圾区：arr[M ~ ]
		//此时数组所有的元素都是有用的
		int M = arr.length;
		int ans = 0;
		//int类型32位，数组元素是非负数，所以符号位，也就是第31位是0，表示非负数
		for (int bit = 30; bit >= 0; bit--) {
			int i = 0;
			//保存垃圾区的起始位置，防止遇到情况时可以回退
			int temp = M;
			//i始终在有效区进行遍历，也就是0 ~ M - 1的范围上
			while (i <= M - 1) {
				//当前元素的bit位上不是1，那么当前元素就和垃圾区的前一个元素进行交换
				if ((arr[i] & (1 << bit)) == 0) {
					swap(arr, i, --M);
				}
				//当前元素的bit位上是1，那么就继续下一个元素
				else {
					i++;
				}
			}
			//如果垃圾区来到2的位置，表示arr[0]和arr[1]的值有效并且arr[0] & arr[1]在bit位上是1
			//直接返回就是最大的结果
			if (M == 2) {
				return arr[0] & arr[1];
			}
			//如果垃圾区来到小于2的位置，表示所有的元素都有用，重新回到原来的垃圾区，继续下一位的判断
			else if (M < 2) {
				M = temp;
			}
			//否则，表示多个数在bit位上都是1，那么返回的最大结果中，当前bit位上肯定是1
			//将当前bit位上的1使用或运算标记到结果ans中
			else {
				ans |= (1 << bit);
			}
		}
		return ans;
	}

	public static void swap(int[] arr, int i, int j) {
		int temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}
}
