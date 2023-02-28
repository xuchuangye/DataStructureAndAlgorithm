package com.mashibing.day22;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Stack;

/**
 * 一个不含有负数的数组可以代表一圈环形山，每个位置的值代表山的高度
 * 比如， {3,1,2,4,5}、{4,5,3,1,2}或{1,2,4,5,3}都代表同样结构的环形山
 * 山峰A和山峰B能够相互看见的条件为:
 * 1.如果A和B是同一座山，认为不能相互看见
 * 2.如果A和B是不同的山，并且在环中相邻，认为可以相互看见
 * 3.如果A和B是不同的山，并且在环中不相邻，假设两座山高度的最小值为min
 * 1)如果A通过顺时针方向到B的途中没有高度比min大的山峰，认为A和B可以相互看见
 * 2)如果A通过逆时针方向到B的途中没有高度比min大的山峰，认为A和B可以相互看见
 * 两个方向只要有一个能看见，就算A和B可以相互看见
 * 给定一个不含有负数且没有重复值的数组 arr，请返回有多少对山峰能够相互看见
 * 进阶，给定一个不含有负数但可能含有重复值的数组arr，返回有多少对山峰能够相互看见
 * <p>
 * 解题思路：
 * 单调栈
 * <p>
 * 解题分析：
 * 1.
 * 可见山峰对
 * 1)相邻必可见
 * 举例：
 * -  4 —— 2
 * - /      \
 * -5       1
 * - \     /
 * -    3
 * 比如：1和2可见，2和4可见，4和5可见，5和3可见，3和1可见
 * 2)不相邻的x和y，有两条路可见或者不可见，顺时针和逆时针，x和y中间只要有一条路并且路上的所有山峰的高度小于Math.min(x, y)，那么x和y就是可见的
 * 举例：
 * -  4 —— 2
 * - /      \
 * -5       1
 * - \     /
 * -    3
 * 3和4是可见的，虽然3 —— 5 —— 4这条路不可见，但是3 —— 1 —— 2 —— 4这条路可见，因为沿途的1和2的高度都比3的高度低，所以3和4可见
 * 2.
 * 秉持高度低的山峰去 找 高度高的山峰既不会算重复，也不会算漏
 * 因为高找低的山峰会算重复
 * 3.
 * 当arr[]中无重复值时，除去max和次max，每一个点都至少有两对山峰对，总共N个点，除去max和次max，剩余N - 2个点，每个点都有两对山峰对，还有(max, 次max)这一对
 * 一共(N - 2) * 2 + 1 -> 2N - 3
 * 举例：
 * -max       次max
 * -  \        /
 * -  旗子   旗子
 * -   \     /
 * -      x
 * 根据高度低找高度高的原则，x点从两边出发，最起码能够找到两对山峰对，将旗子插到中间
 * <p>
 * 4.
 * 当arr[]中有重复值时，需要使用单调栈
 * 举例：
 * -  9 —— 7
 * -        \
 * -         7
 * -        /
 * -       7
 * -      /
 * -9 —— 7
 * max = 9,
 * 单调栈：按照从小到大进行排序
 * | 7, 4个 |
 * | 9, 1个 |
 * ——————————
 * 当准备入栈的元素值9大于栈中元素7，弹出栈顶元素7，计算7的山峰对：对外的逆序对->2 * 4 = 8个，对内C(4,2) = 6个，一共14个
 * C-Combination 组合数
 * C(n,m) = P(n,m) / P(m,m) = n! / m!(n-m)
 * n = 4, m = 2
 * C(4,2) = P(4,2) / P(2,2) = 4! / 2!(4-2) = (4 * 3) / (2 * 1) = 6
 * 当遍历完arr[]所有的元素之后，栈中可能还存在元素
 * 1）当栈中元素还存在两条数据以上时，除去剩余的两条数据，其余的数据的推算公式是：2 * k + C(K, 2)
 * 2）当栈中元素只剩下两条数据时，查看最后一条数据的个数：
 * 如果只有一个，那么倒数第二条的数据的推算公式是：对外1 * k + 对内C(K, 2)
 * 举例：
 * | 7, 4个 |
 * | 9, 1个 |
 * ——————————
 * -    7 —— 7
 * -  /        \
 * -7           7
 * -  \       /
 * -      9
 * 如果不止一个，那么倒数第二条的数据的推算公式是：对外2 * k + 对内C(K, 2)
 * 举例：
 * | 7, 4个 |
 * | 9, 2个 |
 * ——————————
 * -    7 —— 7
 * -  /        \
 * -7           7
 * -  \       /
 * -    9 —— 9
 * 2）当栈中元素只剩下一条数据时，查看最后一条数据的个数：
 * 如果只有一个，那么最后一条的数据的推算公式是：对外0对 + 对内0
 * 如果不止一个，那么最后一条的数据的推算公式是：对外0对 + 对内C(K, 2)
 *
 * @author xcy
 * @date 2022/8/15 - 8:19
 */
