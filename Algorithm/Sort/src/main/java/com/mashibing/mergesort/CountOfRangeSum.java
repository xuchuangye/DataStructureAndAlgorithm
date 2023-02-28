package com.mashibing.mergesort;

import com.mashibing.common.SortCommonUtils;

import java.util.HashSet;

/**
 * 归并排序的衍生问题：区间和的个数问题
 * 给定一个整数数组nums 以及两个整数lower 和 upper 。
 * 求数组中，值位于范围 [lower, upper] （包含lower和upper）之内的 区间和的个数 。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/count-of-range-sum
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * <p>
 * 基本步骤：
 * 1、求数组arr中i - j范围上多少个子数组的累加和满足[Lower,Upper]之间
 * 2、先求出0 - j 的累加和x，然后减去0 - i的累加和y，得到i - j的累加和z
 * 3、i - j的累加和的上限是：0 - j 的累加和x减去Lower，i - j的累加和的下限是：0 - j 的累加和x减去Upper
 * 4、所以i - j 的累加和的范围是：[x - Lower, x - Upper]
 *
 * @author xcy
 * @date 2022/4/17 - 17:35
 */
public class CountOfRangeSum {
	public static void main(String[] args) {
		/*// TODO:恶意代码测试问题，目前无法解决
		int[] arr = {-2147483647, 0, -2147483647, 2147483647};
		int count = countRangeSum(arr, -564, 3864);
		System.out.println(count);*/
		int len = 200;
		int varible = 50;
		System.out.println("测试开始！");
		for (int i = 0; i < 10000; i++) {
			int[] test = SortCommonUtils.generateArray(len, varible);
			int lower = (int) (Math.random() * varible) - (int) (Math.random() * varible);
			int upper = lower + (int) (Math.random() * varible);
			int ans1 = countRangeSumWithNoRecursion(test, lower, upper);
			int ans2 = countRangeSumWithOrderedTable(test, lower, upper);
			if (ans1 != ans2) {
				SortCommonUtils.printArray(test);
				System.out.println(lower);
				System.out.println(upper);
				System.out.println(ans1);
				System.out.println(ans2);
				break;
			}
		}
		System.out.println("测试结束！");
	}

	/**
	 * 区间和的个数问题的最终实现方法 -> 使用递归的方式
	 *
	 * @param nums  原始无序数组
	 * @param lower 范围下限
	 * @param upper 范围上限
	 * @return 返回在原始数组arr[left]到arr[right]中有多少个子数组的累加和在[lower, upper]范围上的个数
	 */
	public static int countRangeSumWithRecursion(int[] nums, int lower, int upper) {
		if (nums == null || nums.length == 0) {
			return 0;
		}
		//前缀和数组
		int[] sum = new int[nums.length];

		//原始数组的第一个元素作为前缀数组的第一个元素
		sum[0] = nums[0];
		//从原始数组的第二个元素开始遍历，
		for (int i = 1; i < nums.length; i++) {
			sum[i] = sum[i - 1] + nums[i];
		}

		return count(sum, 0, sum.length - 1, lower, upper);
	}

	/**
	 * 计算在原始数组arr[left]到arr[right]中有多少个子数组的累加和在[lower, upper]范围上
	 *
	 * @param sum   arr数组每一位元素的从0到该元素的前缀和组成的前缀和数组
	 * @param left  原始无序数组的左边界
	 * @param right 原始无序数组的右边界
	 * @param lower 范围的下限
	 * @param upper 范围的上限
	 * @return 返回在原始数组arr[left]到arr[right]中有多少个子数组的累加和在[lower, upper]范围上
	 */
	public static int count(int[] sum, int left, int right, int lower, int upper) {
		//left == right表示arr[left] == arr[right]
		if (left == right) {
			//sum[left] = arr[0]到arr[left]的前缀和
			//前缀和是否在[lower, upper]左闭右闭区间中
			if (sum[left] >= lower && sum[left] <= upper) {
				//1表示满足arr[left]或者arr[right]的值在范围[lower,upper]上的条件，也就是表示找到了一个
				return 1;
			} else {
				//0表示不满足条件，没有找到
				return 0;
			}
		}
		//中间索引
		int mid = left + ((right - left) >> 1);
		//左边数组
		int leftCount = count(sum, left, mid, lower, upper);
		//右边数组
		int rightCount = count(sum, mid + 1, right, lower, upper);
		//合并
		int merge = merge(sum, left, mid, right, lower, upper);
		return leftCount + rightCount + merge;
	}

