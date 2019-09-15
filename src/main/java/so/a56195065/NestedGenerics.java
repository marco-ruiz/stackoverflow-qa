package so.a56195065;

public class NestedGenerics {
	
	static interface ResourceId<T> {
		T get();
	}
	
	static class UserId implements ResourceId<String> {
		public String get() { return null; } 
	}
	
	static interface Resource<I extends ResourceId<?>> {
		I id();
	}
	
	static class User implements Resource<UserId> {
		public UserId id() { return null; } 
	}
	
	static interface Dao<I extends ResourceId<?>, R extends Resource<I>> {
		R findById(I id);
		
		void save(R u);
	}
	
	static class UserDao implements Dao<UserId, User> {
		public User findById(UserId id) { return null; }
		
		public void save(User u) {}
	}
}
