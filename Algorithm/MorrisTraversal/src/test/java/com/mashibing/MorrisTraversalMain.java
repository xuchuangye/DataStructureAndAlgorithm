package com.mashibing;


import com.mashibing.pojo.TreeNode;

/**
 * @author xcy
 * @date 2022/5/25 - 9:04
 */
public class MorrisTraversalMain {
	public static void main(String[] args) {
		TreeNode<String> root = new TreeNode<>("a");
		root.left = new TreeNode<>("b");
		root.right = new TreeNode<>("c");
		root.left.left = new TreeNode<>("d");
		root.left.right = new TreeNode<>("e");
		root.right.left = new TreeNode<>("f");
		root.right.right = new TreeNode<>("g");
		System.out.println("先序遍历");
		morrisTraversalPreOrder(root);
		System.out.println("中序遍历");
		morrisTraversalInfixOrder(root);
		System.out.println("后序遍历");
		morrisTraversalPostOrder(root);
	}

	/**
	 * Morris遍历
	 *
	 * @param root
	 */
	public static void morrisTraversal(TreeNode<String> root) {
		if (root == null) {
			return;
		}
		TreeNode<String> cur = root;
		TreeNode<String> mostRight = null;

		while (cur != null) {
			mostRight = cur.left;
			if (mostRight != null) {
				while (mostRight.right != null && mostRight.right != cur) {
					mostRight = mostRight.right;
				}
				if (mostRight.right == null) {
					mostRight.right = cur;
					cur = cur.left;
					continue;
				} else {
					mostRight.right = null;
				}
			}
			cur = cur.right;
		}
	}


	/**
	 * Morris遍历 -> 先序遍历
	 *
	 * @param root
	 */
	public static void morrisTraversalPreOrder(TreeNode<String> root) {
		if (root == null) {
			return;
		}
		TreeNode<String> cur = root;
		TreeNode<String> mostRight = null;
		while (cur != null) {
			mostRight = cur.left;
			if (mostRight != null) {
				while (mostRight.right != null && mostRight.right != cur) {
					mostRight = mostRight.right;
				}
				if (mostRight.right == null) {
					//先序遍历
					System.out.print(cur.value + " ");
					mostRight.right = cur;
					cur = cur.left;
					continue;
				} else {
					mostRight.right = null;
				}
			} else {
				//先序遍历
				System.out.print(cur.value + " ");
			}
			cur = cur.right;
		}
		System.out.println();
	}

	/**
	 * Morris遍历 -> 中序遍历
	 *
	 * @param root
	 */
	public static void morrisTraversalInfixOrder(TreeNode<String> root) {
		if (root == null) {
			return;
		}
		TreeNode<String> cur = root;
		TreeNode<String> mostRight = null;
		while (cur != null) {
			mostRight = cur.left;
			if (mostRight != null) {
				while (mostRight.right != null && mostRight.right != cur) {
					mostRight = mostRight.right;
				}
				if (mostRight.right == null) {
					mostRight.right = cur;
					cur = cur.left;
					continue;
				} else {
					mostRight.right = null;
				}
			}
			//中序遍历
			System.out.print(cur.value + " ");
			cur = cur.right;
		}
		System.out.println();
	}

	/**
	 * Morris遍历 -> 后序遍历
	 *
	 * @param root
	 */
	public static void morrisTraversalPostOrder(TreeNode<String> root) {
		if (root == null) {
			return;
		}
		TreeNode<String> cur = root;
		TreeNode<String> mostRight = null;
		while (cur != null) {
			mostRight = cur.left;
			if (mostRight != null) {
				while (mostRight.right != null && mostRight.right != cur) {
					mostRight = mostRight.right;
				}
				if (mostRight.right == null) {
					mostRight.right = cur;
					cur = cur.left;
					continue;
				} else {
					mostRight.right = null;
					//后序遍历
					printRightBoundary(cur.left);
				}
			}
			cur = cur.right;
		}
		cur = root;
		//后序遍历
		printRightBoundary(cur);
		System.out.println();
	}

	/**
	 * 整棵树的右边界相当于一个链表，对链表反转
	 *
	 * @param cur
	 * @return 返回反转之后的链表头节点
	 */
	public static TreeNode<String> reversedList(TreeNode<String> cur) {
		TreeNode<String> pre = null;
		TreeNode<String> next = null;
		while (cur != null) {
			next = cur.right;
			cur.right = pre;
			pre = cur;
			cur = next;
		}
		return pre;
	}

	/**
	 * 逆序打印
	 *
	 * @param node
	 */
	public static void printRightBoundary(TreeNode<String> node) {
		TreeNode<String> tail = reversedList(node);
		TreeNode<String> cur = tail;
		while (cur != null) {
			System.out.print(cur.value + " ");
			cur = cur.right;
		}
		reversedList(tail);
	}
}
