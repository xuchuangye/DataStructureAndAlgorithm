package com.mashibing.day05;

/**
 * 题目二：
 * 如果一个节点X，它左树结构和右树结构完全一样，那么我们说以X为头的树是相等树，
 * 给定一棵二叉树的头节点head，返回head整棵树上有多少棵相等子树
 * <p>
 * 解题思路：
 * 1.二叉树的递归套路
 * 以x为头节点的时候，如何收集答案
 * 分析出左子树收集的答案和右子树收集的答案
 * 以及迅速搞定左子树的结构信息和右子树的结构信息是否相等
 * 如果不优化，需要使用两个递归
 * 通过二叉树的递归套路，可能性使用简单性信息就能表示，
 * 进而想到左子树去序列化和右子树去序列化，但依然不能称之为简单性信息
 * 因为序列化的字符串和题目给定的数据规模还是一样，属于正比关系
 * 进而想到使用哈希函数，让非常长的字符串变成固定长度
 * 2.哈希函数计算两棵子树序列化之后的字符串
 *
 * @author xcy
 * @date 2022/7/18 - 16:22
 */
public class Code02_LeftRightSameTreeNumber {
	public static void main(String[] args) {
		TreeNode node3 = new TreeNode(4);
		TreeNode node4 = new TreeNode(5);
		TreeNode node5 = new TreeNode(6);
		TreeNode node6 = new TreeNode(7);
		TreeNode node1 = new TreeNode(2, node3, null);
		TreeNode node2 = new TreeNode(3, node5, node6);
		TreeNode head = new TreeNode(1, node1, node2);

		String serializable = serialize(head);
		System.out.println(serializable);
	}

	public static class TreeNode {
		public int val;
		public TreeNode left;
		public TreeNode right;

		public TreeNode() {

		}

		public TreeNode(int val) {
			this.val = val;
		}

		public TreeNode(int val, TreeNode left, TreeNode right) {
			this.val = val;
			this.left = left;
			this.right = right;
		}
	}

	/**
	 * 使用暴力递归的方式
	 * <p>
	 * 时间复杂度：O(N * logN)
	 * <p>
	 * Master公式计算过程：
	 * T(N)总的数据规模 + 两棵左右子树的遍历的数据规模2 * T(N / 2) + 两棵子树对比的时间复杂度O(N)
	 * <p>
	 * Master公式：T(N) = a * T(N / b) + O (N ^ d次方) (a,b,d都是常数)
	 * log以b为底a为真数 == b，时间复杂度：O(N * logN)
	 * <p>
	 * 最终计算的时间复杂度： O(N * logN)
	 *
	 * @param head
	 * @return
	 */
	public static int returnBinaryTreeEqualSubTree1(TreeNode head) {
		if (head == null) {
			return 0;
		}
		if (head.left == null && head.right == null) {
			return 1;
		}

		return equalSubTree(head);
	}

	/**
	 * @param node
	 * @return 返回以head为头节点的二叉树中有多少棵相等子树
	 */
	public static int equalSubTree(TreeNode node) {
		if (node == null) {
			return 0;
		}
		//左子节点相等子树
		int leftEqualSubTree = equalSubTree(node.left);
		//右子节点相等子树
		int rightEqualSubTree = equalSubTree(node.right);
		return leftEqualSubTree + rightEqualSubTree + (twoSubtreeIsEqual(node.left, node.right) ? 1 : 0);
	}

	/**
	 * @param node1
	 * @param node2
	 * @return 返回两棵子树是否相等
	 */
	public static boolean twoSubtreeIsEqual(TreeNode node1, TreeNode node2) {
		if (node1 == null || node2 == null) {
			return false;
		}
		return node1.val == node2.val && twoSubtreeIsEqual(node1.left, node2.left) && twoSubtreeIsEqual(node1.right, node2.right);
	}

	/**
	 * 按照二叉树的先序遍历将二叉树进行序列化
	 * @param node
	 * @return
	 */
	public static String serialize(TreeNode node) {
		StringBuilder stringBuilder = new StringBuilder("");
		if (node == null) {
			return "#";
		}

		stringBuilder.append(node.val);

		if (node.left != null) {
			stringBuilder.append(serialize(node.left));
		}else {
			stringBuilder.append("#");
		}

		if (node.right != null) {
			stringBuilder.append(serialize(node.right));
		}else {
			stringBuilder.append("#");
		}

		return stringBuilder.toString();
	}

	/**
	 * Hash函数计算的信息封装类
	 */
	public static class Info {
		/**
		 * 两棵子树中相等子树的个数
		 */
		public int ans;
		/**
		 * Hash函数计算的哈希值字符串
		 */
		public String string;

		public Info(int ans, String string) {
			this.ans = ans;
			this.string = string;
		}
	}
	/**
	 * 使用哈希函数计算并比较两棵子树序列化的哈希值
	 * @param head
	 * @return
	 */
	public static int returnBinaryTreeEqualSubTree2(TreeNode head) {
		if (head == null) {
			return 0;
		}
		if (head.left == null && head.right == null) {
			return 1;
		}
		String string = "SHA-256";
		Hash hash = new Hash(string);
		return process(head, hash).ans;
	}

	public static Info process(TreeNode head, Hash hash) {
		if (head == null) {
			return new Info(0, hash.hashCode("#,"));
		}
		//左边收集信息
		Info left = process(head.left, hash);
		//右边收集信息
		Info right = process(head.right, hash);
		//当前节点下，相等子树的个数 = 如果左子节点的哈希值字符串和右子节点的哈希值字符串相等时， + 1，否则 + 0
		//+ 左子节点下的相等子树的个数 + 右子节点下的相等子树的个数
		int ans = (left.string.equals(right.string) ? 1 : 0) + left.ans + right.ans;
		//当前节点下，哈希函数计算当前节点的哈希值字符串 + "," + 左子节点的哈希值字符串 + 右子节点的哈希值字符串
		String string = hash.hashCode(String.valueOf(head.val)) + "," + left.string + right.string;
		return new Info(ans, string);
	}


	/**
	 * 按照二叉树的先序遍历将二叉树进行反序列化
	 * @param serialize
	 * @return
	 */
	/*public static TreeNode deserialization(String serialize) {
		if (serialize == null || "#".equals(serialize)) {
			return null;
		}
		char[] str = serialize.toCharArray();
		TreeNode head = new TreeNode(str[0] - 'a');

	}*/

}
