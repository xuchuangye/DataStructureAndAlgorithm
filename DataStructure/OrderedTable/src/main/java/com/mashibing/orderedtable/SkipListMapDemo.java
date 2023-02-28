package com.mashibing.orderedtable;

import com.mashibing.common.OrderedTableUtils;

import java.util.ArrayList;

/**
 * @author xcy
 * @date 2022/5/30 - 17:46
 */
public class SkipListMapDemo {
	public static void main(String[] args) {
		SkipListMap<String, String> test = new SkipListMap<>();
		OrderedTableUtils.printAll(test);
		System.out.println("======================");
		test.put("A", "10");
		OrderedTableUtils.printAll(test);
		System.out.println("======================");
		test.remove("A");
		OrderedTableUtils.printAll(test);
		System.out.println("======================");
		test.put("E", "E");
		test.put("B", "B");
		test.put("A", "A");
		test.put("F", "F");
		test.put("C", "C");
		test.put("D", "D");
		OrderedTableUtils.printAll(test);
		System.out.println("======================");
		System.out.println(test.containsKey("B"));
		System.out.println(test.containsKey("Z"));
		System.out.println(test.firstKey());
		System.out.println(test.lastKey());
		System.out.println(test.floorKey("D"));
		System.out.println(test.ceilingKey("D"));
		System.out.println("======================");
		test.remove("D");
		OrderedTableUtils.printAll(test);
		System.out.println("======================");
		System.out.println(test.floorKey("D"));
		System.out.println(test.ceilingKey("D"));
	}
	public static class SkipListNode<K extends Comparable<K>, V> {
		public K k;
		public V v;
		public ArrayList<SkipListNode<K, V>> nextNodes;

		public SkipListNode(K k, V v) {
			this.k = k;
			this.v = v;
			nextNodes = new ArrayList<>();
		}

		/**
		 * 遍历的时候，如果是往右遍历到的null(next == null), 遍历结束
		 * 头(null), 头节点的null，认为最小
		 * node  -> 头，node(null, "")  node.isKeyLess(!null)  true
		 * node里面的k是否比key小，true，不是false
		 *
		 * @param key
		 * @return
		 */
		public boolean isKeyLess(K key) {
			return key != null && (k == null || k.compareTo(key) < 0);
		}

		public boolean isKeyEqual(K key) {
			return (k == null && key == null) || (k != null && key != null && k.compareTo(key) == 0);
		}
	}

	public static class SkipListMap<K extends Comparable<K>, V> {
		public static final double PROBABILITY = 0.5;
		/**
		 * 头节点
		 */
		public SkipListNode<K, V> head;
		/**
		 * 一共挂了多少个节点
		 */
		public int size;

		/**
		 * 最大层数
		 */
		public int maxLevel;

		public SkipListMap() {
			//头节点，key和value都比系统最小值还要小，跳表最左的最小点
			head = new SkipListNode<>(null, null);
			//先让头节点有一层链表
			head.nextNodes.add(null);
			size = 0;
			//初始最大层数在第0层
			maxLevel = 0;
		}

		/**
		 * 从最高层开始，一路找下去，
		 * 最终，找到第0层上小于key值的最右节点
		 *
		 * @param key
		 * @return 找到当前层数最右侧比key值还要小的节点，返回
		 */
		public SkipListNode<K, V> mostRightLessNodeInTree(K key) {
			if (key == null) {
				return null;
			}
			//从当前最大层数开始
			int curLevel = maxLevel;
			//从head头节点开始
			SkipListNode<K, V> cur = head;
			while (curLevel >= 0) {
				//找到当前层比key小的最右节点
				cur = mostRightLessNodeInTree(key, cur, curLevel);
				//继续下一层
				curLevel--;
			}
			return cur;
		}

		/**
		 * 在level层里，如何往右移动
		 * 现在来到的节点是cur，来到了cur的level层，在level层上，找到小于key值最后一个节点并返回
		 *
		 * @param key
		 * @param cur
		 * @param maxLevel
		 * @return
		 */
		public SkipListNode<K, V> mostRightLessNodeInTree(K key, SkipListNode<K, V> cur, int maxLevel) {
			SkipListNode<K, V> next = cur.nextNodes.get(maxLevel);
			//如果next不为空，并且next的k小于key，下一个继续往右走
			while (next != null && next.isKeyLess(key)) {
				cur = next;
				next = cur.nextNodes.get(maxLevel);
			}
			return cur;
		}

		/**
		 * 跳表中是否包含key值的节点
		 *
		 * @param key
		 * @return 如果包含，返回true，否则返回false
		 */
		public boolean containsKey(K key) {
			if (key == null) {
				return false;
			}
			//第0层比key值小的最右节点
			SkipListNode<K, V> less = mostRightLessNodeInTree(key);
			//第0层比key值小的最右节点的下一组节点的第0个
			SkipListNode<K, V> find = less.nextNodes.get(0);
			//如果第0层比key值小的最右节点的下一组节点的第0个不为空，并且值和key值相等，表示跳表中包含key值的节点
			//返回true，否则就表示不包含，返回false
			return find != null && find.isKeyEqual(key);
		}

