package so.a59097202;

import java.util.HashMap;
import java.util.Map;
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
		SAD("Sad", 1, 6), 
		OK("Ok", 7, 8), 
		HAPPY("Happy", 9, 15),
		DEFAULT_STATE("Default");
		
		private String name;
		private int startLevel;
		private int endLevel;
		
		EmotionalState(String name) {
			this(name, 0, -1);
		}
		
		EmotionalState(String name, int startLevel, int endLevel) {
			this.name = name;
			this.startLevel = startLevel;
			this.endLevel = endLevel;
		}

		public String getName() {
			return name;
		}

		public boolean containsLevel(int level) {
			return startLevel <= level && level <= endLevel;
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
