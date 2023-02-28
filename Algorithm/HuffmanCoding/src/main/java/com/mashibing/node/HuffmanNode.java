package com.mashibing.node;

import lombok.Data;

/**
 * @author xcy
 * @date 2022/3/27 - 15:28
 */
@Data
public class HuffmanNode implements Comparable<HuffmanNode>{
	/**
	 * 数据，记录出现的字符
	 */
	private Byte data;
	/**
	 * 权值，记录字符出现的次数
	 */
	private Integer weight;
	/**
	 * 左子节点
	 */
	private HuffmanNode left;
	/**
	 * 右子节点
	 */
	private HuffmanNode right;

	public HuffmanNode(Byte data, Integer weight) {
		this.data = data;
		this.weight = weight;
	}

	@Override
	public int compareTo(HuffmanNode o) {
		//从小到大排序
		return this.weight - o.weight;
	}

	@Override
	public String toString() {
		return "HuffmanNode{" +
				"data=" + data +
				", weight=" + weight +
				'}';
	}

	/**
	 * 前序遍历
	 */
	public void preOrder() {
		System.out.println(this);
		if (this.left != null) {
			this.left.preOrder();
		}
		if (this.right != null) {
			this.right.preOrder();
		}
	}
}
