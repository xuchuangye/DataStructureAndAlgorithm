package com.mashibing.day20;

import java.util.Arrays;
import java.util.HashMap;

/**
 * 如果只给定一个二叉树前序遍历数组pre和中序遍历数组in，能否不重建树，而直接生成这个二叉树的后序数组并返回，
 * 已知二叉树中没有重复值
 *
 * @author xcy
 * @date 2022/8/13 - 8:33
 */
public class Code01_PreAndInArrayToPosArray<T> {
	public static void main(String[] args) {
		Integer[] pre = {1, 2, 4, 5, 3, 6, 7};
		Integer[] in = {4, 2, 5, 1, 6, 3, 7};
		Object[] integers = returnPosArrayWithHashMap(pre, in);
		for (Object integer : integers) {
			System.out.print(integer + " ");
		}
		System.out.println();
		String[] preString = {"a", "b", "d", "e", "c", "f", "g"};
		String[] inString = {"d", "b", "e", "a", "f", "c", "g"};
		Object[] strings = returnPosArrayWithHashMap(preString, inString);
		for (Object string : strings) {
			System.out.print(string + " ");
		}
		System.out.println();
	}

	/**
	 * @param pre
	 * @param in
	 * @return
	 */
	public static Object[] returnPosArray(Object[] pre, Object[] in) {
		if (pre == null || pre.length == 0 || in == null || in.length == 0) {
			return null;
		}
		Object[] pos = new Object[pre.length];
		process(pre, 0, pre.length - 1, in, 0, in.length - 1, pos, 0, pos.length - 1);
		return pos;
	}

	/**
	 * 先序遍历数组pre[]和中序遍历数组in[]以及后序遍历数组pos[]的长度等长
	 *
	 * @param pre 先序遍历数组
	 * @param L1
	 * @param R1
	 * @param in  中序遍历数组
	 * @param L2
	 * @param R2
	 * @param pos 后序遍历数组
	 * @param L3
	 * @param R3
	 */
	public static <T> void process(T[] pre, int L1, int R1,
	                               T[] in, int L2, int R2,
	                               T[] pos, int L3, int R3) {
		//表示当前先序遍历数组只有一个元素，那就是头节点
		//而先序遍历的第一个元素，也就是头节点，刚好对应后序遍历的最后一个元素，也是头节点
		//所以当前先序遍历数组只有一个元素，直接填充在pos[L3]中
		if (L1 == R1) {
			pos[L3] = pre[L1];
		}
		//表示当前先序遍历数组不止一个元素
		else {
			//先填充好后序遍历数组的头节点
			//先序遍历的第一个元素，也就是头节点，刚好对应后序遍历的最后一个元素，也是头节点
			pos[R3] = pre[L1];

			int index = L2;
			for (int i = L2; i <= R2; i++) {
				if (in[i] == pre[L1]) {
					index = i;
					break;
				}
			}
			//后序遍历数组的左子树
			//1.先序遍历数组的左子树
			//先序遍历数组的左子树从L1 + 1开始，因为先序遍历数组的第一个元素对应后序遍历数组的最后一个元素，并且已经填充好了
			//所以先序遍历数组从L1 + 1开始
			//因为index表示先序遍历数组的头节点在中序遍历数组的位置
			//那么中序遍历数组中，头节点的左子树长度为index - L2
			//所以先序遍历数组的左子树从L1 + index - L2结束
			//2.中序遍历数组的左子树
			//中序遍历数组的左子树从L2开始，到index - 1结束
			//3.后序遍历数组的左子树
			//中序遍历数组的左子树从L3开始，到L3 + index - L2 - 1结束
			process(pre, L1 + 1, L1 + index - L2, in, L2, index - 1, pos, L3, L3 + index - L2 - 1);
			//后序遍历数组的右子树
			//1.先序遍历数组的右子树
			//先序遍历数组的右子树从L1 + index - L2 + 1开始，到R1结束
			//2.中序遍历数组的右子树
			//中序遍历数组的右子树从index + 1开始，到R2结束
			//3.后序遍历数组的右子树
			//后序遍历数组的右子树从L3 + index - L2开始，到R3 - 1结束
			//因为先序遍历数组的第一个元素对应后序遍历数组的最后一个元素，并且已经填充好了，所以后序遍历数组的右子树到R3 - 1结束
			process(pre, L1 + index - L2 + 1, R1, in, index + 1, R2, pos, L3 + index - L2, R3 - 1);
		}
	}

