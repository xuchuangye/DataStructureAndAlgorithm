package com.mashibing.day02;

import java.util.HashMap;
import java.util.Map;

/**
 * 题目五：
 * 设计有setAll功能的哈希表，put、get、setAll方法，时间复杂度O(1)
 *
 * @author xcy
 * @date 2022/7/11 - 9:10
 */
public class Code05_SetAll {
	public static void main(String[] args) {

	}

	/**
	 * 将数据的值和时间戳封装成类
	 * @param <V>
	 */
	public static class MyValue<V> {
		/**
		 * 数据的值
		 */
		public V value;
		/**
		 * 时间戳
		 */
		public long time;

		public MyValue(V v, long time) {
			this.value = v;
			this.time = time;
		}
	}

	public static class MyHashMap<K, V> {
		public HashMap<K, MyValue<V>> hashMap;

		/**
		 * setAll的值
		 */
		public MyValue<V> setAll;
		/**
		 * 时间戳
		 */
		public long time;

		public MyHashMap() {
			hashMap = new HashMap<>();
			setAll = new MyValue<>(null, -1);
			time = 0L;
		}

		/**
		 * 每当插入数据或者更新数据时，时间戳都会++
		 *
		 * @param key
		 * @param value
		 */
		public void put(K key, V value) {
			hashMap.put(key, new MyValue<>(value, time));
			time++;
		}

		public V get(K key) {
			if (!hashMap.containsKey(key)) {
				return null;
			} else {
				if (hashMap.get(key).time > setAll.time) {
					return hashMap.get(key).value;
				}else {
					return setAll.value;
				}
			}
		}

		/**
		 * 插入三条数据
		 * key : 1, value : 3, time = 0, 封装数据 -> 1,<3, 0>, time = 1
		 * key : 2, value : 9, time = 1, 封装数据 -> 2,<9, 1>, time = 2
		 * key : 17, value : 4, time = 2, 封装数据 -> 17,<4, 2>, time = 3
		 * 此时进行setAll()
		 * setAllTime = 3, setAll = 7, time = 4
		 * 插入一条数据
		 * key : 17, value : 5, time = 4, 更新数据 -> 17,<5, 4>, time = 5
		 * 此时进行get()
		 * 凡是比setAllTime小的time时间戳，全部的值都为setAll
		 */
		public void setAll(V value) {
			setAll = new MyValue<>(value, time);
			this.time++;
		}
	}
}
