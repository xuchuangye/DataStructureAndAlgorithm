package com.mashibing;

import com.mashibing.list.SingleLinkedList;
import com.mashibing.node.HeroNode;

/**
 * 单向链表的添加、修改、删除、遍历等测试
 * @author xcy
 * @date 2022/3/13 - 10:04
 */
public class LinkedListDemo_001 {
	public static void main(String[] args) {
		//创建节点信息
		HeroNode hero1 = new HeroNode(1, "宋江", "及时雨");
		HeroNode hero2 = new HeroNode(2, "卢俊义", "玉麒麟");
		HeroNode hero3 = new HeroNode(3, "吴用", "智多星");
		HeroNode hero4 = new HeroNode(4, "林冲", "豹子头");

		//创建单链表
		SingleLinkedList singleLinkedList1 = new SingleLinkedList();

		//加入 节点信息
		singleLinkedList1.addNotWithHeroNo(hero1);
		singleLinkedList1.addNotWithHeroNo(hero2);
		singleLinkedList1.addNotWithHeroNo(hero3);
		singleLinkedList1.addNotWithHeroNo(hero4);

		//循环遍历输出单链表的节点信息
		singleLinkedList1.list();

		//测试修改单链表中的节点
		singleLinkedList1.update(new HeroNode(3,"小吴","智多星~~"));

		//测试删除单链表中的节点
		//singleLinkedList1.delete(1);
		//singleLinkedList1.delete(2);
		//singleLinkedList1.delete(3);
		//singleLinkedList1.delete(4);
		System.out.println("----------------------");
		singleLinkedList1.list();

		//创建单链表
		SingleLinkedList singleLinkedList2 = new SingleLinkedList();

		//加入节点信息，按照编号的顺序
		singleLinkedList2.addWithHeroNo(hero1);
		singleLinkedList2.addWithHeroNo(hero3);
		//singleLinkedList2.addWithHeroNo(hero4);
		singleLinkedList2.addWithHeroNo(hero4);
		//singleLinkedList2.addWithHeroNo(hero2);
		singleLinkedList2.addWithHeroNo(hero2);

		//循环遍历输出单链表的节点信息
		//singleLinkedList2.list();
	}

}
