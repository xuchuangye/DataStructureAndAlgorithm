package com.mashibing;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 中缀表达式转换为后缀表达式
 *
 * @author xcy
 * @date 2022/3/17 - 16:15
 */
public class SuffixExpression {
	public static void main(String[] args) {
		String infixExpression = "10+((20+30)*42)-59";

		//中缀表达式
		List<String> infixExpressionList = getInfixExpressionList(infixExpression);
		System.out.println("中缀表达式对应的List：" + infixExpressionList);

		List<String> parseSuffixExpressionList = parseSuffixExpressionList(infixExpressionList);
		System.out.println("后缀表达式对应的List：" + parseSuffixExpressionList);

		int result = suffixExpressionResult(parseSuffixExpressionList);
		System.out.printf("中缀表达式%s的计算结果是%d", infixExpression, result);
	}

	/**
	 * 解析中缀表达式对应的List转换为后缀表达式对应的List
	 * @param list 中缀表达式对应的List
	 * @return 后缀表达式对应的List
	 */
	public static List<String> parseSuffixExpressionList(List<String> list) {
		//运算符栈
		Stack<String> stack = new Stack<>();
		//存储结果的栈
		List<String> arrayList = new ArrayList<>();

		for (String item : list) {
			//如果是数值，直接加入list中
			if (item.matches("\\d+")) {
				arrayList.add(item);
			}
			//如果是左括号，直接压入stack中
			else if ("(".equals(item)) {
				stack.push(item);
			}
			//如果是右括号
			else if (")".equals(item)) {
				//依次弹出stack中栈顶的运算符加入到ArrayList中，直到栈顶运算符是左括号为止，此时将这一对括号丢弃
				while (!stack.peek().equals("(")) {
					arrayList.add(stack.pop());
				}
				//将stack中的左括号丢弃
				stack.pop();
			}
			//stack不为空，并且当前的运算符的优先级小于等于栈顶的运算符的优先级
			else {
				while (stack.size() != 0 && Operation.getValue(stack.peek()) >= Operation.getValue(item)) {
					arrayList.add(stack.pop());
				}

				//还需要将当前的运算符压入到stack栈中
				stack.push(item);
			}
		}

		//最终将stack中剩余的运算符依次加入到ArrayList中
		while (stack.size() != 0) {
			arrayList.add(stack.pop());
		}

		return arrayList;
	}

	/**
	 * 将中缀表达式转换成中缀表达式对应的由String组成的List
	 * @param infix 中缀表达式
	 * @return
	 */
	public static List<String> getInfixExpressionList(String infix) {
		List<String> list = new ArrayList<>();
		//拼接多位数
		String str = "";
		int index = 0;
		char ch = ' ';
		do {
			//不是'0'~'9'
			if ((ch = infix.charAt(index)) < 48 || (ch = infix.charAt(index)) > 57) {
				list.add("" + ch);
				index++;
			} else {
				str = "";
				while (index < infix.length() && ((ch = infix.charAt(index)) >= 48) && ((ch = infix.charAt(index)) <= 57)) {
					str += ch;
					index++;
				}
				list.add(str);
			}
		} while (index < infix.length());

		return list;
	}

	/**
	 * 1) 从左至右扫描，将3和4压入堆栈；
	 * 2) 遇到+运算符，因此弹出4和3（4为栈顶元素，3为次顶元素），计算出3+4的值，得7，再将7入栈；
	 * 3) 将5入栈；
	 * 4) 接下来是×运算符，因此弹出5和7，计算出7×5=35，将35入栈；
	 * 5) 将6入栈；
	 * 6) 最后是-运算符，计算出35-6的值，即29，由此得出最终结果
	 *
	 * @param list
	 * @return
	 */
	public static int suffixExpressionResult(List<String> list) {
		Stack<String> stack = new Stack<>();
		for (String item : list) {
			//使用正则表达式进行匹配，匹配多位数
			if (item.matches("\\d+")) {
				stack.push(item);
			} else {
				//取出栈顶元素
				int num2 = Integer.parseInt(stack.pop());
				//取出次顶元素
				int num1 = Integer.parseInt(stack.pop());
				int result = 0;
				if ("+".equals(item)) {
					result = num1 + num2;
				} else if ("-".equals(item)) {
					result = num1 - num2;
				} else if ("*".equals(item)) {
					result = num1 * num2;
				} else if ("/".equals(item)) {
					result = num1 / num2;
				} else {
					throw new RuntimeException("运算符有误");
				}
				//计算结果入栈
				stack.push(String.valueOf(result));
			}
		}
		//最终栈中只剩一个元素，也就是计算的结果
		return Integer.parseInt(stack.pop());
	}
}

/**
 * 运算符的优先级
 */
class Operation {
	private static final int ADD = 1;
	private static final int SUB = 1;
	private static final int MUL = 2;
	private static final int DIV = 2;

	public static int getValue(String operator) {
		int result = 0;
		switch (operator) {
			case "+":
				result = ADD;
				break;
			case "-":
				result = SUB;
				break;
			case "*":
				result = MUL;
				break;
			case "/":
				result = DIV;
				break;
			default:
				System.out.println("运算符不正确");
				break;
		}
		return result;
	}
}