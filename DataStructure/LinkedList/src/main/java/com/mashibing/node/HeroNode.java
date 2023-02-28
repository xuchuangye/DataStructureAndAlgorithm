package com.mashibing.node;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 表示英雄的节点
 *
 * @author xcy
 * @date 2022/3/13 - 10:54
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HeroNode {
	/**
	 * 编号
	 */
	public Integer no;
	/**
	 * 姓名
	 */
	public String name;
	/**
	 * 绰号
	 */
	public String nickname;
	/**
	 * 指向下一个节点
	 */
	public HeroNode next;

	public HeroNode(Integer no, String name, String nickname) {
		this.no = no;
		this.name = name;
		this.nickname = nickname;
	}

	@Override
	public String toString() {
		return "HeroNode{" +
				"no=" + no +
				", name='" + name + '\'' +
				", nickname='" + nickname + '\'' +
				'}';
	}
}
