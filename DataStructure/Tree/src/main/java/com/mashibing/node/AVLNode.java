package com.mashibing.node;

/**
 * 平衡二叉树节点
 *
 * @author xcy
 * @date 2022/3/29 - 17:03
 */
public class AVLNode {
	public int value;
	public AVLNode left;
	public AVLNode right;

	public AVLNode(int value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "AVLNode{" +
				"value=" + value +
				'}';
	}

	/**
	 * 添加节点
	 *
	 * @param node 节点
	 */
	public void add(AVLNode node) {
		if (node == null) {
			return;
		}

		//因为按照二叉排序树的规则，当添加的节点的权值小于当前子树的根节点的权值，那么统统指向当前子树根节点的左子节点
		if (node.value < this.value) {
			//判断当前子树根节点的左子节点是否为空
			if (this.left == null) {
				//true，那么添加的节点直接作为当前子树根节点的左子节点
				this.left = node;
			} else {
				//false，那么就继续往当前子树根节点的左子节点下递归进行比较
				this.left.add(node);
			}
		} else {
			//当添加的节点的权值大于当前子树的根节点的权值，那么统统指向当前子树根节点的右子节点
			//判断当前子树根节点的右子节点是否为空
			if (this.right == null) {
				//true，那么添加的节点直接作为当前子树根节点的右子节点
				this.right = node;
			} else {
				//false，那么就继续往当前子树根节点的右子节点下递归进行比较
				this.right.add(node);
			}
		}
		//右子树的深度大于左子树，并且差值大于1，需要进行左旋转
		if (this.rightHeight() - this.leftHeight() > 1) {
			//判断当前节点的右子树中左子树的深度大于右子树的深度
			if (this.right.leftHeight() > this.right.rightHeight()) {
				//那么当前节点的右子树就进行右旋转
				this.right.rightRevolve();
				//再对当前节点进行左旋转
				this.leftRevolve();
			}else {
				//直接进行左旋转
				this.leftRevolve();
			}
			return;
		}

		//左子树的深度大于右子树，并且差值大于1，需要进行右旋转
		if (this.leftHeight() - this.rightHeight() > 1) {
			//判断当前节点的左子树中右子树的深度大于左子树的深度，
			if (this.left.rightHeight() > this.left.leftHeight()) {
				//那么当前节点的左子树就进行左旋转
				this.left.leftRevolve();
				//再对当前节点进行右旋转
				this.rightRevolve();
			}else {
				//直接进行右旋转
				this.rightRevolve();
			}
			return;
		}
	}

	/**
	 * 左旋转
	 */
	public void leftRevolve() {
		//1、创建新的节点，值为根节点的值
		AVLNode newNode = new AVLNode(this.value);
		//2、将新节点的左子树指向当前节点的左子树
		newNode.left = this.left;
		//3、将新节点的右子树指向当前节点的右子树的左子树
		newNode.right = this.right.left;
		//4、将当前节点的值替换为右子节点的值
		this.value = this.right.value;
		//5、将当前节点的右子树指向当前节点的右子树的右子树
		this.right = this.right.right;
		//6、将当前节点的左子树指向新的节点
		this.left = newNode;
	}

	/**
	 * 右旋转
	 */
	public void rightRevolve() {
		//1、创建新的节点，节点的值为根节点的值
		AVLNode newNode = new AVLNode(this.value);
		//2、将新节点的右子树指向当前节点的右子树
		newNode.right = this.right;
		//3、将新节点的左子树指向当前节点的左子树的右子树
		newNode.left = this.left.right;
		//4、将当前节点的值替换为左子节点的值
		this.value = this.left.value;
		//5、将当前节点的左子树指向当前节点的左子树的左子树
		this.left = this.left.left;
		//6、将当前节点的右子树指向新的节点
		this.right = newNode;
	}

	/**
	 * 返回左子树的深度
	 *
	 * @return 左子树的深度
	 */
	public int leftHeight() {
		if (left == null) {
			return 0;
		}
		return left.height();
	}

	/**
	 * 返回右子树的深度
	 *
	 * @return 右子树的深度
	 */
	public int rightHeight() {
		if (right == null) {
			return 0;
		}
		return right.height();
	}

	/**
	 * 返回树的深度
	 *
	 * @return 树的深度
	 */
	public int height() {
		//判断左子树和右子树谁的深度更深，最深的叶子节点本身也算一层，所以需要加1
		return Math.max(left == null ? 0 : left.height(), right == null ? 0 : right.height()) + 1;
	}

	/**
	 * 查找当前节点
	 *
	 * @param value 根据值查找对应的节点
	 * @return 返回值是value的节点
	 */
	public AVLNode search(int value) {
		if (value == this.value) {
			return this;
		} else if (value < this.value) {
			if (this.left == null) {
				return null;
			}
			return this.left.search(value);
		} else {
			if (this.right == null) {
				return null;
			}
			return this.right.search(value);
		}
	}

	/**
	 * 查找当前节点的父节点
	 *
	 * @param value 根据值查找对应节点的父节点
	 * @return 当前节点的父节点
	 */
	public AVLNode searchParent(int value) {
		//如果当前节点的左子节点不为空并且当前节点的左子节点的值等于查找的节点的值，
		//或者当前节点的右子节点不为空并且当前节点的右子节点的值等于查找的节点的值。
		//那么当前节点就是要查找的节点的父节点
		if ((this.left != null && value == this.left.value)
				|| (this.right != null && value == this.right.value)) {
			return this;
		} else {
			//如果当前节点的左子节点不为空并且当前节点的值比要查找的节点的值大
			if (this.left != null && value < this.value) {
				//那么就递归当前节点的左子节点
				return this.left.searchParent(value);
			}
			//如果当前节点的右子节点不为空并且当前节点的值比要查找的节点的值小
			else if (this.right != null && value > this.value) {
				//那么就递归当前节点的右子节点
				return this.right.searchParent(value);
			} else {
				//如果都不满足，证明当前节点没有父节点，直接返回null
				return null;
			}
		}
	}

	/**
	 * 中序遍历
	 */
	public void infixOrder() {
		if (this.left != null) {
			this.left.infixOrder();
		}
		System.out.println(this);
		if (this.right != null) {
			this.right.infixOrder();
		}
	}
}
