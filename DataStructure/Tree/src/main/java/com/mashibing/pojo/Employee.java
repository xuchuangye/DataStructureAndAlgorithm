package com.mashibing.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * 员工类
 * @author xcy
 * @date 2022/5/1 - 10:29
 */
public class Employee {
	/**
	 * 员工来聚会的快乐值
	 */
	public int happy;
	/**
	 * 员工的直接下属
	 */
	public List<Employee> nexts;
	public Employee(int happy) {
		this.happy = happy;
		this.nexts = new ArrayList<>();
	}
}
