package com.mashibing.node;

import lombok.Data;

/**
 * 节点
 *
 * @author xcy
 * @date 2022/3/27 - 9:43
 */
@Data
public class TreeNode implements Comparable<TreeNode> {
	/**
	 * 节点的权值
	 */
	public final int value;

	/**
	 * 左子节点
	 */
	public TreeNode left;

	/**
	 * 右子节点
	 */
	public TreeNode right;

	public TreeNode(int value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "Node{" +
				"value=" + value +
				'}';
	}

	@Override
	public int compareTo(TreeNode o) {
		//表示从小到大排序
		return this.value - o.value;
	}

	/**
	 * 前序遍历
	 */
	public void preOrder() {
		System.out.println(this);
		if (this.left != null) {
			this.left.preOrder();
		}
		if (this.right != null) {
			this.right.preOrder();
		}
	}

	/**
	 * 中序遍历
	 */
	public void infixOrder() {
		if (this.left != null) {
			this.left.infixOrder();
		}
		System.out.println(this);
		if (this.right != null) {
			this.right.infixOrder();
		}
	}

	/**
	 * 后序遍历
	 */
	public void suffixOrder() {
		if (this.left != null) {
			this.left.suffixOrder();
		}
		if (this.right != null) {
			this.right.suffixOrder();
		}
		System.out.println(this);
	}
}
