package com.mashibing.day10;

import java.util.*;

/**
 * 题目二：
 * 在实时数据流中找到最常用的K个单词
 * 实现TopK类中的三个方法：
 * TopK(k)，构造方法
 * add(word)，增加一个新单词
 * topk()，得到当前最常使用的K个单词
 * <p>
 * 解题思路：
 * 1.使用加强堆
 * 2.因为大根堆只能进行调整，但是不能维护，因为没有反向索引表的原因，所以不能对数据进行维护和修改
 * 3.所以使用加强堆，堆内有反向索引表
 * <p>
 * <p>
 * LintCode测试链接：
 * https://www.lintcode.com/problem/top-k-frequent-words-ii/
 *
 * @author xcy
 * @date 2022/7/27 - 15:13
 */
public class Code02_TopK {
	public static void main(String[] args) {

	}

	/**
	 * 添加String的过程
	 * 1.首先建立统计词频表
	 * 2.查看是否在堆上，并且堆是否已经满了，如果堆没有满，直接添加在堆上，如果满了，就和小根堆的顶部元素的次数进行比较
	 * 如果比小根堆的顶部元素的次数小，直接淘汰掉，如果比小根堆的顶部元素的次数大，替换掉小根堆的顶部元素
	 * 3.通过反向索引表，找到String的位置，并且在该String的下方调用heapify()进行向下调整，次数小的在上面，次数大的在下面
	 * 4.heapify()的过程：当前String位置所处的节点和子节点进行比较，如果当前父节点次数较大，次数较小的子节点和当前父节点进行交换，
	 * 5.在小根堆中各个节点的次数交换完之后，在反向索引表中，各个String的位置也要进行交换
	 */
	public static class TopK {
		public static class Node {
			public String string;
			public int times;

			public Node(String string, int times) {
				this.string = string;
				this.times = times;
			}
		}

		/**
		 * 词频统计表
		 */
		public static HashMap<String, Node> countMap;
		/**
		 * 反向索引表
		 */
		public static HashMap<Node, Integer> indexMap;
		/**
		 * 堆
		 */
		public static ArrayList<Node> heap;
		/**
		 * 比较器
		 */
		public static Comparator<Node> comparator;
		/**
		 * 堆的大小
		 */
		public static int heapSize;

		/**
		 * 省去堆的排序，直接使用比较器进行比较
		 */
		public static TreeSet<Node> treeSet;

		public TopK(int k) {
			if (k < 0) {
				return;
			}
			heap = new ArrayList<>(k);
			heapSize = 0;
			indexMap = new HashMap<>();
			countMap = new HashMap<>();
			comparator = new NodeComparator();
			treeSet = new TreeSet<>(new TreeSetComparator());
		}

		public static class NodeComparator implements Comparator<Node> {
			@Override
			public int compare(Node o1, Node o2) {
				return (o1.times != o2.times) ? o1.times - o2.times : (o2.string.compareTo(o1.string));
			}
		}

		public static class TreeSetComparator implements Comparator<Node> {

			@Override
			public int compare(Node o1, Node o2) {
				return (o1.times != o2.times) ? o2.times - o1.times : (o1.string.compareTo(o2.string));
			}
		}

