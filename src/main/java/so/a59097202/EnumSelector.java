package so.a59097202;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EnumSelector {
	
	public static void main(String[] args) {
		Map<Integer, String> store = new HashMap<>();
		
		EmotionalState.save(store, 1);
		EmotionalState.save(store, 4);
		EmotionalState.save(store, 8);
		EmotionalState.save(store, 10);
		EmotionalState.save(store, -1);
		System.out.println(store);
	}

	public static enum EmotionalState {
		SAD("Sad", 1, 2, 3, 4, 5, 6), 
		OK("Ok", 7, 8), 
		HAPPY("Happy", 9, 10),
		DEFAULT_STATE("Default");
		
		private String name;
		private List<Integer> levels;
		
		EmotionalState(String name, int... levels) {
			this.name = name;
			this.levels = Arrays.stream(levels).mapToObj(Integer::new).collect(Collectors.toList());
		}
		
		public String getName() {
			return name;
		}

		public boolean containsLevel(int level) {
			return levels.stream().filter(l -> l.intValue() == level).findAny().isPresent();
		}
		
		public static EmotionalState findFor(int level) {
			return Stream.of(EmotionalState.values())
					.filter(en -> en.containsLevel(level))
					.findAny()
					.orElse(DEFAULT_STATE);
		}
		
		public static void save(Map<Integer, String> store, String level) {
			save(store, Integer.parseInt(level));
		}

		public static void save(Map<Integer, String> store, int level) {
			store.put(level, EmotionalState.findFor(level).getName());
		}
	}
}
