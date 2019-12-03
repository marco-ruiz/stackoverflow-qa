package so.a59083054;

public class Interest {

	public static void main(String[] args) {
		System.out.println(computeMaturity(10000, 0.048, 5));
		System.out.println(computeMaturity(100000, 0.0875, 3));
	}
	
	private static double computeMaturity(double principle, double monthlyRate, double months) {
		return principle * Math.pow(1 + monthlyRate, months);
	}
}

