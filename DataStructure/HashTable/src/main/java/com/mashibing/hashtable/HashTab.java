package com.mashibing.hashtable;

import com.mashibing.list.EmpLinkedList;
import com.mashibing.pojo.Emp;

/**
 * 雇员链表所在的哈希表
 * @author xcy
 * @date 2022/3/24 - 9:02
 */
public class HashTab {
	private EmpLinkedList[] empLinkedLists;

	private int size;

	public HashTab(int size) {
		this.size = size;
		empLinkedLists = new EmpLinkedList[size];
		// TODO 此处有坑
		for (int i = 0; i < size; i++) {
			empLinkedLists[i] = new EmpLinkedList();
		}
	}

	/**
	 * 取模函数
	 * @param id
	 * @return
	 */
	public int hashFunction(int id) {
		return id % size;
	}

	public void add(Emp emp) {
		//根据雇员id，并且进行取模运算，将雇员添加到链表中
		int empLinkedListsNo = hashFunction(emp.getId());
		empLinkedLists[empLinkedListsNo].add(emp);
	}

	/**
	 * 遍历所有链表
	 */
	public void list() {
		for (int i = 0; i < size; i++) {
			empLinkedLists[i].list(i);
		}
	}

	/**
	 * 根据雇员编号，查找雇员信息
	 * @param id 雇员编号
	 */
	public void findEmp(int id) {
		//通过哈希函数得到雇员所在的链表
		int empLinkedListNo = hashFunction(id);
		Emp emp = empLinkedLists[empLinkedListNo].findEmpById(id);
		if (emp != null) {
			System.out.println("当前第" + (empLinkedListNo + 1) +"个链表查找到雇员，雇员的id是：" + emp.getId());
		}else {
			System.out.println("在哈希表中，没有找到该雇员");
		}
	}

	/**
	 * 根据雇员编号，删除雇员信息
	 * @param id
	 */
	public void deleteEmp(int id) {
		//通过哈希函数得到雇员所在的链表
		int empLinkedListNo = hashFunction(id);
		empLinkedLists[empLinkedListNo].deleteEmpById(id);
	}

	public void updateEmp(Emp emp) {
		//通过哈希函数得到雇员所在的链表
		int empLinkedListNo = hashFunction(emp.getId());
		empLinkedLists[empLinkedListNo].updateEmpById(emp);
	}
}
