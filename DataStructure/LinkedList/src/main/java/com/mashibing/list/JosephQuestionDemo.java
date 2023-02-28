package com.mashibing.list;

import com.mashibing.node.SingleNode;

/**
 * @author xcy
 * @date 2022/4/26 - 15:29
 */
public class JosephQuestionDemo {
	//first指针
	private SingleNode first = null;
	public static void main(String[] args) {

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

		/*while (true) {
			//说明此时的helper已经是最后的节点
			if (helper.next == first) {
				break;
			}
			helper = helper.next;
		}*/

		while (helper.next != first) {
			//说明此时的helper已经是最后的节点
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
