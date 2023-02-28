package com.mashibing;

/**
 * @author xcy
 * @date 2022/5/25 - 11:42
 */
public class IndexTree {
	public static void main(String[] args) {

	}

	private int[] sum;
	private int MAXN;

	public IndexTree(int size) {
		MAXN = size;
		sum = new int[MAXN + 1];
	}

	/**
	 * 返回1 ~ 指定索引上的累加和
	 *
	 * 时间复杂度：O(logN) 跟索引的二进制位数有关
	 *
	 * arr[] 1 ~ 358的累加和 =
	 *   help[]0101100110索引的累加和
	 * + help[]0101100100索引的累加和
	 * + help[]0101100000索引的累加和
	 * + help[]0101000000索引的累加和
	 * + help[]0100000000索引的累加和
	 * + help[]0000000000索引的累加和
	 * @param index 指定索引
	 * @return返回 1 ~ 指定索引index的累加和
	 */
	public int sum(int index) {
		int res = 0;
		while (index > 0) {
			res += sum[index];
			index -= index & (~index + 1);
		}
		return res;
	}

	/**
	 * 在指定索引上的值进行加操作
	 *
	 * 时间复杂度：O(logN) 跟索引的二进制位数有关
	 *
	 * add() 修改指定i位置的值，跟每次i的二进制最后一个1加1的位置的值有关
	 * 举例：
	 * arr[] 索引 = 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16
	 * 假设6位置上的值被修改了，那么受牵连的索引有6位置，8位置，16位置...
	 *
	 * 6的二级制：00000110
	 * 第一个受牵连的索引：最后一个1加1的二进制：00001000 -> 8
	 * 第二个受牵连的索引：最后一个1加1的二进制：00010000 -> 16
	 * ...直到越界为止
	 *
	 * 所以如果6位置上的值加上指定的值num
	 * 那么8位置上的值也加上num
	 * 那么16位置上的值也加上num
	 * ...直到越界为止
	 * @param index 指定索引
	 * @param num 加操作的值
	 */
	public void add(int index, int num) {
		while (index <= MAXN) {
			sum[index] = sum[index] + num;
			index += index & (~index + 1);
		}
	}
}
