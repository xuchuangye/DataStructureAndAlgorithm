package com.mashibing;

/**
 * 题目二进阶：
 * 32位无符号整数的范围是0~4,294,967,295，
 * 现在有一个正好包含40亿个无符号整数的文件，
 * 所以在整个范围中必然存在没出现过的数。
 * 内存限制为 3KB，但是只用找到一个没出现过的数即可
 * <p>
 * 思路分析：
 *
 * @author xcy
 * @date 2022/5/28 - 15:19
 */
public class SegmentedStatistics {
	public static void main(String[] args) {

	}

	public final int[] range;

	public SegmentedStatistics() {
		//题目要求内存限制为3KB，也就是3000Byte，4个Byte组成一个int类型，所以是3000 / 4 = 750
		//找到离750最近并且比750小的2的5次方 -> 512，所以int[] 申请数组长度为512
		//表示将0 ~ 2的32次方 - 1的范围分成512段
		range = new int[512];
	}

	public void add(int nubmer) {
		range[nubmer / 8388608] = range[nubmer / 8388608] | (1 << (nubmer % 8388608));
	}

	public void delete(int nubmer) {
		range[nubmer / 8388608] = range[nubmer / 8388608] & ~(1 << (nubmer % 8388608));
	}

	public boolean contains(int number) {
		return (range[number / 8388608] & (1 << (number % 8388608))) != 0;
	}

	/*public long find() {
		long left = 0;
		long right = (long) Math.pow(2, 32);
		process(left, right);
	}

	public long process(long left, long right) {
		if (left >= right) {
			return -1;
		}
		long mid = left + ((right - left) >> 1);
		long process1 = process(left, mid);
		long process2 = process(mid + 1, right);
		long L = 0;
		long R = 0;
		if (process1 < (long)(Math.pow(2, 31))) {
			L = left;
			R = mid;
			mid = left + ((mid - left) >> 1);
			process(L, mid);
			process(mid + 1, R);
		}
		if (process2 < (long)(Math.pow(2, 31))) {
			L = mid + 1;
			mid = (mid + 1) + ((R - (mid + 1)) >> 1);
			process(L, mid);
			process(mid + 1,R);
		}
	}*/
}
