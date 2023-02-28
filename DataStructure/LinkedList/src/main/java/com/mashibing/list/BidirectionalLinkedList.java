package com.mashibing.list;

import com.mashibing.node.HeroDoubleNode;

/**
 * 双向链表
 * @author xcy
 * @date 2022/3/15 - 8:23
 */
public class BidirectionalLinkedList {
	//头节点
	private final HeroDoubleNode head = new HeroDoubleNode(0, "", "");
	private final HeroDoubleNode pre = new HeroDoubleNode(0, "", "");

	/**
	 * 添加节点：双向链表节点的添加
	 *
	 * @param newNode 新的节点
	 */
	public void addNotWithHeroNo(HeroDoubleNode newNode) {
		//头节点不能移动，需要辅助节点temp来完成
		HeroDoubleNode temp = head;

		while (true) {
			//如果是尾部节点那么退出循环
			if (temp.next == null) {
				break;
			}
			//如果不是尾部节点，那么继续指向下一个节点
			temp = temp.next;
		}
		//当退出循环时，temp已经是链表中的尾部节点
		//当前尾部节点的next指向新的节点
		temp.next = newNode;
		//新的节点的pre域指向当前的尾部节点
		newNode.pre = temp;
	}

	/**
	 * 添加节点：双向链表节点的添加（按照编号的顺序进行添加）
	 *
	 * @param newNode
	 */
	public void addWithHeroNo(HeroDoubleNode newNode) {
		//头节点不能移动，需要辅助节点temp来完成
		HeroDoubleNode temp = head;
		//编号是否存在，默认不存在，可以添加
		boolean isExist = false;

		while (true) {
			//如果是尾部节点那么退出循环
			if (temp.next == null) {
				break;
			}
			if (temp.next.getNo() > newNode.getNo()) {
				break;
			} else if (temp.next.getNo() == newNode.getNo()) {
				isExist = true;
				break;
			}
			//如果不是尾部节点，那么继续指向下一个节点
			temp = temp.next;
		}
		if (isExist) {
			System.out.printf("添加的编号%d已经存在，无法添加", newNode.getNo());
		} else {
			//当退出循环时，temp是链表中的尾部节点，在temp尾部添加新的节点
			if (temp.next == null) {
				//当前尾部节点的next指向新的节点
				temp.next = newNode;
				//新的节点的pre域指向当前的尾部节点
				newNode.pre = temp;
			}
			//当退出循环时，temp不是链表中的尾部节点，在temp尾部添加新的节点
			else {
				//新的节点next域指向当前节点的下一个节点
				newNode.next = temp.next;
				//当前节点的下一个节点的pre域指向新的节点
				temp.next.pre = newNode;
				//当前尾部节点的next指向新的节点
				temp.next = newNode;
				//新的节点的pre域指向当前的尾部节点
				newNode.pre = temp;
			}
		}
	}

	/**
	 * 遍历节点：双向链表节点的遍历
	 */
	public void list() {
		//判断双向链表是否为空，为空就不需要进行遍历
		if (head.next == null) {
			System.out.println("双向链表为空");
			return;
		}
		//头节点不能移动，需要辅助节点temp来完成
		HeroDoubleNode temp = head.next;
		while (true) {
			if (temp.next == null) {
				System.out.println(temp);
				break;
			}
			System.out.println(temp);
			temp = temp.next;
		}
	}

	public void update(HeroDoubleNode newNode) {
		//判断双向链表是否为空，为空则不需要更新节点
		if (head.next == null) {
			return;
		}
		//从链表的第一个节点开始，因为头节点没有具体的数据，所以头节点不需要更新
		HeroDoubleNode temp = head.next;
		boolean isExist = false;//判断需要更新的节点是否存在，根据编号进行查找

		while (true) {
			if (temp == null) {
				break;
			}
			if (temp.getNo().equals(newNode.getNo())) {
				isExist = true;
				break;
			}
			temp = temp.next;
		}
		//判断需要更新的节点是否存在，如果存在
		if (isExist) {
			temp.name = newNode.getName();
			temp.nickname = newNode.getNickname();
		}
		//如果不存在，输出提示信息
		else {
			System.out.printf("没有找到编号为 %d 的节点，无法修改", newNode.getNo());
		}
	}

	/**
	 * 删除节点：双向链表节点的删除
	 * 思路分析：
	 * 双向链表的节点具有自我删除的特性
	 *
	 * @param deleteNo 根据节点的编号进行删除
	 */
	public void delete(Integer deleteNo) {
		//判断双向链表是否为空，为空就不需要删除节点
		if (head.next == null) {
			System.out.println("链表为空，无法删除");
			return;
		}
		//因为双向链表的节点具有自我删除的特性，所以直接从第一个节点开始
		HeroDoubleNode temp = head.next;
		//需要删除的节点默认不存在
		boolean isExist = false;
		while (true) {
			if (temp == null) {
				break;
			}
			if (temp.getNo() == deleteNo) {
				isExist = true;
				break;
			}

			temp = temp.next;
		}
		//判断需要删除的节点是否存在
		if (isExist) {
			//当前节点的前一个节点的next域指向当前节点的next域
			temp.pre.next = temp.next;
			//判断当前节点是否为尾部节点
			if (temp.next != null) {
				//如果不是尾部节点，然后当前节点的下一个节点的pre域指向当前节点的pre域
				temp.next.pre = temp.pre;
			}
		} else {
			System.out.printf("没有找到编号为 %d 的节点，无法删除", deleteNo);
		}
	}
}
