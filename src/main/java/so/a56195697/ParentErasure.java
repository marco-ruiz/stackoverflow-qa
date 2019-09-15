package so.a56195697;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ParentErasure {
	
	public abstract class AbstractDao<T extends DatabaseTable, R extends Record> {
		private Connection connection;
		private Map<Class, Function> mappers = new HashMap<>();
		
		public <M> void registerMapper(Class<M> mappingClass, Function<M, R> mapper) {
			mappers.put(mappingClass, mapper);
		}
		
		public <M> List<M> insert(List<M> records) {
			if (records.isEmpty())
				return records;
			M rec = records.get(0);
			
			List<? extends Record> actualRecords = (rec instanceof Record) ? (List<Record>) records
			        : createMappedRecords(records, rec.getClass());
			
			connection.insertBulk(actualRecords);
			return records;
		}
		
		private <M> List<R> createMappedRecords(List<M> records, Class<? extends Object> recordsClazz) {
			Function<M, R> mapper = mappers.get(recordsClazz);
			return records.stream().map(mapper::apply).collect(Collectors.toList());
		}
	}
	
	public interface Dao<T> {
		public List<T> insert(List<T> records);
	}
	
	class Record {}
	class DatabaseTable {}
	class DatabaseRecord {}
	
	class Connection {
		public void insertBulk(List<? extends Record> records) {
		}
	}
}
