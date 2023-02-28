package com.mashibing.graph.topology;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

/**
 * 拓扑排序
 * <p>
 * 拓扑排序一定是有向并且无环的图
 * <p>
 * 测试链接：https://www.lintcode.com/problem/topological-sorting
 *
 * @author xcy
 * @date 2022/5/4 - 17:02
 */
public class TopologySort2 {
	public static void main(String[] args) {

	}

	/**
	 * 图的节点信息类(不要提交这个类)
	 */
	public static class DirectedGraphNode {
		/**
		 * 该节点的值
		 */
		public int label;
		/**
		 * 该节点指向其他节点的集合
		 */
		public ArrayList<DirectedGraphNode> neighbors;

		public DirectedGraphNode(int x) {
			label = x;
			neighbors = new ArrayList<DirectedGraphNode>();
		}
	}

	/**
	 * 与当前节点有关的信息封装类
	 */
	public static class Record {
		/**
		 * 该节点
		 */
		public DirectedGraphNode node;
		/**
		 * 该节点的深度
		 */
		public long deep;

		public Record(DirectedGraphNode node, long deep) {
			this.node = node;
			this.deep = deep;
		}
	}

	public static class MyComparator implements Comparator<Record> {

		@Override
		public int compare(Record o1, Record o2) {
			return (int) (o2.deep - o1.deep);
		}
	}

	/**
	 * 图的拓扑排序
	 *
	 * @param graph 给定图中所有的节点
	 * @return 返回根据图的拓扑排序之后的所有节点的集合
	 */
	public static ArrayList<DirectedGraphNode> topSort(ArrayList<DirectedGraphNode> graph) {
		//创建缓存
		HashMap<DirectedGraphNode,Record> cache = new HashMap<>();
		//对给定的图中所有的节点进行遍历
		for (DirectedGraphNode node : graph) {
			//对每一个节点进行缓存操作
			cache(node, cache);
		}
		//将缓存中的值的映射值添加到records集合中
		/*
		//方式一
		for (Record record : cache.values()) {
			records.add(record);
		}*/
		//方式二
		// records.addAll(cache.values());
		//方式三
		ArrayList<Record> records = new ArrayList<>(cache.values());
		//根据Record的点次进行排序
		records.sort(new MyComparator());

		ArrayList<DirectedGraphNode> list = new ArrayList<>();
		//遍历records集合
		for (Record record : records) {
			//将Record信息封装类中的每一个DirectedGraphNode节点添加到list集合中
			list.add(record.node);
		}
		return list;
	}

	/**
	 * 对当前节点和当前节点指向其他节点的个数封装为Record对象
	 * 并且将当前节点与Record对象做缓存
	 * @param cur 当前节点
	 * @param cache 使用HashMap做缓存，key表示当前节点，value表示Record的信息封装类
	 * @return
	 */
	public static Record cache(DirectedGraphNode cur, HashMap<DirectedGraphNode, Record> cache) {
		//判断缓存中是否有当前节点，如果有，直接返回
		if (cache.containsKey(cur)) {
			//返回当前节点的value，也就是Record信息封装类
			return cache.get(cur);
		}
		//如果缓存中没有
		//创建当前节点深度的变量
		long deep = 0;
		//对当前节点指向其他节点的集合进行遍历
		for (DirectedGraphNode node : cur.neighbors) {
			//deep取节点的最大深度
			deep = Math.max(deep, cache(node, cache).deep);
		}
		//将当前节点和当前节点的深度这两个信息封装为Record对象
		//当前节点自己也要算
		Record result = new Record(cur, deep + 1);
		//并且添加到缓存中，方便查询其他节点时快速查询的效率的提升
		cache.put(cur, result);
		return result;
	}
}
