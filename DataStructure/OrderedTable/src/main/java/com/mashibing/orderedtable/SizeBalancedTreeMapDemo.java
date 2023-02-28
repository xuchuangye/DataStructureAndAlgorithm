package com.mashibing.orderedtable;

import com.mashibing.common.OrderedTableUtils;

/**
 * SizeBalanced树
 *
 * @author xcy
 * @date 2022/5/30 - 11:27
 */
public class SizeBalancedTreeMapDemo {
	public static class SBTNode<K extends Comparable<K>, V> {
		public K k;
		public V v;

		public SBTNode<K, V> l;
		public SBTNode<K, V> r;

		public int size;

		public SBTNode(K k, V v) {
			this.k = k;
			this.v = v;
			size = 1;
		}
	}

	public static class SizeBalancedTreeMap<K extends Comparable<K>, V> {
		public SBTNode<K, V> root;

		/**
		 * 右旋
		 *
		 * @param cur 以cur节点为头节点
		 * @return 返回以cur节点为头节点的子树左旋之后的新的头节点
		 */
		public SBTNode<K, V> rightRotate(SBTNode<K, V> cur) {
			SBTNode<K, V> left = cur.l;
			cur.l = left.r;
			left.r = cur;

			left.size = cur.size;
			cur.size = (cur.l != null ? cur.l.size : 0) + (cur.r != null ? cur.r.size : 0) + 1;
			return left;
		}

		/**
		 * 左旋
		 *
		 * @param cur 以cur节点为头节点
		 * @return 返回以cur节点为头节点的子树右旋之后的新的头节点
		 */
		public SBTNode<K, V> leftRotate(SBTNode<K, V> cur) {
			SBTNode<K, V> right = cur.r;
			cur.r = right.l;
			right.l = cur;

			right.size = cur.size;
			cur.size = (cur.l != null ? cur.l.size : 0) + (cur.r != null ? cur.r.size : 0) + 1;
			return right;
		}

		/**
		 * 平衡性的调整
		 *
		 * @param cur 以cur为头节点的整棵树
		 * @return 返回以cur为头节点的整棵树根据判断四种不平衡类型进行平衡性调整之后的新的头节点
		 */
		public SBTNode<K, V> maintain(SBTNode<K, V> cur) {
			//左边的叔叔节点的子树节点个数
			int leftSize = cur.l != null ? cur.l.size : 0;
			//右边的左侄子节点的子树节点个数
			int rightLeftSize = cur.r != null && cur.r.l != null ? cur.r.l.size : 0;
			//右边的右侄子节点的子树节点个数
			int rightRightSize = cur.r != null && cur.r.r != null ? cur.r.r.size : 0;

			//右边的叔叔节点的子树节点个数
			int rightSize = cur.r != null ? cur.r.size : 0;
			//左边的左侄子节点的子树节点个数
			int leftLeftSize = cur.l != null && cur.l.l != null ? cur.l.l.size : 0;
			//左边的右侄子节点的子树节点个数
			int leftRightSize = cur.l != null && cur.l.r != null ? cur.l.r.size : 0;

			//四种不平衡的类型：LL，LR，RR，RL
			//LL：左边的左侄子节点的子树节点个数 > 右边的叔叔节点的子树节点个数
			if (leftLeftSize > rightSize) {
				//以cur为头节点的整棵树进行右旋
				cur = rightRotate(cur);
				cur.r = maintain(cur.r);
				cur = maintain(cur);
			}
			//LR：左边的右侄子节点的子树节点个数 > 右边的叔叔节点的子树节点个数
			else if (leftRightSize > rightSize) {
				//以cur.left为头节点的整棵树进行左旋
				cur.l = leftRotate(cur.l);
				//以cur为头节点的整棵树进行右旋
				cur = rightRotate(cur);

				//cur节点、cur.left节点、cur.right节点的子节点都发生了变化，需要递归调整平衡性
				cur.l = maintain(cur.l);
				cur.r = maintain(cur.r);
				cur = maintain(cur);
			}
			//RR：右边的右侄子节点的子树节点个数 > 左边的叔叔节点的子树节点个数
			else if (rightRightSize > leftSize) {
				//以cur为头节点的整棵树进行左旋
				cur = leftRotate(cur);
				cur.l = maintain(cur.l);
				cur = maintain(cur);
			}
			//RL：右边的左侄子节点的子树节点个数 > 左边的叔叔节点的子树节点个数
			else if (rightLeftSize > leftSize) {
				//以cur.right为头节点的整棵树进行右旋
				cur.r = rightRotate(cur.r);
				//以cur为头节点的整棵树进行左旋
				cur = leftRotate(cur);

				//cur节点、cur.left节点、cur.right节点的子节点都发生了变化，需要递归调整平衡性
				cur.l = maintain(cur.l);
				cur.r = maintain(cur.r);
				cur = maintain(cur);
			}
			return cur;
		}

