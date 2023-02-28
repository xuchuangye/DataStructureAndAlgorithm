### KMP算法

KMP 算法是 D.E.Knuth、J,H,Morris 和 V.R.Pratt 三位神人共同提出的，称之为 Knuth-Morria-Pratt 算法，简称 KMP 算法。
该算法相对于 Brute-Force（暴力）算法有比较大的改进，主要是消除了主串指针的回溯，从而使算法效率有了某种程度的提高。

#### KMP算法核心
1）如何理解next数组
2）如何利用next数组加速匹配过程，优化时的两个实质！（私货解释）
