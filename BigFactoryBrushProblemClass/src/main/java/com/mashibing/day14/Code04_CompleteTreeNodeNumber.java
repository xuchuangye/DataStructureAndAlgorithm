package com.mashibing.day14;

/**
 * 给定一个棵完全二叉树，返回这棵树的节点个数，要求时间复杂度小于O(树的节点数)
 * <p>
 * 完全二叉树的特性：
 * 1.如果当前节点的右子树的最左节点到达最后一层，那么当前节点的左子树节点一定满的
 * -             o
 * -        /         \
 * -       o           o
 * -    /   \        /   \
 * -   o     o      o     o
 * - /  \  /  \   /  \  /  \
 * -o   o o    o o   o o    x
 * 2.如果当前节点的右子树的最左节点没有到达最后一层，也就是左子树的层数 = 右子树的层数 + 1，那么当前节点的
 * 右子树节点一定是满的
 * -            o
 * -        /       \
 * -       o         o
 * -    /   \      /   \
 * -   o     o    o     o
 * - /  \  /  \
 * -o   o o    x
 * <p>
 * 根据完全二叉树的特性：
 * 分为两种情况：
 * 1.如果当前节点的右子树的最左节点在最后一层，那么当前节点的左子树节点一定是满的
 * 2.如果当前节点的右子树的最左节点不在最后一层，那么当前节点的右子树节点一定是满的
 *
 * @author xcy
 * @date 2022/8/2 - 17:00
 */
public class Code04_CompleteTreeNodeNumber {
	public static void main(String[] args) {
		int height = 10;
		int level = 5;
		int count1 = (int) (Math.pow(2, height - level));
		int count2 = 1 << (height - level);
		System.out.println(count1);
		System.out.println(count2);
	}

	public static class Node {
		public int value;
		public Node left;
		public Node right;

		public Node(int value) {
			this.value = value;
		}
	}

	/**
	 * 时间复杂度：O((logN)的2次方)
	 *
	 * @param head 完全二叉树的头节点
	 * @return 返回整棵二叉树的节点个数
	 */
	public static int nodeNumber(Node head) {
		if (head == null) {
			return 0;
		}
		//计算整棵二叉树的层数
		int height = mostLeftNodeLevel(head, 1);
		//二叉树的头节点，以及所在的层数第1层，
		return binaryTree(head, 1, height);
	}

	/**
	 * @param node   当前节点
	 * @param level  当前节点所在的层数
	 * @param height 总层数，也可以表示最后一层
	 * @return 返回以当前节点为头节点的整棵树的节点个数
	 */
	public static int binaryTree(Node node, int level, int height) {
		//当前节点所在的层数 == 总层数(最后一层)，表示只有当前节点自己，所以节点个数返回1
		if (level == height) {
			return 1;
		}
		//当前节点所在的层数是level，当前节点的左子节点和右子节点所在的层数是level + 1
		//表示当前节点的右子树的最左节点是否在最后一层
		//如果在最后一层，那么当前节点的左子树节点一定是满的
		if (mostLeftNodeLevel(node.right, level + 1) == height) {
			//当前节点的左子树的层数 == 总层数height - 当前节点所在的层数
			//return (int) (Math.pow(2, height - level))+ binaryTree(node.right, level + 1, height);
			return (1 << (height - level)) + binaryTree(node.right, level + 1, height);
		}
		//如果不在最后一层，那么当前节点的右子树节点一定是满的
		else {
			//当前节点的右子树的层数 == 总层数height - 当前节点所在的层数 - 1
			//return (int) (Math.pow(2, height - level - 1)) + binaryTree(node.left, level + 1, height);
			return (1 << (height - level - 1)) + binaryTree(node.left, level + 1, height);
		}
	}

	/**
	 * @param node  当前节点
	 * @param level 当前节点的右子树的最左节点所在的层数
	 * @return 返回以当前节点为头节点的整棵树的右子树的最左节点所在的层数
	 */
	public static int mostLeftNodeLevel(Node node, int level) {
		while (node != null) {
			level++;
			node = node.left;
		}
		//因为退出上述while循环时，node == null，level多++了一次，所以需要level - 1
		return level - 1;
	}
}
