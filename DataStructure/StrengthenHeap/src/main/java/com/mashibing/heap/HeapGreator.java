package com.mashibing.heap;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * 加强堆
 * 为什么要手动改写堆？
 * 系统提供的堆无法做到的事情
 * 1、已经入堆的元素，如果参与排序的指标方法变化，系统提供的堆无法做到时间复杂度O(logN)的调整！都是O(N)的调整！
 * 2、系统提供的堆只能弹出堆顶元素，无法删除堆中的任意一个元素，或者说：无法在时间复杂度O(logN)内完成！一定会高于O(logN)
 * 根本原因：系统堆没有反向索引表
 * T类型一定是自定义引用类型
 *
 * @author xcy
 * @date 2022/4/22 - 8:18
 */
public class HeapGreator<T> {
	/**
	 * 堆
	 */
	private final ArrayList<T> heap;
	/**
	 * 反向索引表
	 * 记录每一个T类型的对象在ArrayList中的位置
	 */
	private final HashMap<T, Integer> indexMap;
	/**
	 * 堆的大小
	 */
	private int heapSize;
	/**
	 * 比较器
	 */
	private final Comparator<? super T> comparator;

	public HeapGreator(Comparator<T> comparator) {
		heap = new ArrayList<>();
		indexMap = new HashMap<>();
		heapSize = 0;
		this.comparator = comparator;
	}

	/**
	 * 判断堆是否为空isEmpty
	 *
	 * @return
	 */
	public boolean isEmpty() {
		return heapSize == 0;
	}

	/**
	 * 返回堆的大小size
	 *
	 * @return
	 */
	public int size() {
		return heapSize;
	}

	/**
	 * 判断是否包含指定T类型对象contains
	 *
	 * @param obj
	 * @return
	 */
	public boolean contains(T obj) {
		return indexMap.containsKey(obj);
	}

	/**
	 * 查看堆顶元素peek
	 *
	 * @return
	 */
	public T peek() {
		return heap.get(0);
	}

	/**
	 * 在堆中添加元素push
	 *
	 * @param obj
	 */
	public void push(T obj) {
		heap.add(obj);
		indexMap.put(obj, heapSize);
		heapInsert(heapSize++);
	}

	/**
	 * 在堆中弹出堆顶元素pop
	 *
	 * @return
	 */
	public T pop() {
		//取出堆顶元素
		T pop = heap.get(0);
		//堆顶元素和最后一个元素交换
		swap(0, heapSize - 1);
		//在反向索引表中清除最后一个元素的索引
		indexMap.remove(pop);
		//在堆中通过heapSize移除尾部元素
		heap.remove(--heapSize);
		//堆中顶部元素需要向下调整
		heapify(0);
		return pop;
	}

	/**
	 * 在堆中删除任意一个元素remove
	 * 1、先查看要删除的元素在哪儿
	 * 2、再查看堆中尾部的元素在哪儿
	 * 3、交换要删除的元素和尾部的元素
	 * 4、删除尾部的元素
	 * 5、交换过后的元素进行heapify和heapInsert
	 *
	 * @param obj
	 */
	public void remove(T obj) {
		//1.获取堆中尾部的元素
		T replace = heap.get(heapSize - 1);
		//2.获取当前要删除元素的位置
		Integer curIndex = indexMap.get(obj);
		//3.在堆中删除尾部元素
		heap.remove(--heapSize);
		//4.在反向索引中清除该元素的索引
		indexMap.remove(obj);
		//5.判断要删除的元素是否是堆中尾部元素
		if (obj != replace) {
			//6.在要删除元素的位置上添加end
			heap.set(curIndex, replace);
			//7.在end元素上记录要删除元素的索引
			indexMap.put(replace, curIndex);
			//向上调整或者向下调整
			resign(replace);
		}
	}

	//在堆中插入元素，使用比较器heapInsert

	/**
	 * 当元素内部修改之后，使其堆调整为有序resign
	 *
	 * @param obj 当前元素
	 */
	public void resign(T obj) {
		heapInsert(indexMap.get(obj));
		heapify(indexMap.get(obj));
	}

	/**
	 * 获取所有的元素
	 *
	 * @return
	 */
	public List<T> getAllElements() {
		/*List<T> ans = new ArrayList<>();
		for (T t : heap) {
			ans.add(t);
		}
		return ans;*/
		return new ArrayList<>(heap);
	}

	/**
	 * 当前元素向下调整（下沉）
	 *
	 * @param index
	 */
	public void heapify(int index) {
		//当前节点的左子节点
		int left = 2 * index + 1;
		//如果有左孩子节点
		while (left < heapSize) {
			//判断当前节点的左子节点和右子节点的权值大小，返回权值较大的索引
			int largestIndex = 0;
			int right = left + 1;
			//如果右孩子节点不越界
			if (right < heapSize
					&&
					//如果右孩子节点的值大于左孩子节点的值
					comparator.compare(heap.get(right), heap.get(left)) > 0) {
				//那么最大的子节点就是右孩子节点
				largestIndex = right;
			}
			//否则，没有右孩子节点或者右孩子节点的值小于左孩子节点的值
			else {
				//那么最大的子节点就是左孩子节点
				largestIndex = left;
			}
			//取出权值较大的子节点，再和父节点的权值进行比较，返回权值较大的索引
			largestIndex = comparator.compare(heap.get(largestIndex), heap.get(index)) < 0 ? largestIndex : index;
			//判断最终获取的较大值是否是当前父节点的索引，如果是，直接返回，不需要往下沉
			if (largestIndex == index) {
				break;
			}
			//如果不是，证明当前节点不是整棵子树中的最大的节点，当前节点索引需要和整棵子树的最大值索引进行交换
			swap(largestIndex, index);
			//当前节点来到较大子节点的位置
			index = largestIndex;
			//继续进行是否下沉的判断，从左子节点开始
			left = 2 * index + 1;
		}
	}

	/**
	 * 当前元素向上调整（上浮）
	 *
	 * @param index
	 */
	public void heapInsert(int index) {
		//while退出循环的条件
		//1.当前节点是整棵树的根节点
		//2.当前节点的值干不过父节点的值
		while (comparator.compare(heap.get(index), heap.get((index - 1) / 2)) < 0) {
			swap(index, (index - 1) / 2);
			index = (index - 1) / 2;
		}
	}

	/**
	 * 堆中两个元素的交换
	 *
	 * @param i 第一个元素的位置
	 * @param j 第二个元素的位置
	 */
	private void swap(int i, int j) {
		//1、首先获取堆中两个元素
		T t1 = heap.get(i);
		T t2 = heap.get(j);
		//2、然后在第二个元素的位置j上设置放入第一个元素t1
		//在第一个元素的位置i上设置放入第二个元素t2
		heap.set(j, t1);
		heap.set(i, t2);
		//3、最后再反向索引表中，t2元素的索引是i
		//t1元素的索引是j
		indexMap.put(t2, i);
		indexMap.put(t1, j);
	}
}
