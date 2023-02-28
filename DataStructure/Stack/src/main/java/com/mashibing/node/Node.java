package com.mashibing.node;

import lombok.Data;

/**
 * @author xcy
 * @date 2022/3/16 - 17:17
 */
@Data
public class Node {
	private int value;
	public Node next;

	public Node() {

	}

	public Node(int value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "Node{" +
				"value=" + value +
				'}';
	}
}