	/**
	 * 合并左边数组和右边有序数组，并计算原始arr[left[到arr[right]中有多少个子数组的累加和在[lower,upper]范围上
	 * <p>
	 * 关键点：
	 * 计算个数时能够做到不回退
	 *
	 * @param arr   原始数组
	 * @param left  左边有序数组的左边界
	 * @param mid   左边有序数组的右边界
	 * @param right 右边有序数组的右边界
	 * @param lower 范围下限
	 * @param upper 范围上限
	 * @return
	 */
	public static int merge(int[] arr, int left, int mid, int right, int lower, int upper) {
		//计算合并的过程中，满足原始数组arr[left[到arr[right]中有多少个子数组的累加和在[lower,upper]范围上条件的个数
		int count = 0;
		//指向左边数组的左边界
		int L = left;
		//指向左边数组的左边界
		int R = left;
		//遍历右边有序数组，从mid + 1开始，到right结束
		for (int i = mid + 1; i <= right; i++) {
			//左边数组每个元素的上限和下限
			//下限
			int min = arr[i] - upper;
			//上限
			int max = arr[i] - lower;
			//R <= mid防止越界
			//arr[R]<=max：表示如果arr[R]小于等于max，R就需要++向后移动，直到arr[R]大于max
			while (R <= mid && arr[R] <= max) {
				//arr[R] 大于 max时，R就不移动
				R++;
			}
			//L <= mid防止越界
			//arr[L]<min：表示如果arr[L]小于min，L就需要++向后移动，直到arr[L]不小于min
			while (L <= mid && arr[L] < min) {
				//arr[L] 不小于 min时，L就不移动
				L++;
			}
			//有可能R在L的前面，所以R-L是负数，负数也表示0个，所以要和0比较一下
			//count += Math.max(0, R - L);
			count += Math.max(0, R - L);
			System.out.println(count);
		}

		//合并的基本步骤
		int[] temp = new int[right - left + 1];
		int index = 0;
		//左边数组的起始位置
		int cur1 = left;
		//右边数组的起始位置
		int cur2 = mid + 1;
		//从左边数组的起始位置到左边数组的结束位置的范围 与 右边数组的起始位置到右边数组的结束位置的范围进行遍历
		while (cur1 <= mid && cur2 <= right) {
			temp[index++] = arr[cur1] <= arr[cur2] ? arr[cur1++] : arr[cur2++];
		}
		//要么左边数组的起始位置到左边数组的结束位置的范围没有遍历完
		while (cur1 <= mid) {
			temp[index++] = arr[cur1++];
		}
		//要么右边数组的起始位置到右边数组的结束位置的范围没有遍历完
		while (cur2 <= right) {
			temp[index++] = arr[cur2++];
		}

		for (index = 0; index < temp.length; index++) {
			arr[left + index] = temp[index];
		}
		//System.out.println(count);
		return count;
	}

	/**
	 *
	 * @param nums
	 * @param lower
	 * @param upper
	 * @return
	 */
	public static int countRangeSumWithNoRecursion(int[] nums, int lower, int upper) {
		long[] sums = new long[nums.length + 1];
		for (int i = 0; i < nums.length; ++i)
			sums[i + 1] = sums[i] + nums[i];
		return countWhileMergeSort(sums, 0, nums.length + 1, lower, upper);
	}

	private static int countWhileMergeSort(long[] sums, int start, int end, int lower, int upper) {
		if (end - start <= 1)
			return 0;
		int mid = start + ((end - start) >> 1);
		int count = countWhileMergeSort(sums, start, mid, lower, upper)
				+ countWhileMergeSort(sums, mid, end, lower, upper);
		int j = mid, k = mid, t = mid;
		long[] cache = new long[end - start];
		for (int i = start, r = 0; i < mid; ++i, ++r) {
			while (k < end && sums[k] - sums[i] < lower)
				k++;
			while (j < end && sums[j] - sums[i] <= upper)
				j++;
			while (t < end && sums[t] < sums[i])
				cache[r++] = sums[t++];
			cache[r] = sums[i];
			count += j - k;
		}
		System.arraycopy(cache, 0, sums, start, t - start);
		return count;
	}

