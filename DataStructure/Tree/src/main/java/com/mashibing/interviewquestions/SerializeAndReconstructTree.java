package com.mashibing.interviewquestions;

import com.mashibing.common.BinaryTreeUtils;
import com.mashibing.node.Node;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;


/**
 * 二叉树面试题
 * <p>
 * 实现二叉树的序列化和反序列化 --> 序列化和重构二叉树
 * 1、先序遍历的方式序列化和反序列化
 * 2、按层方式序列化和反序列化
 * <p>
 * 二叉树可以通过先序、后序或者按层遍历的方式序列化和反序列化，
 * 以下代码全部实现了。
 * 但是，二叉树无法通过中序遍历的方式实现序列化和反序列化
 * 因为不同的两棵树，可能得到同样的中序序列，即便补了空位置也可能一样。
 * 比如如下两棵树
 * ..__2
 * ./
 * 1
 * 和
 * 1__
 * ...\
 * ....2
 * 补足空位置的中序遍历结果都是{ null, 1, null, 2, null}
 *
 * @author xcy
 * @date 2022/4/10 - 16:59
 */
public class SerializeAndReconstructTree {
	public static void main(String[] args) {
		int maxLevel = 5;
		int maxValue = 100;
		int testTimes = 1000000;
		System.out.println("test begin");
		for (int i = 0; i < testTimes; i++) {
			Node head = BinaryTreeUtils.generateRandomBST(maxLevel, maxValue);
			Queue<String> pre = preSerializable(head);
			Queue<String> pos = postSerializable(head);
			Queue<String> level = levelSerializable(head);
			Node preBuild = preDeserialization(pre);
			Node posBuild = postDeserialization(pos);
			Node levelBuild = levelDeserialization(level);
			if (!BinaryTreeUtils.isSameValueStructure(preBuild, posBuild) || !BinaryTreeUtils.isSameValueStructure(posBuild, levelBuild)) {
				System.out.println("Oops!");
			}
		}
		System.out.println("test finish!");
	}

	/**
	 * 后序遍历的方式序列化 --> 将二叉树结构序列化为字符串类型的队列
	 *
	 * @param root 二叉树的根节点
	 * @return 字符串类型的队列
	 */
	public static Queue<String> postSerializable(Node root) {
		Queue<String> queue = new LinkedList<>();
		postSerializable(root, queue);
		return queue;
	}

	/**
	 * 后序遍历 --> 将二叉树结构序列化为字符串类型的队列
	 *
	 * @param root 二叉树的根节点
	 */
	public static void postSerializable(Node root, Queue<String> queue) {
		if (root == null) {
			queue.add(null);
		} else {
			postSerializable(root.getLeft(), queue);
			postSerializable(root.getRight(), queue);
			queue.add(String.valueOf(root.getValue()));
		}
	}

	/**
	 * 先序遍历的方式序列化 --> 将二叉树结构序列化为字符串类型的队列
	 *
	 * @param root 二叉树的根节点
	 * @return 字符串类型的队列
	 */
	public static Queue<String> preSerializable(Node root) {
		Queue<String> queue = new LinkedList<>();
		preSerializable(root, queue);
		return queue;
	}

	/**
	 * 先序遍历 --> 将二叉树结构序列化为字符串类型的队列
	 *
	 * @param root 二叉树的根节点
	 */
	public static void preSerializable(Node root, Queue<String> queue) {
		if (root == null) {
			queue.add(null);
		} else {
			queue.add(String.valueOf(root.getValue()));
			preSerializable(root.getLeft(), queue);
			preSerializable(root.getRight(), queue);
		}
	}
	/**
	 * 后序遍历的反序列化 --> 将字符串类型的队列反序列化为二叉树结构
	 *
	 * @param queue 字符串类型的队列
	 * @return 二叉树的根节点
	 */
	public static Node postDeserialization(Queue<String> queue) {
		//字符串类型的队列为空，或者队列的元素个数为0，直接返回空树
		if (queue == null || queue.size() == 0) {
			return null;
		}

		return buildBinaryTreeWithPostOrder(queue);
	}
	/**
	 * 通过后序遍历的方式构建二叉树
	 *
	 * @param queue 字符串类型的栈
	 * @return 二叉树的根节点
	 */
	public static Node buildBinaryTreeWithPostOrder(Queue<String> queue) {
		Stack<String> stack = new Stack<>();

		while (!queue.isEmpty()) {
			stack.push(queue.poll());
		}

		String pop = stack.pop();
		if (pop == null) {
			return null;
		}

		Node root = new Node(Integer.parseInt(pop));
		root.setLeft(buildBinaryTreeWithPostOrder(queue));
		root.setRight(buildBinaryTreeWithPostOrder(queue));
		return root;
	}
	/**
	 * 先序遍历的反序列化 --> 将字符串类型的队列反序列化为二叉树结构
	 *
	 * @param queue 字符串类型的队列
	 * @return 二叉树的根节点
	 */
	public static Node preDeserialization(Queue<String> queue) {
		//字符串类型的队列为空，或者队列的元素个数为0，直接返回空树
		if (queue == null || queue.size() == 0) {
			return null;
		}

		return buildBinaryTreeWithPreOrder(queue);
	}

