package com.mashibing.interviewquestions;

import com.mashibing.common.BinaryTreeUtils;
import com.mashibing.node.Node;

/**
 * 二叉树的面试题
 * <p>
 * 给定一棵二叉树的根节点root，判断该树是否是满二叉树
 * <p>
 * 满二叉树：
 * 假设该树的高度是H，那么该树节点总个数一定是2^H - 1
 * <p>
 * 基本思路：
 *
 * @author xcy
 * @date 2022/4/30 - 9:06
 */
public class IsFullBinaryTree {
	public static void main(String[] args) {
		int maxLevel = 5;
		int maxValue = 100;
		int testTimes = 1000000;
		System.out.println("测试开始");
		for (int i = 0; i < testTimes; i++) {
			Node head = BinaryTreeUtils.generateRandomBST(maxLevel, maxValue);
			if (isFullBinaryTree(head) != isFull2(head)) {
				System.out.println("出错了!");
			}
		}
		System.out.println("测试结束");
	}

	/**
	 * 当前节点需要知道的信息封装类
	 * 当前节点需要知道的信息：
	 * 当前节点所在的树的左子树的高度和左子树的节点个数
	 * 当前节点所在的树的右子树的高度和右子树的节点个数
	 */
	public static class Info {
		public int height;
		public int nodeCount;

		public Info(int height, int nodeCount) {
			this.height = height;
			this.nodeCount = nodeCount;
		}
	}
	/**
	 * 判断该树是否是满二叉树
	 * 判断依据：该树的节点总个数 == 2 的 该树高度 次方 - 1
	 * @param root 该树的根节点
	 * @return 如果该树是满二叉树，返回true，否则返回false
	 */
	public static boolean isFullBinaryTree(Node root) {
		if (root == null) {
			return true;
		}
		Info info = process(root);
		//该树的节点个数 == 2 的 该树高度 次方 - 1
		//return info.nodeCount == Math.pow(2, info.height) - 1;
		return (1 << info.height) - 1 == info.nodeCount;
	}

	/**
	 * 核心逻辑
	 * @param node 当前节点
	 * @return 返回当前节点的信息封装类
	 */
	public static Info process(Node node) {
		if (node == null) {
			return new Info(0, 0);
		}
		Info leftInfo = process(node.left);
		Info rightInfo = process(node.right);
		//该树的高度：比较当前节点所在的树的左子树的高度和右子树的高度之后，再加1（当前节点）
		int height = Math.max(leftInfo.height, rightInfo.height) + 1;
		//该树的节点个数：当前节点所在的树的左子树的节点个数 + 右子树的节点个数 + 1（当前节点）
		int nodeCount = leftInfo.nodeCount + rightInfo.nodeCount + 1;
		return new Info(height, nodeCount);
	}


	// 第二种方法
	// 收集子树是否是满二叉树
	// 收集子树的高度
	// 左树满 && 右树满 && 左右树高度一样 -> 整棵树是满的
	public static boolean isFull2(Node head) {
		if (head == null) {
			return true;
		}
		return process2(head).isFull;
	}

	public static class Info2 {
		public boolean isFull;
		public int height;

		public Info2(boolean f, int h) {
			isFull = f;
			height = h;
		}
	}

	public static Info2 process2(Node h) {
		if (h == null) {
			return new Info2(true, 0);
		}
		Info2 leftInfo = process2(h.left);
		Info2 rightInfo = process2(h.right);
		boolean isFull = leftInfo.isFull && rightInfo.isFull && leftInfo.height == rightInfo.height;
		int height = Math.max(leftInfo.height, rightInfo.height) + 1;
		return new Info2(isFull, height);
	}
}
