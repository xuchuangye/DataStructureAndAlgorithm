package com.mashibing.interviewquestions;

import com.mashibing.common.BinaryTreeUtils;
import com.mashibing.node.Node;
import com.mashibing.node.TreeNode;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 二叉树面试题
 * 题目二：
 * 判断一棵树 是否是 平衡二叉树
 *
 * 平衡二叉树：
 * 平衡二叉树中每一棵子树的左子树高度和右子树高度差值不超过1
 *
 * 基本思路：
 * 1、当前节点所在的树的左子树是平衡二叉树
 * 2、当前节点所在的树的右子树是平衡二叉树
 * 3、当前节点所在的树的左子树高度和右子树高度差值不超过1
 * 4、当前节点所在的树需要知道的信息：左子树是否是平衡二叉树和树的高度以及右子树是否是平衡二叉树和树的高度
 *
 * @author xcy
 * @date 2022/4/10 - 16:59
 */
public class IsBalancedBinaryTree {
	public static void main(String[] args) {
		int maxLevel = 5;
		int maxValue = 100;
		int testTimes = 1000000;
		for (int i = 0; i < testTimes; i++) {
			Node head = BinaryTreeUtils.generateRandomBST(maxLevel, maxValue);
			if (isBalanced1(head) != isBalanced(head)) {
				System.out.println("Oops!");
			}
		}
		System.out.println("finish!");
	}

	/**
	 * 当前节点需要知道的信息：
	 * 左子树是否是平衡二叉树和左子树树的深度
	 * 右子树是否是平衡二叉树和右子树树的深度
	 * 所以创建
	 * 当前节点需要知道的信息封装类
	 */
	public static class Info {
		public boolean isBalanced;
		public int height;
		public Info(boolean isBalanced, int height) {
			this.isBalanced = isBalanced;
			this.height = height;
		}
	}

	/**
	 * 给定一棵二叉树的根节点，判断该树是否是平衡二叉树
	 * @param root 二叉树的根节点
	 * @return 如果该树是平衡二叉树，返回true，否则返回false
	 */
	public static boolean isBalanced(Node root) {
		if (root == null) {
			return true;
		}
		return process(root).isBalanced;
	}

	/**
	 * 判断当前节点是否是平衡二叉树
	 * @param node 当前节点
	 * @return 返回当前节点所在的树的信息封装类
	 */
	public static Info process(Node node) {
		//当前节点如果为空，返回信息封装类
		if (node == null) {
			//是平衡二叉树
			//树的深度为0
			return new Info(true, 0);
		}
		//如果当前节点不为空，可以获取左子树的信息封装类以及右子树的信息封装类
		Info left = process(node.left);
		Info right = process(node.right);

		//如果左子树和右子树都是平衡二叉树，那么就为true，只要有一颗子树不是平衡二叉树，那么就为false
		//如果左子树和右子树树的深度不超过1，那么就为true，否则就为false
		boolean isBalanced = left.isBalanced && right.isBalanced && Math.abs(left.height - right.height) < 2;
		//当前节点所在的子树的树的深度
		//因为没有计算当前节点所在的深度，所以树的深度要加1
		int height = Math.max(left.height, right.height) + 1;
		//返回当前节点的信息封装类
		return new Info(isBalanced, height);
	}


	public static boolean isBalanced1(Node head) {
		boolean[] ans = new boolean[1];
		ans[0] = true;
		process1(head, ans);
		return ans[0];
	}

	public static int process1(Node head, boolean[] ans) {
		if (!ans[0] || head == null) {
			return -1;
		}
		int leftHeight = process1(head.left, ans);
		int rightHeight = process1(head.right, ans);
		if (Math.abs(leftHeight - rightHeight) > 1) {
			ans[0] = false;
		}
		return Math.max(leftHeight, rightHeight) + 1;
	}
}
