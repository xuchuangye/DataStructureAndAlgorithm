package com.mashibing.day04;

import java.util.*;

/**
 * 题目八：
 * 大楼轮廓线问题
 *
 * 解题思路：
 * 1.给定三个数据：
 * [1, 7, 4]表示大楼的轮廓从1开始到7结束，高度是4
 * [2, 6, 5]表示大楼的轮廓从2开始到6结束，高度是5
 * [0, 8, 4]表示大楼的轮廓从0开始到8结束，高度是4
 * 2.每一个数据都创建成对象，
 * 数据[1, 7, 4]创建对象：[1, +, 4],[7, -, 4]
 * 数据[2, 6, 5]创建对象：[2, +, 5],[6, -, 5]
 * 数据[0, 8, 4]创建对象：[0, +, 4],[8, -, 4]
 * 按照一维进行排序，也就是按照每个大楼轮廓的起点进行从小到大的排序
 * [0, +, 4],[1, +, 4],[2, +, 5],[6, -, 5],[7, -, 4],[8, -, 4]
 * 3.创建有序表TreeMap
 * [0, +, 4]表示从0开始高度从0到4，在有序表中添加数据 -> 4, 1，表示高度4的添加记录为1次
 * [1, +, 4表示从1开始高度保持4不变，在有序表中<4, 1>这条数据的value + 1 -> <4, 2>， 表示高度4的添加记录为2次
 * [2, +, 5]表示从2开始高度从4到5，在有序表中添加数据 -> <5, 1>，表示高度5的添加记录为1次
 * [6, -, 5]表示从6开始高度从5到4，在有序表中删除数据 -> <5, 0>，表示高度5的添加记录为0次，当记录为0时，直接删除数据，
 * 防止干扰判断的逻辑
 * [7, -, 4]表示从7开始高度保持4不变，在有序表中<4, 2>这条数据的value - 1 -> <4, 1>， 表示高度4的添加记录为1次
 * [8, -, 4]表示从8开始高度从4到0，在有序表中删除数据 -> <4, 0>，表示高度4的添加记录为0次，当记录为0时，直接删除数据，
 * 防止干扰判断的逻辑
 * 整个高度的变化过程： 0 -> 4 -> 4 -> 5 -> 5 -> 4 -> 4 -> 0
 * 整个高度的变换位置：      0    1    2    6    7    8
 * 4.描述轮廓线的产生，进而想到最大高度的变化，进而想到使用有序表的统计次数来迅速取得每一步的最大高度是什么
 * <p>
 * Leetcode测试链接：
 * https://leetcode.com/problems/the-skyline-problem/
 *
 * @author xcy
 * @date 2022/7/15 - 11:39
 */
public class Code08_TheSkylineProblem {
	public static void main(String[] args) {

	}

	/**
	 * 封装对象
	 */
	public static class Node {
		/**
		 * 当前坐标
		 */
		public int x;
		/**
		 * 是否是高度加的操作，也就是高度上升或者高度下降
		 */
		public boolean isAdd;
		/**
		 * 当前坐标的高度
		 */
		public int h;

		public Node(int x, boolean isAdd, int h) {
			this.x = x;
			this.isAdd = isAdd;
			this.h = h;
		}
	}

	/**
	 * 比较器，根据坐标进行从小到大的排序
	 */
	public static class MyComparator implements Comparator<Node> {
		@Override
		public int compare(Node o1, Node o2) {
			return o1.x - o2.x;
		}
	}

	/**
	 *
	 * @param buildings 建筑物的坐标,横坐标和纵坐标
	 * @return
	 */
	public static List<List<Integer>> getSkyline(int[][] buildings) {
		if (buildings == null || buildings.length == 0 || buildings[0].length == 0) {
			return null;
		}
		//根据建筑物的数量，创建两倍的对象
		Node[] nodes = new Node[buildings.length * 2];
		for (int i = 0; i < buildings.length; i++) {
			//buildings[i][0]表示建筑物轮廓的起始位置，起始位置肯定是高度上升，所以isAdd == true
			nodes[i * 2] = new Node(buildings[i][0], true, buildings[i][2]);
			//buildings[i][1]表示建筑物轮廓的结束位置，结束位置肯定是高度下降，所以isAdd == false
			nodes[i * 2 + 1] = new Node(buildings[i][1], false, buildings[i][2]);
		}
		//按照建筑物的起始坐标进行从小到大的排序
		Arrays.sort(nodes, new MyComparator());
		//高度的词频统计表
		//key : 高度
		//value : 词频统计次数
		TreeMap<Integer, Integer> heightWordFrequencyCountMap = new TreeMap<>();
		//坐标的最大高度统计表
		//key : 当前坐标
		//value : 当前坐标的最大高度
		TreeMap<Integer, Integer> indexMaxHeightMap = new TreeMap<>();

		for (int i = 0; i < nodes.length; i++) {
			//判断是否是加高度的操作，也就是高度上升的操作
			//如果是高度上升的操作
			if (nodes[i].isAdd) {
				//如果是，那么再判断该高度是否统计过
				//如果没有统计过，创建词频统计
				if (!heightWordFrequencyCountMap.containsKey(nodes[i].h)) {
					heightWordFrequencyCountMap.put(nodes[i].h, 1);
				}else {
					heightWordFrequencyCountMap.put(nodes[i].h, heightWordFrequencyCountMap.get(nodes[i].h) + 1);
				}
			}
			//判断是否是减高度的操作，也就是高度下降的操作
			//如果是高度下降的操作
			else {
				//如果该高度的词频统计次数 == 1，那么直接移除该高度，防止影响后序的高度判断
				if (heightWordFrequencyCountMap.get(nodes[i].h) == 1) {
					heightWordFrequencyCountMap.remove(nodes[i].h);
				}
				//如果该高度的词频统计次数 != 1，该高度的词频统计次数 - 1即可
				else {
					heightWordFrequencyCountMap.put(nodes[i].h, heightWordFrequencyCountMap.get(nodes[i].h) - 1);
				}
			}

			//建立当前坐标的最大高度的词频统计
			//如果此时的高度词频统计表为空，表示两种情况
			//情况1，当前坐标的本身高度为0
			//情况2：当前坐标此时已经进行过高度下降操作，该高度的词频统计被移除了
			if (heightWordFrequencyCountMap.isEmpty()) {
				indexMaxHeightMap.put(nodes[i].x, 0);
			}
			//如果此时的高度词频统计表不为空，表示当前坐标有高度，以最后一次的高度为准
			else {
				indexMaxHeightMap.put(nodes[i].x, heightWordFrequencyCountMap.lastKey());
			}
		}
		//轮廓线的产生
		List<List<Integer>> ans = new ArrayList<>();
		for (Map.Entry<Integer, Integer> entry : indexMaxHeightMap.entrySet()) {
			//当前坐标
			Integer curIndex = entry.getKey();
			//当前坐标的最大高度
			Integer curMaxHeight = entry.getValue();
			if (ans.isEmpty() || !ans.get(ans.size() - 1).get(1).equals(curMaxHeight)) {
				ans.add(new ArrayList<>(Arrays.asList(curIndex, curMaxHeight)));
			}
		}
		return ans;
	}
}
