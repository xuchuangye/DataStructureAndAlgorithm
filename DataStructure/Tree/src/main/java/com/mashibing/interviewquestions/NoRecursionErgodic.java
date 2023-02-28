package com.mashibing.interviewquestions;

import com.mashibing.node.Node;

import java.util.Stack;

/**
 * 二叉树以非递归的方式进行遍历
 *
 * 中序遍历（非递归的方式）：
 * 1、当前节点cur指向root根节点，cur所在的整棵树的所有左孩子节点都入栈，直到为空，执行第二步
 * 2、栈中弹出节点并打印，来到弹出的当前节点cur的右子节点，直到为空，继续执行第二步，
 * 当节点回到根节点的第一个右子节点时，执行第一步
 * 3、直到栈为空，退出循环
 *
 * @author xcy
 * @date 2022/4/26 - 16:48
 */
public class NoRecursionErgodic {
	public static void main(String[] args) {
		Node root = new Node(1);
		root.setLeft(new Node(2));
		root.setRight(new Node(3));
		root.getLeft().setLeft(new Node(4));
		root.getLeft().setRight(new Node(5));
		root.getRight().setLeft(new Node(6));
		root.getRight().setRight(new Node(7));
		preErgodic(root);//1,2,4,5,3,6,7
		System.out.println("-------------");
		postErgodicTwoStack(root);//4,5,2,6,7,3,1
		System.out.println("-------------");
		postErgodicOneStack(root);//4,5,2,6,7,3,1
		System.out.println("-------------");
		middleErgodic(root);//4,2,5,1,6,3,7
	}

	/**
	 * 先序遍历 --> 非递归的方式
	 *
	 * @param root 二叉树的根节点
	 */
	public static void preErgodic(Node root) {
		if (root == null) {
			System.out.println("二叉树的头节点为空，无法先序遍历");
			return;
		}
		Stack<Node> stack = new Stack<>();
		stack.push(root);
		while (!stack.isEmpty()) {
			root = stack.pop();
			System.out.println(root);
			//有右节点先压入右节点
			if (root.getRight() != null) {
				stack.push(root.getRight());
			}
			//有左节点最后压入左节点
			if (root.getLeft() != null) {
				stack.push(root.getLeft());
			}
		}
	}

	/**
	 * 中序遍历 --> 非递归的方式
	 *
	 * 整棵树所有节点是可以被子树左边界分解的
	 * @param root 二叉树的根节点
	 */
	public static void middleErgodic(Node root) {
		if (root == null) {
			System.out.println("二叉树的头节点为空，无法中序遍历");
			return;
		}
		Stack<Node> stack = new Stack<>();
		Node cur = root;
		//只要栈不为空或者cur不为空就一直循环
		while (!stack.isEmpty() || cur != null) {
			//如果cur节点不为空
			if (cur != null) {
				//将cur节点入栈
				stack.push(cur);
				//继续往左子节点遍历
				cur = cur.getLeft();
			}else {
				//如果cur为空，就从栈中弹出节点并接收
				cur = stack.pop();
				//打印该节点
				System.out.println(cur);
				//继续往右子节点遍历
				cur = cur.getRight();
			}
		}
	}

	/**
	 * 后序遍历(两个栈的实现方式) --> 非递归的方式
	 *
	 * @param root 二叉树的根节点
	 */
	public static void postErgodicTwoStack(Node root) {
		if (root == null) {
			System.out.println("二叉树的头节点为空，无法后序遍历");
			return;
		}
		//先实现头、右、左的顺序
		Stack<Node> stack = new Stack<>();
		Stack<Node> nodes = new Stack<>();
		stack.push(root);
		while (!stack.isEmpty()) {
			root = stack.pop();
			nodes.push(root);
			//有左节点最后压入左节点
			if (root.getLeft() != null) {
				stack.push(root.getLeft());
			}
			//有右节点先压入右节点
			if (root.getRight() != null) {
				stack.push(root.getRight());
			}
		}

		//头、右、左的顺序逆序之后就是左、右、头，也就是后序遍历的顺序
		//最后依次弹出nodes栈中的顶点元素
		while (!nodes.isEmpty()) {
			System.out.println(nodes.pop());
		}
	}

	/**
	 * 后序遍历(一个栈的实现方式) --> 非递归的方式
	 *
	 * @param root 二叉树的根节点
	 */
	public static void postErgodicOneStack(Node root) {
		if (root == null) {
			System.out.println("二叉树的头节点为空，无法后序遍历");
			return;
		}
		Stack<Node> stack = new Stack<>();
		stack.push(root);
		//node节点记录根节点
		Node node = null;
		while (!stack.isEmpty()) {
			node = stack.peek();
			if (node.getLeft() != null && root != node.getLeft() && root != node.getRight()) {
				stack.push(node.getLeft());
			}else if (node.getRight() != null && root != node.getRight()) {
				stack.push(node.getRight());
			}else {
				System.out.println(stack.pop());
				root = node;
			}
		}
	}
}
