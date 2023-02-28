package com.mashibing.interviewquestions;

import com.mashibing.node.TreeNode;

import java.util.*;

/**
 * 二叉树面试题
 * <p>
 * 题目二：
 * 二叉树的层序遍历
 * 给定一个二叉树的根节点 root ，逆序返回其节点值的层序遍历 。 （即逐层地，从左到右访问所有节点）
 * <p>
 * 举例：
 * root = {3, 9, 20, null, null, 15, 7}
 * 输出：[{15,7},{9,20},{3}]
 * <p>
 * 时间复杂度：
 *
 * @author xcy
 * @date 2022/4/10 - 16:59
 */
public class LevelTraversalBinaryTreeList {
	public static void main(String[] args) {
		TreeNode root = new TreeNode(1);
		root.setLeft(new TreeNode(2));
		root.setRight(new TreeNode(3));
		root.getLeft().setLeft(new TreeNode(4));
		root.getLeft().setRight(new TreeNode(5));
		root.getRight().setLeft(new TreeNode(6));
		root.getRight().setRight(new TreeNode(7));

		List<List<Integer>> lists = levelOrder(root);
		for (List<Integer> list : lists) {
			for (Integer integer : list) {
				System.out.print(integer + " ");
			}
			System.out.println();
		}
	}

	/**
	 * 对树的每一层进行层序遍历，逆序返回集合中由层序遍历的集合而组成的大集合
	 *
	 * @param root 二叉树的根节点
	 * @return 返回由逆序层序遍历的集合组成的大集合
	 * 举例：
	 * {
	 * {4,5,6,7},
	 * {2,3},
	 * {1}
	 * }
	 */
	public static List<List<Integer>> levelOrder(TreeNode root) {
		//逆序实现由层序遍历的集合组成的大集合，需要使用LinkedList，每次层序遍历的集合都插入到0开始的位置上
		//LinkedList插入元素的效率比ArrayList的效率高
		List<List<Integer>> ans = new LinkedList<>();
		if (root == null) {
			return ans;
		}

		//创建一个队列queue的实现LinkedList，因为Queue本身不能实例化，所以需要LinkedList作为Queue的实现
		Queue<TreeNode> queue = new LinkedList<>();
		//先将根节点root添加进去
		queue.add(root);
		//判断队列是否不为空
		while (!queue.isEmpty()) {
			//获取层数
			int size = queue.size();
			//每一层的链表
			List<Integer> curAns = new LinkedList<>();

			//i < size是固定的，不能写i < queue.size()，因为queue是动态添加的，queue.size()会不稳定
			for (int i = 0; i < size; i++) {
				//从队列中取出节点
				TreeNode curNode = queue.poll();
				if (curNode == null) {
					break;
				}
				//先将该节点的值添加到该层的链表中
				curAns.add(curNode.value);
				//判断该节点的左子节点是否为空，如果不为空就添加到队列中
				if (curNode.left != null) {
					queue.add(curNode.left);
				}
				//判断该节点的右子节点是否为空，如果不为空就添加到队列中
				if (curNode.right != null) {
					queue.add(curNode.right);
				}
			}
			//ans.add(curAns);
			//逆序返回由层序遍历的集合组成的大集合，所以从0开始插入
			ans.add(0, curAns);
		}
		return ans;
	}
}
