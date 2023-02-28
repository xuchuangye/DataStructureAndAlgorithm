package com.mashibing.node;

/**
 * @author xcy
 * @date 2022/4/25 - 10:50
 */
public class Node {
	public int value;
	public Node next;
	public Node rand;

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
