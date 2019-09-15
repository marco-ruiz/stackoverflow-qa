package so.a56140651;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FinancialCalculator {
	
	static class MonthlyExpense {
		private String type;
		private double amount;

		public MonthlyExpense(String type, double amount) {
			this.type = type;
			this.amount = amount;
		}
		
		public String getType() { return type; }
		public double getAmount() { return amount; }
		public String toString() { return String.format("%s: %.2f", type, amount); }
	}
	
	private static List<MonthlyExpense> createMonthlyExpenses(List<MonthlyExpense> expenses) {
		Map<String, Double> sums = expenses.stream()
				.collect(Collectors.groupingBy(
							MonthlyExpense::getType, 
							Collectors.summingDouble(MonthlyExpense::getAmount))
		);
		
		return sums.entrySet().stream()
				.map(entry -> new MonthlyExpense(entry.getKey(), entry.getValue()))
//				.map(entry -> createMonthlyExpense(entry.getKey(), entry.getValue()))
				.sorted(Comparator.comparing(MonthlyExpense::getType))
				.collect(Collectors.toList());
	}
	
	public static void main(String[] args) {
		MonthlyExpense[] expenses = new MonthlyExpense[] {
				new MonthlyExpense("UTILITIES", 75),
				new MonthlyExpense("CREDIT", 1000),
				new MonthlyExpense("AUTO", 7500),
				new MonthlyExpense("UTILITIES", 50),
				new MonthlyExpense("CREDIT", 2000),
				new MonthlyExpense("UTILITIES", 150),
				new MonthlyExpense("CAR", 344)
		};  
		System.out.println(createMonthlyExpenses(Arrays.asList(expenses)));
	}
}
