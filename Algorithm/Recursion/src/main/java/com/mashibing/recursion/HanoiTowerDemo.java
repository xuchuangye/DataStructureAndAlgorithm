package com.mashibing.recursion;

/**
 * ----------------------------分治算法 -> 汉诺塔----------------------------
 * 打印n层汉诺塔从最左边移动到最右边的全部过程
 * <p>
 * 时间复杂度： O(2 ^ N - 1)
 * <p>
 * 汉诺塔的规则：
 * 1、不能圆盘大的在圆盘小的上边
 * 2、想要移动在底层的圆盘，必须先移走该圆盘上边的所有圆盘
 * <p>
 * 思路分析一：
 * 圆盘个数为1，从A塔移动到C塔
 * 圆盘个数为n，n >= 2时
 * 1、将A塔的所有圆盘分为两部分，将最下面的一个圆盘作为一部分，最上面的所有圆盘作为另一部分
 * 2、将其余的圆盘都移动到B塔，将最长的圆盘移动到C塔
 * 3、最后将B塔上的所有圆盘都移动到C塔
 * <p>
 * ----------------------------暴力递归算法 -> 汉诺塔----------------------------
 * <p>
 * 思路分析二：
 * 三个 塔，from汉诺塔移动的起点，to汉诺塔移动的终点，other汉诺塔移动的中转点
 * 圆盘层数为1时，直接从from移动到to
 * 圆盘层数不为1时，
 * 1、将from上的n - 1个(除了最后一个)圆盘移动到other
 * 2、将from上的最后一个圆盘移动到to
 * 3、将other上的n - 1个圆盘移动到to
 *
 * @author xcy
 * @date 2022/4/1 - 8:31
 */
