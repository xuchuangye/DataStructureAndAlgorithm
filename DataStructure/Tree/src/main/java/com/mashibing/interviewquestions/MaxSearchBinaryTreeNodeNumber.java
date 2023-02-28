package com.mashibing.interviewquestions;

import com.mashibing.common.BinaryTreeUtils;
import com.mashibing.node.Node;

/**
 * 二叉树的面试题
 * <p>
 * 给定一棵二叉树的根节点，返回所有子树是搜索二叉树的最大节点个数
 * <p>
 * 基本思路：
 * 情况一：当前节点不是根节点
 * 1、当前节点所在的树的左子树的最大搜索二叉树的节点个数
 * 2、当前节点所在的树的右子树的最大搜索二叉树的节点个数
 * 情况二：当前节点是根节点
 * 1、判断当前节点所在的树的左子树是否是搜索二叉树
 * 2、判断当前节点所在的树的右子树是否是搜索二叉树
 * 3、判断当前节点所在的树的左子树的最大值是否小于当前节点的值
 * 4、判断当前节点所在的树的右子树的最小值是否大于当前节点的值
 * 5、如果满足上述所有条件，证明整棵树都是搜索二叉树
 * 那么当前节点所在的树的左子树的节点个数 + 右子树的节点个数 + 1（当前节点） 就是最终结果
 * <p>
 * 是否是搜索二叉树
 * 整棵树的搜索二叉子树的节点个数大小和树的节点个数大小相等，那么该树就是搜索二叉树
 *
 * 在线测试链接 : https://leetcode.com/problems/largest-bst-subtree
 * @author xcy
 * @date 2022/4/30 - 9:45
 */
public class MaxSearchBinaryTreeNodeNumber {
	public static void main(String[] args) {
		int maxLevel = 5;
		int maxValue = 100;
		int testTimes = 1000000;
		System.out.println("测试开始");
		for (int i = 0; i < testTimes; i++) {
			Node head = BinaryTreeUtils.generateRandomBST(maxLevel, maxValue);
			if (allIsSearchBinarySubTreeMaxNodes(head) != largestBSTSubtree(head)) {
				System.out.println("出错了!");
			}
		}
		System.out.println("测试结束");
	}


	/*public static class Info {
		public boolean isSearchBinaryTree;
		public int maxSearchBinarySubTreeSize;
		public int totalSize;
		public int max;
		public int min;

		public Info(boolean isSearchBinaryTree, int maxSearchBinarySubTreeSize, int totalSize, int max, int min) {
			this.isSearchBinaryTree = isSearchBinaryTree;
			this.maxSearchBinarySubTreeSize = maxSearchBinarySubTreeSize;
			this.totalSize = totalSize;
			this.max = max;
			this.min = min;
		}
	}*/


	/*public static int allIsSearchBinarySubTreeMaxNodes(Node root) {
		if (root == null) {
			return 0;
		}
		return process(root).maxSearchBinarySubTreeSize;
	}

	public static Info process(Node node) {
		//Info中的信息无法确定，所有返回null
		if (node == null) {
			return null;
		}
		Info leftInfo = process(node.left);
		Info rightInfo = process(node.right);
		//该树是否是二叉搜索树，默认是
		boolean isSearchBinaryTree = true;
		if (leftInfo != null && !leftInfo.isSearchBinaryTree) {
			isSearchBinaryTree = false;
		}
		if (rightInfo != null && !rightInfo.isSearchBinaryTree) {
			isSearchBinaryTree = false;
		}
		//该树中最大搜索二叉子树的节点个数
		int maxSearchBinarySubTreeSize = 0;
		if (leftInfo != null && rightInfo != null) {
			maxSearchBinarySubTreeSize = Math.max(leftInfo.maxSearchBinarySubTreeSize, rightInfo.maxSearchBinarySubTreeSize);
		}
		//该树的总节点个数
		int totalSize = 0;
		if (leftInfo != null && rightInfo != null) {
			totalSize = leftInfo.totalSize + rightInfo.totalSize + 1;
		}
		//该树的左子树的最大值和右子树的最小值
		int max = node.value;
		int min = node.value;
		if (leftInfo != null) {
			max = Math.max(max, leftInfo.max);
			min = Math.min(min, leftInfo.min);
		}
		if (rightInfo != null) {
			max = Math.max(max, rightInfo.max);
			min = Math.min(min, rightInfo.min);
		}
		if (leftInfo != null && leftInfo.max >= node.value) {
			isSearchBinaryTree = false;
		}
		if (rightInfo != null && rightInfo.min <= node.value) {
			isSearchBinaryTree = false;
		}
		return  new Info(isSearchBinaryTree, maxSearchBinarySubTreeSize, totalSize, max, min);
	}*/