public class Code04_VisibleMountains {
	public static void main(String[] args) {
		int size = 100;
		int max = 100;
		int testTimes = 100000;
		System.out.println("test begin!");
		for (int i = 0; i < testTimes; i++) {
			int[] arr = getRandomArray(size, max);
			if ((rightWay(arr) != getVisibleNum(arr)) || (returnVisiblePeak(arr) != getVisibleNum(arr))) {
				printArray(arr);
				System.out.println(rightWay(arr));
				System.out.println(getVisibleNum(arr));
				System.out.println(returnVisiblePeak(arr));
				break;
			}
		}
		System.out.println("test end!");
	}

	public static class Node {
		/**
		 * 山峰的高度
		 */
		public int value;
		/**
		 * 山峰的个数
		 */
		public int count;

		public Node(int value) {
			this.value = value;
			this.count = 1;
		}
	}

	public static int returnVisiblePeak(int[] arr) {
		if (arr == null || arr.length < 2) {
			return 0;
		}
		/*if (arr.length == 2) {
			return 1;
		}
		if (arr.length == 3) {
			return 3;
		}*/
		int N = arr.length;
		//最高山峰
		int maxIndex = 0;
		for (int i = 0; i < N; i++) {
			maxIndex = arr[maxIndex] < arr[i] ? i : maxIndex;
		}
		int index = nextIndex(maxIndex, N);
		int ans = 0;
		Stack<Node> stack = new Stack<>();
		//将最高山峰和最高山峰的座数入栈底
		stack.push(new Node(arr[maxIndex]));
		while (index != maxIndex) {
			//当栈顶元素的值 < 准备入栈元素的值，进行结算
			while (stack.peek().value < arr[index]) {
				//弹出栈顶元素
				int K = stack.pop().count;
				//结算
				//如果个数不止1个，对外2 * K + 对内C(K, 2)
				//如果个数只有1个，对外2 * K
				ans += 2 * K + getInternalSum(K);
			}
			if (stack.peek().value == arr[index]) {
				stack.peek().count++;
			} else {
				stack.push(new Node(arr[index]));
			}
			index = nextIndex(index, N);
		}
		while (stack.size() > 2) {
			//如果个数不止1个，对外2 * K + 对内C(K, 2)
			//如果个数只有1个，对外2 * K
			int K = stack.pop().count;
			ans += 2 * K + getInternalSum(K);
		}
		if (stack.size() == 2) {
			//如果栈中只剩下两条记录时，查看最后一条记录
			//如果最后一条记录的个数为1，就是对外1 * K，个数大于1，就是对外2 * K
			//7, 4个
			//9, 1个
			int K = stack.pop().count;//(7, 4)
			ans += getInternalSum(K) + (stack.peek().count == 1 ? K : 2 * K);
		}
		ans += getInternalSum(stack.pop().count);
		return ans;
	}

	// 栈中放的记录，
	// value就是值，times是收集的个数
	public static class Record {
		public int value;
		public int times;

		public Record(int value) {
			this.value = value;
			this.times = 1;
		}
	}

	public static int getVisibleNum(int[] arr) {
		if (arr == null || arr.length < 2) {
			return 0;
		}
		int N = arr.length;
		int maxIndex = 0;
		// 先在环中找到其中一个最大值的位置，哪一个都行
		for (int i = 0; i < N; i++) {
			maxIndex = arr[maxIndex] < arr[i] ? i : maxIndex;
		}
		Stack<Record> stack = new Stack<>();
		// 先把(最大值,1)这个记录放入stack中
		stack.push(new Record(arr[maxIndex]));
		// 从最大值位置的下一个位置开始沿next方向遍历
		int index = nextIndex(maxIndex, N);
		// 用“小找大”的方式统计所有可见山峰对
		int res = 0;
		// 遍历阶段开始，当index再次回到maxIndex的时候，说明转了一圈，遍历阶段就结束
		while (index != maxIndex) {
			// 当前数要进入栈，判断会不会破坏第一维的数字从顶到底依次变大
			// 如果破坏了，就依次弹出栈顶记录，并计算山峰对数量
			while (stack.peek().value < arr[index]) {
				int k = stack.pop().times;
				// 弹出记录为(X,K)，如果K==1，产生2对; 如果K>1，产生2*K + C(2,K)对。
				res += getInternalSum(k) + 2 * k;
			}
			// 当前数字arr[index]要进入栈了，如果和当前栈顶数字一样就合并
			// 不一样就把记录(arr[index],1)放入栈中
			if (stack.peek().value == arr[index]) {
				stack.peek().times++;
			} else { // >
				stack.push(new Record(arr[index]));
			}
			index = nextIndex(index, N);
		}
		// 清算阶段开始了
		// 清算阶段的第1小阶段
		while (stack.size() > 2) {
			int times = stack.pop().times;
			res += getInternalSum(times) + 2 * times;
		}
		// 清算阶段的第2小阶段
		if (stack.size() == 2) {
			int times = stack.pop().times;
			res += getInternalSum(times) + (stack.peek().times == 1 ? times : 2 * times);
		}
		// 清算阶段的第3小阶段
		res += getInternalSum(stack.pop().times);
		return res;
	}

