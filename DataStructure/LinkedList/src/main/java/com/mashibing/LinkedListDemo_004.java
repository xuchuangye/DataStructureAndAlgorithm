package com.mashibing;

import com.mashibing.list.BidirectionalLinkedList;
import com.mashibing.list.CircleLinkedList;
import com.mashibing.node.HeroDoubleNode;
import com.mashibing.node.SingleNode;

/**
 * 单向环形链表的测试
 * @author xcy
 * @date 2022/3/13 - 10:04
 */
public class LinkedListDemo_004 {
	public static void main(String[] args) {
		//创建单向环形链表
		CircleLinkedList circleLinkedList = new CircleLinkedList();

		//环形链表节点的添加
		circleLinkedList.addNode(5);

		//循环遍历单向环形链表的节点
		circleLinkedList.list();

		//打印输出环形链表的节点个数
		int length = circleLinkedList.getLength();
		System.out.println("环形链表的节点个数为：" + length);

		//测试约瑟夫出圈问题
		circleLinkedList.JosephQuestion(1, 2, length);
	}

}
