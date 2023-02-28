package com.mashibing.day06;

import java.util.Arrays;

/**
 * 题目三：
 * 给定一个非负整数组成的数组nums。另有一个查询数组queries，其中queries[i]=[xi, mi]
 * 第i个查询的答案是xi和任何nums数组中不超过mi的元素按位异或(XOR)得到的最大值
 * 换句话说，答案是max(nums[j] XOR xi)，其中所有j均满足nums[j] <= mi。
 * 如果nums中的所有元素都大于mi，最终答案就是-1
 * 返回一个整数数组answer作为查询的答案，其中answer.length == queries.length并且answer[i]是第i个查询的答案
 * <p>
 * Leetcode测试链接：
 * https://leetcode.com/problems/maximum-xor-with-an-element-from-array/
 *
 * @author xcy
 * @date 2022/7/21 - 8:55
 */
public class Code03_MaximumXorWithAnElementFromArray {
	public static void main(String[] args) {
		int[] nums = {0, 1, 2, 3, 4};
		int[][] queries = new int[][]{
				{3, 1},
				{1, 3},
				{5, 6}
		};

		int[] ints = maximizeXor(nums, queries);
		System.out.println(Arrays.toString(ints));
	}


	/**
	 * @param nums
	 * @param queries queries[i][j]，其中i表示x，j表示m
	 * @return
	 */
	public static int[] maximizeXor(int[] nums, int[][] queries) {
		// if (nums == null || queries == null || nums.length == 0 || queries.length == 0 || queries[0].length == 0) {
		// 	return null;
		// }
		NumTire numTire = new NumTire();
		for (int num : nums) {
			numTire.add(num);
		}
		int[] ans = new int[queries.length];
		int index = 0;
		for (int[] query : queries) {
			ans[index++] = numTire.maxXOR(query[0], query[1]);
		}
		return ans;
	}

	/**
	 * 前缀树的节点
	 */
	public static class Node {
		/**
		 * 节点路径下的最小值
		 */
		public int min;
		/**
		 * 节点的下一条路径
		 * nexts[0]表示0方向的路径
		 * nexts[0] == null表示0方向的路径没有，无法走通
		 * nexts[0] != null表示0方向的路径有，可以继续往下一条路径走
		 * nexts[1]表示1方向的路径
		 * nexts[1] == null表示1方向的路径没有，无法走通
		 * nexts[1] != null表示1方向的路径有，可以继续往下一条路径走
		 */
		public final Node[] nexts;

		public Node() {
			this.min = Integer.MAX_VALUE;
			this.nexts = new Node[2];
		}
	}

	/**
	 * 前缀树
	 */
	public static class NumTire {
		/**
		 * 头节点
		 */
		public final Node head = new Node();

		/**
		 * 根据传入的num创建对应的路径
		 * @param num
		 */
		public void add(int num) {
			Node cur = head;
			//根据传入的num及时更新head节点的最小值
			head.min = Math.min(head.min, num);
			//int类型32位，范围0 ~ 31
			for (int move = 31; move >= 0; move--) {
				//num的move位上的状态，不是0就是1
				int path = (num >> move) & 1;
				//如果下一个节点为空，则创建节点，否则就复用节点
				cur.nexts[path] = cur.nexts[path] == null ? new Node() : cur.nexts[path];
				//继续下一个节点
				cur = cur.nexts[path];
				//继续下一个节点的最小值更新
				cur.min = Math.min(cur.min, num);
			}
		}

		/**
		 * 在调用 该方法之前，已经在前缀树中添加过一批数据了
		 * @param num
		 * @param m
		 * @return 根据传入的num，找到num和该数值异或结果是最大的并且值不超过m的数
		 */
		public int maxXOR(int num, int m) {
			if (head.min > m) {
				return -1;
			}
			Node cur = head;
			int ans = 0;
			//int类型32位，范围0 ~ 31
			for (int move = 31; move >= 0; move--) {
				//num的move位上的状态，不是0就是1
				int path = (num >> move) & 1;
				//想要遇到的状态，根据最大的原则，除去符号位，其余的位数上都是1是最大的
				//所以符号位是希望遇到和自己一样，除去符号位，剩余的位数上是希望遇到和自己不一样的
				int best = move == 31 ? path : (path ^ 1);
				//实际上走的路径
				//如果下一个节点为空，或者下一个节点的路径上最小值超过了m，那么需要原路返回，异或1之后即可原路返回
				//否则，不需要原路返回，异或0即可
				best ^= (cur.nexts[best] == null || cur.nexts[best].min > m) ? 1 : 0;
				//将经过路径之后选择的最大值|进答案中
				ans |= (path ^ best) << move;
				//继续下一个节点的路径
				cur = cur.nexts[best];
			}
			return ans;
		}
	}
}