	/**
	 * 当前节点所在的树需要知道的信息封装类
	 * 当前节点所在的树需要知道的信息：
	 * 当前节点所在的树的所有是搜索二叉子树的最大节点个数
	 * 当前节点所在的树的总节点个数
	 * 当前节点所在的树的左子树的最大值
	 * 当前节点所在的树的右子树的最小值
	 */
	public static class Info {
		public int maxSearchBinarySubTreeSize;
		public int totalSize;
		public int max;
		public int min;

		public Info(int maxSearchBinarySubTreeSize, int totalSize, int max, int min) {
			this.maxSearchBinarySubTreeSize = maxSearchBinarySubTreeSize;
			this.totalSize = totalSize;
			this.max = max;
			this.min = min;
		}
	}

	/**
	 * 获取该树的满足搜索二叉树的子树的最大的节点个数
	 * 1、判断当前节点所在的树的左子树是否是搜索二叉树，获取左子树中满足搜索二叉树的子树的最大的节点个数
	 * 2、判断当前节点所在的树的右子树是否是搜索二叉树，获取右子树中满足搜索二叉树的子树的最大的节点个数
	 * 3、如果当前节点所在的树的左右子树都是搜索二叉树，
	 * 判断左子树中的最大值是否小于当前节点
	 * 判断右子树中的最小值是否大于当前节点
	 * 如果上述情况都满足，那么整棵树就是搜索二叉树，该树的总节点个数就是最终结果
	 * @param root 二叉树的根节点
	 * @return 返回该树中满足搜索二叉树的子树的最大的节点个数
	 */
	public static int allIsSearchBinarySubTreeMaxNodes(Node root) {
		if (root == null) {
			return 0;
		}
		Info info = process(root);
		//如果maxSearchBinarySubTreeSize == totalSize表示该树是一颗搜索二叉树，返回totalSize
		if (info.maxSearchBinarySubTreeSize == info.totalSize) {
			return info.totalSize;
		}
		//如果该树不是搜索二叉树，返回该树中满足搜索二叉树的子树的最大节点个数
		return info.maxSearchBinarySubTreeSize;
	}