	/**
	 * 使用有序表的思路分析：
	 * 1.必须以i索引的位置为结尾的子数组累加和有多少个在[lower, upper]范围上
	 * 举例：
	 * 子数组必须以0位置结尾达标有多少个
	 * 子数组必须以1位置结尾达标有多少个
	 * 子数组必须以2位置结尾达标有多少个
	 * ...
	 * 子数组必须以N - 1位置结尾达标有多少个
	 * 所有的子数组都不一样，因为结尾的位置不一样，而且所有的子数组都列举全了
	 * 所有的子数组都有结尾位置，所以以结尾为划分子数组，既没有划分多了也没有划分少
	 * 所有的子数组达标的都累加起来，就是arr数组整体有多少个达标的子数组
	 * 2.子数组满足指定指标的有多少个，或者满足的子数组个数，或者子数组最大长度是多少
	 * 这些都是从左往右的尝试模型
	 * 3.假设给定[lower, upper]范围是[10, 60]，从0 ~ i位置的累加和100，
	 * 结论：求必须以i位置结尾的子数组在[10, 60]范围上的有多少个
	 * 本质上就是求必须以0开头的前缀和在[100 - 60, 100 - 10]范围上的有多少个
	 * 举例：
	 * i = 17， 0 ~ 17的前缀和是100，0 ~ 7的前缀和是70，那么8 ~ 17的前缀和是100 - 70 = 30
	 * 表示如果0 ~ i的范围的前缀和在[100 - upper, 100 - lower]，那么以i为结尾的累加和一定在[lower, upper]范围上
	 * 举例：
	 * 假设0 ~ 17的累加和是100，[lower, upper]范围是[10, 60]
	 * 如果0 ~ 13的前缀和是50，在[100 - upper, 100 - lower]范围上，也就是在[40, 90]范围上
	 * 呢么14 ~ 17的累加和一定在[lower, upper]范围上，也就是[10, 60]
	 * 4.arr = [3, -2, 4, 3, 6, -1]，[lower, upper]范围是[1, 5]
	 * 1)准备前缀和的容器
	 * 2)先在容器中添加一个0的前缀和，表示索引0位置之前的前缀和为0，此时前缀和容器中有[0]
	 * 3)从第0位置开始到0位置的前缀和为3，前缀和的范围[3 - 5, 3 - 1] -> [-2, 2]的范围上，
	 * 查看前缀和容器，容器有0这个前缀和在[-2, 2]的范围上，记录0位置上达标的子数组有1个,将前缀和3放入前缀和容器中，
	 * 此时前缀和容器中有[0, 3]
	 * 4)从第0位置开始到1位置的前缀和为1，前缀和的范围[1 - 5, 1 - 1] -> [-4, 0]的范围上，
	 * 查看前缀和容器，容器中有0这个前缀和在[-4, 0]的范围上，记录1位置上达标的子数组有1个，将前缀和1放入前缀和容器中，
	 * 此时前缀和容器中有[0, 3, 1]
	 * 5)从第0位置开始到2位置的前缀和为5，前缀和的范围[5- 5, 5 - 1] -> [0, 4]的范围上，
	 * 查看前缀和容器，容器中有0, 3, 1的前缀和在[0, 4]的范围上，记录2位置上达标的子数组有3个，将前缀和5放入前缀和容器中，
	 * 此时前缀和容器中有[0, 3, 1, 5]
	 * ...
	 * 依此类推
	 * arr =       [3, -2, 4, 3, 6, -1]
	 * 达标子数组个数：1,  1, 3, ... 累加
	 * 5.前缀和容器的特征：
	 * 1)容器能够添加整数
	 * 2)容器能够查询容器中有多少个数在指定范围[lower, upper]范围上
	 * 3)容器能够接收重复数字
	 * 4)这个容器就是有序表
	 * 6.有序表从小到大进行排序，并且能够接收重复数字，支持[a, b]的范围查询
	 * 并不需要直接支持[a, b]的范围查询，只要支持小于指定key值的数量是多少就够了
	 * 举例：
	 * 查看有序表接收很多数字，查询[5, 10]范围上的数字有多少个(不去重)
	 * 1)先求小于5的数有多少个，假设为b
	 * 2)再求小于11的数字有多少个，假设为a
	 * 3)a - b就是[5, 10]范围上有多少个数字
	 * 7.有序表需要支持的功能
	 * 1)有序表能够接收数字
	 * 2)有序表能够接收重复的数字
	 * 3)有序表能够查询小于某个key值的数字有多少个
	 * 8.有序表如何实现添加重复数字
	 * 在节点上添加一个属性，这个属性表示收集了多少个数
	 * 1)第一步，添加5，创建5的头节点，此时收集了1个节点数
	 * 2)第二步，添加3，3比5小，创建左子节点，此时左子节点3收集了1个节点数，父节点5收集了2个节点数
	 * 3)第三步，添加6，6比5大，创建右子节点，此时右子节点6收集了1个节点数，父节点5收集了3个节点数
	 * 4)第四步，添加5，此时父节点5收集了4个节点数
	 * 5)第五步，添加4，4比5小，比3大，创建3的右子节点，此时祖父节点5收集了5个节点数，父节点3收集了2个节点数，4节点收集1个节点数
	 * 6)当前节点的个数 = 当前节点收集的节点数 - (当前节点的左子节点收集的节点数 + 当前节点的右子节点收集的节点数)
	 * 5节点的个数 = 5节点收集5个节点数 - (3节点收集2个节点数 + 6节点收集1个节点数) = 2，所以5节点的个数为2
	 * 9.计算有序表中有多少个小于某个key值的个数
	 * -        10  <- 节点的值
	 * -        100  <- 收集节点数
	 * -      /    \  <- 往右划
	 * -     6      30
	 * -     20     70   <- 往右划
	 * -          /    \
	 * -        20     50
	 * -        30     10
	 * - 往左划 ->    /    \
	 * -            45    55
	 * -            5     2
	 * 假设查询小于46的个数，
	 * 1) 46大于10，往右划，计算有多少个小于46的数字，父节点收集节点数 - 右子节点收集节点数(左子节点本来就小于46)
	 * 100 - 70 = 30
	 * 2) 46大于30，往右划，计算有多少个小于46的数字，父节点收集节点数 - 右子节点收集节点数(左子节点本来就小于46)
	 * 70 - 10 = 60
	 * 3) 46小于50，往左划，不计算
	 * 4) 46大于45，往右划，计算有多少个小于46的数字，父节点收集节点数 - 右子节点收集节点数(左子节点本来就小于46)
	 * 5 - 0 = 5
	 * 5) 小于46的个数 = 30 + 60 + 5 = 95
	 *
	 * @param nums
	 * @param lower
	 * @param upper
	 * @return 求arr数组中有多少个子数组，累加和在[lower, upper]范围上，返回达标的子数组数量
	 */
	public static int countRangeSumWithOrderedTable(int[] nums, int lower, int upper) {
		SizeBalancedTreeSet treeSet = new SizeBalancedTreeSet();
		long sum = 0;
		int ans = 0;
		//前缀和容器中先添加前缀和为0的元素
		treeSet.add(0);
		for (int i = 0; i < nums.length; i++) {
			sum += nums[i];
			//前缀和的范围[sum - upper,  sum - lower]
			//[10, 20]
			//小于21的个数 - 小于10的个数 = 10 ~ 20范围的个数
			long a = treeSet.lessKeySize(sum - lower + 1);
			long b = treeSet.lessKeySize(sum - upper);
			ans += a - b;
			treeSet.add(sum);
		}
		return ans;
	}

