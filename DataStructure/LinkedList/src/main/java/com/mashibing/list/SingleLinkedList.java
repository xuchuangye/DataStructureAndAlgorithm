package com.mashibing.list;

import com.mashibing.node.HeroNode;
/*
题目要求：使用带head头的单向链表实现 –水浒英雄排行榜管理

第一种方法在添加英雄时，直接添加到链表的尾部

第二种方式在添加英雄时，根据排名将英雄插入到指定位置(如果有这个排名，则添加失败，并给出提示)
 */

/**
 * 单链表节点的添加
 * 1)不按照英雄节点的编号顺序进行添加 -> addNotWithHeroNo()
 * 2)按照英雄节点的编号顺序进行添加 -> addWithHeroNo()
 * 遍历输出单链表的所有节点信息 -> list()
 * 修改单链表中指定的节点信息 -> update()
 * 删除单链表中指定的节点 -> delete()
 * @author xcy
 * @date 2022/3/13 - 10:57
 */
public class SingleLinkedList {
	//头节点，不需要存储具体的数据
	private final HeroNode head = new HeroNode(0, "", "");

	/**
	 * 添加节点，不按照英雄的编号顺序进行添加
	 * 实现思路：
	 * 1、首先创建head节点，作用表示单链表的头节点
	 * 2、头节点不能移动，因为头节点移动之后就找不到该单链表，所以需要辅助节点temp来完成
	 * 3、循环遍历判断temp.next域是否为空，next域为空表示当前temp为尾部节点，直接跳出本次循环
	 * 4、如果不是尾部节点，则继续指向下一个节点
	 * 5、退出循环之后，让当前temp节点指向新添加的节点即可
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
	 *      新的节点指向temp的下一个节点，也就是：新的节点.next = temp.next
	 *      temp的指向新的节点，也就是：temp.next = 新的节点
	 * 如果存在
	 *      提示添加节点失败的信息
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
			else if (temp.next.getNo() == heroNode.getNo()) {
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
	 * 修改单链表中的节点信息
	 * @param newHeroNode
	 */
	public void update(HeroNode newHeroNode) {
		//如果只有头节点信息，next域为null，证明单链表为空
		if (head.next == null) {
			return;
		}

		//因为头节点不能移动，所以需要辅助节点temp进行完成
		//修改节点不需要查找前一个节点或者后一个节点，所以不需要head.next
		HeroNode temp = head;
		boolean isExist = false;//判断传递进来的节点的编号是否存在，默认不存在

		while (true) {
			//判断当前节点是否为空
			if (temp == null) {
				break;
			}
			if (temp.getNo().equals(newHeroNode.getNo())) {
				isExist = true;
				break;
			}
			temp = temp.next;
		}
		//退出while循环，判断isExist
		if (isExist) {
			temp.name = newHeroNode.getName();
			temp.nickname = newHeroNode.getNickname();
		}else {
			System.out.printf("没有找到编号：%d 的节点，不能修改\n", newHeroNode.getNo());
		}
	}

	/**
	 * 从单链表中删除一个节点
	 * 思路分析：
	 * 1、首先需要找到删除的节点的前一个节点temp
	 * 2、temp.next = temp.next.next
	 * 3、被删除的节点，因为没有其他引用指向，所以会被垃圾回收机制回收
	 * @param deletedNo 准备删除的节点的编号
	 */
	public void delete(Integer deletedNo) {
		//判断链表是否为空，为空就不需要删除节点
		if (head.next == null) {
			return;
		}
		//头节点不能移动，所以需要辅助节点temp，来找到准备删除的节点的前一个节点
		HeroNode temp = head;
		boolean isExist = false;//判断节点是否找到了，默认没有找到

		while (true) {
			//因为删除节点需要找到前一个节点
			//所以需要判断temp的下一个节点是否为空
			if (temp.next == null) {
				break;
			}
			//判断如果temp的下一个节点就是准备删除的节点，也就是找到了准备删除的节点的前一个节点
			if (temp.next.getNo().equals(deletedNo)) {
				//证明找到了，可以删除
				isExist = true;
				break;
			}
			//上述条件都不满足，需要继续指向下一个节点，继续循环遍历
			temp = temp.next;
		}

		//退出while循环，判断isExist
		if (isExist) {
			//让temp节点直接指向下一个节点的下一个节点
			temp.next = temp.next.next;
		}else {
			System.out.printf("准备删除的节点编号：%d 不存在，不能删除",deletedNo);
		}
	}
}
