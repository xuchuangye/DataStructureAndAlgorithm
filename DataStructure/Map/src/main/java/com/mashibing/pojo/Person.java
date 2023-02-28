package com.mashibing.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author xcy
 * @date 2022/4/8 - 15:53
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Person /*implements Comparable<Person>*/{
	private String name;
	private Integer age;

	/*@Override
	public int compareTo(Person o) {
		return this.age - o.getAge();
	}*/
}
