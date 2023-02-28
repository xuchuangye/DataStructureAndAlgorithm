package com.mashibing.dijkstra.extend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 加强堆
 * 为什么要手动改写堆？
 * 系统提供的堆无法做到的事情
 * 1、已经入堆的元素，如果参与排序的指标方法变化，系统提供的堆无法做到时间复杂度
 * O(logN)的调整！都是O(N)的调整！
 * 2、系统提供的堆只能弹出堆顶元素，无法删除堆中的任意一个元素，或者说：无法在时间
 * 复杂度O(logN)内完成！一定会高于O(logN)
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
	private final HashMap<T, Integer> heapIndexMap;
	/**
	 * 原始出发节点到能够到达的节点的最短距离的对应关系表
	 * key表示T类型的当前节点
	 * value表示原始出发节点到其他节点的最短距离
	 *
	 * 记录的节点不需要移除，只需要将value的值修改为-1，表示之前进入过，之后出去了
	 */
	private final HashMap<T, Integer> distanceMap;
	/**
	 * 堆的大小
	 */
	private int heapSize;

	public HeapGreator(int length) {
		this.heap = new ArrayList<>(length);
		this.heapIndexMap = new HashMap<>();
		this.distanceMap = new HashMap<>();
		this.heapSize = 0;
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
	 * 判断指定T类型对象是否进入过反向索引表
	 * 当对象出去时，只是修改反向索引表的value属性值为-1，并没有从反向索引表中移除
	 *
	 * @param obj
	 * @return
	 */
	public boolean isEntered(T obj) {
		return heapIndexMap.containsKey(obj);
	}

	/**
	 * 判断指定T类型对象是否在堆上
	 * @param obj
	 * @return
	 */
	public boolean isHeap(T obj) {
		return isEntered(obj) && heapIndexMap.get(obj) != -1;
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
	 * 当前元素向下调整
	 *
	 * @param index
	 */
	public void heapify(int index, int size) {
		//当前节点的左子节点
		int left = 2 * index + 1;
		while (left < size) {
			//判断当前节点的左子节点和右子节点的权值大小，返回权值较大的索引
			int smallIndex = left + 1 < size && distanceMap.get(heap.get(left + 1)) < distanceMap.get(heap.get(left)) ? left + 1 : left;
			//取出权值较大的子节点，再和父节点的权值进行比较，返回权值较大的索引
			smallIndex = distanceMap.get(heap.get(smallIndex)) < distanceMap.get(heap.get(index)) ? smallIndex : index;
			//判断最终获取的较大值是否是当前节点的索引，如果是，直接返回
			if (smallIndex == index) {
				break;
			}
			//如果不是，证明当前节点不是整棵子树中的最大的节点，当前节点索引需要和整棵子树的最大值索引进行交换
			swap(smallIndex, index);
			//当前节点来到maxIndex
			index = smallIndex;
			//继续进行下一层的判断，从左子节点开始
			left = 2 * index + 1;
		}
	}

	/**
	 * 当前节点向上调整
	 *
	 * @param index
	 */
	public void heapInsert(T obj, int index) {
		while (distanceMap.get(heap.get(index)) < distanceMap.get(heap.get((index - 1) / 2))) {
			swap(index, (index - 1) / 2);
			index = (index - 1) / 2;
		}
	}
	/**
	 * 添加或更新或忽略
	 * @param obj 当前节点
	 * @param distance 新的距离
	 */
	public void addOrUpdateOrIgnore(T obj, int distance) {
		//判断当前节点是否在堆上，如果已经在堆上，此时进行update操作
		if (isHeap(obj)) {
			distanceMap.put(obj, Math.min(distanceMap.get(obj), distance));
			//根据当前节点以及当前节点在反向索引表中的位置进行向上调整
			heapInsert(obj, heapIndexMap.get(obj));
		}
		//判断当前节点是否进入过反向索引表，如果没有进入过反向索引表，此时进行add操作
		if (!isEntered(obj)) {
			//将此列表中指定位置heapSize的元素替换为指定元素obj
			heap.set(heapSize, obj);
			//在反向索引表中记录当前元素在堆上的位置
			heapIndexMap.put(obj, heapSize);
			//出发节点与当前节点的距离关系映射表中，添加当前节点以及出发节点到当前节点的距离的信息
			distanceMap.put(obj, distance);
			//当前节点向上调整，heapSize堆的大小++
			heapInsert(obj, heapSize++);
		}
		//既不在堆上，也没有进入过反向索引表，此时进行ignore忽略操作
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
	 * 在堆中弹出堆顶元素，也就是距离当前节点最短路径的节点
	 *
	 * @return
	 */
	public NodeRecord<T> pop() {
		NodeRecord<T> nodeRecord = new NodeRecord(heap.get(0) ,distanceMap.get(heap.get(0)));
		//第一个节点和最后一个节点进行交换
		swap(0, heapSize - 1);
		//在反向索引表中将最后一个节点的value属性值，也就是距离设置为-1，表示并不移除
		heapIndexMap.put(heap.get(heapSize - 1), -1);
		//在当前节点与其他节点的最短距离的关系映射表中，将最后一个元素移除
		distanceMap.remove(heap.get(heapSize - 1));

		heap.set(heapSize - 1, null);
		heapify(0, --heapSize);

		return nodeRecord;
	}

	/**
	 * 堆中两个元素的交换
	 *
	 * @param i 第一个元素的位置
	 * @param j 第二个元素的位置
	 */
	public void swap(int i, int j) {
		heapIndexMap.put(heap.get(i), j);
		heapIndexMap.put(heap.get(j), i);
		T t = heap.get(i);
		heap.set(i, heap.get(j));
		heap.set(j, t);
	}
}
