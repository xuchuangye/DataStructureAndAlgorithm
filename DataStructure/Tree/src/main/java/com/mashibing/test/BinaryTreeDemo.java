package com.mashibing.test;

import com.mashibing.node.HeroNode;
import com.mashibing.tree.BinaryTree;

/**
 * 二叉树的测试
 *
 * @author xcy
 * @date 2022/3/24 - 17:23
 */
public class BinaryTreeDemo {
	public static void main(String[] args) {
		BinaryTree binaryTree = new BinaryTree();

		HeroNode root = new HeroNode(1, "宋江");
		HeroNode node2 = new HeroNode(2, "吴用");
		HeroNode node3 = new HeroNode(3, "卢俊义");
		HeroNode node4 = new HeroNode(4, "林冲");
		HeroNode node5 = new HeroNode(5, "关胜");

		//设置根节点
		binaryTree.setRoot(root);
		root.setLeft(node2);
		root.setRight(node3);
		node3.setRight(node4);
		node3.setLeft(node5);

		/*System.out.println("前序遍历的结果：");//1,2,3,5,4
		binaryTree.preOrder();

		System.out.println("中序遍历的结果：");//2,1,5,3,4
		binaryTree.infixOrder();

		System.out.println("后序遍历的结果：");//2,5,4,3,1
		binaryTree.postOrder();*/


		/*
		//前序遍历查找
		HeroNode heroNode1 = binaryTree.preOrderSearch(5);
		if (heroNode1 != null) {
			System.out.printf("前序遍历查找到的id是%d，节点是%s\n", heroNode1.getNo(), heroNode1);
		}else {
			System.out.println("前序遍历查找没有找到");
		}

		//中序遍历查找
		HeroNode heroNode2 = binaryTree.infixOrderSearch(5);
		if (heroNode2 != null) {
			System.out.printf("中序遍历查找到的id是%d，节点是%s\n", heroNode2.getNo(), heroNode2);
		}else {
			System.out.println("中序遍历查找没有找到");
		}

		//后序遍历查找
		HeroNode heroNode3 = binaryTree.postOrderSearch(5);
		if (heroNode3 != null) {
			System.out.printf("后序遍历查找到的id是%d，节点是%s\n", heroNode3.getNo(), heroNode3);
		}else {
			System.out.println("后序遍历查找没有找到");
		}*/

		/*System.out.println("删除节点之前的前序遍历");
		binaryTree.preOrder();//1,2,3,5,4

		binaryTree.deleteNode(5);
		binaryTree.deleteNode(3);

		System.out.println("删除节点之后的前序遍历");
		binaryTree.preOrder();//1,2,3,4*/

		System.out.println("删除节点之前的前序遍历");
		binaryTree.preOrder();//1,2,3,5,4

		binaryTree.removeNode(5);
		binaryTree.removeNode(3);

		System.out.println("删除节点之后的前序遍历");
		binaryTree.preOrder();//1,2,5
	}
}
