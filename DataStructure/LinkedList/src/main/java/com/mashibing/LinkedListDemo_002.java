package com.mashibing;

import com.mashibing.interviewquestions.LinkedListInterviewQuestions005;
import com.mashibing.node.HeroNode;

/**
 * 单向链表面试题的测试
 * @author xcy
 * @date 2022/3/13 - 10:04
 */
public class LinkedListDemo_002 {
	public static void main(String[] args) {
		//创建节点信息
		HeroNode hero1 = new HeroNode(1, "宋江", "呼保义");
		HeroNode hero2 = new HeroNode(2, "卢俊义", "玉麒麟");
		HeroNode hero3 = new HeroNode(3, "吴用", "智多星");
		HeroNode hero4 = new HeroNode(4, "公孙胜", "入云龙");

		//创建单链表
		LinkedListInterviewQuestions005 singlyLinkedList = new LinkedListInterviewQuestions005();
		singlyLinkedList.addWithHeroNo(hero1);
		singlyLinkedList.addWithHeroNo(hero3);
		singlyLinkedList.addWithHeroNo(hero4);
		singlyLinkedList.addWithHeroNo(hero2);

		System.out.println("单链表1的情况");
		singlyLinkedList.list();
		//打印单链表中的头节点
		HeroNode headNode = singlyLinkedList.getHeadNode();
		//打印单链表的总节点个数
		//System.out.println("单链表的总节点个数：" + singlyLinkedList.getLength(headNode));

		//打印单链表中倒数第count个的节点
		//int count = 1;
		//System.out.println("单链表中倒数第" + count + "个的节点是：" + singlyLinkedList.returnLastIndexNode(headNode, count));


		//反转并打印单链表
		//singlyLinkedList.reverseLinkedList(headNode);
		//singlyLinkedList.list();

		//逆序打印单链表的所有节点，不破坏链表的结构
		//System.out.println("逆序打印的链表，不破坏原来链表的结构");
		//singlyLinkedList.reversePrintNode(headNode);


		HeroNode hero5 = new HeroNode(5, "关胜", "大刀");
		HeroNode hero6 = new HeroNode(6, "林冲", "豹子头");
		HeroNode hero7 = new HeroNode(7, "秦明", "霹雳火");
		HeroNode hero8 = new HeroNode(8, "呼延灼", "双鞭");

		//创建单链表
		LinkedListInterviewQuestions005 singlyLinkedList2 = new LinkedListInterviewQuestions005();
		singlyLinkedList2.addWithHeroNo(hero6);
		singlyLinkedList2.addWithHeroNo(hero5);
		singlyLinkedList2.addWithHeroNo(hero8);
		singlyLinkedList2.addWithHeroNo(hero7);

		System.out.println("单链表2的情况");
		singlyLinkedList2.list();
		//打印单链表中的头节点
		HeroNode headNode2 = singlyLinkedList2.getHeadNode();

		HeroNode newNode = singlyLinkedList.mergeTwoLinkedList(headNode, headNode2);

		System.out.println("单链表1和单链表2合并之后的情况");
		singlyLinkedList.printNode(newNode);
	}

}
