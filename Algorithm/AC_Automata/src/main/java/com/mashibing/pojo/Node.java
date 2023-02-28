package com.mashibing.pojo;

/**
 * 前缀树的节点
 *
 * @author xcy
 * @date 2022/5/26 - 10:07
 */
public class Node {
	/**
	 * end表示如果某个节点，确实是某个字符串的结尾，end就不为空，并且end的值是该字符串
	 * 如果某个节点只是该字符串的沿途节点，end就为空
	 * 举例：
	 * o -> end:null
	 * |a
	 * o -> end:null
	 * |b
	 * o -> end:null
	 * |c
	 * o -> end:"abc"
	 */
	public String end;

	/**
	 * 建立好AC自动机，也就是建立好前缀树以及fail节点
	 * 大文章在AC自动机找敏感词，如果收集到某个字符串，并且end不为空，使用endUse标记为已经收集过了，下次不再收集
	 * 防止收集到重复的敏感词
	 * o -> end:null
	 * |a
	 * o -> end:null
	 * |b
	 * o -> end:null
	 * |c
	 * o -> end:"abc" -> endUse:true，表示收集过
	 */
	public boolean endUse;

	/**
	 * fail指针
	 */
	public Node fail;

	/**
	 * 前缀树下级的路
	 */
	public Node[] nexts;

	public Node() {
		end = null;
		endUse = false;
		fail = null;
		//假设大文章都是小写的26个英文字母
		nexts = new Node[26];
	}
}
