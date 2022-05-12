import java.util.Hashtable;
import java.util.Set;

public class BoyerMoore
{
	/**
	 * The lastOccurance function
	 * @param S
	 * @return
	 */
	public static Hashtable<Character, Integer> lastOccurrenceFunction(String S)
	{
		Hashtable<Character, Integer> chars = new Hashtable<Character, Integer>();
		for (int i = 0; i < S.length(); i++) {
			char curr = S.charAt(S.length() - i - 1);
			if (chars.get(curr) == null) {
				chars.put(curr, S.length() - i - 1);
			}
		}
		// System.out.println(chars);
		return chars;
	}

	/**
	 * Run the Boyer Moore Pattern Matching
	 * @param <></>
	 * @param P
	 * @return
	 */
	public static int find(String T, String P)
	{
		int n = T.length();
		int m = P.length();

		Hashtable<Character, Integer> L = lastOccurrenceFunction(P);

		int i = m - 1;
		int j = m - 1;
		// System.out.println(i);

		do {
			if (T.charAt(i) == P.charAt(j)) {
				if (j == 0) {
					// System.out.println("returning " + i + "for BM function");
					return i;
				}
				else {
					i = i - 1;
					j = j - 1;
				}
			} else {
				int l = -1;
				if (L.get(T.charAt(i)) != null) {
					l = L.get(T.charAt(i));
				}
				i = i + m - Math.min(j, 1 + l);
				j = m - 1;
			}
		} while (i <= n - 1);
		// System.out.println("returning " + -1 + "for BM function");
		return -1;

	}



}
