package so.a59080640;

import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

public class PersistingManyToOne {
	
	public void persist(Collection<Category> categories) {
		EntityManager em = getEntityManager();
		em.getTransaction().begin();
		
		// Persist all categories first, without persisting the questions
		categories.forEach(cat -> safelyPersistCategory(em, cat));
		// Associated the not yet persisted questions with the persisted categories  
		categories.stream().forEach(cat -> cat.getQuestions().stream().forEach(question -> question.setCategory(cat)));
		// Finally persist each question
		categories.stream().flatMap(cat -> cat.getQuestions().stream()).forEach(question -> em.persist(question));

		em.getTransaction().commit();
		em.close();
	}
	
	// Safely persists Category without trying to create a relationship to 
	// potentially not persisted Questions
	private void safelyPersistCategory(EntityManager em, Category category) {
		List<Question> questions = category.getQuestions();
		category.setQuestions(null);
		em.persist(category);
		category.setQuestions(questions);
	}
	
	private EntityManager getEntityManager() {
		return null;
	}

	@Entity
	@Table(name = "Category")
	static class Category {
		
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "CID")
		private int categoryId;
				  
		@Column(name = "CNAME")
		private String categoryName;

		@OneToMany(mappedBy = "category" , cascade = CascadeType.PERSIST)
		private List<Question> questions;

		public int getCategoryId() {
			return categoryId;
		}
	
		public void setCategoryId(int categoryId) {
			this.categoryId = categoryId;
		}
	
		public String getCategoryName() {
			return categoryName;
		}
	
		public void setCategoryName(String categoryName) {
			this.categoryName = categoryName;
		}
	
		public List<Question> getQuestions() {
			return questions;
		}
	
		public void setQuestions(List<Question> questions) {
			this.questions = questions;
		}
	}

	@Entity
	@Table(name = "Question")
	static class Question {
		
		@Id
		@Column(name = "QID")
		private int id;
		
		@Column(name = "QText")
		private String question;
		
		@ManyToOne()
		private Category category;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getQuestion() {
			return question;
		}

		public void setQuestion(String question) {
			this.question = question;
		}

		public Category getCategory() {
			return category;
		}

		public void setCategory(Category category) {
			this.category = category;
		}
	}
}
