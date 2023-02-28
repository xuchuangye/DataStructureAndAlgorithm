package com.mashibing.day10;

/**
 * 题目四：
 * 给定一棵搜索二叉树头节点，转化成首尾相接的有序双向链表（节点都有两个方向的指针）
 * <p>
 * 解题思路：
 * 使用二叉树的递归套路
 * <p>
 * LeetCode测试链接：
 * https://leetcode.cn/problems/convert-binary-search-tree-to-sorted-doubly-linked-list/
 *
 * @author xcy
 * @date 2022/7/27 - 15:24
 */
public class Code04_BSTtoDoubleLinkedList {
	public static void main(String[] args) {

	}

	public static class Node {
		public int value;
		public Node left;
		public Node right;

		public Node(int value) {
			this.value = value;
		}
	}

	public static class Info {
		/**
		 * 头节点
		 */
		public Node head;
		/**
		 * 尾节点
		 */
		public Node tail;

		public Info(Node head, Node tail) {
			this.head = head;
			this.tail = tail;
		}
	}

	public static Node treeToDoublyList(Node head) {
		if (head == null) {
			return null;
		}
		Info data = process(head);
		//head ... tail
		data.tail.right = data.head;
		data.head.left = data.tail;
		return data.head;
	}

	public static Info process(Node node) {
		if (node == null) {
			return new Info(null, null);
		}
		//当前节点的左子树信息
		Info leftInfo = process(node.left);
		//当前节点的右子树信息
		Info rightInfo = process(node.right);

		//如果当前节点左子树的尾部节点不为空
		if (leftInfo.tail != null) {
			//当前节点左子树的尾部节点的右指针指向当前节点
			leftInfo.tail.right = node;
		}
		//当前节点的左指针指向leftInfo的尾部节点tail
		node.left = leftInfo.tail;
		//当前节点的右指针指向rightInfo的头部节点head
		node.right = rightInfo.head;

		//如果当前节点右子树的尾部节点不为空
		if (rightInfo.head != null) {
			//当前节点右子树的尾部节点的左指针指向当前节点
			rightInfo.head.left = node;
		}
		//整体链表的头节点rightInfo.head != null ? rightInfo.head : node
		//整体链表的尾节点leftInfo.tail != null ? leftInfo.tail : node
		//            node
		//        /          \
		//leftInfo.tail   rightInfo.head
		return new Info((leftInfo.tail != null ? leftInfo.tail : node), (rightInfo.head != null ? rightInfo.head : node));
	}
}
