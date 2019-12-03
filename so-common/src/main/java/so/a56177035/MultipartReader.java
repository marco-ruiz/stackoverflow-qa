package so.a56177035;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.multipart.MultipartFile;

public class MultipartReader {
	
	public List<String> read(MultipartFile file) throws IOException {
		InputStreamReader in = new InputStreamReader(file.getInputStream());
		file.getBytes();
		try (BufferedReader reader = new BufferedReader(in)) {
			return reader.lines().collect(Collectors.toList());
		}
	}
}
