package com.mashibing.day03;

import java.util.*;

/**
 * 题目八：
 * 给定三个参数，二叉树的头节点head，树上某个节点target，正数K。
 * 从target开始，可以向上走或者向下走，返回与target的距离是K的所有节点
 * <p>
 * 举例：
 * -       1
 * -     /  \
 * -   2     3
 * - /  \     \
 * -4    5     6
 * -   /  \
 * -  7    8
 * 给定二叉树的头节点head = 1，target节点 = 2，K = 2
 * 因为可以向上走或者向下走，所以返回所有与节点2距离为2的节点：7，8，3
 * <p>
 * 解题思路：
 * 1.给定参数head节点，是因为需要往上查看，否则只给定一个target节点，只能看到target节点以下的节点，只能往下查看
 * 2.准备一个parents的Map，表示所有节点的父节点的表，比如，1节点的父节点为null，2节点的父节点为1,3节点的父节点为1
 * 父节点的表能够让节点往上走
 *
 * @author xcy
 * @date 2022/7/13 - 8:35
 */
public class Code08_DistanceKNodes {
	public static void main(String[] args) {

	}

	/**
	 * 二叉树节点类
	 */
	public static class Node {
		public int value;
		public Node left;
		public Node right;

		public Node(int value) {
			this.value = value;
		}
	}

	/**
	 * @param head   二叉树的根节点
	 * @param target 给定的目标节点
	 * @param K      目标节点跟其他节点的距离
	 * @return
	 */
	public static List<Node> distanceKNodes(Node head, Node target, int K) {
		List<Node> ans = new ArrayList<>();
		if (head == null || target == null || K < 0) {
			return ans;
		}
		if (K == 0) {
			ans.add(target);
			return ans;
		}
		//所有节点的父节点的记录表，因为需要往上走
		HashMap<Node, Node> parents = new HashMap<>();
		parents.put(head, null);
		//从根节点开始，向整棵树递归，记录所有节点的父节点
		createParentsHashMap(head, parents);

		//二叉树的宽度优先遍历需要准备队列
		Queue<Node> queue = new LinkedList<>();
		//使用Set标记节点是否已经被访问过
		HashSet<Node> visited = new HashSet<>();

		//将根节点添加到队列中，从target节点开始找
		queue.offer(target);
		//将根节点标记已访问过，从target节点开始标记
		visited.add(target);
		//当前层数
		int curLevel = 0;

		while (!queue.isEmpty()) {
			//使用size的方式一批一批的查找， 可以很好的标记同一批节点的技巧
			int size = queue.size();

			while (size-- > 0) {
				Node cur = queue.poll();
				//如果当前层数 == K，表示就是要找的节点
				if (curLevel == K) {
					ans.add(cur);
				}
				//继续判断左子节点是否为空并且是否被标记为已访问过
				if (cur.left != null && !visited.contains(cur.left)) {
					queue.offer(cur.left);
					visited.add(cur.left);
				}
				//继续判断右子节点是否为空并且是否被标记为已访问过
				if (cur.right != null && !visited.contains(cur.right)) {
					queue.offer(cur.right);
					visited.add(cur.right);
				}
				//继续判断父节点是否为空并且是否被标记为已访问过
				//表示当前节点可以向上走
				if (parents.get(cur) != null && !visited.contains(parents.get(cur))) {
					queue.offer(parents.get(cur));
					visited.add(parents.get(cur));
				}
			}
			//内层while循环退出时，表示当前层已经遍历完，继续下一层，所以curLevel++
			curLevel++;
			//如果当前层数已经超出K，那么就没有必要再继续找了，直接退出外层while
			if (curLevel > K) {
				break;
			}
		}
		return ans;
	}

	/**
	 * 将所有的节点的父节点都记录到parents这个HashMap中
	 *
	 * @param cur
	 * @param parents
	 */
	public static void createParentsHashMap(Node cur, HashMap<Node, Node> parents) {
		//如果当前节点为null，直接返回
		if (cur == null) {
			return;
		}
		//如果当前节点的左子节点不为空
		if (cur.left != null) {
			//将左子节点和父节点记录到parents表中
			parents.put(cur.left, cur);
			//继续向左子节点的整个树递归
			createParentsHashMap(cur.left, parents);
		}
		//如果当前节点的右子节点不为空
		if (cur.right != null) {
			//将右子节点和父节点记录到parents表中
			parents.put(cur.right, cur);
			//继续向右子节点的整棵树递归
			createParentsHashMap(cur.right, parents);
		}
	}
}
