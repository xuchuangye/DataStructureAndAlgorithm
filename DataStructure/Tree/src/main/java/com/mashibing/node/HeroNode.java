package com.mashibing.node;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xcy
 * @date 2022/3/24 - 17:07
 */
@Data
@NoArgsConstructor
public class HeroNode {
	private Integer no;
	private String name;
	private HeroNode left;
	private HeroNode right;

	//leftType值为0时，表示指向的是左子树，1表示指向的是前驱节点
	//rightType值为0时，表示指向的是右子树，1表示指向的是后继节点
	private int leftType;
	private int rightType;

	public HeroNode(Integer no, String name) {
		this.no = no;
		this.name = name;
	}

	@Override
	public String toString() {
		return "HeroNode{" +
				"no=" + no +
				", name='" + name + '\'' +
				'}';
	}




	//前序遍历，中序遍历，后序遍历


	/**
	 * 前序遍历
	 */
	public void preOrder() {
		System.out.println(this);
		if (this.left != null) {
			this.left.preOrder();
		}
		if (this.right != null) {
			this.right.preOrder();
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

	/**
	 * 后序遍历
	 */
	public void postOrder() {
		if (this.left != null) {
			this.left.postOrder();
		}
		if (this.right != null) {
			this.right.postOrder();
		}
		System.out.println(this);
	}

	/**
	 * @param no 查找的编号
	 * @return 如果查找到返回HeroNode，如果没有查找到就返回null
	 */
	public HeroNode preOrderSearch(int no) {
		//判断当前节点的编号是否和查找的编号相等
		if (this.getNo() == no) {
			return this;
		}
		//如果不相等则遍历当前节点的左子节点是否为空，如果左子节点不为空，则递归前序查找
		//如果递归前序查找，找到了节点
		HeroNode resultNode = null;
		if (this.left != null) {
			resultNode = this.left.preOrderSearch(no);
		}
		//如果resultNode不为空，则证明已经查找到该节点，直接返回
		if (resultNode != null) {
			return resultNode;
		}

		//如果resultNode为空，则证明没有查找到，继续判断右子节点是否为空，如果不为空，则继续递归前序查找
		if (this.right != null) {
			resultNode = this.right.preOrderSearch(no);
		}
		//如果resultNode不为空，则证明已经查找到该节点，直接返回
		if (resultNode != null) {
			return resultNode;
		}
		//如果resultNode为空，则证明右子树页没有查找到，结束前序遍历，直接返回null
		return null;
	}

	/**
	 * 中序遍历查找
	 *
	 * @param no 查找的编号
	 * @return 如果查找到返回HeroNode，如果没有查找到就返回null
	 */
	public HeroNode infixOrderSearch(int no) {
		//
		HeroNode resultNode = null;
		//首先判断当前节点的左子节点是否为空，如果不为空，则继续递归中序遍历查找
		if (this.left != null) {
			resultNode = this.left.infixOrderSearch(no);
		}
		//如果resultNode节点不为空，则证明已经查找到该节点，直接返回该节点
		if (resultNode != null) {
			return resultNode;
		}
		//如果resultNode节点为空，则继续判断当前节点是否是查找的节点，如果是，则返回当前节点
		if (this.getNo() == no) {
			return this;
		}
		//如果当前节点不是查找的节点，则继续判断当前节点的右子节点是否为空，如果不为空，则继续递归中序遍历查找
		if (this.right != null) {
			resultNode = this.right.infixOrderSearch(no);
		}
		//如果resultNode节点不为空，则证明已经查找到该节点，直接返回该节点
		if (resultNode != null) {
			return resultNode;
		}
		//如果resultNode节点为空，则证明中序遍历结束仍然没有查找到，返回null
		return null;
	}

	/**
	 * 后序遍历查找
	 *
	 * @param no 查找的编号
	 * @return 如果查找到返回HeroNode，如果没有查找到就返回null
	 */
	public HeroNode postOrderSearch(int no) {
		//记录是否查找到节点
		HeroNode resultNode = null;
		//首先判断当前节点的左子节点是否为空，如果不为空，则继续递归后序遍历查找
		if (this.left != null) {
			resultNode = this.left.postOrderSearch(no);
		}

		//如果resultNode节点不为空，则证明已经查找到该节点，直接返回
		if (resultNode != null) {
			return resultNode;
		}

		//如果resultNode节点为空，则继续判断当前节点的右子节点是否为空，如果不为空，则继续递归后序遍历查找
		if (this.right != null) {
			resultNode = this.right.postOrderSearch(no);
		}
		//如果resultNode节点不为空，则证明已经查找到该节点，直接返回
		if (resultNode != null) {
			return resultNode;
		}

		//如果resultNode节点仍然为空，则判断当前节点是否是要查找的节点，如果是，直接返回该节点
		if (this.getNo() == no) {
			return this;
		}
		//如果不是，则证明后序遍历查找已经结束，仍然没有查找到该节点，返回resultNode
		return null;
	}


	/**
	 * 删除节点
	 * <p>
	 * 注意事项：
	 * 不能判断当前节点本身是否能够删除，应该判断当前节点的子节点是否能够删除
	 *
	 * @param no 要删除的节点编号
	 */
	public void deleteNode(int no) {
		//不能判断当前节点本身是否能够删除，应该判断当前节点的子节点是否能够删除
		//首先判断当前节点的左子节点是否为空，如果不为空，并且左子节点的编号就是要删除的编号
		if (this.left != null && this.left.getNo() == no) {
			//那么直接将当前节点的左子节点置空
			this.left = null;
			//并且返回
			return;
		}
		//接着判断当前节点的右子节点是否为空，如果不为空，并且右子节点的编号就是要删除的编号
		if (this.right != null && this.right.getNo() == no) {
			//那么直接将当前节点的右子节点置空
			this.right = null;
			//并且返回
			return;
		}

		//如果上述条件都不满足，则先递归左子树
		if (this.left != null) {
			this.left.deleteNode(no);
		}

		//如果左子树仍然没有找到要删除的编号，那么递归右子树
		if (this.right != null) {
			this.right.deleteNode(no);
		}
	}

	/**
	 * 删除节点
	 *
	 * 注意事项：
	 * 不能判断当前节点本身是否能够删除，应该判断当前节点的子节点是否能够删除
	 *
	 * @param no 要删除的节点编号
	 */
	public void removeNode(int no) {
		//不能判断当前节点本身是否能够删除，应该判断当前节点的子节点是否能够删除
		//首先判断当前节点的左子节点是否为空，如果不为空，并且左子节点的编号就是要删除的编号
		if (this.left != null && this.left.getNo() == no) {
			//如果当前节点的左子节点的左右子节点都为空
			if (this.left.left == null && this.left.right == null) {
				//那么直接将当前节点的左子节点置空
				this.left = null;
			}
			//如果当前节点的左子节点的左子节点不为空
			else if (this.left.left != null && this.left.right == null){
				//那么将当前节点的左子节点替换为当前节点的左子节点的左子节点
				this.left = this.left.left;
			}

			//如果当前节点的左子节点的右子节点不为空
			else if (this.left.left == null && this.left.right != null){
				//那么将当前节点的左子节点替换为当前节点的左子节点的右子节点
				this.left = this.left.right;
			}
			//如果当前节点的左子节点的左右子节点都不为空
			else {
				this.left = this.left.left;
			}
			//并且返回
			return;
		}
		//接着判断当前节点的右子节点是否为空，如果不为空，并且右子节点的编号就是要删除的编号
		if (this.right != null && this.right.getNo() == no) {
			//如果当前节点的右子节点的左右子节点都为空
			if (this.right.left == null && this.right.right == null) {
				//那么直接将当前节点的右子节点置空
				this.right = null;
			}
			//如果当前节点的右子节点的左子节点不为空
			else if (this.right.left != null && this.right.right == null){
				//那么将当前节点的右子节点替换为当前节点的右子节点的左子节点
				this.right = this.right.left;
			}

			//如果当前节点的右子节点的右子节点不为空
			else if (this.right.left == null && this.right.right != null){
				//那么将当前节点的右子节点替换为当前节点的右子节点的右子节点
				this.right = this.right.right;
			}
			//如果当前节点的右子节点的左子节点和右子节点都不为空时
			else {
				//那么将当前节点的右子节点替换为当前节点的右子节点的左子节点
				this.right = this.right.left;
			}
			//并且返回
			return;
		}

		//如果上述条件都不满足，则先递归左子树
		if (this.left != null) {
			this.left.removeNode(no);
		}

		//如果左子树仍然没有找到要删除的编号，那么递归右子树
		if (this.right != null) {
			this.right.removeNode(no);
		}
	}
}
