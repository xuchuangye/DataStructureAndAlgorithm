package com.mashibing;

/**
 * 前缀树
 * <p>
 * 为什么使用前缀树？
 * 因为哈希表添加字符串的时间复杂度为：O(K)K表示字符串的字符个数
 * 但是前缀树也是O(K)，并且前缀树比哈希表多了支持哪些字符串以某个字符串作为前缀的功能
 *
 * @author xcy
 * @date 2022/4/22 - 17:34
 */
public class PrefixTree {
	public static class Node {
		/**
		 * 表示多少个字符经过的次数
		 */
		public int pass;
		/**
		 * 表示以多少个字符结尾的次数
		 */
		public int end;
		/**
		 * 前缀树下级的路
		 */
		public Node[] nexts;

		//char c = 'b'  -> c - 'a'
		public Node() {
			pass = 0;
			end = 0;
			//26表示小写的英文字母
			/*
			0  a
			1  b
			2  c
			.. ..
			25 z
			nexts[i] == null表示i方向的路径不存在
			nexts[i] != null表示i方向的路径存在
			 */
			nexts = new Node[26];
		}
	}

	public Node root;

	public PrefixTree() {
		root = new Node();
	}

	/**
	 * insert()前缀树接收字符串，并且将拆分字符串为字符，依次挂到前缀树上
	 *
	 * @param word 单词
	 */
	public void insert(String word) {
		if (word == null) {
			return;
		}
		//root节点不能移动，所以需要辅助节点
		Node node = root;
		//任何字符串都需要经过root节点，所以首先pass++
		node.pass++;
		//获取字符串的字符数组
		char[] chars = word.toCharArray();
		//遍历字符数组中的每一个字符
		for (char aChar : chars) {
			//获取该字符
			//aChar - 'a'表示 当前字符去往哪条路径
			int path = aChar - 'a';
			//如果下一个节点的路径为空，则创建该节点
			if (node.nexts[path] == null) {
				node.nexts[path] = new Node();
			}
			//node继续指向经过下一个路径的节点
			node = node.nexts[path];
			//pass++
			node.pass++;
		}
		//当所有字符都遍历完，也就是当前接收的字符串的整个路径组建完毕时，end++;
		node.end++;
	}

	//delete()前缀树多次添加同样的字符串，现在只删除一次
	public void delete(String word) {
		//说明不存在，那就没有删除的必要
		if (search(word) == 0) {
			return;
		}
		//root节点不能移动，所以需要辅助节点
		Node node = root;
		//任何字符串肯定要经过root节点，所以node.pass--
		node.pass--;
		//获取字符串的字符数组
		char[] chars = word.toCharArray();
		//遍历字符数组的每一个元素
		for (char aChar : chars) {
			//search(word) != 0，那就表示至少有一个节点不为空，所以先node.pass--
			node.pass--;
			//获取当前字符的路径
			int path = aChar - 'a';
			//判断该路径下的节点是否存在
			//1.如果该路径下的节点存在，并且节点的pass==0，说明可以删除该节点，将该节点置空即可
			if (node.nexts[path].pass == 0) {
				//如果该路径下的节点的pass==0，说明可以删除该节点，将该节点置空即可
				node.nexts[path] = null;
				return;
			}
			//2.如果该路径下的节点不存在，继续指向经过该路径的下一个节点
			node = node.nexts[path];
		}
		//完整的遍历整个字符数组，说明找到该字符串并且已经删除，end--即可
		node.end--;
	}

	/**
	 * search()前缀树中出现指定字符串的次数，或者加入该字符串的次数
	 *
	 * @param word 单词
	 * @return 返回前缀树中出现指定字符串的次数，或者加入该字符串的次数
	 */
	public int search(String word) {
		//如果字符串为空，返回0
		if (word == null) {
			return 0;
		}
		//root节点不能移动，所以需要辅助节点
		Node node = root;
		//获取字符串的字符数组
		char[] chars = word.toCharArray();
		//遍历字符数组的每一个字符
		for (char aChar : chars) {
			//aChar - 'a'表示 当前字符去往哪条路径
			int index = aChar - 'a';
			//如果在遍历的中途出现路径为空，说明该字符串就不存在
			if (node.nexts[index] == null) {
				//返回0
				return 0;
			}
			//node继续指向经过下一个路径的节点
			node = node.nexts[index];
		}
		//完整的遍历整个字符数组，说明找到该字符串了，返回end即可
		return node.end;
	}

	/**
	 * prefixNumber()前缀树中有多少个字符串是以指定字符串作为前缀的
	 *
	 * @param pre 前缀
	 * @return 返回前缀树中有多少个字符串是以指定字符串作为前缀的
	 */
	public int prefixNumber(String pre) {
		//如果前缀为空，返回0
		if (pre == null) {
			return 0;
		}
		//root节点不能移动，所以需要辅助节点
		Node node = root;
		//获取字符串的字符数组
		char[] chars = pre.toCharArray();
		//遍历字符数组的每一个元素
		for (char aChar : chars) {
			//获取当前字符所在的路径，也就是nexts数组的索引
			int index = aChar - 'a';
			//如果当前字符经过所在的节点为空，说明没有找到该前缀，也就是没有必要继续查找了，直接返回0
			if (node.nexts[index] == null) {
				return 0;
			}
			//如果当前字符经过所在的节点不为空，继续指向经过该路径的下一个节点
			node = node.nexts[index];
		}
		//完整的遍历整个字符数组，说明找到该前缀了，返回经过该前缀的个数pass即可，pass就表示经过当前节点的次数
		return node.pass;
	}

}
