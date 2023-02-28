package com.mashibing.day18;

/**
 * 给定一个数组arr，长度为N，arr中的值只有1，2，3三种
 * arr[i] == 1，代表汉诺塔问题中，从上往下第i个圆盘目前在左
 * arr[i] == 2，代表汉诺塔问题中，从上往下第i个圆盘目前在中
 * arr[i] == 3，代表汉诺塔问题中，从上往下第i个圆盘目前在右
 * 那么arr整体就代表汉诺塔游戏过程中的一个状况，如果这个状况不是汉诺塔最优解运动过程中的状况，返回-1
 * 如果这个状况是汉诺塔最优解运动过程中的状态，返回它是第几个状态
 * <p>
 * 解题思路：
 * 1.
 * arr[]的元素值只有1,2,3这三个值，表示左边，中间，右边，arr[]的长度表示汉诺塔有多少层
 * 2.
 * 题目举例：
 * [1, 1, 1]这是最优解的第1个状态，也就是1,2,3圆盘都在左边
 * [3, 1, 1]这是最优解的第2个状态，也就是1在右边，2,3圆盘都在左边
 * 3.
 * 假设汉诺塔层数为7，arr[]的长度为7
 * 目标：将1~7的圆盘全部从左边移动到右边
 * 拆分步骤
 * (1).将1~6全部从左边移动到中间
 * (2).将7从左边移动到右边
 * (3).将1~6全部从中间移动到右边
 * 4.
 * 抽象为一个函数f(arr, i, from, to, other)
 * i表示arr[]中1 ~ i的圆盘
 * from表示1 ~ i正处于哪？左 中 右
 * to表示1 ~ i去哪？左 中 右
 * other表示除了from，to的另一个
 * 5.
 * 根据步骤3，抽象为三大步骤
 * (1).1 ~ i - 1从from移动到other
 * (2).i从from移动到to
 * (3).1 ~ i - 1从other移动到to
 *
 * @author xcy
 * @date 2022/8/8 - 14:47
 */
public class Code01_HanoiProblem {
	public static void main(String[] args) {

	}

	public static int hanoi(int[] arr) {
		//表示汉诺塔的层数
		int N = arr.length;
		//表示第0 ~ N - 1个圆盘从from移动到to
		//return process(arr, N - 1, from, to, other);
		return process(arr, N - 1, 1, 3, 2);
	}

	/**
	 * 三大步骤：
	 * 第1 ~ index - 1个圆盘，从from移动到other
	 * 第index个圆盘，从from移动到to
	 * 第1 ~ index - 1个圆盘，从other移动到to
	 * <p>
	 * 时间复杂度：O(N)
	 * 因为是单决策递归
	 *
	 * @param arr   原始数组，数组元素值只有1,2,3，分别表示左中右，数组长度为汉诺塔圆盘层数
	 * @param index arr[]中0 ~ index的圆盘
	 * @param from  当前0 ~ index圆盘正处于的位置
	 * @param to    当前0 ~ index圆盘正要移动到的位置
	 * @param other 另一个位置
	 * @return 返回arr[]中0 ~ index的圆盘从from移动到to的最优解的第几个状态
	 */
	public static int process(int[] arr, int index, int from, int to, int other) {
		if (index == -1) {
			return 0;
		}
		//因为第index个圆盘，也就是最后一层的圆盘，在最优解的情况下，一定是在from或者to的位置
		//而当前第index个圆盘，也就是最后一层圆盘，竟然来到other的位置，表示不是最优解，返回-1
		if (arr[index] == other) {
			return -1;
		}
		//arr[index] == from || arr[index] == to
		//当前第index个圆盘，也就是最后一层圆盘，还在from的位置上
		//那就表示第1 ~ index - 1个圆盘还没有从from移动到other
		if (arr[index] == from) {
			return process(arr, index - 1, from, other, to);
		} else {

			//arr[index] == to
			//已知汉诺塔的层数为N，那么所有圆盘从from移动到to的次数为2的N次方
			//第1步：第1 ~ index - 1个圆盘，已经从from的位置移动other的位置了，移动的次数为2的N次方 - 1
			//为什么要减1，因为还差当前第index个圆盘从from移动到to
			int situation1 = (1 << index) - 1;
			//第2步：当前第index个圆盘已经从from的位置移动到to的位置了
			int situation2 = 1;
			//第3步：还有后续步骤第1 ~ index - 1个圆盘从other的位置移动到to的位置
			int situation3 = process(arr, index - 1, other, to, from);
			//如果第1 ~ index - 1个圆盘无法从other的位置移动到to的位置，表示不是最优解，返回-1
			if (situation3 == -1) {
				return -1;
			}
			return situation1 + situation2 + situation3;
		}
	}
}
