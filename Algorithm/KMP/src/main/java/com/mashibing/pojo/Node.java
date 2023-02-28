package com.mashibing.pojo;

/**
 * 节点类
 * @author xcy
 * @date 2022/5/21 - 15:22
 */
public class Node {
	public int value;
	public Node left;
	public Node right;
	public Node(int value) {
		this.value = value;
	}

	public void infixOrder() {
		System.out.println(this.value);
		if (this.left != null) {
			this.left.infixOrder();
		}
		if (this.right != null) {
			this.right.infixOrder();
		}
	}
}
