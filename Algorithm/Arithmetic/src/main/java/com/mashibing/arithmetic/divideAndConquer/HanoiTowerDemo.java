package com.mashibing.arithmetic.divideAndConquer;

/**
 * 分治算法 -> 汉诺塔
 * <p>
 * 思路分析：
 * 圆盘个数为1，从A塔移动到C塔
 * 圆盘个数为n，n >= 2时
 * 1、将A塔的所有圆盘分为两部分，将最下面的一个圆盘作为一部分，最上面的所有圆盘作为另一部分
 * 2、将其余的圆盘都移动到B塔，将最长的圆盘移动到C塔
 * 3、最后将B塔上的所有圆盘都移动到C塔
 *
 * @author xcy
 * @date 2022/4/1 - 8:31
 */
public class HanoiTowerDemo {
	public static void main(String[] args) {
		hanoiTower(6, "A", "B",  "C");
	}

	/**
	 * 汉诺塔问题：一共有三个塔，如果将A塔的所有圆盘都移动到C塔，并且保证顺序不变，中间有个B塔进行辅助
	 * @param num 一共有多少个圆盘
	 * @param A A塔
	 * @param B B塔
	 * @param C C塔
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
}
