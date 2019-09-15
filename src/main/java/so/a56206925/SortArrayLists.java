package so.a56206925;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class SortArrayLists {

	public static void main(String[] args) {
		Comparator<MyObject> comparator = Comparator.comparingDouble(MyObject::getDoubleValue);		
		
		MyObject[] array = new MyObject[] {
				new MyObject(55),
				new MyObject(17.3),
				new MyObject(2.43),
				new MyObject(375),
				new MyObject(100),
				new MyObject(255)
		};
		List<MyObject> list = Arrays.asList(array);
		list.sort(comparator);
		Arrays.sort(array, comparator);

		list.stream().forEach(System.out::println);
		System.out.println("\n\n");
		Arrays.stream(array).forEach(System.out::println);
	}
	
	static class MyObject {
		private double doubleValue;

		public MyObject(double doubleValue) {
			this.doubleValue = doubleValue;
		}

		public double getDoubleValue() {
			return doubleValue;
		}
		
		public String toString() {
			return doubleValue + "";
		}
	}
}