	/**
	 * @param k k
	 * @return 如果k==1返回0，如果k>1返回C(2,k)
	 */
	public static int getInternalSum(int k) {
		return k == 1 ? 0 : (k * (k - 1) / 2);
	}

	/**
	 * 环形数组中当前位置为i，数组长度为size，返回i的下一个位置
	 *
	 * @param i    当前位置i
	 * @param size 数组长度size
	 * @return 返回i的下一个位置
	 */
	public static int nextIndex(int i, int size) {
		return i < (size - 1) ? (i + 1) : 0;
	}

	/**
	 * 环形数组中当前位置为i，数组长度为size，返回i的上一个位置
	 *
	 * @param i    当前位置为i
	 * @param size 数组长度为size
	 * @return 返回i的上一个位置
	 */
	public static int lastIndex(int i, int size) {
		return i > 0 ? (i - 1) : (size - 1);
	}

	/**
	 * @param arr 原始数组
	 * @return for test, O(N^2)的解法，绝对正确
	 */
	public static int rightWay(int[] arr) {
		if (arr == null || arr.length < 2) {
			return 0;
		}
		int res = 0;
		HashSet<String> equalCounted = new HashSet<>();
		for (int i = 0; i < arr.length; i++) {
			// 枚举从每一个位置出发，根据“小找大”原则能找到多少对儿，并且保证不重复找
			res += getVisibleNumFromIndex(arr, i, equalCounted);
		}
		return res;
	}

	// for test
	// 根据“小找大”的原则返回从index出发能找到多少对
	// 相等情况下，比如arr[1]==3，arr[5]==3
	// 之前如果从位置1找过位置5，那么等到从位置5出发时就不再找位置1（去重）
	// 之前找过的、所有相等情况的山峰对，都保存在了equalCounted中
	public static int getVisibleNumFromIndex(int[] arr, int index,
	                                         HashSet<String> equalCounted) {
		int res = 0;
		for (int i = 0; i < arr.length; i++) {
			if (i != index) { // 不找自己
				if (arr[i] == arr[index]) {
					String key = Math.min(index, i) + "_" + Math.max(index, i);
					// 相等情况下，确保之前没找过这一对
					if (equalCounted.add(key) && isVisible(arr, index, i)) {
						res++;
					}
				} else if (isVisible(arr, index, i)) { // 不相等的情况下直接找
					res++;
				}
			}
		}
		return res;
	}

	// for test
	// 调用该函数的前提是，lowIndex和highIndex一定不是同一个位置
	// 在“小找大”的策略下，从lowIndex位置能不能看到highIndex位置
	// next方向或者last方向有一个能走通，就返回true，否则返回false
	public static boolean isVisible(int[] arr, int lowIndex, int highIndex) {
		if (arr[lowIndex] > arr[highIndex]) { // “大找小”的情况直接返回false
			return false;
		}
		int size = arr.length;
		boolean walkNext = true;
		int mid = nextIndex(lowIndex, size);
		// lowIndex通过next方向走到highIndex，沿途不能出现比arr[lowIndex]大的数
		while (mid != highIndex) {
			if (arr[mid] > arr[lowIndex]) {
				walkNext = false;// next方向失败
				break;
			}
			mid = nextIndex(mid, size);
		}
		boolean walkLast = true;
		mid = lastIndex(lowIndex, size);
		// lowIndex通过last方向走到highIndex，沿途不能出现比arr[lowIndex]大的数
		while (mid != highIndex) {
			if (arr[mid] > arr[lowIndex]) {
				walkLast = false; // last方向失败
				break;
			}
			mid = lastIndex(mid, size);
		}
		return walkNext || walkLast; // 有一个成功就是能相互看见
	}

	// for test
	public static int[] getRandomArray(int size, int max) {
		int[] arr = new int[(int) (Math.random() * size)];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = (int) (Math.random() * max);
		}
		return arr;
	}

	// for test
	public static void printArray(int[] arr) {
		if (arr == null || arr.length == 0) {
			return;
		}
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}
}