		/**
		 * 现在，以cur为头的树上，新增，加(key, value)这样的记录
		 * 加完之后，会对cur做检查，该调整调整
		 * 返回，调整完之后，整棵树的新头部
		 *
		 * @param cur
		 * @param key
		 * @param value
		 * @return
		 */
		public SBTNode<K, V> add(SBTNode<K, V> cur, K key, V value) {
			if (cur == null) {
				return new SBTNode<>(key, value);
			} else {
				//cur节点的子树节点个数 ++
				cur.size++;
				if (key.compareTo(cur.k) > 0) {
					cur.r = add(cur.r, key, value);
				} else {
					cur.l = add(cur.l, key, value);
				}
				//cur.size = (cur.l != null ? cur.l.size : 0) + (cur.r != null ? cur.r.size : 0) + 1;
				return maintain(cur);
			}
		}

		/**
		 * 在cur这棵树上，删掉key所代表的节点
		 * 返回cur这棵树的新头部
		 *
		 * @param cur
		 * @param key
		 * @return
		 */
		public SBTNode<K, V> delete(SBTNode<K, V> cur, K key) {
			//以cur为头节点的子树节点个数--
			cur.size--;
			//如果要删除的值大于cur节点的值，那么就删除cur节点的右子节点
			if (key.compareTo(cur.k) > 0) {
				cur.r = delete(cur.r, key);
			}
			//如果要删除的值小于cur节点的值，那么就删除cur节点的左子节点
			else if (key.compareTo(cur.k) < 0) {
				cur.l = delete(cur.l, key);
			}
			//否则，就是删除cur节点
			else {
				//如果cur节点左子节点和右子节点为null，直接cur = null;
				if (cur.l == null && cur.r == null) {
					cur = null;
				}
				//如果cur节点左子节点不为空，右子节点为空，左子节点替代cur节点
				else if (cur.l != null && cur.r == null) {
					cur = cur.l;
				}
				//如果cur节点左子节点为空，右子节点不为空，右子节点替代cur节点
				else if (cur.l == null && cur.r != null) {
					cur = cur.r;
				}
				//如果cur节点左子节点不为空，右子节点不为空，找到右子树的最左节点替代cur节点
				else {
					SBTNode<K, V> pre = null;
					//des来到cur节点的右子节点
					SBTNode<K, V> des = cur.r;
					//以des为头节点的子树节点个数--
					des.size--;
					while (des.l != null) {
						//pre指向des
						pre = des;
						//des指向des.left
						des = des.l;
						//des的子树节点个数--
						des.size--;
					}
					//此时des已经来到cur节点的右子树的最左子节点
					if (pre != null) {
						pre.l = des.r;
						des.r = cur.r;
					}
					des.l = cur.l;
					des.size = des.l.size + (des.r != null ? des.r.size : 0) + 1;
					cur = des;
				}

			}
			//cur = maintain(cur);
			return cur;
		}

		private SBTNode<K, V> getIndex(SBTNode<K, V> cur, int kth) {
			if (kth == (cur.l != null ? cur.l.size : 0) + 1) {
				return cur;
			} else if (kth <= (cur.l != null ? cur.l.size : 0)) {
				return getIndex(cur.l, kth);
			} else {
				return getIndex(cur.r, kth - (cur.l != null ? cur.l.size : 0) - 1);
			}
		}

		private SBTNode<K, V> findLastIndex(K key) {
			SBTNode<K, V> pre = root;
			SBTNode<K, V> cur = root;
			while (cur != null) {
				pre = cur;
				if (key.compareTo(cur.k) == 0) {
					break;
				} else if (key.compareTo(cur.k) < 0) {
					cur = cur.l;
				} else {
					cur = cur.r;
				}
			}
			return pre;
		}

		private SBTNode<K, V> findLastNoSmallIndex(K key) {
			SBTNode<K, V> ans = null;
			SBTNode<K, V> cur = root;
			while (cur != null) {
				if (key.compareTo(cur.k) == 0) {
					ans = cur;
					break;
				} else if (key.compareTo(cur.k) < 0) {
					ans = cur;
					cur = cur.l;
				} else {
					cur = cur.r;
				}
			}
			return ans;
		}