	public static Info process(Node node) {
		//Info中的信息无法确定，所有返回null
		if (node == null) {
			return null;
		}
		Info leftInfo = process(node.left);
		Info rightInfo = process(node.right);
		//该树的左子树的最大值和右子树的最小值
		int max = node.value;
		int min = node.value;
		//该树的总节点个数，默认只有根节点自己
		int totalSize = 1;

		//左子树的最大值
		if (leftInfo != null) {
			max = Math.max(max, leftInfo.max);
			min = Math.min(min, leftInfo.min);
			totalSize += leftInfo.totalSize;
		}
		//右子树的最小值
		if (rightInfo != null) {
			max = Math.max(max, rightInfo.max);
			min = Math.min(min, rightInfo.min);
			totalSize += rightInfo.totalSize;
		}
		//该树中最大搜索二叉子树的节点个数
		int maxSearchBinarySubTreeSize = 0;
		int leftSearchBinarySubTreeSize = -1;
		int rightSearchBinarySubTreeSize = -1;

		//判断左子树是否为空，如果不为空则左子树是否是搜索二叉树
		boolean leftSBT = leftInfo == null || leftInfo.maxSearchBinarySubTreeSize == leftInfo.totalSize;
		//判断右子树是否为空，如果不为空则右子树是否是搜索二叉树
		boolean rightSBT = rightInfo == null || rightInfo.maxSearchBinarySubTreeSize == rightInfo.totalSize;
		//如果左子树不为空
		if (leftInfo != null) {
			//左子树满足搜索二叉树的最大节点个数
			leftSearchBinarySubTreeSize = leftInfo.maxSearchBinarySubTreeSize;
		}
		//如果右子树不为空
		if (rightInfo != null) {
			//右子树满足搜索二叉树的最大节点个数
			rightSearchBinarySubTreeSize = rightInfo.maxSearchBinarySubTreeSize;
		}
		//如果左子树和右子树都为搜索二叉树，为null也是搜索二叉树
		if (leftSBT && rightSBT) {
			//判断左子树是否为空，如果不为空，则左子树中的最大值是否小于当前节点
			boolean leftMaxLessNodeValue = leftInfo == null || (leftInfo.max < node.value);
			//判断右子树是否为空，如果不为空，则右子树中的最小 值是否大于当前节点
			boolean rightMinGreatNodeValue = rightInfo == null || (rightInfo.min > node.value);
			//整棵树都是二叉树
			if (leftMaxLessNodeValue && rightMinGreatNodeValue) {
				//判断左子树是否为空，如果为空，0个节点，如果不为空，leftInfo.totalSize
				int leftSize = leftInfo == null ? 0 : leftInfo.totalSize;
				//判断左子树是否为空，如果为空，0个节点，如果不为空，leftInfo.totalSize
				int rightSize = rightInfo == null ? 0 : rightInfo.totalSize;
				maxSearchBinarySubTreeSize = leftSize + rightSize + 1;
			}
		}

		//
		return new Info(Math.max(Math.max(leftSearchBinarySubTreeSize, rightSearchBinarySubTreeSize), maxSearchBinarySubTreeSize), totalSize, max, min);
	}


	public static int largestBSTSubtree(Node head) {
		if (head == null) {
			return 0;
		}
		return process2(head).maxSearchBinarySubTreeSize;
	}

	public static Info process2(Node x) {
		if (x == null) {
			return null;
		}
		Info leftInfo = process(x.left);
		Info rightInfo = process(x.right);
		int max = x.value;
		int min = x.value;
		int allSize = 1;
		if (leftInfo != null) {
			max = Math.max(leftInfo.max, max);
			min = Math.min(leftInfo.min, min);
			allSize += leftInfo.totalSize;
		}
		if (rightInfo != null) {
			max = Math.max(rightInfo.max, max);
			min = Math.min(rightInfo.min, min);
			allSize += rightInfo.totalSize;
		}
		int p1 = -1;
		if (leftInfo != null) {
			p1 = leftInfo.maxSearchBinarySubTreeSize;
		}
		int p2 = -1;
		if (rightInfo != null) {
			p2 = rightInfo.maxSearchBinarySubTreeSize;
		}
		int p3 = -1;
		boolean leftBST = leftInfo == null ? true : (leftInfo.maxSearchBinarySubTreeSize == leftInfo.totalSize);
		boolean rightBST = rightInfo == null ? true : (rightInfo.maxSearchBinarySubTreeSize == rightInfo.totalSize);
		if (leftBST && rightBST) {
			boolean leftMaxLessX = leftInfo == null ? true : (leftInfo.max < x.value);
			boolean rightMinMoreX = rightInfo == null ? true : (x.value < rightInfo.min);
			if (leftMaxLessX && rightMinMoreX) {
				int leftSize = leftInfo == null ? 0 : leftInfo.totalSize;
				int rightSize = rightInfo == null ? 0 : rightInfo.totalSize;
				p3 = leftSize + rightSize + 1;
			}
		}
		int size = Math.max(p1, Math.max(p2, p3));
		return new Info(size, allSize, max, min);
	}
}
