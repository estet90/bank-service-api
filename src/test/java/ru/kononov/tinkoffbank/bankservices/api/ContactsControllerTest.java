package ru.kononov.tinkoffbank.bankservices.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import ru.kononov.tinkoffbank.bankservices.api.config.CxfRestConfig;
import ru.kononov.tinkoffbank.bankservices.entities.Application;
import ru.kononov.tinkoffbank.bankservices.services.ApplicationService;

import javax.ws.rs.core.MediaType;
import java.util.Comparator;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CxfRestConfig.class)
public class ContactsControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@MockBean
	private ApplicationService applicationService;

	private Application application = new Application();
	private Long correctApplicationId = 1L;
	private Long incorrectApplicationId = 2L;

	@Before
	public void setUp() {
		this.mockMvc = webAppContextSetup(webApplicationContext).build();

		Mockito.when(applicationService.getLastProductByContactId(correctApplicationId)).thenReturn(application);
		Mockito.when(applicationService.getLastProductByContactId(incorrectApplicationId)).thenReturn(null);
	}

		@Test
	public void test() throws Exception {
			mockMvc.perform(get("http://localhost:8080/bank-services/api/contacts/1/applications/last")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

}
