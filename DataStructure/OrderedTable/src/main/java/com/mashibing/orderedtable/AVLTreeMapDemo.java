package com.mashibing.orderedtable;

/**
 * @author xcy
 * @date 2022/5/29 - 18:13
 */
class AVLTreeMapDemo {

	/**
	 * AVL树的记录必须是可以比较的，否则就不是有序表了
	 *
	 * @param <K>
	 * @param <V>
	 */
	public static class AVLNode<K extends Comparable<K>, V> {
		public K k;
		public V v;

		/**
		 * 左子节点
		 */
		public AVLNode<K, V> l;
		/**
		 * 右子节点
		 */
		public AVLNode<K, V> r;
		/**
		 * 节点所在的树的深度
		 */
		public int h;

		public AVLNode(K k, V v) {
			this.k = k;
			this.v = v;
			h = 1;
		}
	}


	/**
	 * AVL树实现有序表
	 *
	 * @param <K>
	 * @param <V>
	 */
	public static class AVLTreeMap<K extends Comparable<K>, V> {
		public AVLNode<K, V> root;
		public int size;

		public AVLTreeMap() {
			root = null;
			size = 0;
		}

		/**
		 * 返回有序表的大小
		 *
		 * @return
		 */
		public int size() {
			return this.size;
		}

		/**
		 * AVL树维持平衡的很重要的因素就是树的深度,所以每一次左旋,每一次右旋,每一次节点相对位置发生调整,都必须要更新平衡因素
		 * 保证每一个节点里都是平衡的
		 *
		 * @param cur
		 * @return
		 */
		private AVLNode<K, V> leftRotate(AVLNode<K, V> cur) {
			if (cur == null) {
				return null;
			}
			AVLNode<K, V> right = cur.r;
			cur.r = right.l;
			right.l = cur;

			cur.h = Math.max((cur.l != null ? cur.l.h : 0), (cur.r != null ? cur.r.h : 0)) + 1;
			right.h = Math.max((right.l != null ? right.l.h : 0), (right.r != null ? right.r.h : 0)) + 1;
			return right;
		}

		/**
		 * AVL树维持平衡的很重要的因素就是树的深度,所以每一次左旋,每一次右旋,每一次节点相对位置发生调整,都必须要更新平衡因素
		 * 保证每一个节点里都是平衡的
		 *
		 * @param cur
		 * @return
		 */
		private AVLNode<K, V> rightRotate(AVLNode<K, V> cur) {
			if (cur == null) {
				return null;
			}
			AVLNode<K, V> left = cur.l;
			cur.l = left.r;
			left.r = cur;

			cur.h = Math.max((cur.l != null ? cur.l.h : 0), (cur.r != null ? cur.r.h : 0)) + 1;
			left.h = Math.max((left.l != null ? left.l.h : 0), (left.r != null ? left.r.h : 0)) + 1;
			return left;
		}

		/**
		 * 平衡性调整的方法maintain()，返回调整之后的新的头节点
		 * //左子树的深度
		 * //右子树的深度
		 * //左子树和右子树的深度的差值(绝对值) > 1，表示出现了不平衡
		 * //判断是左子树的深度深，还是右子树的深度深
		 * //左子树的深度深，有两种情况，LL，LR
		 * //如果左子树中的左子树大于等于右子树的深度，LL和LR都归为LL，进行右旋
		 * //否则，两次旋转，先对cur.l进行左旋，然后cur为头节点的整棵树右旋
		 * //右子树的深度深，有两种情况，RR，RL
		 * //如果右子树中的右子树大于等于左子树的深度，RR和RL都归为RR，进行左旋
		 * //否则，两次旋转，先对cur.r进行右旋，然后cur为头节点的整棵树左旋
		 *
		 * @param cur
		 * @return
		 */
		private AVLNode<K, V> maintain(AVLNode<K, V> cur) {
			if (cur == null) {
				return null;
			}
			//左子树的深度
			int leftHeight = cur.l != null ? cur.l.h : 0;
			//右子树的深度
			int rightHeight = cur.r != null ? cur.r.h : 0;
			//左子树和右子树的深度的差值(绝对值) > 1，表示出现了不平衡
			if (Math.abs(leftHeight - rightHeight) > 1) {
				//判断是左子树的深度深，还是右子树的深度深
				//如果左子树的深度深，有两种情况，LL，LR
				if (leftHeight > rightHeight) {
					int leftLeftHeight = cur.l != null && cur.l.l != null ? cur.l.l.h : 0;
					int leftRightHeight = cur.l != null && cur.l.r != null ? cur.l.r.h : 0;
					//如果左子树中的左子树大于等于右子树的深度，LL和LR都归为LL，进行右旋
					if (leftLeftHeight >= leftRightHeight) {
						cur = rightRotate(cur);
					}
					//否则，两次旋转，先对cur.l进行左旋，然后cur为头节点的整棵树右旋
					else {
						cur.l = leftRotate(cur.l);
						cur = rightRotate(cur);
					}
				}
				//如果右子树的深度深，有两种情况，RR，RL
				else {
					int rightLeftHeight = cur.r != null && cur.r.l != null ? cur.r.l.h : 0;
					int rightRightHeight = cur.r != null && cur.r.r != null ? cur.r.r.h : 0;
					//如果右子树中的右子树大于等于左子树的深度，RR和RL都归为RR，进行左旋
					if (rightRightHeight >= rightLeftHeight) {
						cur = leftRotate(cur);
					} else {
						//否则，两次旋转，先对cur.r进行右旋，然后cur为头节点的整棵树左旋
						cur.r = rightRotate(cur.r);
						cur = leftRotate(cur);
					}
				}
			}
			return cur;
		}


