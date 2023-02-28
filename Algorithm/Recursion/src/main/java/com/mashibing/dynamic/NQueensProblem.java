package com.mashibing.dynamic;

/**
 * N皇后的问题
 * <p>
 * N皇后问题是指在N * N的棋盘上要摆N个皇后
 * 要求任何两个皇后不能在同一行，不能在同一列，也不能在同一条斜线上
 * <p>
 * 给定一个整数N，返回N皇后的摆法有多少种
 * <p>
 * 举例：
 * N = 1，返回1
 * N = 2或者N = 3，2皇后和3皇后问题无论怎么摆都不行，返回0
 * N = 8，返回92
 *
 * @author xcy
 * @date 2022/5/13 - 16:39
 */
public class NQueensProblem {
	public static void main(String[] args) {
		int n = 15;

		long start = System.currentTimeMillis();
		System.out.println(returnNQueenPendulumMethod(n));
		long end = System.currentTimeMillis();
		System.out.println("cost time: " + (end - start) + "ms");

		start = System.currentTimeMillis();
		System.out.println(returnNQueenPendulumMethodWithBitOperation(n));
		end = System.currentTimeMillis();
		System.out.println("cost time: " + (end - start) + "ms");
	}

	/**
	 * 使用暴力递归的方式
	 *
	 * @param N 既表示N * N的棋盘也表示皇后的个数
	 * @return 给定一个整数N，返回在N * N的棋盘上，N个皇后的摆法有多少种
	 */
	public static int returnNQueenPendulumMethod(int N) {
		if (N < 1) {
			return 0;
		}
		int[] record = new int[N];
		return coreLogic(record, 0, N);
	}

	/**
	 * 核心逻辑
	 *
	 * @param record 存放皇后的数组，索引表示存放位置的行，索引对应的值表示存放位置的列
	 * @param row    既表示第index行 --> 范围：0 ~ N - 1
	 * @param N      表示N * N的棋盘
	 * @return 给定一个整数N，返回在N * N的棋盘上，N个皇后的摆法有多少种
	 */
	public static int coreLogic(int[] record, int row, int N) {
		//行已经摆完了，证明方法行得通，返回1种摆法
		if (row == N) {
			return 1;
		}

		int num = 0;
		//摆皇后的列col不能超过摆皇后的行N，所以列col的范围：0 ~ N - 1
		for (int col = 0; col < N; col++) {
			//判断皇后之间 是否冲突
			if (isConflict(record, row, col)) {
				//在row行记录对应的列col
				record[row] = col;
				num += coreLogic(record, row + 1, N);
			}
		}
		return num;
	}

	/**
	 * 判断两个位置是否冲突，包括是否在同一列，是否在同一条斜线
	 *
	 * @param record k表示第k行，record[k]表示第record[k]列
	 * @param i      位置1
	 * @param j      位置2
	 * @return 如果在同一列或者同一条斜线，返回false，否则返回true
	 */
	public static boolean isConflict(int[] record, int i, int j) {
		for (int k = 0; k < i; k++) {
			if (j == record[k] || Math.abs(record[k] - j) == Math.abs(i - k)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 使用暴力递归的方式 --> 位运算进行优化，优化了常数时间
	 *
	 * @param N 既表示N * N的棋盘也表示皇后的个数
	 * @return 给定一个整数N，返回在N * N的棋盘上，N个皇后的摆法有多少种
	 */
	public static int returnNQueenPendulumMethodWithBitOperation(int N) {
		//限制数值范围
		if (N < 1 || N > 32) {
			return 0;
		}
		//生成limit，例如：如果摆放的是7皇后，那么limit = 000...1111111
		int limit = N == 32 ? -1 : (1 << N) - 1;
		//列限制：000...0000000
		//左下限制：000...0000000
		//右下限制：000...0000000
		return coreLogicWithBitOperation(limit, 0, 0, 0);
	}

	/**
	 * N皇后问题
	 * @param limit     限制，例如：如果摆放的是7皇后，那么limit = 000...1111111
	 * @param colLimit  之前皇后列的限制
	 * @param leftDown  之前皇后左下对角线限制
	 * @param rightDown 之前皇后右下对角线限制
	 * @return
	 */
	public static int coreLogicWithBitOperation(int limit, int colLimit, int leftDown, int rightDown) {
		//如果列限制 == limit，说明所有的皇后都已经摆放好了，这是一种方法，返回1
		if (colLimit == limit) {
			return 1;
		}
		//所有皇后可以摆放的位置
		int pos = limit & (~(colLimit | leftDown | rightDown));
		//最右侧的1
		int mostRightOne = 0;
		//累加能够摆放所有皇后的方法数
		int result = 0;
		//如果pos都为0，不执行while循环，直接返回result == 0的摆放方法数
		while (pos != 0) {
			//取出最右侧的1，也就是可以摆放皇后的位置
			//mostRightOne = pos & (~pos + 1);
			mostRightOne = pos & (-pos);
			//减去最右侧的1，表示皇后已经摆放好了
			pos = pos - mostRightOne;
			//下一个皇后摆放的位置
			result += coreLogicWithBitOperation(
					//限制不变
					limit,
					//列限制 | 最右侧的1，表示列限制已经增加摆放好皇后的位置，下一个皇后不能在当前最右侧的1上摆放了
					colLimit | mostRightOne,
					//左下限制 | 最右侧的1，并且左移1位，表示左下限制已经增加摆放好皇后的位置，下一个皇后不能在当前最右侧的1上摆放了
					//左移1位，表示斜线之间不能摆放
					(leftDown | mostRightOne) << 1,
					//右下限制 | 最右侧的1，并且右移1位，表示右下限制已经增加摆放好皇后的位置，下一个皇后不能在当前最右侧的1上摆放了
					//右移1位，表示斜线之间不能摆放
					(rightDown | mostRightOne) >>> 1
			);
		}
		return result;
	}
}
