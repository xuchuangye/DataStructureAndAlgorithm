package com.mashibing;

/**
 * @author xcy
 * @date 2022/5/25 - 8:13
 */
public class SegmentTreeMain {

	public static class SegmentTree {
		private final int MAXN;
		private final int[] arr;
		private final int[] sum;
		private final int[] lazy;
		private final int[] update;
		private final boolean[] change;

		public SegmentTree(int[] original) {
			MAXN = original.length + 1;
			arr = new int[MAXN];
			for (int i = 1; i < MAXN; i++) {
				arr[i] = original[i - 1];
			}
			sum = new int[MAXN << 2];
			lazy = new int[MAXN << 2];
			update = new int[MAXN <<2];
			change = new boolean[MAXN << 2];
		}

		public void pushUp(int rt) {
			sum[rt] = sum[rt << 1] + sum[rt << 1 | 1];
		}

		public void pushDown(int rt, int ln, int rn) {
			if (change[rt]) {
				change[rt << 1] = true;
				change[rt << 1 | 1] = true;
				update[rt << 1] = update[rt];
				update[rt << 1 | 1] = update[rt];
				lazy[rt << 1] = 0;
				lazy[rt << 1 | 1] = 0;
				sum[rt << 1] = update[rt] * ln;
				sum[rt << 1 | 1] = update[rt] * rn;
				change[rt] = false;
			}
			if (lazy[rt] != 0) {
				lazy[rt << 1] += lazy[rt];
				lazy[rt << 1 | 1] += lazy[rt];
				sum[rt << 1] += lazy[rt] * ln;
				sum[rt << 1  | 1] += lazy[rt] * rn;
				lazy[rt] = 0;
			}
		}

		public void build(int l, int r, int rt) {
			if (l == r) {
				sum[rt] = arr[rt];
				return;
			}
			int mid = l + ((r - l) >> 1);
			build(l , mid, rt);
			build(mid + 1 , r, rt);
			pushUp(rt);
		}

		public void add(int L, int R, int num, int l, int r, int rt) {
			if (l >= L && r <= R) {
				sum[rt] += num * (r - l + 1);
				lazy[rt] += num;
				return;
			}
			int mid = l + ((r - l) >> 1);
			pushDown(rt, mid - l +1, r - mid);

			if (L <= mid) {
				add(L, R, num, l, mid , rt << 1);
			}

			if (R > mid) {
				add(L, R, num, mid + 1, r, rt << 1 | 1);
			}
			pushUp(rt);
		}

		public void update(int L, int R, int num, int l, int r, int rt) {
			if (l >= L && r <= R) {
				change[rt] = true;
				update[rt] = num;
				sum[rt] = num * (r - l + 1);
				lazy[rt] = 0;
				return;
			}
			int mid = l + ((r - l ) >> 1);
			pushDown(rt, mid - l + 1, r - mid);

			if (L <= mid) {
				update(L, R, num, l, mid, rt << 1);
			}
			if (R > mid) {
				update(L, R, num, mid + 1, r, rt << 1 | 1);
			}
			pushUp(rt);
		}

		public int query(int L, int R, int l, int r, int rt) {
			if (l >= L && r <= R) {
				return sum[rt];
			}
			int mid = l + ((r - l) >> 1);
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
}
