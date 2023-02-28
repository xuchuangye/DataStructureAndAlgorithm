package com.mashibing.node;

import lombok.Data;

/**
 * 二叉排序树的节点
 *
 * @author xcy
 * @date 2022/3/29 - 10:01
 */
@Data
public class SortNode {
	public int value;
	public SortNode left;
	public SortNode right;

	public SortNode(int value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "SortNode{" +
				"value=" + value +
				'}';
	}

	/**
	 * 查找当前节点
	 *
	 * @param value 根据值查找对应的节点
	 * @return 返回值是value的节点
	 */
	public SortNode search(int value) {
		if (value == this.value) {
			return this;
		} else if (value < this.value) {
			if (this.left == null) {
				return null;
			}
			return this.left.search(value);
		} else {
			if (this.right == null) {
				return null;
			}
			return this.right.search(value);
		}
	}

	/**
	 * 查找当前节点的父节点
	 *
	 * @param value 根据值查找对应节点的父节点
	 * @return 当前节点的父节点
	 */
	public SortNode searchParent(int value) {
		//如果当前节点的左子节点不为空并且当前节点的左子节点的值等于查找的节点的值，
		//或者当前节点的右子节点不为空并且当前节点的右子节点的值等于查找的节点的值。
		//那么当前节点就是要查找的节点的父节点
		if ((this.left != null && value == this.left.value)
				|| (this.right != null && value == this.right.value)) {
			return this;
		} else {
			//如果当前节点的左子节点不为空并且当前节点的值比要查找的节点的值大
			if (this.left != null && value < this.value) {
				//那么就递归当前节点的左子节点
				return this.left.searchParent(value);
			}
			//如果当前节点的右子节点不为空并且当前节点的值比要查找的节点的值小
			else if (this.right != null && value > this.value) {
				//那么就递归当前节点的右子节点
				return this.right.searchParent(value);
			} else {
				//如果都不满足，证明当前节点没有父节点，直接返回null
				return null;
			}
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
	 * 添加节点
	 *
	 * @param node 节点
	 */
	public void add(SortNode node) {
		if (node == null) {
			return;
		}

		//因为按照二叉排序树的规则，当添加的节点的权值小于当前子树的根节点的权值，那么统统指向当前子树根节点的左子节点
		if (node.value < this.value) {
			//判断当前子树根节点的左子节点是否为空
			if (this.left == null) {
				//true，那么添加的节点直接作为当前子树根节点的左子节点
				this.left = node;
			} else {
				//false，那么就继续往当前子树根节点的左子节点下递归进行比较
				this.left.add(node);
			}
		} else {
			//当添加的节点的权值大于当前子树的根节点的权值，那么统统指向当前子树根节点的右子节点
			//判断当前子树根节点的右子节点是否为空
			if (this.right == null) {
				//true，那么添加的节点直接作为当前子树根节点的右子节点
				this.right = node;
			} else {
				//false，那么就继续往当前子树根节点的右子节点下递归进行比较
				this.right.add(node);
			}
		}

	}
}
