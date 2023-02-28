package com.mashibing.unionset;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 并查集 --> 使用HashMap的方式实现，针对int m, int n, int[][] positions进行了改进
 *
 * <p>
 * 核心逻辑：
 * 1、代表节点
 * <p>
 * 优化：
 * 1、数量少的节点集合挂载到数量多的节点集合上，减少集合链的长度
 * 2、如果节点查找该节点的代表节点，整个链过长，
 * 将代表节点优化为当前节点的直接父节点，那么每次操作的时间复杂度都是O(1)，减少集合链的长度
 * 并查集的应用：
 * 1、解决连通性的问题，尤其是图的连通性问题，时间复杂度O(1)
 *
 * @author xcy
 * @date 2022/5/3 - 9:17
 */
public class UnionSetWithPositions {
	private final HashMap<String, String> parents;
	private final HashMap<String, Integer> size;
	private final ArrayList<String> temp;
	private int sets;

	public UnionSetWithPositions(int m, int n) {
		parents = new HashMap<>();
		size = new HashMap<>();
		temp = new ArrayList<>();
		sets = 0;
	}

	public String findHead(String cur) {
		if (!cur.equals(parents.get(cur))) {
			temp.add(cur);
			cur = parents.get(cur);
		}
		for (String str: temp) {
			parents.put(str, cur);
		}
		temp.clear();
		return cur;
	}

	public void union(String moveKey, String key) {
		if (parents.containsKey(moveKey) && (parents.containsKey(key))) {
			String father1 = findHead(moveKey);
			String father2 = findHead(key);
			if (!father1.equals(father2)) {
				Integer size1 = size.get(father1);
				Integer size2 = size.get(father2);
				/*
				String big = size1 >= size2 ? father1 : father2;
				String small = big.equals(father1) ? father2 : father1;
				parents.put(small, big);
				size.put(big, size1 + size2);
				sets--;*/
				if (size1 >= size2) {
					parents.put(father1, father2);
					size.put(father1, size1 + size2);
					size.remove(father1);
				}else {
					parents.put(father2, father1);
					size.put(father2, size2 + size1);
					size.remove(father2);
				}
				sets--;
			}
		}
	}

	public int connect(int row, int col) {
		String key = String.valueOf(row) + "_" + String.valueOf(col);
		if (!parents.containsKey(key)) {
			parents.put(key, key);
			size.put(key, 1);
			sets++;
			String keyUp = String.valueOf(row - 1) + "_" + String.valueOf(col);
			String keyDown = String.valueOf(row + 1) + "_" + String.valueOf(col);
			String keyLeft = String.valueOf(row) + "_" + String.valueOf(col - 1);
			String keyRight = String.valueOf(row) + "_" + String.valueOf(col + 1);
			/*if (!parents.containsKey(keyUp) || !parents.containsKey(keyDown) || !parents.containsKey(keyLeft) || !parents.containsKey(keyRight)) {
				return sets;
			}*/
			union(keyUp, key);
			union(keyDown, key);
			union(keyLeft, key);
			union(keyRight, key);
		}
		return sets;
	}
}
