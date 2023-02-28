package com.mashibing.test;

import com.mashibing.node.Node;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author xcy
 * @date 2022/4/29 - 8:34
 */
public class BinaryTreeMaxWidth {
	public static void main(String[] args) {

	}

	public static int maxWidthNoMap(Node root) {
		if (root == null) {
			return 0;
		}
		Queue<Node> queue = new LinkedList<Node>();
		queue.add(root);

		Node curEnd = root;
		Node nextEnd = null;
		int max = 0;
		int curLevelNodes = 0;

		while (!queue.isEmpty()) {
			Node cur = queue.poll();
			if (cur.getLeft() != null) {
				queue.add(cur.getLeft());
				nextEnd = cur.getLeft();
			}
			if (cur.getRight() != null) {
				queue.add(cur.getRight());
				nextEnd = cur.getRight();
			}
			curLevelNodes++;

			if (cur == curEnd) {
				max = Math.max(max, curLevelNodes);
				curLevelNodes = 0;
				curEnd = nextEnd;
			}
		}

		return max;
	}
}
