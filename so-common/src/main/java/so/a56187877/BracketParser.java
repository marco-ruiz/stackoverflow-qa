package so.a56187877;
import java.util.ArrayList;
import java.util.List;

public class BracketParser {

	public static List<String> parse(String target) throws Exception {
		List<String> results = new ArrayList<>();
		for (int idx = 0; idx < target.length(); idx++) {
			if (target.charAt(idx) == '[') {
				String result = readResult(target, idx + 1);
				if (result == null) throw new Exception();
				results.add(result);
				idx += result.length() + 1;
			}
		}
		return results;
	}
	
	private static String readResult(String target, int startIdx) {
		int openBrackets = 0;
		for (int idx = startIdx; idx < target.length(); idx++) {
			char c = target.charAt(idx);
			if (openBrackets == 0 && c == ']')
				return target.substring(startIdx, idx); 
			if (c == '[') openBrackets++;
			if (c == ']') openBrackets--;
		}
		return null;
	}

	public static void main(String[] args) throws Exception {
		System.out.println(parse("c[ts[0],99:99,99:99] + 5 - d[ts[1],99:99,99:99, ts[2]] + 5"));
	}
}
