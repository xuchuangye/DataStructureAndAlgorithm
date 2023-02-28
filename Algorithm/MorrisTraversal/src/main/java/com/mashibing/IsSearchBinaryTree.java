package com.mashibing;

import com.mashibing.pojo.Node;

/**
 * @author xcy
 * @date 2022/5/23 - 16:02
 */
public class IsSearchBinaryTree {
	public static void main(String[] args) {
		Node root = new Node(4);
		root.left = new Node(2);
		root.right = new Node(6);
		root.left.left = new Node(1);
		root.left.right = new Node(3);
		root.right.left = new Node(5);
		root.right.right = new Node(7);
		boolean isSBT = morrisTraversalInfixOrderIsSearchBianryTree(root);
		System.out.println(isSBT);
	}

	/**
	 * @param root 二叉树的根节点
	 * @return 返回该二叉树是否是搜索二叉树，如果是返回true，否则返回 false
	 */
	public static boolean morrisTraversalInfixOrderIsSearchBianryTree(Node root) {
		if (root == null) {
			return true;
		}
		//当前节点
		Node cur = root;
		//当前节点的左子树的最右子节点
		Node mostRight = null;

		//前一个节点的值
		Integer pre = null;
		//是否是搜索二叉树
		boolean isSBT = true;
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
				else {
					//如果当前节点的左子树的右子节点的右指针已经指向了当前节点
					//说明是第二次经过当前节点，直接打印

					//设置当前节点的左子树的右子节点的右指针为null
					mostRight.right = null;
				}
			}
			/*else {
				//当前节点的左子节点为空，说明不会经过当前节点两次，也就不会回到当前节点两次，直接打印
			}*/
			if (pre != null && pre >= cur.value) {
				isSBT = false;
				//TODO 这里不能直接返回，因为会破坏二叉树的结构
				//return isSBT;
			}
			pre = cur.value;
			//如果当前节点的左子树为空，直接来到当前节点的右子节点
			cur = cur.right;
		}
		return isSBT;
	}
}
