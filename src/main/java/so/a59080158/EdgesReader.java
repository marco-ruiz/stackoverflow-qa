package so.a59080158;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EdgesReader {

	public static void main(String[] args) throws IOException {
		List<Edge> edges = readEdges("PATH/TO/MY/EDGES/TEXT/FILE");
		System.out.println(edges);
	}

	public static List<Edge> readEdges(String filename) throws IOException {
		try (Stream<String> linesStream = Files.lines(Paths.get(filename))) {
			return linesStream
					.map(line -> new Edge(new Scanner(line)))
			        .collect(Collectors.toList());
		}
	}
	
	static class Edge {
		private int first;
		private int second;
		
		public Edge(Scanner scanner) {
			this(scanner.nextInt(), scanner.nextInt());
		}
		
		public Edge(int first, int second) {
			this.first = first;
			this.second = second;
		}

		public int getFirst() {
			return first;
		}

		public int getSecond() {
			return second;
		}

		@Override
		public String toString() {
			return "Edge [first=" + first + ", second=" + second + "]";
		}
	}
}
