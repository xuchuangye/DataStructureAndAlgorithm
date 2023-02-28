package com.mashibing.test;

import com.mashibing.node.HeroNode;
import com.mashibing.tree.ThreadedBinaryTree;

/**
 * 线索化二叉树的测试
 * @author xcy
 * @date 2022/3/26 - 9:41
 */
public class ThreadedBinaryTreeDemo {
	public static void main(String[] args) {
		HeroNode root = new HeroNode(1, "宋江");
		HeroNode node2 = new HeroNode(3, "吴用");
		HeroNode node3 = new HeroNode(6, "卢俊义");
		HeroNode node4 = new HeroNode(8, "林冲");
		HeroNode node5 = new HeroNode(10, "关胜");
		HeroNode node6 = new HeroNode(14, "公孙胜");

		ThreadedBinaryTree threadedBinaryTree = new ThreadedBinaryTree();

		threadedBinaryTree.setRoot(root);
		root.setLeft(node2);
		root.setRight(node3);

		node2.setLeft(node4);
		node2.setRight(node5);

		node3.setLeft(node6);


		/*HeroNode left = node5.getLeft();
		HeroNode right = node5.getRight();

		System.out.printf("%d号节点的前驱节点是%s\n",node5.getNo(), left);
		System.out.printf("%d号节点的后继节点是%s\n",node5.getNo(), right);*/

		//threadedBinaryTree.infixThreadedNodes(root);
		//System.out.println("使用线索化二叉树的方式进行中序遍历");
		//threadedBinaryTree.infixThreadedList();//8,3,10,1,14,6

		threadedBinaryTree.preThreadedNodes(root);
		System.out.println("使用线索化二叉树的方式进行前序遍历");
		threadedBinaryTree.preThreadedList();//1,3,8,10,6,14
	}
}
