package com.mashibing.tree;

import com.mashibing.node.SortNode;

/**
 * 二叉排序树/二叉搜索树
 * @author xcy
 * @date 2022/3/29 - 9:48
 */
public class BinarySearchTree {
	private SortNode root;

	/**
	 * 添加节点
	 * @param node 节点
	 */
	public void add(SortNode node) {
		//判断根节点是否为空
		if (root == null) {
			//true，当前添加的节点直接作为根节点
			root = node;
		}else {
			//false，使用根节点继续添加新的节点
			root.add(node);
		}
	}

	/**
	 * 中序遍历
	 */
	public void infixOrder() {
		if (root == null) {
			System.out.println("二叉排序树为空，无法进行遍历");
		}else {
			root.infixOrder();
		}
	}

	/**
	 * 查找要删除的节点
	 * @param value 要查找的节点的值
	 * @return 返回要删除的节点
	 */
	public SortNode search(int value) {
		if (this.root == null) {
			System.out.println("二叉排序树的节点为空，无法进行查找");
			return null;
		}else {
			return this.root.search(value);
		}
	}

	/**
	 * 查找要删除的节点的父节点
	 * @param value 要查找节点的值
	 * @return 返回要删除的节点的父节点
	 */
	public SortNode searchParent(int value) {
		if (this.root == null) {
			System.out.println("二叉排序树的节点为空，无法进行查找");
			return null;
		}else {
			return this.root.searchParent(value);
		}
	}

	/**
	 * 找到右子树的最小值的节点
	 * 1、返回以node为根节点的二叉排序树中最小值节点的值
	 * 2、删除以node为根节点的二叉排序树中最小值节点
	 * @param node 作为二叉排序树的根节点
	 * @return 返回以node为根节点的二叉排序树中最小值节点的值
	 */
	public int deleteRightTreeMin(SortNode node) {
		SortNode target = node;

		//在二叉排序树中，左子节点的值小于当前节点的值
		while (target.left != null) {
			target = target.left;
		}

		//退出循环后，找到最小值的节点并且删除最小值节点
		deleteNode(target.value);
		//返回最小值节点的值
		return target.value;
	}

	/**
	 * 找到左子树的最大值的节点
	 * 1、返回以node为根节点的二叉排序树中最大值节点的值
	 * 2、删除以node为根节点的二叉排序树中最大值节点
	 * @param node 作为二叉排序树的根节点
	 * @return 返回以node为根节点的二叉排序树中最大值节点的值
	 */
	public int deleteLeftTreeMax(SortNode node) {
		SortNode target = node;

		//在二叉排序树中，右子节点的值大于当前节点的值
		while (target.right != null) {
			target = target.right;
		}
		//退出循环后，找到最大值的节点删除最大值的节点
		deleteNode(target.value);
		//返回最大值节点的值
		return target.value;
	}

	/**
	 * 删除节点
	 *
	 * 注意事项：
	 * 当要删除的节点的父节点为空时，需要考虑要删除节点的父节点为空的情况
	 * @param value
	 */
	public void deleteNode(int value) {
		//获取要删除的节点
		SortNode targetNode = search(value);
		if (targetNode == null) {
			System.out.println("要删除的节点不存在，无法删除");
			return;
		}

		//如果要删除的节点是根节点
		//判断根节点的左子节点是否为空，并且判断根节点的右子节点是否为空
		if (root.left == null && root.right == null) {
			//如果都为空，证明该二叉排序树只有一个根节点，那么将 根节点置空即可
			root = null;
			return;
		}

		//根据上述的判断，证明现在的二叉排序树中至少有两个及两个以上的节点
		//那么获取要删除节点的父节点
		SortNode parent = searchParent(value);

		//如果要删除的是叶子节点
		if (targetNode.left == null && targetNode.right == null) {
			//判断targetNode是parent节点的左子节点还是右子节点
			if (parent.left != null && parent.left.value == targetNode.value) {
				parent.left = null;
			}else if (parent.right != null && parent.right.value == targetNode.value) {
				parent.right = null;
			}
		}
		//如果要删除的是两个子节点的节点
		else if (targetNode.left != null && targetNode.right != null) {
			//传递
			/*int maxValue = deleteLeftTreeMax(targetNode.left);
			targetNode.value = maxValue;*/

			int minValue = deleteRightTreeMin(targetNode.right);
			targetNode.value = minValue;
		}
		//如果要删除的是一个子节点的节点
		else {
			//如果targetNode 有左子结点
			if (targetNode.left != null) {
				//判断父节点是否为空
				if (parent != null) {
					//如果 targetNode 是 parent 的左子结点
					if (parent.left.value == targetNode.value) {
						parent.left = targetNode.left;
					}
					//如果 targetNode 是 parent 的右子结点
					else {
						parent.right = targetNode.left;
					}
				}else {
					//如果父节点为空，证明当前节点就是根节点root，root指向要删除节点的左子节点
					root = targetNode.left;
				}
			}


			//如果targetNode 有右子结点
			if (targetNode.right != null) {
				//判断父节点是否为空
				if (parent != null) {
					//如果 targetNode 是 parent 的左子结点
					if (parent.left.value == targetNode.value) {
						parent.left = targetNode.right;
					}
					//如果 targetNode 是 parent 的右子结点
					else {
						parent.right = targetNode.right;
					}
				}else {
					//如果父节点为空，证明当前节点就是根节点root，root指向要删除节点的右子节点
					root = targetNode.right;
				}
			}
		}
	}
}