		/**
		 * add() --> 添加节点
		 * 假设添加的记录：key是5，value是"五"
		 * 如果cur节点为null，直接创建新节点
		 * 如果添加的值比cur节点的值小，那么就在cur节点的左子树上创建左子节点
		 * 如果添加的值比cur节点的值大，那么就在cur节点的右子树上创建右子节点
		 *
		 * @param cur 当前节点
		 * @param k   key
		 * @param v   value
		 * @return 返回添加节点进行平衡性调整的之后的新的头节点
		 */
		private AVLNode<K, V> add(AVLNode<K, V> cur, K k, V v) {
			if (cur == null) {
				return new AVLNode<K, V>(k, v);
			} else {
				//如果添加的值比cur节点的值小，那么就在cur节点的左子树上创建左子节点
				if (k.compareTo(cur.k) > 0) {
					cur.r = new AVLNode<>(k, v);
				}
				//如果添加的值比cur节点的值大，那么就在cur节点的右子树上创建右子节点
				else {
					cur.l = new AVLNode<>(k, v);
				}
				cur.h = Math.max(cur.l != null ? cur.l.h : 0, cur.r != null ? cur.r.h : 0) + 1;
				return maintain(cur);
			}
		}

		/**
		 * 假设要删除的值：key
		 * 如果要删除的值小于cur节点的值，那么就删除cur节点的左子节点
		 * 如果要删除的值大于cur节点的值，那么就删除cur节点的右子节点
		 * 否则，就是删除cur节点
		 * 如果cur节点左子节点和右子节点为null，直接cur = null;
		 * 如果cur节点左子节点为空，右子节点不为空，右子节点替代cur节点
		 * 如果cur节点左子节点不为空，右子节点为空，左子节点替代cur节点
		 * 如果cur节点左子节点不为空，右子节点不为空，找到右子树的最左节点替代cur节点
		 * des = cur.r
		 * while(des.l != null) {
		 * des = des.l;
		 * }
		 * //在右子树上删除最左子节点并返回新的头节点
		 * cur.r = delete(cur.r, des.k);
		 * //先不在cur为头节点的树上删除K节点，而是在delete()中在cur的右子树上以Y为头节点的子树上删除K节点，
		 * delete(cur.r, des.k)包含了以Y为头节点的整棵树删除K之后的以Y为头节点的整棵树的平衡性调整，返回删除点的节点K
		 * <p>
		 * //K节点的l指向cur节点的l，K节点的r指向cur节点的r，又因为删除K节点时，cur节点的左子树已经完成了平衡性调整，
		 * 所以直接从此时的K节点往上进行平衡性调整，就不需要重复查询K节点的整颗右子树的平衡性调整了
		 * des.l = cur.l;
		 * des.r = cur.r;
		 * //cur节点来到K节点的位置
		 * cur = des;
		 * <p>
		 * 更新cur的h
		 * 每在一个节点上delete的时候，都会查询以自己为头节点的整棵树的平衡性调整
		 *
		 * @param cur 当前节点
		 * @param key 要删除的值
		 * @return 根据要删除的值删除节点之后返回新的cur节点
		 */
		private AVLNode<K, V> delete(AVLNode<K, V> cur, K key) {
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
					//des来到cur节点的右子节点
					AVLNode<K, V> des = cur.r;
					//如果des的左子节点不为空，就继续找下一个左子节点
					while (des.l != null) {
						des = des.l;
					}
					//在右子树上删除最左子节点并返回新的头节点
					//     X
					//   /   \
					//left    Y
					//       /
					//      Z
					//     /
					//    K
					//先不在cur为头节点的树上删除K节点，而是在delete()中在cur的右子树上以Y为头节点的子树上删除K节点，
					//delete(cur.r, des.k)包含了以Y为头节点的整棵树删除K之后的以Y为头节点的整棵树的平衡性调整，
					//返回删除的K节点
					cur.r = delete(cur.r, des.k);

					//K节点的l指向cur节点的l，K节点的r指向cur节点的r，又因为删除K节点时，cur节点的左子树已经完成了平衡性调整，
					//所以直接从此时的K节点往上进行平衡性调整，就不需要重复查询K节点的整颗右子树的平衡性调整了
					des.l = cur.l;
					des.r = cur.r;
					//cur节点来到K节点的位置
					cur = des;
				}
			}
			//更新以cur为头节点的整棵树的深度
			if (cur != null) {
				cur.h = Math.max((cur.l != null ? cur.l.h : 0), (cur.r != null ? cur.r.h : 0)) + 1;
			}
			//每在一个节点上delete的时候，都会查询以自己为头节点的整棵树的平衡性调整
			return maintain(cur);
		}

		private AVLNode<K, V> findLastIndex(K key) {
			AVLNode<K, V> pre = root;
			AVLNode<K, V> cur = root;

			while (cur != null) {
				pre = cur;
				if (key.compareTo(cur.k) == 0) {
					break;
				} else if (key.compareTo(cur.k) > 0) {
					cur = cur.r;
				} else {
					cur = cur.l;
				}
			}
			return pre;
		}

		private AVLNode<K, V> findLastNoSmallIndex(K key) {
			AVLNode<K, V> ans = null;
			AVLNode<K, V> cur = root;
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

		private AVLNode<K, V> findLastNoBigIndex(K key) {
			AVLNode<K, V> ans = null;
			AVLNode<K, V> cur = root;
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

		public boolean containsKey(K key) {
			if (key == null) {
				return false;
			}
			AVLNode<K, V> lastNode = findLastIndex(key);
			return lastNode != null && key.compareTo(lastNode.k) == 0;
		}

		public void put(K key, V value) {
			if (key == null) {
				return;
			}
			AVLNode<K, V> lastNode = findLastIndex(key);
			if (lastNode != null && key.compareTo(lastNode.k) == 0) {
				lastNode.v = value;
			} else {
				size++;
				root = add(root, key, value);
			}
		}

		public void remove(K key) {
			if (key == null) {
				return;
			}
			if (containsKey(key)) {
				size--;
				root = delete(root, key);
			}
		}

		public V get(K key) {
			if (key == null) {
				return null;
			}
			AVLNode<K, V> lastNode = findLastIndex(key);
			if (lastNode != null && key.compareTo(lastNode.k) == 0) {
				return lastNode.v;
			}
			return null;
		}

		public K firstKey() {
			if (root == null) {
				return null;
			}
			AVLNode<K, V> cur = root;
			while (cur.l != null) {
				cur = cur.l;
			}
			return cur.k;
		}

		public K lastKey() {
			if (root == null) {
				return null;
			}
			AVLNode<K, V> cur = root;
			while (cur.r != null) {
				cur = cur.r;
			}
			return cur.k;
		}

		public K floorKey(K key) {
			if (key == null) {
				return null;
			}
			AVLNode<K, V> lastNoBigNode = findLastNoBigIndex(key);
			return lastNoBigNode == null ? null : lastNoBigNode.k;
		}

		public K ceilingKey(K key) {
			if (key == null) {
				return null;
			}
			AVLNode<K, V> lastNoSmallNode = findLastNoSmallIndex(key);
			return lastNoSmallNode == null ? null : lastNoSmallNode.k;
		}
	}
}
