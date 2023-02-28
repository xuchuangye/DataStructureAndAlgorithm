package com.mashibing.interviewquestions;


import com.mashibing.node.Node;
import com.mashibing.node.TreeNode;

import java.util.*;

/**
 * 二叉树面试题
 * <p>
 * 二叉树最宽的层有多少个节点
 *
 * 1、记录当前层结束，就能知道当前层的节点个数
 * 2、准备变量curEnd 表示当前层结束的节点，遍历当前层的最后一个节点
 * 3、nextEnd 表示下一层结束的节点，遍历下一层的最后一个节点
 * 4、curLevelNodes 当前层的节点个数
 * 5、max 记录最大的宽度
 *
 * @author xcy
 * @date 2022/4/10 - 16:59
 */
public class BinaryTreeMaxWidth {
	public static void main(String[] args) {
		int maxLevel = 10;
		int maxValue = 100;
		int testTimes = 1000000;
		for (int i = 0; i < testTimes; i++) {
			Node head = generateRandomBST(maxLevel, maxValue);
			if (maxWidthUseMap(head) != maxWidthNoMap(head)) {
				System.out.println("Oops!");
			}
		}
		System.out.println("finish!");
	}

	public static int maxWidthUseMap(Node root) {
		if (root == null) {
			return 0;
		}
		Queue<Node> queue = new LinkedList<>();
		queue.add(root);
		// levelMap记录每一个节点在哪一层，key表示节点，value表示节点所在的层数
		HashMap<Node, Integer> levelMap = new HashMap<>();
		//根节点在第一层
		levelMap.put(root, 1);
		int curLevel = 1; // 当前正在统计哪一层的宽度
		int curLevelNodes = 0; // 当前层curLevel层，宽度目前是多少
		int max = 0;
		while (!queue.isEmpty()) {
			Node cur = queue.poll();
			int curNodeLevel = levelMap.get(cur);
			if (cur.getLeft() != null) {
				levelMap.put(cur.getLeft(), curNodeLevel + 1);
				queue.add(cur.getLeft());
			}
			if (cur.getRight() != null) {
				levelMap.put(cur.getRight(), curNodeLevel + 1);
				queue.add(cur.getRight());
			}
			if (curNodeLevel == curLevel) {
				curLevelNodes++;
			} else {
				max = Math.max(max, curLevelNodes);
				curLevel++;
				curLevelNodes = 1;
			}
		}
		max = Math.max(max, curLevelNodes);
		return max;
	}

	/**
	 * 不使用Map记录二叉树宽度最宽的节点个数
	 * @param root 二叉树的根节点
	 * @return 返回二叉树宽度最宽的节点个数
	 */
	public static int maxWidthNoMap(Node root) {
		if (root == null) {
			return 0;
		}
		//队列
		Queue<Node> queue = new LinkedList<>();
		//首先将二叉树的根节点添加进去
		queue.add(root);
		//当前层结束的节点，最右节点是谁
		//首先根节点这一层只有根节点自己
		Node curEnd = root;
		//下一层结束的节点，最右节点是谁
		Node nextEnd = null;
		//最大宽度的节点个数
		int max = 0;
		//当前层的节点个数
		int curLevelNodes = 0;
		while (!queue.isEmpty()) {
			//取出根节点或者当前节点
			Node cur = queue.poll();
			//判断是否有左子节点
			if (cur.getLeft() != null) {
				queue.add(cur.getLeft());
				//为下一层遍历的节点开始做准备
				nextEnd = cur.getLeft();
			}
			//判断是否有右子节点
			if (cur.getRight() != null) {
				queue.add(cur.getRight());
				//为下一层遍历的节点开始做准备
				nextEnd = cur.getRight();
			}
			//每次从队列中弹出一个节点，curLevelNodes++
			curLevelNodes++;
			//如果当前节点已经是该层的最后一个节点，表示当前层已经遍历结束
			if (cur == curEnd) {
				//遍历结束就需要将当前层数的节点个数和max比较并获取最大值赋值给max
				max = Math.max(max, curLevelNodes);
				//当前层数的节点个数重新置0
				curLevelNodes = 0;
				//当前层的节点来到下一层的节点
				curEnd = nextEnd;
			}
		}
		return max;
	}
	// for test
	public static Node generateRandomBST(int maxLevel, int maxValue) {
		return generate(1, maxLevel, maxValue);
	}

	// for test
	public static Node generate(int level, int maxLevel, int maxValue) {
		if (level > maxLevel || Math.random() < 0.5) {
			return null;
		}
		Node head = new Node((int) (Math.random() * maxValue));
		head.setLeft(generate(level + 1, maxLevel, maxValue));
		head.setRight(generate(level + 1, maxLevel, maxValue));
		return head;
	}
}
