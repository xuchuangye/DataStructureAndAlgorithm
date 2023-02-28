package com.mashibing.iterviews;

import java.util.ArrayList;

/**
 * @author xcy
 * @date 2022/6/2 - 9:52
 */
public class AddRemoveGetIndexGreat {
	//通过以下这个测试，
	//可以很明显的看到LinkedList的插入、删除、get效率不如SbtList
	//LinkedList需要找到index所在的位置之后才能插入或者读取，时间复杂度O(N)
	//SbtList是平衡搜索二叉树，所以插入或者读取时间复杂度都是O(logN)
	public static void main(String[] args) {
		// 功能测试
		int test = 50000;
		int max = 1000000;
		boolean pass = true;
		ArrayList<Integer> list = new ArrayList<>();
		SizeBalancedTreeList<Integer> sbtList = new SizeBalancedTreeList<>();
		for (int i = 0; i < test; i++) {
			if (list.size() != sbtList.size()) {
				pass = false;
				break;
			}
			if (list.size() > 1 && Math.random() < 0.5) {
				int removeIndex = (int) (Math.random() * list.size());
				list.remove(removeIndex);
				sbtList.remove(removeIndex);
			} else {
				int randomIndex = (int) (Math.random() * (list.size() + 1));
				int randomValue = (int) (Math.random() * (max + 1));
				list.add(randomIndex, randomValue);
				sbtList.add(randomIndex, randomValue);
			}
		}
		for (int i = 0; i < list.size(); i++) {
			if (!list.get(i).equals(sbtList.get(i))) {
				pass = false;
				break;
			}
		}
		System.out.println("功能测试是否通过 : " + pass);

		// 性能测试
		test = 500000;
		list = new ArrayList<>();
		sbtList = new SizeBalancedTreeList<>();
		long start = 0;
		long end = 0;

		start = System.currentTimeMillis();
		for (int i = 0; i < test; i++) {
			int randomIndex = (int) (Math.random() * (list.size() + 1));
			int randomValue = (int) (Math.random() * (max + 1));
			list.add(randomIndex, randomValue);
		}
		end = System.currentTimeMillis();
		System.out.println("ArrayList插入总时长(毫秒) ： " + (end - start));

		start = System.currentTimeMillis();
		for (int i = 0; i < test; i++) {
			int randomIndex = (int) (Math.random() * (i + 1));
			list.get(randomIndex);
		}
		end = System.currentTimeMillis();
		System.out.println("ArrayList读取总时长(毫秒) : " + (end - start));

		start = System.currentTimeMillis();
		for (int i = 0; i < test; i++) {
			int randomIndex = (int) (Math.random() * list.size());
			list.remove(randomIndex);
		}
		end = System.currentTimeMillis();
		System.out.println("ArrayList删除总时长(毫秒) : " + (end - start));

		start = System.currentTimeMillis();
		for (int i = 0; i < test; i++) {
			int randomIndex = (int) (Math.random() * (sbtList.size() + 1));
			int randomValue = (int) (Math.random() * (max + 1));
			sbtList.add(randomIndex, randomValue);
		}
		end = System.currentTimeMillis();
		System.out.println("SizeBalancedTreeList插入总时长(毫秒) : " + (end - start));

		start = System.currentTimeMillis();
		for (int i = 0; i < test; i++) {
			int randomIndex = (int) (Math.random() * (i + 1));
			sbtList.get(randomIndex);
		}
		end = System.currentTimeMillis();
		System.out.println("SizeBalancedTreeList读取总时长(毫秒) :  " + (end - start));

		start = System.currentTimeMillis();
		for (int i = 0; i < test; i++) {
			int randomIndex = (int) (Math.random() * sbtList.size());
			sbtList.remove(randomIndex);
		}
		end = System.currentTimeMillis();
		System.out.println("SizeBalancedTreeList删除总时长(毫秒) :  " + (end - start));
	}

	/**
	 * 没有参与比较器排序的K extends Comparable<K>
	 * 因为是隐含在这棵树的自然时序里
	 *
	 * @param <V>
	 */
	public static class SBTNode<V> {
		public V value;
		public SBTNode<V> l;
		public SBTNode<V> r;

		/**
		 * SizeBalancedTree的平衡因素
		 * 也参与业务，因为每个数都是不一样的
		 */
		public int size;

		public SBTNode(V value) {
			this.value = value;
			size = 1;
		}
	}

	public static class SizeBalancedTreeList<V> {
		public SBTNode<V> root;

		public SBTNode<V> rightRotate(SBTNode<V> cur) {
			//    cur
			//   /
			// right
			// /   \
			//o     o
			SBTNode<V> right = cur.l;
			cur.l = right.r;
			right.r = cur;

			right.size = cur.size;
			cur.size = (cur.l != null ? cur.l.size : 0) + (cur.r != null ? cur.r.size : 0) + 1;
			return right;
		}

		public SBTNode<V> leftRotate(SBTNode<V> cur) {
			// cur
			//   \
			//   left
			//  /    \
			// o      o
			SBTNode<V> left = cur.r;
			cur.r = left.l;
			left.l = cur;

			left.size = cur.size;
			cur.size = (cur.l != null ? cur.l.size : 0) + (cur.r != null ? cur.r.size : 0) + 1;
			return left;
		}

