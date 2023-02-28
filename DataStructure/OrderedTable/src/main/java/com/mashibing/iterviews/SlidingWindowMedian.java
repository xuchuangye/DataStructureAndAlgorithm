package com.mashibing.iterviews;


/**
 * 题目二：
 * 有一个滑动窗口（讲过的）：
 * <p>
 * 1）L是滑动窗口最左位置、R是滑动窗口最右位置，一开始LR都在数组左侧
 * 2）任何一步都可能R往右动，表示某个数进了窗口
 * 3）任何一步都可能L往右动，表示某个数出了窗口
 * <p>
 * 想知道每一个窗口状态的中位数
 *
 * @author xcy
 * @date 2022/6/1 - 7:59
 */
public class SlidingWindowMedian {
	public static void main(String[] args) {

	}

	public static class Node implements Comparable<Node> {
		public int index;
		public int value;

		public Node(int index, int value) {
			this.index = index;
			this.value = value;
		}

		@Override
		public int compareTo(Node node) {
			return this.value != node.value ? Integer.compare(value, node.value)
					: Integer.compare(index, node.index);
		}
	}

	/**
	 * 使用有序表的思路分析：
	 * <p>
	 * 1.准备一个容器，这个容器可以接收数字，支持单个删除
	 * 2.如果得到中位数，这个结构容器的长度N，N为奇数，中间一个数，N为偶数，中间两个数的和 / 2
	 * 3.这个容器可以加入重复数字，支持单个删除，获取index位置的数字
	 * 4.插入排序的时间复杂度：O(N)，有序表的时间复杂度：O(logN)
	 * 5.有序表如何实现添加重复数字
	 * 在节点上添加一个属性，这个属性表示收集了多少个数
	 * 1)第一步，添加5，创建5的头节点，此时收集了1个节点数
	 * 2)第二步，添加3，3比5小，创建左子节点，此时左子节点3收集了1个节点数，父节点5收集了2个节点数
	 * 3)第三步，添加6，6比5大，创建右子节点，此时右子节点6收集了1个节点数，父节点5收集了3个节点数
	 * 4)第四步，添加5，此时父节点5收集了4个节点数
	 * 5)第五步，添加4，4比5小，比3大，创建3的右子节点，此时祖父节点5收集了5个节点数，父节点3收集了2个节点数，4节点收集1个节点数
	 * 6)当前节点的个数 = 当前节点收集的节点数 - (当前节点的左子节点收集的节点数 + 当前节点的右子节点收集的节点数)
	 * -      5 <- 当前节点的值
	 * -      5 <- 当前节点收集的节点数
	 * -    /  \
	 * -   3    6
	 * -   2    1
	 * -    \
	 * -     4
	 * -     1
	 * 5节点的个数 = 5节点收集5个节点数 - (3节点收集2个节点数 + 6节点收集1个节点数) = 2，所以5节点的个数为2
	 * 6.有序表如何实现删除重复数字
	 * 1)第一步：删除3，父节点5收集的节点数 - 1，3节点收集的节点数 - 1
	 * 2)第二步：删除3，父节点5收集的节点数 - 1，3节点收集的节点数 - 1，此时3节点收集节点数为0，删除3节点，调整平衡性
	 * 7.有序表如何查询index位置的数是多少
	 * -      5  <- 当前节点的值
	 * -     100  <- 当前节点收集的节点数
	 * -    /   \
	 * -   3     50
	 * -  10     60
	 * -       /   \
	 * -      30    70
	 * -      10    20
	 * -    /   \
	 * -  20    40
	 * -   2     5
	 * 1)查询27位置的数，来到当前节点5，左子节点3收集的节点数10个，右子节点50收集的节点数60个，当前节点5的个数：100 - 60 - 10
	 * 表示小于5的有10个，等于5的有30个，大于5的有60个
	 * 所以小于5的有10个，等于5的有30个，27位置的数一定是5
	 * 2)查询77位置的数，来到当前节点5，左子节点3收集的节点数10个，右子节点50收集的节点数60个，当前节点5的个数：100 - 60 - 10
	 * 表示小于5的有10个，等于5的有30个，大于5的有60个
	 * 所以77位置的数一定在大于5的60个数的其中一个，也就是77 - 等于5的30个 - 小于5的10个，大于5的第37位置的数
	 * 来到当前节点50，左子节点30收集的节点数10个，右子节点70收集的节点数20个，当前节点50的个数：60 - 10 - 20 = 30
	 * 表示小于50的有10个，等于50的有30个，大于50的有20个
	 * 所以小于50的有10个，等于50的有30个，77位置的数一定是50
	 * 8.取出窗口的中位数，假设有2个数，调用两次index()，取出第1小和第2小，计算累加和之后除以2就是中位数
	 * 假设有3个数，调用一次index()，取出第3 / 2 + 1个数，这个数就是中位数
	 *
	 * @param nums
	 * @param K
	 * @return
	 */
	public static double[] medianSlidingWindow(int[] nums, int K) {
		if (nums == null || nums.length == 0) {
			return null;
		}
		SizeBalancedTreeMap<Node> map = new SizeBalancedTreeMap<>();
		//创建长度为K的窗口
		for (int i = 0; i < K - 1; i++) {
			map.add(new Node(i, nums[i]));
		}
		//准备中位数的数组
		double[] ans = new double[nums.length - K + 1];
		int index = 0;
		for (int i = K - 1; i < nums.length; i++) {
			//每次遍历时，添加一个
			map.add(new Node(i, nums[i]));
			//如果是偶数个，调用两次index()
			if (map.size() % 2 == 0) {
				//取出两个值，累加之后除以2，放入中位数数组中
				Node first = map.getIndexKey(map.size() / 2 + 1);
				Node next = map.getIndexKey(map.size() / 2);
				ans[index++] = ((double) first.value + (double) next.value) / 2;
			}
			//如果是奇数个，之调用依次index()
			else {
				//取出值，直接放入中位数数组中
				Node infix = map.getIndexKey(map.size() / 2 + 1);
				ans[index++] = (double) infix.value;
			}
			//结尾删除一个
			map.remove(new Node(i - K + 1, nums[i - K + 1]));
		}
		return ans;
	}

