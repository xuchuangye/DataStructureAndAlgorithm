package com.mashibing.day07;

import javax.xml.crypto.Data;

/**
 * 题目二：
 * 相机最小覆盖问题：
 * 给定一个二叉树，我们在树的节点上安装摄像头，节点上的每个摄影头都可以监视其父对象、自身及其直接子对象，
 * 计算监控树的所有节点所需的最小摄像头数量
 * <p>
 * 解题思路：
 * 使用二叉树的递归套路
 * <p>
 * LeetCode测试链接：
 * https://leetcode.com/problems/binary-tree-cameras/
 *
 * @author xcy
 * @date 2022/7/23 - 7:51
 */
public class Code02_MinCameraCover {
	public static void main(String[] args) {

	}

	/**
	 * 二叉树的节点
	 */
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
	 * 信息封装类
	 * 前提：以x为头节点，并且以x为头节点的整棵二叉树的所有子节点都被覆盖的情况下
	 */
	public static class Info {
		/**
		 * 以x为头节点，x节点位置没有相机，x没有被覆盖，以x为头节点的整棵二叉树的所有子节点都被覆盖至少需要几台相机
		 */
		public long uncovered;
		/**
		 * 以x为头节点，x节点位置没有相机，x节点被覆盖，以x为头节点的整棵二叉树的所有子节点都被覆盖至少需要几台相机
		 */
		public long coveredNoCamera;
		/**
		 * 以x为头节点，x节点位置有相机，x节点被覆盖，以x为头节点的整棵二叉树的所有子节点都被覆盖至少需要几台相机
		 */
		public long coveredHasCamera;

		public Info(long uncovered, long coveredNoCamera, long coveredHasCamera) {
			this.uncovered = uncovered;
			this.coveredNoCamera = coveredNoCamera;
			this.coveredHasCamera = coveredHasCamera;
		}
	}

	/**
	 * 时间复杂度：O(N)
	 *
	 * @param head
	 * @return 返回以head为头节点，并且以head为头节点的整棵二叉树的所有节点都被覆盖，至少需要相机的数量
	 */
	public static int minCameraCover1(TreeNode head) {
		Info data = process(head);
		return (int) Math.min(data.uncovered + 1, Math.min(data.coveredNoCamera, data.coveredHasCamera));
	}

	/**
	 * @param head
	 * @return
	 */
	public static Info process(TreeNode head) {
		if (head == null) {
			return new Info(Integer.MAX_VALUE, 0, Integer.MAX_VALUE);
		}
		//左子树的信息
		Info leftInfo = process(head.left);
		//右子树的信息
		Info rightInfo = process(head.right);

		//当前节点没有被覆盖，并且没有相机的情况下，以当前节点为头节点的整棵二叉树的所有子节点都被覆盖至少需要的相机个数
		//1.不可能出现左边子节点被覆盖但是有相机，或者右边子节点被覆盖但是有相机的情况，因为如果左子节点或者右子节点有相机，那么当前父节点一定会被覆盖
		//2.不可能出现左边子节点没有被覆盖，右边子节点也没有被覆盖的情况
		//3.所以只有可能出现左边子节点被覆盖但没有相机，或者右边子节点被覆盖但没有相机的情况
		long uncovered = leftInfo.coveredNoCamera + rightInfo.coveredNoCamera;

		//当前节点被覆盖，但是没有相机的情况下，以当前节点为头节点的整棵二叉树的所有子节点都被覆盖至少需要的相机个数
		long coveredNoCamera = Math.min(
				//左边被覆盖有相机，并且右边被覆盖有相机的情况下
				leftInfo.coveredHasCamera + rightInfo.coveredHasCamera,
				Math.min(
						//左边被覆盖没有相机，并且右边被覆盖有相机的情况下
						leftInfo.coveredNoCamera + rightInfo.coveredHasCamera,
						//左边被覆盖有相机，并且右边被覆盖没有相机的情况下
						leftInfo.coveredHasCamera + rightInfo.coveredNoCamera
				)
		);

		//当前节点被覆盖，有相机的情况下，以当前节点为头节点的整棵二叉树的所有子节点都被覆盖至少需要的相机个数
		//当前节点有相机，那么左子节点和 右子节点有没有被覆盖，有没有相机都不重要，哪种情况下能够覆盖整棵树的相机数量最少就行
		long coveredHasCamera = Math.min(leftInfo.uncovered, Math.min(leftInfo.coveredNoCamera, leftInfo.coveredHasCamera))
				+
				Math.min(rightInfo.uncovered, Math.min(rightInfo.coveredNoCamera, rightInfo.coveredHasCamera))
				+
				//最后当前节点需要1台相机
				1;
		return new Info(uncovered, coveredNoCamera, coveredHasCamera);
	}

