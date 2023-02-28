package com.mashibing.test;

import com.mashibing.node.AVLNode;
import com.mashibing.tree.AVLTree;

/**
 * 平衡二叉树的测试
 * @author xcy
 * @date 2022/3/29 - 17:07
 */
public class AVLTreeDemo {
	public static void main(String[] args) {
		//int[] arr = {4, 3, 6, 5, 7, 8};
		//int[] arr = {10, 12, 8, 9, 7, 6};
		int[] arr = {10, 11, 7, 6, 8, 9};
		//int[] arr = {2, 1, 6, 5, 7, 3};
		AVLTree avlTree = new AVLTree();

		for (int i : arr) {
			avlTree.add(new AVLNode(i));
		}

		System.out.println("中序遍历");
		avlTree.infixOrder();//

		System.out.println("整个树的深度：" + avlTree.getRoot().height());
		System.out.println("左子树的深度：" + avlTree.getRoot().leftHeight());
		System.out.println("右子树的深度：" + avlTree.getRoot().rightHeight());
		System.out.println("根节点：" + avlTree.getRoot());
		System.out.println("根节点的左子节点：" + avlTree.getRoot().left);
		System.out.println("根节点的右子节点：" + avlTree.getRoot().right);

		/*System.out.println("右旋转之后的中序遍历");
		avlTree.infixOrder();//6,7,8,9,10,12

		System.out.println("整个树的深度：" + avlTree.getRoot().height());
		System.out.println("左子树的深度：" + avlTree.getRoot().leftHeight());
		System.out.println("右子树的深度：" + avlTree.getRoot().rightHeight());*/
	}
}
