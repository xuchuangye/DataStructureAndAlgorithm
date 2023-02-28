package com.mashibing.day05;

import java.util.Stack;

/**
 * 题目一：
 * 已知一棵搜索二叉树上没有重复值的节点，
 * 现在有一个数组arr，是这棵搜索二叉树先序遍历的结果，请根据arr生成整棵树并返回头节点
 * <p>
 * 举例：
 * arr[] = {5,3,1,2,4,7,6,9,8}
 * -       5
 * -     /   \
 * -    3     7
 * -   / \   / \
 * -  1   4 6   9
 * -   \       /
 * -    2     8
 * 解题思路：
 * 1.使用二叉树递归套路和单调栈
 * <p>
 * <p>
 * LeetCode测试链接：
 * https://leetcode.com/problems/construct-binary-search-tree-from-preorder-traversal/
 *
 * @author xcy
 * @date 2022/7/18 - 16:22
 */
public class Code01_ConstructBinarySearchTreeFromPreorderTraversal {
	public static void main(String[] args) {
		int[] pre = {5, 3, 1, 2, 4, 7, 6, 9, 8};
		TreeNode treeNode1 = bstFromPreorderWithMonotoneStack(pre);
		TreeNode treeNode2 = bstFromPreorderWithRecursion(pre);
		System.out.println(treeNode1.val);
		System.out.println(treeNode2.val);
	}

	public static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;

		TreeNode() {
		}

		TreeNode(int val) {
			this.val = val;
		}

