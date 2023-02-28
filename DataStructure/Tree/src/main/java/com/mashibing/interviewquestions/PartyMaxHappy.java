package com.mashibing.interviewquestions;

import com.mashibing.common.BinaryTreeUtils;
import com.mashibing.pojo.Employee;

/**
 * 二叉树的面试题
 * 排队的最大快乐值
 *
 * 基本原则：
 * 1、发请柬的员工来聚会，没有发请柬的员工不会来，也就是可以选择节点
 * 2、直接上下级的员工不要邀请
 * 3、来聚会的所有员工不违反上述两个条件，计算出出所有员工的快乐值的累加和
 *
 * 基本思路：
 * 1、当前节点x所在的员工来的情况下，快乐值的累加和为：
 * x自己的快乐值 + 直接子节点所在的树(直属员工不来)的最大快乐值累加和
 * 2、当前节点x所在的员工不来的情况
 * 直接子节点所在的树(直属员工来)的最大快乐值累加和
 *
 * @author xcy
 * @date 2022/5/1 - 9:55
 */
public class PartyMaxHappy {
	public static void main(String[] args) {
		int maxLevel = 4;
		int maxNexts = 7;
		int maxHappy = 100;
		int testTimes = 100000;
		System.out.println("测试开始!");
		for (int i = 0; i < testTimes; i++) {
			Employee boss = BinaryTreeUtils.genarateBoss(maxLevel, maxNexts, maxHappy);
			if (maxEmployeeHappySum(boss) != maxEmployeeHappyValue(boss)) {
				System.err.println("测试错误!");
			}
		}
		System.out.println("测试结束!");
	}


	/**
	 * 选择或者不选择当前节点（当前员工来或者不来）的信息封装类
	 */
	public static class Info {
		public int no;
		public int yes;

		public Info(int no, int yes) {
			this.no = no;
			this.yes = yes;
		}
	}
	/**
	 * 获取所有来参加聚会的员工的快乐值累加和
	 * @param root 多叉树的根节点，也就是公司的老板
	 * @return 返回所有来参加聚会的员工的快乐值累加和
	 */
	public static int maxEmployeeHappySum(Employee root) {
		if (root == null) {
			return 0;
		}
		Info info = process(root);
		return Math.max(info.no, info.yes);
	}

	/**
	 * 核心逻辑
	 * @param x 表示多叉树的节点
	 * @return 返回上级来或者不来的信息封装类
	 */
	public static Info process(Employee x) {
		//如果
		if (x == null) {
			return null;
		}
		//如果老板不来的最大快乐值累加和
		int no = 0;
		//如果老板来的最大快乐值累加和
		//首先老板来，肯定要加上自己的快乐值
		int yes = x.happy;

		for (Employee next : x.nexts) {
			//所有直属下级的信息
			Info nextInfo = process(next);
			//如果老板来，那么就加上所有直属下级不来的快乐值累加和
			yes += nextInfo.no;
			//如果老板不来，那么就选择所有直属下级来或者不来的最大快乐值累加和
			no += Math.max(nextInfo.no, nextInfo.yes);
		}
		return new Info(no, yes);
	}

	public static int maxEmployeeHappyValue(Employee boss) {
		if (boss == null) {
			return 0;
		}
		return process1(boss, false);
	}

	// 当前来到的节点叫cur，
	// up表示cur的上级是否来，
	// 该函数含义：
	// 如果up为true，表示在cur上级已经确定来，的情况下，cur整棵树能够提供最大的快乐值是多少？
	// 如果up为false，表示在cur上级已经确定不来，的情况下，cur整棵树能够提供最大的快乐值是多少？
	public static int process1(Employee cur, boolean up) {
		if (up) { // 如果cur的上级来的话，cur没得选，只能不来
			int ans = 0;
			for (Employee next : cur.nexts) {
				ans += process1(next, false);
			}
			return ans;
		} else { // 如果cur的上级不来的话，cur可以选，可以来也可以不来
			int p1 = cur.happy;
			int p2 = 0;
			for (Employee next : cur.nexts) {
				p1 += process1(next, true);
				p2 += process1(next, false);
			}
			return Math.max(p1, p2);
		}
	}
}
