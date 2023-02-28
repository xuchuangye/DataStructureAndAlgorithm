package com.mashibing.day19;

import java.util.HashMap;

/**
 * LRU内存/缓存替换算法
 * 请你设计并实现一个满足LRU (最近最少使用) 缓存 约束的数据结构。
 * 实现 LRUCache 类：
 * LRUCache(int capacity)以正整数 作为容量capacity初始化LRU缓存
 * int get(int key) 如果关键字key存在于缓存中，则返回关键字的值，否则返回-1 。
 * void put(int key, int value)
 * 如果关键字key已经存在，则变更其数据值value；
 * 如果不存在，则向缓存中插入该组key-value 。
 * 如果插入操作导致关键字数量超过capacity ，则应该 逐出 最久未使用的关键字。
 * 函数get和put必须以O(1)的平均时间复杂度运行。
 * <p>
 * Leetcode题目：https://leetcode.com/problems/lru-cache/
 * <p>
 * 解题思路：
 * 双向链表 + 哈希表
 *
 * @author xcy
 * @date 2022/8/10 - 16:15
 */
public class Code01_LRUCache {
	public static void main(String[] args) {
		Code01_LRUCache code01_lruCache = new Code01_LRUCache(10);
		code01_lruCache.get(10);
	}

	public MyCache<Integer, Integer> myCache;

	public Code01_LRUCache(int capacity) {
		myCache = new MyCache<Integer, Integer>(capacity);
	}

	public int get(int key) {
		if (!myCache.containsKey(key)) {
			return -1;
		}
		return myCache.get(key);
	}

	public void put(int key, int value) {
		myCache.put(key, value);
	}

	public static class Node<K, V> {
		public Node<K, V> last;
		public Node<K, V> next;
		public K key;
		public V value;

		public Node(K key, V value) {
			this.key = key;
			this.value = value;
		}
	}

	public static class NodeDoubleLinkedList<K, V> {
		public Node<K, V> head;
		public Node<K, V> tail;

		//添加Node节点，并且挂在双向链表的尾部
		public void addNode(Node<K, V> newNode) {
			if (newNode == null) {
				return;
			}
			//如果双向链表中没有一个节点，头节点指向newNode，尾节点指向newNode
			if (head == null) {
				head = newNode;
				tail = newNode;
			}
			//如果双向链表中不止一个节点
			else {
				//旧的尾部节点的next指向newNode
				tail.next = newNode;
				//newNode的last指针指向旧的尾部节点
				newNode.last = tail;
				//newNode作为新的尾部节点
				tail = newNode;
			}
		}

		//将Node分离出来，调整好头部和尾部的关系，并且将Node节点挂载到尾部
		public void moveNodeToTail(Node<K, V> node) {
			//如果节点为空，直接返回
			if (node == null) {
				return;
			}
			//如果头节点为空，无法将Node节点分离出来
			if (head == null) {
				return;
			}
			//如果Node节点就是尾部节点，那么不需要将Node节点挂载到尾部
			if (tail == node) {
				return;
			}
			//将Node节点分离出来
			//如果Node节点就是头部节点
			if (head == node) {
				//node.next作为新的头节点
				head = node.next;
				//新的头节点last指向null
				head.last = null;
			}
			//如果Node节点不是头部节点
			else {
				node.last.next = node.next;
				node.next.last = node.last;
			}
			//将Node节点挂载到尾部
			tail.next = node;
			node.last = tail;
			node.next = null;
			tail = node;
		}

		//将头节点删除
		public Node<K, V> removeHead() {
			//如果头节点为null，无法删除
			if (head == null) {
				return null;
			}
			Node<K, V> node = head;
			//如果双向链表中只有一个节点
			if (head == tail) {
				head = null;
				tail = null;
			}
			//如果双向链表中不止一个节点
			else {
				head = node.next;
				node.next = null;
				head.last = null;
			}
			return node;
		}

	}

	public static class MyCache<K, V> {
		/**
		 * Key：表示节点的Key
		 * Value：表示节点
		 */
		public final HashMap<K, Node<K, V>> hashMap;
		/**
		 * 维持节点关系的双向链表
		 */
		public final NodeDoubleLinkedList<K, V> doubleLinkedList;
		/**
		 * 容量
		 */
		public final int capacity;

		public MyCache(int capacity) {
			hashMap = new HashMap<>();
			doubleLinkedList = new NodeDoubleLinkedList<>();
			this.capacity = capacity;
		}

		public boolean containsKey(K key) {
			return hashMap.containsKey(key);
		}

		/**
		 * 根据Key获取Value
		 *
		 * @param key key
		 * @return 返回node节点的value
		 */
		public V get(K key) {
			if (!hashMap.containsKey(key)) {
				return null;
			}
			Node<K, V> node = hashMap.get(key);
			//更新node，将node从双向链表中分离出来，挂载到双向链表的尾部
			doubleLinkedList.moveNodeToTail(node);
			return node.value;
		}

		/**
		 * 1.当哈希表中没有记录时，属于添加操作
		 * 2.当哈希表中有记录时，属于更新操作
		 *
		 * @param key   key
		 * @param value value
		 */
		public void put(K key, V value) {
			//如果哈希表中有记录，属于更新操作
			if (hashMap.containsKey(key)) {
				Node<K, V> kvNode = hashMap.get(key);
				//更新node的value值
				kvNode.value = value;
				//将kvNode从双向链表中分离出来，挂载到双向链表的尾部
				doubleLinkedList.moveNodeToTail(kvNode);
			}
			//如果哈希表中没有记录时，属于添加操作
			else {
				Node<K, V> kvNode = new Node<>(key, value);
				//在哈希表中记录
				hashMap.put(key, kvNode);
				//在双向链表中添加
				doubleLinkedList.addNode(kvNode);
				//如果超出双向链表的长度，移除掉头节点，也就是最旧的数据
				if (hashMap.size() == capacity + 1) {
					removeMostUnusedCache();
				}
			}
		}

		/**
		 * 删除双向链表中最旧的数据，也就是头节点
		 */
		public void removeMostUnusedCache() {
			Node<K, V> kvNode = doubleLinkedList.removeHead();
			hashMap.remove(kvNode.key);
		}
	}
}
