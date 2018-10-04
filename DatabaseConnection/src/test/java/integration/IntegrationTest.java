package integration;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.DatabaseConnection.DatabaseConnectionApplication;
import com.example.DatabaseConnection.model.MySpringBootDataModel;
import com.example.DatabaseConnection.repository.MySpringBootRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {DatabaseConnectionApplication.class})
@TestPropertySource(locations = "classpath:test.properties")
@AutoConfigureMockMvc
public class IntegrationTest {
	
	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private MySpringBootRepository repository;
	
	@Before
	public void clearDB() {
		repository.deleteAll();
	}
	
	@Test
	public void findingAndRetrievingPersonFromDatabase() throws Exception{
		repository.save(new MySpringBootDataModel("Dale", "Salford", 2));
		mvc.perform(get("/api/person")
		.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content()
		.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$[0].name", is("Dale")));
	}
	
	@Test
	public void addAPersonToDatabaseTest() throws Exception{
		//repository.save(new MySpringBootDataModel("Dale", "Salford", 2));
		mvc.perform(MockMvcRequestBuilders.post("/api/person")
		.contentType(MediaType.APPLICATION_JSON)
		.content("{\"name\" : \"Robert\",\"address\" : \"Atlantis\", \"age\": 200}"))
		.andExpect(status().isOk())
		.andExpect(content()
		.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.name", is("Robert")));
	}
	
	@Test
	public void updatePersonInDatabaseTest() throws Exception {
		/*mvc.perform(MockMvcRequestBuilders.post("/api/person")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"name\" : \"Robert\",\"address\" : \"Atlantis\", \"age\": 200}"))

				
				.andExpect(status().isOk())
				.andExpect(content()
				.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.name", is("Robert")));*/
		
		repository.save(new MySpringBootDataModel("Dale", "Salford", 2));
		
		Long id = repository.findAll().get(0).getId();
				
		
		mvc.perform(MockMvcRequestBuilders.put("/api/person/" + id)

				.content("{\"name\" : \"Bill\",\"address\" : \"Atlantis\", \"age\": 200}")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content()
				.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.name", is("Bill")));
		

	}
	
	@Test
	public void deletePersonInDatabase() throws Exception {
		repository.save(new MySpringBootDataModel("Dale", "Salford", 2));
		
		Long id = repository.findAll().get(0).getId();
		mvc.perform(MockMvcRequestBuilders.delete("/api/person/" + id))
				.andExpect(status().is2xxSuccessful());
		
		
		}

	
}
