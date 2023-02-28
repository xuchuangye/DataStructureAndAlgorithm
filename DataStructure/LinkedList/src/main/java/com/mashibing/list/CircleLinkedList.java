package com.mashibing.list;

import com.mashibing.node.SingleNode;

/**
 * 单向的环形链表
 *
 * @author xcy
 * @date 2022/3/15 - 15:52
 */
public class CircleLinkedList {
	//first指针
	private SingleNode first = null;

	/**
	 * 单向环形链表添加节点
	 *
	 * @param nums 节点的个数
	 */
	public void addNode(int nums) {
		if (nums < 1) {
			System.out.println("nums的值不正确");
			return;
		}
		//因为first节点不能移动，所以需要辅助节点cur来完成
		SingleNode cur = null;
		for (int i = 1; i <= nums; i++) {
			SingleNode node = new SingleNode(i);
			//单向环形链表添加first指向的节点本身，也就是节点first,next域指向first自己
			if (i == 1) {
				//first指向当前新创建的节点
				first = node;
				//first的next域指向first自己
				first.next = first;
				//cur记录first的位置，准备继续后移
				cur = first;
			}
			//单向环形链表添加除了first指向的节点之外的新的节点
			else {
				cur.next = node;
				node.next = first;
				cur = node;
			}
		}
	}

	public void list() {
		//判断环形链表的节点是否为空
		if (first == null) {
			System.out.println("单项环形链表为空");
			return;
		}

		//first节点不能移动，需要辅助节点temp进行完成
		SingleNode temp = first;

		while (true) {
			System.out.printf("编号%d的节点\n", temp.getNo());
			if (temp.next == first) {
				break;
			}

			temp = temp.next;
		}
	}

	/**
	 * 打印输出单向环形链表的节点个数
	 * @return 链表的节点个数
	 */
	public int getLength() {
		//判断环形链表节点是否为空，如果为空返回0
		if (first.next == null) {
			return 0;
		}

		SingleNode temp = first;
		int len = 0;
		while (true) {
			//此时该temp不是尾部节点，累加一次
			len++;
			//此时该temp是尾部节点，退出循环
			if (temp.next == first) {
				break;
			}
			//此时该temp不是尾部节点，继续指向下一个节点
			temp = temp.next;
		}
		return len;
	}

	/**
	 * 约瑟夫出圈的问题
	 * @param startNo 表示从第几个节点开始计数
	 * @param countNum 表示每次计数几次
	 * @param nums 表示多少个节点
	 */
	public void JosephQuestion(int startNo, int countNum, int nums) {
		//first节点为空，直接返回
		if (first == null) {
			return;
		}
		//数据校验
		if (startNo < 1 || countNum > nums) {
			System.out.printf("您输入的值不正确：startNo不能小于1，countNum不能大于%d", nums);
			return;
		}
		//first节点不能移动，需要辅助节点helper进行
		SingleNode helper = first;

		while (true) {
			//说明此时的helper已经是最后的节点
			if (helper.next == first) {
				break;
			}
			helper = helper.next;
		}
		//first和helper节点同时移动到startNo - 1的位置
		for (int i = 0; i < startNo - 1; i++) {
			first = first.next;
			helper = helper.next;
		}

		while (true) {
			//说明此时helper和first指向同一个节点
			if (helper == first) {
				break;
			}
			//first和helper节点同时移动countNum - 1次
			for (int i = 0; i < countNum - 1; i++) {
				first = first.next;
				helper = helper.next;
			}
			//此时移动完之后的first节点就是准备出圈的节点
			System.out.printf("出圈的节点是：%d\n", first.getNo());

			//准备出圈
			first = first.next;
			helper.next = first;
		}
		System.out.printf("最后出圈的节点是：%d\n", first.getNo());
	}
}