	public static class SBTNode {
		public long key;
		public SBTNode l;
		public SBTNode r;

		/**
		 * SizeBalanced树的平衡因素
		 */
		public long size;
		/**
		 * SizeBalanced树中每个节点收集的节点数
		 */
		public long all;

		public SBTNode(long k) {
			key = k;
			size = 1;
			all = 1;
		}
	}

	public static class SizeBalancedTreeSet {
		public SBTNode root;
		public HashSet<Long> set = new HashSet<>();

		public SizeBalancedTreeSet() {

		}

		/**
		 * 左旋
		 *
		 * @param cur
		 * @return
		 */
		public SBTNode leftRotate(SBTNode cur) {
			//目前当前节点的收集节点数 = 当前节点的左子节点的收集节点数 - 当前节点的右子节点的收集节点数
			long same = (cur.l != null ? cur.l.all : 0) - (cur.r != null ? cur.r.all : 0);
			//左旋的过程
			//cur
			//   \
			//   left
			//   /  \
			//  o    o
 			SBTNode left = cur.r;
			cur.r = left.l;
			left.l = cur;

			left.size = cur.size;
			cur.size = (cur.l != null ? cur.l.size : 0) + (cur.r != null ? cur.r.size : 0) + 1;

			left.all = cur.all;
			cur.all = (cur.l != null ? cur.l.all : 0) + (cur.r != null ? cur.r.all : 0) + same;
			return left;
		}

