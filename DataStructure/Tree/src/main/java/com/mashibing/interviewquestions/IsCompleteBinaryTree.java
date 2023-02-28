package com.mashibing.interviewquestions;

import com.mashibing.common.BinaryTreeUtils;
import com.mashibing.node.Node;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 二叉树的面试题
 * 判断一棵树 是否是 完全二叉树
 * <p>
 * 完全二叉树：
 * 1、当前节点所在的树的左子树和右子树的节点都是满的
 * 2、即使不满，也是最后一层不满的
 * 3、即使是不满，也是从左往右依次变满的
 * <p>
 * 完全二叉树的按层遍历的基本思路：
 * 1、情况一：
 * 该节点是叶子节点，左右子节点都为空，那么整棵树是完全二叉树
 * 2、情况二：
 * 该节点只有右子节点，没有左子节点，那么整棵树不是完全二叉树
 * 该节点左右子节点都不为空，那么整棵树是完全二叉树
 * <p>
 * 完全二叉树递归的基本思路：
 * 1、情况一：
 * 该节点所在的树的左子树和右子树都是满二叉树，并且树的高度一样
 * 2.情况二：
 * 该节点所在的树的左子树是完全二叉树，右子树是满二叉树，并且左子树的高度大于右子树，高度不超过1
 * 3.情况三：
 * 该节点所在的树的左子树和右子树都是满二叉树，并且左子树的高度大于右子树，高度不超过1
 * 4、情况四：
 * 该节点所在的树的左子树是满二叉树，右子树是完全二叉树，并且树的高度一样
 *
 * @author xcy
 * @date 2022/4/29 - 9:51
 */
public class IsCompleteBinaryTree {
	public static void main(String[] args) {
		int maxLevel = 5;
		int maxValue = 100;
		int testTimes = 1000000;
		System.out.println("测试开始");
		for (int i = 0; i < testTimes; i++) {
			Node head = BinaryTreeUtils.generateRandomBST(maxLevel, maxValue);
			if (isCompleteBinaryTreeWithLevelOrder(head) != isCompleteBinaryTreeWithRecursion(head)) {
				System.out.println("测试失败!");
			}
		}
		System.out.println("测试结束!");
	}

	/**
	 * 判断该树是否是完全二叉树 --> 按层遍历的方式
	 *
	 * @param root 二叉树的根节点
	 * @return 如果该树是完全二叉树，返回true，否则返回false
	 */
	public static boolean isCompleteBinaryTreeWithLevelOrder(Node root) {
		if (root == null) {
			return true;
		}
		return process(root);
	}

	/**
	 * 判断该树是否是完全二叉树的核心逻辑
	 *
	 * @param root 二叉树的根节点
	 * @return 如果该树是完全二叉树，返回true，否则返回false
	 */
	public static boolean process(Node root) {
		Queue<Node> queue = new LinkedList<>();
		queue.add(root);
		//是否遇到过左右子节点不双全的节点，默认没有遇到过
		boolean isFull = false;
		while (!queue.isEmpty()) {
			//弹出当前节点
			Node node = queue.poll();
			//左子节点
			Node left = node.left;
			//右子节点
			Node right = node.right;

			//如果遇到过左右子节点不双全的节点，并且该节点不是叶子节点
			//也就证明当前节点不是叶子节点并且左右子节点不满，表示该树不是完全二叉树，直接返回false
			if ((isFull && (left != null || right != null))
					||
					//有右子节点，但是没有左子节点，表示该树不是完全二叉树，直接返回false
					(left == null && right != null)) {
				return false;
			}
			//如果左子节点不为空，添加到队列
			if (left != null) {
				queue.add(left);
			}
			//如果右子节点不为空，添加到队列
			if (right != null) {
				queue.add(right);
			}
			//如果左右子节点不双全，也就是不满，开启isFull
			if (left == null || right == null) {
				isFull = true;
			}
		}
		return true;
	}

	/**
	 * 该节点所在的树需要知道的信息封装类
	 * 该节点所在的树需要知道的信息：
	 * 该节点所在的树的左子树是否是满二叉树或者是完全二叉树，以及树的高度
	 * 该节点所在的树的右子树是否是满二叉树或者是完全二叉树，以及树的高度
	 */
	public static class Info {
		public boolean isFull;
		public boolean isComplete;
		public int height;
		public Info(boolean isFull, boolean isComplete, int height) {
			this.isFull = isFull;
			this.isComplete = isComplete;
			this.height = height;
		}
	}

	/**
	 * 判断该树是否是完全二叉树 --> 按递归遍历的方式
	 * 满足完全二叉树的四种情况：
	 * 情况一：该节点所在的树的左子树是满二叉树，右子树是满二叉树，并且树的高度一样
	 * 情况二：该节点所在的树的左子树是完全二叉树，右子树是满二叉树，并且左子树的高度等于右子树的高度+1
	 * 情况三：该节点所在的树的左子树是满二叉树，右子树是满二叉树，并且左子树的搞多等于右子树的高度
	 * 情况四：该节点所在的树的左子树是满二叉树，右子树是完全二叉树，并且树的高度一样
	 * @param root 二叉树的根节点
	 * @return 如果是完全二叉树，返回true，否则返回false
	 */
	public static boolean isCompleteBinaryTreeWithRecursion(Node root) {
		if (root == null) {
			return true;
		}
		return process1(root).isComplete;
	}

	/**
	 * 核心逻辑
	 * @param node 该节点
	 * @return 该节点所在的树需要的信息封装类
	 */
	public static Info process1(Node node) {
		if (node == null) {
			return new Info(true, true, 0);
		}
		Info leftInfo = process1(node.left);
		Info rightInfo = process1(node.right);
		//该树的高度：
		//该节点所在的树的左子树的高度比较右子树的高度，最终再加上该节点所在的高度
		int height = Math.max(leftInfo.height, rightInfo.height) + 1;
		//判断该树是否是满二叉树
		//该节点所在的树的左子树是满二叉树
		//该节点所在的树的右子树是满二叉树
		//该节点所在的树的左子树的高度和右子树的高度一致
		boolean isFull = leftInfo.isFull && rightInfo.isFull && leftInfo.height == rightInfo.height;
		boolean isComplete = false;
		//如果该树是满二叉树
		//那么就满足该树是完全二叉树的情况一：
		if (isFull) {
			isComplete = true;
		}
		//满足该树是完全二叉树的情况二：
		//该节点所在的树的左子树是完全二叉树
		//该节点所在的树的右子树是满二叉树
		//该节点所在的树的左子树的高度等于右子树的高度+1
		else if (leftInfo.isComplete && rightInfo.isFull && leftInfo.height == rightInfo.height + 1) {
			isComplete = true;
		}

		//满足该树是完全二叉树的情况三：
		//该节点所在的树的左子树是满二叉树
		//该节点所在的树的右子树是满二叉树
		//该节点所在的树的左子树的高度等于右子树的高度+1
		else if (leftInfo.isFull && rightInfo.isFull && leftInfo.height == rightInfo.height + 1) {
			isComplete = true;
		}

		//满足该树是完全二叉树的情况四：
		//该节点所在的树的左子树是满二叉树
		//该节点所在的树的右子树是完全二叉树
		//该节点所在的树的左子树的高度和右子树的高度一样
		else if (leftInfo.isFull && rightInfo.isComplete && leftInfo.height == rightInfo.height) {
			isComplete = true;
		}
		return new Info(isFull, isComplete, height);
	}
}
