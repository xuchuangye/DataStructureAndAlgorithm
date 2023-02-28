package com.mashibing.day21;

import java.util.HashMap;

/**
 * 树链剖分专题
 * 给定数组father，大小为N，表示一共有N个节点
 * father[i] = j 表示点i的父亲是点j， father表示的树一定是一棵树而不是森林
 * 给定数组values，大小为N，values[i]=v表示节点i的权值是v
 * 实现如下4个方法，保证4个方法都很快！
 * 1)让某个子树所有节点值加上v，入参：int head, int v
 * 2)查询某个子树所有节点值的累加和，入参：int head
 * 3)在树上从a到b的整条链上所有加上v，入参：int a, int b, int v
 * 4)查询在树上从a到b的整条链上所有节点值的累加和，入参：int a, int b
 * <p>
 * 概念：
 * 重儿子节点
 * 重链
 * 重链的头
 * <p>
 * 树链剖分的本质是 -> 重链
 * Link - Cur - Tree使用的是树链剖分的实链，还支持森林
 * EC Final -> Word Final
 *
 * @author xcy
 * @date 2022/8/14 - 8:34
 */
public class TreeChainPartition {
	public static void main(String[] args) {
		int N = 50000;
		int V = 100000;
		int[] father = generateFatherArray(N);
		int[] values = generateValueArray(N, V);
		TreeChain tc = new TreeChain(father, values);
		Right right = new Right(father, values);
		int testTime = 1000000;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			double decision = Math.random();
			if (decision < 0.25) {
				int head = (int) (Math.random() * N);
				int value = (int) (Math.random() * V);
				tc.addSubtree(head, value);
				right.addSubtree(head, value);
			} else if (decision < 0.5) {
				int head = (int) (Math.random() * N);
				if (tc.querySubtree(head) != right.querySubtree(head)) {
					System.out.println("出错了!");
				}
			} else if (decision < 0.75) {
				int a = (int) (Math.random() * N);
				int b = (int) (Math.random() * N);
				int value = (int) (Math.random() * V);
				tc.addChain(a, b, value);
				right.addChain(a, b, value);
			} else {
				int a = (int) (Math.random() * N);
				int b = (int) (Math.random() * N);
				if (tc.queryChain(a, b) != right.queryChain(a, b)) {
					System.out.println("出错了!");
				}
			}
		}
		System.out.println("测试结束");
	}

	/**
	 * 树链剖分
	 */
	public static class TreeChain {
		//时间戳，对一棵树生成dfs序时，对遍历的每一个节点进行编号
		private int time;
		//节点个数，从1 ~ N做编号
		private int N;
		//谁是头节点，也就是整棵树的头节点
		private int head;
		//朴素的树结构，描述每一个节点的直接孩子节点
		//arr = { 4, 1, 1, 0, 1}
		//index = 0  1  2  3  4
		//   1          2
		// /   \      /   \
		//2     4 -> 3     5
		//     /          /
		//    0          1
		//   /          /
		//  3          4
		//{
		//   0节点的孩子节点 -> {无}
		//   1节点的孩子节点 -> {4}
		//   2节点的孩子节点 -> {3,5}
		//   3节点的孩子节点 -> {无}
		//   4节点的孩子节点 -> {无}
		//   5节点的孩子节点 -> {1}
		//}
		private int[][] tree;
		//每一个节点的权值/权重数组
		//0节点的权值 -> 6  value[1] = 6
		private int[] val;
		//每一个节点的父节点，元素值包括索引都平移1
		//arr = { 4, 1, 1, 0, 1}
		//index = 0  1  2  3  4
		//将 2 -> 2的头节点改为0
		//father = {0, 5, 0, 2, 1, 2} ->{0, 5, 0, 2, 1, 2}
		//index =   0  1  2  3  4  5     0  1  2  3  4  5
		//                               x
		//0 -> 0该位置放弃使用
		private int[] father;
		//深度，每一个节点处于树的层数
		private int[] dep;
		//son[i] = 0，表示没有重儿子节点
		//son[i] = j，i节点的重儿子节点是j节点，重儿子节点表示以重儿子节点为头节点的整棵树的节点个数最多
		//   2
		// /   \
		//3     5
		//     /
		//    1
		//   /
		//  4
		//son[2] = 5，表示2节点的重儿子节点是5节点
		private int[] son;
		//size[i]表示i节点，以i节点作为头节点的整棵树的节点个数
		private int[] size;
		//重链就是重儿子节点之间连接的链
		//top[i] = j，表示i节点所在的重链中，重链的头是j节点
		//   2
		// /   \
		//3     5
		//     /
		//    1
		//   /
		//  4
		//top[5] = 2，表示5节点所在的重链4 -> 2，重链的头是2节点
		private int[] top;
		//dfn[i] = j，表示i节点在dfs序中是第j个节点
		//   2                   1
		// /   \               /   \
		//3     5 生成dfs序 -> 5     2
		//     /                   /
		//    1                   3
		//   /                   /
		//  4                   4
		//dfn[5] = 2，表示5节点在dfs序中是第2个节点
		private int[] dfn;
		//value[] = {6, 4, 7, 2, 9}
		//index =    1  2  3  4  5
		//   2                   1
		// /   \               /   \
		//3     5 生成dfs序 -> 5     2
		//     /                   /
		//    1                   3
		//   /                   /
		//  4                   4
		//2节点在dfs序中是第1位，权值是4
		//5节点在dfs序中是第2位，权值是9
		//1节点在dfs序中是第3位，权值是6
		//4节点在dfs序中是第4位，权值是2
		//3节点在dfs序中是第5位，权值是7
		//所以
		//tnw[index]中，index表示节点在dfs序中是第几位，tnw[i]表示权值是多少
		//tnw[] = {4, 9, 6, 2, 7}
		//index =  1  2  3  4  5
		private int[] tnw;
		//线段树，在tnw[]上进行连续的区间查询累加和或者所有的节点都加上值
		private final SegmentTree segmentTree;

		public TreeChain(int[] father, int[] value) {
			initTree(father, value);
			//两遍dfs
			//整棵树的头节点head，父节点是0
			dfs1(head, 0);
			//整棵树的头节点head，所在的重链的头还是head自己
			dfs2(head, head);
			//构造线段树
			segmentTree = new SegmentTree(tnw);
			//构建线段树
			segmentTree.build(1, N, 1);
		}

		/**
		 * 原始的树结构tree[][]，创建并且填充好了，可以从每一个节点下找到所有的直接孩子节点
		 * TreeChain类中所有的属性已经初始化完毕
		 * values[]所有元素赋值给val[]
		 * 找到整棵树的头节点head
		 *
		 * @param father
		 * @param values
		 */
		public void initTree(int[] father, int[] values) {
			//各种属性的初始化
			this.time = 0;
			//0位置不使用，从1开始，所以father.length + 1
			this.N = father.length + 1;
			this.tree = new int[N][];
			this.val = new int[N];
			this.father = new int[N];
			this.dep = new int[N];
			this.size = new int[N];
			this.son = new int[N];
			this.top = new int[N];
			this.dfn = new int[N];
			//dfs序的节点数必须和原始数组一致，所以N重新回到father.length，所以N--
			this.tnw = new int[N--];

			//每一个节点的孩子数量的数组
			int[] childNum = new int[N];
			//val[]数组进行初始化
			//arr[] = {3, 4, 2, 9}
			//index =  0  1  2  3
			//val[] = {3, 4, 2, 9}
			//index =  1  2  3  4
			for (int i = 0; i < N; i++) {
				val[i + 1] = values[i];
			}
			//father[i]表示i节点的父节点，如果father[i] == i，表示i节点是头节点，因为平移1，所以i + 1是头节点
			//如果father[i] != i，那么当前father[i]的孩子节点个数++
			for (int i = 0; i < N; i++) {
				if (father[i] == i) {
					head = i + 1;
				} else {
					//孩子数量的数组统计每一个节点的孩子节点数量
					childNum[father[i]]++;
				}
			}
			//创建朴素的树结构，才能够进行dfs序
			//通过每一个节点的直接孩子节点进行dfs序
			//并且每一个节点的直接孩子节点个数是不固定的，所以一维长度先确定下来，二维长度留空，之后进行填充
			//举例：
			//arr[] = {3, 3, 1, 3, 2}
			//index =  0  1  2  3  4
			//统计：
			//3节点有2个孩子节点，实际对应4节点
			//1节点有1个孩子节点，实际对应2节点
			//2节点有1个孩子节点，实际对应3节点
			//tree[father[i] + 1] -> tree[4] = new int[2]
			//tree[father[i] + 1] -> tree[2] = new int[1]
			//tree[father[i] + 1] -> tree[3] = new int[1]
			//其余节点都是0个孩子节点，长度为0
			//并且0位置没有被使用，所以tree[0] = new int[0]
			tree[0] = new int[0];
			//通过遍历将孩子数量的数组的元素值依次创建tree[]中每一个父节点的空间
			for (int i = 0; i < N; i++) {
				//根据每一个节点的孩子数量创建对应的空间
				tree[i + 1] = new int[childNum[i]];
			}
			//tree[father[i] + 1] -> tree[4] = new int[2]
			//tree[father[i] + 1] -> tree[2] = new int[1]
			//tree[father[i] + 1] -> tree[3] = new int[1]
			//--childNum[father[i]]表示每一个父节点的孩子节点，--是因为下标是从0开始的，比如长度为2，下标就是0和1
			for (int i = 0; i < N; i++) {
				//之前head = i + 1，如果i + 1== head，表示是头节点，头节点自己不计算到孩子节点数量当中
				if (i + 1 != head) {
					tree[father[i] + 1][--childNum[father[i]]] = i + 1;
				}
			}
		}

		/**
		 * 找到当前节点的重儿子节点
		 *
		 * @param cur       当前节点
		 * @param curFather 当前节点的父节点
		 */
		public void dfs1(int cur, int curFather) {
			//每一个节点的父节点 -> father[]
			father[cur] = curFather;
			//每一个节点的深度 -> dep[]
			dep[cur] = dep[curFather] + 1;
			//以每一个节点作为头节点的整棵树的节点个数 -> size[]
			size[cur] = 1;
			//以当前节点的孩子节点为头节点的整棵树的节点个数
			int maxSize = -1;
			//遍历每一个节点，以及每一个节点的孩子节点
			for (int child : tree[cur]) {
				//孩子节点作为下一个递归的当前节点，当前节点作为下一个递归的父节点
				dfs1(child, cur);
				//以当前节点为头节点整棵的树的节点个数 累加上 以孩子节点为头节点的整棵树的节点个数
				size[cur] += size[child];
				//如果以孩子节点为头节点的整棵树的节点个数 > maxSize，表示找到了当前节点的重儿子节点
				if (size[child] > maxSize) {
					//更新maxSize
					maxSize = size[child];
					//每一个节点的重儿子节点 -> son[]
					son[cur] = child;
				}
			}
		}

		/**
		 * 根据找到的所有重儿子节点，进行dfs序，也就是编号
		 *
		 * @param cur            当前节点
		 * @param heavyChainHead 当前节点所在重链的头
		 */
		public void dfs2(int cur, int heavyChainHead) {
			//每一个节点所在的dfs序是第几个节点 -> dfn[]
			dfn[cur] = ++time;
			//每一个节点所在的重链的头 -> top[]
			top[cur] = heavyChainHead;
			//每一个节点所在的dfs序是第几个节点，包括节点的权值 -> tnw[]
			tnw[time] = val[cur];

			//表示当前节点有孩子/重儿子节点
			if (son[cur] != 0) {
				//当前节点的重儿子节点继承所在重链的头
				dfs2(son[cur], heavyChainHead);
				for (int child : tree[cur]) {
					//如果当前节点的孩子节点不是重儿子节点
					if (child != son[cur]) {
						//那么当前节点的孩子节点作为自己所在重链的头
						dfs2(child, child);
					}
				}
			}
		}

		/**
		 * 以head节点为头节点的整棵树的所有节点的权值都加上value
		 *
		 * @param head  head节点
		 * @param value value值
		 */
		public void addSubtree(int head, int value) {
			//用户不知道平移1这件事，所以用户希望的head，而实际上是操作head + 1这个节点的，所以head++
			head++;
			//查看head + 1节点在dfs序中是第几个节点，并且以head + 1为头节点的整棵树的所有节点的权值都加上value
			segmentTree.add(dfn[head], dfn[head] + size[head] - 1, value, 1, N, 1);
		}

		/**
		 * @param head head节点
		 * @return 返回以head节点为头节点的整棵树的所有节点的权值的累加和
		 */
		public int querySubtree(int head) {
			////用户不知道平移1这件事，所以用户希望的head，而实际上是操作head + 1这个节点的，所以head++
			head++;
			//查看head + 1节点在dfs序中是第几个节点，并且以head + 1为头节点的整棵树的所有节点的权值的累加和并返回
			return segmentTree.query(dfn[head], dfn[head] + size[head] - 1, 1, N, 1);
		}

		/**
		 * 在a节点到b节点的整条链上的所有节点的权值都加上value
		 *
		 * @param a     a节点
		 * @param b     b节点
		 * @param value value值
		 */
		public void addChain(int a, int b, int value) {
			//用户不知道平移1这件事，所以用户希望的a，而实际上是操作a + 1这个节点的，所以a++
			a++;
			//用户不知道平移1这件事，所以用户希望的b，而实际上是操作b + 1这个节点的，所以b++
			b++;
			//表示a节点和b节点不在同一个重链上
			while (top[a] != top[b]) {
				//哪个节点所在的重链的头深度比较深，那么先添加该节点所在重链的头到该节点中所有节点的权值为value
				if (dep[top[a]] > dep[top[b]]) {
					segmentTree.add(dfn[top[a]], dfn[a], value, 1, N, 1);
					//a节点来到所在重链的头的父节点
					a = father[top[a]];
				} else {
					segmentTree.add(dfn[top[b]], dfn[b], value, 1, N, 1);
					//b节点来到所在重链的头的父节点
					b = father[top[b]];
				}
			}
			//否则表示a节点和b节点在同一个重链上
			if (dep[a] > dep[b]) {
				//哪个节点的深度比较深，那么哪个节点在dfs序中的值就比较大，让值小的在前面
				segmentTree.add(dfn[b], dfn[a], value, 1, N, 1);
			} else {
				segmentTree.add(dfn[a], dfn[b], value, 1, N, 1);
			}
		}

		/**
		 * @param a a节点
		 * @param b b节点
		 * @return 返回在a节点到b节点的整条链上的所有节点的权值累加和
		 */
		public int queryChain(int a, int b) {
			//用户不知道平移1这件事，所以用户希望的a，而实际上是操作a + 1这个节点的，所以a++
			a++;
			//用户不知道平移1这件事，所以用户希望的b，而实际上是操作b + 1这个节点的，所以b++
			b++;
			int ans = 0;
			//表示a节点和b节点不在同一个重链上
			while (top[a] != top[b]) {
				//哪个节点所在的重链的头深度比较深，那么先累加该节点所在重链的头到该节点中所有节点的权值
				if (dep[top[a]] > dep[top[b]]) {
					ans += segmentTree.query(dfn[top[a]], dfn[a], 1, N, 1);
					//a节点来到所在重链的头的父节点
					a = father[top[a]];
				} else {
					ans += segmentTree.query(dfn[top[b]], dfn[b], 1, N, 1);
					//b节点来到所在重链的头的父节点
					b = father[top[b]];
				}
			}
			//否则表示a节点和b节点在同一个重链上
			if (dep[a] > dep[b]) {
				//哪个节点的深度比较深，那么值就比较大，让值小的在前面
				ans += segmentTree.query(dfn[b], dfn[a], 1, N, 1);
			} else {
				ans += segmentTree.query(dfn[a], dfn[b], 1, N, 1);
			}
			return ans;
		}
	}

	/**
	 * 线段树
	 */
	public static class SegmentTree {
		private final int MAXN;
		private final int[] arr;
		private final int[] sum;
		private final int[] lazy;

		public SegmentTree(int[] origin) {
			MAXN = origin.length;
			arr = origin;
			sum = new int[MAXN << 2];
			lazy = new int[MAXN << 2];
		}

		private void pushUp(int rt) {
			sum[rt] = sum[rt << 1] + sum[rt << 1 | 1];
		}

		private void pushDown(int rt, int ln, int rn) {
			if (lazy[rt] != 0) {
				lazy[rt << 1] += lazy[rt];
				sum[rt << 1] += lazy[rt] * ln;
				lazy[rt << 1 | 1] += lazy[rt];
				sum[rt << 1 | 1] += lazy[rt] * rn;
				lazy[rt] = 0;
			}
		}

		public void build(int l, int r, int rt) {
			if (l == r) {
				sum[rt] = arr[l];
				return;
			}
			int mid = (l + r) >> 1;
			build(l, mid, rt << 1);
			build(mid + 1, r, rt << 1 | 1);
			pushUp(rt);
		}

		public void add(int L, int R, int C, int l, int r, int rt) {
			if (L <= l && r <= R) {
				sum[rt] += C * (r - l + 1);
				lazy[rt] += C;
				return;
			}
			int mid = (l + r) >> 1;
			pushDown(rt, mid - l + 1, r - mid);
			if (L <= mid) {
				add(L, R, C, l, mid, rt << 1);
			}
			if (R > mid) {
				add(L, R, C, mid + 1, r, rt << 1 | 1);
			}
			pushUp(rt);
		}

		public int query(int L, int R, int l, int r, int rt) {
			if (L <= l && r <= R) {
				return sum[rt];
			}
			int mid = (l + r) >> 1;
			pushDown(rt, mid - l + 1, r - mid);
			int ans = 0;
			if (L <= mid) {
				ans += query(L, R, l, mid, rt << 1);
			}
			if (R > mid) {
				ans += query(L, R, mid + 1, r, rt << 1 | 1);
			}
			return ans;
		}
	}

	// 为了测试，这个结构是暴力但正确的方法
	public static class Right {
		private final int n;
		private final int[][] tree;
		private final int[] fa;
		private final int[] val;
		private final HashMap<Integer, Integer> path;

		public Right(int[] father, int[] value) {
			n = father.length;
			tree = new int[n][];
			fa = new int[n];
			val = new int[n];
			for (int i = 0; i < n; i++) {
				fa[i] = father[i];
				val[i] = value[i];
			}
			int[] help = new int[n];
			int h = 0;
			for (int i = 0; i < n; i++) {
				if (father[i] == i) {
					h = i;
				} else {
					help[father[i]]++;
				}
			}
			for (int i = 0; i < n; i++) {
				tree[i] = new int[help[i]];
			}
			for (int i = 0; i < n; i++) {
				if (i != h) {
					tree[father[i]][--help[father[i]]] = i;
				}
			}
			path = new HashMap<>();
		}

		public void addSubtree(int head, int value) {
			val[head] += value;
			for (int next : tree[head]) {
				addSubtree(next, value);
			}
		}

		public int querySubtree(int head) {
			int ans = val[head];
			for (int next : tree[head]) {
				ans += querySubtree(next);
			}
			return ans;
		}

		public void addChain(int a, int b, int v) {
			path.clear();
			path.put(a, null);
			while (a != fa[a]) {
				path.put(fa[a], a);
				a = fa[a];
			}
			while (!path.containsKey(b)) {
				val[b] += v;
				b = fa[b];
			}
			val[b] += v;
			while (path.get(b) != null) {
				b = path.get(b);
				val[b] += v;
			}
		}

		public int queryChain(int a, int b) {
			path.clear();
			path.put(a, null);
			while (a != fa[a]) {
				path.put(fa[a], a);
				a = fa[a];
			}
			int ans = 0;
			while (!path.containsKey(b)) {
				ans += val[b];
				b = fa[b];
			}
			ans += val[b];
			while (path.get(b) != null) {
				b = path.get(b);
				ans += val[b];
			}
			return ans;
		}
	}

	/**
	 * 为了测试
	 * 随机生成无环的N个节点的树，可能是多叉树，并且一定不是森林
	 * 输入参数N要大于0
	 *
	 * @param N
	 * @return
	 */
	public static int[] generateFatherArray(int N) {
		int[] order = new int[N];
		for (int i = 0; i < N; i++) {
			order[i] = i;
		}
		//将数组打乱
		for (int i = N - 1; i >= 0; i--) {
			swap(order, i, (int) (Math.random() * (i + 1)));
		}
		int[] ans = new int[N];
		//将order[0]作为整棵树的头节点
		ans[order[0]] = order[0];
		for (int i = 1; i < N; i++) {
			ans[order[i]] = order[(int) (Math.random() * i)];
		}
		return ans;
	}

	// 为了测试
	public static void swap(int[] arr, int i, int j) {
		int tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}

	// 为了测试
	public static int[] generateValueArray(int N, int V) {
		int[] ans = new int[N];
		for (int i = 0; i < N; i++) {
			ans[i] = (int) (Math.random() * V) + 1;
		}
		return ans;
	}
}
