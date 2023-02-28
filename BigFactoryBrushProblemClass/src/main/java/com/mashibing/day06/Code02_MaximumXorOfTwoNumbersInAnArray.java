package com.mashibing.day06;

/**
 * 题目二：
 * 数组中所有数都异或起来的结果，叫做异或和。
 * 给定一个数组arr，想知道arr中哪两个数的异或结果最大，返回最大的异或结果
 * <p>
 * Leetcode测试链接：
 * https://leetcode.com/problems/maximum-xor-of-two-numbers-in-an-array/
 *
 * @author xcy
 * @date 2022/7/21 - 8:54
 */
public class Code02_MaximumXorOfTwoNumbersInAnArray {
	public static void main(String[] args) {

	}

	/**
	 * 前缀树的节点
	 */
	public static class Node {
		/**
		 * nexts[]表示0方向的路和1方向的路
		 * nexts[0]表示0方向的路
		 * nexts[1]表示1方向的路
		 * nexts[0] == null表示0方向的路没有办法走
		 * nexts[0] != null表示0方向的路可以继续往下一个方向走
		 * nexts[1] == null表示1方向的路没有办法走
		 * nexts[1] != null表示1方向的路可以继续往下一个方向走
		 */
		public Node[] nexts = new Node[2];
	}

	/**
	 * 前缀树
	 */
	public static class NumTrie {
		/**
		 * 头节点
		 */
		Node head = new Node();

		/**
		 * 添加方法
		 * 时间复杂度：O(1)
		 *
		 * @param num
		 */
		public void add(int num) {
			Node cur = head;
			//int类型32位，范围是0 ~ 31
			for (int move = 31; move >= 0; move--) {
				//num的move位上的状态，不是0就是1
				int path = (num >> move) & 1;
				//下一个方向，如果没有就创建，如果有，就使用
				cur.nexts[path] = cur.nexts[path] == null ? new Node() : cur.nexts[path];
				//继续下一个方向
				cur = cur.nexts[path];
			}
		}

		/**
		 * 根据传入的num选择异或之后的最大值
		 * 时间复杂度：O(1)
		 *
		 * @param num
		 * @return
		 */
		public int maxXOR(int num) {
			int ans = 0;
			Node cur = head;
			//int类型32位，范围0 ~ 31
			for (int move = 31; move >= 0; move--) {
				//传入的num的move位上的状态，不是0就是1
				int path = (num >> move) & 1;
				//想要遇到的状态
				//因为根据最大的原则，除去符号位，其余的都是1会比较大
				//所以符号位希望遇到和自己一样的状态，1希望遇到1，0希望遇到0，异或之后都是0，表示该数值是非负数
				//除去符号位，希望遇到和自己不一样的状态，1希望遇到0，0希望遇到1，异或之后还是1,
				int best = move == 31 ? path : (path ^ 1);
				//实际上可以遇到的状态
				//如果有则更好，如果没有只能被迫选择
				best = cur.nexts[best] != null ? best : (best ^ 1);
				//将已经选择的最大的结果标记到最终答案里
				ans |= (path ^ best) << move;
				//继续下一个
				cur = cur.nexts[best];
			}
			return ans;
		}
	}

	public static int findMaximumXOR(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		int max = Integer.MIN_VALUE;
		NumTrie numTrie = new NumTrie();
		for (int element : arr) {
			numTrie.add(element);
			int xor = numTrie.maxXOR(element);
			max = Math.max(max, xor);
		}
		return max;
	}
}
