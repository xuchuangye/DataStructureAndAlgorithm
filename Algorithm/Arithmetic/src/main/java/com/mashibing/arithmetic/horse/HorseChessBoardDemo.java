package com.mashibing.arithmetic.horse;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * 马踏棋盘问题（骑士周游问题）
 *
 * 注意事项：
 * 马儿踏步的策略不一样，速度也可能不一样
 * @author xcy
 * @date 2022/4/4 - 16:26
 */
public class HorseChessBoardDemo {
	/**
	 * 棋盘的列
	 */
	public static final int X = 10;
	/**
	 * 棋盘的行
	 */
	public static final int Y = 10;
	public static void main(String[] args) {
		HorseChessBoard horseChessBoard = new HorseChessBoard(X, Y);
		//创建棋盘
		int[][] chessboard = new int[X][Y];
		//步数从1开始
		int step = 1;
		//马儿初始位置的行
		int row = 2;
		//马儿初始位置的 列
		int col = 5;

		Date dateStart = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String start = simpleDateFormat.format(dateStart);
		System.out.printf("骑士周游开始的时间：\n%s\n", start);

		System.out.println();

		horseChessBoard.traversalChessboard(chessboard, row - 1, col - 1, step);

		Date dateEnd = new Date();
		String end = simpleDateFormat.format(dateEnd);
		System.out.printf("骑士周游结束的时间：\n%s\n", end);


		System.out.println("骑士周游之后的棋盘");
		for (int i = 0; i < chessboard.length; i++) {
			for (int j = 0; j < chessboard[i].length; j++) {
				System.out.printf("%5d",chessboard[i][j]);
			}
			System.out.println();
		}
	}
}