		public SBTNode<V> maintain(SBTNode<V> cur) {
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

			//SizeBalancedTree的四种不平衡类型：LL、LR、RR、RL
			//LL：左边的左侄子节点的子树节点个数 大于 右边的叔叔节点的子树节点个数
			if (leftLeftSize > rightSize) {
				//以cur为头节点的整棵树进行右旋
				cur = rightRotate(cur);
				//递归调整cur.r和cur
				cur.r = maintain(cur.r);
				cur = maintain(cur);
			}
			//LR：左边的右侄子节点的子树节点个数 大于 右边的叔叔节点的子树节点个数
			else if (leftRightSize > rightSize) {
				//以cur.l为头节点的整棵树进行左旋
				cur.l = leftRotate(cur.l);
				//以cur为头节点的整棵树进行右旋
				cur = rightRotate(cur);

				//递归调整cur.l、cur.r、cur
				cur.l = maintain(cur.l);
				cur.r = maintain(cur.r);
				cur = maintain(cur);
			}
			//RR：右边的右侄子节点的子树节点个数 大于 左边的叔叔节点的子树节点个数
			else if (rightRightSize > leftSize) {
				//以cur为头节点的整棵树进行左旋
				cur = leftRotate(cur);

				//递归调整cur.l和cur
				cur.l = maintain(cur.l);
				cur = maintain(cur);
			}
			//RL：右边的左侄子节点的子树节点个数 大于 左边的叔叔节点的子树节点个数
			else if (rightLeftSize > leftSize) {
				//以cur.r为头节点的整棵树进行右旋
				cur.r = rightRotate(cur.r);
				//以cur为头节点的整棵树进行左旋
				cur = leftRotate(cur);

				//递归调整cur.l、cur.r、cur
				cur.l = maintain(cur.l);
				cur.r = maintain(cur.r);
				cur = maintain(cur);
			}
			return cur;
		}

		public void add(int index, V value) {
			//将value值进行包装
			SBTNode<V> cur = new SBTNode<V>(value);
			//如果头节点为空，cur作为头节点
			if (root == null) {
				root = cur;
			}
			//如果头节点不为空
			else {
				//并且index 小于等于 头节点的子树节点个数
				if (index <= root.size) {
					//就将cur节点挂在头节点的子树上
					root = add(root, index, cur);
				}
			}
		}

		/**
		 * 将index位置的节点挂在整棵树上
		 *
		 * @param root
		 * @param index
		 * @param cur   cur节点已经创建好了，不需要检查key存在不存在，每个节点的内存地址key都是不同的
		 * @return
		 */
		private SBTNode<V> add(SBTNode<V> root, int index, SBTNode<V> cur) {
			if (root == null) {
				return cur;
			}
			//size和all合并
			root.size++;
			//左边以及root节点的自然序 = root节点左子树收集节点数 + root节点自己
			int naturalSequence = (root.l != null ? root.l.size : 0) + 1;
			if (index < naturalSequence) {
				root.l = add(root.l, index, cur);
			} else {
				root.r = add(root.r, index - naturalSequence, cur);
			}
			root = maintain(root);
			return root;
		}

		public int size() {
			return root != null ? this.root.size : 0;
		}

		public void remove(int index) {
			if (index >= 0 && index < size()) {
				root = remove(root, index);
			}
		}

		private SBTNode<V> remove(SBTNode<V> root, int index) {
			root.size--;
			//表示root节点的自然序
			int rootNaturalSequence = root.l != null ? root.l.size : 0;

			//index 不等于 root节点的自然序，要么在左边，要么在右边
			if (index != rootNaturalSequence) {
				//表示index在左边，去左边删除
				if (index < rootNaturalSequence) {
					root.l = remove(root.l, index);
				}
				//表示index在右边，去右边删除，右边的自然序 = index - 左边的自然序 - 根节点的位置
				else {
					root.r = remove(root.r, index - rootNaturalSequence - 1);
				}
				return root;
			}

			if (root.l == null && root.r == null) {
				return null;
			}
			if (root.l == null) {
				return root.r;
			}
			if (root.r == null) {
				return root.l;
			}
			SBTNode<V> pre = null;
			SBTNode<V> des = root.r;
			des.size--;
			while (des.l != null) {
				pre = des;
				des = des.l;
				des.size--;
			}

			if (pre != null) {
				pre.l = des.r;
				des.r = root.r;
			}
			des.l = root.l;
			des.size = des.l.size + (des.r != null ? des.r.size : 0) + 1;
			return des;
		}

		private SBTNode<V> get(SBTNode<V> root, int index) {
			int leftSize = root.l != null ? root.l.size : 0;
			if (index < leftSize) {
				assert root.l != null;
				return get(root.l, index);
			} else if (index == leftSize) {
				return root;
			} else {
				return get(root.r, index - leftSize - 1);
			}
		}
		public V get(int index) {
			SBTNode<V> ans = get(root, index);
			return ans.value;
		}
	}
}
