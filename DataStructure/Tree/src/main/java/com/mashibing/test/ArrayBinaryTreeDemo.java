package com.mashibing.test;

import com.mashibing.tree.ArrayBinaryTree;

/**
 * 数组二叉树的测试
 * @author xcy
 * @date 2022/3/25 - 15:37
 */
public class ArrayBinaryTreeDemo {
	public static void main(String[] args) {
		int[] arr = {1, 2, 3, 4, 5, 6, 7};

		ArrayBinaryTree arrayBinaryTree = new ArrayBinaryTree(arr);

		System.out.println("顺序存储二叉树的前序遍历");
		arrayBinaryTree.preOrder();//1,2,4,5,3,6,7

		System.out.println();
		System.out.println("顺序存储二叉树的中序遍历");
		arrayBinaryTree.infixOrder(0);//4,2,5,1,6,3,7

		System.out.println();
		System.out.println("顺序存储二叉树的后序遍历");
		arrayBinaryTree.postOrder(0);//4,5,2,6,7,3,1
	}
}
