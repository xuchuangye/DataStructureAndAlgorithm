package com.mashibing.dynamic;

/**
 * 马跳棋盘
 * <p>
 * 棋盘的最左下角是(0, 0)位置，那么整个棋盘就是横坐标上9条线，纵坐标上10条线的区域
 * 给定三个参数：x, y, k
 * 返回"马"从(0, 0)位置出发，必须走k步，最后落在(x, y)上的方法数有多少种？
 * <p>
 * 棋盘大小 9 * 10，马所在的位置(0, 0)，马要到达的位置(x, y)，必须要跳k次
 *
 * @author xcy
 * @date 2022/5/10 - 7:42
 */
public class HorseJumpCheckerboard {
	/**
	 * 棋盘横坐标
	 */
	private static final int Abscissa = 9;
	/**
	 * 棋盘纵坐标
	 */
	private static final int Ordinate = 10;

	public static void main(String[] args) {
		int x = 7;
		int y = 7;
		int k = 10;
		int count1 = horseJumpCheckerboard(x, y, k);
		int count2 = horseJumpCheckerboardWithTable(x, y, k);
		System.out.println(count1);
		System.out.println(count2);
	}


	/**
	 * 返回"马"从(0, 0)位置出发，必须走k步，最后落在(x, y)上的方法数有多少种？ --> 使用暴力递归的方式
	 *
	 * 时间复杂度：O(8 ^ k) k表示马必须要跳的步数
	 * @param x 目标位置的横坐标
	 * @param y 目标位置的纵坐标
	 * @param k 马必须要跳的步数
	 * @return 返回"马"从(0, 0)位置出发，必须走k步，最后落在(x, y)上的方法数有多少种？
	 */
	public static int horseJumpCheckerboard(int x, int y, int k) {
		if (k < 0 || x < 0 || x > Abscissa - 1 || y < 0 || y > Ordinate - 1) {
			return 0;
		}
		//马从(0, 0)的位置开始出发
		return coreLogic(0, 0, k, x, y);
	}

	/**
	 * 核心功能
	 *
	 * @param x    马的起始位置的横坐标
	 * @param y    马的起始位置的纵坐标
	 * @param rest 马的剩余步数
	 * @param a    目标位置的横坐标
	 * @param b    目标位置的纵坐标
	 * @return 返回"马"从(0, 0)位置出发，必须走k步，最后落在(x, y)上的方法数有多少种？
	 */
	public static int coreLogic(int x, int y, int rest, int a, int b) {
		//越界问题
		if (x < 0 || x > Abscissa - 1 || y < 0 || y > Ordinate - 1) {
			return 0;
		}
		//如果马的剩余步数为0，判断马的当前位置是否在目标位置，如果是，则返回1，如果不是，证明该次无效，返回0
		if (rest == 0) {
			return (x == a && y == b) ? 1 : 0;
		}
		//马能够跳的8个方向
		int situation = coreLogic(x + 1, y + 2, rest - 1, a, b);
		situation += coreLogic(x + 2, y + 1, rest - 1, a, b);
		situation += coreLogic(x + 2, y - 1, rest - 1, a, b);
		situation += coreLogic(x + 1, y - 2, rest - 1, a, b);
		situation += coreLogic(x - 1, y - 2, rest - 1, a, b);
		situation += coreLogic(x - 2, y - 1, rest - 1, a, b);
		situation += coreLogic(x - 2, y + 1, rest - 1, a, b);
		situation += coreLogic(x - 1, y + 2, rest - 1, a, b);
		return situation;
	}

	/**
	 * 返回"马"从(0, 0)位置出发，必须走k步，最后落在(x, y)上的方法数有多少种？ --> 使用动态规划的方式
	 *
	 * 时间复杂度：O(9 * 10 * k) -> O(k) k表示马必须要跳的步数
	 * @param a 目标位置的横坐标
	 * @param b 目标位置的纵坐标
	 * @param k 马必须要跳的步数
	 * @return 返回"马"从(0, 0)位置出发，必须走k步，最后落在(x, y)上的方法数有多少种？
	 */
	public static int horseJumpCheckerboardWithTable(int a, int b, int k) {
		/*if (k < 0 || a < 0 || a > Abscissa - 1 || b < 0 || b > Ordinate - 1) {
			return 0;
		}*/
		int[][][] table = new int[Abscissa][Ordinate][k + 1];
		//rest第0行
		table[a][b][0] = 1;
		//从rest第1行开始
		for (int rest = 1; rest <= k; rest++) {
			for (int x = 0; x < Abscissa; x++) {
				for (int y = 0; y < Ordinate; y++) {
					int situation = preventCrossTheBorder(table, x + 1, y + 2, rest - 1);
					situation += preventCrossTheBorder(table,x + 2, y + 1, rest - 1);
					situation += preventCrossTheBorder(table,x + 2, y - 1, rest - 1);
					situation += preventCrossTheBorder(table,x + 1, y - 2, rest - 1);
					situation += preventCrossTheBorder(table,x - 1, y - 2, rest - 1);
					situation += preventCrossTheBorder(table,x - 2, y - 1, rest - 1);
					situation += preventCrossTheBorder(table,x - 2, y + 1, rest - 1);
					situation += preventCrossTheBorder(table,x - 1, y + 2, rest - 1);
					table[x][y][rest] = situation;
				}
			}
		}
		//return coreLogic(0, 0, k, a, b);
		return table[0][0][k];
	}

	/**
	 * 防止出现越界的问题
	 * @param table 三维数组
	 * @param x 横坐标
	 * @param y 纵坐标
	 * @param k 步数
	 * @return
	 */
	private static int preventCrossTheBorder(int[][][] table, int x, int y, int k) {
		if (x < 0 || x > Abscissa - 1 || y < 0 || y > Ordinate - 1) {
			return 0;
		}
		return table[x][y][k];
	}
}
