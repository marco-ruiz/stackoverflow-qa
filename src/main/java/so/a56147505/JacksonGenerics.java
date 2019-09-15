package so.a56147505;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonGenerics {
	
	static interface IUser {
		String getName();
		IUser setName(String name);
		Integer getAge();
		IUser setAge(Integer age);
	}
	
	static class User implements IUser {
		
		@JsonProperty("name")
		private String name;
		@JsonProperty("age")
		private Integer age;
		@JsonIgnore
		private Map<String, Object> additionalProperties = new HashMap<String, Object>();
		
		@JsonProperty("name")
		public String getName() {
			return name;
		}
		
		@JsonProperty("name")
		public User setName(String name) {
			this.name = name;
			return this;
		}
		
		@JsonProperty("age")
		public Integer getAge() {
			return age;
		}
		
		@JsonProperty("age")
		public User setAge(Integer age) {
			this.age = age;
			return this;
		}
		
		@JsonAnyGetter
		public Map<String, Object> getAdditionalProperties() {
			return this.additionalProperties;
		}
		
		@JsonAnySetter
		public void setAdditionalProperty(String name, Object value) {
			this.additionalProperties.put(name, value);
		}
	}
	
	static interface IExample<T extends IUser> {
		T getUser();
		void setUser(T user);
	}
	
	static class Example<T extends IUser> implements IExample<T> {
		
		@JsonProperty("user")
		private T user;
		@JsonIgnore
		private Map<String, Object> additionalProperties = new HashMap<String, Object>();
		
		@JsonProperty("user")
		public T getUser() {
			return user;
		}
		
		@JsonProperty("user")
		public void setUser(T t) {
			this.user = t;
		}
		
		@JsonAnyGetter
		public Map<String, Object> getAdditionalProperties() {
			return this.additionalProperties;
		}
		
		@JsonAnySetter
		public void setAdditionalProperty(String name, Object value) {
			this.additionalProperties.put(name, value);
		}
	}
	
	static class ReadYaml {
		
		public void parse() {
			
			String path = "path/to/user.yaml";
			ObjectMapper mapper = new ObjectMapper();
			
			try {
				TypeReference<Example<User>> typeReference = new TypeReference<Example<User>>() { };
				IExample example = mapper.readValue(new File(path), typeReference);
				System.out.println(example.getUser().getName());
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	public static void main(String[] args) {
		new ReadYaml().parse();
	}
}
