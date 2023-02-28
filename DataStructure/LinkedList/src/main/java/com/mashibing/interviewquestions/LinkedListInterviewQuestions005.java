package com.mashibing.interviewquestions;

import com.mashibing.node.HeroNode;

import java.util.Stack;

/**
 * 带头节点的单向链表的应用实例
 * <p>
 * 题目一：返回单链表中指定倒数第count的的节点 -> returnLastIndexNode(HeroNode head, int count)
 * <p>
 * 题目三：逆序打印输出单链表的所有节点 -> reversePrintNode(HeroNode head)
 * <p>
 * 题目四：合并两个有序单链表，并且合并之后的链表依然有序 -> mergeTwoLinkedList(HeroNode head1, HeroNode head2)
 *
 * @author xcy
 * @date 2022/3/13 - 17:26
 */
public class LinkedListInterviewQuestions005 {
	//头节点，不需要存储具体的数据
	private final HeroNode head = new HeroNode(0, "", "");

	/**
	 * 获取单链表的头节点head
	 *
	 * @return 头节点head
	 */
	public HeroNode getHeadNode() {
		return head;
	}

	/**
	 * 题目：获取单链表中的所有节点个数
	 *
	 * @param head 头节点
	 * @return 单链表中所有节点的个数
	 */
	public int getLength(HeroNode head) {
		if (head.next == null) {
			return 0;
		}
		//头节点不能移动，所以需要使用辅助节点temp来完成，并且头节点没有具体数据，所以不需要打印头节点
		HeroNode temp = head.next;
		//记录单链表中的节点总个数
		int length = 0;
		while (true) {
			if (temp == null) {
				break;
			}
			length++;
			temp = temp.next;
		}
		return length;
	}

	/**
	 * 添加节点，不按照英雄的编号顺序进行添加
	 * 实现思路：
	 * 1、首先创建head节点，作用表示单链表的头节点
	 * 2、头节点不能移动，因为头节点移动之后就找不到该单链表，所以需要辅助节点temp来完成
	 * 3、循环遍历判断temp.next域是否为空，next域为空表示当前temp为尾部节点，直接跳出本次循环
	 * 4、如果不是尾部节点，则继续指向下一个节点
	 * 5、退出循环之后，让当前temp节点指向新添加的节点即可
	 *
	 * @param heroNode 英雄节点
	 */
	public void addNotWithHeroNo(HeroNode heroNode) {
		/*
		添加节点
		1、头节点不能移动，所以需要辅助变量temp来完成
		2、添加节点需要在链表的尾部进行添加
		3、如何判断当前节点是否是尾部节点，根据当前节点的next为null进行判断
		 */
		//1、创建辅助变量
		HeroNode temp = head;
		while (true) {
			//2、当前节点的next为null进行判断，判断是否为尾部节点
			if (temp.next == null) {
				//如果是尾部节点，那么直接break
				break;
			}
			//需要小心，循环遍历时，如果没有找到尾部节点，就需要指向下一个节点，否则就进入死循环
			temp = temp.next;
		}
		//当退出循环时，temp就指向链表的最后，temp指向新添加的节点heroNode
		temp.next = heroNode;
	}

