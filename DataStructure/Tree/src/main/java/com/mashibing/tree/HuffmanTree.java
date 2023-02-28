package com.mashibing.tree;

import com.mashibing.node.Node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 赫夫曼树
 *
 * @author xcy
 * @date 2022/3/27 - 9:29
 */
public class HuffmanTree {
	public static void main(String[] args) {
		int[] arr = {13, 7, 8, 3, 29, 6, 1};
		Node root = createHuffmanTree(arr);
		preOrder(root);
	}

	/**
	 * 前序遍历
	 *
	 * @param root 根节点
	 */
	public static void preOrder(Node root) {
		if (root != null) {
			root.preOrder();
		} else {
			System.out.println("根节点为空，无法进行前序遍历");
		}
	}

	/**
	 * 创建赫夫曼树
	 *
	 * @param arr 数组
	 * @return 返回创建好的赫夫曼树的根节点root
	 */
	public static Node createHuffmanTree(int[] arr) {
		//1.循环遍历数组
		//2.取出数组中的每一个元素封装到Node节点中
		//3.将封装之后的节点依次添加到一个新的List中
		List<Node> nodes = new ArrayList<Node>();

		for (int value : arr) {
			nodes.add(new Node(value));
		}

		Node leftNode = null;
		Node rightNode = null;
		Node parent = null;

		while (nodes.size() > 1) {
			//遍历List集合
			Collections.sort(nodes);
			System.out.println("nodes = " + nodes);

			//从List集合中取出第一个和第二个元素，因为按照从小到大排序，所以第一个和第二个肯定是最小的
			leftNode = nodes.get(0);
			rightNode = nodes.get(1);

			//权值相加
			parent = new Node(leftNode.getValue() + rightNode.getValue());

			//设置parent的左子节点和右子节点
			parent.setLeft(leftNode);
			parent.setRight(rightNode);

			//删除List集合中的第一个和第二个元素
			nodes.remove(leftNode);
			nodes.remove(rightNode);
			//添加第一个和第二个元素的权值相加之后的节点
			nodes.add(parent);
		}
		/*//第一次
		//从List集合中取出第一个和第二个元素，因为按照从小到大排序，所以第一个和第二个肯定是最小的
		Node leftNode = nodes.get(0);
		Node rightNode = nodes.get(1);

		//权值相加
		Node parent = new Node(leftNode.getValue() + rightNode.getValue());

		//删除List集合中的第一个和第二个元素
		nodes.remove(leftNode);
		nodes.remove(rightNode);
		nodes.add(parent);
		//从小到大排序
		Collections.sort(nodes);
		System.out.println(nodes);


		//第二次
		leftNode = nodes.get(0);
		rightNode = nodes.get(1);

		parent = new Node(leftNode.getValue() + rightNode.getValue());

		//删除List集合中的第一个和第二个元素
		nodes.remove(leftNode);
		nodes.remove(rightNode);
		nodes.add(parent);
		//从小到大排序
		Collections.sort(nodes);
		System.out.println(nodes);

		//第三次
		leftNode = nodes.get(0);
		rightNode = nodes.get(1);

		parent = new Node(leftNode.getValue() + rightNode.getValue());

		//删除List集合中的第一个和第二个元素
		nodes.remove(leftNode);
		nodes.remove(rightNode);
		nodes.add(parent);
		//从小到大排序
		Collections.sort(nodes);
		System.out.println(nodes);


		//第四次
		leftNode = nodes.get(0);
		rightNode = nodes.get(1);

		parent = new Node(leftNode.getValue() + rightNode.getValue());

		//删除List集合中的第一个和第二个元素
		nodes.remove(leftNode);
		nodes.remove(rightNode);
		nodes.add(parent);
		//从小到大排序
		Collections.sort(nodes);
		System.out.println(nodes);

		//第五次
		leftNode = nodes.get(0);
		rightNode = nodes.get(1);

		parent = new Node(leftNode.getValue() + rightNode.getValue());

		//删除List集合中的第一个和第二个元素
		nodes.remove(leftNode);
		nodes.remove(rightNode);
		nodes.add(parent);
		//从小到大排序
		Collections.sort(nodes);
		System.out.println(nodes);

		//第六次
		leftNode = nodes.get(0);
		rightNode = nodes.get(1);

		parent = new Node(leftNode.getValue() + rightNode.getValue());

		//删除List集合中的第一个和第二个元素
		nodes.remove(leftNode);
		nodes.remove(rightNode);
		nodes.add(parent);
		//从小到大排序
		Collections.sort(nodes);
		System.out.println(nodes);*/

		//返回赫夫曼树的根节点
		return nodes.get(0);
	}
}
