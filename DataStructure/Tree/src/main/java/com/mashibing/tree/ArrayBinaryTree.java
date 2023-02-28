package com.mashibing.tree;

/**
 * 数组实现顺序存储二叉树
 *
 * @author xcy
 * @date 2022/3/25 - 15:26
 */
public class ArrayBinaryTree {
	private final int[] arr;

	public ArrayBinaryTree(int[] arr) {
		this.arr = arr;
	}

	public void preOrder() {
		this.preOrder(0);
	}

	public void infixOrder() {
		this.infixOrder(0);
	}

	public void postOrder() {
		this.postOrder(0);
	}

	/**
	 * 前序遍历
	 *
	 * @param index 对应数组中第index个元素
	 */
	public void preOrder(int index) {
		if (arr == null || arr.length == 0) {
			System.out.println("数组为空，无法进行顺序存储二叉树遍历");
			return;
		}

		//每个子树的根节点
		System.out.print(arr[index] + "\t");

		//左子树遍历
		if ((index * 2 + 1) < arr.length) {
			preOrder(index * 2 + 1);
		}

		//右子树遍历
		if ((index * 2 + 2) < arr.length) {
			preOrder(index * 2 + 2);
		}
	}

	/**
	 * 中序遍历
	 *
	 * @param index 对应数组中第index个元素
	 */
	public void infixOrder(int index) {
		if (arr == null || arr.length == 0) {
			System.out.println("数组为空，不能进行顺序存储二叉树遍历");
			return;
		}

		//左子树遍历
		if ((index * 2 + 1) < arr.length) {
			infixOrder(index * 2 + 1);
		}

		//每个子树的根节点
		//index == (index - 1) / 2
		System.out.print(arr[index] + "\t");

		//右子树遍历
		if ((index * 2 + 2) < arr.length) {
			infixOrder(index * 2 + 2);
		}
	}

	public void postOrder(int index) {
		if (arr == null || arr.length == 0) {
			System.out.println("数组为空，不能进行顺序存储二叉树遍历");
			return;
		}

		//左子树遍历
		if ((index * 2 + 1) < arr.length) {
			postOrder(index * 2 + 1);
		}

		//右子树遍历
		if ((index * 2 + 2) < arr.length) {
			postOrder(index * 2 + 2);
		}

		//每个子树的根节点
		System.out.print(arr[index] + "\t");
	}

}
