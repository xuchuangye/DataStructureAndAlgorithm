package com.mashibing.Interview;

import com.mashibing.unionset.UnionFindWithIntPositions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 并查集的面试题
 * 给你一个由'1'（陆地）和 '0'（水）组成的的二维网格，请你计算网格中岛屿的数量。
 * 岛屿总是被水包围，并且每座岛屿只能由水平方向和/或竖直方向上相邻的陆地连接形成。
 * 此外，你可以假设该网格的四条边均被水包围。
 * <p>
 * 给定三个参数，int m，int n，int [][]positions
 * 输入：int m = 3，int n = 3， positions = {{0,0}, {0,1}, {1,2}, {2,1}}
 * 输出：1,1,2,3
 * <p>
 * 该由'1'（陆地）和 '0'（水）组成的的二维网格
 * {0, 0, 0}
 * {0, 0, 0}
 * {0, 0, 0}
 * 过程：
 * 输入：positions[0] = {0,0}
 * {1, 0, 0}
 * {0, 0, 0}
 * {0, 0, 0}
 * 岛屿数量：1
 * 输入：positions[0] = {0,1}
 * {1, 1, 0}
 * {0, 0, 0}
 * {0, 0, 0}
 * 岛屿数量：1
 * 输入：positions[0] = {1,2}
 * {1, 1, 0}
 * {0, 0, 1}
 * {0, 0, 0}
 * 岛屿数量：2
 * 输入：positions[0] = {2,1}
 * {1, 1, 0}
 * {0, 0, 1}
 * {0, 1, 0}
 * 岛屿数量：3
 * <p>
 * 要求：
 * 返回每次输入positions中的一个坐标，就返回该次岛屿的数量
 * <p>
 * 时间复杂度：
 * O(M * N) + O(K)
 * O(M * N)表示初始化的时间复杂度
 * K表示positions数组的长度，也就是有K个位置
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/number-of-islands
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author xcy
 * @date 2022/5/3 - 10:24
 */
public class NumberOfIslands_II {
	public static void main(String[] args) {
		int m = 3;
		int n = 3;
		int[][] positions = {
				{0,0},
				{0,1},
				{1,2},
				{2,1}
		};
		List<Integer> list = numIslands2(m, n, positions);
		System.out.println(Arrays.toString(list.toArray()));
	}

	/**
	 * 获取岛屿数量的集合
	 * @param positions 元素位置的二维数组
	 * @return 每当输入positions中的一个坐标时，就获得该次岛屿的数量，返回每次岛屿数量的集合
	 */
	public static List<Integer> numIslands2(int m, int n, int[][] positions) {
		UnionFindWithIntPositions unionFindWithIntPositions = new UnionFindWithIntPositions(m, n);
		ArrayList<Integer> ans = new ArrayList<>();
		//positions = {{0,0}, {0,1}, {1,2}, {2,1}}
		for (int[] position : positions) {
			ans.add(unionFindWithIntPositions.connect(position[0], position[1]));
		}
		return ans;
	}
}
