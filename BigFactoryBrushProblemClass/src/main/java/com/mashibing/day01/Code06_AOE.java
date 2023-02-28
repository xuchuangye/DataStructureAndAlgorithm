package com.mashibing.day01;

import com.mashibing.common.TestUtils;

import java.util.Arrays;

/**
 * 题目六：
 * 给定两个非负数组x和hp，长度都是N，再给定一个正数range
 * x有序，x[i]表示i号怪兽在x轴上的位置
 * hp[i]表示i号怪兽的血量
 * 再给定一个正数range，表示如果法师释放技能的范围长度
 * 被打到的每只怪兽损失1点血量。返回要把所有怪兽血量清空，至少需要释放多少次AOE技能？
 * 三个参数：int[] x, int[] hp, int range
 * 返回值类型：int 次数
 * <p>
 * 解题思路：
 * 线段树
 *
 * @author xcy
 * @date 2022/7/6 - 9:16
 */
public class Code06_AOE {
	public static void main(String[] args) {
		int N = 50;
		int X = 500;
		int H = 60;
		int R = 10;
		int testTime = 50000;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int len = (int) (Math.random() * N) + 1;
			int[] x2 = TestUtils.randomArray(len, X);
			Arrays.sort(x2);
			int[] hp2 = TestUtils.randomArray(len, H);
			int[] x3 = TestUtils.copyArray(x2);
			int[] hp3 = TestUtils.copyArray(hp2);
			int range = (int) (Math.random() * R) + 1;
			int ans2 = minAOEWithGreedyAlgorithm(x2, hp2, range);
			int ans3 = minAOEWithSegmentTree(x3, hp3, range);
			if (ans2 != ans3) {
				System.out.println("出错了！");
			}
		}
		System.out.println("测试结束");

		N = 500000;
		long start;
		long end;
		int[] x2 = TestUtils.randomArray(N, N);
		Arrays.sort(x2);
		int[] hp2 = new int[N];
		for (int i = 0; i < N; i++) {
			hp2[i] = i * 5 + 10;
		}
		int[] x3 = TestUtils.copyArray(x2);
		int[] hp3 = TestUtils.copyArray(hp2);
		int range = 10000;

		start = System.currentTimeMillis();
		System.out.println(minAOEWithGreedyAlgorithm(x2, hp2, range));
		end = System.currentTimeMillis();
		System.out.println("运行时间 : " + (end - start) + " 毫秒");

