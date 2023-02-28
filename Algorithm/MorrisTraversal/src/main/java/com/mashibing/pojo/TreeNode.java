package com.mashibing.pojo;

/**
 * @author xcy
 * @date 2022/5/23 - 11:58
 */
public class TreeNode<T> {
	public T value;
	public TreeNode<T> left;
	public TreeNode<T> right;

	public TreeNode(T value) {
		this.value = value;
	}
}
