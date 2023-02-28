package com.mashibing;

/**
 * 线段树
 * <p>
 * 线段树的公式
 * 当前节点的父节点的索引：当前节点的索引为i，当前节点的父节点的索引为i / 2
 * 当前节点的左子节点的索引：当前节点的索引为i，当前节点的左子节点的索引为2 * i
 * 当前节点的右子节点的索引：当前节点的索引为i，当前节点的右子节点的索引为2 * i + 1
 * <p>
 * 线段树的实现
 * [3, 2, 1, 0, 3]
 * -1, 2, 3, 4, 5
 * <p>
 * 1表示索引，()中1-5表示索引1-5的累加和
 * -----------------------1(1 - 5)
 * ----------------------/        \
 * ------------------2(1 - 2)  3(3 - 5)
 * -----------------/    \       /    \
 * ------------4(1-1)  5(2-2) 6(3-3)  7(4-5)
 * ------------------------------------/    \
 * --------------------------------14(4-4) 15(5-5)
 * 1表示索引，()中1-5表示索引1-5，[]中的值表示索引1-5的累加和
 * -----------------------1[9](1 - 5)
 * ----------------------/           \
 * ------------------2[5](1 - 2)     3[4](3 - 5)
 * -----------------/       \         /       \
 * ------------4[3](1-1)  5[2](2-2) 6[1](3-3)  7[3](4-5)
 * ------------------------------------------/      \
 * -------------------------------------14[0](4-4) 15[3](5-5)
 *
 * 给定一个数组arr，用户希望你实现如下三个方法
 * 1）void add(int L, int R, int V) :  让数组arr[L…R]上每个数都加上V
 * 2）void update(int L, int R, int V) :  让数组arr[L…R]上每个数都变成V
 * 3）int sum(int L, int R) :让返回arr[L…R]这个范围整体的累加和
 * 怎么让这三个方法，时间复杂度都是O(logN)
 * @author xcy
 * @date 2022/5/24 - 8:29
 */
public class SegmentTree {
	public static void main(String[] args) {

	}

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
