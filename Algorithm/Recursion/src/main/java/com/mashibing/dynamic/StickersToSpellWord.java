package com.mashibing.dynamic;

import java.util.HashMap;

/**
 * 给定一个字符串str，给定一个字符串类型的数组arr，出现的字符都是小写英文
 * arr每一个字符串，代表一张贴纸，你可以把单个字符剪开使用，目的是评出str来
 * 返回需要至少多少张贴纸可以完成这个任务
 * <p>
 * 例子：str = "babac", arr = {"bc", "c", "abcd"}
 * 至少需要两张贴纸"ba"和"abcd"，因为使用这两张贴纸，把每一个字符单独剪开，含有2个a 2个b 1个c 可以拼出str的。所以返回2
 * <p>
 * 请问给定一个字符串str，返回在给定数组arr中，至少需要多少张贴纸(每一张贴纸都可以重复使用)能够拼出str
 *
 * @author xcy
 * @date 2022/5/8 - 16:05
 */
public class StickersToSpellWord {

	public static void main(String[] args) {
		String target = "aaabbbccc";
		String[] stickers = {"aab", "bbk", "acc"};
		int count1 = minStickers(stickers, target);
		int count2 = minStickersWithFrequency(stickers, target);
		int count3 = minStickersWithCache(stickers, target);
		System.out.println(count1);
		System.out.println(count2);
		System.out.println(count3);
	}

	/**
	 * 返回在给定的字符串数组stickers中至少多少张贴纸能够拼出给定的字符串target --> 使用暴力递归的方式
	 *
	 * @param stickers 给定的字符串数组
	 * @param target   给定的字符串
	 * @return 返回在给定的字符串数组stickers中至少多少张贴纸能够拼出给定的字符串target
	 */
	public static int minStickers(String[] stickers, String target) {
		if (stickers == null || stickers.length == 0 || target == null || target.length() == 0) {
			return 0;
		}
		int ans = coreLogic(stickers, target);
		return ans == Integer.MAX_VALUE ? -1 : ans;
	}

	/**
	 * 核心逻辑
	 *
	 * @param stickers 给定的字符串数组
	 * @param target   给定的字符串
	 * @return 返回在给定的字符串数组stickers中至少多少张贴纸能够拼出给定的字符串target
	 */
	public static int coreLogic(String[] stickers, String target) {
		if (target.length() == 0) {
			return 0;
		}
		//最小多少张贴纸
		int min = Integer.MAX_VALUE;
		for (String first : stickers) {
			//rest表示target字符串减去first包含的字符剩下的字符串
			String rest = minus(target, first);
			//判断rest不为空，并且rest字符串和初始的字符串target的长度不一样，说明减少了一定的字符
			if (rest.length() != target.length()) {
				//继续递归剩下的字符串rest，直到返回民
				min = Math.min(min, coreLogic(stickers, rest));
			}
		}
		//如果min == Integer.MAX_VALUE，说明多少张贴纸都不能拼出target字符串
		return min + (min == Integer.MAX_VALUE ? 0 : 1);
	}

