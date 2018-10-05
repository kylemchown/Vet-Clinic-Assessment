package com.qa.personIntegration;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
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

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import com.qa.Vetinary.VetinaryApplication;
import com.qa.Vetinary.model.PersonModel;
import com.qa.Vetinary.repository.PersonRepo;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {VetinaryApplication.class})
@AutoConfigureMockMvc
public class IntegrationTest {
	
	public static ExtentReports report;
	public ExtentTest test;
	
	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private PersonRepo repository;
	
	@Before
	public void clearDB() {
		repository.deleteAll();
		
	}
	
	@BeforeClass

	public static void setupClass() {

		report = new ExtentReports("C:\\Users\\Admin\\Desktop\\Reports\\BasicReport.html", true);

	}
	
	@AfterClass
	
	public static void tearDownClass() {
		
		report.flush();
		
	}
	
	@Test
	public void findingAndRetrievingPersonFromDatabase() throws Exception{
		test = report.startTest("Get info from base");
		repository.save(new PersonModel("Dale"));
		test.log(LogStatus.INFO, "Add person successfully");
		mvc.perform(get("/api/person")
		.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content()
		.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$[0].name", is("Dale")));
		test.log(LogStatus.PASS, "The test has now passed");
	}
	
	@Test
	public void addAPersonToDatabaseTest() throws Exception{
		//repository.save(new MySpringBootDataModel("Dale", "Salford", 2));
		test = report.startTest("Add person to the base");
		mvc.perform(MockMvcRequestBuilders.post("/api/person")
		.contentType(MediaType.APPLICATION_JSON)
		.content("{\"name\" : \"Robert\"}"))
		.andExpect(status().isOk())
		.andExpect(content()
		.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.name", is("Robert")));
		test.log(LogStatus.PASS, "There's now a grin on my face");
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
		test = report.startTest("Update person in database");
		
		
		repository.save(new PersonModel("Dale"));
		test.log(LogStatus.INFO, "Starting person added to database");
		
		Long id = repository.findAll().get(0).getId();
		test.log(LogStatus.INFO, "Id of the person added to the database found");
				
		
		mvc.perform(MockMvcRequestBuilders.put("/api/person/" + id)

				.content("{\"name\" : \"Bill\"}")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content()
				.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.name", is("Bill")));
		
		test.log(LogStatus.PASS, "Person successfully updated");
	}
	
	@Test
	public void deletePersonInDatabase() throws Exception {
		test = report.startTest("Delete person from database");
		repository.save(new PersonModel("Dale"));
		test.log(LogStatus.INFO, "Person to be deleted added to the database");
		
		Long id = repository.findAll().get(0).getId();
		test.log(LogStatus.INFO, "Id of the person added to the database found");
		mvc.perform(MockMvcRequestBuilders.delete("/api/person/" + id))
				.andExpect(status().is2xxSuccessful());
		test.log(LogStatus.PASS, "Person successfully deleted");
		
		}

	
}