		/**
		 * 新增、修改value
		 *
		 * @param key
		 * @param value
		 */
		public void put(K key, V value) {
			if (key == null) {
				return;
			}
			//找到第0层比key值小最右的节点
			SkipListNode<K, V> less = mostRightLessNodeInTree(key);
			//最右节点的下一组节点的第0个
			SkipListNode<K, V> find = less.nextNodes.get(0);
			//如果第0层比key值小的最右节点的下一组节点的第0个节点不为空并且该节点的值等于第0层比key值小最右的节点
			if (find != null && find.isKeyEqual(key)) {
				//表示已经记录过key，不需要再次添加，直接更新value就行
				find.v = value;
			}
			//有两种需要添加key和value，也就是创建新的节点的情况
			//1.如果第0层比key值小的最右节点的下一组节点的第0个节点为空，表示没有记录key的值，此时的key值是最大的
			//2.如果第0层比key值小的最右节点的下一组节点的第0个节点不为空，但是比当前key值大，表示key也没有记录过

			//节点个数++
			size++;
			//新增的节点永远拥有第0层链表
			int newNodeLevel = 0;
			//随机新增节点的层数，也就是拥有几层链表
			while (Math.random() < PROBABILITY) {
				newNodeLevel++;
			}
			//设置最大层数
			while (newNodeLevel > maxLevel) {
				//最左边的head将会收到和此时新增节点的最大层数一样高
				head.nextNodes.add(null);
				maxLevel++;
			}
			//创建新增节点
			SkipListNode<K, V> newNode = new SkipListNode<>(key, value);
			//根据新增节点随机出来的层数，新增节点的每一层都需要添加链表
			for (int i = 0; i <= newNodeLevel; i++) {
				newNode.nextNodes.add(null);
			}
			int level = maxLevel;
			SkipListNode<K, V> pre = head;
			while (level >= 0) {
				//在当前一层中，找到比key值小的最右节点
				pre = mostRightLessNodeInTree(key);
				//当新增节点的层数大于等于整个跳表的最大层数时，才会把新增节点挂载到pre节点之后，否则就不挂载
				if (newNodeLevel >= level) {
					//新增节点挂载谁，pre节点的下一个
					newNode.nextNodes.set(level, pre.nextNodes.get(level));
					//pre节点挂载谁，新增节点
					pre.nextNodes.set(level, newNode);
					//新增节点就挂载到pre节点与pre节点的下一个节点之间
					//pre -> newNode -> pre.nextNodes.get(curLevel)
				}
				level--;
			}
		}
		public V get(K key) {
			if (key == null) {
				return null;
			}
			SkipListNode<K, V> less = mostRightLessNodeInTree(key);
			SkipListNode<K, V> next = less.nextNodes.get(0);
			return next != null && next.isKeyEqual(key) ? next.v : null;
		}
		/**
		 * @param key
		 */
		public void remove(K key) {
			if (key == null) {
				return;
			}

			if (containsKey(key)) {
				//删除节点，节点个数--
				size--;
				//从最高层开始
				int level = maxLevel;
				//从head头节点开始找
				SkipListNode<K, V> pre = head;

				while (level >= 0) {
					//当前层找到比key值小并且最右的节点
					pre = mostRightLessNodeInTree(key);
					SkipListNode<K, V> next = pre.nextNodes.get(level);
					//在这一层中，pre的下一个与key值相等的节点就是要删除的节点
					//next不为空，并且next的值等于key值，next就是要删除的节点
					if (next != null && next.isKeyEqual(key)) {
						//在当前这一层中，pre的nextNodes直接指向next的下一个节点，那么next节点就会被删除
						pre.nextNodes.set(level, next.nextNodes.get(level));
					}
					//如果pre等于head节点，并且pre下一个节点为空，说明这一层只有head节点自己，没有其他节点
					//说明被删除的next节点独占这一层，现在这一层已经没有存在的必要了
					if (level != 0 && pre == head && pre.nextNodes.get(0) == null) {
						//head所在的链表层删除这一层
						head.nextNodes.remove(level);
						//跳表的最大层数--
						maxLevel--;
					}
					level--;
				}
			}
		}

		public K firstKey() {
			return head.nextNodes.get(0) != null ? head.nextNodes.get(0).k : null;
		}

		public K lastKey() {
			int level = maxLevel;
			SkipListNode<K, V> cur = head;
			while (level >= 0) {
				SkipListNode<K, V> next = cur.nextNodes.get(level);
				while (next != null) {
					cur = next;
					next = cur.nextNodes.get(level);
				}
				level--;
			}
			return cur.k;
		}

		public K ceilingKey(K key) {
			if (key == null) {
				return null;
			}
			SkipListNode<K, V> less = mostRightLessNodeInTree(key);
			SkipListNode<K, V> next = less.nextNodes.get(0);
			return next != null ? next.k : null;
		}

		public K floorKey(K key) {
			if (key == null) {
				return null;
			}
			SkipListNode<K, V> less = mostRightLessNodeInTree(key);
			SkipListNode<K, V> next = less.nextNodes.get(0);
			return next != null && next.isKeyEqual(key) ? next.k : less.k;
		}

		public int size() {
			return size;
		}
	}
}



