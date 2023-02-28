package com.mashibing.interviewquestions;

import com.mashibing.node.TreeNode;

import java.util.HashMap;
import java.util.Map;

/**
 * 二叉树面试题
 * 题目二：
 * 使用前序数组和中序数组重建一棵树
 * 举例：
 * 前序数组：[1,2,4,5,3,6,7]
 * 中序数组：[4,2,5,1,6,3,7]
 *
 * 时间复杂度：O(N)
 * 递归的次数跟数组的元素个数有关，所以数组元素个数为N，那么递归次数也就为N，所以时间复杂度为O(N)
 *
 * @author xcy
 * @date 2022/4/10 - 16:59
 */
public class PreAndInfixArrayBuildBinaryTree {
	public static void main(String[] args) {
		/*TreeNode root = new TreeNode(1);
		root.setLeft(new TreeNode(2));
		root.setRight(new TreeNode(3));
		root.getLeft().setLeft(new TreeNode(4));
		root.getLeft().setRight(new TreeNode(5));
		root.getRight().setLeft(new TreeNode(6));
		root.getRight().setRight(new TreeNode(7));*/
		int[] preOrder = {1, 2, 4, 5, 3, 6, 7};
		int[] infixOrder = {4, 2, 5, 1, 6, 3, 7};
		TreeNode root = buildTreeWIthMap(preOrder, infixOrder);
		if (root != null) {
			root.infixOrder();
		}
		if (root != null) {
			root.preOrder();
		}
	}

	/**
	 * 获取前序数组和中序数组构建的树的根节点
	 * @param pre 前序数组
	 * @param in 中序数组
	 * @return 返回 前序数组和中序数组构建的树的根节点
	 */
	public static TreeNode buildTree(int[] pre, int[] in) {
		//判断pre数组是否为空，in数组是否为空，pre数组元素的个数是否和in数组元素个数一样
		//如果上述条件都不满足，则直接返回null，表示无法创建数
		if (pre == null || in == null || pre.length != in.length) {
			return null;
		}

		return buildBinaryTree(pre, 0, pre.length - 1, in, 0, in.length - 1);
	}

	/**
	 * @param pre   前序数组
	 * @param left  前序数组的left
	 * @param right 前序数组的right
	 * @param in    中序数组
	 * @param L     中序数组的left
	 * @param R     中序数组的right
	 * @return 返回前序数组和中序数组构建之后的树的头节点
	 */
	public static TreeNode buildBinaryTree(int[] pre, int left, int right, int[] in, int L, int R) {
		//退出递归的重要条件
		//因为实际上有可能左子树为空或者右子树为空的
		if (left > right) {
			return null;
		}
		//记录构建树的新的头节点
		TreeNode head = new TreeNode(pre[left]);

		//如果pre数组中只有一个节点，那么直接返回该节点
		if (left == right) {
			return head;
		}

		int mid = 0;
		//前序数组pre[]中，第一个元素作为头节点
		//中序数组in[]中，找到与前序数组的第一个元素相等的元素，该元素也是头节点，索引为mid
		//如果在中序数组in[]中没有找到，就继续找，mid++
		while (in[mid] != pre[left]) {
			mid++;
		}

		//头节点左子树的范围就是前序数组pre[]中，索引left + 1 到 left + mid - L
		//头节点左子树的范围就是中序数组in[]中，索引0到mid - 1
		head.left = buildBinaryTree(pre, left + 1, left + mid - L, in, L, mid - 1);
		//头节点右子树的范围就是前序数组pre[]中，索引left + mid - L + 1到 right
		//头节点左子树的范围就是中序数组in[]中，索引mid + 1到R
		head.right = buildBinaryTree(pre, left + mid - L + 1, right, in, mid + 1, R);

		return head;
	}



	/**
	 * 获取前序数组和中序数组构建的树的根节点
	 * @param pre 前序数组
	 * @param in 中序数组
	 * @return 返回 前序数组和中序数组构建的树的根节点
	 */
	public static TreeNode buildTreeWIthMap(int[] pre, int[] in) {
		//判断pre数组是否为空，in数组是否为空，pre数组元素的个数是否和in数组元素个数一样
		//如果上述条件都不满足，则直接返回null，表示无法创建数
		if (pre == null || in == null || pre.length != in.length) {
			return null;
		}

		//将中序数组的所有元素记录在一张表中，省去每次递归中循环遍历查找的过程
		Map<Integer, Integer> valueIndexMap = new HashMap<>();
		for (int i = 0; i < in.length; i++) {
			//记录中序数组in[]中每一个元素所在的索引，省去遍历查找的过程
			//Map的key是元素的值，value是元素值所在中序数组in[]中的索引
			valueIndexMap.put(in[i], i);
		}

		return buildBinaryTreeWIthMap(pre, 0, pre.length - 1, in, 0, in.length - 1, valueIndexMap);
	}

	/**
	 * @param pre   前序数组
	 * @param left  前序数组的left
	 * @param right 前序数组的right
	 * @param in    中序数组
	 * @param L     中序数组的left
	 * @param R     中序数组的right
	 * @return 返回前序数组和中序数组构建之后的树的头节点
	 */
	public static TreeNode buildBinaryTreeWIthMap(int[] pre, int left, int right, int[] in, int L, int R, Map<Integer, Integer> valueIndexMap) {
		//退出递归的重要条件
		//因为实际上有可能左子树为空或者右子树为空的
		if (left > right) {
			return null;
		}
		//记录构建树的新的头节点
		TreeNode head = new TreeNode(pre[left]);

		//如果pre数组中只有一个节点，那么直接返回该节点
		if (left == right) {
			return head;
		}

		int mid =  valueIndexMap.get(pre[left]);
		//前序数组pre[]中，第一个元素作为头节点
		//中序数组in[]中，找到与前序数组的第一个元素相等的元素，该元素也是头节点，索引为mid
		//如果在中序数组in[]中没有找到，就继续找，mid++
		/*
		int mid = 0;
		while (in[mid] != pre[left]) {
			mid++;
		}*/

		//头节点左子树的范围就是前序数组pre[]中，索引left + 1 到 left + mid - L
		//头节点左子树的范围就是中序数组in[]中，索引0到mid - 1
		head.left = buildBinaryTreeWIthMap(pre, left + 1, left + mid - L, in, L, mid - 1, valueIndexMap);
		//头节点右子树的范围就是前序数组pre[]中，索引left + mid - L + 1到 right
		//头节点左子树的范围就是中序数组in[]中，索引mid + 1到R
		head.right = buildBinaryTreeWIthMap(pre, left + mid - L + 1, right, in, mid + 1, R, valueIndexMap);

		return head;
	}
}
