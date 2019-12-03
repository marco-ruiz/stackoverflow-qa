package so.a56211328;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public class SortFunctionality {
	private Dao dao;
	
	public Page<Entity> findAll(int page, int size) {
		return dao.findAll(createMyPageRequest(page, size));
	}

	public Page<Entity> getAllBySpecification(
			Specification<Entity> specification, 
			int page, int size) {
		
		return dao.findAll(specification, createMyPageRequest(page, size));
	}
	
    public Page<Entity> getAllBySpecification(
    		Specification<Entity> specification, 
    		Pageable pageable) {
    	
        PageRequest pageRequest = createMyPageRequest(
        		pageable.getPageNumber(), 
        		pageable.getPageSize());
        
		return dao.findAll(specification, pageRequest);
    }	

	private PageRequest createMyPageRequest(int page, int size) {
		return PageRequest.of(page, size, new Sort(Sort.Direction.DESC, "createdAt"));
	}

	static interface Dao extends 
		JpaRepository<Entity, Integer>, JpaSpecificationExecutor<Entity> {}
	
	static class Entity {}
}
