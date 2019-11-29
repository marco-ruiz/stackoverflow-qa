package so.a59097202;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class EnumSelector {
	
	public static void main(String[] args) {
		Map<Integer, String> store = new HashMap<>();
		
		store.computeIfAbsent(1, EmotionalState::findLabel);
		store.computeIfAbsent(4, EmotionalState::findLabel);
		store.computeIfAbsent(8, EmotionalState::findLabel);
		store.computeIfAbsent(10, EmotionalState::findLabel);
		store.computeIfAbsent(35, EmotionalState::findLabel);
		store.computeIfAbsent(0, EmotionalState::findLabel);
		store.computeIfAbsent(40, EmotionalState::findLabel);
		store.computeIfAbsent(25, EmotionalState::findLabel);
		store.computeIfAbsent(33, EmotionalState::findLabel);
		store.computeIfAbsent(55, EmotionalState::findLabel);
		store.computeIfAbsent(-1, EmotionalState::findLabel);
		store.computeIfAbsent(100, EmotionalState::findLabel);

		System.out.println(store);
	}

	public static enum EmotionalState {
		NULL("Null"), // No levels
		SINGLE("Single", 0), // Single level of 0
		COMBO_ENUM("Combo", 20, 30, 40, 50, 60), // From 20-30 + 40, 50, 60
		ENUMERATED("Enumerated", 33, 33, 28, 55), // From 33-33 + 28, 55
		
		SAD("Sad", 1, 6), 
		OK("Ok", 7, 8), 
		HAPPY("Happy", 9, 15),
		DEFAULT_STATE("Default");
		
		private String name;
		private List<Integer> levels;
		
		EmotionalState(String name, int... levels) {
			this.name = name;

			// The first two values will be considered as range
			IntStream initialStream = (levels.length < 2) ? 
					Arrays.stream(levels) : // Single value
					IntStream.range(levels[0], levels[1] + 1); // Range of values
			
			this.levels = initialStream
					.mapToObj(Integer::new)
					.collect(Collectors.toList());
			
			// Add remaining values as literals
			if (levels.length > 2)
				Arrays.stream(Arrays.copyOfRange(levels, 2, levels.length))
					.mapToObj(Integer::new)
					.forEach(this.levels::add);
		}

		public String getName() {
			return name;
		}

		public boolean containsLevel(int level) {
			return levels.stream()
					.filter(l -> l.intValue() == level)
					.findAny()
					.isPresent();
		}
		
		public static EmotionalState findFor(int level) {
			return Stream.of(EmotionalState.values())
					.filter(en -> en.containsLevel(level))
					.findAny()
					.orElse(DEFAULT_STATE);
		}
		
		public static String findLabel(String level) {
			return findLabel(Integer.parseInt(level));
		}

		public static String findLabel(int level) {
			return EmotionalState.findFor(level).getName();
		}
	}
}
