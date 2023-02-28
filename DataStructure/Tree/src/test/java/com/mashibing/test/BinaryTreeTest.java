package com.mashibing.test;

import com.mashibing.node.Node;
import com.mashibing.node.TreeNode;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author xcy
 * @date 2022/4/10 - 16:25
 */
public class BinaryTreeTest {
	public static void main(String[] args) {
		TreeNode root = new TreeNode(1);
		root.setLeft(new TreeNode(2));
		root.setRight(new TreeNode(3));
		root.getLeft().setLeft(new TreeNode(4));
		root.getLeft().setRight(new TreeNode(5));
		root.getRight().setLeft(new TreeNode(6));
		root.getRight().setRight(new TreeNode(7));

		preOrder(root);
		System.out.println("--------------");
		infixOrder(root);
		System.out.println("--------------");
		suffixOrder(root);

		System.out.println("--------------");
		List<List<Integer>> lists = levelOrder(root);
		for (List<Integer> list : lists) {
			for (Integer integer : list) {
				System.out.print(integer + " ");
			}
			System.out.println();
		}

	}

	public static void preOrder(TreeNode root) {
		System.out.println(root);
		if (root.getLeft() != null) {
			root.getLeft().preOrder();
		}

		if (root.getRight() != null) {
			root.getRight().preOrder();
		}
	}

	public static void infixOrder(TreeNode root) {
		if (root.getLeft() != null) {
			root.getLeft().infixOrder();
		}
		System.out.println(root);

		if (root.getRight() != null) {
			root.getRight().infixOrder();
		}
	}

	public static void suffixOrder(TreeNode root) {
		if (root.getLeft() != null) {
			root.getLeft().suffixOrder();
		}
		System.out.println(root);

		if (root.getRight() != null) {
			root.getRight().suffixOrder();
		}
	}

	public static List<List<Integer>> levelOrder(TreeNode root) {
		List<List<Integer>> ans = new LinkedList<>();
		if (root == null) {
			return ans;
		}

		Queue<TreeNode> queue = new LinkedList<>();
		queue.add(root);

		while (!queue.isEmpty()) {
			int size = queue.size();
			LinkedList<Integer> curAns = new LinkedList<>();
			for (int i = 0; i < size; i++) {
				TreeNode cur = queue.poll();
				if (cur == null) {
					break;
				}
				curAns.add(cur.value);
				if (cur.left != null) {
					queue.add(cur.left);
				}
				if (cur.right != null) {
					queue.add(cur.right);
				}
			}
			ans.add(0, curAns);
		}
		return ans;
	}
}
