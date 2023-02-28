package com.mashibing.problem;

/**
 * 递归 - 迷宫问题
 *
 * @author xcy
 * @date 2022/3/19 - 10:01
 */
public class MazeDemo {
	/**
	 * 行
	 */
	public static final int ROW = 8;
	/**
	 * 列
	 */
	public static final int COL = 7;
	/**
	 * 地图
	 */
	public static final int[][] map = new int[ROW][COL];

	static {
		//设置第一列和最后一列的所有元素为1，表示为墙
		for (int i = 0; i < COL; i++) {
			map[0][i] = 1;
			map[7][i] = 1;
		}

		//设置第一行和最后一行的所有元素为1，表示为墙
		for (int i = 0; i < ROW; i++) {
			map[i][0] = 1;
			map[i][6] = 1;
		}
	}

	public static void main(String[] args) {
		//设置挡板
		map[3][1] = 1;
		map[3][2] = 1;

		System.out.println("地图初始的情况");
		for (int i = 0; i < ROW; i++) {
			for (int j = 0; j < COL; j++) {
				System.out.print(map[i][j] + " ");
			}
			System.out.println();
		}

		//使用递归回溯
		setWay(map, 1, 1);

		System.out.println("地图走过之后的情况");
		for (int i = 0; i < ROW; i++) {
			for (int j = 0; j < COL; j++) {
				System.out.print(map[i][j] + " ");
			}
			System.out.println();
		}
	}

	/**
	 *
	 * @param map 地图
	 * @param i 行
	 * @param j 列
	 * @return
	 */
	public static boolean setWay(int[][] map, int i, int j) {
		//设置迷宫出口
		if (map[6][5] == 2) {
			return true;
		} else {
			//当前位置小球还没有走过，所以map[i][j] == 0
			if (map[i][j] == 0) {
				map[i][j] = 2;//将当前位置标记为2，证明已经走过了


				//设置小球走路的策略：下 -> 右 -> 上 -> 左
				if (setWay(map, i + 1, j)) {//先向下走
					return true;
				} else if (setWay(map, i, j + 1)) {//下走不通，向右走
					return true;
				} else if (setWay(map, i - 1, j)) {//右走不通，向上走
					return true;
				} else if (setWay(map, i, j - 1)) {//上走不通，向左走
					return true;
				} else {
					map[i][j] = 3;//将当前位置标记为3，证明所有方向都走不通
					return false;
				}

			} else {
				//map[i][j] != 0，只有几种情况：map[i][j] = 1,2,3
				return false;
			}
		}
	}
}