		TreeNode(int val, TreeNode left, TreeNode right) {
			this.val = val;
			this.left = left;
			this.right = right;
		}
	}

	/**
	 * 使用暴力递归的方式
	 *
	 * 时间复杂度：O(N的2次方)
	 *
	 * @param pre
	 * @return
	 */
	public static TreeNode bstFromPreorderWithRecursion(int[] pre) {
		if (pre == null || pre.length == 0) {
			return null;
		}
		return coreLogicWithRecursion(pre, 0, pre.length - 1);
	}

	/**
	 * @param pre 先序遍历的数组
	 * @param L   开始
	 * @param R   结束
	 * @return 根据L到R的范围，创建搜索二叉树，并返回搜索二叉树的头节点
	 */
	public static TreeNode coreLogicWithRecursion(int[] pre, int L, int R) {
		//L > R表示有两种情况：
		//情况1：这是一个无效范围，不可能有节点，所以为null，让递归也可以返回
		//情况2：整个先序数组中没有小于头节点值的数
		//举例：
		//pre[] = {5, 6, 7, 8, 9, 10}
		//index =  0  1  2  3  4  5
		//         R  L
		//第一个大于头节点值:5的位置是1，那么最后一个小于头节点值:5的位置是0
		//第一个小于头节点值:5的位置是1
		//根据搜索二叉树的规则，左子节点的值小于当前头节点的值
		//头节点的左子节点的范围：第一个小于头节点值:5的位置是1 ~ 最后一个小于头节点值:5的位置是0
		//也就是从1到0，L > R，表示左子节点 == null，也就是没有左子节点
		if (L > R) {
			return null;
		}
		//pre[]的第一个元素必然是搜索二叉树的头节点
		TreeNode head = new TreeNode(pre[L]);
		//找到第一个大于头节点值的位置
		int firstGreator = L + 1;
		for (; firstGreator <= R; firstGreator++) {
			//找到第一个大于头节点值的位置
			if (pre[firstGreator] > pre[L]) {
				break;
			}
		}
		//举例：
		//arr[] = {5,3,1,2,4,7,6,9,8}
		//index =  0 1 2 3 4 5 6 7 8
		//     5
		//   /   \
		//  3     7
		// / \   / \
		//1   4 6   9
		// \       /
		//  2     8
		//第一个大于头节点值:5的位置 -> 5
		//那么头节点的左子节点的范围从arr[]的1 ~ 4的索引位置
		//头节点的右子节点的范围从arr[]的5 ~ 8的索引位置
		//根据搜索二叉树的规则，左子节点的值小于当前头节点的值
		//头节点的左子节点 == 第一个小于头节点的值的位置 ~ 第一个大于头节点值的位置 - 1
		head.left = coreLogicWithRecursion(pre, L + 1, firstGreator - 1);
		//根据搜索二叉树的规则，右子节点的值大于当前头节点的值
		//头节点的右子节点 == 第一个大于头节点值的位置 ~ 最后的位置
		head.right = coreLogicWithRecursion(pre, firstGreator, R);
		return head;
	}

	/**
	 * 使用单调栈的方式实现
	 * @param pre
	 * @return
	 */
	public static TreeNode bstFromPreorderWithMonotoneStack(int[] pre) {
		if (pre == null || pre.length == 0) {
			return null;
		}
		int[] rightNearGreaterNoRepeat = getRightNearGreaterNoRepeatWithArray(pre);
		return coreLogicWithMonotoneStack(pre, 0, pre.length - 1, rightNearGreaterNoRepeat);
	}

	/**
	 *
	 * @param pre
	 * @param L
	 * @param R
	 * @param nearGreater
	 * @return
	 */
	public static TreeNode coreLogicWithMonotoneStack(int[] pre, int L, int R, int[] nearGreater) {
		if (L > R) {
			return null;
		}
		//nearGreater[L]表示右边离自己最近并且比自己的值大的元素的索引不存在，索引使用-1表示
		//nearGreater[L] > R表示右边离自己最近并且比自己的值大的元素的索引已经超出L ~ R的范围
		//R + 1表示索引无效时，表示第一个大于头节点的元素的位置也无效
		//nearGreater[L]表示索引有效
		int firstGreator = (nearGreater[L] == -1 || nearGreater[L] > R) ? R + 1 : nearGreater[L];
		TreeNode head = new TreeNode(pre[L]);
		head.left = coreLogicWithMonotoneStack(pre, L + 1, firstGreator - 1, nearGreater);
		head.right = coreLogicWithMonotoneStack(pre, firstGreator, R, nearGreater);
		return head;
	}

	/**
	 * 使用Stack实现单调栈MonotoneStack
	 * @param arr
	 * @return 返回原始数组的每个元素右边离自己最近并且比自己的值大的元素的索引组成的数组
	 */
	public static int[] getRightNearGreaterNoRepeatWithStack(int[] arr) {
		int[] nearGreater = new int[arr.length];
		Stack<Integer> stack = new Stack<>();
		for (int i = 0; i < arr.length; i++) {
			while (!stack.isEmpty() && arr[stack.peek()] < arr[i]) {
				int pop = stack.pop();
				nearGreater[pop] = i;
			}
			stack.push(i);
		}
		while (!stack.isEmpty()) {
			int pop = stack.pop();
			nearGreater[pop] = -1;
		}
		return nearGreater;
	}

	/**
	 * 使用Array实现单调栈MonotoneStack
	 * @param arr
	 * @return 返回原始数组的每个元素右边离自己最近并且比自己的值大的元素的索引组成的数组
	 */
	public static int[] getRightNearGreaterNoRepeatWithArray(int[] arr) {
		int[] nearGreater = new int[arr.length];

		int[] stack = new int[arr.length];
		int size = 0;
		for (int i = 0; i < arr.length; i++) {
			while (size != 0 && arr[stack[size - 1]] < arr[i]) {
				int pop = stack[--size];
				nearGreater[pop] = i;
			}
			stack[size++] = i;
		}
		while (size != 0) {
			int pop = stack[--size];
			nearGreater[pop] = -1;
		}
		return nearGreater;
	}

	/**
	 *
	 * @param arr
	 * @return 返回原始数组中每个元素的离自己最近并且比自己的值小的左右两边的位置数组组成的二维数组
	 * 举例：
	 * arr =  {4, 6, 3, 1, 8}
	 * index = 0  1  2  3  4
	 * ans = {
	 *     {-1, 2}表示0位置的元素左边离自己最近并且比自己的值小的元素的位置，没有就是-1，右边离自己最近并且比自己的值小的元素的位置2
	 *     { 0, 2}
	 *     {-1, 3}
	 *     {-1,-1}
	 *     { 3,-1}
	 * }
	 */
	public static int[][] getNearLessNoRepeat(int[] arr) {
		int[][] ans = new int[arr.length][2];
		//创建单调栈，栈中元素的值按照从小到大进行排序
		Stack<Integer> stack = new Stack<>();
		for (int i = 0; i < arr.length; i++) {
			//如果栈不为空，并且栈顶元素的值大于当前正在遍历的元素的值
			while (!stack.isEmpty() && arr[stack.peek()] > arr[i]) {
				//弹出栈顶元素
				int pop = stack.pop();
				//左边离自己最近并且比自己小的元素的索引
				//如果栈为空，表示当前正在遍历的元素左边没有离自己最近并且比自己的值小的元素，那么就使用-1表示
				//如果栈不为空，此时的栈顶元素就是左边离自己最近并且比自己的值小的元素
				int leftLessIndex = stack.isEmpty() ? -1 : stack.peek();
				//当前正在遍历的元素的左边离自己最近并且比自己的值小的元素的索引 == leftLessIndex
				ans[pop][0] = leftLessIndex;
				//谁让栈顶元素弹出的元素的索引，就是当前正在遍历的元素的右边离自己最近并且比自己的值小的元素的索引
				ans[pop][1] = i;
			}
			//如果栈为空，正在遍历的元素的索引直接入栈
			//如果栈不为空，并且新入栈的元素的值大于栈顶元素的值，新入栈的元素的索引直接入栈
			stack.push(i);
		}
		//for循环遍历完之后，栈中还有元素需要单独出栈
		//如果栈不为空
		while (!stack.isEmpty()) {
			int pop = stack.pop();
			int leftLessIndex = stack.isEmpty() ? -1 : stack.peek();

			ans[pop][0] = leftLessIndex;
			ans[pop][1] = -1;
		}
		return ans;
	}
}
