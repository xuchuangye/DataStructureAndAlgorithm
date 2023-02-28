package com.mashibing.day17;

/**
 * 给定一个每一行有序、每一列也有序，整体可能无序的二维数组，再给定一个正数k，返回二维数组中第k小的数
 * <p>
 * Leetcode题目：https://leetcode.cn/problems/kth-smallest-element-in-a-sorted-matrix/
 *
 * @author xcy
 * @date 2022/8/7 - 10:04
 */
public class Code02_KthSmallestElementInSortedMatrix {
	public static void main(String[] args) {
		int[][] matrix = {
				{1, 30, 51},
				{7, 42, 52},
				{9, 51, 70},
		};
		Info info = noMoreNum(matrix, 55);
		System.out.println(info.near);
		System.out.println(info.num);
	}

	/**
	 * 时间复杂度：O((N + M) * log(max - min))
	 * N表示matrix的行数
	 * M表示matrix的列数
	 * N + M表示查找时不会进行回退
	 * max表示matrix的最大值
	 * min表示matrix的最小值
	 * log(max - min)表示在min ~ max的范围内进行二分查找
	 *
	 * @param matrix 二维数组
	 * @param K      第K小的数
	 * @return 返回在matrix二维数组中第K小的数
	 */
	public static int kthSmallest(int[][] matrix, int K) {
		if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0 || K < 1) {
			return -1;
		}
		int N = matrix.length;
		int M = matrix[0].length;
		int left = matrix[0][0];
		int right = matrix[N - 1][M - 1];
		int ans = 0;
		while (left <= right) {
			int mid = left + ((right - left) >> 1);
			//返回信息封装类的对象，包含两个属性
			//near：表示小于等于并且最接近mid的值
			//num：表示小于等于mid的个数
			Info info = noMoreNum(matrix, mid);
			//如果小于等于mid的个数小于K，那么表示左部分不够，去右边找
			if (info.num < K) {
				//所以left来到mid + 1的位置
				left = mid + 1;
			}
			//如果小于等于mid的个数大于等于K，那么表示左部分够了
			else {
				//ans记录此时最接近mid的值
				ans = info.near;
				//所以right来到mid - 1的位置
				right = mid - 1;
			}
		}
		return ans;
	}

	public static class Info {
		/**
		 * 小于等于num，并且最接近num的数值
		 */
		public int near;
		/**
		 * 小于等于num的个数
		 */
		public int num;

		public Info(int near, int num) {
			this.near = near;
			this.num = num;
		}
	}

	/**
	 * @param matrix 二维数组
	 * @param mid    目标值
	 * @return 返回信息封装类，包含最接近并且小于等于mid的数，以及小于等于mid的个数
	 */
	public static Info noMoreNum(int[][] matrix, int mid) {
		int row = 0;
		int col = matrix[0].length - 1;
		int near = 0;
		int num = 0;
		while (row < matrix.length && col >= 0) {
			//如果当前位置的值小于等于mid
			if (matrix[row][col] <= mid) {
				//记录此时小于等于mid，并且最接近mid的值
				//如果之后还有，就继续更新
				near = mid;
				//累加当前位置以及左边所有的个数，因为行有序，列有序
				num += col + 1;
				//继续下一行
				row++;
			}
			//否则当前位置的值大于mid
			else {
				//继续前一列
				col--;
			}
		}
		return new Info(near, num);
	}
}
