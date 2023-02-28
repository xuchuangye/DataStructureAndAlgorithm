package com.mashibing;


import com.mashibing.pojo.TreeNode;

/**
 * Morris遍历
 *
 * @author xcy
 * @date 2022/5/23 - 11:57
 */
public class MorrisTraversal {
	public static void main(String[] args) {
		TreeNode<Integer> root = new TreeNode<Integer>(4);
		root.left = new TreeNode<Integer>(2);
		root.right = new TreeNode<Integer>(6);
		root.left.left = new TreeNode<Integer>(1);
		root.left.right = new TreeNode<Integer>(3);
		root.right.left = new TreeNode<Integer>(5);
		root.right.right = new TreeNode<Integer>(7);
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
	public static void morrisTraversal(TreeNode<Integer> root) {
		if (root == null) {
			System.out.println("二叉树的根节点为空，无法进行Morris遍历！");
			return;
		}
		//当前节点
		TreeNode<Integer> cur = root;
		//当前节点的左子树的最右子节点
		TreeNode<Integer> mostRight = null;
		//判断当前节点不为空
		while (cur != null) {
			//先来到当前节点的左子节点
			mostRight = cur.left;
			//判断当前节点的左子节点不为空
			if (mostRight != null) {
				//如果当前节点的左子树不为空
				//判断当前节点的左子树的右子节点是否为空，并且当前节点的左子树的右子节点的右指针没有指向当前节点自己
				while (mostRight.right != null && mostRight.right != cur) {
					//当前节点的左子节点来到当前节点的左子节点的右子节点
					mostRight = mostRight.right;
					//直到两种情况时，退出while循环
				}
				//1.当前节点的左子树的右子节点为空时，退出while循环
				//如果当前节点的左子树的右子节点为空
				if (mostRight.right == null) {
					//当前节点的左子树的右子节点指向当前节点
					mostRight.right = cur;
					//当前节点来到当前节点的左子树
					cur = cur.left;
					//跳过本次循环，进行下一次循环
					continue;
				}
				//2.当前节点的左子树的右子节点的右指针指向了当前节点
				//如果当前节点的左子树的右子节点的右指针已经指向了当前节点
				else {
					//设置当前节点的左子树的右子节点的右指针为null
					mostRight.right = null;
				}
			}
			//如果当前节点的左子树为空，直接来到当前节点的右子节点
			cur = cur.right;
		}
	}

	/**
	 * Morris遍历 --> 先序遍历
	 *
	 * @param root
	 */
	public static void morrisTraversalPreOrder(TreeNode<Integer> root) {
		if (root == null) {
			System.out.println("二叉树的根节点为空，无法进行Morris遍历！");
			return;
		}
		//当前节点
		TreeNode<Integer> cur = root;
		//当前节点的左子树的最右子节点
		TreeNode<Integer> mostRight = null;
		//判断当前节点不为空
		while (cur != null) {
			//先来到当前节点的左子节点
			mostRight = cur.left;
			//判断当前节点的左子节点不为空
			if (mostRight != null) {
				//如果当前节点的左子树不为空
				//判断当前节点的左子树的右子节点是否为空，并且当前节点的左子树的右子节点的右指针没有指向当前节点自己
				while (mostRight.right != null && mostRight.right != cur) {
					//当前节点的左子节点来到当前节点的左子节点的右子节点
					mostRight = mostRight.right;
					//直到两种情况时，退出while循环
				}
				//1.当前节点的左子树的右子节点为空时，退出while循环
				//如果当前节点的左子树的右子节点为空，有两种情况：
				//1)说明当前节点的左子树的右子节点是叶子节点，不会经过该节点两次，直接打印
				//2)说明当前节点的左子树的右子节点为空，但是当前节点的左子树的右子节点的右指针还没有指向当前节点时
				//也就是第一次经过当前节点时，直接打印
				if (mostRight.right == null) {
					System.out.print(cur.value + " ");
					//当前节点的左子树的右子节点指向当前节点
					mostRight.right = cur;
					//当前节点来到当前节点的左子树
					cur = cur.left;
					//跳过本次循环，进行下一次循环
					continue;
				}
				//2.当前节点的左子树的右子节点的右指针指向了当前节点
				//如果当前节点的左子树的右子节点的右指针已经指向了当前节点
				else {
					//设置当前节点的左子树的右子节点的右指针为null
					mostRight.right = null;
				}
			}
			//如果当前节点的左子节点为空，说明不会经过当前节点两次，直接打印
			else {
				System.out.print(cur.value + " ");
			}
			//如果当前节点的左子树为空，直接来到当前节点的右子节点
			cur = cur.right;
		}
		System.out.println();
	}

	/**
	 * Morris遍历 --> 中序遍历
	 *
	 * @param root
	 */
	public static void morrisTraversalInfixOrder(TreeNode<Integer> root) {
		if (root == null) {
			System.out.println("二叉树的根节点为空，无法进行Morris遍历！");
			return;
		}
		//当前节点
		TreeNode<Integer> cur = root;
		//当前节点的左子树的最右子节点
		TreeNode<Integer> mostRight = null;
		//判断当前节点不为空
		while (cur != null) {
			//先来到当前节点的左子节点
			mostRight = cur.left;
			//判断当前节点的左子节点不为空
			if (mostRight != null) {
				//如果当前节点的左子树不为空
				//判断当前节点的左子树的右子节点是否为空，并且当前节点的左子树的右子节点的右指针没有指向当前节点自己
				while (mostRight.right != null && mostRight.right != cur) {
					//当前节点的左子节点来到当前节点的左子节点的右子节点
					mostRight = mostRight.right;
					//直到两种情况时，退出while循环
				}
				//1.当前节点的左子树的右子节点为空时，退出while循环
				//如果当前节点的左子树的右子节点为空
				if (mostRight.right == null) {
					//当前节点的左子树的右子节点指向当前节点
					mostRight.right = cur;
					//当前节点来到当前节点的左子树
					cur = cur.left;
					//跳过本次循环，进行下一次循环
					continue;
				}
				//2.当前节点的左子树的右子节点的右指针指向了当前节点
				//如果当前节点的左子树的右子节点的右指针已经指向了当前节点
				//说明是第二次经过当前节点
				else {
					System.out.print(cur.value + " ");
					//设置当前节点的左子树的右子节点的右指针为null
					mostRight.right = null;
				}
			}
			//当前节点的左子节点为空，说明不会经过当前节点两次，也就不会回到当前节点两次，直接打印
			else {
				System.out.print(cur.value + " ");
			}
			//如果当前节点的左子树为空，直接来到当前节点的右子节点
			cur = cur.right;
		}
		System.out.println();
	}

	/**
	 * Morris遍历 --> 后序遍历
	 *
	 * @param root
	 */
	public static void morrisTraversalPostOrder(TreeNode<Integer> root) {
		if (root == null) {
			System.out.println("二叉树的根节点为空，无法进行Morris遍历！");
			return;
		}
		//当前节点
		TreeNode<Integer> cur = root;
		//当前节点的左子树的最右子节点
		TreeNode<Integer> mostRight = null;
		//判断当前节点不为空
		while (cur != null) {
			//先来到当前节点的左子节点
			mostRight = cur.left;
			//判断当前节点的左子节点不为空
			if (mostRight != null) {
				//如果当前节点的左子树不为空
				//判断当前节点的左子树的右子节点是否为空，并且当前节点的左子树的右子节点的右指针没有指向当前节点自己
				while (mostRight.right != null && mostRight.right != cur) {
					//当前节点的左子节点来到当前节点的左子节点的右子节点
					mostRight = mostRight.right;
					//直到两种情况时，退出while循环
				}
				//1.当前节点的左子树的右子节点为空时，退出while循环
				//如果当前节点的左子树的右子节点为空
				if (mostRight.right == null) {
					//当前节点的左子树的右子节点指向当前节点
					mostRight.right = cur;
					//当前节点来到当前节点的左子树
					cur = cur.left;
					//跳过本次循环，进行下一次循环
					continue;
				}
				//2.当前节点的左子树的右子节点的右指针指向了当前节点
				//如果当前节点的左子树的右子节点的右指针已经指向了当前节点
				else {
					//设置当前节点的左子树的右子节点的右指针为null
					mostRight.right = null;
					//对于能够回到自己两次的节点，在第二次经过的时候，逆序打印左子树的右边界
					//打印当前节点左子树的右边界
					printRightBoundary(cur.left);
				}
			}

			//如果当前节点的左子树为空，直接来到当前节点的右子节点
			cur = cur.right;
		}
		//最后逆序打印整棵树的右边界
		cur = root;
		//打印整棵树的右边界
		printRightBoundary(cur);
	}

	/**
	 * 链表反转
	 *
	 * @param cur
	 * @return 返回反转之后的链表头节点
	 */
	private static TreeNode<Integer> reversedList(TreeNode<Integer> cur) {
		TreeNode<Integer> pre = null;
		TreeNode<Integer> next = null;
		while (cur != null) {
			next = cur.right;
			cur.right = pre;
			pre = cur;
			cur = next;
		}
		return pre;
	}

	/**
	 * 打印右边界
	 *
	 * @param treeNode
	 */
	public static void printRightBoundary(TreeNode<Integer> treeNode) {
		//反转右边界
		TreeNode<Integer> tail = reversedList(treeNode);
		TreeNode<Integer> cur = tail;
		//打印整个右边界
		while (cur != null) {
			System.out.print(cur.value + " ");
			cur = cur.right;
		}
		//反转右边界
		reversedList(tail);
	}
}
