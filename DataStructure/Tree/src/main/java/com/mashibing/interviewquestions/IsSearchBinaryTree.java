package com.mashibing.interviewquestions;

import com.mashibing.common.BinaryTreeUtils;
import com.mashibing.node.Node;

import java.util.ArrayList;

/**
 * 二叉树面试题
 * 判断一棵树 是否是 二叉搜索树/二叉排序树
 * <p>
 * 二叉搜索树：
 * 当前节点所在的树的左子树的最大值比当前节点的值小，右子树的最小值比当前节点的值大
 * <p>
 * 基本思路：
 * 1、当前节点所在的树的左子树是一颗二叉搜索树
 * 2、当前节点所在的树的右子树是一颗二叉搜索树
 * 3、当前节点所在的树的左子树的最大值比当前节点的值小
 * 4、当前节点所在的树的右子树的最小值比当前节点的值大
 *
 * @author xcy
 * @date 2022/4/10 - 16:59
 */
public class IsSearchBinaryTree {
	public static void main(String[] args) {
		int maxLevel = 4;
		int maxValue = 100;
		int testTimes = 1000000;
		for (int i = 0; i < testTimes; i++) {
			Node head = BinaryTreeUtils.generateRandomBST(maxLevel, maxValue);
			if (isSearchBinaryTree(head) != isSearchBinaryTree1(head)) {
				System.out.println("Oops!");
			}
		}
		System.out.println("finish!");
	}


	/**
	 * 当前节点需要知道的信息封装类：
	 * 当前节点所在的树需要知道左子树是否是一颗二叉搜索树
	 * 当前节点所在的树需要知道右子树是否是一颗二叉搜索树
	 * 当前节点所在的树需要知道左子树的最大值是否比当前节点的值小
	 * 当前节点所在的树需要知道右子树的最小值是否比当前节点的值大
	 */
	public static class Info {
		public boolean isSearchBinaryTree;
		public Integer max;
		public Integer min;

		public Info(boolean isBinarySearchTree, Integer max, Integer min) {
			this.isSearchBinaryTree = isBinarySearchTree;
			this.max = max;
			this.min = min;
		}
	}


	/**
	 * 判断一棵树是否是二叉搜索树
	 *
	 * @param root 该树的根节点
	 * @return
	 */
	public static boolean isSearchBinaryTree(Node root) {
		if (root == null) {
			return true;
		}
		return process(root).isSearchBinaryTree;
	}

	/**
	 * 判断一棵树是否是二叉搜索树/二叉排序树
	 *
	 * @param root 该树的根节点
	 * @return 返回一棵树是否是平衡二叉树和该树的深度的信息类Info
	 */
	public static Info process(Node root) {
		//
		if (root == null) {
			return null;
		}
		//默认最大值和最小值都是根节点的值
		int max = root.value;
		int min = root.value;

		//递归左子树
		Info leftInfo = process(root.left);
		//递归右子树
		Info rightInfo = process(root.right);

		//如果左子树不为空
		if (leftInfo != null) {
			//取出左子树中的最大值和最小值
			max = Math.max(leftInfo.max, max);
			min = Math.min(leftInfo.min, min);
		}
		//如果右子树不为空
		if (rightInfo != null) {
			//取出右子树的最大值和最小值
			max = Math.max(rightInfo.max, max);
			min = Math.min(rightInfo.min, min);
		}
		//假设该树是一颗二叉搜索树
		boolean isSearchBinaryTree = true;
		//列举出所有不符合二叉搜索树的条件
		//如果左子树不为空并且左子树不是搜索二叉树
		if (leftInfo != null && !leftInfo.isSearchBinaryTree) {
			//那么整棵树就不是搜索二叉树
			isSearchBinaryTree = false;
		}
		//如果右子树不为空并且右子树不是搜索二叉树
		if (rightInfo != null && !rightInfo.isSearchBinaryTree) {
			//那么整棵树就不是搜索二叉树
			isSearchBinaryTree = false;
		}
		//如果左子树不为空，并且左子树的最大值大于当前节点的值
		if (leftInfo != null && leftInfo.max >= root.value) {
			//那么整棵树就不是搜索二叉树
			isSearchBinaryTree = false;
		}
		//如果右子树不为空，并且右子树的最小值小于当前节点的值
		if (rightInfo != null && rightInfo.min <= root.value) {
			//那么整棵树就不是搜索二叉树
			isSearchBinaryTree = false;
		}

		return new Info(isSearchBinaryTree, max, min);
	}

	public static boolean isSearchBinaryTree1(Node head) {
		if (head == null) {
			return true;
		}
		ArrayList<Node> arr = new ArrayList<>();
		in(head, arr);
		for (int i = 1; i < arr.size(); i++) {
			if (arr.get(i).value <= arr.get(i - 1).value) {
				return false;
			}
		}
		return true;
	}

	public static void in(Node head, ArrayList<Node> arr) {
		if (head == null) {
			return;
		}
		in(head.left, arr);
		arr.add(head);
		in(head.right, arr);
	}
}