	/**
	 * target字符串减去first字符串中包含的字符
	 *
	 * @param target 给定的字符串
	 * @param first target需要剔除first字符串中包含的字符
	 * @return 返回target字符串去除first字符串中包含的字符之后的字符串
	 */
	private static String minus(String target, String first) {
		char[] targetChars = target.toCharArray();
		char[] firstChars = first.toCharArray();
		int[] count = new int[26];
		//例如：target = "aabbcc"
		//count[i]：0表示a出现的次数，1表示b出现的次数，25表示z出现的次数
		for (char targetChar : targetChars) {
			count[targetChar - 'a']++;
		}
		//例如：first = "bbabk"
		//减去count[i]中出现的字符次数，只要次数大于0的字符
		for (char firstChar : firstChars) {
			count[firstChar - 'a']--;
		}
		//上述两个循环得到count[] = {a = 1, b = -1, c = 2, k = -1}
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < 26; i++) {
			//判断是否大于0，大于0表示还有剩余的字符
			if (count[i] > 0) {
				//count[i]表示还剩余的字符个数
				for (int j = 0; j < count[i]; j++) {
					//使用StringBuilder进行拼接
					stringBuilder.append((char) (i + 'a'));
				}
			}
		}
		return stringBuilder.toString();
	}



	/**
	 * 返回在给定的字符串数组stickers中至少多少张贴纸能够拼出给定的字符串target --> 使用动态递归的方式
	 * @param stickers 给定的字符串数组
	 * @param target 需要拼接的目标字符串
	 * @return 返回在给定的字符串数组stickers中至少多少张贴纸能够拼出给定的字符串target
	 */
	public static int minStickersWithFrequency(String[] stickers, String target) {
		if (stickers == null || stickers.length == 0 || target == null || target.length() == 0) {
			return 0;
		}
		//创建词频统计二维数组
		int[][] counts = new int[stickers.length][26];
		for (int i = 0; i < stickers.length; i++) {
			char[] chars = stickers[i].toCharArray();
			for (char aChar : chars) {
				counts[i][aChar - 'a']++;
			}
		}

		int ans = coreLogicWithFrequency(counts, target);
		return ans == Integer.MAX_VALUE ? -1 : ans;
	}

	/**
	 * 返回在给定的字符串数组stickers中至少多少张贴纸能够拼出给定的字符串target --> 使用动态递归的方式
	 * @param stickers 给定的字符串数组转换为词频统计的二维数组
	 * @param t 需要拼接的字符串
	 * @return 返回在给定的字符串数组stickers中至少多少张贴纸能够拼出给定的字符串t
	 */
	public static int coreLogicWithFrequency(int[][] stickers, String t) {
		if (t.length() == 0) {
			return 0;
		}
		//target字符串转换为字符数组targetCharArray
		char[] target = t.toCharArray();
		//统计targetCharArray中出现的字符次数
		//targetCount[0]表示a字符出现的次数，1表示b字符出现的次数，依次类推，25表示z字符出现的次数
		int[] tCount = new int[26];
		for (char aChar : target) {
			tCount[aChar - 'a']++;
		}

		int min = Integer.MAX_VALUE;

		for (int[] sticker : stickers) {
			//第一张贴纸
			//最关键的优化(重要的剪枝！这一步也是贪心算法)
			if (sticker[target[0] - 'a'] > 0) {
				StringBuilder stringBuilder = new StringBuilder();
				//TCount统计的26个索引位置上进行遍历
				for (int j = 0; j < 26; j++) {
					//如果tCount[j] > 0，说明该索引上有值
					if (tCount[j] > 0) {
						//给定的字符串数组转换为词频统计的二维数组，每一行都是字符串转换的词频统计的一维数组
						//tCount[j] - sticker[j]表示目标字符串的词频统计减去给定字符串的词频统计
						int num = tCount[j] - sticker[j];
						//对剩下的没有被减去的字符的数量进行遍历
						for (int k = 0; k < num; k++) {
							//依次添加到stringBuilder中，方便下一行字符串转换的词频统计
							stringBuilder.append((char) (j + 'a'));
						}
					}
				}
				String rest = stringBuilder.toString();
				//剩余的继续进行递归，词频统计
				min = Math.min(min, coreLogicWithFrequency(stickers, rest));
			}
		}

		return min + (min == Integer.MAX_VALUE ? 0 : 1);
	}


	/**
	 * 返回在给定的字符串数组stickers中至少多少张贴纸能够拼出给定的字符串target --> 使用傻缓存的方式
	 * @param stickers 给定的字符串数组
	 * @param target 需要拼接的目标字符串
	 * @return 返回在给定的字符串数组stickers中至少多少张贴纸能够拼出给定的字符串target
	 */
	public static int minStickersWithCache(String[] stickers, String target) {
		if (stickers == null || stickers.length == 0) {
			return 0;
		}
		int[][] counts = new int[stickers.length][26];
		for (int i = 0; i < stickers.length; i++) {
			char[] chars = stickers[i].toCharArray();
			for (char aChar : chars) {
				counts[i][aChar - 'a']++;
			}
		}
		HashMap<String, Integer> map = new HashMap<>();
		map.put("", 0);
		int ans = coreLoginWIthCache(counts, target, map);
		return ans == Integer.MAX_VALUE ? -1 : ans;
	}

	/**
	 * 返回在给定的字符串数组stickers中至少多少张贴纸能够拼出给定的字符串target
	 * @param stickers 给定的字符串数组
	 * @param t 需要拼接的目标字符串
	 * @param map 词频统计表
	 * @return 返回在给定的字符串数组stickers中至少多少张贴纸能够拼出给定的字符串target
	 */
	public static int coreLoginWIthCache(int[][] stickers, String t, HashMap<String, Integer> map) {
		if (map.containsKey(t)) {
			return map.get(t);
		}

		char[] target = t.toCharArray();
		int[] tCounts = new int[26];
		for (char aChar :target){
			tCounts[aChar - 'a']++;
		}
		int min = Integer.MAX_VALUE;

		for (int[] sticker : stickers) {
			if (sticker[target[0] - 'a'] > 0) {
				StringBuilder stringBuilder = new StringBuilder();
				for (int j = 0; j < 26; j++) {
					if (tCounts[j] > 0) {
						int nums = tCounts[j] - sticker[j];
						for (int k = 0; k < nums; k++) {
							stringBuilder.append((char) (j + 'a'));
						}
					}
				}
				String rest = stringBuilder.toString();
				min = Math.min(min, coreLoginWIthCache(stickers, rest, map));
			}
		}
		int ans = min + (min == Integer.MAX_VALUE ? 0 : 1);
		map.put(t, ans);
		return ans;
	}

}
