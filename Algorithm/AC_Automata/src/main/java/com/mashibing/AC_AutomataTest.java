package com.mashibing;

import java.util.List;

/**
 * @author xcy
 * @date 2022/5/26 - 12:15
 */
public class AC_AutomataTest {
	public static void main(String[] args) {
		AC_Automata ac = new AC_Automata();
		ac.insert("dhe");
		ac.insert("he");
		ac.insert("abcdheks");
		// 设置fail指针
		ac.build();

		List<String> contains = ac.containWords("abcdhekskdjfafhasldkflskdjhwqaeruv");
		for (String word : contains) {
			System.out.println(word);
		}
	}
}
