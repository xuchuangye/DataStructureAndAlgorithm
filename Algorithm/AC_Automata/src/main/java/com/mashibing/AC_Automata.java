package com.mashibing;

import com.mashibing.pojo.Node;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * AC自动机
 * 解决在一个大字符串中，找到多个候选字符串的问题
 * @author xcy
 * @date 2022/5/25 - 16:44
 */
public class AC_Automata {
	public Node root;

	//字符串在路上，不在节点上
	public AC_Automata() {
		root = new Node();
	}

	//insert() 构建前缀树，前缀树接收字符串，并且将拆分字符串为字符，依次挂到前缀树上
	//参数 字符串
	public void insert(String s) {
		if (s == null) {
			return;
		}
		//字符数组
		char[] str = s.toCharArray();
		Node cur = root;
		int index = 0;
		for (char c : str) {
			//路，0表示a路，1表示b路，2表示c路...
			index = c - 'a';
			//如果没有当前的路，那么就创建路
			if (cur.nexts[index] == null) {
				cur.nexts[index] = new Node();
			}
			//当前节点来到继续往下的路的节点
			cur = cur.nexts[index];
		}
		//如果当前字符串能够走通所有的路，end = 当前字符串
		cur.end = s;
	}

	//build() 配置fail指针指向
	public void build() {
		//宽度优先遍历使用Queue
		Queue<Node> queue = new LinkedList<>();
		queue.add(root);
		Node cur = null;
		Node curFail = null;
		//弹出父节点的时候，就帮子节点的fail指针设置好
		while (!queue.isEmpty()) {
			//先弹出父节点
			cur = queue.poll();
			//查看父节点下级所有的路
			for (int i = 0; i < 26; i++) {
				//如果路下面挂载节点叫有路，没有挂载节点叫无路
				//将父节点的子节点的fail指针设置好
				//如果真的有子节点
				if (cur.nexts[i] != null) {
					//先设置所有的fail都指向root节点，找到再设置为其他的节点，没有找到维持不变
					cur.nexts[i].fail = root;
					//父节点的指针指向curFail
					curFail = cur.fail;
					//寻找子节点的fail指针该连接谁
					//如果curFail不为空
					while (curFail != null) {
						//如果跳的第一步就有i方向的路，当前下级的路的fail指针就指向i方向的路
						if (curFail.nexts[i] != null) {
							cur.nexts[i].fail = curFail.nexts[i];
							break;
						}
						//如果跳的第一步没有i方向的路，curFail再往fail上跳
						curFail = curFail.fail;
					}
					//不需要判断某个字符串的前缀和某个字符串的后缀匹配并且长度最长
					//父节点留住之前的最长

					//设置好子节点的fail指针，将子节点放入队列中继续进行宽度优先遍历
					queue.add(cur.nexts[i]);
				}
			}
		}
	}


	//containWord()
	//参数 content：大文章
	public List<String> containWords(String content) {
		char[] str = content.toCharArray();
		Node cur = root;
		Node follow = null;
		int index = 0;
		List<String> ans = new ArrayList<>();
		//最多26条路
		for (int i = 0; i < 26; i++) {
			//字符0表示a路，1表示b路，2表示c路...
			index = str[i] - 'a';

			//如果当前字符在这条路上没有匹配出来，就随着fail指针方向走下条路
			while (cur.nexts[index] == null && cur != root) {
				cur = cur.fail;
			}
			//如果没有满足上面while循环，说明有两种情况

			//1.当前节点有能够往下走的路(包括fail指针方向的路)，就继续往下走
			//现在来到的路径，不是root，是可以继续往下匹配的
			//大文章：abce
			//fail指针的方向
			//   o          o
			//  / a        b \
			// o       —— ——  o
			// | b    /      c \
			// o —— ——  —— —— ——o
			// | c     /       e \
			// o —— ——            o
			// | k
			// o
			if (cur.nexts[index] != null) {
				cur = cur.nexts[index];
			}
			//2.当前节点没有能够往下走的路，重新回到root节点
			//现在来到的节点，匹配不了，是前缀树的根节点
			//大文章：b
			//    o
			//  / | \
			// a  b  c
			else {
				cur = root;
			}

			//来到任何节点，顺时针过一圈
			follow = cur;
			//当前节点先不动，follow方便查看的，并且去查看有没有敏感词，去收集敏感词
			while (follow != root) {
				//如果已经收集过了，直接退出while循环
				if (follow.endUse) {
					break;
				}
				//如果没有收集过，并且end不为空，收集敏感词，并且标记已经收集了
				if (follow.end != null) {
					ans.add(follow.end);
					follow.endUse = true;
				}
				//继续往fail指针方向遍历
				follow = follow.fail;
			}
		}
		return ans;
	}
}
