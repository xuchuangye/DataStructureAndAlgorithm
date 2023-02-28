package com.mashibing.test;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @author xcy
 * @date 2022/4/19 - 15:44
 */
public class HeapTest {
	public static class ReverseOrder implements Comparator<Integer> {

		@Override
		public int compare(Integer o1, Integer o2) {
			return -o1 - o2;
		}
	}
	public static void main(String[] args) {
		//默认小根堆,也就是当前子树的头节点的权值最小
		//PriorityQueue<Integer> heap = new PriorityQueue<>(new ReverseOrder());
		//如果想要实现大根堆，需要自定义实现Comparator比较器
		PriorityQueue<Integer> heap = new PriorityQueue<>(new ReverseOrder());
		heap.add(1);
		heap.add(4);
		heap.add(2);
		heap.add(7);
		heap.add(10);
		System.out.println(heap.peek());
	}
}