	/**
	 * 通过先序遍历的方式构建二叉树
	 *
	 * @param queue 字符串类型的队列
	 * @return 二叉树的根节点
	 */
	public static Node buildBinaryTreeWithPreOrder(Queue<String> queue) {
		String poll = queue.poll();
		if (poll == null) {
			return null;
		}
		Node root = new Node(Integer.parseInt(poll));
		root.setLeft(buildBinaryTreeWithPreOrder(queue));
		root.setRight(buildBinaryTreeWithPreOrder(queue));
		return root;
	}

	/**
	 * 按层遍历的方式序列化 --> 将二叉树结构序列化为字符串类型的队列
	 *
	 * @param root 二叉树的根节点
	 * @return 字符串类型的队列
	 */
	public static Queue<String> levelSerializable(Node root) {
		//创建返回结果：按层的方式将二叉树序列化为字符串类型的队列
		Queue<String> ans = new LinkedList<String>();
		if (root == null) {
			ans.add(null);
		} else {
			//首先将根节点添加到ans队列中
			ans.add(String.valueOf(root.getValue()));
			//创建辅助遍历的节点队列
			Queue<Node> queue = new LinkedList<>();
			//将根节点添加到辅助队列
			queue.add(root);
			//判断辅助队列是否为空
			while (!queue.isEmpty()) {
				//不为空，取出队列的节点
				Node node = queue.poll();
				//判断该节点的左子节点是否为空
				if (node.getLeft() != null) {
					//如果不为空，则添加到序列化的队列中
					ans.add(String.valueOf(node.getLeft().getValue()));
					//如果不为空，则添加到辅助队列中
					queue.add(node.getLeft());
				} else {
					//如果为空，则将null填充到序列化的队列中
					ans.add(null);
				}
				//判断该节点的右子节点是否为空
				if (node.getRight() != null) {
					//如果不为空，则添加到序列化的队列中
					ans.add(String.valueOf(node.getRight().getValue()));
					//如果不为空，则添加到辅助队列中
					queue.add(node.getRight());
				} else {
					//如果为空，则将null填充到序列化的队列中
					ans.add(null);
				}
			}
		}

		return ans;
	}

	/**
	 * 按层遍历的方式反序列化 --> 将字符串类型的队列反序列化为二叉树结构
	 *
	 * @param queue 字符串类型的队列
	 * @return 二叉树的根节点
	 */
	public static Node levelDeserialization(Queue<String> queue) {
		if (queue == null || queue.size() == 0) {
			return null;
		}
		return buildBinaryTreeWithLevelOrder(queue);
	}

	/**
	 * 通过按层遍历的方式构建二叉树
	 *
	 * @param queue 字符串类型的队列
	 * @return 二叉树的根节点
	 */
	public static Node buildBinaryTreeWithLevelOrder(Queue<String> queue) {
		String value = queue.poll();
		Node root = null;
		if (value == null) {
			return null;
		} else {
			//首先构建二叉树的根节点
			root = generateNode(value);
			Queue<Node> nodes = new LinkedList<>();
			if (root != null) {
				nodes.add(root);
			}
			while (!nodes.isEmpty()) {
				Node node = nodes.poll();
				node.setLeft(generateNode(queue.poll()));
				node.setRight(generateNode(queue.poll()));
				if (node.getLeft() != null) {
					nodes.add(node.getLeft());
				}
				if (node.getRight() != null) {
					nodes.add(node.getRight());
				}
			}
		}
		return root;
	}

	/**
	 * 创建节点 --> 根据字符串转换为Integer类型的值创建节点
	 *
	 * @param value
	 * @return
	 */
	private static Node generateNode(String value) {
		Node root = null;
		if (value != null) {
			root = new Node(Integer.parseInt(value));
		}
		return root;
	}


}
