package com.mashibing.node;


import lombok.Data;

/**
 * 单节点
 * @author xcy
 * @date 2022/3/15 - 15:49
 */
@Data
public class SingleNode {
	/**
	 * 编号
	 */
	private int no;

	/**
	 * 指向下一个节点
	 */
	public SingleNode next;

	public SingleNode (int no) {
		this.no = no;
	}
}
