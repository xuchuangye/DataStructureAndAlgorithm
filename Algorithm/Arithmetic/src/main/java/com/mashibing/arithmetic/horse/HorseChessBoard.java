package com.mashibing.arithmetic.horse;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 马踏棋盘问题 -> 骑士周游问题
 *
 * @author xcy
 * @date 2022/4/4 - 15:23
 */
public class HorseChessBoard {
	/**
	 * 棋盘的列
	 */
	public final int X;
	/**
	 * 棋盘的行
	 */
	public final int Y;

	/**
	 * 标记棋盘的各个位置上是否被访问过，默认都没有被访问过
	 */
	public boolean[] isVisited;

	/**
	 * 标记棋盘当中所有的位置都被访问过，也就是马儿是否完成骑士周游问题，默认没有完成
	 */
	public boolean finished;

	//构建马踏棋盘
	public HorseChessBoard(int x, int y) {
		this.X = x;
		this.Y = y;
		this.isVisited = new boolean[X * Y];
	}

	/**
	 * 骑士周游问题算法
	 * @param chessboard 棋盘
	 * @param row 马儿当前位置的行，默认从0开始
	 * @param col 马儿当前位置的列，默认从0开始
	 * @param step 步数，默认是从1开始的，因为当前位置已经访问过
	 */
	public void traversalChessboard(int[][] chessboard, int row, int col, int step) {
		//将马儿当前位置的上数值 和 马踏步的步数保持一致
		chessboard[row][col] = step;
		//标记马儿当前的位置已经被访问过
		isVisited[row * X + col] = true;
		//创建马儿当前的位置Point对象
		Point point = new Point(col, row);
		//获取马儿当前位置的下一个位置的集合
		List<Point> points = nextPositionList(point);
		//对马儿当前位置的下一个位置的集合中的每一个Point的下一个位置的集合元素个数进行非递归排序
		sortPointNextList(points);
		//判断集合 是否为空，如果不为空
		while (!points.isEmpty()) {
			//取出第一个位置
			Point p = points.remove(0);
			//判断当前位置是否被访问过，如果没有被访问过
			if (!isVisited[p.y * X + p.x]) {
				//那么就继续递归，访问下一个位置，
				//将当前位置的列作为下一个位置的行，并将当前位置的行作为下一个位置的列，
				//并且记录下一个位置上的数值和下一个位置的马踏步的步数
				traversalChessboard(chessboard, p.y, p.x, step + 1);
			}
		}

		//判断马儿是否完成了任务，使用step和棋盘位置的总数进行比较
		//如果没有达到数量，棋盘就置0，如果达到数量，就标记finished == true
		//step < X * Y是有两种情况
		//1、棋盘到目前为止，仍然没有走完
		//2、棋盘已经走完，但是处于回溯过程
		if (step < X * Y && !finished) {
			//棋盘就置0
			chessboard[row][col] = 0;
			//标记马儿当前的位置为未访问过的
			isVisited[row * X + col] = false;
		}else {
			//标记马儿完成走过整个棋盘的任务
			finished = true;
		}
	}

	/**
	 * 判断马儿当前的位置(curPoint)能够走哪些位置，并将下一个位置添加到List集合中，最多有8个位置
	 *
	 * @param curPoint 马儿当前的位置
	 * @return  当前马儿能够走的下一个位置的List集合
	 */
	public List<Point> nextPositionList(Point curPoint) {
		List<Point> list = new ArrayList<Point>();
		Point point = new Point();

		//马儿能够走5这个位置
		if ((point.x = curPoint.x - 2) >= 0 && (point.y = curPoint.y - 1) >= 0) {
			list.add(new Point(point));
		}
		//马儿能够走6这个位置
		if ((point.x = curPoint.x - 1) >= 0 && (point.y = curPoint.y - 2) >= 0) {
			list.add(new Point(point));
		}
		//马儿能够走7这个位置
		if ((point.x = curPoint.x + 1) < X && (point.y = curPoint.y - 2) >= 0) {
			list.add(new Point(point));
		}
		//马儿能够走0这个位置
		if ((point.x = curPoint.x + 2) < X && (point.y = curPoint.y - 1) >= 0) {
			list.add(new Point(point));
		}
		//马儿能够走1这个位置
		if ((point.x = curPoint.x + 2) < X && (point.y = curPoint.y + 1) < Y) {
			list.add(new Point(point));
		}
		//马儿能够走2这个位置
		if ((point.x = curPoint.x + 1) < X && (point.y = curPoint.y + 2) < Y) {
			list.add(new Point(point));
		}
		//马儿能够走3这个位置
		if ((point.x = curPoint.x - 1) >= 0 && (point.y = curPoint.y + 2) < Y) {
			list.add(new Point(point));
		}
		//马儿能够走4这个位置
		if ((point.x = curPoint.x - 2) >= 0 && (point.y = curPoint.y + 1) < Y) {
			list.add(new Point(point));
		}
		return list;
	}

	/**
	 * 使用贪心算法对马儿当前位置的下一个位置的List集合中的每一个Point的下一个位置的集合个数进行非递减排序，减少回溯的可能
	 * @param points 马儿当前位置的下一个位置的List集合
	 */
	public void sortPointNextList(List<Point> points) {
		points.sort(new Comparator<Point>() {
			@Override
			public int compare(Point point1, Point point2) {
				int point1NextListSize = nextPositionList(point1).size();
				int point2NextListSize = nextPositionList(point2).size();
				return Integer.compare(point1NextListSize, point2NextListSize);
			}
		});
	}
}
