package com.mashibing.interviewquestions;

import com.mashibing.node.TreeNode;

/**
 * 二叉树面试题
 *
 * 题目二：
 * 给定一个二叉树的根节点root 和一个表示目标和的整数targetSum 。
 * 判断该树中是否存在 根节点到叶子节点 的路径，这条路径上所有节点值相加等于目标和targetSum 。
 * 如果存在，返回 true ；否则，返回 false 。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/path-sum
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author xcy
 * @date 2022/4/10 - 16:59
 */
public class BinaryTreeInterviewQuestions008 {
	public static void main(String[] args) {
		TreeNode root = new TreeNode(5);
		root.setLeft(new TreeNode(3));
		root.setRight(new TreeNode(7));
		root.getLeft().setLeft(new TreeNode(1));
		root.getLeft().setRight(new TreeNode(4));
		root.getRight().setLeft(new TreeNode(6));
		root.getRight().setRight(new TreeNode(8));

		boolean hasPathSum = hasPathSum(root, 9);
		System.out.println(hasPathSum);
	}

	public static boolean isSum = false;

	/**
	 * 判断一棵树从根节点到叶子节点的路径和等于指定的路径和sum
	 * @param root 该树的根节点
	 * @param sum 指定的路径总和
	 * @return 满足就返回true，不满足就返回false
	 */
	public static boolean hasPathSum(TreeNode root, int sum) {
		if (root == null) {
			return false;
		}
		isSum = false;
		process(root, 0, sum);
		return isSum;
	}

	/**
	 * 判断一棵树从根节点到各个叶子节点的路径和等于指定的路径和sum
	 * @param root 该树的根节点
	 * @param preSum 之前的路径累加和
	 * @param sum 指定的路径总和
	 */
	public static void process(TreeNode root, int preSum, int sum) {
		//判断节点是否是叶子节点
		if (root.left == null && root.right == null) {
			if (preSum + root.value == sum) {
				isSum = true;
			}
			return;
		}
		//肯定要先累加上当前节点的值
		preSum += root.value;

		//判断节点是否是非叶子节点
		if (root.left != null) {
			process(root.left, preSum, sum);
		}
		if (root.right != null) {
			process(root.right, preSum, sum);
		}
	}
}
