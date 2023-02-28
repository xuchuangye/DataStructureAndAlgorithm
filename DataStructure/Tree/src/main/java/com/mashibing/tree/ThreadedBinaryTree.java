package com.mashibing.tree;

import com.mashibing.node.HeroNode;
import lombok.Data;

/**
 * 线索化二叉树
 *
 * 因为二叉树没有充分利用每一个节点的左右指针，所以才会有线索性二叉树
 *
 * @author xcy
 * @date 2022/3/26 - 9:29
 */
@Data
public class ThreadedBinaryTree {
	/**
	 * 根节点
	 */
	private HeroNode root;

	/**
	 * 前驱节点
	 * 为了实现线索化二叉树，需要创建指向当前节点的前一个节点的引用pre
	 */
	private HeroNode pre = null;



	/**
	 * 线索化二叉树 -> 按照中序遍历的方式进行线索化
	 *
	 * @param node
	 */
	public void infixThreadedNodes(HeroNode node) {
		//判断节点是否为空，如果为空则该节点不能线索化
		if (node == null) {
			return;
		}

		//首先先线索化左子树
		infixThreadedNodes(node.getLeft());

		//然后线索化当前节点的前驱节点
		if (node.getLeft() == null) {
			node.setLeft(pre);
			//表示指向的是前驱节点
			node.setLeftType(1);
		}
		//接着线索化当前节点的后继节点
		if (pre != null && pre.getRight() == null) {
			pre.setRight(node);
			//表示指向的是后继节点
			pre.setRightType(1);
		}

		//
		pre = node;

		//最后线索化右子树
		infixThreadedNodes(node.getRight());
	}

	/**
	 * 遍历线索化二叉树 -> 按照中序遍历的方式
	 */
	public void infixThreadedList() {
		//根节点不能移动，所以需要辅助节点来完成遍历
		HeroNode node = root;
		//节点不能为空，如果为空就无法进行遍历
		while (node != null) {
			//判断当前节点的前驱节点，如果node.getLeftType() == 1就表示当前节点是按照线索化的前驱节点
			//循环遍历直到遇到node.getLeftType() == 0退出循环，也就是遇到默认的左子树结束
			while (node.getLeftType() == 0) {
				node = node.getLeft();
			}

			//打印输出当前节点
			System.out.println(node);

			//判断当前节点的后继节点，如果node.getRightType() == 1就表示当前节点是按照线索化的后继节点
			while (node.getRightType() == 1) {
				node = node.getRight();
				//打印输出当前的后继节点
				System.out.println(node);
			}
			//为了防止死循环，需要使用右子树替换遍历的当前节点
			//为什么使用右子树而不是其他呢？
			//因为线索化二叉树是根据中序遍历的方式进行线索化，顺序是左子树 ，当前子树的根节点，右子树
			//右子树是最后，所以最后使用右子树进行替换
			node = node.getRight();
		}
	}

	/**
	 * 线索化二叉树 -> 按照前序遍历的方式进行线索化
	 * @param node
	 */
	public void preThreadedNodes(HeroNode node) {
		//判断当前节点是否为空，如果为空表示无法进行线索化，直接返回
		if (node == null) {
			return;
		}


		//判断当前节点的左子树是否为空
		if (node.getLeft() == null) {
			//如果为空，则进行线索化为前驱节点
			node.setLeft(pre);
			//线索化之后的节点使用leftType == 1做个标记
			node.setLeftType(1);
		}

		//判断当前节点的前一个节点是否为空，，并且前一个节点的右子树是否为空
		if (pre != null && pre.getRight() == null) {
			//如果为空，则进行线索化为后继节点
			pre.setRight(node);
			//线索化之后的节点使用rightType == 1做个标记
			pre.setRightType(1);
		}


		//当前节点替换前一个节点
		pre = node;

		//线索化左子树
		preThreadedNodes(node.getLeft());

		//线索化右子树
		preThreadedNodes(node.getRight());
	}

	/**
	 * 遍历线索化二叉树 -> 按照前序遍历的方式
	 */
	public void preThreadedList() {

		//因为根节点不能 移动，所以需要辅助节点
		HeroNode node = root;

		//如果当前节点不为空
		while (node != null) {
			//首先打印当前节点
			System.out.println(node);
			//如果当前节点的前驱节点为空
			while (node.getLeftType() == 1) {
				//那么继续遍历下一个节点，查看是否是前驱节点
				node = node.getLeft();
				System.out.println(node);
			}

			while (node.getRightType() == 1) {
				node = node.getRight();
				System.out.println(node);
			}
			node = node.getRight();
		}
	}
}