		start = System.currentTimeMillis();
		System.out.println(minAOEWithSegmentTree(x3, hp3, range));
		end = System.currentTimeMillis();
		System.out.println("运行时间 : " + (end - start) + " 毫秒");
	}

	/**
	 * 纯暴力解法
	 * 太容易超时
	 * 只能小样本量使用
	 *
	 * @param x
	 * @param hp
	 * @param range
	 * @return
	 */
	public static int minAOEWithRecursion(int[] x, int[] hp, int range) {
		if (x == null || hp == null || x.length != hp.length) {
			return 0;
		}
		boolean allClear = true;
		for (int i = 0; i < hp.length; i++) {
			if (hp[i] > 0) {
				allClear = false;
				break;
			}
		}
		if (allClear) {
			return 0;
		} else {
			int ans = Integer.MAX_VALUE;
			for (int left = 0; left < x.length; left++) {
				if (hasHp(x, hp, left, range)) {
					minusOneHp(x, hp, left, range);
					ans = Math.min(ans, 1 + minAOEWithRecursion(x, hp, range));
					addOneHp(x, hp, left, range);
				}
			}
			return ans;
		}
	}

	public static boolean hasHp(int[] x, int[] hp, int left, int range) {
		for (int index = left; index < x.length && x[index] - x[left] <= range; index++) {
			if (hp[index] > 0) {
				return true;
			}
		}
		return false;
	}

	public static void minusOneHp(int[] x, int[] hp, int left, int range) {
		for (int index = left; index < x.length && x[index] - x[left] <= range; index++) {
			hp[index]--;
		}
	}

	public static void addOneHp(int[] x, int[] hp, int left, int range) {
		for (int index = left; index < x.length && x[index] - x[left] <= range; index++) {
			hp[index]++;
		}
	}

	/**
	 * 为了验证
	 * 不用线段树，但是贪心的思路，和课上一样
	 * 1) 总是用技能的最左边缘刮死当前最左侧的没死的怪物
	 * 2) 然后向右找下一个没死的怪物，重复步骤1)
	 *
	 * @param x
	 * @param hp
	 * @param range
	 * @return
	 */
	public static int minAOEWithGreedyAlgorithm(int[] x, int[] hp, int range) {
		// 举个例子：
		// 如果怪兽情况如下，
		// 怪兽所在，x数组  : 2 3 5 6 7 9
		// 怪兽血量，hp数组 : 2 4 1 2 3 1
		// 怪兽编号        : 0 1 2 3 4 5
		// 技能直径，range = 2
		int n = x.length;
		int[] cover = new int[n];
		// 首先求cover数组，
		// 如果技能左边界就在0号怪兽，那么技能到2号怪兽就覆盖不到了
		// 所以cover[0] = 2;
		// 如果技能左边界就在1号怪兽，那么技能到3号怪兽就覆盖不到了
		// 所以cover[1] = 3;
		// 如果技能左边界就在2号怪兽，那么技能到5号怪兽就覆盖不到了
		// 所以cover[2] = 5;
		// 如果技能左边界就在3号怪兽，那么技能到5号怪兽就覆盖不到了
		// 所以cover[3] = 5;
		// 如果技能左边界就在4号怪兽，那么技能到6号怪兽（越界位置）就覆盖不到了
		// 所以cover[4] = 6(越界位置);
		// 如果技能左边界就在5号怪兽，那么技能到6号怪兽（越界位置）就覆盖不到了
		// 所以cover[5] = 6(越界位置);
		// 综上：
		// 如果怪兽情况如下，
		// 怪兽所在，x数组  : 2 3 5 6 7 9
		// 怪兽血量，hp数组 : 2 4 1 2 3 1
		// 怪兽编号        : 0 1 2 3 4 5
		// cover数组情况   : 2 3 5 5 6 6
		// 技能直径，range = 2
		// cover[i] = j，表示如果技能左边界在i怪兽，那么技能会影响i...j-1号所有的怪兽
		// 就是如下的for循环，在求cover数组
		int r = 0;
		for (int i = 0; i < n; i++) {
			while (r < n && x[r] - x[i] <= range) {
				r++;
			}
			cover[i] = r;
		}
		int ans = 0;
		for (int i = 0; i < n; i++) {
			// 假设来到i号怪兽了
			// 如果i号怪兽的血量>0，说明i号怪兽没死
			// 根据我们课上讲的贪心：
			// 我们要让技能的左边界，刮死当前的i号怪兽
			// 这样能够让技能尽可能的往右释放，去尽可能的打掉右侧的怪兽
			// 此时cover[i]，正好的告诉我们，技能影响多大范围。
			// 比如当前来到100号怪兽，血量30
			// 假设cover[100] == 200
			// 说明，技能左边界在100位置，可以影响100号到199号怪兽的血量。
			// 为了打死100号怪兽，我们释放技能30次，
			// 释放的时候，100号到199号怪兽都掉血，30点
			// 然后继续向右寻找没死的怪兽，像课上讲的一样
			if (hp[i] > 0) {
				int minus = hp[i];
				for (int index = i; index < cover[i]; index++) {
					hp[index] -= minus;
				}
				ans += minus;
			}
		}
		return ans;
	}

	/**
	 * 正式方法
	 * 关键点就是：
	 * 1) 线段树
	 * 2) 总是用技能的最左边缘刮死当前最左侧的没死的怪物
	 * 3) 然后向右找下一个没死的怪物，重复步骤2)
	 * @param x
	 * @param hp
	 * @param range
	 * @return
	 */
	public static int minAOEWithSegmentTree(int[] x, int[] hp, int range) {
		int n = x.length;
		int[] cover = new int[n];
		int r = 0;
		// cover[i] : 如果i位置是技能的最左侧，技能往右的range范围内，最右影响到哪
		for (int i = 0; i < n; i++) {
			while (r < n && x[r] - x[i] <= range) {
				r++;
			}
			cover[i] = r - 1;
		}
		SegmentTree st = new SegmentTree(hp);
		st.build(1, n, 1);
		int ans = 0;
		for (int i = 1; i <= n; i++) {
			int leftHP = st.query(i, i, 1, n, 1);
			if (leftHP > 0) {
				ans += leftHP;
				st.add(i, cover[i - 1] + 1, -leftHP, 1, n, 1);
			}
		}
		return ans;
	}

	public static class SegmentTree {

		//original[] 原始数组
		//arr[] 线段树数组
		private final int[] arr;
		/**
		 * sum[] 累加和数组
		 */
		private final int[] sum;
		/**
		 * lazy[] 懒加载数组
		 */
		private final int[] lazy;
		/**
		 * MAXN 原始数组的长度
		 */
		private final int MAXN;
		/**
		 * update[] 懒更新数组
		 */
		private final int[] update;
		/**
		 * change[] 值是否被update的标记数组
		 */
		private final boolean[] change;

		public SegmentTree(int[] original) {
			MAXN = original.length + 1;
			arr = new int[MAXN];//arr[0] 不需要使用
			for (int i = 1; i < MAXN; i++) {
				arr[i] = original[i - 1];
			}
			// 用来支持脑补概念中，某一个范围的累加和信息
			sum = new int[MAXN * 2 * 2];
			// 用来支持脑补概念中，某一个范围沒有往下传送的累加任務
			lazy = new int[MAXN * 2 * 2];
			//用来支持脑补概念中，某一个范围更新任务，更新成了什么
			update = new int[MAXN * 2 * 2];
			//用来支持脑补概念中，某一个范围有没有更新操作的任务
			change = new boolean[MAXN * 2 * 2];
		}


		/**
		 * pushUp() 调整累加和
		 * 左子节点的累加和 + 右子节点的累加和 = 父节点的累加和
		 *
		 * @param rt rt表示数组的索引
		 */
		public void pushUp(int rt) {
			sum[rt] = sum[rt * 2] + sum[rt * 2 + 1];
		}


		/**
		 * pushDown() 往下分发一层
		 * 分发策略
		 * 之前的所有懒增加信息，和懒更新信息，从父节点范围，发给左右两个子节点范围
		 * 参数：rt表示父节点，ln表示左子树的节点个数，rn表示右子树的节点个数
		 * 举例：
		 * 任务 (1 - 500) 5
		 * -----------[1 - 500] 1
		 * ----------/           \
		 * -----[1 - 250] 2      [251 - 500] 3
		 * [1 - 250] 2的懒加载信息  += 父节点[1 - 500]懒加载信息 -> lazy[2 * i] += lazy[i]
		 * [1 - 250] 2的累加和信息  += 父节点[1 - 500]懒加载信息 * [1 - 250] 2的节点个数
		 * 左子节点的懒加载 += 父节点的懒加载
		 * lazy[i * 2] += lazy[i]
		 * sum[i * 2] += lazy[i] * ln
		 *
		 * @param rt rt表示数组的索引，表示当前节点
		 * @param ln 当前节点的左子树的节点个数
		 * @param rn 当前节点的右子树的节点个数
		 */
		public void pushDown(int rt, int ln, int rn) {
			//当前拦住的任务不为空，可以往下更新
			if (change[rt]) {
				//往左边分发
				//左子范围添加标记
				change[rt * 2] = true;
				//更新左子范围的值为父范围的值
				update[rt * 2] = update[rt];
				//之前所有的懒加载信息全部清零
				lazy[rt * 2] = 0;
				//左子范围的累加和为父范围的值 * 左子树的节点个数
				sum[rt * 2] = update[rt] * ln;

				//往右边分发
				//右子范围添加 标记
				change[rt * 2 + 1] = true;
				//更新右子范围的值为父范围的值
				update[rt * 2 + 1] = update[rt];
				//之前所有的懒加载信息全部清零
				lazy[rt * 2 + 1] = 0;
				//右子范围的累加和为父范围的值 * 右子树的节点个数
				sum[rt * 2 + 1] = update[rt] * rn;

				//当前父范围的标记重置
				change[rt] = false;
			}

			//当前拦住的任务不为空，可以往下分发
			if (lazy[rt] != 0) {
				//往左边分发
				//左子节点的懒加载 += 父节点的懒加载
				lazy[rt * 2] += lazy[rt];
				//左子节点的累加和 += 父节点的懒加载 * 左子树的节点个数
				sum[rt * 2] += lazy[rt] * ln;

				//往右边分发
				//右子节点的懒加载 += 父节点的懒加载
				lazy[rt * 2 + 1] += lazy[rt];
				//右子节点的累加和 += 父节点的懒加载 * 右子树的节点个数
				sum[rt * 2 + 1] += lazy[rt] * rn;

				//父节点的懒加载 = 0
				lazy[rt] = 0;
			}
		}

		/**
		 * build() 构建线段树
		 * 在初始化阶段，先将sum[]填好
		 * 在arr[left - right]范围上，执行build()，1 ~ N
		 *
		 * @param left  左边界
		 * @param right 右边界
		 * @param rt    表示这个范围在sum[]中的索引
		 */
		public void build(int left, int right, int rt) {
			//举例：
			//[1 - 500] 1
			if (left == right) {
				sum[rt] = arr[left];
				return;
			}
			int mid = left + ((right - left) / 2);
			build(left, mid, rt * 2);
			build(mid + 1, right, rt * 2 + 1);
			pushUp(rt);
		}


		/**
		 * add()
		 * 参数L, R, num 表示大任务，表示任务需要在[L - R]的范围上加上num
		 * 参数l，r表示索引的范围l - r，参数rt表示累加和数组的索引
		 * 举例：
		 * add(l, r, rt) -> [1 - 1000], 1
		 * add(L, R, num) -> add(3, 874, 5)表示大任务在3 - 874该范围内的所有数都加上5
		 *
		 * @param L   大任务的左边界
		 * @param R   大任务的右边界
		 * @param num 需要加上的值
		 * @param l   当前范围的左边界
		 * @param r   当前范围的右边界
		 * @param rt  当前节点的索引
		 */
		public void add(int L, int R, int num, int l, int r, int rt) {
			//老任务
			//情况1：任务的范围将此时的范围全包了，拦住了，就不需要往下发
			if (l >= L && r <= R) {
				sum[rt] += num * (r - l + 1);
				//拦住任务，不需要往下分发，所以时间复杂度：O(logN)
				lazy[rt] += num;
				return;
			}
			//情况2：任务的范围无法将此时的范围全包，往下分发一层
			int mid = l + ((r - l) / 2);
			//往下分发的任务，左边需要分发多少，右边需要分发多少
			pushDown(rt, mid - l + 1, r - mid);

			//新任务
			//情况3：任务需要发给左边
			//大任务的左边界在当前左边范围内
			if (L <= mid) {
				add(L, R, num, l, mid, rt * 2);
			}
			//情况4：任务需要发给右边
			//大任务的右边界在当前右边范围内
			if (R > mid) {
				add(L, R, num, mid + 1, r, rt * 2 + 1);
			}
			//情况5：左边执行总任务，右边执行总任务
			pushUp(rt);
		}

		/**
		 * update()
		 * add()
		 * 参数L, R, num 表示大任务，表示任务需要在[L - R]的范围上更新成num
		 * 参数l，r表示索引的范围l - r，参数rt表示到哪里找，rt位置
		 * 举例：
		 * add(l, r, rt) -> [1 - 1000], 1
		 * add(L, R, num) -> add(3 - 874, 5) 3 - 874表示大任务在该范围内的所有数都更新成5
		 *
		 * @param L   大任务的左边界
		 * @param R   大任务的右边界
		 * @param num 更新的值
		 * @param l   当前范围的左边界
		 * @param r   当前范围的右边界
		 * @param rt  当前索引
		 */
		public void update(int L, int R, int num, int l, int r, int rt) {
			//老任务
			//情况1：任务的范围将此时的范围全包了，拦住了，就不需要往下发
			//拦住任务，不需要往下分发，所以时间复杂度：O(logN)
			if (l >= L && r <= R) {
				//值都被标记
				change[rt] = true;
				//修改值
				update[rt] = num;
				//累加和
				sum[rt] = num * (r - l + 1);
				//懒加载
				lazy[rt] = 0;
				return;
			}
			int mid = l + ((r - l) / 2);
			//情况2：任务的范围无法将此时的范围全包，往下分发一层
			//往下分发的任务，左边需要分发多少，右边需要分发多少
			pushDown(rt, mid - l + 1, r - mid);
			//新任务
			//情况3：任务需要发给左边
			//大任务的左边界在当前左边范围内
			if (L <= mid) {
				update(L, R, num, l, mid, rt * 2);
			}
			//情况4：任务需要发给右边
			//大任务的右边界在当前右边范围内
			if (R > mid) {
				update(L, R, num, mid + 1, r, rt * 2 + 1);
			}
			//情况5：左边执行总任务，右边执行总任务
			pushUp(rt);
		}

		/**
		 * query() 查询累加和
		 *
		 * @param L 大任务的左边界
		 * @param R 大任务的右边界
		 * @param l 当前范围的左边界
		 * @param r 当前范围的右边界
		 * @param rt 当前索引
		 * @return 返回大任务的[L - R]范围上的累加和
		 */
		public int query(int L, int R, int l, int r, int rt) {
			//老任务
			//情况1：任务的范围将此时的范围全包了，拦住了，就不需要往下发
			//拦住任务，不需要往下分发，所以时间复杂度：O(logN)
			if (l >= L && r <= R) {
				return sum[rt];
			}
			//情况2：任务的范围无法将此时的范围全包，往下分发一层
			//往下分发的任务，左边需要分发多少，右边需要分发多少
			int mid = l + ((r - l) / 2);
			pushDown(rt, mid - l + 1, r - mid);

			int ans = 0;
			if (L <= mid) {
				ans += query(L, R, l, mid, rt * 2);
			}
			if (R > mid) {
				ans += query(L, R, mid + 1, r, rt * 2 + 1);
			}
			return ans;
		}
	}
}