public class HanoiTowerDemo {
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		hanoiTower(5, "左", "中", "右");
		long end = System.currentTimeMillis();
		System.out.println("第一个汉诺塔方法的测试时间：" + (end - start));
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("----------------");
		start = System.currentTimeMillis();
		hanoi(5, "左", "右", "中");
		end = System.currentTimeMillis();
		System.out.println("第二个汉诺塔方法的测试时间：" + (end - start));
	}

	/**
	 * 分治算法的思想
	 * 汉诺塔问题：一共有三个塔，如果将A塔的所有圆盘都移动到C塔，并且保证顺序不变，中间有个B塔进行辅助
	 *
	 * @param num 一共有多少个圆盘
	 * @param A   A塔
	 * @param B   B塔
	 * @param C   C塔
	 */
	public static void hanoiTower(int num, String A, String B, String C) {
		//最下面的圆盘
		if (num == 1) {
			System.out.println("第" + num + "个圆盘从" + A + "移动到" + C);
		}
		//最上面的圆盘，不管多少个圆盘，都属于最上面的
		else {
			//1、将A塔最上面的所有圆盘都移动到B塔
			//最上面的所有圆盘的个数为num - 1，中间需要借助C塔辅助进行移动
			hanoiTower(num - 1, A, C, B);
			//2、将A塔最下面的圆盘移动到C塔
			System.out.println("第" + num + "个圆盘从" + A + "移动到" + C);
			//3、将B塔所有的圆盘都移动到C塔
			//因为从A塔移动到B塔共有num - 1个圆盘，所以B塔移动到C塔的圆盘个数也是num - 1
			//中间需要借助A塔辅助进行移动
			hanoiTower(num - 1, B, A, C);
		}
	}

	/**
	 * 暴力递归算法的思想
	 * 汉诺塔问题 --> 使用递归的方式
	 *
	 * @param num   汉诺塔的圆盘层数
	 * @param from  汉诺塔移动的起点
	 * @param to    汉诺塔移动的终点
	 * @param other 汉诺塔移动的中转点
	 */
	public static void hanoi(int num, String from, String to, String other) {
		if (num == 1) {
			System.out.println("第" + num + "个圆盘从" + from + "移动到" + to);
		} else {
			hanoi(num - 1, from, other, to);
			System.out.println("第" + num + "个圆盘从" + from + "移动到" + to);
			hanoi(num - 1, other, to, from);
		}
	}

	/**
	 * 初始的暴力递归
	 * 1、如果圆盘只有一层
	 * 直接从左移动到右
	 * 2、如果圆盘有多层
	 * 1)从左移动n - 1个到中
	 * 2)从左移动最后一个圆盘n到右
	 * 3)从 中移动n - 1个到右
	 *
	 * @param n 圆盘层数
	 */
	public static void leftToRight(int n) {
		//1、如果圆盘只有一层
		//直接从左移动到右
		if (n == 1) {
			System.out.println("第" + n + "个圆盘从左移动到右");
			return;
		}
		//2、如果圆盘有多层
		//1)从左移动n - 1个到中
		leftToMid(n - 1);
		//2)从左移动最后一个圆盘n到右
		System.out.println("第" + n + "个圆盘从左移动到右");
		//3)从 中移动n - 1个到右
		midToRight(n - 1);
	}

	/**
	 * 1、如果圆盘层数只有一层
	 * 直接从左移动到中
	 * 2、如果圆盘层数有多层
	 * 1)从左移动n - 1个圆盘到右
	 * 2)从左移动最后一个圆盘n到中
	 * 3)从右移动n - 1个圆盘到中
	 *
	 * @param n
	 */
	public static void leftToMid(int n) {
		if (n == 1) {
			System.out.println("第" + n + "个圆盘从左移动到中");
			return;
		}
		leftToRight(n - 1);
		System.out.println("第" + n + "个圆盘从左移动到中");
		rightToMid(n - 1);
	}

	/**
	 * 1、如果圆盘层数只有一层
	 * 直接从中移动到右
	 * 2、如果圆盘层数有多层
	 * 1)从中移动n - 1个圆盘到左
	 * 2)从中移动最后一个圆盘n到右
	 * 3)从左移动n - 1个圆盘到右
	 *
	 * @param n
	 */
	public static void midToRight(int n) {
		if (n == 1) {
			System.out.println("第" + n + "个圆盘从中移动到右");
			return;
		}
		midToLeft(n - 1);
		System.out.println("第" + n + "个圆盘从中移动到右");
		leftToRight(n - 1);
	}

	/**
	 * 1、如果圆盘层数只有一层
	 * 直接从右移动到中
	 * 2、如果圆盘层数有多层
	 * 1)从右移动n - 1个圆盘到左
	 * 2)从右移动最后一个圆盘n到中
	 * 3)从左移动n - 1个圆盘到中
	 *
	 * @param n
	 */
	public static void rightToMid(int n) {
		if (n == 1) {
			System.out.println("第" + n + "个圆盘从右移动到中");
			return;
		}
		rightToLeft(n - 1);
		System.out.println("第" + n + "个圆盘从右移动到中");
		leftToMid(n - 1);
	}

	/**
	 * 1、如果圆盘层数只有一层
	 * 直接从中移动到左
	 * 2、如果圆盘层数有多层
	 * 1)从中移动n - 1个圆盘到右
	 * 2)从中移动最后一个圆盘n到左
	 * 3)从右移动n - 1个圆盘到左
	 *
	 * @param n
	 */
	public static void midToLeft(int n) {
		if (n == 1) {
			System.out.println("第" + n + "个圆盘从中移动到左");
			return;
		}
		midToRight(n - 1);
		System.out.println("第" + n + "个圆盘从中移动到左");
		rightToLeft(n - 1);
	}

	/**
	 * 1、如果圆盘层数只有一层
	 * 直接从右移动到左
	 * 2、如果圆盘层数有多层
	 * 1)从右移动n - 1个圆盘到中
	 * 2)从右移动最后一个圆盘n到左
	 * 3)从中移动n - 1个圆盘到左
	 *
	 * @param n
	 */
	public static void rightToLeft(int n) {
		if (n == 1) {
			System.out.println("第" + n + "个圆盘从右移动到左");
			return;
		}
		rightToMid(n - 1);
		System.out.println("第" + n + "个圆盘从右移动到左");
		midToLeft(n - 1);
	}
}
