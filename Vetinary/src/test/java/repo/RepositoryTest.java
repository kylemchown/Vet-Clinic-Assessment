package repo;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.qa.Vetinary.VetinaryApplication;
import com.qa.Vetinary.model.PersonModel;
import com.qa.Vetinary.repository.PersonRepo;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { VetinaryApplication.class })
@DataJpaTest
public class RepositoryTest {
	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private PersonRepo myRepo;
	
	@Test
	public void retrieveByIdTest() {
		PersonModel model1 = new PersonModel("Alice");
		entityManager.persist(model1);
		entityManager.flush();
		assertTrue(myRepo.findById(model1.getId()).isPresent());
	}
}
