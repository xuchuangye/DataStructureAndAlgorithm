package com.mashibing;

import com.mashibing.list.BidirectionalLinkedList;
import com.mashibing.node.HeroDoubleNode;
import com.mashibing.node.HeroNode;

/**
 * 双向链表节点的添加、修改、删除、遍历等测试
 * @author xcy
 * @date 2022/3/13 - 10:04
 */
public class LinkedListDemo_003 {
	public static void main(String[] args) {
		//创建节点信息
		HeroDoubleNode hero1 = new HeroDoubleNode(1, "宋江", "呼保义");
		HeroDoubleNode hero2 = new HeroDoubleNode(2, "卢俊义", "玉麒麟");
		HeroDoubleNode hero3 = new HeroDoubleNode(3, "吴用", "智多星");
		HeroDoubleNode hero4 = new HeroDoubleNode(4, "公孙胜", "入云龙");

		BidirectionalLinkedList bidirectionalLinkedList = new BidirectionalLinkedList();

		bidirectionalLinkedList.addWithHeroNo(hero1);
		bidirectionalLinkedList.addWithHeroNo(hero4);
		bidirectionalLinkedList.addWithHeroNo(hero2);
		bidirectionalLinkedList.addWithHeroNo(hero3);

		System.out.println("原来的双向链表情况");
		bidirectionalLinkedList.list();


		//System.out.println("更新节点后，双向链表的情况");
		//bidirectionalLinkedList.update(new HeroDoubleNode(3, "小吴", "智多星~~"));
		//bidirectionalLinkedList.list();

		/*System.out.println("删除节点后，双向链表的情况");
		bidirectionalLinkedList.delete(1);
		bidirectionalLinkedList.delete(2);
		bidirectionalLinkedList.delete(3);
		bidirectionalLinkedList.delete(4);
		bidirectionalLinkedList.list();*/
	}

}