	/**
	 * SizeBalancedTree的节点
	 */
	public static class SBTNode<K extends Comparable<K>> {
		public K key;
		public SBTNode<K> l;
		public SBTNode<K> r;

		public int size;
		//public long all;

		public SBTNode(K key) {
			this.key = key;
			size = 1;
			//all = 1;
		}
	}

	/**
	 * SizeBalancedTree
	 * @param <K>
	 */
	public static class SizeBalancedTreeMap<K extends Comparable<K>> {
		public SBTNode<K> root;

		/**
		 * 右旋
		 *
		 * @param cur
		 * @return
		 */
		public SBTNode<K> rightRotate(SBTNode<K> cur) {
			//    cur
			//    /
			//  right
			//  /  \
			// o    o
			SBTNode<K> right = cur.l;
			cur.l = right.r;
			right.r = cur;

			right.size = cur.size;
			cur.size = (cur.l != null ? cur.l.size : 0) + (cur.r != null ? cur.r.size : 0) + 1;

			return right;
		}

		/**
		 * 左旋
		 *
		 * @param cur
		 * @return
		 */
		public SBTNode<K> leftRotate(SBTNode<K> cur) {
			// cur
			//   \
			//   left
			//  /    \
			// o      o
			SBTNode<K> left = cur.r;
			cur.r = left.l;
			left.l = cur;

			left.size = cur.size;
			cur.size = (cur.l != null ? cur.l.size : 0) + (cur.r != null ? cur.r.size : 0) + 1;

			return left;
		}

