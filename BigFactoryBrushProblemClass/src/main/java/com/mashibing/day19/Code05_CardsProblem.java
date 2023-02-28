package com.mashibing.day19;

import java.util.LinkedList;

/**
 * 一张扑克有3个属性，每种属性有3种值（A、B、C）
 * 比如"AAA"，第一个属性值A，第二个属性值A，第三个属性值A
 * 比如"BCA"，第一个属性值B，第二个属性值C，第三个属性值A
 * 给定一个字符串类型的数组cards[]，每一个字符串代表一张扑克
 * 从中挑选三张扑克，一个属性达标的条件是：这个属性在三张扑克中全一样，或全不一样
 * 挑选的三张扑克达标的要求是：每种属性都满足上面的条件
 * 比如："ABC"、"CBC"、"BBC"
 * 第一张第一个属性为"A"、第二张第一个属性为"C"、第三张第一个属性为"B"，全不一样
 * 第一张第二个属性为"B"、第二张第二个属性为"B"、第三张第二个属性为"B"，全一样
 * 第一张第三个属性为"C"、第二张第三个属性为"C"、第三张第三个属性为"C"，全一样
 * 每种属性都满足在三张扑克中全一样，或全不一样，所以这三张扑克达标
 * 返回在cards[]中任意挑选三张扑克，达标的方法数
 * <p>
 * 解题思路：
 * 根据数据量猜解法
 * 必须从牌面着手，因为只有27种牌面
 *
 * @author xcy
 * @date 2022/8/10 - 16:31
 */
public class Code05_CardsProblem {
	public static void main(String[] args) {
		int size = 20;
		int testTime = 100000;
		System.out.println("test begin");
		for (int i = 0; i < testTime; i++) {
			String[] arr = generateCards(size);
			int ans1 = ways1(arr);
			int ans2 = ways2(arr);
			if (ans1 != ans2) {
				for (String str : arr) {
					System.out.println(str);
				}
				System.out.println(ans1);
				System.out.println(ans2);
				break;
			}
		}
		System.out.println("test finish");

		long start = 0;
		long end = 0;
		String[] arr = generateCards(10000000);
		System.out.println("arr size : " + arr.length + " runtime test begin");
		start = System.currentTimeMillis();
		ways2(arr);
		end = System.currentTimeMillis();
		System.out.println("run time : " + (end - start) + " ms");
		System.out.println("runtime test end");
	}

	public static int ways1(String[] cards) {
		LinkedList<String> picks = new LinkedList<>();
		return process1(cards, 0, picks);
	}

	public static int process1(String[] cards, int index, LinkedList<String> picks) {
		if (picks.size() == 3) {
			return getWays1(picks);
		}
		if (index == cards.length) {
			return 0;
		}
		int ways = process1(cards, index + 1, picks);
		picks.addLast(cards[index]);
		ways += process1(cards, index + 1, picks);
		picks.pollLast();
		return ways;
	}

	public static int getWays1(LinkedList<String> picks) {
		char[] s1 = picks.get(0).toCharArray();
		char[] s2 = picks.get(1).toCharArray();
		char[] s3 = picks.get(2).toCharArray();
		for (int i = 0; i < 3; i++) {
			if ((s1[i] != s2[i] && s1[i] != s3[i] && s2[i] != s3[i]) || (s1[i] == s2[i] && s1[i] == s3[i])) {
				continue;
			}
			return 0;
		}
		return 1;
	}


