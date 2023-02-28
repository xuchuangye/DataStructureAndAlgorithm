### Master公式
Master公式用来分析递归的时间复杂度

### 什么时候适合使用Master公式估算递归时间复杂度
递归中子问题的规模是一致的时候适合使用Master公式估算时间复杂度

### Master公式的计算方式
T(N) = a * T(N / b) + O (N ^ d次方) (a,b,d都是常数)

1、 log b为底数a为真数 < d ，时间复杂度为O(N的d次方)
2、 log b为底数a为真数 > d ，时间复杂度为O(N的log b为底数a为真数的次方)
3、 log b为底数a为真数 = d ，时间复杂度为O(N的d次方 * log N)

