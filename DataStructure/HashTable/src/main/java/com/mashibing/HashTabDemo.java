package com.mashibing;

import com.mashibing.hashtable.HashTab;
import com.mashibing.pojo.Emp;

import java.util.Scanner;

/**
 * @author xcy
 * @date 2022/3/24 - 9:33
 */
public class HashTabDemo {
	public static void main(String[] args) {
		HashTab hashTab = new HashTab(7);
		Scanner scanner = new Scanner(System.in);
		String key;

		while (true) {
			System.out.println("add: 添加雇员");
			System.out.println("list: 遍历雇员");
			System.out.println("find: 查找雇员");
			System.out.println("delete: 删除雇员");
			System.out.println("update: 修改雇员");
			System.out.println("exit: 退出");
			key = scanner.next();
			switch (key) {
				case "add":
					Emp emp = new Emp();
					System.out.println("请输入雇员编号：");
					int id = scanner.nextInt();
					System.out.println("请输入雇员名称：");
					String name = scanner.next();
					emp.setId(id);
					emp.setName(name);
					hashTab.add(emp);
					break;
				case "list":
					hashTab.list();
					break;
				case "find":
					System.out.println("请输入要查找的雇员编号：");
					id = scanner.nextInt();
					hashTab.findEmp(id);
					break;
				case "exit":
					scanner.close();
					System.exit(0);
					break;
				case "delete":
					System.out.println("请输入要删除的雇员编号：");
					id = scanner.nextInt();
					hashTab.deleteEmp(id);
					break;
				case "update":
					System.out.println("请输入需要修改的雇员编号：");
					id = scanner.nextInt();
					System.out.println("请输入需要修改的雇员名称");
					name = scanner.next();
					emp = new Emp(id, name);
					hashTab.updateEmp(emp);
					break;
				default:
					break;
			}
		}
	}
}