	/**
	 * 以x为头节点，并且以x为头节点的整棵二叉树的所有子节点都被覆盖的情况下，每个节点的三种状态
	 */
	public static enum Status {
		/**
		 * 没有被覆盖
		 */
		UNCOVERED,
		/**
		 * 被覆盖但是没有相机
		 */
		COVERED_NO_CAMERA,
		/**
		 * 被覆盖但是有相机
		 */
		COVERED_HAS_CAMERA
	}

	/**
	 * 以x为头节点，并且以x为头节点的整棵二叉树的所有子节点都被覆盖的情况下，得到的最优解
	 */
	public static class Data {
		/**
		 * x节点的状态
		 */
		public Status status;
		/**
		 * 在这种状态下，至少需要多少个相机
		 */
		public int cameras;

		public Data(Status status, int cameras) {
			this.status = status;
			this.cameras = cameras;
		}
	}

	/**
	 * 使用贪心算法
	 * <p>
	 * 贪心算法的时间复杂度：O(N)
	 * <p>
	 * 贪心算法的思路：
	 * 假设有一个叶子节点，那么叶子节点的左右子节点都只有一种状态，那就是被覆盖但是没有相机的状态，使用Status.COVERED_NO_CAMERA表示
	 * 假设在该叶子节点上放相机，那么只能覆盖叶子节点的父节点以及叶子节点自己，只能覆盖2个
	 * 如果在叶子节点的父节点上放相机，则能够覆盖叶子节点的父节点的父节点，以及叶子节点的父节点，以及叶子节点的兄弟节点和叶子节点自己，能够覆盖4个
	 * 所以根据这个贪心思路，使用了该方法
	 *
	 * @param root
	 * @return
	 */
	public static int minCameraCover2(TreeNode root) {
		Data data = coreLogic(root);
		return data.cameras + (data.status == Status.UNCOVERED ? 1 : 0);
	}

	public static Data coreLogic(TreeNode node) {
		//如果当前节点为空
		if (node == null) {
			//将当前节点被覆盖并且没有相机的状态以及相机个数的信息封装到Data类并返回
			//返回Data信息封装类：
			//信息状态node.status == Status.COVERED_NO_CAMERA：被覆盖但是没有相机
			//相机个数：0
			return new Data(Status.COVERED_NO_CAMERA, 0);
		}
		//左边(当前节点的左子树)收集的信息
		Data leftData = coreLogic(node.left);
		//右边(当前节点的右子树)收集的信息
		Data rightData = coreLogic(node.right);

		//当前节点的相机个数 = 左子树收集的相机个数 + 右子树收集的相机个数
		int cameras = leftData.cameras + rightData.cameras;

		//如果左子节点没有被覆盖或者右子节点没有被覆盖，那么当前节点必须要有相机
		if (leftData.status == Status.UNCOVERED || rightData.status == Status.UNCOVERED) {
			//所以将当前节点被覆盖并且有相机的状态以及相机个数 + 1的信息封装到Data类并返回
			return new Data(Status.COVERED_HAS_CAMERA, cameras + 1);
		}
		//经过上述判断，左子节点已经被覆盖，右子节点也已经被覆盖
		//如果左子节点被覆盖并且有相机或者右子节点被覆盖并且有相机，那么当前节点只需要被覆盖就行，不需要在当前节点上添加相机了
		if (leftData.status == Status.COVERED_HAS_CAMERA || rightData.status == Status.COVERED_HAS_CAMERA) {
			//所以将当前节点被覆盖并且没有相机的状态以及相机个数的信息封装到Data类并返回
			return new Data(Status.COVERED_NO_CAMERA, cameras);
		}

		//经过上述判断，左子节点被覆盖并且有相机，右子节点被覆盖并且有相机
		//所以在当前节点即使是没有被覆盖的情况下也不需要放相机，让当前节点的父节点去考虑去吧，所以相机个数不变
		//所以将当前节点没有被覆盖并且也没有相机的状态以及相机个数的信息封装到Data类并返回
		return new Data(Status.UNCOVERED, cameras);
	}
}
