package com.mashibing;

import com.mashibing.pojo.Node;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 给定两棵二叉树的头节点head1和head2
 * 想知道head1中是否有某个子树的结构和head2完全一样
 *
 * @author xcy
 * @date 2022/5/21 - 15:21
 */
public class OneTreeContainsAnother {
	public static void main(String[] args) {
		Node root1 = new Node(1);
		root1.left = new Node(2);
		root1.right = new Node(3);
		root1.left.left = new Node(4);
		root1.left.right = new Node(5);
		root1.right.left = new Node(6);
		root1.right.right = new Node(7);

		Node root2 = new Node(2);
		root2.left = new Node(4);
		root2.right = new Node(5);

		boolean b = oneTreeContainsAnother(root1, root2);
		System.out.println(b);
	}

	/**
	 * 1.判断两棵树的节点是否为空
	 * 2.使用中序遍历对两棵树进行序列化
	 * 3.判断其中一棵树中是否包含另一棵树，如果包含，返回true，否则返回false
	 * @param root1
	 * @param root2
	 * @return
	 */
	public static boolean oneTreeContainsAnother(Node root1, Node root2) {
		if (root1 == null || root2 == null) {
			return false;
		}
		Queue<String> queue1 = preSerializable(root1);
		StringBuilder stringBuilder1 = new StringBuilder();
		for (String s : queue1) {
			stringBuilder1.append(s);
		}
		Queue<String> queue2 = preSerializable(root2);
		StringBuilder stringBuilder2 = new StringBuilder();
		for (String s : queue2) {
			stringBuilder2.append(s);
		}
		String str1 = new String(stringBuilder1);
		String str2 = new String(stringBuilder2);
		System.out.println(str1);
		preSerializable(root2);
		System.out.println(str2);
		return KMPAlgorithm.KMP(str1, str2) != 0;
	}

	/**
	 * 先序遍历的方式序列化 --> 将二叉树结构序列化为字符串类型的队列
	 *
	 * @param root 二叉树的根节点
	 * @return 字符串类型的队列
	 */
	public static Queue<String> preSerializable(Node root) {
		Queue<String> queue = new LinkedList<>();
		preSerializable(root, queue);
		return queue;
	}

	/**
	 * 先序遍历 --> 将二叉树结构序列化为字符串类型的队列
	 *
	 * @param root 二叉树的根节点
	 */
	public static void preSerializable(Node root, Queue<String> queue) {
		if (root == null) {
			queue.add("#");
		} else {
			queue.add(String.valueOf(root.value));
			preSerializable(root.left, queue);
			preSerializable(root.right, queue);
		}
	}
}
