package com.mashibing.interviewquestions;

import com.mashibing.node.TreeNode;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 二叉树面试题
 * <p>
 * 题目二：
 * 给定一棵二叉树的根节点 root 和一个整数目标和 targetSum ，
 * 找出所有 从根节点到叶子节点 路径总和 等于给定目标和的路径集合。
 * <p>
 * 叶子节点是指没有子节点的节点。
 * <p>
 * 举例：
 *     5
 *   /   \
 *  8     7
 * /  \  / \
 * 1  5 6   8
 * <p>
 * 指定路径和 18
 * 那么等于指定路径和的路径集合是：
 * {
 * {5, 8, 5},
 * {5, 7, 6}
 * }
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/path-sum-ii
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author xcy
 * @date 2022/4/10 - 16:59
 */
public class BinaryTreeInterviewQuestions009 {
	public static void main(String[] args) {
		TreeNode root = new TreeNode(5);
		root.setLeft(new TreeNode(3));
		root.setRight(new TreeNode(7));
		root.getLeft().setLeft(new TreeNode(1));
		root.getLeft().setRight(new TreeNode(4));
		root.getRight().setLeft(new TreeNode(6));
		root.getRight().setRight(new TreeNode(8));

		List<List<Integer>> lists = pathSum(root, 9);
		for (List<Integer> list : lists) {
			for (Integer integer : list) {
				System.out.print(integer + " ");
			}
			System.out.println();
		}

	}


	/**
	 * 判断一棵树从根节点到叶子节点的路径和等于指定的路径和sum
	 *
	 * @param root 该树的根节点
	 * @param sum  指定的路径总和
	 * @return 满足就返回true，不满足就返回false
	 */
	public static List<List<Integer>> pathSum(TreeNode root, int sum) {
		List<List<Integer>> ans = new LinkedList<>();
		if (root == null) {
			return ans;
		}
		LinkedList<Integer> path = new LinkedList<>();
		process(root, path, 0, sum, ans);
		return ans;
	}

	/**
	 * 判断一棵树从根节点到各个叶子节点的路径和等于指定的路径和sum
	 *
	 * @param node   当前节点
	 * @param path   当前节点的路径
	 * @param preSum 当前节点距离根节点的路径累加和
	 * @param sum    指定的路径总和
	 * @param ans    所有满足指定路径和的所有路径集合
	 * @return
	 */
	public static void process(TreeNode node, List<Integer> path, int preSum, int sum, List<List<Integer>> ans) {
		//如果当前节点是叶子节点
		if (node.left == null && node.right == null) {
			//判断之前的累加和 + 当前节点的值是否 == sum
			if (preSum + node.value == sum) {
				//将当前节点的值加入到path集合中
				path.add(node.value);
				//拷贝path集合，得到新的newPath集合
				List<Integer> newPath = copy(path);
				//将满足指定路径总和的newPath集合添加到ans集合中
				ans.add(newPath);
				//递归需要之后需要返回上一层节点，所以path需要移除当前节点
				path.remove(path.size() - 1);
			}
		}
		//如果当前节点是非叶子节点
		path.add(node.value);
		//累加之前的路径和
		preSum += node.value;
		//判断左子树是否为空
		if (node.left != null) {
			process(node.left, path, preSum, sum, ans);
		}
		//判断右子树是否为空
		if (node.right != null) {
			process(node.right, path, preSum, sum, ans);
		}
		//递归之后，非叶子节点也需要返回到上一层节点，直到根节点为止
		path.remove(path.size() - 1);
	}

	/**
	 * 拷贝集合，返回新的集合
	 *
	 * @param list 初始的集合
	 * @return 新的集合
	 */
	public static List<Integer> copy(List<Integer> list) {
		return new LinkedList<>(list);
	}
}
