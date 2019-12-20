package so.a59419139;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OptionalsTester {

	public static void main(String[] args) {
		Supplier<Optional<?>> s1 = () -> Optional.of("Hello");
		Supplier<Optional<?>> s2 = () -> Optional.of(1L);
		Supplier<Optional<?>> s3 = () -> Optional.of(55.87);
		Supplier<Optional<?>> s4 = () -> Optional.of(true);
		Supplier<Optional<?>> s5 = () -> Optional.of("World");
		Supplier<Optional<?>> failure = () -> Optional.ofNullable(null);
		Supplier<Optional<?>> s7 = () -> Optional.of(55);

		System.out.print("\nFAILING SEMAPHORES: ");
		new OptionalSemaphores(s1, s2, s3, s4, s5, failure, s7).execute(System.out::println);

		System.out.print("\nSUCCESSFUL SEMAPHORES: ");
		new OptionalSemaphores(s1, s2, s3, s4, s5, s7).execute(System.out::println);
	}

	static class OptionalSemaphores {

		private List<OptionalSupplier> optionalSuppliers;
		private List<Object> results = null;
		private boolean allPresent;

		public OptionalSemaphores(Supplier<Optional<?>>... suppliers) {
			optionalSuppliers = Stream.of(suppliers)
					.map(OptionalSupplier::new)
					.collect(Collectors.toList());

			allPresent = optionalSuppliers.stream()
					.map(OptionalSupplier::getEvaluatedOptional)
					.allMatch(Optional::isPresent);

			if (allPresent)
				results = optionalSuppliers.stream()
						.map(OptionalSupplier::getEvaluatedOptional)
						.map(Optional::get)
						.collect(Collectors.toList());
		}

		public boolean isAllPresent() {
			return allPresent;
		}

		public <T> T execute(Function<List<Object>, T> function, T defaultValue) {
			return (allPresent) ? function.apply(results) : defaultValue;
		}

		public void execute(Consumer<List<Object>> function) {
			if (allPresent)
				function.accept(results);
		}
	}

	static class OptionalSupplier {

		private final Supplier<Optional<?>> optionalSupplier;
		private Optional<?> evaluatedOptional = null;

		public OptionalSupplier(Supplier<Optional<?>> supplier) {
			this.optionalSupplier = supplier;
		}

		public Optional<?> getEvaluatedOptional() {
			if (evaluatedOptional == null)
				evaluatedOptional = optionalSupplier.get();

			return evaluatedOptional;
		}
	}
}
