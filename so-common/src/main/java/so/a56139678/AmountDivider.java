package so.a56139678;

public class AmountDivider {
	private int totalMembers, luckies, unluckies;
	private double totalAmount, amountPerLucky, amountPerUnlucky;
	
	public AmountDivider(int numMembers, double amountOwed) {
		totalMembers = numMembers;
		totalAmount = amountOwed;

		double centsOwed = amountOwed * 100;
        int centsPerMember = (int)(centsOwed / totalMembers);
        int centsLeft = (int)centsOwed - centsPerMember * totalMembers;
        
        luckies = totalMembers - centsLeft;
        amountPerLucky = centsPerMember / 100.0;
        unluckies = centsLeft;
        amountPerUnlucky = (centsPerMember + 1) / 100.0;
	}

	public String toString() {
		String luckiesStr = String.format("%d lucky persons will pay %.2f", luckies, amountPerLucky);
		String unluckiesStr = String.format("%d unlucky persons will pay %.2f", unluckies, amountPerUnlucky);
		return String.format("For amount %f divided among %d: \n%s\n%s\n", 
								totalAmount, totalMembers, luckiesStr, unluckiesStr);
	}
	
	public static void main(String[] args) {
		System.out.println(new AmountDivider(3, 200));
		System.out.println(new AmountDivider(17, 365.99));
	}
}