		/**
		 * 右旋
		 *
		 * @param cur
		 * @return
		 */
		public SBTNode rightRotate(SBTNode cur) {
			//目前当前节点的收集节点数 = 当前节点的左子节点的收集节点数 - 当前节点的右子节点的收集节点数
			long same = (cur.l != null ? cur.l.all : 0) - (cur.r != null ? cur.r.all : 0);
			//右旋的过程
			//    cur
			//    /
			// right
			// /   \
			//o     o
			SBTNode right = cur.l;
			cur.l = right.r;
			right.r = cur;

			right.size = cur.size;
			cur.size = (cur.l != null ? cur.l.size : 0) + (cur.r != null ? cur.r.size : 0) + 1;

			right.all = cur.all;
			cur.all = (cur.l != null ? cur.l.all : 0) + (cur.r != null ? cur.r.all : 0) + same;
			return right;
		}

		public SBTNode maintain(SBTNode cur) {
			if (cur == null) {
				return null;
			}

			//左边的叔叔节点的子树节点个数
			long leftSize = cur.l != null ? cur.l.size : 0;
			//右边的左侄子节点的子树节点个数
			long rightLeftSize = cur.r != null && cur.r.l != null ? cur.r.l.size : 0;
			//右边的右侄子节点的子树节点个数
			long rightRightSize = cur.r != null && cur.r.r != null ? cur.r.r.size : 0;


			//右边的叔叔节点的子树节点个数
			long rightSize = cur.r != null ? cur.r.size : 0;
			//左边的左侄子节点的子树节点个数
			long leftLeftSize = cur.l != null && cur.l.l != null ? cur.l.l.size : 0;
			//左边的右侄子节点的子树节点个数
			long leftRightSize = cur.l != null && cur.l.r != null ? cur.l.r.size : 0;

			//SizeBalanced树不平衡的四种类型：LL、LR、RR、RL
			//LL：左边的左侄子的子树节点个数 大于 右边叔叔节点的子树节点个数
			if (leftLeftSize > rightSize) {
				cur = rightRotate(cur);

				cur.r = maintain(cur.r);
				cur = maintain(cur);
			}
			//LR：左边的右侄子的子树节点个数 大于 右边叔叔节点的子树节点个数
			else if (leftRightSize > rightSize) {
				cur.l = leftRotate(cur.l);
				cur = rightRotate(cur);

				cur.l = maintain(cur.l);
				cur.r = maintain(cur.r);
				cur = maintain(cur);
			}
			//RR：右边的右侄子节点的子树节点个数 大于 左边 叔叔节点的子树节点个数
			else if (rightRightSize > leftSize) {
				cur = leftRotate(cur);
				cur.l = maintain(cur.l);
				cur = maintain(cur);
			}
			//RL：右边的左侄子节点的子树节点个数 大于 左边叔叔节点的子树节点个数
			else if (rightLeftSize > leftSize) {
				cur.r = rightRotate(cur.r);
				cur = leftRotate(cur);

				cur.l = maintain(cur.l);
				cur.r = maintain(cur.r);
				cur = maintain(cur);
			}
			return cur;
		}

		private SBTNode add(SBTNode cur, long key, boolean contains) {
			//如果cur节点为空，直接创建cur节点并返回
			if (cur == null) {
				return new SBTNode(key);
			} else {
				//只要cur节点不为空，收集节点数++
				cur.all++;
				//如果已经添加过，直接返回cur节点
				if (cur.key == key) {
					return cur;
				}
				//如果没有添加过，查看Set集合中是否包含key
				else {
					//如果不包含key，cur节点的子树节点个数++
					if (!contains) {
						cur.size++;
					}
					if (key < cur.key) {
						//来到左子树
						cur.l = add(cur.l, key, contains);
					} else {
						//来到右子树
						cur.r = add(cur.r, key, contains);
					}
				}
			}
			return cur;
		}

		public void add(long sum) {
			//判断Set集合是否包含sum，如果包含是true，不包含是false
			boolean contains = set.contains(sum);
			//在有序表中添加sum值
			root = add(root, sum, contains);
			set.add(sum);
		}

		/**
		 * @param key
		 * @return 返回有序表中小于指定key值的个数
		 */
		public long lessKeySize(long key) {
			SBTNode cur = root;
			long ans = 0;
			while (cur != null) {
				if (key == cur.key) {
					//cur节点的左子节点不为空
					//直接返回ans + cur节点左子节点收集的节点数
					return ans + (cur.l != null ? cur.l.all : 0);
				}
				//往左边
				//左边不计算
				else if (key < cur.key) {
					cur = cur.l;
				}
				//往右边
				//右边计算 = cur节点收集的节点数 - cur节点的右子节点收集的节点数
				else {
					ans += (cur.all) - (cur.r != null ? cur.r.all : 0);
					cur = cur.r;
				}
			}
			return ans;
		}
	}
}
