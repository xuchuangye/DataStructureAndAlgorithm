package com.mashibing.test;

import com.mashibing.node.Node;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author xcy
 * @date 2022/4/29 - 9:51
 */
public class IsCompleteBinaryTree {
	public static void main(String[] args) {

	}

	public static boolean isCompleteBinaryTree(Node root) {
		if (root == null) {
			return true;
		}
		boolean isCBT = true;
		Queue<Node> queue = new LinkedList<Node>();
		queue.add(root);
		while (!queue.isEmpty()) {
			Node cur = queue.poll();
			//情况一：该节点是叶子节点，左右子节点都为空，那么整棵树是完全二叉树
			if (cur.getRight() == null && cur.getLeft() == null) {
				break;
			}
			//情况二：该节点只有右子节点，没有左子节点，那么整棵树不是完全二叉树
			//该节点左右子节点都不为空，那么整棵树是完全二叉树
			if (cur.getRight() != null) {
				queue.add(cur.getRight());
			}
			if (cur.getLeft() != null) {
				queue.add(cur.getLeft());
			}else {
				isCBT = false;
				break;
			}
		}
		return isCBT;
	}
}
