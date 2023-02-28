package com.mashibing.matrixcycle;

/**
 * 题目五：
 * 给定一个正方形矩阵matrix，原地调整成顺时针90度转动的样子
 * a  b  c		g  d  a
 * d  e  f	    h  e  b
 * g  h  i	    i  f  c
 * 举例：
 * j h  ->  k j
 * k g      g h
 * <p>
 * 注意事项：
 * 1.必须要原地调整
 * 2.一定是正方形
 * <p>
 * 思路分析：
 * 1.一圈一圈的调整
 * 举例：
 * 假设matrix[][] = new int[5][5];
 * -  0    1    2    3    4
 * 0  o —— o —— o —— o —— o
 * -  |  \                |
 * 1  o    o —— o —— o    o
 * -  |    | \       |    |
 * 2  o    o    o    o    o
 * -  |    |      \  |    |
 * 3  o    o —— o —— o    o
 * -  |                \  |
 * 4  o —— o —— o —— o —— o
 * (1)通过坐标(0, 0)和(4, 4)确定最外层的圈,(0++, 0++)以及(4--, 4--)
 * (2)(0, 0) -> (1, 1),(4, 4) -> (3, 3)通过坐标(1, 1)和(3, 3)确定第二层的圈
 * (3)(1, 1) -> (2, 2),(3, 3) -> (2, 2)通过坐标(2, 2)确定第三层的圈
 * (4)一旦错过去,出现(3, 3)和(1, 1),说明调整结束
 * 2.使用f(a, b, c, d)表示一圈，一圈里怎么调整，f()，main()非常简单
 * 3.如何将一圈进行分组
 * 举例：
 * A B C A
 * C     B
 * B     C
 * A C B A
 * N * N的矩阵，一圈分成N - 1组，N = 4，分成3组，第1组为A，第2组为B，第3组为C
 * A B C D A
 * D       B
 * C       C
 * B       D
 * A D C B A
 * N * N的矩阵，一圈分成N - 1组，N = 5，分成4组，第1组为A，第2组为B，第3组为C，第4组为D
 * 抽象化：
 * - b       d
 * a O ----- O
 * - |       |
 * - |       |
 * - |       |
 * c O ----- O
 * 第一个循环，也就是分组的循环，一共有d - b + 1个列，分成d - b组，从第0组开始，所以 < (d - b)
 * - b
 * a O O O O O
 * - |       |
 * - |       |
 * - |       |
 * - O O O O O d
 * -         c
 * 第二个循环，也就是互相交换的语句:
 * 左上角开始往右：matrix[a][b + i]
 * 右上角开始往下：matrix[a + i][d]
 * 右下角开始往左：matrix[c][d - i]
 * 左下角开始往上：matrix[c - i][b]
 *
 * @author xcy
 * @date 2022/6/9 - 16:43
 */
public class Code05_RotateMatrix {
	public static void main(String[] args) {

	}

	public static void rotate(int[][] matrix) {
		if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
			return;
		}

		int a = 0;
		int b = 0;
		int c = matrix.length - 1;
		int d = matrix[0].length - 1;

		while (a < c) {
			rotateCoreLogic(matrix, a++, b++, c--, d--);
		}
	}

	public static void rotateCoreLogic(int[][] matrix, int a, int b, int c, int d) {
		//分组，一共d - b + 1个列/行，分成d - b组
		for (int i = 0; i < d - b; i++) {
			//保持左上角的值
			int temp = matrix[a][b + i];

			/*
			必须要倒着替换，否则值会被覆盖
			左上角开始往右：matrix[a][b + i]
			左下角开始往上：matrix[c - i][b]
			右下角开始往左：matrix[c][d - i]
			右上角开始往下：matrix[a + i][d]
			*/
			//左下角替换左上角的值
			matrix[a][b + i] = matrix[c - i][b];
			//右下角替换左下角的值
			matrix[c - i][b] = matrix[c][d - i];
			//右上角替换右下角的值
			matrix[c][d - i] = matrix[a + i][d];
			//左上角替换右上角的值
			matrix[a + i][d] = temp;
		}
	}
}
