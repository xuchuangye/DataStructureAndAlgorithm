package com.mashibing.interviewquestions;

import com.mashibing.common.BinaryTreeUtils;
import com.mashibing.node.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * 二叉树面试题
 * 给定一棵二叉树的根节点root，任何节点之间都存在距离，返回整棵二叉树的最大距离
 *
 * 基本思路：
 * 该树节点中最远距离：
 * 情况一、不经过当前节点
 * 1、当前节点所在的树的左子树的最大距离
 * 2、当前节点所在的树的右子树的最大距离
 * 情况二、经过当前节点
 * 3、
 * 当前节点所在的树的左子树与当前节点最远的距离（左子树的高度）
 * +
 * 当前节点所在的树的右子树与当前节点最远的距离（右子树的高度）
 * +
 * 1（当前节点）
 * @author xcy
 * @date 2022/4/30 - 8:27
 */
public class BinaryTreeNodeBetweenMaxDistance {
	public static void main(String[] args) {
		int maxLevel = 4;
		int maxValue = 100;
		int testTimes = 1000000;
		for (int i = 0; i < testTimes; i++) {
			Node head = BinaryTreeUtils.generateRandomBST(maxLevel, maxValue);
			if (maxDistance1(head) != maxDistance(head)) {
				System.out.println("Oops!");
			}
		}
		System.out.println("finish!");
	}

	/**
	 * 当前节点所在的树需要知道的信息封装类
	 * 当前节点所在的树需要知道的信息：
	 * 当前节点所在的树的左子树的最大距离和左子树的高度
	 * 当前节点所在的树的右子树的最大距离和右子树的高度
	 */
	public static class Info {
		/**
		 * 该树的最大距离
		 */
		public int maxDistance;
		/**
		 * 该树的高度
		 */
		public int height;
		public Info(int maxDistance, int height) {
			this.maxDistance = maxDistance;
			this.height = height;
		}
	}

	/**
	 * 获取整棵树的最大距离
	 * @param root 二叉树的根节点
	 * @return 返回整棵树的最大距离
	 */
	public static int maxDistance(Node root) {
		if (root == null) {
			return 0;
		}
		return process(root).maxDistance;
	}

	/**
	 * 核心逻辑
	 * @param node 当前节点
	 * @return 返回整棵树的信息封装类
	 */
	public static Info process(Node node) {
		if (node == null) {
			return new Info(0, 0);
		}

		Info leftInfo = process(node.left);
		Info rightInfo = process(node.right);
		//该树的最大高度：比较左子树的最大高度和右子树的最大高度，最后加上当前节点1
		int height = Math.max(leftInfo.height, rightInfo.height) + 1;
		//该树的高度
		//分为三种情况：
		//1.当前节点所在的树的左子树的距离
		int leftMaxDistance = leftInfo.maxDistance;;
		//2.当前节点所在的树的右子树的距离
		int rightMaxDistance = rightInfo.maxDistance;;
		//3.当前节点所在的树的左子树的高度 + 当前节点所在的树的右子树的高度 + 1(当前节点)
		int totalMaxDistance = leftInfo.height + rightInfo.height + 1;
		int maxDistance = Math.max(Math.max(leftMaxDistance, rightMaxDistance), totalMaxDistance);
		return new Info(maxDistance, height);
	}


	public static int maxDistance1(Node head) {
		if (head == null) {
			return 0;
		}
		ArrayList<Node> arr = getPrelist(head);
		HashMap<Node, Node> parentMap = getParentMap(head);
		int max = 0;
		for (int i = 0; i < arr.size(); i++) {
			for (int j = i; j < arr.size(); j++) {
				max = Math.max(max, distance(parentMap, arr.get(i), arr.get(j)));
			}
		}
		return max;
	}

	public static ArrayList<Node> getPrelist(Node head) {
		ArrayList<Node> arr = new ArrayList<>();
		fillPrelist(head, arr);
		return arr;
	}

	public static void fillPrelist(Node head, ArrayList<Node> arr) {
		if (head == null) {
			return;
		}
		arr.add(head);
		fillPrelist(head.left, arr);
		fillPrelist(head.right, arr);
	}

	public static HashMap<Node, Node> getParentMap(Node head) {
		HashMap<Node, Node> map = new HashMap<>();
		map.put(head, null);
		fillParentMap(head, map);
		return map;
	}

	public static void fillParentMap(Node head, HashMap<Node, Node> parentMap) {
		if (head.left != null) {
			parentMap.put(head.left, head);
			fillParentMap(head.left, parentMap);
		}
		if (head.right != null) {
			parentMap.put(head.right, head);
			fillParentMap(head.right, parentMap);
		}
	}

	public static int distance(HashMap<Node, Node> parentMap, Node o1, Node o2) {
		HashSet<Node> o1Set = new HashSet<>();
		Node cur = o1;
		o1Set.add(cur);
		while (parentMap.get(cur) != null) {
			cur = parentMap.get(cur);
			o1Set.add(cur);
		}
		cur = o2;
		while (!o1Set.contains(cur)) {
			cur = parentMap.get(cur);
		}
		Node lowestAncestor = cur;
		cur = o1;
		int distance1 = 1;
		while (cur != lowestAncestor) {
			cur = parentMap.get(cur);
			distance1++;
		}
		cur = o2;
		int distance2 = 1;
		while (cur != lowestAncestor) {
			cur = parentMap.get(cur);
			distance2++;
		}
		return distance1 + distance2 - 1;
	}
}