		/**
		 * 添加单词
		 *
		 * @param str 单词
		 */
		public void add(String str) {
			//如果单词为空，直接返回
			if (str == null) {
				return;
			}
			//如果堆为空，直接返回
			if (heap.size() == 0) {
				return;
			}
			//当前str对应的节点curNode
			Node curNode = null;
			//对应的节点curNode在堆上的位置
			int preIndex = -1;
			//词频统计表中没有当前str，也就表示是第一次出现
			if (!countMap.containsKey(str)) {
				//根据当前str以及次数，创建对应节点
				curNode = new Node(str, 1);
				//添加到词频统计表中
				countMap.put(str, curNode);
				//反向索引表统计在堆上的位置
				indexMap.put(curNode, -1);
			}
			//词频统计表中有当前str，也就表示不是第一次出现
			else {
				curNode = countMap.get(str);
				// 要在time++之前，先在treeSet中删掉
				// 原因是因为一但times++，curNode在treeSet中的排序就失效了
				// 这种失效会导致整棵treeSet出现问题
				if (treeSet.contains(curNode)) {
					treeSet.remove(curNode);
				}
				//在词频统计表中，当前节点对应的词频数++
				curNode.times++;
				//重新设置反向索引表中当前节点在堆上的位置
				preIndex = indexMap.get(curNode);
			}
			//如果当前str没有在堆上出现过
			if (preIndex == -1) {
				//判断堆是否为空
				//如果堆不为空
				if (heapSize == heap.size()) {
					//如果当前str出现的次数没有大于堆顶元素出现的次数，就略过
					//如果当前str出现的次数大于堆顶元素出现的次数，就进行替换
					if (comparator.compare(heap.get(0), curNode) < 0) {
						//在treeSet中删除堆顶元素
						treeSet.remove(heap.get(0));
						//将当前str对应的节点curNode添加到treeSet中
						treeSet.add(curNode);
						//在反向索引表中将堆顶元素的位置设置为-1，表示在堆上没有该元素
						indexMap.put(heap.get(0), -1);
						//将当前str对应的节点curNode设置为堆顶元素，并且在堆上的位置为0
						indexMap.put(curNode, 0);
						//在堆上设置当前str对应的节点curNode为堆顶元素
						heap.set(0, curNode);
						//从堆顶向下调整
						heapify(0, heapSize);
					}
				}
				//如果堆为空
				else {
					//直接添加
					//在treeSet中添加
					treeSet.add(curNode);
					//在反向索引表中记录当前str对应的节点curNode在堆上的位置是heapSize
					indexMap.put(curNode, heapSize);
					//在堆上设置当前str对应的节点curNode的位置是heapSize
					heap.set(heapSize, curNode);
					//因为是在堆的底部进行添加，所以需要进行向上调整
					heapInsert(heapSize);
					//堆的大小++
					heapSize++;
				}
			}
			//如果当前str在堆上出现过
			else {
				//直接添加
				//在treeSet中直接添加
				treeSet.add(curNode);
				//从当前str对应的节点curNode在堆上的位置开始，进行向下调整
				heapify(preIndex, heapSize);
			}
		}

		/**
		 * @return 返回在堆上出现次数最多的前K个str
		 */
		public static List<String> topk() {
			ArrayList<String> ans = new ArrayList<>();
			for (Node node : treeSet) {
				ans.add(node.string);
			}
			return ans;
		}

		/**
		 * 向上调整
		 *
		 * @param index 当前节点的位置
		 */
		public static void heapInsert(int index) {
			while (index != 0) {
				//父节点的位置
				int parent = (index - 1) / 2;
				//如果当前节点的对应的str出现的次数小于当前节点的父节点对应的str出现的次数，那么就进行交换
				//父节点沉下去，当前子节点浮上来
				if (comparator.compare(heap.get(index), heap.get(parent)) < 0) {
					swap(parent, index);
					//当前的索引来到父节点的索引，继续进行向上调整
					index = parent;
				} else {
					break;
				}
			}
		}

		/**
		 * 根据str出现的次数进行向下调整
		 * <p>
		 * 如果str出现的次数较小，浮上来
		 * 如果str出现的次数较大，沉下去
		 *
		 * @param index    当前str对应的节点curNode在堆上的位置
		 * @param heapSize 当前堆的大小
		 */
		public static void heapify(int index, int heapSize) {
			int left = 2 * index + 1;
			int right = 2 * index + 2;
			int minIndex = 0;
			while (left < heapSize) {
				if (left + 1 < heapSize && comparator.compare(heap.get(left), heap.get(index)) < 0) {
					minIndex = left;
				}
				if (right < heapSize && comparator.compare(heap.get(right), heap.get(minIndex)) < 0) {
					minIndex = right;
				}
				if (minIndex != index) {
					swap(minIndex, index);
				}
				index = minIndex;
				left = 2 * index + 1;
				right = 2 * index + 2;
			}

		}

		/**
		 * 如果向上调整或者向下调整满足str出现的次数，也就是str对应的左右子节点的值 小于父节点的值这个条件，那么就进行交换
		 *
		 * @param index1 当前str对应的节点curNode在堆上出现的位置
		 * @param index2 当前str对应的节点curNode在堆上出现的位置
		 */
		public static void swap(int index1, int index2) {
			indexMap.put(heap.get(index1), index2);
			indexMap.put(heap.get(index2), index1);
			Node temp = heap.get(index1);
			heap.set(index1, heap.get(index2));
			heap.set(index2, temp);
		}
	}
}
