package so.a56159078;
import java.io.IOException;
import java.time.Instant;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class JacksonInstantDeserializer extends StdDeserializer<Instant> {
	public JacksonInstantDeserializer() { this(null); }
	public JacksonInstantDeserializer(Class<?> clazz) { super(clazz); }
	
	@Override
	public Instant deserialize(JsonParser parser, DeserializationContext ctx) throws IOException {
		return Instant.parse(parser.getText());
	}
	
	public class MyDTO {
	    @JsonDeserialize(using = JacksonInstantDeserializer.class)
	    public Instant instant;
	}
}
