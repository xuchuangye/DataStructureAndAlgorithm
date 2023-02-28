package com.mashibing.bean;

/**
 * @author xcy
 * @date 2021/9/19 - 20:04
 */
public class Person {
	private String name;
	private String age;

	public Person() {

	}

	public Person(String name) {
		this();
		this.name = name;
	}

	public Person(String name, String age) {
		this(age);
		this.age = age;
	}
}
