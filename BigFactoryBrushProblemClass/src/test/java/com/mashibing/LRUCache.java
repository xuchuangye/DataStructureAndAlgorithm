package com.mashibing;

import java.util.HashMap;

/**
 * @author xcy
 * @date 2022/8/11 - 15:18
 */
public class LRUCache {
	public static class Node<K, V> {
		public K key;
		public V value;

		public Node<K, V> last;
		public Node<K, V> next;

		public Node(K key, V value) {
			this.key = key;
			this.value = value;
		}
	}

	/**
	 * 双向链表
	 *
	 * @param <K> Key
	 * @param <V> Value
	 */
	public static class NodeDoubleLinkedList<K, V> {
		public Node<K, V> head;
		public Node<K, V> tail;

		public void addNode(Node<K, V> newNode) {
			if (newNode == null) {
				return;
			}
			//如果双向链表为null，表示没有节点
			if (head == null) {
				//头节点指向newNode
				head = newNode;
				//尾节点指向newNode
				tail = newNode;
			}
			//如果双向链表不为null
			else {
				//尾部节点的next指向newNode
				tail.next = newNode;
				//newNode的last指向tail
				newNode.last = tail;
				//newNode作为新的尾节点
				tail = newNode;
			}
		}

		/**
		 * 将node节点从双向链表中分离出来，并且挂载到双向链表的尾部
		 *
		 * @param node
		 */
		public void moveNodeToTail(Node<K, V> node) {
			if (node == null) {
				return;
			}
			if (head == null) {
				return;
			}
			//node节点本来就是尾部节点，那么就不需要挂载到双向链表的尾部
			if (tail == node) {
				return;
			}
			//node节点本来就是头部节点
			if (head == node) {
				//node.next作为新的头节点
				head = node.next;
				//新的头节点的last指向null
				head.last = null;
			}
			//node节点就是普通节点
			else {
				node.last.next = node.next;
				node.next.last = node.last;
			}
			//node节点从双向链表中分离出来之后，挂载到双向链表的尾部
			//尾部节点的next指向node
			tail.next = node;
			//node节点的last指向tail
			node.last = tail;
			//node节点的next指向null
			node.next = null;
			//node节点作为新的尾部节点
			tail = node;
		}

		/**
		 * 删除双向链表的头节点
		 *
		 * @return 返回双向链表的头节点
		 */
		public Node<K, V> removeHead() {
			if (head == null) {
				return null;
			}
			//创建辅助节点
			Node<K, V> node = head;
			//表示双向链表中只有一个节点
			if (head == tail) {
				head = null;
				tail = null;
			}
			//表示双向链表中不止一个节点
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
		 * 创建哈希表，记录Node节点的key，与Node节点
		 * key: Node节点的key
		 * value: Node节点
		 */
		public HashMap<K, Node<K, V>> hashMap;
		/**
		 * 使用双端队列表示缓存结构
		 */
		public NodeDoubleLinkedList<K, V> doubleLinkedList;
		/**
		 * 缓存容量
		 */
		public int capacity;

		public MyCache(int capacity) {
			hashMap = new HashMap<>();
			doubleLinkedList = new NodeDoubleLinkedList<>();
			this.capacity = capacity;
		}

		public V get(K key) {
			if (hashMap.containsKey(key)) {
				Node<K, V> kvNode = hashMap.get(key);
				doubleLinkedList.moveNodeToTail(kvNode);
				return kvNode.value;
			}
			return null;
		}

		public void put(K key, V value) {
			if (hashMap.containsKey(key)) {
				Node<K, V> kvNode = hashMap.get(key);
				kvNode.value = value;
				doubleLinkedList.moveNodeToTail(kvNode);
			}else {
				Node<K, V> kvNode = new Node<>(key, value);
				hashMap.put(key, kvNode);
				doubleLinkedList.addNode(kvNode);
				if (hashMap.size() == capacity + 1) {
					removeMostUnused();
				}
			}
		}

		/**
		 * 删除最不常用的数据
		 */
		public void removeMostUnused() {
			Node<K, V> kvNode = doubleLinkedList.removeHead();
			hashMap.remove(kvNode.key);
		}
	}
}
