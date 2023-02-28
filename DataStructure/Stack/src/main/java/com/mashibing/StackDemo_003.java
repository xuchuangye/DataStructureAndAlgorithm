package com.mashibing;

import com.mashibing.stack.ArrayStack;

/**
 * 栈的应用实例 --> 综合多位数计算器（不包含小括号以及小数运算）
 * @author xcy
 * @date 2022/3/16 - 16:10
 */
public class StackDemo_003 {
	public static void main(String[] args) {
		//数值栈
		ArrayStack numberStack = new ArrayStack(10);
		//运算符栈
		ArrayStack operatorStack = new ArrayStack(10);

		//表达式
		String expression = "30+70*50-80/20+100";


		int num1 = 0;
		int num2 = 0;
		//运算符
		int operator = 0;
		//表达式的每一个字符的索引
		int index = 0;
		//表达式最终的计算结果
		int result = 0;
		char ch = ' ';
		//拼接多位数的字符串
		StringBuilder stringNumber = new StringBuilder("");
		while (true) {
			//依次循环遍历，截取得到表达式的每一个字符
			ch = expression.substring(index, index + 1).charAt(0);

			//判断是否是运算符
			if (operatorStack.isOperator(ch)) {
				//如果当前的符号栈是否为空
				if (!operatorStack.isStackNull()) {
					//如果是运算符，并且栈中已经有运算符，就进行比较，继续比较运算符的优先级
					//如果当前运算符的优先级小于或者等于 运算符栈中栈顶顶运算符的优先级，比如 +- <= */
					if (operatorStack.operatorPriority(ch) <= operatorStack.operatorPriority(operatorStack.peek())) {
						//先取出数值栈中的第一个元素
						num1 = numberStack.pop();
						//然后取出数值栈中的第二个元素
						num2 = numberStack.pop();
						//接着从运算符栈中取出栈顶的运算符
						operator = operatorStack.pop();
						//进行计算
						result = numberStack.OperatorResult(num1, num2, operator);
						//1、将计算结果入栈到数值栈中
						numberStack.push(result);
						//2、将当前的运算符入栈到运算符栈中
						operatorStack.push(ch);
					} else {
						//如果当前运算符的优先级大于 运算符栈中栈顶运算符的优先级
						//那么将当前运算符直接入栈到运算符栈中即可
						operatorStack.push(ch);
					}
				} else {
					//如果符号栈为空，那么运算符直接入栈
					operatorStack.push(ch);
				}
			} else {
				//如果是单位数的数值，那么直接入栈
				//numberStack.push(ch - 48);//‘1’的ASCII码值是49，后面的值依次类推，所以需要减去48

				//第一位数肯定是数值，先添加进字符型的number中
				stringNumber.append(ch);
				//判断ch是否是最后一位
				if (index == expression.length() - 1) {
					numberStack.push(Integer.parseInt(new String(stringNumber)));
				} else {
					//如果index位置后面还有数值，那么就需要进行判断
					if (operatorStack.isOperator(expression.substring(index + 1, index + 2).charAt(0))) {
						numberStack.push(Integer.parseInt(new String(stringNumber)));
						stringNumber = new StringBuilder("");
					}
				}
			}
			//让index+1，并且判断是否扫描到表达式expression的最后
			index++;
			if (index == expression.length()) {
				break;
			}
		}

		//将numberStack和operatorStack的元素逆序
		// TODO 需要将numberStack和operatorStack的元素逆序


		while (true) {
			if (operatorStack.isStackNull()) {
				break;
			}

			//先取出数值栈中的第一个元素
			num1 = numberStack.pop();
			//然后取出数值栈中的第二个元素
			num2 = numberStack.pop();
			//接着从运算符栈中取出栈顶的运算符
			operator = operatorStack.pop();
			//进行计算
			result = numberStack.OperatorResult(num1, num2, operator);
			numberStack.push(result);
		}

		//将数值栈中的最后计算结果取出，因为此时数值栈中只有一个栈顶元素
		int res = numberStack.pop();
		System.out.printf("表达式 %s 的计算结果是：%d", expression, res);
	}
}
