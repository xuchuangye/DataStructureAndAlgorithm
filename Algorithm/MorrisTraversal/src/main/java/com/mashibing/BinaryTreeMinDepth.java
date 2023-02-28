package com.mashibing;

import com.mashibing.pojo.TreeNode;

/**
 * 给定一棵二叉树的头节点head
 * 求以head为头的树中，最小深度是多少
 * <p>
 * 分析思路：
 * 1.cur指向哪个节点，解决当前节点在第几层level的问题
 * 2.判断当前节点是否是叶子节点，如果是叶子节点，更新minDepth最小深度，
 * 3.
 * 1)如果x节点的层数level是7，y节点是x的左子节点，y节点的层数level一定是7 + 1 = 8
 * 2)如果x节点的层数level是7，y节点是x的右子节点，y节点的层数level不一定是7 + 1 = 8
 * a)如果x节点的右子节点是y节点，y节点的左子树的最右子节点的右指针没有指向x节点，y节点的层数level一定是7 + 1 =8
 * b)如果x节点的右子节点是y节点，y节点的左子树的最右子节点的右指针已经指向x节点，
 *   y节点的层数level一定是x节点的level - y节点的左子树的右边界的总层数
 * 4.如何判断当前节点是叶子节点
 * 1)如果当前节点的右指针指向的是第二次经过的节点，将当前节点的右指针指向null之后，判断当前节点是否是叶子节点
 * 5.最后单独判断整棵树的右边界的最右子节点是否是叶子节点
 * 如果是叶子节点，和当前最小深度进行比较
 * 如果不是叶子节点，和 当前最小深度不进行比较
 * <p>
 * LeetCode测试链接：
 * https://leetcode.cn/problems/minimum-depth-of-binary-tree/
 *
 * @author xcy
 * @date 2022/5/23 - 16:18
 */
public class BinaryTreeMinDepth {
	public static void main(String[] args) {
		TreeNode root = new TreeNode(4);
		root.left = new TreeNode(2);
		root.right = new TreeNode(6);
		root.left.left = new TreeNode(1);
		root.left.right = new TreeNode(3);
		root.right.left = new TreeNode(5);
		root.right.right = new TreeNode(7);
		int depth = binaryTreeMinDepthWithRecursion(root);
		System.out.println("二叉树的最小深度：" + depth);
		int depth2 = binaryTreeMinDepthWithMorrisTraversal(root);
		System.out.println("二叉树的最小深度：" + depth2);
	}

	/**
	 * 使用递归的方式
	 *
	 * @param root 二叉树的根节点
	 * @return 返回二叉树的最小深度
	 */
	public static int binaryTreeMinDepthWithRecursion(TreeNode root) {
		if (root == null) {
			return 0;
		}
		//至少根节点为1个深度
		return coreLogic(root);
	}

	/**
	 * 核心逻辑
	 *
	 * @param treeNode 当前节点
	 * @return 返回当前节点的左子树和右子树的最小深度
	 */
	public static int coreLogic(TreeNode treeNode) {
		//node节点是叶子节点
		if (treeNode.left == null && treeNode.right == null) {
			return 1;
		}
		//左子树的深度
		int leftDepth = Integer.MAX_VALUE;
		if (treeNode.left != null) {
			leftDepth = coreLogic(treeNode.left);
		}
		//右子树的深度
		int rightDepth = Integer.MAX_VALUE;
		if (treeNode.right != null) {
			rightDepth = coreLogic(treeNode.right);
		}
		//左子树的最小深度和右子树的最小深度 + 1就是整棵树的最小深度
		return Math.min(leftDepth, rightDepth) + 1;
	}

	/**
	 * 使用Morris遍历的方式
	 *
	 * @param root 二叉树的根节点
	 * @return 返回二叉树的最小深度
	 */
	public static int binaryTreeMinDepthWithMorrisTraversal(TreeNode root) {
		if (root == null) {
			return 0;
		}
		//时间复杂度：O(1)
		TreeNode cur = root;
		//当前节点的左子树的最右子节点
		TreeNode mostRight = null;
		//二叉树的最小深度
		int minDepth = Integer.MAX_VALUE;
		//当前节点所在的层数
		int curLevel = 0;
		while (cur != null) {
			//当前节点的左子树的最右子节点来到当前节点的左子节点
			mostRight = cur.left;
			//如果当前节点的 左子节点不为空
			if (mostRight != null) {
				//创建当前节点到左子树最右节点的距离的变量，并且当前节点的左子节点算作1层深度
				int curNodeToMostRightNodeDistance = 1;
				//当前节点的左子树的右子节点不为空并且右指针没有指向当前节点
				//说明还没有到达最右子节点
				while (mostRight.right != null && mostRight.right != cur) {
					//当前节点的左子树的右子节点不为空，并且还能继续往右，就累加深度
					curNodeToMostRightNodeDistance++;
					//继续当前节点的左子树的右子节点的下一个右子节点
					mostRight = mostRight.right;
				}
				//当前节点的左子树的右子节点为空
				if (mostRight.right == null) {
					curLevel++;
					//当前节点的左子树的最右子节点的右指针指向当前节点
					mostRight.right = cur;
					//当前节点来到当前节点的左子节点
					cur = cur.left;
					continue;
				}
				//当前节点的左子树的右子节点不为空
				else {
					//1.当前节点的左子树的左子节点为空
					if (mostRight.left == null) {
						minDepth = Math.min(curLevel, minDepth);
					}
					//2.当前节点的左子树的左子节点不为空
					//因为当前节点的右子节点的左子树的最右子节点指向当前节点
					//举例：
					// x
					//  \
					//   y
					//  /
					// /
					// \
					//  \
					//   x
					//当前x节点的右子节点y的左子树的最右子节点指向当前x节点
					//所以当前y节点的层数 = x节点的深度 - (y节点到x节点的深度)
					curLevel = curLevel - curNodeToMostRightNodeDistance;
					mostRight.right = null;
				}
			} else {
				curLevel++;
			}
			//如果当前节点的左子树为空，直接去右子树
			cur = cur.right;
		}
		//整棵树的右边界深度，根节点算作1层深度
		int finalRightBoundary = 1;
		//cur重新来到root节点
		cur = root;
		while (cur.right != null) {
			//只要当前节点的右子节点不为空，就继续累加1层深度
			finalRightBoundary++;
			//继续当前节点的下一个右子节点
			cur = cur.right;
		}
		//如果来到整棵树右边界的叶子节点
		//上述while循环遍历完之后cur.right一定等于null
		if (cur.left == null && cur.right == null) {
			//比较整棵树的右边界的深度  以及  左子树和右子树的最小深度，取最小深度
			minDepth = Math.min(finalRightBoundary, minDepth);
		}
		return minDepth;
	}
}
