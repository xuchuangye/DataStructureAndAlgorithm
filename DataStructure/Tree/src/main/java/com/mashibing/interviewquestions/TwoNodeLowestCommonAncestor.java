package com.mashibing.interviewquestions;

import com.mashibing.common.BinaryTreeUtils;
import com.mashibing.node.Node;

import java.util.HashMap;
import java.util.HashSet;

/**
 * 二叉树的面试题
 * 给定一棵二叉树的头节点root，和另外两个节点a和b
 * 返回a和b的最低公共祖先
 * <p>
 * 基本思路：
 * 情况一：与当前节点x无关，x不是节点a和b的最低公共祖先
 * 1。当前节点所在的树的左子树找到了节点a和b的最低公共祖先
 * 2。当前节点所在的树的右子树找到了节点a和b的最低公共祖先
 * 3。当前节点所在的树中节点a和b没有找全
 * 情况二：与当前节点x有关，x是节点a和b的最低公共祖先
 * 1、当前节点所在的树的左子树中找到节点a和b的其中一个，右子树中找到另一个
 * 2、当前节点x本身就是节点a和b的最低公共祖先，x本身就是a节点，左右子树中找到了b
 * 3、当前节点x本身就是节点a和b的最低公共祖先，x本身就是b节点，左右子树中找到了a
 *
 * @author xcy
 * @date 2022/5/1 - 8:15
 */
public class TwoNodeLowestCommonAncestor {
	public static void main(String[] args) {
		int maxLevel = 4;
		int maxValue = 100;
		int testTimes = 10000;
		System.out.println("测试开始");
		for (int i = 0; i < testTimes; i++) {
			Node head = BinaryTreeUtils.generateRandomBST(maxLevel, maxValue);
			Node a = BinaryTreeUtils.pickRandomOne(head);
			Node b = BinaryTreeUtils.pickRandomOne(head);
			if (lowestCommonAncestor(head, a, b) != lowestAncestor(head, a, b)) {
				System.err.println("测试错误!");
			}
		}
		System.out.println("测试结束!");
	}

	/**
	 * 当前节点所在的树需要知道的信息封装类
	 * 当前节点所在的树需要知道的信息：
	 * 1、整棵树是否发现a
	 * 2、整棵树是否发现b
	 * 3、整棵树上是否发现 a和b的最低公共祖先
	 */
	public static class Info {
		public boolean isFindA;
		public boolean isFindB;
		public Node ans;

		public Info(boolean isFindA, boolean isFindB, Node ans) {
			this.isFindA = isFindA;
			this.isFindB = isFindB;
			this.ans = ans;
		}
	}

	/**
	 *
	 * @param root
	 * @param a
	 * @param b
	 * @return
	 */
	public static Node lowestCommonAncestor(Node root, Node a, Node b) {
		if (root == null) {
			return null;
		}
		return process(root, a, b).ans;
	}

	/**
	 * 核心逻辑
	 * @param node
	 * @param a
	 * @param b
	 * @return
	 */
	public static Info process(Node node, Node a, Node b) {
		if (node == null) {
			return new Info(false, false, null);
		}
		Info leftInfo = process(node.left, a, b);
		Info rightInfo = process(node.right, a, b);
		//判断是否发现a节点
		//当前节点本身就是a节点
		//当前节点所在的树的左子树中发现了a节点
		//当前节点所在的树的右子树中发现了a节点
		boolean isFindA = (node == a) || leftInfo.isFindA || rightInfo.isFindA;
		//判断是否发现b节点
		//当前节点本身就是b节点
		//当前节点所在的树的左子树中发现了b节点
		//当前节点所在的树的右子树中发现了b节点
		boolean isFindB = (node == b) || leftInfo.isFindB || rightInfo.isFindB;
		Node ans = null;

		//如果左子树中找到了
		if (leftInfo.ans != null) {
			ans = leftInfo.ans;
		}
		//如果右子树中找到了
		else if (rightInfo.ans != null) {
			ans = rightInfo.ans;
		}else {
			//如果左右子树都没有找到，但是仍然发现了A和B，那么该节点就是答案
			if (isFindA && isFindB) {
				ans = node;
			}
		}

		return new Info(isFindA, isFindB, ans);
	}


	public static Node lowestAncestor(Node head, Node a, Node b) {
		if (head == null) {
			return null;
		}
		// key的父节点是value
		HashMap<Node, Node> parentMap = new HashMap<>();
		parentMap.put(head, null);
		fillParentMap(head, parentMap);
		HashSet<Node> ASet = new HashSet<>();
		Node cur = a;
		ASet.add(cur);
		while (parentMap.get(cur) != null) {
			cur = parentMap.get(cur);
			ASet.add(cur);
		}
		cur = b;
		while (!ASet.contains(cur)) {
			cur = parentMap.get(cur);
		}
		return cur;
	}

	public static void fillParentMap(Node head, HashMap<Node, Node> parentMap) {
		if (head.left != null) {
			parentMap.put(head.left, head);
			fillParentMap(head.left, parentMap);
		}
		if (head.right != null) {
			parentMap.put(head.right, head);
			fillParentMap(head.right, parentMap);
		}
	}
}