	/**
	 * 添加节点，按照英雄的编号顺序进行添加
	 * 实现思路：
	 * 1、首先通过遍历的方式来进行查找，找到新添加的节点位置，因为头节点不能移动，所以需要辅助节点temp来完成
	 * 2、找出并判断temp的下一个节点的编号是否大于新节点的编号
	 * 3、找出并判断temp的下一个节点的编号是否等于新节点的编号，标记一下表示已经存在
	 * 4、上述的条件都不满足，则继续指向下一个节点
	 * 5、退出循环之后，判断是否已经存在
	 * 如果不存在
	 * 新的节点指向temp的下一个节点，也就是：新的节点.next = temp.next
	 * temp的指向新的节点，也就是：temp.next = 新的节点
	 * 如果存在
	 * 提示添加节点失败的信息
	 *
	 * @param heroNode 新添加的英雄节点
	 */
	public void addWithHeroNo(HeroNode heroNode) {
		/*
		添加节点
		1、头节点不能移动，所以需要辅助变量temp来完成
		2、添加节点需要在链表的尾部进行添加
		3、如何判断当前节点是否是尾部节点，根据当前节点的next为null进行判断
		 */
		//1、头节点不能移动，所以需要辅助变量temp来完成找到新添加的节点位置
		HeroNode temp = head;
		//2、通过遍历找到新节点的位置
		boolean flag = false;//编号是否存在，默认false表示不存在
		//因为是单链表的原因，所以temp必须是新添加节点的位置的前一个节点，否则添加不进去
		while (true) {
			//2.1、判断temp是否是尾部节点
			if (temp.next == null) {
				break;
			}
			//2.2、判断temp.next的编号是否大于新节点的编号
			if (temp.next.getNo() > heroNode.getNo()) {
				break;
			}
			//2.3、判断temp.next的编号是否和新节点的编号相等，相等就证明该编号已经存在
			else if (temp.next.getNo().equals(heroNode.getNo())) {
				flag = true;//表示已经存在
				break;
			}
			//2.4、上述条件都不满足，则temp继续指向下一个节点
			temp = temp.next;
		}

		//退出while循环之后，需要判断flag是否已经存在
		if (flag) {
			//如果存在，则提示插入失败的信息
			System.out.printf("准备插入的英雄节点的编号：%d 已经存在了，不能加入\n", heroNode.getNo());
		}
		//如果不存在，证明新的节点可以插入temp节点和temp.next节点
		else {
			//新的节点.next指向temp.next
			heroNode.next = temp.next;
			//temp节点指向新插入的节点
			temp.next = heroNode;
		}
	}

	/**
	 * 循环遍历所有的节点
	 */
	public void list() {
		/*
		遍历链表
		1、判断链表是否为空
		2、头节点不能移动，所以需要辅助变量temp来完成
		3、判断是否是尾部节点，如果不是尾部节点，继续指向下一个节点，并且打印输出当前节点的信息
		 */
		//1、如何判断链表是否为空，根据头节点的next是否为空进行判断
		if (head.next == null) {
			System.out.println("链表为空");
			return;
		}
		//2、头节点不能移动，所以需要辅助变量temp来完成遍历
		//head节点本身并不存储具体的数据，所以不需要遍历，直接从head.next头节点的下一个节点开始遍历即可
		HeroNode temp = head.next;
		while (true) {
			//3、3、判断是否是尾部节点，如果不是尾部节点，继续指向下一个节点，并且打印输出当前尾部节点的信息
			if (temp.next == null) {
				System.out.println(temp);
				break;
			}
			//如果不是尾部节点，那么输出当前节点信息，继续指向下一个节点
			System.out.println(temp);
			//需要小心，循环遍历时，如果没有找到尾部节点，就需要指向下一个节点，否则就进入死循环
			temp = temp.next;
		}
	}


	/**
	 * 题目：返回单链表中指定倒数第count的的节点
	 * 思路分析：
	 * 1、指定单链表的头节点和count，count表示单链表中倒数第count个节点的位置
	 * 2、安全校验count，防止代码恶意侵入
	 * 3、头节点不能移动，所以需要辅助节点temp来完成，并且头节点不算做单链表的总节点个数中，所以直接从head.next开始
	 * 4、循环遍历输出 单链表的总节点个数 - count的差值 -> size 次即可
	 * 5、如果满足 size == 0 或者 temp.next域 == null，证明已经遍历单链表中的所有节点
	 * 6、上述条件都不满足则temp继续指向下一个节点
	 * 7、最终循环结束，返回temp这个节点
	 *
	 * @param head  单链表中的头节点
	 * @param count 倒数第count的
	 * @return 返回单链表中倒数第count的节点
	 */
	public HeroNode returnLastIndexNode(HeroNode head, int count) {
		//单链表为空，返回null
		if (head.next == null) {
			return null;
		}
		//单链表的总节点个数
		int totalCount = getLength(head);

		//需要对count进行安全校验，防止恶意代码侵入
		if (count <= 0 || count > totalCount) {
			System.out.println("您输入的数据不在有效范围：（0 < count <= 链表的总个数:" + totalCount + "）");
			return null;
		}

		int size = totalCount - count;
		//头节点不能移动，需要准备辅助节点temp来完成，因为head节点不算做单链表的总节点个数中，所以直接从next域开始
		HeroNode temp = head.next;
		while (true) {
			if (size == 0) {
				break;
			}
			if (temp.next == null) {
				break;
			}
			size--;
			temp = temp.next;
		}
		return temp;
	}

