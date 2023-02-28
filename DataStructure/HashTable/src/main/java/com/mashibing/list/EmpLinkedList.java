package com.mashibing.list;

import com.mashibing.pojo.Emp;

/**
 * 雇员的链表
 * @author xcy
 * @date 2022/3/24 - 8:55
 */
public class EmpLinkedList {
	private Emp head;

	/**
	 * 在链表中添加雇员信息
	 *
	 * @param emp
	 */
	public void add(Emp emp) {
		//添加第一个雇员
		if (head == null) {
			head = emp;
			return;
		}
		//如果不是第一个雇员
		Emp curEmp = head;
		while (true) {
			if (curEmp.next == null) {
				break;
			}
			curEmp = curEmp.next;
		}
		curEmp.next = emp;
	}

	/**
	 * 遍历链表中的所有的雇员信息
	 */
	public void list(int id) {
		//判断链表是否为空
		if (head == null) {
			//打印提示信息
			System.out.println("当前第" + (id + 1) + "个链表为空");
			return;
		}
		//因为头节点不能移动，所以需要辅助节点
		Emp curEmp = head;

		while (true) {
			//已经判断链表是否为空了，此时肯定不为空，可以打印输出
			System.out.printf("当前第" + (id + 1) + "个链表中的雇员：id => %d name => %s \t", curEmp.getId(), curEmp.getName());
			//如果curEmp是尾部节点，证明已经循环遍历完了，需要退出循环
			if (curEmp.next == null) {
				break;
			}
			//如果curEmp不是尾部节点，证明整个循环没有遍历完，需要继续指向下一个节点
			curEmp = curEmp.next;
		}
		System.out.println();
	}

	/**
	 * 根据雇员编号查找雇员信息
	 *
	 * @param id 雇员编号
	 * @return
	 */
	public Emp findEmpById(int id) {
		//判断链表是否为空
		if (head == null) {
			System.out.println("链表为空");
			return null;
		}
		//头节点不能移动，所以需要辅助节点
		Emp curEmp = head;
		while (true) {
			//如果找到对应的雇员编号，那么就退出循环
			if (curEmp.getId() == id) {
				break;
			}
			//如果curEmp是尾部节点，证明已经循环遍历完了，也需要退出循环
			if (curEmp.next == null) {
				curEmp = null;
				break;
			}
			//如果curEmp不是尾部节点，证明整个循环没有遍历完，需要继续指向下一个节点
			curEmp = curEmp.next;
		}
		//当退出循环时，证明已经找到对应的雇员id，返回该雇员即可
		return curEmp;
	}

	/**
	 * 根据雇员编号删除雇员信息
	 *
	 * @param id
	 */
	public void deleteEmpById(int id) {
		//判断链表是否为空
		if (head == null) {
			System.out.println("链表为空");
			return;
		}
		//因为头节点不能移动，所以需要辅助节点
		Emp curEmp = head;
		//指定删除的雇员编号是否存在，默认不存在
		boolean isExist = false;
		while (true) {
			//如果找到对应的雇员编号，那么就做个标记，表示删除的雇员编号存在
			if (curEmp.getId() == id) {
				//如果找到对应的雇员编号，就将当前雇员信息置空
				isExist = true;
				break;
			}
			//如果curEmp是尾部节点，证明已经循环遍历完了，也需要退出循环
			if (curEmp.next == null) {
				break;
			}
			//如果curEmp不是尾部节点，证明整个循环没有遍历完，需要继续指向下一个节点
			curEmp = curEmp.next;
		}

		if (isExist) {
			curEmp.next = curEmp.next.next;
			System.out.println("删除雇员信息成功");
		} else {
			System.out.println("没有找到该雇员，无法删除");
		}
	}

	/**
	 * 根据雇员编号修改雇员信息
	 * @param emp
	 */
	public void updateEmpById(Emp emp) {
		if (head == null) {
			System.out.println("链表为空");
			return;
		}

		//因为头节点不能移动，所以需要辅助节点
		Emp curEmp = head;
		//
		boolean isExist = false;

		while (true) {
			if (curEmp.getId() == emp.getId()) {
				isExist = true;
				break;
			}
			//如果curEmp是尾部节点，证明已经循环遍历完了，也需要退出循环
			if (curEmp.next == null) {
				break;
			}
			//如果curEmp不是尾部节点，证明整个循环没有遍历完，需要继续指向下一个节点
			curEmp = curEmp.next;
		}

		if (isExist) {
			curEmp.setName(emp.getName());
		}else {
			System.out.printf("没有找到编号为%d的雇员，无法修改\n", curEmp.getId());
		}
	}
}
