package com.mashibing.test;

import com.mashibing.node.Node;

/**
 * @author xcy
 * @date 2022/4/29 - 15:39
 */
public class IsSearchBinaryTree {
	public static void main(String[] args) {

	}

	/**
	 * 当前节点需要知道的信息封装类：
	 * 当前节点所在的树需要知道左子树是否是一颗二叉搜索树
	 * 当前节点所在的树需要知道右子树是否是一颗二叉搜索树
	 * 当前节点所在的树需要知道左子树的最大值是否比当前节点的值小
	 * 当前节点所在的树需要知道右子树的最小值是否比当前节点的值大
	 */
	public static class Info {
		public boolean isBinarySearchTree;
		public int max;
		;
		public int min;

		public Info(boolean isBinarySearchTree, int max, int min) {
			this.isBinarySearchTree = isBinarySearchTree;
			this.max = max;
			this.min = min;
		}
	}

	public static boolean isBinarySearchTree(Node root) {
		if (root == null) {
			return true;
		}
		return process(root).isBinarySearchTree;
	}

	public static Info process(Node node) {
		if (node == null) {
			return null;
		}
		//获取左子树的信息封装类
		Info leftInfo = process(node.left);
		//获取右子树的信息封装类
		Info rightInfo = process(node.right);
		//最大值
		int max = node.value;
		//最小值
		int min = node.value;

		if (leftInfo != null) {
			max = Math.max(max, leftInfo.max);
			min = Math.min(min, leftInfo.max);
		}
		if (rightInfo != null) {
			max = Math.max(max, rightInfo.max);
			min = Math.min(min, rightInfo.min);
		}
		//假设该树是一颗二叉搜索树
		boolean isBinarySearchTree = true;
		//列举出所有不符合二叉搜索树的条件
		//如果左子树不为空并且左子树不是搜索二叉树
		if (leftInfo != null && !leftInfo.isBinarySearchTree) {
			//那么整棵树就不是搜索二叉树
			isBinarySearchTree = false;
		}
		//如果右子树不为空并且右子树不是搜索二叉树
		if (rightInfo != null && !rightInfo.isBinarySearchTree) {
			//那么整棵树就不是搜索二叉树
			isBinarySearchTree = false;
		}
		//如果左子树不为空，并且左子树的最大值大于当前节点的值
		if (leftInfo != null && leftInfo.max >= node.value) {
			//那么整棵树就不是搜索二叉树
			isBinarySearchTree = false;
		}
		//如果右子树不为空，并且右子树的最小值小于当前节点的值
		if (rightInfo != null && rightInfo.min <= node.value) {
			//那么整棵树就不是搜索二叉树
			isBinarySearchTree = false;
		}
		return new Info(isBinarySearchTree, max, min);
	}
}
