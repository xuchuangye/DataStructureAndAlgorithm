package com.mashibing.interviewquestions;


import com.mashibing.node.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * 二叉树面试题
 * <p>
 * Leetcode
 * 431. Encode N-ary Tree to Binary Tree
 * 将N叉树序列化为二叉树
 * 给定一个多叉树的root节点，序列化为唯一的二叉树，返回二叉树的根节点 --> encode
 * 给定一个二叉树的根节点，反序列化为多叉树，返回多叉树的根节点 --> decode
 * <p>
 * 基本思路：
 * 将当前节点的所有子节点作为当前节点的左子节点的右边界
 * 举例：
 * ......a
 * ..../ | \
 * ...b  c  d
 * <p>
 * ....a
 * .../
 * ..b
 * ...\
 * ....c
 * .....\
 * ......d
 *
 * 本题测试链接：https://leetcode.com/problems/encode-n-ary-tree-to-binary-tree
 * @author xcy
 * @date 2022/4/10 - 16:59
 */
public class BinaryTreeInterviewQuestions012 {
	public static void main(String[] args) {

	}

	/**
	 * 多叉树的节点类
	 */
	public static class Node {
		public int value;
		public List<Node> children;

		public Node() {

		}

		public Node(int value) {
			this.value = value;
		}

		public Node(int value, List<Node> children) {
			this.value = value;
			this.children = children;
		}
	}

	class Code {
		/**
		 * 多叉树转换成二叉树，并返回二叉树的根节点
		 *
		 * @param root 多叉树的根节点
		 * @return 二叉树的根节点
		 */
		public TreeNode encode(Node root) {
			if (root == null) {
				return null;
			}
			//将当前多叉树的节点的值作为二叉树根节点的值
			TreeNode treeNode = new TreeNode(root.value);
			//将当前多叉树的子节点作为二叉树根节点的左子节点的右边界
			treeNode.left = en(root.children);
			return treeNode;
		}

		/**
		 * 将当前多叉树的子节点作为二叉树根节点的左子节点的右边界
		 * @param children 多叉树的子节点
		 * @return 二叉树根节点的左子节点
		 */
		public TreeNode en(List<Node> children) {
			//子节点为空，直接返回null
			if (children == null) {
				return null;
			}
			//二叉树根节点的左子节点右边界的节点
			TreeNode root = null;
			//
			TreeNode cur = null;
			for (Node child : children) {
				//将多叉树的节点转换为二叉树的节点
				//将多叉树根节点的子节点的值作为二叉树根节点的左子节点的值和左子节点右边界节点的值
				TreeNode node = new TreeNode(child.value);
				//第一个多叉树根节点的子节点的值作为二叉树根节点的左子节点的值
				//二叉树根节点的左子节点右边界的起始节点root = node
				if (root == null) {
					root = node;
				}
				//其余的将多叉树根节点的子节点的值作为二叉树根节点的左子节点右边界节点的值
				//二叉树根节点的左子节点右边界的起始节点之后的节点cur.right = node
				else {
					cur.right = node;
				}
				//cur指向node节点
				cur = node;
				//继续深度遍历cur节点的左子节点的右边界
				cur.left = en(child.children);
			}
			return root;
		}

		/**
		 * 二叉树转换成多叉树，并返回多叉树的根节点
		 * @param root 二叉树的根节点
		 * @return 多叉树的根节点
		 */
		public Node decode(TreeNode root) {
			if (root == null) {
				return null;
			}
			//传入参数：二叉树根节点的值和二叉树的左子树
			return new Node(root.value, de(root.left));
		}

		/**
		 *
		 * @param root
		 * @return
		 */
		public List<Node> de(TreeNode root) {
			List<Node> children = new ArrayList<>();
			while (root != null) {
				Node node = new Node(root.value, de(root.left));
				children.add(node);
				root = root.right;
			}
			return children;
		}
	}
}