	/**
	 * 题目：单链表的反转
	 * 思路分析:
	 * 1. 先定义一个新链表的头节点 reverseHead = new HeroNode();
	 * 2. 定义cur记录head.next，next记录cur当前节点的下一个节点;
	 * 3. 从头到尾遍历原来的链表，每遍历一个节点，就将其取出，并放在新的链表reverseHead 的最前端.
	 * 4. 原来的链表的head.next = reverseHead.next
	 *
	 * @param head 单链表的头节点
	 */
	public void reverseLinkedList(HeroNode head) {
		//链表为空或者链表只有一个节点
		if (head.next == null || head.next.next == null) {
			return;
		}
		//当前节点，从head的下一个节点开始
		HeroNode cur = head.next;
		//记录当前节点的下一个节点，防止当前节点之后原来的链表找不到
		HeroNode next = null;
		//创建新链表的头节点
		HeroNode reverseHead = new HeroNode(0, "", "");

		//从头到尾遍历原来的链表，每遍历一个节点，就将其取出，并放在新的链表reverseHead 的最前端
		while (cur != null) {
			//先记录当前节点的下一个节点，防止原来的链表找不到
			next = cur.next;
			//当前节点的下一个节点作为新的链表的最前端
			cur.next = reverseHead.next;
			//将当前节点连接到新的链表上
			reverseHead.next = cur;
			//当前节点继续指向记录的下一个节点
			cur = next;
		}
		//循环结束，新的链表建立完成，让原来的链表的head.next域 指向 reverseHead.next域
		head.next = reverseHead.next;
	}

	/**
	 * 题目：逆序打印输出单链表的所有节点
	 * 思路分析：
	 * 1、利用Stack的先进后出特性，循环遍历依次将链表中的所有节点压入栈中
	 * 2、最后循环遍历Stack，按照先进后出的特性让元素依次出栈
	 *
	 * @param head 头节点
	 */
	public void reversePrintNode(HeroNode head) {
		//链表节点为空，不能打印输出
		if (head.next == null) {
			return;
		}

		//头节点不能移动，需要辅助节点temp完成
		HeroNode temp = head.next;
		//创建栈
		Stack<HeroNode> stack = new Stack<>();
		//循环遍历链表，所有的节点依次入栈
		while (temp != null) {
			stack.push(temp);
			temp = temp.next;
		}
		//循环遍历栈，依次出栈
		while (stack.size() > 0) {
			System.out.println(stack.pop());
		}
	}

	/**
	 * 题目：合并两个有序单链表，并且合并之后的链表依然有序
	 *
	 * @param head1 单链表1
	 * @param head2 单链表2
	 */
	public HeroNode mergeTwoLinkedList(HeroNode head1, HeroNode head2) {
		//只要有其中一个单链表的节点为空，那么就没有必要进行合并
		if (head1 == null) {
			return head2;
		}
		if (head2 == null) {
			return head1;
		}

		//新的链表头节点，判断哪个头节点的值更小，哪个就作为新的头节点
		HeroNode newHead = head1.no <= head2.no ? head1 : head2;
		//cur1记录head1节点的下一个节点
		HeroNode cur1 = head1.next;
		//判断head1是否作为新的头节点，如果head1作为新的头节点，那么cur2指向head2
		//如果head2作为新的头节点，那么cur2指向head1
		HeroNode cur2 = head1 == newHead ? head2 : head1;

		//辅助节点
		HeroNode pre = newHead;
		//判断cur1和cur2都不为空
		while (cur1 != null && cur2 != null) {
			//判断cur1节点的值是否小于等于cur2节点的值
			if (cur1.no <= cur2.no) {
				//pre的下一个节点指向cur1
				pre.next = cur1;
				//cur1继续下一个节点
				cur1 = cur1.next;
			} else {
				//pre的下一个节点指向cur1
				pre.next = cur2;
				//cur2继续下一个节点
				cur2 = cur2.next;
			}
			pre = pre.next;
		}
		//退出循环时，当cur1节点为空时，pre.next指向cur2，否则指向cur1
		pre.next = cur1 == null ? cur2 : cur1;

		//因为带头节点的单链表的头节点数据为空，所以直接返回头节点的下一个节点
		return newHead.next;
	}

	/**
	 * 根据单链表的头节点打印输出单链表的所有节点
	 *
	 * @param head 单链表的头节点
	 */
	public void printNode(HeroNode head) {
		if (head.next == null) {
			return;
		}
		HeroNode temp = head.next;
		while (temp != null) {
			System.out.println(temp);
			temp = temp.next;
		}
	}
}
