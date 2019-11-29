package so.a59081207;

import java.util.Arrays;
import java.util.stream.Collectors;

public class BaseNegation {
	
	public static void main(String[] args) {
		out(baseNegate(10, new int[] {5, 6, 3}));
		out(baseNegate(2, new int[] {1, 1, 0, 0, 1, 1, 0, 1, 0, 1}));
		out(baseNegate(16, new int[] {0, 15, 10, 7}));
	}

	private static void out(int[] result) {
		System.out.println(Arrays.stream(result).mapToObj(Integer::new).collect(Collectors.toList()));
	}
	
	public static int[] baseNegate(int base, int[] inDigits) {
		int[] result = new int[inDigits.length];
		baseNegate(base, inDigits, result);
		return result;
	}
	
	public static void baseNegate(int base, int[] inDigits, int[] outDigits) {
		// Compute the complement of the digits
		for (int i = outDigits.length - 1; i >= 0; i--)  
			outDigits[i] = base - (1 + inDigits[i]);

		// Negate the complement by adding +1 to the computed number (collection of digits)
		for (int i = 0; i < outDigits.length; i++) {  
			if (outDigits[i] == base - 1) {
				// Max out digit. Set it to zero and try with the higher order next. 
				outDigits[i] = 0;
			} else {
				// Digit that has room for +1. Finally add the 1 and DONE!
				outDigits[i]++;
				break;
			}
		}
	}
}
