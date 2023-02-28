package com.mashibing.day19;

import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

/**
 * 谷歌面试题：
 * 你有k个非递减排列的整数列表。找到一个最小区间，使得k个列表中的每个列表至少有一个数包含在其中
 * 我们定义如果 b-a < d-c 或者在 b-a == d-c 时 a < c，则区间 [a,b] 比 [c,d] 小。
 * Leetcode题目：https://leetcode.com/problems/smallest-range-covering-elements-from-k-lists/
 *
 * @author xcy
 * @date 2022/8/10 - 16:31
 */
public class Code04_SmallestRangeCoveringElementsFromKLists {
	public static void main(String[] args) {

	}

	public static class Node {
		/**
		 * 节点的值
		 */
		public int value;
		/**
		 * 所在第几组数组
		 */
		public int arrId;
		/**
		 * 在数组中的第几个索引
		 */
		public int index;

		public Node(int value, int arrId, int index) {
			this.value = value;
			this.arrId = arrId;
			this.index = index;
		}
	}

	/**
	 * 按照value值从小到大进行排序，如果value值相等，按照arrId从大到小进行排序
	 */
	public static class TreeSetComparator implements Comparator<Node> {

		@Override
		public int compare(Node o1, Node o2) {
			return o1.value != o2.value ? o1.value - o2.value : o1.arrId - o2.arrId;
		}
	}

	public static int[] smallestRange(List<List<Integer>> nums) {
		/*int N = nums.size();
		//创建有序表
		TreeSet<Node> treeSet = new TreeSet<>(new TreeSetComparator());
		//有序表将每一组数组的第0个元素添加进来
		for (int i = 0; i < N; i++) {
			//nums.get(i).get(0)表示每一组数组的第0个元素
			//i表示第i组数组
			//0表示第组数组的第0个元素
			treeSet.add(new Node(nums.get(i).get(0), i, 0));
		}
		//有序表顶部，默认表示最小值
		int up = 0;
		//有序表底部，默认表示最大值
		int down = 0;
		//表示是否更新最大值与最小值
		boolean set = false;

		while (treeSet.size() == N) {
			//有序表中的顶部就是最小值
			Node min = treeSet.first();
			//有序表中的底部就是最大值
			Node max = treeSet.last();
			//如果当前最大值与最小值的差值 < 已记录的最大值与最小值的差值
			//更新最大值与最小值
			if (!set || (max.value - min.value < down - up)) {
				//表示确定更新最大值与最小值
				set = true;
				up = min.value;
				down = max.value;
			}
			//继续弹出有序表中的最小值
			min = treeSet.pollFirst();
			//记录第几组数组
			int arrId = min.arrId;
			//记录第几组 数组的第几个元素
			int index = min.index + 1;
			//只要不超出给定多个集合的长度，就继续
			if (index != nums.get(arrId).size()) {
				treeSet.add(new Node(nums.get(arrId).get(index), arrId, index));
			}
		}
		return new int[]{up, down};*/

		int N = nums.size();
		TreeSet<Node> treeSet = new TreeSet<>(new TreeSetComparator());

		for (int i = 0; i < nums.size(); i++) {
			treeSet.add(new Node(nums.get(i).get(0), i, 0));
		}
		int up = 0;
		int down = 0;
		boolean set = false;
		while (treeSet.size() == N) {
			Node min = treeSet.first();
			Node max = treeSet.last();

			if (!set || (max.value - min.value < down - up)) {
				set = true;
				up = min.value;
				down = max.value;
			}

			min = treeSet.pollFirst();
			int arrId = min.arrId;
			int index = min.index + 1;
			if (index != nums.get(arrId).size()) {
				treeSet.add(new Node(nums.get(arrId).get(index), arrId, index));
			}
		}
		return new int[]{up, down};
	}
}
