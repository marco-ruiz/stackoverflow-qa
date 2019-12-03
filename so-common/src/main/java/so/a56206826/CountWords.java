package so.a56206826;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class CountWords {

	public static void main(String[] args) {
		String text = "the quick brown fox jumped over the lazy dog's bowl.\nthe dog was angry with the fox for considering him lazy.";
		Comparator<WordStats> sortingCriteria = Comparator
				.comparing(WordStats::getCount).reversed()
				.thenComparing(WordStats::getWord);

		createStats(text, sortingCriteria).forEach(System.out::println);
	}

	public static List<WordStats> createStats(String text, Comparator<WordStats> sortingCriteria) {
		return createOccurrencesMap(text).values().stream()
				.map(WordStats::new)
				.sorted(sortingCriteria)
				.collect(Collectors.toList());
	}
	
	public static Map<String, List<WordOccurrence>> createOccurrencesMap(String text) {
		text = text.replaceAll("\\.", " ");
//		text = text.replaceAll("'s", ""); // dog's != dog ???
		Map<String, List<WordOccurrence>> result = new HashMap<>();
		String[] lines = text.split("\n");
		for (int i = 0; i < lines.length; i++)
			for (String word : lines[i].split("\\s+")) 
				result.computeIfAbsent(word, w -> new ArrayList<>())
							.add(new WordOccurrence(word, i + 1));
		
		return result;
	}
	
	static class WordStats {
		private List<WordOccurrence> occurrences;

		public WordStats(List<WordOccurrence> words) {
			this.occurrences = words;
		}
		
		public String getWord() {
			return occurrences.get(0).getWord();
		}

		public int getCount() {
			return occurrences.size();
		}
		
		public Set<Integer> getLines() {
			return occurrences.stream().map(WordOccurrence::getLineNumber).collect(Collectors.toSet());
		}
		
		public String toString() {
			return String.format("%s %d %s", getWord(), getCount(), getLines());
		}
	}
	
	static class WordOccurrence {
		private final String word;
		private final int lineNumber;

		public WordOccurrence(String word, int lineNumber) {
			this.word = word;
			this.lineNumber = lineNumber;
		}

		public String getWord() {
			return word;
		}

		public int getLineNumber() {
			return lineNumber;
		}
		
		public String toString() {
			return word + "@" + lineNumber;
		}
	}
}
