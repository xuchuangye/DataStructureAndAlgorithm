package com.mashibing.day06;

import com.mashibing.common.TestUtils;

/**
 * 题目一：
 * 数组中所有数都异或起来的结果，叫做异或和。
 * 给定一个数组arr，返回arr的最大子数组异或和
 *
 * @author xcy
 * @date 2022/7/21 - 8:53
 */
public class Code01_MaxXOR {
	public static void main(String[] args) {
		//System.out.println(3 ^ 1 ^ 2);

		int testTime = 500000;
		int maxSize = 30;
		int maxValue = 50;
		boolean succeed = true;
		System.out.println("测试开始！");
		for (int i = 0; i < testTime; i++) {
			int[] arr = TestUtils.generateRandomArray(maxSize, maxValue);
			int comp = maxXORSubarray1(arr);
			int res = maxXORSubarray2(arr);
			if (res != comp) {
				succeed = false;
				TestUtils.printArray(arr);
				System.out.println(res);
				System.out.println(comp);
				break;
			}
		}
		System.out.println("测试结束！");
		System.out.println(succeed ? "Nice!" : "Fucking fucked!");
	}

	// O(N^2)
	public static int maxXORSubarray1(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		// 准备一个前缀异或和数组eor
		// eor[i] = arr[0...i]的异或结果
		int[] eor = new int[arr.length];
		eor[0] = arr[0];
		// 生成eor数组，eor[i]代表arr[0..i]的异或和
		for (int i = 1; i < arr.length; i++) {
			eor[i] = eor[i - 1] ^ arr[i];
		}
		int max = Integer.MIN_VALUE;
		for (int j = 0; j < arr.length; j++) {
			for (int i = 0; i <= j; i++) { // 依次尝试arr[0..j]、arr[1..j]..arr[i..j]..arr[j..j]
				max = Math.max(max, i == 0 ? eor[j] : eor[j] ^ eor[i - 1]);
			}
		}
		return max;
	}

	/**
	 * 前缀树节点
	 */
	public static class Node {
		/**
		 * 使用数组表示前缀树的路：长度为2
		 * nexts[0]表示0方向的路
		 * nexts[1]表示1方向的路
		 * nexts[0] == null表示没有0方向的路，nexts[0] != null表示有0方向的路
		 * nexts[1] == null表示没有1方向的路，nexts[1] != null表示有1方向的路
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
		public Node head = new Node();

		/**
		 * 根据传入的number值创建新的路径
		 *
		 * @param newNum
		 */
		public void add(int newNum) {
			Node cur = head;
			//int类型一共32位，所以范围是0 ~ 31
			for (int move = 31; move >= 0; move--) {
				//求出传入的number值的move位上的状态是0还是1
				//状态是0创建0方向的路，状态是1创建1方向的路
				int path = (newNum >> move) & 1;
				cur.nexts[path] = cur.nexts[path] == null ? new Node() : cur.nexts[path];
				//继续下一条路
				cur = cur.nexts[path];
			}
		}

		/**
		 * 贪心策略：
		 * int类型只有31位上的值是希望遇到跟自己一样的值，其余之后的位数上是希望遇到跟自己不一样的值
		 * 因为31位是符号位，符号位肯定是0最好，最起码异或之后是非负数
		 * 所以如果31位上是1，希望遇到的也是1，如果31位上是0，希望遇到的也是0
		 * <p>
		 * 调用该方法之前，前缀树已经收集过number，并建立好了前缀树结构
		 *
		 * @param number
		 * @return 根据传入的number返回number和谁异或结果最大的
		 */
		public int maxXOR(int number) {
			Node cur = head;
			int ans = 0;
			//int类型一共32位，所以范围是0 ~ 31
			for (int move = 31; move >= 0; move--) {
				//求出传入的number值的move位上的状态是0还是1
				//状态是0创建0方向的路，状态是1创建1方向的路
				int path = (number >> move) & 1;
				//根据异或结果最大的原则，除去符号位，肯定都是1比较好
				//所以只有符合位是希望遇到和自己一样的值，1希望遇到1，异或之后是0，符合位为0表示非负数
				//0希望遇到0，异或之后还是0，符合位为0表示非负数
				//剩下的非符号位希望遇到和自己不一样的值，非符号位肯定都是1最好
				//所以1希望遇到0，异或之后是1，0希望遇到1，异或之后还是1
				//path表示number的move位的状态，不是0就是1
				//所以符号位，也就是第31位还是path，除去符号位，其它位都是相反的，也就是path ^ 1

				//希望遇到的路径
				int best = move == 31 ? path : (path ^ 1);
				//实际走的路径
				//如果没有可以走的路，那么只能被迫走另一条路
				best = cur.nexts[best] != null ? best : (best ^ 1);

				//能选择最好的就选择最好的，然后再根据实际情况，能实际走的就选择实际走的
				//将结果或进答案中

				//path ^ best表示当前位上异或的结果
				ans |= (path ^ best) << move;

				//TODO：这里为什么不是path，而是best
				cur = cur.nexts[best];
			}
			return ans;
		}
	}

	/**
	 * 时间复杂度：O(N)
	 * @param arr
	 * @return
	 */
	public static int maxXORSubarray2(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		int max = Integer.MIN_VALUE;
		//0 ~ i的整体异或和
		int xor = 0;
		NumTrie numTrie = new NumTrie();
		numTrie.add(0);
		for (int element : arr) {
			xor ^= element;
			max = Math.max(max, numTrie.maxXOR(xor));
			numTrie.add(xor);
		}
		return max;
	}
}
