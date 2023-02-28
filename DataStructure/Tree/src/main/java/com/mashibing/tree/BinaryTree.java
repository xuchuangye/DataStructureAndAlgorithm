package com.mashibing.tree;

import com.mashibing.node.HeroNode;
import lombok.Data;

/**
 * 二叉树
 * @author xcy
 * @date 2022/3/24 - 17:15
 */
@Data
public class BinaryTree {
	/**
	 * 根节点
	 */
	private HeroNode root;

	/**
	 * 前序遍历
	 */
	public void preOrder() {
		if (this.root != null) {
			this.root.preOrder();
		}else {
			System.out.println("二叉树为空，无法进行前序遍历");
		}
	}

	/**
	 * 中序遍历
	 */
	public void infixOrder() {
		if (this.root != null) {
			this.root.infixOrder();
		}else {
			System.out.println("二叉树为空，无法进行中序遍历");
		}
	}

	/**
	 * 后序遍历
	 */
	public void postOrder() {
		if (this.root != null) {
			this.root.postOrder();
		}else {
			System.out.println("二叉树为空，无法进行后序遍历");
		}
	}

	/**
	 * 前序遍历查找
	 * @param no 查找的编号
	 * @return 如果查找到，返回HeroNode，如果没有查找到，返回null
	 */
	public HeroNode preOrderSearch(int no) {
		if (this.root != null) {
			return this.root.preOrderSearch(no);
		}else {
			System.out.println("二叉树为空，无法进行前序遍历查找");
			return null;
		}
	}

	/**
	 * 中序遍历查找
	 * @param no 查找的编号
	 * @return 如果查找到，返回HeroNode，如果没有查找到，返回null
	 */
	public HeroNode infixOrderSearch(int no) {
		if (this.root != null) {
			return this.root.infixOrderSearch(no);
		}else {
			System.out.println("二叉树为空，无法进行前序遍历查找");
			return null;
		}
	}

	/**
	 * 后序遍历查找
	 * @param no 查找的编号
	 * @return 如果查找到，返回HeroNode，如果没有查找到，返回null
	 */
	public HeroNode postOrderSearch(int no) {
		if (this.root != null) {
			return this.root.postOrderSearch(no);
		}else {
			System.out.println("二叉树为空，无法进行前序遍历查找");
			return null;
		}
	}

	/**
	 * 删除二叉树中指定的节点
	 * 删除的是叶子节点则直接删除该节点
	 * 删除的是非叶子节点则删除该子树
	 * @param no 要删除的节点编号
	 */
	public void deleteNode(int no) {
		//判断二叉树中的根节点是否为空，如果为空，直接返回
		if (this.root == null) {
			System.out.println("当前要删除指定节点的二叉树为空");
			return;
		}
		//如果二叉树节点不为空，并且根节点就是要删除的节点，那么将根节点置空
		if (this.root.getNo() == no) {
			this.root = null;
		}else {
			//如果根节点不是要删除的节点，那么就遍历二叉树进行删除操作
			this.root.deleteNode(no);
		}
	}

	/**
	 * 删除二叉树中指定的节点
	 * 删除的是叶子节点则直接删除该节点
	 * 删除的是非叶子节点则删除该子树
	 * 删除该子树时，如果该子树只有一个根节点，则直接删除
	 * 如果该子树只有一个子节点，则使用子节点直接替换该子树的根节点
	 * 如果该子树左右子节点都不为空，则使用左子节点直接替换该子树的根节点
	 * @param no 要删除的节点编号
	 */
	public void removeNode(int no) {
		//判断二叉树中的根节点是否为空，如果为空，直接返回
		if (this.root == null) {
			System.out.println("当前要删除指定节点的二叉树为空");
			return;
		}
		//如果二叉树节点不为空，并且根节点就是要删除的节点，那么将根节点置空
		if (this.root.getNo() == no) {
			this.root = null;
		}else {
			//如果根节点不是要删除的节点，那么就遍历二叉树进行删除操作
			this.root.removeNode(no);
		}
	}
}