		private SBTNode<K, V> findLastNoBigIndex(K key) {
			SBTNode<K, V> ans = null;
			SBTNode<K, V> cur = root;
			while (cur != null) {
				if (key.compareTo(cur.k) == 0) {
					ans = cur;
					break;
				} else if (key.compareTo(cur.k) < 0) {
					cur = cur.l;
				} else {
					ans = cur;
					cur = cur.r;
				}
			}
			return ans;
		}

		public int size() {
			return root == null ? 0 : root.size;
		}

		public boolean containsKey(K key) {
			if (key == null) {
				throw new RuntimeException("invalid parameter.");
			}
			SBTNode<K, V> lastNode = findLastIndex(key);
			return lastNode != null && key.compareTo(lastNode.k) == 0;
		}

		// （key，value） put -> 有序表 新增、改value
		public void put(K key, V value) {
			if (key == null) {
				throw new RuntimeException("invalid parameter.");
			}
			SBTNode<K, V> lastNode = findLastIndex(key);
			if (lastNode != null && key.compareTo(lastNode.k) == 0) {
				lastNode.v = value;
			} else {
				root = add(root, key, value);
			}
		}

		public void remove(K key) {
			if (key == null) {
				throw new RuntimeException("invalid parameter.");
			}
			if (containsKey(key)) {
				root = delete(root, key);
			}
		}

		public K getIndexKey(int index) {
			if (index < 0 || index >= this.size()) {
				throw new RuntimeException("invalid parameter.");
			}
			return getIndex(root, index + 1).k;
		}

		public V getIndexValue(int index) {
			if (index < 0 || index >= this.size()) {
				throw new RuntimeException("invalid parameter.");
			}
			return getIndex(root, index + 1).v;
		}

		public V get(K key) {
			if (key == null) {
				throw new RuntimeException("invalid parameter.");
			}
			SBTNode<K, V> lastNode = findLastIndex(key);
			if (lastNode != null && key.compareTo(lastNode.k) == 0) {
				return lastNode.v;
			} else {
				return null;
			}
		}

		public K firstKey() {
			if (root == null) {
				return null;
			}
			SBTNode<K, V> cur = root;
			while (cur.l != null) {
				cur = cur.l;
			}
			return cur.k;
		}

		public K lastKey() {
			if (root == null) {
				return null;
			}
			SBTNode<K, V> cur = root;
			while (cur.r != null) {
				cur = cur.r;
			}
			return cur.k;
		}

		public K floorKey(K key) {
			if (key == null) {
				throw new RuntimeException("invalid parameter.");
			}
			SBTNode<K, V> lastNoBigNode = findLastNoBigIndex(key);
			return lastNoBigNode == null ? null : lastNoBigNode.k;
		}

		public K ceilingKey(K key) {
			if (key == null) {
				throw new RuntimeException("invalid parameter.");
			}
			SBTNode<K, V> lastNoSmallNode = findLastNoSmallIndex(key);
			return lastNoSmallNode == null ? null : lastNoSmallNode.k;
		}
	}

	public static void main(String[] args) {
		SizeBalancedTreeMap<String, Integer> sbt = new SizeBalancedTreeMap<String, Integer>();
		sbt.put("d", 4);
		sbt.put("c", 3);
		sbt.put("a", 1);
		sbt.put("b", 2);
		// sbt.put("e", 5);
		sbt.put("g", 7);
		sbt.put("f", 6);
		sbt.put("h", 8);
		sbt.put("i", 9);
		sbt.put("a", 111);
		System.out.println(sbt.get("a"));
		sbt.put("a", 1);
		System.out.println(sbt.get("a"));
		for (int i = 0; i < sbt.size(); i++) {
			System.out.println(sbt.getIndexKey(i) + " , " + sbt.getIndexValue(i));
		}
		OrderedTableUtils.printAll(sbt.root);
		System.out.println(sbt.firstKey());
		System.out.println(sbt.lastKey());
		System.out.println(sbt.floorKey("g"));
		System.out.println(sbt.ceilingKey("g"));
		System.out.println(sbt.floorKey("e"));
		System.out.println(sbt.ceilingKey("e"));
		System.out.println(sbt.floorKey(""));
		System.out.println(sbt.ceilingKey(""));
		System.out.println(sbt.floorKey("j"));
		System.out.println(sbt.ceilingKey("j"));
		sbt.remove("d");
		OrderedTableUtils.printAll(sbt.root);
		sbt.remove("f");
		OrderedTableUtils.printAll(sbt.root);
	}
}
