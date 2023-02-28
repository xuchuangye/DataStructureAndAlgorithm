package com.mashibing.day02;

import java.util.HashMap;
import java.util.Map;

/**
 * 题目三：
 * 已知一个消息流会不断地吐出整数1~N，但不一定按照顺序依次吐出，如果上次打印的序号为i， 那么当i+1出现时
 * 请打印i+1及其之后接收过的并且连续的所有数，直到1~N全部接收并打印完，请设计这种接收并打印的结构
 * <p>
 * 解题思路：
 * 1.准备两张表，头表和尾表
 * 头表：表示每一个连续区间的头部都在里面
 * 尾表：表示每一个连续区间的尾部都在里面
 * 假设：
 * 插入数据3, c
 * 生成3 - 3的连续区间
 * 头表：3, c
 * 尾表：3, c
 * 判断有没有以2结尾的，有没有以4开头的，都没有
 * 插入数据5, e
 * 生成5 - 5的连续区间
 * 头表：3, c  5, e
 * 尾表：3, c  5, e
 * 判断有没有以4结尾的，有没有以6开头的，都没有
 * 插入数据4, d
 * 生成4 - 4的连续区间
 * 头表：3, c  5, e  4, d
 * 尾表：3, c  5, e  4, d
 * 判读有没有以3结尾的，有，尾表的3的next指针指向4 - 4区间的以4开头的头部，所以在尾表中以3结尾的失效了，删除
 * 在头表中以4开头的失效了，删除
 * 此时：
 * 头表：3, c  5, e
 * 尾表：      5, e  4, d
 * 判断有没有以5开头的，有，4 - 4区间以4结尾的尾部的next指针指向头表以5开头的，所以在尾表中以4结尾的失效了，删除
 * 在头表中以5开头的失效了，删除
 * 此时：
 * 头表：3, c
 * 尾表：      5, e
 *
 * @author xcy
 * @date 2022/7/11 - 9:09
 */
public class Code03_ReceiveAndPrintOrderLine {
	public static void main(String[] args) {
		// MessageBox only receive 1~N
		MessageBox box = new MessageBox();
		// 1....
		System.out.println("这是2来到的时候");
		box.receive(2,"B"); // - 2"
		System.out.println("这是1来到的时候");
		box.receive(1,"A"); // 1 2 -> print, trigger is 1
		box.receive(4,"D"); // - 4
		box.receive(5,"E"); // - 4 5
		box.receive(7,"G"); // - 4 5 - 7
		box.receive(8,"H"); // - 4 5 - 7 8
		box.receive(6,"F"); // - 4 5 6 7 8
		box.receive(3,"C"); // 3 4 5 6 7 8 -> print, trigger is 3
		box.receive(9,"I"); // 9 -> print, trigger is 9
		box.receive(10,"J"); // 10 -> print, trigger is 10
		box.receive(12,"L"); // - 12
		box.receive(13,"M"); // - 12 13
		box.receive(11,"K"); // 11 12 13 -> print, trigger is 11
	}

	/**
	 * 信息载体类
	 */
	public static class Node {
		public String info;
		public Node next;

		public Node(String info) {
			this.info = info;
		}
	}

	public static class MessageBox {
		/**
		 * 头表
		 */
		public HashMap<Integer, Node> headMap;
		/**
		 * 尾表
		 */
		public HashMap<Integer, Node> tailMap;
		/**
		 * 等待的编号
		 */
		public int waitPoint;

		public MessageBox() {
			headMap = new HashMap<>();
			tailMap = new HashMap<>();
			waitPoint = 1;
		}

		/**
		 * 接收数据信息
		 * 1.插入数据3, c
		 * 生成3 - 3的连续区间
		 * 头表：3, c
		 * 尾表：3, c
		 * 判断有没有以2结尾的，有没有以4开头的，都没有
		 * 2.插入数据5, e
		 * 生成5 - 5的连续区间
		 * 头表：3, c  5, e
		 * 尾表：3, c  5, e
		 * 判断有没有以4结尾的，有没有以6开头的，都没有
		 * 3.插入数据4, d
		 * 生成4 - 4的连续区间
		 * 头表：3, c  5, e  4, d
		 * 尾表：3, c  5, e  4, d
		 * 判读有没有以3结尾的，有，尾表的3的next指针指向4 - 4区间的以4开头的头部，所以在尾表中以3结尾的失效了，删除
		 * 在头表中以4开头的失效了，删除
		 * 此时：
		 * 头表：3, c  5, e
		 * 尾表：      5, e  4, d
		 * 判断有没有以5开头的，有，4 - 4区间以4结尾的尾部的next指针指向头表以5开头的，所以在尾表中以4结尾的失效了，删除
		 * 在头表中以5开头的失效了，删除
		 * 此时：
		 * 头表：3, c
		 * 尾表：      5, e
		 *
		 * @param number 信息编号
		 * @param info   信息内容
		 */
		public void receive(Integer number, String info) {
			if (number < 1) {
				return;
			}
			Node cur = new Node(info);
			headMap.put(number, cur);
			tailMap.put(number, cur);
			//判断有没有以number - 1结尾的
			if (tailMap.containsKey(number - 1)) {
				//如果有以number - 1结尾的
				//那么将以number - 1结尾的next指针指向cur
				tailMap.get(number - 1).next = cur;
				//在尾表中，以number - 1结尾的失效了，从尾表中删除
				tailMap.remove(number - 1);
				//在头表中，以number开头的失效了，从头表中删除
				headMap.remove(number);
			}
			//判断有没有以number + 1开头的
			if (headMap.containsKey(number + 1)) {
				//如果有以number + 1开头的
				//那么将cur的next指针指向以number + 1开头的
				cur.next = headMap.get(number + 1);
				//在尾表中，以number结尾的实现了，从尾表中删除
				tailMap.remove(number);
				//在头表中，以number + 1开头的失效了，从头表中删除
				headMap.remove(number + 1);
			}
			//如果接收的信息编号就是需要的编号，在表中将编号对应的Node取出，并且按照next继续打印输出
			if (number == waitPoint) {
				point();
			}
		}

		/**
		 *
		 */
		public void point() {
			Node wait = headMap.get(waitPoint);
			headMap.remove(waitPoint);
			while (wait != null) {
				System.out.print(wait.info + " ");
				wait = wait.next;
				waitPoint++;
			}
			tailMap.remove(waitPoint - 1);
			System.out.println();
		}
	}
}
