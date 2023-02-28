package com.mashibing.dijkstra.extend;

/**
 * @author xcy
 * @date 2022/5/5 - 17:35
 */
public class NodeRecord<T> {
	//当前节点
	public T node;
	//出发节点到当前节点的距离
	public int distance;

	public NodeRecord(T node, int distance) {
		this.node = node;
		this.distance = distance;
	}
}
