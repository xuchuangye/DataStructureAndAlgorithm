package com.mashibing.comparator;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.TreeSet;

/**
 * @author xcy
 * @date 2022/4/10 - 15:06
 */
public class MyComparator implements Comparator<Integer> {
	@Override
	public int compare(Integer o1, Integer o2) {
		return o2 - o1;
	}

	public static void main(String[] args) {
		PriorityQueue<Integer> heap = new PriorityQueue<>(new MyComparator());
		int count = 10;
		for (int i = 1; i <= count; i++) {
			heap.add((int) (Math.random() * (count + 1)));
		}
		while (!heap.isEmpty()) {
			System.out.print(heap.poll() + " ");
		}
		System.out.println("-----------------");
		TreeSet<Integer> treeSet = new TreeSet<>(new MyComparator());
		for (int i = 1; i <= count; i++) {
			treeSet.add((int) (Math.random() * count));
		}
		for (Integer integer : treeSet) {
			System.out.print(integer + " ");
		}
	}
}