	public static int ways2(String[] cards) {
		if (cards == null || cards.length == 0) {
			return 0;
		}
		//统计27种牌面，每种牌面的张数
		int[] counts = new int[27];
		for (String card : cards) {
			char[] str = card.toCharArray();
			//计算每个字符串对应的牌面值
			counts[(str[0] - 'A') * 9 + (str[1] - 'A') * 3 + (str[2] - 'A')]++;
		}
		int ways = 0;
		//遍历统计每种牌面值对应的张数
		for (int faceValue = 0; faceValue < 27; faceValue++) {
			//每种牌面值对应的张数
			int n = counts[faceValue];
			//如果每种牌面值对应的张数 >= 3才能进行统计方法数
			if (n >= 3) {
				//每种牌面值对应的张数 == 3就是1种牌面
				//否则每种牌面值对应的张数 > 3，需要根据Cn3进行计算
				ways += n == 3 ? 1 : (n * (n - 1) * (n - 2) / 6);
			}
		}
		//之前收集过的牌面
		LinkedList<Integer> path = new LinkedList<>();
		for (int i = 0; i < 27; i++) {
			//每种牌面的张数不能为0
			if (counts[i] != 0) {
				//从i这个牌面值开始，依次递增
				path.addLast(i);
				ways += process(counts, i, path);
				path.pollLast();
			}
		}
		return ways;
	}

	/**
	 * @param counts 统计每种牌面的张数，比如："AAA",100张,"ABC",200张
	 * @param pre    之前获取到的最大牌面值，A = 0, B = 1, C = 2
	 *               AAA -> 牌面值0
	 *               AAB -> 牌面值1
	 *               AAC -> 牌面值2
	 *               ABA -> 牌面值3
	 *               ABB -> 牌面值4
	 *               ABC -> 牌面值5
	 * @param path   已经收集到的牌面，比如："AAB", "ABC", "CAB"，而且必须是牌面值依次递增
	 * @return 返回达标的方法数
	 */
	public static int process(int[] counts, int pre, LinkedList<Integer> path) {
		//只要收集到3种牌面，就进行计算方法数
		if (path.size() == 3) {
			return getWays2(counts, path);
		}
		int ways = 0;
		for (int next = pre + 1; next < 27; next++) {
			if (counts[next] != 0) {
				path.addLast(next);
				ways += process(counts, next, path);
				path.pollLast();
			}
		}
		return ways;
	}

	/**
	 * 根据每种牌面的张数，每次按照3种牌面进行统计总的达标方法数
	 *
	 * @param counts 统计每种牌面的张数，比如："AAA",100张,"ABC",200张
	 * @param path   已经收集到的牌面，比如："AAB", "ABC", "CAB"，而且必须是牌面值依次递增
	 * @return 返回达标的总方法数
	 */
	private static int getWays2(int[] counts, LinkedList<Integer> path) {
		//第1个牌面
		int faceValue1 = path.get(0);
		//第2个牌面
		int faceValue2 = path.get(1);
		//第3个牌面
		int faceValue3 = path.get(2);
		for (int i = 9; i > 0; i /= 3) {
			int cur1 = faceValue1 / i;
			int cur2 = faceValue2 / i;
			int cur3 = faceValue3 / i;
			faceValue1 %= i;
			faceValue2 %= i;
			faceValue3 %= i;
			//牌面值达标的具体要求
			//要不全都不一样，要不全都一样
			if ((cur1 != cur2 && cur1 != cur3 && cur2 != cur3) || (cur1 == cur2 && cur1 == cur3)) {
				continue;
			}
			return 0;
		}
		faceValue1 = path.get(0);
		faceValue2 = path.get(1);
		faceValue3 = path.get(2);
		return counts[faceValue1] * counts[faceValue2] * counts[faceValue3];
	}

	/**
	 * 生成随机长度的，并且字面值只包含"A"，"B"，"C"的字符串数组
	 *
	 * @param size 字符串数组的长度
	 * @return 返回随机长度的，并且字面值只包含"A"，"B"，"C"的字符串数组
	 */
	public static String[] generateCards(int size) {
		int n = (int) (Math.random() * size) + 3;
		String[] ans = new String[n];
		for (int i = 0; i < n; i++) {
			char cha0 = (char) ((int) (Math.random() * 3) + 'A');
			char cha1 = (char) ((int) (Math.random() * 3) + 'A');
			char cha2 = (char) ((int) (Math.random() * 3) + 'A');
			ans[i] = String.valueOf(cha0) + String.valueOf(cha1) + String.valueOf(cha2);
		}
		return ans;
	}
}
