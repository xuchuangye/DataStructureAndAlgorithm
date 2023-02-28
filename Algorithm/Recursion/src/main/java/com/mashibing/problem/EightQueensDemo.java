package com.mashibing.problem;

/**
 * 递归 - 八皇后问题
 * 思路分析：
 * 理论上应该创建一个二维数组来表示棋盘，但是实际上可以通过算法，用一个一维数组即可解决问题.
 * arr[8] = {0 , 4, 7, 5, 2, 6, 1, 3}
 * 对应 arr的索引本身 表示第几行，即第几个皇后，索引对应的值 表示第几列，即皇后放置的位置
 * 例如：arr[i] = val ,表示第 i+1 个皇后，放在第 i+1 行的第 val+1 列
 *
 * @author xcy
 * @date 2022/3/19 - 15:17
 */
public class EightQueensDemo {
	public static final int MAX = 8;
	/**
	 * 保存皇后放置的位置
	 */
	public static final int[] array = new int[MAX];
	/**
	 * 累加所以放置所有皇后位置正解的次数
	 */
	public static int COUNT = 0;
	/**
	 * 判断当前准备放置的皇后和已经放置的皇后是否冲突的次数
	 */
	public static int JUDGE_COUNT = 0;

	public static void main(String[] args) {
		//放置皇后肯定要从第一个开始放置
		//因为数组的索引从0开始，所以从0开始放置，但是表示的意思是从第一个皇后开始放置
		placed(0);


		//打印放置所有皇后并且正解的次数
		System.out.printf("放置所有皇后并且正解的次数是：%d\n", COUNT);

		//判断当前准备放置的皇后和已经放置的皇后是否冲突的次数
		System.out.printf("当前准备放置的皇后和已经放置的皇后是否冲突的次数是：%d\n", JUDGE_COUNT);
	}

	/**
	 * 放置皇后
	 *
	 * @param n n表示第几行，也就是第 n+1 个皇后，因为数组索引从0开始，所以n也需要从0开始，防止产生数组索引越界的问题
	 */
	public static void placed(int n) {
		//判断n是第几个皇后，如果n==MAX表示，前面8个皇后已经放置好了，直接打印输出所有的皇后以及位置即可，然后返回
		if (n == MAX) {
			print();
			return;
		}
		//i从0开始，表示第 i+1列
		for (int i = 0; i < MAX; i++) {
			array[n] = i;
			//判断是否冲突
			if (judge(n)) {
				//如果不冲突，继续放置下一个皇后
				placed(n + 1);
			}
			//如果冲突，就会继续下一次循环，i++，也就是array[n]当前放置的皇后的位置往后移一位
		}
	}

	/**
	 * 当放置第n + 1 个皇后时，判断当前这个皇后是否和已经放置好的皇后的位置冲突
	 *
	 * @param n 表示第n + 1个皇后
	 * @return true就表示不冲突，false表示冲突
	 */
	public static boolean judge(int n) {
		JUDGE_COUNT++;
		//i表示第 I+1 个皇后
		for (int i = 0; i < n; i++) {
			//array[n] == array[i]表示当前这个皇后是否和已经放置好的皇后的位置处于同一列
			//Math.abs(n - i) == Math.abs(array[n]) - Math.abs(array[i])表示当前这个皇后是否和已经放置好的皇后的位置处于同一斜线
			if (array[i] == array[n] || Math.abs(n - i) == Math.abs(array[n] - array[i])) {
				//表示冲突
				return false;
			}
		}
		//表示不冲突
		return true;
	}

	/**
	 * 遍历输出所有的皇后以及摆放的位置
	 */
	public static void print() {
		COUNT++;
		//因为数组的索引是从0开始的，所以i也是从开始的，表示第 i+1 个皇后
		for (int i = 0; i < array.length; i++) {
			System.out.print("第" + (i + 1) + "行" + (array[i] + 1) + " ");
		}
		System.out.println();
	}
}
