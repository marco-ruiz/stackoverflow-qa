package so.a59095919;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ArrayAdapterSkeleton {

	public ArrayAdapter<TrimmedElement> createCustomArrayAdapter(
			Context context, List<WholeElement> target, Predicate<WholeElement> condition) {
		
		List<TrimmedElement> trimmedList = target.stream()
				.filter(condition)
				.map(TrimmedElement::new)
				.collect(Collectors.toList());
		
		return new MyCustomArrayAdapter<TrimmedElement>(context, trimmedList);
	}
	
	static class WholeElement {}
	static class TrimmedElement {
		public TrimmedElement(WholeElement ele) {
			// Copy the information desired
		}
	}
	
	// TO AVOID IMPORTING ANDROID DEPENDENCIES
	
	static class ArrayAdapter<T> {
		public ArrayAdapter(Context context, List<T> objects) {}
	}

	static class MyCustomArrayAdapter<T> extends ArrayAdapter<T> {
		public MyCustomArrayAdapter(Context context, List<T> objects) {
			super(context, objects);
		}
	}
	
	static class Context{}
}