		/**
		 * SizeBalancedTree针对四种平衡性的调整
		 *
		 * @param cur
		 * @return
		 */
		public SBTNode<K> maintain(SBTNode<K> cur) {
			if (cur == null) {
				return null;
			}
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

			//SizeBalanced树的四种不平衡类型：LL、LR、RR、RL
			//LL：左边的左侄子节点的子树节点个数 大于 右边叔叔节点的子树节点个数
			if (leftLeftSize > rightSize) {
				cur = rightRotate(cur);
				cur.r = maintain(cur.r);
				cur = maintain(cur);
			}
			//LR：左边的右侄子节点的子树节点个数 大于 右边叔叔节点的子树节点个数
			else if (leftRightSize > rightSize) {
				cur.l = leftRotate(cur.l);
				cur = rightRotate(cur);

				cur.l = maintain(cur.l);
				cur.r = maintain(cur.r);
				cur = maintain(cur);
			}
			//RR：右边的右侄子节点的子树节点个数 大于 左边叔叔节点的子树节点个数
			else if (rightRightSize > leftSize) {
				cur = leftRotate(cur);
				cur.l = maintain(cur.l);
				cur = maintain(cur);
			}
			//RL：右边的左侄子节点的子树节点个数 大于左边叔叔节点的子树节点个数
			else if (rightLeftSize > leftSize) {
				cur.r = rightRotate(cur.r);
				cur = leftRotate(cur);

				cur.l = maintain(cur.l);
				cur.r = maintain(cur.r);
				cur = maintain(cur);
			}
			return cur;
		}

		/**
		 * 根据指定key值，添加节点
		 *
		 * @param cur
		 * @param key
		 * @return
		 */
		public SBTNode<K> add(SBTNode<K> cur, K key) {
			if (cur == null) {
				return new SBTNode<>(key);
			} else {
				cur.size++;
				if (key.compareTo(cur.key) < 0) {
					cur.l = add(cur.l, key);
				} else {
					cur.r = add(cur.r, key);
				}
				return maintain(cur);
			}
		}

		/**
		 * 根据指定key值，删除节点
		 *
		 * @param cur
		 * @param key
		 * @return
		 */
		public SBTNode<K> delete(SBTNode<K> cur, K key) {
			cur.size--;
			if (key.compareTo(cur.key) > 0) {
				cur.r = delete(cur.r, key);
			} else if (key.compareTo(cur.key) < 0) {
				cur.l = delete(cur.l, key);
			} else {
				if (cur.l == null && cur.r == null) {
					cur = null;
				} else if (cur.l != null && cur.r == null) {
					cur = cur.l;
				} else if (cur.l == null && cur.r != null) {
					cur = cur.r;
				} else {
					SBTNode<K> pre = null;
					SBTNode<K> des = cur.r;
					des.size--;
					while (des.l != null) {
						pre = des;
						des = des.l;
						des.size--;
					}

					if (pre != null) {
						pre.l = des.r;
						des.r = cur.r;
					}

					des.l = cur.l;
					des.size = des.l.size + (des.r != null ? des.r.size : 0) + 1;
					cur = des;
				}
			}
			return cur;
		}

		private SBTNode<K> findLastIndex(K key) {
			SBTNode<K> pre = root;
			SBTNode<K> cur = root;
			while (cur != null) {
				pre = cur;
				if (key.compareTo(cur.key) == 0) {
					break;
				} else if (key.compareTo(cur.key) < 0) {
					cur = cur.l;
				} else {
					cur = cur.r;
				}
			}
			return pre;
		}

		private SBTNode<K> getIndex(SBTNode<K> cur, int kth) {
			if (kth == (cur.l != null ? cur.l.size : 0) + 1) {
				return cur;
			} else if (kth <= (cur.l != null ? cur.l.size : 0)) {
				return getIndex(cur.l, kth);
			} else {
				return getIndex(cur.r, kth - (cur.l != null ? cur.l.size : 0) - 1);
			}
		}

		public int size() {
			return root != null ? root.size : 0;
		}

		public boolean containsKey(K key) {
			if (key == null) {
				throw new RuntimeException("invalid parameter.");
			}
			SBTNode<K> lastNode = findLastIndex(key);
			return lastNode != null && key.compareTo(lastNode.key) == 0;
		}

		public void add(K key) {
			if (key == null) {
				throw new RuntimeException("invalid parameter.");
			}
			SBTNode<K> lastNode = findLastIndex(key);
			if (lastNode == null || key.compareTo(lastNode.key) != 0) {
				root = add(root, key);
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
			return getIndex(root, index + 1).key;
		}
	}
}
