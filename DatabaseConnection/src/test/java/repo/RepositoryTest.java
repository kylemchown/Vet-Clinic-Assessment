package repo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.DatabaseConnection.DatabaseConnectionApplication;
import com.example.DatabaseConnection.model.MySpringBootDataModel;
import com.example.DatabaseConnection.repository.MySpringBootRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { DatabaseConnectionApplication.class })
@DataJpaTest
public class RepositoryTest {

		@Autowired
		private TestEntityManager entityManager;
		
		@Autowired
		private MySpringBootRepository myRepo;
		
		@Test
		public void retrieveByIdTest() {
			MySpringBootDataModel model1 = new MySpringBootDataModel("Bob", "Space", 50);
			entityManager.persist(model1);
			entityManager.flush();
			assertTrue(myRepo.findById(model1.getId()).isPresent());
		}
		
		@Test
		public void retrieveByNameTest() {

			MySpringBootDataModel model1 = new MySpringBootDataModel("Bob", "Space", 50);
			entityManager.persist(model1);
			entityManager.flush();
			MySpringBootDataModel found = myRepo.findByName("Bob");
			assertEquals("Bob", found.getName());
		}

}
