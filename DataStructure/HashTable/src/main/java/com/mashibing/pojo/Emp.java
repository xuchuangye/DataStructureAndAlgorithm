package com.mashibing.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 雇员类
 * @author xcy
 * @date 2022/3/24 - 8:50
 */
@Data
@NoArgsConstructor
public class Emp {
	private Integer id;
	private String name;
	public Emp next;
	public Emp(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	@Override
	public String toString() {
		return "Emp{" +
				"id=" + id +
				", name='" + name + '\'' +
				'}';
	}
}
