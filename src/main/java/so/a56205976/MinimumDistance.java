package so.a56205976;
import java.util.ArrayList;
import java.util.List;

public class MinimumDistance {
	
	public static void main(String[] args) {
		printMinimums(5, 50, 3, 42, 18, 16, 8, 30, 44);
	}

	public static void printMinimums(int... array) {
		List<Combination> combinations = new ArrayList<>();
		for (int i = 0; i < array.length; i++) 
			for (int j = i + 1; j < array.length; j++) 
				combinations.add(new Combination(array, i, j));
		
		int min = combinations.stream()
				.mapToInt(Combination::getDistance)
				.min().getAsInt();
		
		combinations.stream()
			.filter(c -> c.getDistance() == min)
			.forEach(c -> System.out.println(c));
	}
	
	static class Combination {
		int indexA, indexB, valueA, valueB;

		public Combination(int[] array, int indexA, int indexB) {
			this.indexA = indexA;
			this.indexB = indexB;
			this.valueA = array[indexA];
			this.valueB = array[indexB];
		}

		public int getDistance() {
			return getHigh() - getLow();
		}

		public boolean isValueAHigh() {
			return valueA > valueB;
		}

		public int getHigh() {
			return isValueAHigh() ? valueA : valueB; 
		}

		public int getLow() {
			return isValueAHigh() ? valueB : valueA; 
		}

		public int getHighIndex() {
			return isValueAHigh() ? indexA : indexB; 
		}

		public int getLowIndex() {
			return isValueAHigh() ? indexB : indexA; 
		}

		public String toString() {
			return String.format("%d[%d] - %d[%d] = %d", 
									getHigh(), getHighIndex(), 
									getLow(), getLowIndex(), 
									getDistance());
		}
	}
}
