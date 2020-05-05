package eu.justas.payments.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PaymentsController.class)
public class PaymentsControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void creates_payment() throws Exception {

        mvc.perform(post("/payments"))
                .andExpect(status().isCreated());
    }

}
