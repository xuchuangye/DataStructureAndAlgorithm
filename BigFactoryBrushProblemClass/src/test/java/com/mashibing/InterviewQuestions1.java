package com.mashibing;

/**
 * @author xcy
 * @date 2022/8/1 - 8:00
 */
public class InterviewQuestions1 {
	public static void main(String[] args) {

	}

	public static int findKthNumber(int[] arr1, int[] arr2, int kth) {
		/*int[] longs = arr1.length >= arr2.length ? arr1 : arr2;
		int[] shorts = arr1.length < arr2.length ? arr1 : arr2;
		int l = longs.length;
		int s = shorts.length;
		if (kth <= s) {
			return process(longs, 0, kth - 1, shorts, 0, kth - 1);
		}
		//l < kth <= (l + s)
		//kth == 23
		//longs.length -> l == 17
		//longs[] = 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17
		//index =                                          12
		//shorts.length -? s == 10
		//shorts[] =1',2',3',4',5',6',7',8',9',10'
		//index =                  5
		if (kth > l && kth <= (l + s)) {
			if (longs[kth - s - 1] >= shorts[s - 1]) {
				return longs[kth - s - 1];
			}
			if (shorts[kth - l - 1] >= longs[l - 1]) {
				return shorts[kth - l - 1];
			}
			return process(longs, kth - s, l - 1, shorts, kth - l, s - 1);
		}
		//s < kth <= l
		//kth == 15
		//longs[] = 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17
		//index =               4
		//shorts[] =1',2',3',4',5',6',7',8',9',10'
		if (longs[kth - s - 1] >= shorts[s - 1]) {
			return longs[kth - s - 1];
		}
		return process(longs, kth - s, l - 1, shorts, 0, s - 1);*/
		int[] longs = arr1.length >= arr2.length ? arr1 : arr2;
		int[] shorts = arr1.length < arr2.length ? arr1 : arr2;
		int l = longs.length;
		int s = shorts.length;

		if (kth <= s) {
			return process(longs, 0, kth - 1, shorts, 0, kth - 1);
		}

		if (kth > l) {
			if (longs[kth - s - 1] >= shorts[s - 1]) {
				return longs[kth - s - 1];
			}
			if (shorts[kth - l - 1] >= longs[l - 1]) {
				return shorts[kth - l - 1];
			}
			return process(longs, kth - s, l - 1, shorts, kth - l, s - 1);
		}

		if (longs[kth - s - 1] >= shorts[s - 1]) {
			return longs[kth - s - 1];
		}
		return process(longs, kth - s, l - 1, shorts, 0, s - 1);
	}

	/**
	 * 两个长度一样的数组
	 *
	 * @param A
	 * @param s1
	 * @param e1
	 * @param B
	 * @param s2
	 * @param e2
	 * @return 返回两个长度一样的数组合并之后第
	 */
	public static int process(int[] A, int s1, int e1, int[] B, int s2, int e2) {
		/*int mid1 = 0;
		int mid2 = 0;
		while (s1 < e1) {
			mid1 = s1 + ((e1 - s1) >> 1);
			mid2 = s2 + ((e2 - s2) >> 1);
			if (A[mid1] == B[mid2]) {
				return A[mid1];
			}
			//A[] = 1, 2, 3, 4, 5
			//s1                     e1
			//B[] = 1',2',3',4',5'
			//s2                     e2
			if (((e1 - s1 + 1) & 1) == 1) {
				if (A[mid1] > B[mid2]) {
					if (B[mid2] >= A[mid1 - 1]) {
						return B[mid2];
					}
					e1 = mid1 - 1;
					s2 = mid2 + 1;
				}
				//A[mid1] < B[mid2]
				else {
					if (A[mid1] >= B[mid2 - 1]) {
						return A[mid1];
					}
					s1 = mid1 + 1;
					e2 = mid2 - 1;
				}
			}
			//A[] = 1, 2, 3, 4
			//s1                  e1
			//B[] = 1',2',3',4'
			//s2                  e2
			else {
				if (A[mid1] > B[mid2]) {
					e1 = mid1;
					s2 = mid2 + 1;
				} else {
					s1 = mid1 + 1;
					e2 = mid2;
				}
			}
		}
		return Math.min(A[s1], B[s2]);*/
		int mid1 = 0;
		int mid2 = 0;
		while (s1 < e1) {
			mid1 = s1 + ((e1 - s1) >> 1);
			mid2 = s2 + ((e2 - s2) >> 1);

			if (A[mid1] == B[mid2]) {
				return A[mid1];
			}

			if (((e1 - s1 + 1) & 1) == 1) {
				if (A[mid1] > B[mid2]) {
					if (B[mid2] >= A[mid1 - 1]) {
						return B[mid2];
					}
					s1 = mid1 + 1;
					e2 = mid2 - 1;
				} else {
					if (A[mid1] >= B[mid2 - 1]) {
						return A[mid1];
					}
					e1 = mid1 - 1;
					s2 = mid2 + 1;
				}
			} else {
				if (A[mid1] > B[mid2]) {
					e1 = mid1;
					s2 = mid2 + 1;
				} else {
					s1 = mid1 + 1;
					e2 = mid2;
				}
			}
		}
		return Math.min(A[s1], B[s2]);
	}
}
