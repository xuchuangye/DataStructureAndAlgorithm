package com.mashibing.arithmetic.greedy;

import java.util.*;

/**
 * 贪心算法
 *
 * 为什么使用贪心算法？
 * 因为穷举法的效率极低，消耗时间过长
 *
 * 贪心算法的思路分析：
 * 目前并没有算法可以快速计算得到准备的值，使用贪婪算法，则可以得到非常接近的解，并且效率高。
 * 选择策略上，因为需要覆盖全部地区的最小集合:
 * 1、遍历所有的广播电台, 找到一个覆盖了最多未覆盖的地区的电台(此电台可能包含一些已覆盖的地区，但没有关系）
 * 2、将这个电台加入到一个集合中(比如ArrayList), 想办法把该电台覆盖的地区在下次比较时去掉。
 * 3、重复第1步，直到覆盖了所有的地区
 *
 * 贪心算法的注意事项：
 * 贪婪算法所得到的结果不一定是最优的结果(有时候会是最优解)，但是都是相对近似(接近)最优解的结果
 * @author xcy
 * @date 2022/4/2 - 8:42
 */
public class GreedyAlgorithm {
	public static void main(String[] args) {
		//所有广播(key -> String)覆盖的地区(value -> HashSet<String>)的Map集合
		Map<String, HashSet<String>> broadCasts = new HashMap<>();

		//
		HashSet<String> hashSet1 = new HashSet<>();
		hashSet1.add("北京");
		hashSet1.add("上海");
		hashSet1.add("天津");

		HashSet<String> hashSet2 = new HashSet<>();
		hashSet2.add("广州");
		hashSet2.add("北京");
		hashSet2.add("深圳");

		HashSet<String> hashSet3 = new HashSet<>();
		hashSet3.add("成都");
		hashSet3.add("上海");
		hashSet3.add("杭州");

		HashSet<String> hashSet4 = new HashSet<>();
		hashSet4.add("上海");
		hashSet4.add("天津");

		HashSet<String> hashSet5 = new HashSet<>();
		hashSet5.add("杭州");
		hashSet5.add("大连");

		broadCasts.put("k1", hashSet1);
		broadCasts.put("k2", hashSet2);
		broadCasts.put("k3", hashSet3);
		broadCasts.put("k4", hashSet4);
		broadCasts.put("k5", hashSet5);

		//广播能够覆盖的所有地区集合
		HashSet<String> allAreas = new HashSet<>();
		allAreas.add("北京");
		allAreas.add("上海");
		allAreas.add("广州");
		allAreas.add("深圳");
		allAreas.add("杭州");
		allAreas.add("成都");
		allAreas.add("天津");
		allAreas.add("大连");
		//System.out.println(allAreas.size());

		//辅助HashSet，保存覆盖地区最多的广播的地区集合
		HashSet<String> tempSet = new HashSet<>();

		//利用贪心算法计算之后最少广播所能够覆盖所有地区的广播集合
		ArrayList<String> areas = new ArrayList<>();

		greedyAlgorithm(broadCasts, allAreas, tempSet, areas);
	}

	/**
	 * 贪心算法
	 * @param broadCasts 所有广播(key -> String)覆盖的地区(value -> HashSet<String>)的Map集合
	 * @param allAreas 广播能够覆盖的所有地区集合
	 * @param tempSet 辅助集合，用于临时存储
	 * @param areas 利用贪心算法计算之后所能够覆盖所有地区的广播集合
	 */
	private static void greedyAlgorithm(Map<String, HashSet<String>> broadCasts, HashSet<String> allAreas, HashSet<String> tempSet, ArrayList<String> areas) {
		//每次循环时，该变量就存储当前循环中广播覆盖的地区与所有广播能够覆盖的地区的交集最多的key
		String maxKey = null;
		while (allAreas.size() > 0) {
			//每次遍历时，都需要将maxKey置空
			maxKey = null;
			for (String key : broadCasts.keySet()) {
				//每次遍历时，都需要将tempSet清空
				tempSet.clear();
				//当前这个(key广播)覆盖的地区集合
				HashSet<String> area = broadCasts.get(key);
				//将当前广播覆盖的地区集合添加到tempSet辅助变量中
				tempSet.addAll(area);
				//将当前广播覆盖的地区集合和广播能够覆盖的所有地区集合进行交集，然后再将交集赋值给tempSet
				tempSet.retainAll(allAreas);
				//记录当前覆盖地区最多的广播当中覆盖地区的个数：maxSize
				int maxSize = 0;
				//判断maxKey是否为空，防止出现空指针异常
				if(maxKey != null) {
					maxSize = broadCasts.get(maxKey).size();
				}
				//tempSet.size() > broadCasts.get(maxKey).size() 体现出贪心算法的特点，每次都选择最优的
				//表示如果当前广播覆盖的地区数量比maxKey对应的广播覆盖地区数量还要多 时，就需要重置maxKey
				if (tempSet.size() > 0 && (maxKey == null || tempSet.size() > maxSize)) {
					maxKey = key;
				}
			}

			//maxKey不为空时
			if (maxKey != null) {
				//就将maxKey添加到areas中
				areas.add(maxKey);
				//将maxKey广播覆盖的地区从广播能够覆盖的所有地区集合中移除
				allAreas.removeAll(broadCasts.get(maxKey));
			}
		}
		System.out.println(areas.toString());
	}
}
