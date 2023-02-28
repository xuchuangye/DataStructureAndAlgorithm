package com.mashibing.map;

import com.mashibing.pojo.Person;

import java.util.Comparator;
import java.util.TreeMap;

/**
 * TreeMap有序表
 * @author xcy
 * @date 2022/4/8 - 15:50
 */
public class TreeMapDemo {
	public static void main(String[] args) {
		//TreeMap的key对于包装类以及String类型
		TreeMap<String, String> map = new TreeMap<>();
		String s1 = "xcy";
		String s2 = "xcy";
		map.put("xcy","xcy666");
		System.out.println(map.get(s1));
		System.out.println(map.get(s2));

		TreeMap<Integer, String> treeMap = new TreeMap<>();
		Integer a = 123456;
		Integer b = 123456;
		treeMap.put(123456, "666");
		System.out.println(treeMap.get(a));
		System.out.println(treeMap.get(b));

		//TreeMap的key不能是自定义引用类型，必须是可以比较的，也就是必须实现Comparable接口或者Comparator接口
		TreeMap<Person, String> personTreeMap = new TreeMap<>(new Comparator<Person>() {
			@Override
			public int compare(Person o1, Person o2) {
				return o1.getAge() - o2.getAge();
			}
		});
		Person  person1 = new Person("赵丽颖",20);
		Person  person2 = new Person("杨幂",25);
		personTreeMap.put(person1, "我的老婆");
		personTreeMap.put(person2, "我的女朋友");
		System.out.println(personTreeMap.get(person1));
		System.out.println(personTreeMap.get(person2));
	}
}
