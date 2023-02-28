package com.mashibing.day14;

import java.util.Scanner;

/**
 * 从二叉树的某个节点x开始，往下子节点都要的，叫子树
 * 在二叉树上只要能连起来的任何结构，叫子拓扑结构；
 * 返回二叉树上满足搜索二叉树性质的、最大子拓扑结构的节点数
 *
 * 本题测试链接 : https://www.nowcoder.com/practice/e13bceaca5b14860b83cbcc4912c5d4a
 *
 * @author xcy
 * @date 2022/8/2 - 17:00
 */
public class Code03_BiggestBSTTopologyInTree {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		int h = sc.nextInt();
		int[][] tree = new int[n + 1][3];
		for (int i = 1; i <= n; i++) {
			int c = sc.nextInt();
			int l = sc.nextInt();
			int r = sc.nextInt();
			tree[l][0] = c;
			tree[r][0] = c;
			tree[c][1] = l;
			tree[c][2] = r;
		}
		System.out.println(maxBSTTopology(h, tree, new int[n + 1]));
		sc.close();
	}

	// h: 代表当前的头节点
	// t: 代表树 t[i][0]是i节点的父节点、t[i][1]是i节点的左孩子、t[i][2]是i节点的右孩子
	// m: i节点为头的最大bst拓扑结构大小 -> m[i]
	// 返回: 以h为头的整棵树上，最大bst拓扑结构的大小
	public static int maxBSTTopology(int h, int[][] t, int[] m) {
		if (h == 0) {
			return 0;
		}
		int l = t[h][1];
		int r = t[h][2];
		int c = 0;
		int p1 = maxBSTTopology(l, t, m);
		int p2 = maxBSTTopology(r, t, m);
		while (l < h && m[l] != 0) {
			l = t[l][2];
		}
		if (m[l] != 0) {
			c = m[l];
			while (l != h) {
				m[l] -= c;
				l = t[l][0];
			}
		}
		while (r > h && m[r] != 0) {
			r = t[r][1];
		}
		if (m[r] != 0) {
			c = m[r];
			while (r != h) {
				m[r] -= c;
				r = t[r][0];
			}
		}
		m[h] = m[t[h][1]] + m[t[h][2]] + 1;
		return Math.max(Math.max(p1, p2), m[h]);
	}
}
