package com.mashibing.day18;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * 给定两个有序数组arr1和arr2，再给定一个整数k，返回来自arr1和arr2的两个数相加和最大的前k个，
 * 两个数必须分别来自两个数组，按照降序输出
 * 时间复杂度为O(k * logk)
 * 输入描述：
 * 第一行三个整数N, K分别表示数组arr1, arr2的大小，以及需要询问的数
 * 接下来一行N个整数，表示arr1内的元素
 * 再接下来一行N个整数，表示arr2内的元素
 * 输出描述：
 * 输出K个整数表示答案
 * <p>
 * 牛客网题目：https://www.nowcoder.com/practice/7201cacf73e7495aa5f88b223bbbf6d1
 *
 * @author xcy
 * @date 2022/8/8 - 14:48
 */
public class Code04_TopKSumCrossTwoArrays {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int N = scanner.nextInt();
		int K = scanner.nextInt();
		int[] arr1 = new int[N];
		int[] arr2 = new int[N];
		for (int i = 0; i < N; i++) {
			arr1[i] = scanner.nextInt();
		}
		for (int i = 0; i < N; i++) {
			arr2[i] = scanner.nextInt();
		}
		int[] topk = topKSum(arr1, arr2, K);
		for (int i = 0; i < K; i++) {
			System.out.println(topk[i] + " ");
		}
		System.out.println();
		scanner.close();
	}

	public static class Node {
		/**
		 * 在arr1中的位置
		 */
		public int index1;
		/**
		 * 在arr2中的位置
		 */
		public int index2;
		/**
		 * arr1[index1] + arr2[index2]的和sum
		 */
		public int sum;

		public Node(int index1, int index2, int sum) {
			this.index1 = index1;
			this.index2 = index2;
			this.sum = sum;
		}
	}

	/**
	 * 大根堆的比较器，根据sum的值按照从大到小进行排序
	 */
	public static class MaxHeapComparator implements Comparator<Node> {
		@Override
		public int compare(Node o1, Node o2) {
			return o2.sum - o1.sum;
		}
	}

	/**
	 * -  0  1  2  3   -> arr2
	 * -0 0  1  2  3
	 * -1 4  5  6  7
	 * -2 8  9  10 11
	 * -3 12 13 14 15
	 * arr1
	 * <p>
	 * 从arr1[3]和arr2[3]开始，也就是二维数组(3, 3)转换为一维数组的索引15开始，两种选择
	 * 往左
	 * 往上
	 * 为了防止14往上，11往左，遇到相同的节点，使用HashSet解决该问题
	 * <p>
	 * 时间复杂度：O(K * logK)
	 * 一共需要收集K个答案，大根堆进入一个数，出来两个数，一共K次，每次log1，总共logK
	 *
	 * @param arr1
	 * @param arr2
	 * @return
	 */
	public static int[] topKSum(int[] arr1, int[] arr2, int topk) {
		if (arr1 == null || arr2 == null || topk < 1) {
			return null;
		}

		int N = arr1.length;
		int M = arr2.length;
		//topk取决于N * M的大小，最多只能是N * M个
		topk = Math.min(topk, N * M);
		int[] res = new int[topk];
		int resIndex = 0;

		int end1 = N - 1;
		int end2 = M - 1;
		//创建大根堆，根据sum的值按照从大到小进行排序
		PriorityQueue<Node> maxHeap = new PriorityQueue<>(new MaxHeapComparator());
		//因为可能会出现，一个节点的上和另一个节点的左是同一个节点，所以需要HashSet，防止遇到同一个节点
		HashSet<Long> set = new HashSet<>();
		maxHeap.add(new Node(end1, end2, arr1[end1] + arr2[end2]));
		set.add(x(end1, end2, M));

		while (resIndex != topk) {
			Node cur = maxHeap.poll();
			//收集答案
			res[resIndex++] = cur.sum;
			end1 = cur.index1;
			end2 = cur.index2;
			//当前节点永远都不可能再次遇到，所以为了节省空间，删除掉当前节点
			set.remove(x(end1, end2, M));

			//end1表示arr1的索引，也就是行数，表示是否能够往上走
			if (end1 - 1 >= 0 && !set.contains(x(end1 - 1, end2, M))) {
				set.add(x(end1 - 1, end2, M));
				maxHeap.add(new Node(end1 - 1, end2, arr1[end1 - 1] + arr2[end2]));
			}

			//end2表示arr2的索引，也就是列数，表示是否能够往左走
			if (end2 - 1 >= 0 && !set.contains(x(end1, end2 - 1, M))) {
				set.add(x(end1, end2 - 1, M));
				maxHeap.add(new Node(end1, end2 - 1, arr1[end1] + arr2[end2 - 1]));
			}
		}

		return res;
	}

	/**
	 * @param i 横坐标
	 * @param j 纵坐标
	 * @param M 列数
	 * @return 返回将二维数组坐标转换成一维数组的索引的结果
	 */
	public static long x(int i, int j, int M) {
		return (long) (i) * (long) (M) + (long) (j);
	}
}
