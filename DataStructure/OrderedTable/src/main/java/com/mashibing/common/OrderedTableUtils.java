package com.mashibing.common;

import com.mashibing.orderedtable.SizeBalancedTreeMapDemo;
import com.mashibing.orderedtable.SkipListMapDemo;

import java.util.Arrays;

/**
 * 有序表的工具类
 * @author xcy
 * @date 2022/5/30 - 15:23
 */
public class OrderedTableUtils {
	// for test
	public static void printAll(SizeBalancedTreeMapDemo.SBTNode<String, Integer> head) {
		System.out.println("Binary Tree:");
		printInOrder(head, 0, "H", 17);
		System.out.println();
	}


	// for test
	public static void printAll(SkipListMapDemo.SkipListMap<String, String> obj) {
		for (int i = obj.maxLevel; i >= 0; i--) {
			System.out.print("Level " + i + " : ");
			SkipListMapDemo.SkipListNode<String, String> cur = obj.head;
			while (cur.nextNodes.get(i) != null) {
				SkipListMapDemo.SkipListNode<String, String> next = cur.nextNodes.get(i);
				System.out.print("(" + next.k + " , " + next.v + ") ");
				cur = next;
			}
			System.out.println();
		}
	}

	// for test
	public static void printInOrder(SizeBalancedTreeMapDemo.SBTNode<String, Integer> head, int height, String to, int len) {
		if (head == null) {
			return;
		}
		printInOrder(head.r, height + 1, "v", len);
		String val = to + "(" + head.k + "," + head.v + ")" + to;
		int lenM = val.length();
		int lenL = (len - lenM) / 2;
		int lenR = len - lenM - lenL;
		val = getSpace(lenL) + val + getSpace(lenR);
		System.out.println(getSpace(height * len) + val);
		printInOrder(head.l, height + 1, "^", len);
	}

	// for test
	public static String getSpace(int num) {
		String space = " ";
		StringBuffer buf = new StringBuffer("");
		for (int i = 0; i < num; i++) {
			buf.append(space);
		}
		return buf.toString();
	}
	// for test
	public static int[] generateArray(int len, int varible) {
		int[] arr = new int[len];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = (int) (Math.random() * varible);
		}
		return arr;
	}

	/**
	 * 打印输出数组
	 *
	 * @param arr 数组
	 */
	public static void printArray(int[] arr) {
		System.out.println(Arrays.toString(arr));
	}
}