	public static Object[] returnPosArrayWithHashMap(Object[] pre, Object[] in) {
		if (pre == null || in == null || in.length == 0 || pre.length != in.length) {
			return null;
		}
		int N = pre.length;
		Object[] pos = new Object[N];
		HashMap<Object, Object> hashMap = new HashMap<>();
		for (int i = 0; i < N; i++) {
			hashMap.put(in[i], i);
		}
		processWithHashMap(pre, 0, N - 1, in, 0, N - 1, pos, 0, N - 1, hashMap);
		return pos;
	}

	/**
	 * 先序遍历数组pre[]和中序遍历数组in[]以及后序遍历数组pos[]的长度等长
	 *
	 * @param pre 先序遍历数组
	 * @param L1
	 * @param R1
	 * @param in  中序遍历数组
	 * @param L2
	 * @param R2
	 * @param pos 后序遍历数组
	 * @param L3
	 * @param R3
	 */
	public static <T> void processWithHashMap(T[] pre, int L1, int R1,
	                                          T[] in, int L2, int R2,
	                                          T[] pos, int L3, int R3,
	                                          HashMap<Object, Object> hashMap) {
		//表示当前先序遍历数组只有一个元素，那就是头节点
		//而先序遍历的第一个元素，也就是头节点，刚好对应后序遍历的最后一个元素，也是头节点
		//所以当前先序遍历数组只有一个元素，直接填充在pos[L3]中
		if (L1 == R1) {
			pos[L3] = pre[L1];
		}
		//表示当前先序遍历数组不止一个元素
		else {
			//先填充好后序遍历数组的头节点
			//先序遍历的第一个元素，也就是头节点，刚好对应后序遍历的最后一个元素，也是头节点
			pos[R3] = pre[L1];

			Integer index = (Integer) hashMap.get(pre[L1]);
			//后序遍历数组的左子树
			//1.先序遍历数组的左子树
			//先序遍历数组的左子树从L1 + 1开始，因为先序遍历数组的第一个元素对应后序遍历数组的最后一个元素，并且已经填充好了
			//所以先序遍历数组从L1 + 1开始
			//因为index表示先序遍历数组的头节点在中序遍历数组的位置
			//那么中序遍历数组中，头节点的左子树长度为index - L2
			//所以先序遍历数组的左子树从L1 + index - L2结束
			//2.中序遍历数组的左子树
			//中序遍历数组的左子树从L2开始，到index - 1结束
			//3.后序遍历数组的左子树
			//中序遍历数组的左子树从L3开始，到L3 + index - L2 - 1结束
			processWithHashMap(pre, L1 + 1, L1 + index - L2, in, L2, index - 1, pos, L3, L3 + index - L2 - 1, hashMap);
			//后序遍历数组的右子树
			//1.先序遍历数组的右子树
			//先序遍历数组的右子树从L1 + index - L2 + 1开始，到R1结束
			//2.中序遍历数组的右子树
			//中序遍历数组的右子树从index + 1开始，到R2结束
			//3.后序遍历数组的右子树
			//后序遍历数组的右子树从L3 + index - L2开始，到R3 - 1结束
			//因为先序遍历数组的第一个元素对应后序遍历数组的最后一个元素，并且已经填充好了，所以后序遍历数组的右子树到R3 - 1结束
			processWithHashMap(pre, L1 + index - L2 + 1, R1, in, index + 1, R2, pos, L3 + index - L2, R3 - 1, hashMap);
		}
	}
}
