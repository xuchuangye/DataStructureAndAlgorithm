package com.mashibing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

/**
 * 落方块问题：在无限长的数轴（即 x 轴）上，我们根据给定的顺序放置对应的正方形方块。
 * <p>
 * 1)第 i 个掉落的方块（positions[i] = (left, side_length)）是正方形，
 * 其中left表示该方块最左边的点位置(positions[i][0])，side_length 表示该方块的边长(positions[i][1])。
 * 2)每个方块的底部边缘平行于数轴（即 x 轴），并且从一个比目前所有的落地方块更高的高度掉落而下。
 * 在上一个方块结束掉落，并保持静止后，才开始掉落新方块。
 * 3)方块的底边具有非常大的粘性，并将保持固定在它们所接触的任何长度表面上（无论是数轴还是其他方块）。
 * 邻接掉落的边不会过早地粘合在一起，因为只有底边才具有粘性。
 * <p>
 * 要求：
 * 返回一个堆叠高度列表ans。
 * 每一个堆叠高度ans[i]表示在通过positions[0], positions[1], ..., positions[i]表示的方块掉落的位置结束后，
 * 目前所有已经落稳的方块堆叠的最高高度
 * <p>
 * 举例：
 * positions[i]表示掉落的方块，positions[i][0]表示方块掉落的点位置，positions[i][1]表示方块的边长
 * <p>
 * 输入: [[1, 2], [2, 3], [6, 1]]
 * 输出: [2, 5, 5]
 * 解释:
 *
 * 第一个方块 positions[0] = [1, 2] 掉落：
 * _aa
 * _aa
 * -------
 * 方块最大高度为 2 。
 *
 * 第二个方块 positions[1] = [2, 3] 掉落：
 * __aaa
 * __aaa
 * __aaa
 * _aa__
 * _aa__
 * --------------
 * 方块最大高度为5。
 * 大的方块保持在较小的方块的顶部，不论它的重心在哪里，因为方块的底部边缘有非常大的粘性。
 *
 * 第三个方块 positions[1] = [6, 1] 掉落：
 * __aaa
 * __aaa
 * __aaa
 * _aa
 * _aa___a
 * --------------
 * 方块最大高度为5。
 *
 * 因此，我们返回结果[2, 5, 5]。
 *
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/falling-squares
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author xcy
 * @date 2022/5/24 - 16:23
 */
public class DropSquareBlock {
	public static void main(String[] args) {

	}

	public List<Integer> fallingSquares(int[][] positions) {
		HashMap<Integer, Integer> map = index(positions);
		SegmentTree segmentTree = new SegmentTree(map.size());

		int max = Integer.MIN_VALUE;
		ArrayList<Integer> ans = new ArrayList<>();
		// 每落一个正方形，收集一下，所有东西组成的图像，最高高度是什么
		for (int[] position : positions) {
			int L = map.get(position[0]);
			int R = map.get(position[0] + position[1] - 1);
			int height = segmentTree.query(L, R, 1, map.size(), 1) + position[1];
			max = Math.max(height, max);
			ans.add(max);
			segmentTree.update(L, R, height, 1, map.size(), 1);
		}
		return ans;
	}

	public HashMap<Integer, Integer> index(int[][] positions) {
		TreeSet<Integer> position = new TreeSet<>();
		for (int[] arr : positions) {
			position.add(arr[0]);
			position.add(arr[0] + arr[1] - 1);
		}
		HashMap<Integer, Integer> map = new HashMap<>();
		int index = 0;
		for (Integer distance : position) {
			map.put(distance, ++index);
		}
		return map;
	}

	/**
	 * 线段树
	 */
	public static class SegmentTree {
		public int[] max;
		public int[] update;
		public boolean[] change;

		/**
		 * @param size 原数组的值都为0，所以不需要数组，只需要长度即可
		 */
		public SegmentTree(int size) {
			int N = size + 1;
			max = new int[N * 2 * 2];
			update = new int[N * 2 * 2];
			change = new boolean[N * 2 * 2];
		}

		/**
		 * pushUp() 调整累加和
		 * Math.max(左子范围的最大值,右子范围的最大值) = 父范围的最大值
		 *
		 * @param rt rt表示数组的索引
		 */
		public void pushUp(int rt) {
			max[rt] = Math.max(max[rt * 2], max[rt * 2 + 1]);
		}

		/**
		 * 往下一层进行分发
		 *
		 * @param rt rt表示数组的索引
		 * @param ln 当前节点的左子树的节点个数
		 * @param rn 当前节点的右子树的节点个数
		 */
		public void pushDown(int rt, int ln, int rn) {
			if (change[rt]) {
				change[rt * 2] = true;
				change[rt * 2 + 1] = true;

				update[rt * 2] = update[rt];
				update[rt * 2 + 1] = update[rt];

				max[rt * 2] = update[rt];
				max[rt * 2 + 1] = update[rt];

				change[rt] = false;
			}
		}

		/**
		 * 根据大任务，更新指定范围的值
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
			//如果大任务的范围能够将当前范围进行全部包围
			if (l >= L && r <= R) {
				//修改更新的标记为true
				change[rt] = true;
				//更新值为num
				update[rt] = num;
				//最大值也更新为num
				max[rt] = num;
				return;
			}

			//当前范围的中间索引
			int mid = l + ((r - l) / 2);
			//如果大任务的范围不能够将当前范围进行全部包围
			//往下一层分发
			pushDown(rt, mid - l + 1, r - mid);

			//新任务
			//往左子范围进行分发
			if (L <= mid) {
				update(L, R, num, l, mid, rt * 2);
			}
			//往右子范围进行分发
			if (R > mid) {
				update(L, R, num, mid + 1, r, rt * 2 + 1);
			}
			//左边执行总任务，右边执行总任务
			pushUp(rt);
		}

		/**
		 * 根据大任务，查询指定范围的最大值
		 *
		 * @param L  大任务的左边界
		 * @param R  大任务的右边界
		 * @param l  当前范围的左边界
		 * @param r  当前范围的右边界
		 * @param rt 当前索引
		 * @return 返回指定范围的最大值
		 */
		public int query(int L, int R, int l, int r, int rt) {
			//老任务
			//如果大任务的范围能够将当前范围进行全部包围
			if (l >= L && r <= R) {
				//直接返回最大值
				return max[rt];
			}
			//当前范围的中间索引
			int mid = l + ((r - l) / 2);
			//如果大任务的范围不能够将当前范围进行全部包围
			//往下一层分发
			pushDown(rt, mid - l + 1, r - mid);

			//新任务
			int left = 0;
			int right = 0;
			//往左子范围进行分发，记录左子范围的最大值
			if (L <= mid) {
				left = query(L, R, l, mid, rt * 2);
			}
			//往右子范围进行分发，记录右子范围的最大值
			if (R > mid) {
				right = query(L, R, mid + 1, r, rt * 2 + 1);
			}
			//返回最大值
			return Math.max(left, right);
		}

	}
}
