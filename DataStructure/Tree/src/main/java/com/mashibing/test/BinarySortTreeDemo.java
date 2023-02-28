package com.mashibing.test;

import com.mashibing.node.SortNode;
import com.mashibing.tree.BinarySearchTree;

/**
 * 二叉搜索树/二叉排序树的测试
 * @author xcy
 * @date 2022/3/29 - 10:13
 */
public class BinarySortTreeDemo {
	public static void main(String[] args) {
		int[] arr = {7, 3, 10, 12, 5, 1, 9, 2};
		BinarySearchTree binarySearchTree = new BinarySearchTree();

		for (int i = 0; i < arr.length; i++) {
			binarySearchTree.add(new SortNode(arr[i]));
		}
		//删除叶子节点
		/*binarySearchTree.deleteNode(2);
		binarySearchTree.deleteNode(5);
		binarySearchTree.deleteNode(9);
		binarySearchTree.deleteNode(12);
		binarySearchTree.deleteNode(1);*/

		//删除只有一个子节点的节点
		//binarySearchTree.deleteNode(1);

		//还是拿出有两个子节点的节点
		//binarySearchTree.deleteNode(7);
		//binarySearchTree.deleteNode(3);
		//binarySearchTree.deleteNode(10);

		//删除所有的节点
		for (int i : arr) {
			binarySearchTree.deleteNode(i);
		}
		binarySearchTree.infixOrder();//1,2,3,5,7,9,10,12

	}
}
